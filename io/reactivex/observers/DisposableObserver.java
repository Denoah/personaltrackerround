package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DisposableObserver<T>
  implements Observer<T>, Disposable
{
  final AtomicReference<Disposable> upstream = new AtomicReference();
  
  public DisposableObserver() {}
  
  public final void dispose()
  {
    DisposableHelper.dispose(this.upstream);
  }
  
  public final boolean isDisposed()
  {
    boolean bool;
    if (this.upstream.get() == DisposableHelper.DISPOSED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void onStart() {}
  
  public final void onSubscribe(Disposable paramDisposable)
  {
    if (EndConsumerHelper.setOnce(this.upstream, paramDisposable, getClass())) {
      onStart();
    }
  }
}
