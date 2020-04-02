package io.fabric.sdk.android;

import android.util.Log;

public class DefaultLogger
  implements Logger
{
  private int logLevel;
  
  public DefaultLogger()
  {
    this.logLevel = 4;
  }
  
  public DefaultLogger(int paramInt)
  {
    this.logLevel = paramInt;
  }
  
  public void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, null);
  }
  
  public void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isLoggable(paramString1, 3)) {
      Log.d(paramString1, paramString2, paramThrowable);
    }
  }
  
  public void e(String paramString1, String paramString2)
  {
    e(paramString1, paramString2, null);
  }
  
  public void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isLoggable(paramString1, 6)) {
      Log.e(paramString1, paramString2, paramThrowable);
    }
  }
  
  public int getLogLevel()
  {
    return this.logLevel;
  }
  
  public void i(String paramString1, String paramString2)
  {
    i(paramString1, paramString2, null);
  }
  
  public void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isLoggable(paramString1, 4)) {
      Log.i(paramString1, paramString2, paramThrowable);
    }
  }
  
  public boolean isLoggable(String paramString, int paramInt)
  {
    boolean bool;
    if ((this.logLevel > paramInt) && (!Log.isLoggable(paramString, paramInt))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void log(int paramInt, String paramString1, String paramString2)
  {
    log(paramInt, paramString1, paramString2, false);
  }
  
  public void log(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramBoolean) || (isLoggable(paramString1, paramInt))) {
      Log.println(paramInt, paramString1, paramString2);
    }
  }
  
  public void setLogLevel(int paramInt)
  {
    this.logLevel = paramInt;
  }
  
  public void v(String paramString1, String paramString2)
  {
    v(paramString1, paramString2, null);
  }
  
  public void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isLoggable(paramString1, 2)) {
      Log.v(paramString1, paramString2, paramThrowable);
    }
  }
  
  public void w(String paramString1, String paramString2)
  {
    w(paramString1, paramString2, null);
  }
  
  public void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isLoggable(paramString1, 5)) {
      Log.w(paramString1, paramString2, paramThrowable);
    }
  }
}
