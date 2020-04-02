package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DataCollectionArbiter;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings
{
  public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
  private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
  private boolean initialized = false;
  private SettingsController settingsController;
  private final AtomicReference<SettingsData> settingsData = new AtomicReference();
  private final CountDownLatch settingsDataLatch = new CountDownLatch(1);
  
  private Settings() {}
  
  public static Settings getInstance()
  {
    return LazyHolder.INSTANCE;
  }
  
  private void setSettingsData(SettingsData paramSettingsData)
  {
    this.settingsData.set(paramSettingsData);
    this.settingsDataLatch.countDown();
  }
  
  public SettingsData awaitSettingsData()
  {
    try
    {
      this.settingsDataLatch.await();
      SettingsData localSettingsData = (SettingsData)this.settingsData.get();
      return localSettingsData;
    }
    catch (InterruptedException localInterruptedException)
    {
      Fabric.getLogger().e("Fabric", "Interrupted while waiting for settings data.");
    }
    return null;
  }
  
  public void clearSettings()
  {
    this.settingsData.set(null);
  }
  
  public Settings initialize(Kit paramKit, IdManager paramIdManager, HttpRequestFactory paramHttpRequestFactory, String paramString1, String paramString2, String paramString3, DataCollectionArbiter paramDataCollectionArbiter)
  {
    try
    {
      boolean bool = this.initialized;
      if (bool) {
        return this;
      }
      if (this.settingsController == null)
      {
        Object localObject1 = paramKit.getContext();
        Object localObject2 = paramIdManager.getAppIdentifier();
        Object localObject3 = new io/fabric/sdk/android/services/common/ApiKey;
        ((ApiKey)localObject3).<init>();
        String str1 = ((ApiKey)localObject3).getValue((Context)localObject1);
        String str2 = paramIdManager.getInstallerPackageName();
        SystemCurrentTimeProvider localSystemCurrentTimeProvider = new io/fabric/sdk/android/services/common/SystemCurrentTimeProvider;
        localSystemCurrentTimeProvider.<init>();
        DefaultSettingsJsonTransform localDefaultSettingsJsonTransform = new io/fabric/sdk/android/services/settings/DefaultSettingsJsonTransform;
        localDefaultSettingsJsonTransform.<init>();
        localObject3 = new io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo;
        ((DefaultCachedSettingsIo)localObject3).<init>(paramKit);
        String str3 = CommonUtils.getAppIconHashOrNull((Context)localObject1);
        String str4 = String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", new Object[] { localObject2 });
        localObject2 = new io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall;
        ((DefaultSettingsSpiCall)localObject2).<init>(paramKit, paramString3, str4, paramHttpRequestFactory);
        str4 = paramIdManager.getModelName();
        paramString3 = paramIdManager.getOsBuildVersionString();
        paramHttpRequestFactory = paramIdManager.getOsDisplayVersionString();
        paramIdManager = paramIdManager.getAppInstallIdentifier();
        String str5 = CommonUtils.createInstanceIdFrom(new String[] { CommonUtils.resolveBuildId((Context)localObject1) });
        int i = DeliveryMechanism.determineFrom(str2).getId();
        localObject1 = new io/fabric/sdk/android/services/settings/SettingsRequest;
        ((SettingsRequest)localObject1).<init>(str1, str4, paramString3, paramHttpRequestFactory, paramIdManager, str5, paramString2, paramString1, i, str3);
        paramIdManager = new io/fabric/sdk/android/services/settings/DefaultSettingsController;
        paramIdManager.<init>(paramKit, (SettingsRequest)localObject1, localSystemCurrentTimeProvider, localDefaultSettingsJsonTransform, (CachedSettingsIo)localObject3, (SettingsSpiCall)localObject2, paramDataCollectionArbiter);
        this.settingsController = paramIdManager;
      }
      this.initialized = true;
      return this;
    }
    finally {}
  }
  
  public boolean loadSettingsData()
  {
    try
    {
      SettingsData localSettingsData = this.settingsController.loadSettingsData();
      setSettingsData(localSettingsData);
      boolean bool;
      if (localSettingsData != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public boolean loadSettingsSkippingCache()
  {
    try
    {
      SettingsData localSettingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
      setSettingsData(localSettingsData);
      if (localSettingsData == null) {
        Fabric.getLogger().e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
      }
      boolean bool;
      if (localSettingsData != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally {}
  }
  
  public void setSettingsController(SettingsController paramSettingsController)
  {
    this.settingsController = paramSettingsController;
  }
  
  public <T> T withSettings(SettingsAccess<T> paramSettingsAccess, T paramT)
  {
    SettingsData localSettingsData = (SettingsData)this.settingsData.get();
    if (localSettingsData != null) {
      paramT = paramSettingsAccess.usingSettings(localSettingsData);
    }
    return paramT;
  }
  
  static class LazyHolder
  {
    private static final Settings INSTANCE = new Settings(null);
    
    LazyHolder() {}
  }
  
  public static abstract interface SettingsAccess<T>
  {
    public abstract T usingSettings(SettingsData paramSettingsData);
  }
}
