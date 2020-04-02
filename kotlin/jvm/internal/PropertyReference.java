package kotlin.jvm.internal;

import kotlin.reflect.KProperty;

public abstract class PropertyReference
  extends CallableReference
  implements KProperty
{
  public PropertyReference() {}
  
  public PropertyReference(Object paramObject)
  {
    super(paramObject);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof PropertyReference))
    {
      paramObject = (PropertyReference)paramObject;
      if ((!getOwner().equals(paramObject.getOwner())) || (!getName().equals(paramObject.getName())) || (!getSignature().equals(paramObject.getSignature())) || (!Intrinsics.areEqual(getBoundReceiver(), paramObject.getBoundReceiver()))) {
        bool = false;
      }
      return bool;
    }
    if ((paramObject instanceof KProperty)) {
      return paramObject.equals(compute());
    }
    return false;
  }
  
  protected KProperty getReflected()
  {
    return (KProperty)super.getReflected();
  }
  
  public int hashCode()
  {
    return (getOwner().hashCode() * 31 + getName().hashCode()) * 31 + getSignature().hashCode();
  }
  
  public boolean isConst()
  {
    return getReflected().isConst();
  }
  
  public boolean isLateinit()
  {
    return getReflected().isLateinit();
  }
  
  public String toString()
  {
    Object localObject = compute();
    if (localObject != this) {
      return localObject.toString();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("property ");
    ((StringBuilder)localObject).append(getName());
    ((StringBuilder)localObject).append(" (Kotlin reflection is not available)");
    return ((StringBuilder)localObject).toString();
  }
}
