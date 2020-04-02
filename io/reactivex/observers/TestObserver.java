package io.reactivex.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class TestObserver<T>
  extends BaseTestConsumer<T, TestObserver<T>>
  implements Observer<T>, Disposable, MaybeObserver<T>, SingleObserver<T>, CompletableObserver
{
  private final Observer<? super T> downstream;
  private QueueDisposable<T> qd;
  private final AtomicReference<Disposable> upstream = new AtomicReference();
  
  public TestObserver()
  {
    this(EmptyObserver.INSTANCE);
  }
  
  public TestObserver(Observer<? super T> paramObserver)
  {
    this.downstream = paramObserver;
  }
  
  public static <T> TestObserver<T> create()
  {
    return new TestObserver();
  }
  
  public static <T> TestObserver<T> create(Observer<? super T> paramObserver)
  {
    return new TestObserver(paramObserver);
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
  
  final TestObserver<T> assertFuseable()
  {
    if (this.qd != null) {
      return this;
    }
    throw new AssertionError("Upstream is not fuseable.");
  }
  
  final TestObserver<T> assertFusionMode(int paramInt)
  {
    int i = this.establishedFusionMode;
    if (i != paramInt)
    {
      if (this.qd != null)
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
  
  final TestObserver<T> assertNotFuseable()
  {
    if (this.qd == null) {
      return this;
    }
    throw new AssertionError("Upstream is fuseable.");
  }
  
  public final TestObserver<T> assertNotSubscribed()
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
  
  public final TestObserver<T> assertOf(Consumer<? super TestObserver<T>> paramConsumer)
  {
    try
    {
      paramConsumer.accept(this);
      return this;
    }
    finally {}
  }
  
  public final TestObserver<T> assertSubscribed()
  {
    if (this.upstream.get() != null) {
      return this;
    }
    throw fail("Not subscribed!");
  }
  
  public final void cancel()
  {
    dispose();
  }
  
  public final void dispose()
  {
    DisposableHelper.dispose(this.upstream);
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
    return isDisposed();
  }
  
  public final boolean isDisposed()
  {
    return DisposableHelper.isDisposed((Disposable)this.upstream.get());
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
        this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
      }
    }
    try
    {
      this.lastThread = Thread.currentThread();
      if (paramThrowable == null)
      {
        List localList = this.errors;
        NullPointerException localNullPointerException = new java/lang/NullPointerException;
        localNullPointerException.<init>("onError received a null Throwable");
        localList.add(localNullPointerException);
      }
      else
      {
        this.errors.add(paramThrowable);
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
          paramT = this.qd.poll();
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
        this.qd.dispose();
      }
    }
    this.values.add(paramT);
    if (paramT == null) {
      this.errors.add(new NullPointerException("onNext received a null value"));
    }
    this.downstream.onNext(paramT);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    this.lastThread = Thread.currentThread();
    if (paramDisposable == null)
    {
      this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
      return;
    }
    Object localObject;
    if (!this.upstream.compareAndSet(null, paramDisposable))
    {
      paramDisposable.dispose();
      if (this.upstream.get() != DisposableHelper.DISPOSED)
      {
        localObject = this.errors;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onSubscribe received multiple subscriptions: ");
        localStringBuilder.append(paramDisposable);
        ((List)localObject).add(new IllegalStateException(localStringBuilder.toString()));
      }
      return;
    }
    if ((this.initialFusionMode != 0) && ((paramDisposable instanceof QueueDisposable)))
    {
      localObject = (QueueDisposable)paramDisposable;
      this.qd = ((QueueDisposable)localObject);
      int i = ((QueueDisposable)localObject).requestFusion(this.initialFusionMode);
      this.establishedFusionMode = i;
      if (i == 1)
      {
        this.checkSubscriptionOnce = true;
        this.lastThread = Thread.currentThread();
        try
        {
          for (;;)
          {
            paramDisposable = this.qd.poll();
            if (paramDisposable == null) {
              break;
            }
            this.values.add(paramDisposable);
          }
          this.completions += 1L;
          this.upstream.lazySet(DisposableHelper.DISPOSED);
        }
        finally
        {
          this.errors.add(paramDisposable);
        }
        return;
      }
    }
    this.downstream.onSubscribe(paramDisposable);
  }
  
  public void onSuccess(T paramT)
  {
    onNext(paramT);
    onComplete();
  }
  
  final TestObserver<T> setInitialFusionMode(int paramInt)
  {
    this.initialFusionMode = paramInt;
    return this;
  }
  
  static enum EmptyObserver
    implements Observer<Object>
  {
    static
    {
      EmptyObserver localEmptyObserver = new EmptyObserver("INSTANCE", 0);
      INSTANCE = localEmptyObserver;
      $VALUES = new EmptyObserver[] { localEmptyObserver };
    }
    
    private EmptyObserver() {}
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable) {}
    
    public void onNext(Object paramObject) {}
    
    public void onSubscribe(Disposable paramDisposable) {}
  }
}
