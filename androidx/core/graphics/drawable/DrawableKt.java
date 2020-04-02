package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\005\032*\020\000\032\0020\001*\0020\0022\b\b\003\020\003\032\0020\0042\b\b\003\020\005\032\0020\0042\n\b\002\020\006\032\004\030\0010\007\0322\020\b\032\0020\t*\0020\0022\b\b\003\020\n\032\0020\0042\b\b\003\020\013\032\0020\0042\b\b\003\020\f\032\0020\0042\b\b\003\020\r\032\0020\004?\006\016"}, d2={"toBitmap", "Landroid/graphics/Bitmap;", "Landroid/graphics/drawable/Drawable;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "updateBounds", "", "left", "top", "right", "bottom", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class DrawableKt
{
  public static final Bitmap toBitmap(Drawable paramDrawable, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Intrinsics.checkParameterIsNotNull(paramDrawable, "$this$toBitmap");
    if ((paramDrawable instanceof BitmapDrawable)) {
      if (paramConfig != null)
      {
        localObject = ((BitmapDrawable)paramDrawable).getBitmap();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "bitmap");
        if (((Bitmap)localObject).getConfig() != paramConfig) {}
      }
      else
      {
        paramDrawable = (BitmapDrawable)paramDrawable;
        if ((paramInt1 == paramDrawable.getIntrinsicWidth()) && (paramInt2 == paramDrawable.getIntrinsicHeight()))
        {
          paramDrawable = paramDrawable.getBitmap();
          Intrinsics.checkExpressionValueIsNotNull(paramDrawable, "bitmap");
          return paramDrawable;
        }
        paramDrawable = Bitmap.createScaledBitmap(paramDrawable.getBitmap(), paramInt1, paramInt2, true);
        Intrinsics.checkExpressionValueIsNotNull(paramDrawable, "Bitmap.createScaledBitma…map, width, height, true)");
        return paramDrawable;
      }
    }
    Object localObject = paramDrawable.getBounds();
    int i = ((Rect)localObject).left;
    int j = ((Rect)localObject).top;
    int k = ((Rect)localObject).right;
    int m = ((Rect)localObject).bottom;
    if (paramConfig == null) {
      paramConfig = Bitmap.Config.ARGB_8888;
    }
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    paramDrawable.setBounds(0, 0, paramInt1, paramInt2);
    paramDrawable.draw(new Canvas(paramConfig));
    paramDrawable.setBounds(i, j, k, m);
    Intrinsics.checkExpressionValueIsNotNull(paramConfig, "bitmap");
    return paramConfig;
  }
  
  public static final void updateBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkParameterIsNotNull(paramDrawable, "$this$updateBounds");
    paramDrawable.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}
