package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public class DelegatedTypeSubstitution
  extends TypeSubstitution
{
  private final TypeSubstitution substitution;
  
  public DelegatedTypeSubstitution(TypeSubstitution paramTypeSubstitution)
  {
    this.substitution = paramTypeSubstitution;
  }
  
  public boolean approximateCapturedTypes()
  {
    return this.substitution.approximateCapturedTypes();
  }
  
  public boolean approximateContravariantCapturedTypes()
  {
    return this.substitution.approximateContravariantCapturedTypes();
  }
  
  public Annotations filterAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    return this.substitution.filterAnnotations(paramAnnotations);
  }
  
  public TypeProjection get(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "key");
    return this.substitution.get(paramKotlinType);
  }
  
  public boolean isEmpty()
  {
    return this.substitution.isEmpty();
  }
  
  public KotlinType prepareTopLevelType(KotlinType paramKotlinType, Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "topLevelType");
    Intrinsics.checkParameterIsNotNull(paramVariance, "position");
    return this.substitution.prepareTopLevelType(paramKotlinType, paramVariance);
  }
}
