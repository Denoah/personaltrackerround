package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.ArrayDeque;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipLast<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final int skip;
  
  public FlowableSkipLast(Flowable<T> paramFlowable, int paramInt)
  {
    super(paramFlowable);
    this.skip = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SkipLastSubscriber(paramSubscriber, this.skip));
  }
  
  static final class SkipLastSubscriber<T>
    extends ArrayDeque<T>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -3807491841935125653L;
    final Subscriber<? super T> downstream;
    final int skip;
    Subscription upstream;
    
    SkipLastSubscriber(Subscriber<? super T> paramSubscriber, int paramInt)
    {
      super();
      this.downstream = paramSubscriber;
      this.skip = paramInt;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.skip == size()) {
        this.downstream.onNext(poll());
      } else {
        this.upstream.request(1L);
      }
      offer(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
