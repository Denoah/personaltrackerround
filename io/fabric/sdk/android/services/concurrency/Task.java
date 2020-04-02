package io.fabric.sdk.android.services.concurrency;

public abstract interface Task
{
  public abstract Throwable getError();
  
  public abstract boolean isFinished();
  
  public abstract void setError(Throwable paramThrowable);
  
  public abstract void setFinished(boolean paramBoolean);
}
