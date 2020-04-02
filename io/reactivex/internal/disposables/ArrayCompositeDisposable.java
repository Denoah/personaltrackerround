package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ArrayCompositeDisposable
  extends AtomicReferenceArray<Disposable>
  implements Disposable
{
  private static final long serialVersionUID = 2746389416410565408L;
  
  public ArrayCompositeDisposable(int paramInt)
  {
    super(paramInt);
  }
  
  public void dispose()
  {
    int i = 0;
    if (get(0) != DisposableHelper.DISPOSED)
    {
      int j = length();
      while (i < j)
      {
        if ((Disposable)get(i) != DisposableHelper.DISPOSED)
        {
          Disposable localDisposable = (Disposable)getAndSet(i, DisposableHelper.DISPOSED);
          if ((localDisposable != DisposableHelper.DISPOSED) && (localDisposable != null)) {
            localDisposable.dispose();
          }
        }
        i++;
      }
    }
  }
  
  public boolean isDisposed()
  {
    boolean bool = false;
    if (get(0) == DisposableHelper.DISPOSED) {
      bool = true;
    }
    return bool;
  }
  
  public Disposable replaceResource(int paramInt, Disposable paramDisposable)
  {
    Disposable localDisposable;
    do
    {
      localDisposable = (Disposable)get(paramInt);
      if (localDisposable == DisposableHelper.DISPOSED)
      {
        paramDisposable.dispose();
        return null;
      }
    } while (!compareAndSet(paramInt, localDisposable, paramDisposable));
    return localDisposable;
  }
  
  public boolean setResource(int paramInt, Disposable paramDisposable)
  {
    Disposable localDisposable;
    do
    {
      localDisposable = (Disposable)get(paramInt);
      if (localDisposable == DisposableHelper.DISPOSED)
      {
        paramDisposable.dispose();
        return false;
      }
    } while (!compareAndSet(paramInt, localDisposable, paramDisposable));
    if (localDisposable != null) {
      localDisposable.dispose();
    }
    return true;
  }
}
