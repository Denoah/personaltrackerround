package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class FirebaseInfo
{
  static final String AUTO_INITIALIZE = "io.fabric.auto_initialize";
  static final String FIREBASE_FEATURE_SWITCH = "com.crashlytics.useFirebaseAppId";
  static final String GOOGLE_APP_ID = "google_app_id";
  
  public FirebaseInfo() {}
  
  String createApiKeyFromFirebaseAppId(String paramString)
  {
    return CommonUtils.sha256(paramString).substring(0, 40);
  }
  
  String getApiKeyFromFirebaseAppId(Context paramContext)
  {
    int i = CommonUtils.getResourcesIdentifier(paramContext, "google_app_id", "string");
    if (i != 0)
    {
      Fabric.getLogger().d("Fabric", "Generating Crashlytics ApiKey from google_app_id in Strings");
      return createApiKeyFromFirebaseAppId(paramContext.getResources().getString(i));
    }
    return null;
  }
  
  boolean hasApiKey(Context paramContext)
  {
    if (!TextUtils.isEmpty(new ApiKey().getApiKeyFromManifest(paramContext))) {
      return true;
    }
    return TextUtils.isEmpty(new ApiKey().getApiKeyFromStrings(paramContext)) ^ true;
  }
  
  boolean hasGoogleAppId(Context paramContext)
  {
    int i = CommonUtils.getResourcesIdentifier(paramContext, "google_app_id", "string");
    if (i == 0) {
      return false;
    }
    return TextUtils.isEmpty(paramContext.getResources().getString(i)) ^ true;
  }
  
  public boolean isAutoInitializeFlagEnabled(Context paramContext)
  {
    int i = CommonUtils.getResourcesIdentifier(paramContext, "io.fabric.auto_initialize", "bool");
    if (i == 0) {
      return false;
    }
    boolean bool = paramContext.getResources().getBoolean(i);
    if (bool) {
      Fabric.getLogger().d("Fabric", "Found Fabric auto-initialization flag for joint Firebase/Fabric customers");
    }
    return bool;
  }
  
  public boolean isFirebaseCrashlyticsEnabled(Context paramContext)
  {
    boolean bool1 = false;
    if (CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.useFirebaseAppId", false)) {
      return true;
    }
    boolean bool2 = bool1;
    if (hasGoogleAppId(paramContext))
    {
      bool2 = bool1;
      if (!hasApiKey(paramContext)) {
        bool2 = true;
      }
    }
    return bool2;
  }
}
