package io.fabric.sdk.android.services.settings;

public class BetaSettingsData
{
  public final int updateSuspendDurationSeconds;
  public final String updateUrl;
  
  public BetaSettingsData(String paramString, int paramInt)
  {
    this.updateUrl = paramString;
    this.updateSuspendDurationSeconds = paramInt;
  }
}
