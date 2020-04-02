package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
  extends AbstractFlowableWithUpstream<TLeft, R>
{
  final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
  final Publisher<? extends TRight> other;
  final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
  final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
  
  public FlowableJoin(Flowable<TLeft> paramFlowable, Publisher<? extends TRight> paramPublisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> paramFunction, Function<? super TRight, ? extends Publisher<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super TRight, ? extends R> paramBiFunction)
  {
    super(paramFlowable);
    this.other = paramPublisher;
    this.leftEnd = paramFunction;
    this.rightEnd = paramFunction1;
    this.resultSelector = paramBiFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    JoinSubscription localJoinSubscription = new JoinSubscription(paramSubscriber, this.leftEnd, this.rightEnd, this.resultSelector);
    paramSubscriber.onSubscribe(localJoinSubscription);
    paramSubscriber = new FlowableGroupJoin.LeftRightSubscriber(localJoinSubscription, true);
    localJoinSubscription.disposables.add(paramSubscriber);
    FlowableGroupJoin.LeftRightSubscriber localLeftRightSubscriber = new FlowableGroupJoin.LeftRightSubscriber(localJoinSubscription, false);
    localJoinSubscription.disposables.add(localLeftRightSubscriber);
    this.source.subscribe(paramSubscriber);
    this.other.subscribe(localLeftRightSubscriber);
  }
  
  static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R>
    extends AtomicInteger
    implements Subscription, FlowableGroupJoin.JoinSupport
  {
    static final Integer LEFT_CLOSE = Integer.valueOf(3);
    static final Integer LEFT_VALUE = Integer.valueOf(1);
    static final Integer RIGHT_CLOSE = Integer.valueOf(4);
    static final Integer RIGHT_VALUE = Integer.valueOf(2);
    private static final long serialVersionUID = -6071216598687999801L;
    final AtomicInteger active;
    volatile boolean cancelled;
    final CompositeDisposable disposables;
    final Subscriber<? super R> downstream;
    final AtomicReference<Throwable> error;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    int leftIndex;
    final Map<Integer, TLeft> lefts;
    final SpscLinkedArrayQueue<Object> queue;
    final AtomicLong requested;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
    int rightIndex;
    final Map<Integer, TRight> rights;
    
    JoinSubscription(Subscriber<? super R> paramSubscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> paramFunction, Function<? super TRight, ? extends Publisher<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super TRight, ? extends R> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.requested = new AtomicLong();
      this.disposables = new CompositeDisposable();
      this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
      this.lefts = new LinkedHashMap();
      this.rights = new LinkedHashMap();
      this.error = new AtomicReference();
      this.leftEnd = paramFunction;
      this.rightEnd = paramFunction1;
      this.resultSelector = paramBiFunction;
      this.active = new AtomicInteger(2);
    }
    
    public void cancel()
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
    
    void cancelAll()
    {
      this.disposables.dispose();
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 132	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 100	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 77	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:downstream	Lorg/reactivestreams/Subscriber;
      //   17: astore_2
      //   18: iconst_1
      //   19: istore_3
      //   20: aload_0
      //   21: getfield 126	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelled	Z
      //   24: ifeq +8 -> 32
      //   27: aload_1
      //   28: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   31: return
      //   32: aload_0
      //   33: getfield 112	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   36: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   39: checkcast 145	java/lang/Throwable
      //   42: ifnull +17 -> 59
      //   45: aload_1
      //   46: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   49: aload_0
      //   50: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelAll	()V
      //   53: aload_0
      //   54: aload_2
      //   55: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   58: return
      //   59: aload_0
      //   60: getfield 121	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:active	Ljava/util/concurrent/atomic/AtomicInteger;
      //   63: invokevirtual 151	java/util/concurrent/atomic/AtomicInteger:get	()I
      //   66: ifne +9 -> 75
      //   69: iconst_1
      //   70: istore 4
      //   72: goto +6 -> 78
      //   75: iconst_0
      //   76: istore 4
      //   78: aload_1
      //   79: invokevirtual 154	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   82: checkcast 58	java/lang/Integer
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
      //   112: getfield 105	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:lefts	Ljava/util/Map;
      //   115: invokeinterface 157 1 0
      //   120: aload_0
      //   121: getfield 107	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rights	Ljava/util/Map;
      //   124: invokeinterface 157 1 0
      //   129: aload_0
      //   130: getfield 87	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   133: invokevirtual 138	io/reactivex/disposables/CompositeDisposable:dispose	()V
      //   136: aload_2
      //   137: invokeinterface 162 1 0
      //   142: return
      //   143: iload 6
      //   145: ifeq +20 -> 165
      //   148: aload_0
      //   149: iload_3
      //   150: ineg
      //   151: invokevirtual 166	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:addAndGet	(I)I
      //   154: istore 4
      //   156: iload 4
      //   158: istore_3
      //   159: iload 4
      //   161: ifne -141 -> 20
      //   164: return
      //   165: aload_1
      //   166: invokevirtual 154	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   169: astore 7
      //   171: aload 5
      //   173: getstatic 64	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:LEFT_VALUE	Ljava/lang/Integer;
      //   176: if_acmpne +279 -> 455
      //   179: aload_0
      //   180: getfield 168	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:leftIndex	I
      //   183: istore 4
      //   185: aload_0
      //   186: iload 4
      //   188: iconst_1
      //   189: iadd
      //   190: putfield 168	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:leftIndex	I
      //   193: aload_0
      //   194: getfield 105	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:lefts	Ljava/util/Map;
      //   197: iload 4
      //   199: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   202: aload 7
      //   204: invokeinterface 172 3 0
      //   209: pop
      //   210: aload_0
      //   211: getfield 114	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:leftEnd	Lio/reactivex/functions/Function;
      //   214: aload 7
      //   216: invokeinterface 178 2 0
      //   221: ldc -76
      //   223: invokestatic 186	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   226: checkcast 188	org/reactivestreams/Publisher
      //   229: astore 5
      //   231: new 190	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   234: dup
      //   235: aload_0
      //   236: iconst_1
      //   237: iload 4
      //   239: invokespecial 193	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableGroupJoin$JoinSupport;ZI)V
      //   242: astore 8
      //   244: aload_0
      //   245: getfield 87	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   248: aload 8
      //   250: invokevirtual 197	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   253: pop
      //   254: aload 5
      //   256: aload 8
      //   258: invokeinterface 200 2 0
      //   263: aload_0
      //   264: getfield 112	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   267: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   270: checkcast 145	java/lang/Throwable
      //   273: ifnull +17 -> 290
      //   276: aload_1
      //   277: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   280: aload_0
      //   281: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelAll	()V
      //   284: aload_0
      //   285: aload_2
      //   286: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   289: return
      //   290: aload_0
      //   291: getfield 82	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   294: invokevirtual 203	java/util/concurrent/atomic/AtomicLong:get	()J
      //   297: lstore 9
      //   299: aload_0
      //   300: getfield 107	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rights	Ljava/util/Map;
      //   303: invokeinterface 207 1 0
      //   308: invokeinterface 213 1 0
      //   313: astore 5
      //   315: lconst_0
      //   316: lstore 11
      //   318: aload 5
      //   320: invokeinterface 219 1 0
      //   325: ifeq +99 -> 424
      //   328: aload 5
      //   330: invokeinterface 222 1 0
      //   335: astore 8
      //   337: aload_0
      //   338: getfield 118	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:resultSelector	Lio/reactivex/functions/BiFunction;
      //   341: aload 7
      //   343: aload 8
      //   345: invokeinterface 226 3 0
      //   350: ldc -28
      //   352: invokestatic 186	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   355: astore 8
      //   357: lload 11
      //   359: lload 9
      //   361: lcmp
      //   362: ifeq +20 -> 382
      //   365: aload_2
      //   366: aload 8
      //   368: invokeinterface 232 2 0
      //   373: lload 11
      //   375: lconst_1
      //   376: ladd
      //   377: lstore 11
      //   379: goto -61 -> 318
      //   382: aload_0
      //   383: getfield 112	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   386: new 234	io/reactivex/exceptions/MissingBackpressureException
      //   389: dup
      //   390: ldc -20
      //   392: invokespecial 239	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   395: invokestatic 245	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   398: pop
      //   399: aload_1
      //   400: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   403: aload_0
      //   404: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelAll	()V
      //   407: aload_0
      //   408: aload_2
      //   409: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   412: return
      //   413: astore 7
      //   415: aload_0
      //   416: aload 7
      //   418: aload_2
      //   419: aload_1
      //   420: invokevirtual 249	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   423: return
      //   424: lload 11
      //   426: lconst_0
      //   427: lcmp
      //   428: ifeq +401 -> 829
      //   431: aload_0
      //   432: getfield 82	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   435: lload 11
      //   437: invokestatic 255	io/reactivex/internal/util/BackpressureHelper:produced	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   440: pop2
      //   441: goto +388 -> 829
      //   444: astore 7
      //   446: aload_0
      //   447: aload 7
      //   449: aload_2
      //   450: aload_1
      //   451: invokevirtual 249	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   454: return
      //   455: aload 5
      //   457: getstatic 66	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:RIGHT_VALUE	Ljava/lang/Integer;
      //   460: if_acmpne +280 -> 740
      //   463: aload_0
      //   464: getfield 257	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rightIndex	I
      //   467: istore 4
      //   469: aload_0
      //   470: iload 4
      //   472: iconst_1
      //   473: iadd
      //   474: putfield 257	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rightIndex	I
      //   477: aload_0
      //   478: getfield 107	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rights	Ljava/util/Map;
      //   481: iload 4
      //   483: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   486: aload 7
      //   488: invokeinterface 172 3 0
      //   493: pop
      //   494: aload_0
      //   495: getfield 116	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rightEnd	Lio/reactivex/functions/Function;
      //   498: aload 7
      //   500: invokeinterface 178 2 0
      //   505: ldc_w 259
      //   508: invokestatic 186	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   511: checkcast 188	org/reactivestreams/Publisher
      //   514: astore 5
      //   516: new 190	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   519: dup
      //   520: aload_0
      //   521: iconst_0
      //   522: iload 4
      //   524: invokespecial 193	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableGroupJoin$JoinSupport;ZI)V
      //   527: astore 8
      //   529: aload_0
      //   530: getfield 87	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   533: aload 8
      //   535: invokevirtual 197	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   538: pop
      //   539: aload 5
      //   541: aload 8
      //   543: invokeinterface 200 2 0
      //   548: aload_0
      //   549: getfield 112	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   552: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   555: checkcast 145	java/lang/Throwable
      //   558: ifnull +17 -> 575
      //   561: aload_1
      //   562: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   565: aload_0
      //   566: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelAll	()V
      //   569: aload_0
      //   570: aload_2
      //   571: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   574: return
      //   575: aload_0
      //   576: getfield 82	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   579: invokevirtual 203	java/util/concurrent/atomic/AtomicLong:get	()J
      //   582: lstore 9
      //   584: aload_0
      //   585: getfield 105	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:lefts	Ljava/util/Map;
      //   588: invokeinterface 207 1 0
      //   593: invokeinterface 213 1 0
      //   598: astore 5
      //   600: lconst_0
      //   601: lstore 11
      //   603: aload 5
      //   605: invokeinterface 219 1 0
      //   610: ifeq +99 -> 709
      //   613: aload 5
      //   615: invokeinterface 222 1 0
      //   620: astore 8
      //   622: aload_0
      //   623: getfield 118	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:resultSelector	Lio/reactivex/functions/BiFunction;
      //   626: aload 8
      //   628: aload 7
      //   630: invokeinterface 226 3 0
      //   635: ldc -28
      //   637: invokestatic 186	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   640: astore 8
      //   642: lload 11
      //   644: lload 9
      //   646: lcmp
      //   647: ifeq +20 -> 667
      //   650: aload_2
      //   651: aload 8
      //   653: invokeinterface 232 2 0
      //   658: lload 11
      //   660: lconst_1
      //   661: ladd
      //   662: lstore 11
      //   664: goto -61 -> 603
      //   667: aload_0
      //   668: getfield 112	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   671: new 234	io/reactivex/exceptions/MissingBackpressureException
      //   674: dup
      //   675: ldc -20
      //   677: invokespecial 239	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   680: invokestatic 245	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   683: pop
      //   684: aload_1
      //   685: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   688: aload_0
      //   689: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:cancelAll	()V
      //   692: aload_0
      //   693: aload_2
      //   694: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   697: return
      //   698: astore 7
      //   700: aload_0
      //   701: aload 7
      //   703: aload_2
      //   704: aload_1
      //   705: invokevirtual 249	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   708: return
      //   709: lload 11
      //   711: lconst_0
      //   712: lcmp
      //   713: ifeq +116 -> 829
      //   716: aload_0
      //   717: getfield 82	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   720: lload 11
      //   722: invokestatic 255	io/reactivex/internal/util/BackpressureHelper:produced	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   725: pop2
      //   726: goto +103 -> 829
      //   729: astore 7
      //   731: aload_0
      //   732: aload 7
      //   734: aload_2
      //   735: aload_1
      //   736: invokevirtual 249	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   739: return
      //   740: aload 5
      //   742: getstatic 68	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:LEFT_CLOSE	Ljava/lang/Integer;
      //   745: if_acmpne +41 -> 786
      //   748: aload 7
      //   750: checkcast 190	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   753: astore 7
      //   755: aload_0
      //   756: getfield 105	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:lefts	Ljava/util/Map;
      //   759: aload 7
      //   761: getfield 262	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:index	I
      //   764: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   767: invokeinterface 265 2 0
      //   772: pop
      //   773: aload_0
      //   774: getfield 87	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   777: aload 7
      //   779: invokevirtual 267	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   782: pop
      //   783: goto +46 -> 829
      //   786: aload 5
      //   788: getstatic 70	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:RIGHT_CLOSE	Ljava/lang/Integer;
      //   791: if_acmpne +38 -> 829
      //   794: aload 7
      //   796: checkcast 190	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   799: astore 7
      //   801: aload_0
      //   802: getfield 107	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:rights	Ljava/util/Map;
      //   805: aload 7
      //   807: getfield 262	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:index	I
      //   810: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   813: invokeinterface 265 2 0
      //   818: pop
      //   819: aload_0
      //   820: getfield 87	io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   823: aload 7
      //   825: invokevirtual 267	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   828: pop
      //   829: goto -809 -> 20
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	832	0	this	JoinSubscription
      //   12	724	1	localSpscLinkedArrayQueue	SpscLinkedArrayQueue
      //   17	718	2	localSubscriber	Subscriber
      //   19	140	3	i	int
      //   70	453	4	j	int
      //   85	702	5	localObject1	Object
      //   93	51	6	k	int
      //   169	173	7	localObject2	Object
      //   413	4	7	localThrowable1	Throwable
      //   444	185	7	localThrowable2	Throwable
      //   698	4	7	localThrowable3	Throwable
      //   729	20	7	localThrowable4	Throwable
      //   753	71	7	localLeftRightEndSubscriber	FlowableGroupJoin.LeftRightEndSubscriber
      //   242	410	8	localObject3	Object
      //   297	348	9	l1	long
      //   316	405	11	l2	long
      // Exception table:
      //   from	to	target	type
      //   337	357	413	finally
      //   210	231	444	finally
      //   622	642	698	finally
      //   494	516	729	finally
    }
    
    void errorAll(Subscriber<?> paramSubscriber)
    {
      Throwable localThrowable = ExceptionHelper.terminate(this.error);
      this.lefts.clear();
      this.rights.clear();
      paramSubscriber.onError(localThrowable);
    }
    
    void fail(Throwable paramThrowable, Subscriber<?> paramSubscriber, SimpleQueue<?> paramSimpleQueue)
    {
      Exceptions.throwIfFatal(paramThrowable);
      ExceptionHelper.addThrowable(this.error, paramThrowable);
      paramSimpleQueue.clear();
      cancelAll();
      errorAll(paramSubscriber);
    }
    
    public void innerClose(boolean paramBoolean, FlowableGroupJoin.LeftRightEndSubscriber paramLeftRightEndSubscriber)
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
        localSpscLinkedArrayQueue.offer(localInteger, paramLeftRightEndSubscriber);
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
    
    public void innerComplete(FlowableGroupJoin.LeftRightSubscriber paramLeftRightSubscriber)
    {
      this.disposables.delete(paramLeftRightSubscriber);
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
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this.requested, paramLong);
      }
    }
  }
}
