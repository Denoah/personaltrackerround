package kotlin.reflect.jvm.internal.impl.load.java;

public final class Constant
  extends JavaDefaultValue
{
  private final Object value;
  
  public Constant(Object paramObject)
  {
    super(null);
    this.value = paramObject;
  }
}
