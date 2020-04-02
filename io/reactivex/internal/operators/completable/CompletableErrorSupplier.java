package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import java.util.concurrent.Callable;

public final class CompletableErrorSupplier
  extends Completable
{
  final Callable<? extends Throwable> errorSupplier;
  
  public CompletableErrorSupplier(Callable<? extends Throwable> paramCallable)
  {
    this.errorSupplier = paramCallable;
  }
  
  /* Error */
  protected void subscribeActual(io.reactivex.CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 14	io/reactivex/internal/operators/completable/CompletableErrorSupplier:errorSupplier	Ljava/util/concurrent/Callable;
    //   4: invokeinterface 25 1 0
    //   9: ldc 27
    //   11: invokestatic 33	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   14: checkcast 35	java/lang/Throwable
    //   17: astore_2
    //   18: goto +8 -> 26
    //   21: astore_2
    //   22: aload_2
    //   23: invokestatic 41	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   26: aload_2
    //   27: aload_1
    //   28: invokestatic 47	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/CompletableObserver;)V
    //   31: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	32	0	this	CompletableErrorSupplier
    //   0	32	1	paramCompletableObserver	io.reactivex.CompletableObserver
    //   17	1	2	localThrowable1	Throwable
    //   21	6	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	18	21	finally
  }
}
