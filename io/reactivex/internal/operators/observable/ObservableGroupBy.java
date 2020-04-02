package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.observables.GroupedObservable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupBy<T, K, V>
  extends AbstractObservableWithUpstream<T, GroupedObservable<K, V>>
{
  final int bufferSize;
  final boolean delayError;
  final Function<? super T, ? extends K> keySelector;
  final Function<? super T, ? extends V> valueSelector;
  
  public ObservableGroupBy(ObservableSource<T> paramObservableSource, Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.keySelector = paramFunction;
    this.valueSelector = paramFunction1;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super GroupedObservable<K, V>> paramObserver)
  {
    this.source.subscribe(new GroupByObserver(paramObserver, this.keySelector, this.valueSelector, this.bufferSize, this.delayError));
  }
  
  public static final class GroupByObserver<T, K, V>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    static final Object NULL_KEY = new Object();
    private static final long serialVersionUID = -3688291656102519502L;
    final int bufferSize;
    final AtomicBoolean cancelled = new AtomicBoolean();
    final boolean delayError;
    final Observer<? super GroupedObservable<K, V>> downstream;
    final Map<Object, ObservableGroupBy.GroupedUnicast<K, V>> groups;
    final Function<? super T, ? extends K> keySelector;
    Disposable upstream;
    final Function<? super T, ? extends V> valueSelector;
    
    public GroupByObserver(Observer<? super GroupedObservable<K, V>> paramObserver, Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.keySelector = paramFunction;
      this.valueSelector = paramFunction1;
      this.bufferSize = paramInt;
      this.delayError = paramBoolean;
      this.groups = new ConcurrentHashMap();
      lazySet(1);
    }
    
    public void cancel(K paramK)
    {
      if (paramK == null) {
        paramK = NULL_KEY;
      }
      this.groups.remove(paramK);
      if (decrementAndGet() == 0) {
        this.upstream.dispose();
      }
    }
    
    public void dispose()
    {
      if ((this.cancelled.compareAndSet(false, true)) && (decrementAndGet() == 0)) {
        this.upstream.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled.get();
    }
    
    public void onComplete()
    {
      Object localObject = new ArrayList(this.groups.values());
      this.groups.clear();
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ObservableGroupBy.GroupedUnicast)((Iterator)localObject).next()).onComplete();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      Object localObject = new ArrayList(this.groups.values());
      this.groups.clear();
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ObservableGroupBy.GroupedUnicast)((Iterator)localObject).next()).onError(paramThrowable);
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Object localObject1 = this.keySelector.apply(paramT);
        Object localObject2;
        if (localObject1 != null) {
          localObject2 = localObject1;
        } else {
          localObject2 = NULL_KEY;
        }
        ObservableGroupBy.GroupedUnicast localGroupedUnicast1 = (ObservableGroupBy.GroupedUnicast)this.groups.get(localObject2);
        ObservableGroupBy.GroupedUnicast localGroupedUnicast2 = localGroupedUnicast1;
        if (localGroupedUnicast1 == null)
        {
          if (this.cancelled.get()) {
            return;
          }
          localGroupedUnicast2 = ObservableGroupBy.GroupedUnicast.createWith(localObject1, this.bufferSize, this, this.delayError);
          this.groups.put(localObject2, localGroupedUnicast2);
          getAndIncrement();
          this.downstream.onNext(localGroupedUnicast2);
        }
        try
        {
          paramT = ObjectHelper.requireNonNull(this.valueSelector.apply(paramT), "The value supplied is null");
          localGroupedUnicast2.onNext(paramT);
          return;
        }
        finally {}
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
  }
  
  static final class GroupedUnicast<K, T>
    extends GroupedObservable<K, T>
  {
    final ObservableGroupBy.State<T, K> state;
    
    protected GroupedUnicast(K paramK, ObservableGroupBy.State<T, K> paramState)
    {
      super();
      this.state = paramState;
    }
    
    public static <T, K> GroupedUnicast<K, T> createWith(K paramK, int paramInt, ObservableGroupBy.GroupByObserver<?, K, T> paramGroupByObserver, boolean paramBoolean)
    {
      return new GroupedUnicast(paramK, new ObservableGroupBy.State(paramInt, paramGroupByObserver, paramK, paramBoolean));
    }
    
    public void onComplete()
    {
      this.state.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.state.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.state.onNext(paramT);
    }
    
    protected void subscribeActual(Observer<? super T> paramObserver)
    {
      this.state.subscribe(paramObserver);
    }
  }
  
  static final class State<T, K>
    extends AtomicInteger
    implements Disposable, ObservableSource<T>
  {
    private static final long serialVersionUID = -3852313036005250360L;
    final AtomicReference<Observer<? super T>> actual = new AtomicReference();
    final AtomicBoolean cancelled = new AtomicBoolean();
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final K key;
    final AtomicBoolean once = new AtomicBoolean();
    final ObservableGroupBy.GroupByObserver<?, K, T> parent;
    final SpscLinkedArrayQueue<T> queue;
    
    State(int paramInt, ObservableGroupBy.GroupByObserver<?, K, T> paramGroupByObserver, K paramK, boolean paramBoolean)
    {
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.parent = paramGroupByObserver;
      this.key = paramK;
      this.delayError = paramBoolean;
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Observer<? super T> paramObserver, boolean paramBoolean3)
    {
      if (this.cancelled.get())
      {
        this.queue.clear();
        this.parent.cancel(this.key);
        this.actual.lazySet(null);
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable;
        if (paramBoolean3)
        {
          if (paramBoolean2)
          {
            localThrowable = this.error;
            this.actual.lazySet(null);
            if (localThrowable != null) {
              paramObserver.onError(localThrowable);
            } else {
              paramObserver.onComplete();
            }
            return true;
          }
        }
        else
        {
          localThrowable = this.error;
          if (localThrowable != null)
          {
            this.queue.clear();
            this.actual.lazySet(null);
            paramObserver.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            this.actual.lazySet(null);
            paramObserver.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    public void dispose()
    {
      if ((this.cancelled.compareAndSet(false, true)) && (getAndIncrement() == 0))
      {
        this.actual.lazySet(null);
        this.parent.cancel(this.key);
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      boolean bool1 = this.delayError;
      Observer localObserver = (Observer)this.actual.get();
      int i = 1;
      for (;;)
      {
        if (localObserver != null) {
          for (;;)
          {
            boolean bool2 = this.done;
            Object localObject = localSpscLinkedArrayQueue.poll();
            boolean bool3;
            if (localObject == null) {
              bool3 = true;
            } else {
              bool3 = false;
            }
            if (checkTerminated(bool2, bool3, localObserver, bool1)) {
              return;
            }
            if (bool3) {
              break;
            }
            localObserver.onNext(localObject);
          }
        }
        int j = addAndGet(-i);
        if (j == 0) {
          return;
        }
        i = j;
        if (localObserver == null)
        {
          localObserver = (Observer)this.actual.get();
          i = j;
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled.get();
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      drain();
    }
    
    public void subscribe(Observer<? super T> paramObserver)
    {
      if (this.once.compareAndSet(false, true))
      {
        paramObserver.onSubscribe(this);
        this.actual.lazySet(paramObserver);
        if (this.cancelled.get()) {
          this.actual.lazySet(null);
        } else {
          drain();
        }
      }
      else
      {
        EmptyDisposable.error(new IllegalStateException("Only one Observer allowed!"), paramObserver);
      }
    }
  }
}
