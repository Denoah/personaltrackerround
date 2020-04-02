package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

public final class FlowableMaterialize<T>
  extends AbstractFlowableWithUpstream<T, Notification<T>>
{
  public FlowableMaterialize(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super Notification<T>> paramSubscriber)
  {
    this.source.subscribe(new MaterializeSubscriber(paramSubscriber));
  }
  
  static final class MaterializeSubscriber<T>
    extends SinglePostCompleteSubscriber<T, Notification<T>>
  {
    private static final long serialVersionUID = -3740826063558713822L;
    
    MaterializeSubscriber(Subscriber<? super Notification<T>> paramSubscriber)
    {
      super();
    }
    
    public void onComplete()
    {
      complete(Notification.createOnComplete());
    }
    
    protected void onDrop(Notification<T> paramNotification)
    {
      if (paramNotification.isOnError()) {
        RxJavaPlugins.onError(paramNotification.getError());
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      complete(Notification.createOnError(paramThrowable));
    }
    
    public void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(Notification.createOnNext(paramT));
    }
  }
}
