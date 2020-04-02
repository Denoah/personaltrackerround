package io.fabric.sdk.android.services.common;

import android.os.Process;

public abstract class BackgroundPriorityRunnable
  implements Runnable
{
  public BackgroundPriorityRunnable() {}
  
  protected abstract void onRun();
  
  public final void run()
  {
    Process.setThreadPriority(10);
    onRun();
  }
}
