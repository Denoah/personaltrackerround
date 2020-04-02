package androidx.work.impl.utils.taskexecutor;

import androidx.work.impl.utils.SerialExecutor;
import java.util.concurrent.Executor;

public abstract interface TaskExecutor
{
  public abstract void executeOnBackgroundThread(Runnable paramRunnable);
  
  public abstract SerialExecutor getBackgroundExecutor();
  
  public abstract Executor getMainThreadExecutor();
  
  public abstract void postToMainThread(Runnable paramRunnable);
}
