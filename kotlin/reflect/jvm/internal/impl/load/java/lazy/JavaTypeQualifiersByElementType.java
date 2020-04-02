package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.EnumMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.QualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;

public final class JavaTypeQualifiersByElementType
{
  private final EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> nullabilityQualifiers;
  
  public JavaTypeQualifiersByElementType(EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> paramEnumMap)
  {
    this.nullabilityQualifiers = paramEnumMap;
  }
  
  public final JavaTypeQualifiers get(AnnotationTypeQualifierResolver.QualifierApplicabilityType paramQualifierApplicabilityType)
  {
    paramQualifierApplicabilityType = (NullabilityQualifierWithMigrationStatus)this.nullabilityQualifiers.get(paramQualifierApplicabilityType);
    if (paramQualifierApplicabilityType != null)
    {
      Intrinsics.checkExpressionValueIsNotNull(paramQualifierApplicabilityType, "nullabilityQualifiers[ap…ilityType] ?: return null");
      return new JavaTypeQualifiers(paramQualifierApplicabilityType.getQualifier(), null, false, paramQualifierApplicabilityType.isForWarningOnly());
    }
    return null;
  }
  
  public final EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> getNullabilityQualifiers()
  {
    return this.nullabilityQualifiers;
  }
}
