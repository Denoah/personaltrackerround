package io.fabric.sdk.android.services.concurrency;

public abstract interface PriorityProvider<T>
  extends Comparable<T>
{
  public abstract Priority getPriority();
}
