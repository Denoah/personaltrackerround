package androidx.camera.core.impl.utils.futures;

import android.util.Log;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

abstract class ImmediateFuture<V>
  implements ListenableFuture<V>
{
  private static final String TAG = "ImmediateFuture";
  
  ImmediateFuture() {}
  
  public static <V> ListenableFuture<V> nullFuture()
  {
    return ImmediateSuccessfulFuture.NULL_FUTURE;
  }
  
  public void addListener(Runnable paramRunnable, Executor paramExecutor)
  {
    Preconditions.checkNotNull(paramRunnable);
    Preconditions.checkNotNull(paramExecutor);
    try
    {
      paramExecutor.execute(paramRunnable);
    }
    catch (RuntimeException localRuntimeException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Experienced RuntimeException while attempting to notify ");
      localStringBuilder.append(paramRunnable);
      localStringBuilder.append(" on Executor ");
      localStringBuilder.append(paramExecutor);
      Log.e("ImmediateFuture", localStringBuilder.toString(), localRuntimeException);
    }
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    return false;
  }
  
  public abstract V get()
    throws ExecutionException;
  
  public V get(long paramLong, TimeUnit paramTimeUnit)
    throws ExecutionException
  {
    Preconditions.checkNotNull(paramTimeUnit);
    return get();
  }
  
  public boolean isCancelled()
  {
    return false;
  }
  
  public boolean isDone()
  {
    return true;
  }
  
  static class ImmediateFailedFuture<V>
    extends ImmediateFuture<V>
  {
    private final Throwable mCause;
    
    ImmediateFailedFuture(Throwable paramThrowable)
    {
      this.mCause = paramThrowable;
    }
    
    public V get()
      throws ExecutionException
    {
      throw new ExecutionException(this.mCause);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(super.toString());
      localStringBuilder.append("[status=FAILURE, cause=[");
      localStringBuilder.append(this.mCause);
      localStringBuilder.append("]]");
      return localStringBuilder.toString();
    }
  }
  
  static final class ImmediateFailedScheduledFuture<V>
    extends ImmediateFuture.ImmediateFailedFuture<V>
    implements ScheduledFuture<V>
  {
    ImmediateFailedScheduledFuture(Throwable paramThrowable)
    {
      super();
    }
    
    public int compareTo(Delayed paramDelayed)
    {
      return -1;
    }
    
    public long getDelay(TimeUnit paramTimeUnit)
    {
      return 0L;
    }
  }
  
  static final class ImmediateSuccessfulFuture<V>
    extends ImmediateFuture<V>
  {
    static final ImmediateFuture<Object> NULL_FUTURE = new ImmediateSuccessfulFuture(null);
    private final V mValue;
    
    ImmediateSuccessfulFuture(V paramV)
    {
      this.mValue = paramV;
    }
    
    public V get()
    {
      return this.mValue;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(super.toString());
      localStringBuilder.append("[status=SUCCESS, result=[");
      localStringBuilder.append(this.mValue);
      localStringBuilder.append("]]");
      return localStringBuilder.toString();
    }
  }
}
