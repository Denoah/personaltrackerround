package io.reactivex.internal.operators.single;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapCompletable<T>
  extends Completable
{
  final Function<? super T, ? extends CompletableSource> mapper;
  final SingleSource<T> source;
  
  public SingleFlatMapCompletable(SingleSource<T> paramSingleSource, Function<? super T, ? extends CompletableSource> paramFunction)
  {
    this.source = paramSingleSource;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    FlatMapCompletableObserver localFlatMapCompletableObserver = new FlatMapCompletableObserver(paramCompletableObserver, this.mapper);
    paramCompletableObserver.onSubscribe(localFlatMapCompletableObserver);
    this.source.subscribe(localFlatMapCompletableObserver);
  }
  
  static final class FlatMapCompletableObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, CompletableObserver, Disposable
  {
    private static final long serialVersionUID = -2177128922851101253L;
    final CompletableObserver downstream;
    final Function<? super T, ? extends CompletableSource> mapper;
    
    FlatMapCompletableObserver(CompletableObserver paramCompletableObserver, Function<? super T, ? extends CompletableSource> paramFunction)
    {
      this.downstream = paramCompletableObserver;
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
      DisposableHelper.replace(this, paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        paramT = (CompletableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null CompletableSource");
        if (!isDisposed()) {
          paramT.subscribe(this);
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
