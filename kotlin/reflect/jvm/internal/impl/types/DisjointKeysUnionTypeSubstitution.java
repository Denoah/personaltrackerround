package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public final class DisjointKeysUnionTypeSubstitution
  extends TypeSubstitution
{
  public static final Companion Companion = new Companion(null);
  private final TypeSubstitution first;
  private final TypeSubstitution second;
  
  private DisjointKeysUnionTypeSubstitution(TypeSubstitution paramTypeSubstitution1, TypeSubstitution paramTypeSubstitution2)
  {
    this.first = paramTypeSubstitution1;
    this.second = paramTypeSubstitution2;
  }
  
  @JvmStatic
  public static final TypeSubstitution create(TypeSubstitution paramTypeSubstitution1, TypeSubstitution paramTypeSubstitution2)
  {
    return Companion.create(paramTypeSubstitution1, paramTypeSubstitution2);
  }
  
  public boolean approximateCapturedTypes()
  {
    boolean bool;
    if ((!this.first.approximateCapturedTypes()) && (!this.second.approximateCapturedTypes())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean approximateContravariantCapturedTypes()
  {
    boolean bool;
    if ((!this.first.approximateContravariantCapturedTypes()) && (!this.second.approximateContravariantCapturedTypes())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public Annotations filterAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    return this.second.filterAnnotations(this.first.filterAnnotations(paramAnnotations));
  }
  
  public TypeProjection get(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "key");
    TypeProjection localTypeProjection = this.first.get(paramKotlinType);
    if (localTypeProjection != null) {
      paramKotlinType = localTypeProjection;
    } else {
      paramKotlinType = this.second.get(paramKotlinType);
    }
    return paramKotlinType;
  }
  
  public boolean isEmpty()
  {
    return false;
  }
  
  public KotlinType prepareTopLevelType(KotlinType paramKotlinType, Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "topLevelType");
    Intrinsics.checkParameterIsNotNull(paramVariance, "position");
    return this.second.prepareTopLevelType(this.first.prepareTopLevelType(paramKotlinType, paramVariance), paramVariance);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final TypeSubstitution create(TypeSubstitution paramTypeSubstitution1, TypeSubstitution paramTypeSubstitution2)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeSubstitution1, "first");
      Intrinsics.checkParameterIsNotNull(paramTypeSubstitution2, "second");
      if (paramTypeSubstitution1.isEmpty()) {
        return paramTypeSubstitution2;
      }
      if (paramTypeSubstitution2.isEmpty()) {
        return paramTypeSubstitution1;
      }
      return (TypeSubstitution)new DisjointKeysUnionTypeSubstitution(paramTypeSubstitution1, paramTypeSubstitution2, null);
    }
  }
}
