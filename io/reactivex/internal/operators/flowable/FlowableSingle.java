package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSingle<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final T defaultValue;
  final boolean failOnEmpty;
  
  public FlowableSingle(Flowable<T> paramFlowable, T paramT, boolean paramBoolean)
  {
    super(paramFlowable);
    this.defaultValue = paramT;
    this.failOnEmpty = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SingleElementSubscriber(paramSubscriber, this.defaultValue, this.failOnEmpty));
  }
  
  static final class SingleElementSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -5526049321428043809L;
    final T defaultValue;
    boolean done;
    final boolean failOnEmpty;
    Subscription upstream;
    
    SingleElementSubscriber(Subscriber<? super T> paramSubscriber, T paramT, boolean paramBoolean)
    {
      super();
      this.defaultValue = paramT;
      this.failOnEmpty = paramBoolean;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject1 = this.value;
      this.value = null;
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = this.defaultValue;
      }
      if (localObject2 == null)
      {
        if (this.failOnEmpty) {
          this.downstream.onError(new NoSuchElementException());
        } else {
          this.downstream.onComplete();
        }
      }
      else {
        complete(localObject2);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.value != null)
      {
        this.done = true;
        this.upstream.cancel();
        this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
        return;
      }
      this.value = paramT;
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
  }
}
