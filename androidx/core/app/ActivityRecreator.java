package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

final class ActivityRecreator
{
  private static final String LOG_TAG = "ActivityRecreator";
  protected static final Class<?> activityThreadClass;
  private static final Handler mainHandler = new Handler(Looper.getMainLooper());
  protected static final Field mainThreadField;
  protected static final Method performStopActivity2ParamsMethod = getPerformStopActivity2Params(activityThreadClass);
  protected static final Method performStopActivity3ParamsMethod;
  protected static final Method requestRelaunchActivityMethod = getRequestRelaunchActivityMethod(activityThreadClass);
  protected static final Field tokenField;
  
  static
  {
    activityThreadClass = getActivityThreadClass();
    mainThreadField = getMainThreadField();
    tokenField = getTokenField();
    performStopActivity3ParamsMethod = getPerformStopActivity3Params(activityThreadClass);
  }
  
  private ActivityRecreator() {}
  
  private static Class<?> getActivityThreadClass()
  {
    try
    {
      Class localClass = Class.forName("android.app.ActivityThread");
      return localClass;
    }
    finally {}
    return null;
  }
  
  private static Field getMainThreadField()
  {
    try
    {
      Field localField = Activity.class.getDeclaredField("mMainThread");
      localField.setAccessible(true);
      return localField;
    }
    finally {}
    return null;
  }
  
  private static Method getPerformStopActivity2Params(Class<?> paramClass)
  {
    if (paramClass == null) {
      return null;
    }
    try
    {
      paramClass = paramClass.getDeclaredMethod("performStopActivity", new Class[] { IBinder.class, Boolean.TYPE });
      paramClass.setAccessible(true);
      return paramClass;
    }
    finally {}
    return null;
  }
  
  private static Method getPerformStopActivity3Params(Class<?> paramClass)
  {
    if (paramClass == null) {
      return null;
    }
    try
    {
      paramClass = paramClass.getDeclaredMethod("performStopActivity", new Class[] { IBinder.class, Boolean.TYPE, String.class });
      paramClass.setAccessible(true);
      return paramClass;
    }
    finally {}
    return null;
  }
  
  private static Method getRequestRelaunchActivityMethod(Class<?> paramClass)
  {
    if ((needsRelaunchCall()) && (paramClass != null)) {}
    try
    {
      paramClass = paramClass.getDeclaredMethod("requestRelaunchActivity", new Class[] { IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE });
      paramClass.setAccessible(true);
      return paramClass;
    }
    finally
    {
      for (;;) {}
    }
    return null;
  }
  
  private static Field getTokenField()
  {
    try
    {
      Field localField = Activity.class.getDeclaredField("mToken");
      localField.setAccessible(true);
      return localField;
    }
    finally {}
    return null;
  }
  
  private static boolean needsRelaunchCall()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT != 26) && (Build.VERSION.SDK_INT != 27)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  protected static boolean queueOnStopIfNecessary(Object paramObject, Activity paramActivity)
  {
    try
    {
      Object localObject1 = tokenField.get(paramActivity);
      if (localObject1 != paramObject) {
        return false;
      }
      Object localObject2 = mainThreadField.get(paramActivity);
      paramActivity = mainHandler;
      paramObject = new androidx/core/app/ActivityRecreator$3;
      paramObject.<init>(localObject2, localObject1);
      return true;
    }
    finally
    {
      Log.e("ActivityRecreator", "Exception while fetching field values", paramObject);
    }
    return false;
  }
  
  static boolean recreate(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      paramActivity.recreate();
      return true;
    }
    if ((needsRelaunchCall()) && (requestRelaunchActivityMethod == null)) {
      return false;
    }
    if ((performStopActivity2ParamsMethod == null) && (performStopActivity3ParamsMethod == null)) {
      return false;
    }
    try
    {
      Object localObject1 = tokenField.get(paramActivity);
      if (localObject1 == null) {
        return false;
      }
      Object localObject2 = mainThreadField.get(paramActivity);
      if (localObject2 == null) {
        return false;
      }
      Application localApplication = paramActivity.getApplication();
      LifecycleCheckCallbacks localLifecycleCheckCallbacks = new androidx/core/app/ActivityRecreator$LifecycleCheckCallbacks;
      localLifecycleCheckCallbacks.<init>(paramActivity);
      localApplication.registerActivityLifecycleCallbacks(localLifecycleCheckCallbacks);
      Handler localHandler = mainHandler;
      Object localObject3 = new androidx/core/app/ActivityRecreator$1;
      ((1)localObject3).<init>(localLifecycleCheckCallbacks, localObject1);
      localHandler.post((Runnable)localObject3);
      try
      {
        if (needsRelaunchCall()) {
          requestRelaunchActivityMethod.invoke(localObject2, new Object[] { localObject1, null, null, Integer.valueOf(0), Boolean.valueOf(false), null, null, Boolean.valueOf(false), Boolean.valueOf(false) });
        } else {
          paramActivity.recreate();
        }
        return true;
      }
      finally
      {
        localObject2 = mainHandler;
        localObject3 = new androidx/core/app/ActivityRecreator$2;
        ((2)localObject3).<init>(localApplication, localLifecycleCheckCallbacks);
        ((Handler)localObject2).post((Runnable)localObject3);
      }
      return false;
    }
    finally {}
  }
  
  private static final class LifecycleCheckCallbacks
    implements Application.ActivityLifecycleCallbacks
  {
    Object currentlyRecreatingToken;
    private Activity mActivity;
    private boolean mDestroyed = false;
    private boolean mStarted = false;
    private boolean mStopQueued = false;
    
    LifecycleCheckCallbacks(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }
    
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity)
    {
      if (this.mActivity == paramActivity)
      {
        this.mActivity = null;
        this.mDestroyed = true;
      }
    }
    
    public void onActivityPaused(Activity paramActivity)
    {
      if ((this.mDestroyed) && (!this.mStopQueued) && (!this.mStarted) && (ActivityRecreator.queueOnStopIfNecessary(this.currentlyRecreatingToken, paramActivity)))
      {
        this.mStopQueued = true;
        this.currentlyRecreatingToken = null;
      }
    }
    
    public void onActivityResumed(Activity paramActivity) {}
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity)
    {
      if (this.mActivity == paramActivity) {
        this.mStarted = true;
      }
    }
    
    public void onActivityStopped(Activity paramActivity) {}
  }
}
