package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ResumeSingleObserver<T>
  implements SingleObserver<T>
{
  final SingleObserver<? super T> downstream;
  final AtomicReference<Disposable> parent;
  
  public ResumeSingleObserver(AtomicReference<Disposable> paramAtomicReference, SingleObserver<? super T> paramSingleObserver)
  {
    this.parent = paramAtomicReference;
    this.downstream = paramSingleObserver;
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.downstream.onError(paramThrowable);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.replace(this.parent, paramDisposable);
  }
  
  public void onSuccess(T paramT)
  {
    this.downstream.onSuccess(paramT);
  }
}
