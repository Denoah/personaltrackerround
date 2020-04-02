package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H?\b?\006\005"}, d2={"toDrawable", "Landroid/graphics/drawable/BitmapDrawable;", "Landroid/graphics/Bitmap;", "resources", "Landroid/content/res/Resources;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class BitmapDrawableKt
{
  public static final BitmapDrawable toDrawable(Bitmap paramBitmap, Resources paramResources)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$toDrawable");
    Intrinsics.checkParameterIsNotNull(paramResources, "resources");
    return new BitmapDrawable(paramResources, paramBitmap);
  }
}
