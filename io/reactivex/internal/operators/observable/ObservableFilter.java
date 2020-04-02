package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableFilter<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public ObservableFilter(ObservableSource<T> paramObservableSource, Predicate<? super T> paramPredicate)
  {
    super(paramObservableSource);
    this.predicate = paramPredicate;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new FilterObserver(paramObserver, this.predicate));
  }
  
  static final class FilterObserver<T>
    extends BasicFuseableObserver<T, T>
  {
    final Predicate<? super T> filter;
    
    FilterObserver(Observer<? super T> paramObserver, Predicate<? super T> paramPredicate)
    {
      super();
      this.filter = paramPredicate;
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/observable/ObservableFilter$FilterObserver:sourceMode	I
      //   4: ifne +38 -> 42
      //   7: aload_0
      //   8: getfield 18	io/reactivex/internal/operators/observable/ObservableFilter$FilterObserver:filter	Lio/reactivex/functions/Predicate;
      //   11: aload_1
      //   12: invokeinterface 33 2 0
      //   17: istore_2
      //   18: iload_2
      //   19: ifeq +33 -> 52
      //   22: aload_0
      //   23: getfield 37	io/reactivex/internal/operators/observable/ObservableFilter$FilterObserver:downstream	Lio/reactivex/Observer;
      //   26: aload_1
      //   27: invokeinterface 41 2 0
      //   32: goto +20 -> 52
      //   35: astore_1
      //   36: aload_0
      //   37: aload_1
      //   38: invokevirtual 45	io/reactivex/internal/operators/observable/ObservableFilter$FilterObserver:fail	(Ljava/lang/Throwable;)V
      //   41: return
      //   42: aload_0
      //   43: getfield 37	io/reactivex/internal/operators/observable/ObservableFilter$FilterObserver:downstream	Lio/reactivex/Observer;
      //   46: aconst_null
      //   47: invokeinterface 41 2 0
      //   52: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	53	0	this	FilterObserver
      //   0	53	1	paramT	T
      //   17	2	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   7	18	35	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject;
      do
      {
        localObject = this.qd.poll();
      } while ((localObject != null) && (!this.filter.test(localObject)));
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
