package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableElementAtMaybe<T>
  extends Maybe<T>
  implements FuseToObservable<T>
{
  final long index;
  final ObservableSource<T> source;
  
  public ObservableElementAtMaybe(ObservableSource<T> paramObservableSource, long paramLong)
  {
    this.source = paramObservableSource;
    this.index = paramLong;
  }
  
  public Observable<T> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableElementAt(this.source, this.index, null, false));
  }
  
  public void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new ElementAtObserver(paramMaybeObserver, this.index));
  }
  
  static final class ElementAtObserver<T>
    implements Observer<T>, Disposable
  {
    long count;
    boolean done;
    final MaybeObserver<? super T> downstream;
    final long index;
    Disposable upstream;
    
    ElementAtObserver(MaybeObserver<? super T> paramMaybeObserver, long paramLong)
    {
      this.downstream = paramMaybeObserver;
      this.index = paramLong;
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
      if (!this.done)
      {
        this.done = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.count;
      if (l == this.index)
      {
        this.done = true;
        this.upstream.dispose();
        this.downstream.onSuccess(paramT);
        return;
      }
      this.count = (l + 1L);
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
