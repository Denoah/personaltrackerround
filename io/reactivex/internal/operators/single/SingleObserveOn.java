package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleObserveOn<T>
  extends Single<T>
{
  final Scheduler scheduler;
  final SingleSource<T> source;
  
  public SingleObserveOn(SingleSource<T> paramSingleSource, Scheduler paramScheduler)
  {
    this.source = paramSingleSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new ObserveOnSingleObserver(paramSingleObserver, this.scheduler));
  }
  
  static final class ObserveOnSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = 3528003840217436037L;
    final SingleObserver<? super T> downstream;
    Throwable error;
    final Scheduler scheduler;
    T value;
    
    ObserveOnSingleObserver(SingleObserver<? super T> paramSingleObserver, Scheduler paramScheduler)
    {
      this.downstream = paramSingleObserver;
      this.scheduler = paramScheduler;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      this.value = paramT;
      DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
    }
    
    public void run()
    {
      Throwable localThrowable = this.error;
      if (localThrowable != null) {
        this.downstream.onError(localThrowable);
      } else {
        this.downstream.onSuccess(this.value);
      }
    }
  }
}
