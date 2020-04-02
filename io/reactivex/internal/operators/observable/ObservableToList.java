package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableToList<T, U extends Collection<? super T>>
  extends AbstractObservableWithUpstream<T, U>
{
  final Callable<U> collectionSupplier;
  
  public ObservableToList(ObservableSource<T> paramObservableSource, int paramInt)
  {
    super(paramObservableSource);
    this.collectionSupplier = Functions.createArrayList(paramInt);
  }
  
  public ObservableToList(ObservableSource<T> paramObservableSource, Callable<U> paramCallable)
  {
    super(paramObservableSource);
    this.collectionSupplier = paramCallable;
  }
  
  public void subscribeActual(Observer<? super U> paramObserver)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new ToListObserver(paramObserver, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class ToListObserver<T, U extends Collection<? super T>>
    implements Observer<T>, Disposable
  {
    U collection;
    final Observer<? super U> downstream;
    Disposable upstream;
    
    ToListObserver(Observer<? super U> paramObserver, U paramU)
    {
      this.downstream = paramObserver;
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
      this.downstream.onNext(localCollection);
      this.downstream.onComplete();
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
