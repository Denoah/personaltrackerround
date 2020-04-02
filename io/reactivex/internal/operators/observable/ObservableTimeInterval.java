package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.TimeUnit;

public final class ObservableTimeInterval<T>
  extends AbstractObservableWithUpstream<T, Timed<T>>
{
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public ObservableTimeInterval(ObservableSource<T> paramObservableSource, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    super(paramObservableSource);
    this.scheduler = paramScheduler;
    this.unit = paramTimeUnit;
  }
  
  public void subscribeActual(Observer<? super Timed<T>> paramObserver)
  {
    this.source.subscribe(new TimeIntervalObserver(paramObserver, this.unit, this.scheduler));
  }
  
  static final class TimeIntervalObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super Timed<T>> downstream;
    long lastTime;
    final Scheduler scheduler;
    final TimeUnit unit;
    Disposable upstream;
    
    TimeIntervalObserver(Observer<? super Timed<T>> paramObserver, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.downstream = paramObserver;
      this.scheduler = paramScheduler;
      this.unit = paramTimeUnit;
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
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.lastTime;
      this.lastTime = l1;
      this.downstream.onNext(new Timed(paramT, l1 - l2, this.unit));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.lastTime = this.scheduler.now(this.unit);
        this.downstream.onSubscribe(this);
      }
    }
  }
}
