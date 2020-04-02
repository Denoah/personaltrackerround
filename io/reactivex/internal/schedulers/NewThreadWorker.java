package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class NewThreadWorker
  extends Scheduler.Worker
  implements Disposable
{
  volatile boolean disposed;
  private final ScheduledExecutorService executor;
  
  public NewThreadWorker(ThreadFactory paramThreadFactory)
  {
    this.executor = SchedulerPoolFactory.create(paramThreadFactory);
  }
  
  public void dispose()
  {
    if (!this.disposed)
    {
      this.disposed = true;
      this.executor.shutdownNow();
    }
  }
  
  public boolean isDisposed()
  {
    return this.disposed;
  }
  
  public Disposable schedule(Runnable paramRunnable)
  {
    return schedule(paramRunnable, 0L, null);
  }
  
  public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    if (this.disposed) {
      return EmptyDisposable.INSTANCE;
    }
    return scheduleActual(paramRunnable, paramLong, paramTimeUnit, null);
  }
  
  public ScheduledRunnable scheduleActual(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit, DisposableContainer paramDisposableContainer)
  {
    ScheduledRunnable localScheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(paramRunnable), paramDisposableContainer);
    if ((paramDisposableContainer != null) && (!paramDisposableContainer.add(localScheduledRunnable))) {
      return localScheduledRunnable;
    }
    if (paramLong <= 0L) {}
    try
    {
      paramRunnable = this.executor.submit(localScheduledRunnable);
      break label71;
      paramRunnable = this.executor.schedule(localScheduledRunnable, paramLong, paramTimeUnit);
      label71:
      localScheduledRunnable.setFuture(paramRunnable);
    }
    catch (RejectedExecutionException paramRunnable)
    {
      if (paramDisposableContainer != null) {
        paramDisposableContainer.remove(localScheduledRunnable);
      }
      RxJavaPlugins.onError(paramRunnable);
    }
    return localScheduledRunnable;
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    ScheduledDirectTask localScheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(paramRunnable));
    if (paramLong <= 0L) {}
    try
    {
      paramRunnable = this.executor.submit(localScheduledDirectTask);
      break label49;
      paramRunnable = this.executor.schedule(localScheduledDirectTask, paramLong, paramTimeUnit);
      label49:
      localScheduledDirectTask.setFuture(paramRunnable);
      return localScheduledDirectTask;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      RxJavaPlugins.onError(paramRunnable);
    }
    return EmptyDisposable.INSTANCE;
  }
  
  public Disposable schedulePeriodicallyDirect(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
    if (paramLong2 <= 0L)
    {
      InstantPeriodicTask localInstantPeriodicTask = new InstantPeriodicTask(paramRunnable, this.executor);
      if (paramLong1 <= 0L) {}
      try
      {
        paramRunnable = this.executor.submit(localInstantPeriodicTask);
        break label62;
        paramRunnable = this.executor.schedule(localInstantPeriodicTask, paramLong1, paramTimeUnit);
        label62:
        localInstantPeriodicTask.setFirst(paramRunnable);
        return localInstantPeriodicTask;
      }
      catch (RejectedExecutionException paramRunnable)
      {
        RxJavaPlugins.onError(paramRunnable);
        return EmptyDisposable.INSTANCE;
      }
    }
    paramRunnable = new ScheduledDirectPeriodicTask(paramRunnable);
    try
    {
      paramRunnable.setFuture(this.executor.scheduleAtFixedRate(paramRunnable, paramLong1, paramLong2, paramTimeUnit));
      return paramRunnable;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      RxJavaPlugins.onError(paramRunnable);
    }
    return EmptyDisposable.INSTANCE;
  }
  
  public void shutdown()
  {
    if (!this.disposed)
    {
      this.disposed = true;
      this.executor.shutdown();
    }
  }
}
