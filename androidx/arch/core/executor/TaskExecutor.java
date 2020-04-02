package androidx.arch.core.executor;

public abstract class TaskExecutor
{
  public TaskExecutor() {}
  
  public abstract void executeOnDiskIO(Runnable paramRunnable);
  
  public void executeOnMainThread(Runnable paramRunnable)
  {
    if (isMainThread()) {
      paramRunnable.run();
    } else {
      postToMainThread(paramRunnable);
    }
  }
  
  public abstract boolean isMainThread();
  
  public abstract void postToMainThread(Runnable paramRunnable);
}
