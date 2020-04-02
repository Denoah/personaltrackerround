package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.EmptyComponent;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupBy<T, K, V>
  extends AbstractFlowableWithUpstream<T, GroupedFlowable<K, V>>
{
  final int bufferSize;
  final boolean delayError;
  final Function<? super T, ? extends K> keySelector;
  final Function<? super Consumer<Object>, ? extends Map<K, Object>> mapFactory;
  final Function<? super T, ? extends V> valueSelector;
  
  public FlowableGroupBy(Flowable<T> paramFlowable, Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, int paramInt, boolean paramBoolean, Function<? super Consumer<Object>, ? extends Map<K, Object>> paramFunction2)
  {
    super(paramFlowable);
    this.keySelector = paramFunction;
    this.valueSelector = paramFunction1;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
    this.mapFactory = paramFunction2;
  }
  
  protected void subscribeActual(Subscriber<? super GroupedFlowable<K, V>> paramSubscriber)
  {
    try
    {
      ConcurrentLinkedQueue localConcurrentLinkedQueue;
      if (this.mapFactory == null) {
        localConcurrentLinkedQueue = null;
      }
      for (Object localObject = new ConcurrentHashMap();; localObject = (Map)this.mapFactory.apply(localObject))
      {
        break;
        localConcurrentLinkedQueue = new java/util/concurrent/ConcurrentLinkedQueue;
        localConcurrentLinkedQueue.<init>();
        localObject = new io/reactivex/internal/operators/flowable/FlowableGroupBy$EvictionAction;
        ((EvictionAction)localObject).<init>(localConcurrentLinkedQueue);
      }
      paramSubscriber = new GroupBySubscriber(paramSubscriber, this.keySelector, this.valueSelector, this.bufferSize, this.delayError, (Map)localObject, localConcurrentLinkedQueue);
      this.source.subscribe(paramSubscriber);
      return;
    }
    catch (Exception localException)
    {
      Exceptions.throwIfFatal(localException);
      paramSubscriber.onSubscribe(EmptyComponent.INSTANCE);
      paramSubscriber.onError(localException);
    }
  }
  
  static final class EvictionAction<K, V>
    implements Consumer<FlowableGroupBy.GroupedUnicast<K, V>>
  {
    final Queue<FlowableGroupBy.GroupedUnicast<K, V>> evictedGroups;
    
    EvictionAction(Queue<FlowableGroupBy.GroupedUnicast<K, V>> paramQueue)
    {
      this.evictedGroups = paramQueue;
    }
    
    public void accept(FlowableGroupBy.GroupedUnicast<K, V> paramGroupedUnicast)
    {
      this.evictedGroups.offer(paramGroupedUnicast);
    }
  }
  
  public static final class GroupBySubscriber<T, K, V>
    extends BasicIntQueueSubscription<GroupedFlowable<K, V>>
    implements FlowableSubscriber<T>
  {
    static final Object NULL_KEY = new Object();
    private static final long serialVersionUID = -3688291656102519502L;
    final int bufferSize;
    final AtomicBoolean cancelled = new AtomicBoolean();
    final boolean delayError;
    boolean done;
    final Subscriber<? super GroupedFlowable<K, V>> downstream;
    Throwable error;
    final Queue<FlowableGroupBy.GroupedUnicast<K, V>> evictedGroups;
    volatile boolean finished;
    final AtomicInteger groupCount = new AtomicInteger(1);
    final Map<Object, FlowableGroupBy.GroupedUnicast<K, V>> groups;
    final Function<? super T, ? extends K> keySelector;
    boolean outputFused;
    final SpscLinkedArrayQueue<GroupedFlowable<K, V>> queue;
    final AtomicLong requested = new AtomicLong();
    Subscription upstream;
    final Function<? super T, ? extends V> valueSelector;
    
    public GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> paramSubscriber, Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, int paramInt, boolean paramBoolean, Map<Object, FlowableGroupBy.GroupedUnicast<K, V>> paramMap, Queue<FlowableGroupBy.GroupedUnicast<K, V>> paramQueue)
    {
      this.downstream = paramSubscriber;
      this.keySelector = paramFunction;
      this.valueSelector = paramFunction1;
      this.bufferSize = paramInt;
      this.delayError = paramBoolean;
      this.groups = paramMap;
      this.evictedGroups = paramQueue;
      this.queue = new SpscLinkedArrayQueue(paramInt);
    }
    
    private void completeEvictions()
    {
      if (this.evictedGroups != null)
      {
        for (int i = 0;; i++)
        {
          FlowableGroupBy.GroupedUnicast localGroupedUnicast = (FlowableGroupBy.GroupedUnicast)this.evictedGroups.poll();
          if (localGroupedUnicast == null) {
            break;
          }
          localGroupedUnicast.onComplete();
        }
        if (i != 0) {
          this.groupCount.addAndGet(-i);
        }
      }
    }
    
    public void cancel()
    {
      if (this.cancelled.compareAndSet(false, true))
      {
        completeEvictions();
        if (this.groupCount.decrementAndGet() == 0) {
          this.upstream.cancel();
        }
      }
    }
    
    public void cancel(K paramK)
    {
      if (paramK == null) {
        paramK = NULL_KEY;
      }
      this.groups.remove(paramK);
      if (this.groupCount.decrementAndGet() == 0)
      {
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, SpscLinkedArrayQueue<?> paramSpscLinkedArrayQueue)
    {
      if (this.cancelled.get())
      {
        paramSpscLinkedArrayQueue.clear();
        return true;
      }
      if (this.delayError)
      {
        if ((paramBoolean1) && (paramBoolean2))
        {
          paramSpscLinkedArrayQueue = this.error;
          if (paramSpscLinkedArrayQueue != null) {
            paramSubscriber.onError(paramSpscLinkedArrayQueue);
          } else {
            paramSubscriber.onComplete();
          }
          return true;
        }
      }
      else if (paramBoolean1)
      {
        Throwable localThrowable = this.error;
        if (localThrowable != null)
        {
          paramSpscLinkedArrayQueue.clear();
          paramSubscriber.onError(localThrowable);
          return true;
        }
        if (paramBoolean2)
        {
          paramSubscriber.onComplete();
          return true;
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.queue.clear();
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      if (this.outputFused) {
        drainFused();
      } else {
        drainNormal();
      }
    }
    
    void drainFused()
    {
      Object localObject = this.queue;
      Subscriber localSubscriber = this.downstream;
      int i = 1;
      int j;
      do
      {
        if (this.cancelled.get())
        {
          ((SpscLinkedArrayQueue)localObject).clear();
          return;
        }
        boolean bool = this.finished;
        if ((bool) && (!this.delayError))
        {
          Throwable localThrowable = this.error;
          if (localThrowable != null)
          {
            ((SpscLinkedArrayQueue)localObject).clear();
            localSubscriber.onError(localThrowable);
            return;
          }
        }
        localSubscriber.onNext(null);
        if (bool)
        {
          localObject = this.error;
          if (localObject != null) {
            localSubscriber.onError((Throwable)localObject);
          } else {
            localSubscriber.onComplete();
          }
          return;
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    void drainNormal()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      Subscriber localSubscriber = this.downstream;
      int i = 1;
      int j;
      do
      {
        long l1 = this.requested.get();
        boolean bool1;
        for (long l2 = 0L;; l2 += 1L)
        {
          bool1 = l2 < l1;
          if (!bool1) {
            break;
          }
          boolean bool2 = this.finished;
          GroupedFlowable localGroupedFlowable = (GroupedFlowable)localSpscLinkedArrayQueue.poll();
          boolean bool3;
          if (localGroupedFlowable == null) {
            bool3 = true;
          } else {
            bool3 = false;
          }
          if (checkTerminated(bool2, bool3, localSubscriber, localSpscLinkedArrayQueue)) {
            return;
          }
          if (bool3) {
            break;
          }
          localSubscriber.onNext(localGroupedFlowable);
        }
        if ((!bool1) && (checkTerminated(this.finished, localSpscLinkedArrayQueue.isEmpty(), localSubscriber, localSpscLinkedArrayQueue))) {
          return;
        }
        if (l2 != 0L)
        {
          if (l1 != Long.MAX_VALUE) {
            this.requested.addAndGet(-l2);
          }
          this.upstream.request(l2);
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public boolean isEmpty()
    {
      return this.queue.isEmpty();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        Object localObject = this.groups.values().iterator();
        while (((Iterator)localObject).hasNext()) {
          ((FlowableGroupBy.GroupedUnicast)((Iterator)localObject).next()).onComplete();
        }
        this.groups.clear();
        localObject = this.evictedGroups;
        if (localObject != null) {
          ((Queue)localObject).clear();
        }
        this.done = true;
        this.finished = true;
        drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      Object localObject = this.groups.values().iterator();
      while (((Iterator)localObject).hasNext()) {
        ((FlowableGroupBy.GroupedUnicast)((Iterator)localObject).next()).onError(paramThrowable);
      }
      this.groups.clear();
      localObject = this.evictedGroups;
      if (localObject != null) {
        ((Queue)localObject).clear();
      }
      this.error = paramThrowable;
      this.finished = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      try
      {
        Object localObject1 = this.keySelector.apply(paramT);
        int i = 0;
        Object localObject2;
        if (localObject1 != null) {
          localObject2 = localObject1;
        } else {
          localObject2 = NULL_KEY;
        }
        FlowableGroupBy.GroupedUnicast localGroupedUnicast1 = (FlowableGroupBy.GroupedUnicast)this.groups.get(localObject2);
        FlowableGroupBy.GroupedUnicast localGroupedUnicast2 = localGroupedUnicast1;
        if (localGroupedUnicast1 == null)
        {
          if (this.cancelled.get()) {
            return;
          }
          localGroupedUnicast2 = FlowableGroupBy.GroupedUnicast.createWith(localObject1, this.bufferSize, this, this.delayError);
          this.groups.put(localObject2, localGroupedUnicast2);
          this.groupCount.getAndIncrement();
          i = 1;
        }
        try
        {
          paramT = ObjectHelper.requireNonNull(this.valueSelector.apply(paramT), "The valueSelector returned null");
          localGroupedUnicast2.onNext(paramT);
          completeEvictions();
          if (i != 0)
          {
            localSpscLinkedArrayQueue.offer(localGroupedUnicast2);
            drain();
          }
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.bufferSize);
      }
    }
    
    public GroupedFlowable<K, V> poll()
    {
      return (GroupedFlowable)this.queue.poll();
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
  }
  
  static final class GroupedUnicast<K, T>
    extends GroupedFlowable<K, T>
  {
    final FlowableGroupBy.State<T, K> state;
    
    protected GroupedUnicast(K paramK, FlowableGroupBy.State<T, K> paramState)
    {
      super();
      this.state = paramState;
    }
    
    public static <T, K> GroupedUnicast<K, T> createWith(K paramK, int paramInt, FlowableGroupBy.GroupBySubscriber<?, K, T> paramGroupBySubscriber, boolean paramBoolean)
    {
      return new GroupedUnicast(paramK, new FlowableGroupBy.State(paramInt, paramGroupBySubscriber, paramK, paramBoolean));
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
    
    protected void subscribeActual(Subscriber<? super T> paramSubscriber)
    {
      this.state.subscribe(paramSubscriber);
    }
  }
  
  static final class State<T, K>
    extends BasicIntQueueSubscription<T>
    implements Publisher<T>
  {
    private static final long serialVersionUID = -3852313036005250360L;
    final AtomicReference<Subscriber<? super T>> actual = new AtomicReference();
    final AtomicBoolean cancelled = new AtomicBoolean();
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final K key;
    final AtomicBoolean once = new AtomicBoolean();
    boolean outputFused;
    final FlowableGroupBy.GroupBySubscriber<?, K, T> parent;
    int produced;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicLong requested = new AtomicLong();
    
    State(int paramInt, FlowableGroupBy.GroupBySubscriber<?, K, T> paramGroupBySubscriber, K paramK, boolean paramBoolean)
    {
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.parent = paramGroupBySubscriber;
      this.key = paramK;
      this.delayError = paramBoolean;
    }
    
    public void cancel()
    {
      if (this.cancelled.compareAndSet(false, true)) {
        this.parent.cancel(this.key);
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber, boolean paramBoolean3)
    {
      if (this.cancelled.get())
      {
        this.queue.clear();
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
            if (localThrowable != null) {
              paramSubscriber.onError(localThrowable);
            } else {
              paramSubscriber.onComplete();
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
            paramSubscriber.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            paramSubscriber.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.queue.clear();
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      if (this.outputFused) {
        drainFused();
      } else {
        drainNormal();
      }
    }
    
    void drainFused()
    {
      Object localObject = this.queue;
      Subscriber localSubscriber = (Subscriber)this.actual.get();
      int i = 1;
      for (;;)
      {
        if (localSubscriber != null)
        {
          if (this.cancelled.get())
          {
            ((SpscLinkedArrayQueue)localObject).clear();
            return;
          }
          boolean bool = this.done;
          if ((bool) && (!this.delayError))
          {
            Throwable localThrowable = this.error;
            if (localThrowable != null)
            {
              ((SpscLinkedArrayQueue)localObject).clear();
              localSubscriber.onError(localThrowable);
              return;
            }
          }
          localSubscriber.onNext(null);
          if (bool)
          {
            localObject = this.error;
            if (localObject != null) {
              localSubscriber.onError((Throwable)localObject);
            } else {
              localSubscriber.onComplete();
            }
            return;
          }
        }
        int j = addAndGet(-i);
        if (j == 0) {
          return;
        }
        i = j;
        if (localSubscriber == null)
        {
          localSubscriber = (Subscriber)this.actual.get();
          i = j;
        }
      }
    }
    
    void drainNormal()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      boolean bool1 = this.delayError;
      Subscriber localSubscriber = (Subscriber)this.actual.get();
      int i = 1;
      for (;;)
      {
        if (localSubscriber != null)
        {
          long l1 = this.requested.get();
          boolean bool2;
          for (long l2 = 0L;; l2 += 1L)
          {
            bool2 = l2 < l1;
            if (!bool2) {
              break;
            }
            boolean bool3 = this.done;
            Object localObject = localSpscLinkedArrayQueue.poll();
            boolean bool4;
            if (localObject == null) {
              bool4 = true;
            } else {
              bool4 = false;
            }
            if (checkTerminated(bool3, bool4, localSubscriber, bool1)) {
              return;
            }
            if (bool4) {
              break;
            }
            localSubscriber.onNext(localObject);
          }
          if ((!bool2) && (checkTerminated(this.done, localSpscLinkedArrayQueue.isEmpty(), localSubscriber, bool1))) {
            return;
          }
          if (l2 != 0L)
          {
            if (l1 != Long.MAX_VALUE) {
              this.requested.addAndGet(-l2);
            }
            this.parent.upstream.request(l2);
          }
        }
        int j = addAndGet(-i);
        if (j == 0) {
          return;
        }
        i = j;
        if (localSubscriber == null)
        {
          localSubscriber = (Subscriber)this.actual.get();
          i = j;
        }
      }
    }
    
    public boolean isEmpty()
    {
      return this.queue.isEmpty();
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
    
    public T poll()
    {
      Object localObject = this.queue.poll();
      if (localObject != null)
      {
        this.produced += 1;
        return localObject;
      }
      int i = this.produced;
      if (i != 0)
      {
        this.produced = 0;
        this.parent.upstream.request(i);
      }
      return null;
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
    
    public void subscribe(Subscriber<? super T> paramSubscriber)
    {
      if (this.once.compareAndSet(false, true))
      {
        paramSubscriber.onSubscribe(this);
        this.actual.lazySet(paramSubscriber);
        drain();
      }
      else
      {
        EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), paramSubscriber);
      }
    }
  }
}
