package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.observers.BaseTestConsumer;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class TestSubscriber<T>
  extends BaseTestConsumer<T, TestSubscriber<T>>
  implements FlowableSubscriber<T>, Subscription, Disposable
{
  private volatile boolean cancelled;
  private final Subscriber<? super T> downstream;
  private final AtomicLong missedRequested;
  private QueueSubscription<T> qs;
  private final AtomicReference<Subscription> upstream;
  
  public TestSubscriber()
  {
    this(EmptySubscriber.INSTANCE, Long.MAX_VALUE);
  }
  
  public TestSubscriber(long paramLong)
  {
    this(EmptySubscriber.INSTANCE, paramLong);
  }
  
  public TestSubscriber(Subscriber<? super T> paramSubscriber)
  {
    this(paramSubscriber, Long.MAX_VALUE);
  }
  
  public TestSubscriber(Subscriber<? super T> paramSubscriber, long paramLong)
  {
    if (paramLong >= 0L)
    {
      this.downstream = paramSubscriber;
      this.upstream = new AtomicReference();
      this.missedRequested = new AtomicLong(paramLong);
      return;
    }
    throw new IllegalArgumentException("Negative initial request not allowed");
  }
  
  public static <T> TestSubscriber<T> create()
  {
    return new TestSubscriber();
  }
  
  public static <T> TestSubscriber<T> create(long paramLong)
  {
    return new TestSubscriber(paramLong);
  }
  
  public static <T> TestSubscriber<T> create(Subscriber<? super T> paramSubscriber)
  {
    return new TestSubscriber(paramSubscriber);
  }
  
  static String fusionModeToString(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unknown(");
          localStringBuilder.append(paramInt);
          localStringBuilder.append(")");
          return localStringBuilder.toString();
        }
        return "ASYNC";
      }
      return "SYNC";
    }
    return "NONE";
  }
  
  final TestSubscriber<T> assertFuseable()
  {
    if (this.qs != null) {
      return this;
    }
    throw new AssertionError("Upstream is not fuseable.");
  }
  
  final TestSubscriber<T> assertFusionMode(int paramInt)
  {
    int i = this.establishedFusionMode;
    if (i != paramInt)
    {
      if (this.qs != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Fusion mode different. Expected: ");
        localStringBuilder.append(fusionModeToString(paramInt));
        localStringBuilder.append(", actual: ");
        localStringBuilder.append(fusionModeToString(i));
        throw new AssertionError(localStringBuilder.toString());
      }
      throw fail("Upstream is not fuseable");
    }
    return this;
  }
  
  final TestSubscriber<T> assertNotFuseable()
  {
    if (this.qs == null) {
      return this;
    }
    throw new AssertionError("Upstream is fuseable.");
  }
  
  public final TestSubscriber<T> assertNotSubscribed()
  {
    if (this.upstream.get() == null)
    {
      if (this.errors.isEmpty()) {
        return this;
      }
      throw fail("Not subscribed but errors found");
    }
    throw fail("Subscribed!");
  }
  
  public final TestSubscriber<T> assertOf(Consumer<? super TestSubscriber<T>> paramConsumer)
  {
    try
    {
      paramConsumer.accept(this);
      return this;
    }
    finally {}
  }
  
  public final TestSubscriber<T> assertSubscribed()
  {
    if (this.upstream.get() != null) {
      return this;
    }
    throw fail("Not subscribed!");
  }
  
  public final void cancel()
  {
    if (!this.cancelled)
    {
      this.cancelled = true;
      SubscriptionHelper.cancel(this.upstream);
    }
  }
  
  public final void dispose()
  {
    cancel();
  }
  
  public final boolean hasSubscription()
  {
    boolean bool;
    if (this.upstream.get() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public final boolean isDisposed()
  {
    return this.cancelled;
  }
  
  public void onComplete()
  {
    if (!this.checkSubscriptionOnce)
    {
      this.checkSubscriptionOnce = true;
      if (this.upstream.get() == null) {
        this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
      }
    }
    try
    {
      this.lastThread = Thread.currentThread();
      this.completions += 1L;
      this.downstream.onComplete();
      return;
    }
    finally
    {
      this.done.countDown();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (!this.checkSubscriptionOnce)
    {
      this.checkSubscriptionOnce = true;
      if (this.upstream.get() == null) {
        this.errors.add(new NullPointerException("onSubscribe not called in proper order"));
      }
    }
    try
    {
      this.lastThread = Thread.currentThread();
      this.errors.add(paramThrowable);
      if (paramThrowable == null)
      {
        List localList = this.errors;
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>("onError received a null Throwable");
        localList.add(localIllegalStateException);
      }
      this.downstream.onError(paramThrowable);
      return;
    }
    finally
    {
      this.done.countDown();
    }
  }
  
  public void onNext(T paramT)
  {
    if (!this.checkSubscriptionOnce)
    {
      this.checkSubscriptionOnce = true;
      if (this.upstream.get() == null) {
        this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
      }
    }
    this.lastThread = Thread.currentThread();
    if (this.establishedFusionMode == 2) {
      try
      {
        for (;;)
        {
          paramT = this.qs.poll();
          if (paramT == null) {
            break;
          }
          this.values.add(paramT);
        }
        return;
      }
      finally
      {
        this.errors.add(paramT);
        this.qs.cancel();
      }
    }
    this.values.add(paramT);
    if (paramT == null) {
      this.errors.add(new NullPointerException("onNext received a null value"));
    }
    this.downstream.onNext(paramT);
  }
  
  protected void onStart() {}
  
  public void onSubscribe(Subscription paramSubscription)
  {
    this.lastThread = Thread.currentThread();
    if (paramSubscription == null)
    {
      this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
      return;
    }
    Object localObject;
    if (!this.upstream.compareAndSet(null, paramSubscription))
    {
      paramSubscription.cancel();
      if (this.upstream.get() != SubscriptionHelper.CANCELLED)
      {
        localObject = this.errors;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onSubscribe received multiple subscriptions: ");
        localStringBuilder.append(paramSubscription);
        ((List)localObject).add(new IllegalStateException(localStringBuilder.toString()));
      }
      return;
    }
    if ((this.initialFusionMode != 0) && ((paramSubscription instanceof QueueSubscription)))
    {
      localObject = (QueueSubscription)paramSubscription;
      this.qs = ((QueueSubscription)localObject);
      int i = ((QueueSubscription)localObject).requestFusion(this.initialFusionMode);
      this.establishedFusionMode = i;
      if (i == 1)
      {
        this.checkSubscriptionOnce = true;
        this.lastThread = Thread.currentThread();
        try
        {
          for (;;)
          {
            paramSubscription = this.qs.poll();
            if (paramSubscription == null) {
              break;
            }
            this.values.add(paramSubscription);
          }
          this.completions += 1L;
        }
        finally
        {
          this.errors.add(paramSubscription);
        }
        return;
      }
    }
    this.downstream.onSubscribe(paramSubscription);
    long l = this.missedRequested.getAndSet(0L);
    if (l != 0L) {
      paramSubscription.request(l);
    }
    onStart();
  }
  
  public final void request(long paramLong)
  {
    SubscriptionHelper.deferredRequest(this.upstream, this.missedRequested, paramLong);
  }
  
  public final TestSubscriber<T> requestMore(long paramLong)
  {
    request(paramLong);
    return this;
  }
  
  final TestSubscriber<T> setInitialFusionMode(int paramInt)
  {
    this.initialFusionMode = paramInt;
    return this;
  }
  
  static enum EmptySubscriber
    implements FlowableSubscriber<Object>
  {
    static
    {
      EmptySubscriber localEmptySubscriber = new EmptySubscriber("INSTANCE", 0);
      INSTANCE = localEmptySubscriber;
      $VALUES = new EmptySubscriber[] { localEmptySubscriber };
    }
    
    private EmptySubscriber() {}
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable) {}
    
    public void onNext(Object paramObject) {}
    
    public void onSubscribe(Subscription paramSubscription) {}
  }
}
