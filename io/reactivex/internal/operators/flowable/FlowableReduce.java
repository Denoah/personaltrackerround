package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableReduce<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final BiFunction<T, T, T> reducer;
  
  public FlowableReduce(Flowable<T> paramFlowable, BiFunction<T, T, T> paramBiFunction)
  {
    super(paramFlowable);
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ReduceSubscriber(paramSubscriber, this.reducer));
  }
  
  static final class ReduceSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -4663883003264602070L;
    final BiFunction<T, T, T> reducer;
    Subscription upstream;
    
    ReduceSubscriber(Subscriber<? super T> paramSubscriber, BiFunction<T, T, T> paramBiFunction)
    {
      super();
      this.reducer = paramBiFunction;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
      this.upstream = SubscriptionHelper.CANCELLED;
    }
    
    public void onComplete()
    {
      if (this.upstream == SubscriptionHelper.CANCELLED) {
        return;
      }
      this.upstream = SubscriptionHelper.CANCELLED;
      Object localObject = this.value;
      if (localObject != null) {
        complete(localObject);
      } else {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.upstream == SubscriptionHelper.CANCELLED)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 35	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   4: getstatic 44	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
      //   7: if_acmpne +4 -> 11
      //   10: return
      //   11: aload_0
      //   12: getfield 49	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:value	Ljava/lang/Object;
      //   15: astore_2
      //   16: aload_2
      //   17: ifnonnull +11 -> 28
      //   20: aload_0
      //   21: aload_1
      //   22: putfield 49	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:value	Ljava/lang/Object;
      //   25: goto +45 -> 70
      //   28: aload_0
      //   29: aload_0
      //   30: getfield 26	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:reducer	Lio/reactivex/functions/BiFunction;
      //   33: aload_2
      //   34: aload_1
      //   35: invokeinterface 75 3 0
      //   40: ldc 77
      //   42: invokestatic 83	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   45: putfield 49	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:value	Ljava/lang/Object;
      //   48: goto +22 -> 70
      //   51: astore_1
      //   52: aload_1
      //   53: invokestatic 88	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   56: aload_0
      //   57: getfield 35	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   60: invokeinterface 38 1 0
      //   65: aload_0
      //   66: aload_1
      //   67: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableReduce$ReduceSubscriber:onError	(Ljava/lang/Throwable;)V
      //   70: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	71	0	this	ReduceSubscriber
      //   0	71	1	paramT	T
      //   15	19	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   28	48	51	finally
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
