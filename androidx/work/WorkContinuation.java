package androidx.work;

import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

public abstract class WorkContinuation
{
  public WorkContinuation() {}
  
  public static WorkContinuation combine(List<WorkContinuation> paramList)
  {
    return ((WorkContinuation)paramList.get(0)).combineInternal(paramList);
  }
  
  protected abstract WorkContinuation combineInternal(List<WorkContinuation> paramList);
  
  public abstract Operation enqueue();
  
  public abstract ListenableFuture<List<WorkInfo>> getWorkInfos();
  
  public abstract LiveData<List<WorkInfo>> getWorkInfosLiveData();
  
  public final WorkContinuation then(OneTimeWorkRequest paramOneTimeWorkRequest)
  {
    return then(Collections.singletonList(paramOneTimeWorkRequest));
  }
  
  public abstract WorkContinuation then(List<OneTimeWorkRequest> paramList);
}
