package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\020\001\n\000\n\002\020\016\n\000\n\002\020\005\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\n\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\000\032\023\020\004\032\004\030\0010\005*\0020\003H\007?\006\002\020\006\032\033\020\004\032\004\030\0010\005*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\t\032\023\020\n\032\004\030\0010\b*\0020\003H\007?\006\002\020\013\032\033\020\n\032\004\030\0010\b*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\f\032\023\020\r\032\004\030\0010\016*\0020\003H\007?\006\002\020\017\032\033\020\r\032\004\030\0010\016*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\020\032\023\020\021\032\004\030\0010\022*\0020\003H\007?\006\002\020\023\032\033\020\021\032\004\030\0010\022*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\024?\006\025"}, d2={"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
  extends StringsKt__StringNumberConversionsJVMKt
{
  public StringsKt__StringNumberConversionsKt() {}
  
  public static final Void numberFormatError(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "input");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid number format: '");
    localStringBuilder.append(paramString);
    localStringBuilder.append('\'');
    throw ((Throwable)new NumberFormatException(localStringBuilder.toString()));
  }
  
  public static final Byte toByteOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toByteOrNull");
    return StringsKt.toByteOrNull(paramString, 10);
  }
  
  public static final Byte toByteOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toByteOrNull");
    paramString = StringsKt.toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if ((paramInt >= -128) && (paramInt <= 127)) {
        return Byte.valueOf((byte)paramInt);
      }
    }
    return null;
  }
  
  public static final Integer toIntOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toIntOrNull");
    return StringsKt.toIntOrNull(paramString, 10);
  }
  
  public static final Integer toIntOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toIntOrNull");
    CharsKt.checkRadix(paramInt);
    int i = paramString.length();
    if (i == 0) {
      return null;
    }
    int j = 0;
    int k = paramString.charAt(0);
    int m = -2147483647;
    int n = 1;
    if (k < 48)
    {
      if (i == 1) {
        return null;
      }
      if (k == 45)
      {
        m = Integer.MIN_VALUE;
        k = 1;
      }
      else if (k == 43)
      {
        k = 0;
      }
      else
      {
        return null;
      }
    }
    else
    {
      k = 0;
      n = k;
    }
    int i1 = -59652323;
    int i2 = n;
    while (i2 < i)
    {
      int i3 = CharsKt.digitOf(paramString.charAt(i2), paramInt);
      if (i3 < 0) {
        return null;
      }
      n = i1;
      if (j < i1) {
        if (i1 == -59652323)
        {
          i1 = m / paramInt;
          n = i1;
          if (j >= i1) {}
        }
        else
        {
          return null;
        }
      }
      j *= paramInt;
      if (j < m + i3) {
        return null;
      }
      j -= i3;
      i2++;
      i1 = n;
    }
    if (k != 0) {
      paramString = Integer.valueOf(j);
    } else {
      paramString = Integer.valueOf(-j);
    }
    return paramString;
  }
  
  public static final Long toLongOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toLongOrNull");
    return StringsKt.toLongOrNull(paramString, 10);
  }
  
  public static final Long toLongOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toLongOrNull");
    CharsKt.checkRadix(paramInt);
    int i = paramString.length();
    if (i == 0) {
      return null;
    }
    int j = 0;
    int k = paramString.charAt(0);
    long l1 = -9223372036854775807L;
    int m = 1;
    if (k < 48)
    {
      if (i == 1) {
        return null;
      }
      if (k == 45)
      {
        l1 = Long.MIN_VALUE;
        j = 1;
      }
      else if (k == 43)
      {
        m = 0;
        j = 1;
      }
      else
      {
        return null;
      }
    }
    else
    {
      m = 0;
    }
    long l2 = 0L;
    long l4;
    for (long l3 = -256204778801521550L; j < i; l3 = l4)
    {
      k = CharsKt.digitOf(paramString.charAt(j), paramInt);
      if (k < 0) {
        return null;
      }
      l4 = l3;
      if (l2 < l3) {
        if (l3 == -256204778801521550L)
        {
          l3 = l1 / paramInt;
          l4 = l3;
          if (l2 >= l3) {}
        }
        else
        {
          return null;
        }
      }
      l2 *= paramInt;
      l3 = k;
      if (l2 < l1 + l3) {
        return null;
      }
      l2 -= l3;
      j++;
    }
    if (m != 0) {
      paramString = Long.valueOf(l2);
    } else {
      paramString = Long.valueOf(-l2);
    }
    return paramString;
  }
  
  public static final Short toShortOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toShortOrNull");
    return StringsKt.toShortOrNull(paramString, 10);
  }
  
  public static final Short toShortOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toShortOrNull");
    paramString = StringsKt.toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if ((paramInt >= 32768) && (paramInt <= 32767)) {
        return Short.valueOf((short)paramInt);
      }
    }
    return null;
  }
}
