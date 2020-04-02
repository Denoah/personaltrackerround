package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

final class TypeAndDefaultQualifiers
{
  private final JavaTypeQualifiers defaultQualifiers;
  private final KotlinType type;
  
  public TypeAndDefaultQualifiers(KotlinType paramKotlinType, JavaTypeQualifiers paramJavaTypeQualifiers)
  {
    this.type = paramKotlinType;
    this.defaultQualifiers = paramJavaTypeQualifiers;
  }
  
  public final KotlinType component1()
  {
    return this.type;
  }
  
  public final JavaTypeQualifiers component2()
  {
    return this.defaultQualifiers;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof TypeAndDefaultQualifiers))
      {
        paramObject = (TypeAndDefaultQualifiers)paramObject;
        if ((Intrinsics.areEqual(this.type, paramObject.type)) && (Intrinsics.areEqual(this.defaultQualifiers, paramObject.defaultQualifiers))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final KotlinType getType()
  {
    return this.type;
  }
  
  public int hashCode()
  {
    Object localObject = this.type;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.defaultQualifiers;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TypeAndDefaultQualifiers(type=");
    localStringBuilder.append(this.type);
    localStringBuilder.append(", defaultQualifiers=");
    localStringBuilder.append(this.defaultQualifiers);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
