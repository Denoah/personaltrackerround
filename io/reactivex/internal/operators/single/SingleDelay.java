package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.TimeUnit;

public final class SingleDelay<T>
  extends Single<T>
{
  final boolean delayError;
  final Scheduler scheduler;
  final SingleSource<? extends T> source;
  final long time;
  final TimeUnit unit;
  
  public SingleDelay(SingleSource<? extends T> paramSingleSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    this.source = paramSingleSource;
    this.time = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramSingleObserver.onSubscribe(localSequentialDisposable);
    this.source.subscribe(new Delay(localSequentialDisposable, paramSingleObserver));
  }
  
  final class Delay
    implements SingleObserver<T>
  {
    final SingleObserver<? super T> downstream;
    private final SequentialDisposable sd;
    
    Delay(SingleObserver<? super T> paramSingleObserver)
    {
      this.sd = paramSingleObserver;
      Object localObject;
      this.downstream = localObject;
    }
    
    public void onError(Throwable paramThrowable)
    {
      SequentialDisposable localSequentialDisposable = this.sd;
      Scheduler localScheduler = SingleDelay.this.scheduler;
      paramThrowable = new OnError(paramThrowable);
      long l;
      if (SingleDelay.this.delayError) {
        l = SingleDelay.this.time;
      } else {
        l = 0L;
      }
      localSequentialDisposable.replace(localScheduler.scheduleDirect(paramThrowable, l, SingleDelay.this.unit));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.sd.replace(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.sd.replace(SingleDelay.this.scheduler.scheduleDirect(new OnSuccess(paramT), SingleDelay.this.time, SingleDelay.this.unit));
    }
    
    final class OnError
      implements Runnable
    {
      private final Throwable e;
      
      OnError(Throwable paramThrowable)
      {
        this.e = paramThrowable;
      }
      
      public void run()
      {
        SingleDelay.Delay.this.downstream.onError(this.e);
      }
    }
    
    final class OnSuccess
      implements Runnable
    {
      private final T value;
      
      OnSuccess()
      {
        Object localObject;
        this.value = localObject;
      }
      
      public void run()
      {
        SingleDelay.Delay.this.downstream.onSuccess(this.value);
      }
    }
  }
}
