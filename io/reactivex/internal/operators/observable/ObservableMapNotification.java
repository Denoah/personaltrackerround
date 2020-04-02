package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableMapNotification<T, R>
  extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>>
{
  final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
  final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
  final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;
  
  public ObservableMapNotification(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, Function<? super Throwable, ? extends ObservableSource<? extends R>> paramFunction1, Callable<? extends ObservableSource<? extends R>> paramCallable)
  {
    super(paramObservableSource);
    this.onNextMapper = paramFunction;
    this.onErrorMapper = paramFunction1;
    this.onCompleteSupplier = paramCallable;
  }
  
  public void subscribeActual(Observer<? super ObservableSource<? extends R>> paramObserver)
  {
    this.source.subscribe(new MapNotificationObserver(paramObserver, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
  }
  
  static final class MapNotificationObserver<T, R>
    implements Observer<T>, Disposable
  {
    final Observer<? super ObservableSource<? extends R>> downstream;
    final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
    final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;
    Disposable upstream;
    
    MapNotificationObserver(Observer<? super ObservableSource<? extends R>> paramObserver, Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, Function<? super Throwable, ? extends ObservableSource<? extends R>> paramFunction1, Callable<? extends ObservableSource<? extends R>> paramCallable)
    {
      this.downstream = paramObserver;
      this.onNextMapper = paramFunction;
      this.onErrorMapper = paramFunction1;
      this.onCompleteSupplier = paramCallable;
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
      try
      {
        ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onComplete ObservableSource returned is null");
        this.downstream.onNext(localObservableSource);
        this.downstream.onComplete();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.onErrorMapper.apply(paramThrowable), "The onError ObservableSource returned is null");
        this.downstream.onNext(localObservableSource);
        this.downstream.onComplete();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        paramT = (ObservableSource)ObjectHelper.requireNonNull(this.onNextMapper.apply(paramT), "The onNext ObservableSource returned is null");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
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
