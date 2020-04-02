package io.reactivex.internal.operators.single;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleDematerialize<T, R>
  extends Maybe<R>
{
  final Function<? super T, Notification<R>> selector;
  final Single<T> source;
  
  public SingleDematerialize(Single<T> paramSingle, Function<? super T, Notification<R>> paramFunction)
  {
    this.source = paramSingle;
    this.selector = paramFunction;
  }
  
  protected void subscribeActual(MaybeObserver<? super R> paramMaybeObserver)
  {
    this.source.subscribe(new DematerializeObserver(paramMaybeObserver, this.selector));
  }
  
  static final class DematerializeObserver<T, R>
    implements SingleObserver<T>, Disposable
  {
    final MaybeObserver<? super R> downstream;
    final Function<? super T, Notification<R>> selector;
    Disposable upstream;
    
    DematerializeObserver(MaybeObserver<? super R> paramMaybeObserver, Function<? super T, Notification<R>> paramFunction)
    {
      this.downstream = paramMaybeObserver;
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
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        paramT = (Notification)ObjectHelper.requireNonNull(this.selector.apply(paramT), "The selector returned a null Notification");
        if (paramT.isOnNext()) {
          this.downstream.onSuccess(paramT.getValue());
        } else if (paramT.isOnComplete()) {
          this.downstream.onComplete();
        } else {
          this.downstream.onError(paramT.getError());
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
  }
}
