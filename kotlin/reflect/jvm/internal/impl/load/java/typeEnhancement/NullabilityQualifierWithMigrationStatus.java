package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;

public final class NullabilityQualifierWithMigrationStatus
{
  private final boolean isForWarningOnly;
  private final NullabilityQualifier qualifier;
  
  public NullabilityQualifierWithMigrationStatus(NullabilityQualifier paramNullabilityQualifier, boolean paramBoolean)
  {
    this.qualifier = paramNullabilityQualifier;
    this.isForWarningOnly = paramBoolean;
  }
  
  public final NullabilityQualifierWithMigrationStatus copy(NullabilityQualifier paramNullabilityQualifier, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramNullabilityQualifier, "qualifier");
    return new NullabilityQualifierWithMigrationStatus(paramNullabilityQualifier, paramBoolean);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof NullabilityQualifierWithMigrationStatus))
      {
        paramObject = (NullabilityQualifierWithMigrationStatus)paramObject;
        if (Intrinsics.areEqual(this.qualifier, paramObject.qualifier))
        {
          int i;
          if (this.isForWarningOnly == paramObject.isForWarningOnly) {
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
  
  public final NullabilityQualifier getQualifier()
  {
    return this.qualifier;
  }
  
  public int hashCode()
  {
    NullabilityQualifier localNullabilityQualifier = this.qualifier;
    int i;
    if (localNullabilityQualifier != null) {
      i = localNullabilityQualifier.hashCode();
    } else {
      i = 0;
    }
    int j = this.isForWarningOnly;
    int k = j;
    if (j != 0) {
      k = 1;
    }
    return i * 31 + k;
  }
  
  public final boolean isForWarningOnly()
  {
    return this.isForWarningOnly;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NullabilityQualifierWithMigrationStatus(qualifier=");
    localStringBuilder.append(this.qualifier);
    localStringBuilder.append(", isForWarningOnly=");
    localStringBuilder.append(this.isForWarningOnly);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
