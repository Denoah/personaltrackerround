package kotlin.reflect.jvm.internal.impl.load.java.lazy;

public abstract interface JavaResolverSettings
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract boolean isReleaseCoroutines();
  
  public static final class Companion
  {
    private Companion() {}
  }
  
  public static final class Default
    implements JavaResolverSettings
  {
    public static final Default INSTANCE = new Default();
    
    private Default() {}
    
    public boolean isReleaseCoroutines()
    {
      return false;
    }
  }
}
