package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableSkipWhile<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public ObservableSkipWhile(ObservableSource<T> paramObservableSource, Predicate<? super T> paramPredicate)
  {
    super(paramObservableSource);
    this.predicate = paramPredicate;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new SkipWhileObserver(paramObserver, this.predicate));
  }
  
  static final class SkipWhileObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    boolean notSkipping;
    final Predicate<? super T> predicate;
    Disposable upstream;
    
    SkipWhileObserver(Observer<? super T> paramObserver, Predicate<? super T> paramPredicate)
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
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.notSkipping) {
        this.downstream.onNext(paramT);
      }
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (!bool)
        {
          this.notSkipping = true;
          this.downstream.onNext(paramT);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        this.downstream.onError(paramT);
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
