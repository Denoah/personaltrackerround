package androidx.work.impl.utils;

import android.content.Context;
import android.content.Intent;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

public class WorkForegroundUpdater
  implements ForegroundUpdater
{
  final ForegroundProcessor mForegroundProcessor;
  private final TaskExecutor mTaskExecutor;
  
  public WorkForegroundUpdater(ForegroundProcessor paramForegroundProcessor, TaskExecutor paramTaskExecutor)
  {
    this.mForegroundProcessor = paramForegroundProcessor;
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public ListenableFuture<Void> setForegroundAsync(final Context paramContext, final UUID paramUUID, final ForegroundInfo paramForegroundInfo)
  {
    final SettableFuture localSettableFuture = SettableFuture.create();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        try
        {
          if (!localSettableFuture.isCancelled())
          {
            Object localObject = paramUUID.toString();
            WorkForegroundUpdater.this.mForegroundProcessor.startForeground((String)localObject, paramForegroundInfo);
            localObject = SystemForegroundDispatcher.createNotifyIntent(paramContext, (String)localObject, paramForegroundInfo);
            paramContext.startService((Intent)localObject);
          }
        }
        finally
        {
          localSettableFuture.setException(localThrowable);
        }
      }
    });
    return localSettableFuture;
  }
}
