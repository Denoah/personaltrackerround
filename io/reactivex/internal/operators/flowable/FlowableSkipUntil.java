package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipUntil<T, U>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Publisher<U> other;
  
  public FlowableSkipUntil(Flowable<T> paramFlowable, Publisher<U> paramPublisher)
  {
    super(paramFlowable);
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SkipUntilMainSubscriber localSkipUntilMainSubscriber = new SkipUntilMainSubscriber(paramSubscriber);
    paramSubscriber.onSubscribe(localSkipUntilMainSubscriber);
    this.other.subscribe(localSkipUntilMainSubscriber.other);
    this.source.subscribe(localSkipUntilMainSubscriber);
  }
  
  static final class SkipUntilMainSubscriber<T>
    extends AtomicInteger
    implements ConditionalSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -6270983465606289181L;
    final Subscriber<? super T> downstream;
    final AtomicThrowable error;
    volatile boolean gate;
    final SkipUntilMainSubscriber<T>.OtherSubscriber other;
    final AtomicLong requested;
    final AtomicReference<Subscription> upstream;
    
    SkipUntilMainSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
      this.other = new OtherSubscriber();
      this.error = new AtomicThrowable();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      SubscriptionHelper.cancel(this.other);
    }
    
    public void onComplete()
    {
      SubscriptionHelper.cancel(this.other);
      HalfSerializer.onComplete(this.downstream, this, this.error);
    }
    
    public void onError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.other);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
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
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (this.gate)
      {
        HalfSerializer.onNext(this.downstream, paramT, this, this.error);
        return true;
      }
      return false;
    }
    
    final class OtherSubscriber
      extends AtomicReference<Subscription>
      implements FlowableSubscriber<Object>
    {
      private static final long serialVersionUID = -5592042965931999169L;
      
      OtherSubscriber() {}
      
      public void onComplete()
      {
        FlowableSkipUntil.SkipUntilMainSubscriber.this.gate = true;
      }
      
      public void onError(Throwable paramThrowable)
      {
        SubscriptionHelper.cancel(FlowableSkipUntil.SkipUntilMainSubscriber.this.upstream);
        Subscriber localSubscriber = FlowableSkipUntil.SkipUntilMainSubscriber.this.downstream;
        FlowableSkipUntil.SkipUntilMainSubscriber localSkipUntilMainSubscriber = FlowableSkipUntil.SkipUntilMainSubscriber.this;
        HalfSerializer.onError(localSubscriber, paramThrowable, localSkipUntilMainSubscriber, localSkipUntilMainSubscriber.error);
      }
      
      public void onNext(Object paramObject)
      {
        FlowableSkipUntil.SkipUntilMainSubscriber.this.gate = true;
        ((Subscription)get()).cancel();
      }
      
      public void onSubscribe(Subscription paramSubscription)
      {
        SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
      }
    }
  }
}
