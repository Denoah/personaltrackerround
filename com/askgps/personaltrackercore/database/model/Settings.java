package com.askgps.personaltrackercore.database.model;

import .r8.backportedMethods.utility.Long.1.hashCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\003\n\002\020\b\n\002\b\027\b?\b\030\0002\0020\001BA\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\005\022\b\b\002\020\006\032\0020\007\022\b\b\002\020\b\032\0020\007\022\b\b\002\020\t\032\0020\007\022\b\b\002\020\n\032\0020\013?\006\002\020\fJ\t\020\027\032\0020\003H?\003J\t\020\030\032\0020\005H?\003J\t\020\031\032\0020\007H?\003J\t\020\032\032\0020\007H?\003J\t\020\033\032\0020\007H?\003J\t\020\034\032\0020\013H?\003JE\020\035\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\0072\b\b\002\020\t\032\0020\0072\b\b\002\020\n\032\0020\013H?\001J\023\020\036\032\0020\0032\b\020\037\032\004\030\0010\001H?\003J\t\020 \032\0020\013H?\001J\t\020!\032\0020\005H?\001R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\r\020\016R\026\020\n\032\0020\0138\006X?\004?\006\b\n\000\032\004\b\017\020\020R\021\020\006\032\0020\007?\006\b\n\000\032\004\b\021\020\022R\021\020\t\032\0020\007?\006\b\n\000\032\004\b\023\020\022R\021\020\b\032\0020\007?\006\b\n\000\032\004\b\024\020\022R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\025\020\026?\006\""}, d2={"Lcom/askgps/personaltrackercore/database/model/Settings;", "", "workIsStart", "", "gaskarAddress", "", "locationInterval", "", "sendTelemetryInterval", "removalFromHandJobInterval", "id", "", "(ZLjava/lang/String;JJJI)V", "getGaskarAddress", "()Ljava/lang/String;", "getId", "()I", "getLocationInterval", "()J", "getRemovalFromHandJobInterval", "getSendTelemetryInterval", "getWorkIsStart", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class Settings
{
  private final String gaskarAddress;
  private final int id;
  private final long locationInterval;
  private final long removalFromHandJobInterval;
  private final long sendTelemetryInterval;
  private final boolean workIsStart;
  
  public Settings()
  {
    this(false, null, 0L, 0L, 0L, 0, 63, null);
  }
  
  public Settings(boolean paramBoolean, String paramString, long paramLong1, long paramLong2, long paramLong3, int paramInt)
  {
    this.workIsStart = paramBoolean;
    this.gaskarAddress = paramString;
    this.locationInterval = paramLong1;
    this.sendTelemetryInterval = paramLong2;
    this.removalFromHandJobInterval = paramLong3;
    this.id = paramInt;
  }
  
  public final boolean component1()
  {
    return this.workIsStart;
  }
  
  public final String component2()
  {
    return this.gaskarAddress;
  }
  
  public final long component3()
  {
    return this.locationInterval;
  }
  
  public final long component4()
  {
    return this.sendTelemetryInterval;
  }
  
  public final long component5()
  {
    return this.removalFromHandJobInterval;
  }
  
  public final int component6()
  {
    return this.id;
  }
  
  public final Settings copy(boolean paramBoolean, String paramString, long paramLong1, long paramLong2, long paramLong3, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "gaskarAddress");
    return new Settings(paramBoolean, paramString, paramLong1, paramLong2, paramLong3, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof Settings))
      {
        paramObject = (Settings)paramObject;
        if ((this.workIsStart == paramObject.workIsStart) && (Intrinsics.areEqual(this.gaskarAddress, paramObject.gaskarAddress)) && (this.locationInterval == paramObject.locationInterval) && (this.sendTelemetryInterval == paramObject.sendTelemetryInterval) && (this.removalFromHandJobInterval == paramObject.removalFromHandJobInterval) && (this.id == paramObject.id)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getGaskarAddress()
  {
    return this.gaskarAddress;
  }
  
  public final int getId()
  {
    return this.id;
  }
  
  public final long getLocationInterval()
  {
    return this.locationInterval;
  }
  
  public final long getRemovalFromHandJobInterval()
  {
    return this.removalFromHandJobInterval;
  }
  
  public final long getSendTelemetryInterval()
  {
    return this.sendTelemetryInterval;
  }
  
  public final boolean getWorkIsStart()
  {
    return this.workIsStart;
  }
  
  public int hashCode()
  {
    boolean bool1 = this.workIsStart;
    boolean bool2 = bool1;
    if (bool1) {
      bool2 = true;
    }
    String str = this.gaskarAddress;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return ((((bool2 * true + i) * 31 + .r8.backportedMethods.utility.Long.1.hashCode.hashCode(this.locationInterval)) * 31 + .r8.backportedMethods.utility.Long.1.hashCode.hashCode(this.sendTelemetryInterval)) * 31 + .r8.backportedMethods.utility.Long.1.hashCode.hashCode(this.removalFromHandJobInterval)) * 31 + this.id;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Settings(workIsStart=");
    localStringBuilder.append(this.workIsStart);
    localStringBuilder.append(", gaskarAddress=");
    localStringBuilder.append(this.gaskarAddress);
    localStringBuilder.append(", locationInterval=");
    localStringBuilder.append(this.locationInterval);
    localStringBuilder.append(", sendTelemetryInterval=");
    localStringBuilder.append(this.sendTelemetryInterval);
    localStringBuilder.append(", removalFromHandJobInterval=");
    localStringBuilder.append(this.removalFromHandJobInterval);
    localStringBuilder.append(", id=");
    localStringBuilder.append(this.id);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
