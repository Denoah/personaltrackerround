package androidx.work.impl.constraints.trackers;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public abstract class ConstraintTracker<T>
{
  private static final String TAG = Logger.tagWithPrefix("ConstraintTracker");
  protected final Context mAppContext;
  T mCurrentState;
  private final Set<ConstraintListener<T>> mListeners = new LinkedHashSet();
  private final Object mLock = new Object();
  protected final TaskExecutor mTaskExecutor;
  
  ConstraintTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    this.mAppContext = paramContext.getApplicationContext();
    this.mTaskExecutor = paramTaskExecutor;
  }
  
  public void addListener(ConstraintListener<T> paramConstraintListener)
  {
    synchronized (this.mLock)
    {
      if (this.mListeners.add(paramConstraintListener))
      {
        if (this.mListeners.size() == 1)
        {
          this.mCurrentState = getInitialState();
          Logger.get().debug(TAG, String.format("%s: initial state = %s", new Object[] { getClass().getSimpleName(), this.mCurrentState }), new Throwable[0]);
          startTracking();
        }
        paramConstraintListener.onConstraintChanged(this.mCurrentState);
      }
      return;
    }
  }
  
  public abstract T getInitialState();
  
  public void removeListener(ConstraintListener<T> paramConstraintListener)
  {
    synchronized (this.mLock)
    {
      if ((this.mListeners.remove(paramConstraintListener)) && (this.mListeners.isEmpty())) {
        stopTracking();
      }
      return;
    }
  }
  
  public void setState(T paramT)
  {
    synchronized (this.mLock)
    {
      if ((this.mCurrentState != paramT) && ((this.mCurrentState == null) || (!this.mCurrentState.equals(paramT))))
      {
        this.mCurrentState = paramT;
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>(this.mListeners);
        Executor localExecutor = this.mTaskExecutor.getMainThreadExecutor();
        paramT = new androidx/work/impl/constraints/trackers/ConstraintTracker$1;
        paramT.<init>(this, localArrayList);
        localExecutor.execute(paramT);
        return;
      }
      return;
    }
  }
  
  public abstract void startTracking();
  
  public abstract void stopTracking();
}
