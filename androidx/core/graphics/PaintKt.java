package androidx.core.graphics;

import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\020\013\n\002\030\002\n\000\n\002\030\002\n\000\032\027\020\000\032\0020\001*\0020\0022\b\020\003\032\004\030\0010\004H?\b?\006\005"}, d2={"setBlendMode", "", "Landroid/graphics/Paint;", "blendModeCompat", "Landroidx/core/graphics/BlendModeCompat;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class PaintKt
{
  public static final boolean setBlendMode(Paint paramPaint, BlendModeCompat paramBlendModeCompat)
  {
    Intrinsics.checkParameterIsNotNull(paramPaint, "$this$setBlendMode");
    return PaintCompat.setBlendMode(paramPaint, paramBlendModeCompat);
  }
}
