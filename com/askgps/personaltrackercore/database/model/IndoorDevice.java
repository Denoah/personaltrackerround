package com.askgps.personaltrackercore.database.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\t\n\002\020\013\n\002\b\004\b?\b\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\t\020\013\032\0020\003H?\003J\t\020\f\032\0020\005H?\003J\035\020\r\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\005H?\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001H?\002J\b\020\021\032\0020\005H\026J\t\020\022\032\0020\003H?\001R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\007\020\bR\021\020\004\032\0020\005?\006\b\n\000\032\004\b\t\020\n?\006\023"}, d2={"Lcom/askgps/personaltrackercore/database/model/IndoorDevice;", "", "name", "", "rssi", "", "(Ljava/lang/String;I)V", "getName", "()Ljava/lang/String;", "getRssi", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class IndoorDevice
{
  private final String name;
  private final int rssi;
  
  public IndoorDevice(String paramString, int paramInt)
  {
    this.name = paramString;
    this.rssi = paramInt;
  }
  
  public final String component1()
  {
    return this.name;
  }
  
  public final int component2()
  {
    return this.rssi;
  }
  
  public final IndoorDevice copy(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    return new IndoorDevice(paramString, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((IndoorDevice)this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof IndoorDevice)) {
      return false;
    }
    return !(Intrinsics.areEqual(this.name, ((IndoorDevice)paramObject).name) ^ true);
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final int getRssi()
  {
    return this.rssi;
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IndoorDevice(name=");
    localStringBuilder.append(this.name);
    localStringBuilder.append(", rssi=");
    localStringBuilder.append(this.rssi);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
