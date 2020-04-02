package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableCreate
  extends Completable
{
  final CompletableOnSubscribe source;
  
  public CompletableCreate(CompletableOnSubscribe paramCompletableOnSubscribe)
  {
    this.source = paramCompletableOnSubscribe;
  }
  
  /* Error */
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: new 6	io/reactivex/internal/operators/completable/CompletableCreate$Emitter
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 21	io/reactivex/internal/operators/completable/CompletableCreate$Emitter:<init>	(Lio/reactivex/CompletableObserver;)V
    //   8: astore_2
    //   9: aload_1
    //   10: aload_2
    //   11: invokeinterface 27 2 0
    //   16: aload_0
    //   17: getfield 16	io/reactivex/internal/operators/completable/CompletableCreate:source	Lio/reactivex/CompletableOnSubscribe;
    //   20: aload_2
    //   21: invokeinterface 33 2 0
    //   26: goto +13 -> 39
    //   29: astore_1
    //   30: aload_1
    //   31: invokestatic 39	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_2
    //   35: aload_1
    //   36: invokevirtual 42	io/reactivex/internal/operators/completable/CompletableCreate$Emitter:onError	(Ljava/lang/Throwable;)V
    //   39: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	CompletableCreate
    //   0	40	1	paramCompletableObserver	CompletableObserver
    //   8	27	2	localEmitter	Emitter
    // Exception table:
    //   from	to	target	type
    //   16	26	29	finally
  }
  
  static final class Emitter
    extends AtomicReference<Disposable>
    implements CompletableEmitter, Disposable
  {
    private static final long serialVersionUID = -2467358622224974244L;
    final CompletableObserver downstream;
    
    Emitter(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      if (get() != DisposableHelper.DISPOSED)
      {
        Disposable localDisposable = (Disposable)getAndSet(DisposableHelper.DISPOSED);
        if (localDisposable != DisposableHelper.DISPOSED) {
          try
          {
            this.downstream.onComplete();
            if (localDisposable != null) {
              localDisposable.dispose();
            }
          }
          finally
          {
            if (localDisposable != null) {
              localDisposable.dispose();
            }
          }
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void setCancellable(Cancellable paramCancellable)
    {
      setDisposable(new CancellableDisposable(paramCancellable));
    }
    
    public void setDisposable(Disposable paramDisposable)
    {
      DisposableHelper.set(this, paramDisposable);
    }
    
    public String toString()
    {
      return String.format("%s{%s}", new Object[] { getClass().getSimpleName(), super.toString() });
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      Object localObject1 = paramThrowable;
      if (paramThrowable == null) {
        localObject1 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      }
      if (get() != DisposableHelper.DISPOSED)
      {
        paramThrowable = (Disposable)getAndSet(DisposableHelper.DISPOSED);
        if (paramThrowable != DisposableHelper.DISPOSED) {
          try
          {
            this.downstream.onError((Throwable)localObject1);
            return true;
          }
          finally
          {
            if (paramThrowable != null) {
              paramThrowable.dispose();
            }
          }
        }
      }
      return false;
    }
  }
}
