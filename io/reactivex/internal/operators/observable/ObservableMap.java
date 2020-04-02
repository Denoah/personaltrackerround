package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableMap<T, U>
  extends AbstractObservableWithUpstream<T, U>
{
  final Function<? super T, ? extends U> function;
  
  public ObservableMap(ObservableSource<T> paramObservableSource, Function<? super T, ? extends U> paramFunction)
  {
    super(paramObservableSource);
    this.function = paramFunction;
  }
  
  public void subscribeActual(Observer<? super U> paramObserver)
  {
    this.source.subscribe(new MapObserver(paramObserver, this.function));
  }
  
  static final class MapObserver<T, U>
    extends BasicFuseableObserver<T, U>
  {
    final Function<? super T, ? extends U> mapper;
    
    MapObserver(Observer<? super U> paramObserver, Function<? super T, ? extends U> paramFunction)
    {
      super();
      this.mapper = paramFunction;
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode != 0)
      {
        this.downstream.onNext(null);
        return;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper function returned a null value.");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    public U poll()
      throws Exception
    {
      Object localObject = this.qd.poll();
      if (localObject != null) {
        localObject = ObjectHelper.requireNonNull(this.mapper.apply(localObject), "The mapper function returned a null value.");
      } else {
        localObject = null;
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
