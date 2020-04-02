package io.reactivex.disposables;

import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

abstract class ReferenceDisposable<T>
  extends AtomicReference<T>
  implements Disposable
{
  private static final long serialVersionUID = 6537757548749041217L;
  
  ReferenceDisposable(T paramT)
  {
    super(ObjectHelper.requireNonNull(paramT, "value is null"));
  }
  
  public final void dispose()
  {
    if (get() != null)
    {
      Object localObject = getAndSet(null);
      if (localObject != null) {
        onDisposed(localObject);
      }
    }
  }
  
  public final boolean isDisposed()
  {
    boolean bool;
    if (get() == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected abstract void onDisposed(T paramT);
}
