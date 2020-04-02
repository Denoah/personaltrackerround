package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCreate<T>
  extends Observable<T>
{
  final ObservableOnSubscribe<T> source;
  
  public ObservableCreate(ObservableOnSubscribe<T> paramObservableOnSubscribe)
  {
    this.source = paramObservableOnSubscribe;
  }
  
  /* Error */
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    // Byte code:
    //   0: new 7	io/reactivex/internal/operators/observable/ObservableCreate$CreateEmitter
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 28	io/reactivex/internal/operators/observable/ObservableCreate$CreateEmitter:<init>	(Lio/reactivex/Observer;)V
    //   8: astore_2
    //   9: aload_1
    //   10: aload_2
    //   11: invokeinterface 34 2 0
    //   16: aload_0
    //   17: getfield 21	io/reactivex/internal/operators/observable/ObservableCreate:source	Lio/reactivex/ObservableOnSubscribe;
    //   20: aload_2
    //   21: invokeinterface 40 2 0
    //   26: goto +13 -> 39
    //   29: astore_1
    //   30: aload_1
    //   31: invokestatic 46	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_2
    //   35: aload_1
    //   36: invokevirtual 49	io/reactivex/internal/operators/observable/ObservableCreate$CreateEmitter:onError	(Ljava/lang/Throwable;)V
    //   39: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	ObservableCreate
    //   0	40	1	paramObserver	Observer<? super T>
    //   8	27	2	localCreateEmitter	CreateEmitter
    // Exception table:
    //   from	to	target	type
    //   16	26	29	finally
  }
  
  static final class CreateEmitter<T>
    extends AtomicReference<Disposable>
    implements ObservableEmitter<T>, Disposable
  {
    private static final long serialVersionUID = -3434801548987643227L;
    final Observer<? super T> observer;
    
    CreateEmitter(Observer<? super T> paramObserver)
    {
      this.observer = paramObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      if (!isDisposed()) {
        try
        {
          this.observer.onComplete();
          dispose();
        }
        finally
        {
          dispose();
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (paramT == null)
      {
        onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        return;
      }
      if (!isDisposed()) {
        this.observer.onNext(paramT);
      }
    }
    
    public ObservableEmitter<T> serialize()
    {
      return new ObservableCreate.SerializedEmitter(this);
    }
    
    public void setCancellable(Cancellable paramCancellable)
    {
      setDisposable(new CancellableDisposable(paramCancellable));
    }
    
    public void setDisposable(Disposable paramDisposable)
    {
      DisposableHelper.set(this, paramDisposable);
    }
    
    public String toString()
    {
      return String.format("%s{%s}", new Object[] { getClass().getSimpleName(), super.toString() });
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      Object localObject = paramThrowable;
      if (paramThrowable == null) {
        localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      }
      if (!isDisposed()) {
        try
        {
          this.observer.onError((Throwable)localObject);
          return true;
        }
        finally
        {
          dispose();
        }
      }
      return false;
    }
  }
  
  static final class SerializedEmitter<T>
    extends AtomicInteger
    implements ObservableEmitter<T>
  {
    private static final long serialVersionUID = 4883307006032401862L;
    volatile boolean done;
    final ObservableEmitter<T> emitter;
    final AtomicThrowable error;
    final SpscLinkedArrayQueue<T> queue;
    
    SerializedEmitter(ObservableEmitter<T> paramObservableEmitter)
    {
      this.emitter = paramObservableEmitter;
      this.error = new AtomicThrowable();
      this.queue = new SpscLinkedArrayQueue(16);
    }
    
    void drain()
    {
      if (getAndIncrement() == 0) {
        drainLoop();
      }
    }
    
    void drainLoop()
    {
      ObservableEmitter localObservableEmitter = this.emitter;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      AtomicThrowable localAtomicThrowable = this.error;
      int i = 1;
      for (;;)
      {
        if (localObservableEmitter.isDisposed())
        {
          localSpscLinkedArrayQueue.clear();
          return;
        }
        if (localAtomicThrowable.get() != null)
        {
          localSpscLinkedArrayQueue.clear();
          localObservableEmitter.onError(localAtomicThrowable.terminate());
          return;
        }
        boolean bool = this.done;
        Object localObject = localSpscLinkedArrayQueue.poll();
        int j;
        if (localObject == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0))
        {
          localObservableEmitter.onComplete();
          return;
        }
        if (j != 0)
        {
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else
        {
          localObservableEmitter.onNext(localObject);
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.emitter.isDisposed();
    }
    
    public void onComplete()
    {
      if ((!this.emitter.isDisposed()) && (!this.done))
      {
        this.done = true;
        drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if ((!this.emitter.isDisposed()) && (!this.done))
      {
        if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
          return;
        }
        if ((get() == 0) && (compareAndSet(0, 1)))
        {
          this.emitter.onNext(paramT);
          if (decrementAndGet() != 0) {
            break label99;
          }
        }
        synchronized (this.queue)
        {
          ???.offer(paramT);
          if (getAndIncrement() != 0) {
            return;
          }
          label99:
          drainLoop();
          return;
        }
      }
    }
    
    public ObservableEmitter<T> serialize()
    {
      return this;
    }
    
    public void setCancellable(Cancellable paramCancellable)
    {
      this.emitter.setCancellable(paramCancellable);
    }
    
    public void setDisposable(Disposable paramDisposable)
    {
      this.emitter.setDisposable(paramDisposable);
    }
    
    public String toString()
    {
      return this.emitter.toString();
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      if ((!this.emitter.isDisposed()) && (!this.done))
      {
        Object localObject = paramThrowable;
        if (paramThrowable == null) {
          localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (this.error.addThrowable((Throwable)localObject))
        {
          this.done = true;
          drain();
          return true;
        }
      }
      return false;
    }
  }
}
