package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public class TypeProjectionImpl
  extends TypeProjectionBase
{
  private final Variance projection;
  private final KotlinType type;
  
  public TypeProjectionImpl(KotlinType paramKotlinType)
  {
    this(Variance.INVARIANT, paramKotlinType);
  }
  
  public TypeProjectionImpl(Variance paramVariance, KotlinType paramKotlinType)
  {
    this.projection = paramVariance;
    this.type = paramKotlinType;
  }
  
  public Variance getProjectionKind()
  {
    Variance localVariance = this.projection;
    if (localVariance == null) {
      $$$reportNull$$$0(3);
    }
    return localVariance;
  }
  
  public KotlinType getType()
  {
    KotlinType localKotlinType = this.type;
    if (localKotlinType == null) {
      $$$reportNull$$$0(4);
    }
    return localKotlinType;
  }
  
  public boolean isStarProjection()
  {
    return false;
  }
  
  public TypeProjection refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(5);
    }
    return new TypeProjectionImpl(this.projection, paramKotlinTypeRefiner.refineType(this.type));
  }
}
