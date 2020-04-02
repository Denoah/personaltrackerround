package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableReduceMaybe<T>
  extends Maybe<T>
{
  final BiFunction<T, T, T> reducer;
  final ObservableSource<T> source;
  
  public ObservableReduceMaybe(ObservableSource<T> paramObservableSource, BiFunction<T, T, T> paramBiFunction)
  {
    this.source = paramObservableSource;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new ReduceObserver(paramMaybeObserver, this.reducer));
  }
  
  static final class ReduceObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final MaybeObserver<? super T> downstream;
    final BiFunction<T, T, T> reducer;
    Disposable upstream;
    T value;
    
    ReduceObserver(MaybeObserver<? super T> paramMaybeObserver, BiFunction<T, T, T> paramBiFunction)
    {
      this.downstream = paramMaybeObserver;
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
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject = this.value;
      this.value = null;
      if (localObject != null) {
        this.downstream.onSuccess(localObject);
      } else {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.value = null;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 49	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:done	Z
      //   4: ifne +62 -> 66
      //   7: aload_0
      //   8: getfield 51	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:value	Ljava/lang/Object;
      //   11: astore_2
      //   12: aload_2
      //   13: ifnonnull +11 -> 24
      //   16: aload_0
      //   17: aload_1
      //   18: putfield 51	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:value	Ljava/lang/Object;
      //   21: goto +45 -> 66
      //   24: aload_0
      //   25: aload_0
      //   26: getfield 34	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:reducer	Lio/reactivex/functions/BiFunction;
      //   29: aload_2
      //   30: aload_1
      //   31: invokeinterface 73 3 0
      //   36: ldc 75
      //   38: invokestatic 81	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   41: putfield 51	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:value	Ljava/lang/Object;
      //   44: goto +22 -> 66
      //   47: astore_1
      //   48: aload_1
      //   49: invokestatic 86	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   52: aload_0
      //   53: getfield 40	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   56: invokeinterface 42 1 0
      //   61: aload_0
      //   62: aload_1
      //   63: invokevirtual 87	io/reactivex/internal/operators/observable/ObservableReduceMaybe$ReduceObserver:onError	(Ljava/lang/Throwable;)V
      //   66: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	67	0	this	ReduceObserver
      //   0	67	1	paramT	T
      //   11	19	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   24	44	47	finally
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
