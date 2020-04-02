package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatMap<T, U>
  extends AbstractObservableWithUpstream<T, U>
{
  final int bufferSize;
  final ErrorMode delayErrors;
  final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
  
  public ObservableConcatMap(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, int paramInt, ErrorMode paramErrorMode)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.delayErrors = paramErrorMode;
    this.bufferSize = Math.max(8, paramInt);
  }
  
  public void subscribeActual(Observer<? super U> paramObserver)
  {
    if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, paramObserver, this.mapper)) {
      return;
    }
    if (this.delayErrors == ErrorMode.IMMEDIATE)
    {
      paramObserver = new SerializedObserver(paramObserver);
      this.source.subscribe(new SourceObserver(paramObserver, this.mapper, this.bufferSize));
    }
    else
    {
      ObservableSource localObservableSource = this.source;
      Function localFunction = this.mapper;
      int i = this.bufferSize;
      boolean bool;
      if (this.delayErrors == ErrorMode.END) {
        bool = true;
      } else {
        bool = false;
      }
      localObservableSource.subscribe(new ConcatMapDelayErrorObserver(paramObserver, localFunction, i, bool));
    }
  }
  
  static final class ConcatMapDelayErrorObserver<T, R>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -6951100001833242599L;
    volatile boolean active;
    final int bufferSize;
    volatile boolean cancelled;
    volatile boolean done;
    final Observer<? super R> downstream;
    final AtomicThrowable error;
    final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
    final DelayErrorInnerObserver<R> observer;
    SimpleQueue<T> queue;
    int sourceMode;
    final boolean tillTheEnd;
    Disposable upstream;
    
    ConcatMapDelayErrorObserver(Observer<? super R> paramObserver, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.bufferSize = paramInt;
      this.tillTheEnd = paramBoolean;
      this.error = new AtomicThrowable();
      this.observer = new DelayErrorInnerObserver(paramObserver, this);
    }
    
    public void dispose()
    {
      this.cancelled = true;
      this.upstream.dispose();
      this.observer.dispose();
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 82	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 50	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:downstream	Lio/reactivex/Observer;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 84	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 61	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   22: astore_3
      //   23: aload_0
      //   24: getfield 86	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:active	Z
      //   27: ifne +311 -> 338
      //   30: aload_0
      //   31: getfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   34: ifeq +10 -> 44
      //   37: aload_2
      //   38: invokeinterface 91 1 0
      //   43: return
      //   44: aload_0
      //   45: getfield 56	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:tillTheEnd	Z
      //   48: ifne +35 -> 83
      //   51: aload_3
      //   52: invokevirtual 95	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   55: checkcast 97	java/lang/Throwable
      //   58: ifnull +25 -> 83
      //   61: aload_2
      //   62: invokeinterface 91 1 0
      //   67: aload_0
      //   68: iconst_1
      //   69: putfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   72: aload_1
      //   73: aload_3
      //   74: invokevirtual 101	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   77: invokeinterface 105 2 0
      //   82: return
      //   83: aload_0
      //   84: getfield 107	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:done	Z
      //   87: istore 4
      //   89: aload_2
      //   90: invokeinterface 110 1 0
      //   95: astore 5
      //   97: aload 5
      //   99: ifnonnull +9 -> 108
      //   102: iconst_1
      //   103: istore 6
      //   105: goto +6 -> 111
      //   108: iconst_0
      //   109: istore 6
      //   111: iload 4
      //   113: ifeq +39 -> 152
      //   116: iload 6
      //   118: ifeq +34 -> 152
      //   121: aload_0
      //   122: iconst_1
      //   123: putfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   126: aload_3
      //   127: invokevirtual 101	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   130: astore_3
      //   131: aload_3
      //   132: ifnull +13 -> 145
      //   135: aload_1
      //   136: aload_3
      //   137: invokeinterface 105 2 0
      //   142: goto +9 -> 151
      //   145: aload_1
      //   146: invokeinterface 113 1 0
      //   151: return
      //   152: iload 6
      //   154: ifne +184 -> 338
      //   157: aload_0
      //   158: getfield 52	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:mapper	Lio/reactivex/functions/Function;
      //   161: aload 5
      //   163: invokeinterface 119 2 0
      //   168: ldc 121
      //   170: invokestatic 127	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   173: checkcast 129	io/reactivex/ObservableSource
      //   176: astore 5
      //   178: aload 5
      //   180: instanceof 131
      //   183: ifeq +55 -> 238
      //   186: aload 5
      //   188: checkcast 131	java/util/concurrent/Callable
      //   191: invokeinterface 134 1 0
      //   196: astore 5
      //   198: aload 5
      //   200: ifnull -177 -> 23
      //   203: aload_0
      //   204: getfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   207: ifne -184 -> 23
      //   210: aload_1
      //   211: aload 5
      //   213: invokeinterface 138 2 0
      //   218: goto -195 -> 23
      //   221: astore 5
      //   223: aload 5
      //   225: invokestatic 143	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   228: aload_3
      //   229: aload 5
      //   231: invokevirtual 147	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   234: pop
      //   235: goto -212 -> 23
      //   238: aload_0
      //   239: iconst_1
      //   240: putfield 86	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:active	Z
      //   243: aload 5
      //   245: aload_0
      //   246: getfield 66	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:observer	Lio/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver$DelayErrorInnerObserver;
      //   249: invokeinterface 151 2 0
      //   254: goto +84 -> 338
      //   257: astore 5
      //   259: aload 5
      //   261: invokestatic 143	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   264: aload_0
      //   265: iconst_1
      //   266: putfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   269: aload_0
      //   270: getfield 74	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   273: invokeinterface 76 1 0
      //   278: aload_2
      //   279: invokeinterface 91 1 0
      //   284: aload_3
      //   285: aload 5
      //   287: invokevirtual 147	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   290: pop
      //   291: aload_1
      //   292: aload_3
      //   293: invokevirtual 101	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   296: invokeinterface 105 2 0
      //   301: return
      //   302: astore_2
      //   303: aload_2
      //   304: invokestatic 143	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   307: aload_0
      //   308: iconst_1
      //   309: putfield 72	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:cancelled	Z
      //   312: aload_0
      //   313: getfield 74	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   316: invokeinterface 76 1 0
      //   321: aload_3
      //   322: aload_2
      //   323: invokevirtual 147	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   326: pop
      //   327: aload_1
      //   328: aload_3
      //   329: invokevirtual 101	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   332: invokeinterface 105 2 0
      //   337: return
      //   338: aload_0
      //   339: invokevirtual 154	io/reactivex/internal/operators/observable/ObservableConcatMap$ConcatMapDelayErrorObserver:decrementAndGet	()I
      //   342: ifne -319 -> 23
      //   345: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	346	0	this	ConcatMapDelayErrorObserver
      //   12	316	1	localObserver	Observer
      //   17	262	2	localSimpleQueue	SimpleQueue
      //   302	21	2	localThrowable1	Throwable
      //   22	307	3	localObject1	Object
      //   87	25	4	bool	boolean
      //   95	117	5	localObject2	Object
      //   221	23	5	localThrowable2	Throwable
      //   257	29	5	localThrowable3	Throwable
      //   103	50	6	i	int
      // Exception table:
      //   from	to	target	type
      //   186	198	221	finally
      //   157	178	257	finally
      //   89	97	302	finally
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
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
      if (this.sourceMode == 0) {
        this.queue.offer(paramT);
      }
      drain();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        if ((paramDisposable instanceof QueueDisposable))
        {
          paramDisposable = (QueueDisposable)paramDisposable;
          int i = paramDisposable.requestFusion(3);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = paramDisposable;
            this.done = true;
            this.downstream.onSubscribe(this);
            drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = paramDisposable;
            this.downstream.onSubscribe(this);
            return;
          }
        }
        this.queue = new SpscLinkedArrayQueue(this.bufferSize);
        this.downstream.onSubscribe(this);
      }
    }
    
    static final class DelayErrorInnerObserver<R>
      extends AtomicReference<Disposable>
      implements Observer<R>
    {
      private static final long serialVersionUID = 2620149119579502636L;
      final Observer<? super R> downstream;
      final ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> parent;
      
      DelayErrorInnerObserver(Observer<? super R> paramObserver, ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> paramConcatMapDelayErrorObserver)
      {
        this.downstream = paramObserver;
        this.parent = paramConcatMapDelayErrorObserver;
      }
      
      void dispose()
      {
        DisposableHelper.dispose(this);
      }
      
      public void onComplete()
      {
        ObservableConcatMap.ConcatMapDelayErrorObserver localConcatMapDelayErrorObserver = this.parent;
        localConcatMapDelayErrorObserver.active = false;
        localConcatMapDelayErrorObserver.drain();
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableConcatMap.ConcatMapDelayErrorObserver localConcatMapDelayErrorObserver = this.parent;
        if (localConcatMapDelayErrorObserver.error.addThrowable(paramThrowable))
        {
          if (!localConcatMapDelayErrorObserver.tillTheEnd) {
            localConcatMapDelayErrorObserver.upstream.dispose();
          }
          localConcatMapDelayErrorObserver.active = false;
          localConcatMapDelayErrorObserver.drain();
        }
        else
        {
          RxJavaPlugins.onError(paramThrowable);
        }
      }
      
      public void onNext(R paramR)
      {
        this.downstream.onNext(paramR);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.replace(this, paramDisposable);
      }
    }
  }
  
  static final class SourceObserver<T, U>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 8828587559905699186L;
    volatile boolean active;
    final int bufferSize;
    volatile boolean disposed;
    volatile boolean done;
    final Observer<? super U> downstream;
    int fusionMode;
    final InnerObserver<U> inner;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
    SimpleQueue<T> queue;
    Disposable upstream;
    
    SourceObserver(Observer<? super U> paramObserver, Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, int paramInt)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.bufferSize = paramInt;
      this.inner = new InnerObserver(paramObserver, this);
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.inner.dispose();
      this.upstream.dispose();
      if (getAndIncrement() == 0) {
        this.queue.clear();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 71	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 62	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:disposed	Z
      //   12: ifeq +13 -> 25
      //   15: aload_0
      //   16: getfield 73	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   19: invokeinterface 78 1 0
      //   24: return
      //   25: aload_0
      //   26: getfield 81	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:active	Z
      //   29: ifne +151 -> 180
      //   32: aload_0
      //   33: getfield 83	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:done	Z
      //   36: istore_1
      //   37: aload_0
      //   38: getfield 73	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   41: invokeinterface 87 1 0
      //   46: astore_2
      //   47: aload_2
      //   48: ifnonnull +8 -> 56
      //   51: iconst_1
      //   52: istore_3
      //   53: goto +5 -> 58
      //   56: iconst_0
      //   57: istore_3
      //   58: iload_1
      //   59: ifeq +22 -> 81
      //   62: iload_3
      //   63: ifeq +18 -> 81
      //   66: aload_0
      //   67: iconst_1
      //   68: putfield 62	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:disposed	Z
      //   71: aload_0
      //   72: getfield 47	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:downstream	Lio/reactivex/Observer;
      //   75: invokeinterface 90 1 0
      //   80: return
      //   81: iload_3
      //   82: ifne +98 -> 180
      //   85: aload_0
      //   86: getfield 49	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:mapper	Lio/reactivex/functions/Function;
      //   89: aload_2
      //   90: invokeinterface 96 2 0
      //   95: ldc 98
      //   97: invokestatic 104	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   100: checkcast 106	io/reactivex/ObservableSource
      //   103: astore_2
      //   104: aload_0
      //   105: iconst_1
      //   106: putfield 81	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:active	Z
      //   109: aload_2
      //   110: aload_0
      //   111: getfield 56	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:inner	Lio/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver$InnerObserver;
      //   114: invokeinterface 110 2 0
      //   119: goto +61 -> 180
      //   122: astore_2
      //   123: aload_2
      //   124: invokestatic 116	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   127: aload_0
      //   128: invokevirtual 117	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:dispose	()V
      //   131: aload_0
      //   132: getfield 73	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   135: invokeinterface 78 1 0
      //   140: aload_0
      //   141: getfield 47	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:downstream	Lio/reactivex/Observer;
      //   144: aload_2
      //   145: invokeinterface 120 2 0
      //   150: return
      //   151: astore_2
      //   152: aload_2
      //   153: invokestatic 116	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   156: aload_0
      //   157: invokevirtual 117	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:dispose	()V
      //   160: aload_0
      //   161: getfield 73	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   164: invokeinterface 78 1 0
      //   169: aload_0
      //   170: getfield 47	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:downstream	Lio/reactivex/Observer;
      //   173: aload_2
      //   174: invokeinterface 120 2 0
      //   179: return
      //   180: aload_0
      //   181: invokevirtual 123	io/reactivex/internal/operators/observable/ObservableConcatMap$SourceObserver:decrementAndGet	()I
      //   184: ifne -176 -> 8
      //   187: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	188	0	this	SourceObserver
      //   36	23	1	bool	boolean
      //   46	64	2	localObject	Object
      //   122	23	2	localThrowable1	Throwable
      //   151	23	2	localThrowable2	Throwable
      //   52	30	3	i	int
      // Exception table:
      //   from	to	target	type
      //   85	104	122	finally
      //   37	47	151	finally
    }
    
    void innerComplete()
    {
      this.active = false;
      drain();
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.fusionMode == 0) {
        this.queue.offer(paramT);
      }
      drain();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        if ((paramDisposable instanceof QueueDisposable))
        {
          paramDisposable = (QueueDisposable)paramDisposable;
          int i = paramDisposable.requestFusion(3);
          if (i == 1)
          {
            this.fusionMode = i;
            this.queue = paramDisposable;
            this.done = true;
            this.downstream.onSubscribe(this);
            drain();
            return;
          }
          if (i == 2)
          {
            this.fusionMode = i;
            this.queue = paramDisposable;
            this.downstream.onSubscribe(this);
            return;
          }
        }
        this.queue = new SpscLinkedArrayQueue(this.bufferSize);
        this.downstream.onSubscribe(this);
      }
    }
    
    static final class InnerObserver<U>
      extends AtomicReference<Disposable>
      implements Observer<U>
    {
      private static final long serialVersionUID = -7449079488798789337L;
      final Observer<? super U> downstream;
      final ObservableConcatMap.SourceObserver<?, ?> parent;
      
      InnerObserver(Observer<? super U> paramObserver, ObservableConcatMap.SourceObserver<?, ?> paramSourceObserver)
      {
        this.downstream = paramObserver;
        this.parent = paramSourceObserver;
      }
      
      void dispose()
      {
        DisposableHelper.dispose(this);
      }
      
      public void onComplete()
      {
        this.parent.innerComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.dispose();
        this.downstream.onError(paramThrowable);
      }
      
      public void onNext(U paramU)
      {
        this.downstream.onNext(paramU);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.replace(this, paramDisposable);
      }
    }
  }
}
