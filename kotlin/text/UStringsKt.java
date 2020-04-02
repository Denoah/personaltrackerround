package kotlin.text;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\000\n\002\020\016\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\023\032\036\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H\007?\001\000?\006\004\b\005\020\006\032\036\020\000\032\0020\001*\0020\0072\006\020\003\032\0020\004H\007?\001\000?\006\004\b\b\020\t\032\036\020\000\032\0020\001*\0020\n2\006\020\003\032\0020\004H\007?\001\000?\006\004\b\013\020\f\032\036\020\000\032\0020\001*\0020\r2\006\020\003\032\0020\004H\007?\001\000?\006\004\b\016\020\017\032\024\020\020\032\0020\002*\0020\001H\007?\001\000?\006\002\020\021\032\034\020\020\032\0020\002*\0020\0012\006\020\003\032\0020\004H\007?\001\000?\006\002\020\022\032\021\020\023\032\004\030\0010\002*\0020\001H\007?\001\000\032\031\020\023\032\004\030\0010\002*\0020\0012\006\020\003\032\0020\004H\007?\001\000\032\024\020\024\032\0020\007*\0020\001H\007?\001\000?\006\002\020\025\032\034\020\024\032\0020\007*\0020\0012\006\020\003\032\0020\004H\007?\001\000?\006\002\020\026\032\021\020\027\032\004\030\0010\007*\0020\001H\007?\001\000\032\031\020\027\032\004\030\0010\007*\0020\0012\006\020\003\032\0020\004H\007?\001\000\032\024\020\030\032\0020\n*\0020\001H\007?\001\000?\006\002\020\031\032\034\020\030\032\0020\n*\0020\0012\006\020\003\032\0020\004H\007?\001\000?\006\002\020\032\032\021\020\033\032\004\030\0010\n*\0020\001H\007?\001\000\032\031\020\033\032\004\030\0010\n*\0020\0012\006\020\003\032\0020\004H\007?\001\000\032\024\020\034\032\0020\r*\0020\001H\007?\001\000?\006\002\020\035\032\034\020\034\032\0020\r*\0020\0012\006\020\003\032\0020\004H\007?\001\000?\006\002\020\036\032\021\020\037\032\004\030\0010\r*\0020\001H\007?\001\000\032\031\020\037\032\004\030\0010\r*\0020\0012\006\020\003\032\0020\004H\007?\001\000?\002\004\n\002\b\031?\006 "}, d2={"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UStringsKt
{
  public static final String toString-JSWoG40(long paramLong, int paramInt)
  {
    return UnsignedKt.ulongToString(paramLong, CharsKt.checkRadix(paramInt));
  }
  
  public static final String toString-LxnNnR4(byte paramByte, int paramInt)
  {
    String str = Integer.toString(paramByte & 0xFF, CharsKt.checkRadix(paramInt));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
    return str;
  }
  
  public static final String toString-V7xB4Y4(int paramInt1, int paramInt2)
  {
    String str = Long.toString(paramInt1 & 0xFFFFFFFF, CharsKt.checkRadix(paramInt2));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Long.toString(this, checkRadix(radix))");
    return str;
  }
  
  public static final String toString-olVBNx4(short paramShort, int paramInt)
  {
    String str = Integer.toString(paramShort & 0xFFFF, CharsKt.checkRadix(paramInt));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Integer.toStri…(this, checkRadix(radix))");
    return str;
  }
  
  public static final byte toUByte(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUByte");
    UByte localUByte = toUByteOrNull(paramString);
    if (localUByte != null) {
      return localUByte.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final byte toUByte(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUByte");
    UByte localUByte = toUByteOrNull(paramString, paramInt);
    if (localUByte != null) {
      return localUByte.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final UByte toUByteOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUByteOrNull");
    return toUByteOrNull(paramString, 10);
  }
  
  public static final UByte toUByteOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUByteOrNull");
    paramString = toUIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.unbox-impl();
      if (UnsignedKt.uintCompare(paramInt, UInt.constructor-impl(255)) > 0) {
        return null;
      }
      return UByte.box-impl(UByte.constructor-impl((byte)paramInt));
    }
    return null;
  }
  
  public static final int toUInt(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUInt");
    UInt localUInt = toUIntOrNull(paramString);
    if (localUInt != null) {
      return localUInt.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final int toUInt(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUInt");
    UInt localUInt = toUIntOrNull(paramString, paramInt);
    if (localUInt != null) {
      return localUInt.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final UInt toUIntOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUIntOrNull");
    return toUIntOrNull(paramString, 10);
  }
  
  public static final UInt toUIntOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUIntOrNull");
    CharsKt.checkRadix(paramInt);
    int i = paramString.length();
    if (i == 0) {
      return null;
    }
    int j = 0;
    int k = paramString.charAt(0);
    int m = 1;
    if (k < 48)
    {
      if ((i == 1) || (k != 43)) {
        return null;
      }
    }
    else {
      m = 0;
    }
    int n = UInt.constructor-impl(paramInt);
    int i1 = 119304647;
    k = m;
    while (k < i)
    {
      int i2 = CharsKt.digitOf(paramString.charAt(k), paramInt);
      if (i2 < 0) {
        return null;
      }
      m = i1;
      if (UnsignedKt.uintCompare(j, i1) > 0) {
        if (i1 == 119304647)
        {
          i1 = UnsignedKt.uintDivide-J1ME1BU(-1, n);
          m = i1;
          if (UnsignedKt.uintCompare(j, i1) <= 0) {}
        }
        else
        {
          return null;
        }
      }
      i1 = UInt.constructor-impl(j * n);
      j = UInt.constructor-impl(UInt.constructor-impl(i2) + i1);
      if (UnsignedKt.uintCompare(j, i1) < 0) {
        return null;
      }
      k++;
      i1 = m;
    }
    return UInt.box-impl(j);
  }
  
  public static final long toULong(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toULong");
    ULong localULong = toULongOrNull(paramString);
    if (localULong != null) {
      return localULong.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final long toULong(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toULong");
    ULong localULong = toULongOrNull(paramString, paramInt);
    if (localULong != null) {
      return localULong.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final ULong toULongOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toULongOrNull");
    return toULongOrNull(paramString, 10);
  }
  
  public static final ULong toULongOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toULongOrNull");
    CharsKt.checkRadix(paramInt);
    int i = paramString.length();
    if (i == 0) {
      return null;
    }
    int j = 0;
    int k = paramString.charAt(0);
    if (k < 48) {
      if ((i != 1) && (k == 43)) {
        j = 1;
      } else {
        return null;
      }
    }
    long l1 = ULong.constructor-impl(paramInt);
    long l2 = 0L;
    long l4;
    for (long l3 = 512409557603043100L; j < i; l3 = l4)
    {
      k = CharsKt.digitOf(paramString.charAt(j), paramInt);
      if (k < 0) {
        return null;
      }
      l4 = l3;
      if (UnsignedKt.ulongCompare(l2, l3) > 0) {
        if (l3 == 512409557603043100L)
        {
          l3 = UnsignedKt.ulongDivide-eb3DHEI(-1L, l1);
          l4 = l3;
          if (UnsignedKt.ulongCompare(l2, l3) <= 0) {}
        }
        else
        {
          return null;
        }
      }
      l3 = ULong.constructor-impl(l2 * l1);
      l2 = ULong.constructor-impl(ULong.constructor-impl(UInt.constructor-impl(k) & 0xFFFFFFFF) + l3);
      if (UnsignedKt.ulongCompare(l2, l3) < 0) {
        return null;
      }
      j++;
    }
    return ULong.box-impl(l2);
  }
  
  public static final short toUShort(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUShort");
    UShort localUShort = toUShortOrNull(paramString);
    if (localUShort != null) {
      return localUShort.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final short toUShort(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUShort");
    UShort localUShort = toUShortOrNull(paramString, paramInt);
    if (localUShort != null) {
      return localUShort.unbox-impl();
    }
    StringsKt.numberFormatError(paramString);
    throw null;
  }
  
  public static final UShort toUShortOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUShortOrNull");
    return toUShortOrNull(paramString, 10);
  }
  
  public static final UShort toUShortOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUShortOrNull");
    paramString = toUIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.unbox-impl();
      if (UnsignedKt.uintCompare(paramInt, UInt.constructor-impl(65535)) > 0) {
        return null;
      }
      return UShort.box-impl(UShort.constructor-impl((short)paramInt));
    }
    return null;
  }
}
