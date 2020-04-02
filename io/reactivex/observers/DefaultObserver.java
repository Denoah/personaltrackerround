package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

public abstract class DefaultObserver<T>
  implements Observer<T>
{
  private Disposable upstream;
  
  public DefaultObserver() {}
  
  protected final void cancel()
  {
    Disposable localDisposable = this.upstream;
    this.upstream = DisposableHelper.DISPOSED;
    localDisposable.dispose();
  }
  
  protected void onStart() {}
  
  public final void onSubscribe(Disposable paramDisposable)
  {
    if (EndConsumerHelper.validate(this.upstream, paramDisposable, getClass()))
    {
      this.upstream = paramDisposable;
      onStart();
    }
  }
}
