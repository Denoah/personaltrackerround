package androidx.work.impl.background.systemalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;
import java.util.Iterator;
import java.util.List;

abstract class ConstraintProxy
  extends BroadcastReceiver
{
  private static final String TAG = Logger.tagWithPrefix("ConstraintProxy");
  
  ConstraintProxy() {}
  
  static void updateAll(Context paramContext, List<WorkSpec> paramList)
  {
    Iterator localIterator = paramList.iterator();
    boolean bool1 = false;
    boolean bool2 = bool1;
    boolean bool3 = bool2;
    boolean bool4 = bool3;
    boolean bool5;
    boolean bool6;
    boolean bool7;
    boolean bool8;
    do
    {
      do
      {
        do
        {
          do
          {
            bool5 = bool1;
            bool6 = bool2;
            bool7 = bool3;
            bool8 = bool4;
            if (!localIterator.hasNext()) {
              break;
            }
            paramList = ((WorkSpec)localIterator.next()).constraints;
            bool5 = bool1 | paramList.requiresBatteryNotLow();
            bool6 = bool2 | paramList.requiresCharging();
            bool7 = bool3 | paramList.requiresStorageNotLow();
            int i;
            if (paramList.getRequiredNetworkType() != NetworkType.NOT_REQUIRED) {
              i = 1;
            } else {
              i = 0;
            }
            bool8 = bool4 | i;
            bool1 = bool5;
            bool2 = bool6;
            bool3 = bool7;
            bool4 = bool8;
          } while (!bool5);
          bool1 = bool5;
          bool2 = bool6;
          bool3 = bool7;
          bool4 = bool8;
        } while (!bool6);
        bool1 = bool5;
        bool2 = bool6;
        bool3 = bool7;
        bool4 = bool8;
      } while (!bool7);
      bool1 = bool5;
      bool2 = bool6;
      bool3 = bool7;
      bool4 = bool8;
    } while (!bool8);
    paramContext.sendBroadcast(ConstraintProxyUpdateReceiver.newConstraintProxyUpdateIntent(paramContext, bool5, bool6, bool7, bool8));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Logger.get().debug(TAG, String.format("onReceive : %s", new Object[] { paramIntent }), new Throwable[0]);
    paramContext.startService(CommandHandler.createConstraintsChangedIntent(paramContext));
  }
  
  public static class BatteryChargingProxy
    extends ConstraintProxy
  {
    public BatteryChargingProxy() {}
  }
  
  public static class BatteryNotLowProxy
    extends ConstraintProxy
  {
    public BatteryNotLowProxy() {}
  }
  
  public static class NetworkStateProxy
    extends ConstraintProxy
  {
    public NetworkStateProxy() {}
  }
  
  public static class StorageNotLowProxy
    extends ConstraintProxy
  {
    public StorageNotLowProxy() {}
  }
}
