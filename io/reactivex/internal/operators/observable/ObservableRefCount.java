package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRefCount<T>
  extends Observable<T>
{
  RefConnection connection;
  final int n;
  final Scheduler scheduler;
  final ConnectableObservable<T> source;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableRefCount(ConnectableObservable<T> paramConnectableObservable)
  {
    this(paramConnectableObservable, 1, 0L, TimeUnit.NANOSECONDS, null);
  }
  
  public ObservableRefCount(ConnectableObservable<T> paramConnectableObservable, int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.source = paramConnectableObservable;
    this.n = paramInt;
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  void cancel(RefConnection paramRefConnection)
  {
    try
    {
      if ((this.connection != null) && (this.connection == paramRefConnection))
      {
        long l = paramRefConnection.subscriberCount - 1L;
        paramRefConnection.subscriberCount = l;
        if ((l == 0L) && (paramRefConnection.connected))
        {
          if (this.timeout == 0L)
          {
            timeout(paramRefConnection);
            return;
          }
          SequentialDisposable localSequentialDisposable = new io/reactivex/internal/disposables/SequentialDisposable;
          localSequentialDisposable.<init>();
          paramRefConnection.timer = localSequentialDisposable;
          localSequentialDisposable.replace(this.scheduler.scheduleDirect(paramRefConnection, this.timeout, this.unit));
          return;
        }
        return;
      }
      return;
    }
    finally {}
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    try
    {
      RefConnection localRefConnection1 = this.connection;
      RefConnection localRefConnection2 = localRefConnection1;
      if (localRefConnection1 == null)
      {
        localRefConnection2 = new io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
        localRefConnection2.<init>(this);
        this.connection = localRefConnection2;
      }
      long l = localRefConnection2.subscriberCount;
      if ((l == 0L) && (localRefConnection2.timer != null)) {
        localRefConnection2.timer.dispose();
      }
      l += 1L;
      localRefConnection2.subscriberCount = l;
      boolean bool = localRefConnection2.connected;
      int i = 1;
      if ((!bool) && (l == this.n)) {
        localRefConnection2.connected = true;
      } else {
        i = 0;
      }
      this.source.subscribe(new RefCountObserver(paramObserver, this, localRefConnection2));
      if (i != 0) {
        this.source.connect(localRefConnection2);
      }
      return;
    }
    finally {}
  }
  
  void terminated(RefConnection paramRefConnection)
  {
    try
    {
      if ((this.connection != null) && (this.connection == paramRefConnection))
      {
        this.connection = null;
        if (paramRefConnection.timer != null) {
          paramRefConnection.timer.dispose();
        }
      }
      long l = paramRefConnection.subscriberCount - 1L;
      paramRefConnection.subscriberCount = l;
      if (l == 0L) {
        if ((this.source instanceof Disposable)) {
          ((Disposable)this.source).dispose();
        } else if ((this.source instanceof ResettableConnectable)) {
          ((ResettableConnectable)this.source).resetIf((Disposable)paramRefConnection.get());
        }
      }
      return;
    }
    finally {}
  }
  
  void timeout(RefConnection paramRefConnection)
  {
    try
    {
      if ((paramRefConnection.subscriberCount == 0L) && (paramRefConnection == this.connection))
      {
        this.connection = null;
        Disposable localDisposable = (Disposable)paramRefConnection.get();
        DisposableHelper.dispose(paramRefConnection);
        if ((this.source instanceof Disposable)) {
          ((Disposable)this.source).dispose();
        } else if ((this.source instanceof ResettableConnectable)) {
          if (localDisposable == null) {
            paramRefConnection.disconnectedEarly = true;
          } else {
            ((ResettableConnectable)this.source).resetIf(localDisposable);
          }
        }
      }
      return;
    }
    finally {}
  }
  
  static final class RefConnection
    extends AtomicReference<Disposable>
    implements Runnable, Consumer<Disposable>
  {
    private static final long serialVersionUID = -4552101107598366241L;
    boolean connected;
    boolean disconnectedEarly;
    final ObservableRefCount<?> parent;
    long subscriberCount;
    Disposable timer;
    
    RefConnection(ObservableRefCount<?> paramObservableRefCount)
    {
      this.parent = paramObservableRefCount;
    }
    
    public void accept(Disposable paramDisposable)
      throws Exception
    {
      DisposableHelper.replace(this, paramDisposable);
      synchronized (this.parent)
      {
        if (this.disconnectedEarly) {
          ((ResettableConnectable)this.parent.source).resetIf(paramDisposable);
        }
        return;
      }
    }
    
    public void run()
    {
      this.parent.timeout(this);
    }
  }
  
  static final class RefCountObserver<T>
    extends AtomicBoolean
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -7419642935409022375L;
    final ObservableRefCount.RefConnection connection;
    final Observer<? super T> downstream;
    final ObservableRefCount<T> parent;
    Disposable upstream;
    
    RefCountObserver(Observer<? super T> paramObserver, ObservableRefCount<T> paramObservableRefCount, ObservableRefCount.RefConnection paramRefConnection)
    {
      this.downstream = paramObserver;
      this.parent = paramObservableRefCount;
      this.connection = paramRefConnection;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      if (compareAndSet(false, true)) {
        this.parent.cancel(this.connection);
      }
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (compareAndSet(false, true))
      {
        this.parent.terminated(this.connection);
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (compareAndSet(false, true))
      {
        this.parent.terminated(this.connection);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
