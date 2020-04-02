package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.NullabilityQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

public final class AnnotationTypeQualifierResolverKt
{
  private static final Map<FqName, NullabilityQualifierWithApplicability> BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS = MapsKt.mapOf(new Pair[] { TuplesKt.to(new FqName("javax.annotation.ParametersAreNullableByDefault"), new NullabilityQualifierWithApplicability(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null), (Collection)CollectionsKt.listOf(AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER))), TuplesKt.to(new FqName("javax.annotation.ParametersAreNonnullByDefault"), new NullabilityQualifierWithApplicability(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null), (Collection)CollectionsKt.listOf(AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER))) });
  private static final Set<FqName> BUILT_IN_TYPE_QUALIFIER_FQ_NAMES = SetsKt.setOf(new FqName[] { JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION(), JvmAnnotationNamesKt.getJAVAX_CHECKFORNULL_ANNOTATION() });
  private static final FqName MIGRATION_ANNOTATION_FQNAME;
  private static final FqName TYPE_QUALIFIER_DEFAULT_FQNAME;
  private static final FqName TYPE_QUALIFIER_FQNAME;
  private static final FqName TYPE_QUALIFIER_NICKNAME_FQNAME = new FqName("javax.annotation.meta.TypeQualifierNickname");
  
  static
  {
    TYPE_QUALIFIER_FQNAME = new FqName("javax.annotation.meta.TypeQualifier");
    TYPE_QUALIFIER_DEFAULT_FQNAME = new FqName("javax.annotation.meta.TypeQualifierDefault");
    MIGRATION_ANNOTATION_FQNAME = new FqName("kotlin.annotations.jvm.UnderMigration");
  }
  
  public static final Map<FqName, NullabilityQualifierWithApplicability> getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS()
  {
    return BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS;
  }
  
  public static final FqName getMIGRATION_ANNOTATION_FQNAME()
  {
    return MIGRATION_ANNOTATION_FQNAME;
  }
  
  public static final FqName getTYPE_QUALIFIER_DEFAULT_FQNAME()
  {
    return TYPE_QUALIFIER_DEFAULT_FQNAME;
  }
  
  public static final FqName getTYPE_QUALIFIER_NICKNAME_FQNAME()
  {
    return TYPE_QUALIFIER_NICKNAME_FQNAME;
  }
  
  private static final boolean isAnnotatedWithTypeQualifier(ClassDescriptor paramClassDescriptor)
  {
    boolean bool;
    if ((!BUILT_IN_TYPE_QUALIFIER_FQ_NAMES.contains(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)paramClassDescriptor))) && (!paramClassDescriptor.getAnnotations().hasAnnotation(TYPE_QUALIFIER_FQNAME))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
