package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

public final class StorageKt
{
  public static final <T> T getValue(NotNullLazyValue<? extends T> paramNotNullLazyValue, Object paramObject, KProperty<?> paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramNotNullLazyValue, "$this$getValue");
    Intrinsics.checkParameterIsNotNull(paramKProperty, "p");
    return paramNotNullLazyValue.invoke();
  }
  
  public static final <T> T getValue(NullableLazyValue<? extends T> paramNullableLazyValue, Object paramObject, KProperty<?> paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramNullableLazyValue, "$this$getValue");
    Intrinsics.checkParameterIsNotNull(paramKProperty, "p");
    return paramNullableLazyValue.invoke();
  }
}
