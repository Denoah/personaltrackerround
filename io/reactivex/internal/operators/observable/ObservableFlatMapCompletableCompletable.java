package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapCompletableCompletable<T>
  extends Completable
  implements FuseToObservable<T>
{
  final boolean delayErrors;
  final Function<? super T, ? extends CompletableSource> mapper;
  final ObservableSource<T> source;
  
  public ObservableFlatMapCompletableCompletable(ObservableSource<T> paramObservableSource, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
  {
    this.source = paramObservableSource;
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
  }
  
  public Observable<T> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletable(this.source, this.mapper, this.delayErrors));
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new FlatMapCompletableMainObserver(paramCompletableObserver, this.mapper, this.delayErrors));
  }
  
  static final class FlatMapCompletableMainObserver<T>
    extends AtomicInteger
    implements Disposable, Observer<T>
  {
    private static final long serialVersionUID = 8443155186132538303L;
    final boolean delayErrors;
    volatile boolean disposed;
    final CompletableObserver downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final CompositeDisposable set;
    Disposable upstream;
    
    FlatMapCompletableMainObserver(CompletableObserver paramCompletableObserver, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
    {
      this.downstream = paramCompletableObserver;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.errors = new AtomicThrowable();
      this.set = new CompositeDisposable();
      lazySet(1);
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.upstream.dispose();
      this.set.dispose();
    }
    
    void innerComplete(FlatMapCompletableMainObserver<T>.InnerObserver paramFlatMapCompletableMainObserver)
    {
      this.set.delete(paramFlatMapCompletableMainObserver);
      onComplete();
    }
    
    void innerError(FlatMapCompletableMainObserver<T>.InnerObserver paramFlatMapCompletableMainObserver, Throwable paramThrowable)
    {
      this.set.delete(paramFlatMapCompletableMainObserver);
      onError(paramThrowable);
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (decrementAndGet() == 0)
      {
        Throwable localThrowable = this.errors.terminate();
        if (localThrowable != null) {
          this.downstream.onError(localThrowable);
        } else {
          this.downstream.onComplete();
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        if (this.delayErrors)
        {
          if (decrementAndGet() == 0)
          {
            paramThrowable = this.errors.terminate();
            this.downstream.onError(paramThrowable);
          }
        }
        else
        {
          dispose();
          if (getAndSet(0) > 0)
          {
            paramThrowable = this.errors.terminate();
            this.downstream.onError(paramThrowable);
          }
        }
      }
      else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        CompletableSource localCompletableSource = (CompletableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null CompletableSource");
        getAndIncrement();
        paramT = new InnerObserver();
        if ((!this.disposed) && (this.set.add(paramT))) {
          localCompletableSource.subscribe(paramT);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    final class InnerObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver, Disposable
    {
      private static final long serialVersionUID = 8606673141535671828L;
      
      InnerObserver() {}
      
      public void dispose()
      {
        DisposableHelper.dispose(this);
      }
      
      public boolean isDisposed()
      {
        return DisposableHelper.isDisposed((Disposable)get());
      }
      
      public void onComplete()
      {
        ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
