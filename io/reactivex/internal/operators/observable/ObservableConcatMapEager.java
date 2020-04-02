package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.InnerQueuedObserver;
import io.reactivex.internal.observers.InnerQueuedObserverSupport;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableConcatMapEager<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final ErrorMode errorMode;
  final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
  final int maxConcurrency;
  final int prefetch;
  
  public ObservableConcatMapEager(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, ErrorMode paramErrorMode, int paramInt1, int paramInt2)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.errorMode = paramErrorMode;
    this.maxConcurrency = paramInt1;
    this.prefetch = paramInt2;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    this.source.subscribe(new ConcatMapEagerMainObserver(paramObserver, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
  }
  
  static final class ConcatMapEagerMainObserver<T, R>
    extends AtomicInteger
    implements Observer<T>, Disposable, InnerQueuedObserverSupport<R>
  {
    private static final long serialVersionUID = 8080567949447303262L;
    int activeCount;
    volatile boolean cancelled;
    InnerQueuedObserver<R> current;
    volatile boolean done;
    final Observer<? super R> downstream;
    final AtomicThrowable error;
    final ErrorMode errorMode;
    final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
    final int maxConcurrency;
    final ArrayDeque<InnerQueuedObserver<R>> observers;
    final int prefetch;
    SimpleQueue<T> queue;
    int sourceMode;
    Disposable upstream;
    
    ConcatMapEagerMainObserver(Observer<? super R> paramObserver, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt1, int paramInt2, ErrorMode paramErrorMode)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.maxConcurrency = paramInt1;
      this.prefetch = paramInt2;
      this.errorMode = paramErrorMode;
      this.error = new AtomicThrowable();
      this.observers = new ArrayDeque();
    }
    
    public void dispose()
    {
      if (this.cancelled) {
        return;
      }
      this.cancelled = true;
      this.upstream.dispose();
      drainAndDispose();
    }
    
    void disposeAll()
    {
      InnerQueuedObserver localInnerQueuedObserver = this.current;
      if (localInnerQueuedObserver != null) {
        localInnerQueuedObserver.dispose();
      }
      for (;;)
      {
        localInnerQueuedObserver = (InnerQueuedObserver)this.observers.poll();
        if (localInnerQueuedObserver == null) {
          return;
        }
        localInnerQueuedObserver.dispose();
      }
    }
    
    /* Error */
    public void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 100	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 102	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 72	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:observers	Ljava/util/ArrayDeque;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 54	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:downstream	Lio/reactivex/Observer;
      //   22: astore_3
      //   23: aload_0
      //   24: getfield 62	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:errorMode	Lio/reactivex/internal/util/ErrorMode;
      //   27: astore 4
      //   29: iconst_1
      //   30: istore 5
      //   32: aload_0
      //   33: getfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   36: istore 6
      //   38: iload 6
      //   40: aload_0
      //   41: getfield 58	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:maxConcurrency	I
      //   44: if_icmpeq +189 -> 233
      //   47: aload_0
      //   48: getfield 78	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:cancelled	Z
      //   51: ifeq +14 -> 65
      //   54: aload_1
      //   55: invokeinterface 109 1 0
      //   60: aload_0
      //   61: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   64: return
      //   65: aload 4
      //   67: getstatic 116	io/reactivex/internal/util/ErrorMode:IMMEDIATE	Lio/reactivex/internal/util/ErrorMode;
      //   70: if_acmpne +40 -> 110
      //   73: aload_0
      //   74: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   77: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   80: checkcast 121	java/lang/Throwable
      //   83: ifnull +27 -> 110
      //   86: aload_1
      //   87: invokeinterface 109 1 0
      //   92: aload_0
      //   93: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   96: aload_3
      //   97: aload_0
      //   98: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   101: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   104: invokeinterface 129 2 0
      //   109: return
      //   110: aload_1
      //   111: invokeinterface 130 1 0
      //   116: astore 7
      //   118: aload 7
      //   120: ifnonnull +6 -> 126
      //   123: goto +110 -> 233
      //   126: aload_0
      //   127: getfield 56	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:mapper	Lio/reactivex/functions/Function;
      //   130: aload 7
      //   132: invokeinterface 136 2 0
      //   137: ldc -118
      //   139: invokestatic 144	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   142: checkcast 146	io/reactivex/ObservableSource
      //   145: astore 8
      //   147: new 90	io/reactivex/internal/observers/InnerQueuedObserver
      //   150: dup
      //   151: aload_0
      //   152: aload_0
      //   153: getfield 60	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:prefetch	I
      //   156: invokespecial 149	io/reactivex/internal/observers/InnerQueuedObserver:<init>	(Lio/reactivex/internal/observers/InnerQueuedObserverSupport;I)V
      //   159: astore 7
      //   161: aload_2
      //   162: aload 7
      //   164: invokevirtual 153	java/util/ArrayDeque:offer	(Ljava/lang/Object;)Z
      //   167: pop
      //   168: aload 8
      //   170: aload 7
      //   172: invokeinterface 157 2 0
      //   177: iinc 6 1
      //   180: goto -142 -> 38
      //   183: astore 7
      //   185: aload 7
      //   187: invokestatic 162	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   190: aload_0
      //   191: getfield 80	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   194: invokeinterface 82 1 0
      //   199: aload_1
      //   200: invokeinterface 109 1 0
      //   205: aload_0
      //   206: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   209: aload_0
      //   210: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   213: aload 7
      //   215: invokevirtual 166	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   218: pop
      //   219: aload_3
      //   220: aload_0
      //   221: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   224: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   227: invokeinterface 129 2 0
      //   232: return
      //   233: aload_0
      //   234: iload 6
      //   236: putfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   239: aload_0
      //   240: getfield 78	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:cancelled	Z
      //   243: ifeq +14 -> 257
      //   246: aload_1
      //   247: invokeinterface 109 1 0
      //   252: aload_0
      //   253: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   256: return
      //   257: aload 4
      //   259: getstatic 116	io/reactivex/internal/util/ErrorMode:IMMEDIATE	Lio/reactivex/internal/util/ErrorMode;
      //   262: if_acmpne +40 -> 302
      //   265: aload_0
      //   266: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   269: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   272: checkcast 121	java/lang/Throwable
      //   275: ifnull +27 -> 302
      //   278: aload_1
      //   279: invokeinterface 109 1 0
      //   284: aload_0
      //   285: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   288: aload_3
      //   289: aload_0
      //   290: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   293: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   296: invokeinterface 129 2 0
      //   301: return
      //   302: aload_0
      //   303: getfield 88	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:current	Lio/reactivex/internal/observers/InnerQueuedObserver;
      //   306: astore 8
      //   308: aload 8
      //   310: astore 7
      //   312: aload 8
      //   314: ifnonnull +144 -> 458
      //   317: aload 4
      //   319: getstatic 169	io/reactivex/internal/util/ErrorMode:BOUNDARY	Lio/reactivex/internal/util/ErrorMode;
      //   322: if_acmpne +40 -> 362
      //   325: aload_0
      //   326: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   329: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   332: checkcast 121	java/lang/Throwable
      //   335: ifnull +27 -> 362
      //   338: aload_1
      //   339: invokeinterface 109 1 0
      //   344: aload_0
      //   345: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   348: aload_3
      //   349: aload_0
      //   350: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   353: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   356: invokeinterface 129 2 0
      //   361: return
      //   362: aload_0
      //   363: getfield 171	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:done	Z
      //   366: istore 9
      //   368: aload_2
      //   369: invokevirtual 95	java/util/ArrayDeque:poll	()Ljava/lang/Object;
      //   372: checkcast 90	io/reactivex/internal/observers/InnerQueuedObserver
      //   375: astore 7
      //   377: aload 7
      //   379: ifnonnull +9 -> 388
      //   382: iconst_1
      //   383: istore 6
      //   385: goto +6 -> 391
      //   388: iconst_0
      //   389: istore 6
      //   391: iload 9
      //   393: ifeq +54 -> 447
      //   396: iload 6
      //   398: ifeq +49 -> 447
      //   401: aload_0
      //   402: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   405: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   408: checkcast 121	java/lang/Throwable
      //   411: ifnull +29 -> 440
      //   414: aload_1
      //   415: invokeinterface 109 1 0
      //   420: aload_0
      //   421: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   424: aload_3
      //   425: aload_0
      //   426: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   429: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   432: invokeinterface 129 2 0
      //   437: goto +9 -> 446
      //   440: aload_3
      //   441: invokeinterface 174 1 0
      //   446: return
      //   447: iload 6
      //   449: ifne +9 -> 458
      //   452: aload_0
      //   453: aload 7
      //   455: putfield 88	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:current	Lio/reactivex/internal/observers/InnerQueuedObserver;
      //   458: aload 7
      //   460: ifnull +185 -> 645
      //   463: aload 7
      //   465: invokevirtual 177	io/reactivex/internal/observers/InnerQueuedObserver:queue	()Lio/reactivex/internal/fuseable/SimpleQueue;
      //   468: astore 8
      //   470: aload_0
      //   471: getfield 78	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:cancelled	Z
      //   474: ifeq +14 -> 488
      //   477: aload_1
      //   478: invokeinterface 109 1 0
      //   483: aload_0
      //   484: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   487: return
      //   488: aload 7
      //   490: invokevirtual 181	io/reactivex/internal/observers/InnerQueuedObserver:isDone	()Z
      //   493: istore 9
      //   495: aload 4
      //   497: getstatic 116	io/reactivex/internal/util/ErrorMode:IMMEDIATE	Lio/reactivex/internal/util/ErrorMode;
      //   500: if_acmpne +40 -> 540
      //   503: aload_0
      //   504: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   507: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   510: checkcast 121	java/lang/Throwable
      //   513: ifnull +27 -> 540
      //   516: aload_1
      //   517: invokeinterface 109 1 0
      //   522: aload_0
      //   523: invokevirtual 111	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:disposeAll	()V
      //   526: aload_3
      //   527: aload_0
      //   528: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   531: invokevirtual 125	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   534: invokeinterface 129 2 0
      //   539: return
      //   540: aload 8
      //   542: invokeinterface 130 1 0
      //   547: astore 10
      //   549: aload 10
      //   551: ifnonnull +9 -> 560
      //   554: iconst_1
      //   555: istore 6
      //   557: goto +6 -> 563
      //   560: iconst_0
      //   561: istore 6
      //   563: iload 9
      //   565: ifeq +26 -> 591
      //   568: iload 6
      //   570: ifeq +21 -> 591
      //   573: aload_0
      //   574: aconst_null
      //   575: putfield 88	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:current	Lio/reactivex/internal/observers/InnerQueuedObserver;
      //   578: aload_0
      //   579: aload_0
      //   580: getfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   583: iconst_1
      //   584: isub
      //   585: putfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   588: goto -556 -> 32
      //   591: iload 6
      //   593: ifeq +6 -> 599
      //   596: goto +49 -> 645
      //   599: aload_3
      //   600: aload 10
      //   602: invokeinterface 185 2 0
      //   607: goto -137 -> 470
      //   610: astore 7
      //   612: aload 7
      //   614: invokestatic 162	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   617: aload_0
      //   618: getfield 67	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   621: aload 7
      //   623: invokevirtual 166	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   626: pop
      //   627: aload_0
      //   628: aconst_null
      //   629: putfield 88	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:current	Lio/reactivex/internal/observers/InnerQueuedObserver;
      //   632: aload_0
      //   633: aload_0
      //   634: getfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   637: iconst_1
      //   638: isub
      //   639: putfield 104	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:activeCount	I
      //   642: goto -610 -> 32
      //   645: aload_0
      //   646: iload 5
      //   648: ineg
      //   649: invokevirtual 189	io/reactivex/internal/operators/observable/ObservableConcatMapEager$ConcatMapEagerMainObserver:addAndGet	(I)I
      //   652: istore 6
      //   654: iload 6
      //   656: istore 5
      //   658: iload 6
      //   660: ifne -628 -> 32
      //   663: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	664	0	this	ConcatMapEagerMainObserver
      //   12	505	1	localSimpleQueue	SimpleQueue
      //   17	352	2	localArrayDeque	ArrayDeque
      //   22	578	3	localObserver	Observer
      //   27	469	4	localErrorMode	ErrorMode
      //   30	627	5	i	int
      //   36	623	6	j	int
      //   116	55	7	localObject1	Object
      //   183	31	7	localThrowable1	Throwable
      //   310	179	7	localObject2	Object
      //   610	12	7	localThrowable2	Throwable
      //   145	396	8	localObject3	Object
      //   366	198	9	bool	boolean
      //   547	54	10	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   110	118	183	finally
      //   126	147	183	finally
      //   540	549	610	finally
    }
    
    void drainAndDispose()
    {
      if (getAndIncrement() == 0) {
        do
        {
          this.queue.clear();
          disposeAll();
        } while (decrementAndGet() != 0);
      }
    }
    
    public void innerComplete(InnerQueuedObserver<R> paramInnerQueuedObserver)
    {
      paramInnerQueuedObserver.setDone();
      drain();
    }
    
    public void innerError(InnerQueuedObserver<R> paramInnerQueuedObserver, Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
      {
        if (this.errorMode == ErrorMode.IMMEDIATE) {
          this.upstream.dispose();
        }
        paramInnerQueuedObserver.setDone();
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerNext(InnerQueuedObserver<R> paramInnerQueuedObserver, R paramR)
    {
      paramInnerQueuedObserver.queue().offer(paramR);
      drain();
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
        this.queue = new SpscLinkedArrayQueue(this.prefetch);
        this.downstream.onSubscribe(this);
      }
    }
  }
}
