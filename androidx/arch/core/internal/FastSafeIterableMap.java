package androidx.arch.core.internal;

import java.util.HashMap;
import java.util.Map.Entry;

public class FastSafeIterableMap<K, V>
  extends SafeIterableMap<K, V>
{
  private HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap();
  
  public FastSafeIterableMap() {}
  
  public Map.Entry<K, V> ceil(K paramK)
  {
    if (contains(paramK)) {
      return ((SafeIterableMap.Entry)this.mHashMap.get(paramK)).mPrevious;
    }
    return null;
  }
  
  public boolean contains(K paramK)
  {
    return this.mHashMap.containsKey(paramK);
  }
  
  protected SafeIterableMap.Entry<K, V> get(K paramK)
  {
    return (SafeIterableMap.Entry)this.mHashMap.get(paramK);
  }
  
  public V putIfAbsent(K paramK, V paramV)
  {
    SafeIterableMap.Entry localEntry = get(paramK);
    if (localEntry != null) {
      return localEntry.mValue;
    }
    this.mHashMap.put(paramK, put(paramK, paramV));
    return null;
  }
  
  public V remove(K paramK)
  {
    Object localObject = super.remove(paramK);
    this.mHashMap.remove(paramK);
    return localObject;
  }
}
