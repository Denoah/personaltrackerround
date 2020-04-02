package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDematerialize<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final Function<? super T, ? extends Notification<R>> selector;
  
  public ObservableDematerialize(ObservableSource<T> paramObservableSource, Function<? super T, ? extends Notification<R>> paramFunction)
  {
    super(paramObservableSource);
    this.selector = paramFunction;
  }
  
  public void subscribeActual(Observer<? super R> paramObserver)
  {
    this.source.subscribe(new DematerializeObserver(paramObserver, this.selector));
  }
  
  static final class DematerializeObserver<T, R>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super R> downstream;
    final Function<? super T, ? extends Notification<R>> selector;
    Disposable upstream;
    
    DematerializeObserver(Observer<? super R> paramObserver, Function<? super T, ? extends Notification<R>> paramFunction)
    {
      this.downstream = paramObserver;
      this.selector = paramFunction;
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
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
      if (this.done)
      {
        if ((paramT instanceof Notification))
        {
          paramT = (Notification)paramT;
          if (paramT.isOnError()) {
            RxJavaPlugins.onError(paramT.getError());
          }
        }
        return;
      }
      try
      {
        paramT = (Notification)ObjectHelper.requireNonNull(this.selector.apply(paramT), "The selector returned a null Notification");
        if (paramT.isOnError())
        {
          this.upstream.dispose();
          onError(paramT.getError());
        }
        else if (paramT.isOnComplete())
        {
          this.upstream.dispose();
          onComplete();
        }
        else
        {
          this.downstream.onNext(paramT.getValue());
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
  }
}
