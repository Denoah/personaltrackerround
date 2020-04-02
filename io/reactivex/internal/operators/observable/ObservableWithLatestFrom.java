package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWithLatestFrom<T, U, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final BiFunction<? super T, ? super U, ? extends R> combiner;
  final ObservableSource<? extends U> other;
  
  public ObservableWithLatestFrom(ObservableSource<T> paramObservableSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, ObservableSource<? extends U> paramObservableSource1)
  {
    super(paramObservableSource);
    this.combiner = paramBiFunction;
    this.other = paramObservableSource1;
  }
  
  public void subscribeActual(Observer<? super R> paramObserver)
  {
    SerializedObserver localSerializedObserver = new SerializedObserver(paramObserver);
    paramObserver = new WithLatestFromObserver(localSerializedObserver, this.combiner);
    localSerializedObserver.onSubscribe(paramObserver);
    this.other.subscribe(new WithLatestFromOtherObserver(paramObserver));
    this.source.subscribe(paramObserver);
  }
  
  static final class WithLatestFromObserver<T, U, R>
    extends AtomicReference<U>
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -312246233408980075L;
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final Observer<? super R> downstream;
    final AtomicReference<Disposable> other = new AtomicReference();
    final AtomicReference<Disposable> upstream = new AtomicReference();
    
    WithLatestFromObserver(Observer<? super R> paramObserver, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
    {
      this.downstream = paramObserver;
      this.combiner = paramBiFunction;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this.other);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this.other);
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.other);
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 67	io/reactivex/internal/operators/observable/ObservableWithLatestFrom$WithLatestFromObserver:get	()Ljava/lang/Object;
      //   4: astore_2
      //   5: aload_2
      //   6: ifnull +52 -> 58
      //   9: aload_0
      //   10: getfield 39	io/reactivex/internal/operators/observable/ObservableWithLatestFrom$WithLatestFromObserver:combiner	Lio/reactivex/functions/BiFunction;
      //   13: aload_1
      //   14: aload_2
      //   15: invokeinterface 73 3 0
      //   20: ldc 75
      //   22: invokestatic 81	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   25: astore_1
      //   26: aload_0
      //   27: getfield 37	io/reactivex/internal/operators/observable/ObservableWithLatestFrom$WithLatestFromObserver:downstream	Lio/reactivex/Observer;
      //   30: aload_1
      //   31: invokeinterface 83 2 0
      //   36: goto +22 -> 58
      //   39: astore_1
      //   40: aload_1
      //   41: invokestatic 88	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   44: aload_0
      //   45: invokevirtual 90	io/reactivex/internal/operators/observable/ObservableWithLatestFrom$WithLatestFromObserver:dispose	()V
      //   48: aload_0
      //   49: getfield 37	io/reactivex/internal/operators/observable/ObservableWithLatestFrom$WithLatestFromObserver:downstream	Lio/reactivex/Observer;
      //   52: aload_1
      //   53: invokeinterface 64 2 0
      //   58: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	59	0	this	WithLatestFromObserver
      //   0	59	1	paramT	T
      //   4	11	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   9	26	39	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    public void otherError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.upstream);
      this.downstream.onError(paramThrowable);
    }
    
    public boolean setOther(Disposable paramDisposable)
    {
      return DisposableHelper.setOnce(this.other, paramDisposable);
    }
  }
  
  final class WithLatestFromOtherObserver
    implements Observer<U>
  {
    private final ObservableWithLatestFrom.WithLatestFromObserver<T, U, R> parent;
    
    WithLatestFromOtherObserver()
    {
      Object localObject;
      this.parent = localObject;
    }
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.otherError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      this.parent.lazySet(paramU);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.parent.setOther(paramDisposable);
    }
  }
}
