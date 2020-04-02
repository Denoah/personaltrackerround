package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;

public abstract interface NewKotlinTypeChecker
  extends KotlinTypeChecker
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract KotlinTypeRefiner getKotlinTypeRefiner();
  
  public abstract OverridingUtil getOverridingUtil();
  
  public static final class Companion
  {
    private static final NewKotlinTypeCheckerImpl Default = new NewKotlinTypeCheckerImpl((KotlinTypeRefiner)KotlinTypeRefiner.Default.INSTANCE);
    
    private Companion() {}
    
    public final NewKotlinTypeCheckerImpl getDefault()
    {
      return Default;
    }
  }
}
