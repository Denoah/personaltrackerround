package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;

public abstract interface TypeProjection
  extends TypeArgumentMarker
{
  public abstract Variance getProjectionKind();
  
  public abstract KotlinType getType();
  
  public abstract boolean isStarProjection();
  
  public abstract TypeProjection refine(KotlinTypeRefiner paramKotlinTypeRefiner);
}
