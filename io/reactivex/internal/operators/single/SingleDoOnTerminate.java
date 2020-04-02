package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;

public final class SingleDoOnTerminate<T>
  extends Single<T>
{
  final Action onTerminate;
  final SingleSource<T> source;
  
  public SingleDoOnTerminate(SingleSource<T> paramSingleSource, Action paramAction)
  {
    this.source = paramSingleSource;
    this.onTerminate = paramAction;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoOnTerminate(paramSingleObserver));
  }
  
  final class DoOnTerminate
    implements SingleObserver<T>
  {
    final SingleObserver<? super T> downstream;
    
    DoOnTerminate()
    {
      Object localObject;
      this.downstream = localObject;
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 19	io/reactivex/internal/operators/single/SingleDoOnTerminate$DoOnTerminate:this$0	Lio/reactivex/internal/operators/single/SingleDoOnTerminate;
      //   4: getfield 33	io/reactivex/internal/operators/single/SingleDoOnTerminate:onTerminate	Lio/reactivex/functions/Action;
      //   7: invokeinterface 38 1 0
      //   12: goto +28 -> 40
      //   15: astore_2
      //   16: aload_2
      //   17: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   20: new 45	io/reactivex/exceptions/CompositeException
      //   23: dup
      //   24: iconst_2
      //   25: anewarray 47	java/lang/Throwable
      //   28: dup
      //   29: iconst_0
      //   30: aload_1
      //   31: aastore
      //   32: dup
      //   33: iconst_1
      //   34: aload_2
      //   35: aastore
      //   36: invokespecial 50	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   39: astore_1
      //   40: aload_0
      //   41: getfield 24	io/reactivex/internal/operators/single/SingleDoOnTerminate$DoOnTerminate:downstream	Lio/reactivex/SingleObserver;
      //   44: aload_1
      //   45: invokeinterface 52 2 0
      //   50: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	51	0	this	DoOnTerminate
      //   0	51	1	paramThrowable	Throwable
      //   15	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	12	15	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.downstream.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        SingleDoOnTerminate.this.onTerminate.run();
        this.downstream.onSuccess(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
  }
}
