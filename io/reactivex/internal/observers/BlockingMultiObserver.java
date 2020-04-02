package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class BlockingMultiObserver<T>
  extends CountDownLatch
  implements SingleObserver<T>, CompletableObserver, MaybeObserver<T>
{
  volatile boolean cancelled;
  Throwable error;
  Disposable upstream;
  T value;
  
  public BlockingMultiObserver()
  {
    super(1);
  }
  
  public boolean blockingAwait(long paramLong, TimeUnit paramTimeUnit)
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        if (!await(paramLong, paramTimeUnit))
        {
          dispose();
          return false;
        }
      }
      catch (InterruptedException paramTimeUnit)
      {
        dispose();
        throw ExceptionHelper.wrapOrThrow(paramTimeUnit);
      }
    }
    paramTimeUnit = this.error;
    if (paramTimeUnit == null) {
      return true;
    }
    throw ExceptionHelper.wrapOrThrow(paramTimeUnit);
  }
  
  public T blockingGet()
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        await();
      }
      catch (InterruptedException localInterruptedException)
      {
        dispose();
        throw ExceptionHelper.wrapOrThrow(localInterruptedException);
      }
    }
    Throwable localThrowable = this.error;
    if (localThrowable == null) {
      return this.value;
    }
    throw ExceptionHelper.wrapOrThrow(localThrowable);
  }
  
  public T blockingGet(T paramT)
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        await();
      }
      catch (InterruptedException paramT)
      {
        dispose();
        throw ExceptionHelper.wrapOrThrow(paramT);
      }
    }
    Object localObject = this.error;
    if (localObject == null)
    {
      localObject = this.value;
      if (localObject != null) {
        paramT = (TT)localObject;
      }
      return paramT;
    }
    throw ExceptionHelper.wrapOrThrow((Throwable)localObject);
  }
  
  public Throwable blockingGetError()
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        await();
      }
      catch (InterruptedException localInterruptedException)
      {
        dispose();
        return localInterruptedException;
      }
    }
    return this.error;
  }
  
  public Throwable blockingGetError(long paramLong, TimeUnit paramTimeUnit)
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        if (!await(paramLong, paramTimeUnit))
        {
          dispose();
          TimeoutException localTimeoutException = new java/util/concurrent/TimeoutException;
          localTimeoutException.<init>(ExceptionHelper.timeoutMessage(paramLong, paramTimeUnit));
          throw ExceptionHelper.wrapOrThrow(localTimeoutException);
        }
      }
      catch (InterruptedException paramTimeUnit)
      {
        dispose();
        throw ExceptionHelper.wrapOrThrow(paramTimeUnit);
      }
    }
    return this.error;
  }
  
  void dispose()
  {
    this.cancelled = true;
    Disposable localDisposable = this.upstream;
    if (localDisposable != null) {
      localDisposable.dispose();
    }
  }
  
  public void onComplete()
  {
    countDown();
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.error = paramThrowable;
    countDown();
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    this.upstream = paramDisposable;
    if (this.cancelled) {
      paramDisposable.dispose();
    }
  }
  
  public void onSuccess(T paramT)
  {
    this.value = paramT;
    countDown();
  }
}
