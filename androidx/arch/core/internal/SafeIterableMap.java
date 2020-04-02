package androidx.arch.core.internal;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public class SafeIterableMap<K, V>
  implements Iterable<Map.Entry<K, V>>
{
  private Entry<K, V> mEnd;
  private WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap();
  private int mSize = 0;
  Entry<K, V> mStart;
  
  public SafeIterableMap() {}
  
  public Iterator<Map.Entry<K, V>> descendingIterator()
  {
    DescendingIterator localDescendingIterator = new DescendingIterator(this.mEnd, this.mStart);
    this.mIterators.put(localDescendingIterator, Boolean.valueOf(false));
    return localDescendingIterator;
  }
  
  public Map.Entry<K, V> eldest()
  {
    return this.mStart;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof SafeIterableMap)) {
      return false;
    }
    Object localObject1 = (SafeIterableMap)paramObject;
    if (size() != ((SafeIterableMap)localObject1).size()) {
      return false;
    }
    paramObject = iterator();
    Iterator localIterator = ((SafeIterableMap)localObject1).iterator();
    while ((paramObject.hasNext()) && (localIterator.hasNext()))
    {
      localObject1 = (Map.Entry)paramObject.next();
      Object localObject2 = localIterator.next();
      if (((localObject1 == null) && (localObject2 != null)) || ((localObject1 != null) && (!((Map.Entry)localObject1).equals(localObject2)))) {
        return false;
      }
    }
    if ((paramObject.hasNext()) || (localIterator.hasNext())) {
      bool = false;
    }
    return bool;
  }
  
  protected Entry<K, V> get(K paramK)
  {
    for (Entry localEntry = this.mStart; (localEntry != null) && (!localEntry.mKey.equals(paramK)); localEntry = localEntry.mNext) {}
    return localEntry;
  }
  
  public int hashCode()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext()) {
      i += ((Map.Entry)localIterator.next()).hashCode();
    }
    return i;
  }
  
  public Iterator<Map.Entry<K, V>> iterator()
  {
    AscendingIterator localAscendingIterator = new AscendingIterator(this.mStart, this.mEnd);
    this.mIterators.put(localAscendingIterator, Boolean.valueOf(false));
    return localAscendingIterator;
  }
  
  public SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions()
  {
    IteratorWithAdditions localIteratorWithAdditions = new IteratorWithAdditions();
    this.mIterators.put(localIteratorWithAdditions, Boolean.valueOf(false));
    return localIteratorWithAdditions;
  }
  
  public Map.Entry<K, V> newest()
  {
    return this.mEnd;
  }
  
  protected Entry<K, V> put(K paramK, V paramV)
  {
    paramK = new Entry(paramK, paramV);
    this.mSize += 1;
    paramV = this.mEnd;
    if (paramV == null)
    {
      this.mStart = paramK;
      this.mEnd = paramK;
      return paramK;
    }
    paramV.mNext = paramK;
    paramK.mPrevious = this.mEnd;
    this.mEnd = paramK;
    return paramK;
  }
  
  public V putIfAbsent(K paramK, V paramV)
  {
    Entry localEntry = get(paramK);
    if (localEntry != null) {
      return localEntry.mValue;
    }
    put(paramK, paramV);
    return null;
  }
  
  public V remove(K paramK)
  {
    paramK = get(paramK);
    if (paramK == null) {
      return null;
    }
    this.mSize -= 1;
    if (!this.mIterators.isEmpty())
    {
      Iterator localIterator = this.mIterators.keySet().iterator();
      while (localIterator.hasNext()) {
        ((SupportRemove)localIterator.next()).supportRemove(paramK);
      }
    }
    if (paramK.mPrevious != null) {
      paramK.mPrevious.mNext = paramK.mNext;
    } else {
      this.mStart = paramK.mNext;
    }
    if (paramK.mNext != null) {
      paramK.mNext.mPrevious = paramK.mPrevious;
    } else {
      this.mEnd = paramK.mPrevious;
    }
    paramK.mNext = null;
    paramK.mPrevious = null;
    return paramK.mValue;
  }
  
  public int size()
  {
    return this.mSize;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((Map.Entry)localIterator.next()).toString());
      if (localIterator.hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  static class AscendingIterator<K, V>
    extends SafeIterableMap.ListIterator<K, V>
  {
    AscendingIterator(SafeIterableMap.Entry<K, V> paramEntry1, SafeIterableMap.Entry<K, V> paramEntry2)
    {
      super(paramEntry2);
    }
    
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> paramEntry)
    {
      return paramEntry.mPrevious;
    }
    
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> paramEntry)
    {
      return paramEntry.mNext;
    }
  }
  
  private static class DescendingIterator<K, V>
    extends SafeIterableMap.ListIterator<K, V>
  {
    DescendingIterator(SafeIterableMap.Entry<K, V> paramEntry1, SafeIterableMap.Entry<K, V> paramEntry2)
    {
      super(paramEntry2);
    }
    
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> paramEntry)
    {
      return paramEntry.mNext;
    }
    
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> paramEntry)
    {
      return paramEntry.mPrevious;
    }
  }
  
  static class Entry<K, V>
    implements Map.Entry<K, V>
  {
    final K mKey;
    Entry<K, V> mNext;
    Entry<K, V> mPrevious;
    final V mValue;
    
    Entry(K paramK, V paramV)
    {
      this.mKey = paramK;
      this.mValue = paramV;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof Entry)) {
        return false;
      }
      paramObject = (Entry)paramObject;
      if ((!this.mKey.equals(paramObject.mKey)) || (!this.mValue.equals(paramObject.mValue))) {
        bool = false;
      }
      return bool;
    }
    
    public K getKey()
    {
      return this.mKey;
    }
    
    public V getValue()
    {
      return this.mValue;
    }
    
    public int hashCode()
    {
      return this.mKey.hashCode() ^ this.mValue.hashCode();
    }
    
    public V setValue(V paramV)
    {
      throw new UnsupportedOperationException("An entry modification is not supported");
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.mKey);
      localStringBuilder.append("=");
      localStringBuilder.append(this.mValue);
      return localStringBuilder.toString();
    }
  }
  
  private class IteratorWithAdditions
    implements Iterator<Map.Entry<K, V>>, SafeIterableMap.SupportRemove<K, V>
  {
    private boolean mBeforeStart = true;
    private SafeIterableMap.Entry<K, V> mCurrent;
    
    IteratorWithAdditions() {}
    
    public boolean hasNext()
    {
      boolean bool1 = this.mBeforeStart;
      boolean bool2 = true;
      boolean bool3 = true;
      if (bool1)
      {
        if (SafeIterableMap.this.mStart == null) {
          bool3 = false;
        }
        return bool3;
      }
      SafeIterableMap.Entry localEntry = this.mCurrent;
      if ((localEntry != null) && (localEntry.mNext != null)) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    
    public Map.Entry<K, V> next()
    {
      if (this.mBeforeStart)
      {
        this.mBeforeStart = false;
        this.mCurrent = SafeIterableMap.this.mStart;
      }
      else
      {
        SafeIterableMap.Entry localEntry = this.mCurrent;
        if (localEntry != null) {
          localEntry = localEntry.mNext;
        } else {
          localEntry = null;
        }
        this.mCurrent = localEntry;
      }
      return this.mCurrent;
    }
    
    public void supportRemove(SafeIterableMap.Entry<K, V> paramEntry)
    {
      SafeIterableMap.Entry localEntry = this.mCurrent;
      if (paramEntry == localEntry)
      {
        paramEntry = localEntry.mPrevious;
        this.mCurrent = paramEntry;
        boolean bool;
        if (paramEntry == null) {
          bool = true;
        } else {
          bool = false;
        }
        this.mBeforeStart = bool;
      }
    }
  }
  
  private static abstract class ListIterator<K, V>
    implements Iterator<Map.Entry<K, V>>, SafeIterableMap.SupportRemove<K, V>
  {
    SafeIterableMap.Entry<K, V> mExpectedEnd;
    SafeIterableMap.Entry<K, V> mNext;
    
    ListIterator(SafeIterableMap.Entry<K, V> paramEntry1, SafeIterableMap.Entry<K, V> paramEntry2)
    {
      this.mExpectedEnd = paramEntry2;
      this.mNext = paramEntry1;
    }
    
    private SafeIterableMap.Entry<K, V> nextNode()
    {
      SafeIterableMap.Entry localEntry1 = this.mNext;
      SafeIterableMap.Entry localEntry2 = this.mExpectedEnd;
      if ((localEntry1 != localEntry2) && (localEntry2 != null)) {
        return forward(localEntry1);
      }
      return null;
    }
    
    abstract SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> paramEntry);
    
    abstract SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> paramEntry);
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.mNext != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Map.Entry<K, V> next()
    {
      SafeIterableMap.Entry localEntry = this.mNext;
      this.mNext = nextNode();
      return localEntry;
    }
    
    public void supportRemove(SafeIterableMap.Entry<K, V> paramEntry)
    {
      if ((this.mExpectedEnd == paramEntry) && (paramEntry == this.mNext))
      {
        this.mNext = null;
        this.mExpectedEnd = null;
      }
      SafeIterableMap.Entry localEntry = this.mExpectedEnd;
      if (localEntry == paramEntry) {
        this.mExpectedEnd = backward(localEntry);
      }
      if (this.mNext == paramEntry) {
        this.mNext = nextNode();
      }
    }
  }
  
  static abstract interface SupportRemove<K, V>
  {
    public abstract void supportRemove(SafeIterableMap.Entry<K, V> paramEntry);
  }
}
