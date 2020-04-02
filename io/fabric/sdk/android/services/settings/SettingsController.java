package io.fabric.sdk.android.services.settings;

public abstract interface SettingsController
{
  public abstract SettingsData loadSettingsData();
  
  public abstract SettingsData loadSettingsData(SettingsCacheBehavior paramSettingsCacheBehavior);
}
