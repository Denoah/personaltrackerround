package androidx.work.impl.utils;

import androidx.arch.core.util.Function;
import androidx.work.WorkInfo;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpec.WorkInfoPojo;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.UUID;

public abstract class StatusRunnable<T>
  implements Runnable
{
  private final SettableFuture<T> mFuture = SettableFuture.create();
  
  public StatusRunnable() {}
  
  public static StatusRunnable<List<WorkInfo>> forStringIds(WorkManagerImpl paramWorkManagerImpl, final List<String> paramList)
  {
    new StatusRunnable()
    {
      public List<WorkInfo> runInternal()
      {
        List localList = this.val$workManager.getWorkDatabase().workSpecDao().getWorkStatusPojoForIds(paramList);
        return (List)WorkSpec.WORK_INFO_MAPPER.apply(localList);
      }
    };
  }
  
  public static StatusRunnable<List<WorkInfo>> forTag(WorkManagerImpl paramWorkManagerImpl, final String paramString)
  {
    new StatusRunnable()
    {
      List<WorkInfo> runInternal()
      {
        List localList = this.val$workManager.getWorkDatabase().workSpecDao().getWorkStatusPojoForTag(paramString);
        return (List)WorkSpec.WORK_INFO_MAPPER.apply(localList);
      }
    };
  }
  
  public static StatusRunnable<WorkInfo> forUUID(WorkManagerImpl paramWorkManagerImpl, final UUID paramUUID)
  {
    new StatusRunnable()
    {
      WorkInfo runInternal()
      {
        Object localObject = this.val$workManager.getWorkDatabase().workSpecDao().getWorkStatusPojoForId(paramUUID.toString());
        if (localObject != null) {
          localObject = ((WorkSpec.WorkInfoPojo)localObject).toWorkInfo();
        } else {
          localObject = null;
        }
        return localObject;
      }
    };
  }
  
  public static StatusRunnable<List<WorkInfo>> forUniqueWork(WorkManagerImpl paramWorkManagerImpl, final String paramString)
  {
    new StatusRunnable()
    {
      List<WorkInfo> runInternal()
      {
        List localList = this.val$workManager.getWorkDatabase().workSpecDao().getWorkStatusPojoForName(paramString);
        return (List)WorkSpec.WORK_INFO_MAPPER.apply(localList);
      }
    };
  }
  
  public ListenableFuture<T> getFuture()
  {
    return this.mFuture;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 60	androidx/work/impl/utils/StatusRunnable:runInternal	()Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_0
    //   6: getfield 30	androidx/work/impl/utils/StatusRunnable:mFuture	Landroidx/work/impl/utils/futures/SettableFuture;
    //   9: aload_1
    //   10: invokevirtual 64	androidx/work/impl/utils/futures/SettableFuture:set	(Ljava/lang/Object;)Z
    //   13: pop
    //   14: goto +13 -> 27
    //   17: astore_1
    //   18: aload_0
    //   19: getfield 30	androidx/work/impl/utils/StatusRunnable:mFuture	Landroidx/work/impl/utils/futures/SettableFuture;
    //   22: aload_1
    //   23: invokevirtual 68	androidx/work/impl/utils/futures/SettableFuture:setException	(Ljava/lang/Throwable;)Z
    //   26: pop
    //   27: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	28	0	this	StatusRunnable
    //   4	6	1	localObject	Object
    //   17	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	14	17	finally
  }
  
  abstract T runInternal();
}
