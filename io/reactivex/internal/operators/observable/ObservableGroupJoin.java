package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
  extends AbstractObservableWithUpstream<TLeft, R>
{
  final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
  final ObservableSource<? extends TRight> other;
  final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> resultSelector;
  final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
  
  public ObservableGroupJoin(ObservableSource<TLeft> paramObservableSource, ObservableSource<? extends TRight> paramObservableSource1, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> paramBiFunction)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
    this.leftEnd = paramFunction;
    this.rightEnd = paramFunction1;
    this.resultSelector = paramBiFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    GroupJoinDisposable localGroupJoinDisposable = new GroupJoinDisposable(paramObserver, this.leftEnd, this.rightEnd, this.resultSelector);
    paramObserver.onSubscribe(localGroupJoinDisposable);
    paramObserver = new LeftRightObserver(localGroupJoinDisposable, true);
    localGroupJoinDisposable.disposables.add(paramObserver);
    LeftRightObserver localLeftRightObserver = new LeftRightObserver(localGroupJoinDisposable, false);
    localGroupJoinDisposable.disposables.add(localLeftRightObserver);
    this.source.subscribe(paramObserver);
    this.other.subscribe(localLeftRightObserver);
  }
  
  static final class GroupJoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R>
    extends AtomicInteger
    implements Disposable, ObservableGroupJoin.JoinSupport
  {
    static final Integer LEFT_CLOSE = Integer.valueOf(3);
    static final Integer LEFT_VALUE = Integer.valueOf(1);
    static final Integer RIGHT_CLOSE = Integer.valueOf(4);
    static final Integer RIGHT_VALUE = Integer.valueOf(2);
    private static final long serialVersionUID = -6071216598687999801L;
    final AtomicInteger active;
    volatile boolean cancelled;
    final CompositeDisposable disposables;
    final Observer<? super R> downstream;
    final AtomicReference<Throwable> error;
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
    int leftIndex;
    final Map<Integer, UnicastSubject<TRight>> lefts;
    final SpscLinkedArrayQueue<Object> queue;
    final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> resultSelector;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
    int rightIndex;
    final Map<Integer, TRight> rights;
    
    GroupJoinDisposable(Observer<? super R> paramObserver, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> paramBiFunction)
    {
      this.downstream = paramObserver;
      this.disposables = new CompositeDisposable();
      this.queue = new SpscLinkedArrayQueue(Observable.bufferSize());
      this.lefts = new LinkedHashMap();
      this.rights = new LinkedHashMap();
      this.error = new AtomicReference();
      this.leftEnd = paramFunction;
      this.rightEnd = paramFunction1;
      this.resultSelector = paramBiFunction;
      this.active = new AtomicInteger(2);
    }
    
    void cancelAll()
    {
      this.disposables.dispose();
    }
    
    public void dispose()
    {
      if (this.cancelled) {
        return;
      }
      this.cancelled = true;
      cancelAll();
      if (getAndIncrement() == 0) {
        this.queue.clear();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 127	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 93	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 75	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:downstream	Lio/reactivex/Observer;
      //   17: astore_2
      //   18: iconst_1
      //   19: istore_3
      //   20: aload_0
      //   21: getfield 122	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:cancelled	Z
      //   24: ifeq +8 -> 32
      //   27: aload_1
      //   28: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   31: return
      //   32: aload_0
      //   33: getfield 105	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   36: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   39: checkcast 137	java/lang/Throwable
      //   42: ifnull +17 -> 59
      //   45: aload_1
      //   46: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   49: aload_0
      //   50: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:cancelAll	()V
      //   53: aload_0
      //   54: aload_2
      //   55: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   58: return
      //   59: aload_0
      //   60: getfield 114	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:active	Ljava/util/concurrent/atomic/AtomicInteger;
      //   63: invokevirtual 143	java/util/concurrent/atomic/AtomicInteger:get	()I
      //   66: ifne +9 -> 75
      //   69: iconst_1
      //   70: istore 4
      //   72: goto +6 -> 78
      //   75: iconst_0
      //   76: istore 4
      //   78: aload_1
      //   79: invokevirtual 146	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   82: checkcast 56	java/lang/Integer
      //   85: astore 5
      //   87: aload 5
      //   89: ifnonnull +9 -> 98
      //   92: iconst_1
      //   93: istore 6
      //   95: goto +6 -> 101
      //   98: iconst_0
      //   99: istore 6
      //   101: iload 4
      //   103: ifeq +79 -> 182
      //   106: iload 6
      //   108: ifeq +74 -> 182
      //   111: aload_0
      //   112: getfield 98	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:lefts	Ljava/util/Map;
      //   115: invokeinterface 152 1 0
      //   120: invokeinterface 158 1 0
      //   125: astore_1
      //   126: aload_1
      //   127: invokeinterface 164 1 0
      //   132: ifeq +18 -> 150
      //   135: aload_1
      //   136: invokeinterface 167 1 0
      //   141: checkcast 169	io/reactivex/subjects/UnicastSubject
      //   144: invokevirtual 172	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   147: goto -21 -> 126
      //   150: aload_0
      //   151: getfield 98	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:lefts	Ljava/util/Map;
      //   154: invokeinterface 173 1 0
      //   159: aload_0
      //   160: getfield 100	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rights	Ljava/util/Map;
      //   163: invokeinterface 173 1 0
      //   168: aload_0
      //   169: getfield 80	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   172: invokevirtual 120	io/reactivex/disposables/CompositeDisposable:dispose	()V
      //   175: aload_2
      //   176: invokeinterface 176 1 0
      //   181: return
      //   182: iload 6
      //   184: ifeq +20 -> 204
      //   187: aload_0
      //   188: iload_3
      //   189: ineg
      //   190: invokevirtual 180	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:addAndGet	(I)I
      //   193: istore 4
      //   195: iload 4
      //   197: istore_3
      //   198: iload 4
      //   200: ifne -180 -> 20
      //   203: return
      //   204: aload_1
      //   205: invokevirtual 146	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   208: astore 7
      //   210: aload 5
      //   212: getstatic 62	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:LEFT_VALUE	Ljava/lang/Integer;
      //   215: if_acmpne +210 -> 425
      //   218: invokestatic 184	io/reactivex/subjects/UnicastSubject:create	()Lio/reactivex/subjects/UnicastSubject;
      //   221: astore 5
      //   223: aload_0
      //   224: getfield 186	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:leftIndex	I
      //   227: istore 4
      //   229: aload_0
      //   230: iload 4
      //   232: iconst_1
      //   233: iadd
      //   234: putfield 186	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:leftIndex	I
      //   237: aload_0
      //   238: getfield 98	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:lefts	Ljava/util/Map;
      //   241: iload 4
      //   243: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   246: aload 5
      //   248: invokeinterface 190 3 0
      //   253: pop
      //   254: aload_0
      //   255: getfield 107	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:leftEnd	Lio/reactivex/functions/Function;
      //   258: aload 7
      //   260: invokeinterface 196 2 0
      //   265: ldc -58
      //   267: invokestatic 204	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   270: checkcast 206	io/reactivex/ObservableSource
      //   273: astore 8
      //   275: new 208	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   278: dup
      //   279: aload_0
      //   280: iconst_1
      //   281: iload 4
      //   283: invokespecial 211	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableGroupJoin$JoinSupport;ZI)V
      //   286: astore 9
      //   288: aload_0
      //   289: getfield 80	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   292: aload 9
      //   294: invokevirtual 215	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   297: pop
      //   298: aload 8
      //   300: aload 9
      //   302: invokeinterface 218 2 0
      //   307: aload_0
      //   308: getfield 105	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   311: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   314: checkcast 137	java/lang/Throwable
      //   317: ifnull +17 -> 334
      //   320: aload_1
      //   321: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   324: aload_0
      //   325: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:cancelAll	()V
      //   328: aload_0
      //   329: aload_2
      //   330: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   333: return
      //   334: aload_0
      //   335: getfield 111	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:resultSelector	Lio/reactivex/functions/BiFunction;
      //   338: aload 7
      //   340: aload 5
      //   342: invokeinterface 222 3 0
      //   347: ldc -32
      //   349: invokestatic 204	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   352: astore 7
      //   354: aload_2
      //   355: aload 7
      //   357: invokeinterface 228 2 0
      //   362: aload_0
      //   363: getfield 100	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rights	Ljava/util/Map;
      //   366: invokeinterface 152 1 0
      //   371: invokeinterface 158 1 0
      //   376: astore 7
      //   378: aload 7
      //   380: invokeinterface 164 1 0
      //   385: ifeq -365 -> 20
      //   388: aload 5
      //   390: aload 7
      //   392: invokeinterface 167 1 0
      //   397: invokevirtual 229	io/reactivex/subjects/UnicastSubject:onNext	(Ljava/lang/Object;)V
      //   400: goto -22 -> 378
      //   403: astore 7
      //   405: aload_0
      //   406: aload 7
      //   408: aload_2
      //   409: aload_1
      //   410: invokevirtual 233	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   413: return
      //   414: astore 7
      //   416: aload_0
      //   417: aload 7
      //   419: aload_2
      //   420: aload_1
      //   421: invokevirtual 233	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   424: return
      //   425: aload 5
      //   427: getstatic 64	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:RIGHT_VALUE	Ljava/lang/Integer;
      //   430: if_acmpne +169 -> 599
      //   433: aload_0
      //   434: getfield 235	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rightIndex	I
      //   437: istore 4
      //   439: aload_0
      //   440: iload 4
      //   442: iconst_1
      //   443: iadd
      //   444: putfield 235	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rightIndex	I
      //   447: aload_0
      //   448: getfield 100	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rights	Ljava/util/Map;
      //   451: iload 4
      //   453: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   456: aload 7
      //   458: invokeinterface 190 3 0
      //   463: pop
      //   464: aload_0
      //   465: getfield 109	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rightEnd	Lio/reactivex/functions/Function;
      //   468: aload 7
      //   470: invokeinterface 196 2 0
      //   475: ldc -19
      //   477: invokestatic 204	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   480: checkcast 206	io/reactivex/ObservableSource
      //   483: astore 5
      //   485: new 208	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   488: dup
      //   489: aload_0
      //   490: iconst_0
      //   491: iload 4
      //   493: invokespecial 211	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableGroupJoin$JoinSupport;ZI)V
      //   496: astore 9
      //   498: aload_0
      //   499: getfield 80	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   502: aload 9
      //   504: invokevirtual 215	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   507: pop
      //   508: aload 5
      //   510: aload 9
      //   512: invokeinterface 218 2 0
      //   517: aload_0
      //   518: getfield 105	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   521: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   524: checkcast 137	java/lang/Throwable
      //   527: ifnull +17 -> 544
      //   530: aload_1
      //   531: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   534: aload_0
      //   535: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:cancelAll	()V
      //   538: aload_0
      //   539: aload_2
      //   540: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   543: return
      //   544: aload_0
      //   545: getfield 98	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:lefts	Ljava/util/Map;
      //   548: invokeinterface 152 1 0
      //   553: invokeinterface 158 1 0
      //   558: astore 5
      //   560: aload 5
      //   562: invokeinterface 164 1 0
      //   567: ifeq -547 -> 20
      //   570: aload 5
      //   572: invokeinterface 167 1 0
      //   577: checkcast 169	io/reactivex/subjects/UnicastSubject
      //   580: aload 7
      //   582: invokevirtual 229	io/reactivex/subjects/UnicastSubject:onNext	(Ljava/lang/Object;)V
      //   585: goto -25 -> 560
      //   588: astore 7
      //   590: aload_0
      //   591: aload 7
      //   593: aload_2
      //   594: aload_1
      //   595: invokevirtual 233	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   598: return
      //   599: aload 5
      //   601: getstatic 66	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:LEFT_CLOSE	Ljava/lang/Integer;
      //   604: if_acmpne +55 -> 659
      //   607: aload 7
      //   609: checkcast 208	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   612: astore 7
      //   614: aload_0
      //   615: getfield 98	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:lefts	Ljava/util/Map;
      //   618: aload 7
      //   620: getfield 240	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:index	I
      //   623: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   626: invokeinterface 243 2 0
      //   631: checkcast 169	io/reactivex/subjects/UnicastSubject
      //   634: astore 5
      //   636: aload_0
      //   637: getfield 80	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   640: aload 7
      //   642: invokevirtual 245	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   645: pop
      //   646: aload 5
      //   648: ifnull -628 -> 20
      //   651: aload 5
      //   653: invokevirtual 172	io/reactivex/subjects/UnicastSubject:onComplete	()V
      //   656: goto -636 -> 20
      //   659: aload 5
      //   661: getstatic 68	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:RIGHT_CLOSE	Ljava/lang/Integer;
      //   664: if_acmpne -644 -> 20
      //   667: aload 7
      //   669: checkcast 208	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   672: astore 7
      //   674: aload_0
      //   675: getfield 100	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:rights	Ljava/util/Map;
      //   678: aload 7
      //   680: getfield 240	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:index	I
      //   683: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   686: invokeinterface 243 2 0
      //   691: pop
      //   692: aload_0
      //   693: getfield 80	io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   696: aload 7
      //   698: invokevirtual 245	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   701: pop
      //   702: goto -682 -> 20
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	705	0	this	GroupJoinDisposable
      //   12	583	1	localObject1	Object
      //   17	577	2	localObserver	Observer
      //   19	179	3	i	int
      //   70	422	4	j	int
      //   85	575	5	localObject2	Object
      //   93	90	6	k	int
      //   208	183	7	localObject3	Object
      //   403	4	7	localThrowable1	Throwable
      //   414	167	7	localThrowable2	Throwable
      //   588	20	7	localThrowable3	Throwable
      //   612	85	7	localLeftRightEndObserver1	ObservableGroupJoin.LeftRightEndObserver
      //   273	26	8	localObservableSource	ObservableSource
      //   286	225	9	localLeftRightEndObserver2	ObservableGroupJoin.LeftRightEndObserver
      // Exception table:
      //   from	to	target	type
      //   334	354	403	finally
      //   254	275	414	finally
      //   464	485	588	finally
    }
    
    void errorAll(Observer<?> paramObserver)
    {
      Throwable localThrowable = ExceptionHelper.terminate(this.error);
      Iterator localIterator = this.lefts.values().iterator();
      while (localIterator.hasNext()) {
        ((UnicastSubject)localIterator.next()).onError(localThrowable);
      }
      this.lefts.clear();
      this.rights.clear();
      paramObserver.onError(localThrowable);
    }
    
    void fail(Throwable paramThrowable, Observer<?> paramObserver, SpscLinkedArrayQueue<?> paramSpscLinkedArrayQueue)
    {
      Exceptions.throwIfFatal(paramThrowable);
      ExceptionHelper.addThrowable(this.error, paramThrowable);
      paramSpscLinkedArrayQueue.clear();
      cancelAll();
      errorAll(paramObserver);
    }
    
    public void innerClose(boolean paramBoolean, ObservableGroupJoin.LeftRightEndObserver paramLeftRightEndObserver)
    {
      try
      {
        SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
        Integer localInteger;
        if (paramBoolean) {
          localInteger = LEFT_CLOSE;
        } else {
          localInteger = RIGHT_CLOSE;
        }
        localSpscLinkedArrayQueue.offer(localInteger, paramLeftRightEndObserver);
        drain();
        return;
      }
      finally {}
    }
    
    public void innerCloseError(Throwable paramThrowable)
    {
      if (ExceptionHelper.addThrowable(this.error, paramThrowable)) {
        drain();
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerComplete(ObservableGroupJoin.LeftRightObserver paramLeftRightObserver)
    {
      this.disposables.delete(paramLeftRightObserver);
      this.active.decrementAndGet();
      drain();
    }
    
    public void innerError(Throwable paramThrowable)
    {
      if (ExceptionHelper.addThrowable(this.error, paramThrowable))
      {
        this.active.decrementAndGet();
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerValue(boolean paramBoolean, Object paramObject)
    {
      try
      {
        SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
        Integer localInteger;
        if (paramBoolean) {
          localInteger = LEFT_VALUE;
        } else {
          localInteger = RIGHT_VALUE;
        }
        localSpscLinkedArrayQueue.offer(localInteger, paramObject);
        drain();
        return;
      }
      finally {}
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
  }
  
  static abstract interface JoinSupport
  {
    public abstract void innerClose(boolean paramBoolean, ObservableGroupJoin.LeftRightEndObserver paramLeftRightEndObserver);
    
    public abstract void innerCloseError(Throwable paramThrowable);
    
    public abstract void innerComplete(ObservableGroupJoin.LeftRightObserver paramLeftRightObserver);
    
    public abstract void innerError(Throwable paramThrowable);
    
    public abstract void innerValue(boolean paramBoolean, Object paramObject);
  }
  
  static final class LeftRightEndObserver
    extends AtomicReference<Disposable>
    implements Observer<Object>, Disposable
  {
    private static final long serialVersionUID = 1883890389173668373L;
    final int index;
    final boolean isLeft;
    final ObservableGroupJoin.JoinSupport parent;
    
    LeftRightEndObserver(ObservableGroupJoin.JoinSupport paramJoinSupport, boolean paramBoolean, int paramInt)
    {
      this.parent = paramJoinSupport;
      this.isLeft = paramBoolean;
      this.index = paramInt;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      this.parent.innerClose(this.isLeft, this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerCloseError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      if (DisposableHelper.dispose(this)) {
        this.parent.innerClose(this.isLeft, this);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
  
  static final class LeftRightObserver
    extends AtomicReference<Disposable>
    implements Observer<Object>, Disposable
  {
    private static final long serialVersionUID = 1883890389173668373L;
    final boolean isLeft;
    final ObservableGroupJoin.JoinSupport parent;
    
    LeftRightObserver(ObservableGroupJoin.JoinSupport paramJoinSupport, boolean paramBoolean)
    {
      this.parent = paramJoinSupport;
      this.isLeft = paramBoolean;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      this.parent.innerComplete(this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.parent.innerValue(this.isLeft, paramObject);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
