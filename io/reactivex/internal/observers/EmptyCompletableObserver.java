package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class EmptyCompletableObserver
  extends AtomicReference<Disposable>
  implements CompletableObserver, Disposable, LambdaConsumerIntrospection
{
  private static final long serialVersionUID = -7545121636549663526L;
  
  public EmptyCompletableObserver() {}
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
  }
  
  public boolean hasCustomOnError()
  {
    return false;
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (get() == DisposableHelper.DISPOSED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    lazySet(DisposableHelper.DISPOSED);
  }
  
  public void onError(Throwable paramThrowable)
  {
    lazySet(DisposableHelper.DISPOSED);
    RxJavaPlugins.onError(new OnErrorNotImplementedException(paramThrowable));
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this, paramDisposable);
  }
}
