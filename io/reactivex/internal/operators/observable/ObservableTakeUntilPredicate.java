package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTakeUntilPredicate<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public ObservableTakeUntilPredicate(ObservableSource<T> paramObservableSource, Predicate<? super T> paramPredicate)
  {
    super(paramObservableSource);
    this.predicate = paramPredicate;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeUntilPredicateObserver(paramObserver, this.predicate));
  }
  
  static final class TakeUntilPredicateObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super T> downstream;
    final Predicate<? super T> predicate;
    Disposable upstream;
    
    TakeUntilPredicateObserver(Observer<? super T> paramObserver, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramObserver;
      this.predicate = paramPredicate;
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
      if (!this.done)
      {
        this.done = true;
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:done	Z
      //   4: ifne +73 -> 77
      //   7: aload_0
      //   8: getfield 29	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:downstream	Lio/reactivex/Observer;
      //   11: aload_1
      //   12: invokeinterface 59 2 0
      //   17: aload_0
      //   18: getfield 31	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:predicate	Lio/reactivex/functions/Predicate;
      //   21: aload_1
      //   22: invokeinterface 65 2 0
      //   27: istore_2
      //   28: iload_2
      //   29: ifeq +48 -> 77
      //   32: aload_0
      //   33: iconst_1
      //   34: putfield 46	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:done	Z
      //   37: aload_0
      //   38: getfield 37	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   41: invokeinterface 39 1 0
      //   46: aload_0
      //   47: getfield 29	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:downstream	Lio/reactivex/Observer;
      //   50: invokeinterface 48 1 0
      //   55: goto +22 -> 77
      //   58: astore_1
      //   59: aload_1
      //   60: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   63: aload_0
      //   64: getfield 37	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   67: invokeinterface 39 1 0
      //   72: aload_0
      //   73: aload_1
      //   74: invokevirtual 71	io/reactivex/internal/operators/observable/ObservableTakeUntilPredicate$TakeUntilPredicateObserver:onError	(Ljava/lang/Throwable;)V
      //   77: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	78	0	this	TakeUntilPredicateObserver
      //   0	78	1	paramT	T
      //   27	2	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   17	28	58	finally
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
