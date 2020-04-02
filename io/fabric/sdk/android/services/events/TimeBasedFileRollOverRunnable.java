package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;

public class TimeBasedFileRollOverRunnable
  implements Runnable
{
  private final Context context;
  private final FileRollOverManager fileRollOverManager;
  
  public TimeBasedFileRollOverRunnable(Context paramContext, FileRollOverManager paramFileRollOverManager)
  {
    this.context = paramContext;
    this.fileRollOverManager = paramFileRollOverManager;
  }
  
  public void run()
  {
    try
    {
      CommonUtils.logControlled(this.context, "Performing time based file roll over.");
      if (!this.fileRollOverManager.rollFileOver()) {
        this.fileRollOverManager.cancelTimeBasedFileRollOver();
      }
    }
    catch (Exception localException)
    {
      CommonUtils.logControlledError(this.context, "Failed to roll over file", localException);
    }
  }
}
