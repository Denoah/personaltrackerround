package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;

final class NameAndSignature
{
  private final Name name;
  private final String signature;
  
  public NameAndSignature(Name paramName, String paramString)
  {
    this.name = paramName;
    this.signature = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof NameAndSignature))
      {
        paramObject = (NameAndSignature)paramObject;
        if ((Intrinsics.areEqual(this.name, paramObject.name)) && (Intrinsics.areEqual(this.signature, paramObject.signature))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final Name getName()
  {
    return this.name;
  }
  
  public final String getSignature()
  {
    return this.signature;
  }
  
  public int hashCode()
  {
    Object localObject = this.name;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.signature;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NameAndSignature(name=");
    localStringBuilder.append(this.name);
    localStringBuilder.append(", signature=");
    localStringBuilder.append(this.signature);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
