package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableMergeWithSingle<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final SingleSource<? extends T> other;
  
  public ObservableMergeWithSingle(Observable<T> paramObservable, SingleSource<? extends T> paramSingleSource)
  {
    super(paramObservable);
    this.other = paramSingleSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    MergeWithObserver localMergeWithObserver = new MergeWithObserver(paramObserver);
    paramObserver.onSubscribe(localMergeWithObserver);
    this.source.subscribe(localMergeWithObserver);
    this.other.subscribe(localMergeWithObserver.otherObserver);
  }
  
  static final class MergeWithObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
    static final int OTHER_STATE_HAS_VALUE = 1;
    private static final long serialVersionUID = -4592979584110982903L;
    volatile boolean disposed;
    final Observer<? super T> downstream;
    final AtomicThrowable error;
    final AtomicReference<Disposable> mainDisposable;
    volatile boolean mainDone;
    final OtherObserver<T> otherObserver;
    volatile int otherState;
    volatile SimplePlainQueue<T> queue;
    T singleItem;
    
    MergeWithObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
      this.mainDisposable = new AtomicReference();
      this.otherObserver = new OtherObserver(this);
      this.error = new AtomicThrowable();
    }
    
    public void dispose()
    {
      this.disposed = true;
      DisposableHelper.dispose(this.mainDisposable);
      DisposableHelper.dispose(this.otherObserver);
      if (getAndIncrement() == 0)
      {
        this.queue = null;
        this.singleItem = null;
      }
    }
    
    void drain()
    {
      if (getAndIncrement() == 0) {
        drainLoop();
      }
    }
    
    void drainLoop()
    {
      Observer localObserver = this.downstream;
      int i = 1;
      for (;;)
      {
        if (this.disposed)
        {
          this.singleItem = null;
          this.queue = null;
          return;
        }
        if (this.error.get() != null)
        {
          this.singleItem = null;
          this.queue = null;
          localObserver.onError(this.error.terminate());
          return;
        }
        int j = this.otherState;
        int k = j;
        if (j == 1)
        {
          localObject = this.singleItem;
          this.singleItem = null;
          this.otherState = 2;
          localObserver.onNext(localObject);
          k = 2;
        }
        boolean bool = this.mainDone;
        Object localObject = this.queue;
        if (localObject != null) {
          localObject = ((SimplePlainQueue)localObject).poll();
        } else {
          localObject = null;
        }
        if (localObject == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0) && (k == 2))
        {
          this.queue = null;
          localObserver.onComplete();
          return;
        }
        if (j != 0)
        {
          k = addAndGet(-i);
          i = k;
          if (k != 0) {}
        }
        else
        {
          localObserver.onNext(localObject);
        }
      }
    }
    
    SimplePlainQueue<T> getOrCreateQueue()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Object localObject = localSimplePlainQueue;
      if (localSimplePlainQueue == null)
      {
        localObject = new SpscLinkedArrayQueue(Observable.bufferSize());
        this.queue = ((SimplePlainQueue)localObject);
      }
      return localObject;
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.mainDisposable.get());
    }
    
    public void onComplete()
    {
      this.mainDone = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
      {
        DisposableHelper.dispose(this.mainDisposable);
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (compareAndSet(0, 1))
      {
        this.downstream.onNext(paramT);
        if (decrementAndGet() != 0) {}
      }
      else
      {
        getOrCreateQueue().offer(paramT);
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.mainDisposable, paramDisposable);
    }
    
    void otherError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
      {
        DisposableHelper.dispose(this.mainDisposable);
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void otherSuccess(T paramT)
    {
      if (compareAndSet(0, 1))
      {
        this.downstream.onNext(paramT);
        this.otherState = 2;
      }
      else
      {
        this.singleItem = paramT;
        this.otherState = 1;
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    static final class OtherObserver<T>
      extends AtomicReference<Disposable>
      implements SingleObserver<T>
    {
      private static final long serialVersionUID = -2935427570954647017L;
      final ObservableMergeWithSingle.MergeWithObserver<T> parent;
      
      OtherObserver(ObservableMergeWithSingle.MergeWithObserver<T> paramMergeWithObserver)
      {
        this.parent = paramMergeWithObserver;
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.otherError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
      
      public void onSuccess(T paramT)
      {
        this.parent.otherSuccess(paramT);
      }
    }
  }
}
