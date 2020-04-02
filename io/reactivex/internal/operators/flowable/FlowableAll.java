package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAll<T>
  extends AbstractFlowableWithUpstream<T, Boolean>
{
  final Predicate<? super T> predicate;
  
  public FlowableAll(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super Boolean> paramSubscriber)
  {
    this.source.subscribe(new AllSubscriber(paramSubscriber, this.predicate));
  }
  
  static final class AllSubscriber<T>
    extends DeferredScalarSubscription<Boolean>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -3521127104134758517L;
    boolean done;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    AllSubscriber(Subscriber<? super Boolean> paramSubscriber, Predicate<? super T> paramPredicate)
    {
      super();
      this.predicate = paramPredicate;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      complete(Boolean.valueOf(true));
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (!bool)
        {
          this.done = true;
          this.upstream.cancel();
          complete(Boolean.valueOf(false));
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
  }
}
