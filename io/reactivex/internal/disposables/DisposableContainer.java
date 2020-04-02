package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

public abstract interface DisposableContainer
{
  public abstract boolean add(Disposable paramDisposable);
  
  public abstract boolean delete(Disposable paramDisposable);
  
  public abstract boolean remove(Disposable paramDisposable);
}
