package androidx.work.impl.constraints.controllers;

import android.content.Context;
import androidx.work.Constraints;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class BatteryNotLowController
  extends ConstraintController<Boolean>
{
  public BatteryNotLowController(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(Trackers.getInstance(paramContext, paramTaskExecutor).getBatteryNotLowTracker());
  }
  
  boolean hasConstraint(WorkSpec paramWorkSpec)
  {
    return paramWorkSpec.constraints.requiresBatteryNotLow();
  }
  
  boolean isConstrained(Boolean paramBoolean)
  {
    return paramBoolean.booleanValue() ^ true;
  }
}
