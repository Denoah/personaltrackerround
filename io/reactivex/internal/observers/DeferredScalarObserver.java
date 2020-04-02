package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public abstract class DeferredScalarObserver<T, R>
  extends DeferredScalarDisposable<R>
  implements Observer<T>
{
  private static final long serialVersionUID = -266195175408988651L;
  protected Disposable upstream;
  
  public DeferredScalarObserver(Observer<? super R> paramObserver)
  {
    super(paramObserver);
  }
  
  public void dispose()
  {
    super.dispose();
    this.upstream.dispose();
  }
  
  public void onComplete()
  {
    Object localObject = this.value;
    if (localObject != null)
    {
      this.value = null;
      complete(localObject);
    }
    else
    {
      complete();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.value = null;
    error(paramThrowable);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (DisposableHelper.validate(this.upstream, paramDisposable))
    {
      this.upstream = paramDisposable;
      this.downstream.onSubscribe(this);
    }
  }
}
