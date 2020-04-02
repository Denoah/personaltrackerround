package kotlin.reflect.jvm.internal.impl.load.java.components;

public abstract interface SamConversionResolver
{
  public static final class Empty
    implements SamConversionResolver
  {
    public static final Empty INSTANCE = new Empty();
    
    private Empty() {}
  }
}
