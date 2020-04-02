package dagger.internal;

import java.util.Collections;
import java.util.Map;

public final class MapBuilder<K, V>
{
  private final Map<K, V> contributions;
  
  private MapBuilder(int paramInt)
  {
    this.contributions = DaggerCollections.newLinkedHashMapWithExpectedSize(paramInt);
  }
  
  public static <K, V> MapBuilder<K, V> newMapBuilder(int paramInt)
  {
    return new MapBuilder(paramInt);
  }
  
  public Map<K, V> build()
  {
    if (this.contributions.size() != 0) {
      return Collections.unmodifiableMap(this.contributions);
    }
    return Collections.emptyMap();
  }
  
  public MapBuilder<K, V> put(K paramK, V paramV)
  {
    this.contributions.put(paramK, paramV);
    return this;
  }
  
  public MapBuilder<K, V> putAll(Map<K, V> paramMap)
  {
    this.contributions.putAll(paramMap);
    return this;
  }
}
