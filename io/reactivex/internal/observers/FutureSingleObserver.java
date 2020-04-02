package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class FutureSingleObserver<T>
  extends CountDownLatch
  implements SingleObserver<T>, Future<T>, Disposable
{
  Throwable error;
  final AtomicReference<Disposable> upstream = new AtomicReference();
  T value;
  
  public FutureSingleObserver()
  {
    super(1);
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    Disposable localDisposable;
    do
    {
      localDisposable = (Disposable)this.upstream.get();
      if ((localDisposable == this) || (localDisposable == DisposableHelper.DISPOSED)) {
        break;
      }
    } while (!this.upstream.compareAndSet(localDisposable, DisposableHelper.DISPOSED));
    if (localDisposable != null) {
      localDisposable.dispose();
    }
    countDown();
    return true;
    return false;
  }
  
  public void dispose() {}
  
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
    return DisposableHelper.isDisposed((Disposable)this.upstream.get());
  }
  
  public boolean isDisposed()
  {
    return isDone();
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
  
  public void onError(Throwable paramThrowable)
  {
    Disposable localDisposable;
    do
    {
      localDisposable = (Disposable)this.upstream.get();
      if (localDisposable == DisposableHelper.DISPOSED)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
    } while (!this.upstream.compareAndSet(localDisposable, this));
    countDown();
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this.upstream, paramDisposable);
  }
  
  public void onSuccess(T paramT)
  {
    Disposable localDisposable = (Disposable)this.upstream.get();
    if (localDisposable == DisposableHelper.DISPOSED) {
      return;
    }
    this.value = paramT;
    this.upstream.compareAndSet(localDisposable, this);
    countDown();
  }
}
