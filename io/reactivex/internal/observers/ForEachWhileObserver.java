package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ForEachWhileObserver<T>
  extends AtomicReference<Disposable>
  implements Observer<T>, Disposable
{
  private static final long serialVersionUID = -4403180040475402120L;
  boolean done;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Predicate<? super T> onNext;
  
  public ForEachWhileObserver(Predicate<? super T> paramPredicate, Consumer<? super Throwable> paramConsumer, Action paramAction)
  {
    this.onNext = paramPredicate;
    this.onError = paramConsumer;
    this.onComplete = paramAction;
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
  }
  
  public boolean isDisposed()
  {
    return DisposableHelper.isDisposed((Disposable)get());
  }
  
  /* Error */
  public void onComplete()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 54	io/reactivex/internal/observers/ForEachWhileObserver:done	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield 54	io/reactivex/internal/observers/ForEachWhileObserver:done	Z
    //   13: aload_0
    //   14: getfield 34	io/reactivex/internal/observers/ForEachWhileObserver:onComplete	Lio/reactivex/functions/Action;
    //   17: invokeinterface 59 1 0
    //   22: goto +12 -> 34
    //   25: astore_1
    //   26: aload_1
    //   27: invokestatic 65	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   30: aload_1
    //   31: invokestatic 69	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   34: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	35	0	this	ForEachWhileObserver
    //   25	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   13	22	25	finally
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 54	io/reactivex/internal/observers/ForEachWhileObserver:done	Z
    //   4: ifeq +8 -> 12
    //   7: aload_1
    //   8: invokestatic 69	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 54	io/reactivex/internal/observers/ForEachWhileObserver:done	Z
    //   17: aload_0
    //   18: getfield 32	io/reactivex/internal/observers/ForEachWhileObserver:onError	Lio/reactivex/functions/Consumer;
    //   21: aload_1
    //   22: invokeinterface 75 2 0
    //   27: goto +30 -> 57
    //   30: astore_2
    //   31: aload_2
    //   32: invokestatic 65	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   35: new 77	io/reactivex/exceptions/CompositeException
    //   38: dup
    //   39: iconst_2
    //   40: anewarray 79	java/lang/Throwable
    //   43: dup
    //   44: iconst_0
    //   45: aload_1
    //   46: aastore
    //   47: dup
    //   48: iconst_1
    //   49: aload_2
    //   50: aastore
    //   51: invokespecial 82	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   54: invokestatic 69	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   57: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	ForEachWhileObserver
    //   0	58	1	paramThrowable	Throwable
    //   30	20	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	27	30	finally
  }
  
  public void onNext(T paramT)
  {
    if (this.done) {
      return;
    }
    try
    {
      boolean bool = this.onNext.test(paramT);
      if (!bool)
      {
        dispose();
        onComplete();
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(paramT);
      dispose();
      onError(paramT);
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this, paramDisposable);
  }
}
