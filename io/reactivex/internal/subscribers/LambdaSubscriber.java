package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class LambdaSubscriber<T>
  extends AtomicReference<Subscription>
  implements FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection
{
  private static final long serialVersionUID = -7251123623727029452L;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onNext;
  final Consumer<? super Subscription> onSubscribe;
  
  public LambdaSubscriber(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction, Consumer<? super Subscription> paramConsumer2)
  {
    this.onNext = paramConsumer;
    this.onError = paramConsumer1;
    this.onComplete = paramAction;
    this.onSubscribe = paramConsumer2;
  }
  
  public void cancel()
  {
    SubscriptionHelper.cancel(this);
  }
  
  public void dispose()
  {
    cancel();
  }
  
  public boolean hasCustomOnError()
  {
    boolean bool;
    if (this.onError != Functions.ON_ERROR_MISSING) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (get() == SubscriptionHelper.CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  /* Error */
  public void onComplete()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 63	io/reactivex/internal/subscribers/LambdaSubscriber:get	()Ljava/lang/Object;
    //   4: getstatic 67	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   7: if_acmpeq +31 -> 38
    //   10: aload_0
    //   11: getstatic 67	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   14: invokevirtual 71	io/reactivex/internal/subscribers/LambdaSubscriber:lazySet	(Ljava/lang/Object;)V
    //   17: aload_0
    //   18: getfield 37	io/reactivex/internal/subscribers/LambdaSubscriber:onComplete	Lio/reactivex/functions/Action;
    //   21: invokeinterface 76 1 0
    //   26: goto +12 -> 38
    //   29: astore_1
    //   30: aload_1
    //   31: invokestatic 82	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_1
    //   35: invokestatic 86	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   38: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	39	0	this	LambdaSubscriber
    //   29	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	26	29	finally
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 63	io/reactivex/internal/subscribers/LambdaSubscriber:get	()Ljava/lang/Object;
    //   4: getstatic 67	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   7: if_acmpeq +53 -> 60
    //   10: aload_0
    //   11: getstatic 67	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   14: invokevirtual 71	io/reactivex/internal/subscribers/LambdaSubscriber:lazySet	(Ljava/lang/Object;)V
    //   17: aload_0
    //   18: getfield 35	io/reactivex/internal/subscribers/LambdaSubscriber:onError	Lio/reactivex/functions/Consumer;
    //   21: aload_1
    //   22: invokeinterface 91 2 0
    //   27: goto +37 -> 64
    //   30: astore_2
    //   31: aload_2
    //   32: invokestatic 82	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   35: new 93	io/reactivex/exceptions/CompositeException
    //   38: dup
    //   39: iconst_2
    //   40: anewarray 95	java/lang/Throwable
    //   43: dup
    //   44: iconst_0
    //   45: aload_1
    //   46: aastore
    //   47: dup
    //   48: iconst_1
    //   49: aload_2
    //   50: aastore
    //   51: invokespecial 98	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   54: invokestatic 86	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   57: goto +7 -> 64
    //   60: aload_1
    //   61: invokestatic 86	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   64: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	LambdaSubscriber
    //   0	65	1	paramThrowable	Throwable
    //   30	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	27	30	finally
  }
  
  /* Error */
  public void onNext(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 100	io/reactivex/internal/subscribers/LambdaSubscriber:isDisposed	()Z
    //   4: ifne +38 -> 42
    //   7: aload_0
    //   8: getfield 33	io/reactivex/internal/subscribers/LambdaSubscriber:onNext	Lio/reactivex/functions/Consumer;
    //   11: aload_1
    //   12: invokeinterface 91 2 0
    //   17: goto +25 -> 42
    //   20: astore_1
    //   21: aload_1
    //   22: invokestatic 82	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   25: aload_0
    //   26: invokevirtual 63	io/reactivex/internal/subscribers/LambdaSubscriber:get	()Ljava/lang/Object;
    //   29: checkcast 9	org/reactivestreams/Subscription
    //   32: invokeinterface 101 1 0
    //   37: aload_0
    //   38: aload_1
    //   39: invokevirtual 102	io/reactivex/internal/subscribers/LambdaSubscriber:onError	(Ljava/lang/Throwable;)V
    //   42: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	43	0	this	LambdaSubscriber
    //   0	43	1	paramT	T
    // Exception table:
    //   from	to	target	type
    //   7	17	20	finally
  }
  
  /* Error */
  public void onSubscribe(Subscription paramSubscription)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 108	io/reactivex/internal/subscriptions/SubscriptionHelper:setOnce	(Ljava/util/concurrent/atomic/AtomicReference;Lorg/reactivestreams/Subscription;)Z
    //   5: ifeq +32 -> 37
    //   8: aload_0
    //   9: getfield 39	io/reactivex/internal/subscribers/LambdaSubscriber:onSubscribe	Lio/reactivex/functions/Consumer;
    //   12: aload_0
    //   13: invokeinterface 91 2 0
    //   18: goto +19 -> 37
    //   21: astore_2
    //   22: aload_2
    //   23: invokestatic 82	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: aload_1
    //   27: invokeinterface 101 1 0
    //   32: aload_0
    //   33: aload_2
    //   34: invokevirtual 102	io/reactivex/internal/subscribers/LambdaSubscriber:onError	(Ljava/lang/Throwable;)V
    //   37: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	LambdaSubscriber
    //   0	38	1	paramSubscription	Subscription
    //   21	13	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   8	18	21	finally
  }
  
  public void request(long paramLong)
  {
    ((Subscription)get()).request(paramLong);
  }
}
