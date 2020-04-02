package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableDistinct<T, K>
  extends AbstractObservableWithUpstream<T, T>
{
  final Callable<? extends Collection<? super K>> collectionSupplier;
  final Function<? super T, K> keySelector;
  
  public ObservableDistinct(ObservableSource<T> paramObservableSource, Function<? super T, K> paramFunction, Callable<? extends Collection<? super K>> paramCallable)
  {
    super(paramObservableSource);
    this.keySelector = paramFunction;
    this.collectionSupplier = paramCallable;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new DistinctObserver(paramObserver, this.keySelector, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class DistinctObserver<T, K>
    extends BasicFuseableObserver<T, T>
  {
    final Collection<? super K> collection;
    final Function<? super T, K> keySelector;
    
    DistinctObserver(Observer<? super T> paramObserver, Function<? super T, K> paramFunction, Collection<? super K> paramCollection)
    {
      super();
      this.keySelector = paramFunction;
      this.collection = paramCollection;
    }
    
    public void clear()
    {
      this.collection.clear();
      super.clear();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.collection.clear();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
      }
      else
      {
        this.done = true;
        this.collection.clear();
        this.downstream.onError(paramThrowable);
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 38	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 59	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:sourceMode	I
      //   12: ifne +54 -> 66
      //   15: aload_0
      //   16: getfield 21	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:keySelector	Lio/reactivex/functions/Function;
      //   19: aload_1
      //   20: invokeinterface 65 2 0
      //   25: ldc 67
      //   27: invokestatic 73	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: astore_2
      //   31: aload_0
      //   32: getfield 23	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:collection	Ljava/util/Collection;
      //   35: aload_2
      //   36: invokeinterface 77 2 0
      //   41: istore_3
      //   42: iload_3
      //   43: ifeq +33 -> 76
      //   46: aload_0
      //   47: getfield 42	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:downstream	Lio/reactivex/Observer;
      //   50: aload_1
      //   51: invokeinterface 79 2 0
      //   56: goto +20 -> 76
      //   59: astore_1
      //   60: aload_0
      //   61: aload_1
      //   62: invokevirtual 82	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:fail	(Ljava/lang/Throwable;)V
      //   65: return
      //   66: aload_0
      //   67: getfield 42	io/reactivex/internal/operators/observable/ObservableDistinct$DistinctObserver:downstream	Lio/reactivex/Observer;
      //   70: aconst_null
      //   71: invokeinterface 79 2 0
      //   76: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	77	0	this	DistinctObserver
      //   0	77	1	paramT	T
      //   30	6	2	localObject	Object
      //   41	2	3	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   15	42	59	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject;
      do
      {
        localObject = this.qd.poll();
      } while ((localObject != null) && (!this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply(localObject), "The keySelector returned a null key"))));
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
