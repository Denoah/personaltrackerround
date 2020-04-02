package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class NumbersKt
{
  public static final NumberWithRadix extractRadix(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "value");
    if ((!StringsKt.startsWith$default(paramString, "0x", false, 2, null)) && (!StringsKt.startsWith$default(paramString, "0X", false, 2, null)))
    {
      if ((!StringsKt.startsWith$default(paramString, "0b", false, 2, null)) && (!StringsKt.startsWith$default(paramString, "0B", false, 2, null)))
      {
        paramString = new NumberWithRadix(paramString, 10);
      }
      else
      {
        paramString = paramString.substring(2);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
        paramString = new NumberWithRadix(paramString, 2);
      }
    }
    else
    {
      paramString = paramString.substring(2);
      Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
      paramString = new NumberWithRadix(paramString, 16);
    }
    return paramString;
  }
}
