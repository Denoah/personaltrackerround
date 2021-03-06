package androidx.core.location;

import android.location.LocationManager;
import android.os.Build.VERSION;

public final class LocationManagerCompat
{
  private LocationManagerCompat() {}
  
  public static boolean isLocationEnabled(LocationManager paramLocationManager)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return paramLocationManager.isLocationEnabled();
    }
    boolean bool;
    if ((!paramLocationManager.isProviderEnabled("network")) && (!paramLocationManager.isProviderEnabled("gps"))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
