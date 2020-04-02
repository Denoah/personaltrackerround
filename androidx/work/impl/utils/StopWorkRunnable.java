package androidx.work.impl.utils;

import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpecDao;

public class StopWorkRunnable
  implements Runnable
{
  private static final String TAG = Logger.tagWithPrefix("StopWorkRunnable");
  private final boolean mStopInForeground;
  private final WorkManagerImpl mWorkManagerImpl;
  private final String mWorkSpecId;
  
  public StopWorkRunnable(WorkManagerImpl paramWorkManagerImpl, String paramString, boolean paramBoolean)
  {
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkSpecId = paramString;
    this.mStopInForeground = paramBoolean;
  }
  
  public void run()
  {
    WorkDatabase localWorkDatabase = this.mWorkManagerImpl.getWorkDatabase();
    WorkSpecDao localWorkSpecDao = localWorkDatabase.workSpecDao();
    localWorkDatabase.beginTransaction();
    try
    {
      if (localWorkSpecDao.getState(this.mWorkSpecId) == WorkInfo.State.RUNNING) {
        localWorkSpecDao.setState(WorkInfo.State.ENQUEUED, new String[] { this.mWorkSpecId });
      }
      boolean bool;
      if (this.mStopInForeground) {
        bool = this.mWorkManagerImpl.getProcessor().stopForegroundWork(this.mWorkSpecId);
      } else {
        bool = this.mWorkManagerImpl.getProcessor().stopWork(this.mWorkSpecId);
      }
      Logger.get().debug(TAG, String.format("StopWorkRunnable for %s; Processor.stopWork = %s", new Object[] { this.mWorkSpecId, Boolean.valueOf(bool) }), new Throwable[0]);
      localWorkDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
}
