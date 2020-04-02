package io.fabric.sdk.android.services.events;

public class DisabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  public DisabledEventsStrategy() {}
  
  public void cancelTimeBasedFileRollOver() {}
  
  public void deleteAllEvents() {}
  
  public FilesSender getFilesSender()
  {
    return null;
  }
  
  public void recordEvent(T paramT) {}
  
  public boolean rollFileOver()
  {
    return false;
  }
  
  public void scheduleTimeBasedRollOverIfNeeded() {}
  
  public void sendEvents() {}
}
