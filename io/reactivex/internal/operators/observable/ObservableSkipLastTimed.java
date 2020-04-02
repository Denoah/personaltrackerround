package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSkipLastTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final int bufferSize;
  final boolean delayError;
  final Scheduler scheduler;
  final long time;
  final TimeUnit unit;
  
  public ObservableSkipLastTimed(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.time = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new SkipLastTimedObserver(paramObserver, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
  }
  
  static final class SkipLastTimedObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -5677354903406201275L;
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    final Observer<? super T> downstream;
    Throwable error;
    final SpscLinkedArrayQueue<Object> queue;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;
    Disposable upstream;
    
    SkipLastTimedObserver(Observer<? super T> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.time = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.delayError = paramBoolean;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.dispose();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = this.downstream;
      Object localObject1 = this.queue;
      boolean bool1 = this.delayError;
      TimeUnit localTimeUnit = this.unit;
      Scheduler localScheduler = this.scheduler;
      long l1 = this.time;
      int i = 1;
      for (;;)
      {
        if (this.cancelled)
        {
          this.queue.clear();
          return;
        }
        boolean bool2 = this.done;
        Object localObject2 = (Long)((SpscLinkedArrayQueue)localObject1).peek();
        int j;
        if (localObject2 == null) {
          j = 1;
        } else {
          j = 0;
        }
        long l2 = localScheduler.now(localTimeUnit);
        int k = j;
        if (j == 0)
        {
          k = j;
          if (((Long)localObject2).longValue() > l2 - l1) {
            k = 1;
          }
        }
        if (bool2) {
          if (bool1)
          {
            if (k != 0)
            {
              localObject1 = this.error;
              if (localObject1 != null) {
                localObserver.onError((Throwable)localObject1);
              } else {
                localObserver.onComplete();
              }
            }
          }
          else
          {
            localObject2 = this.error;
            if (localObject2 != null)
            {
              this.queue.clear();
              localObserver.onError((Throwable)localObject2);
              return;
            }
            if (k != 0)
            {
              localObserver.onComplete();
              return;
            }
          }
        }
        if (k != 0)
        {
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else
        {
          ((SpscLinkedArrayQueue)localObject1).poll();
          localObserver.onNext(((SpscLinkedArrayQueue)localObject1).poll());
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
      this.queue.offer(Long.valueOf(this.scheduler.now(this.unit)), paramT);
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
  }
}
