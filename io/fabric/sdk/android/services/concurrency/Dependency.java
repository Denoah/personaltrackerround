package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public abstract interface Dependency<T>
{
  public abstract void addDependency(T paramT);
  
  public abstract boolean areDependenciesMet();
  
  public abstract Collection<T> getDependencies();
}
