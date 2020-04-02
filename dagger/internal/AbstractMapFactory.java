package dagger.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Provider;

abstract class AbstractMapFactory<K, V, V2>
  implements Factory<Map<K, V2>>
{
  private final Map<K, Provider<V>> contributingMap;
  
  AbstractMapFactory(Map<K, Provider<V>> paramMap)
  {
    this.contributingMap = Collections.unmodifiableMap(paramMap);
  }
  
  final Map<K, Provider<V>> contributingMap()
  {
    return this.contributingMap;
  }
  
  public static abstract class Builder<K, V, V2>
  {
    final LinkedHashMap<K, Provider<V>> map;
    
    Builder(int paramInt)
    {
      this.map = DaggerCollections.newLinkedHashMapWithExpectedSize(paramInt);
    }
    
    Builder<K, V, V2> put(K paramK, Provider<V> paramProvider)
    {
      this.map.put(Preconditions.checkNotNull(paramK, "key"), Preconditions.checkNotNull(paramProvider, "provider"));
      return this;
    }
    
    Builder<K, V, V2> putAll(Provider<Map<K, V2>> paramProvider)
    {
      if ((paramProvider instanceof DelegateFactory)) {
        return putAll(((DelegateFactory)paramProvider).getDelegate());
      }
      paramProvider = (AbstractMapFactory)paramProvider;
      this.map.putAll(paramProvider.contributingMap);
      return this;
    }
  }
}
