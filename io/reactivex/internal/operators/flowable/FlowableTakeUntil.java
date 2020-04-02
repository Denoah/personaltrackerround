package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeUntil<T, U>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Publisher<? extends U> other;
  
  public FlowableTakeUntil(Flowable<T> paramFlowable, Publisher<? extends U> paramPublisher)
  {
    super(paramFlowable);
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    TakeUntilMainSubscriber localTakeUntilMainSubscriber = new TakeUntilMainSubscriber(paramSubscriber);
    paramSubscriber.onSubscribe(localTakeUntilMainSubscriber);
    this.other.subscribe(localTakeUntilMainSubscriber.other);
    this.source.subscribe(localTakeUntilMainSubscriber);
  }
  
  static final class TakeUntilMainSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -4945480365982832967L;
    final Subscriber<? super T> downstream;
    final AtomicThrowable error;
    final TakeUntilMainSubscriber<T>.OtherSubscriber other;
    final AtomicLong requested;
    final AtomicReference<Subscription> upstream;
    
    TakeUntilMainSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
      this.requested = new AtomicLong();
      this.upstream = new AtomicReference();
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
      HalfSerializer.onNext(this.downstream, paramT, this, this.error);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, paramSubscription);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    final class OtherSubscriber
      extends AtomicReference<Subscription>
      implements FlowableSubscriber<Object>
    {
      private static final long serialVersionUID = -3592821756711087922L;
      
      OtherSubscriber() {}
      
      public void onComplete()
      {
        SubscriptionHelper.cancel(FlowableTakeUntil.TakeUntilMainSubscriber.this.upstream);
        Subscriber localSubscriber = FlowableTakeUntil.TakeUntilMainSubscriber.this.downstream;
        FlowableTakeUntil.TakeUntilMainSubscriber localTakeUntilMainSubscriber = FlowableTakeUntil.TakeUntilMainSubscriber.this;
        HalfSerializer.onComplete(localSubscriber, localTakeUntilMainSubscriber, localTakeUntilMainSubscriber.error);
      }
      
      public void onError(Throwable paramThrowable)
      {
        SubscriptionHelper.cancel(FlowableTakeUntil.TakeUntilMainSubscriber.this.upstream);
        Subscriber localSubscriber = FlowableTakeUntil.TakeUntilMainSubscriber.this.downstream;
        FlowableTakeUntil.TakeUntilMainSubscriber localTakeUntilMainSubscriber = FlowableTakeUntil.TakeUntilMainSubscriber.this;
        HalfSerializer.onError(localSubscriber, paramThrowable, localTakeUntilMainSubscriber, localTakeUntilMainSubscriber.error);
      }
      
      public void onNext(Object paramObject)
      {
        SubscriptionHelper.cancel(this);
        onComplete();
      }
      
      public void onSubscribe(Subscription paramSubscription)
      {
        SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
      }
    }
  }
}
