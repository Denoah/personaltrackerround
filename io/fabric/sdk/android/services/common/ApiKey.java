package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class ApiKey
{
  static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";
  static final String FABRIC_API_KEY = "io.fabric.ApiKey";
  static final String STRING_TWITTER_CONSUMER_SECRET = "@string/twitter_consumer_secret";
  
  public ApiKey() {}
  
  @Deprecated
  public static String getApiKey(Context paramContext)
  {
    Fabric.getLogger().w("Fabric", "getApiKey(context) is deprecated, please upgrade kit(s) to the latest version.");
    return new ApiKey().getValue(paramContext);
  }
  
  @Deprecated
  public static String getApiKey(Context paramContext, boolean paramBoolean)
  {
    Fabric.getLogger().w("Fabric", "getApiKey(context, debug) is deprecated, please upgrade kit(s) to the latest version.");
    return new ApiKey().getValue(paramContext);
  }
  
  protected String buildApiKeyInstructions()
  {
    return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
  }
  
  protected String getApiKeyFromFirebaseAppId(Context paramContext)
  {
    return new FirebaseInfo().getApiKeyFromFirebaseAppId(paramContext);
  }
  
  protected String getApiKeyFromManifest(Context paramContext)
  {
    StringBuilder localStringBuilder = null;
    Object localObject1 = null;
    Context localContext = null;
    Object localObject2 = localStringBuilder;
    try
    {
      Object localObject3 = paramContext.getPackageName();
      localObject2 = localStringBuilder;
      localObject3 = paramContext.getPackageManager().getApplicationInfo((String)localObject3, 128).metaData;
      paramContext = localObject1;
      if (localObject3 == null) {
        return paramContext;
      }
      localObject2 = localStringBuilder;
      paramContext = ((Bundle)localObject3).getString("io.fabric.ApiKey");
      try
      {
        if ("@string/twitter_consumer_secret".equals(paramContext)) {
          Fabric.getLogger().d("Fabric", "Ignoring bad default value for Fabric ApiKey set by FirebaseUI-Auth");
        } else {
          localContext = paramContext;
        }
        paramContext = localContext;
        if (localContext != null) {
          return paramContext;
        }
        localObject2 = localContext;
        Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Manifest");
        localObject2 = localContext;
        paramContext = ((Bundle)localObject3).getString("com.crashlytics.ApiKey");
      }
      catch (Exception localException1) {}
      localObject2 = Fabric.getLogger();
    }
    catch (Exception localException2)
    {
      paramContext = (Context)localObject2;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Caught non-fatal exception while retrieving apiKey: ");
    localStringBuilder.append(localException2);
    ((Logger)localObject2).d("Fabric", localStringBuilder.toString());
    return paramContext;
  }
  
  protected String getApiKeyFromStrings(Context paramContext)
  {
    int i = CommonUtils.getResourcesIdentifier(paramContext, "io.fabric.ApiKey", "string");
    int j = i;
    if (i == 0)
    {
      Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Strings");
      j = CommonUtils.getResourcesIdentifier(paramContext, "com.crashlytics.ApiKey", "string");
    }
    if (j != 0) {
      paramContext = paramContext.getResources().getString(j);
    } else {
      paramContext = null;
    }
    return paramContext;
  }
  
  public String getValue(Context paramContext)
  {
    Object localObject1 = getApiKeyFromManifest(paramContext);
    Object localObject2 = localObject1;
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      localObject2 = getApiKeyFromStrings(paramContext);
    }
    localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = getApiKeyFromFirebaseAppId(paramContext);
    }
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      logErrorOrThrowException(paramContext);
    }
    return localObject1;
  }
  
  protected void logErrorOrThrowException(Context paramContext)
  {
    if ((!Fabric.isDebuggable()) && (!CommonUtils.isAppDebuggable(paramContext)))
    {
      Fabric.getLogger().e("Fabric", buildApiKeyInstructions());
      return;
    }
    throw new IllegalArgumentException(buildApiKeyInstructions());
  }
}
