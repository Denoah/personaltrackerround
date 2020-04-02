package io.reactivex.internal.operators.parallel;

import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelFromArray<T>
  extends ParallelFlowable<T>
{
  final Publisher<T>[] sources;
  
  public ParallelFromArray(Publisher<T>[] paramArrayOfPublisher)
  {
    this.sources = paramArrayOfPublisher;
  }
  
  public int parallelism()
  {
    return this.sources.length;
  }
  
  public void subscribe(Subscriber<? super T>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    for (int j = 0; j < i; j++) {
      this.sources[j].subscribe(paramArrayOfSubscriber[j]);
    }
  }
}
