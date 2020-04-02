package androidx.work.impl.model;

import java.util.List;

public abstract interface DependencyDao
{
  public abstract List<String> getDependentWorkIds(String paramString);
  
  public abstract List<String> getPrerequisites(String paramString);
  
  public abstract boolean hasCompletedAllPrerequisites(String paramString);
  
  public abstract boolean hasDependents(String paramString);
  
  public abstract void insertDependency(Dependency paramDependency);
}
