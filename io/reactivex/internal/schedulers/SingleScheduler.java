package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleScheduler
  extends Scheduler
{
  private static final String KEY_SINGLE_PRIORITY = "rx2.single-priority";
  static final ScheduledExecutorService SHUTDOWN;
  static final RxThreadFactory SINGLE_THREAD_FACTORY = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5).intValue())), true);
  private static final String THREAD_NAME_PREFIX = "RxSingleScheduler";
  final AtomicReference<ScheduledExecutorService> executor;
  final ThreadFactory threadFactory;
  
  static
  {
    ScheduledExecutorService localScheduledExecutorService = Executors.newScheduledThreadPool(0);
    SHUTDOWN = localScheduledExecutorService;
    localScheduledExecutorService.shutdown();
  }
  
  public SingleScheduler()
  {
    this(SINGLE_THREAD_FACTORY);
  }
  
  public SingleScheduler(ThreadFactory paramThreadFactory)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    this.executor = localAtomicReference;
    this.threadFactory = paramThreadFactory;
    localAtomicReference.lazySet(createExecutor(paramThreadFactory));
  }
  
  static ScheduledExecutorService createExecutor(ThreadFactory paramThreadFactory)
  {
    return SchedulerPoolFactory.create(paramThreadFactory);
  }
  
  public Scheduler.Worker createWorker()
  {
    return new ScheduledWorker((ScheduledExecutorService)this.executor.get());
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    ScheduledDirectTask localScheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(paramRunnable));
    if (paramLong <= 0L) {}
    try
    {
      paramRunnable = ((ScheduledExecutorService)this.executor.get()).submit(localScheduledDirectTask);
      break label61;
      paramRunnable = ((ScheduledExecutorService)this.executor.get()).schedule(localScheduledDirectTask, paramLong, paramTimeUnit);
      label61:
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
    Object localObject = RxJavaPlugins.onSchedule(paramRunnable);
    if (paramLong2 <= 0L)
    {
      paramRunnable = (ScheduledExecutorService)this.executor.get();
      localObject = new InstantPeriodicTask((Runnable)localObject, paramRunnable);
      if (paramLong1 <= 0L) {}
      try
      {
        paramRunnable = paramRunnable.submit((Callable)localObject);
        break label66;
        paramRunnable = paramRunnable.schedule((Callable)localObject, paramLong1, paramTimeUnit);
        label66:
        ((InstantPeriodicTask)localObject).setFirst(paramRunnable);
        return localObject;
      }
      catch (RejectedExecutionException paramRunnable)
      {
        RxJavaPlugins.onError(paramRunnable);
        return EmptyDisposable.INSTANCE;
      }
    }
    paramRunnable = new ScheduledDirectPeriodicTask((Runnable)localObject);
    try
    {
      paramRunnable.setFuture(((ScheduledExecutorService)this.executor.get()).scheduleAtFixedRate(paramRunnable, paramLong1, paramLong2, paramTimeUnit));
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
    ScheduledExecutorService localScheduledExecutorService1 = (ScheduledExecutorService)this.executor.get();
    ScheduledExecutorService localScheduledExecutorService2 = SHUTDOWN;
    if (localScheduledExecutorService1 != localScheduledExecutorService2)
    {
      localScheduledExecutorService1 = (ScheduledExecutorService)this.executor.getAndSet(localScheduledExecutorService2);
      if (localScheduledExecutorService1 != SHUTDOWN) {
        localScheduledExecutorService1.shutdownNow();
      }
    }
  }
  
  public void start()
  {
    Object localObject1 = null;
    ScheduledExecutorService localScheduledExecutorService;
    Object localObject2;
    do
    {
      localScheduledExecutorService = (ScheduledExecutorService)this.executor.get();
      if (localScheduledExecutorService != SHUTDOWN)
      {
        if (localObject1 != null) {
          localObject1.shutdown();
        }
        return;
      }
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = createExecutor(this.threadFactory);
      }
      localObject1 = localObject2;
    } while (!this.executor.compareAndSet(localScheduledExecutorService, localObject2));
  }
  
  static final class ScheduledWorker
    extends Scheduler.Worker
  {
    volatile boolean disposed;
    final ScheduledExecutorService executor;
    final CompositeDisposable tasks;
    
    ScheduledWorker(ScheduledExecutorService paramScheduledExecutorService)
    {
      this.executor = paramScheduledExecutorService;
      this.tasks = new CompositeDisposable();
    }
    
    public void dispose()
    {
      if (!this.disposed)
      {
        this.disposed = true;
        this.tasks.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      ScheduledRunnable localScheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(paramRunnable), this.tasks);
      this.tasks.add(localScheduledRunnable);
      if (paramLong <= 0L) {}
      try
      {
        paramRunnable = this.executor.submit(localScheduledRunnable);
        break label74;
        paramRunnable = this.executor.schedule(localScheduledRunnable, paramLong, paramTimeUnit);
        label74:
        localScheduledRunnable.setFuture(paramRunnable);
        return localScheduledRunnable;
      }
      catch (RejectedExecutionException paramRunnable)
      {
        dispose();
        RxJavaPlugins.onError(paramRunnable);
      }
      return EmptyDisposable.INSTANCE;
    }
  }
}
