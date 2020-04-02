package androidx.work.impl.utils;

import androidx.work.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class WorkTimer
{
  private static final String TAG = Logger.tagWithPrefix("WorkTimer");
  private final ThreadFactory mBackgroundThreadFactory = new ThreadFactory()
  {
    private int mThreadsCreated = 0;
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      Thread localThread = Executors.defaultThreadFactory().newThread(paramAnonymousRunnable);
      paramAnonymousRunnable = new StringBuilder();
      paramAnonymousRunnable.append("WorkManager-WorkTimer-thread-");
      paramAnonymousRunnable.append(this.mThreadsCreated);
      localThread.setName(paramAnonymousRunnable.toString());
      this.mThreadsCreated += 1;
      return localThread;
    }
  };
  private final ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor(this.mBackgroundThreadFactory);
  final Map<String, TimeLimitExceededListener> mListeners = new HashMap();
  final Object mLock = new Object();
  final Map<String, WorkTimerRunnable> mTimerMap = new HashMap();
  
  public WorkTimer() {}
  
  public ScheduledExecutorService getExecutorService()
  {
    return this.mExecutorService;
  }
  
  public Map<String, TimeLimitExceededListener> getListeners()
  {
    try
    {
      Map localMap = this.mListeners;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public Map<String, WorkTimerRunnable> getTimerMap()
  {
    try
    {
      Map localMap = this.mTimerMap;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onDestroy()
  {
    if (!this.mExecutorService.isShutdown()) {
      this.mExecutorService.shutdownNow();
    }
  }
  
  public void startTimer(String paramString, long paramLong, TimeLimitExceededListener paramTimeLimitExceededListener)
  {
    synchronized (this.mLock)
    {
      Logger.get().debug(TAG, String.format("Starting timer for %s", new Object[] { paramString }), new Throwable[0]);
      stopTimer(paramString);
      WorkTimerRunnable localWorkTimerRunnable = new androidx/work/impl/utils/WorkTimer$WorkTimerRunnable;
      localWorkTimerRunnable.<init>(this, paramString);
      this.mTimerMap.put(paramString, localWorkTimerRunnable);
      this.mListeners.put(paramString, paramTimeLimitExceededListener);
      this.mExecutorService.schedule(localWorkTimerRunnable, paramLong, TimeUnit.MILLISECONDS);
      return;
    }
  }
  
  public void stopTimer(String paramString)
  {
    synchronized (this.mLock)
    {
      if ((WorkTimerRunnable)this.mTimerMap.remove(paramString) != null)
      {
        Logger.get().debug(TAG, String.format("Stopping timer for %s", new Object[] { paramString }), new Throwable[0]);
        this.mListeners.remove(paramString);
      }
      return;
    }
  }
  
  public static abstract interface TimeLimitExceededListener
  {
    public abstract void onTimeLimitExceeded(String paramString);
  }
  
  public static class WorkTimerRunnable
    implements Runnable
  {
    static final String TAG = "WrkTimerRunnable";
    private final String mWorkSpecId;
    private final WorkTimer mWorkTimer;
    
    WorkTimerRunnable(WorkTimer paramWorkTimer, String paramString)
    {
      this.mWorkTimer = paramWorkTimer;
      this.mWorkSpecId = paramString;
    }
    
    public void run()
    {
      synchronized (this.mWorkTimer.mLock)
      {
        if ((WorkTimerRunnable)this.mWorkTimer.mTimerMap.remove(this.mWorkSpecId) != null)
        {
          WorkTimer.TimeLimitExceededListener localTimeLimitExceededListener = (WorkTimer.TimeLimitExceededListener)this.mWorkTimer.mListeners.remove(this.mWorkSpecId);
          if (localTimeLimitExceededListener != null) {
            localTimeLimitExceededListener.onTimeLimitExceeded(this.mWorkSpecId);
          }
        }
        else
        {
          Logger.get().debug("WrkTimerRunnable", String.format("Timer with %s is already marked as complete.", new Object[] { this.mWorkSpecId }), new Throwable[0]);
        }
        return;
      }
    }
  }
}
