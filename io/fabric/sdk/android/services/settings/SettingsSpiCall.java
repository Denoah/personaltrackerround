package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public abstract interface SettingsSpiCall
{
  public abstract JSONObject invoke(SettingsRequest paramSettingsRequest);
}
