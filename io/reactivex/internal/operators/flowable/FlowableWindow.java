package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindow<T>
  extends AbstractFlowableWithUpstream<T, Flowable<T>>
{
  final int bufferSize;
  final long size;
  final long skip;
  
  public FlowableWindow(Flowable<T> paramFlowable, long paramLong1, long paramLong2, int paramInt)
  {
    super(paramFlowable);
    this.size = paramLong1;
    this.skip = paramLong2;
    this.bufferSize = paramInt;
  }
  
  public void subscribeActual(Subscriber<? super Flowable<T>> paramSubscriber)
  {
    long l1 = this.skip;
    long l2 = this.size;
    if (l1 == l2) {
      this.source.subscribe(new WindowExactSubscriber(paramSubscriber, this.size, this.bufferSize));
    } else if (l1 > l2) {
      this.source.subscribe(new WindowSkipSubscriber(paramSubscriber, this.size, this.skip, this.bufferSize));
    } else {
      this.source.subscribe(new WindowOverlapSubscriber(paramSubscriber, this.size, this.skip, this.bufferSize));
    }
  }
  
  static final class WindowExactSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = -2365647875069161133L;
    final int bufferSize;
    final Subscriber<? super Flowable<T>> downstream;
    long index;
    final AtomicBoolean once;
    final long size;
    Subscription upstream;
    UnicastProcessor<T> window;
    
    WindowExactSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong, int paramInt)
    {
      super();
      this.downstream = paramSubscriber;
      this.size = paramLong;
      this.once = new AtomicBoolean();
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      if (this.once.compareAndSet(false, true)) {
        run();
      }
    }
    
    public void onComplete()
    {
      UnicastProcessor localUnicastProcessor = this.window;
      if (localUnicastProcessor != null)
      {
        this.window = null;
        localUnicastProcessor.onComplete();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      UnicastProcessor localUnicastProcessor = this.window;
      if (localUnicastProcessor != null)
      {
        this.window = null;
        localUnicastProcessor.onError(paramThrowable);
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      long l = this.index;
      UnicastProcessor localUnicastProcessor = this.window;
      if (l == 0L)
      {
        getAndIncrement();
        localUnicastProcessor = UnicastProcessor.create(this.bufferSize, this);
        this.window = localUnicastProcessor;
        this.downstream.onNext(localUnicastProcessor);
      }
      l += 1L;
      localUnicastProcessor.onNext(paramT);
      if (l == this.size)
      {
        this.index = 0L;
        this.window = null;
        localUnicastProcessor.onComplete();
      }
      else
      {
        this.index = l;
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        paramLong = BackpressureHelper.multiplyCap(this.size, paramLong);
        this.upstream.request(paramLong);
      }
    }
    
    public void run()
    {
      if (decrementAndGet() == 0) {
        this.upstream.cancel();
      }
    }
  }
  
  static final class WindowOverlapSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = 2428527070996323976L;
    final int bufferSize;
    volatile boolean cancelled;
    volatile boolean done;
    final Subscriber<? super Flowable<T>> downstream;
    Throwable error;
    final AtomicBoolean firstRequest;
    long index;
    final AtomicBoolean once;
    long produced;
    final SpscLinkedArrayQueue<UnicastProcessor<T>> queue;
    final AtomicLong requested;
    final long size;
    final long skip;
    Subscription upstream;
    final ArrayDeque<UnicastProcessor<T>> windows;
    final AtomicInteger wip;
    
    WindowOverlapSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong1, long paramLong2, int paramInt)
    {
      super();
      this.downstream = paramSubscriber;
      this.size = paramLong1;
      this.skip = paramLong2;
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.windows = new ArrayDeque();
      this.once = new AtomicBoolean();
      this.firstRequest = new AtomicBoolean();
      this.requested = new AtomicLong();
      this.wip = new AtomicInteger();
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      this.cancelled = true;
      if (this.once.compareAndSet(false, true)) {
        run();
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, SpscLinkedArrayQueue<?> paramSpscLinkedArrayQueue)
    {
      if (this.cancelled)
      {
        paramSpscLinkedArrayQueue.clear();
        return true;
      }
      if (paramBoolean1)
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
    
    void drain()
    {
      if (this.wip.getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
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
          boolean bool2 = this.done;
          UnicastProcessor localUnicastProcessor = (UnicastProcessor)localSpscLinkedArrayQueue.poll();
          boolean bool3;
          if (localUnicastProcessor == null) {
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
          localSubscriber.onNext(localUnicastProcessor);
        }
        if ((!bool1) && (checkTerminated(this.done, localSpscLinkedArrayQueue.isEmpty(), localSubscriber, localSpscLinkedArrayQueue))) {
          return;
        }
        if ((l2 != 0L) && (l1 != Long.MAX_VALUE)) {
          this.requested.addAndGet(-l2);
        }
        j = this.wip.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      Iterator localIterator = this.windows.iterator();
      while (localIterator.hasNext()) {
        ((Processor)localIterator.next()).onComplete();
      }
      this.windows.clear();
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      Iterator localIterator = this.windows.iterator();
      while (localIterator.hasNext()) {
        ((Processor)localIterator.next()).onError(paramThrowable);
      }
      this.windows.clear();
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l1 = this.index;
      if ((l1 == 0L) && (!this.cancelled))
      {
        getAndIncrement();
        localObject = UnicastProcessor.create(this.bufferSize, this);
        this.windows.offer(localObject);
        this.queue.offer(localObject);
        drain();
      }
      long l2 = l1 + 1L;
      Object localObject = this.windows.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Processor)((Iterator)localObject).next()).onNext(paramT);
      }
      l1 = this.produced + 1L;
      if (l1 == this.size)
      {
        this.produced = (l1 - this.skip);
        paramT = (Processor)this.windows.poll();
        if (paramT != null) {
          paramT.onComplete();
        }
      }
      else
      {
        this.produced = l1;
      }
      if (l2 == this.skip) {
        this.index = 0L;
      } else {
        this.index = l2;
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        if ((!this.firstRequest.get()) && (this.firstRequest.compareAndSet(false, true)))
        {
          paramLong = BackpressureHelper.multiplyCap(this.skip, paramLong - 1L);
          paramLong = BackpressureHelper.addCap(this.size, paramLong);
          this.upstream.request(paramLong);
        }
        else
        {
          paramLong = BackpressureHelper.multiplyCap(this.skip, paramLong);
          this.upstream.request(paramLong);
        }
        drain();
      }
    }
    
    public void run()
    {
      if (decrementAndGet() == 0) {
        this.upstream.cancel();
      }
    }
  }
  
  static final class WindowSkipSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = -8792836352386833856L;
    final int bufferSize;
    final Subscriber<? super Flowable<T>> downstream;
    final AtomicBoolean firstRequest;
    long index;
    final AtomicBoolean once;
    final long size;
    final long skip;
    Subscription upstream;
    UnicastProcessor<T> window;
    
    WindowSkipSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong1, long paramLong2, int paramInt)
    {
      super();
      this.downstream = paramSubscriber;
      this.size = paramLong1;
      this.skip = paramLong2;
      this.once = new AtomicBoolean();
      this.firstRequest = new AtomicBoolean();
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      if (this.once.compareAndSet(false, true)) {
        run();
      }
    }
    
    public void onComplete()
    {
      UnicastProcessor localUnicastProcessor = this.window;
      if (localUnicastProcessor != null)
      {
        this.window = null;
        localUnicastProcessor.onComplete();
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      UnicastProcessor localUnicastProcessor = this.window;
      if (localUnicastProcessor != null)
      {
        this.window = null;
        localUnicastProcessor.onError(paramThrowable);
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      long l = this.index;
      UnicastProcessor localUnicastProcessor = this.window;
      if (l == 0L)
      {
        getAndIncrement();
        localUnicastProcessor = UnicastProcessor.create(this.bufferSize, this);
        this.window = localUnicastProcessor;
        this.downstream.onNext(localUnicastProcessor);
      }
      l += 1L;
      if (localUnicastProcessor != null) {
        localUnicastProcessor.onNext(paramT);
      }
      if (l == this.size)
      {
        this.window = null;
        localUnicastProcessor.onComplete();
      }
      if (l == this.skip) {
        this.index = 0L;
      } else {
        this.index = l;
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        if ((!this.firstRequest.get()) && (this.firstRequest.compareAndSet(false, true)))
        {
          paramLong = BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.size, paramLong), BackpressureHelper.multiplyCap(this.skip - this.size, paramLong - 1L));
          this.upstream.request(paramLong);
        }
        else
        {
          paramLong = BackpressureHelper.multiplyCap(this.skip, paramLong);
          this.upstream.request(paramLong);
        }
      }
    }
    
    public void run()
    {
      if (decrementAndGet() == 0) {
        this.upstream.cancel();
      }
    }
  }
}
