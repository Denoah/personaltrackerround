package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrampolineScheduler
  extends Scheduler
{
  private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();
  
  TrampolineScheduler() {}
  
  public static TrampolineScheduler instance()
  {
    return INSTANCE;
  }
  
  public Scheduler.Worker createWorker()
  {
    return new TrampolineWorker();
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable)
  {
    RxJavaPlugins.onSchedule(paramRunnable).run();
    return EmptyDisposable.INSTANCE;
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    try
    {
      paramTimeUnit.sleep(paramLong);
      RxJavaPlugins.onSchedule(paramRunnable).run();
    }
    catch (InterruptedException paramRunnable)
    {
      Thread.currentThread().interrupt();
      RxJavaPlugins.onError(paramRunnable);
    }
    return EmptyDisposable.INSTANCE;
  }
  
  static final class SleepingRunnable
    implements Runnable
  {
    private final long execTime;
    private final Runnable run;
    private final TrampolineScheduler.TrampolineWorker worker;
    
    SleepingRunnable(Runnable paramRunnable, TrampolineScheduler.TrampolineWorker paramTrampolineWorker, long paramLong)
    {
      this.run = paramRunnable;
      this.worker = paramTrampolineWorker;
      this.execTime = paramLong;
    }
    
    public void run()
    {
      if (!this.worker.disposed)
      {
        long l1 = this.worker.now(TimeUnit.MILLISECONDS);
        long l2 = this.execTime;
        if (l2 > l1) {
          try
          {
            Thread.sleep(l2 - l1);
          }
          catch (InterruptedException localInterruptedException)
          {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(localInterruptedException);
            return;
          }
        }
        if (!this.worker.disposed) {
          this.run.run();
        }
      }
    }
  }
  
  static final class TimedRunnable
    implements Comparable<TimedRunnable>
  {
    final int count;
    volatile boolean disposed;
    final long execTime;
    final Runnable run;
    
    TimedRunnable(Runnable paramRunnable, Long paramLong, int paramInt)
    {
      this.run = paramRunnable;
      this.execTime = paramLong.longValue();
      this.count = paramInt;
    }
    
    public int compareTo(TimedRunnable paramTimedRunnable)
    {
      int i = ObjectHelper.compare(this.execTime, paramTimedRunnable.execTime);
      if (i == 0) {
        return ObjectHelper.compare(this.count, paramTimedRunnable.count);
      }
      return i;
    }
  }
  
  static final class TrampolineWorker
    extends Scheduler.Worker
    implements Disposable
  {
    final AtomicInteger counter = new AtomicInteger();
    volatile boolean disposed;
    final PriorityBlockingQueue<TrampolineScheduler.TimedRunnable> queue = new PriorityBlockingQueue();
    private final AtomicInteger wip = new AtomicInteger();
    
    TrampolineWorker() {}
    
    public void dispose()
    {
      this.disposed = true;
    }
    
    Disposable enqueue(Runnable paramRunnable, long paramLong)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      paramRunnable = new TrampolineScheduler.TimedRunnable(paramRunnable, Long.valueOf(paramLong), this.counter.incrementAndGet());
      this.queue.add(paramRunnable);
      if (this.wip.getAndIncrement() == 0)
      {
        int i = 1;
        for (;;)
        {
          if (this.disposed)
          {
            this.queue.clear();
            return EmptyDisposable.INSTANCE;
          }
          paramRunnable = (TrampolineScheduler.TimedRunnable)this.queue.poll();
          if (paramRunnable == null)
          {
            int j = this.wip.addAndGet(-i);
            i = j;
            if (j == 0) {
              return EmptyDisposable.INSTANCE;
            }
          }
          else if (!paramRunnable.disposed)
          {
            paramRunnable.run.run();
          }
        }
      }
      return Disposables.fromRunnable(new AppendToQueueTask(paramRunnable));
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      return enqueue(paramRunnable, now(TimeUnit.MILLISECONDS));
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      paramLong = now(TimeUnit.MILLISECONDS) + paramTimeUnit.toMillis(paramLong);
      return enqueue(new TrampolineScheduler.SleepingRunnable(paramRunnable, this, paramLong), paramLong);
    }
    
    final class AppendToQueueTask
      implements Runnable
    {
      final TrampolineScheduler.TimedRunnable timedRunnable;
      
      AppendToQueueTask(TrampolineScheduler.TimedRunnable paramTimedRunnable)
      {
        this.timedRunnable = paramTimedRunnable;
      }
      
      public void run()
      {
        this.timedRunnable.disposed = true;
        TrampolineScheduler.TrampolineWorker.this.queue.remove(this.timedRunnable);
      }
    }
  }
}
