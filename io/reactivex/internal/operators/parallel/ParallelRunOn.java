package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport.WorkerCallback;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelRunOn<T>
  extends ParallelFlowable<T>
{
  final int prefetch;
  final Scheduler scheduler;
  final ParallelFlowable<? extends T> source;
  
  public ParallelRunOn(ParallelFlowable<? extends T> paramParallelFlowable, Scheduler paramScheduler, int paramInt)
  {
    this.source = paramParallelFlowable;
    this.scheduler = paramScheduler;
    this.prefetch = paramInt;
  }
  
  void createSubscriber(int paramInt, Subscriber<? super T>[] paramArrayOfSubscriber, Subscriber<T>[] paramArrayOfSubscriber1, Scheduler.Worker paramWorker)
  {
    paramArrayOfSubscriber = paramArrayOfSubscriber[paramInt];
    SpscArrayQueue localSpscArrayQueue = new SpscArrayQueue(this.prefetch);
    if ((paramArrayOfSubscriber instanceof ConditionalSubscriber)) {
      paramArrayOfSubscriber1[paramInt] = new RunOnConditionalSubscriber((ConditionalSubscriber)paramArrayOfSubscriber, this.prefetch, localSpscArrayQueue, paramWorker);
    } else {
      paramArrayOfSubscriber1[paramInt] = new RunOnSubscriber(paramArrayOfSubscriber, this.prefetch, localSpscArrayQueue, paramWorker);
    }
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  public void subscribe(Subscriber<? super T>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    Subscriber[] arrayOfSubscriber = new Subscriber[i];
    Scheduler localScheduler = this.scheduler;
    if ((localScheduler instanceof SchedulerMultiWorkerSupport)) {
      ((SchedulerMultiWorkerSupport)localScheduler).createWorkers(i, new MultiWorkerCallback(paramArrayOfSubscriber, arrayOfSubscriber));
    } else {
      for (int j = 0; j < i; j++) {
        createSubscriber(j, paramArrayOfSubscriber, arrayOfSubscriber, this.scheduler.createWorker());
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static abstract class BaseRunOnSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = 9222303586456402150L;
    volatile boolean cancelled;
    int consumed;
    volatile boolean done;
    Throwable error;
    final int limit;
    final int prefetch;
    final SpscArrayQueue<T> queue;
    final AtomicLong requested = new AtomicLong();
    Subscription upstream;
    final Scheduler.Worker worker;
    
    BaseRunOnSubscriber(int paramInt, SpscArrayQueue<T> paramSpscArrayQueue, Scheduler.Worker paramWorker)
    {
      this.prefetch = paramInt;
      this.queue = paramSpscArrayQueue;
      this.limit = (paramInt - (paramInt >> 2));
      this.worker = paramWorker;
    }
    
    public final void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        this.worker.dispose();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    public final void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      schedule();
    }
    
    public final void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      schedule();
    }
    
    public final void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (!this.queue.offer(paramT))
      {
        this.upstream.cancel();
        onError(new MissingBackpressureException("Queue is full?!"));
        return;
      }
      schedule();
    }
    
    public final void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        schedule();
      }
    }
    
    final void schedule()
    {
      if (getAndIncrement() == 0) {
        this.worker.schedule(this);
      }
    }
  }
  
  final class MultiWorkerCallback
    implements SchedulerMultiWorkerSupport.WorkerCallback
  {
    final Subscriber<T>[] parents;
    final Subscriber<? super T>[] subscribers;
    
    MultiWorkerCallback(Subscriber<T>[] paramArrayOfSubscriber)
    {
      this.subscribers = paramArrayOfSubscriber;
      Object localObject;
      this.parents = localObject;
    }
    
    public void onWorker(int paramInt, Scheduler.Worker paramWorker)
    {
      ParallelRunOn.this.createSubscriber(paramInt, this.subscribers, this.parents, paramWorker);
    }
  }
  
  static final class RunOnConditionalSubscriber<T>
    extends ParallelRunOn.BaseRunOnSubscriber<T>
  {
    private static final long serialVersionUID = 1075119423897941642L;
    final ConditionalSubscriber<? super T> downstream;
    
    RunOnConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, int paramInt, SpscArrayQueue<T> paramSpscArrayQueue, Scheduler.Worker paramWorker)
    {
      super(paramSpscArrayQueue, paramWorker);
      this.downstream = paramConditionalSubscriber;
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public void run()
    {
      int i = this.consumed;
      SpscArrayQueue localSpscArrayQueue = this.queue;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      int j = this.limit;
      int k = 1;
      for (;;)
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        boolean bool1;
        Object localObject;
        for (;;)
        {
          bool1 = l2 < l1;
          if (!bool1) {
            break;
          }
          if (this.cancelled)
          {
            localSpscArrayQueue.clear();
            return;
          }
          boolean bool2 = this.done;
          if (bool2)
          {
            localObject = this.error;
            if (localObject != null)
            {
              localSpscArrayQueue.clear();
              localConditionalSubscriber.onError((Throwable)localObject);
              this.worker.dispose();
              return;
            }
          }
          localObject = localSpscArrayQueue.poll();
          if (localObject == null) {
            m = 1;
          } else {
            m = 0;
          }
          if ((bool2) && (m != 0))
          {
            localConditionalSubscriber.onComplete();
            this.worker.dispose();
            return;
          }
          if (m != 0) {
            break;
          }
          long l3 = l2;
          if (localConditionalSubscriber.tryOnNext(localObject)) {
            l3 = l2 + 1L;
          }
          m = i + 1;
          i = m;
          l2 = l3;
          if (m == j)
          {
            this.upstream.request(m);
            i = 0;
            l2 = l3;
          }
        }
        if (!bool1)
        {
          if (this.cancelled)
          {
            localSpscArrayQueue.clear();
            return;
          }
          if (this.done)
          {
            localObject = this.error;
            if (localObject != null)
            {
              localSpscArrayQueue.clear();
              localConditionalSubscriber.onError((Throwable)localObject);
              this.worker.dispose();
              return;
            }
            if (localSpscArrayQueue.isEmpty())
            {
              localConditionalSubscriber.onComplete();
              this.worker.dispose();
              return;
            }
          }
        }
        if ((l2 != 0L) && (l1 != Long.MAX_VALUE)) {
          this.requested.addAndGet(-l2);
        }
        int m = get();
        if (m == k)
        {
          this.consumed = i;
          m = addAndGet(-k);
          k = m;
          if (m != 0) {}
        }
        else
        {
          k = m;
        }
      }
    }
  }
  
  static final class RunOnSubscriber<T>
    extends ParallelRunOn.BaseRunOnSubscriber<T>
  {
    private static final long serialVersionUID = 1075119423897941642L;
    final Subscriber<? super T> downstream;
    
    RunOnSubscriber(Subscriber<? super T> paramSubscriber, int paramInt, SpscArrayQueue<T> paramSpscArrayQueue, Scheduler.Worker paramWorker)
    {
      super(paramSpscArrayQueue, paramWorker);
      this.downstream = paramSubscriber;
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public void run()
    {
      int i = this.consumed;
      SpscArrayQueue localSpscArrayQueue = this.queue;
      Subscriber localSubscriber = this.downstream;
      int j = this.limit;
      int k = 1;
      for (;;)
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        boolean bool1;
        Object localObject;
        for (;;)
        {
          bool1 = l2 < l1;
          if (!bool1) {
            break;
          }
          if (this.cancelled)
          {
            localSpscArrayQueue.clear();
            return;
          }
          boolean bool2 = this.done;
          if (bool2)
          {
            localObject = this.error;
            if (localObject != null)
            {
              localSpscArrayQueue.clear();
              localSubscriber.onError((Throwable)localObject);
              this.worker.dispose();
              return;
            }
          }
          localObject = localSpscArrayQueue.poll();
          if (localObject == null) {
            m = 1;
          } else {
            m = 0;
          }
          if ((bool2) && (m != 0))
          {
            localSubscriber.onComplete();
            this.worker.dispose();
            return;
          }
          if (m != 0) {
            break;
          }
          localSubscriber.onNext(localObject);
          long l3 = l2 + 1L;
          m = i + 1;
          i = m;
          l2 = l3;
          if (m == j)
          {
            this.upstream.request(m);
            i = 0;
            l2 = l3;
          }
        }
        if (!bool1)
        {
          if (this.cancelled)
          {
            localSpscArrayQueue.clear();
            return;
          }
          if (this.done)
          {
            localObject = this.error;
            if (localObject != null)
            {
              localSpscArrayQueue.clear();
              localSubscriber.onError((Throwable)localObject);
              this.worker.dispose();
              return;
            }
            if (localSpscArrayQueue.isEmpty())
            {
              localSubscriber.onComplete();
              this.worker.dispose();
              return;
            }
          }
        }
        if ((l2 != 0L) && (l1 != Long.MAX_VALUE)) {
          this.requested.addAndGet(-l2);
        }
        int m = get();
        if (m == k)
        {
          this.consumed = i;
          m = addAndGet(-k);
          k = m;
          if (m != 0) {}
        }
        else
        {
          k = m;
        }
      }
    }
  }
}
