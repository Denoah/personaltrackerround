package io.reactivex.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DisposableSingleObserver<T>
  implements SingleObserver<T>, Disposable
{
  final AtomicReference<Disposable> upstream = new AtomicReference();
  
  public DisposableSingleObserver() {}
  
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
