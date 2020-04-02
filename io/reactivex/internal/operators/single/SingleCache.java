package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCache<T>
  extends Single<T>
  implements SingleObserver<T>
{
  static final CacheDisposable[] EMPTY = new CacheDisposable[0];
  static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
  Throwable error;
  final AtomicReference<CacheDisposable<T>[]> observers;
  final SingleSource<? extends T> source;
  T value;
  final AtomicInteger wip;
  
  public SingleCache(SingleSource<? extends T> paramSingleSource)
  {
    this.source = paramSingleSource;
    this.wip = new AtomicInteger();
    this.observers = new AtomicReference(EMPTY);
  }
  
  boolean add(CacheDisposable<T> paramCacheDisposable)
  {
    CacheDisposable[] arrayOfCacheDisposable1;
    CacheDisposable[] arrayOfCacheDisposable2;
    do
    {
      arrayOfCacheDisposable1 = (CacheDisposable[])this.observers.get();
      if (arrayOfCacheDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfCacheDisposable1.length;
      arrayOfCacheDisposable2 = new CacheDisposable[i + 1];
      System.arraycopy(arrayOfCacheDisposable1, 0, arrayOfCacheDisposable2, 0, i);
      arrayOfCacheDisposable2[i] = paramCacheDisposable;
    } while (!this.observers.compareAndSet(arrayOfCacheDisposable1, arrayOfCacheDisposable2));
    return true;
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.error = paramThrowable;
    for (CacheDisposable localCacheDisposable : (CacheDisposable[])this.observers.getAndSet(TERMINATED)) {
      if (!localCacheDisposable.isDisposed()) {
        localCacheDisposable.downstream.onError(paramThrowable);
      }
    }
  }
  
  public void onSubscribe(Disposable paramDisposable) {}
  
  public void onSuccess(T paramT)
  {
    this.value = paramT;
    for (CacheDisposable localCacheDisposable : (CacheDisposable[])this.observers.getAndSet(TERMINATED)) {
      if (!localCacheDisposable.isDisposed()) {
        localCacheDisposable.downstream.onSuccess(paramT);
      }
    }
  }
  
  void remove(CacheDisposable<T> paramCacheDisposable)
  {
    CacheDisposable[] arrayOfCacheDisposable1;
    CacheDisposable[] arrayOfCacheDisposable2;
    do
    {
      arrayOfCacheDisposable1 = (CacheDisposable[])this.observers.get();
      int i = arrayOfCacheDisposable1.length;
      if (i == 0) {
        return;
      }
      int j = -1;
      int m;
      for (int k = 0;; k++)
      {
        m = j;
        if (k >= i) {
          break;
        }
        if (arrayOfCacheDisposable1[k] == paramCacheDisposable)
        {
          m = k;
          break;
        }
      }
      if (m < 0) {
        return;
      }
      if (i == 1)
      {
        arrayOfCacheDisposable2 = EMPTY;
      }
      else
      {
        arrayOfCacheDisposable2 = new CacheDisposable[i - 1];
        System.arraycopy(arrayOfCacheDisposable1, 0, arrayOfCacheDisposable2, 0, m);
        System.arraycopy(arrayOfCacheDisposable1, m + 1, arrayOfCacheDisposable2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfCacheDisposable1, arrayOfCacheDisposable2));
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    Object localObject = new CacheDisposable(paramSingleObserver, this);
    paramSingleObserver.onSubscribe((Disposable)localObject);
    if (add((CacheDisposable)localObject))
    {
      if (((CacheDisposable)localObject).isDisposed()) {
        remove((CacheDisposable)localObject);
      }
      if (this.wip.getAndIncrement() == 0) {
        this.source.subscribe(this);
      }
      return;
    }
    localObject = this.error;
    if (localObject != null) {
      paramSingleObserver.onError((Throwable)localObject);
    } else {
      paramSingleObserver.onSuccess(this.value);
    }
  }
  
  static final class CacheDisposable<T>
    extends AtomicBoolean
    implements Disposable
  {
    private static final long serialVersionUID = 7514387411091976596L;
    final SingleObserver<? super T> downstream;
    final SingleCache<T> parent;
    
    CacheDisposable(SingleObserver<? super T> paramSingleObserver, SingleCache<T> paramSingleCache)
    {
      this.downstream = paramSingleObserver;
      this.parent = paramSingleCache;
    }
    
    public void dispose()
    {
      if (compareAndSet(false, true)) {
        this.parent.remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      return get();
    }
  }
}
