package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;

public final class CompletableOnErrorComplete
  extends Completable
{
  final Predicate<? super Throwable> predicate;
  final CompletableSource source;
  
  public CompletableOnErrorComplete(CompletableSource paramCompletableSource, Predicate<? super Throwable> paramPredicate)
  {
    this.source = paramCompletableSource;
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new OnError(paramCompletableObserver));
  }
  
  final class OnError
    implements CompletableObserver
  {
    private final CompletableObserver downstream;
    
    OnError(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        boolean bool = CompletableOnErrorComplete.this.predicate.test(paramThrowable);
        if (bool) {
          this.downstream.onComplete();
        } else {
          this.downstream.onError(paramThrowable);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.downstream.onSubscribe(paramDisposable);
    }
  }
}
