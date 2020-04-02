package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundary<T, B>
  extends AbstractObservableWithUpstream<T, Observable<T>>
{
  final int capacityHint;
  final ObservableSource<B> other;
  
  public ObservableWindowBoundary(ObservableSource<T> paramObservableSource, ObservableSource<B> paramObservableSource1, int paramInt)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
    this.capacityHint = paramInt;
  }
  
  public void subscribeActual(Observer<? super Observable<T>> paramObserver)
  {
    WindowBoundaryMainObserver localWindowBoundaryMainObserver = new WindowBoundaryMainObserver(paramObserver, this.capacityHint);
    paramObserver.onSubscribe(localWindowBoundaryMainObserver);
    this.other.subscribe(localWindowBoundaryMainObserver.boundaryObserver);
    this.source.subscribe(localWindowBoundaryMainObserver);
  }
  
  static final class WindowBoundaryInnerObserver<T, B>
    extends DisposableObserver<B>
  {
    boolean done;
    final ObservableWindowBoundary.WindowBoundaryMainObserver<T, B> parent;
    
    WindowBoundaryInnerObserver(ObservableWindowBoundary.WindowBoundaryMainObserver<T, B> paramWindowBoundaryMainObserver)
    {
      this.parent = paramWindowBoundaryMainObserver;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.parent.innerComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      if (this.done) {
        return;
      }
      this.parent.innerNext();
    }
  }
  
  static final class WindowBoundaryMainObserver<T, B>
    extends AtomicInteger
    implements Observer<T>, Disposable, Runnable
  {
    static final Object NEXT_WINDOW = new Object();
    private static final long serialVersionUID = 2233020065421370272L;
    final ObservableWindowBoundary.WindowBoundaryInnerObserver<T, B> boundaryObserver;
    final int capacityHint;
    volatile boolean done;
    final Observer<? super Observable<T>> downstream;
    final AtomicThrowable errors;
    final MpscLinkedQueue<Object> queue;
    final AtomicBoolean stopWindows;
    final AtomicReference<Disposable> upstream;
    UnicastSubject<T> window;
    final AtomicInteger windows;
    
    WindowBoundaryMainObserver(Observer<? super Observable<T>> paramObserver, int paramInt)
    {
      this.downstream = paramObserver;
      this.capacityHint = paramInt;
      this.boundaryObserver = new ObservableWindowBoundary.WindowBoundaryInnerObserver(this);
      this.upstream = new AtomicReference();
      this.windows = new AtomicInteger(1);
      this.queue = new MpscLinkedQueue();
      this.errors = new AtomicThrowable();
      this.stopWindows = new AtomicBoolean();
    }
    
    public void dispose()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        this.boundaryObserver.dispose();
        if (this.windows.decrementAndGet() == 0) {
          DisposableHelper.dispose(this.upstream);
        }
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = this.downstream;
      Object localObject1 = this.queue;
      AtomicThrowable localAtomicThrowable = this.errors;
      int i = 1;
      for (;;)
      {
        if (this.windows.get() == 0)
        {
          ((MpscLinkedQueue)localObject1).clear();
          this.window = null;
          return;
        }
        UnicastSubject localUnicastSubject = this.window;
        boolean bool = this.done;
        if ((bool) && (localAtomicThrowable.get() != null))
        {
          ((MpscLinkedQueue)localObject1).clear();
          localObject1 = localAtomicThrowable.terminate();
          if (localUnicastSubject != null)
          {
            this.window = null;
            localUnicastSubject.onError((Throwable)localObject1);
          }
          localObserver.onError((Throwable)localObject1);
          return;
        }
        Object localObject2 = ((MpscLinkedQueue)localObject1).poll();
        int j;
        if (localObject2 == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0))
        {
          localObject1 = localAtomicThrowable.terminate();
          if (localObject1 == null)
          {
            if (localUnicastSubject != null)
            {
              this.window = null;
              localUnicastSubject.onComplete();
            }
            localObserver.onComplete();
          }
          else
          {
            if (localUnicastSubject != null)
            {
              this.window = null;
              localUnicastSubject.onError((Throwable)localObject1);
            }
            localObserver.onError((Throwable)localObject1);
          }
          return;
        }
        if (j != 0)
        {
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else if (localObject2 != NEXT_WINDOW)
        {
          localUnicastSubject.onNext(localObject2);
        }
        else
        {
          if (localUnicastSubject != null)
          {
            this.window = null;
            localUnicastSubject.onComplete();
          }
          if (!this.stopWindows.get())
          {
            localUnicastSubject = UnicastSubject.create(this.capacityHint, this);
            this.window = localUnicastSubject;
            this.windows.getAndIncrement();
            localObserver.onNext(localUnicastSubject);
          }
        }
      }
    }
    
    void innerComplete()
    {
      DisposableHelper.dispose(this.upstream);
      this.done = true;
      drain();
    }
    
    void innerError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.upstream);
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerNext()
    {
      this.queue.offer(NEXT_WINDOW);
      drain();
    }
    
    public boolean isDisposed()
    {
      return this.stopWindows.get();
    }
    
    public void onComplete()
    {
      this.boundaryObserver.dispose();
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.boundaryObserver.dispose();
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      drain();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this.upstream, paramDisposable)) {
        innerNext();
      }
    }
    
    public void run()
    {
      if (this.windows.decrementAndGet() == 0) {
        DisposableHelper.dispose(this.upstream);
      }
    }
  }
}
