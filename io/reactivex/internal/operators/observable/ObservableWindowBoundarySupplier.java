package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySupplier<T, B>
  extends AbstractObservableWithUpstream<T, Observable<T>>
{
  final int capacityHint;
  final Callable<? extends ObservableSource<B>> other;
  
  public ObservableWindowBoundarySupplier(ObservableSource<T> paramObservableSource, Callable<? extends ObservableSource<B>> paramCallable, int paramInt)
  {
    super(paramObservableSource);
    this.other = paramCallable;
    this.capacityHint = paramInt;
  }
  
  public void subscribeActual(Observer<? super Observable<T>> paramObserver)
  {
    paramObserver = new WindowBoundaryMainObserver(paramObserver, this.capacityHint, this.other);
    this.source.subscribe(paramObserver);
  }
  
  static final class WindowBoundaryInnerObserver<T, B>
    extends DisposableObserver<B>
  {
    boolean done;
    final ObservableWindowBoundarySupplier.WindowBoundaryMainObserver<T, B> parent;
    
    WindowBoundaryInnerObserver(ObservableWindowBoundarySupplier.WindowBoundaryMainObserver<T, B> paramWindowBoundaryMainObserver)
    {
      this.parent = paramWindowBoundaryMainObserver;
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
  
  static final class WindowBoundaryMainObserver<T, B>
    extends AtomicInteger
    implements Observer<T>, Disposable, Runnable
  {
    static final ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<Object, Object> BOUNDARY_DISPOSED = new ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver(null);
    static final Object NEXT_WINDOW = new Object();
    private static final long serialVersionUID = 2233020065421370272L;
    final AtomicReference<ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<T, B>> boundaryObserver;
    final int capacityHint;
    volatile boolean done;
    final Observer<? super Observable<T>> downstream;
    final AtomicThrowable errors;
    final Callable<? extends ObservableSource<B>> other;
    final MpscLinkedQueue<Object> queue;
    final AtomicBoolean stopWindows;
    Disposable upstream;
    UnicastSubject<T> window;
    final AtomicInteger windows;
    
    WindowBoundaryMainObserver(Observer<? super Observable<T>> paramObserver, int paramInt, Callable<? extends ObservableSource<B>> paramCallable)
    {
      this.downstream = paramObserver;
      this.capacityHint = paramInt;
      this.boundaryObserver = new AtomicReference();
      this.windows = new AtomicInteger(1);
      this.queue = new MpscLinkedQueue();
      this.errors = new AtomicThrowable();
      this.stopWindows = new AtomicBoolean();
      this.other = paramCallable;
    }
    
    public void dispose()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        disposeBoundary();
        if (this.windows.decrementAndGet() == 0) {
          this.upstream.dispose();
        }
      }
    }
    
    void disposeBoundary()
    {
      Disposable localDisposable = (Disposable)this.boundaryObserver.getAndSet(BOUNDARY_DISPOSED);
      if ((localDisposable != null) && (localDisposable != BOUNDARY_DISPOSED)) {
        localDisposable.dispose();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 126	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 71	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:downstream	Lio/reactivex/Observer;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 88	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:queue	Lio/reactivex/internal/queue/MpscLinkedQueue;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 93	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   22: astore_3
      //   23: iconst_1
      //   24: istore 4
      //   26: aload_0
      //   27: getfield 83	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:windows	Ljava/util/concurrent/atomic/AtomicInteger;
      //   30: invokevirtual 129	java/util/concurrent/atomic/AtomicInteger:get	()I
      //   33: ifne +13 -> 46
      //   36: aload_2
      //   37: invokevirtual 132	io/reactivex/internal/queue/MpscLinkedQueue:clear	()V
      //   40: aload_0
      //   41: aconst_null
      //   42: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   45: return
      //   46: aload_0
      //   47: getfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   50: astore 5
      //   52: aload_0
      //   53: getfield 136	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:done	Z
      //   56: istore 6
      //   58: iload 6
      //   60: ifeq +43 -> 103
      //   63: aload_3
      //   64: invokevirtual 139	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   67: ifnull +36 -> 103
      //   70: aload_2
      //   71: invokevirtual 132	io/reactivex/internal/queue/MpscLinkedQueue:clear	()V
      //   74: aload_3
      //   75: invokevirtual 143	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   78: astore_2
      //   79: aload 5
      //   81: ifnull +14 -> 95
      //   84: aload_0
      //   85: aconst_null
      //   86: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   89: aload 5
      //   91: aload_2
      //   92: invokevirtual 149	io/reactivex/subjects/UnicastSubject:onError	(Ljava/lang/Throwable;)V
      //   95: aload_1
      //   96: aload_2
      //   97: invokeinterface 150 2 0
      //   102: return
      //   103: aload_2
      //   104: invokevirtual 153	io/reactivex/internal/queue/MpscLinkedQueue:poll	()Ljava/lang/Object;
      //   107: astore 7
      //   109: aload 7
      //   111: ifnonnull +9 -> 120
      //   114: iconst_1
      //   115: istore 8
      //   117: goto +6 -> 123
      //   120: iconst_0
      //   121: istore 8
      //   123: iload 6
      //   125: ifeq +65 -> 190
      //   128: iload 8
      //   130: ifeq +60 -> 190
      //   133: aload_3
      //   134: invokevirtual 143	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   137: astore_2
      //   138: aload_2
      //   139: ifnonnull +27 -> 166
      //   142: aload 5
      //   144: ifnull +13 -> 157
      //   147: aload_0
      //   148: aconst_null
      //   149: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   152: aload 5
      //   154: invokevirtual 156	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   157: aload_1
      //   158: invokeinterface 157 1 0
      //   163: goto +26 -> 189
      //   166: aload 5
      //   168: ifnull +14 -> 182
      //   171: aload_0
      //   172: aconst_null
      //   173: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   176: aload 5
      //   178: aload_2
      //   179: invokevirtual 149	io/reactivex/subjects/UnicastSubject:onError	(Ljava/lang/Throwable;)V
      //   182: aload_1
      //   183: aload_2
      //   184: invokeinterface 150 2 0
      //   189: return
      //   190: iload 8
      //   192: ifeq +22 -> 214
      //   195: aload_0
      //   196: iload 4
      //   198: ineg
      //   199: invokevirtual 161	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:addAndGet	(I)I
      //   202: istore 8
      //   204: iload 8
      //   206: istore 4
      //   208: iload 8
      //   210: ifne -184 -> 26
      //   213: return
      //   214: aload 7
      //   216: getstatic 66	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:NEXT_WINDOW	Ljava/lang/Object;
      //   219: if_acmpeq +13 -> 232
      //   222: aload 5
      //   224: aload 7
      //   226: invokevirtual 165	io/reactivex/subjects/UnicastSubject:onNext	(Ljava/lang/Object;)V
      //   229: goto -203 -> 26
      //   232: aload 5
      //   234: ifnull +13 -> 247
      //   237: aload_0
      //   238: aconst_null
      //   239: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   242: aload 5
      //   244: invokevirtual 156	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   247: aload_0
      //   248: getfield 98	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:stopWindows	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   251: invokevirtual 168	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   254: ifne -228 -> 26
      //   257: aload_0
      //   258: getfield 73	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:capacityHint	I
      //   261: aload_0
      //   262: invokestatic 172	io/reactivex/subjects/UnicastSubject:create	(ILjava/lang/Runnable;)Lio/reactivex/subjects/UnicastSubject;
      //   265: astore 9
      //   267: aload_0
      //   268: aload 9
      //   270: putfield 134	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:window	Lio/reactivex/subjects/UnicastSubject;
      //   273: aload_0
      //   274: getfield 83	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:windows	Ljava/util/concurrent/atomic/AtomicInteger;
      //   277: invokevirtual 173	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
      //   280: pop
      //   281: aload_0
      //   282: getfield 100	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:other	Ljava/util/concurrent/Callable;
      //   285: invokeinterface 178 1 0
      //   290: ldc -76
      //   292: invokestatic 186	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   295: checkcast 188	io/reactivex/ObservableSource
      //   298: astore 7
      //   300: new 54	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryInnerObserver
      //   303: dup
      //   304: aload_0
      //   305: invokespecial 58	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryInnerObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver;)V
      //   308: astore 5
      //   310: aload_0
      //   311: getfield 78	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:boundaryObserver	Ljava/util/concurrent/atomic/AtomicReference;
      //   314: aconst_null
      //   315: aload 5
      //   317: invokevirtual 191	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   320: ifeq -294 -> 26
      //   323: aload 7
      //   325: aload 5
      //   327: invokeinterface 195 2 0
      //   332: aload_1
      //   333: aload 9
      //   335: invokeinterface 196 2 0
      //   340: goto -314 -> 26
      //   343: astore 5
      //   345: aload 5
      //   347: invokestatic 201	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   350: aload_3
      //   351: aload 5
      //   353: invokevirtual 205	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   356: pop
      //   357: aload_0
      //   358: iconst_1
      //   359: putfield 136	io/reactivex/internal/operators/observable/ObservableWindowBoundarySupplier$WindowBoundaryMainObserver:done	Z
      //   362: goto -336 -> 26
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	365	0	this	WindowBoundaryMainObserver
      //   12	321	1	localObserver	Observer
      //   17	167	2	localObject1	Object
      //   22	329	3	localAtomicThrowable	AtomicThrowable
      //   24	183	4	i	int
      //   50	276	5	localObject2	Object
      //   343	9	5	localThrowable	Throwable
      //   56	68	6	bool	boolean
      //   107	217	7	localObject3	Object
      //   115	94	8	j	int
      //   265	69	9	localUnicastSubject	UnicastSubject
      // Exception table:
      //   from	to	target	type
      //   281	300	343	finally
    }
    
    void innerComplete()
    {
      this.upstream.dispose();
      this.done = true;
      drain();
    }
    
    void innerError(Throwable paramThrowable)
    {
      this.upstream.dispose();
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
    
    void innerNext(ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<T, B> paramWindowBoundaryInnerObserver)
    {
      this.boundaryObserver.compareAndSet(paramWindowBoundaryInnerObserver, null);
      this.queue.offer(NEXT_WINDOW);
      drain();
    }
    
    public boolean isDisposed()
    {
      return this.stopWindows.get();
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
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
        this.queue.offer(NEXT_WINDOW);
        drain();
      }
    }
    
    public void run()
    {
      if (this.windows.decrementAndGet() == 0) {
        this.upstream.dispose();
      }
    }
  }
}
