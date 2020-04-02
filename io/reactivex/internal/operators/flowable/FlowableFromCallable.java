package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableFromCallable<T>
  extends Flowable<T>
  implements Callable<T>
{
  final Callable<? extends T> callable;
  
  public FlowableFromCallable(Callable<? extends T> paramCallable)
  {
    this.callable = paramCallable;
  }
  
  public T call()
    throws Exception
  {
    return ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    DeferredScalarSubscription localDeferredScalarSubscription = new DeferredScalarSubscription(paramSubscriber);
    paramSubscriber.onSubscribe(localDeferredScalarSubscription);
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
      localDeferredScalarSubscription.complete(localObject);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      if (localDeferredScalarSubscription.isCancelled()) {
        RxJavaPlugins.onError(localThrowable);
      } else {
        paramSubscriber.onError(localThrowable);
      }
    }
  }
}
