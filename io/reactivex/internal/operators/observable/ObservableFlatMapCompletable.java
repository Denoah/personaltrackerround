package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapCompletable<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final boolean delayErrors;
  final Function<? super T, ? extends CompletableSource> mapper;
  
  public ObservableFlatMapCompletable(ObservableSource<T> paramObservableSource, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new FlatMapCompletableMainObserver(paramObserver, this.mapper, this.delayErrors));
  }
  
  static final class FlatMapCompletableMainObserver<T>
    extends BasicIntQueueDisposable<T>
    implements Observer<T>
  {
    private static final long serialVersionUID = 8443155186132538303L;
    final boolean delayErrors;
    volatile boolean disposed;
    final Observer<? super T> downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final CompositeDisposable set;
    Disposable upstream;
    
    FlatMapCompletableMainObserver(Observer<? super T> paramObserver, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.errors = new AtomicThrowable();
      this.set = new CompositeDisposable();
      lazySet(1);
    }
    
    public void clear() {}
    
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
    
    public boolean isEmpty()
    {
      return true;
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
        paramT = (CompletableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null CompletableSource");
        getAndIncrement();
        InnerObserver localInnerObserver = new InnerObserver();
        if ((!this.disposed) && (this.set.add(localInnerObserver))) {
          paramT.subscribe(localInnerObserver);
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
    
    public T poll()
      throws Exception
    {
      return null;
    }
    
    public int requestFusion(int paramInt)
    {
      return paramInt & 0x2;
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
        ObservableFlatMapCompletable.FlatMapCompletableMainObserver.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableFlatMapCompletable.FlatMapCompletableMainObserver.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
