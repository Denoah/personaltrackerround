package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdManager
{
  private static final String BAD_ANDROID_ID = "9774d56d682e549c";
  public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
  public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
  public static final String DEFAULT_VERSION_NAME = "0.0";
  private static final String FORWARD_SLASH_REGEX = Pattern.quote("/");
  private static final Pattern ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
  static final String PREFKEY_ADVERTISING_ID = "crashlytics.advertising.id";
  private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
  AdvertisingInfo advertisingInfo;
  AdvertisingInfoProvider advertisingInfoProvider;
  private final Context appContext;
  private final String appIdentifier;
  private final String appInstallIdentifier;
  private final boolean collectHardwareIds;
  private final boolean collectUserIds;
  boolean fetchedAdvertisingInfo;
  FirebaseInfo firebaseInfo;
  private final ReentrantLock installationIdLock = new ReentrantLock();
  private final InstallerPackageNameProvider installerPackageNameProvider;
  private final Collection<Kit> kits;
  
  public IdManager(Context paramContext, String paramString1, String paramString2, Collection<Kit> paramCollection)
  {
    if (paramContext != null)
    {
      if (paramString1 != null)
      {
        if (paramCollection != null)
        {
          this.appContext = paramContext;
          this.appIdentifier = paramString1;
          this.appInstallIdentifier = paramString2;
          this.kits = paramCollection;
          this.installerPackageNameProvider = new InstallerPackageNameProvider();
          this.advertisingInfoProvider = new AdvertisingInfoProvider(paramContext);
          this.firebaseInfo = new FirebaseInfo();
          boolean bool = CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.CollectDeviceIdentifiers", true);
          this.collectHardwareIds = bool;
          if (!bool)
          {
            paramString1 = Fabric.getLogger();
            paramString2 = new StringBuilder();
            paramString2.append("Device ID collection disabled for ");
            paramString2.append(paramContext.getPackageName());
            paramString1.d("Fabric", paramString2.toString());
          }
          bool = CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.CollectUserIdentifiers", true);
          this.collectUserIds = bool;
          if (!bool)
          {
            paramString1 = Fabric.getLogger();
            paramString2 = new StringBuilder();
            paramString2.append("User information collection disabled for ");
            paramString2.append(paramContext.getPackageName());
            paramString1.d("Fabric", paramString2.toString());
          }
          return;
        }
        throw new IllegalArgumentException("kits must not be null");
      }
      throw new IllegalArgumentException("appIdentifier must not be null");
    }
    throw new IllegalArgumentException("appContext must not be null");
  }
  
  private void checkAdvertisingIdRotation(SharedPreferences paramSharedPreferences)
  {
    AdvertisingInfo localAdvertisingInfo = getAdvertisingInfo();
    if (localAdvertisingInfo != null) {
      flushInstallationIdIfNecessary(paramSharedPreferences, localAdvertisingInfo.advertisingId);
    }
  }
  
  private String createInstallationUUID(SharedPreferences paramSharedPreferences)
  {
    this.installationIdLock.lock();
    try
    {
      String str1 = paramSharedPreferences.getString("crashlytics.installation.id", null);
      String str2 = str1;
      if (str1 == null)
      {
        str2 = formatId(UUID.randomUUID().toString());
        paramSharedPreferences.edit().putString("crashlytics.installation.id", str2).commit();
      }
      return str2;
    }
    finally
    {
      this.installationIdLock.unlock();
    }
  }
  
  private Boolean explicitCheckLimitAdTracking()
  {
    AdvertisingInfo localAdvertisingInfo = getAdvertisingInfo();
    if (localAdvertisingInfo != null) {
      return Boolean.valueOf(localAdvertisingInfo.limitAdTrackingEnabled);
    }
    return null;
  }
  
  private void flushInstallationIdIfNecessary(SharedPreferences paramSharedPreferences, String paramString)
  {
    this.installationIdLock.lock();
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool) {
        return;
      }
      String str = paramSharedPreferences.getString("crashlytics.advertising.id", null);
      if (TextUtils.isEmpty(str)) {
        paramSharedPreferences.edit().putString("crashlytics.advertising.id", paramString).commit();
      } else if (!str.equals(paramString)) {
        paramSharedPreferences.edit().remove("crashlytics.installation.id").putString("crashlytics.advertising.id", paramString).commit();
      }
      return;
    }
    finally
    {
      this.installationIdLock.unlock();
    }
  }
  
  private String formatId(String paramString)
  {
    if (paramString == null) {
      paramString = null;
    } else {
      paramString = ID_PATTERN.matcher(paramString).replaceAll("").toLowerCase(Locale.US);
    }
    return paramString;
  }
  
  private void putNonNullIdInto(Map<DeviceIdentifierType, String> paramMap, DeviceIdentifierType paramDeviceIdentifierType, String paramString)
  {
    if (paramString != null) {
      paramMap.put(paramDeviceIdentifierType, paramString);
    }
  }
  
  private String removeForwardSlashesIn(String paramString)
  {
    return paramString.replaceAll(FORWARD_SLASH_REGEX, "");
  }
  
  public boolean canCollectUserIds()
  {
    return this.collectUserIds;
  }
  
  @Deprecated
  public String createIdHeaderValue(String paramString1, String paramString2)
  {
    return "";
  }
  
  @Deprecated
  public String getAdvertisingId()
  {
    return null;
  }
  
  AdvertisingInfo getAdvertisingInfo()
  {
    try
    {
      if (!this.fetchedAdvertisingInfo)
      {
        this.advertisingInfo = this.advertisingInfoProvider.getAdvertisingInfo();
        this.fetchedAdvertisingInfo = true;
      }
      AdvertisingInfo localAdvertisingInfo = this.advertisingInfo;
      return localAdvertisingInfo;
    }
    finally {}
  }
  
  @Deprecated
  public String getAndroidId()
  {
    return null;
  }
  
  public String getAppIdentifier()
  {
    return this.appIdentifier;
  }
  
  public String getAppInstallIdentifier()
  {
    Object localObject1 = this.appInstallIdentifier;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject1 = CommonUtils.getSharedPrefs(this.appContext);
      checkAdvertisingIdRotation((SharedPreferences)localObject1);
      localObject2 = ((SharedPreferences)localObject1).getString("crashlytics.installation.id", null);
      if (localObject2 == null) {
        localObject2 = createInstallationUUID((SharedPreferences)localObject1);
      }
    }
    return localObject2;
  }
  
  @Deprecated
  public String getBluetoothMacAddress()
  {
    return null;
  }
  
  public Map<DeviceIdentifierType, String> getDeviceIdentifiers()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.kits.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Kit)localIterator.next();
      if ((localObject instanceof DeviceIdentifierProvider))
      {
        localObject = ((DeviceIdentifierProvider)localObject).getDeviceIdentifiers().entrySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
          Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
          putNonNullIdInto(localHashMap, (DeviceIdentifierType)localEntry.getKey(), (String)localEntry.getValue());
        }
      }
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public String getInstallerPackageName()
  {
    return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
  }
  
  public String getModelName()
  {
    return String.format(Locale.US, "%s/%s", new Object[] { removeForwardSlashesIn(Build.MANUFACTURER), removeForwardSlashesIn(Build.MODEL) });
  }
  
  public String getOsBuildVersionString()
  {
    return removeForwardSlashesIn(Build.VERSION.INCREMENTAL);
  }
  
  public String getOsDisplayVersionString()
  {
    return removeForwardSlashesIn(Build.VERSION.RELEASE);
  }
  
  public String getOsVersionString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getOsDisplayVersionString());
    localStringBuilder.append("/");
    localStringBuilder.append(getOsBuildVersionString());
    return localStringBuilder.toString();
  }
  
  @Deprecated
  public String getSerialNumber()
  {
    return null;
  }
  
  @Deprecated
  public String getTelephonyId()
  {
    return null;
  }
  
  @Deprecated
  public String getWifiMacAddress()
  {
    return null;
  }
  
  public Boolean isLimitAdTrackingEnabled()
  {
    Boolean localBoolean;
    if (shouldCollectHardwareIds()) {
      localBoolean = explicitCheckLimitAdTracking();
    } else {
      localBoolean = null;
    }
    return localBoolean;
  }
  
  protected boolean shouldCollectHardwareIds()
  {
    boolean bool;
    if ((this.collectHardwareIds) && (!this.firebaseInfo.isFirebaseCrashlyticsEnabled(this.appContext))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static enum DeviceIdentifierType
  {
    public final int protobufIndex;
    
    static
    {
      BLUETOOTH_MAC_ADDRESS = new DeviceIdentifierType("BLUETOOTH_MAC_ADDRESS", 1, 2);
      FONT_TOKEN = new DeviceIdentifierType("FONT_TOKEN", 2, 53);
      ANDROID_ID = new DeviceIdentifierType("ANDROID_ID", 3, 100);
      ANDROID_DEVICE_ID = new DeviceIdentifierType("ANDROID_DEVICE_ID", 4, 101);
      ANDROID_SERIAL = new DeviceIdentifierType("ANDROID_SERIAL", 5, 102);
      DeviceIdentifierType localDeviceIdentifierType = new DeviceIdentifierType("ANDROID_ADVERTISING_ID", 6, 103);
      ANDROID_ADVERTISING_ID = localDeviceIdentifierType;
      $VALUES = new DeviceIdentifierType[] { WIFI_MAC_ADDRESS, BLUETOOTH_MAC_ADDRESS, FONT_TOKEN, ANDROID_ID, ANDROID_DEVICE_ID, ANDROID_SERIAL, localDeviceIdentifierType };
    }
    
    private DeviceIdentifierType(int paramInt)
    {
      this.protobufIndex = paramInt;
    }
  }
}
