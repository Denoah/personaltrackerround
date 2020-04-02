package androidx.camera.core.impl;

import android.util.Log;
import android.view.Surface;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DeferrableSurface
{
  private static final boolean DEBUG = false;
  protected static final String TAG = "DeferrableSurface";
  private static AtomicInteger sTotalCount = new AtomicInteger(0);
  private static AtomicInteger sUsedCount = new AtomicInteger(0);
  private boolean mClosed = false;
  private final Object mLock = new Object();
  private CallbackToFutureAdapter.Completer<Void> mTerminationCompleter;
  private final ListenableFuture<Void> mTerminationFuture = CallbackToFutureAdapter.getFuture(new _..Lambda.DeferrableSurface.4AwivYkWbX9ifTwpoNEQg994K4I(this));
  private int mUseCount = 0;
  
  public DeferrableSurface() {}
  
  private void printGlobalDebugCounts(String paramString, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("[total_surfaces=");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(", used_surfaces=");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append("](");
    localStringBuilder.append(this);
    localStringBuilder.append("}");
    Log.d("DeferrableSurface", localStringBuilder.toString());
  }
  
  public final void close()
  {
    synchronized (this.mLock)
    {
      if (!this.mClosed)
      {
        this.mClosed = true;
        if (this.mUseCount == 0)
        {
          localCompleter = this.mTerminationCompleter;
          this.mTerminationCompleter = null;
          break label41;
        }
      }
      CallbackToFutureAdapter.Completer localCompleter = null;
      label41:
      if (localCompleter != null) {
        localCompleter.set(null);
      }
      return;
    }
  }
  
  public void decrementUseCount()
  {
    synchronized (this.mLock)
    {
      if (this.mUseCount != 0)
      {
        int i = this.mUseCount - 1;
        this.mUseCount = i;
        if ((i == 0) && (this.mClosed))
        {
          localObject2 = this.mTerminationCompleter;
          this.mTerminationCompleter = null;
        }
        else
        {
          localObject2 = null;
        }
        if (localObject2 != null) {
          ((CallbackToFutureAdapter.Completer)localObject2).set(null);
        }
        return;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Decrementing use count occurs more times than incrementing");
      throw ((Throwable)localObject2);
    }
  }
  
  public final ListenableFuture<Surface> getSurface()
  {
    synchronized (this.mLock)
    {
      if (this.mClosed)
      {
        localObject2 = new androidx/camera/core/impl/DeferrableSurface$SurfaceClosedException;
        ((SurfaceClosedException)localObject2).<init>("DeferrableSurface already closed.", this);
        localObject2 = Futures.immediateFailedFuture((Throwable)localObject2);
        return localObject2;
      }
      Object localObject2 = provideSurface();
      return localObject2;
    }
  }
  
  public ListenableFuture<Void> getTerminationFuture()
  {
    return Futures.nonCancellationPropagating(this.mTerminationFuture);
  }
  
  public int getUseCount()
  {
    synchronized (this.mLock)
    {
      int i = this.mUseCount;
      return i;
    }
  }
  
  public void incrementUseCount()
    throws DeferrableSurface.SurfaceClosedException
  {
    synchronized (this.mLock)
    {
      if ((this.mUseCount == 0) && (this.mClosed))
      {
        SurfaceClosedException localSurfaceClosedException = new androidx/camera/core/impl/DeferrableSurface$SurfaceClosedException;
        localSurfaceClosedException.<init>("Cannot begin use on a closed surface.", this);
        throw localSurfaceClosedException;
      }
      this.mUseCount += 1;
      return;
    }
  }
  
  protected abstract ListenableFuture<Surface> provideSurface();
  
  public static final class SurfaceClosedException
    extends Exception
  {
    DeferrableSurface mDeferrableSurface;
    
    public SurfaceClosedException(String paramString, DeferrableSurface paramDeferrableSurface)
    {
      super();
      this.mDeferrableSurface = paramDeferrableSurface;
    }
    
    public DeferrableSurface getDeferrableSurface()
    {
      return this.mDeferrableSurface;
    }
  }
  
  public static final class SurfaceUnavailableException
    extends Exception
  {
    public SurfaceUnavailableException(String paramString)
    {
      super();
    }
  }
}
