package androidx.work.impl.background.greedy;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import androidx.work.Constraints;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GreedyScheduler
  implements Scheduler, WorkConstraintsCallback, ExecutionListener
{
  private static final String TAG = Logger.tagWithPrefix("GreedyScheduler");
  private List<WorkSpec> mConstrainedWorkSpecs = new ArrayList();
  private final Context mContext;
  private Boolean mIsMainProcess;
  private final Object mLock;
  private boolean mRegisteredExecutionListener;
  private final WorkConstraintsTracker mWorkConstraintsTracker;
  private final WorkManagerImpl mWorkManagerImpl;
  
  public GreedyScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl, WorkConstraintsTracker paramWorkConstraintsTracker)
  {
    this.mContext = paramContext;
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkConstraintsTracker = paramWorkConstraintsTracker;
    this.mLock = new Object();
  }
  
  public GreedyScheduler(Context paramContext, TaskExecutor paramTaskExecutor, WorkManagerImpl paramWorkManagerImpl)
  {
    this.mContext = paramContext;
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mWorkConstraintsTracker = new WorkConstraintsTracker(paramContext, paramTaskExecutor, this);
    this.mLock = new Object();
  }
  
  private String getProcessName()
  {
    int i = Process.myPid();
    Object localObject = (ActivityManager)this.mContext.getSystemService("activity");
    if (localObject != null)
    {
      localObject = ((ActivityManager)localObject).getRunningAppProcesses();
      if ((localObject != null) && (!((List)localObject).isEmpty()))
      {
        Iterator localIterator = ((List)localObject).iterator();
        while (localIterator.hasNext())
        {
          localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
          if (((ActivityManager.RunningAppProcessInfo)localObject).pid == i) {
            return ((ActivityManager.RunningAppProcessInfo)localObject).processName;
          }
        }
      }
    }
    return null;
  }
  
  private void registerExecutionListenerIfNeeded()
  {
    if (!this.mRegisteredExecutionListener)
    {
      this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
      this.mRegisteredExecutionListener = true;
    }
  }
  
  private void removeConstraintTrackingFor(String paramString)
  {
    synchronized (this.mLock)
    {
      int i = this.mConstrainedWorkSpecs.size();
      for (int j = 0; j < i; j++) {
        if (((WorkSpec)this.mConstrainedWorkSpecs.get(j)).id.equals(paramString))
        {
          Logger.get().debug(TAG, String.format("Stopping tracking for %s", new Object[] { paramString }), new Throwable[0]);
          this.mConstrainedWorkSpecs.remove(j);
          this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
          break;
        }
      }
      return;
    }
  }
  
  public void cancel(String paramString)
  {
    if (this.mIsMainProcess == null) {
      this.mIsMainProcess = Boolean.valueOf(TextUtils.equals(this.mContext.getPackageName(), getProcessName()));
    }
    if (!this.mIsMainProcess.booleanValue())
    {
      Logger.get().info(TAG, "Ignoring schedule request in non-main process", new Throwable[0]);
      return;
    }
    registerExecutionListenerIfNeeded();
    Logger.get().debug(TAG, String.format("Cancelling work ID %s", new Object[] { paramString }), new Throwable[0]);
    this.mWorkManagerImpl.stopWork(paramString);
  }
  
  public void onAllConstraintsMet(List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (String)localIterator.next();
      Logger.get().debug(TAG, String.format("Constraints met: Scheduling work ID %s", new Object[] { paramList }), new Throwable[0]);
      this.mWorkManagerImpl.startWork(paramList);
    }
  }
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      String str = (String)paramList.next();
      Logger.get().debug(TAG, String.format("Constraints not met: Cancelling work ID %s", new Object[] { str }), new Throwable[0]);
      this.mWorkManagerImpl.stopWork(str);
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    removeConstraintTrackingFor(paramString);
  }
  
  public void schedule(WorkSpec... arg1)
  {
    if (this.mIsMainProcess == null) {
      this.mIsMainProcess = Boolean.valueOf(TextUtils.equals(this.mContext.getPackageName(), getProcessName()));
    }
    if (!this.mIsMainProcess.booleanValue())
    {
      Logger.get().info(TAG, "Ignoring schedule request in non-main process", new Throwable[0]);
      return;
    }
    registerExecutionListenerIfNeeded();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = ???.length;
    for (int j = 0; j < i; j++)
    {
      WorkSpec localWorkSpec = ???[j];
      if ((localWorkSpec.state == WorkInfo.State.ENQUEUED) && (!localWorkSpec.isPeriodic()) && (localWorkSpec.initialDelay == 0L) && (!localWorkSpec.isBackedOff())) {
        if (localWorkSpec.hasConstraints())
        {
          if ((Build.VERSION.SDK_INT >= 23) && (localWorkSpec.constraints.requiresDeviceIdle()))
          {
            Logger.get().debug(TAG, String.format("Ignoring WorkSpec %s, Requires device idle.", new Object[] { localWorkSpec }), new Throwable[0]);
          }
          else if ((Build.VERSION.SDK_INT >= 24) && (localWorkSpec.constraints.hasContentUriTriggers()))
          {
            Logger.get().debug(TAG, String.format("Ignoring WorkSpec %s, Requires ContentUri triggers.", new Object[] { localWorkSpec }), new Throwable[0]);
          }
          else
          {
            localArrayList1.add(localWorkSpec);
            localArrayList2.add(localWorkSpec.id);
          }
        }
        else
        {
          Logger.get().debug(TAG, String.format("Starting work for %s", new Object[] { localWorkSpec.id }), new Throwable[0]);
          this.mWorkManagerImpl.startWork(localWorkSpec.id);
        }
      }
    }
    synchronized (this.mLock)
    {
      if (!localArrayList1.isEmpty())
      {
        Logger.get().debug(TAG, String.format("Starting tracking for [%s]", new Object[] { TextUtils.join(",", localArrayList2) }), new Throwable[0]);
        this.mConstrainedWorkSpecs.addAll(localArrayList1);
        this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
      }
      return;
    }
  }
}
