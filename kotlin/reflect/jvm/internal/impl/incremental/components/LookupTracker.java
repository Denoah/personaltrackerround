package kotlin.reflect.jvm.internal.impl.incremental.components;

import kotlin.jvm.internal.Intrinsics;

public abstract interface LookupTracker
{
  public abstract boolean getRequiresPosition();
  
  public abstract void record(String paramString1, Position paramPosition, String paramString2, ScopeKind paramScopeKind, String paramString3);
  
  public static final class DO_NOTHING
    implements LookupTracker
  {
    public static final DO_NOTHING INSTANCE = new DO_NOTHING();
    
    private DO_NOTHING() {}
    
    public boolean getRequiresPosition()
    {
      return false;
    }
    
    public void record(String paramString1, Position paramPosition, String paramString2, ScopeKind paramScopeKind, String paramString3)
    {
      Intrinsics.checkParameterIsNotNull(paramString1, "filePath");
      Intrinsics.checkParameterIsNotNull(paramPosition, "position");
      Intrinsics.checkParameterIsNotNull(paramString2, "scopeFqName");
      Intrinsics.checkParameterIsNotNull(paramScopeKind, "scopeKind");
      Intrinsics.checkParameterIsNotNull(paramString3, "name");
    }
  }
}
