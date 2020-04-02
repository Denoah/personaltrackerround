package androidx.camera.core.impl.utils;

import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;

public final class MainThreadAsyncHandler
{
  private static volatile Handler sHandler;
  
  private MainThreadAsyncHandler() {}
  
  public static Handler getInstance()
  {
    if (sHandler != null) {
      return sHandler;
    }
    try
    {
      if (sHandler == null) {
        sHandler = HandlerCompat.createAsync(Looper.getMainLooper());
      }
      return sHandler;
    }
    finally {}
  }
}
