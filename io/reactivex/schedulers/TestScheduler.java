package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class TestScheduler
  extends Scheduler
{
  long counter;
  final Queue<TimedRunnable> queue = new PriorityBlockingQueue(11);
  volatile long time;
  
  public TestScheduler() {}
  
  public TestScheduler(long paramLong, TimeUnit paramTimeUnit)
  {
    this.time = paramTimeUnit.toNanos(paramLong);
  }
  
  private void triggerActions(long paramLong)
  {
    for (;;)
    {
      TimedRunnable localTimedRunnable = (TimedRunnable)this.queue.peek();
      if ((localTimedRunnable == null) || (localTimedRunnable.time > paramLong)) {
        break;
      }
      long l;
      if (localTimedRunnable.time == 0L) {
        l = this.time;
      } else {
        l = localTimedRunnable.time;
      }
      this.time = l;
      this.queue.remove(localTimedRunnable);
      if (!localTimedRunnable.scheduler.disposed) {
        localTimedRunnable.run.run();
      }
    }
    this.time = paramLong;
  }
  
  public void advanceTimeBy(long paramLong, TimeUnit paramTimeUnit)
  {
    advanceTimeTo(this.time + paramTimeUnit.toNanos(paramLong), TimeUnit.NANOSECONDS);
  }
  
  public void advanceTimeTo(long paramLong, TimeUnit paramTimeUnit)
  {
    triggerActions(paramTimeUnit.toNanos(paramLong));
  }
  
  public Scheduler.Worker createWorker()
  {
    return new TestWorker();
  }
  
  public long now(TimeUnit paramTimeUnit)
  {
    return paramTimeUnit.convert(this.time, TimeUnit.NANOSECONDS);
  }
  
  public void triggerActions()
  {
    triggerActions(this.time);
  }
  
  final class TestWorker
    extends Scheduler.Worker
  {
    volatile boolean disposed;
    
    TestWorker() {}
    
    public void dispose()
    {
      this.disposed = true;
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public long now(TimeUnit paramTimeUnit)
    {
      return TestScheduler.this.now(paramTimeUnit);
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      TestScheduler localTestScheduler = TestScheduler.this;
      long l = localTestScheduler.counter;
      localTestScheduler.counter = (1L + l);
      paramRunnable = new TestScheduler.TimedRunnable(this, 0L, paramRunnable, l);
      TestScheduler.this.queue.add(paramRunnable);
      return Disposables.fromRunnable(new QueueRemove(paramRunnable));
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (this.disposed) {
        return EmptyDisposable.INSTANCE;
      }
      long l1 = TestScheduler.this.time;
      long l2 = paramTimeUnit.toNanos(paramLong);
      paramTimeUnit = TestScheduler.this;
      paramLong = paramTimeUnit.counter;
      paramTimeUnit.counter = (1L + paramLong);
      paramRunnable = new TestScheduler.TimedRunnable(this, l1 + l2, paramRunnable, paramLong);
      TestScheduler.this.queue.add(paramRunnable);
      return Disposables.fromRunnable(new QueueRemove(paramRunnable));
    }
    
    final class QueueRemove
      implements Runnable
    {
      final TestScheduler.TimedRunnable timedAction;
      
      QueueRemove(TestScheduler.TimedRunnable paramTimedRunnable)
      {
        this.timedAction = paramTimedRunnable;
      }
      
      public void run()
      {
        TestScheduler.this.queue.remove(this.timedAction);
      }
    }
  }
  
  static final class TimedRunnable
    implements Comparable<TimedRunnable>
  {
    final long count;
    final Runnable run;
    final TestScheduler.TestWorker scheduler;
    final long time;
    
    TimedRunnable(TestScheduler.TestWorker paramTestWorker, long paramLong1, Runnable paramRunnable, long paramLong2)
    {
      this.time = paramLong1;
      this.run = paramRunnable;
      this.scheduler = paramTestWorker;
      this.count = paramLong2;
    }
    
    public int compareTo(TimedRunnable paramTimedRunnable)
    {
      long l1 = this.time;
      long l2 = paramTimedRunnable.time;
      if (l1 == l2) {
        return ObjectHelper.compare(this.count, paramTimedRunnable.count);
      }
      return ObjectHelper.compare(l1, l2);
    }
    
    public String toString()
    {
      return String.format("TimedRunnable(time = %d, run = %s)", new Object[] { Long.valueOf(this.time), this.run.toString() });
    }
  }
}
