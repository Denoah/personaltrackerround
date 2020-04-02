package kotlin.ranges;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\005\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\020\016\n\002\b\002\b\007\030\000 \0272\0020\0012\b\022\004\022\0020\0030\002:\001\027B\030\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003?\001\000?\006\002\020\006J\033\020\n\032\0020\0132\006\020\f\032\0020\003H?\002?\001\000?\006\004\b\r\020\016J\023\020\017\032\0020\0132\b\020\020\032\004\030\0010\021H?\002J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\013H\026J\b\020\025\032\0020\026H\026R\027\020\005\032\0020\0038VX?\004?\001\000?\006\006\032\004\b\007\020\bR\027\020\004\032\0020\0038VX?\004?\001\000?\006\006\032\004\b\t\020\b?\001\000?\002\004\n\002\b\031?\006\030"}, d2={"Lkotlin/ranges/UIntRange;", "Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/UInt;", "start", "endInclusive", "(IILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/UInt;", "getStart", "contains", "", "value", "contains-WZ4Q5Ns", "(I)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UIntRange
  extends UIntProgression
  implements ClosedRange<UInt>
{
  public static final Companion Companion = new Companion(null);
  private static final UIntRange EMPTY = new UIntRange(-1, 0, null);
  
  private UIntRange(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, 1, null);
  }
  
  public boolean contains-WZ4Q5Ns(int paramInt)
  {
    boolean bool;
    if ((UnsignedKt.uintCompare(getFirst(), paramInt) <= 0) && (UnsignedKt.uintCompare(paramInt, getLast()) <= 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof UIntRange)) {
      if ((!isEmpty()) || (!((UIntRange)paramObject).isEmpty()))
      {
        int i = getFirst();
        paramObject = (UIntRange)paramObject;
        if ((i != paramObject.getFirst()) || (getLast() != paramObject.getLast())) {}
      }
      else
      {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public UInt getEndInclusive()
  {
    return UInt.box-impl(getLast());
  }
  
  public UInt getStart()
  {
    return UInt.box-impl(getFirst());
  }
  
  public int hashCode()
  {
    int i;
    if (isEmpty()) {
      i = -1;
    } else {
      i = getFirst() * 31 + getLast();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (UnsignedKt.uintCompare(getFirst(), getLast()) > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(UInt.toString-impl(getFirst()));
    localStringBuilder.append("..");
    localStringBuilder.append(UInt.toString-impl(getLast()));
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lkotlin/ranges/UIntRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/UIntRange;", "getEMPTY", "()Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final UIntRange getEMPTY()
    {
      return UIntRange.access$getEMPTY$cp();
    }
  }
}
