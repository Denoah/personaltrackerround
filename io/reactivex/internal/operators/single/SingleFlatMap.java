package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMap<T, R>
  extends Single<R>
{
  final Function<? super T, ? extends SingleSource<? extends R>> mapper;
  final SingleSource<? extends T> source;
  
  public SingleFlatMap(SingleSource<? extends T> paramSingleSource, Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    this.mapper = paramFunction;
    this.source = paramSingleSource;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    this.source.subscribe(new SingleFlatMapCallback(paramSingleObserver, this.mapper));
  }
  
  static final class SingleFlatMapCallback<T, R>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable
  {
    private static final long serialVersionUID = 3258103020495908596L;
    final SingleObserver<? super R> downstream;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    
    SingleFlatMapCallback(SingleObserver<? super R> paramSingleObserver, Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
    {
      this.downstream = paramSingleObserver;
      this.mapper = paramFunction;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        paramT = (SingleSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The single returned by the mapper is null");
        if (!isDisposed()) {
          paramT.subscribe(new FlatMapSingleObserver(this, this.downstream));
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
    
    static final class FlatMapSingleObserver<R>
      implements SingleObserver<R>
    {
      final SingleObserver<? super R> downstream;
      final AtomicReference<Disposable> parent;
      
      FlatMapSingleObserver(AtomicReference<Disposable> paramAtomicReference, SingleObserver<? super R> paramSingleObserver)
      {
        this.parent = paramAtomicReference;
        this.downstream = paramSingleObserver;
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.downstream.onError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.replace(this.parent, paramDisposable);
      }
      
      public void onSuccess(R paramR)
      {
        this.downstream.onSuccess(paramR);
      }
    }
  }
}
