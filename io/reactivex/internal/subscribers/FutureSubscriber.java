package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FutureSubscriber<T>
  extends CountDownLatch
  implements FlowableSubscriber<T>, Future<T>, Subscription
{
  Throwable error;
  final AtomicReference<Subscription> upstream = new AtomicReference();
  T value;
  
  public FutureSubscriber()
  {
    super(1);
  }
  
  public void cancel() {}
  
  public boolean cancel(boolean paramBoolean)
  {
    Subscription localSubscription;
    do
    {
      localSubscription = (Subscription)this.upstream.get();
      if ((localSubscription == this) || (localSubscription == SubscriptionHelper.CANCELLED)) {
        break;
      }
    } while (!this.upstream.compareAndSet(localSubscription, SubscriptionHelper.CANCELLED));
    if (localSubscription != null) {
      localSubscription.cancel();
    }
    countDown();
    return true;
    return false;
  }
  
  public T get()
    throws InterruptedException, ExecutionException
  {
    if (getCount() != 0L)
    {
      BlockingHelper.verifyNonBlocking();
      await();
    }
    if (!isCancelled())
    {
      Throwable localThrowable = this.error;
      if (localThrowable == null) {
        return this.value;
      }
      throw new ExecutionException(localThrowable);
    }
    throw new CancellationException();
  }
  
  public T get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    if (getCount() != 0L)
    {
      BlockingHelper.verifyNonBlocking();
      if (!await(paramLong, paramTimeUnit)) {
        throw new TimeoutException(ExceptionHelper.timeoutMessage(paramLong, paramTimeUnit));
      }
    }
    if (!isCancelled())
    {
      paramTimeUnit = this.error;
      if (paramTimeUnit == null) {
        return this.value;
      }
      throw new ExecutionException(paramTimeUnit);
    }
    throw new CancellationException();
  }
  
  public boolean isCancelled()
  {
    boolean bool;
    if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDone()
  {
    boolean bool;
    if (getCount() == 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    if (this.value == null)
    {
      onError(new NoSuchElementException("The source is empty"));
      return;
    }
    Subscription localSubscription;
    do
    {
      localSubscription = (Subscription)this.upstream.get();
      if ((localSubscription == this) || (localSubscription == SubscriptionHelper.CANCELLED)) {
        break;
      }
    } while (!this.upstream.compareAndSet(localSubscription, this));
    countDown();
  }
  
  public void onError(Throwable paramThrowable)
  {
    Subscription localSubscription;
    do
    {
      localSubscription = (Subscription)this.upstream.get();
      if ((localSubscription == this) || (localSubscription == SubscriptionHelper.CANCELLED)) {
        break;
      }
      this.error = paramThrowable;
    } while (!this.upstream.compareAndSet(localSubscription, this));
    countDown();
    return;
    RxJavaPlugins.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    if (this.value != null)
    {
      ((Subscription)this.upstream.get()).cancel();
      onError(new IndexOutOfBoundsException("More than one element received"));
      return;
    }
    this.value = paramT;
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    SubscriptionHelper.setOnce(this.upstream, paramSubscription, Long.MAX_VALUE);
  }
  
  public void request(long paramLong) {}
}
