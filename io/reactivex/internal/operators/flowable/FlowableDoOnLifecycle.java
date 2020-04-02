package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDoOnLifecycle<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  private final Action onCancel;
  private final LongConsumer onRequest;
  private final Consumer<? super Subscription> onSubscribe;
  
  public FlowableDoOnLifecycle(Flowable<T> paramFlowable, Consumer<? super Subscription> paramConsumer, LongConsumer paramLongConsumer, Action paramAction)
  {
    super(paramFlowable);
    this.onSubscribe = paramConsumer;
    this.onRequest = paramLongConsumer;
    this.onCancel = paramAction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SubscriptionLambdaSubscriber(paramSubscriber, this.onSubscribe, this.onRequest, this.onCancel));
  }
  
  static final class SubscriptionLambdaSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final Subscriber<? super T> downstream;
    final Action onCancel;
    final LongConsumer onRequest;
    final Consumer<? super Subscription> onSubscribe;
    Subscription upstream;
    
    SubscriptionLambdaSubscriber(Subscriber<? super T> paramSubscriber, Consumer<? super Subscription> paramConsumer, LongConsumer paramLongConsumer, Action paramAction)
    {
      this.downstream = paramSubscriber;
      this.onSubscribe = paramConsumer;
      this.onCancel = paramAction;
      this.onRequest = paramLongConsumer;
    }
    
    /* Error */
    public void cancel()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 43	io/reactivex/internal/operators/flowable/FlowableDoOnLifecycle$SubscriptionLambdaSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   4: astore_1
      //   5: aload_1
      //   6: getstatic 49	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
      //   9: if_acmpeq +37 -> 46
      //   12: aload_0
      //   13: getstatic 49	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
      //   16: putfield 43	io/reactivex/internal/operators/flowable/FlowableDoOnLifecycle$SubscriptionLambdaSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   19: aload_0
      //   20: getfield 35	io/reactivex/internal/operators/flowable/FlowableDoOnLifecycle$SubscriptionLambdaSubscriber:onCancel	Lio/reactivex/functions/Action;
      //   23: invokeinterface 54 1 0
      //   28: goto +12 -> 40
      //   31: astore_2
      //   32: aload_2
      //   33: invokestatic 60	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   36: aload_2
      //   37: invokestatic 65	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   40: aload_1
      //   41: invokeinterface 67 1 0
      //   46: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	47	0	this	SubscriptionLambdaSubscriber
      //   4	37	1	localSubscription	Subscription
      //   31	6	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   19	28	31	finally
    }
    
    public void onComplete()
    {
      if (this.upstream != SubscriptionHelper.CANCELLED) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.upstream != SubscriptionHelper.CANCELLED) {
        this.downstream.onError(paramThrowable);
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      try
      {
        this.onSubscribe.accept(paramSubscription);
        if (SubscriptionHelper.validate(this.upstream, paramSubscription))
        {
          this.upstream = paramSubscription;
          this.downstream.onSubscribe(this);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        paramSubscription.cancel();
        this.upstream = SubscriptionHelper.CANCELLED;
        EmptySubscription.error(localThrowable, this.downstream);
      }
    }
    
    /* Error */
    public void request(long paramLong)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnLifecycle$SubscriptionLambdaSubscriber:onRequest	Lio/reactivex/functions/LongConsumer;
      //   4: lload_1
      //   5: invokeinterface 102 3 0
      //   10: goto +12 -> 22
      //   13: astore_3
      //   14: aload_3
      //   15: invokestatic 60	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   18: aload_3
      //   19: invokestatic 65	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   22: aload_0
      //   23: getfield 43	io/reactivex/internal/operators/flowable/FlowableDoOnLifecycle$SubscriptionLambdaSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   26: lload_1
      //   27: invokeinterface 104 3 0
      //   32: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	33	0	this	SubscriptionLambdaSubscriber
      //   0	33	1	paramLong	long
      //   13	6	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	10	13	finally
    }
  }
}
