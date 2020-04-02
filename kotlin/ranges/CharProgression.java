package kotlin.ranges;

import kotlin.Metadata;
import kotlin.collections.CharIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\034\n\002\020\f\n\002\b\003\n\002\020\b\n\002\b\t\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b\026\030\000 \0312\b\022\004\022\0020\0020\001:\001\031B\037\b\000\022\006\020\003\032\0020\002\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006?\006\002\020\007J\023\020\017\032\0020\0202\b\020\021\032\004\030\0010\022H?\002J\b\020\023\032\0020\006H\026J\b\020\024\032\0020\020H\026J\t\020\025\032\0020\026H?\002J\b\020\027\032\0020\030H\026R\021\020\b\032\0020\002?\006\b\n\000\032\004\b\t\020\nR\021\020\013\032\0020\002?\006\b\n\000\032\004\b\f\020\nR\021\020\005\032\0020\006?\006\b\n\000\032\004\b\r\020\016?\006\032"}, d2={"Lkotlin/ranges/CharProgression;", "", "", "start", "endInclusive", "step", "", "(CCI)V", "first", "getFirst", "()C", "last", "getLast", "getStep", "()I", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/CharIterator;", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public class CharProgression
  implements Iterable<Character>, KMappedMarker
{
  public static final Companion Companion = new Companion(null);
  private final char first;
  private final char last;
  private final int step;
  
  public CharProgression(char paramChar1, char paramChar2, int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != Integer.MIN_VALUE)
      {
        this.first = ((char)paramChar1);
        this.last = ((char)(char)ProgressionUtilKt.getProgressionLastElement(paramChar1, paramChar2, paramInt));
        this.step = paramInt;
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation."));
    }
    throw ((Throwable)new IllegalArgumentException("Step must be non-zero."));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof CharProgression)) {
      if ((!isEmpty()) || (!((CharProgression)paramObject).isEmpty()))
      {
        int i = this.first;
        paramObject = (CharProgression)paramObject;
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
  
  public final char getFirst()
  {
    return this.first;
  }
  
  public final char getLast()
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
      i = (this.first * '\037' + this.last) * 31 + this.step;
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
  
  public CharIterator iterator()
  {
    return (CharIterator)new CharProgressionIterator(this.first, this.last, this.step);
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\f\n\002\b\002\n\002\020\b\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\036\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\t?\006\n"}, d2={"Lkotlin/ranges/CharProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/CharProgression;", "rangeStart", "", "rangeEnd", "step", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final CharProgression fromClosedRange(char paramChar1, char paramChar2, int paramInt)
    {
      return new CharProgression(paramChar1, paramChar2, paramInt);
    }
  }
}
