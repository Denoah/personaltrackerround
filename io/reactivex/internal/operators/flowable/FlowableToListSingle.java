package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class FlowableToListSingle<T, U extends Collection<? super T>>
  extends Single<U>
  implements FuseToFlowable<U>
{
  final Callable<U> collectionSupplier;
  final Flowable<T> source;
  
  public FlowableToListSingle(Flowable<T> paramFlowable)
  {
    this(paramFlowable, ArrayListSupplier.asCallable());
  }
  
  public FlowableToListSingle(Flowable<T> paramFlowable, Callable<U> paramCallable)
  {
    this.source = paramFlowable;
    this.collectionSupplier = paramCallable;
  }
  
  public Flowable<U> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableToList(this.source, this.collectionSupplier));
  }
  
  protected void subscribeActual(SingleObserver<? super U> paramSingleObserver)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new ToListSubscriber(paramSingleObserver, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
  
  static final class ToListSubscriber<T, U extends Collection<? super T>>
    implements FlowableSubscriber<T>, Disposable
  {
    final SingleObserver<? super U> downstream;
    Subscription upstream;
    U value;
    
    ToListSubscriber(SingleObserver<? super U> paramSingleObserver, U paramU)
    {
      this.downstream = paramSingleObserver;
      this.value = paramU;
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      this.upstream = SubscriptionHelper.CANCELLED;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.upstream == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onSuccess(this.value);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = null;
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.value.add(paramT);
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
