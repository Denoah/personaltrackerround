package io.fabric.sdk.android.services.concurrency;

public abstract interface DelegateProvider
{
  public abstract <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T getDelegate();
}
