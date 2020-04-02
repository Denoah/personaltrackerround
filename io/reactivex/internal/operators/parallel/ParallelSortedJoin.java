package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelSortedJoin<T>
  extends Flowable<T>
{
  final Comparator<? super T> comparator;
  final ParallelFlowable<List<T>> source;
  
  public ParallelSortedJoin(ParallelFlowable<List<T>> paramParallelFlowable, Comparator<? super T> paramComparator)
  {
    this.source = paramParallelFlowable;
    this.comparator = paramComparator;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SortedJoinSubscription localSortedJoinSubscription = new SortedJoinSubscription(paramSubscriber, this.source.parallelism(), this.comparator);
    paramSubscriber.onSubscribe(localSortedJoinSubscription);
    this.source.subscribe(localSortedJoinSubscription.subscribers);
  }
  
  static final class SortedJoinInnerSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<List<T>>
  {
    private static final long serialVersionUID = 6751017204873808094L;
    final int index;
    final ParallelSortedJoin.SortedJoinSubscription<T> parent;
    
    SortedJoinInnerSubscriber(ParallelSortedJoin.SortedJoinSubscription<T> paramSortedJoinSubscription, int paramInt)
    {
      this.parent = paramSortedJoinSubscription;
      this.index = paramInt;
    }
    
    void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(List<T> paramList)
    {
      this.parent.innerNext(paramList, this.index);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
  
  static final class SortedJoinSubscription<T>
    extends AtomicInteger
    implements Subscription
  {
    private static final long serialVersionUID = 3481980673745556697L;
    volatile boolean cancelled;
    final Comparator<? super T> comparator;
    final Subscriber<? super T> downstream;
    final AtomicReference<Throwable> error = new AtomicReference();
    final int[] indexes;
    final List<T>[] lists;
    final AtomicInteger remaining = new AtomicInteger();
    final AtomicLong requested = new AtomicLong();
    final ParallelSortedJoin.SortedJoinInnerSubscriber<T>[] subscribers;
    
    SortedJoinSubscription(Subscriber<? super T> paramSubscriber, int paramInt, Comparator<? super T> paramComparator)
    {
      this.downstream = paramSubscriber;
      this.comparator = paramComparator;
      paramSubscriber = new ParallelSortedJoin.SortedJoinInnerSubscriber[paramInt];
      for (int i = 0; i < paramInt; i++) {
        paramSubscriber[i] = new ParallelSortedJoin.SortedJoinInnerSubscriber(this, i);
      }
      this.subscribers = paramSubscriber;
      this.lists = new List[paramInt];
      this.indexes = new int[paramInt];
      this.remaining.lazySet(paramInt);
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelAll();
        if (getAndIncrement() == 0) {
          Arrays.fill(this.lists, null);
        }
      }
    }
    
    void cancelAll()
    {
      ParallelSortedJoin.SortedJoinInnerSubscriber[] arrayOfSortedJoinInnerSubscriber = this.subscribers;
      int i = arrayOfSortedJoinInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfSortedJoinInnerSubscriber[j].cancel();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 88	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 56	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:downstream	Lorg/reactivestreams/Subscriber;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 69	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:lists	[Ljava/util/List;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 71	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:indexes	[I
      //   22: astore_3
      //   23: aload_3
      //   24: arraylength
      //   25: istore 4
      //   27: iconst_1
      //   28: istore 5
      //   30: aload_0
      //   31: getfield 47	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   34: invokevirtual 101	java/util/concurrent/atomic/AtomicLong:get	()J
      //   37: lstore 6
      //   39: lconst_0
      //   40: lstore 8
      //   42: lload 8
      //   44: lload 6
      //   46: lcmp
      //   47: istore 10
      //   49: iload 10
      //   51: ifeq +295 -> 346
      //   54: aload_0
      //   55: getfield 81	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:cancelled	Z
      //   58: ifeq +9 -> 67
      //   61: aload_2
      //   62: aconst_null
      //   63: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   66: return
      //   67: aload_0
      //   68: getfield 54	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   71: invokevirtual 104	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   74: checkcast 106	java/lang/Throwable
      //   77: astore 11
      //   79: aload 11
      //   81: ifnull +21 -> 102
      //   84: aload_0
      //   85: invokevirtual 84	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:cancelAll	()V
      //   88: aload_2
      //   89: aconst_null
      //   90: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   93: aload_1
      //   94: aload 11
      //   96: invokeinterface 112 2 0
      //   101: return
      //   102: iconst_m1
      //   103: istore 12
      //   105: aconst_null
      //   106: astore 11
      //   108: iconst_0
      //   109: istore 10
      //   111: iload 10
      //   113: iload 4
      //   115: if_icmpge +187 -> 302
      //   118: aload_2
      //   119: iload 10
      //   121: aaload
      //   122: astore 13
      //   124: aload_3
      //   125: iload 10
      //   127: iaload
      //   128: istore 14
      //   130: aload 11
      //   132: astore 15
      //   134: iload 12
      //   136: istore 16
      //   138: aload 13
      //   140: invokeinterface 115 1 0
      //   145: iload 14
      //   147: if_icmpeq +141 -> 288
      //   150: aload 11
      //   152: ifnonnull +25 -> 177
      //   155: aload 13
      //   157: iload 14
      //   159: invokeinterface 118 2 0
      //   164: astore 11
      //   166: iload 10
      //   168: istore 16
      //   170: aload 11
      //   172: astore 15
      //   174: goto +114 -> 288
      //   177: aload 13
      //   179: iload 14
      //   181: invokeinterface 118 2 0
      //   186: astore 13
      //   188: aload_0
      //   189: getfield 58	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:comparator	Ljava/util/Comparator;
      //   192: aload 11
      //   194: aload 13
      //   196: invokeinterface 124 3 0
      //   201: istore 16
      //   203: iload 16
      //   205: ifle +9 -> 214
      //   208: iconst_1
      //   209: istore 14
      //   211: goto +6 -> 217
      //   214: iconst_0
      //   215: istore 14
      //   217: aload 11
      //   219: astore 15
      //   221: iload 12
      //   223: istore 16
      //   225: iload 14
      //   227: ifeq +61 -> 288
      //   230: aload 13
      //   232: astore 11
      //   234: goto -68 -> 166
      //   237: astore 11
      //   239: aload 11
      //   241: invokestatic 129	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   244: aload_0
      //   245: invokevirtual 84	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:cancelAll	()V
      //   248: aload_2
      //   249: aconst_null
      //   250: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   253: aload_0
      //   254: getfield 54	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   257: aconst_null
      //   258: aload 11
      //   260: invokevirtual 133	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   263: ifne +8 -> 271
      //   266: aload 11
      //   268: invokestatic 136	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   271: aload_1
      //   272: aload_0
      //   273: getfield 54	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   276: invokevirtual 104	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   279: checkcast 106	java/lang/Throwable
      //   282: invokeinterface 112 2 0
      //   287: return
      //   288: iinc 10 1
      //   291: aload 15
      //   293: astore 11
      //   295: iload 16
      //   297: istore 12
      //   299: goto -188 -> 111
      //   302: aload 11
      //   304: ifnonnull +15 -> 319
      //   307: aload_2
      //   308: aconst_null
      //   309: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   312: aload_1
      //   313: invokeinterface 139 1 0
      //   318: return
      //   319: aload_1
      //   320: aload 11
      //   322: invokeinterface 143 2 0
      //   327: aload_3
      //   328: iload 12
      //   330: aload_3
      //   331: iload 12
      //   333: iaload
      //   334: iconst_1
      //   335: iadd
      //   336: iastore
      //   337: lload 8
      //   339: lconst_1
      //   340: ladd
      //   341: lstore 8
      //   343: goto -301 -> 42
      //   346: iload 10
      //   348: ifne +109 -> 457
      //   351: aload_0
      //   352: getfield 81	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:cancelled	Z
      //   355: ifeq +9 -> 364
      //   358: aload_2
      //   359: aconst_null
      //   360: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   363: return
      //   364: aload_0
      //   365: getfield 54	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   368: invokevirtual 104	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   371: checkcast 106	java/lang/Throwable
      //   374: astore 11
      //   376: aload 11
      //   378: ifnull +21 -> 399
      //   381: aload_0
      //   382: invokevirtual 84	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:cancelAll	()V
      //   385: aload_2
      //   386: aconst_null
      //   387: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   390: aload_1
      //   391: aload 11
      //   393: invokeinterface 112 2 0
      //   398: return
      //   399: iconst_0
      //   400: istore 10
      //   402: iload 10
      //   404: iload 4
      //   406: if_icmpge +31 -> 437
      //   409: aload_3
      //   410: iload 10
      //   412: iaload
      //   413: aload_2
      //   414: iload 10
      //   416: aaload
      //   417: invokeinterface 115 1 0
      //   422: if_icmpeq +9 -> 431
      //   425: iconst_0
      //   426: istore 10
      //   428: goto +12 -> 440
      //   431: iinc 10 1
      //   434: goto -32 -> 402
      //   437: iconst_1
      //   438: istore 10
      //   440: iload 10
      //   442: ifeq +15 -> 457
      //   445: aload_2
      //   446: aconst_null
      //   447: invokestatic 94	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   450: aload_1
      //   451: invokeinterface 139 1 0
      //   456: return
      //   457: lload 8
      //   459: lconst_0
      //   460: lcmp
      //   461: ifeq +23 -> 484
      //   464: lload 6
      //   466: ldc2_w 144
      //   469: lcmp
      //   470: ifeq +14 -> 484
      //   473: aload_0
      //   474: getfield 47	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   477: lload 8
      //   479: lneg
      //   480: invokevirtual 149	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   483: pop2
      //   484: aload_0
      //   485: invokevirtual 151	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:get	()I
      //   488: istore 12
      //   490: iload 12
      //   492: istore 10
      //   494: iload 12
      //   496: iload 5
      //   498: if_icmpne +22 -> 520
      //   501: aload_0
      //   502: iload 5
      //   504: ineg
      //   505: invokevirtual 154	io/reactivex/internal/operators/parallel/ParallelSortedJoin$SortedJoinSubscription:addAndGet	(I)I
      //   508: istore 5
      //   510: iload 5
      //   512: istore 10
      //   514: iload 5
      //   516: ifne +4 -> 520
      //   519: return
      //   520: iload 10
      //   522: istore 5
      //   524: goto -494 -> 30
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	527	0	this	SortedJoinSubscription
      //   12	439	1	localSubscriber	Subscriber
      //   17	429	2	arrayOfList	List[]
      //   22	388	3	arrayOfInt	int[]
      //   25	382	4	i	int
      //   28	495	5	j	int
      //   37	428	6	l1	long
      //   40	438	8	l2	long
      //   47	300	10	k	int
      //   400	15	10	m	int
      //   426	95	10	n	int
      //   77	156	11	localObject1	Object
      //   237	30	11	localThrowable	Throwable
      //   293	99	11	localObject2	Object
      //   103	396	12	i1	int
      //   122	109	13	localObject3	Object
      //   128	98	14	i2	int
      //   132	160	15	localObject4	Object
      //   136	160	16	i3	int
      // Exception table:
      //   from	to	target	type
      //   188	203	237	finally
    }
    
    void innerError(Throwable paramThrowable)
    {
      if (this.error.compareAndSet(null, paramThrowable)) {
        drain();
      } else if (paramThrowable != this.error.get()) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerNext(List<T> paramList, int paramInt)
    {
      this.lists[paramInt] = paramList;
      if (this.remaining.decrementAndGet() == 0) {
        drain();
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        if (this.remaining.get() == 0) {
          drain();
        }
      }
    }
  }
}
