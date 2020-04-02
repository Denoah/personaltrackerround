package androidx.work.impl.constraints.controllers;

import android.content.Context;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class NetworkUnmeteredController
  extends ConstraintController<NetworkState>
{
  public NetworkUnmeteredController(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(Trackers.getInstance(paramContext, paramTaskExecutor).getNetworkStateTracker());
  }
  
  boolean hasConstraint(WorkSpec paramWorkSpec)
  {
    boolean bool;
    if (paramWorkSpec.constraints.getRequiredNetworkType() == NetworkType.UNMETERED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isConstrained(NetworkState paramNetworkState)
  {
    boolean bool;
    if ((paramNetworkState.isConnected()) && (!paramNetworkState.isMetered())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
