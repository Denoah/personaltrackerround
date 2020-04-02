package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DataCollectionArbiter;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

class Onboarding
  extends Kit<Boolean>
{
  private static final String BINARY_BUILD_TYPE = "binary";
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  private String applicationLabel;
  private String installerPackageName;
  private final Future<Map<String, KitInfo>> kitsFinder;
  private PackageInfo packageInfo;
  private PackageManager packageManager;
  private String packageName;
  private final Collection<Kit> providedKits;
  private final HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
  private String targetAndroidSdkVersion;
  private String versionCode;
  private String versionName;
  
  public Onboarding(Future<Map<String, KitInfo>> paramFuture, Collection<Kit> paramCollection)
  {
    this.kitsFinder = paramFuture;
    this.providedKits = paramCollection;
  }
  
  private AppRequestData buildAppRequest(IconRequest paramIconRequest, Collection<KitInfo> paramCollection)
  {
    Object localObject = getContext();
    String str = new ApiKey().getValue((Context)localObject);
    localObject = CommonUtils.createInstanceIdFrom(new String[] { CommonUtils.resolveBuildId((Context)localObject) });
    int i = DeliveryMechanism.determineFrom(this.installerPackageName).getId();
    return new AppRequestData(str, getIdManager().getAppIdentifier(), this.versionName, this.versionCode, (String)localObject, this.applicationLabel, i, this.targetAndroidSdkVersion, "0", paramIconRequest, paramCollection);
  }
  
  private boolean performAutoConfigure(String paramString, AppSettingsData paramAppSettingsData, Collection<KitInfo> paramCollection)
  {
    boolean bool;
    if ("new".equals(paramAppSettingsData.status))
    {
      if (performCreateApp(paramString, paramAppSettingsData, paramCollection))
      {
        bool = Settings.getInstance().loadSettingsSkippingCache();
      }
      else
      {
        Fabric.getLogger().e("Fabric", "Failed to create app with Crashlytics service.", null);
        bool = false;
      }
    }
    else if ("configured".equals(paramAppSettingsData.status))
    {
      bool = Settings.getInstance().loadSettingsSkippingCache();
    }
    else
    {
      if (paramAppSettingsData.updateRequired)
      {
        Fabric.getLogger().d("Fabric", "Server says an update is required - forcing a full App update.");
        performUpdateApp(paramString, paramAppSettingsData, paramCollection);
      }
      bool = true;
    }
    return bool;
  }
  
  private boolean performCreateApp(String paramString, AppSettingsData paramAppSettingsData, Collection<KitInfo> paramCollection)
  {
    paramString = buildAppRequest(IconRequest.build(getContext(), paramString), paramCollection);
    return new CreateAppSpiCall(this, getOverridenSpiEndpoint(), paramAppSettingsData.url, this.requestFactory).invoke(paramString);
  }
  
  private boolean performUpdateApp(AppSettingsData paramAppSettingsData, IconRequest paramIconRequest, Collection<KitInfo> paramCollection)
  {
    paramIconRequest = buildAppRequest(paramIconRequest, paramCollection);
    return new UpdateAppSpiCall(this, getOverridenSpiEndpoint(), paramAppSettingsData.url, this.requestFactory).invoke(paramIconRequest);
  }
  
  private boolean performUpdateApp(String paramString, AppSettingsData paramAppSettingsData, Collection<KitInfo> paramCollection)
  {
    return performUpdateApp(paramAppSettingsData, IconRequest.build(getContext(), paramString), paramCollection);
  }
  
  private SettingsData retrieveSettingsData()
  {
    try
    {
      Settings.getInstance().initialize(this, this.idManager, this.requestFactory, this.versionCode, this.versionName, getOverridenSpiEndpoint(), DataCollectionArbiter.getInstance(getContext())).loadSettingsData();
      SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
      return localSettingsData;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Error dealing with settings", localException);
    }
    return null;
  }
  
  protected Boolean doInBackground()
  {
    String str = CommonUtils.getAppIconHashOrNull(getContext());
    SettingsData localSettingsData = retrieveSettingsData();
    if (localSettingsData != null) {
      try
      {
        if (this.kitsFinder != null) {
          localObject = (Map)this.kitsFinder.get();
        } else {
          localObject = new HashMap();
        }
        Object localObject = mergeKits((Map)localObject, this.providedKits);
        bool = performAutoConfigure(str, localSettingsData.appData, ((Map)localObject).values());
      }
      catch (Exception localException)
      {
        Fabric.getLogger().e("Fabric", "Error performing auto configuration.", localException);
      }
    }
    boolean bool = false;
    return Boolean.valueOf(bool);
  }
  
  public String getIdentifier()
  {
    return "io.fabric.sdk.android:fabric";
  }
  
  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
  }
  
  public String getVersion()
  {
    return "1.4.8.32";
  }
  
  Map<String, KitInfo> mergeKits(Map<String, KitInfo> paramMap, Collection<Kit> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      Kit localKit = (Kit)paramCollection.next();
      if (!paramMap.containsKey(localKit.getIdentifier())) {
        paramMap.put(localKit.getIdentifier(), new KitInfo(localKit.getIdentifier(), localKit.getVersion(), "binary"));
      }
    }
    return paramMap;
  }
  
  protected boolean onPreExecute()
  {
    try
    {
      this.installerPackageName = getIdManager().getInstallerPackageName();
      this.packageManager = getContext().getPackageManager();
      Object localObject = getContext().getPackageName();
      this.packageName = ((String)localObject);
      localObject = this.packageManager.getPackageInfo((String)localObject, 0);
      this.packageInfo = ((PackageInfo)localObject);
      this.versionCode = Integer.toString(((PackageInfo)localObject).versionCode);
      if (this.packageInfo.versionName == null) {
        localObject = "0.0";
      } else {
        localObject = this.packageInfo.versionName;
      }
      this.versionName = ((String)localObject);
      this.applicationLabel = this.packageManager.getApplicationLabel(getContext().getApplicationInfo()).toString();
      this.targetAndroidSdkVersion = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Fabric.getLogger().e("Fabric", "Failed init", localNameNotFoundException);
    }
    return false;
  }
}
