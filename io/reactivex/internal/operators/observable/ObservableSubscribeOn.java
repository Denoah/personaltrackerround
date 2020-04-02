package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSubscribeOn<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Scheduler scheduler;
  
  public ObservableSubscribeOn(ObservableSource<T> paramObservableSource, Scheduler paramScheduler)
  {
    super(paramObservableSource);
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SubscribeOnObserver localSubscribeOnObserver = new SubscribeOnObserver(paramObserver);
    paramObserver.onSubscribe(localSubscribeOnObserver);
    localSubscribeOnObserver.setDisposable(this.scheduler.scheduleDirect(new SubscribeTask(localSubscribeOnObserver)));
  }
  
  static final class SubscribeOnObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 8094547886072529208L;
    final Observer<? super T> downstream;
    final AtomicReference<Disposable> upstream;
    
    SubscribeOnObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
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
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    void setDisposable(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
  
  final class SubscribeTask
    implements Runnable
  {
    private final ObservableSubscribeOn.SubscribeOnObserver<T> parent;
    
    SubscribeTask()
    {
      Object localObject;
      this.parent = localObject;
    }
    
    public void run()
    {
      ObservableSubscribeOn.this.source.subscribe(this.parent);
    }
  }
}
