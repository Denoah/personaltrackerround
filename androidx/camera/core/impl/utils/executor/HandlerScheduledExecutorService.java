package androidx.camera.core.impl.utils.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

final class HandlerScheduledExecutorService
  extends AbstractExecutorService
  implements ScheduledExecutorService
{
  private static ThreadLocal<ScheduledExecutorService> sThreadLocalInstance = new ThreadLocal()
  {
    public ScheduledExecutorService initialValue()
    {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        return CameraXExecutors.mainThreadExecutor();
      }
      if (Looper.myLooper() != null) {
        return new HandlerScheduledExecutorService(new Handler(Looper.myLooper()));
      }
      return null;
    }
  };
  private final Handler mHandler;
  
  HandlerScheduledExecutorService(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }
  
  private RejectedExecutionException createPostFailedException()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mHandler);
    localStringBuilder.append(" is shutting down");
    return new RejectedExecutionException(localStringBuilder.toString());
  }
  
  static ScheduledExecutorService currentThreadExecutor()
  {
    ScheduledExecutorService localScheduledExecutorService = (ScheduledExecutorService)sThreadLocalInstance.get();
    Object localObject = localScheduledExecutorService;
    if (localScheduledExecutorService == null)
    {
      localObject = Looper.myLooper();
      if (localObject != null)
      {
        localObject = new HandlerScheduledExecutorService(new Handler((Looper)localObject));
        sThreadLocalInstance.set(localObject);
      }
      else
      {
        throw new IllegalStateException("Current thread has no looper!");
      }
    }
    return localObject;
  }
  
  public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
  {
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append(HandlerScheduledExecutorService.class.getSimpleName());
    paramTimeUnit.append(" cannot be shut down. Use Looper.quitSafely().");
    throw new UnsupportedOperationException(paramTimeUnit.toString());
  }
  
  public void execute(Runnable paramRunnable)
  {
    if (this.mHandler.post(paramRunnable)) {
      return;
    }
    throw createPostFailedException();
  }
  
  public boolean isShutdown()
  {
    return false;
  }
  
  public boolean isTerminated()
  {
    return false;
  }
  
  public ScheduledFuture<?> schedule(final Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    schedule(new Callable()
    {
      public Void call()
      {
        paramRunnable.run();
        return null;
      }
    }, paramLong, paramTimeUnit);
  }
  
  public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
  {
    paramLong = SystemClock.uptimeMillis() + TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit);
    paramCallable = new HandlerScheduledFuture(this.mHandler, paramLong, paramCallable);
    if (this.mHandler.postAtTime(paramCallable, paramLong)) {
      return paramCallable;
    }
    return Futures.immediateFailedScheduledFuture(createPostFailedException());
  }
  
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    paramRunnable = new StringBuilder();
    paramRunnable.append(HandlerScheduledExecutorService.class.getSimpleName());
    paramRunnable.append(" does not yet support fixed-rate scheduling.");
    throw new UnsupportedOperationException(paramRunnable.toString());
  }
  
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    paramRunnable = new StringBuilder();
    paramRunnable.append(HandlerScheduledExecutorService.class.getSimpleName());
    paramRunnable.append(" does not yet support fixed-delay scheduling.");
    throw new UnsupportedOperationException(paramRunnable.toString());
  }
  
  public void shutdown()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(HandlerScheduledExecutorService.class.getSimpleName());
    localStringBuilder.append(" cannot be shut down. Use Looper.quitSafely().");
    throw new UnsupportedOperationException(localStringBuilder.toString());
  }
  
  public List<Runnable> shutdownNow()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(HandlerScheduledExecutorService.class.getSimpleName());
    localStringBuilder.append(" cannot be shut down. Use Looper.quitSafely().");
    throw new UnsupportedOperationException(localStringBuilder.toString());
  }
  
  private static class HandlerScheduledFuture<V>
    implements RunnableScheduledFuture<V>
  {
    final AtomicReference<CallbackToFutureAdapter.Completer<V>> mCompleter = new AtomicReference(null);
    private final ListenableFuture<V> mDelegate;
    private final long mRunAtMillis;
    private final Callable<V> mTask;
    
    HandlerScheduledFuture(final Handler paramHandler, long paramLong, final Callable<V> paramCallable)
    {
      this.mRunAtMillis = paramLong;
      this.mTask = paramCallable;
      this.mDelegate = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver()
      {
        public Object attachCompleter(CallbackToFutureAdapter.Completer<V> paramAnonymousCompleter)
          throws RejectedExecutionException
        {
          paramAnonymousCompleter.addCancellationListener(new Runnable()
          {
            public void run()
            {
              if (HandlerScheduledExecutorService.HandlerScheduledFuture.this.mCompleter.getAndSet(null) != null) {
                HandlerScheduledExecutorService.HandlerScheduledFuture.1.this.val$handler.removeCallbacks(HandlerScheduledExecutorService.HandlerScheduledFuture.this);
              }
            }
          }, CameraXExecutors.directExecutor());
          HandlerScheduledExecutorService.HandlerScheduledFuture.this.mCompleter.set(paramAnonymousCompleter);
          paramAnonymousCompleter = new StringBuilder();
          paramAnonymousCompleter.append("HandlerScheduledFuture-");
          paramAnonymousCompleter.append(paramCallable.toString());
          return paramAnonymousCompleter.toString();
        }
      });
    }
    
    public boolean cancel(boolean paramBoolean)
    {
      return this.mDelegate.cancel(paramBoolean);
    }
    
    public int compareTo(Delayed paramDelayed)
    {
      return Long.compare(getDelay(TimeUnit.MILLISECONDS), paramDelayed.getDelay(TimeUnit.MILLISECONDS));
    }
    
    public V get()
      throws ExecutionException, InterruptedException
    {
      return this.mDelegate.get();
    }
    
    public V get(long paramLong, TimeUnit paramTimeUnit)
      throws ExecutionException, InterruptedException, TimeoutException
    {
      return this.mDelegate.get(paramLong, paramTimeUnit);
    }
    
    public long getDelay(TimeUnit paramTimeUnit)
    {
      return paramTimeUnit.convert(this.mRunAtMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
    
    public boolean isCancelled()
    {
      return this.mDelegate.isCancelled();
    }
    
    public boolean isDone()
    {
      return this.mDelegate.isDone();
    }
    
    public boolean isPeriodic()
    {
      return false;
    }
    
    public void run()
    {
      CallbackToFutureAdapter.Completer localCompleter = (CallbackToFutureAdapter.Completer)this.mCompleter.getAndSet(null);
      if (localCompleter != null) {
        try
        {
          localCompleter.set(this.mTask.call());
        }
        catch (Exception localException)
        {
          localCompleter.setException(localException);
        }
      }
    }
  }
}
