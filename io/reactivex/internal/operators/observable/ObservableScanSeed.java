package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableScanSeed<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final BiFunction<R, ? super T, R> accumulator;
  final Callable<R> seedSupplier;
  
  public ObservableScanSeed(ObservableSource<T> paramObservableSource, Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    super(paramObservableSource);
    this.accumulator = paramBiFunction;
    this.seedSupplier = paramCallable;
  }
  
  public void subscribeActual(Observer<? super R> paramObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null");
      this.source.subscribe(new ScanSeedObserver(paramObserver, this.accumulator, localObject));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class ScanSeedObserver<T, R>
    implements Observer<T>, Disposable
  {
    final BiFunction<R, ? super T, R> accumulator;
    boolean done;
    final Observer<? super R> downstream;
    Disposable upstream;
    R value;
    
    ScanSeedObserver(Observer<? super R> paramObserver, BiFunction<R, ? super T, R> paramBiFunction, R paramR)
    {
      this.downstream = paramObserver;
      this.accumulator = paramBiFunction;
      this.value = paramR;
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
      Object localObject = this.value;
      try
      {
        paramT = ObjectHelper.requireNonNull(this.accumulator.apply(localObject, paramT), "The accumulator returned a null value");
        this.value = paramT;
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
        this.downstream.onNext(this.value);
      }
    }
  }
}
