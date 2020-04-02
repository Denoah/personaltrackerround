package io.reactivex.processors;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport("none")
public final class MulticastProcessor<T>
  extends FlowableProcessor<T>
{
  static final MulticastSubscription[] EMPTY = new MulticastSubscription[0];
  static final MulticastSubscription[] TERMINATED = new MulticastSubscription[0];
  final int bufferSize;
  int consumed;
  volatile boolean done;
  volatile Throwable error;
  int fusionMode;
  final int limit;
  final AtomicBoolean once;
  volatile SimpleQueue<T> queue;
  final boolean refcount;
  final AtomicReference<MulticastSubscription<T>[]> subscribers;
  final AtomicReference<Subscription> upstream;
  final AtomicInteger wip;
  
  MulticastProcessor(int paramInt, boolean paramBoolean)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    this.bufferSize = paramInt;
    this.limit = (paramInt - (paramInt >> 2));
    this.wip = new AtomicInteger();
    this.subscribers = new AtomicReference(EMPTY);
    this.upstream = new AtomicReference();
    this.refcount = paramBoolean;
    this.once = new AtomicBoolean();
  }
  
  @CheckReturnValue
  public static <T> MulticastProcessor<T> create()
  {
    return new MulticastProcessor(bufferSize(), false);
  }
  
  @CheckReturnValue
  public static <T> MulticastProcessor<T> create(int paramInt)
  {
    return new MulticastProcessor(paramInt, false);
  }
  
  @CheckReturnValue
  public static <T> MulticastProcessor<T> create(int paramInt, boolean paramBoolean)
  {
    return new MulticastProcessor(paramInt, paramBoolean);
  }
  
  @CheckReturnValue
  public static <T> MulticastProcessor<T> create(boolean paramBoolean)
  {
    return new MulticastProcessor(bufferSize(), paramBoolean);
  }
  
  boolean add(MulticastSubscription<T> paramMulticastSubscription)
  {
    MulticastSubscription[] arrayOfMulticastSubscription1;
    MulticastSubscription[] arrayOfMulticastSubscription2;
    do
    {
      arrayOfMulticastSubscription1 = (MulticastSubscription[])this.subscribers.get();
      if (arrayOfMulticastSubscription1 == TERMINATED) {
        return false;
      }
      int i = arrayOfMulticastSubscription1.length;
      arrayOfMulticastSubscription2 = new MulticastSubscription[i + 1];
      System.arraycopy(arrayOfMulticastSubscription1, 0, arrayOfMulticastSubscription2, 0, i);
      arrayOfMulticastSubscription2[i] = paramMulticastSubscription;
    } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, arrayOfMulticastSubscription2));
    return true;
  }
  
  /* Error */
  void drain()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 66	io/reactivex/processors/MulticastProcessor:wip	Ljava/util/concurrent/atomic/AtomicInteger;
    //   4: invokevirtual 122	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   7: ifeq +4 -> 11
    //   10: return
    //   11: aload_0
    //   12: getfield 73	io/reactivex/processors/MulticastProcessor:subscribers	Ljava/util/concurrent/atomic/AtomicReference;
    //   15: astore_1
    //   16: aload_0
    //   17: getfield 124	io/reactivex/processors/MulticastProcessor:consumed	I
    //   20: istore_2
    //   21: aload_0
    //   22: getfield 61	io/reactivex/processors/MulticastProcessor:limit	I
    //   25: istore_3
    //   26: aload_0
    //   27: getfield 126	io/reactivex/processors/MulticastProcessor:fusionMode	I
    //   30: istore 4
    //   32: iconst_1
    //   33: istore 5
    //   35: aload_0
    //   36: getfield 128	io/reactivex/processors/MulticastProcessor:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
    //   39: astore 6
    //   41: iload_2
    //   42: istore 7
    //   44: aload 6
    //   46: ifnull +566 -> 612
    //   49: aload_1
    //   50: invokevirtual 106	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   53: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   56: astore 8
    //   58: iload_2
    //   59: istore 7
    //   61: aload 8
    //   63: arraylength
    //   64: ifeq +548 -> 612
    //   67: aload 8
    //   69: arraylength
    //   70: istore 9
    //   72: ldc2_w 129
    //   75: lstore 10
    //   77: iconst_0
    //   78: istore 7
    //   80: iload 7
    //   82: iload 9
    //   84: if_icmpge +75 -> 159
    //   87: aload 8
    //   89: iload 7
    //   91: aaload
    //   92: astore 12
    //   94: aload 12
    //   96: invokevirtual 133	io/reactivex/processors/MulticastProcessor$MulticastSubscription:get	()J
    //   99: lstore 13
    //   101: lload 10
    //   103: lstore 15
    //   105: lload 13
    //   107: lconst_0
    //   108: lcmp
    //   109: iflt +40 -> 149
    //   112: lload 10
    //   114: ldc2_w 129
    //   117: lcmp
    //   118: ifne +16 -> 134
    //   121: lload 13
    //   123: aload 12
    //   125: getfield 137	io/reactivex/processors/MulticastProcessor$MulticastSubscription:emitted	J
    //   128: lsub
    //   129: lstore 15
    //   131: goto +18 -> 149
    //   134: lload 10
    //   136: lload 13
    //   138: aload 12
    //   140: getfield 137	io/reactivex/processors/MulticastProcessor$MulticastSubscription:emitted	J
    //   143: lsub
    //   144: invokestatic 143	java/lang/Math:min	(JJ)J
    //   147: lstore 15
    //   149: iinc 7 1
    //   152: lload 15
    //   154: lstore 10
    //   156: goto -76 -> 80
    //   159: lload 10
    //   161: lconst_0
    //   162: lcmp
    //   163: istore 9
    //   165: iload 9
    //   167: ifle +298 -> 465
    //   170: aload_1
    //   171: invokevirtual 106	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   174: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   177: astore 12
    //   179: aload 12
    //   181: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   184: if_acmpne +11 -> 195
    //   187: aload 6
    //   189: invokeinterface 148 1 0
    //   194: return
    //   195: aload 8
    //   197: aload 12
    //   199: if_acmpeq +6 -> 205
    //   202: goto +300 -> 502
    //   205: aload_0
    //   206: getfield 150	io/reactivex/processors/MulticastProcessor:done	Z
    //   209: istore 17
    //   211: aload 6
    //   213: invokeinterface 153 1 0
    //   218: astore 12
    //   220: goto +35 -> 255
    //   223: astore 12
    //   225: aload 12
    //   227: invokestatic 159	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   230: aload_0
    //   231: getfield 76	io/reactivex/processors/MulticastProcessor:upstream	Ljava/util/concurrent/atomic/AtomicReference;
    //   234: invokestatic 165	io/reactivex/internal/subscriptions/SubscriptionHelper:cancel	(Ljava/util/concurrent/atomic/AtomicReference;)Z
    //   237: pop
    //   238: aload_0
    //   239: aload 12
    //   241: putfield 167	io/reactivex/processors/MulticastProcessor:error	Ljava/lang/Throwable;
    //   244: aload_0
    //   245: iconst_1
    //   246: putfield 150	io/reactivex/processors/MulticastProcessor:done	Z
    //   249: aconst_null
    //   250: astore 12
    //   252: iconst_1
    //   253: istore 17
    //   255: aload 12
    //   257: ifnonnull +9 -> 266
    //   260: iconst_1
    //   261: istore 7
    //   263: goto +6 -> 269
    //   266: iconst_0
    //   267: istore 7
    //   269: iload 17
    //   271: ifeq +95 -> 366
    //   274: iload 7
    //   276: ifeq +90 -> 366
    //   279: aload_0
    //   280: getfield 167	io/reactivex/processors/MulticastProcessor:error	Ljava/lang/Throwable;
    //   283: astore 12
    //   285: aload 12
    //   287: ifnull +40 -> 327
    //   290: aload_1
    //   291: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   294: invokevirtual 171	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   297: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   300: astore_1
    //   301: aload_1
    //   302: arraylength
    //   303: istore 5
    //   305: iconst_0
    //   306: istore_2
    //   307: iload_2
    //   308: iload 5
    //   310: if_icmpge +55 -> 365
    //   313: aload_1
    //   314: iload_2
    //   315: aaload
    //   316: aload 12
    //   318: invokevirtual 174	io/reactivex/processors/MulticastProcessor$MulticastSubscription:onError	(Ljava/lang/Throwable;)V
    //   321: iinc 2 1
    //   324: goto -17 -> 307
    //   327: aload_1
    //   328: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   331: invokevirtual 171	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   334: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   337: astore 12
    //   339: aload 12
    //   341: arraylength
    //   342: istore 5
    //   344: iconst_0
    //   345: istore_2
    //   346: iload_2
    //   347: iload 5
    //   349: if_icmpge +16 -> 365
    //   352: aload 12
    //   354: iload_2
    //   355: aaload
    //   356: invokevirtual 177	io/reactivex/processors/MulticastProcessor$MulticastSubscription:onComplete	()V
    //   359: iinc 2 1
    //   362: goto -16 -> 346
    //   365: return
    //   366: iload 7
    //   368: ifeq +6 -> 374
    //   371: goto +94 -> 465
    //   374: aload 8
    //   376: arraylength
    //   377: istore 9
    //   379: iconst_0
    //   380: istore 7
    //   382: iload 7
    //   384: iload 9
    //   386: if_icmpge +19 -> 405
    //   389: aload 8
    //   391: iload 7
    //   393: aaload
    //   394: aload 12
    //   396: invokevirtual 180	io/reactivex/processors/MulticastProcessor$MulticastSubscription:onNext	(Ljava/lang/Object;)V
    //   399: iinc 7 1
    //   402: goto -20 -> 382
    //   405: lload 10
    //   407: lconst_1
    //   408: lsub
    //   409: lstore 15
    //   411: lload 15
    //   413: lstore 10
    //   415: iload 4
    //   417: iconst_1
    //   418: if_icmpeq -259 -> 159
    //   421: iload_2
    //   422: iconst_1
    //   423: iadd
    //   424: istore 7
    //   426: iload 7
    //   428: istore_2
    //   429: lload 15
    //   431: lstore 10
    //   433: iload 7
    //   435: iload_3
    //   436: if_icmpne -277 -> 159
    //   439: aload_0
    //   440: getfield 76	io/reactivex/processors/MulticastProcessor:upstream	Ljava/util/concurrent/atomic/AtomicReference;
    //   443: invokevirtual 106	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   446: checkcast 182	org/reactivestreams/Subscription
    //   449: iload_3
    //   450: i2l
    //   451: invokeinterface 186 3 0
    //   456: iconst_0
    //   457: istore_2
    //   458: lload 15
    //   460: lstore 10
    //   462: goto -303 -> 159
    //   465: iload 9
    //   467: ifne +142 -> 609
    //   470: aload_1
    //   471: invokevirtual 106	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   474: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   477: astore 12
    //   479: aload 12
    //   481: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   484: if_acmpne +11 -> 495
    //   487: aload 6
    //   489: invokeinterface 148 1 0
    //   494: return
    //   495: aload 8
    //   497: aload 12
    //   499: if_acmpeq +6 -> 505
    //   502: goto -467 -> 35
    //   505: aload_0
    //   506: getfield 150	io/reactivex/processors/MulticastProcessor:done	Z
    //   509: ifeq +100 -> 609
    //   512: aload 6
    //   514: invokeinterface 190 1 0
    //   519: ifeq +90 -> 609
    //   522: aload_0
    //   523: getfield 167	io/reactivex/processors/MulticastProcessor:error	Ljava/lang/Throwable;
    //   526: astore 12
    //   528: aload 12
    //   530: ifnull +40 -> 570
    //   533: aload_1
    //   534: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   537: invokevirtual 171	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   540: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   543: astore_1
    //   544: aload_1
    //   545: arraylength
    //   546: istore 5
    //   548: iconst_0
    //   549: istore_2
    //   550: iload_2
    //   551: iload 5
    //   553: if_icmpge +55 -> 608
    //   556: aload_1
    //   557: iload_2
    //   558: aaload
    //   559: aload 12
    //   561: invokevirtual 174	io/reactivex/processors/MulticastProcessor$MulticastSubscription:onError	(Ljava/lang/Throwable;)V
    //   564: iinc 2 1
    //   567: goto -17 -> 550
    //   570: aload_1
    //   571: getstatic 45	io/reactivex/processors/MulticastProcessor:TERMINATED	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   574: invokevirtual 171	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   577: checkcast 107	[Lio/reactivex/processors/MulticastProcessor$MulticastSubscription;
    //   580: astore 12
    //   582: aload 12
    //   584: arraylength
    //   585: istore 5
    //   587: iconst_0
    //   588: istore_2
    //   589: iload_2
    //   590: iload 5
    //   592: if_icmpge +16 -> 608
    //   595: aload 12
    //   597: iload_2
    //   598: aaload
    //   599: invokevirtual 177	io/reactivex/processors/MulticastProcessor$MulticastSubscription:onComplete	()V
    //   602: iinc 2 1
    //   605: goto -16 -> 589
    //   608: return
    //   609: iload_2
    //   610: istore 7
    //   612: aload_0
    //   613: getfield 66	io/reactivex/processors/MulticastProcessor:wip	Ljava/util/concurrent/atomic/AtomicInteger;
    //   616: iload 5
    //   618: ineg
    //   619: invokevirtual 194	java/util/concurrent/atomic/AtomicInteger:addAndGet	(I)I
    //   622: istore 9
    //   624: iload 7
    //   626: istore_2
    //   627: iload 9
    //   629: istore 5
    //   631: iload 9
    //   633: ifne -598 -> 35
    //   636: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	637	0	this	MulticastProcessor
    //   15	556	1	localObject1	Object
    //   20	607	2	i	int
    //   25	425	3	j	int
    //   30	389	4	k	int
    //   33	597	5	m	int
    //   39	474	6	localSimpleQueue	SimpleQueue
    //   42	583	7	n	int
    //   56	440	8	arrayOfMulticastSubscription	MulticastSubscription[]
    //   70	15	9	i1	int
    //   163	3	9	bool1	boolean
    //   377	255	9	i2	int
    //   75	386	10	l1	long
    //   92	127	12	localObject2	Object
    //   223	17	12	localThrowable	Throwable
    //   250	346	12	localObject3	Object
    //   99	38	13	l2	long
    //   103	356	15	l3	long
    //   209	61	17	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   211	220	223	finally
  }
  
  public Throwable getThrowable()
  {
    Throwable localThrowable;
    if (this.once.get()) {
      localThrowable = this.error;
    } else {
      localThrowable = null;
    }
    return localThrowable;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.once.get()) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasSubscribers()
  {
    boolean bool;
    if (((MulticastSubscription[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.once.get()) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean offer(T paramT)
  {
    if (this.once.get()) {
      return false;
    }
    ObjectHelper.requireNonNull(paramT, "offer called with null. Null values are generally not allowed in 2.x operators and sources.");
    if ((this.fusionMode == 0) && (this.queue.offer(paramT)))
    {
      drain();
      return true;
    }
    return false;
  }
  
  public void onComplete()
  {
    if (this.once.compareAndSet(false, true))
    {
      this.done = true;
      drain();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.once.compareAndSet(false, true))
    {
      this.error = paramThrowable;
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
    if (this.once.get()) {
      return;
    }
    if (this.fusionMode == 0)
    {
      ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.queue.offer(paramT))
      {
        SubscriptionHelper.cancel(this.upstream);
        onError(new MissingBackpressureException());
        return;
      }
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
          this.fusionMode = i;
          this.queue = localQueueSubscription;
          this.done = true;
          drain();
          return;
        }
        if (i == 2)
        {
          this.fusionMode = i;
          this.queue = localQueueSubscription;
          paramSubscription.request(this.bufferSize);
          return;
        }
      }
      this.queue = new SpscArrayQueue(this.bufferSize);
      paramSubscription.request(this.bufferSize);
    }
  }
  
  void remove(MulticastSubscription<T> paramMulticastSubscription)
  {
    MulticastSubscription[] arrayOfMulticastSubscription1;
    label126:
    MulticastSubscription[] arrayOfMulticastSubscription2;
    do
    {
      int i;
      int m;
      do
      {
        do
        {
          arrayOfMulticastSubscription1 = (MulticastSubscription[])this.subscribers.get();
          i = arrayOfMulticastSubscription1.length;
          if (i == 0) {
            return;
          }
          int j = -1;
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
          if (i != 1) {
            break label126;
          }
          if (!this.refcount) {
            break;
          }
        } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, TERMINATED));
        SubscriptionHelper.cancel(this.upstream);
        this.once.set(true);
        return;
      } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, EMPTY));
      break;
      arrayOfMulticastSubscription2 = new MulticastSubscription[i - 1];
      System.arraycopy(arrayOfMulticastSubscription1, 0, arrayOfMulticastSubscription2, 0, m);
      System.arraycopy(arrayOfMulticastSubscription1, m + 1, arrayOfMulticastSubscription2, m, i - m - 1);
    } while (!this.subscribers.compareAndSet(arrayOfMulticastSubscription1, arrayOfMulticastSubscription2));
  }
  
  public void start()
  {
    if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
      this.queue = new SpscArrayQueue(this.bufferSize);
    }
  }
  
  public void startUnbounded()
  {
    if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
      this.queue = new SpscLinkedArrayQueue(this.bufferSize);
    }
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject = new MulticastSubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe((Subscription)localObject);
    if (add((MulticastSubscription)localObject))
    {
      if (((MulticastSubscription)localObject).get() == Long.MIN_VALUE) {
        remove((MulticastSubscription)localObject);
      } else {
        drain();
      }
    }
    else
    {
      if ((this.once.get()) || (!this.refcount))
      {
        localObject = this.error;
        if (localObject != null)
        {
          paramSubscriber.onError((Throwable)localObject);
          return;
        }
      }
      paramSubscriber.onComplete();
    }
  }
  
  static final class MulticastSubscription<T>
    extends AtomicLong
    implements Subscription
  {
    private static final long serialVersionUID = -363282618957264509L;
    final Subscriber<? super T> downstream;
    long emitted;
    final MulticastProcessor<T> parent;
    
    MulticastSubscription(Subscriber<? super T> paramSubscriber, MulticastProcessor<T> paramMulticastProcessor)
    {
      this.downstream = paramSubscriber;
      this.parent = paramMulticastProcessor;
    }
    
    public void cancel()
    {
      if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
        this.parent.remove(this);
      }
    }
    
    void onComplete()
    {
      if (get() != Long.MIN_VALUE) {
        this.downstream.onComplete();
      }
    }
    
    void onError(Throwable paramThrowable)
    {
      if (get() != Long.MIN_VALUE) {
        this.downstream.onError(paramThrowable);
      }
    }
    
    void onNext(T paramT)
    {
      if (get() != Long.MIN_VALUE)
      {
        this.emitted += 1L;
        this.downstream.onNext(paramT);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        long l1;
        long l2;
        do
        {
          l1 = get();
          if (l1 == Long.MIN_VALUE) {
            break;
          }
          l2 = Long.MAX_VALUE;
          if (l1 == Long.MAX_VALUE) {
            break;
          }
          long l3 = l1 + paramLong;
          if (l3 >= 0L) {
            l2 = l3;
          }
        } while (!compareAndSet(l1, l2));
        this.parent.drain();
      }
    }
  }
}
