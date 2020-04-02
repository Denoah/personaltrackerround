package io.reactivex.internal.operators.single;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapMaybe<T, R>
  extends Maybe<R>
{
  final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
  final SingleSource<? extends T> source;
  
  public SingleFlatMapMaybe(SingleSource<? extends T> paramSingleSource, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    this.mapper = paramFunction;
    this.source = paramSingleSource;
  }
  
  protected void subscribeActual(MaybeObserver<? super R> paramMaybeObserver)
  {
    this.source.subscribe(new FlatMapSingleObserver(paramMaybeObserver, this.mapper));
  }
  
  static final class FlatMapMaybeObserver<R>
    implements MaybeObserver<R>
  {
    final MaybeObserver<? super R> downstream;
    final AtomicReference<Disposable> parent;
    
    FlatMapMaybeObserver(AtomicReference<Disposable> paramAtomicReference, MaybeObserver<? super R> paramMaybeObserver)
    {
      this.parent = paramAtomicReference;
      this.downstream = paramMaybeObserver;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
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
  
  static final class FlatMapSingleObserver<T, R>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable
  {
    private static final long serialVersionUID = -5843758257109742742L;
    final MaybeObserver<? super R> downstream;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    
    FlatMapSingleObserver(MaybeObserver<? super R> paramMaybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
    {
      this.downstream = paramMaybeObserver;
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
        paramT = (MaybeSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null MaybeSource");
        if (!isDisposed()) {
          paramT.subscribe(new SingleFlatMapMaybe.FlatMapMaybeObserver(this, this.downstream));
        }
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
