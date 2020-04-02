package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSamplePublisher<T>
  extends Flowable<T>
{
  final boolean emitLast;
  final Publisher<?> other;
  final Publisher<T> source;
  
  public FlowableSamplePublisher(Publisher<T> paramPublisher, Publisher<?> paramPublisher1, boolean paramBoolean)
  {
    this.source = paramPublisher;
    this.other = paramPublisher1;
    this.emitLast = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    paramSubscriber = new SerializedSubscriber(paramSubscriber);
    if (this.emitLast) {
      this.source.subscribe(new SampleMainEmitLast(paramSubscriber, this.other));
    } else {
      this.source.subscribe(new SampleMainNoLast(paramSubscriber, this.other));
    }
  }
  
  static final class SampleMainEmitLast<T>
    extends FlowableSamplePublisher.SamplePublisherSubscriber<T>
  {
    private static final long serialVersionUID = -3029755663834015785L;
    volatile boolean done;
    final AtomicInteger wip = new AtomicInteger();
    
    SampleMainEmitLast(Subscriber<? super T> paramSubscriber, Publisher<?> paramPublisher)
    {
      super(paramPublisher);
    }
    
    void completion()
    {
      this.done = true;
      if (this.wip.getAndIncrement() == 0)
      {
        emit();
        this.downstream.onComplete();
      }
    }
    
    void run()
    {
      if (this.wip.getAndIncrement() == 0) {
        do
        {
          boolean bool = this.done;
          emit();
          if (bool)
          {
            this.downstream.onComplete();
            return;
          }
        } while (this.wip.decrementAndGet() != 0);
      }
    }
  }
  
  static final class SampleMainNoLast<T>
    extends FlowableSamplePublisher.SamplePublisherSubscriber<T>
  {
    private static final long serialVersionUID = -3029755663834015785L;
    
    SampleMainNoLast(Subscriber<? super T> paramSubscriber, Publisher<?> paramPublisher)
    {
      super(paramPublisher);
    }
    
    void completion()
    {
      this.downstream.onComplete();
    }
    
    void run()
    {
      emit();
    }
  }
  
  static abstract class SamplePublisherSubscriber<T>
    extends AtomicReference<T>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -3517602651313910099L;
    final Subscriber<? super T> downstream;
    final AtomicReference<Subscription> other = new AtomicReference();
    final AtomicLong requested = new AtomicLong();
    final Publisher<?> sampler;
    Subscription upstream;
    
    SamplePublisherSubscriber(Subscriber<? super T> paramSubscriber, Publisher<?> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.sampler = paramPublisher;
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.other);
      this.upstream.cancel();
    }
    
    public void complete()
    {
      this.upstream.cancel();
      completion();
    }
    
    abstract void completion();
    
    void emit()
    {
      Object localObject = getAndSet(null);
      if (localObject != null) {
        if (this.requested.get() != 0L)
        {
          this.downstream.onNext(localObject);
          BackpressureHelper.produced(this.requested, 1L);
        }
        else
        {
          cancel();
          this.downstream.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
      }
    }
    
    public void error(Throwable paramThrowable)
    {
      this.upstream.cancel();
      this.downstream.onError(paramThrowable);
    }
    
    public void onComplete()
    {
      SubscriptionHelper.cancel(this.other);
      completion();
    }
    
    public void onError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.other);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      lazySet(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        if (this.other.get() == null)
        {
          this.sampler.subscribe(new FlowableSamplePublisher.SamplerSubscriber(this));
          paramSubscription.request(Long.MAX_VALUE);
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this.requested, paramLong);
      }
    }
    
    abstract void run();
    
    void setOther(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this.other, paramSubscription, Long.MAX_VALUE);
    }
  }
  
  static final class SamplerSubscriber<T>
    implements FlowableSubscriber<Object>
  {
    final FlowableSamplePublisher.SamplePublisherSubscriber<T> parent;
    
    SamplerSubscriber(FlowableSamplePublisher.SamplePublisherSubscriber<T> paramSamplePublisherSubscriber)
    {
      this.parent = paramSamplePublisherSubscriber;
    }
    
    public void onComplete()
    {
      this.parent.complete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.error(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.parent.run();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.parent.setOther(paramSubscription);
    }
  }
}
