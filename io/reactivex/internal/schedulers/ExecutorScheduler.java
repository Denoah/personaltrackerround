package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ExecutorScheduler
  extends Scheduler
{
  static final Scheduler HELPER = ;
  final Executor executor;
  final boolean interruptibleWorker;
  
  public ExecutorScheduler(Executor paramExecutor, boolean paramBoolean)
  {
    this.executor = paramExecutor;
    this.interruptibleWorker = paramBoolean;
  }
  
  public Scheduler.Worker createWorker()
  {
    return new ExecutorWorker(this.executor, this.interruptibleWorker);
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable)
  {
    paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
    try
    {
      if ((this.executor instanceof ExecutorService))
      {
        localObject = new io/reactivex/internal/schedulers/ScheduledDirectTask;
        ((ScheduledDirectTask)localObject).<init>(paramRunnable);
        ((ScheduledDirectTask)localObject).setFuture(((ExecutorService)this.executor).submit((Callable)localObject));
        return localObject;
      }
      if (this.interruptibleWorker)
      {
        localObject = new io/reactivex/internal/schedulers/ExecutorScheduler$ExecutorWorker$InterruptibleRunnable;
        ((ExecutorScheduler.ExecutorWorker.InterruptibleRunnable)localObject).<init>(paramRunnable, null);
        this.executor.execute((Runnable)localObject);
        return localObject;
      }
      Object localObject = new io/reactivex/internal/schedulers/ExecutorScheduler$ExecutorWorker$BooleanRunnable;
      ((ExecutorScheduler.ExecutorWorker.BooleanRunnable)localObject).<init>(paramRunnable);
      this.executor.execute((Runnable)localObject);
      return localObject;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      RxJavaPlugins.onError(paramRunnable);
    }
    return EmptyDisposable.INSTANCE;
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
    if ((this.executor instanceof ScheduledExecutorService)) {
      try
      {
        ScheduledDirectTask localScheduledDirectTask = new io/reactivex/internal/schedulers/ScheduledDirectTask;
        localScheduledDirectTask.<init>(paramRunnable);
        localScheduledDirectTask.setFuture(((ScheduledExecutorService)this.executor).schedule(localScheduledDirectTask, paramLong, paramTimeUnit));
        return localScheduledDirectTask;
      }
      catch (RejectedExecutionException paramRunnable)
      {
        RxJavaPlugins.onError(paramRunnable);
        return EmptyDisposable.INSTANCE;
      }
    }
    paramRunnable = new DelayedRunnable(paramRunnable);
    paramTimeUnit = HELPER.scheduleDirect(new DelayedDispose(paramRunnable), paramLong, paramTimeUnit);
    paramRunnable.timed.replace(paramTimeUnit);
    return paramRunnable;
  }
  
  public Disposable schedulePeriodicallyDirect(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    if ((this.executor instanceof ScheduledExecutorService))
    {
      paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
      try
      {
        ScheduledDirectPeriodicTask localScheduledDirectPeriodicTask = new io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask;
        localScheduledDirectPeriodicTask.<init>(paramRunnable);
        localScheduledDirectPeriodicTask.setFuture(((ScheduledExecutorService)this.executor).scheduleAtFixedRate(localScheduledDirectPeriodicTask, paramLong1, paramLong2, paramTimeUnit));
        return localScheduledDirectPeriodicTask;
      }
      catch (RejectedExecutionException paramRunnable)
      {
        RxJavaPlugins.onError(paramRunnable);
        return EmptyDisposable.INSTANCE;
      }
    }
    return super.schedulePeriodicallyDirect(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
  }
  
  final class DelayedDispose
    implements Runnable
  {
    private final ExecutorScheduler.DelayedRunnable dr;
    
    DelayedDispose(ExecutorScheduler.DelayedRunnable paramDelayedRunnable)
    {
      this.dr = paramDelayedRunnable;
    }
    
    public void run()
    {
      this.dr.direct.replace(ExecutorScheduler.this.scheduleDirect(this.dr));
    }
  }
  
  static final class DelayedRunnable
    extends AtomicReference<Runnable>
    implements Runnable, Disposable, SchedulerRunnableIntrospection
  {
    private static final long serialVersionUID = -4101336210206799084L;
    final SequentialDisposable direct = new SequentialDisposable();
    final SequentialDisposable timed = new SequentialDisposable();
    
    DelayedRunnable(Runnable paramRunnable)
    {
      super();
    }
    
    public void dispose()
    {
      if (getAndSet(null) != null)
      {
        this.timed.dispose();
        this.direct.dispose();
      }
    }
    
    public Runnable getWrappedRunnable()
    {
      Runnable localRunnable = (Runnable)get();
      if (localRunnable == null) {
        localRunnable = Functions.EMPTY_RUNNABLE;
      }
      return localRunnable;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void run()
    {
      Runnable localRunnable = (Runnable)get();
      if (localRunnable != null) {
        try
        {
          localRunnable.run();
          lazySet(null);
          this.timed.lazySet(DisposableHelper.DISPOSED);
          this.direct.lazySet(DisposableHelper.DISPOSED);
        }
        finally
        {
          lazySet(null);
          this.timed.lazySet(DisposableHelper.DISPOSED);
          this.direct.lazySet(DisposableHelper.DISPOSED);
        }
      }
    }
  }
  
  public static final class ExecutorWorker
    extends Scheduler.Worker
    implements Runnable
  {
    volatile boolean disposed;
    final Executor executor;
    final boolean interruptibleWorker;
    final MpscLinkedQueue<Runnable> queue;
    final CompositeDisposable tasks = new CompositeDisposable();
    final AtomicInteger wip = new AtomicInteger();
    
    public ExecutorWorker(Executor paramExecutor, boolean paramBoolean)
    {
      this.executor = paramExecutor;
      this.queue = new MpscLinkedQueue();
      this.interruptibleWorker = paramBoolean;
    }
    
    public void dispose()
    {
      if (!this.disposed)
      {
        this.disposed = true;
        this.tasks.dispose();
        if (this.wip.getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public void run()
    {
      MpscLinkedQueue localMpscLinkedQueue = this.queue;
      int i = 1;
      if (this.disposed)
      {
        localMpscLinkedQueue.clear();
        return;
      }
      do
      {
        Runnable localRunnable = (Runnable)localMpscLinkedQueue.poll();
        if (localRunnable == null)
        {
          if (this.disposed)
          {
            localMpscLinkedQueue.clear();
            return;
          }
          int j = this.wip.addAndGet(-i);
          i = j;
          if (j != 0) {
            break;
          }
          return;
        }
        localRunnable.run();
      } while (!this.disposed);
      localMpscLinkedQueue.clear();
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
      if (this.interruptibleWorker)
      {
        paramRunnable = new InterruptibleRunnable(paramRunnable, this.tasks);
        this.tasks.add(paramRunnable);
      }
      else
      {
        paramRunnable = new BooleanRunnable(paramRunnable);
      }
      this.queue.offer(paramRunnable);
      if (this.wip.getAndIncrement() == 0) {
        try
        {
          this.executor.execute(this);
        }
        catch (RejectedExecutionException paramRunnable)
        {
          this.disposed = true;
          this.queue.clear();
          RxJavaPlugins.onError(paramRunnable);
          return EmptyDisposable.INSTANCE;
        }
      }
      return paramRunnable;
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (paramLong <= 0L) {
        return schedule(paramRunnable);
      }
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      SequentialDisposable localSequentialDisposable1 = new SequentialDisposable();
      SequentialDisposable localSequentialDisposable2 = new SequentialDisposable(localSequentialDisposable1);
      paramRunnable = new ScheduledRunnable(new SequentialDispose(localSequentialDisposable2, RxJavaPlugins.onSchedule(paramRunnable)), this.tasks);
      this.tasks.add(paramRunnable);
      Executor localExecutor = this.executor;
      if ((localExecutor instanceof ScheduledExecutorService)) {
        try
        {
          paramRunnable.setFuture(((ScheduledExecutorService)localExecutor).schedule(paramRunnable, paramLong, paramTimeUnit));
        }
        catch (RejectedExecutionException paramRunnable)
        {
          this.disposed = true;
          RxJavaPlugins.onError(paramRunnable);
          return EmptyDisposable.INSTANCE;
        }
      } else {
        paramRunnable.setFuture(new DisposeOnCancel(ExecutorScheduler.HELPER.scheduleDirect(paramRunnable, paramLong, paramTimeUnit)));
      }
      localSequentialDisposable1.replace(paramRunnable);
      return localSequentialDisposable2;
    }
    
    static final class BooleanRunnable
      extends AtomicBoolean
      implements Runnable, Disposable
    {
      private static final long serialVersionUID = -2421395018820541164L;
      final Runnable actual;
      
      BooleanRunnable(Runnable paramRunnable)
      {
        this.actual = paramRunnable;
      }
      
      public void dispose()
      {
        lazySet(true);
      }
      
      public boolean isDisposed()
      {
        return get();
      }
      
      public void run()
      {
        if (get()) {
          return;
        }
        try
        {
          this.actual.run();
          return;
        }
        finally
        {
          lazySet(true);
        }
      }
    }
    
    static final class InterruptibleRunnable
      extends AtomicInteger
      implements Runnable, Disposable
    {
      static final int FINISHED = 2;
      static final int INTERRUPTED = 4;
      static final int INTERRUPTING = 3;
      static final int READY = 0;
      static final int RUNNING = 1;
      private static final long serialVersionUID = -3603436687413320876L;
      final Runnable run;
      final DisposableContainer tasks;
      volatile Thread thread;
      
      InterruptibleRunnable(Runnable paramRunnable, DisposableContainer paramDisposableContainer)
      {
        this.run = paramRunnable;
        this.tasks = paramDisposableContainer;
      }
      
      void cleanup()
      {
        DisposableContainer localDisposableContainer = this.tasks;
        if (localDisposableContainer != null) {
          localDisposableContainer.delete(this);
        }
      }
      
      public void dispose()
      {
        do
        {
          do
          {
            int i = get();
            if (i >= 2) {
              return;
            }
            if (i != 0) {
              break;
            }
          } while (!compareAndSet(0, 4));
          cleanup();
          break;
        } while (!compareAndSet(1, 3));
        Thread localThread = this.thread;
        if (localThread != null)
        {
          localThread.interrupt();
          this.thread = null;
        }
        set(4);
        cleanup();
      }
      
      public boolean isDisposed()
      {
        boolean bool;
        if (get() >= 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void run()
      {
        if (get() == 0)
        {
          this.thread = Thread.currentThread();
          if (compareAndSet(0, 1)) {}
          try
          {
            this.run.run();
            this.thread = null;
            if (compareAndSet(1, 2))
            {
              cleanup();
            }
            else
            {
              while (get() == 3) {
                Thread.yield();
              }
              Thread.interrupted();
            }
          }
          finally
          {
            this.thread = null;
            if (!compareAndSet(1, 2))
            {
              while (get() == 3) {
                Thread.yield();
              }
              Thread.interrupted();
            }
            else
            {
              cleanup();
            }
          }
        }
      }
    }
    
    final class SequentialDispose
      implements Runnable
    {
      private final Runnable decoratedRun;
      private final SequentialDisposable mar;
      
      SequentialDispose(SequentialDisposable paramSequentialDisposable, Runnable paramRunnable)
      {
        this.mar = paramSequentialDisposable;
        this.decoratedRun = paramRunnable;
      }
      
      public void run()
      {
        this.mar.replace(ExecutorScheduler.ExecutorWorker.this.schedule(this.decoratedRun));
      }
    }
  }
}
