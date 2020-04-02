package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletablePeek
  extends Completable
{
  final Action onAfterTerminate;
  final Action onComplete;
  final Action onDispose;
  final Consumer<? super Throwable> onError;
  final Consumer<? super Disposable> onSubscribe;
  final Action onTerminate;
  final CompletableSource source;
  
  public CompletablePeek(CompletableSource paramCompletableSource, Consumer<? super Disposable> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2, Action paramAction3, Action paramAction4)
  {
    this.source = paramCompletableSource;
    this.onSubscribe = paramConsumer;
    this.onError = paramConsumer1;
    this.onComplete = paramAction1;
    this.onTerminate = paramAction2;
    this.onAfterTerminate = paramAction3;
    this.onDispose = paramAction4;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new CompletableObserverImplementation(paramCompletableObserver));
  }
  
  final class CompletableObserverImplementation
    implements CompletableObserver, Disposable
  {
    final CompletableObserver downstream;
    Disposable upstream;
    
    CompletableObserverImplementation(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    /* Error */
    public void dispose()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 21	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:this$0	Lio/reactivex/internal/operators/completable/CompletablePeek;
      //   4: getfield 32	io/reactivex/internal/operators/completable/CompletablePeek:onDispose	Lio/reactivex/functions/Action;
      //   7: invokeinterface 37 1 0
      //   12: goto +12 -> 24
      //   15: astore_1
      //   16: aload_1
      //   17: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   20: aload_1
      //   21: invokestatic 48	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   24: aload_0
      //   25: getfield 50	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:upstream	Lio/reactivex/disposables/Disposable;
      //   28: invokeinterface 52 1 0
      //   33: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	34	0	this	CompletableObserverImplementation
      //   15	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	12	15	finally
    }
    
    /* Error */
    void doAfter()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 21	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:this$0	Lio/reactivex/internal/operators/completable/CompletablePeek;
      //   4: getfield 56	io/reactivex/internal/operators/completable/CompletablePeek:onAfterTerminate	Lio/reactivex/functions/Action;
      //   7: invokeinterface 37 1 0
      //   12: goto +12 -> 24
      //   15: astore_1
      //   16: aload_1
      //   17: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   20: aload_1
      //   21: invokestatic 48	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   24: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	25	0	this	CompletableObserverImplementation
      //   15	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	12	15	finally
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (this.upstream == DisposableHelper.DISPOSED) {
        return;
      }
      try
      {
        CompletablePeek.this.onComplete.run();
        CompletablePeek.this.onTerminate.run();
        this.downstream.onComplete();
        doAfter();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 50	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:upstream	Lio/reactivex/disposables/Disposable;
      //   4: getstatic 67	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   7: if_acmpne +8 -> 15
      //   10: aload_1
      //   11: invokestatic 48	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   14: return
      //   15: aload_0
      //   16: getfield 21	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:this$0	Lio/reactivex/internal/operators/completable/CompletablePeek;
      //   19: getfield 80	io/reactivex/internal/operators/completable/CompletablePeek:onError	Lio/reactivex/functions/Consumer;
      //   22: aload_1
      //   23: invokeinterface 86 2 0
      //   28: aload_0
      //   29: getfield 21	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:this$0	Lio/reactivex/internal/operators/completable/CompletablePeek;
      //   32: getfield 72	io/reactivex/internal/operators/completable/CompletablePeek:onTerminate	Lio/reactivex/functions/Action;
      //   35: invokeinterface 37 1 0
      //   40: goto +28 -> 68
      //   43: astore_2
      //   44: aload_2
      //   45: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   48: new 88	io/reactivex/exceptions/CompositeException
      //   51: dup
      //   52: iconst_2
      //   53: anewarray 90	java/lang/Throwable
      //   56: dup
      //   57: iconst_0
      //   58: aload_1
      //   59: aastore
      //   60: dup
      //   61: iconst_1
      //   62: aload_2
      //   63: aastore
      //   64: invokespecial 93	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   67: astore_1
      //   68: aload_0
      //   69: getfield 26	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:downstream	Lio/reactivex/CompletableObserver;
      //   72: aload_1
      //   73: invokeinterface 77 2 0
      //   78: aload_0
      //   79: invokevirtual 76	io/reactivex/internal/operators/completable/CompletablePeek$CompletableObserverImplementation:doAfter	()V
      //   82: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	83	0	this	CompletableObserverImplementation
      //   0	83	1	paramThrowable	Throwable
      //   43	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   15	40	43	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      try
      {
        CompletablePeek.this.onSubscribe.accept(paramDisposable);
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
}
