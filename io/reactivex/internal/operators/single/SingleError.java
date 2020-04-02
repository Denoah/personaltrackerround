package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import java.util.concurrent.Callable;

public final class SingleError<T>
  extends Single<T>
{
  final Callable<? extends Throwable> errorSupplier;
  
  public SingleError(Callable<? extends Throwable> paramCallable)
  {
    this.errorSupplier = paramCallable;
  }
  
  /* Error */
  protected void subscribeActual(io.reactivex.SingleObserver<? super T> paramSingleObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 15	io/reactivex/internal/operators/single/SingleError:errorSupplier	Ljava/util/concurrent/Callable;
    //   4: invokeinterface 26 1 0
    //   9: ldc 28
    //   11: invokestatic 34	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   14: checkcast 36	java/lang/Throwable
    //   17: astore_2
    //   18: goto +8 -> 26
    //   21: astore_2
    //   22: aload_2
    //   23: invokestatic 42	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: aload_2
    //   27: aload_1
    //   28: invokestatic 48	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/SingleObserver;)V
    //   31: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	32	0	this	SingleError
    //   0	32	1	paramSingleObserver	io.reactivex.SingleObserver<? super T>
    //   17	1	2	localThrowable1	Throwable
    //   21	6	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	18	21	finally
  }
}
