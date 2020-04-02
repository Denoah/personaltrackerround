package io.reactivex.disposables;

import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SerialDisposable
  implements Disposable
{
  final AtomicReference<Disposable> resource;
  
  public SerialDisposable()
  {
    this.resource = new AtomicReference();
  }
  
  public SerialDisposable(Disposable paramDisposable)
  {
    this.resource = new AtomicReference(paramDisposable);
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this.resource);
  }
  
  public Disposable get()
  {
    Disposable localDisposable1 = (Disposable)this.resource.get();
    Disposable localDisposable2 = localDisposable1;
    if (localDisposable1 == DisposableHelper.DISPOSED) {
      localDisposable2 = Disposables.disposed();
    }
    return localDisposable2;
  }
  
  public boolean isDisposed()
  {
    return DisposableHelper.isDisposed((Disposable)this.resource.get());
  }
  
  public boolean replace(Disposable paramDisposable)
  {
    return DisposableHelper.replace(this.resource, paramDisposable);
  }
  
  public boolean set(Disposable paramDisposable)
  {
    return DisposableHelper.set(this.resource, paramDisposable);
  }
}
