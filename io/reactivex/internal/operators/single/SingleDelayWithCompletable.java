package io.reactivex.internal.operators.single;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithCompletable<T>
  extends Single<T>
{
  final CompletableSource other;
  final SingleSource<T> source;
  
  public SingleDelayWithCompletable(SingleSource<T> paramSingleSource, CompletableSource paramCompletableSource)
  {
    this.source = paramSingleSource;
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.other.subscribe(new OtherObserver(paramSingleObserver, this.source));
  }
  
  static final class OtherObserver<T>
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable
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
    
    public void onComplete()
    {
      this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
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
  }
}
