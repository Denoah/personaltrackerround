package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\b\032#\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\005\032\0020\006H?\b\0327\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\b2\b\b\002\020\t\032\0020\nH?\b\032&\020\013\032\0020\001*\0020\0012\027\020\f\032\023\022\004\022\0020\016\022\004\022\0020\0170\r?\006\002\b\020H?\b\032\025\020\021\032\0020\b*\0020\0012\006\020\022\032\0020\023H?\n\032\025\020\021\032\0020\b*\0020\0012\006\020\022\032\0020\024H?\n\032\035\020\025\032\0020\003*\0020\0012\006\020\026\032\0020\0032\006\020\027\032\0020\003H?\n\032'\020\030\032\0020\001*\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\031\032\0020\bH?\b\032'\020\032\032\0020\017*\0020\0012\006\020\026\032\0020\0032\006\020\027\032\0020\0032\b\b\001\020\033\032\0020\003H?\n?\006\034"}, d2={"createBitmap", "Landroid/graphics/Bitmap;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "hasAlpha", "", "colorSpace", "Landroid/graphics/ColorSpace;", "applyCanvas", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "contains", "p", "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "get", "x", "y", "scale", "filter", "set", "color", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class BitmapKt
{
  public static final Bitmap applyCanvas(Bitmap paramBitmap, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$applyCanvas");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    paramFunction1.invoke(new Canvas(paramBitmap));
    return paramBitmap;
  }
  
  public static final boolean contains(Bitmap paramBitmap, Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramPoint, "p");
    boolean bool;
    if ((paramPoint.x >= 0) && (paramPoint.x < paramBitmap.getWidth()) && (paramPoint.y >= 0) && (paramPoint.y < paramBitmap.getHeight())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean contains(Bitmap paramBitmap, PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramPointF, "p");
    float f1 = paramPointF.x;
    boolean bool1 = false;
    float f2 = 0;
    boolean bool2 = bool1;
    if (f1 >= f2)
    {
      bool2 = bool1;
      if (paramPointF.x < paramBitmap.getWidth())
      {
        bool2 = bool1;
        if (paramPointF.y >= f2)
        {
          bool2 = bool1;
          if (paramPointF.y < paramBitmap.getHeight()) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  public static final Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Intrinsics.checkParameterIsNotNull(paramConfig, "config");
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    Intrinsics.checkExpressionValueIsNotNull(paramConfig, "Bitmap.createBitmap(width, height, config)");
    return paramConfig;
  }
  
  public static final Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean, ColorSpace paramColorSpace)
  {
    Intrinsics.checkParameterIsNotNull(paramConfig, "config");
    Intrinsics.checkParameterIsNotNull(paramColorSpace, "colorSpace");
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig, paramBoolean, paramColorSpace);
    Intrinsics.checkExpressionValueIsNotNull(paramConfig, "Bitmap.createBitmap(widt…ig, hasAlpha, colorSpace)");
    return paramConfig;
  }
  
  public static final int get(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$get");
    return paramBitmap.getPixel(paramInt1, paramInt2);
  }
  
  public static final Bitmap scale(Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$scale");
    paramBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, paramBoolean);
    Intrinsics.checkExpressionValueIsNotNull(paramBitmap, "Bitmap.createScaledBitma…s, width, height, filter)");
    return paramBitmap;
  }
  
  public static final void set(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "$this$set");
    paramBitmap.setPixel(paramInt1, paramInt2, paramInt3);
  }
}
