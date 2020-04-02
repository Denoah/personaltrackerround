package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

public final class FlowableFromFuture<T>
  extends Flowable<T>
{
  final Future<? extends T> future;
  final long timeout;
  final TimeUnit unit;
  
  public FlowableFromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    this.future = paramFuture;
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    DeferredScalarSubscription localDeferredScalarSubscription = new DeferredScalarSubscription(paramSubscriber);
    paramSubscriber.onSubscribe(localDeferredScalarSubscription);
    try
    {
      Object localObject;
      if (this.unit != null) {
        localObject = this.future.get(this.timeout, this.unit);
      } else {
        localObject = this.future.get();
      }
      if (localObject == null) {
        paramSubscriber.onError(new NullPointerException("The future returned null"));
      } else {
        localDeferredScalarSubscription.complete(localObject);
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      if (!localDeferredScalarSubscription.isCancelled()) {
        paramSubscriber.onError(localThrowable);
      }
    }
  }
}
