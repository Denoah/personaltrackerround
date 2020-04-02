package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRetryPredicate<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final long count;
  final Predicate<? super Throwable> predicate;
  
  public ObservableRetryPredicate(Observable<T> paramObservable, long paramLong, Predicate<? super Throwable> paramPredicate)
  {
    super(paramObservable);
    this.predicate = paramPredicate;
    this.count = paramLong;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramObserver.onSubscribe(localSequentialDisposable);
    new RepeatObserver(paramObserver, this.count, this.predicate, localSequentialDisposable, this.source).subscribeNext();
  }
  
  static final class RepeatObserver<T>
    extends AtomicInteger
    implements Observer<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Observer<? super T> downstream;
    final Predicate<? super Throwable> predicate;
    long remaining;
    final ObservableSource<? extends T> source;
    final SequentialDisposable upstream;
    
    RepeatObserver(Observer<? super T> paramObserver, long paramLong, Predicate<? super Throwable> paramPredicate, SequentialDisposable paramSequentialDisposable, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.upstream = paramSequentialDisposable;
      this.source = paramObservableSource;
      this.predicate = paramPredicate;
      this.remaining = paramLong;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      long l = this.remaining;
      if (l != Long.MAX_VALUE) {
        this.remaining = (l - 1L);
      }
      if (l == 0L) {
        this.downstream.onError(paramThrowable);
      }
      try
      {
        boolean bool = this.predicate.test(paramThrowable);
        if (!bool)
        {
          this.downstream.onError(paramThrowable);
          return;
        }
        subscribeNext();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.upstream.replace(paramDisposable);
    }
    
    void subscribeNext()
    {
      if (getAndIncrement() == 0)
      {
        int i = 1;
        int j;
        do
        {
          if (this.upstream.isDisposed()) {
            return;
          }
          this.source.subscribe(this);
          j = addAndGet(-i);
          i = j;
        } while (j != 0);
      }
    }
  }
}
