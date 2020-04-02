package io.fabric.sdk.android.services.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Kit;

public class PreferenceStoreImpl
  implements PreferenceStore
{
  private final Context context;
  private final String preferenceName;
  private final SharedPreferences sharedPreferences;
  
  public PreferenceStoreImpl(Context paramContext, String paramString)
  {
    if (paramContext != null)
    {
      this.context = paramContext;
      this.preferenceName = paramString;
      this.sharedPreferences = paramContext.getSharedPreferences(paramString, 0);
      return;
    }
    throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
  }
  
  @Deprecated
  public PreferenceStoreImpl(Kit paramKit)
  {
    this(paramKit.getContext(), paramKit.getClass().getName());
  }
  
  public SharedPreferences.Editor edit()
  {
    return this.sharedPreferences.edit();
  }
  
  public SharedPreferences get()
  {
    return this.sharedPreferences;
  }
  
  public boolean save(SharedPreferences.Editor paramEditor)
  {
    if (Build.VERSION.SDK_INT >= 9)
    {
      paramEditor.apply();
      return true;
    }
    return paramEditor.commit();
  }
}
