package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelJoin<T>
  extends Flowable<T>
{
  final boolean delayErrors;
  final int prefetch;
  final ParallelFlowable<? extends T> source;
  
  public ParallelJoin(ParallelFlowable<? extends T> paramParallelFlowable, int paramInt, boolean paramBoolean)
  {
    this.source = paramParallelFlowable;
    this.prefetch = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject;
    if (this.delayErrors) {
      localObject = new JoinSubscriptionDelayError(paramSubscriber, this.source.parallelism(), this.prefetch);
    } else {
      localObject = new JoinSubscription(paramSubscriber, this.source.parallelism(), this.prefetch);
    }
    paramSubscriber.onSubscribe((Subscription)localObject);
    this.source.subscribe(((JoinSubscriptionBase)localObject).subscribers);
  }
  
  static final class JoinInnerSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 8410034718427740355L;
    final int limit;
    final ParallelJoin.JoinSubscriptionBase<T> parent;
    final int prefetch;
    long produced;
    volatile SimplePlainQueue<T> queue;
    
    JoinInnerSubscriber(ParallelJoin.JoinSubscriptionBase<T> paramJoinSubscriptionBase, int paramInt)
    {
      this.parent = paramJoinSubscriptionBase;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
    }
    
    public boolean cancel()
    {
      return SubscriptionHelper.cancel(this);
    }
    
    SimplePlainQueue<T> getQueue()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Object localObject = localSimplePlainQueue;
      if (localSimplePlainQueue == null)
      {
        localObject = new SpscArrayQueue(this.prefetch);
        this.queue = ((SimplePlainQueue)localObject);
      }
      return localObject;
    }
    
    public void onComplete()
    {
      this.parent.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.parent.onNext(this, paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, this.prefetch);
    }
    
    public void request(long paramLong)
    {
      paramLong = this.produced + paramLong;
      if (paramLong >= this.limit)
      {
        this.produced = 0L;
        ((Subscription)get()).request(paramLong);
      }
      else
      {
        this.produced = paramLong;
      }
    }
    
    public void requestOne()
    {
      long l = this.produced + 1L;
      if (l == this.limit)
      {
        this.produced = 0L;
        ((Subscription)get()).request(l);
      }
      else
      {
        this.produced = l;
      }
    }
  }
  
  static final class JoinSubscription<T>
    extends ParallelJoin.JoinSubscriptionBase<T>
  {
    private static final long serialVersionUID = 6312374661811000451L;
    
    JoinSubscription(Subscriber<? super T> paramSubscriber, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      drainLoop();
    }
    
    void drainLoop()
    {
      ParallelJoin.JoinInnerSubscriber[] arrayOfJoinInnerSubscriber = this.subscribers;
      int i = arrayOfJoinInnerSubscriber.length;
      Subscriber localSubscriber = this.downstream;
      int k;
      for (int j = 1;; j = k)
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        long l3;
        Object localObject1;
        int n;
        do
        {
          l3 = l2;
          if (l2 == l1) {
            break;
          }
          if (this.cancelled)
          {
            cleanup();
            return;
          }
          localObject1 = (Throwable)this.errors.get();
          if (localObject1 != null)
          {
            cleanup();
            localSubscriber.onError((Throwable)localObject1);
            return;
          }
          if (this.done.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          int m = 0;
          n = 1;
          l3 = l2;
          while (m < arrayOfJoinInnerSubscriber.length)
          {
            localObject1 = arrayOfJoinInnerSubscriber[m];
            Object localObject2 = ((ParallelJoin.JoinInnerSubscriber)localObject1).queue;
            l2 = l3;
            i1 = n;
            if (localObject2 != null)
            {
              localObject2 = ((SimplePlainQueue)localObject2).poll();
              l2 = l3;
              i1 = n;
              if (localObject2 != null)
              {
                localSubscriber.onNext(localObject2);
                ((ParallelJoin.JoinInnerSubscriber)localObject1).requestOne();
                l2 = l3 + 1L;
                if (l2 == l1)
                {
                  l3 = l2;
                  break label243;
                }
                i1 = 0;
              }
            }
            m++;
            l3 = l2;
            n = i1;
          }
          if ((k != 0) && (n != 0))
          {
            localSubscriber.onComplete();
            return;
          }
          l2 = l3;
        } while (n == 0);
        label243:
        if (l3 == l1)
        {
          if (this.cancelled)
          {
            cleanup();
            return;
          }
          localObject1 = (Throwable)this.errors.get();
          if (localObject1 != null)
          {
            cleanup();
            localSubscriber.onError((Throwable)localObject1);
            return;
          }
          if (this.done.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          for (i1 = 0; i1 < i; i1++)
          {
            localObject1 = arrayOfJoinInnerSubscriber[i1].queue;
            if ((localObject1 != null) && (!((SimpleQueue)localObject1).isEmpty()))
            {
              i1 = 0;
              break label360;
            }
          }
          i1 = 1;
          label360:
          if ((k != 0) && (i1 != 0))
          {
            localSubscriber.onComplete();
            return;
          }
        }
        if ((l3 != 0L) && (l1 != Long.MAX_VALUE)) {
          this.requested.addAndGet(-l3);
        }
        int i1 = get();
        k = i1;
        if (i1 == j)
        {
          j = addAndGet(-j);
          k = j;
          if (j == 0) {
            return;
          }
        }
      }
    }
    
    public void onComplete()
    {
      this.done.decrementAndGet();
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.compareAndSet(null, paramThrowable))
      {
        cancelAll();
        drain();
      }
      else if (paramThrowable != this.errors.get())
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(ParallelJoin.JoinInnerSubscriber<T> paramJoinInnerSubscriber, T paramT)
    {
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        if (this.requested.get() != 0L)
        {
          this.downstream.onNext(paramT);
          if (this.requested.get() != Long.MAX_VALUE) {
            this.requested.decrementAndGet();
          }
          paramJoinInnerSubscriber.request(1L);
        }
        else if (!paramJoinInnerSubscriber.getQueue().offer(paramT))
        {
          cancelAll();
          paramJoinInnerSubscriber = new MissingBackpressureException("Queue full?!");
          if (this.errors.compareAndSet(null, paramJoinInnerSubscriber)) {
            this.downstream.onError(paramJoinInnerSubscriber);
          } else {
            RxJavaPlugins.onError(paramJoinInnerSubscriber);
          }
          return;
        }
        if (decrementAndGet() != 0) {}
      }
      else
      {
        if (!paramJoinInnerSubscriber.getQueue().offer(paramT))
        {
          cancelAll();
          onError(new MissingBackpressureException("Queue full?!"));
          return;
        }
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
  }
  
  static abstract class JoinSubscriptionBase<T>
    extends AtomicInteger
    implements Subscription
  {
    private static final long serialVersionUID = 3100232009247827843L;
    volatile boolean cancelled;
    final AtomicInteger done = new AtomicInteger();
    final Subscriber<? super T> downstream;
    final AtomicThrowable errors = new AtomicThrowable();
    final AtomicLong requested = new AtomicLong();
    final ParallelJoin.JoinInnerSubscriber<T>[] subscribers;
    
    JoinSubscriptionBase(Subscriber<? super T> paramSubscriber, int paramInt1, int paramInt2)
    {
      this.downstream = paramSubscriber;
      paramSubscriber = new ParallelJoin.JoinInnerSubscriber[paramInt1];
      for (int i = 0; i < paramInt1; i++) {
        paramSubscriber[i] = new ParallelJoin.JoinInnerSubscriber(this, paramInt2);
      }
      this.subscribers = paramSubscriber;
      this.done.lazySet(paramInt1);
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelAll();
        if (getAndIncrement() == 0) {
          cleanup();
        }
      }
    }
    
    void cancelAll()
    {
      ParallelJoin.JoinInnerSubscriber[] arrayOfJoinInnerSubscriber = this.subscribers;
      int i = arrayOfJoinInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfJoinInnerSubscriber[j].cancel();
      }
    }
    
    void cleanup()
    {
      ParallelJoin.JoinInnerSubscriber[] arrayOfJoinInnerSubscriber = this.subscribers;
      int i = arrayOfJoinInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfJoinInnerSubscriber[j].queue = null;
      }
    }
    
    abstract void drain();
    
    abstract void onComplete();
    
    abstract void onError(Throwable paramThrowable);
    
    abstract void onNext(ParallelJoin.JoinInnerSubscriber<T> paramJoinInnerSubscriber, T paramT);
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
  
  static final class JoinSubscriptionDelayError<T>
    extends ParallelJoin.JoinSubscriptionBase<T>
  {
    private static final long serialVersionUID = -5737965195918321883L;
    
    JoinSubscriptionDelayError(Subscriber<? super T> paramSubscriber, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      drainLoop();
    }
    
    void drainLoop()
    {
      ParallelJoin.JoinInnerSubscriber[] arrayOfJoinInnerSubscriber = this.subscribers;
      int i = arrayOfJoinInnerSubscriber.length;
      Subscriber localSubscriber = this.downstream;
      int k;
      for (int j = 1;; j = k)
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        long l3;
        Object localObject1;
        do
        {
          l3 = l2;
          if (l2 == l1) {
            break;
          }
          if (this.cancelled)
          {
            cleanup();
            return;
          }
          if (this.done.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          int m = 0;
          n = 1;
          l3 = l2;
          while (m < i)
          {
            localObject1 = arrayOfJoinInnerSubscriber[m];
            Object localObject2 = ((ParallelJoin.JoinInnerSubscriber)localObject1).queue;
            l2 = l3;
            int i1 = n;
            if (localObject2 != null)
            {
              localObject2 = ((SimplePlainQueue)localObject2).poll();
              l2 = l3;
              i1 = n;
              if (localObject2 != null)
              {
                localSubscriber.onNext(localObject2);
                ((ParallelJoin.JoinInnerSubscriber)localObject1).requestOne();
                l2 = l3 + 1L;
                if (l2 == l1)
                {
                  l3 = l2;
                  break label241;
                }
                i1 = 0;
              }
            }
            m++;
            l3 = l2;
            n = i1;
          }
          if ((k != 0) && (n != 0))
          {
            if ((Throwable)this.errors.get() != null) {
              localSubscriber.onError(this.errors.terminate());
            } else {
              localSubscriber.onComplete();
            }
            return;
          }
          l2 = l3;
        } while (n == 0);
        label241:
        if (l3 == l1)
        {
          if (this.cancelled)
          {
            cleanup();
            return;
          }
          if (this.done.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          for (n = 0; n < i; n++)
          {
            localObject1 = arrayOfJoinInnerSubscriber[n].queue;
            if ((localObject1 != null) && (!((SimpleQueue)localObject1).isEmpty()))
            {
              n = 0;
              break label328;
            }
          }
          n = 1;
          label328:
          if ((k != 0) && (n != 0))
          {
            if ((Throwable)this.errors.get() != null) {
              localSubscriber.onError(this.errors.terminate());
            } else {
              localSubscriber.onComplete();
            }
            return;
          }
        }
        if ((l3 != 0L) && (l1 != Long.MAX_VALUE)) {
          this.requested.addAndGet(-l3);
        }
        int n = get();
        k = n;
        if (n == j)
        {
          j = addAndGet(-j);
          k = j;
          if (j == 0) {
            return;
          }
        }
      }
    }
    
    void onComplete()
    {
      this.done.decrementAndGet();
      drain();
    }
    
    void onError(Throwable paramThrowable)
    {
      this.errors.addThrowable(paramThrowable);
      this.done.decrementAndGet();
      drain();
    }
    
    void onNext(ParallelJoin.JoinInnerSubscriber<T> paramJoinInnerSubscriber, T paramT)
    {
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        if (this.requested.get() != 0L)
        {
          this.downstream.onNext(paramT);
          if (this.requested.get() != Long.MAX_VALUE) {
            this.requested.decrementAndGet();
          }
          paramJoinInnerSubscriber.request(1L);
        }
        else if (!paramJoinInnerSubscriber.getQueue().offer(paramT))
        {
          paramJoinInnerSubscriber.cancel();
          this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
          this.done.decrementAndGet();
          drainLoop();
          return;
        }
        if (decrementAndGet() != 0) {}
      }
      else
      {
        if ((!paramJoinInnerSubscriber.getQueue().offer(paramT)) && (paramJoinInnerSubscriber.cancel()))
        {
          this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
          this.done.decrementAndGet();
        }
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
  }
}
