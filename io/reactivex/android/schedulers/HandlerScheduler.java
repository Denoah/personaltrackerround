package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

final class HandlerScheduler
  extends Scheduler
{
  private final boolean async;
  private final Handler handler;
  
  HandlerScheduler(Handler paramHandler, boolean paramBoolean)
  {
    this.handler = paramHandler;
    this.async = paramBoolean;
  }
  
  public Scheduler.Worker createWorker()
  {
    return new HandlerWorker(this.handler, this.async);
  }
  
  public Disposable scheduleDirect(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramRunnable != null)
    {
      if (paramTimeUnit != null)
      {
        paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
        paramRunnable = new ScheduledRunnable(this.handler, paramRunnable);
        Message localMessage = Message.obtain(this.handler, paramRunnable);
        if (this.async) {
          localMessage.setAsynchronous(true);
        }
        this.handler.sendMessageDelayed(localMessage, paramTimeUnit.toMillis(paramLong));
        return paramRunnable;
      }
      throw new NullPointerException("unit == null");
    }
    throw new NullPointerException("run == null");
  }
  
  private static final class HandlerWorker
    extends Scheduler.Worker
  {
    private final boolean async;
    private volatile boolean disposed;
    private final Handler handler;
    
    HandlerWorker(Handler paramHandler, boolean paramBoolean)
    {
      this.handler = paramHandler;
      this.async = paramBoolean;
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.handler.removeCallbacksAndMessages(this);
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      if (paramRunnable != null)
      {
        if (paramTimeUnit != null)
        {
          if (this.disposed) {
            return Disposables.disposed();
          }
          paramRunnable = RxJavaPlugins.onSchedule(paramRunnable);
          HandlerScheduler.ScheduledRunnable localScheduledRunnable = new HandlerScheduler.ScheduledRunnable(this.handler, paramRunnable);
          paramRunnable = Message.obtain(this.handler, localScheduledRunnable);
          paramRunnable.obj = this;
          if (this.async) {
            paramRunnable.setAsynchronous(true);
          }
          this.handler.sendMessageDelayed(paramRunnable, paramTimeUnit.toMillis(paramLong));
          if (this.disposed)
          {
            this.handler.removeCallbacks(localScheduledRunnable);
            return Disposables.disposed();
          }
          return localScheduledRunnable;
        }
        throw new NullPointerException("unit == null");
      }
      throw new NullPointerException("run == null");
    }
  }
  
  private static final class ScheduledRunnable
    implements Runnable, Disposable
  {
    private final Runnable delegate;
    private volatile boolean disposed;
    private final Handler handler;
    
    ScheduledRunnable(Handler paramHandler, Runnable paramRunnable)
    {
      this.handler = paramHandler;
      this.delegate = paramRunnable;
    }
    
    public void dispose()
    {
      this.handler.removeCallbacks(this);
      this.disposed = true;
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 26	io/reactivex/android/schedulers/HandlerScheduler$ScheduledRunnable:delegate	Ljava/lang/Runnable;
      //   4: invokeinterface 41 1 0
      //   9: goto +8 -> 17
      //   12: astore_1
      //   13: aload_1
      //   14: invokestatic 47	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   17: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	18	0	this	ScheduledRunnable
      //   12	2	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	9	12	finally
    }
  }
}
