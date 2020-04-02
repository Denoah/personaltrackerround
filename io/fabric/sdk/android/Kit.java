package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Task;
import java.io.File;
import java.util.Collection;

public abstract class Kit<Result>
  implements Comparable<Kit>
{
  Context context;
  final DependsOn dependsOnAnnotation = (DependsOn)getClass().getAnnotation(DependsOn.class);
  Fabric fabric;
  IdManager idManager;
  InitializationCallback<Result> initializationCallback;
  InitializationTask<Result> initializationTask = new InitializationTask(this);
  
  public Kit() {}
  
  public int compareTo(Kit paramKit)
  {
    if (containsAnnotatedDependency(paramKit)) {
      return 1;
    }
    if (paramKit.containsAnnotatedDependency(this)) {
      return -1;
    }
    if ((hasAnnotatedDependency()) && (!paramKit.hasAnnotatedDependency())) {
      return 1;
    }
    if ((!hasAnnotatedDependency()) && (paramKit.hasAnnotatedDependency())) {
      return -1;
    }
    return 0;
  }
  
  boolean containsAnnotatedDependency(Kit paramKit)
  {
    if (hasAnnotatedDependency())
    {
      Class[] arrayOfClass = this.dependsOnAnnotation.value();
      int i = arrayOfClass.length;
      for (int j = 0; j < i; j++) {
        if (arrayOfClass[j].isAssignableFrom(paramKit.getClass())) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected abstract Result doInBackground();
  
  public Context getContext()
  {
    return this.context;
  }
  
  protected Collection<Task> getDependencies()
  {
    return this.initializationTask.getDependencies();
  }
  
  public Fabric getFabric()
  {
    return this.fabric;
  }
  
  protected IdManager getIdManager()
  {
    return this.idManager;
  }
  
  public abstract String getIdentifier();
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".Fabric");
    localStringBuilder.append(File.separator);
    localStringBuilder.append(getIdentifier());
    return localStringBuilder.toString();
  }
  
  public abstract String getVersion();
  
  boolean hasAnnotatedDependency()
  {
    boolean bool;
    if (this.dependsOnAnnotation != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  final void initialize()
  {
    this.initializationTask.executeOnExecutor(this.fabric.getExecutorService(), new Void[] { (Void)null });
  }
  
  void injectParameters(Context paramContext, Fabric paramFabric, InitializationCallback<Result> paramInitializationCallback, IdManager paramIdManager)
  {
    this.fabric = paramFabric;
    this.context = new FabricContext(paramContext, getIdentifier(), getPath());
    this.initializationCallback = paramInitializationCallback;
    this.idManager = paramIdManager;
  }
  
  protected void onCancelled(Result paramResult) {}
  
  protected void onPostExecute(Result paramResult) {}
  
  protected boolean onPreExecute()
  {
    return true;
  }
}
