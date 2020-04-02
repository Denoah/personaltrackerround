package kotlin.ranges;

import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\020\034\n\002\020\b\n\002\b\013\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b\026\030\000 \0272\b\022\004\022\0020\0020\001:\001\027B\037\b\000\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\002?\006\002\020\006J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H?\002J\b\020\021\032\0020\002H\026J\b\020\022\032\0020\016H\026J\t\020\023\032\0020\024H?\002J\b\020\025\032\0020\026H\026R\021\020\007\032\0020\002?\006\b\n\000\032\004\b\b\020\tR\021\020\n\032\0020\002?\006\b\n\000\032\004\b\013\020\tR\021\020\005\032\0020\002?\006\b\n\000\032\004\b\f\020\t?\006\030"}, d2={"Lkotlin/ranges/IntProgression;", "", "", "start", "endInclusive", "step", "(III)V", "first", "getFirst", "()I", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/IntIterator;", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public class IntProgression
  implements Iterable<Integer>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  private final int first;
  private final int last;
  private final int step;
  
  public IntProgression(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 != 0)
    {
      if (paramInt3 != Integer.MIN_VALUE)
      {
        this.first = paramInt1;
        this.last = ProgressionUtilKt.getProgressionLastElement(paramInt1, paramInt2, paramInt3);
        this.step = paramInt3;
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation."));
    }
    throw ((Throwable)new IllegalArgumentException("Step must be non-zero."));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof IntProgression)) {
      if ((!isEmpty()) || (!((IntProgression)paramObject).isEmpty()))
      {
        int i = this.first;
        paramObject = (IntProgression)paramObject;
        if ((i != paramObject.first) || (this.last != paramObject.last) || (this.step != paramObject.step)) {}
      }
      else
      {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public final int getFirst()
  {
    return this.first;
  }
  
  public final int getLast()
  {
    return this.last;
  }
  
  public final int getStep()
  {
    return this.step;
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = (this.first * 31 + this.last) * 31 + this.step;
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    int i = this.step;
    boolean bool = true;
    if (i > 0 ? this.first <= this.last : this.first >= this.last) {
      bool = false;
    }
    return bool;
  }
  
  public IntIterator iterator()
  {
    return (IntIterator)new IntProgressionIterator(this.first, this.last, this.step);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder;
    int i;
    if (this.step > 0)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.first);
      localStringBuilder.append("..");
      localStringBuilder.append(this.last);
      localStringBuilder.append(" step ");
      i = this.step;
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.first);
      localStringBuilder.append(" downTo ");
      localStringBuilder.append(this.last);
      localStringBuilder.append(" step ");
      i = -this.step;
    }
    localStringBuilder.append(i);
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\036\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\006?\006\t"}, d2={"Lkotlin/ranges/IntProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/IntProgression;", "rangeStart", "", "rangeEnd", "step", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final IntProgression fromClosedRange(int paramInt1, int paramInt2, int paramInt3)
    {
      return new IntProgression(paramInt1, paramInt2, paramInt3);
    }
  }
}
