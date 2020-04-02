package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
  protected final Context context;
  final ScheduledExecutorService executorService;
  protected final EventsFilesManager<T> filesManager;
  volatile int rolloverIntervalSeconds = -1;
  final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef;
  
  public EnabledEventsStrategy(Context paramContext, ScheduledExecutorService paramScheduledExecutorService, EventsFilesManager<T> paramEventsFilesManager)
  {
    this.context = paramContext;
    this.executorService = paramScheduledExecutorService;
    this.filesManager = paramEventsFilesManager;
    this.scheduledRolloverFutureRef = new AtomicReference();
  }
  
  public void cancelTimeBasedFileRollOver()
  {
    if (this.scheduledRolloverFutureRef.get() != null)
    {
      CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
      ((ScheduledFuture)this.scheduledRolloverFutureRef.get()).cancel(false);
      this.scheduledRolloverFutureRef.set(null);
    }
  }
  
  protected void configureRollover(int paramInt)
  {
    this.rolloverIntervalSeconds = paramInt;
    scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
  }
  
  public void deleteAllEvents()
  {
    this.filesManager.deleteAllEventsFiles();
  }
  
  public int getRollover()
  {
    return this.rolloverIntervalSeconds;
  }
  
  public void recordEvent(T paramT)
  {
    CommonUtils.logControlled(this.context, paramT.toString());
    try
    {
      this.filesManager.writeEvent(paramT);
    }
    catch (IOException paramT)
    {
      CommonUtils.logControlledError(this.context, "Failed to write event.", paramT);
    }
    scheduleTimeBasedRollOverIfNeeded();
  }
  
  public boolean rollFileOver()
  {
    try
    {
      boolean bool = this.filesManager.rollFileOver();
      return bool;
    }
    catch (IOException localIOException)
    {
      CommonUtils.logControlledError(this.context, "Failed to roll file over.", localIOException);
    }
    return false;
  }
  
  void scheduleTimeBasedFileRollOver(long paramLong1, long paramLong2)
  {
    int i;
    if (this.scheduledRolloverFutureRef.get() == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      TimeBasedFileRollOverRunnable localTimeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
      Context localContext = this.context;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Scheduling time based file roll over every ");
      localStringBuilder.append(paramLong2);
      localStringBuilder.append(" seconds");
      CommonUtils.logControlled(localContext, localStringBuilder.toString());
      try
      {
        this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(localTimeBasedFileRollOverRunnable, paramLong1, paramLong2, TimeUnit.SECONDS));
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", localRejectedExecutionException);
      }
    }
  }
  
  public void scheduleTimeBasedRollOverIfNeeded()
  {
    int i;
    if (this.rolloverIntervalSeconds != -1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
    }
  }
  
  void sendAndCleanUpIfSuccess()
  {
    FilesSender localFilesSender = getFilesSender();
    if (localFilesSender == null)
    {
      CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
      return;
    }
    CommonUtils.logControlled(this.context, "Sending all files");
    Object localObject = this.filesManager.getBatchOfFilesToSend();
    int i = 0;
    for (;;)
    {
      int j = i;
      int k = i;
      try
      {
        if (((List)localObject).size() > 0)
        {
          j = i;
          CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[] { Integer.valueOf(((List)localObject).size()) }));
          j = i;
          boolean bool = localFilesSender.send((List)localObject);
          k = i;
          if (bool)
          {
            j = i;
            k = i + ((List)localObject).size();
            j = k;
            this.filesManager.deleteSentFiles((List)localObject);
          }
          if (bool)
          {
            j = k;
            localObject = this.filesManager.getBatchOfFilesToSend();
            i = k;
          }
        }
      }
      catch (Exception localException)
      {
        localObject = this.context;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Failed to send batch of analytics files to server: ");
        localStringBuilder.append(localException.getMessage());
        CommonUtils.logControlledError((Context)localObject, localStringBuilder.toString(), localException);
        k = j;
        if (k == 0) {
          this.filesManager.deleteOldestInRollOverIfOverMax();
        }
      }
    }
  }
  
  public void sendEvents()
  {
    sendAndCleanUpIfSuccess();
  }
}
