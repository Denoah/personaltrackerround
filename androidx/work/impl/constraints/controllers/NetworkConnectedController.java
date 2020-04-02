package androidx.work.impl.constraints.controllers;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class NetworkConnectedController
  extends ConstraintController<NetworkState>
{
  public NetworkConnectedController(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(Trackers.getInstance(paramContext, paramTaskExecutor).getNetworkStateTracker());
  }
  
  boolean hasConstraint(WorkSpec paramWorkSpec)
  {
    boolean bool;
    if (paramWorkSpec.constraints.getRequiredNetworkType() == NetworkType.CONNECTED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isConstrained(NetworkState paramNetworkState)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = true;
    if (i >= 26)
    {
      boolean bool2 = bool1;
      if (paramNetworkState.isConnected()) {
        if (!paramNetworkState.isValidated()) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }
      }
      return bool2;
    }
    return paramNetworkState.isConnected() ^ true;
  }
}
