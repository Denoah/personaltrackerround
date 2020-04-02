package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;

public abstract interface JavaPrimitiveType
  extends JavaType
{
  public abstract PrimitiveType getType();
}
