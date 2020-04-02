package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDoOnDispose<T>
  extends Single<T>
{
  final Action onDispose;
  final SingleSource<T> source;
  
  public SingleDoOnDispose(SingleSource<T> paramSingleSource, Action paramAction)
  {
    this.source = paramSingleSource;
    this.onDispose = paramAction;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoOnDisposeObserver(paramSingleObserver, this.onDispose));
  }
  
  static final class DoOnDisposeObserver<T>
    extends AtomicReference<Action>
    implements SingleObserver<T>, Disposable
  {
    private static final long serialVersionUID = -8583764624474935784L;
    final SingleObserver<? super T> downstream;
    Disposable upstream;
    
    DoOnDisposeObserver(SingleObserver<? super T> paramSingleObserver, Action paramAction)
    {
      this.downstream = paramSingleObserver;
      lazySet(paramAction);
    }
    
    /* Error */
    public void dispose()
    {
      // Byte code:
      //   0: aload_0
      //   1: aconst_null
      //   2: invokevirtual 40	io/reactivex/internal/operators/single/SingleDoOnDispose$DoOnDisposeObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   5: checkcast 42	io/reactivex/functions/Action
      //   8: astore_1
      //   9: aload_1
      //   10: ifnull +30 -> 40
      //   13: aload_1
      //   14: invokeinterface 45 1 0
      //   19: goto +12 -> 31
      //   22: astore_1
      //   23: aload_1
      //   24: invokestatic 51	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   27: aload_1
      //   28: invokestatic 56	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   31: aload_0
      //   32: getfield 58	io/reactivex/internal/operators/single/SingleDoOnDispose$DoOnDisposeObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   35: invokeinterface 60 1 0
      //   40: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	41	0	this	DoOnDisposeObserver
      //   8	6	1	localAction	Action
      //   22	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   13	19	22	finally
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
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
    }
  }
}
