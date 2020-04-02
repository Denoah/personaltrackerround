package androidx.work.impl.background.systemalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.utils.IdGenerator;

class Alarms
{
  private static final String TAG = Logger.tagWithPrefix("Alarms");
  
  private Alarms() {}
  
  public static void cancelAlarm(Context paramContext, WorkManagerImpl paramWorkManagerImpl, String paramString)
  {
    SystemIdInfoDao localSystemIdInfoDao = paramWorkManagerImpl.getWorkDatabase().systemIdInfoDao();
    paramWorkManagerImpl = localSystemIdInfoDao.getSystemIdInfo(paramString);
    if (paramWorkManagerImpl != null)
    {
      cancelExactAlarm(paramContext, paramString, paramWorkManagerImpl.systemId);
      Logger.get().debug(TAG, String.format("Removing SystemIdInfo for workSpecId (%s)", new Object[] { paramString }), new Throwable[0]);
      localSystemIdInfoDao.removeSystemIdInfo(paramString);
    }
  }
  
  private static void cancelExactAlarm(Context paramContext, String paramString, int paramInt)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    paramContext = PendingIntent.getService(paramContext, paramInt, CommandHandler.createDelayMetIntent(paramContext, paramString), 536870912);
    if ((paramContext != null) && (localAlarmManager != null))
    {
      Logger.get().debug(TAG, String.format("Cancelling existing alarm with (workSpecId, systemId) (%s, %s)", new Object[] { paramString, Integer.valueOf(paramInt) }), new Throwable[0]);
      localAlarmManager.cancel(paramContext);
    }
  }
  
  public static void setAlarm(Context paramContext, WorkManagerImpl paramWorkManagerImpl, String paramString, long paramLong)
  {
    paramWorkManagerImpl = paramWorkManagerImpl.getWorkDatabase();
    SystemIdInfoDao localSystemIdInfoDao = paramWorkManagerImpl.systemIdInfoDao();
    SystemIdInfo localSystemIdInfo = localSystemIdInfoDao.getSystemIdInfo(paramString);
    if (localSystemIdInfo != null)
    {
      cancelExactAlarm(paramContext, paramString, localSystemIdInfo.systemId);
      setExactAlarm(paramContext, paramString, localSystemIdInfo.systemId, paramLong);
    }
    else
    {
      int i = new IdGenerator(paramWorkManagerImpl).nextAlarmManagerId();
      localSystemIdInfoDao.insertSystemIdInfo(new SystemIdInfo(paramString, i));
      setExactAlarm(paramContext, paramString, i, paramLong);
    }
  }
  
  private static void setExactAlarm(Context paramContext, String paramString, int paramInt, long paramLong)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    paramContext = PendingIntent.getService(paramContext, paramInt, CommandHandler.createDelayMetIntent(paramContext, paramString), 134217728);
    if (localAlarmManager != null) {
      if (Build.VERSION.SDK_INT >= 19) {
        localAlarmManager.setExact(0, paramLong, paramContext);
      } else {
        localAlarmManager.set(0, paramLong, paramContext);
      }
    }
  }
}
