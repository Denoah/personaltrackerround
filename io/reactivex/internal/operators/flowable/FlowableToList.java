package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableToList<T, U extends Collection<? super T>>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Callable<U> collectionSupplier;
  
  public FlowableToList(Flowable<T> paramFlowable, Callable<U> paramCallable)
  {
    super(paramFlowable);
    this.collectionSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new ToListSubscriber(paramSubscriber, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class ToListSubscriber<T, U extends Collection<? super T>>
    extends DeferredScalarSubscription<U>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -8134157938864266736L;
    Subscription upstream;
    
    ToListSubscriber(Subscriber<? super U> paramSubscriber, U paramU)
    {
      super();
      this.value = paramU;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      complete(this.value);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      Collection localCollection = (Collection)this.value;
      if (localCollection != null) {
        localCollection.add(paramT);
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
