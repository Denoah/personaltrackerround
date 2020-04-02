package kotlin.reflect.jvm.internal.impl.types;

public abstract class TypeProjectionBase
  implements TypeProjection
{
  public TypeProjectionBase() {}
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TypeProjection)) {
      return false;
    }
    paramObject = (TypeProjection)paramObject;
    if (isStarProjection() != paramObject.isStarProjection()) {
      return false;
    }
    if (getProjectionKind() != paramObject.getProjectionKind()) {
      return false;
    }
    return getType().equals(paramObject.getType());
  }
  
  public int hashCode()
  {
    int i = getProjectionKind().hashCode();
    int j;
    if (TypeUtils.noExpectedType(getType()))
    {
      j = i * 31 + 19;
    }
    else
    {
      if (isStarProjection()) {
        j = 17;
      } else {
        j = getType().hashCode();
      }
      j = i * 31 + j;
    }
    return j;
  }
  
  public String toString()
  {
    if (isStarProjection()) {
      return "*";
    }
    if (getProjectionKind() == Variance.INVARIANT) {
      return getType().toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getProjectionKind());
    localStringBuilder.append(" ");
    localStringBuilder.append(getType());
    return localStringBuilder.toString();
  }
}
