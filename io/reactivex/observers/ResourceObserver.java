package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ResourceObserver<T>
  implements Observer<T>, Disposable
{
  private final ListCompositeDisposable resources = new ListCompositeDisposable();
  private final AtomicReference<Disposable> upstream = new AtomicReference();
  
  public ResourceObserver() {}
  
  public final void add(Disposable paramDisposable)
  {
    ObjectHelper.requireNonNull(paramDisposable, "resource is null");
    this.resources.add(paramDisposable);
  }
  
  public final void dispose()
  {
    if (DisposableHelper.dispose(this.upstream)) {
      this.resources.dispose();
    }
  }
  
  public final boolean isDisposed()
  {
    return DisposableHelper.isDisposed((Disposable)this.upstream.get());
  }
  
  protected void onStart() {}
  
  public final void onSubscribe(Disposable paramDisposable)
  {
    if (EndConsumerHelper.setOnce(this.upstream, paramDisposable, getClass())) {
      onStart();
    }
  }
}
