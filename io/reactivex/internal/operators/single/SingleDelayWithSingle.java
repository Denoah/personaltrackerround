package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithSingle<T, U>
  extends Single<T>
{
  final SingleSource<U> other;
  final SingleSource<T> source;
  
  public SingleDelayWithSingle(SingleSource<T> paramSingleSource, SingleSource<U> paramSingleSource1)
  {
    this.source = paramSingleSource;
    this.other = paramSingleSource1;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.other.subscribe(new OtherObserver(paramSingleObserver, this.source));
  }
  
  static final class OtherObserver<T, U>
    extends AtomicReference<Disposable>
    implements SingleObserver<U>, Disposable
  {
    private static final long serialVersionUID = -8565274649390031272L;
    final SingleObserver<? super T> downstream;
    final SingleSource<T> source;
    
    OtherObserver(SingleObserver<? super T> paramSingleObserver, SingleSource<T> paramSingleSource)
    {
      this.downstream = paramSingleObserver;
      this.source = paramSingleSource;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(U paramU)
    {
      this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
    }
  }
}
