package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;

public final class SingleContains<T>
  extends Single<Boolean>
{
  final BiPredicate<Object, Object> comparer;
  final SingleSource<T> source;
  final Object value;
  
  public SingleContains(SingleSource<T> paramSingleSource, Object paramObject, BiPredicate<Object, Object> paramBiPredicate)
  {
    this.source = paramSingleSource;
    this.value = paramObject;
    this.comparer = paramBiPredicate;
  }
  
  protected void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    this.source.subscribe(new ContainsSingleObserver(paramSingleObserver));
  }
  
  final class ContainsSingleObserver
    implements SingleObserver<T>
  {
    private final SingleObserver<? super Boolean> downstream;
    
    ContainsSingleObserver()
    {
      Object localObject;
      this.downstream = localObject;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.downstream.onSubscribe(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        boolean bool = SingleContains.this.comparer.test(paramT, SingleContains.this.value);
        this.downstream.onSuccess(Boolean.valueOf(bool));
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
