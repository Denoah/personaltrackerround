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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableDebounceTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableDebounceTimed(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
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
  
  static final class DebounceEmitter<T>
    extends AtomicReference<Disposable>
    implements Runnable, Disposable
  {
    private static final long serialVersionUID = 6812032969491025141L;
    final long idx;
    final AtomicBoolean once = new AtomicBoolean();
    final ObservableDebounceTimed.DebounceTimedObserver<T> parent;
    final T value;
    
    DebounceEmitter(T paramT, long paramLong, ObservableDebounceTimed.DebounceTimedObserver<T> paramDebounceTimedObserver)
    {
      this.value = paramT;
      this.idx = paramLong;
      this.parent = paramDebounceTimedObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void run()
    {
      if (this.once.compareAndSet(false, true)) {
        this.parent.emit(this.idx, this.value, this);
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.replace(this, paramDisposable);
    }
  }
  
  static final class DebounceTimedObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super T> downstream;
    volatile long index;
    final long timeout;
    Disposable timer;
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
    
    void emit(long paramLong, T paramT, ObservableDebounceTimed.DebounceEmitter<T> paramDebounceEmitter)
    {
      if (paramLong == this.index)
      {
        this.downstream.onNext(paramT);
        paramDebounceEmitter.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.worker.isDisposed();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject = this.timer;
      if (localObject != null) {
        ((Disposable)localObject).dispose();
      }
      localObject = (ObservableDebounceTimed.DebounceEmitter)localObject;
      if (localObject != null) {
        ((ObservableDebounceTimed.DebounceEmitter)localObject).run();
      }
      this.downstream.onComplete();
      this.worker.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      Disposable localDisposable = this.timer;
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
      this.worker.dispose();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.index + 1L;
      this.index = l;
      Disposable localDisposable = this.timer;
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      paramT = new ObservableDebounceTimed.DebounceEmitter(paramT, l, this);
      this.timer = paramT;
      paramT.setResource(this.worker.schedule(paramT, this.timeout, this.unit));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
