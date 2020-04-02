package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDematerialize<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final Function<? super T, ? extends Notification<R>> selector;
  
  public FlowableDematerialize(Flowable<T> paramFlowable, Function<? super T, ? extends Notification<R>> paramFunction)
  {
    super(paramFlowable);
    this.selector = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new DematerializeSubscriber(paramSubscriber, this.selector));
  }
  
  static final class DematerializeSubscriber<T, R>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super R> downstream;
    final Function<? super T, ? extends Notification<R>> selector;
    Subscription upstream;
    
    DematerializeSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Notification<R>> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.selector = paramFunction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
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
      if (this.done)
      {
        if ((paramT instanceof Notification))
        {
          paramT = (Notification)paramT;
          if (paramT.isOnError()) {
            RxJavaPlugins.onError(paramT.getError());
          }
        }
        return;
      }
      try
      {
        paramT = (Notification)ObjectHelper.requireNonNull(this.selector.apply(paramT), "The selector returned a null Notification");
        if (paramT.isOnError())
        {
          this.upstream.cancel();
          onError(paramT.getError());
        }
        else if (paramT.isOnComplete())
        {
          this.upstream.cancel();
          onComplete();
        }
        else
        {
          this.downstream.onNext(paramT.getValue());
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
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
