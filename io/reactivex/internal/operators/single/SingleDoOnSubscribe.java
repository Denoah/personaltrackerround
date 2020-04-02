package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleDoOnSubscribe<T>
  extends Single<T>
{
  final Consumer<? super Disposable> onSubscribe;
  final SingleSource<T> source;
  
  public SingleDoOnSubscribe(SingleSource<T> paramSingleSource, Consumer<? super Disposable> paramConsumer)
  {
    this.source = paramSingleSource;
    this.onSubscribe = paramConsumer;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DoOnSubscribeSingleObserver(paramSingleObserver, this.onSubscribe));
  }
  
  static final class DoOnSubscribeSingleObserver<T>
    implements SingleObserver<T>
  {
    boolean done;
    final SingleObserver<? super T> downstream;
    final Consumer<? super Disposable> onSubscribe;
    
    DoOnSubscribeSingleObserver(SingleObserver<? super T> paramSingleObserver, Consumer<? super Disposable> paramConsumer)
    {
      this.downstream = paramSingleObserver;
      this.onSubscribe = paramConsumer;
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      try
      {
        this.onSubscribe.accept(paramDisposable);
        this.downstream.onSubscribe(paramDisposable);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.done = true;
        paramDisposable.dispose();
        EmptyDisposable.error(localThrowable, this.downstream);
      }
    }
    
    public void onSuccess(T paramT)
    {
      if (this.done) {
        return;
      }
      this.downstream.onSuccess(paramT);
    }
  }
}
