package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatUntil<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final BooleanSupplier until;
  
  public FlowableRepeatUntil(Flowable<T> paramFlowable, BooleanSupplier paramBooleanSupplier)
  {
    super(paramFlowable);
    this.until = paramBooleanSupplier;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SubscriptionArbiter localSubscriptionArbiter = new SubscriptionArbiter(false);
    paramSubscriber.onSubscribe(localSubscriptionArbiter);
    new RepeatSubscriber(paramSubscriber, this.until, localSubscriptionArbiter, this.source).subscribeNext();
  }
  
  static final class RepeatSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Subscriber<? super T> downstream;
    long produced;
    final SubscriptionArbiter sa;
    final Publisher<? extends T> source;
    final BooleanSupplier stop;
    
    RepeatSubscriber(Subscriber<? super T> paramSubscriber, BooleanSupplier paramBooleanSupplier, SubscriptionArbiter paramSubscriptionArbiter, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.sa = paramSubscriptionArbiter;
      this.source = paramPublisher;
      this.stop = paramBooleanSupplier;
    }
    
    public void onComplete()
    {
      try
      {
        boolean bool = this.stop.getAsBoolean();
        if (bool) {
          this.downstream.onComplete();
        } else {
          subscribeNext();
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.sa.setSubscription(paramSubscription);
    }
    
    void subscribeNext()
    {
      if (getAndIncrement() == 0)
      {
        int i = 1;
        int j;
        do
        {
          if (this.sa.isCancelled()) {
            return;
          }
          long l = this.produced;
          if (l != 0L)
          {
            this.produced = 0L;
            this.sa.produced(l);
          }
          this.source.subscribe(this);
          j = addAndGet(-i);
          i = j;
        } while (j != 0);
      }
    }
  }
}
