package io.fabric.sdk.android.services.common;

import java.util.Map;

public abstract interface DeviceIdentifierProvider
{
  public abstract Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers();
}
