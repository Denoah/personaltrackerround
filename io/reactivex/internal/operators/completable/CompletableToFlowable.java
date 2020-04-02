package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.internal.observers.SubscriberCompletableObserver;
import org.reactivestreams.Subscriber;

public final class CompletableToFlowable<T>
  extends Flowable<T>
{
  final CompletableSource source;
  
  public CompletableToFlowable(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    paramSubscriber = new SubscriberCompletableObserver(paramSubscriber);
    this.source.subscribe(paramSubscriber);
  }
}
