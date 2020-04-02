package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublish<T>
  extends ConnectableFlowable<T>
  implements HasUpstreamPublisher<T>
{
  static final long CANCELLED = Long.MIN_VALUE;
  final int bufferSize;
  final AtomicReference<PublishSubscriber<T>> current;
  final Publisher<T> onSubscribe;
  final Flowable<T> source;
  
  private FlowablePublish(Publisher<T> paramPublisher, Flowable<T> paramFlowable, AtomicReference<PublishSubscriber<T>> paramAtomicReference, int paramInt)
  {
    this.onSubscribe = paramPublisher;
    this.source = paramFlowable;
    this.current = paramAtomicReference;
    this.bufferSize = paramInt;
  }
  
  public static <T> ConnectableFlowable<T> create(Flowable<T> paramFlowable, int paramInt)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    return RxJavaPlugins.onAssembly(new FlowablePublish(new FlowablePublisher(localAtomicReference, paramInt), paramFlowable, localAtomicReference, paramInt));
  }
  
  public void connect(Consumer<? super Disposable> paramConsumer)
  {
    PublishSubscriber localPublishSubscriber1;
    PublishSubscriber localPublishSubscriber2;
    do
    {
      localPublishSubscriber1 = (PublishSubscriber)this.current.get();
      if (localPublishSubscriber1 != null)
      {
        localPublishSubscriber2 = localPublishSubscriber1;
        if (!localPublishSubscriber1.isDisposed()) {
          break;
        }
      }
      localPublishSubscriber2 = new PublishSubscriber(this.current, this.bufferSize);
    } while (!this.current.compareAndSet(localPublishSubscriber1, localPublishSubscriber2));
    boolean bool = localPublishSubscriber2.shouldConnect.get();
    int i = 1;
    if ((bool) || (!localPublishSubscriber2.shouldConnect.compareAndSet(false, true))) {
      i = 0;
    }
    try
    {
      paramConsumer.accept(localPublishSubscriber2);
      if (i != 0) {
        this.source.subscribe(localPublishSubscriber2);
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(paramConsumer);
    }
  }
  
  public Publisher<T> source()
  {
    return this.source;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.onSubscribe.subscribe(paramSubscriber);
  }
  
  static final class FlowablePublisher<T>
    implements Publisher<T>
  {
    private final int bufferSize;
    private final AtomicReference<FlowablePublish.PublishSubscriber<T>> curr;
    
    FlowablePublisher(AtomicReference<FlowablePublish.PublishSubscriber<T>> paramAtomicReference, int paramInt)
    {
      this.curr = paramAtomicReference;
      this.bufferSize = paramInt;
    }
    
    public void subscribe(Subscriber<? super T> paramSubscriber)
    {
      FlowablePublish.InnerSubscriber localInnerSubscriber = new FlowablePublish.InnerSubscriber(paramSubscriber);
      paramSubscriber.onSubscribe(localInnerSubscriber);
      do
      {
        FlowablePublish.PublishSubscriber localPublishSubscriber;
        do
        {
          localPublishSubscriber = (FlowablePublish.PublishSubscriber)this.curr.get();
          if (localPublishSubscriber != null)
          {
            paramSubscriber = localPublishSubscriber;
            if (!localPublishSubscriber.isDisposed()) {
              break;
            }
          }
          paramSubscriber = new FlowablePublish.PublishSubscriber(this.curr, this.bufferSize);
        } while (!this.curr.compareAndSet(localPublishSubscriber, paramSubscriber));
      } while (!paramSubscriber.add(localInnerSubscriber));
      if (localInnerSubscriber.get() == Long.MIN_VALUE) {
        paramSubscriber.remove(localInnerSubscriber);
      } else {
        localInnerSubscriber.parent = paramSubscriber;
      }
      paramSubscriber.dispatch();
    }
  }
  
  static final class InnerSubscriber<T>
    extends AtomicLong
    implements Subscription
  {
    private static final long serialVersionUID = -4453897557930727610L;
    final Subscriber<? super T> child;
    long emitted;
    volatile FlowablePublish.PublishSubscriber<T> parent;
    
    InnerSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.child = paramSubscriber;
    }
    
    public void cancel()
    {
      if ((get() != Long.MIN_VALUE) && (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE))
      {
        FlowablePublish.PublishSubscriber localPublishSubscriber = this.parent;
        if (localPublishSubscriber != null)
        {
          localPublishSubscriber.remove(this);
          localPublishSubscriber.dispatch();
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.addCancel(this, paramLong);
        FlowablePublish.PublishSubscriber localPublishSubscriber = this.parent;
        if (localPublishSubscriber != null) {
          localPublishSubscriber.dispatch();
        }
      }
    }
  }
  
  static final class PublishSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Disposable
  {
    static final FlowablePublish.InnerSubscriber[] EMPTY = new FlowablePublish.InnerSubscriber[0];
    static final FlowablePublish.InnerSubscriber[] TERMINATED = new FlowablePublish.InnerSubscriber[0];
    private static final long serialVersionUID = -202316842419149694L;
    final int bufferSize;
    final AtomicReference<PublishSubscriber<T>> current;
    volatile SimpleQueue<T> queue;
    final AtomicBoolean shouldConnect;
    int sourceMode;
    final AtomicReference<FlowablePublish.InnerSubscriber<T>[]> subscribers = new AtomicReference(EMPTY);
    volatile Object terminalEvent;
    final AtomicReference<Subscription> upstream = new AtomicReference();
    
    PublishSubscriber(AtomicReference<PublishSubscriber<T>> paramAtomicReference, int paramInt)
    {
      this.current = paramAtomicReference;
      this.shouldConnect = new AtomicBoolean();
      this.bufferSize = paramInt;
    }
    
    boolean add(FlowablePublish.InnerSubscriber<T> paramInnerSubscriber)
    {
      FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber1;
      FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber2;
      do
      {
        arrayOfInnerSubscriber1 = (FlowablePublish.InnerSubscriber[])this.subscribers.get();
        if (arrayOfInnerSubscriber1 == TERMINATED) {
          return false;
        }
        int i = arrayOfInnerSubscriber1.length;
        arrayOfInnerSubscriber2 = new FlowablePublish.InnerSubscriber[i + 1];
        System.arraycopy(arrayOfInnerSubscriber1, 0, arrayOfInnerSubscriber2, 0, i);
        arrayOfInnerSubscriber2[i] = paramInnerSubscriber;
      } while (!this.subscribers.compareAndSet(arrayOfInnerSubscriber1, arrayOfInnerSubscriber2));
      return true;
    }
    
    boolean checkTerminated(Object paramObject, boolean paramBoolean)
    {
      int i = 0;
      int j = 0;
      if (paramObject != null) {
        if (NotificationLite.isComplete(paramObject))
        {
          if (paramBoolean)
          {
            this.current.compareAndSet(this, null);
            paramObject = (FlowablePublish.InnerSubscriber[])this.subscribers.getAndSet(TERMINATED);
            i = paramObject.length;
            while (j < i)
            {
              paramObject[j].child.onComplete();
              j++;
            }
            return true;
          }
        }
        else
        {
          paramObject = NotificationLite.getError(paramObject);
          this.current.compareAndSet(this, null);
          FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber = (FlowablePublish.InnerSubscriber[])this.subscribers.getAndSet(TERMINATED);
          if (arrayOfInnerSubscriber.length != 0)
          {
            int k = arrayOfInnerSubscriber.length;
            for (j = i; j < k; j++) {
              arrayOfInnerSubscriber[j].child.onError(paramObject);
            }
          }
          RxJavaPlugins.onError(paramObject);
          return true;
        }
      }
      return false;
    }
    
    /* Error */
    void dispatch()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 59	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:subscribers	Ljava/util/concurrent/atomic/AtomicReference;
      //   12: astore_1
      //   13: aload_1
      //   14: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   17: checkcast 77	[Lio/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber;
      //   20: astore_2
      //   21: iconst_1
      //   22: istore_3
      //   23: iconst_1
      //   24: istore 4
      //   26: aload_0
      //   27: getfield 127	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   30: astore 5
      //   32: aload_0
      //   33: getfield 129	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   36: astore 6
      //   38: aload 6
      //   40: ifnull +22 -> 62
      //   43: aload 6
      //   45: invokeinterface 135 1 0
      //   50: ifeq +6 -> 56
      //   53: goto +9 -> 62
      //   56: iconst_0
      //   57: istore 7
      //   59: goto +6 -> 65
      //   62: iload_3
      //   63: istore 7
      //   65: aload_0
      //   66: aload 5
      //   68: iload 7
      //   70: invokevirtual 137	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   73: ifeq +4 -> 77
      //   76: return
      //   77: iload_3
      //   78: istore 8
      //   80: iload 7
      //   82: ifne +525 -> 607
      //   85: aload_2
      //   86: arraylength
      //   87: istore 9
      //   89: aload_2
      //   90: arraylength
      //   91: istore 10
      //   93: iconst_0
      //   94: istore 8
      //   96: iconst_0
      //   97: istore 11
      //   99: ldc2_w 138
      //   102: lstore 12
      //   104: iload 8
      //   106: iload 10
      //   108: if_icmpge +52 -> 160
      //   111: aload_2
      //   112: iload 8
      //   114: aaload
      //   115: astore 5
      //   117: aload 5
      //   119: invokevirtual 142	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:get	()J
      //   122: lstore 14
      //   124: lload 14
      //   126: ldc2_w 143
      //   129: lcmp
      //   130: ifeq +21 -> 151
      //   133: lload 12
      //   135: lload 14
      //   137: aload 5
      //   139: getfield 147	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:emitted	J
      //   142: lsub
      //   143: invokestatic 153	java/lang/Math:min	(JJ)J
      //   146: lstore 12
      //   148: goto +6 -> 154
      //   151: iinc 11 1
      //   154: iinc 8 1
      //   157: goto -53 -> 104
      //   160: iload 9
      //   162: iload 11
      //   164: if_icmpne +112 -> 276
      //   167: aload_0
      //   168: getfield 127	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   171: astore 5
      //   173: aload 6
      //   175: invokeinterface 156 1 0
      //   180: astore 6
      //   182: goto +41 -> 223
      //   185: astore 6
      //   187: aload 6
      //   189: invokestatic 161	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   192: aload_0
      //   193: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   196: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   199: checkcast 163	org/reactivestreams/Subscription
      //   202: invokeinterface 166 1 0
      //   207: aload 6
      //   209: invokestatic 170	io/reactivex/internal/util/NotificationLite:error	(Ljava/lang/Throwable;)Ljava/lang/Object;
      //   212: astore 5
      //   214: aload_0
      //   215: aload 5
      //   217: putfield 127	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   220: aconst_null
      //   221: astore 6
      //   223: aload 6
      //   225: ifnonnull +9 -> 234
      //   228: iload_3
      //   229: istore 7
      //   231: goto +6 -> 237
      //   234: iconst_0
      //   235: istore 7
      //   237: aload_0
      //   238: aload 5
      //   240: iload 7
      //   242: invokevirtual 137	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   245: ifeq +4 -> 249
      //   248: return
      //   249: aload_0
      //   250: getfield 172	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:sourceMode	I
      //   253: iload_3
      //   254: if_icmpeq -228 -> 26
      //   257: aload_0
      //   258: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   261: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   264: checkcast 163	org/reactivestreams/Subscription
      //   267: lconst_1
      //   268: invokeinterface 176 3 0
      //   273: goto -247 -> 26
      //   276: iconst_0
      //   277: istore 8
      //   279: iload 8
      //   281: i2l
      //   282: lstore 14
      //   284: lload 14
      //   286: lload 12
      //   288: lcmp
      //   289: ifge +257 -> 546
      //   292: aload_0
      //   293: getfield 127	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   296: astore 16
      //   298: aload 6
      //   300: invokeinterface 156 1 0
      //   305: astore 5
      //   307: goto +41 -> 348
      //   310: astore 5
      //   312: aload 5
      //   314: invokestatic 161	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   317: aload_0
      //   318: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   321: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   324: checkcast 163	org/reactivestreams/Subscription
      //   327: invokeinterface 166 1 0
      //   332: aload 5
      //   334: invokestatic 170	io/reactivex/internal/util/NotificationLite:error	(Ljava/lang/Throwable;)Ljava/lang/Object;
      //   337: astore 16
      //   339: aload_0
      //   340: aload 16
      //   342: putfield 127	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   345: aconst_null
      //   346: astore 5
      //   348: aload 5
      //   350: ifnonnull +6 -> 356
      //   353: goto +5 -> 358
      //   356: iconst_0
      //   357: istore_3
      //   358: aload_0
      //   359: aload 16
      //   361: iload_3
      //   362: invokevirtual 137	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   365: ifeq +4 -> 369
      //   368: return
      //   369: iload_3
      //   370: ifeq +9 -> 379
      //   373: iload_3
      //   374: istore 7
      //   376: goto +170 -> 546
      //   379: aload 5
      //   381: invokestatic 179	io/reactivex/internal/util/NotificationLite:getValue	(Ljava/lang/Object;)Ljava/lang/Object;
      //   384: astore 5
      //   386: aload_2
      //   387: arraylength
      //   388: istore 10
      //   390: iconst_0
      //   391: istore 7
      //   393: iconst_0
      //   394: istore 11
      //   396: iload 7
      //   398: iload 10
      //   400: if_icmpge +73 -> 473
      //   403: aload_2
      //   404: iload 7
      //   406: aaload
      //   407: astore 16
      //   409: aload 16
      //   411: invokevirtual 142	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:get	()J
      //   414: lstore 14
      //   416: lload 14
      //   418: ldc2_w 143
      //   421: lcmp
      //   422: ifeq +42 -> 464
      //   425: lload 14
      //   427: ldc2_w 138
      //   430: lcmp
      //   431: ifeq +18 -> 449
      //   434: aload 16
      //   436: aload 16
      //   438: getfield 147	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:emitted	J
      //   441: lconst_1
      //   442: ladd
      //   443: putfield 147	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:emitted	J
      //   446: goto +3 -> 449
      //   449: aload 16
      //   451: getfield 104	io/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber:child	Lorg/reactivestreams/Subscriber;
      //   454: aload 5
      //   456: invokeinterface 182 2 0
      //   461: goto +6 -> 467
      //   464: iconst_1
      //   465: istore 11
      //   467: iinc 7 1
      //   470: goto -74 -> 396
      //   473: iinc 8 1
      //   476: aload_1
      //   477: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   480: checkcast 77	[Lio/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber;
      //   483: astore 5
      //   485: iload 11
      //   487: ifne +20 -> 507
      //   490: aload 5
      //   492: aload_2
      //   493: if_acmpeq +6 -> 499
      //   496: goto +11 -> 507
      //   499: iload_3
      //   500: istore 7
      //   502: iconst_1
      //   503: istore_3
      //   504: goto -225 -> 279
      //   507: iload 8
      //   509: ifeq +29 -> 538
      //   512: aload_0
      //   513: getfield 172	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:sourceMode	I
      //   516: iconst_1
      //   517: if_icmpeq +21 -> 538
      //   520: aload_0
      //   521: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   524: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   527: checkcast 163	org/reactivestreams/Subscription
      //   530: iload 8
      //   532: i2l
      //   533: invokeinterface 176 3 0
      //   538: aload 5
      //   540: astore_2
      //   541: iconst_1
      //   542: istore_3
      //   543: goto -517 -> 26
      //   546: iload 8
      //   548: ifeq +31 -> 579
      //   551: aload_0
      //   552: getfield 172	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:sourceMode	I
      //   555: iconst_1
      //   556: if_icmpeq +23 -> 579
      //   559: aload_0
      //   560: getfield 54	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:upstream	Ljava/util/concurrent/atomic/AtomicReference;
      //   563: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   566: checkcast 163	org/reactivestreams/Subscription
      //   569: lload 14
      //   571: invokeinterface 176 3 0
      //   576: goto +3 -> 579
      //   579: iconst_1
      //   580: istore 11
      //   582: iconst_1
      //   583: istore_3
      //   584: iload 11
      //   586: istore 8
      //   588: lload 12
      //   590: lconst_0
      //   591: lcmp
      //   592: ifeq +15 -> 607
      //   595: iload 11
      //   597: istore 8
      //   599: iload 7
      //   601: ifne +6 -> 607
      //   604: goto -578 -> 26
      //   607: aload_0
      //   608: iload 4
      //   610: ineg
      //   611: invokevirtual 186	io/reactivex/internal/operators/flowable/FlowablePublish$PublishSubscriber:addAndGet	(I)I
      //   614: istore 4
      //   616: iload 4
      //   618: ifne +4 -> 622
      //   621: return
      //   622: aload_1
      //   623: invokevirtual 76	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   626: checkcast 77	[Lio/reactivex/internal/operators/flowable/FlowablePublish$InnerSubscriber;
      //   629: astore_2
      //   630: iload 8
      //   632: istore_3
      //   633: goto -607 -> 26
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	636	0	this	PublishSubscriber
      //   12	611	1	localAtomicReference	AtomicReference
      //   20	610	2	localObject1	Object
      //   22	478	3	i	int
      //   503	130	3	j	int
      //   24	593	4	k	int
      //   30	276	5	localObject2	Object
      //   310	23	5	localThrowable1	Throwable
      //   346	193	5	localObject3	Object
      //   36	145	6	localObject4	Object
      //   185	23	6	localThrowable2	Throwable
      //   221	78	6	localObject5	Object
      //   57	543	7	m	int
      //   78	1	8	n	int
      //   94	537	8	i1	int
      //   87	78	9	i2	int
      //   91	310	10	i3	int
      //   97	499	11	i4	int
      //   102	487	12	l1	long
      //   122	448	14	l2	long
      //   296	154	16	localObject6	Object
      // Exception table:
      //   from	to	target	type
      //   173	182	185	finally
      //   298	307	310	finally
    }
    
    public void dispose()
    {
      Object localObject = this.subscribers.get();
      FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber = TERMINATED;
      if ((localObject != arrayOfInnerSubscriber) && ((FlowablePublish.InnerSubscriber[])this.subscribers.getAndSet(arrayOfInnerSubscriber) != TERMINATED))
      {
        this.current.compareAndSet(this, null);
        SubscriptionHelper.cancel(this.upstream);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.subscribers.get() == TERMINATED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (this.terminalEvent == null)
      {
        this.terminalEvent = NotificationLite.complete();
        dispatch();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.terminalEvent == null)
      {
        this.terminalEvent = NotificationLite.error(paramThrowable);
        dispatch();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if ((this.sourceMode == 0) && (!this.queue.offer(paramT)))
      {
        onError(new MissingBackpressureException("Prefetch queue is full?!"));
        return;
      }
      dispatch();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this.upstream, paramSubscription))
      {
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(7);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.terminalEvent = NotificationLite.complete();
            dispatch();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            paramSubscription.request(this.bufferSize);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.bufferSize);
        paramSubscription.request(this.bufferSize);
      }
    }
    
    void remove(FlowablePublish.InnerSubscriber<T> paramInnerSubscriber)
    {
      FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber1;
      FlowablePublish.InnerSubscriber[] arrayOfInnerSubscriber2;
      do
      {
        arrayOfInnerSubscriber1 = (FlowablePublish.InnerSubscriber[])this.subscribers.get();
        int i = arrayOfInnerSubscriber1.length;
        if (i == 0) {
          break;
        }
        int j = -1;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= i) {
            break;
          }
          if (arrayOfInnerSubscriber1[k].equals(paramInnerSubscriber))
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
          arrayOfInnerSubscriber2 = EMPTY;
        }
        else
        {
          arrayOfInnerSubscriber2 = new FlowablePublish.InnerSubscriber[i - 1];
          System.arraycopy(arrayOfInnerSubscriber1, 0, arrayOfInnerSubscriber2, 0, m);
          System.arraycopy(arrayOfInnerSubscriber1, m + 1, arrayOfInnerSubscriber2, m, i - m - 1);
        }
      } while (!this.subscribers.compareAndSet(arrayOfInnerSubscriber1, arrayOfInnerSubscriber2));
    }
  }
}
