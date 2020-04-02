package io.fabric.sdk.android.services.events;

import java.io.IOException;

public abstract interface FileRollOverManager
{
  public abstract void cancelTimeBasedFileRollOver();
  
  public abstract boolean rollFileOver()
    throws IOException;
  
  public abstract void scheduleTimeBasedRollOverIfNeeded();
}
