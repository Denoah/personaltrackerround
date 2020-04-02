package io.fabric.sdk.android.services.settings;

public enum SettingsCacheBehavior
{
  static
  {
    SKIP_CACHE_LOOKUP = new SettingsCacheBehavior("SKIP_CACHE_LOOKUP", 1);
    SettingsCacheBehavior localSettingsCacheBehavior = new SettingsCacheBehavior("IGNORE_CACHE_EXPIRATION", 2);
    IGNORE_CACHE_EXPIRATION = localSettingsCacheBehavior;
    $VALUES = new SettingsCacheBehavior[] { USE_CACHE, SKIP_CACHE_LOOKUP, localSettingsCacheBehavior };
  }
  
  private SettingsCacheBehavior() {}
}
