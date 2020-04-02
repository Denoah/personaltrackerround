package androidx.work.impl;

import androidx.work.impl.model.WorkSpec;

public abstract interface Scheduler
{
  public static final int MAX_SCHEDULER_LIMIT = 50;
  
  public abstract void cancel(String paramString);
  
  public abstract void schedule(WorkSpec... paramVarArgs);
}
