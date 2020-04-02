package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureLatest<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableOnBackpressureLatest(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new BackpressureLatestSubscriber(paramSubscriber));
  }
  
  static final class BackpressureLatestSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 163080509307634843L;
    volatile boolean cancelled;
    final AtomicReference<T> current = new AtomicReference();
    volatile boolean done;
    final Subscriber<? super T> downstream;
    Throwable error;
    final AtomicLong requested = new AtomicLong();
    Subscription upstream;
    
    BackpressureLatestSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.current.lazySet(null);
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, AtomicReference<T> paramAtomicReference)
    {
      if (this.cancelled)
      {
        paramAtomicReference.lazySet(null);
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable = this.error;
        if (localThrowable != null)
        {
          paramAtomicReference.lazySet(null);
          paramSubscriber.onError(localThrowable);
          return true;
        }
        if (paramBoolean2)
        {
          paramSubscriber.onComplete();
          return true;
        }
      }
      return false;
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      AtomicLong localAtomicLong = this.requested;
      AtomicReference localAtomicReference = this.current;
      int i = 1;
      int j;
      do
      {
        boolean bool1;
        boolean bool2;
        boolean bool3;
        for (long l1 = 0L;; l1 += 1L)
        {
          long l2 = localAtomicLong.get();
          bool1 = false;
          if (l1 == l2) {
            break;
          }
          bool2 = this.done;
          Object localObject = localAtomicReference.getAndSet(null);
          if (localObject == null) {
            bool3 = true;
          } else {
            bool3 = false;
          }
          if (checkTerminated(bool2, bool3, localSubscriber, localAtomicReference)) {
            return;
          }
          if (bool3) {
            break;
          }
          localSubscriber.onNext(localObject);
        }
        if (l1 == localAtomicLong.get())
        {
          bool2 = this.done;
          bool3 = bool1;
          if (localAtomicReference.get() == null) {
            bool3 = true;
          }
          if (checkTerminated(bool2, bool3, localSubscriber, localAtomicReference)) {
            return;
          }
        }
        if (l1 != 0L) {
          BackpressureHelper.produced(localAtomicLong, l1);
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
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
      this.current.lazySet(paramT);
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
}
