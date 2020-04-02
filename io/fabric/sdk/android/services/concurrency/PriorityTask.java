package io.fabric.sdk.android.services.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask
  implements Dependency<Task>, PriorityProvider, Task
{
  private final List<Task> dependencies = new ArrayList();
  private final AtomicBoolean hasRun = new AtomicBoolean(false);
  private final AtomicReference<Throwable> throwable = new AtomicReference(null);
  
  public PriorityTask() {}
  
  public static boolean isProperDelegate(Object paramObject)
  {
    bool1 = false;
    try
    {
      Dependency localDependency = (Dependency)paramObject;
      Task localTask = (Task)paramObject;
      paramObject = (PriorityProvider)paramObject;
      bool2 = bool1;
      if (localDependency != null)
      {
        bool2 = bool1;
        if (localTask != null)
        {
          bool2 = bool1;
          if (paramObject != null) {
            bool2 = true;
          }
        }
      }
    }
    catch (ClassCastException paramObject)
    {
      for (;;)
      {
        boolean bool2 = bool1;
      }
    }
    return bool2;
  }
  
  public void addDependency(Task paramTask)
  {
    try
    {
      this.dependencies.add(paramTask);
      return;
    }
    finally
    {
      paramTask = finally;
      throw paramTask;
    }
  }
  
  public boolean areDependenciesMet()
  {
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext()) {
      if (!((Task)localIterator.next()).isFinished()) {
        return false;
      }
    }
    return true;
  }
  
  public int compareTo(Object paramObject)
  {
    return Priority.compareTo(this, paramObject);
  }
  
  public Collection<Task> getDependencies()
  {
    try
    {
      Collection localCollection = Collections.unmodifiableCollection(this.dependencies);
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public Throwable getError()
  {
    return (Throwable)this.throwable.get();
  }
  
  public Priority getPriority()
  {
    return Priority.NORMAL;
  }
  
  public boolean isFinished()
  {
    return this.hasRun.get();
  }
  
  public void setError(Throwable paramThrowable)
  {
    this.throwable.set(paramThrowable);
  }
  
  public void setFinished(boolean paramBoolean)
  {
    try
    {
      this.hasRun.set(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}
