package kotlin.reflect.jvm.internal.pcollections;

public final class HashPMap<K, V>
{
  private static final HashPMap<Object, Object> EMPTY = new HashPMap(IntTreePMap.empty(), 0);
  private final IntTreePMap<ConsPStack<MapEntry<K, V>>> intMap;
  private final int size;
  
  private HashPMap(IntTreePMap<ConsPStack<MapEntry<K, V>>> paramIntTreePMap, int paramInt)
  {
    this.intMap = paramIntTreePMap;
    this.size = paramInt;
  }
  
  public static <K, V> HashPMap<K, V> empty()
  {
    HashPMap localHashPMap = EMPTY;
    if (localHashPMap == null) {
      $$$reportNull$$$0(0);
    }
    return localHashPMap;
  }
  
  private ConsPStack<MapEntry<K, V>> getEntries(int paramInt)
  {
    ConsPStack localConsPStack1 = (ConsPStack)this.intMap.get(paramInt);
    ConsPStack localConsPStack2 = localConsPStack1;
    if (localConsPStack1 == null) {
      localConsPStack2 = ConsPStack.empty();
    }
    return localConsPStack2;
  }
  
  private static <K, V> int keyIndexIn(ConsPStack<MapEntry<K, V>> paramConsPStack, Object paramObject)
  {
    for (int i = 0; (paramConsPStack != null) && (paramConsPStack.size() > 0); i++)
    {
      if (((MapEntry)paramConsPStack.first).key.equals(paramObject)) {
        return i;
      }
      paramConsPStack = paramConsPStack.rest;
    }
    return -1;
  }
  
  public boolean containsKey(Object paramObject)
  {
    boolean bool;
    if (keyIndexIn(getEntries(paramObject.hashCode()), paramObject) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public V get(Object paramObject)
  {
    for (ConsPStack localConsPStack = getEntries(paramObject.hashCode()); (localConsPStack != null) && (localConsPStack.size() > 0); localConsPStack = localConsPStack.rest)
    {
      MapEntry localMapEntry = (MapEntry)localConsPStack.first;
      if (localMapEntry.key.equals(paramObject)) {
        return localMapEntry.value;
      }
    }
    return null;
  }
  
  public HashPMap<K, V> minus(Object paramObject)
  {
    ConsPStack localConsPStack = getEntries(paramObject.hashCode());
    int i = keyIndexIn(localConsPStack, paramObject);
    if (i == -1) {
      return this;
    }
    localConsPStack = localConsPStack.minus(i);
    if (localConsPStack.size() == 0) {
      return new HashPMap(this.intMap.minus(paramObject.hashCode()), this.size - 1);
    }
    return new HashPMap(this.intMap.plus(paramObject.hashCode(), localConsPStack), this.size - 1);
  }
  
  public HashPMap<K, V> plus(K paramK, V paramV)
  {
    ConsPStack localConsPStack1 = getEntries(paramK.hashCode());
    int i = localConsPStack1.size();
    int j = keyIndexIn(localConsPStack1, paramK);
    ConsPStack localConsPStack2 = localConsPStack1;
    if (j != -1) {
      localConsPStack2 = localConsPStack1.minus(j);
    }
    paramV = localConsPStack2.plus(new MapEntry(paramK, paramV));
    return new HashPMap(this.intMap.plus(paramK.hashCode(), paramV), this.size - i + paramV.size());
  }
  
  public int size()
  {
    return this.size;
  }
}
