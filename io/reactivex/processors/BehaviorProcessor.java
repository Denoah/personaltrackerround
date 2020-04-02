package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class BehaviorProcessor<T>
  extends FlowableProcessor<T>
{
  static final BehaviorSubscription[] EMPTY = new BehaviorSubscription[0];
  static final Object[] EMPTY_ARRAY = new Object[0];
  static final BehaviorSubscription[] TERMINATED = new BehaviorSubscription[0];
  long index;
  final ReadWriteLock lock;
  final Lock readLock;
  final AtomicReference<BehaviorSubscription<T>[]> subscribers;
  final AtomicReference<Throwable> terminalEvent;
  final AtomicReference<Object> value = new AtomicReference();
  final Lock writeLock;
  
  BehaviorProcessor()
  {
    ReentrantReadWriteLock localReentrantReadWriteLock = new ReentrantReadWriteLock();
    this.lock = localReentrantReadWriteLock;
    this.readLock = localReentrantReadWriteLock.readLock();
    this.writeLock = this.lock.writeLock();
    this.subscribers = new AtomicReference(EMPTY);
    this.terminalEvent = new AtomicReference();
  }
  
  BehaviorProcessor(T paramT)
  {
    this();
    this.value.lazySet(ObjectHelper.requireNonNull(paramT, "defaultValue is null"));
  }
  
  @CheckReturnValue
  public static <T> BehaviorProcessor<T> create()
  {
    return new BehaviorProcessor();
  }
  
  @CheckReturnValue
  public static <T> BehaviorProcessor<T> createDefault(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultValue is null");
    return new BehaviorProcessor(paramT);
  }
  
  boolean add(BehaviorSubscription<T> paramBehaviorSubscription)
  {
    BehaviorSubscription[] arrayOfBehaviorSubscription1;
    BehaviorSubscription[] arrayOfBehaviorSubscription2;
    do
    {
      arrayOfBehaviorSubscription1 = (BehaviorSubscription[])this.subscribers.get();
      if (arrayOfBehaviorSubscription1 == TERMINATED) {
        return false;
      }
      int i = arrayOfBehaviorSubscription1.length;
      arrayOfBehaviorSubscription2 = new BehaviorSubscription[i + 1];
      System.arraycopy(arrayOfBehaviorSubscription1, 0, arrayOfBehaviorSubscription2, 0, i);
      arrayOfBehaviorSubscription2[i] = paramBehaviorSubscription;
    } while (!this.subscribers.compareAndSet(arrayOfBehaviorSubscription1, arrayOfBehaviorSubscription2));
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
  
  public boolean hasSubscribers()
  {
    boolean bool;
    if (((BehaviorSubscription[])this.subscribers.get()).length != 0) {
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
  
  public boolean offer(T paramT)
  {
    if (paramT == null)
    {
      onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
      return true;
    }
    BehaviorSubscription[] arrayOfBehaviorSubscription = (BehaviorSubscription[])this.subscribers.get();
    int i = arrayOfBehaviorSubscription.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (arrayOfBehaviorSubscription[k].isFull()) {
        return false;
      }
    }
    paramT = NotificationLite.next(paramT);
    setCurrent(paramT);
    i = arrayOfBehaviorSubscription.length;
    for (k = j; k < i; k++) {
      arrayOfBehaviorSubscription[k].emitNext(paramT, this.index);
    }
    return true;
  }
  
  public void onComplete()
  {
    if (!this.terminalEvent.compareAndSet(null, ExceptionHelper.TERMINATED)) {
      return;
    }
    Object localObject = NotificationLite.complete();
    BehaviorSubscription[] arrayOfBehaviorSubscription = terminate(localObject);
    int i = arrayOfBehaviorSubscription.length;
    for (int j = 0; j < i; j++) {
      arrayOfBehaviorSubscription[j].emitNext(localObject, this.index);
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
    paramThrowable = NotificationLite.error(paramThrowable);
    BehaviorSubscription[] arrayOfBehaviorSubscription = terminate(paramThrowable);
    int i = arrayOfBehaviorSubscription.length;
    for (int j = 0; j < i; j++) {
      arrayOfBehaviorSubscription[j].emitNext(paramThrowable, this.index);
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
    BehaviorSubscription[] arrayOfBehaviorSubscription = (BehaviorSubscription[])this.subscribers.get();
    int i = arrayOfBehaviorSubscription.length;
    for (int j = 0; j < i; j++) {
      arrayOfBehaviorSubscription[j].emitNext(paramT, this.index);
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (this.terminalEvent.get() != null)
    {
      paramSubscription.cancel();
      return;
    }
    paramSubscription.request(Long.MAX_VALUE);
  }
  
  void remove(BehaviorSubscription<T> paramBehaviorSubscription)
  {
    BehaviorSubscription[] arrayOfBehaviorSubscription1;
    BehaviorSubscription[] arrayOfBehaviorSubscription2;
    do
    {
      arrayOfBehaviorSubscription1 = (BehaviorSubscription[])this.subscribers.get();
      int i = arrayOfBehaviorSubscription1.length;
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
        if (arrayOfBehaviorSubscription1[k] == paramBehaviorSubscription)
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
        arrayOfBehaviorSubscription2 = EMPTY;
      }
      else
      {
        arrayOfBehaviorSubscription2 = new BehaviorSubscription[i - 1];
        System.arraycopy(arrayOfBehaviorSubscription1, 0, arrayOfBehaviorSubscription2, 0, m);
        System.arraycopy(arrayOfBehaviorSubscription1, m + 1, arrayOfBehaviorSubscription2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfBehaviorSubscription1, arrayOfBehaviorSubscription2));
  }
  
  void setCurrent(Object paramObject)
  {
    Lock localLock = this.writeLock;
    localLock.lock();
    this.index += 1L;
    this.value.lazySet(paramObject);
    localLock.unlock();
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject = new BehaviorSubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe((Subscription)localObject);
    if (add((BehaviorSubscription)localObject))
    {
      if (((BehaviorSubscription)localObject).cancelled) {
        remove((BehaviorSubscription)localObject);
      } else {
        ((BehaviorSubscription)localObject).emitFirst();
      }
    }
    else
    {
      localObject = (Throwable)this.terminalEvent.get();
      if (localObject == ExceptionHelper.TERMINATED) {
        paramSubscriber.onComplete();
      } else {
        paramSubscriber.onError((Throwable)localObject);
      }
    }
  }
  
  int subscriberCount()
  {
    return ((BehaviorSubscription[])this.subscribers.get()).length;
  }
  
  BehaviorSubscription<T>[] terminate(Object paramObject)
  {
    BehaviorSubscription[] arrayOfBehaviorSubscription1 = (BehaviorSubscription[])this.subscribers.get();
    BehaviorSubscription[] arrayOfBehaviorSubscription2 = TERMINATED;
    BehaviorSubscription[] arrayOfBehaviorSubscription3 = arrayOfBehaviorSubscription1;
    if (arrayOfBehaviorSubscription1 != arrayOfBehaviorSubscription2)
    {
      arrayOfBehaviorSubscription1 = (BehaviorSubscription[])this.subscribers.getAndSet(arrayOfBehaviorSubscription2);
      arrayOfBehaviorSubscription3 = arrayOfBehaviorSubscription1;
      if (arrayOfBehaviorSubscription1 != TERMINATED)
      {
        setCurrent(paramObject);
        arrayOfBehaviorSubscription3 = arrayOfBehaviorSubscription1;
      }
    }
    return arrayOfBehaviorSubscription3;
  }
  
  static final class BehaviorSubscription<T>
    extends AtomicLong
    implements Subscription, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object>
  {
    private static final long serialVersionUID = 3293175281126227086L;
    volatile boolean cancelled;
    final Subscriber<? super T> downstream;
    boolean emitting;
    boolean fastPath;
    long index;
    boolean next;
    AppendOnlyLinkedArrayList<Object> queue;
    final BehaviorProcessor<T> state;
    
    BehaviorSubscription(Subscriber<? super T> paramSubscriber, BehaviorProcessor<T> paramBehaviorProcessor)
    {
      this.downstream = paramSubscriber;
      this.state = paramBehaviorProcessor;
    }
    
    public void cancel()
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
        Lock localLock = ((BehaviorProcessor)localObject1).readLock;
        localLock.lock();
        this.index = ((BehaviorProcessor)localObject1).index;
        localObject1 = ((BehaviorProcessor)localObject1).value.get();
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
    
    public boolean isFull()
    {
      boolean bool;
      if (get() == 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this, paramLong);
      }
    }
    
    public boolean test(Object paramObject)
    {
      if (this.cancelled) {
        return true;
      }
      if (NotificationLite.isComplete(paramObject))
      {
        this.downstream.onComplete();
        return true;
      }
      if (NotificationLite.isError(paramObject))
      {
        this.downstream.onError(NotificationLite.getError(paramObject));
        return true;
      }
      long l = get();
      if (l != 0L)
      {
        this.downstream.onNext(NotificationLite.getValue(paramObject));
        if (l != Long.MAX_VALUE) {
          decrementAndGet();
        }
        return false;
      }
      cancel();
      this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
      return true;
    }
  }
}
