package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundary<T, B>
  extends AbstractFlowableWithUpstream<T, Flowable<T>>
{
  final int capacityHint;
  final Publisher<B> other;
  
  public FlowableWindowBoundary(Flowable<T> paramFlowable, Publisher<B> paramPublisher, int paramInt)
  {
    super(paramFlowable);
    this.other = paramPublisher;
    this.capacityHint = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super Flowable<T>> paramSubscriber)
  {
    WindowBoundaryMainSubscriber localWindowBoundaryMainSubscriber = new WindowBoundaryMainSubscriber(paramSubscriber, this.capacityHint);
    paramSubscriber.onSubscribe(localWindowBoundaryMainSubscriber);
    localWindowBoundaryMainSubscriber.innerNext();
    this.other.subscribe(localWindowBoundaryMainSubscriber.boundarySubscriber);
    this.source.subscribe(localWindowBoundaryMainSubscriber);
  }
  
  static final class WindowBoundaryInnerSubscriber<T, B>
    extends DisposableSubscriber<B>
  {
    boolean done;
    final FlowableWindowBoundary.WindowBoundaryMainSubscriber<T, B> parent;
    
    WindowBoundaryInnerSubscriber(FlowableWindowBoundary.WindowBoundaryMainSubscriber<T, B> paramWindowBoundaryMainSubscriber)
    {
      this.parent = paramWindowBoundaryMainSubscriber;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.parent.innerComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      if (this.done) {
        return;
      }
      this.parent.innerNext();
    }
  }
  
  static final class WindowBoundaryMainSubscriber<T, B>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    static final Object NEXT_WINDOW = new Object();
    private static final long serialVersionUID = 2233020065421370272L;
    final FlowableWindowBoundary.WindowBoundaryInnerSubscriber<T, B> boundarySubscriber;
    final int capacityHint;
    volatile boolean done;
    final Subscriber<? super Flowable<T>> downstream;
    long emitted;
    final AtomicThrowable errors;
    final MpscLinkedQueue<Object> queue;
    final AtomicLong requested;
    final AtomicBoolean stopWindows;
    final AtomicReference<Subscription> upstream;
    UnicastProcessor<T> window;
    final AtomicInteger windows;
    
    WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.capacityHint = paramInt;
      this.boundarySubscriber = new FlowableWindowBoundary.WindowBoundaryInnerSubscriber(this);
      this.upstream = new AtomicReference();
      this.windows = new AtomicInteger(1);
      this.queue = new MpscLinkedQueue();
      this.errors = new AtomicThrowable();
      this.stopWindows = new AtomicBoolean();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (this.stopWindows.compareAndSet(false, true))
      {
        this.boundarySubscriber.dispose();
        if (this.windows.decrementAndGet() == 0) {
          SubscriptionHelper.cancel(this.upstream);
        }
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      MpscLinkedQueue localMpscLinkedQueue = this.queue;
      Object localObject1 = this.errors;
      long l = this.emitted;
      int i = 1;
      for (;;)
      {
        if (this.windows.get() == 0)
        {
          localMpscLinkedQueue.clear();
          this.window = null;
          return;
        }
        UnicastProcessor localUnicastProcessor = this.window;
        boolean bool = this.done;
        if ((bool) && (((AtomicThrowable)localObject1).get() != null))
        {
          localMpscLinkedQueue.clear();
          localObject1 = ((AtomicThrowable)localObject1).terminate();
          if (localUnicastProcessor != null)
          {
            this.window = null;
            localUnicastProcessor.onError((Throwable)localObject1);
          }
          localSubscriber.onError((Throwable)localObject1);
          return;
        }
        Object localObject2 = localMpscLinkedQueue.poll();
        int j;
        if (localObject2 == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0))
        {
          localObject1 = ((AtomicThrowable)localObject1).terminate();
          if (localObject1 == null)
          {
            if (localUnicastProcessor != null)
            {
              this.window = null;
              localUnicastProcessor.onComplete();
            }
            localSubscriber.onComplete();
          }
          else
          {
            if (localUnicastProcessor != null)
            {
              this.window = null;
              localUnicastProcessor.onError((Throwable)localObject1);
            }
            localSubscriber.onError((Throwable)localObject1);
          }
          return;
        }
        if (j != 0)
        {
          this.emitted = l;
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else if (localObject2 != NEXT_WINDOW)
        {
          localUnicastProcessor.onNext(localObject2);
        }
        else
        {
          if (localUnicastProcessor != null)
          {
            this.window = null;
            localUnicastProcessor.onComplete();
          }
          if (!this.stopWindows.get())
          {
            localUnicastProcessor = UnicastProcessor.create(this.capacityHint, this);
            this.window = localUnicastProcessor;
            this.windows.getAndIncrement();
            if (l != this.requested.get())
            {
              l += 1L;
              localSubscriber.onNext(localUnicastProcessor);
            }
            else
            {
              SubscriptionHelper.cancel(this.upstream);
              this.boundarySubscriber.dispose();
              ((AtomicThrowable)localObject1).addThrowable(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
              this.done = true;
            }
          }
        }
      }
    }
    
    void innerComplete()
    {
      SubscriptionHelper.cancel(this.upstream);
      this.done = true;
      drain();
    }
    
    void innerError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.upstream);
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerNext()
    {
      this.queue.offer(NEXT_WINDOW);
      drain();
    }
    
    public void onComplete()
    {
      this.boundarySubscriber.dispose();
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.boundarySubscriber.dispose();
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this.upstream, paramSubscription, Long.MAX_VALUE);
    }
    
    public void request(long paramLong)
    {
      BackpressureHelper.add(this.requested, paramLong);
    }
    
    public void run()
    {
      if (this.windows.decrementAndGet() == 0) {
        SubscriptionHelper.cancel(this.upstream);
      }
    }
  }
}
