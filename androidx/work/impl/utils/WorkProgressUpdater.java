package androidx.work.impl.utils;

import android.content.Context;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.ProgressUpdater;
import androidx.work.WorkInfo.State;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.model.WorkProgress;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

public class WorkProgressUpdater
  implements ProgressUpdater
{
  static final String TAG = Logger.tagWithPrefix("WorkProgressUpdater");
  final TaskExecutor mTaskExecutor;
  final WorkDatabase mWorkDatabase;
  
  public WorkProgressUpdater(WorkDatabase paramWorkDatabase, TaskExecutor paramTaskExecutor)
  {
    this.mWorkDatabase = paramWorkDatabase;
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public ListenableFuture<Void> updateProgress(final Context paramContext, final UUID paramUUID, final Data paramData)
  {
    paramContext = SettableFuture.create();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        String str = paramUUID.toString();
        Logger.get().debug(WorkProgressUpdater.TAG, String.format("Updating progress for %s (%s)", new Object[] { paramUUID, paramData }), new Throwable[0]);
        WorkProgressUpdater.this.mWorkDatabase.beginTransaction();
        try
        {
          Object localObject2 = WorkProgressUpdater.this.mWorkDatabase.workSpecDao().getWorkSpec(str);
          if (localObject2 != null)
          {
            if (((WorkSpec)localObject2).state == WorkInfo.State.RUNNING)
            {
              localObject2 = new androidx/work/impl/model/WorkProgress;
              ((WorkProgress)localObject2).<init>(str, paramData);
              WorkProgressUpdater.this.mWorkDatabase.workProgressDao().insert((WorkProgress)localObject2);
            }
            else
            {
              Logger.get().warning(WorkProgressUpdater.TAG, String.format("Ignoring setProgressAsync(...). WorkSpec (%s) is not in a RUNNING state.", new Object[] { str }), new Throwable[0]);
            }
          }
          else {
            Logger.get().warning(WorkProgressUpdater.TAG, String.format("Ignoring setProgressAsync(...). WorkSpec (%s) does not exist.", new Object[] { str }), new Throwable[0]);
          }
          paramContext.set(null);
          WorkProgressUpdater.this.mWorkDatabase.setTransactionSuccessful();
        }
        finally {}
        try
        {
          Logger.get().error(WorkProgressUpdater.TAG, "Error updating Worker progress", new Throwable[] { localThrowable });
          paramContext.setException(localThrowable);
          return;
        }
        finally
        {
          WorkProgressUpdater.this.mWorkDatabase.endTransaction();
        }
      }
    });
    return paramContext;
  }
}
