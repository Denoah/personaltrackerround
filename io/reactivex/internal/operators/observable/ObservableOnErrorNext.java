package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableOnErrorNext<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final boolean allowFatal;
  final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;
  
  public ObservableOnErrorNext(ObservableSource<T> paramObservableSource, Function<? super Throwable, ? extends ObservableSource<? extends T>> paramFunction, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.nextSupplier = paramFunction;
    this.allowFatal = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    OnErrorNextObserver localOnErrorNextObserver = new OnErrorNextObserver(paramObserver, this.nextSupplier, this.allowFatal);
    paramObserver.onSubscribe(localOnErrorNextObserver.arbiter);
    this.source.subscribe(localOnErrorNextObserver);
  }
  
  static final class OnErrorNextObserver<T>
    implements Observer<T>
  {
    final boolean allowFatal;
    final SequentialDisposable arbiter;
    boolean done;
    final Observer<? super T> downstream;
    final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;
    boolean once;
    
    OnErrorNextObserver(Observer<? super T> paramObserver, Function<? super Throwable, ? extends ObservableSource<? extends T>> paramFunction, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.nextSupplier = paramFunction;
      this.allowFatal = paramBoolean;
      this.arbiter = new SequentialDisposable();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.once = true;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once)
      {
        if (this.done)
        {
          RxJavaPlugins.onError(paramThrowable);
          return;
        }
        this.downstream.onError(paramThrowable);
        return;
      }
      this.once = true;
      if ((this.allowFatal) && (!(paramThrowable instanceof Exception)))
      {
        this.downstream.onError(paramThrowable);
        return;
      }
      try
      {
        Object localObject = (ObservableSource)this.nextSupplier.apply(paramThrowable);
        if (localObject == null)
        {
          localObject = new NullPointerException("Observable is null");
          ((NullPointerException)localObject).initCause(paramThrowable);
          this.downstream.onError((Throwable)localObject);
          return;
        }
        ((ObservableSource)localObject).subscribe(this);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.arbiter.replace(paramDisposable);
    }
  }
}
