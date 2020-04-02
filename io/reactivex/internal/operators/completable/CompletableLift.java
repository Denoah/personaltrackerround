package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableOperator;
import io.reactivex.CompletableSource;

public final class CompletableLift
  extends Completable
{
  final CompletableOperator onLift;
  final CompletableSource source;
  
  public CompletableLift(CompletableSource paramCompletableSource, CompletableOperator paramCompletableOperator)
  {
    this.source = paramCompletableSource;
    this.onLift = paramCompletableOperator;
  }
  
  /* Error */
  protected void subscribeActual(io.reactivex.CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 17	io/reactivex/internal/operators/completable/CompletableLift:onLift	Lio/reactivex/CompletableOperator;
    //   4: aload_1
    //   5: invokeinterface 28 2 0
    //   10: astore_1
    //   11: aload_0
    //   12: getfield 15	io/reactivex/internal/operators/completable/CompletableLift:source	Lio/reactivex/CompletableSource;
    //   15: aload_1
    //   16: invokeinterface 33 2 0
    //   21: goto +12 -> 33
    //   24: astore_1
    //   25: aload_1
    //   26: invokestatic 39	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   29: aload_1
    //   30: invokestatic 44	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   33: return
    //   34: astore_1
    //   35: aload_1
    //   36: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	37	0	this	CompletableLift
    //   0	37	1	paramCompletableObserver	io.reactivex.CompletableObserver
    // Exception table:
    //   from	to	target	type
    //   0	21	24	finally
    //   0	21	34	java/lang/NullPointerException
  }
}
