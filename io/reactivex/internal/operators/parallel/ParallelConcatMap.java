package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelConcatMap<T, R>
  extends ParallelFlowable<R>
{
  final ErrorMode errorMode;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final int prefetch;
  final ParallelFlowable<T> source;
  
  public ParallelConcatMap(ParallelFlowable<T> paramParallelFlowable, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, ErrorMode paramErrorMode)
  {
    this.source = paramParallelFlowable;
    this.mapper = ((Function)ObjectHelper.requireNonNull(paramFunction, "mapper"));
    this.prefetch = paramInt;
    this.errorMode = ((ErrorMode)ObjectHelper.requireNonNull(paramErrorMode, "errorMode"));
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
      arrayOfSubscriber[j] = FlowableConcatMap.subscribe(paramArrayOfSubscriber[j], this.mapper, this.prefetch, this.errorMode);
    }
    this.source.subscribe(arrayOfSubscriber);
  }
}
