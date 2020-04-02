package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class BiConsumerSingleObserver<T>
  extends AtomicReference<Disposable>
  implements SingleObserver<T>, Disposable
{
  private static final long serialVersionUID = 4943102778943297569L;
  final BiConsumer<? super T, ? super Throwable> onCallback;
  
  public BiConsumerSingleObserver(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
  {
    this.onCallback = paramBiConsumer;
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
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
    //   1: getstatic 42	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   4: invokevirtual 48	io/reactivex/internal/observers/BiConsumerSingleObserver:lazySet	(Ljava/lang/Object;)V
    //   7: aload_0
    //   8: getfield 23	io/reactivex/internal/observers/BiConsumerSingleObserver:onCallback	Lio/reactivex/functions/BiConsumer;
    //   11: aconst_null
    //   12: aload_1
    //   13: invokeinterface 54 3 0
    //   18: goto +30 -> 48
    //   21: astore_2
    //   22: aload_2
    //   23: invokestatic 59	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: new 61	io/reactivex/exceptions/CompositeException
    //   29: dup
    //   30: iconst_2
    //   31: anewarray 63	java/lang/Throwable
    //   34: dup
    //   35: iconst_0
    //   36: aload_1
    //   37: aastore
    //   38: dup
    //   39: iconst_1
    //   40: aload_2
    //   41: aastore
    //   42: invokespecial 66	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   45: invokestatic 70	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   48: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	49	0	this	BiConsumerSingleObserver
    //   0	49	1	paramThrowable	Throwable
    //   21	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	18	21	finally
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
    //   1: getstatic 42	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   4: invokevirtual 48	io/reactivex/internal/observers/BiConsumerSingleObserver:lazySet	(Ljava/lang/Object;)V
    //   7: aload_0
    //   8: getfield 23	io/reactivex/internal/observers/BiConsumerSingleObserver:onCallback	Lio/reactivex/functions/BiConsumer;
    //   11: aload_1
    //   12: aconst_null
    //   13: invokeinterface 54 3 0
    //   18: goto +12 -> 30
    //   21: astore_1
    //   22: aload_1
    //   23: invokestatic 59	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: aload_1
    //   27: invokestatic 70	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   30: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	this	BiConsumerSingleObserver
    //   0	31	1	paramT	T
    // Exception table:
    //   from	to	target	type
    //   0	18	21	finally
  }
}
