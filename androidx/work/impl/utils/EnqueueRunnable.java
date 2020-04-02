package androidx.work.impl.utils;

import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.Constraints;
import androidx.work.Data.Builder;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.Operation;
import androidx.work.Operation.State.FAILURE;
import androidx.work.WorkInfo.State;
import androidx.work.WorkRequest;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.Scheduler;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkContinuationImpl;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.model.Dependency;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkName;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpec.IdAndState;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTag;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnqueueRunnable
  implements Runnable
{
  private static final String TAG = Logger.tagWithPrefix("EnqueueRunnable");
  private final OperationImpl mOperation;
  private final WorkContinuationImpl mWorkContinuation;
  
  public EnqueueRunnable(WorkContinuationImpl paramWorkContinuationImpl)
  {
    this.mWorkContinuation = paramWorkContinuationImpl;
    this.mOperation = new OperationImpl();
  }
  
  private static boolean enqueueContinuation(WorkContinuationImpl paramWorkContinuationImpl)
  {
    Set localSet = WorkContinuationImpl.prerequisitesFor(paramWorkContinuationImpl);
    boolean bool = enqueueWorkWithPrerequisites(paramWorkContinuationImpl.getWorkManagerImpl(), paramWorkContinuationImpl.getWork(), (String[])localSet.toArray(new String[0]), paramWorkContinuationImpl.getName(), paramWorkContinuationImpl.getExistingWorkPolicy());
    paramWorkContinuationImpl.markEnqueued();
    return bool;
  }
  
  private static boolean enqueueWorkWithPrerequisites(WorkManagerImpl paramWorkManagerImpl, List<? extends WorkRequest> paramList, String[] paramArrayOfString, String paramString, ExistingWorkPolicy paramExistingWorkPolicy)
  {
    long l = System.currentTimeMillis();
    WorkDatabase localWorkDatabase = paramWorkManagerImpl.getWorkDatabase();
    int i;
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    int n;
    int i1;
    Object localObject1;
    Object localObject2;
    if (i != 0)
    {
      int j = paramArrayOfString.length;
      k = 1;
      int m = 0;
      n = 0;
      i1 = 0;
      for (;;)
      {
        i2 = k;
        i3 = n;
        int i4 = i1;
        if (m >= j) {
          break;
        }
        localObject1 = paramArrayOfString[m];
        localObject2 = localWorkDatabase.workSpecDao().getWorkSpec((String)localObject1);
        if (localObject2 == null)
        {
          Logger.get().error(TAG, String.format("Prerequisite %s doesn't exist; not enqueuing", new Object[] { localObject1 }), new Throwable[0]);
          return false;
        }
        localObject2 = ((WorkSpec)localObject2).state;
        if (localObject2 == WorkInfo.State.SUCCEEDED) {
          i5 = 1;
        } else {
          i5 = 0;
        }
        k &= i5;
        if (localObject2 == WorkInfo.State.FAILED)
        {
          i5 = 1;
        }
        else
        {
          i5 = n;
          if (localObject2 == WorkInfo.State.CANCELLED)
          {
            i1 = 1;
            i5 = n;
          }
        }
        m++;
        n = i5;
      }
    }
    int i2 = 1;
    int i3 = 0;
    int i5 = 0;
    int k = TextUtils.isEmpty(paramString) ^ true;
    if ((k != 0) && (i == 0)) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    if (i1 != 0)
    {
      localObject2 = localWorkDatabase.workSpecDao().getWorkSpecIdAndStatesForName(paramString);
      if (!((List)localObject2).isEmpty())
      {
        if (paramExistingWorkPolicy == ExistingWorkPolicy.APPEND)
        {
          paramExistingWorkPolicy = localWorkDatabase.dependencyDao();
          localObject1 = new ArrayList();
          Iterator localIterator = ((List)localObject2).iterator();
          while (localIterator.hasNext())
          {
            localObject2 = (WorkSpec.IdAndState)localIterator.next();
            n = i2;
            i = i3;
            i1 = i5;
            if (!paramExistingWorkPolicy.hasDependents(((WorkSpec.IdAndState)localObject2).id))
            {
              if (((WorkSpec.IdAndState)localObject2).state == WorkInfo.State.SUCCEEDED) {
                i1 = 1;
              } else {
                i1 = 0;
              }
              if (((WorkSpec.IdAndState)localObject2).state == WorkInfo.State.FAILED)
              {
                i = 1;
              }
              else
              {
                i = i3;
                if (((WorkSpec.IdAndState)localObject2).state == WorkInfo.State.CANCELLED)
                {
                  i5 = 1;
                  i = i3;
                }
              }
              ((List)localObject1).add(((WorkSpec.IdAndState)localObject2).id);
              n = i1 & i2;
              i1 = i5;
            }
            i2 = n;
            i3 = i;
            i5 = i1;
          }
          paramArrayOfString = (String[])((List)localObject1).toArray(paramArrayOfString);
          if (paramArrayOfString.length > 0) {
            i = 1;
          } else {
            i = 0;
          }
          bool = false;
          break label603;
        }
        if (paramExistingWorkPolicy == ExistingWorkPolicy.KEEP)
        {
          localObject1 = ((List)localObject2).iterator();
          while (((Iterator)localObject1).hasNext())
          {
            paramExistingWorkPolicy = (WorkSpec.IdAndState)((Iterator)localObject1).next();
            if ((paramExistingWorkPolicy.state == WorkInfo.State.ENQUEUED) || (paramExistingWorkPolicy.state == WorkInfo.State.RUNNING)) {
              return false;
            }
          }
        }
        CancelWorkRunnable.forName(paramString, paramWorkManagerImpl, false).run();
        paramExistingWorkPolicy = localWorkDatabase.workSpecDao();
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext()) {
          paramExistingWorkPolicy.delete(((WorkSpec.IdAndState)((Iterator)localObject2).next()).id);
        }
        bool = true;
        break label603;
      }
    }
    boolean bool = false;
    label603:
    paramExistingWorkPolicy = paramList.iterator();
    while (paramExistingWorkPolicy.hasNext())
    {
      paramList = (WorkRequest)paramExistingWorkPolicy.next();
      localObject2 = paramList.getWorkSpec();
      if ((i != 0) && (i2 == 0))
      {
        if (i3 != 0) {
          ((WorkSpec)localObject2).state = WorkInfo.State.FAILED;
        } else if (i5 != 0) {
          ((WorkSpec)localObject2).state = WorkInfo.State.CANCELLED;
        } else {
          ((WorkSpec)localObject2).state = WorkInfo.State.BLOCKED;
        }
      }
      else
      {
        if (((WorkSpec)localObject2).isPeriodic()) {
          break label709;
        }
        ((WorkSpec)localObject2).periodStartTime = l;
      }
      break label715;
      label709:
      ((WorkSpec)localObject2).periodStartTime = 0L;
      label715:
      if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT <= 25)) {
        tryDelegateConstrainedWorkSpec((WorkSpec)localObject2);
      } else if ((Build.VERSION.SDK_INT <= 22) && (usesScheduler(paramWorkManagerImpl, "androidx.work.impl.background.gcm.GcmScheduler"))) {
        tryDelegateConstrainedWorkSpec((WorkSpec)localObject2);
      }
      if (((WorkSpec)localObject2).state == WorkInfo.State.ENQUEUED) {
        bool = true;
      }
      localWorkDatabase.workSpecDao().insertWorkSpec((WorkSpec)localObject2);
      if (i != 0)
      {
        n = paramArrayOfString.length;
        for (i1 = 0; i1 < n; i1++)
        {
          localObject2 = paramArrayOfString[i1];
          localObject2 = new Dependency(paramList.getStringId(), (String)localObject2);
          localWorkDatabase.dependencyDao().insertDependency((Dependency)localObject2);
        }
      }
      localObject2 = paramList.getTags().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (String)((Iterator)localObject2).next();
        localWorkDatabase.workTagDao().insert(new WorkTag((String)localObject1, paramList.getStringId()));
      }
      if (k != 0) {
        localWorkDatabase.workNameDao().insert(new WorkName(paramString, paramList.getStringId()));
      }
    }
    return bool;
  }
  
  private static boolean processContinuation(WorkContinuationImpl paramWorkContinuationImpl)
  {
    Object localObject = paramWorkContinuationImpl.getParents();
    boolean bool = false;
    if (localObject != null)
    {
      Iterator localIterator = ((List)localObject).iterator();
      bool = false;
      while (localIterator.hasNext())
      {
        localObject = (WorkContinuationImpl)localIterator.next();
        if (!((WorkContinuationImpl)localObject).isEnqueued()) {
          bool |= processContinuation((WorkContinuationImpl)localObject);
        } else {
          Logger.get().warning(TAG, String.format("Already enqueued work ids (%s).", new Object[] { TextUtils.join(", ", ((WorkContinuationImpl)localObject).getIds()) }), new Throwable[0]);
        }
      }
    }
    return enqueueContinuation(paramWorkContinuationImpl) | bool;
  }
  
  private static void tryDelegateConstrainedWorkSpec(WorkSpec paramWorkSpec)
  {
    Object localObject = paramWorkSpec.constraints;
    if ((((Constraints)localObject).requiresBatteryNotLow()) || (((Constraints)localObject).requiresStorageNotLow()))
    {
      String str = paramWorkSpec.workerClassName;
      localObject = new Data.Builder();
      ((Data.Builder)localObject).putAll(paramWorkSpec.input).putString("androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME", str);
      paramWorkSpec.workerClassName = ConstraintTrackingWorker.class.getName();
      paramWorkSpec.input = ((Data.Builder)localObject).build();
    }
  }
  
  private static boolean usesScheduler(WorkManagerImpl paramWorkManagerImpl, String paramString)
  {
    try
    {
      paramString = Class.forName(paramString);
      paramWorkManagerImpl = paramWorkManagerImpl.getSchedulers().iterator();
      while (paramWorkManagerImpl.hasNext())
      {
        boolean bool = paramString.isAssignableFrom(((Scheduler)paramWorkManagerImpl.next()).getClass());
        if (bool) {
          return true;
        }
      }
    }
    catch (ClassNotFoundException paramWorkManagerImpl)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean addToDatabase()
  {
    WorkDatabase localWorkDatabase = this.mWorkContinuation.getWorkManagerImpl().getWorkDatabase();
    localWorkDatabase.beginTransaction();
    try
    {
      boolean bool = processContinuation(this.mWorkContinuation);
      localWorkDatabase.setTransactionSuccessful();
      return bool;
    }
    finally
    {
      localWorkDatabase.endTransaction();
    }
  }
  
  public Operation getOperation()
  {
    return this.mOperation;
  }
  
  public void run()
  {
    try
    {
      if (!this.mWorkContinuation.hasCycles())
      {
        if (addToDatabase())
        {
          PackageManagerHelper.setComponentEnabled(this.mWorkContinuation.getWorkManagerImpl().getApplicationContext(), RescheduleReceiver.class, true);
          scheduleWorkInBackground();
        }
        this.mOperation.setState(Operation.SUCCESS);
      }
      else
      {
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>(String.format("WorkContinuation has cycles (%s)", new Object[] { this.mWorkContinuation }));
        throw localIllegalStateException;
      }
    }
    finally
    {
      this.mOperation.setState(new Operation.State.FAILURE(localThrowable));
    }
  }
  
  public void scheduleWorkInBackground()
  {
    WorkManagerImpl localWorkManagerImpl = this.mWorkContinuation.getWorkManagerImpl();
    Schedulers.schedule(localWorkManagerImpl.getConfiguration(), localWorkManagerImpl.getWorkDatabase(), localWorkManagerImpl.getSchedulers());
  }
}
