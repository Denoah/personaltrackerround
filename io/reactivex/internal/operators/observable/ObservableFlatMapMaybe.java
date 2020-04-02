package io.reactivex.internal.operators.observable;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapMaybe<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final boolean delayErrors;
  final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
  
  public ObservableFlatMapMaybe(ObservableSource<T> paramObservableSource, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    this.source.subscribe(new FlatMapMaybeObserver(paramObserver, this.mapper, this.delayErrors));
  }
  
  static final class FlatMapMaybeObserver<T, R>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 8600231336733376951L;
    final AtomicInteger active;
    volatile boolean cancelled;
    final boolean delayErrors;
    final Observer<? super R> downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final AtomicReference<SpscLinkedArrayQueue<R>> queue;
    final CompositeDisposable set;
    Disposable upstream;
    
    FlatMapMaybeObserver(Observer<? super R> paramObserver, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.set = new CompositeDisposable();
      this.errors = new AtomicThrowable();
      this.active = new AtomicInteger(1);
      this.queue = new AtomicReference();
    }
    
    void clear()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = (SpscLinkedArrayQueue)this.queue.get();
      if (localSpscLinkedArrayQueue != null) {
        localSpscLinkedArrayQueue.clear();
      }
    }
    
    public void dispose()
    {
      this.cancelled = true;
      this.upstream.dispose();
      this.set.dispose();
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
      AtomicInteger localAtomicInteger = this.active;
      AtomicReference localAtomicReference = this.queue;
      int i = 1;
      for (;;)
      {
        if (this.cancelled)
        {
          clear();
          return;
        }
        if ((!this.delayErrors) && ((Throwable)this.errors.get() != null))
        {
          localObject = this.errors.terminate();
          clear();
          localObserver.onError((Throwable)localObject);
          return;
        }
        int j = localAtomicInteger.get();
        int k = 0;
        if (j == 0) {
          j = 1;
        } else {
          j = 0;
        }
        Object localObject = (SpscLinkedArrayQueue)localAtomicReference.get();
        if (localObject != null) {
          localObject = ((SpscLinkedArrayQueue)localObject).poll();
        } else {
          localObject = null;
        }
        if (localObject == null) {
          k = 1;
        }
        if ((j != 0) && (k != 0))
        {
          localObject = this.errors.terminate();
          if (localObject != null) {
            localObserver.onError((Throwable)localObject);
          } else {
            localObserver.onComplete();
          }
          return;
        }
        if (k != 0)
        {
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else
        {
          localObserver.onNext(localObject);
        }
      }
    }
    
    SpscLinkedArrayQueue<R> getOrCreateQueue()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue;
      do
      {
        localSpscLinkedArrayQueue = (SpscLinkedArrayQueue)this.queue.get();
        if (localSpscLinkedArrayQueue != null) {
          return localSpscLinkedArrayQueue;
        }
        localSpscLinkedArrayQueue = new SpscLinkedArrayQueue(Observable.bufferSize());
      } while (!this.queue.compareAndSet(null, localSpscLinkedArrayQueue));
      return localSpscLinkedArrayQueue;
    }
    
    void innerComplete(FlatMapMaybeObserver<T, R>.InnerObserver paramFlatMapMaybeObserver)
    {
      this.set.delete(paramFlatMapMaybeObserver);
      if (get() == 0)
      {
        int i = 1;
        if (compareAndSet(0, 1))
        {
          if (this.active.decrementAndGet() != 0) {
            i = 0;
          }
          paramFlatMapMaybeObserver = (SpscLinkedArrayQueue)this.queue.get();
          if ((i != 0) && ((paramFlatMapMaybeObserver == null) || (paramFlatMapMaybeObserver.isEmpty())))
          {
            paramFlatMapMaybeObserver = this.errors.terminate();
            if (paramFlatMapMaybeObserver != null) {
              this.downstream.onError(paramFlatMapMaybeObserver);
            } else {
              this.downstream.onComplete();
            }
            return;
          }
          if (decrementAndGet() == 0) {
            return;
          }
          drainLoop();
          return;
        }
      }
      this.active.decrementAndGet();
      drain();
    }
    
    void innerError(FlatMapMaybeObserver<T, R>.InnerObserver paramFlatMapMaybeObserver, Throwable paramThrowable)
    {
      this.set.delete(paramFlatMapMaybeObserver);
      if (this.errors.addThrowable(paramThrowable))
      {
        if (!this.delayErrors)
        {
          this.upstream.dispose();
          this.set.dispose();
        }
        this.active.decrementAndGet();
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerSuccess(FlatMapMaybeObserver<T, R>.InnerObserver arg1, R paramR)
    {
      this.set.delete(???);
      if (get() == 0)
      {
        int i = 1;
        if (compareAndSet(0, 1))
        {
          this.downstream.onNext(paramR);
          if (this.active.decrementAndGet() != 0) {
            i = 0;
          }
          ??? = (SpscLinkedArrayQueue)this.queue.get();
          if ((i != 0) && ((??? == null) || (???.isEmpty())))
          {
            ??? = this.errors.terminate();
            if (??? != null) {
              this.downstream.onError(???);
            } else {
              this.downstream.onComplete();
            }
            return;
          }
          if (decrementAndGet() != 0) {
            break label152;
          }
          return;
        }
      }
      synchronized (getOrCreateQueue())
      {
        ???.offer(paramR);
        this.active.decrementAndGet();
        if (getAndIncrement() != 0) {
          return;
        }
        label152:
        drainLoop();
        return;
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.active.decrementAndGet();
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.active.decrementAndGet();
      if (this.errors.addThrowable(paramThrowable))
      {
        if (!this.delayErrors) {
          this.set.dispose();
        }
        drain();
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
        paramT = (MaybeSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null MaybeSource");
        this.active.getAndIncrement();
        InnerObserver localInnerObserver = new InnerObserver();
        if ((!this.cancelled) && (this.set.add(localInnerObserver))) {
          paramT.subscribe(localInnerObserver);
        }
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
    
    final class InnerObserver
      extends AtomicReference<Disposable>
      implements MaybeObserver<R>, Disposable
    {
      private static final long serialVersionUID = -502562646270949838L;
      
      InnerObserver() {}
      
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
        ObservableFlatMapMaybe.FlatMapMaybeObserver.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableFlatMapMaybe.FlatMapMaybeObserver.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
      
      public void onSuccess(R paramR)
      {
        ObservableFlatMapMaybe.FlatMapMaybeObserver.this.innerSuccess(this, paramR);
      }
    }
  }
}
