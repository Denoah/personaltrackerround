package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class FlowableCollectSingle<T, U>
  extends Single<U>
  implements FuseToFlowable<U>
{
  final BiConsumer<? super U, ? super T> collector;
  final Callable<? extends U> initialSupplier;
  final Flowable<T> source;
  
  public FlowableCollectSingle(Flowable<T> paramFlowable, Callable<? extends U> paramCallable, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    this.source = paramFlowable;
    this.initialSupplier = paramCallable;
    this.collector = paramBiConsumer;
  }
  
  public Flowable<U> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableCollect(this.source, this.initialSupplier, this.collector));
  }
  
  protected void subscribeActual(SingleObserver<? super U> paramSingleObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
      this.source.subscribe(new CollectSubscriber(paramSingleObserver, localObject, this.collector));
      return;
    }
    finally
    {
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
  
  static final class CollectSubscriber<T, U>
    implements FlowableSubscriber<T>, Disposable
  {
    final BiConsumer<? super U, ? super T> collector;
    boolean done;
    final SingleObserver<? super U> downstream;
    final U u;
    Subscription upstream;
    
    CollectSubscriber(SingleObserver<? super U> paramSingleObserver, U paramU, BiConsumer<? super U, ? super T> paramBiConsumer)
    {
      this.downstream = paramSingleObserver;
      this.collector = paramBiConsumer;
      this.u = paramU;
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onSuccess(this.u);
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 58	io/reactivex/internal/operators/flowable/FlowableCollectSingle$CollectSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 34	io/reactivex/internal/operators/flowable/FlowableCollectSingle$CollectSubscriber:collector	Lio/reactivex/functions/BiConsumer;
      //   12: aload_0
      //   13: getfield 36	io/reactivex/internal/operators/flowable/FlowableCollectSingle$CollectSubscriber:u	Ljava/lang/Object;
      //   16: aload_1
      //   17: invokeinterface 78 3 0
      //   22: goto +22 -> 44
      //   25: astore_1
      //   26: aload_1
      //   27: invokestatic 83	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   30: aload_0
      //   31: getfield 42	io/reactivex/internal/operators/flowable/FlowableCollectSingle$CollectSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   34: invokeinterface 47 1 0
      //   39: aload_0
      //   40: aload_1
      //   41: invokevirtual 84	io/reactivex/internal/operators/flowable/FlowableCollectSingle$CollectSubscriber:onError	(Ljava/lang/Throwable;)V
      //   44: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	45	0	this	CollectSubscriber
      //   0	45	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   8	22	25	finally
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
