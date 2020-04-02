package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class UnicastProcessor<T>
  extends FlowableProcessor<T>
{
  volatile boolean cancelled;
  final boolean delayError;
  volatile boolean done;
  final AtomicReference<Subscriber<? super T>> downstream;
  boolean enableOperatorFusion;
  Throwable error;
  final AtomicReference<Runnable> onTerminate;
  final AtomicBoolean once;
  final SpscLinkedArrayQueue<T> queue;
  final AtomicLong requested;
  final BasicIntQueueSubscription<T> wip;
  
  UnicastProcessor(int paramInt)
  {
    this(paramInt, null, true);
  }
  
  UnicastProcessor(int paramInt, Runnable paramRunnable)
  {
    this(paramInt, paramRunnable, true);
  }
  
  UnicastProcessor(int paramInt, Runnable paramRunnable, boolean paramBoolean)
  {
    this.queue = new SpscLinkedArrayQueue(ObjectHelper.verifyPositive(paramInt, "capacityHint"));
    this.onTerminate = new AtomicReference(paramRunnable);
    this.delayError = paramBoolean;
    this.downstream = new AtomicReference();
    this.once = new AtomicBoolean();
    this.wip = new UnicastQueueSubscription();
    this.requested = new AtomicLong();
  }
  
  @CheckReturnValue
  public static <T> UnicastProcessor<T> create()
  {
    return new UnicastProcessor(bufferSize());
  }
  
  @CheckReturnValue
  public static <T> UnicastProcessor<T> create(int paramInt)
  {
    return new UnicastProcessor(paramInt);
  }
  
  @CheckReturnValue
  public static <T> UnicastProcessor<T> create(int paramInt, Runnable paramRunnable)
  {
    ObjectHelper.requireNonNull(paramRunnable, "onTerminate");
    return new UnicastProcessor(paramInt, paramRunnable);
  }
  
  @CheckReturnValue
  public static <T> UnicastProcessor<T> create(int paramInt, Runnable paramRunnable, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramRunnable, "onTerminate");
    return new UnicastProcessor(paramInt, paramRunnable, paramBoolean);
  }
  
  @CheckReturnValue
  public static <T> UnicastProcessor<T> create(boolean paramBoolean)
  {
    return new UnicastProcessor(bufferSize(), null, paramBoolean);
  }
  
  boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Subscriber<? super T> paramSubscriber, SpscLinkedArrayQueue<T> paramSpscLinkedArrayQueue)
  {
    if (this.cancelled)
    {
      paramSpscLinkedArrayQueue.clear();
      this.downstream.lazySet(null);
      return true;
    }
    if (paramBoolean2)
    {
      if ((paramBoolean1) && (this.error != null))
      {
        paramSpscLinkedArrayQueue.clear();
        this.downstream.lazySet(null);
        paramSubscriber.onError(this.error);
        return true;
      }
      if (paramBoolean3)
      {
        paramSpscLinkedArrayQueue = this.error;
        this.downstream.lazySet(null);
        if (paramSpscLinkedArrayQueue != null) {
          paramSubscriber.onError(paramSpscLinkedArrayQueue);
        } else {
          paramSubscriber.onComplete();
        }
        return true;
      }
    }
    return false;
  }
  
  void doTerminate()
  {
    Runnable localRunnable = (Runnable)this.onTerminate.getAndSet(null);
    if (localRunnable != null) {
      localRunnable.run();
    }
  }
  
  void drain()
  {
    if (this.wip.getAndIncrement() != 0) {
      return;
    }
    int i = 1;
    for (Subscriber localSubscriber = (Subscriber)this.downstream.get();; localSubscriber = (Subscriber)this.downstream.get())
    {
      if (localSubscriber != null)
      {
        if (this.enableOperatorFusion) {
          drainFused(localSubscriber);
        } else {
          drainRegular(localSubscriber);
        }
        return;
      }
      i = this.wip.addAndGet(-i);
      if (i == 0) {
        return;
      }
    }
  }
  
  void drainFused(Subscriber<? super T> paramSubscriber)
  {
    Object localObject = this.queue;
    boolean bool1 = this.delayError;
    int i = 1;
    int j;
    do
    {
      if (this.cancelled)
      {
        ((SpscLinkedArrayQueue)localObject).clear();
        this.downstream.lazySet(null);
        return;
      }
      boolean bool2 = this.done;
      if (((bool1 ^ true)) && (bool2) && (this.error != null))
      {
        ((SpscLinkedArrayQueue)localObject).clear();
        this.downstream.lazySet(null);
        paramSubscriber.onError(this.error);
        return;
      }
      paramSubscriber.onNext(null);
      if (bool2)
      {
        this.downstream.lazySet(null);
        localObject = this.error;
        if (localObject != null) {
          paramSubscriber.onError((Throwable)localObject);
        } else {
          paramSubscriber.onComplete();
        }
        return;
      }
      j = this.wip.addAndGet(-i);
      i = j;
    } while (j != 0);
  }
  
  void drainRegular(Subscriber<? super T> paramSubscriber)
  {
    SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
    boolean bool1 = this.delayError ^ true;
    int i = 1;
    for (;;)
    {
      long l1 = this.requested.get();
      boolean bool2;
      for (long l2 = 0L;; l2 = 1L + l2)
      {
        bool2 = l1 < l2;
        if (!bool2) {
          break;
        }
        boolean bool3 = this.done;
        Object localObject = localSpscLinkedArrayQueue.poll();
        boolean bool4;
        if (localObject == null) {
          bool4 = true;
        } else {
          bool4 = false;
        }
        if (checkTerminated(bool1, bool3, bool4, paramSubscriber, localSpscLinkedArrayQueue)) {
          return;
        }
        if (bool4) {
          break;
        }
        paramSubscriber.onNext(localObject);
      }
      if ((!bool2) && (checkTerminated(bool1, this.done, localSpscLinkedArrayQueue.isEmpty(), paramSubscriber, localSpscLinkedArrayQueue))) {
        return;
      }
      if ((l2 != 0L) && (l1 != Long.MAX_VALUE)) {
        this.requested.addAndGet(-l2);
      }
      i = this.wip.addAndGet(-i);
      if (i == 0) {
        return;
      }
    }
  }
  
  public Throwable getThrowable()
  {
    if (this.done) {
      return this.error;
    }
    return null;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.done) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasSubscribers()
  {
    boolean bool;
    if (this.downstream.get() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.done) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    if ((!this.done) && (!this.cancelled))
    {
      this.done = true;
      doTerminate();
      drain();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    if ((!this.done) && (!this.cancelled))
    {
      this.error = paramThrowable;
      this.done = true;
      doTerminate();
      drain();
      return;
    }
    RxJavaPlugins.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if ((!this.done) && (!this.cancelled))
    {
      this.queue.offer(paramT);
      drain();
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if ((!this.done) && (!this.cancelled)) {
      paramSubscription.request(Long.MAX_VALUE);
    } else {
      paramSubscription.cancel();
    }
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((!this.once.get()) && (this.once.compareAndSet(false, true)))
    {
      paramSubscriber.onSubscribe(this.wip);
      this.downstream.set(paramSubscriber);
      if (this.cancelled) {
        this.downstream.lazySet(null);
      } else {
        drain();
      }
    }
    else
    {
      EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), paramSubscriber);
    }
  }
  
  final class UnicastQueueSubscription
    extends BasicIntQueueSubscription<T>
  {
    private static final long serialVersionUID = -4896760517184205454L;
    
    UnicastQueueSubscription() {}
    
    public void cancel()
    {
      if (UnicastProcessor.this.cancelled) {
        return;
      }
      UnicastProcessor.this.cancelled = true;
      UnicastProcessor.this.doTerminate();
      if ((!UnicastProcessor.this.enableOperatorFusion) && (UnicastProcessor.this.wip.getAndIncrement() == 0))
      {
        UnicastProcessor.this.queue.clear();
        UnicastProcessor.this.downstream.lazySet(null);
      }
    }
    
    public void clear()
    {
      UnicastProcessor.this.queue.clear();
    }
    
    public boolean isEmpty()
    {
      return UnicastProcessor.this.queue.isEmpty();
    }
    
    public T poll()
    {
      return UnicastProcessor.this.queue.poll();
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(UnicastProcessor.this.requested, paramLong);
        UnicastProcessor.this.drain();
      }
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        UnicastProcessor.this.enableOperatorFusion = true;
        return 2;
      }
      return 0;
    }
  }
}
