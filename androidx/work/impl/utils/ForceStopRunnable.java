package androidx.work.impl.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabasePathHelper;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ForceStopRunnable
  implements Runnable
{
  static final String ACTION_FORCE_STOP_RESCHEDULE = "ACTION_FORCE_STOP_RESCHEDULE";
  private static final int ALARM_ID = -1;
  private static final String TAG = Logger.tagWithPrefix("ForceStopRunnable");
  private static final long TEN_YEARS = TimeUnit.DAYS.toMillis(3650L);
  private final Context mContext;
  private final WorkManagerImpl mWorkManager;
  
  public ForceStopRunnable(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mWorkManager = paramWorkManagerImpl;
  }
  
  static Intent getIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramContext, BroadcastReceiver.class));
    localIntent.setAction("ACTION_FORCE_STOP_RESCHEDULE");
    return localIntent;
  }
  
  private static PendingIntent getPendingIntent(Context paramContext, int paramInt)
  {
    return PendingIntent.getBroadcast(paramContext, -1, getIntent(paramContext), paramInt);
  }
  
  static void setAlarm(Context paramContext)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    paramContext = getPendingIntent(paramContext, 134217728);
    long l = System.currentTimeMillis() + TEN_YEARS;
    if (localAlarmManager != null) {
      if (Build.VERSION.SDK_INT >= 19) {
        localAlarmManager.setExact(0, l, paramContext);
      } else {
        localAlarmManager.set(0, l, paramContext);
      }
    }
  }
  
  public boolean cleanUp()
  {
    if (Build.VERSION.SDK_INT >= 23) {
      SystemJobScheduler.cancelInvalidJobs(this.mContext);
    }
    WorkDatabase localWorkDatabase = this.mWorkManager.getWorkDatabase();
    WorkSpecDao localWorkSpecDao = localWorkDatabase.workSpecDao();
    WorkProgressDao localWorkProgressDao = localWorkDatabase.workProgressDao();
    localWorkDatabase.beginTransaction();
    try
    {
      Object localObject2 = localWorkSpecDao.getRunningWork();
      boolean bool;
      if ((localObject2 != null) && (!((List)localObject2).isEmpty())) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        Iterator localIterator = ((List)localObject2).iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (WorkSpec)localIterator.next();
          localWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { ((WorkSpec)localObject2).id });
          localWorkSpecDao.markWorkSpecScheduled(((WorkSpec)localObject2).id, -1L);
        }
      }
      localWorkProgressDao.deleteAll();
      localWorkDatabase.setTransactionSuccessful();
      return bool;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
  
  public boolean isForceStopped()
  {
    if (getPendingIntent(this.mContext, 536870912) == null)
    {
      setAlarm(this.mContext);
      return true;
    }
    return false;
  }
  
  public void run()
  {
    WorkDatabasePathHelper.migrateDatabase(this.mContext);
    Logger.get().debug(TAG, "Performing cleanup operations.", new Throwable[0]);
    try
    {
      boolean bool = cleanUp();
      if (shouldRescheduleWorkers())
      {
        Logger.get().debug(TAG, "Rescheduling Workers.", new Throwable[0]);
        this.mWorkManager.rescheduleEligibleWork();
        this.mWorkManager.getPreferenceUtils().setNeedsReschedule(false);
      }
      else if (isForceStopped())
      {
        Logger.get().debug(TAG, "Application was force-stopped, rescheduling.", new Throwable[0]);
        this.mWorkManager.rescheduleEligibleWork();
      }
      else if (bool)
      {
        Logger.get().debug(TAG, "Found unfinished work, scheduling it.", new Throwable[0]);
        Schedulers.schedule(this.mWorkManager.getConfiguration(), this.mWorkManager.getWorkDatabase(), this.mWorkManager.getSchedulers());
      }
      this.mWorkManager.onForceStopRunnableCompleted();
      return;
    }
    catch (SQLiteAccessPermException localSQLiteAccessPermException) {}catch (SQLiteDatabaseCorruptException localSQLiteDatabaseCorruptException) {}catch (SQLiteCantOpenDatabaseException localSQLiteCantOpenDatabaseException) {}
    Logger.get().error(TAG, "The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", new Throwable[] { localSQLiteCantOpenDatabaseException });
    throw new IllegalStateException("The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", localSQLiteCantOpenDatabaseException);
  }
  
  boolean shouldRescheduleWorkers()
  {
    return this.mWorkManager.getPreferenceUtils().getNeedsReschedule();
  }
  
  public static class BroadcastReceiver
    extends BroadcastReceiver
  {
    private static final String TAG = Logger.tagWithPrefix("ForceStopRunnable$Rcvr");
    
    public BroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && ("ACTION_FORCE_STOP_RESCHEDULE".equals(paramIntent.getAction())))
      {
        Logger.get().verbose(TAG, "Rescheduling alarm that keeps track of force-stops.", new Throwable[0]);
        ForceStopRunnable.setAlarm(paramContext);
      }
    }
  }
}
