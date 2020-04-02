package androidx.camera.core.impl.utils.executor;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ScheduledExecutorService;

final class MainThreadExecutor
{
  private static volatile ScheduledExecutorService sInstance;
  
  private MainThreadExecutor() {}
  
  static ScheduledExecutorService getInstance()
  {
    if (sInstance != null) {
      return sInstance;
    }
    try
    {
      if (sInstance == null)
      {
        HandlerScheduledExecutorService localHandlerScheduledExecutorService = new androidx/camera/core/impl/utils/executor/HandlerScheduledExecutorService;
        Handler localHandler = new android/os/Handler;
        localHandler.<init>(Looper.getMainLooper());
        localHandlerScheduledExecutorService.<init>(localHandler);
        sInstance = localHandlerScheduledExecutorService;
      }
      return sInstance;
    }
    finally {}
  }
}
