package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRetryBiPredicate<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final BiPredicate<? super Integer, ? super Throwable> predicate;
  
  public ObservableRetryBiPredicate(Observable<T> paramObservable, BiPredicate<? super Integer, ? super Throwable> paramBiPredicate)
  {
    super(paramObservable);
    this.predicate = paramBiPredicate;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramObserver.onSubscribe(localSequentialDisposable);
    new RetryBiObserver(paramObserver, this.predicate, localSequentialDisposable, this.source).subscribeNext();
  }
  
  static final class RetryBiObserver<T>
    extends AtomicInteger
    implements Observer<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Observer<? super T> downstream;
    final BiPredicate<? super Integer, ? super Throwable> predicate;
    int retries;
    final ObservableSource<? extends T> source;
    final SequentialDisposable upstream;
    
    RetryBiObserver(Observer<? super T> paramObserver, BiPredicate<? super Integer, ? super Throwable> paramBiPredicate, SequentialDisposable paramSequentialDisposable, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.upstream = paramSequentialDisposable;
      this.source = paramObservableSource;
      this.predicate = paramBiPredicate;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        BiPredicate localBiPredicate = this.predicate;
        int i = this.retries + 1;
        this.retries = i;
        boolean bool = localBiPredicate.test(Integer.valueOf(i), paramThrowable);
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
