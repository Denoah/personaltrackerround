package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableAndThenCompletable
  extends Completable
{
  final CompletableSource next;
  final CompletableSource source;
  
  public CompletableAndThenCompletable(CompletableSource paramCompletableSource1, CompletableSource paramCompletableSource2)
  {
    this.source = paramCompletableSource1;
    this.next = paramCompletableSource2;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new SourceObserver(paramCompletableObserver, this.next));
  }
  
  static final class NextObserver
    implements CompletableObserver
  {
    final CompletableObserver downstream;
    final AtomicReference<Disposable> parent;
    
    public NextObserver(AtomicReference<Disposable> paramAtomicReference, CompletableObserver paramCompletableObserver)
    {
      this.parent = paramAtomicReference;
      this.downstream = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.replace(this.parent, paramDisposable);
    }
  }
  
  static final class SourceObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable
  {
    private static final long serialVersionUID = -4101678820158072998L;
    final CompletableObserver actualObserver;
    final CompletableSource next;
    
    SourceObserver(CompletableObserver paramCompletableObserver, CompletableSource paramCompletableSource)
    {
      this.actualObserver = paramCompletableObserver;
      this.next = paramCompletableSource;
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
      this.next.subscribe(new CompletableAndThenCompletable.NextObserver(this, this.actualObserver));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.actualObserver.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.actualObserver.onSubscribe(this);
      }
    }
  }
}
