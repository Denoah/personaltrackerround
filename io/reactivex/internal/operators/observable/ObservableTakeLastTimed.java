package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableTakeLastTimed<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final int bufferSize;
  final long count;
  final boolean delayError;
  final Scheduler scheduler;
  final long time;
  final TimeUnit unit;
  
  public ObservableTakeLastTimed(ObservableSource<T> paramObservableSource, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.count = paramLong1;
    this.time = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeLastTimedObserver(paramObserver, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
  }
  
  static final class TakeLastTimedObserver<T>
    extends AtomicBoolean
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -5677354903406201275L;
    volatile boolean cancelled;
    final long count;
    final boolean delayError;
    final Observer<? super T> downstream;
    Throwable error;
    final SpscLinkedArrayQueue<Object> queue;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;
    Disposable upstream;
    
    TakeLastTimedObserver(Observer<? super T> paramObserver, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.count = paramLong1;
      this.time = paramLong2;
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
        if (compareAndSet(false, true)) {
          this.queue.clear();
        }
      }
    }
    
    void drain()
    {
      if (!compareAndSet(false, true)) {
        return;
      }
      Observer localObserver = this.downstream;
      Object localObject1 = this.queue;
      boolean bool = this.delayError;
      for (;;)
      {
        if (this.cancelled)
        {
          ((SpscLinkedArrayQueue)localObject1).clear();
          return;
        }
        if (!bool)
        {
          localObject2 = this.error;
          if (localObject2 != null)
          {
            ((SpscLinkedArrayQueue)localObject1).clear();
            localObserver.onError((Throwable)localObject2);
            return;
          }
        }
        Object localObject3 = ((SpscLinkedArrayQueue)localObject1).poll();
        int i;
        if (localObject3 == null) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          localObject1 = this.error;
          if (localObject1 != null) {
            localObserver.onError((Throwable)localObject1);
          } else {
            localObserver.onComplete();
          }
          return;
        }
        Object localObject2 = ((SpscLinkedArrayQueue)localObject1).poll();
        if (((Long)localObject3).longValue() >= this.scheduler.now(this.unit) - this.time) {
          localObserver.onNext(localObject2);
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      drain();
    }
    
    public void onNext(T paramT)
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.time;
      long l3 = this.count;
      int i;
      if (l3 == Long.MAX_VALUE) {
        i = 1;
      } else {
        i = 0;
      }
      localSpscLinkedArrayQueue.offer(Long.valueOf(l1), paramT);
      while ((!localSpscLinkedArrayQueue.isEmpty()) && ((((Long)localSpscLinkedArrayQueue.peek()).longValue() <= l1 - l2) || ((i == 0) && (localSpscLinkedArrayQueue.size() >> 1 > l3))))
      {
        localSpscLinkedArrayQueue.poll();
        localSpscLinkedArrayQueue.poll();
      }
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
