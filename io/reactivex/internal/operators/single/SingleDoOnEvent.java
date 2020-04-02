package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;

public final class SingleDoOnEvent<T>
  extends Single<T>
{
  final BiConsumer<? super T, ? super Throwable> onEvent;
  final SingleSource<T> source;
  
  public SingleDoOnEvent(SingleSource<T> paramSingleSource, BiConsumer<? super T, ? super Throwable> paramBiConsumer)
  {
    this.source = paramSingleSource;
    this.onEvent = paramBiConsumer;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoOnEvent(paramSingleObserver));
  }
  
  final class DoOnEvent
    implements SingleObserver<T>
  {
    private final SingleObserver<? super T> downstream;
    
    DoOnEvent()
    {
      Object localObject;
      this.downstream = localObject;
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 19	io/reactivex/internal/operators/single/SingleDoOnEvent$DoOnEvent:this$0	Lio/reactivex/internal/operators/single/SingleDoOnEvent;
      //   4: getfield 33	io/reactivex/internal/operators/single/SingleDoOnEvent:onEvent	Lio/reactivex/functions/BiConsumer;
      //   7: aconst_null
      //   8: aload_1
      //   9: invokeinterface 39 3 0
      //   14: goto +28 -> 42
      //   17: astore_2
      //   18: aload_2
      //   19: invokestatic 44	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   22: new 46	io/reactivex/exceptions/CompositeException
      //   25: dup
      //   26: iconst_2
      //   27: anewarray 48	java/lang/Throwable
      //   30: dup
      //   31: iconst_0
      //   32: aload_1
      //   33: aastore
      //   34: dup
      //   35: iconst_1
      //   36: aload_2
      //   37: aastore
      //   38: invokespecial 51	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   41: astore_1
      //   42: aload_0
      //   43: getfield 24	io/reactivex/internal/operators/single/SingleDoOnEvent$DoOnEvent:downstream	Lio/reactivex/SingleObserver;
      //   46: aload_1
      //   47: invokeinterface 53 2 0
      //   52: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	53	0	this	DoOnEvent
      //   0	53	1	paramThrowable	Throwable
      //   17	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	14	17	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.downstream.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        SingleDoOnEvent.this.onEvent.accept(paramT, null);
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
