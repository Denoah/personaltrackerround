package androidx.core.util;

import android.util.Size;
import android.util.SizeF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\020\b\n\002\030\002\n\002\020\007\n\002\030\002\n\002\b\002\032\r\020\000\032\0020\001*\0020\002H?\n\032\r\020\000\032\0020\003*\0020\004H?\n\032\r\020\005\032\0020\001*\0020\002H?\n\032\r\020\005\032\0020\003*\0020\004H?\n?\006\006"}, d2={"component1", "", "Landroid/util/Size;", "", "Landroid/util/SizeF;", "component2", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SizeKt
{
  public static final float component1(SizeF paramSizeF)
  {
    Intrinsics.checkParameterIsNotNull(paramSizeF, "$this$component1");
    return paramSizeF.getWidth();
  }
  
  public static final int component1(Size paramSize)
  {
    Intrinsics.checkParameterIsNotNull(paramSize, "$this$component1");
    return paramSize.getWidth();
  }
  
  public static final float component2(SizeF paramSizeF)
  {
    Intrinsics.checkParameterIsNotNull(paramSizeF, "$this$component2");
    return paramSizeF.getHeight();
  }
  
  public static final int component2(Size paramSize)
  {
    Intrinsics.checkParameterIsNotNull(paramSize, "$this$component2");
    return paramSize.getHeight();
  }
}
