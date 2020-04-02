package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;

public abstract interface CapturedTypeConstructor
  extends TypeConstructor
{
  public abstract TypeProjection getProjection();
}
