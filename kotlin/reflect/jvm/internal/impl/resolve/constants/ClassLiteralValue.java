package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

public final class ClassLiteralValue
{
  private final int arrayNestedness;
  private final ClassId classId;
  
  public ClassLiteralValue(ClassId paramClassId, int paramInt)
  {
    this.classId = paramClassId;
    this.arrayNestedness = paramInt;
  }
  
  public final ClassId component1()
  {
    return this.classId;
  }
  
  public final int component2()
  {
    return this.arrayNestedness;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof ClassLiteralValue))
      {
        paramObject = (ClassLiteralValue)paramObject;
        if (Intrinsics.areEqual(this.classId, paramObject.classId))
        {
          int i;
          if (this.arrayNestedness == paramObject.arrayNestedness) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0) {
            break label58;
          }
        }
      }
      return false;
    }
    label58:
    return true;
  }
  
  public final int getArrayNestedness()
  {
    return this.arrayNestedness;
  }
  
  public final ClassId getClassId()
  {
    return this.classId;
  }
  
  public int hashCode()
  {
    ClassId localClassId = this.classId;
    int i;
    if (localClassId != null) {
      i = localClassId.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + this.arrayNestedness;
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder();
    int i = this.arrayNestedness;
    int j = 0;
    for (int k = 0; k < i; k++) {
      ((StringBuilder)localObject).append("kotlin/Array<");
    }
    ((StringBuilder)localObject).append(this.classId);
    i = this.arrayNestedness;
    for (k = j; k < i; k++) {
      ((StringBuilder)localObject).append(">");
    }
    localObject = ((StringBuilder)localObject).toString();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "StringBuilder().apply(builderAction).toString()");
    return localObject;
  }
}
