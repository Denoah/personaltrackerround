package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class ForEachWhileSubscriber<T>
  extends AtomicReference<Subscription>
  implements FlowableSubscriber<T>, Disposable
{
  private static final long serialVersionUID = -4403180040475402120L;
  boolean done;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Predicate<? super T> onNext;
  
  public ForEachWhileSubscriber(Predicate<? super T> paramPredicate, Consumer<? super Throwable> paramConsumer, Action paramAction)
  {
    this.onNext = paramPredicate;
    this.onError = paramConsumer;
    this.onComplete = paramAction;
  }
  
  public void dispose()
  {
    SubscriptionHelper.cancel(this);
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
    //   1: getfield 56	io/reactivex/internal/subscribers/ForEachWhileSubscriber:done	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield 56	io/reactivex/internal/subscribers/ForEachWhileSubscriber:done	Z
    //   13: aload_0
    //   14: getfield 34	io/reactivex/internal/subscribers/ForEachWhileSubscriber:onComplete	Lio/reactivex/functions/Action;
    //   17: invokeinterface 61 1 0
    //   22: goto +12 -> 34
    //   25: astore_1
    //   26: aload_1
    //   27: invokestatic 67	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   30: aload_1
    //   31: invokestatic 71	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   34: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	35	0	this	ForEachWhileSubscriber
    //   25	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   13	22	25	finally
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 56	io/reactivex/internal/subscribers/ForEachWhileSubscriber:done	Z
    //   4: ifeq +8 -> 12
    //   7: aload_1
    //   8: invokestatic 71	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 56	io/reactivex/internal/subscribers/ForEachWhileSubscriber:done	Z
    //   17: aload_0
    //   18: getfield 32	io/reactivex/internal/subscribers/ForEachWhileSubscriber:onError	Lio/reactivex/functions/Consumer;
    //   21: aload_1
    //   22: invokeinterface 77 2 0
    //   27: goto +30 -> 57
    //   30: astore_2
    //   31: aload_2
    //   32: invokestatic 67	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   35: new 79	io/reactivex/exceptions/CompositeException
    //   38: dup
    //   39: iconst_2
    //   40: anewarray 81	java/lang/Throwable
    //   43: dup
    //   44: iconst_0
    //   45: aload_1
    //   46: aastore
    //   47: dup
    //   48: iconst_1
    //   49: aload_2
    //   50: aastore
    //   51: invokespecial 84	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   54: invokestatic 71	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   57: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	ForEachWhileSubscriber
    //   0	58	1	paramThrowable	Throwable
    //   30	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	27	30	finally
  }
  
  public void onNext(T paramT)
  {
    if (this.done) {
      return;
    }
    try
    {
      boolean bool = this.onNext.test(paramT);
      if (!bool)
      {
        dispose();
        onComplete();
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(paramT);
      dispose();
      onError(paramT);
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
  }
}
