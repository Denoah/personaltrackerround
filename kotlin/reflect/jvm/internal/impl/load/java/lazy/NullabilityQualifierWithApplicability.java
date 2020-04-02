package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.QualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;

public final class NullabilityQualifierWithApplicability
{
  private final NullabilityQualifierWithMigrationStatus nullabilityQualifier;
  private final Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> qualifierApplicabilityTypes;
  
  public NullabilityQualifierWithApplicability(NullabilityQualifierWithMigrationStatus paramNullabilityQualifierWithMigrationStatus, Collection<? extends AnnotationTypeQualifierResolver.QualifierApplicabilityType> paramCollection)
  {
    this.nullabilityQualifier = paramNullabilityQualifierWithMigrationStatus;
    this.qualifierApplicabilityTypes = paramCollection;
  }
  
  public final NullabilityQualifierWithMigrationStatus component1()
  {
    return this.nullabilityQualifier;
  }
  
  public final Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> component2()
  {
    return this.qualifierApplicabilityTypes;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof NullabilityQualifierWithApplicability))
      {
        paramObject = (NullabilityQualifierWithApplicability)paramObject;
        if ((Intrinsics.areEqual(this.nullabilityQualifier, paramObject.nullabilityQualifier)) && (Intrinsics.areEqual(this.qualifierApplicabilityTypes, paramObject.qualifierApplicabilityTypes))) {}
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
    Object localObject = this.nullabilityQualifier;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.qualifierApplicabilityTypes;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NullabilityQualifierWithApplicability(nullabilityQualifier=");
    localStringBuilder.append(this.nullabilityQualifier);
    localStringBuilder.append(", qualifierApplicabilityTypes=");
    localStringBuilder.append(this.qualifierApplicabilityTypes);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
