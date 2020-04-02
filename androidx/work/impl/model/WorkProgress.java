package androidx.work.impl.model;

import androidx.work.Data;

public class WorkProgress
{
  public final Data mProgress;
  public final String mWorkSpecId;
  
  public WorkProgress(String paramString, Data paramData)
  {
    this.mWorkSpecId = paramString;
    this.mProgress = paramData;
  }
}
