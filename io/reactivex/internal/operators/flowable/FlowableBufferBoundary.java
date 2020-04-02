package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
  final Publisher<? extends Open> bufferOpen;
  final Callable<U> bufferSupplier;
  
  public FlowableBufferBoundary(Flowable<T> paramFlowable, Publisher<? extends Open> paramPublisher, Function<? super Open, ? extends Publisher<? extends Close>> paramFunction, Callable<U> paramCallable)
  {
    super(paramFlowable);
    this.bufferOpen = paramPublisher;
    this.bufferClose = paramFunction;
    this.bufferSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    BufferBoundarySubscriber localBufferBoundarySubscriber = new BufferBoundarySubscriber(paramSubscriber, this.bufferOpen, this.bufferClose, this.bufferSupplier);
    paramSubscriber.onSubscribe(localBufferBoundarySubscriber);
    this.source.subscribe(localBufferBoundarySubscriber);
  }
  
  static final class BufferBoundarySubscriber<T, C extends Collection<? super T>, Open, Close>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -8466418554264089604L;
    final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
    final Publisher<? extends Open> bufferOpen;
    final Callable<C> bufferSupplier;
    Map<Long, C> buffers;
    volatile boolean cancelled;
    volatile boolean done;
    final Subscriber<? super C> downstream;
    long emitted;
    final AtomicThrowable errors;
    long index;
    final SpscLinkedArrayQueue<C> queue;
    final AtomicLong requested;
    final CompositeDisposable subscribers;
    final AtomicReference<Subscription> upstream;
    
    BufferBoundarySubscriber(Subscriber<? super C> paramSubscriber, Publisher<? extends Open> paramPublisher, Function<? super Open, ? extends Publisher<? extends Close>> paramFunction, Callable<C> paramCallable)
    {
      this.downstream = paramSubscriber;
      this.bufferSupplier = paramCallable;
      this.bufferOpen = paramPublisher;
      this.bufferClose = paramFunction;
      this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
      this.subscribers = new CompositeDisposable();
      this.requested = new AtomicLong();
      this.upstream = new AtomicReference();
      this.buffers = new LinkedHashMap();
      this.errors = new AtomicThrowable();
    }
    
    void boundaryError(Disposable paramDisposable, Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.upstream);
      this.subscribers.delete(paramDisposable);
      onError(paramThrowable);
    }
    
    public void cancel()
    {
      if (SubscriptionHelper.cancel(this.upstream))
      {
        this.cancelled = true;
        this.subscribers.dispose();
        try
        {
          this.buffers = null;
          if (getAndIncrement() != 0) {
            this.queue.clear();
          }
        }
        finally {}
      }
    }
    
    void close(FlowableBufferBoundary.BufferCloseSubscriber<T, C> paramBufferCloseSubscriber, long paramLong)
    {
      this.subscribers.delete(paramBufferCloseSubscriber);
      int i;
      if (this.subscribers.size() == 0)
      {
        SubscriptionHelper.cancel(this.upstream);
        i = 1;
      }
      else
      {
        i = 0;
      }
      try
      {
        if (this.buffers == null) {
          return;
        }
        this.queue.offer(this.buffers.remove(Long.valueOf(paramLong)));
        if (i != 0) {
          this.done = true;
        }
        drain();
        return;
      }
      finally {}
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      long l1 = this.emitted;
      Subscriber localSubscriber = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      int i = 1;
      int j;
      do
      {
        long l2 = this.requested.get();
        boolean bool1;
        for (;;)
        {
          bool1 = l1 < l2;
          if (!bool1) {
            break;
          }
          if (this.cancelled)
          {
            localSpscLinkedArrayQueue.clear();
            return;
          }
          boolean bool2 = this.done;
          if ((bool2) && (this.errors.get() != null))
          {
            localSpscLinkedArrayQueue.clear();
            localSubscriber.onError(this.errors.terminate());
            return;
          }
          Collection localCollection = (Collection)localSpscLinkedArrayQueue.poll();
          if (localCollection == null) {
            j = 1;
          } else {
            j = 0;
          }
          if ((bool2) && (j != 0))
          {
            localSubscriber.onComplete();
            return;
          }
          if (j != 0) {
            break;
          }
          localSubscriber.onNext(localCollection);
          l1 += 1L;
        }
        if (!bool1)
        {
          if (this.cancelled)
          {
            localSpscLinkedArrayQueue.clear();
            return;
          }
          if (this.done)
          {
            if (this.errors.get() != null)
            {
              localSpscLinkedArrayQueue.clear();
              localSubscriber.onError(this.errors.terminate());
              return;
            }
            if (localSpscLinkedArrayQueue.isEmpty())
            {
              localSubscriber.onComplete();
              return;
            }
          }
        }
        this.emitted = l1;
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
      this.subscribers.dispose();
      try
      {
        Object localObject1 = this.buffers;
        if (localObject1 == null) {
          return;
        }
        Iterator localIterator = ((Map)localObject1).values().iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Collection)localIterator.next();
          this.queue.offer(localObject1);
        }
        this.buffers = null;
        this.done = true;
        drain();
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        this.subscribers.dispose();
        try
        {
          this.buffers = null;
          this.done = true;
          drain();
        }
        finally {}
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
        Object localObject = this.buffers;
        if (localObject == null) {
          return;
        }
        localObject = ((Map)localObject).values().iterator();
        while (((Iterator)localObject).hasNext()) {
          ((Collection)((Iterator)localObject).next()).add(paramT);
        }
        return;
      }
      finally {}
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this.upstream, paramSubscription))
      {
        BufferOpenSubscriber localBufferOpenSubscriber = new BufferOpenSubscriber(this);
        this.subscribers.add(localBufferOpenSubscriber);
        this.bufferOpen.subscribe(localBufferOpenSubscriber);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    void open(Open paramOpen)
    {
      try
      {
        Object localObject = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null Collection");
        paramOpen = (Publisher)ObjectHelper.requireNonNull(this.bufferClose.apply(paramOpen), "The bufferClose returned a null Publisher");
        long l = this.index;
        this.index = (1L + l);
        try
        {
          Map localMap = this.buffers;
          if (localMap == null) {
            return;
          }
          localMap.put(Long.valueOf(l), localObject);
          localObject = new FlowableBufferBoundary.BufferCloseSubscriber(this, l);
          this.subscribers.add((Disposable)localObject);
          paramOpen.subscribe((Subscriber)localObject);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramOpen);
        SubscriptionHelper.cancel(this.upstream);
      }
    }
    
    void openComplete(BufferOpenSubscriber<Open> paramBufferOpenSubscriber)
    {
      this.subscribers.delete(paramBufferOpenSubscriber);
      if (this.subscribers.size() == 0)
      {
        SubscriptionHelper.cancel(this.upstream);
        this.done = true;
        drain();
      }
    }
    
    public void request(long paramLong)
    {
      BackpressureHelper.add(this.requested, paramLong);
      drain();
    }
    
    static final class BufferOpenSubscriber<Open>
      extends AtomicReference<Subscription>
      implements FlowableSubscriber<Open>, Disposable
    {
      private static final long serialVersionUID = -8498650778633225126L;
      final FlowableBufferBoundary.BufferBoundarySubscriber<?, ?, Open, ?> parent;
      
      BufferOpenSubscriber(FlowableBufferBoundary.BufferBoundarySubscriber<?, ?, Open, ?> paramBufferBoundarySubscriber)
      {
        this.parent = paramBufferBoundarySubscriber;
      }
      
      public void dispose()
      {
        SubscriptionHelper.cancel(this);
      }
      
      public boolean isDisposed()
      {
        boolean bool;
        if (get() == SubscriptionHelper.CANCELLED) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void onComplete()
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.openComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.boundaryError(this, paramThrowable);
      }
      
      public void onNext(Open paramOpen)
      {
        this.parent.open(paramOpen);
      }
      
      public void onSubscribe(Subscription paramSubscription)
      {
        SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
      }
    }
  }
  
  static final class BufferCloseSubscriber<T, C extends Collection<? super T>>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<Object>, Disposable
  {
    private static final long serialVersionUID = -8498650778633225126L;
    final long index;
    final FlowableBufferBoundary.BufferBoundarySubscriber<T, C, ?, ?> parent;
    
    BufferCloseSubscriber(FlowableBufferBoundary.BufferBoundarySubscriber<T, C, ?, ?> paramBufferBoundarySubscriber, long paramLong)
    {
      this.parent = paramBufferBoundarySubscriber;
      this.index = paramLong;
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (get() != SubscriptionHelper.CANCELLED)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.close(this, this.index);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get() != SubscriptionHelper.CANCELLED)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        this.parent.boundaryError(this, paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(Object paramObject)
    {
      paramObject = (Subscription)get();
      if (paramObject != SubscriptionHelper.CANCELLED)
      {
        lazySet(SubscriptionHelper.CANCELLED);
        paramObject.cancel();
        this.parent.close(this, this.index);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
}
