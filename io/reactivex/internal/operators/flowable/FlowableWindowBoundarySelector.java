package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySelector<T, B, V>
  extends AbstractFlowableWithUpstream<T, Flowable<T>>
{
  final int bufferSize;
  final Function<? super B, ? extends Publisher<V>> close;
  final Publisher<B> open;
  
  public FlowableWindowBoundarySelector(Flowable<T> paramFlowable, Publisher<B> paramPublisher, Function<? super B, ? extends Publisher<V>> paramFunction, int paramInt)
  {
    super(paramFlowable);
    this.open = paramPublisher;
    this.close = paramFunction;
    this.bufferSize = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super Flowable<T>> paramSubscriber)
  {
    this.source.subscribe(new WindowBoundaryMainSubscriber(new SerializedSubscriber(paramSubscriber), this.open, this.close, this.bufferSize));
  }
  
  static final class OperatorWindowBoundaryCloseSubscriber<T, V>
    extends DisposableSubscriber<V>
  {
    boolean done;
    final FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, ?, V> parent;
    final UnicastProcessor<T> w;
    
    OperatorWindowBoundaryCloseSubscriber(FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, ?, V> paramWindowBoundaryMainSubscriber, UnicastProcessor<T> paramUnicastProcessor)
    {
      this.parent = paramWindowBoundaryMainSubscriber;
      this.w = paramUnicastProcessor;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.parent.close(this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.parent.error(paramThrowable);
    }
    
    public void onNext(V paramV)
    {
      cancel();
      onComplete();
    }
  }
  
  static final class OperatorWindowBoundaryOpenSubscriber<T, B>
    extends DisposableSubscriber<B>
  {
    final FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, B, ?> parent;
    
    OperatorWindowBoundaryOpenSubscriber(FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, B, ?> paramWindowBoundaryMainSubscriber)
    {
      this.parent = paramWindowBoundaryMainSubscriber;
    }
    
    public void onComplete()
    {
      this.parent.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.error(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      this.parent.open(paramB);
    }
  }
  
  static final class WindowBoundaryMainSubscriber<T, B, V>
    extends QueueDrainSubscriber<T, Object, Flowable<T>>
    implements Subscription
  {
    final AtomicReference<Disposable> boundary = new AtomicReference();
    final int bufferSize;
    final Function<? super B, ? extends Publisher<V>> close;
    final Publisher<B> open;
    final CompositeDisposable resources;
    final AtomicBoolean stopWindows = new AtomicBoolean();
    Subscription upstream;
    final AtomicLong windows = new AtomicLong();
    final List<UnicastProcessor<T>> ws;
    
    WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, Publisher<B> paramPublisher, Function<? super B, ? extends Publisher<V>> paramFunction, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.open = paramPublisher;
      this.close = paramFunction;
      this.bufferSize = paramInt;
      this.resources = new CompositeDisposable();
      this.ws = new ArrayList();
      this.windows.lazySet(1L);
    }
    
    public boolean accept(Subscriber<? super Flowable<T>> paramSubscriber, Object paramObject)
    {
      return false;
    }
    
    public void cancel()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this.boundary);
        if (this.windows.decrementAndGet() == 0L) {
          this.upstream.cancel();
        }
      }
    }
    
    void close(FlowableWindowBoundarySelector.OperatorWindowBoundaryCloseSubscriber<T, V> paramOperatorWindowBoundaryCloseSubscriber)
    {
      this.resources.delete(paramOperatorWindowBoundaryCloseSubscriber);
      this.queue.offer(new FlowableWindowBoundarySelector.WindowOperation(paramOperatorWindowBoundaryCloseSubscriber.w, null));
      if (enter()) {
        drainLoop();
      }
    }
    
    void dispose()
    {
      this.resources.dispose();
      DisposableHelper.dispose(this.boundary);
    }
    
    /* Error */
    void drainLoop()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 111	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:queue	Lio/reactivex/internal/fuseable/SimplePlainQueue;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 142	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 73	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:ws	Ljava/util/List;
      //   14: astore_3
      //   15: iconst_1
      //   16: istore 4
      //   18: aload_0
      //   19: getfield 146	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:done	Z
      //   22: istore 5
      //   24: aload_1
      //   25: invokeinterface 150 1 0
      //   30: astore 6
      //   32: aload 6
      //   34: ifnonnull +9 -> 43
      //   37: iconst_1
      //   38: istore 7
      //   40: goto +6 -> 46
      //   43: iconst_0
      //   44: istore 7
      //   46: iload 5
      //   48: ifeq +91 -> 139
      //   51: iload 7
      //   53: ifeq +86 -> 139
      //   56: aload_0
      //   57: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:dispose	()V
      //   60: aload_0
      //   61: getfield 155	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:error	Ljava/lang/Throwable;
      //   64: astore_2
      //   65: aload_2
      //   66: ifnull +35 -> 101
      //   69: aload_3
      //   70: invokeinterface 161 1 0
      //   75: astore_1
      //   76: aload_1
      //   77: invokeinterface 166 1 0
      //   82: ifeq +50 -> 132
      //   85: aload_1
      //   86: invokeinterface 169 1 0
      //   91: checkcast 171	io/reactivex/processors/UnicastProcessor
      //   94: aload_2
      //   95: invokevirtual 175	io/reactivex/processors/UnicastProcessor:onError	(Ljava/lang/Throwable;)V
      //   98: goto -22 -> 76
      //   101: aload_3
      //   102: invokeinterface 161 1 0
      //   107: astore_2
      //   108: aload_2
      //   109: invokeinterface 166 1 0
      //   114: ifeq +18 -> 132
      //   117: aload_2
      //   118: invokeinterface 169 1 0
      //   123: checkcast 171	io/reactivex/processors/UnicastProcessor
      //   126: invokevirtual 178	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   129: goto -21 -> 108
      //   132: aload_3
      //   133: invokeinterface 181 1 0
      //   138: return
      //   139: iload 7
      //   141: ifeq +22 -> 163
      //   144: aload_0
      //   145: iload 4
      //   147: ineg
      //   148: invokevirtual 185	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:leave	(I)I
      //   151: istore 7
      //   153: iload 7
      //   155: istore 4
      //   157: iload 7
      //   159: ifne -141 -> 18
      //   162: return
      //   163: aload 6
      //   165: instanceof 113
      //   168: ifeq +231 -> 399
      //   171: aload 6
      //   173: checkcast 113	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowOperation
      //   176: astore 8
      //   178: aload 8
      //   180: getfield 186	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/processors/UnicastProcessor;
      //   183: ifnull +42 -> 225
      //   186: aload_3
      //   187: aload 8
      //   189: getfield 186	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/processors/UnicastProcessor;
      //   192: invokeinterface 189 2 0
      //   197: ifeq -179 -> 18
      //   200: aload 8
      //   202: getfield 186	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/processors/UnicastProcessor;
      //   205: invokevirtual 178	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   208: aload_0
      //   209: getfield 52	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:windows	Ljava/util/concurrent/atomic/AtomicLong;
      //   212: invokevirtual 98	java/util/concurrent/atomic/AtomicLong:decrementAndGet	()J
      //   215: lconst_0
      //   216: lcmp
      //   217: ifne -199 -> 18
      //   220: aload_0
      //   221: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:dispose	()V
      //   224: return
      //   225: aload_0
      //   226: getfield 57	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:stopWindows	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   229: invokevirtual 192	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   232: ifeq +6 -> 238
      //   235: goto -217 -> 18
      //   238: aload_0
      //   239: getfield 63	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:bufferSize	I
      //   242: invokestatic 196	io/reactivex/processors/UnicastProcessor:create	(I)Lio/reactivex/processors/UnicastProcessor;
      //   245: astore 6
      //   247: aload_0
      //   248: invokevirtual 199	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:requested	()J
      //   251: lstore 9
      //   253: lload 9
      //   255: lconst_0
      //   256: lcmp
      //   257: ifeq +120 -> 377
      //   260: aload_3
      //   261: aload 6
      //   263: invokeinterface 202 2 0
      //   268: pop
      //   269: aload_2
      //   270: aload 6
      //   272: invokeinterface 208 2 0
      //   277: lload 9
      //   279: ldc2_w 209
      //   282: lcmp
      //   283: ifeq +9 -> 292
      //   286: aload_0
      //   287: lconst_1
      //   288: invokevirtual 214	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:produced	(J)J
      //   291: pop2
      //   292: aload_0
      //   293: getfield 61	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:close	Lio/reactivex/functions/Function;
      //   296: aload 8
      //   298: getfield 217	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowOperation:open	Ljava/lang/Object;
      //   301: invokeinterface 223 2 0
      //   306: ldc -31
      //   308: invokestatic 231	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   311: checkcast 233	org/reactivestreams/Publisher
      //   314: astore 8
      //   316: new 115	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$OperatorWindowBoundaryCloseSubscriber
      //   319: dup
      //   320: aload_0
      //   321: aload 6
      //   323: invokespecial 236	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$OperatorWindowBoundaryCloseSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber;Lio/reactivex/processors/UnicastProcessor;)V
      //   326: astore 6
      //   328: aload_0
      //   329: getfield 68	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:resources	Lio/reactivex/disposables/CompositeDisposable;
      //   332: aload 6
      //   334: invokevirtual 238	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   337: ifeq -319 -> 18
      //   340: aload_0
      //   341: getfield 52	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:windows	Ljava/util/concurrent/atomic/AtomicLong;
      //   344: invokevirtual 241	java/util/concurrent/atomic/AtomicLong:getAndIncrement	()J
      //   347: pop2
      //   348: aload 8
      //   350: aload 6
      //   352: invokeinterface 245 2 0
      //   357: goto -339 -> 18
      //   360: astore 8
      //   362: aload_0
      //   363: invokevirtual 246	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:cancel	()V
      //   366: aload_2
      //   367: aload 8
      //   369: invokeinterface 247 2 0
      //   374: goto -356 -> 18
      //   377: aload_0
      //   378: invokevirtual 246	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySelector$WindowBoundaryMainSubscriber:cancel	()V
      //   381: aload_2
      //   382: new 249	io/reactivex/exceptions/MissingBackpressureException
      //   385: dup
      //   386: ldc -5
      //   388: invokespecial 254	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   391: invokeinterface 247 2 0
      //   396: goto -378 -> 18
      //   399: aload_3
      //   400: invokeinterface 161 1 0
      //   405: astore 8
      //   407: aload 8
      //   409: invokeinterface 166 1 0
      //   414: ifeq -396 -> 18
      //   417: aload 8
      //   419: invokeinterface 169 1 0
      //   424: checkcast 171	io/reactivex/processors/UnicastProcessor
      //   427: aload 6
      //   429: invokestatic 259	io/reactivex/internal/util/NotificationLite:getValue	(Ljava/lang/Object;)Ljava/lang/Object;
      //   432: invokevirtual 260	io/reactivex/processors/UnicastProcessor:onNext	(Ljava/lang/Object;)V
      //   435: goto -28 -> 407
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	438	0	this	WindowBoundaryMainSubscriber
      //   4	82	1	localObject1	Object
      //   9	373	2	localObject2	Object
      //   14	386	3	localList	List
      //   16	140	4	i	int
      //   22	25	5	bool	boolean
      //   30	398	6	localObject3	Object
      //   38	120	7	j	int
      //   176	173	8	localObject4	Object
      //   360	8	8	localThrowable	Throwable
      //   405	13	8	localIterator	Iterator
      //   251	27	9	l	long
      // Exception table:
      //   from	to	target	type
      //   292	316	360	finally
    }
    
    void error(Throwable paramThrowable)
    {
      this.upstream.cancel();
      this.resources.dispose();
      DisposableHelper.dispose(this.boundary);
      this.downstream.onError(paramThrowable);
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      if (this.windows.decrementAndGet() == 0L) {
        this.resources.dispose();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      if (this.windows.decrementAndGet() == 0L) {
        this.resources.dispose();
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (fastEnter())
      {
        Iterator localIterator = this.ws.iterator();
        while (localIterator.hasNext()) {
          ((UnicastProcessor)localIterator.next()).onNext(paramT);
        }
        if (leave(-1) != 0) {}
      }
      else
      {
        this.queue.offer(NotificationLite.next(paramT));
        if (!enter()) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        if (this.stopWindows.get()) {
          return;
        }
        FlowableWindowBoundarySelector.OperatorWindowBoundaryOpenSubscriber localOperatorWindowBoundaryOpenSubscriber = new FlowableWindowBoundarySelector.OperatorWindowBoundaryOpenSubscriber(this);
        if (this.boundary.compareAndSet(null, localOperatorWindowBoundaryOpenSubscriber))
        {
          paramSubscription.request(Long.MAX_VALUE);
          this.open.subscribe(localOperatorWindowBoundaryOpenSubscriber);
        }
      }
    }
    
    void open(B paramB)
    {
      this.queue.offer(new FlowableWindowBoundarySelector.WindowOperation(null, paramB));
      if (enter()) {
        drainLoop();
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
  }
  
  static final class WindowOperation<T, B>
  {
    final B open;
    final UnicastProcessor<T> w;
    
    WindowOperation(UnicastProcessor<T> paramUnicastProcessor, B paramB)
    {
      this.w = paramUnicastProcessor;
      this.open = paramB;
    }
  }
}
