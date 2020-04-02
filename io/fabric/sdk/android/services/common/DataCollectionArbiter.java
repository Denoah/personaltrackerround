package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class DataCollectionArbiter
{
  private static final String FIREBASE_CRASHLYTICS_COLLECTION_ENABLED = "firebase_crashlytics_collection_enabled";
  private static final String FIREBASE_CRASHLYTICS_PREFS = "com.google.firebase.crashlytics.prefs";
  private static DataCollectionArbiter instance;
  private static Object instanceLock = new Object();
  private volatile boolean crashlyticsDataCollectionEnabled;
  private volatile boolean crashlyticsDataCollectionExplicitlySet;
  private final FirebaseApp firebaseApp;
  private boolean isUnity;
  private final SharedPreferences sharedPreferences;
  
  private DataCollectionArbiter(Context paramContext)
  {
    boolean bool1 = false;
    this.isUnity = false;
    if (paramContext != null)
    {
      this.sharedPreferences = paramContext.getSharedPreferences("com.google.firebase.crashlytics.prefs", 0);
      this.firebaseApp = FirebaseAppImpl.getInstance(paramContext);
      if (this.sharedPreferences.contains("firebase_crashlytics_collection_enabled")) {
        bool2 = this.sharedPreferences.getBoolean("firebase_crashlytics_collection_enabled", true);
      }
      boolean bool3;
      for (;;)
      {
        bool3 = true;
        break;
        try
        {
          Object localObject = paramContext.getPackageManager();
          if (localObject != null)
          {
            localObject = ((PackageManager)localObject).getApplicationInfo(paramContext.getPackageName(), 128);
            if ((localObject != null) && (((ApplicationInfo)localObject).metaData != null) && (((ApplicationInfo)localObject).metaData.containsKey("firebase_crashlytics_collection_enabled"))) {
              bool2 = ((ApplicationInfo)localObject).metaData.getBoolean("firebase_crashlytics_collection_enabled");
            }
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          Fabric.getLogger().d("Fabric", "Unable to get PackageManager. Falling through", localNameNotFoundException);
          bool3 = false;
          bool2 = true;
        }
      }
      this.crashlyticsDataCollectionEnabled = bool2;
      this.crashlyticsDataCollectionExplicitlySet = bool3;
      boolean bool2 = bool1;
      if (CommonUtils.resolveUnityEditorVersion(paramContext) != null) {
        bool2 = true;
      }
      this.isUnity = bool2;
      return;
    }
    throw new RuntimeException("null context");
  }
  
  public static DataCollectionArbiter getInstance(Context paramContext)
  {
    synchronized (instanceLock)
    {
      if (instance == null)
      {
        DataCollectionArbiter localDataCollectionArbiter = new io/fabric/sdk/android/services/common/DataCollectionArbiter;
        localDataCollectionArbiter.<init>(paramContext);
        instance = localDataCollectionArbiter;
      }
      paramContext = instance;
      return paramContext;
    }
  }
  
  public static void resetForTesting(Context paramContext)
  {
    synchronized (instanceLock)
    {
      DataCollectionArbiter localDataCollectionArbiter = new io/fabric/sdk/android/services/common/DataCollectionArbiter;
      localDataCollectionArbiter.<init>(paramContext);
      instance = localDataCollectionArbiter;
      return;
    }
  }
  
  public boolean isDataCollectionEnabled()
  {
    if ((this.isUnity) && (this.crashlyticsDataCollectionExplicitlySet)) {
      return this.crashlyticsDataCollectionEnabled;
    }
    FirebaseApp localFirebaseApp = this.firebaseApp;
    if (localFirebaseApp != null) {
      return localFirebaseApp.isDataCollectionDefaultEnabled();
    }
    return true;
  }
  
  public void setCrashlyticsDataCollectionEnabled(boolean paramBoolean)
  {
    this.crashlyticsDataCollectionEnabled = paramBoolean;
    this.crashlyticsDataCollectionExplicitlySet = true;
    this.sharedPreferences.edit().putBoolean("firebase_crashlytics_collection_enabled", paramBoolean).commit();
  }
  
  public boolean shouldAutoInitialize()
  {
    return this.crashlyticsDataCollectionEnabled;
  }
}
