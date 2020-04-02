package kotlin.ranges;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.collections.UIntIterator;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\034\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\t\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b\027\030\000 \0312\b\022\004\022\0020\0020\001:\001\031B\"\b\000\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006?\001\000?\006\002\020\007J\023\020\017\032\0020\0202\b\020\021\032\004\030\0010\022H?\002J\b\020\023\032\0020\006H\026J\b\020\024\032\0020\020H\026J\t\020\025\032\0020\026H?\002J\b\020\027\032\0020\030H\026R\026\020\b\032\0020\002?\001\000?\006\n\n\002\020\013\032\004\b\t\020\nR\026\020\f\032\0020\002?\001\000?\006\n\n\002\020\013\032\004\b\r\020\nR\021\020\005\032\0020\006?\006\b\n\000\032\004\b\016\020\n?\001\000?\002\004\n\002\b\031?\006\032"}, d2={"Lkotlin/ranges/UIntProgression;", "", "Lkotlin/UInt;", "start", "endInclusive", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst", "()I", "I", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/UIntIterator;", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public class UIntProgression
  implements Iterable<UInt>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  private final int first;
  private final int last;
  private final int step;
  
  private UIntProgression(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 != 0)
    {
      if (paramInt3 != Integer.MIN_VALUE)
      {
        this.first = paramInt1;
        this.last = UProgressionUtilKt.getProgressionLastElement-Nkh28Cs(paramInt1, paramInt2, paramInt3);
        this.step = paramInt3;
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation."));
    }
    throw ((Throwable)new IllegalArgumentException("Step must be non-zero."));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof UIntProgression)) {
      if ((!isEmpty()) || (!((UIntProgression)paramObject).isEmpty()))
      {
        int i = this.first;
        paramObject = (UIntProgression)paramObject;
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
    if (i > 0 ? UnsignedKt.uintCompare(this.first, this.last) <= 0 : UnsignedKt.uintCompare(this.first, this.last) >= 0) {
      bool = false;
    }
    return bool;
  }
  
  public UIntIterator iterator()
  {
    return (UIntIterator)new UIntProgressionIterator(this.first, this.last, this.step, null);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder;
    int i;
    if (this.step > 0)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(UInt.toString-impl(this.first));
      localStringBuilder.append("..");
      localStringBuilder.append(UInt.toString-impl(this.last));
      localStringBuilder.append(" step ");
      i = this.step;
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(UInt.toString-impl(this.first));
      localStringBuilder.append(" downTo ");
      localStringBuilder.append(UInt.toString-impl(this.last));
      localStringBuilder.append(" step ");
      i = -this.step;
    }
    localStringBuilder.append(i);
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J(\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\t?\001\000?\006\004\b\n\020\013?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlin/ranges/UIntProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/UIntProgression;", "rangeStart", "Lkotlin/UInt;", "rangeEnd", "step", "", "fromClosedRange-Nkh28Cs", "(III)Lkotlin/ranges/UIntProgression;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final UIntProgression fromClosedRange-Nkh28Cs(int paramInt1, int paramInt2, int paramInt3)
    {
      return new UIntProgression(paramInt1, paramInt2, paramInt3, null);
    }
  }
}
