package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public abstract interface StorageManager
{
  public abstract <T> T compute(Function0<? extends T> paramFunction0);
  
  public abstract <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues();
  
  public abstract <T> NotNullLazyValue<T> createLazyValue(Function0<? extends T> paramFunction0);
  
  public abstract <T> NotNullLazyValue<T> createLazyValueWithPostCompute(Function0<? extends T> paramFunction0, Function1<? super Boolean, ? extends T> paramFunction1, Function1<? super T, Unit> paramFunction11);
  
  public abstract <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(Function1<? super K, ? extends V> paramFunction1);
  
  public abstract <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(Function1<? super K, ? extends V> paramFunction1);
  
  public abstract <T> NullableLazyValue<T> createNullableLazyValue(Function0<? extends T> paramFunction0);
  
  public abstract <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(Function0<? extends T> paramFunction0, T paramT);
}
