package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUnsubscribeOn<T>
  extends Single<T>
{
  final Scheduler scheduler;
  final SingleSource<T> source;
  
  public SingleUnsubscribeOn(SingleSource<T> paramSingleSource, Scheduler paramScheduler)
  {
    this.source = paramSingleSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new UnsubscribeOnSingleObserver(paramSingleObserver, this.scheduler));
  }
  
  static final class UnsubscribeOnSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = 3256698449646456986L;
    final SingleObserver<? super T> downstream;
    Disposable ds;
    final Scheduler scheduler;
    
    UnsubscribeOnSingleObserver(SingleObserver<? super T> paramSingleObserver, Scheduler paramScheduler)
    {
      this.downstream = paramSingleObserver;
      this.scheduler = paramScheduler;
    }
    
    public void dispose()
    {
      Disposable localDisposable = (Disposable)getAndSet(DisposableHelper.DISPOSED);
      if (localDisposable != DisposableHelper.DISPOSED)
      {
        this.ds = localDisposable;
        this.scheduler.scheduleDirect(this);
      }
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
    }
    
    public void run()
    {
      this.ds.dispose();
    }
  }
}
