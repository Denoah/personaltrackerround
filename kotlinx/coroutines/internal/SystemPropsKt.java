package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"kotlinx/coroutines/internal/SystemPropsKt__SystemPropsKt", "kotlinx/coroutines/internal/SystemPropsKt__SystemProps_commonKt"}, k=4, mv={1, 1, 16})
public final class SystemPropsKt
{
  public static final int getAVAILABLE_PROCESSORS()
  {
    return SystemPropsKt__SystemPropsKt.getAVAILABLE_PROCESSORS();
  }
  
  public static final int systemProp(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramInt1, paramInt2, paramInt3);
  }
  
  public static final long systemProp(String paramString, long paramLong1, long paramLong2, long paramLong3)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramLong1, paramLong2, paramLong3);
  }
  
  public static final String systemProp(String paramString)
  {
    return SystemPropsKt__SystemPropsKt.systemProp(paramString);
  }
  
  public static final boolean systemProp(String paramString, boolean paramBoolean)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramBoolean);
  }
}
