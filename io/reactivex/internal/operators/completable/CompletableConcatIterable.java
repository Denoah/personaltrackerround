package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableConcatIterable
  extends Completable
{
  final Iterable<? extends CompletableSource> sources;
  
  public CompletableConcatIterable(Iterable<? extends CompletableSource> paramIterable)
  {
    this.sources = paramIterable;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    try
    {
      Object localObject = (Iterator)ObjectHelper.requireNonNull(this.sources.iterator(), "The iterator returned is null");
      localObject = new ConcatInnerObserver(paramCompletableObserver, (Iterator)localObject);
      paramCompletableObserver.onSubscribe(((ConcatInnerObserver)localObject).sd);
      ((ConcatInnerObserver)localObject).next();
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramCompletableObserver);
    }
  }
  
  static final class ConcatInnerObserver
    extends AtomicInteger
    implements CompletableObserver
  {
    private static final long serialVersionUID = -7965400327305809232L;
    final CompletableObserver downstream;
    final SequentialDisposable sd;
    final Iterator<? extends CompletableSource> sources;
    
    ConcatInnerObserver(CompletableObserver paramCompletableObserver, Iterator<? extends CompletableSource> paramIterator)
    {
      this.downstream = paramCompletableObserver;
      this.sources = paramIterator;
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
      Iterator localIterator = this.sources;
      for (;;)
      {
        if (this.sd.isDisposed()) {
          return;
        }
        try
        {
          boolean bool = localIterator.hasNext();
          if (!bool)
          {
            this.downstream.onComplete();
            return;
          }
          try
          {
            CompletableSource localCompletableSource = (CompletableSource)ObjectHelper.requireNonNull(localIterator.next(), "The CompletableSource returned is null");
            localCompletableSource.subscribe(this);
            if (decrementAndGet() != 0) {
              continue;
            }
            return;
          }
          finally {}
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable2);
          this.downstream.onError(localThrowable2);
        }
      }
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
