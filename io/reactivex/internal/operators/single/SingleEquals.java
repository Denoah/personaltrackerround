package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class SingleEquals<T>
  extends Single<Boolean>
{
  final SingleSource<? extends T> first;
  final SingleSource<? extends T> second;
  
  public SingleEquals(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2)
  {
    this.first = paramSingleSource1;
    this.second = paramSingleSource2;
  }
  
  protected void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    AtomicInteger localAtomicInteger = new AtomicInteger();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = null;
    arrayOfObject[1] = null;
    CompositeDisposable localCompositeDisposable = new CompositeDisposable();
    paramSingleObserver.onSubscribe(localCompositeDisposable);
    this.first.subscribe(new InnerObserver(0, localCompositeDisposable, arrayOfObject, paramSingleObserver, localAtomicInteger));
    this.second.subscribe(new InnerObserver(1, localCompositeDisposable, arrayOfObject, paramSingleObserver, localAtomicInteger));
  }
  
  static class InnerObserver<T>
    implements SingleObserver<T>
  {
    final AtomicInteger count;
    final SingleObserver<? super Boolean> downstream;
    final int index;
    final CompositeDisposable set;
    final Object[] values;
    
    InnerObserver(int paramInt, CompositeDisposable paramCompositeDisposable, Object[] paramArrayOfObject, SingleObserver<? super Boolean> paramSingleObserver, AtomicInteger paramAtomicInteger)
    {
      this.index = paramInt;
      this.set = paramCompositeDisposable;
      this.values = paramArrayOfObject;
      this.downstream = paramSingleObserver;
      this.count = paramAtomicInteger;
    }
    
    public void onError(Throwable paramThrowable)
    {
      int i;
      do
      {
        i = this.count.get();
        if (i >= 2)
        {
          RxJavaPlugins.onError(paramThrowable);
          return;
        }
      } while (!this.count.compareAndSet(i, 2));
      this.set.dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.set.add(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.values[this.index] = paramT;
      if (this.count.incrementAndGet() == 2)
      {
        paramT = this.downstream;
        Object[] arrayOfObject = this.values;
        paramT.onSuccess(Boolean.valueOf(ObjectHelper.equals(arrayOfObject[0], arrayOfObject[1])));
      }
    }
  }
}
