package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ComputationScheduler
  extends Scheduler
  implements SchedulerMultiWorkerSupport
{
  private static final String KEY_COMPUTATION_PRIORITY = "rx2.computation-priority";
  static final String KEY_MAX_THREADS = "rx2.computation-threads";
  static final int MAX_THREADS = cap(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx2.computation-threads", 0).intValue());
  static final FixedSchedulerPool NONE;
  static final PoolWorker SHUTDOWN_WORKER;
  static final RxThreadFactory THREAD_FACTORY;
  private static final String THREAD_NAME_PREFIX = "RxComputationThreadPool";
  final AtomicReference<FixedSchedulerPool> pool;
  final ThreadFactory threadFactory;
  
  static
  {
    Object localObject = new PoolWorker(new RxThreadFactory("RxComputationShutdown"));
    SHUTDOWN_WORKER = (PoolWorker)localObject;
    ((PoolWorker)localObject).dispose();
    localObject = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5).intValue())), true);
    THREAD_FACTORY = (RxThreadFactory)localObject;
    localObject = new FixedSchedulerPool(0, (ThreadFactory)localObject);
    NONE = (FixedSchedulerPool)localObject;
    ((FixedSchedulerPool)localObject).shutdown();
  }
  
  public ComputationScheduler()
  {
    this(THREAD_FACTORY);
  }
  
  public ComputationScheduler(ThreadFactory paramThreadFactory)
  {
    this.threadFactory = paramThreadFactory;
    this.pool = new AtomicReference(NONE);
    start();
  }
  
  static int cap(int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    if (paramInt2 > 0) {
      if (paramInt2 > paramInt1) {
        i = paramInt1;
      } else {
        i = paramInt2;
      }
    }
    return i;
  }
  
  public Scheduler.Worker createWorker()
  {
    return new EventLoopWorker(((FixedSchedulerPool)this.pool.get()).getEventLoop());
  }
  
  public void createWorkers(int paramInt, SchedulerMultiWorkerSupport.WorkerCallback paramWorkerCallback)
  {
    ObjectHelper.verifyPositive(paramInt, "number > 0 required");
    ((FixedSchedulerPool)this.pool.get()).createWorkers(paramInt, paramWorkerCallback);
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    return ((FixedSchedulerPool)this.pool.get()).getEventLoop().scheduleDirect(paramRunnable, paramLong, paramTimeUnit);
  }
  
  public Disposable schedulePeriodicallyDirect(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    return ((FixedSchedulerPool)this.pool.get()).getEventLoop().schedulePeriodicallyDirect(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
  }
  
  public void shutdown()
  {
    FixedSchedulerPool localFixedSchedulerPool1;
    FixedSchedulerPool localFixedSchedulerPool2;
    do
    {
      localFixedSchedulerPool1 = (FixedSchedulerPool)this.pool.get();
      localFixedSchedulerPool2 = NONE;
      if (localFixedSchedulerPool1 == localFixedSchedulerPool2) {
        return;
      }
    } while (!this.pool.compareAndSet(localFixedSchedulerPool1, localFixedSchedulerPool2));
    localFixedSchedulerPool1.shutdown();
  }
  
  public void start()
  {
    FixedSchedulerPool localFixedSchedulerPool = new FixedSchedulerPool(MAX_THREADS, this.threadFactory);
    if (!this.pool.compareAndSet(NONE, localFixedSchedulerPool)) {
      localFixedSchedulerPool.shutdown();
    }
  }
  
  static final class EventLoopWorker
    extends Scheduler.Worker
  {
    private final ListCompositeDisposable both;
    volatile boolean disposed;
    private final ComputationScheduler.PoolWorker poolWorker;
    private final ListCompositeDisposable serial;
    private final CompositeDisposable timed;
    
    EventLoopWorker(ComputationScheduler.PoolWorker paramPoolWorker)
    {
      this.poolWorker = paramPoolWorker;
      this.serial = new ListCompositeDisposable();
      this.timed = new CompositeDisposable();
      paramPoolWorker = new ListCompositeDisposable();
      this.both = paramPoolWorker;
      paramPoolWorker.add(this.serial);
      this.both.add(this.timed);
    }
    
    public void dispose()
    {
      if (!this.disposed)
      {
        this.disposed = true;
        this.both.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      return this.poolWorker.scheduleActual(paramRunnable, 0L, TimeUnit.MILLISECONDS, this.serial);
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      return this.poolWorker.scheduleActual(paramRunnable, paramLong, paramTimeUnit, this.timed);
    }
  }
  
  static final class FixedSchedulerPool
    implements SchedulerMultiWorkerSupport
  {
    final int cores;
    final ComputationScheduler.PoolWorker[] eventLoops;
    long n;
    
    FixedSchedulerPool(int paramInt, ThreadFactory paramThreadFactory)
    {
      this.cores = paramInt;
      this.eventLoops = new ComputationScheduler.PoolWorker[paramInt];
      for (int i = 0; i < paramInt; i++) {
        this.eventLoops[i] = new ComputationScheduler.PoolWorker(paramThreadFactory);
      }
    }
    
    public void createWorkers(int paramInt, SchedulerMultiWorkerSupport.WorkerCallback paramWorkerCallback)
    {
      int i = this.cores;
      int j = 0;
      if (i == 0) {
        while (j < paramInt)
        {
          paramWorkerCallback.onWorker(j, ComputationScheduler.SHUTDOWN_WORKER);
          j++;
        }
      }
      j = (int)this.n % i;
      for (int k = 0; k < paramInt; k++)
      {
        paramWorkerCallback.onWorker(k, new ComputationScheduler.EventLoopWorker(this.eventLoops[j]));
        int m = j + 1;
        j = m;
        if (m == i) {
          j = 0;
        }
      }
      this.n = j;
    }
    
    public ComputationScheduler.PoolWorker getEventLoop()
    {
      int i = this.cores;
      if (i == 0) {
        return ComputationScheduler.SHUTDOWN_WORKER;
      }
      ComputationScheduler.PoolWorker[] arrayOfPoolWorker = this.eventLoops;
      long l = this.n;
      this.n = (1L + l);
      return arrayOfPoolWorker[((int)(l % i))];
    }
    
    public void shutdown()
    {
      ComputationScheduler.PoolWorker[] arrayOfPoolWorker = this.eventLoops;
      int i = arrayOfPoolWorker.length;
      for (int j = 0; j < i; j++) {
        arrayOfPoolWorker[j].dispose();
      }
    }
  }
  
  static final class PoolWorker
    extends NewThreadWorker
  {
    PoolWorker(ThreadFactory paramThreadFactory)
    {
      super();
    }
  }
}
