package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public abstract interface PackagePartProvider
{
  public abstract List<String> findPackageParts(String paramString);
  
  public static final class Empty
    implements PackagePartProvider
  {
    public static final Empty INSTANCE = new Empty();
    
    private Empty() {}
    
    public List<String> findPackageParts(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "packageFqName");
      return CollectionsKt.emptyList();
    }
  }
}
