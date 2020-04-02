package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublishMulticast<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final boolean delayError;
  final int prefetch;
  final Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector;
  
  public FlowablePublishMulticast(Flowable<T> paramFlowable, Function<? super Flowable<T>, ? extends Publisher<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.selector = paramFunction;
    this.prefetch = paramInt;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    MulticastProcessor localMulticastProcessor = new MulticastProcessor(this.prefetch, this.delayError);
    try
    {
      Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.selector.apply(localMulticastProcessor), "selector returned a null Publisher");
      localPublisher.subscribe(new OutputCanceller(paramSubscriber, localMulticastProcessor));
      this.source.subscribe(localMulticastProcessor);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class MulticastProcessor<T>
    extends Flowable<T>
    implements FlowableSubscriber<T>, Disposable
  {
    static final FlowablePublishMulticast.MulticastSubscription[] EMPTY = new FlowablePublishMulticast.MulticastSubscription[0];
    static final FlowablePublishMulticast.MulticastSubscription[] TERMINATED = new FlowablePublishMulticast.MulticastSubscription[0];
    int consumed;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final int limit;
    final int prefetch;
    volatile SimpleQueue<T> queue;
    int sourceMode;
    final AtomicReference<FlowablePublishMulticast.MulticastSubscription<T>[]> subscribers;
    final AtomicReference<Subscription> upstream;
    final AtomicInteger wip;
    
    MulticastProcessor(int paramInt, boolean paramBoolean)
    {
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      this.delayError = paramBoolean;
      this.wip = new AtomicInteger();
      this.upstream = new AtomicReference();
      this.subscribers = new AtomicReference(EMPTY);
    }
    
    boolean add(FlowablePublishMulticast.MulticastSubscription<T> paramMulticastSubscription)
    {
      FlowablePublishMulticast.MulticastSubscription[] arrayOfMulticastSubscription1;
      FlowablePublishMulticast.MulticastSubscription[] arrayOfMulticastSubscription2;
      do
      {
        arrayOfMulticastSubscription1 = (FlowablePublishMulticast.MulticastSubscription[])this.subscribers.get();
        if (arrayOfMulticastSubscription1 == TERMINATED) {
          return false;
        }
        int i = arrayOfMulticastSubscription1.length;
        arrayOfMulticastSubscription2 = new FlowablePublishMulticast.MulticastSubscription[i + 1];
        System.arraycopy(arrayOfMulticastSubscription1, 0, arrayOfMulticastSubscription2, 0, i);
        arrayOfMulticastSubscription2[i] = paramMulticastSubscription;
      } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, arrayOfMulticastSubscription2));
      return true;
    }
    
    void completeAll()
    {
      for (FlowablePublishMulticast.MulticastSubscription localMulticastSubscription : (FlowablePublishMulticast.MulticastSubscription[])this.subscribers.getAndSet(TERMINATED)) {
        if (localMulticastSubscription.get() != Long.MIN_VALUE) {
          localMulticastSubscription.downstream.onComplete();
        }
      }
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this.upstream);
      if (this.wip.getAndIncrement() == 0)
      {
        SimpleQueue localSimpleQueue = this.queue;
        if (localSimpleQueue != null) {
          localSimpleQueue.clear();
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 59	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   4: invokevirtual 118	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
      //   7: ifeq +4 -> 11
      //   10: return
      //   11: aload_0
      //   12: getfield 120	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   15: astore_1
      //   16: aload_0
      //   17: getfield 128	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:consumed	I
      //   20: istore_2
      //   21: aload_0
      //   22: getfield 52	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:limit	I
      //   25: istore_3
      //   26: aload_0
      //   27: getfield 130	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:sourceMode	I
      //   30: iconst_1
      //   31: if_icmpeq +9 -> 40
      //   34: iconst_1
      //   35: istore 4
      //   37: goto +6 -> 43
      //   40: iconst_0
      //   41: istore 4
      //   43: aload_0
      //   44: getfield 69	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:subscribers	Ljava/util/concurrent/atomic/AtomicReference;
      //   47: astore 5
      //   49: aload 5
      //   51: invokevirtual 75	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   54: checkcast 76	[Lio/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription;
      //   57: astore 6
      //   59: iconst_1
      //   60: istore 7
      //   62: aload 6
      //   64: arraylength
      //   65: istore 8
      //   67: aload_1
      //   68: ifnull +553 -> 621
      //   71: iload 8
      //   73: ifeq +548 -> 621
      //   76: aload 6
      //   78: arraylength
      //   79: istore 9
      //   81: ldc2_w 131
      //   84: lstore 10
      //   86: iconst_0
      //   87: istore 12
      //   89: iload 12
      //   91: iload 9
      //   93: if_icmpge +83 -> 176
      //   96: aload 6
      //   98: iload 12
      //   100: aaload
      //   101: astore 13
      //   103: aload 13
      //   105: invokevirtual 96	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:get	()J
      //   108: aload 13
      //   110: getfield 136	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:emitted	J
      //   113: lsub
      //   114: lstore 14
      //   116: lload 14
      //   118: ldc2_w 97
      //   121: lcmp
      //   122: ifeq +30 -> 152
      //   125: iload 8
      //   127: istore 16
      //   129: lload 10
      //   131: lstore 17
      //   133: lload 10
      //   135: lload 14
      //   137: lcmp
      //   138: ifle +24 -> 162
      //   141: lload 14
      //   143: lstore 17
      //   145: iload 8
      //   147: istore 16
      //   149: goto +13 -> 162
      //   152: iload 8
      //   154: iconst_1
      //   155: isub
      //   156: istore 16
      //   158: lload 10
      //   160: lstore 17
      //   162: iinc 12 1
      //   165: iload 16
      //   167: istore 8
      //   169: lload 17
      //   171: lstore 10
      //   173: goto -84 -> 89
      //   176: iload_2
      //   177: istore 12
      //   179: iload 8
      //   181: ifne +9 -> 190
      //   184: lconst_0
      //   185: lstore 10
      //   187: iload_2
      //   188: istore 12
      //   190: lload 10
      //   192: lconst_0
      //   193: lcmp
      //   194: istore 8
      //   196: iload 8
      //   198: ifeq +308 -> 506
      //   201: aload_0
      //   202: invokevirtual 140	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:isDisposed	()Z
      //   205: ifeq +10 -> 215
      //   208: aload_1
      //   209: invokeinterface 125 1 0
      //   214: return
      //   215: aload_0
      //   216: getfield 142	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:done	Z
      //   219: istore 19
      //   221: iload 19
      //   223: ifeq +28 -> 251
      //   226: aload_0
      //   227: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:delayError	Z
      //   230: ifne +21 -> 251
      //   233: aload_0
      //   234: getfield 144	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:error	Ljava/lang/Throwable;
      //   237: astore 13
      //   239: aload 13
      //   241: ifnull +10 -> 251
      //   244: aload_0
      //   245: aload 13
      //   247: invokevirtual 148	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:errorAll	(Ljava/lang/Throwable;)V
      //   250: return
      //   251: aload_1
      //   252: invokeinterface 151 1 0
      //   257: astore 20
      //   259: aload 20
      //   261: ifnonnull +8 -> 269
      //   264: iconst_1
      //   265: istore_2
      //   266: goto +5 -> 271
      //   269: iconst_0
      //   270: istore_2
      //   271: iload 19
      //   273: ifeq +32 -> 305
      //   276: iload_2
      //   277: ifeq +28 -> 305
      //   280: aload_0
      //   281: getfield 144	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:error	Ljava/lang/Throwable;
      //   284: astore 5
      //   286: aload 5
      //   288: ifnull +12 -> 300
      //   291: aload_0
      //   292: aload 5
      //   294: invokevirtual 148	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:errorAll	(Ljava/lang/Throwable;)V
      //   297: goto +7 -> 304
      //   300: aload_0
      //   301: invokevirtual 153	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:completeAll	()V
      //   304: return
      //   305: iload_2
      //   306: ifeq +6 -> 312
      //   309: goto +197 -> 506
      //   312: aload 6
      //   314: arraylength
      //   315: istore 16
      //   317: iconst_0
      //   318: istore_2
      //   319: iconst_0
      //   320: istore 8
      //   322: iload_2
      //   323: iload 16
      //   325: if_icmpge +70 -> 395
      //   328: aload 6
      //   330: iload_2
      //   331: aaload
      //   332: astore 13
      //   334: aload 13
      //   336: invokevirtual 96	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:get	()J
      //   339: lstore 17
      //   341: lload 17
      //   343: ldc2_w 97
      //   346: lcmp
      //   347: ifeq +39 -> 386
      //   350: lload 17
      //   352: ldc2_w 131
      //   355: lcmp
      //   356: ifeq +15 -> 371
      //   359: aload 13
      //   361: aload 13
      //   363: getfield 136	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:emitted	J
      //   366: lconst_1
      //   367: ladd
      //   368: putfield 136	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:emitted	J
      //   371: aload 13
      //   373: getfield 102	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription:downstream	Lorg/reactivestreams/Subscriber;
      //   376: aload 20
      //   378: invokeinterface 156 2 0
      //   383: goto +6 -> 389
      //   386: iconst_1
      //   387: istore 8
      //   389: iinc 2 1
      //   392: goto -70 -> 322
      //   395: lload 10
      //   397: lconst_1
      //   398: lsub
      //   399: lstore 10
      //   401: iload 12
      //   403: istore_2
      //   404: iload 4
      //   406: ifeq +34 -> 440
      //   409: iinc 12 1
      //   412: iload 12
      //   414: istore_2
      //   415: iload 12
      //   417: iload_3
      //   418: if_icmpne +22 -> 440
      //   421: aload_0
      //   422: getfield 64	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   425: invokevirtual 75	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   428: checkcast 158	org/reactivestreams/Subscription
      //   431: iload_3
      //   432: i2l
      //   433: invokeinterface 162 3 0
      //   438: iconst_0
      //   439: istore_2
      //   440: aload 5
      //   442: invokevirtual 75	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   445: checkcast 76	[Lio/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription;
      //   448: astore 13
      //   450: iload 8
      //   452: ifne +19 -> 471
      //   455: aload 13
      //   457: aload 6
      //   459: if_acmpeq +6 -> 465
      //   462: goto +9 -> 471
      //   465: iload_2
      //   466: istore 12
      //   468: goto -278 -> 190
      //   471: aload 13
      //   473: astore 6
      //   475: aload_1
      //   476: astore 13
      //   478: aload 5
      //   480: astore_1
      //   481: goto +201 -> 682
      //   484: astore 5
      //   486: aload 5
      //   488: invokestatic 167	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   491: aload_0
      //   492: getfield 64	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   495: invokestatic 114	io/reactivex/internal/subscriptions/SubscriptionHelper:cancel	(Ljava/util/concurrent/atomic/AtomicReference;)Z
      //   498: pop
      //   499: aload_0
      //   500: aload 5
      //   502: invokevirtual 148	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:errorAll	(Ljava/lang/Throwable;)V
      //   505: return
      //   506: iload 12
      //   508: istore_2
      //   509: aload 5
      //   511: astore 6
      //   513: iload 8
      //   515: ifne +110 -> 625
      //   518: aload_0
      //   519: invokevirtual 140	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:isDisposed	()Z
      //   522: ifeq +10 -> 532
      //   525: aload_1
      //   526: invokeinterface 125 1 0
      //   531: return
      //   532: aload_0
      //   533: getfield 142	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:done	Z
      //   536: istore 19
      //   538: iload 19
      //   540: ifeq +28 -> 568
      //   543: aload_0
      //   544: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:delayError	Z
      //   547: ifne +21 -> 568
      //   550: aload_0
      //   551: getfield 144	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:error	Ljava/lang/Throwable;
      //   554: astore 6
      //   556: aload 6
      //   558: ifnull +10 -> 568
      //   561: aload_0
      //   562: aload 6
      //   564: invokevirtual 148	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:errorAll	(Ljava/lang/Throwable;)V
      //   567: return
      //   568: iload 12
      //   570: istore_2
      //   571: aload 5
      //   573: astore 6
      //   575: iload 19
      //   577: ifeq +48 -> 625
      //   580: iload 12
      //   582: istore_2
      //   583: aload 5
      //   585: astore 6
      //   587: aload_1
      //   588: invokeinterface 170 1 0
      //   593: ifeq +32 -> 625
      //   596: aload_0
      //   597: getfield 144	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:error	Ljava/lang/Throwable;
      //   600: astore 5
      //   602: aload 5
      //   604: ifnull +12 -> 616
      //   607: aload_0
      //   608: aload 5
      //   610: invokevirtual 148	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:errorAll	(Ljava/lang/Throwable;)V
      //   613: goto +7 -> 620
      //   616: aload_0
      //   617: invokevirtual 153	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:completeAll	()V
      //   620: return
      //   621: aload 5
      //   623: astore 6
      //   625: aload_0
      //   626: iload_2
      //   627: putfield 128	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:consumed	I
      //   630: aload_0
      //   631: getfield 59	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   634: iload 7
      //   636: ineg
      //   637: invokevirtual 174	java/util/concurrent/atomic/AtomicInteger:addAndGet	(I)I
      //   640: istore 7
      //   642: iload 7
      //   644: ifne +4 -> 648
      //   647: return
      //   648: aload_1
      //   649: astore 5
      //   651: aload_1
      //   652: ifnonnull +9 -> 661
      //   655: aload_0
      //   656: getfield 120	io/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastProcessor:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   659: astore 5
      //   661: aload 6
      //   663: invokevirtual 75	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   666: checkcast 76	[Lio/reactivex/internal/operators/flowable/FlowablePublishMulticast$MulticastSubscription;
      //   669: astore 13
      //   671: aload 6
      //   673: astore_1
      //   674: aload 13
      //   676: astore 6
      //   678: aload 5
      //   680: astore 13
      //   682: aload_1
      //   683: astore 5
      //   685: aload 13
      //   687: astore_1
      //   688: goto -626 -> 62
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	691	0	this	MulticastProcessor
      //   15	673	1	localObject1	Object
      //   20	607	2	i	int
      //   25	407	3	j	int
      //   35	370	4	k	int
      //   47	432	5	localObject2	Object
      //   484	100	5	localThrowable	Throwable
      //   600	84	5	localObject3	Object
      //   57	620	6	localObject4	Object
      //   60	583	7	m	int
      //   65	115	8	n	int
      //   194	320	8	bool1	boolean
      //   79	15	9	i1	int
      //   84	316	10	l1	long
      //   87	494	12	i2	int
      //   101	585	13	localObject5	Object
      //   114	28	14	l2	long
      //   127	199	16	i3	int
      //   131	220	17	l3	long
      //   219	357	19	bool2	boolean
      //   257	120	20	localObject6	Object
      // Exception table:
      //   from	to	target	type
      //   251	259	484	finally
    }
    
    void errorAll(Throwable paramThrowable)
    {
      for (FlowablePublishMulticast.MulticastSubscription localMulticastSubscription : (FlowablePublishMulticast.MulticastSubscription[])this.subscribers.getAndSet(TERMINATED)) {
        if (localMulticastSubscription.get() != Long.MIN_VALUE) {
          localMulticastSubscription.downstream.onError(paramThrowable);
        }
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
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
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if ((this.sourceMode == 0) && (!this.queue.offer(paramT)))
      {
        ((Subscription)this.upstream.get()).cancel();
        onError(new MissingBackpressureException());
        return;
      }
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this.upstream, paramSubscription))
      {
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(3);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            QueueDrainHelper.request(paramSubscription, this.prefetch);
            return;
          }
        }
        this.queue = QueueDrainHelper.createQueue(this.prefetch);
        QueueDrainHelper.request(paramSubscription, this.prefetch);
      }
    }
    
    void remove(FlowablePublishMulticast.MulticastSubscription<T> paramMulticastSubscription)
    {
      FlowablePublishMulticast.MulticastSubscription[] arrayOfMulticastSubscription1;
      FlowablePublishMulticast.MulticastSubscription[] arrayOfMulticastSubscription2;
      do
      {
        arrayOfMulticastSubscription1 = (FlowablePublishMulticast.MulticastSubscription[])this.subscribers.get();
        int i = arrayOfMulticastSubscription1.length;
        if (i == 0) {
          return;
        }
        int j = -1;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= i) {
            break;
          }
          if (arrayOfMulticastSubscription1[k] == paramMulticastSubscription)
          {
            m = k;
            break;
          }
        }
        if (m < 0) {
          return;
        }
        if (i == 1)
        {
          arrayOfMulticastSubscription2 = EMPTY;
        }
        else
        {
          arrayOfMulticastSubscription2 = new FlowablePublishMulticast.MulticastSubscription[i - 1];
          System.arraycopy(arrayOfMulticastSubscription1, 0, arrayOfMulticastSubscription2, 0, m);
          System.arraycopy(arrayOfMulticastSubscription1, m + 1, arrayOfMulticastSubscription2, m, i - m - 1);
        }
      } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, arrayOfMulticastSubscription2));
    }
    
    protected void subscribeActual(Subscriber<? super T> paramSubscriber)
    {
      Object localObject = new FlowablePublishMulticast.MulticastSubscription(paramSubscriber, this);
      paramSubscriber.onSubscribe((Subscription)localObject);
      if (add((FlowablePublishMulticast.MulticastSubscription)localObject))
      {
        if (((FlowablePublishMulticast.MulticastSubscription)localObject).isCancelled())
        {
          remove((FlowablePublishMulticast.MulticastSubscription)localObject);
          return;
        }
        drain();
      }
      else
      {
        localObject = this.error;
        if (localObject != null) {
          paramSubscriber.onError((Throwable)localObject);
        } else {
          paramSubscriber.onComplete();
        }
      }
    }
  }
  
  static final class MulticastSubscription<T>
    extends AtomicLong
    implements Subscription
  {
    private static final long serialVersionUID = 8664815189257569791L;
    final Subscriber<? super T> downstream;
    long emitted;
    final FlowablePublishMulticast.MulticastProcessor<T> parent;
    
    MulticastSubscription(Subscriber<? super T> paramSubscriber, FlowablePublishMulticast.MulticastProcessor<T> paramMulticastProcessor)
    {
      this.downstream = paramSubscriber;
      this.parent = paramMulticastProcessor;
    }
    
    public void cancel()
    {
      if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE)
      {
        this.parent.remove(this);
        this.parent.drain();
      }
    }
    
    public boolean isCancelled()
    {
      boolean bool;
      if (get() == Long.MIN_VALUE) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.addCancel(this, paramLong);
        this.parent.drain();
      }
    }
  }
  
  static final class OutputCanceller<R>
    implements FlowableSubscriber<R>, Subscription
  {
    final Subscriber<? super R> downstream;
    final FlowablePublishMulticast.MulticastProcessor<?> processor;
    Subscription upstream;
    
    OutputCanceller(Subscriber<? super R> paramSubscriber, FlowablePublishMulticast.MulticastProcessor<?> paramMulticastProcessor)
    {
      this.downstream = paramSubscriber;
      this.processor = paramMulticastProcessor;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      this.processor.dispose();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      this.processor.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      this.processor.dispose();
    }
    
    public void onNext(R paramR)
    {
      this.downstream.onNext(paramR);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
