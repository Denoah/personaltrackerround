package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableThrottleLatest<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final boolean emitLast;
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableThrottleLatest(Observable<T> paramObservable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramObservable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.emitLast = paramBoolean;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ThrottleLatestObserver(paramObserver, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast));
  }
  
  static final class ThrottleLatestObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = -8296689127439125014L;
    volatile boolean cancelled;
    volatile boolean done;
    final Observer<? super T> downstream;
    final boolean emitLast;
    Throwable error;
    final AtomicReference<T> latest;
    final long timeout;
    volatile boolean timerFired;
    boolean timerRunning;
    final TimeUnit unit;
    Disposable upstream;
    final Scheduler.Worker worker;
    
    ThrottleLatestObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.emitLast = paramBoolean;
      this.latest = new AtomicReference();
    }
    
    public void dispose()
    {
      this.cancelled = true;
      this.upstream.dispose();
      this.worker.dispose();
      if (getAndIncrement() == 0) {
        this.latest.lazySet(null);
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Object localObject = this.latest;
      Observer localObserver = this.downstream;
      int i = 1;
      for (;;)
      {
        if (this.cancelled)
        {
          ((AtomicReference)localObject).lazySet(null);
          return;
        }
        boolean bool = this.done;
        if ((bool) && (this.error != null))
        {
          ((AtomicReference)localObject).lazySet(null);
          localObserver.onError(this.error);
          this.worker.dispose();
          return;
        }
        if (((AtomicReference)localObject).get() == null) {
          j = 1;
        } else {
          j = 0;
        }
        if (bool)
        {
          localObject = ((AtomicReference)localObject).getAndSet(null);
          if ((j == 0) && (this.emitLast)) {
            localObserver.onNext(localObject);
          }
          localObserver.onComplete();
          this.worker.dispose();
          return;
        }
        if (j != 0)
        {
          if (this.timerFired)
          {
            this.timerRunning = false;
            this.timerFired = false;
          }
        }
        else {
          if ((!this.timerRunning) || (this.timerFired)) {
            break label193;
          }
        }
        int j = addAndGet(-i);
        i = j;
        if (j == 0)
        {
          return;
          label193:
          localObserver.onNext(((AtomicReference)localObject).getAndSet(null));
          this.timerFired = false;
          this.timerRunning = true;
          this.worker.schedule(this, this.timeout, this.unit);
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      this.latest.set(paramT);
      drain();
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
      this.timerFired = true;
      drain();
    }
  }
}
