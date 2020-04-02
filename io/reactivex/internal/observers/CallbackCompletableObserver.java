package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CallbackCompletableObserver
  extends AtomicReference<Disposable>
  implements CompletableObserver, Disposable, Consumer<Throwable>, LambdaConsumerIntrospection
{
  private static final long serialVersionUID = -4361286194466301354L;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  
  public CallbackCompletableObserver(Action paramAction)
  {
    this.onError = this;
    this.onComplete = paramAction;
  }
  
  public CallbackCompletableObserver(Consumer<? super Throwable> paramConsumer, Action paramAction)
  {
    this.onError = paramConsumer;
    this.onComplete = paramAction;
  }
  
  public void accept(Throwable paramThrowable)
  {
    RxJavaPlugins.onError(new OnErrorNotImplementedException(paramThrowable));
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
  }
  
  public boolean hasCustomOnError()
  {
    boolean bool;
    if (this.onError != this) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (get() == DisposableHelper.DISPOSED) {
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
    //   1: getfield 31	io/reactivex/internal/observers/CallbackCompletableObserver:onComplete	Lio/reactivex/functions/Action;
    //   4: invokeinterface 75 1 0
    //   9: goto +12 -> 21
    //   12: astore_1
    //   13: aload_1
    //   14: invokestatic 80	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   17: aload_1
    //   18: invokestatic 53	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   21: aload_0
    //   22: getstatic 70	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   25: invokevirtual 83	io/reactivex/internal/observers/CallbackCompletableObserver:lazySet	(Ljava/lang/Object;)V
    //   28: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	29	0	this	CallbackCompletableObserver
    //   12	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	9	12	finally
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 29	io/reactivex/internal/observers/CallbackCompletableObserver:onError	Lio/reactivex/functions/Consumer;
    //   4: aload_1
    //   5: invokeinterface 85 2 0
    //   10: goto +12 -> 22
    //   13: astore_1
    //   14: aload_1
    //   15: invokestatic 80	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   18: aload_1
    //   19: invokestatic 53	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   22: aload_0
    //   23: getstatic 70	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   26: invokevirtual 83	io/reactivex/internal/observers/CallbackCompletableObserver:lazySet	(Ljava/lang/Object;)V
    //   29: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	CallbackCompletableObserver
    //   0	30	1	paramThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	10	13	finally
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this, paramDisposable);
  }
}
