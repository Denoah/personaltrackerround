package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableCountSingle<T>
  extends Single<Long>
  implements FuseToObservable<Long>
{
  final ObservableSource<T> source;
  
  public ObservableCountSingle(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  public Observable<Long> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableCount(this.source));
  }
  
  public void subscribeActual(SingleObserver<? super Long> paramSingleObserver)
  {
    this.source.subscribe(new CountObserver(paramSingleObserver));
  }
  
  static final class CountObserver
    implements Observer<Object>, Disposable
  {
    long count;
    final SingleObserver<? super Long> downstream;
    Disposable upstream;
    
    CountObserver(SingleObserver<? super Long> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onSuccess(Long.valueOf(this.count));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.count += 1L;
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
