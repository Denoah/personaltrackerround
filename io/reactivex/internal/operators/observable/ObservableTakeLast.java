package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.ArrayDeque;

public final class ObservableTakeLast<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final int count;
  
  public ObservableTakeLast(ObservableSource<T> paramObservableSource, int paramInt)
  {
    super(paramObservableSource);
    this.count = paramInt;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeLastObserver(paramObserver, this.count));
  }
  
  static final class TakeLastObserver<T>
    extends ArrayDeque<T>
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 7240042530241604978L;
    volatile boolean cancelled;
    final int count;
    final Observer<? super T> downstream;
    Disposable upstream;
    
    TakeLastObserver(Observer<? super T> paramObserver, int paramInt)
    {
      this.downstream = paramObserver;
      this.count = paramInt;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      Observer localObserver = this.downstream;
      for (;;)
      {
        if (this.cancelled) {
          return;
        }
        Object localObject = poll();
        if (localObject == null)
        {
          if (!this.cancelled) {
            localObserver.onComplete();
          }
          return;
        }
        localObserver.onNext(localObject);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.count == size()) {
        poll();
      }
      offer(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
