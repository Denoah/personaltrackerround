package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRepeat<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final long count;
  
  public ObservableRepeat(Observable<T> paramObservable, long paramLong)
  {
    super(paramObservable);
    this.count = paramLong;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramObserver.onSubscribe(localSequentialDisposable);
    long l1 = this.count;
    long l2 = Long.MAX_VALUE;
    if (l1 != Long.MAX_VALUE) {
      l2 = l1 - 1L;
    }
    new RepeatObserver(paramObserver, l2, localSequentialDisposable, this.source).subscribeNext();
  }
  
  static final class RepeatObserver<T>
    extends AtomicInteger
    implements Observer<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Observer<? super T> downstream;
    long remaining;
    final SequentialDisposable sd;
    final ObservableSource<? extends T> source;
    
    RepeatObserver(Observer<? super T> paramObserver, long paramLong, SequentialDisposable paramSequentialDisposable, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.sd = paramSequentialDisposable;
      this.source = paramObservableSource;
      this.remaining = paramLong;
    }
    
    public void onComplete()
    {
      long l = this.remaining;
      if (l != Long.MAX_VALUE) {
        this.remaining = (l - 1L);
      }
      if (l != 0L) {
        subscribeNext();
      } else {
        this.downstream.onComplete();
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
      this.sd.replace(paramDisposable);
    }
    
    void subscribeNext()
    {
      if (getAndIncrement() == 0)
      {
        int i = 1;
        int j;
        do
        {
          if (this.sd.isDisposed()) {
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
