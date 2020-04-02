package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCreate<T>
  extends Single<T>
{
  final SingleOnSubscribe<T> source;
  
  public SingleCreate(SingleOnSubscribe<T> paramSingleOnSubscribe)
  {
    this.source = paramSingleOnSubscribe;
  }
  
  /* Error */
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    // Byte code:
    //   0: new 7	io/reactivex/internal/operators/single/SingleCreate$Emitter
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 25	io/reactivex/internal/operators/single/SingleCreate$Emitter:<init>	(Lio/reactivex/SingleObserver;)V
    //   8: astore_2
    //   9: aload_1
    //   10: aload_2
    //   11: invokeinterface 31 2 0
    //   16: aload_0
    //   17: getfield 18	io/reactivex/internal/operators/single/SingleCreate:source	Lio/reactivex/SingleOnSubscribe;
    //   20: aload_2
    //   21: invokeinterface 37 2 0
    //   26: goto +13 -> 39
    //   29: astore_1
    //   30: aload_1
    //   31: invokestatic 43	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_2
    //   35: aload_1
    //   36: invokevirtual 46	io/reactivex/internal/operators/single/SingleCreate$Emitter:onError	(Ljava/lang/Throwable;)V
    //   39: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	SingleCreate
    //   0	40	1	paramSingleObserver	SingleObserver<? super T>
    //   8	27	2	localEmitter	Emitter
    // Exception table:
    //   from	to	target	type
    //   16	26	29	finally
  }
  
  static final class Emitter<T>
    extends AtomicReference<Disposable>
    implements SingleEmitter<T>, Disposable
  {
    private static final long serialVersionUID = -2467358622224974244L;
    final SingleObserver<? super T> downstream;
    
    Emitter(SingleObserver<? super T> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSuccess(T paramT)
    {
      if (get() != DisposableHelper.DISPOSED)
      {
        Disposable localDisposable = (Disposable)getAndSet(DisposableHelper.DISPOSED);
        if (localDisposable != DisposableHelper.DISPOSED)
        {
          if (paramT == null) {}
          try
          {
            SingleObserver localSingleObserver = this.downstream;
            paramT = new java/lang/NullPointerException;
            paramT.<init>("onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
            localSingleObserver.onError(paramT);
            break label67;
            this.downstream.onSuccess(paramT);
          }
          finally
          {
            label67:
            if (localDisposable != null) {
              localDisposable.dispose();
            }
          }
        }
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
