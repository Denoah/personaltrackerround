package kotlin.reflect.jvm.internal.impl.platform;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

public class TargetPlatform
  implements Collection<SimplePlatform>, KMappedMarker
{
  private final Set<SimplePlatform> componentPlatforms;
  
  public boolean addAll(Collection<? extends SimplePlatform> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void clear()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean contains(SimplePlatform paramSimplePlatform)
  {
    Intrinsics.checkParameterIsNotNull(paramSimplePlatform, "element");
    return this.componentPlatforms.contains(paramSimplePlatform);
  }
  
  public boolean containsAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    return this.componentPlatforms.containsAll(paramCollection);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((TargetPlatform)this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TargetPlatform)) {
      return false;
    }
    return !(Intrinsics.areEqual(this.componentPlatforms, ((TargetPlatform)paramObject).componentPlatforms) ^ true);
  }
  
  public final Set<SimplePlatform> getComponentPlatforms()
  {
    return this.componentPlatforms;
  }
  
  public int getSize()
  {
    return this.componentPlatforms.size();
  }
  
  public int hashCode()
  {
    return this.componentPlatforms.hashCode();
  }
  
  public boolean isEmpty()
  {
    return this.componentPlatforms.isEmpty();
  }
  
  public Iterator<SimplePlatform> iterator()
  {
    return this.componentPlatforms.iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public Object[] toArray()
  {
    return CollectionToArray.toArray(this);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return CollectionToArray.toArray(this, paramArrayOfT);
  }
  
  public String toString()
  {
    return PlatformUtilKt.getPresentableDescription(this);
  }
}
