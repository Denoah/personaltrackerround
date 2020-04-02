package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class CompletableDoOnEvent
  extends Completable
{
  final Consumer<? super Throwable> onEvent;
  final CompletableSource source;
  
  public CompletableDoOnEvent(CompletableSource paramCompletableSource, Consumer<? super Throwable> paramConsumer)
  {
    this.source = paramCompletableSource;
    this.onEvent = paramConsumer;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new DoOnEvent(paramCompletableObserver));
  }
  
  final class DoOnEvent
    implements CompletableObserver
  {
    private final CompletableObserver observer;
    
    DoOnEvent(CompletableObserver paramCompletableObserver)
    {
      this.observer = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      try
      {
        CompletableDoOnEvent.this.onEvent.accept(null);
        this.observer.onComplete();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.observer.onError(localThrowable);
      }
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 17	io/reactivex/internal/operators/completable/CompletableDoOnEvent$DoOnEvent:this$0	Lio/reactivex/internal/operators/completable/CompletableDoOnEvent;
      //   4: getfield 28	io/reactivex/internal/operators/completable/CompletableDoOnEvent:onEvent	Lio/reactivex/functions/Consumer;
      //   7: aload_1
      //   8: invokeinterface 34 2 0
      //   13: goto +28 -> 41
      //   16: astore_2
      //   17: aload_2
      //   18: invokestatic 42	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   21: new 47	io/reactivex/exceptions/CompositeException
      //   24: dup
      //   25: iconst_2
      //   26: anewarray 49	java/lang/Throwable
      //   29: dup
      //   30: iconst_0
      //   31: aload_1
      //   32: aastore
      //   33: dup
      //   34: iconst_1
      //   35: aload_2
      //   36: aastore
      //   37: invokespecial 52	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   40: astore_1
      //   41: aload_0
      //   42: getfield 22	io/reactivex/internal/operators/completable/CompletableDoOnEvent$DoOnEvent:observer	Lio/reactivex/CompletableObserver;
      //   45: aload_1
      //   46: invokeinterface 45 2 0
      //   51: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	52	0	this	DoOnEvent
      //   0	52	1	paramThrowable	Throwable
      //   16	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	13	16	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.observer.onSubscribe(paramDisposable);
    }
  }
}
