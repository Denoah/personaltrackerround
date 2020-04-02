package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeoutTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Publisher<? extends T> other;
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public FlowableTimeoutTimed(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, Publisher<? extends T> paramPublisher)
  {
    super(paramFlowable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject;
    if (this.other == null)
    {
      localObject = new TimeoutSubscriber(paramSubscriber, this.timeout, this.unit, this.scheduler.createWorker());
      paramSubscriber.onSubscribe((Subscription)localObject);
      ((TimeoutSubscriber)localObject).startTimeout(0L);
      this.source.subscribe((FlowableSubscriber)localObject);
    }
    else
    {
      localObject = new TimeoutFallbackSubscriber(paramSubscriber, this.timeout, this.unit, this.scheduler.createWorker(), this.other);
      paramSubscriber.onSubscribe((Subscription)localObject);
      ((TimeoutFallbackSubscriber)localObject).startTimeout(0L);
      this.source.subscribe((FlowableSubscriber)localObject);
    }
  }
  
  static final class FallbackSubscriber<T>
    implements FlowableSubscriber<T>
  {
    final SubscriptionArbiter arbiter;
    final Subscriber<? super T> downstream;
    
    FallbackSubscriber(Subscriber<? super T> paramSubscriber, SubscriptionArbiter paramSubscriptionArbiter)
    {
      this.downstream = paramSubscriber;
      this.arbiter = paramSubscriptionArbiter;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.arbiter.setSubscription(paramSubscription);
    }
  }
  
  static final class TimeoutFallbackSubscriber<T>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T>, FlowableTimeoutTimed.TimeoutSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    long consumed;
    final Subscriber<? super T> downstream;
    Publisher<? extends T> fallback;
    final AtomicLong index;
    final SequentialDisposable task;
    final long timeout;
    final TimeUnit unit;
    final AtomicReference<Subscription> upstream;
    final Scheduler.Worker worker;
    
    TimeoutFallbackSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, Publisher<? extends T> paramPublisher)
    {
      super();
      this.downstream = paramSubscriber;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.fallback = paramPublisher;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
      this.index = new AtomicLong();
    }
    
    public void cancel()
    {
      super.cancel();
      this.worker.dispose();
    }
    
    public void onComplete()
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.worker.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.worker.dispose();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = this.index.get();
      if (l1 != Long.MAX_VALUE)
      {
        AtomicLong localAtomicLong = this.index;
        long l2 = l1 + 1L;
        if (localAtomicLong.compareAndSet(l1, l2))
        {
          ((Disposable)this.task.get()).dispose();
          this.consumed += 1L;
          this.downstream.onNext(paramT);
          startTimeout(l2);
        }
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this.upstream, paramSubscription)) {
        setSubscription(paramSubscription);
      }
    }
    
    public void onTimeout(long paramLong)
    {
      if (this.index.compareAndSet(paramLong, Long.MAX_VALUE))
      {
        SubscriptionHelper.cancel(this.upstream);
        paramLong = this.consumed;
        if (paramLong != 0L) {
          produced(paramLong);
        }
        Publisher localPublisher = this.fallback;
        this.fallback = null;
        localPublisher.subscribe(new FlowableTimeoutTimed.FallbackSubscriber(this.downstream, this));
        this.worker.dispose();
      }
    }
    
    void startTimeout(long paramLong)
    {
      this.task.replace(this.worker.schedule(new FlowableTimeoutTimed.TimeoutTask(paramLong, this), this.timeout, this.unit));
    }
  }
  
  static final class TimeoutSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription, FlowableTimeoutTimed.TimeoutSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    final Subscriber<? super T> downstream;
    final AtomicLong requested;
    final SequentialDisposable task;
    final long timeout;
    final TimeUnit unit;
    final AtomicReference<Subscription> upstream;
    final Scheduler.Worker worker;
    
    TimeoutSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      this.downstream = paramSubscriber;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      this.worker.dispose();
    }
    
    public void onComplete()
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.worker.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.worker.dispose();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = get();
      if (l1 != Long.MAX_VALUE)
      {
        long l2 = 1L + l1;
        if (compareAndSet(l1, l2))
        {
          ((Disposable)this.task.get()).dispose();
          this.downstream.onNext(paramT);
          startTimeout(l2);
        }
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, paramSubscription);
    }
    
    public void onTimeout(long paramLong)
    {
      if (compareAndSet(paramLong, Long.MAX_VALUE))
      {
        SubscriptionHelper.cancel(this.upstream);
        this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
        this.worker.dispose();
      }
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    void startTimeout(long paramLong)
    {
      this.task.replace(this.worker.schedule(new FlowableTimeoutTimed.TimeoutTask(paramLong, this), this.timeout, this.unit));
    }
  }
  
  static abstract interface TimeoutSupport
  {
    public abstract void onTimeout(long paramLong);
  }
  
  static final class TimeoutTask
    implements Runnable
  {
    final long idx;
    final FlowableTimeoutTimed.TimeoutSupport parent;
    
    TimeoutTask(long paramLong, FlowableTimeoutTimed.TimeoutSupport paramTimeoutSupport)
    {
      this.idx = paramLong;
      this.parent = paramTimeoutSupport;
    }
    
    public void run()
    {
      this.parent.onTimeout(this.idx);
    }
  }
}
