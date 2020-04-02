package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class PublishSubject<T>
  extends Subject<T>
{
  static final PublishDisposable[] EMPTY = new PublishDisposable[0];
  static final PublishDisposable[] TERMINATED = new PublishDisposable[0];
  Throwable error;
  final AtomicReference<PublishDisposable<T>[]> subscribers = new AtomicReference(EMPTY);
  
  PublishSubject() {}
  
  @CheckReturnValue
  public static <T> PublishSubject<T> create()
  {
    return new PublishSubject();
  }
  
  boolean add(PublishDisposable<T> paramPublishDisposable)
  {
    PublishDisposable[] arrayOfPublishDisposable1;
    PublishDisposable[] arrayOfPublishDisposable2;
    do
    {
      arrayOfPublishDisposable1 = (PublishDisposable[])this.subscribers.get();
      if (arrayOfPublishDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfPublishDisposable1.length;
      arrayOfPublishDisposable2 = new PublishDisposable[i + 1];
      System.arraycopy(arrayOfPublishDisposable1, 0, arrayOfPublishDisposable2, 0, i);
      arrayOfPublishDisposable2[i] = paramPublishDisposable;
    } while (!this.subscribers.compareAndSet(arrayOfPublishDisposable1, arrayOfPublishDisposable2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    if (this.subscribers.get() == TERMINATED) {
      return this.error;
    }
    return null;
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
    if (((PublishDisposable[])this.subscribers.get()).length != 0) {
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
  
  public void onComplete()
  {
    Object localObject = this.subscribers.get();
    PublishDisposable[] arrayOfPublishDisposable = TERMINATED;
    if (localObject == arrayOfPublishDisposable) {
      return;
    }
    localObject = (PublishDisposable[])this.subscribers.getAndSet(arrayOfPublishDisposable);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      localObject[j].onComplete();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.subscribers.get();
    PublishDisposable[] arrayOfPublishDisposable = TERMINATED;
    if (localObject == arrayOfPublishDisposable)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.error = paramThrowable;
    arrayOfPublishDisposable = (PublishDisposable[])this.subscribers.getAndSet(arrayOfPublishDisposable);
    int i = arrayOfPublishDisposable.length;
    for (int j = 0; j < i; j++) {
      arrayOfPublishDisposable[j].onError(paramThrowable);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    PublishDisposable[] arrayOfPublishDisposable = (PublishDisposable[])this.subscribers.get();
    int i = arrayOfPublishDisposable.length;
    for (int j = 0; j < i; j++) {
      arrayOfPublishDisposable[j].onNext(paramT);
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (this.subscribers.get() == TERMINATED) {
      paramDisposable.dispose();
    }
  }
  
  void remove(PublishDisposable<T> paramPublishDisposable)
  {
    PublishDisposable[] arrayOfPublishDisposable1;
    PublishDisposable[] arrayOfPublishDisposable2;
    do
    {
      arrayOfPublishDisposable1 = (PublishDisposable[])this.subscribers.get();
      if ((arrayOfPublishDisposable1 == TERMINATED) || (arrayOfPublishDisposable1 == EMPTY)) {
        break;
      }
      int i = arrayOfPublishDisposable1.length;
      int j = -1;
      int m;
      for (int k = 0;; k++)
      {
        m = j;
        if (k >= i) {
          break;
        }
        if (arrayOfPublishDisposable1[k] == paramPublishDisposable)
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
        arrayOfPublishDisposable2 = EMPTY;
      }
      else
      {
        arrayOfPublishDisposable2 = new PublishDisposable[i - 1];
        System.arraycopy(arrayOfPublishDisposable1, 0, arrayOfPublishDisposable2, 0, m);
        System.arraycopy(arrayOfPublishDisposable1, m + 1, arrayOfPublishDisposable2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfPublishDisposable1, arrayOfPublishDisposable2));
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject = new PublishDisposable(paramObserver, this);
    paramObserver.onSubscribe((Disposable)localObject);
    if (add((PublishDisposable)localObject))
    {
      if (((PublishDisposable)localObject).isDisposed()) {
        remove((PublishDisposable)localObject);
      }
    }
    else
    {
      localObject = this.error;
      if (localObject != null) {
        paramObserver.onError((Throwable)localObject);
      } else {
        paramObserver.onComplete();
      }
    }
  }
  
  static final class PublishDisposable<T>
    extends AtomicBoolean
    implements Disposable
  {
    private static final long serialVersionUID = 3562861878281475070L;
    final Observer<? super T> downstream;
    final PublishSubject<T> parent;
    
    PublishDisposable(Observer<? super T> paramObserver, PublishSubject<T> paramPublishSubject)
    {
      this.downstream = paramObserver;
      this.parent = paramPublishSubject;
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
    
    public void onComplete()
    {
      if (!get()) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get()) {
        RxJavaPlugins.onError(paramThrowable);
      } else {
        this.downstream.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (!get()) {
        this.downstream.onNext(paramT);
      }
    }
  }
}
