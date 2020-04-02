package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleDoAfterTerminate<T>
  extends Single<T>
{
  final Action onAfterTerminate;
  final SingleSource<T> source;
  
  public SingleDoAfterTerminate(SingleSource<T> paramSingleSource, Action paramAction)
  {
    this.source = paramSingleSource;
    this.onAfterTerminate = paramAction;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoAfterTerminateObserver(paramSingleObserver, this.onAfterTerminate));
  }
  
  static final class DoAfterTerminateObserver<T>
    implements SingleObserver<T>, Disposable
  {
    final SingleObserver<? super T> downstream;
    final Action onAfterTerminate;
    Disposable upstream;
    
    DoAfterTerminateObserver(SingleObserver<? super T> paramSingleObserver, Action paramAction)
    {
      this.downstream = paramSingleObserver;
      this.onAfterTerminate = paramAction;
    }
    
    /* Error */
    private void onAfterTerminate()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 28	io/reactivex/internal/operators/single/SingleDoAfterTerminate$DoAfterTerminateObserver:onAfterTerminate	Lio/reactivex/functions/Action;
      //   4: invokeinterface 36 1 0
      //   9: goto +12 -> 21
      //   12: astore_1
      //   13: aload_1
      //   14: invokestatic 42	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   17: aload_1
      //   18: invokestatic 47	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   21: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	22	0	this	DoAfterTerminateObserver
      //   12	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	9	12	finally
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
      onAfterTerminate();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
      onAfterTerminate();
    }
  }
}
