package androidx.camera.core.impl;

import android.view.Surface;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public final class DeferrableSurfaces
{
  private DeferrableSurfaces() {}
  
  public static void decrementAll(List<DeferrableSurface> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      ((DeferrableSurface)paramList.next()).decrementUseCount();
    }
  }
  
  public static void incrementAll(List<DeferrableSurface> paramList)
    throws DeferrableSurface.SurfaceClosedException
  {
    if (!paramList.isEmpty())
    {
      int i = 0;
      for (;;)
      {
        int j = i;
        try
        {
          ((DeferrableSurface)paramList.get(i)).incrementUseCount();
          int k = i + 1;
          j = k;
          int m = paramList.size();
          i = k;
          if (k < m) {
            break;
          }
        }
        catch (DeferrableSurface.SurfaceClosedException localSurfaceClosedException)
        {
          for (i = j - 1; i >= 0; i--) {
            ((DeferrableSurface)paramList.get(i)).decrementUseCount();
          }
          throw localSurfaceClosedException;
        }
      }
    }
  }
  
  public static ListenableFuture<List<Surface>> surfaceListWithTimeout(Collection<DeferrableSurface> paramCollection, boolean paramBoolean, long paramLong, Executor paramExecutor, ScheduledExecutorService paramScheduledExecutorService)
  {
    ArrayList localArrayList = new ArrayList();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      localArrayList.add(((DeferrableSurface)paramCollection.next()).getSurface());
    }
    return CallbackToFutureAdapter.getFuture(new _..Lambda.DeferrableSurfaces._eJ7iY3oHlcbhnrK9kAok8keF3w(localArrayList, paramScheduledExecutorService, paramExecutor, paramLong, paramBoolean));
  }
  
  public static boolean tryIncrementAll(List<DeferrableSurface> paramList)
  {
    try
    {
      incrementAll(paramList);
      return true;
    }
    catch (DeferrableSurface.SurfaceClosedException paramList) {}
    return false;
  }
}
