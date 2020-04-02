package androidx.work;

import android.content.Context;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DelegatingWorkerFactory
  extends WorkerFactory
{
  private static final String TAG = Logger.tagWithPrefix("DelegatingWkrFctry");
  private final List<WorkerFactory> mFactories = new LinkedList();
  
  public DelegatingWorkerFactory() {}
  
  public final void addFactory(WorkerFactory paramWorkerFactory)
  {
    this.mFactories.add(paramWorkerFactory);
  }
  
  public final ListenableWorker createWorker(Context paramContext, String paramString, WorkerParameters paramWorkerParameters)
  {
    Iterator localIterator = this.mFactories.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (WorkerFactory)localIterator.next();
      try
      {
        localObject = ((WorkerFactory)localObject).createWorker(paramContext, paramString, paramWorkerParameters);
        if (localObject != null) {
          return localObject;
        }
      }
      finally
      {
        paramString = String.format("Unable to instantiate a ListenableWorker (%s)", new Object[] { paramString });
        Logger.get().error(TAG, paramString, new Throwable[] { paramContext });
      }
    }
    return null;
  }
  
  List<WorkerFactory> getFactories()
  {
    return this.mFactories;
  }
}
