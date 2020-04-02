package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class BehaviorSubject<T>
  extends Subject<T>
{
  static final BehaviorDisposable[] EMPTY = new BehaviorDisposable[0];
  private static final Object[] EMPTY_ARRAY = new Object[0];
  static final BehaviorDisposable[] TERMINATED = new BehaviorDisposable[0];
  long index;
  final ReadWriteLock lock;
  final Lock readLock;
  final AtomicReference<BehaviorDisposable<T>[]> subscribers;
  final AtomicReference<Throwable> terminalEvent;
  final AtomicReference<Object> value;
  final Lock writeLock;
  
  BehaviorSubject()
  {
    ReentrantReadWriteLock localReentrantReadWriteLock = new ReentrantReadWriteLock();
    this.lock = localReentrantReadWriteLock;
    this.readLock = localReentrantReadWriteLock.readLock();
    this.writeLock = this.lock.writeLock();
    this.subscribers = new AtomicReference(EMPTY);
    this.value = new AtomicReference();
    this.terminalEvent = new AtomicReference();
  }
  
  BehaviorSubject(T paramT)
  {
    this();
    this.value.lazySet(ObjectHelper.requireNonNull(paramT, "defaultValue is null"));
  }
  
  @CheckReturnValue
  public static <T> BehaviorSubject<T> create()
  {
    return new BehaviorSubject();
  }
  
  @CheckReturnValue
  public static <T> BehaviorSubject<T> createDefault(T paramT)
  {
    return new BehaviorSubject(paramT);
  }
  
  boolean add(BehaviorDisposable<T> paramBehaviorDisposable)
  {
    BehaviorDisposable[] arrayOfBehaviorDisposable1;
    BehaviorDisposable[] arrayOfBehaviorDisposable2;
    do
    {
      arrayOfBehaviorDisposable1 = (BehaviorDisposable[])this.subscribers.get();
      if (arrayOfBehaviorDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfBehaviorDisposable1.length;
      arrayOfBehaviorDisposable2 = new BehaviorDisposable[i + 1];
      System.arraycopy(arrayOfBehaviorDisposable1, 0, arrayOfBehaviorDisposable2, 0, i);
      arrayOfBehaviorDisposable2[i] = paramBehaviorDisposable;
    } while (!this.subscribers.compareAndSet(arrayOfBehaviorDisposable1, arrayOfBehaviorDisposable2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    Object localObject = this.value.get();
    if (NotificationLite.isError(localObject)) {
      return NotificationLite.getError(localObject);
    }
    return null;
  }
  
  public T getValue()
  {
    Object localObject = this.value.get();
    if ((!NotificationLite.isComplete(localObject)) && (!NotificationLite.isError(localObject))) {
      return NotificationLite.getValue(localObject);
    }
    return null;
  }
  
  @Deprecated
  public Object[] getValues()
  {
    Object[] arrayOfObject1 = getValues((Object[])EMPTY_ARRAY);
    Object[] arrayOfObject2 = arrayOfObject1;
    if (arrayOfObject1 == EMPTY_ARRAY) {
      arrayOfObject2 = new Object[0];
    }
    return arrayOfObject2;
  }
  
  @Deprecated
  public T[] getValues(T[] paramArrayOfT)
  {
    Object localObject1 = this.value.get();
    if ((localObject1 != null) && (!NotificationLite.isComplete(localObject1)) && (!NotificationLite.isError(localObject1)))
    {
      Object localObject2 = NotificationLite.getValue(localObject1);
      if (paramArrayOfT.length != 0)
      {
        paramArrayOfT[0] = localObject2;
        localObject1 = paramArrayOfT;
        if (paramArrayOfT.length != 1)
        {
          paramArrayOfT[1] = null;
          localObject1 = paramArrayOfT;
        }
      }
      else
      {
        localObject1 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), 1);
        localObject1[0] = localObject2;
      }
      return localObject1;
    }
    if (paramArrayOfT.length != 0) {
      paramArrayOfT[0] = null;
    }
    return paramArrayOfT;
  }
  
  public boolean hasComplete()
  {
    return NotificationLite.isComplete(this.value.get());
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((BehaviorDisposable[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    return NotificationLite.isError(this.value.get());
  }
  
  public boolean hasValue()
  {
    Object localObject = this.value.get();
    boolean bool;
    if ((localObject != null) && (!NotificationLite.isComplete(localObject)) && (!NotificationLite.isError(localObject))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    if (!this.terminalEvent.compareAndSet(null, ExceptionHelper.TERMINATED)) {
      return;
    }
    Object localObject = NotificationLite.complete();
    BehaviorDisposable[] arrayOfBehaviorDisposable = terminate(localObject);
    int i = arrayOfBehaviorDisposable.length;
    for (int j = 0; j < i; j++) {
      arrayOfBehaviorDisposable[j].emitNext(localObject, this.index);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (!this.terminalEvent.compareAndSet(null, paramThrowable))
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    Object localObject = NotificationLite.error(paramThrowable);
    paramThrowable = terminate(localObject);
    int i = paramThrowable.length;
    for (int j = 0; j < i; j++) {
      paramThrowable[j].emitNext(localObject, this.index);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.terminalEvent.get() != null) {
      return;
    }
    paramT = NotificationLite.next(paramT);
    setCurrent(paramT);
    BehaviorDisposable[] arrayOfBehaviorDisposable = (BehaviorDisposable[])this.subscribers.get();
    int i = arrayOfBehaviorDisposable.length;
    for (int j = 0; j < i; j++) {
      arrayOfBehaviorDisposable[j].emitNext(paramT, this.index);
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (this.terminalEvent.get() != null) {
      paramDisposable.dispose();
    }
  }
  
  void remove(BehaviorDisposable<T> paramBehaviorDisposable)
  {
    BehaviorDisposable[] arrayOfBehaviorDisposable1;
    BehaviorDisposable[] arrayOfBehaviorDisposable2;
    do
    {
      arrayOfBehaviorDisposable1 = (BehaviorDisposable[])this.subscribers.get();
      int i = arrayOfBehaviorDisposable1.length;
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
        if (arrayOfBehaviorDisposable1[k] == paramBehaviorDisposable)
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
        arrayOfBehaviorDisposable2 = EMPTY;
      }
      else
      {
        arrayOfBehaviorDisposable2 = new BehaviorDisposable[i - 1];
        System.arraycopy(arrayOfBehaviorDisposable1, 0, arrayOfBehaviorDisposable2, 0, m);
        System.arraycopy(arrayOfBehaviorDisposable1, m + 1, arrayOfBehaviorDisposable2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfBehaviorDisposable1, arrayOfBehaviorDisposable2));
  }
  
  void setCurrent(Object paramObject)
  {
    this.writeLock.lock();
    this.index += 1L;
    this.value.lazySet(paramObject);
    this.writeLock.unlock();
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject = new BehaviorDisposable(paramObserver, this);
    paramObserver.onSubscribe((Disposable)localObject);
    if (add((BehaviorDisposable)localObject))
    {
      if (((BehaviorDisposable)localObject).cancelled) {
        remove((BehaviorDisposable)localObject);
      } else {
        ((BehaviorDisposable)localObject).emitFirst();
      }
    }
    else
    {
      localObject = (Throwable)this.terminalEvent.get();
      if (localObject == ExceptionHelper.TERMINATED) {
        paramObserver.onComplete();
      } else {
        paramObserver.onError((Throwable)localObject);
      }
    }
  }
  
  int subscriberCount()
  {
    return ((BehaviorDisposable[])this.subscribers.get()).length;
  }
  
  BehaviorDisposable<T>[] terminate(Object paramObject)
  {
    BehaviorDisposable[] arrayOfBehaviorDisposable = (BehaviorDisposable[])this.subscribers.getAndSet(TERMINATED);
    if (arrayOfBehaviorDisposable != TERMINATED) {
      setCurrent(paramObject);
    }
    return arrayOfBehaviorDisposable;
  }
  
  static final class BehaviorDisposable<T>
    implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object>
  {
    volatile boolean cancelled;
    final Observer<? super T> downstream;
    boolean emitting;
    boolean fastPath;
    long index;
    boolean next;
    AppendOnlyLinkedArrayList<Object> queue;
    final BehaviorSubject<T> state;
    
    BehaviorDisposable(Observer<? super T> paramObserver, BehaviorSubject<T> paramBehaviorSubject)
    {
      this.downstream = paramObserver;
      this.state = paramBehaviorSubject;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.state.remove(this);
      }
    }
    
    void emitFirst()
    {
      if (this.cancelled) {
        return;
      }
      try
      {
        if (this.cancelled) {
          return;
        }
        if (this.next) {
          return;
        }
        Object localObject1 = this.state;
        Lock localLock = ((BehaviorSubject)localObject1).readLock;
        localLock.lock();
        this.index = ((BehaviorSubject)localObject1).index;
        localObject1 = ((BehaviorSubject)localObject1).value.get();
        localLock.unlock();
        boolean bool;
        if (localObject1 != null) {
          bool = true;
        } else {
          bool = false;
        }
        this.emitting = bool;
        this.next = true;
        if (localObject1 != null)
        {
          if (test(localObject1)) {
            return;
          }
          emitLoop();
        }
        return;
      }
      finally {}
    }
    
    void emitLoop()
    {
      for (;;)
      {
        if (this.cancelled) {
          return;
        }
        try
        {
          AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList = this.queue;
          if (localAppendOnlyLinkedArrayList == null)
          {
            this.emitting = false;
            return;
          }
          this.queue = null;
          localAppendOnlyLinkedArrayList.forEachWhile(this);
        }
        finally {}
      }
    }
    
    void emitNext(Object paramObject, long paramLong)
    {
      if (this.cancelled) {
        return;
      }
      if (!this.fastPath) {
        try
        {
          if (this.cancelled) {
            return;
          }
          if (this.index == paramLong) {
            return;
          }
          if (this.emitting)
          {
            AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
            AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
            if (localAppendOnlyLinkedArrayList1 == null)
            {
              localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
              localAppendOnlyLinkedArrayList2.<init>(4);
              this.queue = localAppendOnlyLinkedArrayList2;
            }
            localAppendOnlyLinkedArrayList2.add(paramObject);
            return;
          }
          this.next = true;
          this.fastPath = true;
        }
        finally {}
      }
      test(paramObject);
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public boolean test(Object paramObject)
    {
      boolean bool;
      if ((!this.cancelled) && (!NotificationLite.accept(paramObject, this.downstream))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
}
