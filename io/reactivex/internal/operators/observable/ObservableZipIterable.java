package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;

public final class ObservableZipIterable<T, U, V>
  extends Observable<V>
{
  final Iterable<U> other;
  final Observable<? extends T> source;
  final BiFunction<? super T, ? super U, ? extends V> zipper;
  
  public ObservableZipIterable(Observable<? extends T> paramObservable, Iterable<U> paramIterable, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
  {
    this.source = paramObservable;
    this.other = paramIterable;
    this.zipper = paramBiFunction;
  }
  
  public void subscribeActual(Observer<? super V> paramObserver)
  {
    try
    {
      Iterator localIterator = (Iterator)ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
      try
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          EmptyDisposable.complete(paramObserver);
          return;
        }
        this.source.subscribe(new ZipIterableObserver(paramObserver, localIterator, this.zipper));
        return;
      }
      finally {}
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable2);
      EmptyDisposable.error(localThrowable2, paramObserver);
    }
  }
  
  static final class ZipIterableObserver<T, U, V>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super V> downstream;
    final Iterator<U> iterator;
    Disposable upstream;
    final BiFunction<? super T, ? super U, ? extends V> zipper;
    
    ZipIterableObserver(Observer<? super V> paramObserver, Iterator<U> paramIterator, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
    {
      this.downstream = paramObserver;
      this.iterator = paramIterator;
      this.zipper = paramBiFunction;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    void error(Throwable paramThrowable)
    {
      this.done = true;
      this.upstream.dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      try
      {
        Object localObject = ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value");
        try
        {
          paramT = ObjectHelper.requireNonNull(this.zipper.apply(paramT, localObject), "The zipper function returned a null value");
          this.downstream.onNext(paramT);
          try
          {
            boolean bool = this.iterator.hasNext();
            if (!bool)
            {
              this.done = true;
              this.upstream.dispose();
              this.downstream.onComplete();
            }
            return;
          }
          finally {}
          paramT = finally;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        error(paramT);
      }
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
