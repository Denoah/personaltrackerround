package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCollect<T, U>
  extends AbstractFlowableWithUpstream<T, U>
{
  final BiConsumer<? super U, ? super T> collector;
  final Callable<? extends U> initialSupplier;
  
  public FlowableCollect(Flowable<T> paramFlowable, Callable<? extends U> paramCallable, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    super(paramFlowable);
    this.initialSupplier = paramCallable;
    this.collector = paramBiConsumer;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initial value supplied is null");
      this.source.subscribe(new CollectSubscriber(paramSubscriber, localObject, this.collector));
      return;
    }
    finally
    {
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class CollectSubscriber<T, U>
    extends DeferredScalarSubscription<U>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -3589550218733891694L;
    final BiConsumer<? super U, ? super T> collector;
    boolean done;
    final U u;
    Subscription upstream;
    
    CollectSubscriber(Subscriber<? super U> paramSubscriber, U paramU, BiConsumer<? super U, ? super T> paramBiConsumer)
    {
      super();
      this.collector = paramBiConsumer;
      this.u = paramU;
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
      complete(this.u);
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
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 48	io/reactivex/internal/operators/flowable/FlowableCollect$CollectSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 31	io/reactivex/internal/operators/flowable/FlowableCollect$CollectSubscriber:collector	Lio/reactivex/functions/BiConsumer;
      //   12: aload_0
      //   13: getfield 33	io/reactivex/internal/operators/flowable/FlowableCollect$CollectSubscriber:u	Ljava/lang/Object;
      //   16: aload_1
      //   17: invokeinterface 72 3 0
      //   22: goto +22 -> 44
      //   25: astore_1
      //   26: aload_1
      //   27: invokestatic 77	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   30: aload_0
      //   31: getfield 42	io/reactivex/internal/operators/flowable/FlowableCollect$CollectSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   34: invokeinterface 45 1 0
      //   39: aload_0
      //   40: aload_1
      //   41: invokevirtual 78	io/reactivex/internal/operators/flowable/FlowableCollect$CollectSubscriber:onError	(Ljava/lang/Throwable;)V
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
