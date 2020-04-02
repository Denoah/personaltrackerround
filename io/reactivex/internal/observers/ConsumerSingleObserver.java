package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.Functions;
import io.reactivex.observers.LambdaConsumerIntrospection;
import java.util.concurrent.atomic.AtomicReference;

public final class ConsumerSingleObserver<T>
  extends AtomicReference<Disposable>
  implements SingleObserver<T>, Disposable, LambdaConsumerIntrospection
{
  private static final long serialVersionUID = -7012088219455310787L;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onSuccess;
  
  public ConsumerSingleObserver(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1)
  {
    this.onSuccess = paramConsumer;
    this.onError = paramConsumer1;
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
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
    if (get() == DisposableHelper.DISPOSED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getstatic 54	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   4: invokevirtual 59	io/reactivex/internal/observers/ConsumerSingleObserver:lazySet	(Ljava/lang/Object;)V
    //   7: aload_0
    //   8: getfield 29	io/reactivex/internal/observers/ConsumerSingleObserver:onError	Lio/reactivex/functions/Consumer;
    //   11: aload_1
    //   12: invokeinterface 64 2 0
    //   17: goto +30 -> 47
    //   20: astore_2
    //   21: aload_2
    //   22: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   25: new 71	io/reactivex/exceptions/CompositeException
    //   28: dup
    //   29: iconst_2
    //   30: anewarray 73	java/lang/Throwable
    //   33: dup
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: dup
    //   38: iconst_1
    //   39: aload_2
    //   40: aastore
    //   41: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   44: invokestatic 80	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   47: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	ConsumerSingleObserver
    //   0	48	1	paramThrowable	Throwable
    //   20	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   7	17	20	finally
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this, paramDisposable);
  }
  
  /* Error */
  public void onSuccess(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: getstatic 54	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   4: invokevirtual 59	io/reactivex/internal/observers/ConsumerSingleObserver:lazySet	(Ljava/lang/Object;)V
    //   7: aload_0
    //   8: getfield 27	io/reactivex/internal/observers/ConsumerSingleObserver:onSuccess	Lio/reactivex/functions/Consumer;
    //   11: aload_1
    //   12: invokeinterface 64 2 0
    //   17: goto +12 -> 29
    //   20: astore_1
    //   21: aload_1
    //   22: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   25: aload_1
    //   26: invokestatic 80	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   29: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	ConsumerSingleObserver
    //   0	30	1	paramT	T
    // Exception table:
    //   from	to	target	type
    //   7	17	20	finally
  }
}
