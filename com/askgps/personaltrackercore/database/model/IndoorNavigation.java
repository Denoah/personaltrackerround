package com.askgps.personaltrackercore.database.model;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\000\n\002\020\"\n\002\030\002\n\002\b\t\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b?\b\030\0002\0020\001B!\022\f\020\002\032\b\022\004\022\0020\0040\003\022\f\020\005\032\b\022\004\022\0020\0040\003?\006\002\020\006J\017\020\n\032\b\022\004\022\0020\0040\003H?\003J\017\020\013\032\b\022\004\022\0020\0040\003H?\003J)\020\f\032\0020\0002\016\b\002\020\002\032\b\022\004\022\0020\0040\0032\016\b\002\020\005\032\b\022\004\022\0020\0040\003H?\001J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\001H?\003J\t\020\020\032\0020\021H?\001J\t\020\022\032\0020\023H?\001R\027\020\002\032\b\022\004\022\0020\0040\003?\006\b\n\000\032\004\b\007\020\bR\027\020\005\032\b\022\004\022\0020\0040\003?\006\b\n\000\032\004\b\t\020\b?\006\024"}, d2={"Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;", "", "bluetoothDevices", "", "Lcom/askgps/personaltrackercore/database/model/IndoorDevice;", "wifiDevices", "(Ljava/util/Set;Ljava/util/Set;)V", "getBluetoothDevices", "()Ljava/util/Set;", "getWifiDevices", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class IndoorNavigation
{
  private final Set<IndoorDevice> bluetoothDevices;
  private final Set<IndoorDevice> wifiDevices;
  
  public IndoorNavigation(Set<IndoorDevice> paramSet1, Set<IndoorDevice> paramSet2)
  {
    this.bluetoothDevices = paramSet1;
    this.wifiDevices = paramSet2;
  }
  
  public final Set<IndoorDevice> component1()
  {
    return this.bluetoothDevices;
  }
  
  public final Set<IndoorDevice> component2()
  {
    return this.wifiDevices;
  }
  
  public final IndoorNavigation copy(Set<IndoorDevice> paramSet1, Set<IndoorDevice> paramSet2)
  {
    Intrinsics.checkParameterIsNotNull(paramSet1, "bluetoothDevices");
    Intrinsics.checkParameterIsNotNull(paramSet2, "wifiDevices");
    return new IndoorNavigation(paramSet1, paramSet2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof IndoorNavigation))
      {
        paramObject = (IndoorNavigation)paramObject;
        if ((Intrinsics.areEqual(this.bluetoothDevices, paramObject.bluetoothDevices)) && (Intrinsics.areEqual(this.wifiDevices, paramObject.wifiDevices))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final Set<IndoorDevice> getBluetoothDevices()
  {
    return this.bluetoothDevices;
  }
  
  public final Set<IndoorDevice> getWifiDevices()
  {
    return this.wifiDevices;
  }
  
  public int hashCode()
  {
    Set localSet = this.bluetoothDevices;
    int i = 0;
    int j;
    if (localSet != null) {
      j = localSet.hashCode();
    } else {
      j = 0;
    }
    localSet = this.wifiDevices;
    if (localSet != null) {
      i = localSet.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IndoorNavigation(bluetoothDevices=");
    localStringBuilder.append(this.bluetoothDevices);
    localStringBuilder.append(", wifiDevices=");
    localStringBuilder.append(this.wifiDevices);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
