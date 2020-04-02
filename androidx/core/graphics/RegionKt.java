package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020(\n\002\b\007\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\003H?\f\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\f\032\025\020\004\032\0020\005*\0020\0012\006\020\006\032\0020\007H?\n\0320\020\b\032\0020\t*\0020\0012!\020\n\032\035\022\023\022\0210\003?\006\f\b\f\022\b\b\r\022\004\b\b(\016\022\004\022\0020\t0\013H?\b\032\023\020\017\032\b\022\004\022\0020\0030\020*\0020\001H?\002\032\025\020\021\032\0020\001*\0020\0012\006\020\002\032\0020\003H?\n\032\025\020\021\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\n\032\r\020\022\032\0020\001*\0020\001H?\n\032\025\020\023\032\0020\001*\0020\0012\006\020\002\032\0020\003H?\f\032\025\020\023\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\f\032\025\020\024\032\0020\001*\0020\0012\006\020\002\032\0020\003H?\n\032\025\020\024\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\n\032\r\020\025\032\0020\001*\0020\001H?\n\032\025\020\026\032\0020\001*\0020\0012\006\020\002\032\0020\003H?\f\032\025\020\026\032\0020\001*\0020\0012\006\020\002\032\0020\001H?\f?\006\027"}, d2={"and", "Landroid/graphics/Region;", "r", "Landroid/graphics/Rect;", "contains", "", "p", "Landroid/graphics/Point;", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "rect", "iterator", "", "minus", "not", "or", "plus", "unaryMinus", "xor", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class RegionKt
{
  public static final Region and(Region paramRegion, Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$and");
    Intrinsics.checkParameterIsNotNull(paramRect, "r");
    paramRegion = new Region(paramRegion);
    paramRegion.op(paramRect, Region.Op.INTERSECT);
    return paramRegion;
  }
  
  public static final Region and(Region paramRegion1, Region paramRegion2)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion1, "$this$and");
    Intrinsics.checkParameterIsNotNull(paramRegion2, "r");
    paramRegion1 = new Region(paramRegion1);
    paramRegion1.op(paramRegion2, Region.Op.INTERSECT);
    return paramRegion1;
  }
  
  public static final boolean contains(Region paramRegion, Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramPoint, "p");
    return paramRegion.contains(paramPoint.x, paramPoint.y);
  }
  
  public static final void forEach(Region paramRegion, Function1<? super Rect, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramRegion = new RegionIterator(paramRegion);
    for (;;)
    {
      Rect localRect = new Rect();
      if (!paramRegion.next(localRect)) {
        return;
      }
      paramFunction1.invoke(localRect);
    }
  }
  
  public static final Iterator<Rect> iterator(Region paramRegion)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$iterator");
    (Iterator)new Iterator()
    {
      private boolean hasMore;
      private final RegionIterator iterator = new RegionIterator(this.$this_iterator);
      private final Rect rect;
      
      public boolean hasNext()
      {
        return this.hasMore;
      }
      
      public Rect next()
      {
        if (this.hasMore)
        {
          Rect localRect = new Rect(this.rect);
          this.hasMore = this.iterator.next(this.rect);
          return localRect;
        }
        throw ((Throwable)new IndexOutOfBoundsException());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
    };
  }
  
  public static final Region minus(Region paramRegion, Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramRect, "r");
    paramRegion = new Region(paramRegion);
    paramRegion.op(paramRect, Region.Op.DIFFERENCE);
    return paramRegion;
  }
  
  public static final Region minus(Region paramRegion1, Region paramRegion2)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramRegion2, "r");
    paramRegion1 = new Region(paramRegion1);
    paramRegion1.op(paramRegion2, Region.Op.DIFFERENCE);
    return paramRegion1;
  }
  
  public static final Region not(Region paramRegion)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$not");
    Region localRegion = new Region(paramRegion.getBounds());
    localRegion.op(paramRegion, Region.Op.DIFFERENCE);
    return localRegion;
  }
  
  public static final Region or(Region paramRegion, Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$or");
    Intrinsics.checkParameterIsNotNull(paramRect, "r");
    paramRegion = new Region(paramRegion);
    paramRegion.union(paramRect);
    return paramRegion;
  }
  
  public static final Region or(Region paramRegion1, Region paramRegion2)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion1, "$this$or");
    Intrinsics.checkParameterIsNotNull(paramRegion2, "r");
    paramRegion1 = new Region(paramRegion1);
    paramRegion1.op(paramRegion2, Region.Op.UNION);
    return paramRegion1;
  }
  
  public static final Region plus(Region paramRegion, Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramRect, "r");
    paramRegion = new Region(paramRegion);
    paramRegion.union(paramRect);
    return paramRegion;
  }
  
  public static final Region plus(Region paramRegion1, Region paramRegion2)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramRegion2, "r");
    paramRegion1 = new Region(paramRegion1);
    paramRegion1.op(paramRegion2, Region.Op.UNION);
    return paramRegion1;
  }
  
  public static final Region unaryMinus(Region paramRegion)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$unaryMinus");
    Region localRegion = new Region(paramRegion.getBounds());
    localRegion.op(paramRegion, Region.Op.DIFFERENCE);
    return localRegion;
  }
  
  public static final Region xor(Region paramRegion, Rect paramRect)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion, "$this$xor");
    Intrinsics.checkParameterIsNotNull(paramRect, "r");
    paramRegion = new Region(paramRegion);
    paramRegion.op(paramRect, Region.Op.XOR);
    return paramRegion;
  }
  
  public static final Region xor(Region paramRegion1, Region paramRegion2)
  {
    Intrinsics.checkParameterIsNotNull(paramRegion1, "$this$xor");
    Intrinsics.checkParameterIsNotNull(paramRegion2, "r");
    paramRegion1 = new Region(paramRegion1);
    paramRegion1.op(paramRegion2, Region.Op.XOR);
    return paramRegion1;
  }
}
