package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\020\007\n\002\b\004\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\002\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\f\032\025\020\000\032\0020\003*\0020\0032\006\020\002\032\0020\003H?\f\032\r\020\004\032\0020\005*\0020\001H?\n\032\r\020\004\032\0020\006*\0020\003H?\n\032\r\020\007\032\0020\005*\0020\001H?\n\032\r\020\007\032\0020\006*\0020\003H?\n\032\r\020\b\032\0020\005*\0020\001H?\n\032\r\020\b\032\0020\006*\0020\003H?\n\032\r\020\t\032\0020\005*\0020\001H?\n\032\r\020\t\032\0020\006*\0020\003H?\n\032\025\020\n\032\0020\013*\0020\0012\006\020\f\032\0020\rH?\n\032\025\020\n\032\0020\013*\0020\0032\006\020\f\032\0020\016H?\n\032\025\020\017\032\0020\001*\0020\0012\006\020\020\032\0020\rH?\n\032\025\020\017\032\0020\021*\0020\0012\006\020\002\032\0020\001H?\n\032\025\020\017\032\0020\001*\0020\0012\006\020\020\032\0020\005H?\n\032\025\020\017\032\0020\003*\0020\0032\006\020\020\032\0020\016H?\n\032\025\020\017\032\0020\021*\0020\0032\006\020\002\032\0020\003H?\n\032\025\020\017\032\0020\003*\0020\0032\006\020\020\032\0020\006H?\n\032\025\020\022\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\f\032\025\020\022\032\0020\003*\0020\0032\006\020\002\032\0020\003H?\f\032\025\020\023\032\0020\001*\0020\0012\006\020\020\032\0020\rH?\n\032\025\020\023\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\n\032\025\020\023\032\0020\001*\0020\0012\006\020\020\032\0020\005H?\n\032\025\020\023\032\0020\003*\0020\0032\006\020\020\032\0020\016H?\n\032\025\020\023\032\0020\003*\0020\0032\006\020\002\032\0020\003H?\n\032\025\020\023\032\0020\003*\0020\0032\006\020\020\032\0020\006H?\n\032\025\020\024\032\0020\001*\0020\0012\006\020\025\032\0020\005H?\n\032\025\020\024\032\0020\003*\0020\0032\006\020\025\032\0020\006H?\n\032\025\020\024\032\0020\003*\0020\0032\006\020\025\032\0020\005H?\n\032\r\020\026\032\0020\001*\0020\003H?\b\032\r\020\027\032\0020\003*\0020\001H?\b\032\r\020\030\032\0020\021*\0020\001H?\b\032\r\020\030\032\0020\021*\0020\003H?\b\032\025\020\031\032\0020\003*\0020\0032\006\020\032\032\0020\033H?\b\032\025\020\034\032\0020\021*\0020\0012\006\020\002\032\0020\001H?\f\032\025\020\034\032\0020\021*\0020\0032\006\020\002\032\0020\003H?\f?\006\035"}, d2={"and", "Landroid/graphics/Rect;", "r", "Landroid/graphics/RectF;", "component1", "", "", "component2", "component3", "component4", "contains", "", "p", "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "minus", "xy", "Landroid/graphics/Region;", "or", "plus", "times", "factor", "toRect", "toRectF", "toRegion", "transform", "m", "Landroid/graphics/Matrix;", "xor", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class RectKt
{
  public static final Rect and(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkParameterIsNotNull(paramRect1, "$this$and");
    Intrinsics.checkParameterIsNotNull(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.intersect(paramRect2);
    return paramRect1;
  }
  
  public static final RectF and(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF1, "$this$and");
    Intrinsics.checkParameterIsNotNull(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.intersect(paramRectF2);
    return paramRectF1;
  }
  
  public static final float component1(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$component1");
    return paramRectF.left;
  }
  
  public static final int component1(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$component1");
    return paramRect.left;
  }
  
  public static final float component2(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$component2");
    return paramRectF.top;
  }
  
  public static final int component2(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$component2");
    return paramRect.top;
  }
  
  public static final float component3(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$component3");
    return paramRectF.right;
  }
  
  public static final int component3(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$component3");
    return paramRect.right;
  }
  
  public static final float component4(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$component4");
    return paramRectF.bottom;
  }
  
  public static final int component4(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$component4");
    return paramRect.bottom;
  }
  
  public static final boolean contains(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramPoint, "p");
    return paramRect.contains(paramPoint.x, paramPoint.y);
  }
  
  public static final boolean contains(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramPointF, "p");
    return paramRectF.contains(paramPointF.x, paramPointF.y);
  }
  
  public static final Rect minus(Rect paramRect, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$minus");
    paramRect = new Rect(paramRect);
    paramInt = -paramInt;
    paramRect.offset(paramInt, paramInt);
    return paramRect;
  }
  
  public static final Rect minus(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramPoint, "xy");
    paramRect = new Rect(paramRect);
    paramRect.offset(-paramPoint.x, -paramPoint.y);
    return paramRect;
  }
  
  public static final RectF minus(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$minus");
    paramRectF = new RectF(paramRectF);
    paramFloat = -paramFloat;
    paramRectF.offset(paramFloat, paramFloat);
    return paramRectF;
  }
  
  public static final RectF minus(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramPointF, "xy");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(-paramPointF.x, -paramPointF.y);
    return paramRectF;
  }
  
  public static final Region minus(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkParameterIsNotNull(paramRect1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramRect2, "r");
    paramRect1 = new Region(paramRect1);
    paramRect1.op(paramRect2, Region.Op.DIFFERENCE);
    return paramRect1;
  }
  
  public static final Region minus(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramRectF2, "r");
    Object localObject = new Rect();
    paramRectF1.roundOut((Rect)localObject);
    localObject = new Region((Rect)localObject);
    paramRectF1 = new Rect();
    paramRectF2.roundOut(paramRectF1);
    ((Region)localObject).op(paramRectF1, Region.Op.DIFFERENCE);
    return localObject;
  }
  
  public static final Rect or(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkParameterIsNotNull(paramRect1, "$this$or");
    Intrinsics.checkParameterIsNotNull(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.union(paramRect2);
    return paramRect1;
  }
  
  public static final RectF or(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF1, "$this$or");
    Intrinsics.checkParameterIsNotNull(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.union(paramRectF2);
    return paramRectF1;
  }
  
  public static final Rect plus(Rect paramRect, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$plus");
    paramRect = new Rect(paramRect);
    paramRect.offset(paramInt, paramInt);
    return paramRect;
  }
  
  public static final Rect plus(Rect paramRect, Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramPoint, "xy");
    paramRect = new Rect(paramRect);
    paramRect.offset(paramPoint.x, paramPoint.y);
    return paramRect;
  }
  
  public static final Rect plus(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkParameterIsNotNull(paramRect1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramRect2, "r");
    paramRect1 = new Rect(paramRect1);
    paramRect1.union(paramRect2);
    return paramRect1;
  }
  
  public static final RectF plus(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$plus");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(paramFloat, paramFloat);
    return paramRectF;
  }
  
  public static final RectF plus(RectF paramRectF, PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramPointF, "xy");
    paramRectF = new RectF(paramRectF);
    paramRectF.offset(paramPointF.x, paramPointF.y);
    return paramRectF;
  }
  
  public static final RectF plus(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramRectF2, "r");
    paramRectF1 = new RectF(paramRectF1);
    paramRectF1.union(paramRectF2);
    return paramRectF1;
  }
  
  public static final Rect times(Rect paramRect, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$times");
    paramRect = new Rect(paramRect);
    paramRect.top *= paramInt;
    paramRect.left *= paramInt;
    paramRect.right *= paramInt;
    paramRect.bottom *= paramInt;
    return paramRect;
  }
  
  public static final RectF times(RectF paramRectF, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$times");
    paramRectF = new RectF(paramRectF);
    paramRectF.top *= paramFloat;
    paramRectF.left *= paramFloat;
    paramRectF.right *= paramFloat;
    paramRectF.bottom *= paramFloat;
    return paramRectF;
  }
  
  public static final RectF times(RectF paramRectF, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$times");
    float f = paramInt;
    paramRectF = new RectF(paramRectF);
    paramRectF.top *= f;
    paramRectF.left *= f;
    paramRectF.right *= f;
    paramRectF.bottom *= f;
    return paramRectF;
  }
  
  public static final Rect toRect(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$toRect");
    Rect localRect = new Rect();
    paramRectF.roundOut(localRect);
    return localRect;
  }
  
  public static final RectF toRectF(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$toRectF");
    return new RectF(paramRect);
  }
  
  public static final Region toRegion(Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRect, "$this$toRegion");
    return new Region(paramRect);
  }
  
  public static final Region toRegion(RectF paramRectF)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$toRegion");
    Rect localRect = new Rect();
    paramRectF.roundOut(localRect);
    return new Region(localRect);
  }
  
  public static final RectF transform(RectF paramRectF, Matrix paramMatrix)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF, "$this$transform");
    Intrinsics.checkParameterIsNotNull(paramMatrix, "m");
    paramMatrix.mapRect(paramRectF);
    return paramRectF;
  }
  
  public static final Region xor(Rect paramRect1, Rect paramRect2)
  {
    Intrinsics.checkParameterIsNotNull(paramRect1, "$this$xor");
    Intrinsics.checkParameterIsNotNull(paramRect2, "r");
    paramRect1 = new Region(paramRect1);
    paramRect1.op(paramRect2, Region.Op.XOR);
    return paramRect1;
  }
  
  public static final Region xor(RectF paramRectF1, RectF paramRectF2)
  {
    Intrinsics.checkParameterIsNotNull(paramRectF1, "$this$xor");
    Intrinsics.checkParameterIsNotNull(paramRectF2, "r");
    Rect localRect = new Rect();
    paramRectF1.roundOut(localRect);
    paramRectF1 = new Region(localRect);
    localRect = new Rect();
    paramRectF2.roundOut(localRect);
    paramRectF1.op(localRect, Region.Op.XOR);
    return paramRectF1;
  }
}
