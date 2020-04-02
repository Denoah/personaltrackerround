package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.jvm.internal.Intrinsics;

public final class ApproximationBounds<T>
{
  private final T lower;
  private final T upper;
  
  public ApproximationBounds(T paramT1, T paramT2)
  {
    this.lower = paramT1;
    this.upper = paramT2;
  }
  
  public final T component1()
  {
    return this.lower;
  }
  
  public final T component2()
  {
    return this.upper;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof ApproximationBounds))
      {
        paramObject = (ApproximationBounds)paramObject;
        if ((Intrinsics.areEqual(this.lower, paramObject.lower)) && (Intrinsics.areEqual(this.upper, paramObject.upper))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final T getLower()
  {
    return this.lower;
  }
  
  public final T getUpper()
  {
    return this.upper;
  }
  
  public int hashCode()
  {
    Object localObject = this.lower;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.upper;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ApproximationBounds(lower=");
    localStringBuilder.append(this.lower);
    localStringBuilder.append(", upper=");
    localStringBuilder.append(this.upper);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
