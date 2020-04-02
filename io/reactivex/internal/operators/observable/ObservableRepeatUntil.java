package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRepeatUntil<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final BooleanSupplier until;
  
  public ObservableRepeatUntil(Observable<T> paramObservable, BooleanSupplier paramBooleanSupplier)
  {
    super(paramObservable);
    this.until = paramBooleanSupplier;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramObserver.onSubscribe(localSequentialDisposable);
    new RepeatUntilObserver(paramObserver, this.until, localSequentialDisposable, this.source).subscribeNext();
  }
  
  static final class RepeatUntilObserver<T>
    extends AtomicInteger
    implements Observer<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Observer<? super T> downstream;
    final ObservableSource<? extends T> source;
    final BooleanSupplier stop;
    final SequentialDisposable upstream;
    
    RepeatUntilObserver(Observer<? super T> paramObserver, BooleanSupplier paramBooleanSupplier, SequentialDisposable paramSequentialDisposable, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.upstream = paramSequentialDisposable;
      this.source = paramObservableSource;
      this.stop = paramBooleanSupplier;
    }
    
    public void onComplete()
    {
      try
      {
        boolean bool = this.stop.getAsBoolean();
        if (bool) {
          this.downstream.onComplete();
        } else {
          subscribeNext();
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
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
          this.source.subscribe(this);
          j = addAndGet(-i);
          i = j;
        } while (j != 0);
      }
    }
  }
}
