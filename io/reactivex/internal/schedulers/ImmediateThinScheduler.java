package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import java.util.concurrent.TimeUnit;

public final class ImmediateThinScheduler
  extends Scheduler
{
  static final Disposable DISPOSED;
  public static final Scheduler INSTANCE = new ImmediateThinScheduler();
  static final Scheduler.Worker WORKER = new ImmediateThinWorker();
  
  static
  {
    Disposable localDisposable = Disposables.empty();
    DISPOSED = localDisposable;
    localDisposable.dispose();
  }
  
  private ImmediateThinScheduler() {}
  
  public Scheduler.Worker createWorker()
  {
    return WORKER;
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable)
  {
    paramRunnable.run();
    return DISPOSED;
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
  }
  
  public Disposable schedulePeriodicallyDirect(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
  }
  
  static final class ImmediateThinWorker
    extends Scheduler.Worker
  {
    ImmediateThinWorker() {}
    
    public void dispose() {}
    
    public boolean isDisposed()
    {
      return false;
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      paramRunnable.run();
      return ImmediateThinScheduler.DISPOSED;
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
    }
    
    public Disposable schedulePeriodically(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
    {
      throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
    }
  }
}
