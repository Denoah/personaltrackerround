package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.Callable;

public final class CompletableToSingle<T>
  extends Single<T>
{
  final T completionValue;
  final Callable<? extends T> completionValueSupplier;
  final CompletableSource source;
  
  public CompletableToSingle(CompletableSource paramCompletableSource, Callable<? extends T> paramCallable, T paramT)
  {
    this.source = paramCompletableSource;
    this.completionValue = paramT;
    this.completionValueSupplier = paramCallable;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new ToSingle(paramSingleObserver));
  }
  
  final class ToSingle
    implements CompletableObserver
  {
    private final SingleObserver<? super T> observer;
    
    ToSingle()
    {
      Object localObject;
      this.observer = localObject;
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 18	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:this$0	Lio/reactivex/internal/operators/completable/CompletableToSingle;
      //   4: getfield 31	io/reactivex/internal/operators/completable/CompletableToSingle:completionValueSupplier	Ljava/util/concurrent/Callable;
      //   7: ifnull +35 -> 42
      //   10: aload_0
      //   11: getfield 18	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:this$0	Lio/reactivex/internal/operators/completable/CompletableToSingle;
      //   14: getfield 31	io/reactivex/internal/operators/completable/CompletableToSingle:completionValueSupplier	Ljava/util/concurrent/Callable;
      //   17: invokeinterface 37 1 0
      //   22: astore_1
      //   23: goto +27 -> 50
      //   26: astore_1
      //   27: aload_1
      //   28: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   31: aload_0
      //   32: getfield 23	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:observer	Lio/reactivex/SingleObserver;
      //   35: aload_1
      //   36: invokeinterface 48 2 0
      //   41: return
      //   42: aload_0
      //   43: getfield 18	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:this$0	Lio/reactivex/internal/operators/completable/CompletableToSingle;
      //   46: getfield 52	io/reactivex/internal/operators/completable/CompletableToSingle:completionValue	Ljava/lang/Object;
      //   49: astore_1
      //   50: aload_1
      //   51: ifnonnull +24 -> 75
      //   54: aload_0
      //   55: getfield 23	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:observer	Lio/reactivex/SingleObserver;
      //   58: new 54	java/lang/NullPointerException
      //   61: dup
      //   62: ldc 56
      //   64: invokespecial 59	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   67: invokeinterface 48 2 0
      //   72: goto +13 -> 85
      //   75: aload_0
      //   76: getfield 23	io/reactivex/internal/operators/completable/CompletableToSingle$ToSingle:observer	Lio/reactivex/SingleObserver;
      //   79: aload_1
      //   80: invokeinterface 63 2 0
      //   85: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	86	0	this	ToSingle
      //   22	1	1	localObject1	Object
      //   26	10	1	localThrowable	Throwable
      //   49	31	1	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   10	23	26	finally
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.observer.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.observer.onSubscribe(paramDisposable);
    }
  }
}
