package dagger.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Provider;

public final class MapFactory<K, V>
  extends AbstractMapFactory<K, V, V>
{
  private static final Provider<Map<Object, Object>> EMPTY = InstanceFactory.create(Collections.emptyMap());
  
  private MapFactory(Map<K, Provider<V>> paramMap)
  {
    super(paramMap);
  }
  
  public static <K, V> Builder<K, V> builder(int paramInt)
  {
    return new Builder(paramInt, null);
  }
  
  public static <K, V> Provider<Map<K, V>> emptyMapProvider()
  {
    return EMPTY;
  }
  
  public Map<K, V> get()
  {
    LinkedHashMap localLinkedHashMap = DaggerCollections.newLinkedHashMapWithExpectedSize(contributingMap().size());
    Iterator localIterator = contributingMap().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localLinkedHashMap.put(localEntry.getKey(), ((Provider)localEntry.getValue()).get());
    }
    return Collections.unmodifiableMap(localLinkedHashMap);
  }
  
  public static final class Builder<K, V>
    extends AbstractMapFactory.Builder<K, V, V>
  {
    private Builder(int paramInt)
    {
      super();
    }
    
    public MapFactory<K, V> build()
    {
      return new MapFactory(this.map, null);
    }
    
    public Builder<K, V> put(K paramK, Provider<V> paramProvider)
    {
      super.put(paramK, paramProvider);
      return this;
    }
    
    public Builder<K, V> putAll(Provider<Map<K, V>> paramProvider)
    {
      super.putAll(paramProvider);
      return this;
    }
  }
}
