package androidx.work;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class WorkInfo
{
  private UUID mId;
  private Data mOutputData;
  private Data mProgress;
  private int mRunAttemptCount;
  private State mState;
  private Set<String> mTags;
  
  public WorkInfo(UUID paramUUID, State paramState, Data paramData1, List<String> paramList, Data paramData2, int paramInt)
  {
    this.mId = paramUUID;
    this.mState = paramState;
    this.mOutputData = paramData1;
    this.mTags = new HashSet(paramList);
    this.mProgress = paramData2;
    this.mRunAttemptCount = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (WorkInfo)paramObject;
      if (this.mRunAttemptCount != paramObject.mRunAttemptCount) {
        return false;
      }
      if (!this.mId.equals(paramObject.mId)) {
        return false;
      }
      if (this.mState != paramObject.mState) {
        return false;
      }
      if (!this.mOutputData.equals(paramObject.mOutputData)) {
        return false;
      }
      if (!this.mTags.equals(paramObject.mTags)) {
        return false;
      }
      return this.mProgress.equals(paramObject.mProgress);
    }
    return false;
  }
  
  public UUID getId()
  {
    return this.mId;
  }
  
  public Data getOutputData()
  {
    return this.mOutputData;
  }
  
  public Data getProgress()
  {
    return this.mProgress;
  }
  
  public int getRunAttemptCount()
  {
    return this.mRunAttemptCount;
  }
  
  public State getState()
  {
    return this.mState;
  }
  
  public Set<String> getTags()
  {
    return this.mTags;
  }
  
  public int hashCode()
  {
    return ((((this.mId.hashCode() * 31 + this.mState.hashCode()) * 31 + this.mOutputData.hashCode()) * 31 + this.mTags.hashCode()) * 31 + this.mProgress.hashCode()) * 31 + this.mRunAttemptCount;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("WorkInfo{mId='");
    localStringBuilder.append(this.mId);
    localStringBuilder.append('\'');
    localStringBuilder.append(", mState=");
    localStringBuilder.append(this.mState);
    localStringBuilder.append(", mOutputData=");
    localStringBuilder.append(this.mOutputData);
    localStringBuilder.append(", mTags=");
    localStringBuilder.append(this.mTags);
    localStringBuilder.append(", mProgress=");
    localStringBuilder.append(this.mProgress);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public static enum State
  {
    static
    {
      FAILED = new State("FAILED", 3);
      BLOCKED = new State("BLOCKED", 4);
      State localState = new State("CANCELLED", 5);
      CANCELLED = localState;
      $VALUES = new State[] { ENQUEUED, RUNNING, SUCCEEDED, FAILED, BLOCKED, localState };
    }
    
    private State() {}
    
    public boolean isFinished()
    {
      boolean bool;
      if ((this != SUCCEEDED) && (this != FAILED) && (this != CANCELLED)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
}
