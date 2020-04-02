package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

final class InstantPeriodicTask
  implements Callable<Void>, Disposable
{
  static final FutureTask<Void> CANCELLED = new FutureTask(Functions.EMPTY_RUNNABLE, null);
  final ExecutorService executor;
  final AtomicReference<Future<?>> first;
  final AtomicReference<Future<?>> rest;
  Thread runner;
  final Runnable task;
  
  InstantPeriodicTask(Runnable paramRunnable, ExecutorService paramExecutorService)
  {
    this.task = paramRunnable;
    this.first = new AtomicReference();
    this.rest = new AtomicReference();
    this.executor = paramExecutorService;
  }
  
  /* Error */
  public Void call()
    throws java.lang.Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 66	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   4: putfield 68	io/reactivex/internal/schedulers/InstantPeriodicTask:runner	Ljava/lang/Thread;
    //   7: aload_0
    //   8: getfield 43	io/reactivex/internal/schedulers/InstantPeriodicTask:task	Ljava/lang/Runnable;
    //   11: invokeinterface 73 1 0
    //   16: aload_0
    //   17: aload_0
    //   18: getfield 52	io/reactivex/internal/schedulers/InstantPeriodicTask:executor	Ljava/util/concurrent/ExecutorService;
    //   21: aload_0
    //   22: invokeinterface 79 2 0
    //   27: invokevirtual 83	io/reactivex/internal/schedulers/InstantPeriodicTask:setRest	(Ljava/util/concurrent/Future;)V
    //   30: aload_0
    //   31: aconst_null
    //   32: putfield 68	io/reactivex/internal/schedulers/InstantPeriodicTask:runner	Ljava/lang/Thread;
    //   35: goto +13 -> 48
    //   38: astore_1
    //   39: aload_0
    //   40: aconst_null
    //   41: putfield 68	io/reactivex/internal/schedulers/InstantPeriodicTask:runner	Ljava/lang/Thread;
    //   44: aload_1
    //   45: invokestatic 89	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   48: aconst_null
    //   49: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	50	0	this	InstantPeriodicTask
    //   38	7	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   7	35	38	finally
  }
  
  public void dispose()
  {
    Future localFuture = (Future)this.first.getAndSet(CANCELLED);
    boolean bool1 = true;
    boolean bool2;
    if ((localFuture != null) && (localFuture != CANCELLED))
    {
      if (this.runner != Thread.currentThread()) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      localFuture.cancel(bool2);
    }
    localFuture = (Future)this.rest.getAndSet(CANCELLED);
    if ((localFuture != null) && (localFuture != CANCELLED))
    {
      if (this.runner != Thread.currentThread()) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }
      localFuture.cancel(bool2);
    }
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (this.first.get() == CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void setFirst(Future<?> paramFuture)
  {
    Future localFuture;
    do
    {
      localFuture = (Future)this.first.get();
      if (localFuture == CANCELLED)
      {
        boolean bool;
        if (this.runner != Thread.currentThread()) {
          bool = true;
        } else {
          bool = false;
        }
        paramFuture.cancel(bool);
        return;
      }
    } while (!this.first.compareAndSet(localFuture, paramFuture));
  }
  
  void setRest(Future<?> paramFuture)
  {
    Future localFuture;
    do
    {
      localFuture = (Future)this.rest.get();
      if (localFuture == CANCELLED)
      {
        boolean bool;
        if (this.runner != Thread.currentThread()) {
          bool = true;
        } else {
          bool = false;
        }
        paramFuture.cancel(bool);
        return;
      }
    } while (!this.rest.compareAndSet(localFuture, paramFuture));
  }
}
