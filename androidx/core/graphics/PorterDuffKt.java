package androidx.core.graphics;

import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H?\b\032\r\020\005\032\0020\006*\0020\002H?\b?\006\007"}, d2={"toColorFilter", "Landroid/graphics/PorterDuffColorFilter;", "Landroid/graphics/PorterDuff$Mode;", "color", "", "toXfermode", "Landroid/graphics/PorterDuffXfermode;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class PorterDuffKt
{
  public static final PorterDuffColorFilter toColorFilter(PorterDuff.Mode paramMode, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramMode, "$this$toColorFilter");
    return new PorterDuffColorFilter(paramInt, paramMode);
  }
  
  public static final PorterDuffXfermode toXfermode(PorterDuff.Mode paramMode)
  {
    Intrinsics.checkParameterIsNotNull(paramMode, "$this$toXfermode");
    return new PorterDuffXfermode(paramMode);
  }
}
