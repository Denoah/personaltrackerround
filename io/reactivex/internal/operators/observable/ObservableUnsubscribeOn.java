package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableUnsubscribeOn<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Scheduler scheduler;
  
  public ObservableUnsubscribeOn(ObservableSource<T> paramObservableSource, Scheduler paramScheduler)
  {
    super(paramObservableSource);
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new UnsubscribeObserver(paramObserver, this.scheduler));
  }
  
  static final class UnsubscribeObserver<T>
    extends AtomicBoolean
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 1015244841293359600L;
    final Observer<? super T> downstream;
    final Scheduler scheduler;
    Disposable upstream;
    
    UnsubscribeObserver(Observer<? super T> paramObserver, Scheduler paramScheduler)
    {
      this.downstream = paramObserver;
      this.scheduler = paramScheduler;
    }
    
    public void dispose()
    {
      if (compareAndSet(false, true)) {
        this.scheduler.scheduleDirect(new DisposeTask());
      }
    }
    
    public boolean isDisposed()
    {
      return get();
    }
    
    public void onComplete()
    {
      if (!get()) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get())
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (!get()) {
        this.downstream.onNext(paramT);
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
    
    final class DisposeTask
      implements Runnable
    {
      DisposeTask() {}
      
      public void run()
      {
        ObservableUnsubscribeOn.UnsubscribeObserver.this.upstream.dispose();
      }
    }
  }
}
