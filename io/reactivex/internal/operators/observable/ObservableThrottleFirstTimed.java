package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableThrottleFirstTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableThrottleFirstTimed(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    super(paramObservableSource);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DebounceTimedObserver(new SerializedObserver(paramObserver), this.timeout, this.unit, this.scheduler.createWorker()));
  }
  
  static final class DebounceTimedObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = 786994795061867455L;
    boolean done;
    final Observer<? super T> downstream;
    volatile boolean gate;
    final long timeout;
    final TimeUnit unit;
    Disposable upstream;
    final Scheduler.Worker worker;
    
    DebounceTimedObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      this.downstream = paramObserver;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.worker.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.worker.isDisposed();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.downstream.onComplete();
        this.worker.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
      }
      else
      {
        this.done = true;
        this.downstream.onError(paramThrowable);
        this.worker.dispose();
      }
    }
    
    public void onNext(T paramT)
    {
      if ((!this.gate) && (!this.done))
      {
        this.gate = true;
        this.downstream.onNext(paramT);
        paramT = (Disposable)get();
        if (paramT != null) {
          paramT.dispose();
        }
        DisposableHelper.replace(this, this.worker.schedule(this, this.timeout, this.unit));
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
    
    public void run()
    {
      this.gate = false;
    }
  }
}
