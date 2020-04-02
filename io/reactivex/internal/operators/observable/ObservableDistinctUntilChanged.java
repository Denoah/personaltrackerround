package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableDistinctUntilChanged<T, K>
  extends AbstractObservableWithUpstream<T, T>
{
  final BiPredicate<? super K, ? super K> comparer;
  final Function<? super T, K> keySelector;
  
  public ObservableDistinctUntilChanged(ObservableSource<T> paramObservableSource, Function<? super T, K> paramFunction, BiPredicate<? super K, ? super K> paramBiPredicate)
  {
    super(paramObservableSource);
    this.keySelector = paramFunction;
    this.comparer = paramBiPredicate;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DistinctUntilChangedObserver(paramObserver, this.keySelector, this.comparer));
  }
  
  static final class DistinctUntilChangedObserver<T, K>
    extends BasicFuseableObserver<T, T>
  {
    final BiPredicate<? super K, ? super K> comparer;
    boolean hasValue;
    final Function<? super T, K> keySelector;
    K last;
    
    DistinctUntilChangedObserver(Observer<? super T> paramObserver, Function<? super T, K> paramFunction, BiPredicate<? super K, ? super K> paramBiPredicate)
    {
      super();
      this.keySelector = paramFunction;
      this.comparer = paramBiPredicate;
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode != 0)
      {
        this.downstream.onNext(paramT);
        return;
      }
      try
      {
        Object localObject = this.keySelector.apply(paramT);
        if (this.hasValue)
        {
          boolean bool = this.comparer.test(this.last, localObject);
          this.last = localObject;
          if (!bool) {}
        }
        else
        {
          this.hasValue = true;
          this.last = localObject;
        }
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    public T poll()
      throws Exception
    {
      for (;;)
      {
        Object localObject1 = this.qd.poll();
        if (localObject1 == null) {
          return null;
        }
        Object localObject2 = this.keySelector.apply(localObject1);
        if (!this.hasValue)
        {
          this.hasValue = true;
          this.last = localObject2;
          return localObject1;
        }
        if (!this.comparer.test(this.last, localObject2))
        {
          this.last = localObject2;
          return localObject1;
        }
        this.last = localObject2;
      }
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
