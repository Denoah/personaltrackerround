package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import java.util.concurrent.TimeUnit;

public abstract class Scheduler
{
  static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15L).longValue());
  
  public Scheduler() {}
  
  public static long clockDriftTolerance()
  {
    return CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
  }
  
  public abstract Worker createWorker();
  
  public long now(TimeUnit paramTimeUnit)
  {
    return paramTimeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable)
  {
    return scheduleDirect(paramRunnable, 0L, TimeUnit.NANOSECONDS);
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    Worker localWorker = createWorker();
    paramRunnable = new DisposeTask(RxJavaPlugins.onSchedule(paramRunnable), localWorker);
    localWorker.schedule(paramRunnable, paramLong, paramTimeUnit);
    return paramRunnable;
  }
  
  public Disposable schedulePeriodicallyDirect(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    Worker localWorker = createWorker();
    paramRunnable = new PeriodicDirectTask(RxJavaPlugins.onSchedule(paramRunnable), localWorker);
    paramTimeUnit = localWorker.schedulePeriodically(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
    if (paramTimeUnit == EmptyDisposable.INSTANCE) {
      return paramTimeUnit;
    }
    return paramRunnable;
  }
  
  public void shutdown() {}
  
  public void start() {}
  
  public <S extends Scheduler,  extends Disposable> S when(Function<Flowable<Flowable<Completable>>, Completable> paramFunction)
  {
    return new SchedulerWhen(paramFunction, this);
  }
  
  static final class DisposeTask
    implements Disposable, Runnable, SchedulerRunnableIntrospection
  {
    final Runnable decoratedRun;
    Thread runner;
    final Scheduler.Worker w;
    
    DisposeTask(Runnable paramRunnable, Scheduler.Worker paramWorker)
    {
      this.decoratedRun = paramRunnable;
      this.w = paramWorker;
    }
    
    public void dispose()
    {
      if (this.runner == Thread.currentThread())
      {
        Scheduler.Worker localWorker = this.w;
        if ((localWorker instanceof NewThreadWorker))
        {
          ((NewThreadWorker)localWorker).shutdown();
          return;
        }
      }
      this.w.dispose();
    }
    
    public Runnable getWrappedRunnable()
    {
      return this.decoratedRun;
    }
    
    public boolean isDisposed()
    {
      return this.w.isDisposed();
    }
    
    public void run()
    {
      this.runner = Thread.currentThread();
      try
      {
        this.decoratedRun.run();
        return;
      }
      finally
      {
        dispose();
        this.runner = null;
      }
    }
  }
  
  static final class PeriodicDirectTask
    implements Disposable, Runnable, SchedulerRunnableIntrospection
  {
    volatile boolean disposed;
    final Runnable run;
    final Scheduler.Worker worker;
    
    PeriodicDirectTask(Runnable paramRunnable, Scheduler.Worker paramWorker)
    {
      this.run = paramRunnable;
      this.worker = paramWorker;
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.worker.dispose();
    }
    
    public Runnable getWrappedRunnable()
    {
      return this.run;
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 32	io/reactivex/Scheduler$PeriodicDirectTask:disposed	Z
      //   4: ifne +32 -> 36
      //   7: aload_0
      //   8: getfield 26	io/reactivex/Scheduler$PeriodicDirectTask:run	Ljava/lang/Runnable;
      //   11: invokeinterface 42 1 0
      //   16: goto +20 -> 36
      //   19: astore_1
      //   20: aload_1
      //   21: invokestatic 48	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   24: aload_0
      //   25: getfield 28	io/reactivex/Scheduler$PeriodicDirectTask:worker	Lio/reactivex/Scheduler$Worker;
      //   28: invokevirtual 36	io/reactivex/Scheduler$Worker:dispose	()V
      //   31: aload_1
      //   32: invokestatic 54	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
      //   35: athrow
      //   36: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	37	0	this	PeriodicDirectTask
      //   19	13	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   7	16	19	finally
    }
  }
  
  public static abstract class Worker
    implements Disposable
  {
    public Worker() {}
    
    public long now(TimeUnit paramTimeUnit)
    {
      return paramTimeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      return schedule(paramRunnable, 0L, TimeUnit.NANOSECONDS);
    }
    
    public abstract Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
    
    public Disposable schedulePeriodically(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
    {
      SequentialDisposable localSequentialDisposable1 = new SequentialDisposable();
      SequentialDisposable localSequentialDisposable2 = new SequentialDisposable(localSequentialDisposable1);
      paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
      paramLong2 = paramTimeUnit.toNanos(paramLong2);
      long l = now(TimeUnit.NANOSECONDS);
      paramRunnable = schedule(new PeriodicTask(l + paramTimeUnit.toNanos(paramLong1), paramRunnable, l, localSequentialDisposable2, paramLong2), paramLong1, paramTimeUnit);
      if (paramRunnable == EmptyDisposable.INSTANCE) {
        return paramRunnable;
      }
      localSequentialDisposable1.replace(paramRunnable);
      return localSequentialDisposable2;
    }
    
    final class PeriodicTask
      implements Runnable, SchedulerRunnableIntrospection
    {
      long count;
      final Runnable decoratedRun;
      long lastNowNanoseconds;
      final long periodInNanoseconds;
      final SequentialDisposable sd;
      long startInNanoseconds;
      
      PeriodicTask(long paramLong1, Runnable paramRunnable, long paramLong2, SequentialDisposable paramSequentialDisposable, long paramLong3)
      {
        this.decoratedRun = paramRunnable;
        this.sd = paramSequentialDisposable;
        this.periodInNanoseconds = paramLong3;
        this.lastNowNanoseconds = paramLong2;
        this.startInNanoseconds = paramLong1;
      }
      
      public Runnable getWrappedRunnable()
      {
        return this.decoratedRun;
      }
      
      public void run()
      {
        this.decoratedRun.run();
        if (!this.sd.isDisposed())
        {
          long l1 = Scheduler.Worker.this.now(TimeUnit.NANOSECONDS);
          long l2 = Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
          long l3 = this.lastNowNanoseconds;
          if ((l2 + l1 >= l3) && (l1 < l3 + this.periodInNanoseconds + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS))
          {
            l3 = this.startInNanoseconds;
            l2 = this.count + 1L;
            this.count = l2;
            l2 = l3 + l2 * this.periodInNanoseconds;
          }
          else
          {
            long l4 = this.periodInNanoseconds;
            l2 = l1 + l4;
            l3 = this.count + 1L;
            this.count = l3;
            this.startInNanoseconds = (l2 - l4 * l3);
          }
          this.lastNowNanoseconds = l1;
          this.sd.replace(Scheduler.Worker.this.schedule(this, l2 - l1, TimeUnit.NANOSECONDS));
        }
      }
    }
  }
}
