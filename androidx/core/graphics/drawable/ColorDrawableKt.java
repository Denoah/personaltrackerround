package androidx.core.graphics.drawable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\030\002\n\002\030\002\n\002\020\b\n\000\032\r\020\000\032\0020\001*\0020\002H?\b\032\r\020\000\032\0020\001*\0020\003H?\b?\006\004"}, d2={"toDrawable", "Landroid/graphics/drawable/ColorDrawable;", "Landroid/graphics/Color;", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ColorDrawableKt
{
  public static final ColorDrawable toDrawable(int paramInt)
  {
    return new ColorDrawable(paramInt);
  }
  
  public static final ColorDrawable toDrawable(Color paramColor)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$toDrawable");
    return new ColorDrawable(paramColor.toArgb());
  }
}
