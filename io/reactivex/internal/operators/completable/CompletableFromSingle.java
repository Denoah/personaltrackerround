package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;

public final class CompletableFromSingle<T>
  extends Completable
{
  final SingleSource<T> single;
  
  public CompletableFromSingle(SingleSource<T> paramSingleSource)
  {
    this.single = paramSingleSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.single.subscribe(new CompletableFromSingleObserver(paramCompletableObserver));
  }
  
  static final class CompletableFromSingleObserver<T>
    implements SingleObserver<T>
  {
    final CompletableObserver co;
    
    CompletableFromSingleObserver(CompletableObserver paramCompletableObserver)
    {
      this.co = paramCompletableObserver;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.co.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.co.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.co.onComplete();
    }
  }
}
