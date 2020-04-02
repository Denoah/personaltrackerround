package io.fabric.sdk.android;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ActivityLifecycleManager
{
  private final Application application;
  private ActivityLifecycleCallbacksWrapper callbacksWrapper;
  
  public ActivityLifecycleManager(Context paramContext)
  {
    this.application = ((Application)paramContext.getApplicationContext());
    if (Build.VERSION.SDK_INT >= 14) {
      this.callbacksWrapper = new ActivityLifecycleCallbacksWrapper(this.application);
    }
  }
  
  public boolean registerCallbacks(Callbacks paramCallbacks)
  {
    ActivityLifecycleCallbacksWrapper localActivityLifecycleCallbacksWrapper = this.callbacksWrapper;
    boolean bool;
    if ((localActivityLifecycleCallbacksWrapper != null) && (localActivityLifecycleCallbacksWrapper.registerLifecycleCallbacks(paramCallbacks))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void resetCallbacks()
  {
    ActivityLifecycleCallbacksWrapper localActivityLifecycleCallbacksWrapper = this.callbacksWrapper;
    if (localActivityLifecycleCallbacksWrapper != null) {
      localActivityLifecycleCallbacksWrapper.clearCallbacks();
    }
  }
  
  private static class ActivityLifecycleCallbacksWrapper
  {
    private final Application application;
    private final Set<Application.ActivityLifecycleCallbacks> registeredCallbacks = new HashSet();
    
    ActivityLifecycleCallbacksWrapper(Application paramApplication)
    {
      this.application = paramApplication;
    }
    
    private void clearCallbacks()
    {
      Iterator localIterator = this.registeredCallbacks.iterator();
      while (localIterator.hasNext())
      {
        Application.ActivityLifecycleCallbacks localActivityLifecycleCallbacks = (Application.ActivityLifecycleCallbacks)localIterator.next();
        this.application.unregisterActivityLifecycleCallbacks(localActivityLifecycleCallbacks);
      }
    }
    
    private boolean registerLifecycleCallbacks(final ActivityLifecycleManager.Callbacks paramCallbacks)
    {
      if (this.application != null)
      {
        paramCallbacks = new Application.ActivityLifecycleCallbacks()
        {
          public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramCallbacks.onActivityCreated(paramAnonymousActivity, paramAnonymousBundle);
          }
          
          public void onActivityDestroyed(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityDestroyed(paramAnonymousActivity);
          }
          
          public void onActivityPaused(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityPaused(paramAnonymousActivity);
          }
          
          public void onActivityResumed(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityResumed(paramAnonymousActivity);
          }
          
          public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramCallbacks.onActivitySaveInstanceState(paramAnonymousActivity, paramAnonymousBundle);
          }
          
          public void onActivityStarted(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityStarted(paramAnonymousActivity);
          }
          
          public void onActivityStopped(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityStopped(paramAnonymousActivity);
          }
        };
        this.application.registerActivityLifecycleCallbacks(paramCallbacks);
        this.registeredCallbacks.add(paramCallbacks);
        return true;
      }
      return false;
    }
  }
  
  public static abstract class Callbacks
  {
    public Callbacks() {}
    
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity) {}
    
    public void onActivityPaused(Activity paramActivity) {}
    
    public void onActivityResumed(Activity paramActivity) {}
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity) {}
    
    public void onActivityStopped(Activity paramActivity) {}
  }
}
