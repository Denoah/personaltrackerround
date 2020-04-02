package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableToListSingle<T, U extends Collection<? super T>>
  extends Single<U>
  implements FuseToObservable<U>
{
  final Callable<U> collectionSupplier;
  final ObservableSource<T> source;
  
  public ObservableToListSingle(ObservableSource<T> paramObservableSource, int paramInt)
  {
    this.source = paramObservableSource;
    this.collectionSupplier = Functions.createArrayList(paramInt);
  }
  
  public ObservableToListSingle(ObservableSource<T> paramObservableSource, Callable<U> paramCallable)
  {
    this.source = paramObservableSource;
    this.collectionSupplier = paramCallable;
  }
  
  public Observable<U> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableToList(this.source, this.collectionSupplier));
  }
  
  public void subscribeActual(SingleObserver<? super U> paramSingleObserver)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new ToListObserver(paramSingleObserver, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
  
  static final class ToListObserver<T, U extends Collection<? super T>>
    implements Observer<T>, Disposable
  {
    U collection;
    final SingleObserver<? super U> downstream;
    Disposable upstream;
    
    ToListObserver(SingleObserver<? super U> paramSingleObserver, U paramU)
    {
      this.downstream = paramSingleObserver;
      this.collection = paramU;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      Collection localCollection = this.collection;
      this.collection = null;
      this.downstream.onSuccess(localCollection);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.collection = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.collection.add(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
