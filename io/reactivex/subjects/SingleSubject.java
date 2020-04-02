package io.reactivex.subjects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubject<T>
  extends Single<T>
  implements SingleObserver<T>
{
  static final SingleDisposable[] EMPTY = new SingleDisposable[0];
  static final SingleDisposable[] TERMINATED = new SingleDisposable[0];
  Throwable error;
  final AtomicReference<SingleDisposable<T>[]> observers = new AtomicReference(EMPTY);
  final AtomicBoolean once = new AtomicBoolean();
  T value;
  
  SingleSubject() {}
  
  @CheckReturnValue
  public static <T> SingleSubject<T> create()
  {
    return new SingleSubject();
  }
  
  boolean add(SingleDisposable<T> paramSingleDisposable)
  {
    SingleDisposable[] arrayOfSingleDisposable1;
    SingleDisposable[] arrayOfSingleDisposable2;
    do
    {
      arrayOfSingleDisposable1 = (SingleDisposable[])this.observers.get();
      if (arrayOfSingleDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfSingleDisposable1.length;
      arrayOfSingleDisposable2 = new SingleDisposable[i + 1];
      System.arraycopy(arrayOfSingleDisposable1, 0, arrayOfSingleDisposable2, 0, i);
      arrayOfSingleDisposable2[i] = paramSingleDisposable;
    } while (!this.observers.compareAndSet(arrayOfSingleDisposable1, arrayOfSingleDisposable2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    if (this.observers.get() == TERMINATED) {
      return this.error;
    }
    return null;
  }
  
  public T getValue()
  {
    if (this.observers.get() == TERMINATED) {
      return this.value;
    }
    return null;
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((SingleDisposable[])this.observers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.observers.get() == TERMINATED) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasValue()
  {
    boolean bool;
    if ((this.observers.get() == TERMINATED) && (this.value != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int observerCount()
  {
    return ((SingleDisposable[])this.observers.get()).length;
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.once;
    int i = 0;
    if (((AtomicBoolean)localObject).compareAndSet(false, true))
    {
      this.error = paramThrowable;
      localObject = (SingleDisposable[])this.observers.getAndSet(TERMINATED);
      int j = localObject.length;
      while (i < j)
      {
        localObject[i].downstream.onError(paramThrowable);
        i++;
      }
    }
    RxJavaPlugins.onError(paramThrowable);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (this.observers.get() == TERMINATED) {
      paramDisposable.dispose();
    }
  }
  
  public void onSuccess(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.once;
    int i = 0;
    if (((AtomicBoolean)localObject).compareAndSet(false, true))
    {
      this.value = paramT;
      localObject = (SingleDisposable[])this.observers.getAndSet(TERMINATED);
      int j = localObject.length;
      while (i < j)
      {
        localObject[i].downstream.onSuccess(paramT);
        i++;
      }
    }
  }
  
  void remove(SingleDisposable<T> paramSingleDisposable)
  {
    SingleDisposable[] arrayOfSingleDisposable1;
    SingleDisposable[] arrayOfSingleDisposable2;
    do
    {
      arrayOfSingleDisposable1 = (SingleDisposable[])this.observers.get();
      int i = arrayOfSingleDisposable1.length;
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
        if (arrayOfSingleDisposable1[k] == paramSingleDisposable)
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
        arrayOfSingleDisposable2 = EMPTY;
      }
      else
      {
        arrayOfSingleDisposable2 = new SingleDisposable[i - 1];
        System.arraycopy(arrayOfSingleDisposable1, 0, arrayOfSingleDisposable2, 0, m);
        System.arraycopy(arrayOfSingleDisposable1, m + 1, arrayOfSingleDisposable2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfSingleDisposable1, arrayOfSingleDisposable2));
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    Object localObject = new SingleDisposable(paramSingleObserver, this);
    paramSingleObserver.onSubscribe((Disposable)localObject);
    if (add((SingleDisposable)localObject))
    {
      if (((SingleDisposable)localObject).isDisposed()) {
        remove((SingleDisposable)localObject);
      }
    }
    else
    {
      localObject = this.error;
      if (localObject != null) {
        paramSingleObserver.onError((Throwable)localObject);
      } else {
        paramSingleObserver.onSuccess(this.value);
      }
    }
  }
  
  static final class SingleDisposable<T>
    extends AtomicReference<SingleSubject<T>>
    implements Disposable
  {
    private static final long serialVersionUID = -7650903191002190468L;
    final SingleObserver<? super T> downstream;
    
    SingleDisposable(SingleObserver<? super T> paramSingleObserver, SingleSubject<T> paramSingleSubject)
    {
      this.downstream = paramSingleObserver;
      lazySet(paramSingleSubject);
    }
    
    public void dispose()
    {
      SingleSubject localSingleSubject = (SingleSubject)getAndSet(null);
      if (localSingleSubject != null) {
        localSingleSubject.remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}
