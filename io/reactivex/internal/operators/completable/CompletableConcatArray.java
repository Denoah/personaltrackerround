package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableConcatArray
  extends Completable
{
  final CompletableSource[] sources;
  
  public CompletableConcatArray(CompletableSource[] paramArrayOfCompletableSource)
  {
    this.sources = paramArrayOfCompletableSource;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    ConcatInnerObserver localConcatInnerObserver = new ConcatInnerObserver(paramCompletableObserver, this.sources);
    paramCompletableObserver.onSubscribe(localConcatInnerObserver.sd);
    localConcatInnerObserver.next();
  }
  
  static final class ConcatInnerObserver
    extends AtomicInteger
    implements CompletableObserver
  {
    private static final long serialVersionUID = -7965400327305809232L;
    final CompletableObserver downstream;
    int index;
    final SequentialDisposable sd;
    final CompletableSource[] sources;
    
    ConcatInnerObserver(CompletableObserver paramCompletableObserver, CompletableSource[] paramArrayOfCompletableSource)
    {
      this.downstream = paramCompletableObserver;
      this.sources = paramArrayOfCompletableSource;
      this.sd = new SequentialDisposable();
    }
    
    void next()
    {
      if (this.sd.isDisposed()) {
        return;
      }
      if (getAndIncrement() != 0) {
        return;
      }
      CompletableSource[] arrayOfCompletableSource = this.sources;
      do
      {
        if (this.sd.isDisposed()) {
          return;
        }
        int i = this.index;
        this.index = (i + 1);
        if (i == arrayOfCompletableSource.length)
        {
          this.downstream.onComplete();
          return;
        }
        arrayOfCompletableSource[i].subscribe(this);
      } while (decrementAndGet() != 0);
    }
    
    public void onComplete()
    {
      next();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.sd.replace(paramDisposable);
    }
  }
}
