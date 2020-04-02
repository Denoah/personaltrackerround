package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableDoFinally
  extends Completable
{
  final Action onFinally;
  final CompletableSource source;
  
  public CompletableDoFinally(CompletableSource paramCompletableSource, Action paramAction)
  {
    this.source = paramCompletableSource;
    this.onFinally = paramAction;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new DoFinallyObserver(paramCompletableObserver, this.onFinally));
  }
  
  static final class DoFinallyObserver
    extends AtomicInteger
    implements CompletableObserver, Disposable
  {
    private static final long serialVersionUID = 4109457741734051389L;
    final CompletableObserver downstream;
    final Action onFinally;
    Disposable upstream;
    
    DoFinallyObserver(CompletableObserver paramCompletableObserver, Action paramAction)
    {
      this.downstream = paramCompletableObserver;
      this.onFinally = paramAction;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      runFinally();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      runFinally();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      runFinally();
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
    void runFinally()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 64	io/reactivex/internal/operators/completable/CompletableDoFinally$DoFinallyObserver:compareAndSet	(II)Z
      //   6: ifeq +24 -> 30
      //   9: aload_0
      //   10: getfield 30	io/reactivex/internal/operators/completable/CompletableDoFinally$DoFinallyObserver:onFinally	Lio/reactivex/functions/Action;
      //   13: invokeinterface 69 1 0
      //   18: goto +12 -> 30
      //   21: astore_1
      //   22: aload_1
      //   23: invokestatic 74	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   26: aload_1
      //   27: invokestatic 77	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   30: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	31	0	this	DoFinallyObserver
      //   21	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	18	21	finally
    }
  }
}
