package kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize;

import java.util.Iterator;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class CapitalizeDecapitalizeKt
{
  public static final String capitalizeAsciiOnly(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$capitalizeAsciiOnly");
    int i;
    if (((CharSequence)paramString).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramString;
    }
    char c = paramString.charAt(0);
    Object localObject;
    if ('a' > c)
    {
      localObject = paramString;
    }
    else
    {
      localObject = paramString;
      if ('z' >= c)
      {
        c = Character.toUpperCase(c);
        paramString = paramString.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(String.valueOf(c));
        ((StringBuilder)localObject).append(paramString);
        localObject = ((StringBuilder)localObject).toString();
      }
    }
    return localObject;
  }
  
  public static final String decapitalizeAsciiOnly(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$decapitalizeAsciiOnly");
    int i;
    if (((CharSequence)paramString).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramString;
    }
    char c = paramString.charAt(0);
    String str;
    if ('A' > c)
    {
      str = paramString;
    }
    else
    {
      str = paramString;
      if ('Z' >= c)
      {
        c = Character.toLowerCase(c);
        str = paramString.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).substring(startIndex)");
        paramString = new StringBuilder();
        paramString.append(String.valueOf(c));
        paramString.append(str);
        str = paramString.toString();
      }
    }
    return str;
  }
  
  public static final String decapitalizeSmartForCompiler(String paramString, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$decapitalizeSmartForCompiler");
    Object localObject1 = (CharSequence)paramString;
    int i;
    if (((CharSequence)localObject1).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject2 = paramString;
    if (i == 0) {
      if (!isUpperCaseCharAt(paramString, 0, paramBoolean))
      {
        localObject2 = paramString;
      }
      else
      {
        if ((paramString.length() != 1) && (isUpperCaseCharAt(paramString, 1, paramBoolean)))
        {
          localObject1 = ((Iterable)StringsKt.getIndices((CharSequence)localObject1)).iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = ((Iterator)localObject1).next();
            if ((isUpperCaseCharAt(paramString, ((Number)localObject2).intValue(), paramBoolean) ^ true)) {
              break label123;
            }
          }
          localObject2 = null;
          label123:
          localObject2 = (Integer)localObject2;
          if (localObject2 != null)
          {
            i = ((Integer)localObject2).intValue() - 1;
            localObject2 = new StringBuilder();
            localObject1 = paramString.substring(0, i);
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            ((StringBuilder)localObject2).append(toLowerCase((String)localObject1, paramBoolean));
            paramString = paramString.substring(i);
            Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
            ((StringBuilder)localObject2).append(paramString);
            return ((StringBuilder)localObject2).toString();
          }
          return toLowerCase(paramString, paramBoolean);
        }
        if (paramBoolean) {
          localObject2 = decapitalizeAsciiOnly(paramString);
        } else {
          localObject2 = StringsKt.decapitalize(paramString);
        }
      }
    }
    return localObject2;
  }
  
  private static final boolean isUpperCaseCharAt(String paramString, int paramInt, boolean paramBoolean)
  {
    char c = paramString.charAt(paramInt);
    if (paramBoolean)
    {
      if (('A' <= c) && ('Z' >= c)) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
    }
    else {
      paramBoolean = Character.isUpperCase(c);
    }
    return paramBoolean;
  }
  
  private static final String toLowerCase(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramString = toLowerCaseAsciiOnly(paramString);
    }
    else
    {
      if (paramString == null) {
        break label29;
      }
      paramString = paramString.toLowerCase();
      Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).toLowerCase()");
    }
    return paramString;
    label29:
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public static final String toLowerCaseAsciiOnly(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toLowerCaseAsciiOnly");
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c1 = paramString.charAt(j);
      char c2;
      if ('A' > c1)
      {
        c2 = c1;
      }
      else
      {
        c2 = c1;
        if ('Z' >= c1)
        {
          char c3 = Character.toLowerCase(c1);
          c2 = c3;
        }
      }
      localStringBuilder.append(c2);
    }
    paramString = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramString, "builder.toString()");
    return paramString;
  }
}
