package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMap<T, U>
  extends AbstractObservableWithUpstream<T, U>
{
  final int bufferSize;
  final boolean delayErrors;
  final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
  final int maxConcurrency;
  
  public ObservableFlatMap(ObservableSource<T> paramObservableSource, Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt1;
    this.bufferSize = paramInt2;
  }
  
  public void subscribeActual(Observer<? super U> paramObserver)
  {
    if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, paramObserver, this.mapper)) {
      return;
    }
    this.source.subscribe(new MergeObserver(paramObserver, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
  }
  
  static final class InnerObserver<T, U>
    extends AtomicReference<Disposable>
    implements Observer<U>
  {
    private static final long serialVersionUID = -4606175640614850599L;
    volatile boolean done;
    int fusionMode;
    final long id;
    final ObservableFlatMap.MergeObserver<T, U> parent;
    volatile SimpleQueue<U> queue;
    
    InnerObserver(ObservableFlatMap.MergeObserver<T, U> paramMergeObserver, long paramLong)
    {
      this.id = paramLong;
      this.parent = paramMergeObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.parent.errors.addThrowable(paramThrowable))
      {
        if (!this.parent.delayErrors) {
          this.parent.disposeAll();
        }
        this.done = true;
        this.parent.drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(U paramU)
    {
      if (this.fusionMode == 0) {
        this.parent.tryEmit(paramU, this);
      } else {
        this.parent.drain();
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if ((DisposableHelper.setOnce(this, paramDisposable)) && ((paramDisposable instanceof QueueDisposable)))
      {
        paramDisposable = (QueueDisposable)paramDisposable;
        int i = paramDisposable.requestFusion(7);
        if (i == 1)
        {
          this.fusionMode = i;
          this.queue = paramDisposable;
          this.done = true;
          this.parent.drain();
          return;
        }
        if (i == 2)
        {
          this.fusionMode = i;
          this.queue = paramDisposable;
        }
      }
    }
  }
  
  static final class MergeObserver<T, U>
    extends AtomicInteger
    implements Disposable, Observer<T>
  {
    static final ObservableFlatMap.InnerObserver<?, ?>[] CANCELLED = new ObservableFlatMap.InnerObserver[0];
    static final ObservableFlatMap.InnerObserver<?, ?>[] EMPTY = new ObservableFlatMap.InnerObserver[0];
    private static final long serialVersionUID = -2117620485640801370L;
    final int bufferSize;
    volatile boolean cancelled;
    final boolean delayErrors;
    volatile boolean done;
    final Observer<? super U> downstream;
    final AtomicThrowable errors = new AtomicThrowable();
    long lastId;
    int lastIndex;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
    final int maxConcurrency;
    final AtomicReference<ObservableFlatMap.InnerObserver<?, ?>[]> observers;
    volatile SimplePlainQueue<U> queue;
    Queue<ObservableSource<? extends U>> sources;
    long uniqueId;
    Disposable upstream;
    int wip;
    
    MergeObserver(Observer<? super U> paramObserver, Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.maxConcurrency = paramInt1;
      this.bufferSize = paramInt2;
      if (paramInt1 != Integer.MAX_VALUE) {
        this.sources = new ArrayDeque(paramInt1);
      }
      this.observers = new AtomicReference(EMPTY);
    }
    
    boolean addInner(ObservableFlatMap.InnerObserver<T, U> paramInnerObserver)
    {
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver1;
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver2;
      do
      {
        arrayOfInnerObserver1 = (ObservableFlatMap.InnerObserver[])this.observers.get();
        if (arrayOfInnerObserver1 == CANCELLED)
        {
          paramInnerObserver.dispose();
          return false;
        }
        int i = arrayOfInnerObserver1.length;
        arrayOfInnerObserver2 = new ObservableFlatMap.InnerObserver[i + 1];
        System.arraycopy(arrayOfInnerObserver1, 0, arrayOfInnerObserver2, 0, i);
        arrayOfInnerObserver2[i] = paramInnerObserver;
      } while (!this.observers.compareAndSet(arrayOfInnerObserver1, arrayOfInnerObserver2));
      return true;
    }
    
    boolean checkTerminate()
    {
      if (this.cancelled) {
        return true;
      }
      Throwable localThrowable = (Throwable)this.errors.get();
      if ((!this.delayErrors) && (localThrowable != null))
      {
        disposeAll();
        localThrowable = this.errors.terminate();
        if (localThrowable != ExceptionHelper.TERMINATED) {
          this.downstream.onError(localThrowable);
        }
        return true;
      }
      return false;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        if (disposeAll())
        {
          Throwable localThrowable = this.errors.terminate();
          if ((localThrowable != null) && (localThrowable != ExceptionHelper.TERMINATED)) {
            RxJavaPlugins.onError(localThrowable);
          }
        }
      }
    }
    
    boolean disposeAll()
    {
      this.upstream.dispose();
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver1 = (ObservableFlatMap.InnerObserver[])this.observers.get();
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver2 = CANCELLED;
      int i = 0;
      if (arrayOfInnerObserver1 != arrayOfInnerObserver2)
      {
        arrayOfInnerObserver1 = (ObservableFlatMap.InnerObserver[])this.observers.getAndSet(arrayOfInnerObserver2);
        if (arrayOfInnerObserver1 != CANCELLED)
        {
          int j = arrayOfInnerObserver1.length;
          while (i < j)
          {
            arrayOfInnerObserver1[i].dispose();
            i++;
          }
          return true;
        }
      }
      return false;
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
      int k;
      label555:
      do
      {
        if (checkTerminate()) {
          return;
        }
        Object localObject3 = this.queue;
        if (localObject3 != null) {
          for (;;)
          {
            if (checkTerminate()) {
              return;
            }
            localObject4 = ((SimplePlainQueue)localObject3).poll();
            if (localObject4 == null) {
              break;
            }
            localObserver.onNext(localObject4);
          }
        }
        boolean bool = this.done;
        Object localObject4 = this.queue;
        localObject3 = (ObservableFlatMap.InnerObserver[])this.observers.get();
        int j = localObject3.length;
        k = this.maxConcurrency;
        int m = 0;
        if (k != Integer.MAX_VALUE) {
          try
          {
            k = this.sources.size();
          }
          finally {}
        } else {
          k = 0;
        }
        if ((bool) && ((localObject4 == null) || (((SimplePlainQueue)localObject4).isEmpty())) && (j == 0) && (k == 0))
        {
          localObject3 = this.errors.terminate();
          if (localObject3 != ExceptionHelper.TERMINATED) {
            if (localObject3 == null) {
              localObject1.onComplete();
            } else {
              localObject1.onError((Throwable)localObject3);
            }
          }
          return;
        }
        k = m;
        if (j != 0)
        {
          long l = this.lastId;
          m = this.lastIndex;
          int n;
          if (j > m)
          {
            k = m;
            if (localObject3[m].id == l) {}
          }
          else
          {
            k = m;
            if (j <= m) {
              k = 0;
            }
            for (m = 0; (m < j) && (localObject3[k].id != l); m++)
            {
              n = k + 1;
              k = n;
              if (n == j) {
                k = 0;
              }
            }
            this.lastIndex = k;
            this.lastId = localObject3[k].id;
          }
          int i1 = 0;
          m = i1;
          while (i1 < j)
          {
            if (checkTerminate()) {
              return;
            }
            localObject4 = localObject3[k];
            SimpleQueue localSimpleQueue1 = ((ObservableFlatMap.InnerObserver)localObject4).queue;
            int i2;
            if (localSimpleQueue1 != null)
            {
              try
              {
                do
                {
                  Object localObject5 = localSimpleQueue1.poll();
                  if (localObject5 == null) {
                    break;
                  }
                  localObject1.onNext(localObject5);
                } while (!checkTerminate());
                return;
              }
              finally
              {
                Exceptions.throwIfFatal(localThrowable);
                ((ObservableFlatMap.InnerObserver)localObject4).dispose();
                this.errors.addThrowable(localThrowable);
                if (checkTerminate()) {
                  return;
                }
                removeInner((ObservableFlatMap.InnerObserver)localObject4);
                n = m + 1;
                i2 = k + 1;
                m = n;
                k = i2;
                if (i2 != j) {
                  break label555;
                }
              }
              m = n;
            }
            else
            {
              bool = ((ObservableFlatMap.InnerObserver)localObject4).done;
              SimpleQueue localSimpleQueue2 = ((ObservableFlatMap.InnerObserver)localObject4).queue;
              n = m;
              if (bool) {
                if (localSimpleQueue2 != null)
                {
                  n = m;
                  if (!localSimpleQueue2.isEmpty()) {}
                }
                else
                {
                  removeInner((ObservableFlatMap.InnerObserver)localObject4);
                  if (checkTerminate()) {
                    return;
                  }
                  n = m + 1;
                }
              }
              i2 = k + 1;
              m = n;
              k = i2;
              if (i2 != j) {
                break label555;
              }
              m = n;
            }
            k = 0;
            i1++;
          }
          this.lastIndex = k;
          this.lastId = localObject3[k].id;
          k = m;
        }
        if (k != 0) {
          while ((this.maxConcurrency != Integer.MAX_VALUE) && (k != 0)) {
            try
            {
              localObject3 = (ObservableSource)this.sources.poll();
              if (localObject3 == null) {
                this.wip -= 1;
              } else {
                subscribeInner((ObservableSource)localObject3);
              }
              k--;
            }
            finally {}
          }
        }
        k = addAndGet(-i);
        i = k;
      } while (k != 0);
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
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
      if (this.done) {
        return;
      }
      try
      {
        paramT = (ObservableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null ObservableSource");
        if (this.maxConcurrency != Integer.MAX_VALUE) {
          try
          {
            if (this.wip == this.maxConcurrency)
            {
              this.sources.offer(paramT);
              return;
            }
            this.wip += 1;
          }
          finally {}
        }
        subscribeInner(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    void removeInner(ObservableFlatMap.InnerObserver<T, U> paramInnerObserver)
    {
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver1;
      ObservableFlatMap.InnerObserver[] arrayOfInnerObserver2;
      do
      {
        arrayOfInnerObserver1 = (ObservableFlatMap.InnerObserver[])this.observers.get();
        int i = arrayOfInnerObserver1.length;
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
          if (arrayOfInnerObserver1[k] == paramInnerObserver)
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
          arrayOfInnerObserver2 = EMPTY;
        }
        else
        {
          arrayOfInnerObserver2 = new ObservableFlatMap.InnerObserver[i - 1];
          System.arraycopy(arrayOfInnerObserver1, 0, arrayOfInnerObserver2, 0, m);
          System.arraycopy(arrayOfInnerObserver1, m + 1, arrayOfInnerObserver2, m, i - m - 1);
        }
      } while (!this.observers.compareAndSet(arrayOfInnerObserver1, arrayOfInnerObserver2));
    }
    
    void subscribeInner(ObservableSource<? extends U> paramObservableSource)
    {
      while ((paramObservableSource instanceof Callable))
      {
        if ((!tryEmitScalar((Callable)paramObservableSource)) || (this.maxConcurrency == Integer.MAX_VALUE)) {
          return;
        }
        int i = 0;
        try
        {
          paramObservableSource = (ObservableSource)this.sources.poll();
          if (paramObservableSource == null)
          {
            this.wip -= 1;
            i = 1;
          }
          if (i != 0)
          {
            drain();
            return;
          }
        }
        finally {}
      }
      long l = this.uniqueId;
      this.uniqueId = (1L + l);
      ObservableFlatMap.InnerObserver localInnerObserver = new ObservableFlatMap.InnerObserver(this, l);
      if (addInner(localInnerObserver)) {
        paramObservableSource.subscribe(localInnerObserver);
      }
    }
    
    void tryEmit(U paramU, ObservableFlatMap.InnerObserver<T, U> paramInnerObserver)
    {
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        this.downstream.onNext(paramU);
        if (decrementAndGet() != 0) {}
      }
      else
      {
        SimpleQueue localSimpleQueue = paramInnerObserver.queue;
        Object localObject = localSimpleQueue;
        if (localSimpleQueue == null)
        {
          localObject = new SpscLinkedArrayQueue(this.bufferSize);
          paramInnerObserver.queue = ((SimpleQueue)localObject);
        }
        ((SimpleQueue)localObject).offer(paramU);
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    boolean tryEmitScalar(Callable<? extends U> paramCallable)
    {
      try
      {
        Object localObject = paramCallable.call();
        if (localObject == null) {
          return true;
        }
        if ((get() == 0) && (compareAndSet(0, 1)))
        {
          this.downstream.onNext(localObject);
          if (decrementAndGet() == 0) {
            return true;
          }
        }
        else
        {
          SimplePlainQueue localSimplePlainQueue = this.queue;
          paramCallable = localSimplePlainQueue;
          if (localSimplePlainQueue == null)
          {
            if (this.maxConcurrency == Integer.MAX_VALUE) {
              paramCallable = new SpscLinkedArrayQueue(this.bufferSize);
            } else {
              paramCallable = new SpscArrayQueue(this.maxConcurrency);
            }
            this.queue = paramCallable;
          }
          if (!paramCallable.offer(localObject))
          {
            onError(new IllegalStateException("Scalar queue full?!"));
            return true;
          }
          if (getAndIncrement() != 0) {
            return false;
          }
        }
        drainLoop();
        return true;
      }
      finally
      {
        Exceptions.throwIfFatal(paramCallable);
        this.errors.addThrowable(paramCallable);
        drain();
      }
      return true;
    }
  }
}
