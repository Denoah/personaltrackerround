package androidx.work.impl.model;

import androidx.arch.core.util.Function;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.WorkInfo.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class WorkSpec
{
  public static final long SCHEDULE_NOT_REQUESTED_YET = -1L;
  private static final String TAG = Logger.tagWithPrefix("WorkSpec");
  public static final Function<List<WorkInfoPojo>, List<WorkInfo>> WORK_INFO_MAPPER = new Function()
  {
    public List<WorkInfo> apply(List<WorkSpec.WorkInfoPojo> paramAnonymousList)
    {
      if (paramAnonymousList == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList(paramAnonymousList.size());
      paramAnonymousList = paramAnonymousList.iterator();
      while (paramAnonymousList.hasNext()) {
        localArrayList.add(((WorkSpec.WorkInfoPojo)paramAnonymousList.next()).toWorkInfo());
      }
      return localArrayList;
    }
  };
  public long backoffDelayDuration = 30000L;
  public BackoffPolicy backoffPolicy = BackoffPolicy.EXPONENTIAL;
  public Constraints constraints = Constraints.NONE;
  public long flexDuration;
  public String id;
  public long initialDelay;
  public Data input = Data.EMPTY;
  public String inputMergerClassName;
  public long intervalDuration;
  public long minimumRetentionDuration;
  public Data output = Data.EMPTY;
  public long periodStartTime;
  public int runAttemptCount;
  public boolean runInForeground;
  public long scheduleRequestedAt = -1L;
  public WorkInfo.State state = WorkInfo.State.ENQUEUED;
  public String workerClassName;
  
  public WorkSpec(WorkSpec paramWorkSpec)
  {
    this.id = paramWorkSpec.id;
    this.workerClassName = paramWorkSpec.workerClassName;
    this.state = paramWorkSpec.state;
    this.inputMergerClassName = paramWorkSpec.inputMergerClassName;
    this.input = new Data(paramWorkSpec.input);
    this.output = new Data(paramWorkSpec.output);
    this.initialDelay = paramWorkSpec.initialDelay;
    this.intervalDuration = paramWorkSpec.intervalDuration;
    this.flexDuration = paramWorkSpec.flexDuration;
    this.constraints = new Constraints(paramWorkSpec.constraints);
    this.runAttemptCount = paramWorkSpec.runAttemptCount;
    this.backoffPolicy = paramWorkSpec.backoffPolicy;
    this.backoffDelayDuration = paramWorkSpec.backoffDelayDuration;
    this.periodStartTime = paramWorkSpec.periodStartTime;
    this.minimumRetentionDuration = paramWorkSpec.minimumRetentionDuration;
    this.scheduleRequestedAt = paramWorkSpec.scheduleRequestedAt;
    this.runInForeground = paramWorkSpec.runInForeground;
  }
  
  public WorkSpec(String paramString1, String paramString2)
  {
    this.id = paramString1;
    this.workerClassName = paramString2;
  }
  
  public long calculateNextRunTime()
  {
    boolean bool = isBackedOff();
    int i = 0;
    int j = 0;
    if (bool)
    {
      if (this.backoffPolicy == BackoffPolicy.LINEAR) {
        j = 1;
      }
      if (j != 0) {
        l1 = this.backoffDelayDuration * this.runAttemptCount;
      } else {
        l1 = Math.scalb((float)this.backoffDelayDuration, this.runAttemptCount - 1);
      }
      return this.periodStartTime + Math.min(18000000L, l1);
    }
    bool = isPeriodic();
    long l2 = 0L;
    if (bool)
    {
      long l3 = System.currentTimeMillis();
      long l4 = this.periodStartTime;
      l1 = l4;
      if (l4 == 0L) {
        l1 = this.initialDelay + l3;
      }
      j = i;
      if (this.flexDuration != this.intervalDuration) {
        j = 1;
      }
      if (j != 0)
      {
        if (this.periodStartTime == 0L) {
          l2 = this.flexDuration * -1L;
        }
        return l1 + this.intervalDuration + l2;
      }
      if (this.periodStartTime != 0L) {
        l2 = this.intervalDuration;
      }
      return l1 + l2;
    }
    l2 = this.periodStartTime;
    long l1 = l2;
    if (l2 == 0L) {
      l1 = System.currentTimeMillis();
    }
    return l1 + this.initialDelay;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof WorkSpec)) {
      return false;
    }
    WorkSpec localWorkSpec = (WorkSpec)paramObject;
    if (this.initialDelay != localWorkSpec.initialDelay) {
      return false;
    }
    if (this.intervalDuration != localWorkSpec.intervalDuration) {
      return false;
    }
    if (this.flexDuration != localWorkSpec.flexDuration) {
      return false;
    }
    if (this.runAttemptCount != localWorkSpec.runAttemptCount) {
      return false;
    }
    if (this.backoffDelayDuration != localWorkSpec.backoffDelayDuration) {
      return false;
    }
    if (this.periodStartTime != localWorkSpec.periodStartTime) {
      return false;
    }
    if (this.minimumRetentionDuration != localWorkSpec.minimumRetentionDuration) {
      return false;
    }
    if (this.scheduleRequestedAt != localWorkSpec.scheduleRequestedAt) {
      return false;
    }
    if (this.runInForeground != localWorkSpec.runInForeground) {
      return false;
    }
    if (!this.id.equals(localWorkSpec.id)) {
      return false;
    }
    if (this.state != localWorkSpec.state) {
      return false;
    }
    if (!this.workerClassName.equals(localWorkSpec.workerClassName)) {
      return false;
    }
    paramObject = this.inputMergerClassName;
    if (paramObject != null ? !paramObject.equals(localWorkSpec.inputMergerClassName) : localWorkSpec.inputMergerClassName != null) {
      return false;
    }
    if (!this.input.equals(localWorkSpec.input)) {
      return false;
    }
    if (!this.output.equals(localWorkSpec.output)) {
      return false;
    }
    if (!this.constraints.equals(localWorkSpec.constraints)) {
      return false;
    }
    if (this.backoffPolicy != localWorkSpec.backoffPolicy) {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasConstraints()
  {
    return Constraints.NONE.equals(this.constraints) ^ true;
  }
  
  public int hashCode()
  {
    int i = this.id.hashCode();
    int j = this.state.hashCode();
    int k = this.workerClassName.hashCode();
    String str = this.inputMergerClassName;
    int m;
    if (str != null) {
      m = str.hashCode();
    } else {
      m = 0;
    }
    int n = this.input.hashCode();
    int i1 = this.output.hashCode();
    long l = this.initialDelay;
    int i2 = (int)(l ^ l >>> 32);
    l = this.intervalDuration;
    int i3 = (int)(l ^ l >>> 32);
    l = this.flexDuration;
    int i4 = (int)(l ^ l >>> 32);
    int i5 = this.constraints.hashCode();
    int i6 = this.runAttemptCount;
    int i7 = this.backoffPolicy.hashCode();
    l = this.backoffDelayDuration;
    int i8 = (int)(l ^ l >>> 32);
    l = this.periodStartTime;
    int i9 = (int)(l ^ l >>> 32);
    l = this.minimumRetentionDuration;
    int i10 = (int)(l ^ l >>> 32);
    l = this.scheduleRequestedAt;
    return (((((((((((((((i * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i2) * 31 + i3) * 31 + i4) * 31 + i5) * 31 + i6) * 31 + i7) * 31 + i8) * 31 + i9) * 31 + i10) * 31 + (int)(l ^ l >>> 32)) * 31 + this.runInForeground;
  }
  
  public boolean isBackedOff()
  {
    boolean bool;
    if ((this.state == WorkInfo.State.ENQUEUED) && (this.runAttemptCount > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isPeriodic()
  {
    boolean bool;
    if (this.intervalDuration != 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setBackoffDelayDuration(long paramLong)
  {
    long l = paramLong;
    if (paramLong > 18000000L)
    {
      Logger.get().warning(TAG, "Backoff delay duration exceeds maximum value", new Throwable[0]);
      l = 18000000L;
    }
    paramLong = l;
    if (l < 10000L)
    {
      Logger.get().warning(TAG, "Backoff delay duration less than minimum value", new Throwable[0]);
      paramLong = 10000L;
    }
    this.backoffDelayDuration = paramLong;
  }
  
  public void setPeriodic(long paramLong)
  {
    long l = paramLong;
    if (paramLong < 900000L)
    {
      Logger.get().warning(TAG, String.format("Interval duration lesser than minimum allowed value; Changed to %s", new Object[] { Long.valueOf(900000L) }), new Throwable[0]);
      l = 900000L;
    }
    setPeriodic(l, l);
  }
  
  public void setPeriodic(long paramLong1, long paramLong2)
  {
    long l = paramLong1;
    if (paramLong1 < 900000L)
    {
      Logger.get().warning(TAG, String.format("Interval duration lesser than minimum allowed value; Changed to %s", new Object[] { Long.valueOf(900000L) }), new Throwable[0]);
      l = 900000L;
    }
    paramLong1 = paramLong2;
    if (paramLong2 < 300000L)
    {
      Logger.get().warning(TAG, String.format("Flex duration lesser than minimum allowed value; Changed to %s", new Object[] { Long.valueOf(300000L) }), new Throwable[0]);
      paramLong1 = 300000L;
    }
    paramLong2 = paramLong1;
    if (paramLong1 > l)
    {
      Logger.get().warning(TAG, String.format("Flex duration greater than interval duration; Changed to %s", new Object[] { Long.valueOf(l) }), new Throwable[0]);
      paramLong2 = l;
    }
    this.intervalDuration = l;
    this.flexDuration = paramLong2;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{WorkSpec: ");
    localStringBuilder.append(this.id);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public static class IdAndState
  {
    public String id;
    public WorkInfo.State state;
    
    public IdAndState() {}
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof IdAndState)) {
        return false;
      }
      paramObject = (IdAndState)paramObject;
      if (this.state != paramObject.state) {
        return false;
      }
      return this.id.equals(paramObject.id);
    }
    
    public int hashCode()
    {
      return this.id.hashCode() * 31 + this.state.hashCode();
    }
  }
  
  public static class WorkInfoPojo
  {
    public String id;
    public Data output;
    public List<Data> progress;
    public int runAttemptCount;
    public WorkInfo.State state;
    public List<String> tags;
    
    public WorkInfoPojo() {}
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof WorkInfoPojo)) {
        return false;
      }
      paramObject = (WorkInfoPojo)paramObject;
      if (this.runAttemptCount != paramObject.runAttemptCount) {
        return false;
      }
      Object localObject = this.id;
      if (localObject != null ? !((String)localObject).equals(paramObject.id) : paramObject.id != null) {
        return false;
      }
      if (this.state != paramObject.state) {
        return false;
      }
      localObject = this.output;
      if (localObject != null ? !((Data)localObject).equals(paramObject.output) : paramObject.output != null) {
        return false;
      }
      localObject = this.tags;
      if (localObject != null ? !((List)localObject).equals(paramObject.tags) : paramObject.tags != null) {
        return false;
      }
      localObject = this.progress;
      paramObject = paramObject.progress;
      if (localObject != null) {
        bool = ((List)localObject).equals(paramObject);
      } else if (paramObject != null) {
        bool = false;
      }
      return bool;
    }
    
    public int hashCode()
    {
      Object localObject = this.id;
      int i = 0;
      int j;
      if (localObject != null) {
        j = ((String)localObject).hashCode();
      } else {
        j = 0;
      }
      localObject = this.state;
      int k;
      if (localObject != null) {
        k = ((WorkInfo.State)localObject).hashCode();
      } else {
        k = 0;
      }
      localObject = this.output;
      int m;
      if (localObject != null) {
        m = ((Data)localObject).hashCode();
      } else {
        m = 0;
      }
      int n = this.runAttemptCount;
      localObject = this.tags;
      int i1;
      if (localObject != null) {
        i1 = ((List)localObject).hashCode();
      } else {
        i1 = 0;
      }
      localObject = this.progress;
      if (localObject != null) {
        i = ((List)localObject).hashCode();
      }
      return ((((j * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i;
    }
    
    public WorkInfo toWorkInfo()
    {
      Object localObject = this.progress;
      if ((localObject != null) && (!((List)localObject).isEmpty())) {
        localObject = (Data)this.progress.get(0);
      } else {
        localObject = Data.EMPTY;
      }
      return new WorkInfo(UUID.fromString(this.id), this.state, this.output, this.tags, (Data)localObject, this.runAttemptCount);
    }
  }
}
