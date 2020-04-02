package androidx.core.app;

import android.app.ActivityManager;
import android.os.Build.VERSION;

public final class ActivityManagerCompat
{
  private ActivityManagerCompat() {}
  
  public static boolean isLowRamDevice(ActivityManager paramActivityManager)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramActivityManager.isLowRamDevice();
    }
    return false;
  }
}
