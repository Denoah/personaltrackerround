package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.functions.Function0;

public abstract interface NotNullLazyValue<T>
  extends Function0<T>
{
  public abstract boolean isComputed();
}
