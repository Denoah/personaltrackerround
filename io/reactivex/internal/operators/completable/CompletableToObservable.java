package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class CompletableToObservable<T>
  extends Observable<T>
{
  final CompletableSource source;
  
  public CompletableToObservable(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ObserverCompletableObserver(paramObserver));
  }
  
  static final class ObserverCompletableObserver
    extends BasicQueueDisposable<Void>
    implements CompletableObserver
  {
    final Observer<?> observer;
    Disposable upstream;
    
    ObserverCompletableObserver(Observer<?> paramObserver)
    {
      this.observer = paramObserver;
    }
    
    public void clear() {}
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public boolean isEmpty()
    {
      return true;
    }
    
    public void onComplete()
    {
      this.observer.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.observer.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.observer.onSubscribe(this);
      }
    }
    
    public Void poll()
      throws Exception
    {
      return null;
    }
    
    public int requestFusion(int paramInt)
    {
      return paramInt & 0x2;
    }
  }
}
