package androidx.work.impl.constraints;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.constraints.controllers.BatteryChargingController;
import androidx.work.impl.constraints.controllers.BatteryNotLowController;
import androidx.work.impl.constraints.controllers.ConstraintController;
import androidx.work.impl.constraints.controllers.ConstraintController.OnConstraintUpdatedCallback;
import androidx.work.impl.constraints.controllers.NetworkConnectedController;
import androidx.work.impl.constraints.controllers.NetworkMeteredController;
import androidx.work.impl.constraints.controllers.NetworkNotRoamingController;
import androidx.work.impl.constraints.controllers.NetworkUnmeteredController;
import androidx.work.impl.constraints.controllers.StorageNotLowController;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkConstraintsTracker
  implements ConstraintController.OnConstraintUpdatedCallback
{
  private static final String TAG = Logger.tagWithPrefix("WorkConstraintsTracker");
  private final WorkConstraintsCallback mCallback;
  private final ConstraintController<?>[] mConstraintControllers;
  private final Object mLock;
  
  public WorkConstraintsTracker(Context paramContext, TaskExecutor paramTaskExecutor, WorkConstraintsCallback paramWorkConstraintsCallback)
  {
    paramContext = paramContext.getApplicationContext();
    this.mCallback = paramWorkConstraintsCallback;
    this.mConstraintControllers = new ConstraintController[] { new BatteryChargingController(paramContext, paramTaskExecutor), new BatteryNotLowController(paramContext, paramTaskExecutor), new StorageNotLowController(paramContext, paramTaskExecutor), new NetworkConnectedController(paramContext, paramTaskExecutor), new NetworkUnmeteredController(paramContext, paramTaskExecutor), new NetworkNotRoamingController(paramContext, paramTaskExecutor), new NetworkMeteredController(paramContext, paramTaskExecutor) };
    this.mLock = new Object();
  }
  
  WorkConstraintsTracker(WorkConstraintsCallback paramWorkConstraintsCallback, ConstraintController<?>[] paramArrayOfConstraintController)
  {
    this.mCallback = paramWorkConstraintsCallback;
    this.mConstraintControllers = paramArrayOfConstraintController;
    this.mLock = new Object();
  }
  
  public boolean areAllConstraintsMet(String paramString)
  {
    synchronized (this.mLock)
    {
      for (ConstraintController localConstraintController : this.mConstraintControllers) {
        if (localConstraintController.isWorkSpecConstrained(paramString))
        {
          Logger.get().debug(TAG, String.format("Work %s constrained by %s", new Object[] { paramString, localConstraintController.getClass().getSimpleName() }), new Throwable[0]);
          return false;
        }
      }
      return true;
    }
  }
  
  public void onConstraintMet(List<String> paramList)
  {
    synchronized (this.mLock)
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        String str = (String)paramList.next();
        if (areAllConstraintsMet(str))
        {
          Logger.get().debug(TAG, String.format("Constraints met for %s", new Object[] { str }), new Throwable[0]);
          localArrayList.add(str);
        }
      }
      if (this.mCallback != null) {
        this.mCallback.onAllConstraintsMet(localArrayList);
      }
      return;
    }
  }
  
  public void onConstraintNotMet(List<String> paramList)
  {
    synchronized (this.mLock)
    {
      if (this.mCallback != null) {
        this.mCallback.onAllConstraintsNotMet(paramList);
      }
      return;
    }
  }
  
  public void replace(Iterable<WorkSpec> paramIterable)
  {
    synchronized (this.mLock)
    {
      ConstraintController[] arrayOfConstraintController = this.mConstraintControllers;
      int i = arrayOfConstraintController.length;
      int j = 0;
      for (int k = 0; k < i; k++) {
        arrayOfConstraintController[k].setCallback(null);
      }
      arrayOfConstraintController = this.mConstraintControllers;
      i = arrayOfConstraintController.length;
      for (k = 0; k < i; k++) {
        arrayOfConstraintController[k].replace(paramIterable);
      }
      paramIterable = this.mConstraintControllers;
      i = paramIterable.length;
      for (k = j; k < i; k++) {
        paramIterable[k].setCallback(this);
      }
      return;
    }
  }
  
  public void reset()
  {
    synchronized (this.mLock)
    {
      ConstraintController[] arrayOfConstraintController = this.mConstraintControllers;
      int i = arrayOfConstraintController.length;
      for (int j = 0; j < i; j++) {
        arrayOfConstraintController[j].reset();
      }
      return;
    }
  }
}
