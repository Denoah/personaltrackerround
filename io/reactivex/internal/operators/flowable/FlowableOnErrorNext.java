package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnErrorNext<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean allowFatal;
  final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
  
  public FlowableOnErrorNext(Flowable<T> paramFlowable, Function<? super Throwable, ? extends Publisher<? extends T>> paramFunction, boolean paramBoolean)
  {
    super(paramFlowable);
    this.nextSupplier = paramFunction;
    this.allowFatal = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    OnErrorNextSubscriber localOnErrorNextSubscriber = new OnErrorNextSubscriber(paramSubscriber, this.nextSupplier, this.allowFatal);
    paramSubscriber.onSubscribe(localOnErrorNextSubscriber);
    this.source.subscribe(localOnErrorNextSubscriber);
  }
  
  static final class OnErrorNextSubscriber<T>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 4063763155303814625L;
    final boolean allowFatal;
    boolean done;
    final Subscriber<? super T> downstream;
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
    boolean once;
    long produced;
    
    OnErrorNextSubscriber(Subscriber<? super T> paramSubscriber, Function<? super Throwable, ? extends Publisher<? extends T>> paramFunction, boolean paramBoolean)
    {
      super();
      this.downstream = paramSubscriber;
      this.nextSupplier = paramFunction;
      this.allowFatal = paramBoolean;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.once = true;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once)
      {
        if (this.done)
        {
          RxJavaPlugins.onError(paramThrowable);
          return;
        }
        this.downstream.onError(paramThrowable);
        return;
      }
      this.once = true;
      if ((this.allowFatal) && (!(paramThrowable instanceof Exception)))
      {
        this.downstream.onError(paramThrowable);
        return;
      }
      try
      {
        Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.nextSupplier.apply(paramThrowable), "The nextSupplier returned a null Publisher");
        long l = this.produced;
        if (l != 0L) {
          produced(l);
        }
        localPublisher.subscribe(this);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (!this.once) {
        this.produced += 1L;
      }
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      setSubscription(paramSubscription);
    }
  }
}
