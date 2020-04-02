package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;

public final class ObservableSkipUntil<T, U>
  extends AbstractObservableWithUpstream<T, T>
{
  final ObservableSource<U> other;
  
  public ObservableSkipUntil(ObservableSource<T> paramObservableSource, ObservableSource<U> paramObservableSource1)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SerializedObserver localSerializedObserver = new SerializedObserver(paramObserver);
    ArrayCompositeDisposable localArrayCompositeDisposable = new ArrayCompositeDisposable(2);
    localSerializedObserver.onSubscribe(localArrayCompositeDisposable);
    paramObserver = new SkipUntilObserver(localSerializedObserver, localArrayCompositeDisposable);
    this.other.subscribe(new SkipUntil(localArrayCompositeDisposable, paramObserver, localSerializedObserver));
    this.source.subscribe(paramObserver);
  }
  
  final class SkipUntil
    implements Observer<U>
  {
    final ArrayCompositeDisposable frc;
    final SerializedObserver<T> serial;
    final ObservableSkipUntil.SkipUntilObserver<T> sus;
    Disposable upstream;
    
    SkipUntil(ObservableSkipUntil.SkipUntilObserver<T> paramSkipUntilObserver, SerializedObserver<T> paramSerializedObserver)
    {
      this.frc = paramSkipUntilObserver;
      this.sus = paramSerializedObserver;
      Object localObject;
      this.serial = localObject;
    }
    
    public void onComplete()
    {
      this.sus.notSkipping = true;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.frc.dispose();
      this.serial.onError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      this.upstream.dispose();
      this.sus.notSkipping = true;
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.frc.setResource(1, paramDisposable);
      }
    }
  }
  
  static final class SkipUntilObserver<T>
    implements Observer<T>
  {
    final Observer<? super T> downstream;
    final ArrayCompositeDisposable frc;
    volatile boolean notSkipping;
    boolean notSkippingLocal;
    Disposable upstream;
    
    SkipUntilObserver(Observer<? super T> paramObserver, ArrayCompositeDisposable paramArrayCompositeDisposable)
    {
      this.downstream = paramObserver;
      this.frc = paramArrayCompositeDisposable;
    }
    
    public void onComplete()
    {
      this.frc.dispose();
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.frc.dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.notSkippingLocal)
      {
        this.downstream.onNext(paramT);
      }
      else if (this.notSkipping)
      {
        this.notSkippingLocal = true;
        this.downstream.onNext(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.frc.setResource(0, paramDisposable);
      }
    }
  }
}
