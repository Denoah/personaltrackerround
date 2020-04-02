package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublish<T>
  extends ConnectableObservable<T>
  implements HasUpstreamObservableSource<T>
{
  final AtomicReference<PublishObserver<T>> current;
  final ObservableSource<T> onSubscribe;
  final ObservableSource<T> source;
  
  private ObservablePublish(ObservableSource<T> paramObservableSource1, ObservableSource<T> paramObservableSource2, AtomicReference<PublishObserver<T>> paramAtomicReference)
  {
    this.onSubscribe = paramObservableSource1;
    this.source = paramObservableSource2;
    this.current = paramAtomicReference;
  }
  
  public static <T> ConnectableObservable<T> create(ObservableSource<T> paramObservableSource)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    return RxJavaPlugins.onAssembly(new ObservablePublish(new PublishSource(localAtomicReference), paramObservableSource, localAtomicReference));
  }
  
  public void connect(Consumer<? super Disposable> paramConsumer)
  {
    PublishObserver localPublishObserver1;
    PublishObserver localPublishObserver2;
    do
    {
      localPublishObserver1 = (PublishObserver)this.current.get();
      if (localPublishObserver1 != null)
      {
        localPublishObserver2 = localPublishObserver1;
        if (!localPublishObserver1.isDisposed()) {
          break;
        }
      }
      localPublishObserver2 = new PublishObserver(this.current);
    } while (!this.current.compareAndSet(localPublishObserver1, localPublishObserver2));
    boolean bool = localPublishObserver2.shouldConnect.get();
    int i = 1;
    if ((bool) || (!localPublishObserver2.shouldConnect.compareAndSet(false, true))) {
      i = 0;
    }
    try
    {
      paramConsumer.accept(localPublishObserver2);
      if (i != 0) {
        this.source.subscribe(localPublishObserver2);
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(paramConsumer);
    }
  }
  
  public ObservableSource<T> source()
  {
    return this.source;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.onSubscribe.subscribe(paramObserver);
  }
  
  static final class InnerDisposable<T>
    extends AtomicReference<Object>
    implements Disposable
  {
    private static final long serialVersionUID = -1100270633763673112L;
    final Observer<? super T> child;
    
    InnerDisposable(Observer<? super T> paramObserver)
    {
      this.child = paramObserver;
    }
    
    public void dispose()
    {
      Object localObject = getAndSet(this);
      if ((localObject != null) && (localObject != this)) {
        ((ObservablePublish.PublishObserver)localObject).remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == this) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void setParent(ObservablePublish.PublishObserver<T> paramPublishObserver)
    {
      if (!compareAndSet(null, paramPublishObserver)) {
        paramPublishObserver.remove(this);
      }
    }
  }
  
  static final class PublishObserver<T>
    implements Observer<T>, Disposable
  {
    static final ObservablePublish.InnerDisposable[] EMPTY = new ObservablePublish.InnerDisposable[0];
    static final ObservablePublish.InnerDisposable[] TERMINATED = new ObservablePublish.InnerDisposable[0];
    final AtomicReference<PublishObserver<T>> current;
    final AtomicReference<ObservablePublish.InnerDisposable<T>[]> observers = new AtomicReference(EMPTY);
    final AtomicBoolean shouldConnect;
    final AtomicReference<Disposable> upstream = new AtomicReference();
    
    PublishObserver(AtomicReference<PublishObserver<T>> paramAtomicReference)
    {
      this.current = paramAtomicReference;
      this.shouldConnect = new AtomicBoolean();
    }
    
    boolean add(ObservablePublish.InnerDisposable<T> paramInnerDisposable)
    {
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable1;
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable2;
      do
      {
        arrayOfInnerDisposable1 = (ObservablePublish.InnerDisposable[])this.observers.get();
        if (arrayOfInnerDisposable1 == TERMINATED) {
          return false;
        }
        int i = arrayOfInnerDisposable1.length;
        arrayOfInnerDisposable2 = new ObservablePublish.InnerDisposable[i + 1];
        System.arraycopy(arrayOfInnerDisposable1, 0, arrayOfInnerDisposable2, 0, i);
        arrayOfInnerDisposable2[i] = paramInnerDisposable;
      } while (!this.observers.compareAndSet(arrayOfInnerDisposable1, arrayOfInnerDisposable2));
      return true;
    }
    
    public void dispose()
    {
      if ((ObservablePublish.InnerDisposable[])this.observers.getAndSet(TERMINATED) != TERMINATED)
      {
        this.current.compareAndSet(this, null);
        DisposableHelper.dispose(this.upstream);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.observers.get() == TERMINATED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      this.current.compareAndSet(this, null);
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable = (ObservablePublish.InnerDisposable[])this.observers.getAndSet(TERMINATED);
      int i = arrayOfInnerDisposable.length;
      for (int j = 0; j < i; j++) {
        arrayOfInnerDisposable[j].child.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.current.compareAndSet(this, null);
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable = (ObservablePublish.InnerDisposable[])this.observers.getAndSet(TERMINATED);
      if (arrayOfInnerDisposable.length != 0)
      {
        int i = arrayOfInnerDisposable.length;
        for (int j = 0; j < i; j++) {
          arrayOfInnerDisposable[j].child.onError(paramThrowable);
        }
      }
      RxJavaPlugins.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable = (ObservablePublish.InnerDisposable[])this.observers.get();
      int i = arrayOfInnerDisposable.length;
      for (int j = 0; j < i; j++) {
        arrayOfInnerDisposable[j].child.onNext(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    void remove(ObservablePublish.InnerDisposable<T> paramInnerDisposable)
    {
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable1;
      ObservablePublish.InnerDisposable[] arrayOfInnerDisposable2;
      do
      {
        arrayOfInnerDisposable1 = (ObservablePublish.InnerDisposable[])this.observers.get();
        int i = arrayOfInnerDisposable1.length;
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
          if (arrayOfInnerDisposable1[k].equals(paramInnerDisposable))
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
          arrayOfInnerDisposable2 = EMPTY;
        }
        else
        {
          arrayOfInnerDisposable2 = new ObservablePublish.InnerDisposable[i - 1];
          System.arraycopy(arrayOfInnerDisposable1, 0, arrayOfInnerDisposable2, 0, m);
          System.arraycopy(arrayOfInnerDisposable1, m + 1, arrayOfInnerDisposable2, m, i - m - 1);
        }
      } while (!this.observers.compareAndSet(arrayOfInnerDisposable1, arrayOfInnerDisposable2));
    }
  }
  
  static final class PublishSource<T>
    implements ObservableSource<T>
  {
    private final AtomicReference<ObservablePublish.PublishObserver<T>> curr;
    
    PublishSource(AtomicReference<ObservablePublish.PublishObserver<T>> paramAtomicReference)
    {
      this.curr = paramAtomicReference;
    }
    
    public void subscribe(Observer<? super T> paramObserver)
    {
      ObservablePublish.InnerDisposable localInnerDisposable = new ObservablePublish.InnerDisposable(paramObserver);
      paramObserver.onSubscribe(localInnerDisposable);
      do
      {
        ObservablePublish.PublishObserver localPublishObserver;
        do
        {
          localPublishObserver = (ObservablePublish.PublishObserver)this.curr.get();
          if (localPublishObserver != null)
          {
            paramObserver = localPublishObserver;
            if (!localPublishObserver.isDisposed()) {
              break;
            }
          }
          paramObserver = new ObservablePublish.PublishObserver(this.curr);
        } while (!this.curr.compareAndSet(localPublishObserver, paramObserver));
      } while (!paramObserver.add(localInnerDisposable));
      localInnerDisposable.setParent(paramObserver);
    }
  }
}
