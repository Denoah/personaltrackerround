package androidx.work.impl;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmScheduler;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.background.systemjob.SystemJobService;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.PackageManagerHelper;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

public class Schedulers
{
  public static final String GCM_SCHEDULER = "androidx.work.impl.background.gcm.GcmScheduler";
  private static final String TAG = Logger.tagWithPrefix("Schedulers");
  
  private Schedulers() {}
  
  static Scheduler createBestAvailableBackgroundScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramWorkManagerImpl = new SystemJobScheduler(paramContext, paramWorkManagerImpl);
      PackageManagerHelper.setComponentEnabled(paramContext, SystemJobService.class, true);
      Logger.get().debug(TAG, "Created SystemJobScheduler and enabled SystemJobService", new Throwable[0]);
    }
    else
    {
      Scheduler localScheduler = tryCreateGcmBasedScheduler(paramContext);
      paramWorkManagerImpl = localScheduler;
      if (localScheduler == null)
      {
        paramWorkManagerImpl = new SystemAlarmScheduler(paramContext);
        PackageManagerHelper.setComponentEnabled(paramContext, SystemAlarmService.class, true);
        Logger.get().debug(TAG, "Created SystemAlarmScheduler", new Throwable[0]);
      }
    }
    return paramWorkManagerImpl;
  }
  
  public static void schedule(Configuration paramConfiguration, WorkDatabase paramWorkDatabase, List<Scheduler> paramList)
  {
    if ((paramList != null) && (paramList.size() != 0))
    {
      WorkSpecDao localWorkSpecDao = paramWorkDatabase.workSpecDao();
      paramWorkDatabase.beginTransaction();
      try
      {
        paramConfiguration = localWorkSpecDao.getEligibleWorkForScheduling(paramConfiguration.getMaxSchedulerLimit());
        if ((paramConfiguration != null) && (paramConfiguration.size() > 0))
        {
          long l = System.currentTimeMillis();
          Iterator localIterator = paramConfiguration.iterator();
          while (localIterator.hasNext()) {
            localWorkSpecDao.markWorkSpecScheduled(((WorkSpec)localIterator.next()).id, l);
          }
        }
        paramWorkDatabase.setTransactionSuccessful();
        paramWorkDatabase.endTransaction();
        if ((paramConfiguration != null) && (paramConfiguration.size() > 0))
        {
          paramConfiguration = (WorkSpec[])paramConfiguration.toArray(new WorkSpec[0]);
          paramWorkDatabase = paramList.iterator();
          while (paramWorkDatabase.hasNext()) {
            ((Scheduler)paramWorkDatabase.next()).schedule(paramConfiguration);
          }
        }
        return;
      }
      finally
      {
        paramWorkDatabase.endTransaction();
      }
    }
  }
  
  private static Scheduler tryCreateGcmBasedScheduler(Context paramContext)
  {
    try
    {
      paramContext = (Scheduler)Class.forName("androidx.work.impl.background.gcm.GcmScheduler").getConstructor(new Class[] { Context.class }).newInstance(new Object[] { paramContext });
      Logger.get().debug(TAG, String.format("Created %s", new Object[] { "androidx.work.impl.background.gcm.GcmScheduler" }), new Throwable[0]);
      return paramContext;
    }
    finally
    {
      Logger.get().debug(TAG, "Unable to create GCM Scheduler", new Throwable[] { paramContext });
    }
    return null;
  }
}
