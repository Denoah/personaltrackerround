package io.reactivex.processors;

import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ReplayProcessor<T>
  extends FlowableProcessor<T>
{
  static final ReplaySubscription[] EMPTY = new ReplaySubscription[0];
  private static final Object[] EMPTY_ARRAY = new Object[0];
  static final ReplaySubscription[] TERMINATED = new ReplaySubscription[0];
  final ReplayBuffer<T> buffer;
  boolean done;
  final AtomicReference<ReplaySubscription<T>[]> subscribers;
  
  ReplayProcessor(ReplayBuffer<T> paramReplayBuffer)
  {
    this.buffer = paramReplayBuffer;
    this.subscribers = new AtomicReference(EMPTY);
  }
  
  @CheckReturnValue
  public static <T> ReplayProcessor<T> create()
  {
    return new ReplayProcessor(new UnboundedReplayBuffer(16));
  }
  
  @CheckReturnValue
  public static <T> ReplayProcessor<T> create(int paramInt)
  {
    return new ReplayProcessor(new UnboundedReplayBuffer(paramInt));
  }
  
  static <T> ReplayProcessor<T> createUnbounded()
  {
    return new ReplayProcessor(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
  }
  
  @CheckReturnValue
  public static <T> ReplayProcessor<T> createWithSize(int paramInt)
  {
    return new ReplayProcessor(new SizeBoundReplayBuffer(paramInt));
  }
  
  @CheckReturnValue
  public static <T> ReplayProcessor<T> createWithTime(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return new ReplayProcessor(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  public static <T> ReplayProcessor<T> createWithTimeAndSize(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    return new ReplayProcessor(new SizeAndTimeBoundReplayBuffer(paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  boolean add(ReplaySubscription<T> paramReplaySubscription)
  {
    ReplaySubscription[] arrayOfReplaySubscription1;
    ReplaySubscription[] arrayOfReplaySubscription2;
    do
    {
      arrayOfReplaySubscription1 = (ReplaySubscription[])this.subscribers.get();
      if (arrayOfReplaySubscription1 == TERMINATED) {
        return false;
      }
      int i = arrayOfReplaySubscription1.length;
      arrayOfReplaySubscription2 = new ReplaySubscription[i + 1];
      System.arraycopy(arrayOfReplaySubscription1, 0, arrayOfReplaySubscription2, 0, i);
      arrayOfReplaySubscription2[i] = paramReplaySubscription;
    } while (!this.subscribers.compareAndSet(arrayOfReplaySubscription1, arrayOfReplaySubscription2));
    return true;
  }
  
  public void cleanupBuffer()
  {
    this.buffer.trimHead();
  }
  
  public Throwable getThrowable()
  {
    ReplayBuffer localReplayBuffer = this.buffer;
    if (localReplayBuffer.isDone()) {
      return localReplayBuffer.getError();
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
    ReplayBuffer localReplayBuffer = this.buffer;
    boolean bool;
    if ((localReplayBuffer.isDone()) && (localReplayBuffer.getError() == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasSubscribers()
  {
    boolean bool;
    if (((ReplaySubscription[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    ReplayBuffer localReplayBuffer = this.buffer;
    boolean bool;
    if ((localReplayBuffer.isDone()) && (localReplayBuffer.getError() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
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
  
  public void onComplete()
  {
    if (this.done) {
      return;
    }
    this.done = true;
    ReplayBuffer localReplayBuffer = this.buffer;
    localReplayBuffer.complete();
    ReplaySubscription[] arrayOfReplaySubscription = (ReplaySubscription[])this.subscribers.getAndSet(TERMINATED);
    int i = arrayOfReplaySubscription.length;
    for (int j = 0; j < i; j++) {
      localReplayBuffer.replay(arrayOfReplaySubscription[j]);
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
    ReplayBuffer localReplayBuffer = this.buffer;
    localReplayBuffer.error(paramThrowable);
    paramThrowable = (ReplaySubscription[])this.subscribers.getAndSet(TERMINATED);
    int i = paramThrowable.length;
    for (int j = 0; j < i; j++) {
      localReplayBuffer.replay(paramThrowable[j]);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.done) {
      return;
    }
    ReplayBuffer localReplayBuffer = this.buffer;
    localReplayBuffer.next(paramT);
    paramT = (ReplaySubscription[])this.subscribers.get();
    int i = paramT.length;
    for (int j = 0; j < i; j++) {
      localReplayBuffer.replay(paramT[j]);
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (this.done)
    {
      paramSubscription.cancel();
      return;
    }
    paramSubscription.request(Long.MAX_VALUE);
  }
  
  void remove(ReplaySubscription<T> paramReplaySubscription)
  {
    ReplaySubscription[] arrayOfReplaySubscription1;
    ReplaySubscription[] arrayOfReplaySubscription2;
    do
    {
      arrayOfReplaySubscription1 = (ReplaySubscription[])this.subscribers.get();
      if ((arrayOfReplaySubscription1 == TERMINATED) || (arrayOfReplaySubscription1 == EMPTY)) {
        break;
      }
      int i = arrayOfReplaySubscription1.length;
      int j = -1;
      int m;
      for (int k = 0;; k++)
      {
        m = j;
        if (k >= i) {
          break;
        }
        if (arrayOfReplaySubscription1[k] == paramReplaySubscription)
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
        arrayOfReplaySubscription2 = EMPTY;
      }
      else
      {
        arrayOfReplaySubscription2 = new ReplaySubscription[i - 1];
        System.arraycopy(arrayOfReplaySubscription1, 0, arrayOfReplaySubscription2, 0, m);
        System.arraycopy(arrayOfReplaySubscription1, m + 1, arrayOfReplaySubscription2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfReplaySubscription1, arrayOfReplaySubscription2));
  }
  
  int size()
  {
    return this.buffer.size();
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    ReplaySubscription localReplaySubscription = new ReplaySubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe(localReplaySubscription);
    if ((add(localReplaySubscription)) && (localReplaySubscription.cancelled))
    {
      remove(localReplaySubscription);
      return;
    }
    this.buffer.replay(localReplaySubscription);
  }
  
  int subscriberCount()
  {
    return ((ReplaySubscription[])this.subscribers.get()).length;
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
    public abstract void complete();
    
    public abstract void error(Throwable paramThrowable);
    
    public abstract Throwable getError();
    
    public abstract T getValue();
    
    public abstract T[] getValues(T[] paramArrayOfT);
    
    public abstract boolean isDone();
    
    public abstract void next(T paramT);
    
    public abstract void replay(ReplayProcessor.ReplaySubscription<T> paramReplaySubscription);
    
    public abstract int size();
    
    public abstract void trimHead();
  }
  
  static final class ReplaySubscription<T>
    extends AtomicInteger
    implements Subscription
  {
    private static final long serialVersionUID = 466549804534799122L;
    volatile boolean cancelled;
    final Subscriber<? super T> downstream;
    long emitted;
    Object index;
    final AtomicLong requested;
    final ReplayProcessor<T> state;
    
    ReplaySubscription(Subscriber<? super T> paramSubscriber, ReplayProcessor<T> paramReplayProcessor)
    {
      this.downstream = paramSubscriber;
      this.state = paramReplayProcessor;
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.state.remove(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        this.state.buffer.replay(this);
      }
    }
  }
  
  static final class SizeAndTimeBoundReplayBuffer<T>
    implements ReplayProcessor.ReplayBuffer<T>
  {
    volatile boolean done;
    Throwable error;
    volatile ReplayProcessor.TimedNode<T> head;
    final long maxAge;
    final int maxSize;
    final Scheduler scheduler;
    int size;
    ReplayProcessor.TimedNode<T> tail;
    final TimeUnit unit;
    
    SizeAndTimeBoundReplayBuffer(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.maxSize = ObjectHelper.verifyPositive(paramInt, "maxSize");
      this.maxAge = ObjectHelper.verifyPositive(paramLong, "maxAge");
      this.unit = ((TimeUnit)ObjectHelper.requireNonNull(paramTimeUnit, "unit is null"));
      this.scheduler = ((Scheduler)ObjectHelper.requireNonNull(paramScheduler, "scheduler is null"));
      paramTimeUnit = new ReplayProcessor.TimedNode(null, 0L);
      this.tail = paramTimeUnit;
      this.head = paramTimeUnit;
    }
    
    public void complete()
    {
      trimFinal();
      this.done = true;
    }
    
    public void error(Throwable paramThrowable)
    {
      trimFinal();
      this.error = paramThrowable;
      this.done = true;
    }
    
    public Throwable getError()
    {
      return this.error;
    }
    
    ReplayProcessor.TimedNode<T> getHead()
    {
      Object localObject1 = this.head;
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplayProcessor.TimedNode localTimedNode;
      for (Object localObject2 = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject1).get(); (localObject2 != null) && (((ReplayProcessor.TimedNode)localObject2).time <= l1 - l2); localObject2 = localTimedNode)
      {
        localTimedNode = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject2).get();
        localObject1 = localObject2;
      }
      return localObject1;
    }
    
    public T getValue()
    {
      ReplayProcessor.TimedNode localTimedNode;
      for (Object localObject = this.head;; localObject = localTimedNode)
      {
        localTimedNode = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject).get();
        if (localTimedNode == null)
        {
          long l1 = this.scheduler.now(this.unit);
          long l2 = this.maxAge;
          if (((ReplayProcessor.TimedNode)localObject).time < l1 - l2) {
            return null;
          }
          return ((ReplayProcessor.TimedNode)localObject).value;
        }
      }
    }
    
    public T[] getValues(T[] paramArrayOfT)
    {
      ReplayProcessor.TimedNode localTimedNode = getHead();
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
          localObject1 = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject1).get();
          localObject2[k] = ((ReplayProcessor.TimedNode)localObject1).value;
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
    
    public boolean isDone()
    {
      return this.done;
    }
    
    public void next(T paramT)
    {
      ReplayProcessor.TimedNode localTimedNode = new ReplayProcessor.TimedNode(paramT, this.scheduler.now(this.unit));
      paramT = this.tail;
      this.tail = localTimedNode;
      this.size += 1;
      paramT.set(localTimedNode);
      trim();
    }
    
    public void replay(ReplayProcessor.ReplaySubscription<T> paramReplaySubscription)
    {
      if (paramReplaySubscription.getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = paramReplaySubscription.downstream;
      ReplayProcessor.TimedNode localTimedNode1 = (ReplayProcessor.TimedNode)paramReplaySubscription.index;
      ReplayProcessor.TimedNode localTimedNode2 = localTimedNode1;
      if (localTimedNode1 == null) {
        localTimedNode2 = getHead();
      }
      long l1 = paramReplaySubscription.emitted;
      int i = 1;
      int j;
      do
      {
        long l2 = paramReplaySubscription.requested.get();
        boolean bool1;
        for (;;)
        {
          bool1 = l1 < l2;
          if (!bool1) {
            break;
          }
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          boolean bool2 = this.done;
          localTimedNode1 = (ReplayProcessor.TimedNode)localTimedNode2.get();
          if (localTimedNode1 == null) {
            j = 1;
          } else {
            j = 0;
          }
          if ((bool2) && (j != 0))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
          if (j != 0) {
            break;
          }
          localSubscriber.onNext(localTimedNode1.value);
          l1 += 1L;
          localTimedNode2 = localTimedNode1;
        }
        if (!bool1)
        {
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          if ((this.done) && (localTimedNode2.get() == null))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
        }
        paramReplaySubscription.index = localTimedNode2;
        paramReplaySubscription.emitted = l1;
        j = paramReplaySubscription.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public int size()
    {
      return size(getHead());
    }
    
    int size(ReplayProcessor.TimedNode<T> paramTimedNode)
    {
      for (int i = 0; i != Integer.MAX_VALUE; i++)
      {
        paramTimedNode = (ReplayProcessor.TimedNode)paramTimedNode.get();
        if (paramTimedNode == null) {
          break;
        }
      }
      return i;
    }
    
    void trim()
    {
      int i = this.size;
      if (i > this.maxSize)
      {
        this.size = (i - 1);
        this.head = ((ReplayProcessor.TimedNode)this.head.get());
      }
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplayProcessor.TimedNode localTimedNode;
      for (Object localObject = this.head;; localObject = localTimedNode)
      {
        localTimedNode = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject).get();
        if (localTimedNode == null)
        {
          this.head = ((ReplayProcessor.TimedNode)localObject);
        }
        else
        {
          if (localTimedNode.time <= l1 - l2) {
            continue;
          }
          this.head = ((ReplayProcessor.TimedNode)localObject);
        }
        return;
      }
    }
    
    void trimFinal()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      ReplayProcessor.TimedNode localTimedNode;
      for (Object localObject = this.head;; localObject = localTimedNode)
      {
        localTimedNode = (ReplayProcessor.TimedNode)((ReplayProcessor.TimedNode)localObject).get();
        if (localTimedNode == null)
        {
          if (((ReplayProcessor.TimedNode)localObject).value != null) {
            this.head = new ReplayProcessor.TimedNode(null, 0L);
          } else {
            this.head = ((ReplayProcessor.TimedNode)localObject);
          }
        }
        else
        {
          if (localTimedNode.time <= l1 - l2) {
            continue;
          }
          if (((ReplayProcessor.TimedNode)localObject).value != null)
          {
            localTimedNode = new ReplayProcessor.TimedNode(null, 0L);
            localTimedNode.lazySet(((ReplayProcessor.TimedNode)localObject).get());
            this.head = localTimedNode;
          }
          else
          {
            this.head = ((ReplayProcessor.TimedNode)localObject);
          }
        }
        return;
      }
    }
    
    public void trimHead()
    {
      if (this.head.value != null)
      {
        ReplayProcessor.TimedNode localTimedNode = new ReplayProcessor.TimedNode(null, 0L);
        localTimedNode.lazySet(this.head.get());
        this.head = localTimedNode;
      }
    }
  }
  
  static final class SizeBoundReplayBuffer<T>
    implements ReplayProcessor.ReplayBuffer<T>
  {
    volatile boolean done;
    Throwable error;
    volatile ReplayProcessor.Node<T> head;
    final int maxSize;
    int size;
    ReplayProcessor.Node<T> tail;
    
    SizeBoundReplayBuffer(int paramInt)
    {
      this.maxSize = ObjectHelper.verifyPositive(paramInt, "maxSize");
      ReplayProcessor.Node localNode = new ReplayProcessor.Node(null);
      this.tail = localNode;
      this.head = localNode;
    }
    
    public void complete()
    {
      trimHead();
      this.done = true;
    }
    
    public void error(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      trimHead();
      this.done = true;
    }
    
    public Throwable getError()
    {
      return this.error;
    }
    
    public T getValue()
    {
      ReplayProcessor.Node localNode;
      for (Object localObject = this.head;; localObject = localNode)
      {
        localNode = (ReplayProcessor.Node)((ReplayProcessor.Node)localObject).get();
        if (localNode == null) {
          return ((ReplayProcessor.Node)localObject).value;
        }
      }
    }
    
    public T[] getValues(T[] paramArrayOfT)
    {
      ReplayProcessor.Node localNode1 = this.head;
      int i = 0;
      Object localObject = localNode1;
      for (int j = 0;; j++)
      {
        localObject = (ReplayProcessor.Node)((ReplayProcessor.Node)localObject).get();
        if (localObject == null)
        {
          ReplayProcessor.Node localNode2 = localNode1;
          int k = i;
          localObject = paramArrayOfT;
          if (paramArrayOfT.length < j)
          {
            localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
            k = i;
            localNode2 = localNode1;
          }
          while (k < j)
          {
            localNode2 = (ReplayProcessor.Node)localNode2.get();
            localObject[k] = localNode2.value;
            k++;
          }
          if (localObject.length > j) {
            localObject[j] = null;
          }
          return localObject;
        }
      }
    }
    
    public boolean isDone()
    {
      return this.done;
    }
    
    public void next(T paramT)
    {
      paramT = new ReplayProcessor.Node(paramT);
      ReplayProcessor.Node localNode = this.tail;
      this.tail = paramT;
      this.size += 1;
      localNode.set(paramT);
      trim();
    }
    
    public void replay(ReplayProcessor.ReplaySubscription<T> paramReplaySubscription)
    {
      if (paramReplaySubscription.getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = paramReplaySubscription.downstream;
      ReplayProcessor.Node localNode1 = (ReplayProcessor.Node)paramReplaySubscription.index;
      ReplayProcessor.Node localNode2 = localNode1;
      if (localNode1 == null) {
        localNode2 = this.head;
      }
      long l1 = paramReplaySubscription.emitted;
      int i = 1;
      int j;
      do
      {
        long l2 = paramReplaySubscription.requested.get();
        boolean bool1;
        for (;;)
        {
          bool1 = l1 < l2;
          if (!bool1) {
            break;
          }
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          boolean bool2 = this.done;
          localNode1 = (ReplayProcessor.Node)localNode2.get();
          if (localNode1 == null) {
            j = 1;
          } else {
            j = 0;
          }
          if ((bool2) && (j != 0))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
          if (j != 0) {
            break;
          }
          localSubscriber.onNext(localNode1.value);
          l1 += 1L;
          localNode2 = localNode1;
        }
        if (!bool1)
        {
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          if ((this.done) && (localNode2.get() == null))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
        }
        paramReplaySubscription.index = localNode2;
        paramReplaySubscription.emitted = l1;
        j = paramReplaySubscription.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public int size()
    {
      ReplayProcessor.Node localNode = this.head;
      for (int i = 0; i != Integer.MAX_VALUE; i++)
      {
        localNode = (ReplayProcessor.Node)localNode.get();
        if (localNode == null) {
          break;
        }
      }
      return i;
    }
    
    void trim()
    {
      int i = this.size;
      if (i > this.maxSize)
      {
        this.size = (i - 1);
        this.head = ((ReplayProcessor.Node)this.head.get());
      }
    }
    
    public void trimHead()
    {
      if (this.head.value != null)
      {
        ReplayProcessor.Node localNode = new ReplayProcessor.Node(null);
        localNode.lazySet(this.head.get());
        this.head = localNode;
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
    implements ReplayProcessor.ReplayBuffer<T>
  {
    final List<T> buffer;
    volatile boolean done;
    Throwable error;
    volatile int size;
    
    UnboundedReplayBuffer(int paramInt)
    {
      this.buffer = new ArrayList(ObjectHelper.verifyPositive(paramInt, "capacityHint"));
    }
    
    public void complete()
    {
      this.done = true;
    }
    
    public void error(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
    }
    
    public Throwable getError()
    {
      return this.error;
    }
    
    public T getValue()
    {
      int i = this.size;
      if (i == 0) {
        return null;
      }
      return this.buffer.get(i - 1);
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
      int k = j;
      Object localObject = paramArrayOfT;
      if (paramArrayOfT.length < i) {
        localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
      }
      for (k = j; k < i; k++) {
        localObject[k] = localList.get(k);
      }
      if (localObject.length > i) {
        localObject[i] = null;
      }
      return localObject;
    }
    
    public boolean isDone()
    {
      return this.done;
    }
    
    public void next(T paramT)
    {
      this.buffer.add(paramT);
      this.size += 1;
    }
    
    public void replay(ReplayProcessor.ReplaySubscription<T> paramReplaySubscription)
    {
      if (paramReplaySubscription.getAndIncrement() != 0) {
        return;
      }
      List localList = this.buffer;
      Subscriber localSubscriber = paramReplaySubscription.downstream;
      Integer localInteger = (Integer)paramReplaySubscription.index;
      int i = 0;
      if (localInteger != null) {
        i = localInteger.intValue();
      } else {
        paramReplaySubscription.index = Integer.valueOf(0);
      }
      long l1 = paramReplaySubscription.emitted;
      int j = 1;
      int k;
      do
      {
        long l2 = paramReplaySubscription.requested.get();
        boolean bool1;
        boolean bool2;
        for (;;)
        {
          bool1 = l1 < l2;
          if (!bool1) {
            break;
          }
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          bool2 = this.done;
          int m = this.size;
          if ((bool2) && (i == m))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
          if (i == m) {
            break;
          }
          localSubscriber.onNext(localList.get(i));
          i++;
          l1 += 1L;
        }
        if (!bool1)
        {
          if (paramReplaySubscription.cancelled)
          {
            paramReplaySubscription.index = null;
            return;
          }
          bool2 = this.done;
          k = this.size;
          if ((bool2) && (i == k))
          {
            paramReplaySubscription.index = null;
            paramReplaySubscription.cancelled = true;
            paramReplaySubscription = this.error;
            if (paramReplaySubscription == null) {
              localSubscriber.onComplete();
            } else {
              localSubscriber.onError(paramReplaySubscription);
            }
            return;
          }
        }
        paramReplaySubscription.index = Integer.valueOf(i);
        paramReplaySubscription.emitted = l1;
        k = paramReplaySubscription.addAndGet(-j);
        j = k;
      } while (k != 0);
    }
    
    public int size()
    {
      return this.size;
    }
    
    public void trimHead() {}
  }
}
