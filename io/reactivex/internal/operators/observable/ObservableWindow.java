package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableWindow<T>
  extends AbstractObservableWithUpstream<T, Observable<T>>
{
  final int capacityHint;
  final long count;
  final long skip;
  
  public ObservableWindow(ObservableSource<T> paramObservableSource, long paramLong1, long paramLong2, int paramInt)
  {
    super(paramObservableSource);
    this.count = paramLong1;
    this.skip = paramLong2;
    this.capacityHint = paramInt;
  }
  
  public void subscribeActual(Observer<? super Observable<T>> paramObserver)
  {
    if (this.count == this.skip) {
      this.source.subscribe(new WindowExactObserver(paramObserver, this.count, this.capacityHint));
    } else {
      this.source.subscribe(new WindowSkipObserver(paramObserver, this.count, this.skip, this.capacityHint));
    }
  }
  
  static final class WindowExactObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = -7481782523886138128L;
    volatile boolean cancelled;
    final int capacityHint;
    final long count;
    final Observer<? super Observable<T>> downstream;
    long size;
    Disposable upstream;
    UnicastSubject<T> window;
    
    WindowExactObserver(Observer<? super Observable<T>> paramObserver, long paramLong, int paramInt)
    {
      this.downstream = paramObserver;
      this.count = paramLong;
      this.capacityHint = paramInt;
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      UnicastSubject localUnicastSubject = this.window;
      if (localUnicastSubject != null)
      {
        this.window = null;
        localUnicastSubject.onComplete();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      UnicastSubject localUnicastSubject = this.window;
      if (localUnicastSubject != null)
      {
        this.window = null;
        localUnicastSubject.onError(paramThrowable);
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      UnicastSubject localUnicastSubject1 = this.window;
      UnicastSubject localUnicastSubject2 = localUnicastSubject1;
      if (localUnicastSubject1 == null)
      {
        localUnicastSubject2 = localUnicastSubject1;
        if (!this.cancelled)
        {
          localUnicastSubject2 = UnicastSubject.create(this.capacityHint, this);
          this.window = localUnicastSubject2;
          this.downstream.onNext(localUnicastSubject2);
        }
      }
      if (localUnicastSubject2 != null)
      {
        localUnicastSubject2.onNext(paramT);
        long l = this.size + 1L;
        this.size = l;
        if (l >= this.count)
        {
          this.size = 0L;
          this.window = null;
          localUnicastSubject2.onComplete();
          if (this.cancelled) {
            this.upstream.dispose();
          }
        }
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
    
    public void run()
    {
      if (this.cancelled) {
        this.upstream.dispose();
      }
    }
  }
  
  static final class WindowSkipObserver<T>
    extends AtomicBoolean
    implements Observer<T>, Disposable, Runnable
  {
    private static final long serialVersionUID = 3366976432059579510L;
    volatile boolean cancelled;
    final int capacityHint;
    final long count;
    final Observer<? super Observable<T>> downstream;
    long firstEmission;
    long index;
    final long skip;
    Disposable upstream;
    final ArrayDeque<UnicastSubject<T>> windows;
    final AtomicInteger wip = new AtomicInteger();
    
    WindowSkipObserver(Observer<? super Observable<T>> paramObserver, long paramLong1, long paramLong2, int paramInt)
    {
      this.downstream = paramObserver;
      this.count = paramLong1;
      this.skip = paramLong2;
      this.capacityHint = paramInt;
      this.windows = new ArrayDeque();
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      ArrayDeque localArrayDeque = this.windows;
      while (!localArrayDeque.isEmpty()) {
        ((UnicastSubject)localArrayDeque.poll()).onComplete();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      ArrayDeque localArrayDeque = this.windows;
      while (!localArrayDeque.isEmpty()) {
        ((UnicastSubject)localArrayDeque.poll()).onError(paramThrowable);
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      ArrayDeque localArrayDeque = this.windows;
      long l1 = this.index;
      long l2 = this.skip;
      if ((l1 % l2 == 0L) && (!this.cancelled))
      {
        this.wip.getAndIncrement();
        localObject = UnicastSubject.create(this.capacityHint, this);
        localArrayDeque.offer(localObject);
        this.downstream.onNext(localObject);
      }
      long l3 = this.firstEmission + 1L;
      Object localObject = localArrayDeque.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((UnicastSubject)((Iterator)localObject).next()).onNext(paramT);
      }
      if (l3 >= this.count)
      {
        ((UnicastSubject)localArrayDeque.poll()).onComplete();
        if ((localArrayDeque.isEmpty()) && (this.cancelled))
        {
          this.upstream.dispose();
          return;
        }
        this.firstEmission = (l3 - l2);
      }
      else
      {
        this.firstEmission = l3;
      }
      this.index = (l1 + 1L);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void run()
    {
      if ((this.wip.decrementAndGet() == 0) && (this.cancelled)) {
        this.upstream.dispose();
      }
    }
  }
}
