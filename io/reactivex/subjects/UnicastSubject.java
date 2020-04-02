package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class UnicastSubject<T>
  extends Subject<T>
{
  final boolean delayError;
  volatile boolean disposed;
  volatile boolean done;
  final AtomicReference<Observer<? super T>> downstream;
  boolean enableOperatorFusion;
  Throwable error;
  final AtomicReference<Runnable> onTerminate;
  final AtomicBoolean once;
  final SpscLinkedArrayQueue<T> queue;
  final BasicIntQueueDisposable<T> wip;
  
  UnicastSubject(int paramInt, Runnable paramRunnable)
  {
    this(paramInt, paramRunnable, true);
  }
  
  UnicastSubject(int paramInt, Runnable paramRunnable, boolean paramBoolean)
  {
    this.queue = new SpscLinkedArrayQueue(ObjectHelper.verifyPositive(paramInt, "capacityHint"));
    this.onTerminate = new AtomicReference(ObjectHelper.requireNonNull(paramRunnable, "onTerminate"));
    this.delayError = paramBoolean;
    this.downstream = new AtomicReference();
    this.once = new AtomicBoolean();
    this.wip = new UnicastQueueDisposable();
  }
  
  UnicastSubject(int paramInt, boolean paramBoolean)
  {
    this.queue = new SpscLinkedArrayQueue(ObjectHelper.verifyPositive(paramInt, "capacityHint"));
    this.onTerminate = new AtomicReference();
    this.delayError = paramBoolean;
    this.downstream = new AtomicReference();
    this.once = new AtomicBoolean();
    this.wip = new UnicastQueueDisposable();
  }
  
  @CheckReturnValue
  public static <T> UnicastSubject<T> create()
  {
    return new UnicastSubject(bufferSize(), true);
  }
  
  @CheckReturnValue
  public static <T> UnicastSubject<T> create(int paramInt)
  {
    return new UnicastSubject(paramInt, true);
  }
  
  @CheckReturnValue
  public static <T> UnicastSubject<T> create(int paramInt, Runnable paramRunnable)
  {
    return new UnicastSubject(paramInt, paramRunnable, true);
  }
  
  @CheckReturnValue
  public static <T> UnicastSubject<T> create(int paramInt, Runnable paramRunnable, boolean paramBoolean)
  {
    return new UnicastSubject(paramInt, paramRunnable, paramBoolean);
  }
  
  @CheckReturnValue
  public static <T> UnicastSubject<T> create(boolean paramBoolean)
  {
    return new UnicastSubject(bufferSize(), paramBoolean);
  }
  
  void doTerminate()
  {
    Runnable localRunnable = (Runnable)this.onTerminate.get();
    if ((localRunnable != null) && (this.onTerminate.compareAndSet(localRunnable, null))) {
      localRunnable.run();
    }
  }
  
  void drain()
  {
    if (this.wip.getAndIncrement() != 0) {
      return;
    }
    Observer localObserver = (Observer)this.downstream.get();
    int i = 1;
    for (;;)
    {
      if (localObserver != null)
      {
        if (this.enableOperatorFusion) {
          drainFused(localObserver);
        } else {
          drainNormal(localObserver);
        }
        return;
      }
      i = this.wip.addAndGet(-i);
      if (i == 0) {
        return;
      }
      localObserver = (Observer)this.downstream.get();
    }
  }
  
  void drainFused(Observer<? super T> paramObserver)
  {
    SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
    boolean bool1 = this.delayError;
    int i = 1;
    int j;
    do
    {
      if (this.disposed)
      {
        this.downstream.lazySet(null);
        localSpscLinkedArrayQueue.clear();
        return;
      }
      boolean bool2 = this.done;
      if (((bool1 ^ true)) && (bool2) && (failedFast(localSpscLinkedArrayQueue, paramObserver))) {
        return;
      }
      paramObserver.onNext(null);
      if (bool2)
      {
        errorOrComplete(paramObserver);
        return;
      }
      j = this.wip.addAndGet(-i);
      i = j;
    } while (j != 0);
  }
  
  void drainNormal(Observer<? super T> paramObserver)
  {
    SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
    boolean bool1 = this.delayError;
    int i = 1;
    int j = i;
    for (;;)
    {
      if (this.disposed)
      {
        this.downstream.lazySet(null);
        localSpscLinkedArrayQueue.clear();
        return;
      }
      boolean bool2 = this.done;
      Object localObject = this.queue.poll();
      int k;
      if (localObject == null) {
        k = 1;
      } else {
        k = 0;
      }
      int m = i;
      if (bool2)
      {
        m = i;
        if ((bool1 ^ true))
        {
          m = i;
          if (i != 0)
          {
            if (failedFast(localSpscLinkedArrayQueue, paramObserver)) {
              return;
            }
            m = 0;
          }
        }
        if (k != 0)
        {
          errorOrComplete(paramObserver);
          return;
        }
      }
      if (k != 0)
      {
        k = this.wip.addAndGet(-j);
        i = m;
        j = k;
        if (k != 0) {}
      }
      else
      {
        paramObserver.onNext(localObject);
        i = m;
      }
    }
  }
  
  void errorOrComplete(Observer<? super T> paramObserver)
  {
    this.downstream.lazySet(null);
    Throwable localThrowable = this.error;
    if (localThrowable != null) {
      paramObserver.onError(localThrowable);
    } else {
      paramObserver.onComplete();
    }
  }
  
  boolean failedFast(SimpleQueue<T> paramSimpleQueue, Observer<? super T> paramObserver)
  {
    Throwable localThrowable = this.error;
    if (localThrowable != null)
    {
      this.downstream.lazySet(null);
      paramSimpleQueue.clear();
      paramObserver.onError(localThrowable);
      return true;
    }
    return false;
  }
  
  public Throwable getThrowable()
  {
    if (this.done) {
      return this.error;
    }
    return null;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.done) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (this.downstream.get() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.done) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    if ((!this.done) && (!this.disposed))
    {
      this.done = true;
      doTerminate();
      drain();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    if ((!this.done) && (!this.disposed))
    {
      this.error = paramThrowable;
      this.done = true;
      doTerminate();
      drain();
      return;
    }
    RxJavaPlugins.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if ((!this.done) && (!this.disposed))
    {
      this.queue.offer(paramT);
      drain();
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if ((this.done) || (this.disposed)) {
      paramDisposable.dispose();
    }
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    if ((!this.once.get()) && (this.once.compareAndSet(false, true)))
    {
      paramObserver.onSubscribe(this.wip);
      this.downstream.lazySet(paramObserver);
      if (this.disposed)
      {
        this.downstream.lazySet(null);
        return;
      }
      drain();
    }
    else
    {
      EmptyDisposable.error(new IllegalStateException("Only a single observer allowed."), paramObserver);
    }
  }
  
  final class UnicastQueueDisposable
    extends BasicIntQueueDisposable<T>
  {
    private static final long serialVersionUID = 7926949470189395511L;
    
    UnicastQueueDisposable() {}
    
    public void clear()
    {
      UnicastSubject.this.queue.clear();
    }
    
    public void dispose()
    {
      if (!UnicastSubject.this.disposed)
      {
        UnicastSubject.this.disposed = true;
        UnicastSubject.this.doTerminate();
        UnicastSubject.this.downstream.lazySet(null);
        if (UnicastSubject.this.wip.getAndIncrement() == 0)
        {
          UnicastSubject.this.downstream.lazySet(null);
          UnicastSubject.this.queue.clear();
        }
      }
    }
    
    public boolean isDisposed()
    {
      return UnicastSubject.this.disposed;
    }
    
    public boolean isEmpty()
    {
      return UnicastSubject.this.queue.isEmpty();
    }
    
    public T poll()
      throws Exception
    {
      return UnicastSubject.this.queue.poll();
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        UnicastSubject.this.enableOperatorFusion = true;
        return 2;
      }
      return 0;
    }
  }
}
