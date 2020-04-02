package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableCache
  extends Completable
  implements CompletableObserver
{
  static final InnerCompletableCache[] EMPTY = new InnerCompletableCache[0];
  static final InnerCompletableCache[] TERMINATED = new InnerCompletableCache[0];
  Throwable error;
  final AtomicReference<InnerCompletableCache[]> observers;
  final AtomicBoolean once;
  final CompletableSource source;
  
  public CompletableCache(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
    this.observers = new AtomicReference(EMPTY);
    this.once = new AtomicBoolean();
  }
  
  boolean add(InnerCompletableCache paramInnerCompletableCache)
  {
    InnerCompletableCache[] arrayOfInnerCompletableCache1;
    InnerCompletableCache[] arrayOfInnerCompletableCache2;
    do
    {
      arrayOfInnerCompletableCache1 = (InnerCompletableCache[])this.observers.get();
      if (arrayOfInnerCompletableCache1 == TERMINATED) {
        return false;
      }
      int i = arrayOfInnerCompletableCache1.length;
      arrayOfInnerCompletableCache2 = new InnerCompletableCache[i + 1];
      System.arraycopy(arrayOfInnerCompletableCache1, 0, arrayOfInnerCompletableCache2, 0, i);
      arrayOfInnerCompletableCache2[i] = paramInnerCompletableCache;
    } while (!this.observers.compareAndSet(arrayOfInnerCompletableCache1, arrayOfInnerCompletableCache2));
    return true;
  }
  
  public void onComplete()
  {
    for (InnerCompletableCache localInnerCompletableCache : (InnerCompletableCache[])this.observers.getAndSet(TERMINATED)) {
      if (!localInnerCompletableCache.get()) {
        localInnerCompletableCache.downstream.onComplete();
      }
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.error = paramThrowable;
    for (InnerCompletableCache localInnerCompletableCache : (InnerCompletableCache[])this.observers.getAndSet(TERMINATED)) {
      if (!localInnerCompletableCache.get()) {
        localInnerCompletableCache.downstream.onError(paramThrowable);
      }
    }
  }
  
  public void onSubscribe(Disposable paramDisposable) {}
  
  void remove(InnerCompletableCache paramInnerCompletableCache)
  {
    InnerCompletableCache[] arrayOfInnerCompletableCache1;
    InnerCompletableCache[] arrayOfInnerCompletableCache2;
    do
    {
      arrayOfInnerCompletableCache1 = (InnerCompletableCache[])this.observers.get();
      int i = arrayOfInnerCompletableCache1.length;
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
        if (arrayOfInnerCompletableCache1[k] == paramInnerCompletableCache)
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
        arrayOfInnerCompletableCache2 = EMPTY;
      }
      else
      {
        arrayOfInnerCompletableCache2 = new InnerCompletableCache[i - 1];
        System.arraycopy(arrayOfInnerCompletableCache1, 0, arrayOfInnerCompletableCache2, 0, m);
        System.arraycopy(arrayOfInnerCompletableCache1, m + 1, arrayOfInnerCompletableCache2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfInnerCompletableCache1, arrayOfInnerCompletableCache2));
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Object localObject = new InnerCompletableCache(paramCompletableObserver);
    paramCompletableObserver.onSubscribe((Disposable)localObject);
    if (add((InnerCompletableCache)localObject))
    {
      if (((InnerCompletableCache)localObject).isDisposed()) {
        remove((InnerCompletableCache)localObject);
      }
      if (this.once.compareAndSet(false, true)) {
        this.source.subscribe(this);
      }
    }
    else
    {
      localObject = this.error;
      if (localObject != null) {
        paramCompletableObserver.onError((Throwable)localObject);
      } else {
        paramCompletableObserver.onComplete();
      }
    }
  }
  
  final class InnerCompletableCache
    extends AtomicBoolean
    implements Disposable
  {
    private static final long serialVersionUID = 8943152917179642732L;
    final CompletableObserver downstream;
    
    InnerCompletableCache(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void dispose()
    {
      if (compareAndSet(false, true)) {
        CompletableCache.this.remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      return get();
    }
  }
}
