package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

final class EnhancementResult<T>
{
  private final Annotations enhancementAnnotations;
  private final T result;
  
  public EnhancementResult(T paramT, Annotations paramAnnotations)
  {
    this.result = paramT;
    this.enhancementAnnotations = paramAnnotations;
  }
  
  public final T component1()
  {
    return this.result;
  }
  
  public final Annotations component2()
  {
    return this.enhancementAnnotations;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof EnhancementResult))
      {
        paramObject = (EnhancementResult)paramObject;
        if ((Intrinsics.areEqual(this.result, paramObject.result)) && (Intrinsics.areEqual(this.enhancementAnnotations, paramObject.enhancementAnnotations))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    Object localObject = this.result;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.enhancementAnnotations;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("EnhancementResult(result=");
    localStringBuilder.append(this.result);
    localStringBuilder.append(", enhancementAnnotations=");
    localStringBuilder.append(this.enhancementAnnotations);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
