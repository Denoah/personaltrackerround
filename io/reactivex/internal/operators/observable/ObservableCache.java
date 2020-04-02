package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCache<T>
  extends AbstractObservableWithUpstream<T, T>
  implements Observer<T>
{
  static final CacheDisposable[] EMPTY = new CacheDisposable[0];
  static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
  final int capacityHint;
  volatile boolean done;
  Throwable error;
  final Node<T> head;
  final AtomicReference<CacheDisposable<T>[]> observers;
  final AtomicBoolean once;
  volatile long size;
  Node<T> tail;
  int tailOffset;
  
  public ObservableCache(Observable<T> paramObservable, int paramInt)
  {
    super(paramObservable);
    this.capacityHint = paramInt;
    this.once = new AtomicBoolean();
    paramObservable = new Node(paramInt);
    this.head = paramObservable;
    this.tail = paramObservable;
    this.observers = new AtomicReference(EMPTY);
  }
  
  void add(CacheDisposable<T> paramCacheDisposable)
  {
    CacheDisposable[] arrayOfCacheDisposable1;
    CacheDisposable[] arrayOfCacheDisposable2;
    do
    {
      arrayOfCacheDisposable1 = (CacheDisposable[])this.observers.get();
      if (arrayOfCacheDisposable1 == TERMINATED) {
        return;
      }
      int i = arrayOfCacheDisposable1.length;
      arrayOfCacheDisposable2 = new CacheDisposable[i + 1];
      System.arraycopy(arrayOfCacheDisposable1, 0, arrayOfCacheDisposable2, 0, i);
      arrayOfCacheDisposable2[i] = paramCacheDisposable;
    } while (!this.observers.compareAndSet(arrayOfCacheDisposable1, arrayOfCacheDisposable2));
  }
  
  long cachedEventCount()
  {
    return this.size;
  }
  
  boolean hasObservers()
  {
    boolean bool;
    if (((CacheDisposable[])this.observers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isConnected()
  {
    return this.once.get();
  }
  
  public void onComplete()
  {
    this.done = true;
    CacheDisposable[] arrayOfCacheDisposable = (CacheDisposable[])this.observers.getAndSet(TERMINATED);
    int i = arrayOfCacheDisposable.length;
    for (int j = 0; j < i; j++) {
      replay(arrayOfCacheDisposable[j]);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.error = paramThrowable;
    this.done = true;
    paramThrowable = (CacheDisposable[])this.observers.getAndSet(TERMINATED);
    int i = paramThrowable.length;
    for (int j = 0; j < i; j++) {
      replay(paramThrowable[j]);
    }
  }
  
  public void onNext(T paramT)
  {
    int i = this.tailOffset;
    int j = this.capacityHint;
    int k = 0;
    if (i == j)
    {
      Node localNode = new Node(i);
      localNode.values[0] = paramT;
      this.tailOffset = 1;
      this.tail.next = localNode;
      this.tail = localNode;
    }
    else
    {
      this.tail.values[i] = paramT;
      this.tailOffset = (i + 1);
    }
    this.size += 1L;
    paramT = (CacheDisposable[])this.observers.get();
    j = paramT.length;
    while (k < j)
    {
      replay(paramT[k]);
      k++;
    }
  }
  
  public void onSubscribe(Disposable paramDisposable) {}
  
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
  
  void replay(CacheDisposable<T> paramCacheDisposable)
  {
    if (paramCacheDisposable.getAndIncrement() != 0) {
      return;
    }
    long l = paramCacheDisposable.index;
    int i = paramCacheDisposable.offset;
    Object localObject1 = paramCacheDisposable.node;
    Observer localObserver = paramCacheDisposable.downstream;
    int j = this.capacityHint;
    int k = 1;
    int m;
    do
    {
      for (;;)
      {
        if (paramCacheDisposable.disposed)
        {
          paramCacheDisposable.node = null;
          return;
        }
        boolean bool = this.done;
        if (this.size == l) {
          m = 1;
        } else {
          m = 0;
        }
        if ((bool) && (m != 0))
        {
          paramCacheDisposable.node = null;
          paramCacheDisposable = this.error;
          if (paramCacheDisposable != null) {
            localObserver.onError(paramCacheDisposable);
          } else {
            localObserver.onComplete();
          }
          return;
        }
        if (m != 0) {
          break;
        }
        m = i;
        Object localObject2 = localObject1;
        if (i == j)
        {
          localObject2 = ((Node)localObject1).next;
          m = 0;
        }
        localObserver.onNext(localObject2.values[m]);
        i = m + 1;
        l += 1L;
        localObject1 = localObject2;
      }
      paramCacheDisposable.index = l;
      paramCacheDisposable.offset = i;
      paramCacheDisposable.node = ((Node)localObject1);
      m = paramCacheDisposable.addAndGet(-k);
      k = m;
    } while (m != 0);
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    CacheDisposable localCacheDisposable = new CacheDisposable(paramObserver, this);
    paramObserver.onSubscribe(localCacheDisposable);
    add(localCacheDisposable);
    if ((!this.once.get()) && (this.once.compareAndSet(false, true))) {
      this.source.subscribe(this);
    } else {
      replay(localCacheDisposable);
    }
  }
  
  static final class CacheDisposable<T>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = 6770240836423125754L;
    volatile boolean disposed;
    final Observer<? super T> downstream;
    long index;
    ObservableCache.Node<T> node;
    int offset;
    final ObservableCache<T> parent;
    
    CacheDisposable(Observer<? super T> paramObserver, ObservableCache<T> paramObservableCache)
    {
      this.downstream = paramObserver;
      this.parent = paramObservableCache;
      this.node = paramObservableCache.head;
    }
    
    public void dispose()
    {
      if (!this.disposed)
      {
        this.disposed = true;
        this.parent.remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
  }
  
  static final class Node<T>
  {
    volatile Node<T> next;
    final T[] values;
    
    Node(int paramInt)
    {
      this.values = ((Object[])new Object[paramInt]);
    }
  }
}
