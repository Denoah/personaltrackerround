package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeoutTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final ObservableSource<? extends T> other;
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableTimeoutTimed(Observable<T> paramObservable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, ObservableSource<? extends T> paramObservableSource)
  {
    super(paramObservable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.other = paramObservableSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject;
    if (this.other == null)
    {
      localObject = new TimeoutObserver(paramObserver, this.timeout, this.unit, this.scheduler.createWorker());
      paramObserver.onSubscribe((Disposable)localObject);
      ((TimeoutObserver)localObject).startTimeout(0L);
      this.source.subscribe((Observer)localObject);
    }
    else
    {
      localObject = new TimeoutFallbackObserver(paramObserver, this.timeout, this.unit, this.scheduler.createWorker(), this.other);
      paramObserver.onSubscribe((Disposable)localObject);
      ((TimeoutFallbackObserver)localObject).startTimeout(0L);
      this.source.subscribe((Observer)localObject);
    }
  }
  
  static final class FallbackObserver<T>
    implements Observer<T>
  {
    final AtomicReference<Disposable> arbiter;
    final Observer<? super T> downstream;
    
    FallbackObserver(Observer<? super T> paramObserver, AtomicReference<Disposable> paramAtomicReference)
    {
      this.downstream = paramObserver;
      this.arbiter = paramAtomicReference;
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
      DisposableHelper.replace(this.arbiter, paramDisposable);
    }
  }
  
  static final class TimeoutFallbackObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Disposable, ObservableTimeoutTimed.TimeoutSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    final Observer<? super T> downstream;
    ObservableSource<? extends T> fallback;
    final AtomicLong index;
    final SequentialDisposable task;
    final long timeout;
    final TimeUnit unit;
    final AtomicReference<Disposable> upstream;
    final Scheduler.Worker worker;
    
    TimeoutFallbackObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.fallback = paramObservableSource;
      this.task = new SequentialDisposable();
      this.index = new AtomicLong();
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this);
      this.worker.dispose();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.worker.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.worker.dispose();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = this.index.get();
      if (l1 != Long.MAX_VALUE)
      {
        AtomicLong localAtomicLong = this.index;
        long l2 = 1L + l1;
        if (localAtomicLong.compareAndSet(l1, l2))
        {
          ((Disposable)this.task.get()).dispose();
          this.downstream.onNext(paramT);
          startTimeout(l2);
        }
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    public void onTimeout(long paramLong)
    {
      if (this.index.compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this.upstream);
        ObservableSource localObservableSource = this.fallback;
        this.fallback = null;
        localObservableSource.subscribe(new ObservableTimeoutTimed.FallbackObserver(this.downstream, this));
        this.worker.dispose();
      }
    }
    
    void startTimeout(long paramLong)
    {
      this.task.replace(this.worker.schedule(new ObservableTimeoutTimed.TimeoutTask(paramLong, this), this.timeout, this.unit));
    }
  }
  
  static final class TimeoutObserver<T>
    extends AtomicLong
    implements Observer<T>, Disposable, ObservableTimeoutTimed.TimeoutSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    final Observer<? super T> downstream;
    final SequentialDisposable task;
    final long timeout;
    final TimeUnit unit;
    final AtomicReference<Disposable> upstream;
    final Scheduler.Worker worker;
    
    TimeoutObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      this.downstream = paramObserver;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      this.worker.dispose();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.worker.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.worker.dispose();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = get();
      if (l1 != Long.MAX_VALUE)
      {
        long l2 = 1L + l1;
        if (compareAndSet(l1, l2))
        {
          ((Disposable)this.task.get()).dispose();
          this.downstream.onNext(paramT);
          startTimeout(l2);
        }
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    public void onTimeout(long paramLong)
    {
      if (compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this.upstream);
        this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
        this.worker.dispose();
      }
    }
    
    void startTimeout(long paramLong)
    {
      this.task.replace(this.worker.schedule(new ObservableTimeoutTimed.TimeoutTask(paramLong, this), this.timeout, this.unit));
    }
  }
  
  static abstract interface TimeoutSupport
  {
    public abstract void onTimeout(long paramLong);
  }
  
  static final class TimeoutTask
    implements Runnable
  {
    final long idx;
    final ObservableTimeoutTimed.TimeoutSupport parent;
    
    TimeoutTask(long paramLong, ObservableTimeoutTimed.TimeoutSupport paramTimeoutSupport)
    {
      this.idx = paramLong;
      this.parent = paramTimeoutSupport;
    }
    
    public void run()
    {
      this.parent.onTimeout(this.idx);
    }
  }
}
