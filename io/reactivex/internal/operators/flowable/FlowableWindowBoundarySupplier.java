package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySupplier<T, B>
  extends AbstractFlowableWithUpstream<T, Flowable<T>>
{
  final int capacityHint;
  final Callable<? extends Publisher<B>> other;
  
  public FlowableWindowBoundarySupplier(Flowable<T> paramFlowable, Callable<? extends Publisher<B>> paramCallable, int paramInt)
  {
    super(paramFlowable);
    this.other = paramCallable;
    this.capacityHint = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super Flowable<T>> paramSubscriber)
  {
    paramSubscriber = new WindowBoundaryMainSubscriber(paramSubscriber, this.capacityHint, this.other);
    this.source.subscribe(paramSubscriber);
  }
  
  static final class WindowBoundaryInnerSubscriber<T, B>
    extends DisposableSubscriber<B>
  {
    boolean done;
    final FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber<T, B> parent;
    
    WindowBoundaryInnerSubscriber(FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber<T, B> paramWindowBoundaryMainSubscriber)
    {
      this.parent = paramWindowBoundaryMainSubscriber;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.parent.innerComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      if (this.done) {
        return;
      }
      this.done = true;
      dispose();
      this.parent.innerNext(this);
    }
  }
  
  static final class WindowBoundaryMainSubscriber<T, B>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    static final FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<Object, Object> BOUNDARY_DISPOSED = new FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber(null);
    static final Object NEXT_WINDOW = new Object();
    private static final long serialVersionUID = 2233020065421370272L;
    final AtomicReference<FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<T, B>> boundarySubscriber;
    final int capacityHint;
    volatile boolean done;
    final Subscriber<? super Flowable<T>> downstream;
    long emitted;
    final AtomicThrowable errors;
    final Callable<? extends Publisher<B>> other;
    final MpscLinkedQueue<Object> queue;
    final AtomicLong requested;
    final AtomicBoolean stopWindows;
    Subscription upstream;
    UnicastProcessor<T> window;
    final AtomicInteger windows;
    
    WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, int paramInt, Callable<? extends Publisher<B>> paramCallable)
    {
      this.downstream = paramSubscriber;
      this.capacityHint = paramInt;
      this.boundarySubscriber = new AtomicReference();
      this.windows = new AtomicInteger(1);
      this.queue = new MpscLinkedQueue();
      this.errors = new AtomicThrowable();
      this.stopWindows = new AtomicBoolean();
      this.other = paramCallable;
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        disposeBoundary();
        if (this.windows.decrementAndGet() == 0) {
          this.upstream.cancel();
        }
      }
    }
    
    void disposeBoundary()
    {
      Disposable localDisposable = (Disposable)this.boundarySubscriber.getAndSet(BOUNDARY_DISPOSED);
      if ((localDisposable != null) && (localDisposable != BOUNDARY_DISPOSED)) {
        localDisposable.dispose();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 139	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 74	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 91	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:queue	Lio/reactivex/internal/queue/MpscLinkedQueue;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 96	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   22: astore_3
      //   23: aload_0
      //   24: getfield 141	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:emitted	J
      //   27: lstore 4
      //   29: iconst_1
      //   30: istore 6
      //   32: aload_0
      //   33: getfield 86	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:windows	Ljava/util/concurrent/atomic/AtomicInteger;
      //   36: invokevirtual 144	java/util/concurrent/atomic/AtomicInteger:get	()I
      //   39: ifne +13 -> 52
      //   42: aload_2
      //   43: invokevirtual 147	io/reactivex/internal/queue/MpscLinkedQueue:clear	()V
      //   46: aload_0
      //   47: aconst_null
      //   48: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   51: return
      //   52: aload_0
      //   53: getfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   56: astore 7
      //   58: aload_0
      //   59: getfield 151	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:done	Z
      //   62: istore 8
      //   64: iload 8
      //   66: ifeq +43 -> 109
      //   69: aload_3
      //   70: invokevirtual 154	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   73: ifnull +36 -> 109
      //   76: aload_2
      //   77: invokevirtual 147	io/reactivex/internal/queue/MpscLinkedQueue:clear	()V
      //   80: aload_3
      //   81: invokevirtual 158	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   84: astore_3
      //   85: aload 7
      //   87: ifnull +14 -> 101
      //   90: aload_0
      //   91: aconst_null
      //   92: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   95: aload 7
      //   97: aload_3
      //   98: invokevirtual 164	io/reactivex/processors/UnicastProcessor:onError	(Ljava/lang/Throwable;)V
      //   101: aload_1
      //   102: aload_3
      //   103: invokeinterface 167 2 0
      //   108: return
      //   109: aload_2
      //   110: invokevirtual 170	io/reactivex/internal/queue/MpscLinkedQueue:poll	()Ljava/lang/Object;
      //   113: astore 9
      //   115: aload 9
      //   117: ifnonnull +9 -> 126
      //   120: iconst_1
      //   121: istore 10
      //   123: goto +6 -> 129
      //   126: iconst_0
      //   127: istore 10
      //   129: iload 8
      //   131: ifeq +65 -> 196
      //   134: iload 10
      //   136: ifeq +60 -> 196
      //   139: aload_3
      //   140: invokevirtual 158	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   143: astore_3
      //   144: aload_3
      //   145: ifnonnull +27 -> 172
      //   148: aload 7
      //   150: ifnull +13 -> 163
      //   153: aload_0
      //   154: aconst_null
      //   155: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   158: aload 7
      //   160: invokevirtual 173	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   163: aload_1
      //   164: invokeinterface 174 1 0
      //   169: goto +26 -> 195
      //   172: aload 7
      //   174: ifnull +14 -> 188
      //   177: aload_0
      //   178: aconst_null
      //   179: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   182: aload 7
      //   184: aload_3
      //   185: invokevirtual 164	io/reactivex/processors/UnicastProcessor:onError	(Ljava/lang/Throwable;)V
      //   188: aload_1
      //   189: aload_3
      //   190: invokeinterface 167 2 0
      //   195: return
      //   196: iload 10
      //   198: ifeq +28 -> 226
      //   201: aload_0
      //   202: lload 4
      //   204: putfield 141	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:emitted	J
      //   207: aload_0
      //   208: iload 6
      //   210: ineg
      //   211: invokevirtual 178	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:addAndGet	(I)I
      //   214: istore 10
      //   216: iload 10
      //   218: istore 6
      //   220: iload 10
      //   222: ifne -190 -> 32
      //   225: return
      //   226: aload 9
      //   228: getstatic 69	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:NEXT_WINDOW	Ljava/lang/Object;
      //   231: if_acmpeq +13 -> 244
      //   234: aload 7
      //   236: aload 9
      //   238: invokevirtual 182	io/reactivex/processors/UnicastProcessor:onNext	(Ljava/lang/Object;)V
      //   241: goto -209 -> 32
      //   244: aload 7
      //   246: ifnull +13 -> 259
      //   249: aload_0
      //   250: aconst_null
      //   251: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   254: aload 7
      //   256: invokevirtual 173	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   259: aload_0
      //   260: getfield 101	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:stopWindows	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   263: invokevirtual 185	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   266: ifne -234 -> 32
      //   269: lload 4
      //   271: aload_0
      //   272: getfield 108	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   275: invokevirtual 188	java/util/concurrent/atomic/AtomicLong:get	()J
      //   278: lcmp
      //   279: ifeq +117 -> 396
      //   282: aload_0
      //   283: getfield 76	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:capacityHint	I
      //   286: aload_0
      //   287: invokestatic 192	io/reactivex/processors/UnicastProcessor:create	(ILjava/lang/Runnable;)Lio/reactivex/processors/UnicastProcessor;
      //   290: astore 7
      //   292: aload_0
      //   293: aload 7
      //   295: putfield 149	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:window	Lio/reactivex/processors/UnicastProcessor;
      //   298: aload_0
      //   299: getfield 86	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:windows	Ljava/util/concurrent/atomic/AtomicInteger;
      //   302: invokevirtual 193	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
      //   305: pop
      //   306: aload_0
      //   307: getfield 103	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:other	Ljava/util/concurrent/Callable;
      //   310: invokeinterface 198 1 0
      //   315: ldc -56
      //   317: invokestatic 206	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   320: checkcast 208	org/reactivestreams/Publisher
      //   323: astore 9
      //   325: new 57	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryInnerSubscriber
      //   328: dup
      //   329: aload_0
      //   330: invokespecial 61	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryInnerSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber;)V
      //   333: astore 11
      //   335: aload_0
      //   336: getfield 81	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:boundarySubscriber	Ljava/util/concurrent/atomic/AtomicReference;
      //   339: aconst_null
      //   340: aload 11
      //   342: invokevirtual 211	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   345: ifeq -313 -> 32
      //   348: aload 9
      //   350: aload 11
      //   352: invokeinterface 215 2 0
      //   357: lload 4
      //   359: lconst_1
      //   360: ladd
      //   361: lstore 4
      //   363: aload_1
      //   364: aload 7
      //   366: invokeinterface 216 2 0
      //   371: goto -339 -> 32
      //   374: astore 7
      //   376: aload 7
      //   378: invokestatic 221	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   381: aload_3
      //   382: aload 7
      //   384: invokevirtual 225	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   387: pop
      //   388: aload_0
      //   389: iconst_1
      //   390: putfield 151	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:done	Z
      //   393: goto -361 -> 32
      //   396: aload_0
      //   397: getfield 124	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   400: invokeinterface 126 1 0
      //   405: aload_0
      //   406: invokevirtual 118	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:disposeBoundary	()V
      //   409: aload_3
      //   410: new 227	io/reactivex/exceptions/MissingBackpressureException
      //   413: dup
      //   414: ldc -27
      //   416: invokespecial 232	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   419: invokevirtual 225	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   422: pop
      //   423: aload_0
      //   424: iconst_1
      //   425: putfield 151	io/reactivex/internal/operators/flowable/FlowableWindowBoundarySupplier$WindowBoundaryMainSubscriber:done	Z
      //   428: goto -396 -> 32
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	431	0	this	WindowBoundaryMainSubscriber
      //   12	352	1	localSubscriber	Subscriber
      //   17	93	2	localMpscLinkedQueue	MpscLinkedQueue
      //   22	388	3	localObject1	Object
      //   27	335	4	l	long
      //   30	189	6	i	int
      //   56	309	7	localUnicastProcessor	UnicastProcessor
      //   374	9	7	localThrowable	Throwable
      //   62	68	8	bool	boolean
      //   113	236	9	localObject2	Object
      //   121	100	10	j	int
      //   333	18	11	localWindowBoundaryInnerSubscriber	FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber
      // Exception table:
      //   from	to	target	type
      //   306	325	374	finally
    }
    
    void innerComplete()
    {
      this.upstream.cancel();
      this.done = true;
      drain();
    }
    
    void innerError(Throwable paramThrowable)
    {
      this.upstream.cancel();
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerNext(FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<T, B> paramWindowBoundaryInnerSubscriber)
    {
      this.boundarySubscriber.compareAndSet(paramWindowBoundaryInnerSubscriber, null);
      this.queue.offer(NEXT_WINDOW);
      drain();
    }
    
    public void onComplete()
    {
      disposeBoundary();
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      disposeBoundary();
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        this.queue.offer(NEXT_WINDOW);
        drain();
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public void request(long paramLong)
    {
      BackpressureHelper.add(this.requested, paramLong);
    }
    
    public void run()
    {
      if (this.windows.decrementAndGet() == 0) {
        this.upstream.cancel();
      }
    }
  }
}
