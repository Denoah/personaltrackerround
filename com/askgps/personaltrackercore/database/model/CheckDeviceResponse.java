package com.askgps.personaltrackercore.database.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\006\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\002\b?\b\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\t\020\007\032\0020\003H?\003J\023\020\b\032\0020\0002\b\b\002\020\002\032\0020\003H?\001J\023\020\t\032\0020\n2\b\020\013\032\004\030\0010\001H?\003J\t\020\f\032\0020\rH?\001J\t\020\016\032\0020\003H?\001R\026\020\002\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\005\020\006?\006\017"}, d2={"Lcom/askgps/personaltrackercore/database/model/CheckDeviceResponse;", "", "status", "", "(Ljava/lang/String;)V", "getStatus", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class CheckDeviceResponse
{
  @SerializedName("status")
  private final String status;
  
  public CheckDeviceResponse(String paramString)
  {
    this.status = paramString;
  }
  
  public final String component1()
  {
    return this.status;
  }
  
  public final CheckDeviceResponse copy(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "status");
    return new CheckDeviceResponse(paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof CheckDeviceResponse))
      {
        paramObject = (CheckDeviceResponse)paramObject;
        if (Intrinsics.areEqual(this.status, paramObject.status)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getStatus()
  {
    return this.status;
  }
  
  public int hashCode()
  {
    String str = this.status;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CheckDeviceResponse(status=");
    localStringBuilder.append(this.status);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
