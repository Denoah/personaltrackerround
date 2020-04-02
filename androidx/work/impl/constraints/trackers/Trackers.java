package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class Trackers
{
  private static Trackers sInstance;
  private BatteryChargingTracker mBatteryChargingTracker;
  private BatteryNotLowTracker mBatteryNotLowTracker;
  private NetworkStateTracker mNetworkStateTracker;
  private StorageNotLowTracker mStorageNotLowTracker;
  
  private Trackers(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    paramContext = paramContext.getApplicationContext();
    this.mBatteryChargingTracker = new BatteryChargingTracker(paramContext, paramTaskExecutor);
    this.mBatteryNotLowTracker = new BatteryNotLowTracker(paramContext, paramTaskExecutor);
    this.mNetworkStateTracker = new NetworkStateTracker(paramContext, paramTaskExecutor);
    this.mStorageNotLowTracker = new StorageNotLowTracker(paramContext, paramTaskExecutor);
  }
  
  public static Trackers getInstance(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    try
    {
      if (sInstance == null)
      {
        Trackers localTrackers = new androidx/work/impl/constraints/trackers/Trackers;
        localTrackers.<init>(paramContext, paramTaskExecutor);
        sInstance = localTrackers;
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public static void setInstance(Trackers paramTrackers)
  {
    try
    {
      sInstance = paramTrackers;
      return;
    }
    finally
    {
      paramTrackers = finally;
      throw paramTrackers;
    }
  }
  
  public BatteryChargingTracker getBatteryChargingTracker()
  {
    return this.mBatteryChargingTracker;
  }
  
  public BatteryNotLowTracker getBatteryNotLowTracker()
  {
    return this.mBatteryNotLowTracker;
  }
  
  public NetworkStateTracker getNetworkStateTracker()
  {
    return this.mNetworkStateTracker;
  }
  
  public StorageNotLowTracker getStorageNotLowTracker()
  {
    return this.mStorageNotLowTracker;
  }
}
