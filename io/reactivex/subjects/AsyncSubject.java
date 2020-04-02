package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class AsyncSubject<T>
  extends Subject<T>
{
  static final AsyncDisposable[] EMPTY = new AsyncDisposable[0];
  static final AsyncDisposable[] TERMINATED = new AsyncDisposable[0];
  Throwable error;
  final AtomicReference<AsyncDisposable<T>[]> subscribers = new AtomicReference(EMPTY);
  T value;
  
  AsyncSubject() {}
  
  @CheckReturnValue
  public static <T> AsyncSubject<T> create()
  {
    return new AsyncSubject();
  }
  
  boolean add(AsyncDisposable<T> paramAsyncDisposable)
  {
    AsyncDisposable[] arrayOfAsyncDisposable1;
    AsyncDisposable[] arrayOfAsyncDisposable2;
    do
    {
      arrayOfAsyncDisposable1 = (AsyncDisposable[])this.subscribers.get();
      if (arrayOfAsyncDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfAsyncDisposable1.length;
      arrayOfAsyncDisposable2 = new AsyncDisposable[i + 1];
      System.arraycopy(arrayOfAsyncDisposable1, 0, arrayOfAsyncDisposable2, 0, i);
      arrayOfAsyncDisposable2[i] = paramAsyncDisposable;
    } while (!this.subscribers.compareAndSet(arrayOfAsyncDisposable1, arrayOfAsyncDisposable2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    Throwable localThrowable;
    if (this.subscribers.get() == TERMINATED) {
      localThrowable = this.error;
    } else {
      localThrowable = null;
    }
    return localThrowable;
  }
  
  public T getValue()
  {
    Object localObject;
    if (this.subscribers.get() == TERMINATED) {
      localObject = this.value;
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  @Deprecated
  public Object[] getValues()
  {
    Object localObject = getValue();
    Object[] arrayOfObject;
    if (localObject != null)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = localObject;
    }
    else
    {
      arrayOfObject = new Object[0];
    }
    return arrayOfObject;
  }
  
  @Deprecated
  public T[] getValues(T[] paramArrayOfT)
  {
    Object localObject1 = getValue();
    if (localObject1 == null)
    {
      if (paramArrayOfT.length != 0) {
        paramArrayOfT[0] = null;
      }
      return paramArrayOfT;
    }
    Object localObject2 = paramArrayOfT;
    if (paramArrayOfT.length == 0) {
      localObject2 = Arrays.copyOf(paramArrayOfT, 1);
    }
    localObject2[0] = localObject1;
    if (localObject2.length != 1) {
      localObject2[1] = null;
    }
    return localObject2;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((AsyncDisposable[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasValue()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.value != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    Object localObject = this.subscribers.get();
    AsyncDisposable[] arrayOfAsyncDisposable = TERMINATED;
    if (localObject == arrayOfAsyncDisposable) {
      return;
    }
    localObject = this.value;
    arrayOfAsyncDisposable = (AsyncDisposable[])this.subscribers.getAndSet(arrayOfAsyncDisposable);
    int i = 0;
    int j = 0;
    if (localObject == null)
    {
      i = arrayOfAsyncDisposable.length;
      while (j < i)
      {
        arrayOfAsyncDisposable[j].onComplete();
        j++;
      }
    }
    int k = arrayOfAsyncDisposable.length;
    for (j = i; j < k; j++) {
      arrayOfAsyncDisposable[j].complete(localObject);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.subscribers.get();
    AsyncDisposable[] arrayOfAsyncDisposable = TERMINATED;
    if (localObject == arrayOfAsyncDisposable)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.value = null;
    this.error = paramThrowable;
    localObject = (AsyncDisposable[])this.subscribers.getAndSet(arrayOfAsyncDisposable);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      localObject[j].onError(paramThrowable);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.subscribers.get() == TERMINATED) {
      return;
    }
    this.value = paramT;
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (this.subscribers.get() == TERMINATED) {
      paramDisposable.dispose();
    }
  }
  
  void remove(AsyncDisposable<T> paramAsyncDisposable)
  {
    AsyncDisposable[] arrayOfAsyncDisposable1;
    AsyncDisposable[] arrayOfAsyncDisposable2;
    do
    {
      arrayOfAsyncDisposable1 = (AsyncDisposable[])this.subscribers.get();
      int i = arrayOfAsyncDisposable1.length;
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
        if (arrayOfAsyncDisposable1[k] == paramAsyncDisposable)
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
        arrayOfAsyncDisposable2 = EMPTY;
      }
      else
      {
        arrayOfAsyncDisposable2 = new AsyncDisposable[i - 1];
        System.arraycopy(arrayOfAsyncDisposable1, 0, arrayOfAsyncDisposable2, 0, m);
        System.arraycopy(arrayOfAsyncDisposable1, m + 1, arrayOfAsyncDisposable2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfAsyncDisposable1, arrayOfAsyncDisposable2));
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    AsyncDisposable localAsyncDisposable = new AsyncDisposable(paramObserver, this);
    paramObserver.onSubscribe(localAsyncDisposable);
    if (add(localAsyncDisposable))
    {
      if (localAsyncDisposable.isDisposed()) {
        remove(localAsyncDisposable);
      }
    }
    else
    {
      Throwable localThrowable = this.error;
      if (localThrowable != null)
      {
        paramObserver.onError(localThrowable);
      }
      else
      {
        paramObserver = this.value;
        if (paramObserver != null) {
          localAsyncDisposable.complete(paramObserver);
        } else {
          localAsyncDisposable.onComplete();
        }
      }
    }
  }
  
  static final class AsyncDisposable<T>
    extends DeferredScalarDisposable<T>
  {
    private static final long serialVersionUID = 5629876084736248016L;
    final AsyncSubject<T> parent;
    
    AsyncDisposable(Observer<? super T> paramObserver, AsyncSubject<T> paramAsyncSubject)
    {
      super();
      this.parent = paramAsyncSubject;
    }
    
    public void dispose()
    {
      if (super.tryDispose()) {
        this.parent.remove(this);
      }
    }
    
    void onComplete()
    {
      if (!isDisposed()) {
        this.downstream.onComplete();
      }
    }
    
    void onError(Throwable paramThrowable)
    {
      if (isDisposed()) {
        RxJavaPlugins.onError(paramThrowable);
      } else {
        this.downstream.onError(paramThrowable);
      }
    }
  }
}
