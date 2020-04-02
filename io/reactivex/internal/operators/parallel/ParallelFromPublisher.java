package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFromPublisher<T>
  extends ParallelFlowable<T>
{
  final int parallelism;
  final int prefetch;
  final Publisher<? extends T> source;
  
  public ParallelFromPublisher(Publisher<? extends T> paramPublisher, int paramInt1, int paramInt2)
  {
    this.source = paramPublisher;
    this.parallelism = paramInt1;
    this.prefetch = paramInt2;
  }
  
  public int parallelism()
  {
    return this.parallelism;
  }
  
  public void subscribe(Subscriber<? super T>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    this.source.subscribe(new ParallelDispatcher(paramArrayOfSubscriber, this.prefetch));
  }
  
  static final class ParallelDispatcher<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -4470634016609963609L;
    volatile boolean cancelled;
    volatile boolean done;
    final long[] emissions;
    Throwable error;
    int index;
    final int limit;
    final int prefetch;
    int produced;
    SimpleQueue<T> queue;
    final AtomicLongArray requests;
    int sourceMode;
    final AtomicInteger subscriberCount = new AtomicInteger();
    final Subscriber<? super T>[] subscribers;
    Subscription upstream;
    
    ParallelDispatcher(Subscriber<? super T>[] paramArrayOfSubscriber, int paramInt)
    {
      this.subscribers = paramArrayOfSubscriber;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      paramInt = paramArrayOfSubscriber.length;
      int i = paramInt + paramInt;
      paramArrayOfSubscriber = new AtomicLongArray(i + 1);
      this.requests = paramArrayOfSubscriber;
      paramArrayOfSubscriber.lazySet(i, paramInt);
      this.emissions = new long[paramInt];
    }
    
    void cancel(int paramInt)
    {
      if (this.requests.decrementAndGet(paramInt) == 0L)
      {
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      if (this.sourceMode == 1) {
        drainSync();
      } else {
        drainAsync();
      }
    }
    
    /* Error */
    void drainAsync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 90	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 51	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:subscribers	[Lorg/reactivestreams/Subscriber;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 62	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:requests	Ljava/util/concurrent/atomic/AtomicLongArray;
      //   14: astore_3
      //   15: aload_0
      //   16: getfield 68	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:emissions	[J
      //   19: astore 4
      //   21: aload 4
      //   23: arraylength
      //   24: istore 5
      //   26: aload_0
      //   27: getfield 106	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:index	I
      //   30: istore 6
      //   32: aload_0
      //   33: getfield 108	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:produced	I
      //   36: istore 7
      //   38: iconst_1
      //   39: istore 8
      //   41: iconst_0
      //   42: istore 9
      //   44: iconst_0
      //   45: istore 10
      //   47: iconst_0
      //   48: istore 11
      //   50: iconst_0
      //   51: istore 12
      //   53: aload_0
      //   54: getfield 78	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:cancelled	Z
      //   57: ifeq +10 -> 67
      //   60: aload_1
      //   61: invokeinterface 95 1 0
      //   66: return
      //   67: aload_0
      //   68: getfield 110	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:done	Z
      //   71: istore 13
      //   73: iload 13
      //   75: ifeq +53 -> 128
      //   78: aload_0
      //   79: getfield 112	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:error	Ljava/lang/Throwable;
      //   82: astore 14
      //   84: aload 14
      //   86: ifnull +42 -> 128
      //   89: aload_1
      //   90: invokeinterface 95 1 0
      //   95: aload_2
      //   96: arraylength
      //   97: istore 8
      //   99: iload 11
      //   101: istore 15
      //   103: iload 15
      //   105: iload 8
      //   107: if_icmpge +20 -> 127
      //   110: aload_2
      //   111: iload 15
      //   113: aaload
      //   114: aload 14
      //   116: invokeinterface 118 2 0
      //   121: iinc 15 1
      //   124: goto -21 -> 103
      //   127: return
      //   128: aload_1
      //   129: invokeinterface 122 1 0
      //   134: istore 16
      //   136: iload 13
      //   138: ifeq +39 -> 177
      //   141: iload 16
      //   143: ifeq +34 -> 177
      //   146: aload_2
      //   147: arraylength
      //   148: istore 8
      //   150: iload 9
      //   152: istore 15
      //   154: iload 15
      //   156: iload 8
      //   158: if_icmpge +18 -> 176
      //   161: aload_2
      //   162: iload 15
      //   164: aaload
      //   165: invokeinterface 125 1 0
      //   170: iinc 15 1
      //   173: goto -19 -> 154
      //   176: return
      //   177: iload 16
      //   179: ifeq +14 -> 193
      //   182: iload 6
      //   184: istore 15
      //   186: iload 7
      //   188: istore 17
      //   190: goto +219 -> 409
      //   193: aload_3
      //   194: iload 6
      //   196: invokevirtual 128	java/util/concurrent/atomic/AtomicLongArray:get	(I)J
      //   199: lstore 18
      //   201: aload 4
      //   203: iload 6
      //   205: laload
      //   206: lstore 20
      //   208: lload 18
      //   210: lload 20
      //   212: lcmp
      //   213: ifeq +150 -> 363
      //   216: aload_3
      //   217: iload 5
      //   219: iload 6
      //   221: iadd
      //   222: invokevirtual 128	java/util/concurrent/atomic/AtomicLongArray:get	(I)J
      //   225: lconst_0
      //   226: lcmp
      //   227: ifne +136 -> 363
      //   230: aload_1
      //   231: invokeinterface 132 1 0
      //   236: astore 14
      //   238: aload 14
      //   240: ifnonnull +14 -> 254
      //   243: iload 6
      //   245: istore 15
      //   247: iload 7
      //   249: istore 17
      //   251: goto +158 -> 409
      //   254: aload_2
      //   255: iload 6
      //   257: aaload
      //   258: aload 14
      //   260: invokeinterface 136 2 0
      //   265: aload 4
      //   267: iload 6
      //   269: lload 20
      //   271: lconst_1
      //   272: ladd
      //   273: lastore
      //   274: iload 7
      //   276: iconst_1
      //   277: iadd
      //   278: istore 15
      //   280: iload 15
      //   282: istore 17
      //   284: iload 15
      //   286: aload_0
      //   287: getfield 55	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:limit	I
      //   290: if_icmpne +18 -> 308
      //   293: aload_0
      //   294: getfield 80	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:upstream	Lorg/reactivestreams/Subscription;
      //   297: iload 15
      //   299: i2l
      //   300: invokeinterface 140 3 0
      //   305: iconst_0
      //   306: istore 17
      //   308: iconst_0
      //   309: istore 22
      //   311: goto +62 -> 373
      //   314: astore 4
      //   316: aload 4
      //   318: invokestatic 145	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   321: aload_0
      //   322: getfield 80	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:upstream	Lorg/reactivestreams/Subscription;
      //   325: invokeinterface 84 1 0
      //   330: aload_2
      //   331: arraylength
      //   332: istore 8
      //   334: iload 10
      //   336: istore 15
      //   338: iload 15
      //   340: iload 8
      //   342: if_icmpge +20 -> 362
      //   345: aload_2
      //   346: iload 15
      //   348: aaload
      //   349: aload 4
      //   351: invokeinterface 118 2 0
      //   356: iinc 15 1
      //   359: goto -21 -> 338
      //   362: return
      //   363: iload 12
      //   365: iconst_1
      //   366: iadd
      //   367: istore 22
      //   369: iload 7
      //   371: istore 17
      //   373: iinc 6 1
      //   376: iload 6
      //   378: istore 15
      //   380: iload 6
      //   382: iload 5
      //   384: if_icmpne +6 -> 390
      //   387: iconst_0
      //   388: istore 15
      //   390: iload 15
      //   392: istore 6
      //   394: iload 17
      //   396: istore 7
      //   398: iload 22
      //   400: istore 12
      //   402: iload 22
      //   404: iload 5
      //   406: if_icmpne -353 -> 53
      //   409: aload_0
      //   410: invokevirtual 147	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:get	()I
      //   413: istore 6
      //   415: iload 6
      //   417: iload 8
      //   419: if_icmpne +42 -> 461
      //   422: aload_0
      //   423: iload 15
      //   425: putfield 106	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:index	I
      //   428: aload_0
      //   429: iload 17
      //   431: putfield 108	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:produced	I
      //   434: aload_0
      //   435: iload 8
      //   437: ineg
      //   438: invokevirtual 151	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:addAndGet	(I)I
      //   441: istore 22
      //   443: iload 15
      //   445: istore 6
      //   447: iload 17
      //   449: istore 7
      //   451: iload 22
      //   453: istore 8
      //   455: iload 22
      //   457: ifne -416 -> 41
      //   460: return
      //   461: iload 6
      //   463: istore 8
      //   465: iload 15
      //   467: istore 6
      //   469: iload 17
      //   471: istore 7
      //   473: goto -432 -> 41
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	476	0	this	ParallelDispatcher
      //   4	227	1	localSimpleQueue	SimpleQueue
      //   9	337	2	arrayOfSubscriber	Subscriber[]
      //   14	203	3	localAtomicLongArray	AtomicLongArray
      //   19	247	4	arrayOfLong	long[]
      //   314	36	4	localThrowable	Throwable
      //   24	383	5	i	int
      //   30	438	6	j	int
      //   36	436	7	k	int
      //   39	425	8	m	int
      //   42	109	9	n	int
      //   45	290	10	i1	int
      //   48	52	11	i2	int
      //   51	350	12	i3	int
      //   71	66	13	bool1	boolean
      //   82	177	14	localObject	Object
      //   101	365	15	i4	int
      //   134	44	16	bool2	boolean
      //   188	282	17	i5	int
      //   199	10	18	l1	long
      //   206	64	20	l2	long
      //   309	147	22	i6	int
      // Exception table:
      //   from	to	target	type
      //   230	238	314	finally
    }
    
    /* Error */
    void drainSync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 90	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 51	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:subscribers	[Lorg/reactivestreams/Subscriber;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 62	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:requests	Ljava/util/concurrent/atomic/AtomicLongArray;
      //   14: astore_3
      //   15: aload_0
      //   16: getfield 68	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:emissions	[J
      //   19: astore 4
      //   21: aload 4
      //   23: arraylength
      //   24: istore 5
      //   26: aload_0
      //   27: getfield 106	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:index	I
      //   30: istore 6
      //   32: iconst_1
      //   33: istore 7
      //   35: iconst_0
      //   36: istore 8
      //   38: iconst_0
      //   39: istore 9
      //   41: iconst_0
      //   42: istore 10
      //   44: iconst_0
      //   45: istore 11
      //   47: iload 6
      //   49: istore 12
      //   51: aload_0
      //   52: getfield 78	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:cancelled	Z
      //   55: ifeq +10 -> 65
      //   58: aload_1
      //   59: invokeinterface 95 1 0
      //   64: return
      //   65: aload_1
      //   66: invokeinterface 122 1 0
      //   71: ifeq +34 -> 105
      //   74: aload_2
      //   75: arraylength
      //   76: istore 7
      //   78: iload 10
      //   80: istore 6
      //   82: iload 6
      //   84: iload 7
      //   86: if_icmpge +18 -> 104
      //   89: aload_2
      //   90: iload 6
      //   92: aaload
      //   93: invokeinterface 125 1 0
      //   98: iinc 6 1
      //   101: goto -19 -> 82
      //   104: return
      //   105: aload_3
      //   106: iload 12
      //   108: invokevirtual 128	java/util/concurrent/atomic/AtomicLongArray:get	(I)J
      //   111: lstore 13
      //   113: aload 4
      //   115: iload 12
      //   117: laload
      //   118: lstore 15
      //   120: lload 13
      //   122: lload 15
      //   124: lcmp
      //   125: ifeq +133 -> 258
      //   128: aload_3
      //   129: iload 5
      //   131: iload 12
      //   133: iadd
      //   134: invokevirtual 128	java/util/concurrent/atomic/AtomicLongArray:get	(I)J
      //   137: lconst_0
      //   138: lcmp
      //   139: ifne +119 -> 258
      //   142: aload_1
      //   143: invokeinterface 132 1 0
      //   148: astore 17
      //   150: aload 17
      //   152: ifnonnull +34 -> 186
      //   155: aload_2
      //   156: arraylength
      //   157: istore 7
      //   159: iload 8
      //   161: istore 6
      //   163: iload 6
      //   165: iload 7
      //   167: if_icmpge +18 -> 185
      //   170: aload_2
      //   171: iload 6
      //   173: aaload
      //   174: invokeinterface 125 1 0
      //   179: iinc 6 1
      //   182: goto -19 -> 163
      //   185: return
      //   186: aload_2
      //   187: iload 12
      //   189: aaload
      //   190: aload 17
      //   192: invokeinterface 136 2 0
      //   197: aload 4
      //   199: iload 12
      //   201: lload 15
      //   203: lconst_1
      //   204: ladd
      //   205: lastore
      //   206: iconst_0
      //   207: istore 18
      //   209: goto +55 -> 264
      //   212: astore_3
      //   213: aload_3
      //   214: invokestatic 145	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   217: aload_0
      //   218: getfield 80	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:upstream	Lorg/reactivestreams/Subscription;
      //   221: invokeinterface 84 1 0
      //   226: aload_2
      //   227: arraylength
      //   228: istore 7
      //   230: iload 9
      //   232: istore 6
      //   234: iload 6
      //   236: iload 7
      //   238: if_icmpge +19 -> 257
      //   241: aload_2
      //   242: iload 6
      //   244: aaload
      //   245: aload_3
      //   246: invokeinterface 118 2 0
      //   251: iinc 6 1
      //   254: goto -20 -> 234
      //   257: return
      //   258: iload 11
      //   260: iconst_1
      //   261: iadd
      //   262: istore 18
      //   264: iinc 12 1
      //   267: iload 12
      //   269: istore 6
      //   271: iload 12
      //   273: iload 5
      //   275: if_icmpne +6 -> 281
      //   278: iconst_0
      //   279: istore 6
      //   281: iload 6
      //   283: istore 12
      //   285: iload 18
      //   287: istore 11
      //   289: iload 18
      //   291: iload 5
      //   293: if_icmpne -242 -> 51
      //   296: aload_0
      //   297: invokevirtual 147	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:get	()I
      //   300: istore 18
      //   302: iload 18
      //   304: iload 7
      //   306: if_icmpne +28 -> 334
      //   309: aload_0
      //   310: iload 6
      //   312: putfield 106	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:index	I
      //   315: aload_0
      //   316: iload 7
      //   318: ineg
      //   319: invokevirtual 151	io/reactivex/internal/operators/parallel/ParallelFromPublisher$ParallelDispatcher:addAndGet	(I)I
      //   322: istore 18
      //   324: iload 18
      //   326: istore 7
      //   328: iload 18
      //   330: ifne -295 -> 35
      //   333: return
      //   334: iload 18
      //   336: istore 7
      //   338: goto -303 -> 35
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	341	0	this	ParallelDispatcher
      //   4	139	1	localSimpleQueue	SimpleQueue
      //   9	233	2	arrayOfSubscriber	Subscriber[]
      //   14	115	3	localAtomicLongArray	AtomicLongArray
      //   212	34	3	localThrowable	Throwable
      //   19	179	4	arrayOfLong	long[]
      //   24	270	5	i	int
      //   30	281	6	j	int
      //   33	304	7	k	int
      //   36	124	8	m	int
      //   39	192	9	n	int
      //   42	37	10	i1	int
      //   45	243	11	i2	int
      //   49	235	12	i3	int
      //   111	10	13	l1	long
      //   118	84	15	l2	long
      //   148	43	17	localObject	Object
      //   207	128	18	i4	int
      // Exception table:
      //   from	to	target	type
      //   142	150	212	finally
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if ((this.sourceMode == 0) && (!this.queue.offer(paramT)))
      {
        this.upstream.cancel();
        onError(new MissingBackpressureException("Queue is full?"));
        return;
      }
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(7);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            setupSubscribers();
            drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            setupSubscribers();
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        setupSubscribers();
        paramSubscription.request(this.prefetch);
      }
    }
    
    void setupSubscribers()
    {
      Subscriber[] arrayOfSubscriber = this.subscribers;
      int i = arrayOfSubscriber.length;
      int k;
      for (int j = 0; j < i; j = k)
      {
        if (this.cancelled) {
          return;
        }
        AtomicInteger localAtomicInteger = this.subscriberCount;
        k = j + 1;
        localAtomicInteger.lazySet(k);
        arrayOfSubscriber[j].onSubscribe(new RailSubscription(j, i));
      }
    }
    
    final class RailSubscription
      implements Subscription
    {
      final int j;
      final int m;
      
      RailSubscription(int paramInt1, int paramInt2)
      {
        this.j = paramInt1;
        this.m = paramInt2;
      }
      
      public void cancel()
      {
        Object localObject = ParallelFromPublisher.ParallelDispatcher.this.requests;
        int i = this.m;
        if (((AtomicLongArray)localObject).compareAndSet(this.j + i, 0L, 1L))
        {
          localObject = ParallelFromPublisher.ParallelDispatcher.this;
          i = this.m;
          ((ParallelFromPublisher.ParallelDispatcher)localObject).cancel(i + i);
        }
      }
      
      public void request(long paramLong)
      {
        if (SubscriptionHelper.validate(paramLong))
        {
          AtomicLongArray localAtomicLongArray = ParallelFromPublisher.ParallelDispatcher.this.requests;
          long l1;
          long l2;
          do
          {
            l1 = localAtomicLongArray.get(this.j);
            if (l1 == Long.MAX_VALUE) {
              return;
            }
            l2 = BackpressureHelper.addCap(l1, paramLong);
          } while (!localAtomicLongArray.compareAndSet(this.j, l1, l2));
          if (ParallelFromPublisher.ParallelDispatcher.this.subscriberCount.get() == this.m) {
            ParallelFromPublisher.ParallelDispatcher.this.drain();
          }
        }
      }
    }
  }
}
