package androidx.work;

import android.content.Context;
import java.lang.reflect.Constructor;

public abstract class WorkerFactory
{
  private static final String TAG = Logger.tagWithPrefix("WorkerFactory");
  
  public WorkerFactory() {}
  
  public static WorkerFactory getDefaultWorkerFactory()
  {
    new WorkerFactory()
    {
      public ListenableWorker createWorker(Context paramAnonymousContext, String paramAnonymousString, WorkerParameters paramAnonymousWorkerParameters)
      {
        return null;
      }
    };
  }
  
  public abstract ListenableWorker createWorker(Context paramContext, String paramString, WorkerParameters paramWorkerParameters);
  
  public final ListenableWorker createWorkerWithDefaultFallback(Context paramContext, String paramString, WorkerParameters paramWorkerParameters)
  {
    ListenableWorker localListenableWorker = createWorker(paramContext, paramString, paramWorkerParameters);
    Object localObject1 = localListenableWorker;
    Object localObject2;
    if (localListenableWorker == null)
    {
      Object localObject3 = null;
      try
      {
        localObject1 = Class.forName(paramString).asSubclass(ListenableWorker.class);
        localObject3 = localObject1;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        localObject2 = Logger.get();
        String str = TAG;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Class not found: ");
        localStringBuilder.append(paramString);
        ((Logger)localObject2).error(str, localStringBuilder.toString(), new Throwable[0]);
      }
      localObject2 = localListenableWorker;
      if (localObject3 != null) {
        try
        {
          localObject2 = (ListenableWorker)((Class)localObject3).getDeclaredConstructor(new Class[] { Context.class, WorkerParameters.class }).newInstance(new Object[] { paramContext, paramWorkerParameters });
        }
        catch (Exception paramContext)
        {
          localObject2 = Logger.get();
          localObject3 = TAG;
          paramWorkerParameters = new StringBuilder();
          paramWorkerParameters.append("Could not instantiate ");
          paramWorkerParameters.append(paramString);
          ((Logger)localObject2).error((String)localObject3, paramWorkerParameters.toString(), new Throwable[] { paramContext });
          localObject2 = localListenableWorker;
        }
      }
    }
    if ((localObject2 != null) && (((ListenableWorker)localObject2).isUsed())) {
      throw new IllegalStateException(String.format("WorkerFactory (%s) returned an instance of a ListenableWorker (%s) which has already been invoked. createWorker() must always return a new instance of a ListenableWorker.", new Object[] { getClass().getName(), paramString }));
    }
    return localObject2;
  }
}
