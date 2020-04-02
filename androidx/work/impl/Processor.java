package androidx.work.impl;

import android.content.Context;
import android.os.PowerManager.WakeLock;
import androidx.core.content.ContextCompat;
import androidx.work.Configuration;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.foreground.SystemForegroundService;
import androidx.work.impl.utils.SerialExecutor;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Processor
  implements ExecutionListener, ForegroundProcessor
{
  private static final String FOREGROUND_WAKELOCK_TAG = "ProcessorForegroundLck";
  private static final String TAG = Logger.tagWithPrefix("Processor");
  private Context mAppContext;
  private Set<String> mCancelledIds;
  private Configuration mConfiguration;
  private Map<String, WorkerWrapper> mEnqueuedWorkMap;
  private PowerManager.WakeLock mForegroundLock;
  private Map<String, WorkerWrapper> mForegroundWorkMap;
  private final Object mLock;
  private final List<ExecutionListener> mOuterListeners;
  private List<Scheduler> mSchedulers;
  private WorkDatabase mWorkDatabase;
  private TaskExecutor mWorkTaskExecutor;
  
  public Processor(Context paramContext, Configuration paramConfiguration, TaskExecutor paramTaskExecutor, WorkDatabase paramWorkDatabase, List<Scheduler> paramList)
  {
    this.mAppContext = paramContext;
    this.mConfiguration = paramConfiguration;
    this.mWorkTaskExecutor = paramTaskExecutor;
    this.mWorkDatabase = paramWorkDatabase;
    this.mEnqueuedWorkMap = new HashMap();
    this.mForegroundWorkMap = new HashMap();
    this.mSchedulers = paramList;
    this.mCancelledIds = new HashSet();
    this.mOuterListeners = new ArrayList();
    this.mForegroundLock = null;
    this.mLock = new Object();
  }
  
  private static boolean interrupt(String paramString, WorkerWrapper paramWorkerWrapper)
  {
    if (paramWorkerWrapper != null)
    {
      paramWorkerWrapper.interrupt();
      Logger.get().debug(TAG, String.format("WorkerWrapper interrupted for %s", new Object[] { paramString }), new Throwable[0]);
      return true;
    }
    Logger.get().debug(TAG, String.format("WorkerWrapper could not be found for %s", new Object[] { paramString }), new Throwable[0]);
    return false;
  }
  
  private void stopForegroundService()
  {
    synchronized (this.mLock)
    {
      if (!(this.mForegroundWorkMap.isEmpty() ^ true))
      {
        SystemForegroundService localSystemForegroundService = SystemForegroundService.getInstance();
        if (localSystemForegroundService != null)
        {
          Logger.get().debug(TAG, "No more foreground work. Stopping SystemForegroundService", new Throwable[0]);
          localSystemForegroundService.stopForegroundService();
        }
        else
        {
          Logger.get().debug(TAG, "No more foreground work. SystemForegroundService is already stopped", new Throwable[0]);
        }
        if (this.mForegroundLock != null)
        {
          this.mForegroundLock.release();
          this.mForegroundLock = null;
        }
      }
      return;
    }
  }
  
  public void addExecutionListener(ExecutionListener paramExecutionListener)
  {
    synchronized (this.mLock)
    {
      this.mOuterListeners.add(paramExecutionListener);
      return;
    }
  }
  
  public boolean hasWork()
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if ((this.mEnqueuedWorkMap.isEmpty()) && (this.mForegroundWorkMap.isEmpty())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  public boolean isCancelled(String paramString)
  {
    synchronized (this.mLock)
    {
      boolean bool = this.mCancelledIds.contains(paramString);
      return bool;
    }
  }
  
  public boolean isEnqueued(String paramString)
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if ((!this.mEnqueuedWorkMap.containsKey(paramString)) && (!this.mForegroundWorkMap.containsKey(paramString))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      this.mEnqueuedWorkMap.remove(paramString);
      Logger.get().debug(TAG, String.format("%s %s executed; reschedule = %s", new Object[] { getClass().getSimpleName(), paramString, Boolean.valueOf(paramBoolean) }), new Throwable[0]);
      Iterator localIterator = this.mOuterListeners.iterator();
      while (localIterator.hasNext()) {
        ((ExecutionListener)localIterator.next()).onExecuted(paramString, paramBoolean);
      }
      return;
    }
  }
  
  public void removeExecutionListener(ExecutionListener paramExecutionListener)
  {
    synchronized (this.mLock)
    {
      this.mOuterListeners.remove(paramExecutionListener);
      return;
    }
  }
  
  public void startForeground(String paramString, ForegroundInfo paramForegroundInfo)
  {
    synchronized (this.mLock)
    {
      Logger.get().info(TAG, String.format("Moving WorkSpec (%s) to the foreground", new Object[] { paramString }), new Throwable[0]);
      WorkerWrapper localWorkerWrapper = (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString);
      if (localWorkerWrapper != null)
      {
        if (this.mForegroundLock == null)
        {
          PowerManager.WakeLock localWakeLock = WakeLocks.newWakeLock(this.mAppContext, "ProcessorForegroundLck");
          this.mForegroundLock = localWakeLock;
          localWakeLock.acquire();
        }
        this.mForegroundWorkMap.put(paramString, localWorkerWrapper);
        paramString = SystemForegroundDispatcher.createStartForegroundIntent(this.mAppContext, paramString, paramForegroundInfo);
        ContextCompat.startForegroundService(this.mAppContext, paramString);
      }
      return;
    }
  }
  
  public boolean startWork(String paramString)
  {
    return startWork(paramString, null);
  }
  
  public boolean startWork(String paramString, WorkerParameters.RuntimeExtras paramRuntimeExtras)
  {
    synchronized (this.mLock)
    {
      if (this.mEnqueuedWorkMap.containsKey(paramString))
      {
        Logger.get().debug(TAG, String.format("Work %s is already enqueued for processing", new Object[] { paramString }), new Throwable[0]);
        return false;
      }
      Object localObject2 = new androidx/work/impl/WorkerWrapper$Builder;
      ((WorkerWrapper.Builder)localObject2).<init>(this.mAppContext, this.mConfiguration, this.mWorkTaskExecutor, this, this.mWorkDatabase, paramString);
      WorkerWrapper localWorkerWrapper = ((WorkerWrapper.Builder)localObject2).withSchedulers(this.mSchedulers).withRuntimeExtras(paramRuntimeExtras).build();
      localObject2 = localWorkerWrapper.getFuture();
      paramRuntimeExtras = new androidx/work/impl/Processor$FutureListener;
      paramRuntimeExtras.<init>(this, paramString, (ListenableFuture)localObject2);
      ((ListenableFuture)localObject2).addListener(paramRuntimeExtras, this.mWorkTaskExecutor.getMainThreadExecutor());
      this.mEnqueuedWorkMap.put(paramString, localWorkerWrapper);
      this.mWorkTaskExecutor.getBackgroundExecutor().execute(localWorkerWrapper);
      Logger.get().debug(TAG, String.format("%s: processing %s", new Object[] { getClass().getSimpleName(), paramString }), new Throwable[0]);
      return true;
    }
  }
  
  public boolean stopAndCancelWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Object localObject2 = Logger.get();
      Object localObject3 = TAG;
      int i = 1;
      ((Logger)localObject2).debug((String)localObject3, String.format("Processor cancelling %s", new Object[] { paramString }), new Throwable[0]);
      this.mCancelledIds.add(paramString);
      localObject2 = (WorkerWrapper)this.mForegroundWorkMap.remove(paramString);
      if (localObject2 == null) {
        i = 0;
      }
      localObject3 = localObject2;
      if (localObject2 == null) {
        localObject3 = (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString);
      }
      boolean bool = interrupt(paramString, (WorkerWrapper)localObject3);
      if (i != 0) {
        stopForegroundService();
      }
      return bool;
    }
  }
  
  public void stopForeground(String paramString)
  {
    synchronized (this.mLock)
    {
      this.mForegroundWorkMap.remove(paramString);
      stopForegroundService();
      return;
    }
  }
  
  public boolean stopForegroundWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Logger.get().debug(TAG, String.format("Processor stopping foreground work %s", new Object[] { paramString }), new Throwable[0]);
      boolean bool = interrupt(paramString, (WorkerWrapper)this.mForegroundWorkMap.remove(paramString));
      return bool;
    }
  }
  
  public boolean stopWork(String paramString)
  {
    synchronized (this.mLock)
    {
      Logger.get().debug(TAG, String.format("Processor stopping background work %s", new Object[] { paramString }), new Throwable[0]);
      boolean bool = interrupt(paramString, (WorkerWrapper)this.mEnqueuedWorkMap.remove(paramString));
      return bool;
    }
  }
  
  private static class FutureListener
    implements Runnable
  {
    private ExecutionListener mExecutionListener;
    private ListenableFuture<Boolean> mFuture;
    private String mWorkSpecId;
    
    FutureListener(ExecutionListener paramExecutionListener, String paramString, ListenableFuture<Boolean> paramListenableFuture)
    {
      this.mExecutionListener = paramExecutionListener;
      this.mWorkSpecId = paramString;
      this.mFuture = paramListenableFuture;
    }
    
    public void run()
    {
      boolean bool;
      try
      {
        bool = ((Boolean)this.mFuture.get()).booleanValue();
      }
      catch (InterruptedException|ExecutionException localInterruptedException)
      {
        bool = true;
      }
      this.mExecutionListener.onExecuted(this.mWorkSpecId, bool);
    }
  }
}
