package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public final class SingleOnErrorReturn<T>
  extends Single<T>
{
  final SingleSource<? extends T> source;
  final T value;
  final Function<? super Throwable, ? extends T> valueSupplier;
  
  public SingleOnErrorReturn(SingleSource<? extends T> paramSingleSource, Function<? super Throwable, ? extends T> paramFunction, T paramT)
  {
    this.source = paramSingleSource;
    this.valueSupplier = paramFunction;
    this.value = paramT;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new OnErrorReturn(paramSingleObserver));
  }
  
  final class OnErrorReturn
    implements SingleObserver<T>
  {
    private final SingleObserver<? super T> observer;
    
    OnErrorReturn()
    {
      Object localObject;
      this.observer = localObject;
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 19	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:this$0	Lio/reactivex/internal/operators/single/SingleOnErrorReturn;
      //   4: getfield 33	io/reactivex/internal/operators/single/SingleOnErrorReturn:valueSupplier	Lio/reactivex/functions/Function;
      //   7: ifnull +54 -> 61
      //   10: aload_0
      //   11: getfield 19	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:this$0	Lio/reactivex/internal/operators/single/SingleOnErrorReturn;
      //   14: getfield 33	io/reactivex/internal/operators/single/SingleOnErrorReturn:valueSupplier	Lio/reactivex/functions/Function;
      //   17: aload_1
      //   18: invokeinterface 39 2 0
      //   23: astore_2
      //   24: goto +45 -> 69
      //   27: astore_2
      //   28: aload_2
      //   29: invokestatic 44	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   32: aload_0
      //   33: getfield 24	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:observer	Lio/reactivex/SingleObserver;
      //   36: new 46	io/reactivex/exceptions/CompositeException
      //   39: dup
      //   40: iconst_2
      //   41: anewarray 48	java/lang/Throwable
      //   44: dup
      //   45: iconst_0
      //   46: aload_1
      //   47: aastore
      //   48: dup
      //   49: iconst_1
      //   50: aload_2
      //   51: aastore
      //   52: invokespecial 51	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   55: invokeinterface 53 2 0
      //   60: return
      //   61: aload_0
      //   62: getfield 19	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:this$0	Lio/reactivex/internal/operators/single/SingleOnErrorReturn;
      //   65: getfield 57	io/reactivex/internal/operators/single/SingleOnErrorReturn:value	Ljava/lang/Object;
      //   68: astore_2
      //   69: aload_2
      //   70: ifnonnull +30 -> 100
      //   73: new 59	java/lang/NullPointerException
      //   76: dup
      //   77: ldc 61
      //   79: invokespecial 64	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   82: astore_2
      //   83: aload_2
      //   84: aload_1
      //   85: invokevirtual 68	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
      //   88: pop
      //   89: aload_0
      //   90: getfield 24	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:observer	Lio/reactivex/SingleObserver;
      //   93: aload_2
      //   94: invokeinterface 53 2 0
      //   99: return
      //   100: aload_0
      //   101: getfield 24	io/reactivex/internal/operators/single/SingleOnErrorReturn$OnErrorReturn:observer	Lio/reactivex/SingleObserver;
      //   104: aload_2
      //   105: invokeinterface 72 2 0
      //   110: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	111	0	this	OnErrorReturn
      //   0	111	1	paramThrowable	Throwable
      //   23	1	2	localObject1	Object
      //   27	24	2	localThrowable	Throwable
      //   68	37	2	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   10	24	27	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.observer.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.observer.onSuccess(paramT);
    }
  }
}
