package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleDoAfterSuccess<T>
  extends Single<T>
{
  final Consumer<? super T> onAfterSuccess;
  final SingleSource<T> source;
  
  public SingleDoAfterSuccess(SingleSource<T> paramSingleSource, Consumer<? super T> paramConsumer)
  {
    this.source = paramSingleSource;
    this.onAfterSuccess = paramConsumer;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoAfterObserver(paramSingleObserver, this.onAfterSuccess));
  }
  
  static final class DoAfterObserver<T>
    implements SingleObserver<T>, Disposable
  {
    final SingleObserver<? super T> downstream;
    final Consumer<? super T> onAfterSuccess;
    Disposable upstream;
    
    DoAfterObserver(SingleObserver<? super T> paramSingleObserver, Consumer<? super T> paramConsumer)
    {
      this.downstream = paramSingleObserver;
      this.onAfterSuccess = paramConsumer;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    /* Error */
    public void onSuccess(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/single/SingleDoAfterSuccess$DoAfterObserver:downstream	Lio/reactivex/SingleObserver;
      //   4: aload_1
      //   5: invokeinterface 59 2 0
      //   10: aload_0
      //   11: getfield 29	io/reactivex/internal/operators/single/SingleDoAfterSuccess$DoAfterObserver:onAfterSuccess	Lio/reactivex/functions/Consumer;
      //   14: aload_1
      //   15: invokeinterface 64 2 0
      //   20: goto +12 -> 32
      //   23: astore_1
      //   24: aload_1
      //   25: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   28: aload_1
      //   29: invokestatic 72	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   32: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	33	0	this	DoAfterObserver
      //   0	33	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   10	20	23	finally
    }
  }
}
