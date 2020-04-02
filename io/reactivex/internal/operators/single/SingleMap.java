package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleMap<T, R>
  extends Single<R>
{
  final Function<? super T, ? extends R> mapper;
  final SingleSource<? extends T> source;
  
  public SingleMap(SingleSource<? extends T> paramSingleSource, Function<? super T, ? extends R> paramFunction)
  {
    this.source = paramSingleSource;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    this.source.subscribe(new MapSingleObserver(paramSingleObserver, this.mapper));
  }
  
  static final class MapSingleObserver<T, R>
    implements SingleObserver<T>
  {
    final Function<? super T, ? extends R> mapper;
    final SingleObserver<? super R> t;
    
    MapSingleObserver(SingleObserver<? super R> paramSingleObserver, Function<? super T, ? extends R> paramFunction)
    {
      this.t = paramSingleObserver;
      this.mapper = paramFunction;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.t.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.t.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper function returned a null value.");
        this.t.onSuccess(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        onError(paramT);
      }
    }
  }
}
