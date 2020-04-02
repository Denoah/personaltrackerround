package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCache<T>
  extends AbstractFlowableWithUpstream<T, T>
  implements FlowableSubscriber<T>
{
  static final CacheSubscription[] EMPTY = new CacheSubscription[0];
  static final CacheSubscription[] TERMINATED = new CacheSubscription[0];
  final int capacityHint;
  volatile boolean done;
  Throwable error;
  final Node<T> head;
  final AtomicBoolean once;
  volatile long size;
  final AtomicReference<CacheSubscription<T>[]> subscribers;
  Node<T> tail;
  int tailOffset;
  
  public FlowableCache(Flowable<T> paramFlowable, int paramInt)
  {
    super(paramFlowable);
    this.capacityHint = paramInt;
    this.once = new AtomicBoolean();
    paramFlowable = new Node(paramInt);
    this.head = paramFlowable;
    this.tail = paramFlowable;
    this.subscribers = new AtomicReference(EMPTY);
  }
  
  void add(CacheSubscription<T> paramCacheSubscription)
  {
    CacheSubscription[] arrayOfCacheSubscription1;
    CacheSubscription[] arrayOfCacheSubscription2;
    do
    {
      arrayOfCacheSubscription1 = (CacheSubscription[])this.subscribers.get();
      if (arrayOfCacheSubscription1 == TERMINATED) {
        return;
      }
      int i = arrayOfCacheSubscription1.length;
      arrayOfCacheSubscription2 = new CacheSubscription[i + 1];
      System.arraycopy(arrayOfCacheSubscription1, 0, arrayOfCacheSubscription2, 0, i);
      arrayOfCacheSubscription2[i] = paramCacheSubscription;
    } while (!this.subscribers.compareAndSet(arrayOfCacheSubscription1, arrayOfCacheSubscription2));
  }
  
  long cachedEventCount()
  {
    return this.size;
  }
  
  boolean hasSubscribers()
  {
    boolean bool;
    if (((CacheSubscription[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isConnected()
  {
    return this.once.get();
  }
  
  public void onComplete()
  {
    this.done = true;
    CacheSubscription[] arrayOfCacheSubscription = (CacheSubscription[])this.subscribers.getAndSet(TERMINATED);
    int i = arrayOfCacheSubscription.length;
    for (int j = 0; j < i; j++) {
      replay(arrayOfCacheSubscription[j]);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.done)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.error = paramThrowable;
    this.done = true;
    paramThrowable = (CacheSubscription[])this.subscribers.getAndSet(TERMINATED);
    int i = paramThrowable.length;
    for (int j = 0; j < i; j++) {
      replay(paramThrowable[j]);
    }
  }
  
  public void onNext(T paramT)
  {
    int i = this.tailOffset;
    int j = this.capacityHint;
    int k = 0;
    if (i == j)
    {
      Node localNode = new Node(i);
      localNode.values[0] = paramT;
      this.tailOffset = 1;
      this.tail.next = localNode;
      this.tail = localNode;
    }
    else
    {
      this.tail.values[i] = paramT;
      this.tailOffset = (i + 1);
    }
    this.size += 1L;
    paramT = (CacheSubscription[])this.subscribers.get();
    j = paramT.length;
    while (k < j)
    {
      replay(paramT[k]);
      k++;
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    paramSubscription.request(Long.MAX_VALUE);
  }
  
  void remove(CacheSubscription<T> paramCacheSubscription)
  {
    CacheSubscription[] arrayOfCacheSubscription1;
    CacheSubscription[] arrayOfCacheSubscription2;
    do
    {
      arrayOfCacheSubscription1 = (CacheSubscription[])this.subscribers.get();
      int i = arrayOfCacheSubscription1.length;
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
        if (arrayOfCacheSubscription1[k] == paramCacheSubscription)
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
        arrayOfCacheSubscription2 = EMPTY;
      }
      else
      {
        arrayOfCacheSubscription2 = new CacheSubscription[i - 1];
        System.arraycopy(arrayOfCacheSubscription1, 0, arrayOfCacheSubscription2, 0, m);
        System.arraycopy(arrayOfCacheSubscription1, m + 1, arrayOfCacheSubscription2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfCacheSubscription1, arrayOfCacheSubscription2));
  }
  
  void replay(CacheSubscription<T> paramCacheSubscription)
  {
    if (paramCacheSubscription.getAndIncrement() != 0) {
      return;
    }
    long l1 = paramCacheSubscription.index;
    int i = paramCacheSubscription.offset;
    Object localObject1 = paramCacheSubscription.node;
    AtomicLong localAtomicLong = paramCacheSubscription.requested;
    Subscriber localSubscriber = paramCacheSubscription.downstream;
    int j = this.capacityHint;
    int k = 1;
    int m;
    do
    {
      for (;;)
      {
        boolean bool = this.done;
        if (this.size == l1) {
          m = 1;
        } else {
          m = 0;
        }
        if ((bool) && (m != 0))
        {
          paramCacheSubscription.node = null;
          paramCacheSubscription = this.error;
          if (paramCacheSubscription != null) {
            localSubscriber.onError(paramCacheSubscription);
          } else {
            localSubscriber.onComplete();
          }
          return;
        }
        if (m != 0) {
          break;
        }
        long l2 = localAtomicLong.get();
        if (l2 == Long.MIN_VALUE)
        {
          paramCacheSubscription.node = null;
          return;
        }
        if (l2 == l1) {
          break;
        }
        m = i;
        Object localObject2 = localObject1;
        if (i == j)
        {
          localObject2 = ((Node)localObject1).next;
          m = 0;
        }
        localSubscriber.onNext(localObject2.values[m]);
        i = m + 1;
        l1 += 1L;
        localObject1 = localObject2;
      }
      paramCacheSubscription.index = l1;
      paramCacheSubscription.offset = i;
      paramCacheSubscription.node = ((Node)localObject1);
      m = paramCacheSubscription.addAndGet(-k);
      k = m;
    } while (m != 0);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    CacheSubscription localCacheSubscription = new CacheSubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe(localCacheSubscription);
    add(localCacheSubscription);
    if ((!this.once.get()) && (this.once.compareAndSet(false, true))) {
      this.source.subscribe(this);
    } else {
      replay(localCacheSubscription);
    }
  }
  
  static final class CacheSubscription<T>
    extends AtomicInteger
    implements Subscription
  {
    private static final long serialVersionUID = 6770240836423125754L;
    final Subscriber<? super T> downstream;
    long index;
    FlowableCache.Node<T> node;
    int offset;
    final FlowableCache<T> parent;
    final AtomicLong requested;
    
    CacheSubscription(Subscriber<? super T> paramSubscriber, FlowableCache<T> paramFlowableCache)
    {
      this.downstream = paramSubscriber;
      this.parent = paramFlowableCache;
      this.node = paramFlowableCache.head;
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (this.requested.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
        this.parent.remove(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.addCancel(this.requested, paramLong);
        this.parent.replay(this);
      }
    }
  }
  
  static final class Node<T>
  {
    volatile Node<T> next;
    final T[] values;
    
    Node(int paramInt)
    {
      this.values = ((Object[])new Object[paramInt]);
    }
  }
}
