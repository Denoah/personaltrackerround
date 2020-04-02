package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

public abstract interface SettingsJsonTransform
{
  public abstract SettingsData buildFromJson(CurrentTimeProvider paramCurrentTimeProvider, JSONObject paramJSONObject)
    throws JSONException;
  
  public abstract JSONObject toJson(SettingsData paramSettingsData)
    throws JSONException;
}
