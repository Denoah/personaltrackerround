package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableReduceSeedSingle<T, R>
  extends Single<R>
{
  final BiFunction<R, ? super T, R> reducer;
  final R seed;
  final ObservableSource<T> source;
  
  public ObservableReduceSeedSingle(ObservableSource<T> paramObservableSource, R paramR, BiFunction<R, ? super T, R> paramBiFunction)
  {
    this.source = paramObservableSource;
    this.seed = paramR;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    this.source.subscribe(new ReduceSeedObserver(paramSingleObserver, this.reducer, this.seed));
  }
  
  static final class ReduceSeedObserver<T, R>
    implements Observer<T>, Disposable
  {
    final SingleObserver<? super R> downstream;
    final BiFunction<R, ? super T, R> reducer;
    Disposable upstream;
    R value;
    
    ReduceSeedObserver(SingleObserver<? super R> paramSingleObserver, BiFunction<R, ? super T, R> paramBiFunction, R paramR)
    {
      this.downstream = paramSingleObserver;
      this.value = paramR;
      this.reducer = paramBiFunction;
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
      Object localObject = this.value;
      if (localObject != null)
      {
        this.value = null;
        this.downstream.onSuccess(localObject);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.value != null)
      {
        this.value = null;
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
      //   1: getfield 32	io/reactivex/internal/operators/observable/ObservableReduceSeedSingle$ReduceSeedObserver:value	Ljava/lang/Object;
      //   4: astore_2
      //   5: aload_2
      //   6: ifnull +45 -> 51
      //   9: aload_0
      //   10: aload_0
      //   11: getfield 34	io/reactivex/internal/operators/observable/ObservableReduceSeedSingle$ReduceSeedObserver:reducer	Lio/reactivex/functions/BiFunction;
      //   14: aload_2
      //   15: aload_1
      //   16: invokeinterface 67 3 0
      //   21: ldc 69
      //   23: invokestatic 75	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   26: putfield 32	io/reactivex/internal/operators/observable/ObservableReduceSeedSingle$ReduceSeedObserver:value	Ljava/lang/Object;
      //   29: goto +22 -> 51
      //   32: astore_1
      //   33: aload_1
      //   34: invokestatic 80	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 40	io/reactivex/internal/operators/observable/ObservableReduceSeedSingle$ReduceSeedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   41: invokeinterface 42 1 0
      //   46: aload_0
      //   47: aload_1
      //   48: invokevirtual 81	io/reactivex/internal/operators/observable/ObservableReduceSeedSingle$ReduceSeedObserver:onError	(Ljava/lang/Throwable;)V
      //   51: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	52	0	this	ReduceSeedObserver
      //   0	52	1	paramT	T
      //   4	11	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   9	29	32	finally
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
