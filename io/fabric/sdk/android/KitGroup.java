package io.fabric.sdk.android;

import java.util.Collection;

public abstract interface KitGroup
{
  public abstract Collection<? extends Kit> getKits();
}
