package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public abstract interface CachedSettingsIo
{
  public abstract JSONObject readCachedSettings();
  
  public abstract void writeCachedSettings(long paramLong, JSONObject paramJSONObject);
}
