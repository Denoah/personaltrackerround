package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class IoScheduler
  extends Scheduler
{
  static final RxThreadFactory EVICTOR_THREAD_FACTORY;
  private static final String EVICTOR_THREAD_NAME_PREFIX = "RxCachedWorkerPoolEvictor";
  private static final long KEEP_ALIVE_TIME;
  public static final long KEEP_ALIVE_TIME_DEFAULT = 60L;
  private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
  private static final String KEY_IO_PRIORITY = "rx2.io-priority";
  private static final String KEY_KEEP_ALIVE_TIME = "rx2.io-keep-alive-time";
  static final CachedWorkerPool NONE;
  static final ThreadWorker SHUTDOWN_THREAD_WORKER;
  static final RxThreadFactory WORKER_THREAD_FACTORY;
  private static final String WORKER_THREAD_NAME_PREFIX = "RxCachedThreadScheduler";
  final AtomicReference<CachedWorkerPool> pool;
  final ThreadFactory threadFactory;
  
  static
  {
    KEEP_ALIVE_TIME = Long.getLong("rx2.io-keep-alive-time", 60L).longValue();
    Object localObject = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
    SHUTDOWN_THREAD_WORKER = (ThreadWorker)localObject;
    ((ThreadWorker)localObject).dispose();
    int i = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
    WORKER_THREAD_FACTORY = new RxThreadFactory("RxCachedThreadScheduler", i);
    EVICTOR_THREAD_FACTORY = new RxThreadFactory("RxCachedWorkerPoolEvictor", i);
    localObject = new CachedWorkerPool(0L, null, WORKER_THREAD_FACTORY);
    NONE = (CachedWorkerPool)localObject;
    ((CachedWorkerPool)localObject).shutdown();
  }
  
  public IoScheduler()
  {
    this(WORKER_THREAD_FACTORY);
  }
  
  public IoScheduler(ThreadFactory paramThreadFactory)
  {
    this.threadFactory = paramThreadFactory;
    this.pool = new AtomicReference(NONE);
    start();
  }
  
  public Scheduler.Worker createWorker()
  {
    return new EventLoopWorker((CachedWorkerPool)this.pool.get());
  }
  
  public void shutdown()
  {
    CachedWorkerPool localCachedWorkerPool1;
    CachedWorkerPool localCachedWorkerPool2;
    do
    {
      localCachedWorkerPool1 = (CachedWorkerPool)this.pool.get();
      localCachedWorkerPool2 = NONE;
      if (localCachedWorkerPool1 == localCachedWorkerPool2) {
        return;
      }
    } while (!this.pool.compareAndSet(localCachedWorkerPool1, localCachedWorkerPool2));
    localCachedWorkerPool1.shutdown();
  }
  
  public int size()
  {
    return ((CachedWorkerPool)this.pool.get()).allWorkers.size();
  }
  
  public void start()
  {
    CachedWorkerPool localCachedWorkerPool = new CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, this.threadFactory);
    if (!this.pool.compareAndSet(NONE, localCachedWorkerPool)) {
      localCachedWorkerPool.shutdown();
    }
  }
  
  static final class CachedWorkerPool
    implements Runnable
  {
    final CompositeDisposable allWorkers;
    private final ScheduledExecutorService evictorService;
    private final Future<?> evictorTask;
    private final ConcurrentLinkedQueue<IoScheduler.ThreadWorker> expiringWorkerQueue;
    private final long keepAliveTime;
    private final ThreadFactory threadFactory;
    
    CachedWorkerPool(long paramLong, TimeUnit paramTimeUnit, ThreadFactory paramThreadFactory)
    {
      if (paramTimeUnit != null) {
        paramLong = paramTimeUnit.toNanos(paramLong);
      } else {
        paramLong = 0L;
      }
      this.keepAliveTime = paramLong;
      this.expiringWorkerQueue = new ConcurrentLinkedQueue();
      this.allWorkers = new CompositeDisposable();
      this.threadFactory = paramThreadFactory;
      paramThreadFactory = null;
      if (paramTimeUnit != null)
      {
        paramThreadFactory = Executors.newScheduledThreadPool(1, IoScheduler.EVICTOR_THREAD_FACTORY);
        paramLong = this.keepAliveTime;
        paramTimeUnit = paramThreadFactory.scheduleWithFixedDelay(this, paramLong, paramLong, TimeUnit.NANOSECONDS);
      }
      else
      {
        paramTimeUnit = null;
      }
      this.evictorService = paramThreadFactory;
      this.evictorTask = paramTimeUnit;
    }
    
    void evictExpiredWorkers()
    {
      if (!this.expiringWorkerQueue.isEmpty())
      {
        long l = now();
        Iterator localIterator = this.expiringWorkerQueue.iterator();
        while (localIterator.hasNext())
        {
          IoScheduler.ThreadWorker localThreadWorker = (IoScheduler.ThreadWorker)localIterator.next();
          if (localThreadWorker.getExpirationTime() > l) {
            break;
          }
          if (this.expiringWorkerQueue.remove(localThreadWorker)) {
            this.allWorkers.remove(localThreadWorker);
          }
        }
      }
    }
    
    IoScheduler.ThreadWorker get()
    {
      if (this.allWorkers.isDisposed()) {
        return IoScheduler.SHUTDOWN_THREAD_WORKER;
      }
      while (!this.expiringWorkerQueue.isEmpty())
      {
        localThreadWorker = (IoScheduler.ThreadWorker)this.expiringWorkerQueue.poll();
        if (localThreadWorker != null) {
          return localThreadWorker;
        }
      }
      IoScheduler.ThreadWorker localThreadWorker = new IoScheduler.ThreadWorker(this.threadFactory);
      this.allWorkers.add(localThreadWorker);
      return localThreadWorker;
    }
    
    long now()
    {
      return System.nanoTime();
    }
    
    void release(IoScheduler.ThreadWorker paramThreadWorker)
    {
      paramThreadWorker.setExpirationTime(now() + this.keepAliveTime);
      this.expiringWorkerQueue.offer(paramThreadWorker);
    }
    
    public void run()
    {
      evictExpiredWorkers();
    }
    
    void shutdown()
    {
      this.allWorkers.dispose();
      Object localObject = this.evictorTask;
      if (localObject != null) {
        ((Future)localObject).cancel(true);
      }
      localObject = this.evictorService;
      if (localObject != null) {
        ((ScheduledExecutorService)localObject).shutdownNow();
      }
    }
  }
  
  static final class EventLoopWorker
    extends Scheduler.Worker
  {
    final AtomicBoolean once = new AtomicBoolean();
    private final IoScheduler.CachedWorkerPool pool;
    private final CompositeDisposable tasks;
    private final IoScheduler.ThreadWorker threadWorker;
    
    EventLoopWorker(IoScheduler.CachedWorkerPool paramCachedWorkerPool)
    {
      this.pool = paramCachedWorkerPool;
      this.tasks = new CompositeDisposable();
      this.threadWorker = paramCachedWorkerPool.get();
    }
    
    public void dispose()
    {
      if (this.once.compareAndSet(false, true))
      {
        this.tasks.dispose();
        this.pool.release(this.threadWorker);
      }
    }
    
    public boolean isDisposed()
    {
      return this.once.get();
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (this.tasks.isDisposed()) {
        return EmptyDisposable.INSTANCE;
      }
      return this.threadWorker.scheduleActual(paramRunnable, paramLong, paramTimeUnit, this.tasks);
    }
  }
  
  static final class ThreadWorker
    extends NewThreadWorker
  {
    private long expirationTime = 0L;
    
    ThreadWorker(ThreadFactory paramThreadFactory)
    {
      super();
    }
    
    public long getExpirationTime()
    {
      return this.expirationTime;
    }
    
    public void setExpirationTime(long paramLong)
    {
      this.expirationTime = paramLong;
    }
  }
}
