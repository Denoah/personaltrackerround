package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SingleFlatMapPublisher<T, R>
  extends Flowable<R>
{
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final SingleSource<T> source;
  
  public SingleFlatMapPublisher(SingleSource<T> paramSingleSource, Function<? super T, ? extends Publisher<? extends R>> paramFunction)
  {
    this.source = paramSingleSource;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new SingleFlatMapPublisherObserver(paramSubscriber, this.mapper));
  }
  
  static final class SingleFlatMapPublisherObserver<S, T>
    extends AtomicLong
    implements SingleObserver<S>, FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 7759721921468635667L;
    Disposable disposable;
    final Subscriber<? super T> downstream;
    final Function<? super S, ? extends Publisher<? extends T>> mapper;
    final AtomicReference<Subscription> parent;
    
    SingleFlatMapPublisherObserver(Subscriber<? super T> paramSubscriber, Function<? super S, ? extends Publisher<? extends T>> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.parent = new AtomicReference();
    }
    
    public void cancel()
    {
      this.disposable.dispose();
      SubscriptionHelper.cancel(this.parent);
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
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.disposable = paramDisposable;
      this.downstream.onSubscribe(this);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.parent, this, paramSubscription);
    }
    
    public void onSuccess(S paramS)
    {
      try
      {
        paramS = (Publisher)ObjectHelper.requireNonNull(this.mapper.apply(paramS), "the mapper returned a null Publisher");
        paramS.subscribe(this);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramS);
        this.downstream.onError(paramS);
      }
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.parent, this, paramLong);
    }
  }
}
