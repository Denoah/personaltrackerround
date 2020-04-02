package io.reactivex.disposables;

public abstract interface Disposable
{
  public abstract void dispose();
  
  public abstract boolean isDisposed();
}
