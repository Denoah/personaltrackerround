package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSwitchMap<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final int bufferSize;
  final boolean delayErrors;
  final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
  
  public ObservableSwitchMap(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.bufferSize = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super R> paramObserver)
  {
    if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, paramObserver, this.mapper)) {
      return;
    }
    this.source.subscribe(new SwitchMapObserver(paramObserver, this.mapper, this.bufferSize, this.delayErrors));
  }
  
  static final class SwitchMapInnerObserver<T, R>
    extends AtomicReference<Disposable>
    implements Observer<R>
  {
    private static final long serialVersionUID = 3837284832786408377L;
    final int bufferSize;
    volatile boolean done;
    final long index;
    final ObservableSwitchMap.SwitchMapObserver<T, R> parent;
    volatile SimpleQueue<R> queue;
    
    SwitchMapInnerObserver(ObservableSwitchMap.SwitchMapObserver<T, R> paramSwitchMapObserver, long paramLong, int paramInt)
    {
      this.parent = paramSwitchMapObserver;
      this.index = paramLong;
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      if (this.index == this.parent.unique)
      {
        this.done = true;
        this.parent.drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(this, paramThrowable);
    }
    
    public void onNext(R paramR)
    {
      if (this.index == this.parent.unique)
      {
        if (paramR != null) {
          this.queue.offer(paramR);
        }
        this.parent.drain();
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable))
      {
        if ((paramDisposable instanceof QueueDisposable))
        {
          paramDisposable = (QueueDisposable)paramDisposable;
          int i = paramDisposable.requestFusion(7);
          if (i == 1)
          {
            this.queue = paramDisposable;
            this.done = true;
            this.parent.drain();
            return;
          }
          if (i == 2)
          {
            this.queue = paramDisposable;
            return;
          }
        }
        this.queue = new SpscLinkedArrayQueue(this.bufferSize);
      }
    }
  }
  
  static final class SwitchMapObserver<T, R>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    static final ObservableSwitchMap.SwitchMapInnerObserver<Object, Object> CANCELLED;
    private static final long serialVersionUID = -3491074160481096299L;
    final AtomicReference<ObservableSwitchMap.SwitchMapInnerObserver<T, R>> active = new AtomicReference();
    final int bufferSize;
    volatile boolean cancelled;
    final boolean delayErrors;
    volatile boolean done;
    final Observer<? super R> downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
    volatile long unique;
    Disposable upstream;
    
    static
    {
      ObservableSwitchMap.SwitchMapInnerObserver localSwitchMapInnerObserver = new ObservableSwitchMap.SwitchMapInnerObserver(null, -1L, 1);
      CANCELLED = localSwitchMapInnerObserver;
      localSwitchMapInnerObserver.cancel();
    }
    
    SwitchMapObserver(Observer<? super R> paramObserver, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.bufferSize = paramInt;
      this.delayErrors = paramBoolean;
      this.errors = new AtomicThrowable();
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.dispose();
        disposeInner();
      }
    }
    
    void disposeInner()
    {
      ObservableSwitchMap.SwitchMapInnerObserver localSwitchMapInnerObserver1 = (ObservableSwitchMap.SwitchMapInnerObserver)this.active.get();
      ObservableSwitchMap.SwitchMapInnerObserver localSwitchMapInnerObserver2 = CANCELLED;
      if (localSwitchMapInnerObserver1 != localSwitchMapInnerObserver2)
      {
        localSwitchMapInnerObserver2 = (ObservableSwitchMap.SwitchMapInnerObserver)this.active.getAndSet(localSwitchMapInnerObserver2);
        if ((localSwitchMapInnerObserver2 != CANCELLED) && (localSwitchMapInnerObserver2 != null)) {
          localSwitchMapInnerObserver2.cancel();
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 101	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 65	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:downstream	Lio/reactivex/Observer;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 63	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 71	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:delayErrors	Z
      //   22: istore_3
      //   23: iconst_1
      //   24: istore 4
      //   26: aload_0
      //   27: getfield 81	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:cancelled	Z
      //   30: ifeq +4 -> 34
      //   33: return
      //   34: aload_0
      //   35: getfield 103	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:done	Z
      //   38: ifeq +102 -> 140
      //   41: aload_2
      //   42: invokevirtual 92	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   45: ifnonnull +9 -> 54
      //   48: iconst_1
      //   49: istore 5
      //   51: goto +6 -> 57
      //   54: iconst_0
      //   55: istore 5
      //   57: iload_3
      //   58: ifeq +43 -> 101
      //   61: iload 5
      //   63: ifeq +77 -> 140
      //   66: aload_0
      //   67: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   70: invokevirtual 104	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   73: checkcast 106	java/lang/Throwable
      //   76: astore 6
      //   78: aload 6
      //   80: ifnull +14 -> 94
      //   83: aload_1
      //   84: aload 6
      //   86: invokeinterface 110 2 0
      //   91: goto +9 -> 100
      //   94: aload_1
      //   95: invokeinterface 113 1 0
      //   100: return
      //   101: aload_0
      //   102: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   105: invokevirtual 104	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   108: checkcast 106	java/lang/Throwable
      //   111: ifnull +17 -> 128
      //   114: aload_1
      //   115: aload_0
      //   116: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   119: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   122: invokeinterface 110 2 0
      //   127: return
      //   128: iload 5
      //   130: ifeq +10 -> 140
      //   133: aload_1
      //   134: invokeinterface 113 1 0
      //   139: return
      //   140: aload_2
      //   141: invokevirtual 92	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   144: checkcast 43	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapInnerObserver
      //   147: astore 7
      //   149: aload 7
      //   151: ifnull +291 -> 442
      //   154: aload 7
      //   156: getfield 121	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapInnerObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   159: astore 8
      //   161: aload 8
      //   163: ifnull +279 -> 442
      //   166: aload 7
      //   168: getfield 122	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapInnerObserver:done	Z
      //   171: ifeq +75 -> 246
      //   174: aload 8
      //   176: invokeinterface 128 1 0
      //   181: istore 9
      //   183: iload_3
      //   184: ifeq +19 -> 203
      //   187: iload 9
      //   189: ifeq +57 -> 246
      //   192: aload_2
      //   193: aload 7
      //   195: aconst_null
      //   196: invokevirtual 132	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   199: pop
      //   200: goto -174 -> 26
      //   203: aload_0
      //   204: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   207: invokevirtual 104	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   210: checkcast 106	java/lang/Throwable
      //   213: ifnull +17 -> 230
      //   216: aload_1
      //   217: aload_0
      //   218: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   221: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   224: invokeinterface 110 2 0
      //   229: return
      //   230: iload 9
      //   232: ifeq +14 -> 246
      //   235: aload_2
      //   236: aload 7
      //   238: aconst_null
      //   239: invokevirtual 132	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   242: pop
      //   243: goto -217 -> 26
      //   246: iconst_0
      //   247: istore 5
      //   249: aload_0
      //   250: getfield 81	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:cancelled	Z
      //   253: ifeq +4 -> 257
      //   256: return
      //   257: aload 7
      //   259: aload_2
      //   260: invokevirtual 92	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   263: if_acmpeq +9 -> 272
      //   266: iconst_1
      //   267: istore 5
      //   269: goto +154 -> 423
      //   272: iload_3
      //   273: ifne +30 -> 303
      //   276: aload_0
      //   277: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   280: invokevirtual 104	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   283: checkcast 106	java/lang/Throwable
      //   286: ifnull +17 -> 303
      //   289: aload_1
      //   290: aload_0
      //   291: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   294: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   297: invokeinterface 110 2 0
      //   302: return
      //   303: aload 7
      //   305: getfield 122	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapInnerObserver:done	Z
      //   308: istore 9
      //   310: aload 8
      //   312: invokeinterface 135 1 0
      //   317: astore 6
      //   319: goto +64 -> 383
      //   322: astore 6
      //   324: aload 6
      //   326: invokestatic 140	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   329: aload_0
      //   330: getfield 76	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   333: aload 6
      //   335: invokevirtual 144	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   338: pop
      //   339: aload_2
      //   340: aload 7
      //   342: aconst_null
      //   343: invokevirtual 132	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   346: pop
      //   347: iload_3
      //   348: ifne +24 -> 372
      //   351: aload_0
      //   352: invokevirtual 88	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:disposeInner	()V
      //   355: aload_0
      //   356: getfield 83	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   359: invokeinterface 85 1 0
      //   364: aload_0
      //   365: iconst_1
      //   366: putfield 103	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:done	Z
      //   369: goto +8 -> 377
      //   372: aload 7
      //   374: invokevirtual 54	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapInnerObserver:cancel	()V
      //   377: iconst_1
      //   378: istore 5
      //   380: aconst_null
      //   381: astore 6
      //   383: aload 6
      //   385: ifnonnull +9 -> 394
      //   388: iconst_1
      //   389: istore 10
      //   391: goto +6 -> 397
      //   394: iconst_0
      //   395: istore 10
      //   397: iload 9
      //   399: ifeq +19 -> 418
      //   402: iload 10
      //   404: ifeq +14 -> 418
      //   407: aload_2
      //   408: aload 7
      //   410: aconst_null
      //   411: invokevirtual 132	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   414: pop
      //   415: goto -149 -> 266
      //   418: iload 10
      //   420: ifeq +11 -> 431
      //   423: iload 5
      //   425: ifeq +17 -> 442
      //   428: goto -402 -> 26
      //   431: aload_1
      //   432: aload 6
      //   434: invokeinterface 148 2 0
      //   439: goto -190 -> 249
      //   442: aload_0
      //   443: iload 4
      //   445: ineg
      //   446: invokevirtual 152	io/reactivex/internal/operators/observable/ObservableSwitchMap$SwitchMapObserver:addAndGet	(I)I
      //   449: istore 5
      //   451: iload 5
      //   453: istore 4
      //   455: iload 5
      //   457: ifne -431 -> 26
      //   460: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	461	0	this	SwitchMapObserver
      //   12	420	1	localObserver	Observer
      //   17	391	2	localAtomicReference	AtomicReference
      //   22	326	3	bool1	boolean
      //   24	430	4	i	int
      //   49	407	5	j	int
      //   76	242	6	localObject1	Object
      //   322	12	6	localThrowable	Throwable
      //   381	52	6	localObject2	Object
      //   147	262	7	localSwitchMapInnerObserver	ObservableSwitchMap.SwitchMapInnerObserver
      //   159	152	8	localSimpleQueue	SimpleQueue
      //   181	217	9	bool2	boolean
      //   389	30	10	k	int
      // Exception table:
      //   from	to	target	type
      //   310	319	322	finally
    }
    
    void innerError(ObservableSwitchMap.SwitchMapInnerObserver<T, R> paramSwitchMapInnerObserver, Throwable paramThrowable)
    {
      if ((paramSwitchMapInnerObserver.index == this.unique) && (this.errors.addThrowable(paramThrowable)))
      {
        if (!this.delayErrors) {
          this.upstream.dispose();
        }
        paramSwitchMapInnerObserver.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if ((!this.done) && (this.errors.addThrowable(paramThrowable)))
      {
        if (!this.delayErrors) {
          disposeInner();
        }
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
      long l = this.unique + 1L;
      this.unique = l;
      ObservableSwitchMap.SwitchMapInnerObserver localSwitchMapInnerObserver1 = (ObservableSwitchMap.SwitchMapInnerObserver)this.active.get();
      if (localSwitchMapInnerObserver1 != null) {
        localSwitchMapInnerObserver1.cancel();
      }
      try
      {
        paramT = (ObservableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The ObservableSource returned is null");
        ObservableSwitchMap.SwitchMapInnerObserver localSwitchMapInnerObserver2 = new ObservableSwitchMap.SwitchMapInnerObserver(this, l, this.bufferSize);
        do
        {
          localSwitchMapInnerObserver1 = (ObservableSwitchMap.SwitchMapInnerObserver)this.active.get();
          if (localSwitchMapInnerObserver1 == CANCELLED) {
            break;
          }
        } while (!this.active.compareAndSet(localSwitchMapInnerObserver1, localSwitchMapInnerObserver2));
        paramT.subscribe(localSwitchMapInnerObserver2);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
