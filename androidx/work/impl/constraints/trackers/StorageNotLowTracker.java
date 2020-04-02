package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class StorageNotLowTracker
  extends BroadcastReceiverConstraintTracker<Boolean>
{
  private static final String TAG = Logger.tagWithPrefix("StorageNotLowTracker");
  
  public StorageNotLowTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
  }
  
  public Boolean getInitialState()
  {
    Object localObject = this.mAppContext.registerReceiver(null, getIntentFilter());
    if ((localObject != null) && (((Intent)localObject).getAction() != null))
    {
      localObject = ((Intent)localObject).getAction();
      int i = -1;
      int j = ((String)localObject).hashCode();
      if (j != -1181163412)
      {
        if ((j == -730838620) && (((String)localObject).equals("android.intent.action.DEVICE_STORAGE_OK"))) {
          i = 0;
        }
      }
      else if (((String)localObject).equals("android.intent.action.DEVICE_STORAGE_LOW")) {
        i = 1;
      }
      if (i != 0)
      {
        if (i != 1) {
          return null;
        }
        return Boolean.valueOf(false);
      }
    }
    return Boolean.valueOf(true);
  }
  
  public IntentFilter getIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
    localIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
    return localIntentFilter;
  }
  
  public void onBroadcastReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction() == null) {
      return;
    }
    Logger.get().debug(TAG, String.format("Received %s", new Object[] { paramIntent.getAction() }), new Throwable[0]);
    paramContext = paramIntent.getAction();
    int i = -1;
    int j = paramContext.hashCode();
    if (j != -1181163412)
    {
      if ((j == -730838620) && (paramContext.equals("android.intent.action.DEVICE_STORAGE_OK"))) {
        i = 0;
      }
    }
    else if (paramContext.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
      i = 1;
    }
    if (i != 0)
    {
      if (i == 1) {
        setState(Boolean.valueOf(false));
      }
    }
    else {
      setState(Boolean.valueOf(true));
    }
  }
}
