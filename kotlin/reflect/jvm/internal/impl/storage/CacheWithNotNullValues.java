package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.functions.Function0;

public abstract interface CacheWithNotNullValues<K, V>
{
  public abstract V computeIfAbsent(K paramK, Function0<? extends V> paramFunction0);
}
