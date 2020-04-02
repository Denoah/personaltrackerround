package io.reactivex.disposables;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

final class FutureDisposable
  extends AtomicReference<Future<?>>
  implements Disposable
{
  private static final long serialVersionUID = 6545242830671168775L;
  private final boolean allowInterrupt;
  
  FutureDisposable(Future<?> paramFuture, boolean paramBoolean)
  {
    super(paramFuture);
    this.allowInterrupt = paramBoolean;
  }
  
  public void dispose()
  {
    Future localFuture = (Future)getAndSet(null);
    if (localFuture != null) {
      localFuture.cancel(this.allowInterrupt);
    }
  }
  
  public boolean isDisposed()
  {
    Future localFuture = (Future)get();
    boolean bool;
    if ((localFuture != null) && (!localFuture.isDone())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
