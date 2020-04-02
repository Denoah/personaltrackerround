package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
  extends AbstractObservableWithUpstream<TLeft, R>
{
  final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
  final ObservableSource<? extends TRight> other;
  final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
  final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
  
  public ObservableJoin(ObservableSource<TLeft> paramObservableSource, ObservableSource<? extends TRight> paramObservableSource1, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super TRight, ? extends R> paramBiFunction)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
    this.leftEnd = paramFunction;
    this.rightEnd = paramFunction1;
    this.resultSelector = paramBiFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    JoinDisposable localJoinDisposable = new JoinDisposable(paramObserver, this.leftEnd, this.rightEnd, this.resultSelector);
    paramObserver.onSubscribe(localJoinDisposable);
    ObservableGroupJoin.LeftRightObserver localLeftRightObserver = new ObservableGroupJoin.LeftRightObserver(localJoinDisposable, true);
    localJoinDisposable.disposables.add(localLeftRightObserver);
    paramObserver = new ObservableGroupJoin.LeftRightObserver(localJoinDisposable, false);
    localJoinDisposable.disposables.add(paramObserver);
    this.source.subscribe(localLeftRightObserver);
    this.other.subscribe(paramObserver);
  }
  
  static final class JoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R>
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
    final Map<Integer, TLeft> lefts;
    final SpscLinkedArrayQueue<Object> queue;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
    int rightIndex;
    final Map<Integer, TRight> rights;
    
    JoinDisposable(Observer<? super R> paramObserver, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super TRight, ? extends R> paramBiFunction)
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
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelAll();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 127	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 93	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 75	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:downstream	Lio/reactivex/Observer;
      //   17: astore_2
      //   18: iconst_1
      //   19: istore_3
      //   20: aload_0
      //   21: getfield 122	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:cancelled	Z
      //   24: ifeq +8 -> 32
      //   27: aload_1
      //   28: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   31: return
      //   32: aload_0
      //   33: getfield 105	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   36: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   39: checkcast 137	java/lang/Throwable
      //   42: ifnull +17 -> 59
      //   45: aload_1
      //   46: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   49: aload_0
      //   50: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:cancelAll	()V
      //   53: aload_0
      //   54: aload_2
      //   55: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   58: return
      //   59: aload_0
      //   60: getfield 114	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:active	Ljava/util/concurrent/atomic/AtomicInteger;
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
      //   103: ifeq +40 -> 143
      //   106: iload 6
      //   108: ifeq +35 -> 143
      //   111: aload_0
      //   112: getfield 98	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:lefts	Ljava/util/Map;
      //   115: invokeinterface 149 1 0
      //   120: aload_0
      //   121: getfield 100	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rights	Ljava/util/Map;
      //   124: invokeinterface 149 1 0
      //   129: aload_0
      //   130: getfield 80	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   133: invokevirtual 120	io/reactivex/disposables/CompositeDisposable:dispose	()V
      //   136: aload_2
      //   137: invokeinterface 154 1 0
      //   142: return
      //   143: iload 6
      //   145: ifeq +20 -> 165
      //   148: aload_0
      //   149: iload_3
      //   150: ineg
      //   151: invokevirtual 158	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:addAndGet	(I)I
      //   154: istore 4
      //   156: iload 4
      //   158: istore_3
      //   159: iload 4
      //   161: ifne -141 -> 20
      //   164: return
      //   165: aload_1
      //   166: invokevirtual 146	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   169: astore 7
      //   171: aload 5
      //   173: getstatic 62	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:LEFT_VALUE	Ljava/lang/Integer;
      //   176: if_acmpne +202 -> 378
      //   179: aload_0
      //   180: getfield 160	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:leftIndex	I
      //   183: istore 4
      //   185: aload_0
      //   186: iload 4
      //   188: iconst_1
      //   189: iadd
      //   190: putfield 160	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:leftIndex	I
      //   193: aload_0
      //   194: getfield 98	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:lefts	Ljava/util/Map;
      //   197: iload 4
      //   199: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   202: aload 7
      //   204: invokeinterface 164 3 0
      //   209: pop
      //   210: aload_0
      //   211: getfield 107	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:leftEnd	Lio/reactivex/functions/Function;
      //   214: aload 7
      //   216: invokeinterface 170 2 0
      //   221: ldc -84
      //   223: invokestatic 178	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   226: checkcast 180	io/reactivex/ObservableSource
      //   229: astore 8
      //   231: new 182	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   234: dup
      //   235: aload_0
      //   236: iconst_1
      //   237: iload 4
      //   239: invokespecial 185	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableGroupJoin$JoinSupport;ZI)V
      //   242: astore 5
      //   244: aload_0
      //   245: getfield 80	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   248: aload 5
      //   250: invokevirtual 189	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   253: pop
      //   254: aload 8
      //   256: aload 5
      //   258: invokeinterface 192 2 0
      //   263: aload_0
      //   264: getfield 105	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   267: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   270: checkcast 137	java/lang/Throwable
      //   273: ifnull +17 -> 290
      //   276: aload_1
      //   277: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   280: aload_0
      //   281: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:cancelAll	()V
      //   284: aload_0
      //   285: aload_2
      //   286: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   289: return
      //   290: aload_0
      //   291: getfield 100	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rights	Ljava/util/Map;
      //   294: invokeinterface 196 1 0
      //   299: invokeinterface 202 1 0
      //   304: astore 5
      //   306: aload 5
      //   308: invokeinterface 208 1 0
      //   313: ifeq -293 -> 20
      //   316: aload 5
      //   318: invokeinterface 211 1 0
      //   323: astore 8
      //   325: aload_0
      //   326: getfield 111	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:resultSelector	Lio/reactivex/functions/BiFunction;
      //   329: aload 7
      //   331: aload 8
      //   333: invokeinterface 215 3 0
      //   338: ldc -39
      //   340: invokestatic 178	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   343: astore 8
      //   345: aload_2
      //   346: aload 8
      //   348: invokeinterface 221 2 0
      //   353: goto -47 -> 306
      //   356: astore 7
      //   358: aload_0
      //   359: aload 7
      //   361: aload_2
      //   362: aload_1
      //   363: invokevirtual 225	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   366: return
      //   367: astore 7
      //   369: aload_0
      //   370: aload 7
      //   372: aload_2
      //   373: aload_1
      //   374: invokevirtual 225	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   377: return
      //   378: aload 5
      //   380: getstatic 64	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:RIGHT_VALUE	Ljava/lang/Integer;
      //   383: if_acmpne +202 -> 585
      //   386: aload_0
      //   387: getfield 227	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rightIndex	I
      //   390: istore 4
      //   392: aload_0
      //   393: iload 4
      //   395: iconst_1
      //   396: iadd
      //   397: putfield 227	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rightIndex	I
      //   400: aload_0
      //   401: getfield 100	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rights	Ljava/util/Map;
      //   404: iload 4
      //   406: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   409: aload 7
      //   411: invokeinterface 164 3 0
      //   416: pop
      //   417: aload_0
      //   418: getfield 109	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rightEnd	Lio/reactivex/functions/Function;
      //   421: aload 7
      //   423: invokeinterface 170 2 0
      //   428: ldc -27
      //   430: invokestatic 178	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   433: checkcast 180	io/reactivex/ObservableSource
      //   436: astore 8
      //   438: new 182	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   441: dup
      //   442: aload_0
      //   443: iconst_0
      //   444: iload 4
      //   446: invokespecial 185	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableGroupJoin$JoinSupport;ZI)V
      //   449: astore 5
      //   451: aload_0
      //   452: getfield 80	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   455: aload 5
      //   457: invokevirtual 189	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   460: pop
      //   461: aload 8
      //   463: aload 5
      //   465: invokeinterface 192 2 0
      //   470: aload_0
      //   471: getfield 105	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   474: invokevirtual 135	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   477: checkcast 137	java/lang/Throwable
      //   480: ifnull +17 -> 497
      //   483: aload_1
      //   484: invokevirtual 130	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   487: aload_0
      //   488: invokevirtual 124	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:cancelAll	()V
      //   491: aload_0
      //   492: aload_2
      //   493: invokevirtual 141	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:errorAll	(Lio/reactivex/Observer;)V
      //   496: return
      //   497: aload_0
      //   498: getfield 98	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:lefts	Ljava/util/Map;
      //   501: invokeinterface 196 1 0
      //   506: invokeinterface 202 1 0
      //   511: astore 5
      //   513: aload 5
      //   515: invokeinterface 208 1 0
      //   520: ifeq -500 -> 20
      //   523: aload 5
      //   525: invokeinterface 211 1 0
      //   530: astore 8
      //   532: aload_0
      //   533: getfield 111	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:resultSelector	Lio/reactivex/functions/BiFunction;
      //   536: aload 8
      //   538: aload 7
      //   540: invokeinterface 215 3 0
      //   545: ldc -39
      //   547: invokestatic 178	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   550: astore 8
      //   552: aload_2
      //   553: aload 8
      //   555: invokeinterface 221 2 0
      //   560: goto -47 -> 513
      //   563: astore 7
      //   565: aload_0
      //   566: aload 7
      //   568: aload_2
      //   569: aload_1
      //   570: invokevirtual 225	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   573: return
      //   574: astore 7
      //   576: aload_0
      //   577: aload 7
      //   579: aload_2
      //   580: aload_1
      //   581: invokevirtual 225	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:fail	(Ljava/lang/Throwable;Lio/reactivex/Observer;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   584: return
      //   585: aload 5
      //   587: getstatic 66	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:LEFT_CLOSE	Ljava/lang/Integer;
      //   590: if_acmpne +41 -> 631
      //   593: aload 7
      //   595: checkcast 182	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   598: astore 7
      //   600: aload_0
      //   601: getfield 98	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:lefts	Ljava/util/Map;
      //   604: aload 7
      //   606: getfield 232	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:index	I
      //   609: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   612: invokeinterface 235 2 0
      //   617: pop
      //   618: aload_0
      //   619: getfield 80	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   622: aload 7
      //   624: invokevirtual 237	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   627: pop
      //   628: goto -608 -> 20
      //   631: aload 7
      //   633: checkcast 182	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver
      //   636: astore 7
      //   638: aload_0
      //   639: getfield 100	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:rights	Ljava/util/Map;
      //   642: aload 7
      //   644: getfield 232	io/reactivex/internal/operators/observable/ObservableGroupJoin$LeftRightEndObserver:index	I
      //   647: invokestatic 60	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   650: invokeinterface 235 2 0
      //   655: pop
      //   656: aload_0
      //   657: getfield 80	io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   660: aload 7
      //   662: invokevirtual 237	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   665: pop
      //   666: goto -646 -> 20
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	669	0	this	JoinDisposable
      //   12	569	1	localSpscLinkedArrayQueue	SpscLinkedArrayQueue
      //   17	563	2	localObserver	Observer
      //   19	140	3	i	int
      //   70	375	4	j	int
      //   85	501	5	localObject1	Object
      //   93	51	6	k	int
      //   169	161	7	localObject2	Object
      //   356	4	7	localThrowable1	Throwable
      //   367	172	7	localThrowable2	Throwable
      //   563	4	7	localThrowable3	Throwable
      //   574	20	7	localThrowable4	Throwable
      //   598	63	7	localLeftRightEndObserver	ObservableGroupJoin.LeftRightEndObserver
      //   229	325	8	localObject3	Object
      // Exception table:
      //   from	to	target	type
      //   325	345	356	finally
      //   210	231	367	finally
      //   532	552	563	finally
      //   417	438	574	finally
    }
    
    void errorAll(Observer<?> paramObserver)
    {
      Throwable localThrowable = ExceptionHelper.terminate(this.error);
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
}
