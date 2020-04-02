package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

public abstract interface ResettableConnectable
{
  public abstract void resetIf(Disposable paramDisposable);
}
