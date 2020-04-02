package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSubscribeOn<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean nonScheduledRequests;
  final Scheduler scheduler;
  
  public FlowableSubscribeOn(Flowable<T> paramFlowable, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramFlowable);
    this.scheduler = paramScheduler;
    this.nonScheduledRequests = paramBoolean;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    SubscribeOnSubscriber localSubscribeOnSubscriber = new SubscribeOnSubscriber(paramSubscriber, localWorker, this.source, this.nonScheduledRequests);
    paramSubscriber.onSubscribe(localSubscribeOnSubscriber);
    localWorker.schedule(localSubscribeOnSubscriber);
  }
  
  static final class SubscribeOnSubscriber<T>
    extends AtomicReference<Thread>
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = 8094547886072529208L;
    final Subscriber<? super T> downstream;
    final boolean nonScheduledRequests;
    final AtomicLong requested;
    Publisher<T> source;
    final AtomicReference<Subscription> upstream;
    final Scheduler.Worker worker;
    
    SubscribeOnSubscriber(Subscriber<? super T> paramSubscriber, Scheduler.Worker paramWorker, Publisher<T> paramPublisher, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.worker = paramWorker;
      this.source = paramPublisher;
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
      this.nonScheduledRequests = (paramBoolean ^ true);
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      this.worker.dispose();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      this.worker.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      this.worker.dispose();
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this.upstream, paramSubscription))
      {
        long l = this.requested.getAndSet(0L);
        if (l != 0L) {
          requestUpstream(l, paramSubscription);
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        Subscription localSubscription = (Subscription)this.upstream.get();
        if (localSubscription != null)
        {
          requestUpstream(paramLong, localSubscription);
        }
        else
        {
          BackpressureHelper.add(this.requested, paramLong);
          localSubscription = (Subscription)this.upstream.get();
          if (localSubscription != null)
          {
            paramLong = this.requested.getAndSet(0L);
            if (paramLong != 0L) {
              requestUpstream(paramLong, localSubscription);
            }
          }
        }
      }
    }
    
    void requestUpstream(long paramLong, Subscription paramSubscription)
    {
      if ((!this.nonScheduledRequests) && (Thread.currentThread() != get())) {
        this.worker.schedule(new Request(paramSubscription, paramLong));
      } else {
        paramSubscription.request(paramLong);
      }
    }
    
    public void run()
    {
      lazySet(Thread.currentThread());
      Publisher localPublisher = this.source;
      this.source = null;
      localPublisher.subscribe(this);
    }
    
    static final class Request
      implements Runnable
    {
      final long n;
      final Subscription upstream;
      
      Request(Subscription paramSubscription, long paramLong)
      {
        this.upstream = paramSubscription;
        this.n = paramLong;
      }
      
      public void run()
      {
        this.upstream.request(this.n);
      }
    }
  }
}
