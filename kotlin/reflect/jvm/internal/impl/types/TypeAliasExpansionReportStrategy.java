package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;

public abstract interface TypeAliasExpansionReportStrategy
{
  public abstract void boundsViolationInSubstitution(KotlinType paramKotlinType1, KotlinType paramKotlinType2, KotlinType paramKotlinType3, TypeParameterDescriptor paramTypeParameterDescriptor);
  
  public abstract void conflictingProjection(TypeAliasDescriptor paramTypeAliasDescriptor, TypeParameterDescriptor paramTypeParameterDescriptor, KotlinType paramKotlinType);
  
  public abstract void recursiveTypeAlias(TypeAliasDescriptor paramTypeAliasDescriptor);
  
  public abstract void repeatedAnnotation(AnnotationDescriptor paramAnnotationDescriptor);
  
  public static final class DO_NOTHING
    implements TypeAliasExpansionReportStrategy
  {
    public static final DO_NOTHING INSTANCE = new DO_NOTHING();
    
    private DO_NOTHING() {}
    
    public void boundsViolationInSubstitution(KotlinType paramKotlinType1, KotlinType paramKotlinType2, KotlinType paramKotlinType3, TypeParameterDescriptor paramTypeParameterDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinType1, "bound");
      Intrinsics.checkParameterIsNotNull(paramKotlinType2, "unsubstitutedArgument");
      Intrinsics.checkParameterIsNotNull(paramKotlinType3, "argument");
      Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "typeParameter");
    }
    
    public void conflictingProjection(TypeAliasDescriptor paramTypeAliasDescriptor, TypeParameterDescriptor paramTypeParameterDescriptor, KotlinType paramKotlinType)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "typeAlias");
      Intrinsics.checkParameterIsNotNull(paramKotlinType, "substitutedArgument");
    }
    
    public void recursiveTypeAlias(TypeAliasDescriptor paramTypeAliasDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "typeAlias");
    }
    
    public void repeatedAnnotation(AnnotationDescriptor paramAnnotationDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotation");
    }
  }
}
