package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.ArrayDeque;

public final class ObservableSkipLast<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final int skip;
  
  public ObservableSkipLast(ObservableSource<T> paramObservableSource, int paramInt)
  {
    super(paramObservableSource);
    this.skip = paramInt;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new SkipLastObserver(paramObserver, this.skip));
  }
  
  static final class SkipLastObserver<T>
    extends ArrayDeque<T>
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -3807491841935125653L;
    final Observer<? super T> downstream;
    final int skip;
    Disposable upstream;
    
    SkipLastObserver(Observer<? super T> paramObserver, int paramInt)
    {
      super();
      this.downstream = paramObserver;
      this.skip = paramInt;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.skip == size()) {
        this.downstream.onNext(poll());
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
