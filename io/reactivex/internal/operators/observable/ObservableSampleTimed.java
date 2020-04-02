package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final boolean emitLast;
  final long period;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public ObservableSampleTimed(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.period = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.emitLast = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    paramObserver = new SerializedObserver(paramObserver);
    if (this.emitLast) {
      this.source.subscribe(new SampleTimedEmitLast(paramObserver, this.period, this.unit, this.scheduler));
    } else {
      this.source.subscribe(new SampleTimedNoLast(paramObserver, this.period, this.unit, this.scheduler));
    }
  }
  
  static final class SampleTimedEmitLast<T>
    extends ObservableSampleTimed.SampleTimedObserver<T>
  {
    private static final long serialVersionUID = -7139995637533111443L;
    final AtomicInteger wip = new AtomicInteger(1);
    
    SampleTimedEmitLast(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(paramLong, paramTimeUnit, paramScheduler);
    }
    
    void complete()
    {
      emit();
      if (this.wip.decrementAndGet() == 0) {
        this.downstream.onComplete();
      }
    }
    
    public void run()
    {
      if (this.wip.incrementAndGet() == 2)
      {
        emit();
        if (this.wip.decrementAndGet() == 0) {
          this.downstream.onComplete();
        }
      }
    }
  }
  
  static final class SampleTimedNoLast<T>
    extends ObservableSampleTimed.SampleTimedObserver<T>
  {
    private static final long serialVersionUID = -7139995637533111443L;
    
    SampleTimedNoLast(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(paramLong, paramTimeUnit, paramScheduler);
    }
    
    void complete()
    {
      this.downstream.onComplete();
    }
    
    public void run()
    {
      emit();
    }
  }
  
  static abstract class SampleTimedObserver<T>
    extends AtomicReference<T>
    implements Observer<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = -3517602651313910099L;
    final Observer<? super T> downstream;
    final long period;
    final Scheduler scheduler;
    final AtomicReference<Disposable> timer = new AtomicReference();
    final TimeUnit unit;
    Disposable upstream;
    
    SampleTimedObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.downstream = paramObserver;
      this.period = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    void cancelTimer()
    {
      DisposableHelper.dispose(this.timer);
    }
    
    abstract void complete();
    
    public void dispose()
    {
      cancelTimer();
      this.upstream.dispose();
    }
    
    void emit()
    {
      Object localObject = getAndSet(null);
      if (localObject != null) {
        this.downstream.onNext(localObject);
      }
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      cancelTimer();
      complete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      cancelTimer();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      lazySet(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
        paramDisposable = this.scheduler;
        long l = this.period;
        paramDisposable = paramDisposable.schedulePeriodicallyDirect(this, l, l, this.unit);
        DisposableHelper.replace(this.timer, paramDisposable);
      }
    }
  }
}
