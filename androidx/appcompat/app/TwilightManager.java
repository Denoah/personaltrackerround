package androidx.appcompat.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import java.util.Calendar;

class TwilightManager
{
  private static final int SUNRISE = 6;
  private static final int SUNSET = 22;
  private static final String TAG = "TwilightManager";
  private static TwilightManager sInstance;
  private final Context mContext;
  private final LocationManager mLocationManager;
  private final TwilightState mTwilightState = new TwilightState();
  
  TwilightManager(Context paramContext, LocationManager paramLocationManager)
  {
    this.mContext = paramContext;
    this.mLocationManager = paramLocationManager;
  }
  
  static TwilightManager getInstance(Context paramContext)
  {
    if (sInstance == null)
    {
      paramContext = paramContext.getApplicationContext();
      sInstance = new TwilightManager(paramContext, (LocationManager)paramContext.getSystemService("location"));
    }
    return sInstance;
  }
  
  private Location getLastKnownLocation()
  {
    int i = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION");
    Location localLocation1 = null;
    Location localLocation2;
    if (i == 0) {
      localLocation2 = getLastKnownLocationForProvider("network");
    } else {
      localLocation2 = null;
    }
    if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0) {
      localLocation1 = getLastKnownLocationForProvider("gps");
    }
    if ((localLocation1 != null) && (localLocation2 != null))
    {
      Location localLocation3 = localLocation2;
      if (localLocation1.getTime() > localLocation2.getTime()) {
        localLocation3 = localLocation1;
      }
      return localLocation3;
    }
    if (localLocation1 != null) {
      localLocation2 = localLocation1;
    }
    return localLocation2;
  }
  
  private Location getLastKnownLocationForProvider(String paramString)
  {
    try
    {
      if (this.mLocationManager.isProviderEnabled(paramString))
      {
        paramString = this.mLocationManager.getLastKnownLocation(paramString);
        return paramString;
      }
    }
    catch (Exception paramString)
    {
      Log.d("TwilightManager", "Failed to get last known location", paramString);
    }
    return null;
  }
  
  private boolean isStateValid()
  {
    boolean bool;
    if (this.mTwilightState.nextUpdate > System.currentTimeMillis()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static void setInstance(TwilightManager paramTwilightManager)
  {
    sInstance = paramTwilightManager;
  }
  
  private void updateState(Location paramLocation)
  {
    TwilightState localTwilightState = this.mTwilightState;
    long l1 = System.currentTimeMillis();
    TwilightCalculator localTwilightCalculator = TwilightCalculator.getInstance();
    localTwilightCalculator.calculateTwilight(l1 - 86400000L, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l2 = localTwilightCalculator.sunset;
    localTwilightCalculator.calculateTwilight(l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    int i = localTwilightCalculator.state;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    long l3 = localTwilightCalculator.sunrise;
    long l4 = localTwilightCalculator.sunset;
    localTwilightCalculator.calculateTwilight(86400000L + l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l5 = localTwilightCalculator.sunrise;
    if ((l3 != -1L) && (l4 != -1L))
    {
      if (l1 > l4) {
        l1 = 0L + l5;
      } else if (l1 > l3) {
        l1 = 0L + l4;
      } else {
        l1 = 0L + l3;
      }
      l1 += 60000L;
    }
    else
    {
      l1 = 43200000L + l1;
    }
    localTwilightState.isNight = bool;
    localTwilightState.yesterdaySunset = l2;
    localTwilightState.todaySunrise = l3;
    localTwilightState.todaySunset = l4;
    localTwilightState.tomorrowSunrise = l5;
    localTwilightState.nextUpdate = l1;
  }
  
  boolean isNight()
  {
    TwilightState localTwilightState = this.mTwilightState;
    if (isStateValid()) {
      return localTwilightState.isNight;
    }
    Location localLocation = getLastKnownLocation();
    if (localLocation != null)
    {
      updateState(localLocation);
      return localTwilightState.isNight;
    }
    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
    int i = Calendar.getInstance().get(11);
    boolean bool;
    if ((i >= 6) && (i < 22)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static class TwilightState
  {
    boolean isNight;
    long nextUpdate;
    long todaySunrise;
    long todaySunset;
    long tomorrowSunrise;
    long yesterdaySunset;
    
    TwilightState() {}
  }
}
