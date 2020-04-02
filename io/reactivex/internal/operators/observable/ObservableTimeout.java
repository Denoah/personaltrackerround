package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeout<T, U, V>
  extends AbstractObservableWithUpstream<T, T>
{
  final ObservableSource<U> firstTimeoutIndicator;
  final Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator;
  final ObservableSource<? extends T> other;
  
  public ObservableTimeout(Observable<T> paramObservable, ObservableSource<U> paramObservableSource, Function<? super T, ? extends ObservableSource<V>> paramFunction, ObservableSource<? extends T> paramObservableSource1)
  {
    super(paramObservable);
    this.firstTimeoutIndicator = paramObservableSource;
    this.itemTimeoutIndicator = paramFunction;
    this.other = paramObservableSource1;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject;
    if (this.other == null)
    {
      localObject = new TimeoutObserver(paramObserver, this.itemTimeoutIndicator);
      paramObserver.onSubscribe((Disposable)localObject);
      ((TimeoutObserver)localObject).startFirstTimeout(this.firstTimeoutIndicator);
      this.source.subscribe((Observer)localObject);
    }
    else
    {
      localObject = new TimeoutFallbackObserver(paramObserver, this.itemTimeoutIndicator, this.other);
      paramObserver.onSubscribe((Disposable)localObject);
      ((TimeoutFallbackObserver)localObject).startFirstTimeout(this.firstTimeoutIndicator);
      this.source.subscribe((Observer)localObject);
    }
  }
  
  static final class TimeoutConsumer
    extends AtomicReference<Disposable>
    implements Observer<Object>, Disposable
  {
    private static final long serialVersionUID = 8708641127342403073L;
    final long idx;
    final ObservableTimeout.TimeoutSelectorSupport parent;
    
    TimeoutConsumer(long paramLong, ObservableTimeout.TimeoutSelectorSupport paramTimeoutSelectorSupport)
    {
      this.idx = paramLong;
      this.parent = paramTimeoutSelectorSupport;
    }
    
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
      if (get() != DisposableHelper.DISPOSED)
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.onTimeout(this.idx);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get() != DisposableHelper.DISPOSED)
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.onTimeoutError(this.idx, paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(Object paramObject)
    {
      paramObject = (Disposable)get();
      if (paramObject != DisposableHelper.DISPOSED)
      {
        paramObject.dispose();
        lazySet(DisposableHelper.DISPOSED);
        this.parent.onTimeout(this.idx);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
  
  static final class TimeoutFallbackObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Disposable, ObservableTimeout.TimeoutSelectorSupport
  {
    private static final long serialVersionUID = -7508389464265974549L;
    final Observer<? super T> downstream;
    ObservableSource<? extends T> fallback;
    final AtomicLong index;
    final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
    final SequentialDisposable task;
    final AtomicReference<Disposable> upstream;
    
    TimeoutFallbackObserver(Observer<? super T> paramObserver, Function<? super T, ? extends ObservableSource<?>> paramFunction, ObservableSource<? extends T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.itemTimeoutIndicator = paramFunction;
      this.task = new SequentialDisposable();
      this.fallback = paramObservableSource;
      this.index = new AtomicLong();
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this);
      this.task.dispose();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
        this.task.dispose();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
        this.task.dispose();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = this.index.get();
      if (l1 != Long.MAX_VALUE)
      {
        Object localObject = this.index;
        long l2 = 1L + l1;
        if (((AtomicLong)localObject).compareAndSet(l1, l2))
        {
          localObject = (Disposable)this.task.get();
          if (localObject != null) {
            ((Disposable)localObject).dispose();
          }
          this.downstream.onNext(paramT);
          try
          {
            localObject = (ObservableSource)ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(paramT), "The itemTimeoutIndicator returned a null ObservableSource.");
            paramT = new ObservableTimeout.TimeoutConsumer(l2, this);
            if (this.task.replace(paramT)) {
              ((ObservableSource)localObject).subscribe(paramT);
            }
            return;
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            ((Disposable)this.upstream.get()).dispose();
            this.index.getAndSet(Long.MAX_VALUE);
            this.downstream.onError(paramT);
          }
        }
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    public void onTimeout(long paramLong)
    {
      if (this.index.compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this.upstream);
        ObservableSource localObservableSource = this.fallback;
        this.fallback = null;
        localObservableSource.subscribe(new ObservableTimeoutTimed.FallbackObserver(this.downstream, this));
      }
    }
    
    public void onTimeoutError(long paramLong, Throwable paramThrowable)
    {
      if (this.index.compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void startFirstTimeout(ObservableSource<?> paramObservableSource)
    {
      if (paramObservableSource != null)
      {
        ObservableTimeout.TimeoutConsumer localTimeoutConsumer = new ObservableTimeout.TimeoutConsumer(0L, this);
        if (this.task.replace(localTimeoutConsumer)) {
          paramObservableSource.subscribe(localTimeoutConsumer);
        }
      }
    }
  }
  
  static final class TimeoutObserver<T>
    extends AtomicLong
    implements Observer<T>, Disposable, ObservableTimeout.TimeoutSelectorSupport
  {
    private static final long serialVersionUID = 3764492702657003550L;
    final Observer<? super T> downstream;
    final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
    final SequentialDisposable task;
    final AtomicReference<Disposable> upstream;
    
    TimeoutObserver(Observer<? super T> paramObserver, Function<? super T, ? extends ObservableSource<?>> paramFunction)
    {
      this.downstream = paramObserver;
      this.itemTimeoutIndicator = paramFunction;
      this.task = new SequentialDisposable();
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      this.task.dispose();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE)
      {
        this.task.dispose();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l1 = get();
      if (l1 != Long.MAX_VALUE)
      {
        long l2 = 1L + l1;
        if (compareAndSet(l1, l2))
        {
          Object localObject = (Disposable)this.task.get();
          if (localObject != null) {
            ((Disposable)localObject).dispose();
          }
          this.downstream.onNext(paramT);
          try
          {
            paramT = (ObservableSource)ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(paramT), "The itemTimeoutIndicator returned a null ObservableSource.");
            localObject = new ObservableTimeout.TimeoutConsumer(l2, this);
            if (this.task.replace((Disposable)localObject)) {
              paramT.subscribe((Observer)localObject);
            }
            return;
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            ((Disposable)this.upstream.get()).dispose();
            getAndSet(Long.MAX_VALUE);
            this.downstream.onError(paramT);
          }
        }
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    public void onTimeout(long paramLong)
    {
      if (compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this.upstream);
        this.downstream.onError(new TimeoutException());
      }
    }
    
    public void onTimeoutError(long paramLong, Throwable paramThrowable)
    {
      if (compareAndSet(paramLong, Long.MAX_VALUE))
      {
        DisposableHelper.dispose(this.upstream);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void startFirstTimeout(ObservableSource<?> paramObservableSource)
    {
      if (paramObservableSource != null)
      {
        ObservableTimeout.TimeoutConsumer localTimeoutConsumer = new ObservableTimeout.TimeoutConsumer(0L, this);
        if (this.task.replace(localTimeoutConsumer)) {
          paramObservableSource.subscribe(localTimeoutConsumer);
        }
      }
    }
  }
  
  static abstract interface TimeoutSelectorSupport
    extends ObservableTimeoutTimed.TimeoutSupport
  {
    public abstract void onTimeoutError(long paramLong, Throwable paramThrowable);
  }
}
