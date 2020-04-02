package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class BoundedSubscriber<T>
  extends AtomicReference<Subscription>
  implements FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection
{
  private static final long serialVersionUID = -7251123623727029452L;
  final int bufferSize;
  int consumed;
  final int limit;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onNext;
  final Consumer<? super Subscription> onSubscribe;
  
  public BoundedSubscriber(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction, Consumer<? super Subscription> paramConsumer2, int paramInt)
  {
    this.onNext = paramConsumer;
    this.onError = paramConsumer1;
    this.onComplete = paramAction;
    this.onSubscribe = paramConsumer2;
    this.bufferSize = paramInt;
    this.limit = (paramInt - (paramInt >> 2));
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
    //   1: invokevirtual 71	io/reactivex/internal/subscribers/BoundedSubscriber:get	()Ljava/lang/Object;
    //   4: getstatic 75	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   7: if_acmpeq +31 -> 38
    //   10: aload_0
    //   11: getstatic 75	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   14: invokevirtual 79	io/reactivex/internal/subscribers/BoundedSubscriber:lazySet	(Ljava/lang/Object;)V
    //   17: aload_0
    //   18: getfield 41	io/reactivex/internal/subscribers/BoundedSubscriber:onComplete	Lio/reactivex/functions/Action;
    //   21: invokeinterface 84 1 0
    //   26: goto +12 -> 38
    //   29: astore_1
    //   30: aload_1
    //   31: invokestatic 90	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_1
    //   35: invokestatic 94	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   38: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	39	0	this	BoundedSubscriber
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
    //   1: invokevirtual 71	io/reactivex/internal/subscribers/BoundedSubscriber:get	()Ljava/lang/Object;
    //   4: getstatic 75	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   7: if_acmpeq +53 -> 60
    //   10: aload_0
    //   11: getstatic 75	io/reactivex/internal/subscriptions/SubscriptionHelper:CANCELLED	Lio/reactivex/internal/subscriptions/SubscriptionHelper;
    //   14: invokevirtual 79	io/reactivex/internal/subscribers/BoundedSubscriber:lazySet	(Ljava/lang/Object;)V
    //   17: aload_0
    //   18: getfield 39	io/reactivex/internal/subscribers/BoundedSubscriber:onError	Lio/reactivex/functions/Consumer;
    //   21: aload_1
    //   22: invokeinterface 99 2 0
    //   27: goto +37 -> 64
    //   30: astore_2
    //   31: aload_2
    //   32: invokestatic 90	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   35: new 101	io/reactivex/exceptions/CompositeException
    //   38: dup
    //   39: iconst_2
    //   40: anewarray 103	java/lang/Throwable
    //   43: dup
    //   44: iconst_0
    //   45: aload_1
    //   46: aastore
    //   47: dup
    //   48: iconst_1
    //   49: aload_2
    //   50: aastore
    //   51: invokespecial 106	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   54: invokestatic 94	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   57: goto +7 -> 64
    //   60: aload_1
    //   61: invokestatic 94	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   64: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	BoundedSubscriber
    //   0	65	1	paramThrowable	Throwable
    //   30	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	27	30	finally
  }
  
  public void onNext(T paramT)
  {
    if (!isDisposed()) {
      try
      {
        this.onNext.accept(paramT);
        int i = this.consumed + 1;
        if (i == this.limit)
        {
          this.consumed = 0;
          ((Subscription)get()).request(this.limit);
        }
        else
        {
          this.consumed = i;
        }
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        ((Subscription)get()).cancel();
        onError(paramT);
      }
    }
  }
  
  /* Error */
  public void onSubscribe(Subscription paramSubscription)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 122	io/reactivex/internal/subscriptions/SubscriptionHelper:setOnce	(Ljava/util/concurrent/atomic/AtomicReference;Lorg/reactivestreams/Subscription;)Z
    //   5: ifeq +32 -> 37
    //   8: aload_0
    //   9: getfield 43	io/reactivex/internal/subscribers/BoundedSubscriber:onSubscribe	Lio/reactivex/functions/Consumer;
    //   12: aload_0
    //   13: invokeinterface 99 2 0
    //   18: goto +19 -> 37
    //   21: astore_2
    //   22: aload_2
    //   23: invokestatic 90	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: aload_1
    //   27: invokeinterface 115 1 0
    //   32: aload_0
    //   33: aload_2
    //   34: invokevirtual 116	io/reactivex/internal/subscribers/BoundedSubscriber:onError	(Ljava/lang/Throwable;)V
    //   37: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	BoundedSubscriber
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
