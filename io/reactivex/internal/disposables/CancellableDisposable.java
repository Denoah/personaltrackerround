package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CancellableDisposable
  extends AtomicReference<Cancellable>
  implements Disposable
{
  private static final long serialVersionUID = 5718521705281392066L;
  
  public CancellableDisposable(Cancellable paramCancellable)
  {
    super(paramCancellable);
  }
  
  public void dispose()
  {
    if (get() != null)
    {
      Cancellable localCancellable = (Cancellable)getAndSet(null);
      if (localCancellable != null) {
        try
        {
          localCancellable.cancel();
        }
        catch (Exception localException)
        {
          Exceptions.throwIfFatal(localException);
          RxJavaPlugins.onError(localException);
        }
      }
    }
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (get() == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
