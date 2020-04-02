package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty0.Getter;

public abstract class PropertyReference0
  extends PropertyReference
  implements KProperty0
{
  public PropertyReference0() {}
  
  public PropertyReference0(Object paramObject)
  {
    super(paramObject);
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.property0(this);
  }
  
  public Object getDelegate()
  {
    return ((KProperty0)getReflected()).getDelegate();
  }
  
  public KProperty0.Getter getGetter()
  {
    return ((KProperty0)getReflected()).getGetter();
  }
  
  public Object invoke()
  {
    return get();
  }
}
