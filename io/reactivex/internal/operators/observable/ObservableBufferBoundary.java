package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferBoundary<T, U extends Collection<? super T>, Open, Close>
  extends AbstractObservableWithUpstream<T, U>
{
  final Function<? super Open, ? extends ObservableSource<? extends Close>> bufferClose;
  final ObservableSource<? extends Open> bufferOpen;
  final Callable<U> bufferSupplier;
  
  public ObservableBufferBoundary(ObservableSource<T> paramObservableSource, ObservableSource<? extends Open> paramObservableSource1, Function<? super Open, ? extends ObservableSource<? extends Close>> paramFunction, Callable<U> paramCallable)
  {
    super(paramObservableSource);
    this.bufferOpen = paramObservableSource1;
    this.bufferClose = paramFunction;
    this.bufferSupplier = paramCallable;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    BufferBoundaryObserver localBufferBoundaryObserver = new BufferBoundaryObserver(paramObserver, this.bufferOpen, this.bufferClose, this.bufferSupplier);
    paramObserver.onSubscribe(localBufferBoundaryObserver);
    this.source.subscribe(localBufferBoundaryObserver);
  }
  
  static final class BufferBoundaryObserver<T, C extends Collection<? super T>, Open, Close>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -8466418554264089604L;
    final Function<? super Open, ? extends ObservableSource<? extends Close>> bufferClose;
    final ObservableSource<? extends Open> bufferOpen;
    final Callable<C> bufferSupplier;
    Map<Long, C> buffers;
    volatile boolean cancelled;
    volatile boolean done;
    final Observer<? super C> downstream;
    final AtomicThrowable errors;
    long index;
    final CompositeDisposable observers;
    final SpscLinkedArrayQueue<C> queue;
    final AtomicReference<Disposable> upstream;
    
    BufferBoundaryObserver(Observer<? super C> paramObserver, ObservableSource<? extends Open> paramObservableSource, Function<? super Open, ? extends ObservableSource<? extends Close>> paramFunction, Callable<C> paramCallable)
    {
      this.downstream = paramObserver;
      this.bufferSupplier = paramCallable;
      this.bufferOpen = paramObservableSource;
      this.bufferClose = paramFunction;
      this.queue = new SpscLinkedArrayQueue(Observable.bufferSize());
      this.observers = new CompositeDisposable();
      this.upstream = new AtomicReference();
      this.buffers = new LinkedHashMap();
      this.errors = new AtomicThrowable();
    }
    
    void boundaryError(Disposable paramDisposable, Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.upstream);
      this.observers.delete(paramDisposable);
      onError(paramThrowable);
    }
    
    void close(ObservableBufferBoundary.BufferCloseObserver<T, C> paramBufferCloseObserver, long paramLong)
    {
      this.observers.delete(paramBufferCloseObserver);
      int i;
      if (this.observers.size() == 0)
      {
        DisposableHelper.dispose(this.upstream);
        i = 1;
      }
      else
      {
        i = 0;
      }
      try
      {
        if (this.buffers == null) {
          return;
        }
        this.queue.offer(this.buffers.remove(Long.valueOf(paramLong)));
        if (i != 0) {
          this.done = true;
        }
        drain();
        return;
      }
      finally {}
    }
    
    public void dispose()
    {
      if (DisposableHelper.dispose(this.upstream))
      {
        this.cancelled = true;
        this.observers.dispose();
        try
        {
          this.buffers = null;
          if (getAndIncrement() != 0) {
            this.queue.clear();
          }
        }
        finally {}
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      int i = 1;
      for (;;)
      {
        if (this.cancelled)
        {
          localSpscLinkedArrayQueue.clear();
          return;
        }
        boolean bool = this.done;
        if ((bool) && (this.errors.get() != null))
        {
          localSpscLinkedArrayQueue.clear();
          localObserver.onError(this.errors.terminate());
          return;
        }
        Collection localCollection = (Collection)localSpscLinkedArrayQueue.poll();
        int j;
        if (localCollection == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0))
        {
          localObserver.onComplete();
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
          localObserver.onNext(localCollection);
        }
      }
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      this.observers.dispose();
      try
      {
        Object localObject1 = this.buffers;
        if (localObject1 == null) {
          return;
        }
        localObject1 = ((Map)localObject1).values().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          Collection localCollection = (Collection)((Iterator)localObject1).next();
          this.queue.offer(localCollection);
        }
        this.buffers = null;
        this.done = true;
        drain();
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        this.observers.dispose();
        try
        {
          this.buffers = null;
          this.done = true;
          drain();
        }
        finally {}
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Object localObject = this.buffers;
        if (localObject == null) {
          return;
        }
        localObject = ((Map)localObject).values().iterator();
        while (((Iterator)localObject).hasNext()) {
          ((Collection)((Iterator)localObject).next()).add(paramT);
        }
        return;
      }
      finally {}
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this.upstream, paramDisposable))
      {
        paramDisposable = new BufferOpenObserver(this);
        this.observers.add(paramDisposable);
        this.bufferOpen.subscribe(paramDisposable);
      }
    }
    
    void open(Open paramOpen)
    {
      try
      {
        Object localObject = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null Collection");
        paramOpen = (ObservableSource)ObjectHelper.requireNonNull(this.bufferClose.apply(paramOpen), "The bufferClose returned a null ObservableSource");
        long l = this.index;
        this.index = (1L + l);
        try
        {
          Map localMap = this.buffers;
          if (localMap == null) {
            return;
          }
          localMap.put(Long.valueOf(l), localObject);
          localObject = new ObservableBufferBoundary.BufferCloseObserver(this, l);
          this.observers.add((Disposable)localObject);
          paramOpen.subscribe((Observer)localObject);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramOpen);
        DisposableHelper.dispose(this.upstream);
      }
    }
    
    void openComplete(BufferOpenObserver<Open> paramBufferOpenObserver)
    {
      this.observers.delete(paramBufferOpenObserver);
      if (this.observers.size() == 0)
      {
        DisposableHelper.dispose(this.upstream);
        this.done = true;
        drain();
      }
    }
    
    static final class BufferOpenObserver<Open>
      extends AtomicReference<Disposable>
      implements Observer<Open>, Disposable
    {
      private static final long serialVersionUID = -8498650778633225126L;
      final ObservableBufferBoundary.BufferBoundaryObserver<?, ?, Open, ?> parent;
      
      BufferOpenObserver(ObservableBufferBoundary.BufferBoundaryObserver<?, ?, Open, ?> paramBufferBoundaryObserver)
      {
        this.parent = paramBufferBoundaryObserver;
      }
      
      public void dispose()
      {
        DisposableHelper.dispose(this);
      }
      
      public boolean isDisposed()
      {
        boolean bool;
        if (get() == DisposableHelper.DISPOSED) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void onComplete()
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.openComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.boundaryError(this, paramThrowable);
      }
      
      public void onNext(Open paramOpen)
      {
        this.parent.open(paramOpen);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
  
  static final class BufferCloseObserver<T, C extends Collection<? super T>>
    extends AtomicReference<Disposable>
    implements Observer<Object>, Disposable
  {
    private static final long serialVersionUID = -8498650778633225126L;
    final long index;
    final ObservableBufferBoundary.BufferBoundaryObserver<T, C, ?, ?> parent;
    
    BufferCloseObserver(ObservableBufferBoundary.BufferBoundaryObserver<T, C, ?, ?> paramBufferBoundaryObserver, long paramLong)
    {
      this.parent = paramBufferBoundaryObserver;
      this.index = paramLong;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (get() != DisposableHelper.DISPOSED)
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.close(this, this.index);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get() != DisposableHelper.DISPOSED)
      {
        lazySet(DisposableHelper.DISPOSED);
        this.parent.boundaryError(this, paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(Object paramObject)
    {
      paramObject = (Disposable)get();
      if (paramObject != DisposableHelper.DISPOSED)
      {
        lazySet(DisposableHelper.DISPOSED);
        paramObject.dispose();
        this.parent.close(this, this.index);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
