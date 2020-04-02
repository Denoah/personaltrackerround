package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelaySubscriptionOther<T, U>
  extends Flowable<T>
{
  final Publisher<? extends T> main;
  final Publisher<U> other;
  
  public FlowableDelaySubscriptionOther(Publisher<? extends T> paramPublisher, Publisher<U> paramPublisher1)
  {
    this.main = paramPublisher;
    this.other = paramPublisher1;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    MainSubscriber localMainSubscriber = new MainSubscriber(paramSubscriber, this.main);
    paramSubscriber.onSubscribe(localMainSubscriber);
    this.other.subscribe(localMainSubscriber.other);
  }
  
  static final class MainSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 2259811067697317255L;
    final Subscriber<? super T> downstream;
    final Publisher<? extends T> main;
    final MainSubscriber<T>.OtherSubscriber other;
    final AtomicReference<Subscription> upstream;
    
    MainSubscriber(Subscriber<? super T> paramSubscriber, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.main = paramPublisher;
      this.other = new OtherSubscriber();
      this.upstream = new AtomicReference();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.other);
      SubscriptionHelper.cancel(this.upstream);
    }
    
    void next()
    {
      this.main.subscribe(this);
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
      SubscriptionHelper.deferredSetOnce(this.upstream, this, paramSubscription);
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        SubscriptionHelper.deferredRequest(this.upstream, this, paramLong);
      }
    }
    
    final class OtherSubscriber
      extends AtomicReference<Subscription>
      implements FlowableSubscriber<Object>
    {
      private static final long serialVersionUID = -3892798459447644106L;
      
      OtherSubscriber() {}
      
      public void onComplete()
      {
        if ((Subscription)get() != SubscriptionHelper.CANCELLED) {
          FlowableDelaySubscriptionOther.MainSubscriber.this.next();
        }
      }
      
      public void onError(Throwable paramThrowable)
      {
        if ((Subscription)get() != SubscriptionHelper.CANCELLED) {
          FlowableDelaySubscriptionOther.MainSubscriber.this.downstream.onError(paramThrowable);
        } else {
          RxJavaPlugins.onError(paramThrowable);
        }
      }
      
      public void onNext(Object paramObject)
      {
        paramObject = (Subscription)get();
        if (paramObject != SubscriptionHelper.CANCELLED)
        {
          lazySet(SubscriptionHelper.CANCELLED);
          paramObject.cancel();
          FlowableDelaySubscriptionOther.MainSubscriber.this.next();
        }
      }
      
      public void onSubscribe(Subscription paramSubscription)
      {
        if (SubscriptionHelper.setOnce(this, paramSubscription)) {
          paramSubscription.request(Long.MAX_VALUE);
        }
      }
    }
  }
}
