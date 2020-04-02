package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTakeWhile<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public ObservableTakeWhile(ObservableSource<T> paramObservableSource, Predicate<? super T> paramPredicate)
  {
    super(paramObservableSource);
    this.predicate = paramPredicate;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeWhileObserver(paramObserver, this.predicate));
  }
  
  static final class TakeWhileObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super T> downstream;
    final Predicate<? super T> predicate;
    Disposable upstream;
    
    TakeWhileObserver(Observer<? super T> paramObserver, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramObserver;
      this.predicate = paramPredicate;
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
        boolean bool = this.predicate.test(paramT);
        if (!bool)
        {
          this.done = true;
          this.upstream.dispose();
          this.downstream.onComplete();
          return;
        }
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
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
