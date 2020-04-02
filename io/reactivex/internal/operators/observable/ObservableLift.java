package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;

public final class ObservableLift<R, T>
  extends AbstractObservableWithUpstream<T, R>
{
  final ObservableOperator<? extends R, ? super T> operator;
  
  public ObservableLift(ObservableSource<T> paramObservableSource, ObservableOperator<? extends R, ? super T> paramObservableOperator)
  {
    super(paramObservableSource);
    this.operator = paramObservableOperator;
  }
  
  /* Error */
  public void subscribeActual(io.reactivex.Observer<? super R> paramObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 15	io/reactivex/internal/operators/observable/ObservableLift:operator	Lio/reactivex/ObservableOperator;
    //   4: aload_1
    //   5: invokeinterface 28 2 0
    //   10: astore_2
    //   11: new 30	java/lang/StringBuilder
    //   14: astore_1
    //   15: aload_1
    //   16: invokespecial 33	java/lang/StringBuilder:<init>	()V
    //   19: aload_1
    //   20: ldc 35
    //   22: invokevirtual 39	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_1
    //   27: aload_0
    //   28: getfield 15	io/reactivex/internal/operators/observable/ObservableLift:operator	Lio/reactivex/ObservableOperator;
    //   31: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload_1
    //   36: ldc 44
    //   38: invokevirtual 39	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload_2
    //   43: aload_1
    //   44: invokevirtual 48	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokestatic 54	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   50: checkcast 56	io/reactivex/Observer
    //   53: astore_1
    //   54: aload_0
    //   55: getfield 60	io/reactivex/internal/operators/observable/ObservableLift:source	Lio/reactivex/ObservableSource;
    //   58: aload_1
    //   59: invokeinterface 65 2 0
    //   64: return
    //   65: astore_2
    //   66: aload_2
    //   67: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   70: aload_2
    //   71: invokestatic 76	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   74: new 22	java/lang/NullPointerException
    //   77: dup
    //   78: ldc 78
    //   80: invokespecial 81	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   83: astore_1
    //   84: aload_1
    //   85: aload_2
    //   86: invokevirtual 85	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   89: pop
    //   90: aload_1
    //   91: athrow
    //   92: astore_1
    //   93: aload_1
    //   94: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	95	0	this	ObservableLift
    //   0	95	1	paramObserver	io.reactivex.Observer<? super R>
    //   10	33	2	localObserver	io.reactivex.Observer
    //   65	21	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	54	65	finally
    //   0	54	92	java/lang/NullPointerException
  }
}
