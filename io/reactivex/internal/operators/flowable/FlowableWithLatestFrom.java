package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFrom<T, U, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final BiFunction<? super T, ? super U, ? extends R> combiner;
  final Publisher<? extends U> other;
  
  public FlowableWithLatestFrom(Flowable<T> paramFlowable, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, Publisher<? extends U> paramPublisher)
  {
    super(paramFlowable);
    this.combiner = paramBiFunction;
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    paramSubscriber = new SerializedSubscriber(paramSubscriber);
    WithLatestFromSubscriber localWithLatestFromSubscriber = new WithLatestFromSubscriber(paramSubscriber, this.combiner);
    paramSubscriber.onSubscribe(localWithLatestFromSubscriber);
    this.other.subscribe(new FlowableWithLatestSubscriber(localWithLatestFromSubscriber));
    this.source.subscribe(localWithLatestFromSubscriber);
  }
  
  final class FlowableWithLatestSubscriber
    implements FlowableSubscriber<U>
  {
    private final FlowableWithLatestFrom.WithLatestFromSubscriber<T, U, R> wlf;
    
    FlowableWithLatestSubscriber()
    {
      Object localObject;
      this.wlf = localObject;
    }
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable)
    {
      this.wlf.otherError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      this.wlf.lazySet(paramU);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (this.wlf.setOther(paramSubscription)) {
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
  }
  
  static final class WithLatestFromSubscriber<T, U, R>
    extends AtomicReference<U>
    implements ConditionalSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -312246233408980075L;
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final Subscriber<? super R> downstream;
    final AtomicReference<Subscription> other = new AtomicReference();
    final AtomicLong requested = new AtomicLong();
    final AtomicReference<Subscription> upstream = new AtomicReference();
    
    WithLatestFromSubscriber(Subscriber<? super R> paramSubscriber, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.combiner = paramBiFunction;
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      SubscriptionHelper.cancel(this.other);
    }
    
    public void onComplete()
    {
      SubscriptionHelper.cancel(this.other);
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.other);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (!tryOnNext(paramT)) {
        ((Subscription)this.upstream.get()).request(1L);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, paramSubscription);
    }
    
    public void otherError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.upstream);
      this.downstream.onError(paramThrowable);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    public boolean setOther(Subscription paramSubscription)
    {
      return SubscriptionHelper.setOnce(this.other, paramSubscription);
    }
    
    public boolean tryOnNext(T paramT)
    {
      Object localObject = get();
      if (localObject != null) {
        try
        {
          paramT = ObjectHelper.requireNonNull(this.combiner.apply(paramT, localObject), "The combiner returned a null value");
          this.downstream.onNext(paramT);
          return true;
        }
        finally
        {
          Exceptions.throwIfFatal(paramT);
          cancel();
          this.downstream.onError(paramT);
        }
      }
      return false;
    }
  }
}
