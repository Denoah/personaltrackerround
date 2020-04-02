package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

public final class ObservableDelay<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final long delay;
  final boolean delayError;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public ObservableDelay(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    if (!this.delayError) {
      paramObserver = new SerializedObserver(paramObserver);
    }
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    this.source.subscribe(new DelayObserver(paramObserver, this.delay, this.unit, localWorker, this.delayError));
  }
  
  static final class DelayObserver<T>
    implements Observer<T>, Disposable
  {
    final long delay;
    final boolean delayError;
    final Observer<? super T> downstream;
    final TimeUnit unit;
    Disposable upstream;
    final Scheduler.Worker w;
    
    DelayObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.delay = paramLong;
      this.unit = paramTimeUnit;
      this.w = paramWorker;
      this.delayError = paramBoolean;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.w.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.w.isDisposed();
    }
    
    public void onComplete()
    {
      this.w.schedule(new OnComplete(), this.delay, this.unit);
    }
    
    public void onError(Throwable paramThrowable)
    {
      Scheduler.Worker localWorker = this.w;
      paramThrowable = new OnError(paramThrowable);
      long l;
      if (this.delayError) {
        l = this.delay;
      } else {
        l = 0L;
      }
      localWorker.schedule(paramThrowable, l, this.unit);
    }
    
    public void onNext(T paramT)
    {
      this.w.schedule(new OnNext(paramT), this.delay, this.unit);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    final class OnComplete
      implements Runnable
    {
      OnComplete() {}
      
      public void run()
      {
        try
        {
          ObservableDelay.DelayObserver.this.downstream.onComplete();
          return;
        }
        finally
        {
          ObservableDelay.DelayObserver.this.w.dispose();
        }
      }
    }
    
    final class OnError
      implements Runnable
    {
      private final Throwable throwable;
      
      OnError(Throwable paramThrowable)
      {
        this.throwable = paramThrowable;
      }
      
      public void run()
      {
        try
        {
          ObservableDelay.DelayObserver.this.downstream.onError(this.throwable);
          return;
        }
        finally
        {
          ObservableDelay.DelayObserver.this.w.dispose();
        }
      }
    }
    
    final class OnNext
      implements Runnable
    {
      private final T t;
      
      OnNext()
      {
        Object localObject;
        this.t = localObject;
      }
      
      public void run()
      {
        ObservableDelay.DelayObserver.this.downstream.onNext(this.t);
      }
    }
  }
}
