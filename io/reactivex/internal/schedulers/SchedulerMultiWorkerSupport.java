package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler.Worker;

public abstract interface SchedulerMultiWorkerSupport
{
  public abstract void createWorkers(int paramInt, WorkerCallback paramWorkerCallback);
  
  public static abstract interface WorkerCallback
  {
    public abstract void onWorker(int paramInt, Scheduler.Worker paramWorker);
  }
}
