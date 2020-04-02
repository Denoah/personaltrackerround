package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatWhen<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Function<? super Flowable<Object>, ? extends Publisher<?>> handler;
  
  public FlowableRepeatWhen(Flowable<T> paramFlowable, Function<? super Flowable<Object>, ? extends Publisher<?>> paramFunction)
  {
    super(paramFlowable);
    this.handler = paramFunction;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramSubscriber);
    Object localObject = UnicastProcessor.create(8).toSerialized();
    try
    {
      Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.handler.apply(localObject), "handler returned a null Publisher");
      WhenReceiver localWhenReceiver = new WhenReceiver(this.source);
      localObject = new RepeatWhenSubscriber(localSerializedSubscriber, (FlowableProcessor)localObject, localWhenReceiver);
      localWhenReceiver.subscriber = ((WhenSourceSubscriber)localObject);
      paramSubscriber.onSubscribe((Subscription)localObject);
      localPublisher.subscribe(localWhenReceiver);
      localWhenReceiver.onNext(Integer.valueOf(0));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class RepeatWhenSubscriber<T>
    extends FlowableRepeatWhen.WhenSourceSubscriber<T, Object>
  {
    private static final long serialVersionUID = -2680129890138081029L;
    
    RepeatWhenSubscriber(Subscriber<? super T> paramSubscriber, FlowableProcessor<Object> paramFlowableProcessor, Subscription paramSubscription)
    {
      super(paramFlowableProcessor, paramSubscription);
    }
    
    public void onComplete()
    {
      again(Integer.valueOf(0));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.receiver.cancel();
      this.downstream.onError(paramThrowable);
    }
  }
  
  static final class WhenReceiver<T, U>
    extends AtomicInteger
    implements FlowableSubscriber<Object>, Subscription
  {
    private static final long serialVersionUID = 2827772011130406689L;
    final AtomicLong requested;
    final Publisher<T> source;
    FlowableRepeatWhen.WhenSourceSubscriber<T, U> subscriber;
    final AtomicReference<Subscription> upstream;
    
    WhenReceiver(Publisher<T> paramPublisher)
    {
      this.source = paramPublisher;
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
    }
    
    public void onComplete()
    {
      this.subscriber.cancel();
      this.subscriber.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.subscriber.cancel();
      this.subscriber.downstream.onError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      if (getAndIncrement() == 0) {
        do
        {
          if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
            return;
          }
          this.source.subscribe(this.subscriber);
        } while (decrementAndGet() != 0);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, paramSubscription);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
  }
  
  static abstract class WhenSourceSubscriber<T, U>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -5604623027276966720L;
    protected final Subscriber<? super T> downstream;
    protected final FlowableProcessor<U> processor;
    private long produced;
    protected final Subscription receiver;
    
    WhenSourceSubscriber(Subscriber<? super T> paramSubscriber, FlowableProcessor<U> paramFlowableProcessor, Subscription paramSubscription)
    {
      super();
      this.downstream = paramSubscriber;
      this.processor = paramFlowableProcessor;
      this.receiver = paramSubscription;
    }
    
    protected final void again(U paramU)
    {
      setSubscription(EmptySubscription.INSTANCE);
      long l = this.produced;
      if (l != 0L)
      {
        this.produced = 0L;
        produced(l);
      }
      this.receiver.request(1L);
      this.processor.onNext(paramU);
    }
    
    public final void cancel()
    {
      super.cancel();
      this.receiver.cancel();
    }
    
    public final void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
    
    public final void onSubscribe(Subscription paramSubscription)
    {
      setSubscription(paramSubscription);
    }
  }
}
