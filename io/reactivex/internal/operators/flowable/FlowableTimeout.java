package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeout<T, U, V>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Publisher<U> firstTimeoutIndicator;
  final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;
  final Publisher<? extends T> other;
  
  public FlowableTimeout(Flowable<T> paramFlowable, Publisher<U> paramPublisher, Function<? super T, ? extends Publisher<V>> paramFunction, Publisher<? extends T> paramPublisher1)
  {
    super(paramFlowable);
    this.firstTimeoutIndicator = paramPublisher;
    this.itemTimeoutIndicator = paramFunction;
    this.other = paramPublisher1;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject;
    if (this.other == null)
    {
      localObject = new TimeoutSubscriber(paramSubscriber, this.itemTimeoutIndicator);
      paramSubscriber.onSubscribe((Subscription)localObject);
      ((TimeoutSubscriber)localObject).startFirstTimeout(this.firstTimeoutIndicator);
      this.source.subscribe((FlowableSubscriber)localObject);
    }
    else
    {
      localObject = new TimeoutFallbackSubscriber(paramSubscriber, this.itemTimeoutIndicator, this.other);
      paramSubscriber.onSubscribe((Subscription)localObject);
      ((TimeoutFallbackSubscriber)localObject).startFirstTimeout(this.firstTimeoutIndicator);
      this.source.subscribe((FlowableSubscriber)localObject);
    }
  }
  
  static final class TimeoutConsumer
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<Object>, Disposable
  {
    private static final long serialVersionUID = 8708641127342403073L;
    final long idx;
    final FlowableTimeout.TimeoutSelectorSupport parent;
    
    TimeoutConsumer(long paramLong, FlowableTimeout.TimeoutSelectorSupport paramTimeoutSelectorSupport)
    {
      this.idx = paramLong;
      this.parent = paramTimeoutSelectorSupport;
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (get() != SubscriptionHelper.CANCELLED)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.onTimeout(this.idx);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get() != SubscriptionHelper.CANCELLED)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.onTimeoutError(this.idx, paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(Object paramObject)
    {
      paramObject = (Subscription)get();
      if (paramObject != SubscriptionHelper.CANCELLED)
      {
        paramObject.cancel();
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.onTimeout(this.idx);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
  
  static final class TimeoutFallbackSubscriber<T>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T>, FlowableTimeout.TimeoutSelectorSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    long consumed;
    final Subscriber<? super T> downstream;
    Publisher<? extends T> fallback;
    final AtomicLong index;
    final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
    final SequentialDisposable task;
    final AtomicReference<Subscription> upstream;
    
    TimeoutFallbackSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, ? extends Publisher<?>> paramFunction, Publisher<? extends T> paramPublisher)
    {
      super();
      this.downstream = paramSubscriber;
      this.itemTimeoutIndicator = paramFunction;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
      this.fallback = paramPublisher;
      this.index = new AtomicLong();
    }
    
    public void cancel()
    {
      super.cancel();
      this.task.dispose();
    }
    
    public void onComplete()
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.task.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.task.dispose();
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
        Object localObject = this.index;
        long l2 = l1 + 1L;
        if (((AtomicLong)localObject).compareAndSet(l1, l2))
        {
          localObject = (Disposable)this.task.get();
          if (localObject != null) {
            ((Disposable)localObject).dispose();
          }
          this.consumed += 1L;
          this.downstream.onNext(paramT);
          try
          {
            paramT = (Publisher)ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(paramT), "The itemTimeoutIndicator returned a null Publisher.");
            localObject = new FlowableTimeout.TimeoutConsumer(l2, this);
            if (this.task.replace((Disposable)localObject)) {
              paramT.subscribe((Subscriber)localObject);
            }
            return;
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            ((Subscription)this.upstream.get()).cancel();
            this.index.getAndSet(Long.MAX_VALUE);
            this.downstream.onError(paramT);
          }
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
        Publisher localPublisher = this.fallback;
        this.fallback = null;
        paramLong = this.consumed;
        if (paramLong != 0L) {
          produced(paramLong);
        }
        localPublisher.subscribe(new FlowableTimeoutTimed.FallbackSubscriber(this.downstream, this));
      }
    }
    
    public void onTimeoutError(long paramLong, Throwable paramThrowable)
    {
      if (this.index.compareAndSet(paramLong, Long.MAX_VALUE))
      {
        SubscriptionHelper.cancel(this.upstream);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void startFirstTimeout(Publisher<?> paramPublisher)
    {
      if (paramPublisher != null)
      {
        FlowableTimeout.TimeoutConsumer localTimeoutConsumer = new FlowableTimeout.TimeoutConsumer(0L, this);
        if (this.task.replace(localTimeoutConsumer)) {
          paramPublisher.subscribe(localTimeoutConsumer);
        }
      }
    }
  }
  
  static abstract interface TimeoutSelectorSupport
    extends FlowableTimeoutTimed.TimeoutSupport
  {
    public abstract void onTimeoutError(long paramLong, Throwable paramThrowable);
  }
  
  static final class TimeoutSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription, FlowableTimeout.TimeoutSelectorSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    final Subscriber<? super T> downstream;
    final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
    final AtomicLong requested;
    final SequentialDisposable task;
    final AtomicReference<Subscription> upstream;
    
    TimeoutSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, ? extends Publisher<?>> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.itemTimeoutIndicator = paramFunction;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      this.task.dispose();
    }
    
    public void onComplete()
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
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
          Object localObject = (Disposable)this.task.get();
          if (localObject != null) {
            ((Disposable)localObject).dispose();
          }
          this.downstream.onNext(paramT);
          try
          {
            paramT = (Publisher)ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(paramT), "The itemTimeoutIndicator returned a null Publisher.");
            localObject = new FlowableTimeout.TimeoutConsumer(l2, this);
            if (this.task.replace((Disposable)localObject)) {
              paramT.subscribe((Subscriber)localObject);
            }
            return;
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            ((Subscription)this.upstream.get()).cancel();
            getAndSet(Long.MAX_VALUE);
            this.downstream.onError(paramT);
          }
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
        this.downstream.onError(new TimeoutException());
      }
    }
    
    public void onTimeoutError(long paramLong, Throwable paramThrowable)
    {
      if (compareAndSet(paramLong, Long.MAX_VALUE))
      {
        SubscriptionHelper.cancel(this.upstream);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    void startFirstTimeout(Publisher<?> paramPublisher)
    {
      if (paramPublisher != null)
      {
        FlowableTimeout.TimeoutConsumer localTimeoutConsumer = new FlowableTimeout.TimeoutConsumer(0L, this);
        if (this.task.replace(localTimeoutConsumer)) {
          paramPublisher.subscribe(localTimeoutConsumer);
        }
      }
    }
  }
}
