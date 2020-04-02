package kotlin.internal;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\006\n\002\020\b\n\000\n\002\020\t\n\002\b\002\032*\020\000\032\0020\0012\006\020\002\032\0020\0012\006\020\003\032\0020\0012\006\020\004\032\0020\001H\002?\001\000?\006\004\b\005\020\006\032*\020\000\032\0020\0072\006\020\002\032\0020\0072\006\020\003\032\0020\0072\006\020\004\032\0020\007H\002?\001\000?\006\004\b\b\020\t\032*\020\n\032\0020\0012\006\020\013\032\0020\0012\006\020\f\032\0020\0012\006\020\r\032\0020\016H\001?\001\000?\006\004\b\017\020\006\032*\020\n\032\0020\0072\006\020\013\032\0020\0072\006\020\f\032\0020\0072\006\020\r\032\0020\020H\001?\001\000?\006\004\b\021\020\t?\002\004\n\002\b\031?\006\022"}, d2={"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UProgressionUtilKt
{
  private static final int differenceModulo-WZ9TVnA(int paramInt1, int paramInt2, int paramInt3)
  {
    paramInt1 = UnsignedKt.uintRemainder-J1ME1BU(paramInt1, paramInt3);
    int i = UnsignedKt.uintRemainder-J1ME1BU(paramInt2, paramInt3);
    paramInt2 = UnsignedKt.uintCompare(paramInt1, i);
    paramInt1 = UInt.constructor-impl(paramInt1 - i);
    if (paramInt2 < 0) {
      paramInt1 = UInt.constructor-impl(paramInt1 + paramInt3);
    }
    return paramInt1;
  }
  
  private static final long differenceModulo-sambcqE(long paramLong1, long paramLong2, long paramLong3)
  {
    paramLong1 = UnsignedKt.ulongRemainder-eb3DHEI(paramLong1, paramLong3);
    paramLong2 = UnsignedKt.ulongRemainder-eb3DHEI(paramLong2, paramLong3);
    int i = UnsignedKt.ulongCompare(paramLong1, paramLong2);
    paramLong1 = ULong.constructor-impl(paramLong1 - paramLong2);
    if (i < 0) {
      paramLong1 = ULong.constructor-impl(paramLong1 + paramLong3);
    }
    return paramLong1;
  }
  
  public static final long getProgressionLastElement-7ftBX0g(long paramLong1, long paramLong2, long paramLong3)
  {
    boolean bool = paramLong3 < 0L;
    if (bool)
    {
      if (UnsignedKt.ulongCompare(paramLong1, paramLong2) < 0) {
        paramLong2 = ULong.constructor-impl(paramLong2 - differenceModulo-sambcqE(paramLong2, paramLong1, ULong.constructor-impl(paramLong3)));
      }
    }
    else
    {
      if (!bool) {
        break label76;
      }
      if (UnsignedKt.ulongCompare(paramLong1, paramLong2) > 0) {
        paramLong2 = ULong.constructor-impl(paramLong2 + differenceModulo-sambcqE(paramLong1, paramLong2, ULong.constructor-impl(-paramLong3)));
      }
    }
    return paramLong2;
    label76:
    throw ((Throwable)new IllegalArgumentException("Step is zero."));
  }
  
  public static final int getProgressionLastElement-Nkh28Cs(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 0)
    {
      if (UnsignedKt.uintCompare(paramInt1, paramInt2) < 0) {
        paramInt2 = UInt.constructor-impl(paramInt2 - differenceModulo-WZ9TVnA(paramInt2, paramInt1, UInt.constructor-impl(paramInt3)));
      }
    }
    else
    {
      if (paramInt3 >= 0) {
        break label66;
      }
      if (UnsignedKt.uintCompare(paramInt1, paramInt2) > 0) {
        paramInt2 = UInt.constructor-impl(paramInt2 + differenceModulo-WZ9TVnA(paramInt1, paramInt2, UInt.constructor-impl(-paramInt3)));
      }
    }
    return paramInt2;
    label66:
    throw ((Throwable)new IllegalArgumentException("Step is zero."));
  }
}
