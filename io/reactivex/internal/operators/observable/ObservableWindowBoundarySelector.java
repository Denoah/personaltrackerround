package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySelector<T, B, V>
  extends AbstractObservableWithUpstream<T, Observable<T>>
{
  final int bufferSize;
  final Function<? super B, ? extends ObservableSource<V>> close;
  final ObservableSource<B> open;
  
  public ObservableWindowBoundarySelector(ObservableSource<T> paramObservableSource, ObservableSource<B> paramObservableSource1, Function<? super B, ? extends ObservableSource<V>> paramFunction, int paramInt)
  {
    super(paramObservableSource);
    this.open = paramObservableSource1;
    this.close = paramFunction;
    this.bufferSize = paramInt;
  }
  
  public void subscribeActual(Observer<? super Observable<T>> paramObserver)
  {
    this.source.subscribe(new WindowBoundaryMainObserver(new SerializedObserver(paramObserver), this.open, this.close, this.bufferSize));
  }
  
  static final class OperatorWindowBoundaryCloseObserver<T, V>
    extends DisposableObserver<V>
  {
    boolean done;
    final ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, ?, V> parent;
    final UnicastSubject<T> w;
    
    OperatorWindowBoundaryCloseObserver(ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, ?, V> paramWindowBoundaryMainObserver, UnicastSubject<T> paramUnicastSubject)
    {
      this.parent = paramWindowBoundaryMainObserver;
      this.w = paramUnicastSubject;
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
      dispose();
      onComplete();
    }
  }
  
  static final class OperatorWindowBoundaryOpenObserver<T, B>
    extends DisposableObserver<B>
  {
    final ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, B, ?> parent;
    
    OperatorWindowBoundaryOpenObserver(ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, B, ?> paramWindowBoundaryMainObserver)
    {
      this.parent = paramWindowBoundaryMainObserver;
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
  
  static final class WindowBoundaryMainObserver<T, B, V>
    extends QueueDrainObserver<T, Object, Observable<T>>
    implements Disposable
  {
    final AtomicReference<Disposable> boundary = new AtomicReference();
    final int bufferSize;
    final Function<? super B, ? extends ObservableSource<V>> close;
    final ObservableSource<B> open;
    final CompositeDisposable resources;
    final AtomicBoolean stopWindows = new AtomicBoolean();
    Disposable upstream;
    final AtomicLong windows = new AtomicLong();
    final List<UnicastSubject<T>> ws;
    
    WindowBoundaryMainObserver(Observer<? super Observable<T>> paramObserver, ObservableSource<B> paramObservableSource, Function<? super B, ? extends ObservableSource<V>> paramFunction, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.open = paramObservableSource;
      this.close = paramFunction;
      this.bufferSize = paramInt;
      this.resources = new CompositeDisposable();
      this.ws = new ArrayList();
      this.windows.lazySet(1L);
    }
    
    public void accept(Observer<? super Observable<T>> paramObserver, Object paramObject) {}
    
    void close(ObservableWindowBoundarySelector.OperatorWindowBoundaryCloseObserver<T, V> paramOperatorWindowBoundaryCloseObserver)
    {
      this.resources.delete(paramOperatorWindowBoundaryCloseObserver);
      this.queue.offer(new ObservableWindowBoundarySelector.WindowOperation(paramOperatorWindowBoundaryCloseObserver.w, null));
      if (enter()) {
        drainLoop();
      }
    }
    
    public void dispose()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this.boundary);
        if (this.windows.decrementAndGet() == 0L) {
          this.upstream.dispose();
        }
      }
    }
    
    void disposeBoundary()
    {
      this.resources.dispose();
      DisposableHelper.dispose(this.boundary);
    }
    
    /* Error */
    void drainLoop()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 92	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:queue	Lio/reactivex/internal/fuseable/SimplePlainQueue;
      //   4: checkcast 36	io/reactivex/internal/queue/MpscLinkedQueue
      //   7: astore_1
      //   8: aload_0
      //   9: getfield 141	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:downstream	Lio/reactivex/Observer;
      //   12: astore_2
      //   13: aload_0
      //   14: getfield 73	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:ws	Ljava/util/List;
      //   17: astore_3
      //   18: iconst_1
      //   19: istore 4
      //   21: aload_0
      //   22: getfield 145	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:done	Z
      //   25: istore 5
      //   27: aload_1
      //   28: invokevirtual 149	io/reactivex/internal/queue/MpscLinkedQueue:poll	()Ljava/lang/Object;
      //   31: astore 6
      //   33: aload 6
      //   35: ifnonnull +9 -> 44
      //   38: iconst_1
      //   39: istore 7
      //   41: goto +6 -> 47
      //   44: iconst_0
      //   45: istore 7
      //   47: iload 5
      //   49: ifeq +91 -> 140
      //   52: iload 7
      //   54: ifeq +86 -> 140
      //   57: aload_0
      //   58: invokevirtual 151	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:disposeBoundary	()V
      //   61: aload_0
      //   62: getfield 155	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:error	Ljava/lang/Throwable;
      //   65: astore_2
      //   66: aload_2
      //   67: ifnull +35 -> 102
      //   70: aload_3
      //   71: invokeinterface 161 1 0
      //   76: astore_1
      //   77: aload_1
      //   78: invokeinterface 166 1 0
      //   83: ifeq +50 -> 133
      //   86: aload_1
      //   87: invokeinterface 169 1 0
      //   92: checkcast 171	io/reactivex/subjects/UnicastSubject
      //   95: aload_2
      //   96: invokevirtual 175	io/reactivex/subjects/UnicastSubject:onError	(Ljava/lang/Throwable;)V
      //   99: goto -22 -> 77
      //   102: aload_3
      //   103: invokeinterface 161 1 0
      //   108: astore_2
      //   109: aload_2
      //   110: invokeinterface 166 1 0
      //   115: ifeq +18 -> 133
      //   118: aload_2
      //   119: invokeinterface 169 1 0
      //   124: checkcast 171	io/reactivex/subjects/UnicastSubject
      //   127: invokevirtual 178	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   130: goto -21 -> 109
      //   133: aload_3
      //   134: invokeinterface 181 1 0
      //   139: return
      //   140: iload 7
      //   142: ifeq +22 -> 164
      //   145: aload_0
      //   146: iload 4
      //   148: ineg
      //   149: invokevirtual 185	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:leave	(I)I
      //   152: istore 7
      //   154: iload 7
      //   156: istore 4
      //   158: iload 7
      //   160: ifne -139 -> 21
      //   163: return
      //   164: aload 6
      //   166: instanceof 94
      //   169: ifeq +190 -> 359
      //   172: aload 6
      //   174: checkcast 94	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowOperation
      //   177: astore 6
      //   179: aload 6
      //   181: getfield 186	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/subjects/UnicastSubject;
      //   184: ifnull +42 -> 226
      //   187: aload_3
      //   188: aload 6
      //   190: getfield 186	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/subjects/UnicastSubject;
      //   193: invokeinterface 189 2 0
      //   198: ifeq -177 -> 21
      //   201: aload 6
      //   203: getfield 186	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowOperation:w	Lio/reactivex/subjects/UnicastSubject;
      //   206: invokevirtual 178	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   209: aload_0
      //   210: getfield 52	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:windows	Ljava/util/concurrent/atomic/AtomicLong;
      //   213: invokevirtual 131	java/util/concurrent/atomic/AtomicLong:decrementAndGet	()J
      //   216: lconst_0
      //   217: lcmp
      //   218: ifne -197 -> 21
      //   221: aload_0
      //   222: invokevirtual 151	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:disposeBoundary	()V
      //   225: return
      //   226: aload_0
      //   227: getfield 57	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:stopWindows	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   230: invokevirtual 192	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   233: ifeq +6 -> 239
      //   236: goto -215 -> 21
      //   239: aload_0
      //   240: getfield 63	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:bufferSize	I
      //   243: invokestatic 196	io/reactivex/subjects/UnicastSubject:create	(I)Lio/reactivex/subjects/UnicastSubject;
      //   246: astore 8
      //   248: aload_3
      //   249: aload 8
      //   251: invokeinterface 199 2 0
      //   256: pop
      //   257: aload_2
      //   258: aload 8
      //   260: invokeinterface 205 2 0
      //   265: aload_0
      //   266: getfield 61	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:close	Lio/reactivex/functions/Function;
      //   269: aload 6
      //   271: getfield 208	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowOperation:open	Ljava/lang/Object;
      //   274: invokeinterface 214 2 0
      //   279: ldc -40
      //   281: invokestatic 222	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   284: checkcast 224	io/reactivex/ObservableSource
      //   287: astore 6
      //   289: new 96	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$OperatorWindowBoundaryCloseObserver
      //   292: dup
      //   293: aload_0
      //   294: aload 8
      //   296: invokespecial 227	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$OperatorWindowBoundaryCloseObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver;Lio/reactivex/subjects/UnicastSubject;)V
      //   299: astore 8
      //   301: aload_0
      //   302: getfield 68	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:resources	Lio/reactivex/disposables/CompositeDisposable;
      //   305: aload 8
      //   307: invokevirtual 229	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   310: ifeq -289 -> 21
      //   313: aload_0
      //   314: getfield 52	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:windows	Ljava/util/concurrent/atomic/AtomicLong;
      //   317: invokevirtual 232	java/util/concurrent/atomic/AtomicLong:getAndIncrement	()J
      //   320: pop2
      //   321: aload 6
      //   323: aload 8
      //   325: invokeinterface 236 2 0
      //   330: goto -309 -> 21
      //   333: astore 6
      //   335: aload 6
      //   337: invokestatic 241	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   340: aload_0
      //   341: getfield 57	io/reactivex/internal/operators/observable/ObservableWindowBoundarySelector$WindowBoundaryMainObserver:stopWindows	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   344: iconst_1
      //   345: invokevirtual 245	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
      //   348: aload_2
      //   349: aload 6
      //   351: invokeinterface 246 2 0
      //   356: goto -335 -> 21
      //   359: aload_3
      //   360: invokeinterface 161 1 0
      //   365: astore 8
      //   367: aload 8
      //   369: invokeinterface 166 1 0
      //   374: ifeq -353 -> 21
      //   377: aload 8
      //   379: invokeinterface 169 1 0
      //   384: checkcast 171	io/reactivex/subjects/UnicastSubject
      //   387: aload 6
      //   389: invokestatic 251	io/reactivex/internal/util/NotificationLite:getValue	(Ljava/lang/Object;)Ljava/lang/Object;
      //   392: invokevirtual 252	io/reactivex/subjects/UnicastSubject:onNext	(Ljava/lang/Object;)V
      //   395: goto -28 -> 367
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	398	0	this	WindowBoundaryMainObserver
      //   7	80	1	localObject1	Object
      //   12	337	2	localObject2	Object
      //   17	343	3	localList	List
      //   19	138	4	i	int
      //   25	23	5	bool	boolean
      //   31	291	6	localObject3	Object
      //   333	55	6	localThrowable	Throwable
      //   39	120	7	j	int
      //   246	132	8	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   265	289	333	finally
    }
    
    void error(Throwable paramThrowable)
    {
      this.upstream.dispose();
      this.resources.dispose();
      onError(paramThrowable);
    }
    
    public boolean isDisposed()
    {
      return this.stopWindows.get();
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
      if (fastEnter())
      {
        Iterator localIterator = this.ws.iterator();
        while (localIterator.hasNext()) {
          ((UnicastSubject)localIterator.next()).onNext(paramT);
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
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
        if (this.stopWindows.get()) {
          return;
        }
        paramDisposable = new ObservableWindowBoundarySelector.OperatorWindowBoundaryOpenObserver(this);
        if (this.boundary.compareAndSet(null, paramDisposable)) {
          this.open.subscribe(paramDisposable);
        }
      }
    }
    
    void open(B paramB)
    {
      this.queue.offer(new ObservableWindowBoundarySelector.WindowOperation(null, paramB));
      if (enter()) {
        drainLoop();
      }
    }
  }
  
  static final class WindowOperation<T, B>
  {
    final B open;
    final UnicastSubject<T> w;
    
    WindowOperation(UnicastSubject<T> paramUnicastSubject, B paramB)
    {
      this.w = paramUnicastSubject;
      this.open = paramB;
    }
  }
}
