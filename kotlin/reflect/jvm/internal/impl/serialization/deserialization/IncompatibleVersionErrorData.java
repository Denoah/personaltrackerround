package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

public final class IncompatibleVersionErrorData<T extends BinaryVersion>
{
  private final T actualVersion;
  private final ClassId classId;
  private final T expectedVersion;
  private final String filePath;
  
  public IncompatibleVersionErrorData(T paramT1, T paramT2, String paramString, ClassId paramClassId)
  {
    this.actualVersion = paramT1;
    this.expectedVersion = paramT2;
    this.filePath = paramString;
    this.classId = paramClassId;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof IncompatibleVersionErrorData))
      {
        paramObject = (IncompatibleVersionErrorData)paramObject;
        if ((Intrinsics.areEqual(this.actualVersion, paramObject.actualVersion)) && (Intrinsics.areEqual(this.expectedVersion, paramObject.expectedVersion)) && (Intrinsics.areEqual(this.filePath, paramObject.filePath)) && (Intrinsics.areEqual(this.classId, paramObject.classId))) {}
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
    Object localObject = this.actualVersion;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.expectedVersion;
    int k;
    if (localObject != null) {
      k = localObject.hashCode();
    } else {
      k = 0;
    }
    localObject = this.filePath;
    int m;
    if (localObject != null) {
      m = localObject.hashCode();
    } else {
      m = 0;
    }
    localObject = this.classId;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return ((j * 31 + k) * 31 + m) * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IncompatibleVersionErrorData(actualVersion=");
    localStringBuilder.append(this.actualVersion);
    localStringBuilder.append(", expectedVersion=");
    localStringBuilder.append(this.expectedVersion);
    localStringBuilder.append(", filePath=");
    localStringBuilder.append(this.filePath);
    localStringBuilder.append(", classId=");
    localStringBuilder.append(this.classId);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
