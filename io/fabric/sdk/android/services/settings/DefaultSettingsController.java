package io.fabric.sdk.android.services.settings;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.DataCollectionArbiter;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsController
  implements SettingsController
{
  private static final String LOAD_ERROR_MESSAGE = "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.";
  private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
  private final CachedSettingsIo cachedSettingsIo;
  private final CurrentTimeProvider currentTimeProvider;
  private final DataCollectionArbiter dataCollectionArbiter;
  private final Kit kit;
  private final PreferenceStore preferenceStore;
  private final SettingsJsonTransform settingsJsonTransform;
  private final SettingsRequest settingsRequest;
  private final SettingsSpiCall settingsSpiCall;
  
  public DefaultSettingsController(Kit paramKit, SettingsRequest paramSettingsRequest, CurrentTimeProvider paramCurrentTimeProvider, SettingsJsonTransform paramSettingsJsonTransform, CachedSettingsIo paramCachedSettingsIo, SettingsSpiCall paramSettingsSpiCall, DataCollectionArbiter paramDataCollectionArbiter)
  {
    this.kit = paramKit;
    this.settingsRequest = paramSettingsRequest;
    this.currentTimeProvider = paramCurrentTimeProvider;
    this.settingsJsonTransform = paramSettingsJsonTransform;
    this.cachedSettingsIo = paramCachedSettingsIo;
    this.settingsSpiCall = paramSettingsSpiCall;
    this.dataCollectionArbiter = paramDataCollectionArbiter;
    this.preferenceStore = new PreferenceStoreImpl(paramKit);
  }
  
  private SettingsData getCachedSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = localObject1;
    try
    {
      if (!SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(paramSettingsCacheBehavior))
      {
        JSONObject localJSONObject = this.cachedSettingsIo.readCachedSettings();
        if (localJSONObject != null)
        {
          localObject3 = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, localJSONObject);
          if (localObject3 != null)
          {
            logSettings(localJSONObject, "Loaded cached settings: ");
            long l = this.currentTimeProvider.getCurrentTimeMillis();
            if ((!SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(paramSettingsCacheBehavior)) && (((SettingsData)localObject3).isExpired(l)))
            {
              Fabric.getLogger().d("Fabric", "Cached settings have expired.");
              localObject3 = localObject1;
              break label188;
            }
            try
            {
              Fabric.getLogger().d("Fabric", "Returning cached settings.");
            }
            catch (Exception paramSettingsCacheBehavior)
            {
              break label175;
            }
          }
          else
          {
            Fabric.getLogger().e("Fabric", "Failed to transform cached settings data.", null);
            localObject3 = localObject1;
            break label188;
          }
        }
        else
        {
          Fabric.getLogger().d("Fabric", "No cached settings data found.");
          localObject3 = localObject1;
        }
      }
    }
    catch (Exception paramSettingsCacheBehavior)
    {
      localObject3 = localObject2;
      label175:
      Fabric.getLogger().e("Fabric", "Failed to get cached settings", paramSettingsCacheBehavior);
    }
    label188:
    return localObject3;
  }
  
  private void logSettings(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    Logger localLogger = Fabric.getLogger();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(paramJSONObject.toString());
    localLogger.d("Fabric", localStringBuilder.toString());
  }
  
  boolean buildInstanceIdentifierChanged()
  {
    return getStoredBuildInstanceIdentifier().equals(getBuildInstanceIdentifierFromContext()) ^ true;
  }
  
  String getBuildInstanceIdentifierFromContext()
  {
    return CommonUtils.createInstanceIdFrom(new String[] { CommonUtils.resolveBuildId(this.kit.getContext()) });
  }
  
  String getStoredBuildInstanceIdentifier()
  {
    return this.preferenceStore.get().getString("existing_instance_identifier", "");
  }
  
  public SettingsData loadSettingsData()
  {
    return loadSettingsData(SettingsCacheBehavior.USE_CACHE);
  }
  
  public SettingsData loadSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior)
  {
    boolean bool = this.dataCollectionArbiter.isDataCollectionEnabled();
    Object localObject1 = null;
    JSONObject localJSONObject = null;
    if (!bool)
    {
      Fabric.getLogger().d("Fabric", "Not fetching settings, because data collection is disabled by Firebase.");
      return null;
    }
    Object localObject2 = localJSONObject;
    Object localObject3 = localObject1;
    try
    {
      if (!Fabric.isDebuggable())
      {
        localObject2 = localJSONObject;
        localObject3 = localObject1;
        if (!buildInstanceIdentifierChanged())
        {
          localObject3 = localObject1;
          localObject2 = getCachedSettingsData(paramSettingsCacheBehavior);
        }
      }
      localObject3 = localObject2;
      if (localObject2 == null)
      {
        localObject3 = localObject2;
        localJSONObject = this.settingsSpiCall.invoke(this.settingsRequest);
        localObject3 = localObject2;
        if (localJSONObject != null)
        {
          localObject3 = localObject2;
          paramSettingsCacheBehavior = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, localJSONObject);
          localObject3 = paramSettingsCacheBehavior;
          this.cachedSettingsIo.writeCachedSettings(paramSettingsCacheBehavior.expiresAtMillis, localJSONObject);
          localObject3 = paramSettingsCacheBehavior;
          logSettings(localJSONObject, "Loaded settings: ");
          localObject3 = paramSettingsCacheBehavior;
          setStoredBuildInstanceIdentifier(getBuildInstanceIdentifierFromContext());
          localObject3 = paramSettingsCacheBehavior;
        }
      }
      paramSettingsCacheBehavior = (SettingsCacheBehavior)localObject3;
      if (localObject3 == null) {
        paramSettingsCacheBehavior = getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
      }
    }
    catch (Exception paramSettingsCacheBehavior)
    {
      Fabric.getLogger().e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", paramSettingsCacheBehavior);
      paramSettingsCacheBehavior = (SettingsCacheBehavior)localObject3;
    }
    return paramSettingsCacheBehavior;
  }
  
  boolean setStoredBuildInstanceIdentifier(String paramString)
  {
    SharedPreferences.Editor localEditor = this.preferenceStore.edit();
    localEditor.putString("existing_instance_identifier", paramString);
    return this.preferenceStore.save(localEditor);
  }
}
