package io.fabric.sdk.android.services.events;

public abstract interface EventsManager<T>
{
  public abstract void deleteAllEvents();
  
  public abstract void recordEvent(T paramT);
  
  public abstract void sendEvents();
}
