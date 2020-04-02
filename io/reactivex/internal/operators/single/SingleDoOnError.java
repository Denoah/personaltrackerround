package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public final class SingleDoOnError<T>
  extends Single<T>
{
  final Consumer<? super Throwable> onError;
  final SingleSource<T> source;
  
  public SingleDoOnError(SingleSource<T> paramSingleSource, Consumer<? super Throwable> paramConsumer)
  {
    this.source = paramSingleSource;
    this.onError = paramConsumer;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoOnError(paramSingleObserver));
  }
  
  final class DoOnError
    implements SingleObserver<T>
  {
    private final SingleObserver<? super T> downstream;
    
    DoOnError()
    {
      Object localObject;
      this.downstream = localObject;
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 19	io/reactivex/internal/operators/single/SingleDoOnError$DoOnError:this$0	Lio/reactivex/internal/operators/single/SingleDoOnError;
      //   4: getfield 32	io/reactivex/internal/operators/single/SingleDoOnError:onError	Lio/reactivex/functions/Consumer;
      //   7: aload_1
      //   8: invokeinterface 38 2 0
      //   13: goto +28 -> 41
      //   16: astore_2
      //   17: aload_2
      //   18: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   21: new 45	io/reactivex/exceptions/CompositeException
      //   24: dup
      //   25: iconst_2
      //   26: anewarray 47	java/lang/Throwable
      //   29: dup
      //   30: iconst_0
      //   31: aload_1
      //   32: aastore
      //   33: dup
      //   34: iconst_1
      //   35: aload_2
      //   36: aastore
      //   37: invokespecial 50	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   40: astore_1
      //   41: aload_0
      //   42: getfield 24	io/reactivex/internal/operators/single/SingleDoOnError$DoOnError:downstream	Lio/reactivex/SingleObserver;
      //   45: aload_1
      //   46: invokeinterface 52 2 0
      //   51: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	52	0	this	DoOnError
      //   0	52	1	paramThrowable	Throwable
      //   16	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	13	16	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.downstream.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
    }
  }
}
