package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class DefaultBuiltIns
  extends KotlinBuiltIns
{
  public static final Companion Companion = new Companion(null);
  private static final DefaultBuiltIns Instance = new DefaultBuiltIns(false, 1, null);
  
  public DefaultBuiltIns()
  {
    this(false, 1, null);
  }
  
  public DefaultBuiltIns(boolean paramBoolean)
  {
    super((StorageManager)new LockBasedStorageManager("DefaultBuiltIns"));
    if (paramBoolean) {
      createBuiltInsModule(false);
    }
  }
  
  public static final DefaultBuiltIns getInstance()
  {
    return Instance;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}
