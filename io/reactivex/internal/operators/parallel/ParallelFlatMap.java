package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelFlatMap<T, R>
  extends ParallelFlowable<R>
{
  final boolean delayError;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final int maxConcurrency;
  final int prefetch;
  final ParallelFlowable<T> source;
  
  public ParallelFlatMap(ParallelFlowable<T> paramParallelFlowable, Function<? super T, ? extends Publisher<? extends R>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.source = paramParallelFlowable;
    this.mapper = paramFunction;
    this.delayError = paramBoolean;
    this.maxConcurrency = paramInt1;
    this.prefetch = paramInt2;
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  public void subscribe(Subscriber<? super R>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    Subscriber[] arrayOfSubscriber = new Subscriber[i];
    for (int j = 0; j < i; j++) {
      arrayOfSubscriber[j] = FlowableFlatMap.subscribe(paramArrayOfSubscriber[j], this.mapper, this.delayError, this.maxConcurrency, this.prefetch);
    }
    this.source.subscribe(arrayOfSubscriber);
  }
}
