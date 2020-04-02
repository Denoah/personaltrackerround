package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
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

public final class FutureObserver<T>
  extends CountDownLatch
  implements Observer<T>, Future<T>, Disposable
{
  Throwable error;
  final AtomicReference<Disposable> upstream = new AtomicReference();
  T value;
  
  public FutureObserver()
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
  
  public void onComplete()
  {
    if (this.value == null)
    {
      onError(new NoSuchElementException("The source is empty"));
      return;
    }
    Disposable localDisposable;
    do
    {
      localDisposable = (Disposable)this.upstream.get();
      if ((localDisposable == this) || (localDisposable == DisposableHelper.DISPOSED)) {
        break;
      }
    } while (!this.upstream.compareAndSet(localDisposable, this));
    countDown();
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.error == null)
    {
      this.error = paramThrowable;
      Disposable localDisposable;
      do
      {
        localDisposable = (Disposable)this.upstream.get();
        if ((localDisposable == this) || (localDisposable == DisposableHelper.DISPOSED)) {
          break;
        }
      } while (!this.upstream.compareAndSet(localDisposable, this));
      countDown();
      return;
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    RxJavaPlugins.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    if (this.value != null)
    {
      ((Disposable)this.upstream.get()).dispose();
      onError(new IndexOutOfBoundsException("More than one element received"));
      return;
    }
    this.value = paramT;
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    DisposableHelper.setOnce(this.upstream, paramDisposable);
  }
}
