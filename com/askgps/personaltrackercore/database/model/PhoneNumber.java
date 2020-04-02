package com.askgps.personaltrackercore.database.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\t\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\003\b?\b\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003?\006\002\020\005J\t\020\t\032\0020\003H?\003J\t\020\n\032\0020\003H?\003J\035\020\013\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\003H?\001J\023\020\f\032\0020\r2\b\020\016\032\004\030\0010\001H?\002J\b\020\017\032\0020\020H\026J\006\020\021\032\0020\003J\t\020\022\032\0020\003H?\001R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\006\020\007R\026\020\004\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\b\020\007?\006\023"}, d2={"Lcom/askgps/personaltrackercore/database/model/PhoneNumber;", "", "name", "", "number", "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getNumber", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toListItem", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PhoneNumber
{
  private final String name;
  private final String number;
  
  public PhoneNumber(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.number = paramString2;
  }
  
  public final String component1()
  {
    return this.name;
  }
  
  public final String component2()
  {
    return this.number;
  }
  
  public final PhoneNumber copy(String paramString1, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "name");
    Intrinsics.checkParameterIsNotNull(paramString2, "number");
    return new PhoneNumber(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((PhoneNumber)this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof PhoneNumber)) {
      return false;
    }
    return !(Intrinsics.areEqual(this.number, ((PhoneNumber)paramObject).number) ^ true);
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final String getNumber()
  {
    return this.number;
  }
  
  public int hashCode()
  {
    return this.number.hashCode();
  }
  
  public final String toListItem()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.name);
    localStringBuilder.append('\n');
    localStringBuilder.append(this.number);
    return localStringBuilder.toString();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PhoneNumber(name=");
    localStringBuilder.append(this.name);
    localStringBuilder.append(", number=");
    localStringBuilder.append(this.number);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
