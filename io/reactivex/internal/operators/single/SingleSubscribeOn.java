package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubscribeOn<T>
  extends Single<T>
{
  final Scheduler scheduler;
  final SingleSource<? extends T> source;
  
  public SingleSubscribeOn(SingleSource<? extends T> paramSingleSource, Scheduler paramScheduler)
  {
    this.source = paramSingleSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    SubscribeOnObserver localSubscribeOnObserver = new SubscribeOnObserver(paramSingleObserver, this.source);
    paramSingleObserver.onSubscribe(localSubscribeOnObserver);
    paramSingleObserver = this.scheduler.scheduleDirect(localSubscribeOnObserver);
    localSubscribeOnObserver.task.replace(paramSingleObserver);
  }
  
  static final class SubscribeOnObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = 7000911171163930287L;
    final SingleObserver<? super T> downstream;
    final SingleSource<? extends T> source;
    final SequentialDisposable task;
    
    SubscribeOnObserver(SingleObserver<? super T> paramSingleObserver, SingleSource<? extends T> paramSingleSource)
    {
      this.downstream = paramSingleObserver;
      this.source = paramSingleSource;
      this.task = new SequentialDisposable();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
      this.task.dispose();
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
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
    }
    
    public void run()
    {
      this.source.subscribe(this);
    }
  }
}
