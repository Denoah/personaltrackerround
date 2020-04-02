package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
  extends AbstractFlowableWithUpstream<TLeft, R>
{
  final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
  final Publisher<? extends TRight> other;
  final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
  final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
  
  public FlowableGroupJoin(Flowable<TLeft> paramFlowable, Publisher<? extends TRight> paramPublisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> paramFunction, Function<? super TRight, ? extends Publisher<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> paramBiFunction)
  {
    super(paramFlowable);
    this.other = paramPublisher;
    this.leftEnd = paramFunction;
    this.rightEnd = paramFunction1;
    this.resultSelector = paramBiFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    GroupJoinSubscription localGroupJoinSubscription = new GroupJoinSubscription(paramSubscriber, this.leftEnd, this.rightEnd, this.resultSelector);
    paramSubscriber.onSubscribe(localGroupJoinSubscription);
    LeftRightSubscriber localLeftRightSubscriber = new LeftRightSubscriber(localGroupJoinSubscription, true);
    localGroupJoinSubscription.disposables.add(localLeftRightSubscriber);
    paramSubscriber = new LeftRightSubscriber(localGroupJoinSubscription, false);
    localGroupJoinSubscription.disposables.add(paramSubscriber);
    this.source.subscribe(localLeftRightSubscriber);
    this.other.subscribe(paramSubscriber);
  }
  
  static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R>
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
    final Map<Integer, UnicastProcessor<TRight>> lefts;
    final SpscLinkedArrayQueue<Object> queue;
    final AtomicLong requested;
    final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
    int rightIndex;
    final Map<Integer, TRight> rights;
    
    GroupJoinSubscription(Subscriber<? super R> paramSubscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> paramFunction, Function<? super TRight, ? extends Publisher<TRightEnd>> paramFunction1, BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> paramBiFunction)
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
      //   1: invokevirtual 132	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 100	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 77	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:downstream	Lorg/reactivestreams/Subscriber;
      //   17: astore_2
      //   18: iconst_1
      //   19: istore_3
      //   20: aload_0
      //   21: getfield 126	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:cancelled	Z
      //   24: ifeq +8 -> 32
      //   27: aload_1
      //   28: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   31: return
      //   32: aload_0
      //   33: getfield 112	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   36: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   39: checkcast 145	java/lang/Throwable
      //   42: ifnull +17 -> 59
      //   45: aload_1
      //   46: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   49: aload_0
      //   50: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:cancelAll	()V
      //   53: aload_0
      //   54: aload_2
      //   55: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   58: return
      //   59: aload_0
      //   60: getfield 121	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:active	Ljava/util/concurrent/atomic/AtomicInteger;
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
      //   103: ifeq +79 -> 182
      //   106: iload 6
      //   108: ifeq +74 -> 182
      //   111: aload_0
      //   112: getfield 105	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:lefts	Ljava/util/Map;
      //   115: invokeinterface 160 1 0
      //   120: invokeinterface 166 1 0
      //   125: astore_1
      //   126: aload_1
      //   127: invokeinterface 172 1 0
      //   132: ifeq +18 -> 150
      //   135: aload_1
      //   136: invokeinterface 175 1 0
      //   141: checkcast 177	io/reactivex/processors/UnicastProcessor
      //   144: invokevirtual 180	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   147: goto -21 -> 126
      //   150: aload_0
      //   151: getfield 105	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:lefts	Ljava/util/Map;
      //   154: invokeinterface 181 1 0
      //   159: aload_0
      //   160: getfield 107	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rights	Ljava/util/Map;
      //   163: invokeinterface 181 1 0
      //   168: aload_0
      //   169: getfield 87	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   172: invokevirtual 138	io/reactivex/disposables/CompositeDisposable:dispose	()V
      //   175: aload_2
      //   176: invokeinterface 184 1 0
      //   181: return
      //   182: iload 6
      //   184: ifeq +20 -> 204
      //   187: aload_0
      //   188: iload_3
      //   189: ineg
      //   190: invokevirtual 188	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:addAndGet	(I)I
      //   193: istore 4
      //   195: iload 4
      //   197: istore_3
      //   198: iload 4
      //   200: ifne -180 -> 20
      //   203: return
      //   204: aload_1
      //   205: invokevirtual 154	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   208: astore 7
      //   210: aload 5
      //   212: getstatic 64	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:LEFT_VALUE	Ljava/lang/Integer;
      //   215: if_acmpne +247 -> 462
      //   218: invokestatic 192	io/reactivex/processors/UnicastProcessor:create	()Lio/reactivex/processors/UnicastProcessor;
      //   221: astore 5
      //   223: aload_0
      //   224: getfield 194	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:leftIndex	I
      //   227: istore 4
      //   229: aload_0
      //   230: iload 4
      //   232: iconst_1
      //   233: iadd
      //   234: putfield 194	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:leftIndex	I
      //   237: aload_0
      //   238: getfield 105	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:lefts	Ljava/util/Map;
      //   241: iload 4
      //   243: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   246: aload 5
      //   248: invokeinterface 198 3 0
      //   253: pop
      //   254: aload_0
      //   255: getfield 114	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:leftEnd	Lio/reactivex/functions/Function;
      //   258: aload 7
      //   260: invokeinterface 204 2 0
      //   265: ldc -50
      //   267: invokestatic 212	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   270: checkcast 214	org/reactivestreams/Publisher
      //   273: astore 8
      //   275: new 216	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   278: dup
      //   279: aload_0
      //   280: iconst_1
      //   281: iload 4
      //   283: invokespecial 219	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableGroupJoin$JoinSupport;ZI)V
      //   286: astore 9
      //   288: aload_0
      //   289: getfield 87	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   292: aload 9
      //   294: invokevirtual 223	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   297: pop
      //   298: aload 8
      //   300: aload 9
      //   302: invokeinterface 226 2 0
      //   307: aload_0
      //   308: getfield 112	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   311: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   314: checkcast 145	java/lang/Throwable
      //   317: ifnull +17 -> 334
      //   320: aload_1
      //   321: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   324: aload_0
      //   325: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:cancelAll	()V
      //   328: aload_0
      //   329: aload_2
      //   330: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   333: return
      //   334: aload_0
      //   335: getfield 118	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:resultSelector	Lio/reactivex/functions/BiFunction;
      //   338: aload 7
      //   340: aload 5
      //   342: invokeinterface 230 3 0
      //   347: ldc -24
      //   349: invokestatic 212	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   352: astore 7
      //   354: aload_0
      //   355: getfield 82	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   358: invokevirtual 235	java/util/concurrent/atomic/AtomicLong:get	()J
      //   361: lconst_0
      //   362: lcmp
      //   363: ifeq +61 -> 424
      //   366: aload_2
      //   367: aload 7
      //   369: invokeinterface 239 2 0
      //   374: aload_0
      //   375: getfield 82	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   378: lconst_1
      //   379: invokestatic 245	io/reactivex/internal/util/BackpressureHelper:produced	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   382: pop2
      //   383: aload_0
      //   384: getfield 107	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rights	Ljava/util/Map;
      //   387: invokeinterface 160 1 0
      //   392: invokeinterface 166 1 0
      //   397: astore 7
      //   399: aload 7
      //   401: invokeinterface 172 1 0
      //   406: ifeq -386 -> 20
      //   409: aload 5
      //   411: aload 7
      //   413: invokeinterface 175 1 0
      //   418: invokevirtual 246	io/reactivex/processors/UnicastProcessor:onNext	(Ljava/lang/Object;)V
      //   421: goto -22 -> 399
      //   424: aload_0
      //   425: new 248	io/reactivex/exceptions/MissingBackpressureException
      //   428: dup
      //   429: ldc -6
      //   431: invokespecial 253	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   434: aload_2
      //   435: aload_1
      //   436: invokevirtual 257	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   439: return
      //   440: astore 7
      //   442: aload_0
      //   443: aload 7
      //   445: aload_2
      //   446: aload_1
      //   447: invokevirtual 257	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   450: return
      //   451: astore 7
      //   453: aload_0
      //   454: aload 7
      //   456: aload_2
      //   457: aload_1
      //   458: invokevirtual 257	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   461: return
      //   462: aload 5
      //   464: getstatic 66	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:RIGHT_VALUE	Ljava/lang/Integer;
      //   467: if_acmpne +170 -> 637
      //   470: aload_0
      //   471: getfield 259	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rightIndex	I
      //   474: istore 4
      //   476: aload_0
      //   477: iload 4
      //   479: iconst_1
      //   480: iadd
      //   481: putfield 259	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rightIndex	I
      //   484: aload_0
      //   485: getfield 107	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rights	Ljava/util/Map;
      //   488: iload 4
      //   490: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   493: aload 7
      //   495: invokeinterface 198 3 0
      //   500: pop
      //   501: aload_0
      //   502: getfield 116	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rightEnd	Lio/reactivex/functions/Function;
      //   505: aload 7
      //   507: invokeinterface 204 2 0
      //   512: ldc_w 261
      //   515: invokestatic 212	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   518: checkcast 214	org/reactivestreams/Publisher
      //   521: astore 9
      //   523: new 216	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   526: dup
      //   527: aload_0
      //   528: iconst_0
      //   529: iload 4
      //   531: invokespecial 219	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableGroupJoin$JoinSupport;ZI)V
      //   534: astore 5
      //   536: aload_0
      //   537: getfield 87	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   540: aload 5
      //   542: invokevirtual 223	io/reactivex/disposables/CompositeDisposable:add	(Lio/reactivex/disposables/Disposable;)Z
      //   545: pop
      //   546: aload 9
      //   548: aload 5
      //   550: invokeinterface 226 2 0
      //   555: aload_0
      //   556: getfield 112	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   559: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   562: checkcast 145	java/lang/Throwable
      //   565: ifnull +17 -> 582
      //   568: aload_1
      //   569: invokevirtual 135	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   572: aload_0
      //   573: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:cancelAll	()V
      //   576: aload_0
      //   577: aload_2
      //   578: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:errorAll	(Lorg/reactivestreams/Subscriber;)V
      //   581: return
      //   582: aload_0
      //   583: getfield 105	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:lefts	Ljava/util/Map;
      //   586: invokeinterface 160 1 0
      //   591: invokeinterface 166 1 0
      //   596: astore 5
      //   598: aload 5
      //   600: invokeinterface 172 1 0
      //   605: ifeq -585 -> 20
      //   608: aload 5
      //   610: invokeinterface 175 1 0
      //   615: checkcast 177	io/reactivex/processors/UnicastProcessor
      //   618: aload 7
      //   620: invokevirtual 246	io/reactivex/processors/UnicastProcessor:onNext	(Ljava/lang/Object;)V
      //   623: goto -25 -> 598
      //   626: astore 7
      //   628: aload_0
      //   629: aload 7
      //   631: aload_2
      //   632: aload_1
      //   633: invokevirtual 257	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:fail	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)V
      //   636: return
      //   637: aload 5
      //   639: getstatic 68	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:LEFT_CLOSE	Ljava/lang/Integer;
      //   642: if_acmpne +55 -> 697
      //   645: aload 7
      //   647: checkcast 216	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   650: astore 7
      //   652: aload_0
      //   653: getfield 105	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:lefts	Ljava/util/Map;
      //   656: aload 7
      //   658: getfield 264	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:index	I
      //   661: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   664: invokeinterface 267 2 0
      //   669: checkcast 177	io/reactivex/processors/UnicastProcessor
      //   672: astore 5
      //   674: aload_0
      //   675: getfield 87	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   678: aload 7
      //   680: invokevirtual 269	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   683: pop
      //   684: aload 5
      //   686: ifnull -666 -> 20
      //   689: aload 5
      //   691: invokevirtual 180	io/reactivex/processors/UnicastProcessor:onComplete	()V
      //   694: goto -674 -> 20
      //   697: aload 5
      //   699: getstatic 70	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:RIGHT_CLOSE	Ljava/lang/Integer;
      //   702: if_acmpne -682 -> 20
      //   705: aload 7
      //   707: checkcast 216	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber
      //   710: astore 7
      //   712: aload_0
      //   713: getfield 107	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:rights	Ljava/util/Map;
      //   716: aload 7
      //   718: getfield 264	io/reactivex/internal/operators/flowable/FlowableGroupJoin$LeftRightEndSubscriber:index	I
      //   721: invokestatic 62	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   724: invokeinterface 267 2 0
      //   729: pop
      //   730: aload_0
      //   731: getfield 87	io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription:disposables	Lio/reactivex/disposables/CompositeDisposable;
      //   734: aload 7
      //   736: invokevirtual 269	io/reactivex/disposables/CompositeDisposable:remove	(Lio/reactivex/disposables/Disposable;)Z
      //   739: pop
      //   740: goto -720 -> 20
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	743	0	this	GroupJoinSubscription
      //   12	621	1	localObject1	Object
      //   17	615	2	localSubscriber	Subscriber
      //   19	179	3	i	int
      //   70	460	4	j	int
      //   85	613	5	localObject2	Object
      //   93	90	6	k	int
      //   208	204	7	localObject3	Object
      //   440	4	7	localThrowable1	Throwable
      //   451	168	7	localThrowable2	Throwable
      //   626	20	7	localThrowable3	Throwable
      //   650	85	7	localLeftRightEndSubscriber	FlowableGroupJoin.LeftRightEndSubscriber
      //   273	26	8	localPublisher	Publisher
      //   286	261	9	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   334	354	440	finally
      //   254	275	451	finally
      //   501	523	626	finally
    }
    
    void errorAll(Subscriber<?> paramSubscriber)
    {
      Throwable localThrowable = ExceptionHelper.terminate(this.error);
      Iterator localIterator = this.lefts.values().iterator();
      while (localIterator.hasNext()) {
        ((UnicastProcessor)localIterator.next()).onError(localThrowable);
      }
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
  
  static abstract interface JoinSupport
  {
    public abstract void innerClose(boolean paramBoolean, FlowableGroupJoin.LeftRightEndSubscriber paramLeftRightEndSubscriber);
    
    public abstract void innerCloseError(Throwable paramThrowable);
    
    public abstract void innerComplete(FlowableGroupJoin.LeftRightSubscriber paramLeftRightSubscriber);
    
    public abstract void innerError(Throwable paramThrowable);
    
    public abstract void innerValue(boolean paramBoolean, Object paramObject);
  }
  
  static final class LeftRightEndSubscriber
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<Object>, Disposable
  {
    private static final long serialVersionUID = 1883890389173668373L;
    final int index;
    final boolean isLeft;
    final FlowableGroupJoin.JoinSupport parent;
    
    LeftRightEndSubscriber(FlowableGroupJoin.JoinSupport paramJoinSupport, boolean paramBoolean, int paramInt)
    {
      this.parent = paramJoinSupport;
      this.isLeft = paramBoolean;
      this.index = paramInt;
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
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
      if (SubscriptionHelper.cancel(this)) {
        this.parent.innerClose(this.isLeft, this);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
  
  static final class LeftRightSubscriber
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<Object>, Disposable
  {
    private static final long serialVersionUID = 1883890389173668373L;
    final boolean isLeft;
    final FlowableGroupJoin.JoinSupport parent;
    
    LeftRightSubscriber(FlowableGroupJoin.JoinSupport paramJoinSupport, boolean paramBoolean)
    {
      this.parent = paramJoinSupport;
      this.isLeft = paramBoolean;
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
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
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
}
