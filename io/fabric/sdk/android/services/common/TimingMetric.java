package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

public class TimingMetric
{
  private final boolean disabled;
  private long duration;
  private final String eventName;
  private long start;
  private final String tag;
  
  public TimingMetric(String paramString1, String paramString2)
  {
    this.eventName = paramString1;
    this.tag = paramString2;
    this.disabled = (Log.isLoggable(paramString2, 2) ^ true);
  }
  
  private void reportToLog()
  {
    String str = this.tag;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.eventName);
    localStringBuilder.append(": ");
    localStringBuilder.append(this.duration);
    localStringBuilder.append("ms");
    Log.v(str, localStringBuilder.toString());
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  public void startMeasuring()
  {
    try
    {
      boolean bool = this.disabled;
      if (bool) {
        return;
      }
      this.start = SystemClock.elapsedRealtime();
      this.duration = 0L;
      return;
    }
    finally {}
  }
  
  public void stopMeasuring()
  {
    try
    {
      boolean bool = this.disabled;
      if (bool) {
        return;
      }
      long l = this.duration;
      if (l != 0L) {
        return;
      }
      this.duration = (SystemClock.elapsedRealtime() - this.start);
      reportToLog();
      return;
    }
    finally {}
  }
}
