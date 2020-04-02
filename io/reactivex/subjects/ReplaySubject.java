package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ReplaySubject<T>
  extends Subject<T>
{
  static final ReplayDisposable[] EMPTY = new ReplayDisposable[0];
  private static final Object[] EMPTY_ARRAY = new Object[0];
  static final ReplayDisposable[] TERMINATED = new ReplayDisposable[0];
  final ReplayBuffer<T> buffer;
  boolean done;
  final AtomicReference<ReplayDisposable<T>[]> observers;
  
  ReplaySubject(ReplayBuffer<T> paramReplayBuffer)
  {
    this.buffer = paramReplayBuffer;
    this.observers = new AtomicReference(EMPTY);
  }
  
  @CheckReturnValue
  public static <T> ReplaySubject<T> create()
  {
    return new ReplaySubject(new UnboundedReplayBuffer(16));
  }
  
  @CheckReturnValue
  public static <T> ReplaySubject<T> create(int paramInt)
  {
    return new ReplaySubject(new UnboundedReplayBuffer(paramInt));
  }
  
  static <T> ReplaySubject<T> createUnbounded()
  {
    return new ReplaySubject(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
  }
  
  @CheckReturnValue
  public static <T> ReplaySubject<T> createWithSize(int paramInt)
  {
    return new ReplaySubject(new SizeBoundReplayBuffer(paramInt));
  }
  
  @CheckReturnValue
  public static <T> ReplaySubject<T> createWithTime(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return new ReplaySubject(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  public static <T> ReplaySubject<T> createWithTimeAndSize(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    return new ReplaySubject(new SizeAndTimeBoundReplayBuffer(paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  boolean add(ReplayDisposable<T> paramReplayDisposable)
  {
    ReplayDisposable[] arrayOfReplayDisposable1;
    ReplayDisposable[] arrayOfReplayDisposable2;
    do
    {
      arrayOfReplayDisposable1 = (ReplayDisposable[])this.observers.get();
      if (arrayOfReplayDisposable1 == TERMINATED) {
        return false;
      }
      int i = arrayOfReplayDisposable1.length;
      arrayOfReplayDisposable2 = new ReplayDisposable[i + 1];
      System.arraycopy(arrayOfReplayDisposable1, 0, arrayOfReplayDisposable2, 0, i);
      arrayOfReplayDisposable2[i] = paramReplayDisposable;
    } while (!this.observers.compareAndSet(arrayOfReplayDisposable1, arrayOfReplayDisposable2));
    return true;
  }
  
  public void cleanupBuffer()
  {
    this.buffer.trimHead();
  }
  
  public Throwable getThrowable()
  {
    Object localObject = this.buffer.get();
    if (NotificationLite.isError(localObject)) {
      return NotificationLite.getError(localObject);
    }
    return null;
  }
  
  public T getValue()
  {
    return this.buffer.getValue();
  }
  
  public Object[] getValues()
  {
    Object[] arrayOfObject1 = getValues((Object[])EMPTY_ARRAY);
    Object[] arrayOfObject2 = arrayOfObject1;
    if (arrayOfObject1 == EMPTY_ARRAY) {
      arrayOfObject2 = new Object[0];
    }
    return arrayOfObject2;
  }
  
  public T[] getValues(T[] paramArrayOfT)
  {
    return this.buffer.getValues(paramArrayOfT);
  }
  
  public boolean hasComplete()
  {
    return NotificationLite.isComplete(this.buffer.get());
  }
  
  public boolean hasObservers()
  {
    boolean bool;
    if (((ReplayDisposable[])this.observers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    return NotificationLite.isError(this.buffer.get());
  }
  
  public boolean hasValue()
  {
    boolean bool;
    if (this.buffer.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int observerCount()
  {
    return ((ReplayDisposable[])this.observers.get()).length;
  }
  
  public void onComplete()
  {
    if (this.done) {
      return;
    }
    this.done = true;
    Object localObject = NotificationLite.complete();
    ReplayBuffer localReplayBuffer = this.buffer;
    localReplayBuffer.addFinal(localObject);
    localObject = terminate(localObject);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      localReplayBuffer.replay(localObject[j]);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.done)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.done = true;
    Object localObject = NotificationLite.error(paramThrowable);
    paramThrowable = this.buffer;
    paramThrowable.addFinal(localObject);
    localObject = terminate(localObject);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      paramThrowable.replay(localObject[j]);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.done) {
      return;
    }
    ReplayBuffer localReplayBuffer = this.buffer;
    localReplayBuffer.add(paramT);
    paramT = (ReplayDisposable[])this.observers.get();
    int i = paramT.length;
    for (int j = 0; j < i; j++) {
      localReplayBuffer.replay(paramT[j]);
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (this.done) {
      paramDisposable.dispose();
    }
  }
  
  void remove(ReplayDisposable<T> paramReplayDisposable)
  {
    ReplayDisposable[] arrayOfReplayDisposable1;
    ReplayDisposable[] arrayOfReplayDisposable2;
    do
    {
      arrayOfReplayDisposable1 = (ReplayDisposable[])this.observers.get();
      if ((arrayOfReplayDisposable1 == TERMINATED) || (arrayOfReplayDisposable1 == EMPTY)) {
        break;
      }
      int i = arrayOfReplayDisposable1.length;
      int j = -1;
      int m;
      for (int k = 0;; k++)
      {
        m = j;
        if (k >= i) {
          break;
        }
        if (arrayOfReplayDisposable1[k] == paramReplayDisposable)
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
        arrayOfReplayDisposable2 = EMPTY;
      }
      else
      {
        arrayOfReplayDisposable2 = new ReplayDisposable[i - 1];
        System.arraycopy(arrayOfReplayDisposable1, 0, arrayOfReplayDisposable2, 0, m);
        System.arraycopy(arrayOfReplayDisposable1, m + 1, arrayOfReplayDisposable2, m, i - m - 1);
      }
    } while (!this.observers.compareAndSet(arrayOfReplayDisposable1, arrayOfReplayDisposable2));
  }
  
  int size()
  {
    return this.buffer.size();
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    ReplayDisposable localReplayDisposable = new ReplayDisposable(paramObserver, this);
    paramObserver.onSubscribe(localReplayDisposable);
    if (!localReplayDisposable.cancelled)
    {
      if ((add(localReplayDisposable)) && (localReplayDisposable.cancelled))
      {
        remove(localReplayDisposable);
        return;
      }
      this.buffer.replay(localReplayDisposable);
    }
  }
  
  ReplayDisposable<T>[] terminate(Object paramObject)
  {
    if (this.buffer.compareAndSet(null, paramObject)) {
      return (ReplayDisposable[])this.observers.getAndSet(TERMINATED);
    }
    return TERMINATED;
  }
  
  static final class Node<T>
    extends AtomicReference<Node<T>>
  {
    private static final long serialVersionUID = 6404226426336033100L;
    final T value;
    
    Node(T paramT)
    {
      this.value = paramT;
    }
  }
  
  static abstract interface ReplayBuffer<T>
  {
    public abstract void add(T paramT);
    
    public abstract void addFinal(Object paramObject);
    
    public abstract boolean compareAndSet(Object paramObject1, Object paramObject2);
    
    public abstract Object get();
    
    public abstract T getValue();
    
    public abstract T[] getValues(T[] paramArrayOfT);
    
    public abstract void replay(ReplaySubject.ReplayDisposable<T> paramReplayDisposable);
    
    public abstract int size();
    
    public abstract void trimHead();
  }
  
  static final class ReplayDisposable<T>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = 466549804534799122L;
    volatile boolean cancelled;
    final Observer<? super T> downstream;
    Object index;
    final ReplaySubject<T> state;
    
    ReplayDisposable(Observer<? super T> paramObserver, ReplaySubject<T> paramReplaySubject)
    {
      this.downstream = paramObserver;
      this.state = paramReplaySubject;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.state.remove(this);
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
  }
  
  static final class SizeAndTimeBoundReplayBuffer<T>
    extends AtomicReference<Object>
    implements ReplaySubject.ReplayBuffer<T>
  {
    private static final long serialVersionUID = -8056260896137901749L;
    volatile boolean done;
    volatile ReplaySubject.TimedNode<Object> head;
    final long maxAge;
    final int maxSize;
    final Scheduler scheduler;
    int size;
    ReplaySubject.TimedNode<Object> tail;
    final TimeUnit unit;
    
    SizeAndTimeBoundReplayBuffer(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.maxSize = ObjectHelper.verifyPositive(paramInt, "maxSize");
      this.maxAge = ObjectHelper.verifyPositive(paramLong, "maxAge");
      this.unit = ((TimeUnit)ObjectHelper.requireNonNull(paramTimeUnit, "unit is null"));
      this.scheduler = ((Scheduler)ObjectHelper.requireNonNull(paramScheduler, "scheduler is null"));
      paramTimeUnit = new ReplaySubject.TimedNode(null, 0L);
      this.tail = paramTimeUnit;
      this.head = paramTimeUnit;
    }
    
    public void add(T paramT)
    {
      ReplaySubject.TimedNode localTimedNode = new ReplaySubject.TimedNode(paramT, this.scheduler.now(this.unit));
      paramT = this.tail;
      this.tail = localTimedNode;
      this.size += 1;
      paramT.set(localTimedNode);
      trim();
    }
    
    public void addFinal(Object paramObject)
    {
      ReplaySubject.TimedNode localTimedNode = new ReplaySubject.TimedNode(paramObject, Long.MAX_VALUE);
      paramObject = this.tail;
      this.tail = localTimedNode;
      this.size += 1;
      paramObject.lazySet(localTimedNode);
      trimFinal();
      this.done = true;
    }
    
    ReplaySubject.TimedNode<Object> getHead()
    {
      Object localObject1 = this.head;
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplaySubject.TimedNode localTimedNode;
      for (Object localObject2 = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject1).get(); (localObject2 != null) && (((ReplaySubject.TimedNode)localObject2).time <= l1 - l2); localObject2 = localTimedNode)
      {
        localTimedNode = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject2).get();
        localObject1 = localObject2;
      }
      return localObject1;
    }
    
    public T getValue()
    {
      Object localObject1 = this.head;
      Object localObject2 = null;
      for (;;)
      {
        ReplaySubject.TimedNode localTimedNode = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject1).get();
        if (localTimedNode == null)
        {
          long l1 = this.scheduler.now(this.unit);
          long l2 = this.maxAge;
          if (((ReplaySubject.TimedNode)localObject1).time < l1 - l2) {
            return null;
          }
          localObject1 = ((ReplaySubject.TimedNode)localObject1).value;
          if (localObject1 == null) {
            return null;
          }
          if ((!NotificationLite.isComplete(localObject1)) && (!NotificationLite.isError(localObject1))) {
            return localObject1;
          }
          return localObject2.value;
        }
        localObject2 = localObject1;
        localObject1 = localTimedNode;
      }
    }
    
    public T[] getValues(T[] paramArrayOfT)
    {
      ReplaySubject.TimedNode localTimedNode = getHead();
      int i = size(localTimedNode);
      int j = 0;
      Object localObject1;
      if (i == 0)
      {
        localObject1 = paramArrayOfT;
        if (paramArrayOfT.length != 0)
        {
          paramArrayOfT[0] = null;
          localObject1 = paramArrayOfT;
        }
      }
      else
      {
        localObject1 = localTimedNode;
        int k = j;
        Object localObject2 = paramArrayOfT;
        if (paramArrayOfT.length < i)
        {
          localObject2 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
          k = j;
          localObject1 = localTimedNode;
        }
        while (k != i)
        {
          localObject1 = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject1).get();
          localObject2[k] = ((ReplaySubject.TimedNode)localObject1).value;
          k++;
        }
        localObject1 = localObject2;
        if (localObject2.length > i)
        {
          localObject2[i] = null;
          localObject1 = localObject2;
        }
      }
      return localObject1;
    }
    
    public void replay(ReplaySubject.ReplayDisposable<T> paramReplayDisposable)
    {
      if (paramReplayDisposable.getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = paramReplayDisposable.downstream;
      ReplaySubject.TimedNode localTimedNode = (ReplaySubject.TimedNode)paramReplayDisposable.index;
      Object localObject = localTimedNode;
      if (localTimedNode == null) {
        localObject = getHead();
      }
      int i = 1;
      if (paramReplayDisposable.cancelled)
      {
        paramReplayDisposable.index = null;
        return;
      }
      for (;;)
      {
        if (paramReplayDisposable.cancelled)
        {
          paramReplayDisposable.index = null;
          return;
        }
        localTimedNode = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject).get();
        if (localTimedNode == null)
        {
          if (((ReplaySubject.TimedNode)localObject).get() != null) {
            break;
          }
          paramReplayDisposable.index = localObject;
          int j = paramReplayDisposable.addAndGet(-i);
          i = j;
          if (j != 0) {
            break;
          }
          return;
        }
        localObject = localTimedNode.value;
        if ((this.done) && (localTimedNode.get() == null))
        {
          if (NotificationLite.isComplete(localObject)) {
            localObserver.onComplete();
          } else {
            localObserver.onError(NotificationLite.getError(localObject));
          }
          paramReplayDisposable.index = null;
          paramReplayDisposable.cancelled = true;
          return;
        }
        localObserver.onNext(localObject);
        localObject = localTimedNode;
      }
    }
    
    public int size()
    {
      return size(getHead());
    }
    
    int size(ReplaySubject.TimedNode<Object> paramTimedNode)
    {
      int i = 0;
      int j;
      for (;;)
      {
        j = i;
        if (i == Integer.MAX_VALUE) {
          break;
        }
        ReplaySubject.TimedNode localTimedNode = (ReplaySubject.TimedNode)paramTimedNode.get();
        if (localTimedNode == null)
        {
          paramTimedNode = paramTimedNode.value;
          if (!NotificationLite.isComplete(paramTimedNode))
          {
            j = i;
            if (!NotificationLite.isError(paramTimedNode)) {
              break;
            }
          }
          j = i - 1;
          break;
        }
        i++;
        paramTimedNode = localTimedNode;
      }
      return j;
    }
    
    void trim()
    {
      int i = this.size;
      if (i > this.maxSize)
      {
        this.size = (i - 1);
        this.head = ((ReplaySubject.TimedNode)this.head.get());
      }
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplaySubject.TimedNode localTimedNode;
      for (Object localObject = this.head;; localObject = localTimedNode)
      {
        localTimedNode = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject).get();
        if (localTimedNode == null)
        {
          this.head = ((ReplaySubject.TimedNode)localObject);
        }
        else
        {
          if (localTimedNode.time <= l1 - l2) {
            continue;
          }
          this.head = ((ReplaySubject.TimedNode)localObject);
        }
        return;
      }
    }
    
    void trimFinal()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplaySubject.TimedNode localTimedNode;
      for (Object localObject = this.head;; localObject = localTimedNode)
      {
        localTimedNode = (ReplaySubject.TimedNode)((ReplaySubject.TimedNode)localObject).get();
        if (localTimedNode.get() == null)
        {
          if (((ReplaySubject.TimedNode)localObject).value != null)
          {
            localTimedNode = new ReplaySubject.TimedNode(null, 0L);
            localTimedNode.lazySet(((ReplaySubject.TimedNode)localObject).get());
            this.head = localTimedNode;
          }
          else
          {
            this.head = ((ReplaySubject.TimedNode)localObject);
          }
        }
        else
        {
          if (localTimedNode.time <= l1 - l2) {
            continue;
          }
          if (((ReplaySubject.TimedNode)localObject).value != null)
          {
            localTimedNode = new ReplaySubject.TimedNode(null, 0L);
            localTimedNode.lazySet(((ReplaySubject.TimedNode)localObject).get());
            this.head = localTimedNode;
          }
          else
          {
            this.head = ((ReplaySubject.TimedNode)localObject);
          }
        }
        return;
      }
    }
    
    public void trimHead()
    {
      ReplaySubject.TimedNode localTimedNode1 = this.head;
      if (localTimedNode1.value != null)
      {
        ReplaySubject.TimedNode localTimedNode2 = new ReplaySubject.TimedNode(null, 0L);
        localTimedNode2.lazySet(localTimedNode1.get());
        this.head = localTimedNode2;
      }
    }
  }
  
  static final class SizeBoundReplayBuffer<T>
    extends AtomicReference<Object>
    implements ReplaySubject.ReplayBuffer<T>
  {
    private static final long serialVersionUID = 1107649250281456395L;
    volatile boolean done;
    volatile ReplaySubject.Node<Object> head;
    final int maxSize;
    int size;
    ReplaySubject.Node<Object> tail;
    
    SizeBoundReplayBuffer(int paramInt)
    {
      this.maxSize = ObjectHelper.verifyPositive(paramInt, "maxSize");
      ReplaySubject.Node localNode = new ReplaySubject.Node(null);
      this.tail = localNode;
      this.head = localNode;
    }
    
    public void add(T paramT)
    {
      ReplaySubject.Node localNode = new ReplaySubject.Node(paramT);
      paramT = this.tail;
      this.tail = localNode;
      this.size += 1;
      paramT.set(localNode);
      trim();
    }
    
    public void addFinal(Object paramObject)
    {
      paramObject = new ReplaySubject.Node(paramObject);
      ReplaySubject.Node localNode = this.tail;
      this.tail = paramObject;
      this.size += 1;
      localNode.lazySet(paramObject);
      trimHead();
      this.done = true;
    }
    
    public T getValue()
    {
      Object localObject1 = this.head;
      Object localObject2 = null;
      for (;;)
      {
        ReplaySubject.Node localNode = (ReplaySubject.Node)((ReplaySubject.Node)localObject1).get();
        if (localNode == null)
        {
          localObject1 = ((ReplaySubject.Node)localObject1).value;
          if (localObject1 == null) {
            return null;
          }
          if ((!NotificationLite.isComplete(localObject1)) && (!NotificationLite.isError(localObject1))) {
            return localObject1;
          }
          return localObject2.value;
        }
        localObject2 = localObject1;
        localObject1 = localNode;
      }
    }
    
    public T[] getValues(T[] paramArrayOfT)
    {
      ReplaySubject.Node localNode = this.head;
      int i = size();
      int j = 0;
      Object localObject1;
      if (i == 0)
      {
        localObject1 = paramArrayOfT;
        if (paramArrayOfT.length != 0)
        {
          paramArrayOfT[0] = null;
          localObject1 = paramArrayOfT;
        }
      }
      else
      {
        localObject1 = localNode;
        int k = j;
        Object localObject2 = paramArrayOfT;
        if (paramArrayOfT.length < i)
        {
          localObject2 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
          k = j;
          localObject1 = localNode;
        }
        while (k != i)
        {
          localObject1 = (ReplaySubject.Node)((ReplaySubject.Node)localObject1).get();
          localObject2[k] = ((ReplaySubject.Node)localObject1).value;
          k++;
        }
        localObject1 = localObject2;
        if (localObject2.length > i)
        {
          localObject2[i] = null;
          localObject1 = localObject2;
        }
      }
      return localObject1;
    }
    
    public void replay(ReplaySubject.ReplayDisposable<T> paramReplayDisposable)
    {
      if (paramReplayDisposable.getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = paramReplayDisposable.downstream;
      ReplaySubject.Node localNode = (ReplaySubject.Node)paramReplayDisposable.index;
      Object localObject = localNode;
      if (localNode == null) {
        localObject = this.head;
      }
      int i = 1;
      for (;;)
      {
        if (paramReplayDisposable.cancelled)
        {
          paramReplayDisposable.index = null;
          return;
        }
        localNode = (ReplaySubject.Node)((ReplaySubject.Node)localObject).get();
        if (localNode == null)
        {
          if (((ReplaySubject.Node)localObject).get() == null)
          {
            paramReplayDisposable.index = localObject;
            int j = paramReplayDisposable.addAndGet(-i);
            i = j;
            if (j != 0) {}
          }
        }
        else
        {
          localObject = localNode.value;
          if ((this.done) && (localNode.get() == null))
          {
            if (NotificationLite.isComplete(localObject)) {
              localObserver.onComplete();
            } else {
              localObserver.onError(NotificationLite.getError(localObject));
            }
            paramReplayDisposable.index = null;
            paramReplayDisposable.cancelled = true;
            return;
          }
          localObserver.onNext(localObject);
          localObject = localNode;
        }
      }
    }
    
    public int size()
    {
      Object localObject = this.head;
      int i = 0;
      int j;
      for (;;)
      {
        j = i;
        if (i == Integer.MAX_VALUE) {
          break;
        }
        ReplaySubject.Node localNode = (ReplaySubject.Node)((ReplaySubject.Node)localObject).get();
        if (localNode == null)
        {
          localObject = ((ReplaySubject.Node)localObject).value;
          if (!NotificationLite.isComplete(localObject))
          {
            j = i;
            if (!NotificationLite.isError(localObject)) {
              break;
            }
          }
          j = i - 1;
          break;
        }
        i++;
        localObject = localNode;
      }
      return j;
    }
    
    void trim()
    {
      int i = this.size;
      if (i > this.maxSize)
      {
        this.size = (i - 1);
        this.head = ((ReplaySubject.Node)this.head.get());
      }
    }
    
    public void trimHead()
    {
      ReplaySubject.Node localNode1 = this.head;
      if (localNode1.value != null)
      {
        ReplaySubject.Node localNode2 = new ReplaySubject.Node(null);
        localNode2.lazySet(localNode1.get());
        this.head = localNode2;
      }
    }
  }
  
  static final class TimedNode<T>
    extends AtomicReference<TimedNode<T>>
  {
    private static final long serialVersionUID = 6404226426336033100L;
    final long time;
    final T value;
    
    TimedNode(T paramT, long paramLong)
    {
      this.value = paramT;
      this.time = paramLong;
    }
  }
  
  static final class UnboundedReplayBuffer<T>
    extends AtomicReference<Object>
    implements ReplaySubject.ReplayBuffer<T>
  {
    private static final long serialVersionUID = -733876083048047795L;
    final List<Object> buffer;
    volatile boolean done;
    volatile int size;
    
    UnboundedReplayBuffer(int paramInt)
    {
      this.buffer = new ArrayList(ObjectHelper.verifyPositive(paramInt, "capacityHint"));
    }
    
    public void add(T paramT)
    {
      this.buffer.add(paramT);
      this.size += 1;
    }
    
    public void addFinal(Object paramObject)
    {
      this.buffer.add(paramObject);
      trimHead();
      this.size += 1;
      this.done = true;
    }
    
    public T getValue()
    {
      int i = this.size;
      if (i != 0)
      {
        List localList = this.buffer;
        Object localObject = localList.get(i - 1);
        if ((!NotificationLite.isComplete(localObject)) && (!NotificationLite.isError(localObject))) {
          return localObject;
        }
        if (i == 1) {
          return null;
        }
        return localList.get(i - 2);
      }
      return null;
    }
    
    public T[] getValues(T[] paramArrayOfT)
    {
      int i = this.size;
      int j = 0;
      if (i == 0)
      {
        if (paramArrayOfT.length != 0) {
          paramArrayOfT[0] = null;
        }
        return paramArrayOfT;
      }
      List localList = this.buffer;
      Object localObject = localList.get(i - 1);
      int k;
      if (!NotificationLite.isComplete(localObject))
      {
        k = i;
        if (!NotificationLite.isError(localObject)) {}
      }
      else
      {
        i--;
        k = i;
        if (i == 0)
        {
          if (paramArrayOfT.length != 0) {
            paramArrayOfT[0] = null;
          }
          return paramArrayOfT;
        }
      }
      i = j;
      localObject = paramArrayOfT;
      if (paramArrayOfT.length < k) {
        localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), k);
      }
      for (i = j; i < k; i++) {
        localObject[i] = localList.get(i);
      }
      if (localObject.length > k) {
        localObject[k] = null;
      }
      return localObject;
    }
    
    public void replay(ReplaySubject.ReplayDisposable<T> paramReplayDisposable)
    {
      if (paramReplayDisposable.getAndIncrement() != 0) {
        return;
      }
      List localList = this.buffer;
      Observer localObserver = paramReplayDisposable.downstream;
      Object localObject = (Integer)paramReplayDisposable.index;
      int i = 0;
      if (localObject != null) {
        i = ((Integer)localObject).intValue();
      } else {
        paramReplayDisposable.index = Integer.valueOf(0);
      }
      int j = 1;
      int k = i;
      i = j;
      do
      {
        do
        {
          if (paramReplayDisposable.cancelled)
          {
            paramReplayDisposable.index = null;
            return;
          }
          for (int m = this.size; m != k; m = j)
          {
            if (paramReplayDisposable.cancelled)
            {
              paramReplayDisposable.index = null;
              return;
            }
            localObject = localList.get(k);
            j = m;
            if (this.done)
            {
              int n = k + 1;
              j = m;
              if (n == m)
              {
                m = this.size;
                j = m;
                if (n == m)
                {
                  if (NotificationLite.isComplete(localObject)) {
                    localObserver.onComplete();
                  } else {
                    localObserver.onError(NotificationLite.getError(localObject));
                  }
                  paramReplayDisposable.index = null;
                  paramReplayDisposable.cancelled = true;
                  return;
                }
              }
            }
            localObserver.onNext(localObject);
            k++;
          }
        } while (k != this.size);
        paramReplayDisposable.index = Integer.valueOf(k);
        j = paramReplayDisposable.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public int size()
    {
      int i = this.size;
      if (i != 0)
      {
        Object localObject = this.buffer;
        int j = i - 1;
        localObject = ((List)localObject).get(j);
        if ((!NotificationLite.isComplete(localObject)) && (!NotificationLite.isError(localObject))) {
          return i;
        }
        return j;
      }
      return 0;
    }
    
    public void trimHead() {}
  }
}
