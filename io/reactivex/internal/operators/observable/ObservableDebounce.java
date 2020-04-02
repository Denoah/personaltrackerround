package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableDebounce<T, U>
  extends AbstractObservableWithUpstream<T, T>
{
  final Function<? super T, ? extends ObservableSource<U>> debounceSelector;
  
  public ObservableDebounce(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<U>> paramFunction)
  {
    super(paramObservableSource);
    this.debounceSelector = paramFunction;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DebounceObserver(new SerializedObserver(paramObserver), this.debounceSelector));
  }
  
  static final class DebounceObserver<T, U>
    implements Observer<T>, Disposable
  {
    final Function<? super T, ? extends ObservableSource<U>> debounceSelector;
    final AtomicReference<Disposable> debouncer = new AtomicReference();
    boolean done;
    final Observer<? super T> downstream;
    volatile long index;
    Disposable upstream;
    
    DebounceObserver(Observer<? super T> paramObserver, Function<? super T, ? extends ObservableSource<U>> paramFunction)
    {
      this.downstream = paramObserver;
      this.debounceSelector = paramFunction;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      DisposableHelper.dispose(this.debouncer);
    }
    
    void emit(long paramLong, T paramT)
    {
      if (paramLong == this.index) {
        this.downstream.onNext(paramT);
      }
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Disposable localDisposable = (Disposable)this.debouncer.get();
      if (localDisposable != DisposableHelper.DISPOSED)
      {
        ((DebounceInnerObserver)localDisposable).emit();
        DisposableHelper.dispose(this.debouncer);
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.debouncer);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.index + 1L;
      this.index = l;
      Disposable localDisposable = (Disposable)this.debouncer.get();
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      try
      {
        ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.debounceSelector.apply(paramT), "The ObservableSource supplied is null");
        paramT = new DebounceInnerObserver(this, l, paramT);
        if (this.debouncer.compareAndSet(localDisposable, paramT)) {
          localObservableSource.subscribe(paramT);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        dispose();
        this.downstream.onError(paramT);
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
    
    static final class DebounceInnerObserver<T, U>
      extends DisposableObserver<U>
    {
      boolean done;
      final long index;
      final AtomicBoolean once = new AtomicBoolean();
      final ObservableDebounce.DebounceObserver<T, U> parent;
      final T value;
      
      DebounceInnerObserver(ObservableDebounce.DebounceObserver<T, U> paramDebounceObserver, long paramLong, T paramT)
      {
        this.parent = paramDebounceObserver;
        this.index = paramLong;
        this.value = paramT;
      }
      
      void emit()
      {
        if (this.once.compareAndSet(false, true)) {
          this.parent.emit(this.index, this.value);
        }
      }
      
      public void onComplete()
      {
        if (this.done) {
          return;
        }
        this.done = true;
        emit();
      }
      
      public void onError(Throwable paramThrowable)
      {
        if (this.done)
        {
          RxJavaPlugins.onError(paramThrowable);
          return;
        }
        this.done = true;
        this.parent.onError(paramThrowable);
      }
      
      public void onNext(U paramU)
      {
        if (this.done) {
          return;
        }
        this.done = true;
        dispose();
        emit();
      }
    }
  }
}
