package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public abstract class TypeSubstitution
{
  public static final Companion Companion = new Companion(null);
  public static final TypeSubstitution EMPTY = (TypeSubstitution)new TypeSubstitution.Companion.EMPTY.1();
  
  public TypeSubstitution() {}
  
  public boolean approximateCapturedTypes()
  {
    return false;
  }
  
  public boolean approximateContravariantCapturedTypes()
  {
    return false;
  }
  
  public final TypeSubstitutor buildSubstitutor()
  {
    TypeSubstitutor localTypeSubstitutor = TypeSubstitutor.create(this);
    Intrinsics.checkExpressionValueIsNotNull(localTypeSubstitutor, "TypeSubstitutor.create(this)");
    return localTypeSubstitutor;
  }
  
  public Annotations filterAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    return paramAnnotations;
  }
  
  public abstract TypeProjection get(KotlinType paramKotlinType);
  
  public boolean isEmpty()
  {
    return false;
  }
  
  public KotlinType prepareTopLevelType(KotlinType paramKotlinType, Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "topLevelType");
    Intrinsics.checkParameterIsNotNull(paramVariance, "position");
    return paramKotlinType;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}
