package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

abstract class AbstractDirectTask
  extends AtomicReference<Future<?>>
  implements Disposable, SchedulerRunnableIntrospection
{
  protected static final FutureTask<Void> DISPOSED = new FutureTask(Functions.EMPTY_RUNNABLE, null);
  protected static final FutureTask<Void> FINISHED = new FutureTask(Functions.EMPTY_RUNNABLE, null);
  private static final long serialVersionUID = 1811839108042568751L;
  protected final Runnable runnable;
  protected Thread runner;
  
  AbstractDirectTask(Runnable paramRunnable)
  {
    this.runnable = paramRunnable;
  }
  
  public final void dispose()
  {
    Future localFuture = (Future)get();
    if (localFuture != FINISHED)
    {
      FutureTask localFutureTask = DISPOSED;
      if ((localFuture != localFutureTask) && (compareAndSet(localFuture, localFutureTask)) && (localFuture != null))
      {
        boolean bool;
        if (this.runner != Thread.currentThread()) {
          bool = true;
        } else {
          bool = false;
        }
        localFuture.cancel(bool);
      }
    }
  }
  
  public Runnable getWrappedRunnable()
  {
    return this.runnable;
  }
  
  public final boolean isDisposed()
  {
    Future localFuture = (Future)get();
    boolean bool;
    if ((localFuture != FINISHED) && (localFuture != DISPOSED)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public final void setFuture(Future<?> paramFuture)
  {
    Future localFuture;
    do
    {
      localFuture = (Future)get();
      if (localFuture == FINISHED) {
        break;
      }
      if (localFuture == DISPOSED)
      {
        boolean bool;
        if (this.runner != Thread.currentThread()) {
          bool = true;
        } else {
          bool = false;
        }
        paramFuture.cancel(bool);
        break;
      }
    } while (!compareAndSet(localFuture, paramFuture));
  }
}
