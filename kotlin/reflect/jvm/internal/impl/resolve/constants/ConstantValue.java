package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract class ConstantValue<T>
{
  private final T value;
  
  public ConstantValue(T paramT)
  {
    this.value = paramT;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((ConstantValue)this != paramObject)
    {
      Object localObject1 = getValue();
      bool = paramObject instanceof ConstantValue;
      Object localObject2 = null;
      if (!bool) {
        paramObject = null;
      }
      ConstantValue localConstantValue = (ConstantValue)paramObject;
      paramObject = localObject2;
      if (localConstantValue != null) {
        paramObject = localConstantValue.getValue();
      }
      if (!Intrinsics.areEqual(localObject1, paramObject)) {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  public abstract KotlinType getType(ModuleDescriptor paramModuleDescriptor);
  
  public T getValue()
  {
    return this.value;
  }
  
  public int hashCode()
  {
    Object localObject = getValue();
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public String toString()
  {
    return String.valueOf(getValue());
  }
}
