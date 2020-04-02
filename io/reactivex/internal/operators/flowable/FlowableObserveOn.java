package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableObserveOn<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean delayError;
  final int prefetch;
  final Scheduler scheduler;
  
  public FlowableObserveOn(Flowable<T> paramFlowable, Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    super(paramFlowable);
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
    this.prefetch = paramInt;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new ObserveOnConditionalSubscriber((ConditionalSubscriber)paramSubscriber, localWorker, this.delayError, this.prefetch));
    } else {
      this.source.subscribe(new ObserveOnSubscriber(paramSubscriber, localWorker, this.delayError, this.prefetch));
    }
  }
  
  static abstract class BaseObserveOnSubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements FlowableSubscriber<T>, Runnable
  {
    private static final long serialVersionUID = -8241002408341274697L;
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final int limit;
    boolean outputFused;
    final int prefetch;
    long produced;
    SimpleQueue<T> queue;
    final AtomicLong requested;
    int sourceMode;
    Subscription upstream;
    final Scheduler.Worker worker;
    
    BaseObserveOnSubscriber(Scheduler.Worker paramWorker, boolean paramBoolean, int paramInt)
    {
      this.worker = paramWorker;
      this.delayError = paramBoolean;
      this.prefetch = paramInt;
      this.requested = new AtomicLong();
      this.limit = (paramInt - (paramInt >> 2));
    }
    
    public final void cancel()
    {
      if (this.cancelled) {
        return;
      }
      this.cancelled = true;
      this.upstream.cancel();
      this.worker.dispose();
      if (getAndIncrement() == 0) {
        this.queue.clear();
      }
    }
    
    final boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber)
    {
      if (this.cancelled)
      {
        clear();
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable;
        if (this.delayError)
        {
          if (paramBoolean2)
          {
            this.cancelled = true;
            localThrowable = this.error;
            if (localThrowable != null) {
              paramSubscriber.onError(localThrowable);
            } else {
              paramSubscriber.onComplete();
            }
            this.worker.dispose();
            return true;
          }
        }
        else
        {
          localThrowable = this.error;
          if (localThrowable != null)
          {
            this.cancelled = true;
            clear();
            paramSubscriber.onError(localThrowable);
            this.worker.dispose();
            return true;
          }
          if (paramBoolean2)
          {
            this.cancelled = true;
            paramSubscriber.onComplete();
            this.worker.dispose();
            return true;
          }
        }
      }
      return false;
    }
    
    public final void clear()
    {
      this.queue.clear();
    }
    
    public final boolean isEmpty()
    {
      return this.queue.isEmpty();
    }
    
    public final void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        trySchedule();
      }
    }
    
    public final void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      trySchedule();
    }
    
    public final void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode == 2)
      {
        trySchedule();
        return;
      }
      if (!this.queue.offer(paramT))
      {
        this.upstream.cancel();
        this.error = new MissingBackpressureException("Queue is full?!");
        this.done = true;
      }
      trySchedule();
    }
    
    public final void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        trySchedule();
      }
    }
    
    public final int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
    
    public final void run()
    {
      if (this.outputFused) {
        runBackfused();
      } else if (this.sourceMode == 1) {
        runSync();
      } else {
        runAsync();
      }
    }
    
    abstract void runAsync();
    
    abstract void runBackfused();
    
    abstract void runSync();
    
    final void trySchedule()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      this.worker.schedule(this);
    }
  }
  
  static final class ObserveOnConditionalSubscriber<T>
    extends FlowableObserveOn.BaseObserveOnSubscriber<T>
  {
    private static final long serialVersionUID = 644624475404284533L;
    long consumed;
    final ConditionalSubscriber<? super T> downstream;
    
    ObserveOnConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Scheduler.Worker paramWorker, boolean paramBoolean, int paramInt)
    {
      super(paramBoolean, paramInt);
      this.downstream = paramConditionalSubscriber;
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
            this.sourceMode = 1;
            this.queue = localQueueSubscription;
            this.done = true;
            this.downstream.onSubscribe(this);
            return;
          }
          if (i == 2)
          {
            this.sourceMode = 2;
            this.queue = localQueueSubscription;
            this.downstream.onSubscribe(this);
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.queue.poll();
      if ((localObject != null) && (this.sourceMode != 1))
      {
        long l = this.consumed + 1L;
        if (l == this.limit)
        {
          this.consumed = 0L;
          this.upstream.request(l);
        }
        else
        {
          this.consumed = l;
        }
      }
      return localObject;
    }
    
    /* Error */
    void runAsync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 52	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 94	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:produced	J
      //   14: lstore_3
      //   15: aload_0
      //   16: getfield 84	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:consumed	J
      //   19: lstore 5
      //   21: iconst_1
      //   22: istore 7
      //   24: aload_0
      //   25: getfield 98	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   28: invokevirtual 104	java/util/concurrent/atomic/AtomicLong:get	()J
      //   31: lstore 8
      //   33: lload_3
      //   34: lload 8
      //   36: lcmp
      //   37: istore 10
      //   39: iload 10
      //   41: ifeq +158 -> 199
      //   44: aload_0
      //   45: getfield 56	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:done	Z
      //   48: istore 11
      //   50: aload_2
      //   51: invokeinterface 82 1 0
      //   56: astore 12
      //   58: aload 12
      //   60: ifnonnull +9 -> 69
      //   63: iconst_1
      //   64: istore 13
      //   66: goto +6 -> 72
      //   69: iconst_0
      //   70: istore 13
      //   72: aload_0
      //   73: iload 11
      //   75: iload 13
      //   77: aload_1
      //   78: invokevirtual 108	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;)Z
      //   81: ifeq +4 -> 85
      //   84: return
      //   85: iload 13
      //   87: ifeq +6 -> 93
      //   90: goto +109 -> 199
      //   93: lload_3
      //   94: lstore 14
      //   96: aload_1
      //   97: aload 12
      //   99: invokeinterface 112 2 0
      //   104: ifeq +8 -> 112
      //   107: lload_3
      //   108: lconst_1
      //   109: ladd
      //   110: lstore 14
      //   112: lload 5
      //   114: lconst_1
      //   115: ladd
      //   116: lstore 16
      //   118: lload 14
      //   120: lstore_3
      //   121: lload 16
      //   123: lstore 5
      //   125: lload 16
      //   127: aload_0
      //   128: getfield 87	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:limit	I
      //   131: i2l
      //   132: lcmp
      //   133: ifne -100 -> 33
      //   136: aload_0
      //   137: getfield 32	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   140: lload 16
      //   142: invokeinterface 69 3 0
      //   147: lconst_0
      //   148: lstore 5
      //   150: lload 14
      //   152: lstore_3
      //   153: goto -120 -> 33
      //   156: astore 12
      //   158: aload 12
      //   160: invokestatic 118	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   163: aload_0
      //   164: iconst_1
      //   165: putfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   168: aload_0
      //   169: getfield 32	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   172: invokeinterface 124 1 0
      //   177: aload_2
      //   178: invokeinterface 127 1 0
      //   183: aload_1
      //   184: aload 12
      //   186: invokeinterface 130 2 0
      //   191: aload_0
      //   192: getfield 134	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   195: invokevirtual 139	io/reactivex/Scheduler$Worker:dispose	()V
      //   198: return
      //   199: iload 10
      //   201: ifne +22 -> 223
      //   204: aload_0
      //   205: aload_0
      //   206: getfield 56	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:done	Z
      //   209: aload_2
      //   210: invokeinterface 143 1 0
      //   215: aload_1
      //   216: invokevirtual 108	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;)Z
      //   219: ifeq +4 -> 223
      //   222: return
      //   223: aload_0
      //   224: invokevirtual 146	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:get	()I
      //   227: istore 10
      //   229: iload 7
      //   231: iload 10
      //   233: if_icmpne +33 -> 266
      //   236: aload_0
      //   237: lload_3
      //   238: putfield 94	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:produced	J
      //   241: aload_0
      //   242: lload 5
      //   244: putfield 84	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:consumed	J
      //   247: aload_0
      //   248: iload 7
      //   250: ineg
      //   251: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:addAndGet	(I)I
      //   254: istore 10
      //   256: iload 10
      //   258: istore 7
      //   260: iload 10
      //   262: ifne -238 -> 24
      //   265: return
      //   266: iload 10
      //   268: istore 7
      //   270: goto -246 -> 24
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	273	0	this	ObserveOnConditionalSubscriber
      //   4	212	1	localConditionalSubscriber	ConditionalSubscriber
      //   9	201	2	localSimpleQueue	SimpleQueue
      //   14	224	3	l1	long
      //   19	224	5	l2	long
      //   22	247	7	i	int
      //   31	4	8	l3	long
      //   37	163	10	bool1	boolean
      //   227	40	10	j	int
      //   48	26	11	bool2	boolean
      //   56	42	12	localObject	Object
      //   156	29	12	localThrowable	Throwable
      //   64	22	13	bool3	boolean
      //   94	57	14	l4	long
      //   116	25	16	l5	long
      // Exception table:
      //   from	to	target	type
      //   50	58	156	finally
    }
    
    void runBackfused()
    {
      int i = 1;
      int j;
      do
      {
        if (this.cancelled) {
          return;
        }
        boolean bool = this.done;
        this.downstream.onNext(null);
        if (bool)
        {
          this.cancelled = true;
          Throwable localThrowable = this.error;
          if (localThrowable != null) {
            this.downstream.onError(localThrowable);
          } else {
            this.downstream.onComplete();
          }
          this.worker.dispose();
          return;
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    /* Error */
    void runSync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 52	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 94	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:produced	J
      //   14: lstore_3
      //   15: iconst_1
      //   16: istore 5
      //   18: aload_0
      //   19: getfield 98	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   22: invokevirtual 104	java/util/concurrent/atomic/AtomicLong:get	()J
      //   25: lstore 6
      //   27: lload_3
      //   28: lload 6
      //   30: lcmp
      //   31: ifeq +98 -> 129
      //   34: aload_2
      //   35: invokeinterface 82 1 0
      //   40: astore 8
      //   42: aload_0
      //   43: getfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   46: ifeq +4 -> 50
      //   49: return
      //   50: aload 8
      //   52: ifnonnull +22 -> 74
      //   55: aload_0
      //   56: iconst_1
      //   57: putfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   60: aload_1
      //   61: invokeinterface 161 1 0
      //   66: aload_0
      //   67: getfield 134	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   70: invokevirtual 139	io/reactivex/Scheduler$Worker:dispose	()V
      //   73: return
      //   74: aload_1
      //   75: aload 8
      //   77: invokeinterface 112 2 0
      //   82: ifeq -55 -> 27
      //   85: lload_3
      //   86: lconst_1
      //   87: ladd
      //   88: lstore_3
      //   89: goto -62 -> 27
      //   92: astore 8
      //   94: aload 8
      //   96: invokestatic 118	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   99: aload_0
      //   100: iconst_1
      //   101: putfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   104: aload_0
      //   105: getfield 32	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   108: invokeinterface 124 1 0
      //   113: aload_1
      //   114: aload 8
      //   116: invokeinterface 130 2 0
      //   121: aload_0
      //   122: getfield 134	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   125: invokevirtual 139	io/reactivex/Scheduler$Worker:dispose	()V
      //   128: return
      //   129: aload_0
      //   130: getfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   133: ifeq +4 -> 137
      //   136: return
      //   137: aload_2
      //   138: invokeinterface 143 1 0
      //   143: ifeq +22 -> 165
      //   146: aload_0
      //   147: iconst_1
      //   148: putfield 121	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:cancelled	Z
      //   151: aload_1
      //   152: invokeinterface 161 1 0
      //   157: aload_0
      //   158: getfield 134	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   161: invokevirtual 139	io/reactivex/Scheduler$Worker:dispose	()V
      //   164: return
      //   165: aload_0
      //   166: invokevirtual 146	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:get	()I
      //   169: istore 9
      //   171: iload 5
      //   173: iload 9
      //   175: if_icmpne +27 -> 202
      //   178: aload_0
      //   179: lload_3
      //   180: putfield 94	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:produced	J
      //   183: aload_0
      //   184: iload 5
      //   186: ineg
      //   187: invokevirtual 149	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnConditionalSubscriber:addAndGet	(I)I
      //   190: istore 9
      //   192: iload 9
      //   194: istore 5
      //   196: iload 9
      //   198: ifne -180 -> 18
      //   201: return
      //   202: iload 9
      //   204: istore 5
      //   206: goto -188 -> 18
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	209	0	this	ObserveOnConditionalSubscriber
      //   4	148	1	localConditionalSubscriber	ConditionalSubscriber
      //   9	129	2	localSimpleQueue	SimpleQueue
      //   14	166	3	l1	long
      //   16	189	5	i	int
      //   25	4	6	l2	long
      //   40	36	8	localObject	Object
      //   92	23	8	localThrowable	Throwable
      //   169	34	9	j	int
      // Exception table:
      //   from	to	target	type
      //   34	42	92	finally
    }
  }
  
  static final class ObserveOnSubscriber<T>
    extends FlowableObserveOn.BaseObserveOnSubscriber<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -4547113800637756442L;
    final Subscriber<? super T> downstream;
    
    ObserveOnSubscriber(Subscriber<? super T> paramSubscriber, Scheduler.Worker paramWorker, boolean paramBoolean, int paramInt)
    {
      super(paramBoolean, paramInt);
      this.downstream = paramSubscriber;
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
            this.sourceMode = 1;
            this.queue = localQueueSubscription;
            this.done = true;
            this.downstream.onSubscribe(this);
            return;
          }
          if (i == 2)
          {
            this.sourceMode = 2;
            this.queue = localQueueSubscription;
            this.downstream.onSubscribe(this);
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.queue.poll();
      if ((localObject != null) && (this.sourceMode != 1))
      {
        long l = this.produced + 1L;
        if (l == this.limit)
        {
          this.produced = 0L;
          this.upstream.request(l);
        }
        else
        {
          this.produced = l;
        }
      }
      return localObject;
    }
    
    /* Error */
    void runAsync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 24	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 53	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 86	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:produced	J
      //   14: lstore_3
      //   15: iconst_1
      //   16: istore 5
      //   18: aload_0
      //   19: getfield 97	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   22: invokevirtual 103	java/util/concurrent/atomic/AtomicLong:get	()J
      //   25: lstore 6
      //   27: lload_3
      //   28: lload 6
      //   30: lcmp
      //   31: istore 8
      //   33: iload 8
      //   35: ifeq +167 -> 202
      //   38: aload_0
      //   39: getfield 57	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:done	Z
      //   42: istore 9
      //   44: aload_2
      //   45: invokeinterface 83 1 0
      //   50: astore 10
      //   52: aload 10
      //   54: ifnonnull +9 -> 63
      //   57: iconst_1
      //   58: istore 11
      //   60: goto +6 -> 66
      //   63: iconst_0
      //   64: istore 11
      //   66: aload_0
      //   67: iload 9
      //   69: iload 11
      //   71: aload_1
      //   72: invokevirtual 107	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;)Z
      //   75: ifeq +4 -> 79
      //   78: return
      //   79: iload 11
      //   81: ifeq +6 -> 87
      //   84: goto +118 -> 202
      //   87: aload_1
      //   88: aload 10
      //   90: invokeinterface 111 2 0
      //   95: lload_3
      //   96: lconst_1
      //   97: ladd
      //   98: lstore 12
      //   100: lload 12
      //   102: lstore_3
      //   103: lload 12
      //   105: aload_0
      //   106: getfield 89	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:limit	I
      //   109: i2l
      //   110: lcmp
      //   111: ifne -84 -> 27
      //   114: lload 6
      //   116: lstore 14
      //   118: lload 6
      //   120: ldc2_w 112
      //   123: lcmp
      //   124: ifeq +15 -> 139
      //   127: aload_0
      //   128: getfield 97	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   131: lload 12
      //   133: lneg
      //   134: invokevirtual 117	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   137: lstore 14
      //   139: aload_0
      //   140: getfield 33	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   143: lload 12
      //   145: invokeinterface 70 3 0
      //   150: lconst_0
      //   151: lstore_3
      //   152: lload 14
      //   154: lstore 6
      //   156: goto -129 -> 27
      //   159: astore 10
      //   161: aload 10
      //   163: invokestatic 123	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   166: aload_0
      //   167: iconst_1
      //   168: putfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   171: aload_0
      //   172: getfield 33	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   175: invokeinterface 129 1 0
      //   180: aload_2
      //   181: invokeinterface 132 1 0
      //   186: aload_1
      //   187: aload 10
      //   189: invokeinterface 135 2 0
      //   194: aload_0
      //   195: getfield 139	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   198: invokevirtual 144	io/reactivex/Scheduler$Worker:dispose	()V
      //   201: return
      //   202: iload 8
      //   204: ifne +22 -> 226
      //   207: aload_0
      //   208: aload_0
      //   209: getfield 57	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:done	Z
      //   212: aload_2
      //   213: invokeinterface 148 1 0
      //   218: aload_1
      //   219: invokevirtual 107	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;)Z
      //   222: ifeq +4 -> 226
      //   225: return
      //   226: aload_0
      //   227: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:get	()I
      //   230: istore 8
      //   232: iload 5
      //   234: iload 8
      //   236: if_icmpne +27 -> 263
      //   239: aload_0
      //   240: lload_3
      //   241: putfield 86	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:produced	J
      //   244: aload_0
      //   245: iload 5
      //   247: ineg
      //   248: invokevirtual 153	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:addAndGet	(I)I
      //   251: istore 8
      //   253: iload 8
      //   255: istore 5
      //   257: iload 8
      //   259: ifne -241 -> 18
      //   262: return
      //   263: iload 8
      //   265: istore 5
      //   267: goto -249 -> 18
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	270	0	this	ObserveOnSubscriber
      //   4	215	1	localSubscriber	Subscriber
      //   9	204	2	localSimpleQueue	SimpleQueue
      //   14	227	3	l1	long
      //   16	250	5	i	int
      //   25	130	6	l2	long
      //   31	172	8	bool1	boolean
      //   230	34	8	j	int
      //   42	26	9	bool2	boolean
      //   50	39	10	localObject	Object
      //   159	29	10	localThrowable	Throwable
      //   58	22	11	bool3	boolean
      //   98	46	12	l3	long
      //   116	37	14	l4	long
      // Exception table:
      //   from	to	target	type
      //   44	52	159	finally
    }
    
    void runBackfused()
    {
      int i = 1;
      int j;
      do
      {
        if (this.cancelled) {
          return;
        }
        boolean bool = this.done;
        this.downstream.onNext(null);
        if (bool)
        {
          this.cancelled = true;
          Throwable localThrowable = this.error;
          if (localThrowable != null) {
            this.downstream.onError(localThrowable);
          } else {
            this.downstream.onComplete();
          }
          this.worker.dispose();
          return;
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    /* Error */
    void runSync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 24	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 53	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   9: astore_2
      //   10: aload_0
      //   11: getfield 86	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:produced	J
      //   14: lstore_3
      //   15: iconst_1
      //   16: istore 5
      //   18: aload_0
      //   19: getfield 97	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   22: invokevirtual 103	java/util/concurrent/atomic/AtomicLong:get	()J
      //   25: lstore 6
      //   27: lload_3
      //   28: lload 6
      //   30: lcmp
      //   31: ifeq +92 -> 123
      //   34: aload_2
      //   35: invokeinterface 83 1 0
      //   40: astore 8
      //   42: aload_0
      //   43: getfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   46: ifeq +4 -> 50
      //   49: return
      //   50: aload 8
      //   52: ifnonnull +22 -> 74
      //   55: aload_0
      //   56: iconst_1
      //   57: putfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   60: aload_1
      //   61: invokeinterface 161 1 0
      //   66: aload_0
      //   67: getfield 139	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   70: invokevirtual 144	io/reactivex/Scheduler$Worker:dispose	()V
      //   73: return
      //   74: aload_1
      //   75: aload 8
      //   77: invokeinterface 111 2 0
      //   82: lload_3
      //   83: lconst_1
      //   84: ladd
      //   85: lstore_3
      //   86: goto -59 -> 27
      //   89: astore_2
      //   90: aload_2
      //   91: invokestatic 123	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   94: aload_0
      //   95: iconst_1
      //   96: putfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   99: aload_0
      //   100: getfield 33	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   103: invokeinterface 129 1 0
      //   108: aload_1
      //   109: aload_2
      //   110: invokeinterface 135 2 0
      //   115: aload_0
      //   116: getfield 139	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   119: invokevirtual 144	io/reactivex/Scheduler$Worker:dispose	()V
      //   122: return
      //   123: aload_0
      //   124: getfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   127: ifeq +4 -> 131
      //   130: return
      //   131: aload_2
      //   132: invokeinterface 148 1 0
      //   137: ifeq +22 -> 159
      //   140: aload_0
      //   141: iconst_1
      //   142: putfield 126	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:cancelled	Z
      //   145: aload_1
      //   146: invokeinterface 161 1 0
      //   151: aload_0
      //   152: getfield 139	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:worker	Lio/reactivex/Scheduler$Worker;
      //   155: invokevirtual 144	io/reactivex/Scheduler$Worker:dispose	()V
      //   158: return
      //   159: aload_0
      //   160: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:get	()I
      //   163: istore 9
      //   165: iload 5
      //   167: iload 9
      //   169: if_icmpne +27 -> 196
      //   172: aload_0
      //   173: lload_3
      //   174: putfield 86	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:produced	J
      //   177: aload_0
      //   178: iload 5
      //   180: ineg
      //   181: invokevirtual 153	io/reactivex/internal/operators/flowable/FlowableObserveOn$ObserveOnSubscriber:addAndGet	(I)I
      //   184: istore 9
      //   186: iload 9
      //   188: istore 5
      //   190: iload 9
      //   192: ifne -174 -> 18
      //   195: return
      //   196: iload 9
      //   198: istore 5
      //   200: goto -182 -> 18
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	203	0	this	ObserveOnSubscriber
      //   4	142	1	localSubscriber	Subscriber
      //   9	26	2	localSimpleQueue	SimpleQueue
      //   89	43	2	localThrowable	Throwable
      //   14	160	3	l1	long
      //   16	183	5	i	int
      //   25	4	6	l2	long
      //   40	36	8	localObject	Object
      //   163	34	9	j	int
      // Exception table:
      //   from	to	target	type
      //   34	42	89	finally
    }
  }
}
