package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableDoAfterNext<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Consumer<? super T> onAfterNext;
  
  public ObservableDoAfterNext(ObservableSource<T> paramObservableSource, Consumer<? super T> paramConsumer)
  {
    super(paramObservableSource);
    this.onAfterNext = paramConsumer;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DoAfterObserver(paramObserver, this.onAfterNext));
  }
  
  static final class DoAfterObserver<T>
    extends BasicFuseableObserver<T, T>
  {
    final Consumer<? super T> onAfterNext;
    
    DoAfterObserver(Observer<? super T> paramObserver, Consumer<? super T> paramConsumer)
    {
      super();
      this.onAfterNext = paramConsumer;
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/observable/ObservableDoAfterNext$DoAfterObserver:downstream	Lio/reactivex/Observer;
      //   4: aload_1
      //   5: invokeinterface 31 2 0
      //   10: aload_0
      //   11: getfield 35	io/reactivex/internal/operators/observable/ObservableDoAfterNext$DoAfterObserver:sourceMode	I
      //   14: ifne +22 -> 36
      //   17: aload_0
      //   18: getfield 18	io/reactivex/internal/operators/observable/ObservableDoAfterNext$DoAfterObserver:onAfterNext	Lio/reactivex/functions/Consumer;
      //   21: aload_1
      //   22: invokeinterface 40 2 0
      //   27: goto +9 -> 36
      //   30: astore_1
      //   31: aload_0
      //   32: aload_1
      //   33: invokevirtual 44	io/reactivex/internal/operators/observable/ObservableDoAfterNext$DoAfterObserver:fail	(Ljava/lang/Throwable;)V
      //   36: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	37	0	this	DoAfterObserver
      //   0	37	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   17	27	30	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qd.poll();
      if (localObject != null) {
        this.onAfterNext.accept(localObject);
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
