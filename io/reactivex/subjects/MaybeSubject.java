package io.reactivex.subjects;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSubject<T>
  extends Maybe<T>
  implements MaybeObserver<T>
{
  static final MaybeDisposable[] EMPTY = new MaybeDisposable[0];
  static final MaybeDisposable[] TERMINATED = new MaybeDisposable[0];
  Throwable error;
  final AtomicReference<MaybeDisposable<T>[]> observers = new AtomicReference(EMPTY);
  final AtomicBoolean once = new AtomicBoolean();
  T value;
  
  MaybeSubject() {}
  
  @CheckReturnValue
  public static <T> MaybeSubject<T> create()
  {
    return new MaybeSubject();
  }
  
  boolean add(MaybeDisposable<T> paramMaybeDisposable)
  {
    MaybeDisposable[] arrayOfMaybeDisposable1;
    MaybeDisposable[] arrayOfMaybeDisposable2;
    do
    {
      arrayOfMaybeDisposable1 = (MaybeDisposable[])this.observers.get();
      if (arrayOfMaybeDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfMaybeDisposable1.length;
      arrayOfMaybeDisposable2 = new MaybeDisposable[i + 1];
      System.arraycopy(arrayOfMaybeDisposable1, 0, arrayOfMaybeDisposable2, 0, i);
      arrayOfMaybeDisposable2[i] = paramMaybeDisposable;
    } while (!this.observers.compareAndSet(arrayOfMaybeDisposable1, arrayOfMaybeDisposable2));
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
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.observers.get() == TERMINATED) && (this.value == null) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((MaybeDisposable[])this.observers.get()).length != 0) {
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
    return ((MaybeDisposable[])this.observers.get()).length;
  }
  
  public void onComplete()
  {
    Object localObject = this.once;
    int i = 0;
    if (((AtomicBoolean)localObject).compareAndSet(false, true))
    {
      localObject = (MaybeDisposable[])this.observers.getAndSet(TERMINATED);
      int j = localObject.length;
      while (i < j)
      {
        localObject[i].downstream.onComplete();
        i++;
      }
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.once;
    int i = 0;
    if (((AtomicBoolean)localObject).compareAndSet(false, true))
    {
      this.error = paramThrowable;
      localObject = (MaybeDisposable[])this.observers.getAndSet(TERMINATED);
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
      localObject = (MaybeDisposable[])this.observers.getAndSet(TERMINATED);
      int j = localObject.length;
      while (i < j)
      {
        localObject[i].downstream.onSuccess(paramT);
        i++;
      }
    }
  }
  
  void remove(MaybeDisposable<T> paramMaybeDisposable)
  {
    MaybeDisposable[] arrayOfMaybeDisposable1;
    MaybeDisposable[] arrayOfMaybeDisposable2;
    do
    {
      arrayOfMaybeDisposable1 = (MaybeDisposable[])this.observers.get();
      int i = arrayOfMaybeDisposable1.length;
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
        if (arrayOfMaybeDisposable1[k] == paramMaybeDisposable)
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
        arrayOfMaybeDisposable2 = EMPTY;
      }
      else
      {
        arrayOfMaybeDisposable2 = new MaybeDisposable[i - 1];
        System.arraycopy(arrayOfMaybeDisposable1, 0, arrayOfMaybeDisposable2, 0, m);
        System.arraycopy(arrayOfMaybeDisposable1, m + 1, arrayOfMaybeDisposable2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfMaybeDisposable1, arrayOfMaybeDisposable2));
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    Object localObject = new MaybeDisposable(paramMaybeObserver, this);
    paramMaybeObserver.onSubscribe((Disposable)localObject);
    if (add((MaybeDisposable)localObject))
    {
      if (((MaybeDisposable)localObject).isDisposed()) {
        remove((MaybeDisposable)localObject);
      }
    }
    else
    {
      localObject = this.error;
      if (localObject != null)
      {
        paramMaybeObserver.onError((Throwable)localObject);
      }
      else
      {
        localObject = this.value;
        if (localObject == null) {
          paramMaybeObserver.onComplete();
        } else {
          paramMaybeObserver.onSuccess(localObject);
        }
      }
    }
  }
  
  static final class MaybeDisposable<T>
    extends AtomicReference<MaybeSubject<T>>
    implements Disposable
  {
    private static final long serialVersionUID = -7650903191002190468L;
    final MaybeObserver<? super T> downstream;
    
    MaybeDisposable(MaybeObserver<? super T> paramMaybeObserver, MaybeSubject<T> paramMaybeSubject)
    {
      this.downstream = paramMaybeObserver;
      lazySet(paramMaybeSubject);
    }
    
    public void dispose()
    {
      MaybeSubject localMaybeSubject = (MaybeSubject)getAndSet(null);
      if (localMaybeSubject != null) {
        localMaybeSubject.remove(this);
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
