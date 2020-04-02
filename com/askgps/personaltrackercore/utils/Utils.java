package com.askgps.personaltrackercore.utils;

import android.content.Context;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\002\n\002\b\006\b&\030\0002\0020\001B\005?\006\002\020\002J\b\020\r\032\0020\016H&J\b\020\017\032\0020\016H&J\b\020\020\032\0020\016H&J\b\020\021\032\0020\016H&J\b\020\022\032\0020\016H\026J\b\020\023\032\0020\016H\026R\022\020\003\032\0020\004X¦\004?\006\006\032\004\b\005\020\006R\032\020\007\032\0020\bX?\016?\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\f?\006\024"}, d2={"Lcom/askgps/personaltrackercore/utils/Utils;", "", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "listenerIsStart", "", "getListenerIsStart", "()Z", "setListenerIsStart", "(Z)V", "onCreate", "", "onDestroy", "onPause", "onResume", "registerSensorListener", "unregisterSensorListener", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract class Utils
{
  private volatile boolean listenerIsStart;
  
  public Utils() {}
  
  public abstract Context getContext();
  
  protected final boolean getListenerIsStart()
  {
    return this.listenerIsStart;
  }
  
  public abstract void onCreate();
  
  public abstract void onDestroy();
  
  public abstract void onPause();
  
  public abstract void onResume();
  
  public void registerSensorListener() {}
  
  protected final void setListenerIsStart(boolean paramBoolean)
  {
    this.listenerIsStart = paramBoolean;
  }
  
  public void unregisterSensorListener() {}
}
