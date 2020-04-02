package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchIfEmpty<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Publisher<? extends T> other;
  
  public FlowableSwitchIfEmpty(Flowable<T> paramFlowable, Publisher<? extends T> paramPublisher)
  {
    super(paramFlowable);
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SwitchIfEmptySubscriber localSwitchIfEmptySubscriber = new SwitchIfEmptySubscriber(paramSubscriber, this.other);
    paramSubscriber.onSubscribe(localSwitchIfEmptySubscriber.arbiter);
    this.source.subscribe(localSwitchIfEmptySubscriber);
  }
  
  static final class SwitchIfEmptySubscriber<T>
    implements FlowableSubscriber<T>
  {
    final SubscriptionArbiter arbiter;
    final Subscriber<? super T> downstream;
    boolean empty;
    final Publisher<? extends T> other;
    
    SwitchIfEmptySubscriber(Subscriber<? super T> paramSubscriber, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.other = paramPublisher;
      this.empty = true;
      this.arbiter = new SubscriptionArbiter(false);
    }
    
    public void onComplete()
    {
      if (this.empty)
      {
        this.empty = false;
        this.other.subscribe(this);
      }
      else
      {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.empty) {
        this.empty = false;
      }
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.arbiter.setSubscription(paramSubscription);
    }
  }
}
