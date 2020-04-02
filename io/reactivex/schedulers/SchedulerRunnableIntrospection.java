package io.reactivex.schedulers;

public abstract interface SchedulerRunnableIntrospection
{
  public abstract Runnable getWrappedRunnable();
}
