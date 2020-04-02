package androidx.work.impl.foreground;

import androidx.work.ForegroundInfo;

public abstract interface ForegroundProcessor
{
  public abstract void startForeground(String paramString, ForegroundInfo paramForegroundInfo);
  
  public abstract void stopForeground(String paramString);
}
