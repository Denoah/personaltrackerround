package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferTimed<T, U extends Collection<? super T>>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Callable<U> bufferSupplier;
  final int maxSize;
  final boolean restartTimerOnMaxSize;
  final Scheduler scheduler;
  final long timeskip;
  final long timespan;
  final TimeUnit unit;
  
  public FlowableBufferTimed(Flowable<T> paramFlowable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, Callable<U> paramCallable, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.timespan = paramLong1;
    this.timeskip = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSupplier = paramCallable;
    this.maxSize = paramInt;
    this.restartTimerOnMaxSize = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    if ((this.timespan == this.timeskip) && (this.maxSize == Integer.MAX_VALUE))
    {
      this.source.subscribe(new BufferExactUnboundedSubscriber(new SerializedSubscriber(paramSubscriber), this.bufferSupplier, this.timespan, this.unit, this.scheduler));
      return;
    }
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    if (this.timespan == this.timeskip)
    {
      this.source.subscribe(new BufferExactBoundedSubscriber(new SerializedSubscriber(paramSubscriber), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, localWorker));
      return;
    }
    this.source.subscribe(new BufferSkipBoundedSubscriber(new SerializedSubscriber(paramSubscriber), this.bufferSupplier, this.timespan, this.timeskip, this.unit, localWorker));
  }
  
  static final class BufferExactBoundedSubscriber<T, U extends Collection<? super T>>
    extends QueueDrainSubscriber<T, U, U>
    implements Subscription, Runnable, Disposable
  {
    U buffer;
    final Callable<U> bufferSupplier;
    long consumerIndex;
    final int maxSize;
    long producerIndex;
    final boolean restartTimerOnMaxSize;
    Disposable timer;
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker w;
    
    BufferExactBoundedSubscriber(Subscriber<? super U> paramSubscriber, Callable<U> paramCallable, long paramLong, TimeUnit paramTimeUnit, int paramInt, boolean paramBoolean, Scheduler.Worker paramWorker)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.maxSize = paramInt;
      this.restartTimerOnMaxSize = paramBoolean;
      this.w = paramWorker;
    }
    
    public boolean accept(Subscriber<? super U> paramSubscriber, U paramU)
    {
      paramSubscriber.onNext(paramU);
      return true;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        dispose();
      }
    }
    
    public void dispose()
    {
      try
      {
        this.buffer = null;
        this.upstream.cancel();
        this.w.dispose();
        return;
      }
      finally {}
    }
    
    public boolean isDisposed()
    {
      return this.w.isDisposed();
    }
    
    public void onComplete()
    {
      try
      {
        Collection localCollection = this.buffer;
        this.buffer = null;
        this.queue.offer(localCollection);
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this, this);
        }
        this.w.dispose();
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        this.buffer = null;
        this.downstream.onError(paramThrowable);
        this.w.dispose();
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 84	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:buffer	Ljava/util/Collection;
      //   6: astore_2
      //   7: aload_2
      //   8: ifnonnull +6 -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_2
      //   15: aload_1
      //   16: invokeinterface 129 2 0
      //   21: pop
      //   22: aload_2
      //   23: invokeinterface 133 1 0
      //   28: aload_0
      //   29: getfield 54	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:maxSize	I
      //   32: if_icmpge +6 -> 38
      //   35: aload_0
      //   36: monitorexit
      //   37: return
      //   38: aload_0
      //   39: aconst_null
      //   40: putfield 84	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:buffer	Ljava/util/Collection;
      //   43: aload_0
      //   44: aload_0
      //   45: getfield 135	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:producerIndex	J
      //   48: lconst_1
      //   49: ladd
      //   50: putfield 135	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:producerIndex	J
      //   53: aload_0
      //   54: monitorexit
      //   55: aload_0
      //   56: getfield 56	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:restartTimerOnMaxSize	Z
      //   59: ifeq +12 -> 71
      //   62: aload_0
      //   63: getfield 137	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:timer	Lio/reactivex/disposables/Disposable;
      //   66: invokeinterface 138 1 0
      //   71: aload_0
      //   72: aload_2
      //   73: iconst_0
      //   74: aload_0
      //   75: invokevirtual 142	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:fastPathOrderedEmitMax	(Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
      //   78: aload_0
      //   79: getfield 48	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:bufferSupplier	Ljava/util/concurrent/Callable;
      //   82: invokeinterface 148 1 0
      //   87: ldc -106
      //   89: invokestatic 156	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   92: checkcast 65	java/util/Collection
      //   95: astore_1
      //   96: aload_0
      //   97: monitorenter
      //   98: aload_0
      //   99: aload_1
      //   100: putfield 84	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:buffer	Ljava/util/Collection;
      //   103: aload_0
      //   104: aload_0
      //   105: getfield 158	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:consumerIndex	J
      //   108: lconst_1
      //   109: ladd
      //   110: putfield 158	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:consumerIndex	J
      //   113: aload_0
      //   114: monitorexit
      //   115: aload_0
      //   116: getfield 56	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:restartTimerOnMaxSize	Z
      //   119: ifeq +28 -> 147
      //   122: aload_0
      //   123: getfield 58	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:w	Lio/reactivex/Scheduler$Worker;
      //   126: astore_1
      //   127: aload_0
      //   128: getfield 50	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:timespan	J
      //   131: lstore_3
      //   132: aload_0
      //   133: aload_1
      //   134: aload_0
      //   135: lload_3
      //   136: lload_3
      //   137: aload_0
      //   138: getfield 52	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:unit	Ljava/util/concurrent/TimeUnit;
      //   141: invokevirtual 162	io/reactivex/Scheduler$Worker:schedulePeriodically	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   144: putfield 137	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:timer	Lio/reactivex/disposables/Disposable;
      //   147: return
      //   148: astore_1
      //   149: aload_0
      //   150: monitorexit
      //   151: aload_1
      //   152: athrow
      //   153: astore_1
      //   154: aload_1
      //   155: invokestatic 167	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   158: aload_0
      //   159: invokevirtual 168	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:cancel	()V
      //   162: aload_0
      //   163: getfield 116	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   166: aload_1
      //   167: invokeinterface 126 2 0
      //   172: return
      //   173: astore_1
      //   174: aload_0
      //   175: monitorexit
      //   176: aload_1
      //   177: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	178	0	this	BufferExactBoundedSubscriber
      //   0	178	1	paramT	T
      //   6	67	2	localCollection	Collection
      //   131	6	3	l	long
      // Exception table:
      //   from	to	target	type
      //   98	115	148	finally
      //   149	151	148	finally
      //   78	96	153	finally
      //   2	7	173	finally
      //   11	13	173	finally
      //   14	37	173	finally
      //   38	55	173	finally
      //   174	176	173	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (!SubscriptionHelper.validate(this.upstream, paramSubscription)) {
        return;
      }
      this.upstream = paramSubscription;
      try
      {
        Object localObject = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
        this.buffer = ((Collection)localObject);
        this.downstream.onSubscribe(this);
        localObject = this.w;
        long l = this.timespan;
        this.timer = ((Scheduler.Worker)localObject).schedulePeriodically(this, l, l, this.unit);
        paramSubscription.request(Long.MAX_VALUE);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.w.dispose();
        paramSubscription.cancel();
        EmptySubscription.error(localThrowable, this.downstream);
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    public void run()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
        try
        {
          Collection localCollection2 = this.buffer;
          if ((localCollection2 != null) && (this.producerIndex == this.consumerIndex))
          {
            this.buffer = localCollection1;
            fastPathOrderedEmitMax(localCollection2, false, this);
            return;
          }
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        cancel();
      }
    }
  }
  
  static final class BufferExactUnboundedSubscriber<T, U extends Collection<? super T>>
    extends QueueDrainSubscriber<T, U, U>
    implements Subscription, Runnable, Disposable
  {
    U buffer;
    final Callable<U> bufferSupplier;
    final Scheduler scheduler;
    final AtomicReference<Disposable> timer = new AtomicReference();
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    
    BufferExactUnboundedSubscriber(Subscriber<? super U> paramSubscriber, Callable<U> paramCallable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    public boolean accept(Subscriber<? super U> paramSubscriber, U paramU)
    {
      this.downstream.onNext(paramU);
      return true;
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      DisposableHelper.dispose(this.timer);
    }
    
    public void dispose()
    {
      cancel();
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.timer.get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this.timer);
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection == null) {
          return;
        }
        this.buffer = null;
        this.queue.offer(localCollection);
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, null, this);
        }
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.timer);
      try
      {
        this.buffer = null;
        this.downstream.onError(paramThrowable);
        return;
      }
      finally {}
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection != null) {
          localCollection.add(paramT);
        }
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onSubscribe(Subscription paramSubscription)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 82	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   4: aload_1
      //   5: invokestatic 142	io/reactivex/internal/subscriptions/SubscriptionHelper:validate	(Lorg/reactivestreams/Subscription;Lorg/reactivestreams/Subscription;)Z
      //   8: ifeq +117 -> 125
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 82	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   16: aload_0
      //   17: getfield 48	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:bufferSupplier	Ljava/util/concurrent/Callable;
      //   20: invokeinterface 147 1 0
      //   25: ldc -107
      //   27: invokestatic 155	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: checkcast 61	java/util/Collection
      //   33: astore_2
      //   34: aload_0
      //   35: aload_2
      //   36: putfield 104	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:buffer	Ljava/util/Collection;
      //   39: aload_0
      //   40: getfield 68	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   43: aload_0
      //   44: invokeinterface 157 2 0
      //   49: aload_0
      //   50: getfield 80	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:cancelled	Z
      //   53: ifne +72 -> 125
      //   56: aload_1
      //   57: ldc2_w 158
      //   60: invokeinterface 163 3 0
      //   65: aload_0
      //   66: getfield 54	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:scheduler	Lio/reactivex/Scheduler;
      //   69: astore_1
      //   70: aload_0
      //   71: getfield 50	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:timespan	J
      //   74: lstore_3
      //   75: aload_1
      //   76: aload_0
      //   77: lload_3
      //   78: lload_3
      //   79: aload_0
      //   80: getfield 52	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:unit	Ljava/util/concurrent/TimeUnit;
      //   83: invokevirtual 169	io/reactivex/Scheduler:schedulePeriodicallyDirect	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   86: astore_1
      //   87: aload_0
      //   88: getfield 46	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:timer	Ljava/util/concurrent/atomic/AtomicReference;
      //   91: aconst_null
      //   92: aload_1
      //   93: invokevirtual 173	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   96: ifne +29 -> 125
      //   99: aload_1
      //   100: invokeinterface 175 1 0
      //   105: goto +20 -> 125
      //   108: astore_1
      //   109: aload_1
      //   110: invokestatic 180	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   113: aload_0
      //   114: invokevirtual 91	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:cancel	()V
      //   117: aload_1
      //   118: aload_0
      //   119: getfield 68	io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   122: invokestatic 186	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
      //   125: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	126	0	this	BufferExactUnboundedSubscriber
      //   0	126	1	paramSubscription	Subscription
      //   33	3	2	localCollection	Collection
      //   74	5	3	l	long
      // Exception table:
      //   from	to	target	type
      //   16	34	108	finally
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    public void run()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
        try
        {
          Collection localCollection2 = this.buffer;
          if (localCollection2 == null) {
            return;
          }
          this.buffer = localCollection1;
          fastPathEmitMax(localCollection2, false, this);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        cancel();
      }
    }
  }
  
  static final class BufferSkipBoundedSubscriber<T, U extends Collection<? super T>>
    extends QueueDrainSubscriber<T, U, U>
    implements Subscription, Runnable
  {
    final Callable<U> bufferSupplier;
    final List<U> buffers;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker w;
    
    BufferSkipBoundedSubscriber(Subscriber<? super U> paramSubscriber, Callable<U> paramCallable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong1;
      this.timeskip = paramLong2;
      this.unit = paramTimeUnit;
      this.w = paramWorker;
      this.buffers = new LinkedList();
    }
    
    public boolean accept(Subscriber<? super U> paramSubscriber, U paramU)
    {
      paramSubscriber.onNext(paramU);
      return true;
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      this.w.dispose();
      clear();
    }
    
    void clear()
    {
      try
      {
        this.buffers.clear();
        return;
      }
      finally {}
    }
    
    public void onComplete()
    {
      try
      {
        Object localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>(this.buffers);
        this.buffers.clear();
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          Collection localCollection = (Collection)((Iterator)localObject1).next();
          this.queue.offer(localCollection);
        }
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this.w, this);
        }
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.done = true;
      this.w.dispose();
      clear();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Iterator localIterator = this.buffers.iterator();
        while (localIterator.hasNext()) {
          ((Collection)localIterator.next()).add(paramT);
        }
        return;
      }
      finally {}
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (!SubscriptionHelper.validate(this.upstream, paramSubscription)) {
        return;
      }
      this.upstream = paramSubscription;
      try
      {
        Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
        this.buffers.add(localCollection);
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
        paramSubscription = this.w;
        long l = this.timeskip;
        paramSubscription.schedulePeriodically(this, l, l, this.unit);
        this.w.schedule(new RemoveFromBuffer(localCollection), this.timespan, this.unit);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.w.dispose();
        paramSubscription.cancel();
        EmptySubscription.error(localThrowable, this.downstream);
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    public void run()
    {
      if (this.cancelled) {
        return;
      }
      try
      {
        Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
        try
        {
          if (this.cancelled) {
            return;
          }
          this.buffers.add(localCollection);
          this.w.schedule(new RemoveFromBuffer(localCollection), this.timespan, this.unit);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        cancel();
      }
    }
    
    final class RemoveFromBuffer
      implements Runnable
    {
      private final U buffer;
      
      RemoveFromBuffer()
      {
        Object localObject;
        this.buffer = localObject;
      }
      
      public void run()
      {
        synchronized (FlowableBufferTimed.BufferSkipBoundedSubscriber.this)
        {
          FlowableBufferTimed.BufferSkipBoundedSubscriber.this.buffers.remove(this.buffer);
          ??? = FlowableBufferTimed.BufferSkipBoundedSubscriber.this;
          ???.fastPathOrderedEmitMax(this.buffer, false, ???.w);
          return;
        }
      }
    }
  }
}
