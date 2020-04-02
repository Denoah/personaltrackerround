package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class DisposableLambdaObserver<T>
  implements Observer<T>, Disposable
{
  final Observer<? super T> downstream;
  final Action onDispose;
  final Consumer<? super Disposable> onSubscribe;
  Disposable upstream;
  
  public DisposableLambdaObserver(Observer<? super T> paramObserver, Consumer<? super Disposable> paramConsumer, Action paramAction)
  {
    this.downstream = paramObserver;
    this.onSubscribe = paramConsumer;
    this.onDispose = paramAction;
  }
  
  /* Error */
  public void dispose()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 36	io/reactivex/internal/observers/DisposableLambdaObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   4: astore_1
    //   5: aload_1
    //   6: getstatic 42	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   9: if_acmpeq +37 -> 46
    //   12: aload_0
    //   13: getstatic 42	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
    //   16: putfield 36	io/reactivex/internal/observers/DisposableLambdaObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   19: aload_0
    //   20: getfield 30	io/reactivex/internal/observers/DisposableLambdaObserver:onDispose	Lio/reactivex/functions/Action;
    //   23: invokeinterface 47 1 0
    //   28: goto +12 -> 40
    //   31: astore_2
    //   32: aload_2
    //   33: invokestatic 53	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   36: aload_2
    //   37: invokestatic 58	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   40: aload_1
    //   41: invokeinterface 60 1 0
    //   46: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	DisposableLambdaObserver
    //   4	37	1	localDisposable	Disposable
    //   31	6	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   19	28	31	finally
  }
  
  public boolean isDisposed()
  {
    return this.upstream.isDisposed();
  }
  
  public void onComplete()
  {
    if (this.upstream != DisposableHelper.DISPOSED)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onComplete();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.upstream != DisposableHelper.DISPOSED)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onError(paramThrowable);
    }
    else
    {
      RxJavaPlugins.onError(paramThrowable);
    }
  }
  
  public void onNext(T paramT)
  {
    this.downstream.onNext(paramT);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    try
    {
      this.onSubscribe.accept(paramDisposable);
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      paramDisposable.dispose();
      this.upstream = DisposableHelper.DISPOSED;
      EmptyDisposable.error(localThrowable, this.downstream);
    }
  }
}
