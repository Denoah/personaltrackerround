package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableScan<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final BiFunction<T, T, T> accumulator;
  
  public ObservableScan(ObservableSource<T> paramObservableSource, BiFunction<T, T, T> paramBiFunction)
  {
    super(paramObservableSource);
    this.accumulator = paramBiFunction;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ScanObserver(paramObserver, this.accumulator));
  }
  
  static final class ScanObserver<T>
    implements Observer<T>, Disposable
  {
    final BiFunction<T, T, T> accumulator;
    boolean done;
    final Observer<? super T> downstream;
    Disposable upstream;
    T value;
    
    ScanObserver(Observer<? super T> paramObserver, BiFunction<T, T, T> paramBiFunction)
    {
      this.downstream = paramObserver;
      this.accumulator = paramBiFunction;
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
      Observer localObserver = this.downstream;
      Object localObject = this.value;
      if (localObject == null)
      {
        this.value = paramT;
        localObserver.onNext(paramT);
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.accumulator.apply(localObject, paramT), "The value returned by the accumulator is null");
        this.value = paramT;
        localObserver.onNext(paramT);
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
