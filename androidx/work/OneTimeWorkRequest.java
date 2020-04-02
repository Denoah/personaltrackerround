package androidx.work;

import android.os.Build.VERSION;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class OneTimeWorkRequest
  extends WorkRequest
{
  OneTimeWorkRequest(Builder paramBuilder)
  {
    super(paramBuilder.mId, paramBuilder.mWorkSpec, paramBuilder.mTags);
  }
  
  public static OneTimeWorkRequest from(Class<? extends ListenableWorker> paramClass)
  {
    return (OneTimeWorkRequest)new Builder(paramClass).build();
  }
  
  public static List<OneTimeWorkRequest> from(List<Class<? extends ListenableWorker>> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(new Builder((Class)paramList.next()).build());
    }
    return localArrayList;
  }
  
  public static final class Builder
    extends WorkRequest.Builder<Builder, OneTimeWorkRequest>
  {
    public Builder(Class<? extends ListenableWorker> paramClass)
    {
      super();
      this.mWorkSpec.inputMergerClassName = OverwritingInputMerger.class.getName();
    }
    
    OneTimeWorkRequest buildInternal()
    {
      if ((this.mBackoffCriteriaSet) && (Build.VERSION.SDK_INT >= 23) && (this.mWorkSpec.constraints.requiresDeviceIdle())) {
        throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
      }
      if ((this.mWorkSpec.runInForeground) && (Build.VERSION.SDK_INT >= 23) && (this.mWorkSpec.constraints.requiresDeviceIdle())) {
        throw new IllegalArgumentException("Cannot run in foreground with an idle mode constraint");
      }
      return new OneTimeWorkRequest(this);
    }
    
    Builder getThis()
    {
      return this;
    }
    
    public Builder setInputMerger(Class<? extends InputMerger> paramClass)
    {
      this.mWorkSpec.inputMergerClassName = paramClass.getName();
      return this;
    }
  }
}
