package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

final class FallbackBuiltIns
  extends KotlinBuiltIns
{
  public static final Companion Companion = new Companion(null);
  private static final KotlinBuiltIns Instance = (KotlinBuiltIns)new FallbackBuiltIns();
  
  private FallbackBuiltIns()
  {
    super((StorageManager)new LockBasedStorageManager("FallbackBuiltIns"));
    createBuiltInsModule(true);
  }
  
  protected PlatformDependentDeclarationFilter.All getPlatformDependentDeclarationFilter()
  {
    return PlatformDependentDeclarationFilter.All.INSTANCE;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final KotlinBuiltIns getInstance()
    {
      return FallbackBuiltIns.access$getInstance$cp();
    }
  }
}
