package io.reactivex.subjects;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableSubject
  extends Completable
  implements CompletableObserver
{
  static final CompletableDisposable[] EMPTY = new CompletableDisposable[0];
  static final CompletableDisposable[] TERMINATED = new CompletableDisposable[0];
  Throwable error;
  final AtomicReference<CompletableDisposable[]> observers = new AtomicReference(EMPTY);
  final AtomicBoolean once = new AtomicBoolean();
  
  CompletableSubject() {}
  
  @CheckReturnValue
  public static CompletableSubject create()
  {
    return new CompletableSubject();
  }
  
  boolean add(CompletableDisposable paramCompletableDisposable)
  {
    CompletableDisposable[] arrayOfCompletableDisposable1;
    CompletableDisposable[] arrayOfCompletableDisposable2;
    do
    {
      arrayOfCompletableDisposable1 = (CompletableDisposable[])this.observers.get();
      if (arrayOfCompletableDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfCompletableDisposable1.length;
      arrayOfCompletableDisposable2 = new CompletableDisposable[i + 1];
      System.arraycopy(arrayOfCompletableDisposable1, 0, arrayOfCompletableDisposable2, 0, i);
      arrayOfCompletableDisposable2[i] = paramCompletableDisposable;
    } while (!this.observers.compareAndSet(arrayOfCompletableDisposable1, arrayOfCompletableDisposable2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    if (this.observers.get() == TERMINATED) {
      return this.error;
    }
    return null;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.observers.get() == TERMINATED) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((CompletableDisposable[])this.observers.get()).length != 0) {
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
  
  int observerCount()
  {
    return ((CompletableDisposable[])this.observers.get()).length;
  }
  
  public void onComplete()
  {
    Object localObject = this.once;
    int i = 0;
    if (((AtomicBoolean)localObject).compareAndSet(false, true))
    {
      localObject = (CompletableDisposable[])this.observers.getAndSet(TERMINATED);
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
      localObject = (CompletableDisposable[])this.observers.getAndSet(TERMINATED);
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
  
  void remove(CompletableDisposable paramCompletableDisposable)
  {
    CompletableDisposable[] arrayOfCompletableDisposable1;
    CompletableDisposable[] arrayOfCompletableDisposable2;
    do
    {
      arrayOfCompletableDisposable1 = (CompletableDisposable[])this.observers.get();
      int i = arrayOfCompletableDisposable1.length;
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
        if (arrayOfCompletableDisposable1[k] == paramCompletableDisposable)
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
        arrayOfCompletableDisposable2 = EMPTY;
      }
      else
      {
        arrayOfCompletableDisposable2 = new CompletableDisposable[i - 1];
        System.arraycopy(arrayOfCompletableDisposable1, 0, arrayOfCompletableDisposable2, 0, m);
        System.arraycopy(arrayOfCompletableDisposable1, m + 1, arrayOfCompletableDisposable2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfCompletableDisposable1, arrayOfCompletableDisposable2));
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Object localObject = new CompletableDisposable(paramCompletableObserver, this);
    paramCompletableObserver.onSubscribe((Disposable)localObject);
    if (add((CompletableDisposable)localObject))
    {
      if (((CompletableDisposable)localObject).isDisposed()) {
        remove((CompletableDisposable)localObject);
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
  
  static final class CompletableDisposable
    extends AtomicReference<CompletableSubject>
    implements Disposable
  {
    private static final long serialVersionUID = -7650903191002190468L;
    final CompletableObserver downstream;
    
    CompletableDisposable(CompletableObserver paramCompletableObserver, CompletableSubject paramCompletableSubject)
    {
      this.downstream = paramCompletableObserver;
      lazySet(paramCompletableSubject);
    }
    
    public void dispose()
    {
      CompletableSubject localCompletableSubject = (CompletableSubject)getAndSet(null);
      if (localCompletableSubject != null) {
        localCompletableSubject.remove(this);
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
