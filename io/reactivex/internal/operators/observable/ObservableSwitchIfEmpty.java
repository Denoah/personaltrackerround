package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;

public final class ObservableSwitchIfEmpty<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final ObservableSource<? extends T> other;
  
  public ObservableSwitchIfEmpty(ObservableSource<T> paramObservableSource, ObservableSource<? extends T> paramObservableSource1)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SwitchIfEmptyObserver localSwitchIfEmptyObserver = new SwitchIfEmptyObserver(paramObserver, this.other);
    paramObserver.onSubscribe(localSwitchIfEmptyObserver.arbiter);
    this.source.subscribe(localSwitchIfEmptyObserver);
  }
  
  static final class SwitchIfEmptyObserver<T>
    implements Observer<T>
  {
    final SequentialDisposable arbiter;
    final Observer<? super T> downstream;
    boolean empty;
    final ObservableSource<? extends T> other;
    
    SwitchIfEmptyObserver(Observer<? super T> paramObserver, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.other = paramObservableSource;
      this.empty = true;
      this.arbiter = new SequentialDisposable();
    }
    
    public void onComplete()
    {
      if (this.empty)
      {
        this.empty = false;
        this.other.subscribe(this);
      }
      else
      {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.empty) {
        this.empty = false;
      }
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.arbiter.update(paramDisposable);
    }
  }
}
