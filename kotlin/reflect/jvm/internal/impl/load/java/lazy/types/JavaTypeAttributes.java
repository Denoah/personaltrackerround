package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;

public final class JavaTypeAttributes
{
  private final JavaTypeFlexibility flexibility;
  private final TypeUsage howThisTypeIsUsed;
  private final boolean isForAnnotationParameter;
  private final TypeParameterDescriptor upperBoundOfTypeParameter;
  
  public JavaTypeAttributes(TypeUsage paramTypeUsage, JavaTypeFlexibility paramJavaTypeFlexibility, boolean paramBoolean, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    this.howThisTypeIsUsed = paramTypeUsage;
    this.flexibility = paramJavaTypeFlexibility;
    this.isForAnnotationParameter = paramBoolean;
    this.upperBoundOfTypeParameter = paramTypeParameterDescriptor;
  }
  
  public final JavaTypeAttributes copy(TypeUsage paramTypeUsage, JavaTypeFlexibility paramJavaTypeFlexibility, boolean paramBoolean, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeUsage, "howThisTypeIsUsed");
    Intrinsics.checkParameterIsNotNull(paramJavaTypeFlexibility, "flexibility");
    return new JavaTypeAttributes(paramTypeUsage, paramJavaTypeFlexibility, paramBoolean, paramTypeParameterDescriptor);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof JavaTypeAttributes))
      {
        paramObject = (JavaTypeAttributes)paramObject;
        if ((Intrinsics.areEqual(this.howThisTypeIsUsed, paramObject.howThisTypeIsUsed)) && (Intrinsics.areEqual(this.flexibility, paramObject.flexibility)))
        {
          int i;
          if (this.isForAnnotationParameter == paramObject.isForAnnotationParameter) {
            i = 1;
          } else {
            i = 0;
          }
          if ((i != 0) && (Intrinsics.areEqual(this.upperBoundOfTypeParameter, paramObject.upperBoundOfTypeParameter))) {
            break label86;
          }
        }
      }
      return false;
    }
    label86:
    return true;
  }
  
  public final JavaTypeFlexibility getFlexibility()
  {
    return this.flexibility;
  }
  
  public final TypeUsage getHowThisTypeIsUsed()
  {
    return this.howThisTypeIsUsed;
  }
  
  public final TypeParameterDescriptor getUpperBoundOfTypeParameter()
  {
    return this.upperBoundOfTypeParameter;
  }
  
  public int hashCode()
  {
    Object localObject = this.howThisTypeIsUsed;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.flexibility;
    int k;
    if (localObject != null) {
      k = localObject.hashCode();
    } else {
      k = 0;
    }
    int m = this.isForAnnotationParameter;
    int n = m;
    if (m != 0) {
      n = 1;
    }
    localObject = this.upperBoundOfTypeParameter;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return ((j * 31 + k) * 31 + n) * 31 + i;
  }
  
  public final boolean isForAnnotationParameter()
  {
    return this.isForAnnotationParameter;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("JavaTypeAttributes(howThisTypeIsUsed=");
    localStringBuilder.append(this.howThisTypeIsUsed);
    localStringBuilder.append(", flexibility=");
    localStringBuilder.append(this.flexibility);
    localStringBuilder.append(", isForAnnotationParameter=");
    localStringBuilder.append(this.isForAnnotationParameter);
    localStringBuilder.append(", upperBoundOfTypeParameter=");
    localStringBuilder.append(this.upperBoundOfTypeParameter);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public final JavaTypeAttributes withFlexibility(JavaTypeFlexibility paramJavaTypeFlexibility)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaTypeFlexibility, "flexibility");
    return copy$default(this, null, paramJavaTypeFlexibility, false, null, 13, null);
  }
}
