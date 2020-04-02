package androidx.camera.core.impl.utils;

import android.os.Looper;
import androidx.core.util.Preconditions;

public final class Threads
{
  private Threads() {}
  
  public static void checkBackgroundThread()
  {
    Preconditions.checkState(isBackgroundThread(), "In application's main thread");
  }
  
  public static void checkMainThread()
  {
    Preconditions.checkState(isMainThread(), "Not in application's main thread");
  }
  
  public static boolean isBackgroundThread()
  {
    return isMainThread() ^ true;
  }
  
  public static boolean isMainThread()
  {
    boolean bool;
    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
