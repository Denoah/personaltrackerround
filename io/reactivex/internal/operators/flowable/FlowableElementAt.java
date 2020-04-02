package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableElementAt<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final T defaultValue;
  final boolean errorOnFewer;
  final long index;
  
  public FlowableElementAt(Flowable<T> paramFlowable, long paramLong, T paramT, boolean paramBoolean)
  {
    super(paramFlowable);
    this.index = paramLong;
    this.defaultValue = paramT;
    this.errorOnFewer = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ElementAtSubscriber(paramSubscriber, this.index, this.defaultValue, this.errorOnFewer));
  }
  
  static final class ElementAtSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 4066607327284737757L;
    long count;
    final T defaultValue;
    boolean done;
    final boolean errorOnFewer;
    final long index;
    Subscription upstream;
    
    ElementAtSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, T paramT, boolean paramBoolean)
    {
      super();
      this.index = paramLong;
      this.defaultValue = paramT;
      this.errorOnFewer = paramBoolean;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        Object localObject = this.defaultValue;
        if (localObject == null)
        {
          if (this.errorOnFewer) {
            this.downstream.onError(new NoSuchElementException());
          } else {
            this.downstream.onComplete();
          }
        }
        else {
          complete(localObject);
        }
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
      long l = this.count;
      if (l == this.index)
      {
        this.done = true;
        this.upstream.cancel();
        complete(paramT);
        return;
      }
      this.count = (l + 1L);
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
