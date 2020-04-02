package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryWhen<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;
  
  public FlowableRetryWhen(Flowable<T> paramFlowable, Function<? super Flowable<Throwable>, ? extends Publisher<?>> paramFunction)
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
      FlowableRepeatWhen.WhenReceiver localWhenReceiver = new FlowableRepeatWhen.WhenReceiver(this.source);
      localObject = new RetryWhenSubscriber(localSerializedSubscriber, (FlowableProcessor)localObject, localWhenReceiver);
      localWhenReceiver.subscriber = ((FlowableRepeatWhen.WhenSourceSubscriber)localObject);
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
  
  static final class RetryWhenSubscriber<T>
    extends FlowableRepeatWhen.WhenSourceSubscriber<T, Throwable>
  {
    private static final long serialVersionUID = -2680129890138081029L;
    
    RetryWhenSubscriber(Subscriber<? super T> paramSubscriber, FlowableProcessor<Throwable> paramFlowableProcessor, Subscription paramSubscription)
    {
      super(paramFlowableProcessor, paramSubscription);
    }
    
    public void onComplete()
    {
      this.receiver.cancel();
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      again(paramThrowable);
    }
  }
}
