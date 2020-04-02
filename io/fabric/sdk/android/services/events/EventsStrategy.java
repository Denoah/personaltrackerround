package io.fabric.sdk.android.services.events;

public abstract interface EventsStrategy<T>
  extends FileRollOverManager, EventsManager<T>
{
  public abstract FilesSender getFilesSender();
}
