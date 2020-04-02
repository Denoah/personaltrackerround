package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZip<T, R>
  extends Flowable<R>
{
  final int bufferSize;
  final boolean delayError;
  final Publisher<? extends T>[] sources;
  final Iterable<? extends Publisher<? extends T>> sourcesIterable;
  final Function<? super Object[], ? extends R> zipper;
  
  public FlowableZip(Publisher<? extends T>[] paramArrayOfPublisher, Iterable<? extends Publisher<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
  {
    this.sources = paramArrayOfPublisher;
    this.sourcesIterable = paramIterable;
    this.zipper = paramFunction;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    Object localObject1 = this.sources;
    if (localObject1 == null)
    {
      localObject2 = new Publisher[8];
      Iterator localIterator = this.sourcesIterable.iterator();
      int i = 0;
      for (;;)
      {
        localObject1 = localObject2;
        j = i;
        if (!localIterator.hasNext()) {
          break;
        }
        Publisher localPublisher = (Publisher)localIterator.next();
        localObject1 = localObject2;
        if (i == localObject2.length)
        {
          localObject1 = new Publisher[(i >> 2) + i];
          System.arraycopy(localObject2, 0, localObject1, 0, i);
        }
        localObject1[i] = localPublisher;
        i++;
        localObject2 = localObject1;
      }
    }
    int j = localObject1.length;
    if (j == 0)
    {
      EmptySubscription.complete(paramSubscriber);
      return;
    }
    Object localObject2 = new ZipCoordinator(paramSubscriber, this.zipper, j, this.bufferSize, this.delayError);
    paramSubscriber.onSubscribe((Subscription)localObject2);
    ((ZipCoordinator)localObject2).subscribe((Publisher[])localObject1, j);
  }
  
  static final class ZipCoordinator<T, R>
    extends AtomicInteger
    implements Subscription
  {
    private static final long serialVersionUID = -2434867452883857743L;
    volatile boolean cancelled;
    final Object[] current;
    final boolean delayErrors;
    final Subscriber<? super R> downstream;
    final AtomicThrowable errors;
    final AtomicLong requested;
    final FlowableZip.ZipSubscriber<T, R>[] subscribers;
    final Function<? super Object[], ? extends R> zipper;
    
    ZipCoordinator(Subscriber<? super R> paramSubscriber, Function<? super Object[], ? extends R> paramFunction, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.zipper = paramFunction;
      this.delayErrors = paramBoolean;
      paramSubscriber = new FlowableZip.ZipSubscriber[paramInt1];
      for (int i = 0; i < paramInt1; i++) {
        paramSubscriber[i] = new FlowableZip.ZipSubscriber(this, paramInt2);
      }
      this.current = new Object[paramInt1];
      this.subscribers = paramSubscriber;
      this.requested = new AtomicLong();
      this.errors = new AtomicThrowable();
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelAll();
      }
    }
    
    void cancelAll()
    {
      FlowableZip.ZipSubscriber[] arrayOfZipSubscriber = this.subscribers;
      int i = arrayOfZipSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfZipSubscriber[j].cancel();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 80	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 39	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 54	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:subscribers	[Lio/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber;
      //   17: astore_2
      //   18: aload_2
      //   19: arraylength
      //   20: istore_3
      //   21: aload_0
      //   22: getfield 52	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:current	[Ljava/lang/Object;
      //   25: astore 4
      //   27: iconst_1
      //   28: istore 5
      //   30: aload_0
      //   31: getfield 59	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   34: invokevirtual 84	java/util/concurrent/atomic/AtomicLong:get	()J
      //   37: lstore 6
      //   39: lconst_0
      //   40: lstore 8
      //   42: lload 6
      //   44: lload 8
      //   46: lcmp
      //   47: istore 10
      //   49: iload 10
      //   51: ifeq +336 -> 387
      //   54: aload_0
      //   55: getfield 70	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelled	Z
      //   58: ifeq +4 -> 62
      //   61: return
      //   62: aload_0
      //   63: getfield 43	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:delayErrors	Z
      //   66: ifne +31 -> 97
      //   69: aload_0
      //   70: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   73: invokevirtual 87	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   76: ifnull +21 -> 97
      //   79: aload_0
      //   80: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   83: aload_1
      //   84: aload_0
      //   85: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   88: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   91: invokeinterface 97 2 0
      //   96: return
      //   97: iconst_0
      //   98: istore 11
      //   100: iload 11
      //   102: istore 12
      //   104: iload 12
      //   106: iload_3
      //   107: if_icmpge +193 -> 300
      //   110: aload_2
      //   111: iload 12
      //   113: aaload
      //   114: astore 13
      //   116: iload 11
      //   118: istore 14
      //   120: aload 4
      //   122: iload 12
      //   124: aaload
      //   125: ifnonnull +165 -> 290
      //   128: aload 13
      //   130: getfield 100	io/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber:done	Z
      //   133: istore 15
      //   135: aload 13
      //   137: getfield 104	io/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   140: astore 13
      //   142: aload 13
      //   144: ifnull +15 -> 159
      //   147: aload 13
      //   149: invokeinterface 109 1 0
      //   154: astore 13
      //   156: goto +6 -> 162
      //   159: aconst_null
      //   160: astore 13
      //   162: aload 13
      //   164: ifnonnull +9 -> 173
      //   167: iconst_1
      //   168: istore 14
      //   170: goto +6 -> 176
      //   173: iconst_0
      //   174: istore 14
      //   176: iload 15
      //   178: ifeq +48 -> 226
      //   181: iload 14
      //   183: ifeq +43 -> 226
      //   186: aload_0
      //   187: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   190: aload_0
      //   191: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   194: invokevirtual 87	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   197: checkcast 111	java/lang/Throwable
      //   200: ifnull +19 -> 219
      //   203: aload_1
      //   204: aload_0
      //   205: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   208: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   211: invokeinterface 97 2 0
      //   216: goto +9 -> 225
      //   219: aload_1
      //   220: invokeinterface 114 1 0
      //   225: return
      //   226: iload 14
      //   228: ifne +59 -> 287
      //   231: aload 4
      //   233: iload 12
      //   235: aload 13
      //   237: aastore
      //   238: iload 11
      //   240: istore 14
      //   242: goto +48 -> 290
      //   245: astore 13
      //   247: aload 13
      //   249: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   252: aload_0
      //   253: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   256: aload 13
      //   258: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   261: pop
      //   262: aload_0
      //   263: getfield 43	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:delayErrors	Z
      //   266: ifne +21 -> 287
      //   269: aload_0
      //   270: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   273: aload_1
      //   274: aload_0
      //   275: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   278: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   281: invokeinterface 97 2 0
      //   286: return
      //   287: iconst_1
      //   288: istore 14
      //   290: iinc 12 1
      //   293: iload 14
      //   295: istore 11
      //   297: goto -193 -> 104
      //   300: iload 11
      //   302: ifeq +6 -> 308
      //   305: goto +82 -> 387
      //   308: aload_0
      //   309: getfield 41	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:zipper	Lio/reactivex/functions/Function;
      //   312: aload 4
      //   314: invokevirtual 127	[Ljava/lang/Object;:clone	()Ljava/lang/Object;
      //   317: invokeinterface 133 2 0
      //   322: ldc -121
      //   324: invokestatic 141	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   327: astore 13
      //   329: aload_1
      //   330: aload 13
      //   332: invokeinterface 145 2 0
      //   337: lload 8
      //   339: lconst_1
      //   340: ladd
      //   341: lstore 8
      //   343: aload 4
      //   345: aconst_null
      //   346: invokestatic 151	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   349: goto -307 -> 42
      //   352: astore 13
      //   354: aload 13
      //   356: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   359: aload_0
      //   360: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   363: aload_0
      //   364: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   367: aload 13
      //   369: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   372: pop
      //   373: aload_1
      //   374: aload_0
      //   375: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   378: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   381: invokeinterface 97 2 0
      //   386: return
      //   387: iload 10
      //   389: ifne +230 -> 619
      //   392: aload_0
      //   393: getfield 70	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelled	Z
      //   396: ifeq +4 -> 400
      //   399: return
      //   400: aload_0
      //   401: getfield 43	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:delayErrors	Z
      //   404: ifne +31 -> 435
      //   407: aload_0
      //   408: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   411: invokevirtual 87	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   414: ifnull +21 -> 435
      //   417: aload_0
      //   418: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   421: aload_1
      //   422: aload_0
      //   423: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   426: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   429: invokeinterface 97 2 0
      //   434: return
      //   435: iconst_0
      //   436: istore 12
      //   438: iload 12
      //   440: iload_3
      //   441: if_icmpge +178 -> 619
      //   444: aload_2
      //   445: iload 12
      //   447: aaload
      //   448: astore 13
      //   450: aload 4
      //   452: iload 12
      //   454: aaload
      //   455: ifnonnull +158 -> 613
      //   458: aload 13
      //   460: getfield 100	io/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber:done	Z
      //   463: istore 15
      //   465: aload 13
      //   467: getfield 104	io/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   470: astore 13
      //   472: aload 13
      //   474: ifnull +15 -> 489
      //   477: aload 13
      //   479: invokeinterface 109 1 0
      //   484: astore 13
      //   486: goto +6 -> 492
      //   489: aconst_null
      //   490: astore 13
      //   492: aload 13
      //   494: ifnonnull +9 -> 503
      //   497: iconst_1
      //   498: istore 11
      //   500: goto +6 -> 506
      //   503: iconst_0
      //   504: istore 11
      //   506: iload 15
      //   508: ifeq +48 -> 556
      //   511: iload 11
      //   513: ifeq +43 -> 556
      //   516: aload_0
      //   517: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   520: aload_0
      //   521: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   524: invokevirtual 87	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   527: checkcast 111	java/lang/Throwable
      //   530: ifnull +19 -> 549
      //   533: aload_1
      //   534: aload_0
      //   535: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   538: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   541: invokeinterface 97 2 0
      //   546: goto +9 -> 555
      //   549: aload_1
      //   550: invokeinterface 114 1 0
      //   555: return
      //   556: iload 11
      //   558: ifne +55 -> 613
      //   561: aload 4
      //   563: iload 12
      //   565: aload 13
      //   567: aastore
      //   568: goto +45 -> 613
      //   571: astore 13
      //   573: aload 13
      //   575: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   578: aload_0
      //   579: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   582: aload 13
      //   584: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   587: pop
      //   588: aload_0
      //   589: getfield 43	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:delayErrors	Z
      //   592: ifne +21 -> 613
      //   595: aload_0
      //   596: invokevirtual 73	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:cancelAll	()V
      //   599: aload_1
      //   600: aload_0
      //   601: getfield 64	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   604: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   607: invokeinterface 97 2 0
      //   612: return
      //   613: iinc 12 1
      //   616: goto -178 -> 438
      //   619: lload 8
      //   621: lconst_0
      //   622: lcmp
      //   623: ifeq +52 -> 675
      //   626: aload_2
      //   627: arraylength
      //   628: istore 11
      //   630: iconst_0
      //   631: istore 12
      //   633: iload 12
      //   635: iload 11
      //   637: if_icmpge +18 -> 655
      //   640: aload_2
      //   641: iload 12
      //   643: aaload
      //   644: lload 8
      //   646: invokevirtual 155	io/reactivex/internal/operators/flowable/FlowableZip$ZipSubscriber:request	(J)V
      //   649: iinc 12 1
      //   652: goto -19 -> 633
      //   655: lload 6
      //   657: ldc2_w 156
      //   660: lcmp
      //   661: ifeq +14 -> 675
      //   664: aload_0
      //   665: getfield 59	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   668: lload 8
      //   670: lneg
      //   671: invokevirtual 161	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   674: pop2
      //   675: aload_0
      //   676: iload 5
      //   678: ineg
      //   679: invokevirtual 164	io/reactivex/internal/operators/flowable/FlowableZip$ZipCoordinator:addAndGet	(I)I
      //   682: istore 12
      //   684: iload 12
      //   686: istore 5
      //   688: iload 12
      //   690: ifne -660 -> 30
      //   693: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	694	0	this	ZipCoordinator
      //   12	588	1	localSubscriber	Subscriber
      //   17	624	2	arrayOfZipSubscriber	FlowableZip.ZipSubscriber[]
      //   20	422	3	i	int
      //   25	537	4	arrayOfObject	Object[]
      //   28	659	5	j	int
      //   37	619	6	l1	long
      //   40	629	8	l2	long
      //   47	341	10	bool1	boolean
      //   98	540	11	k	int
      //   102	587	12	m	int
      //   114	122	13	localObject1	Object
      //   245	12	13	localThrowable1	Throwable
      //   327	4	13	localObject2	Object
      //   352	16	13	localThrowable2	Throwable
      //   448	118	13	localObject3	Object
      //   571	12	13	localThrowable3	Throwable
      //   118	176	14	n	int
      //   133	374	15	bool2	boolean
      // Exception table:
      //   from	to	target	type
      //   128	142	245	finally
      //   147	156	245	finally
      //   186	216	245	finally
      //   219	225	245	finally
      //   308	329	352	finally
      //   458	472	571	finally
      //   477	486	571	finally
      //   516	546	571	finally
      //   549	555	571	finally
    }
    
    void error(FlowableZip.ZipSubscriber<T, R> paramZipSubscriber, Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        paramZipSubscriber.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    void subscribe(Publisher<? extends T>[] paramArrayOfPublisher, int paramInt)
    {
      FlowableZip.ZipSubscriber[] arrayOfZipSubscriber = this.subscribers;
      for (int i = 0; (i < paramInt) && (!this.cancelled) && ((this.delayErrors) || (this.errors.get() == null)); i++) {
        paramArrayOfPublisher[i].subscribe(arrayOfZipSubscriber[i]);
      }
    }
  }
  
  static final class ZipSubscriber<T, R>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -4627193790118206028L;
    volatile boolean done;
    final int limit;
    final FlowableZip.ZipCoordinator<T, R> parent;
    final int prefetch;
    long produced;
    SimpleQueue<T> queue;
    int sourceMode;
    
    ZipSubscriber(FlowableZip.ZipCoordinator<T, R> paramZipCoordinator, int paramInt)
    {
      this.parent = paramZipCoordinator;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.error(this, paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.sourceMode != 2) {
        this.queue.offer(paramT);
      }
      this.parent.drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this, paramSubscription))
      {
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(7);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            this.parent.drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public void request(long paramLong)
    {
      if (this.sourceMode != 1)
      {
        paramLong = this.produced + paramLong;
        if (paramLong >= this.limit)
        {
          this.produced = 0L;
          ((Subscription)get()).request(paramLong);
        }
        else
        {
          this.produced = paramLong;
        }
      }
    }
  }
}
