package kotlin.reflect.jvm.internal.impl.name;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class FqNamesUtilKt
{
  private static final boolean isSubpackageOf(String paramString1, String paramString2)
  {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (StringsKt.startsWith$default(paramString1, paramString2, false, 2, null))
    {
      bool2 = bool1;
      if (paramString1.charAt(paramString2.length()) == '.') {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public static final boolean isSubpackageOf(FqName paramFqName1, FqName paramFqName2)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName1, "$this$isSubpackageOf");
    Intrinsics.checkParameterIsNotNull(paramFqName2, "packageName");
    boolean bool1 = Intrinsics.areEqual(paramFqName1, paramFqName2);
    boolean bool2 = true;
    if ((!bool1) && (!paramFqName2.isRoot()))
    {
      paramFqName1 = paramFqName1.asString();
      Intrinsics.checkExpressionValueIsNotNull(paramFqName1, "this.asString()");
      paramFqName2 = paramFqName2.asString();
      Intrinsics.checkExpressionValueIsNotNull(paramFqName2, "packageName.asString()");
      bool2 = isSubpackageOf(paramFqName1, paramFqName2);
    }
    return bool2;
  }
  
  public static final boolean isValidJavaFqName(String paramString)
  {
    boolean bool = false;
    if (paramString == null) {
      return false;
    }
    State localState = State.BEGINNING;
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      int k = FqNamesUtilKt.WhenMappings.$EnumSwitchMapping$0[localState.ordinal()];
      if ((k != 1) && (k != 2))
      {
        if (k == 3) {
          if (c == '.') {
            localState = State.AFTER_DOT;
          } else if (!Character.isJavaIdentifierPart(c)) {
            return false;
          }
        }
      }
      else
      {
        if (!Character.isJavaIdentifierPart(c)) {
          return false;
        }
        localState = State.MIDDLE;
      }
    }
    if (localState != State.AFTER_DOT) {
      bool = true;
    }
    return bool;
  }
  
  public static final FqName tail(FqName paramFqName1, FqName paramFqName2)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName1, "$this$tail");
    Intrinsics.checkParameterIsNotNull(paramFqName2, "prefix");
    FqName localFqName = paramFqName1;
    if (isSubpackageOf(paramFqName1, paramFqName2)) {
      if (paramFqName2.isRoot())
      {
        localFqName = paramFqName1;
      }
      else if (Intrinsics.areEqual(paramFqName1, paramFqName2))
      {
        localFqName = FqName.ROOT;
        Intrinsics.checkExpressionValueIsNotNull(localFqName, "FqName.ROOT");
      }
      else
      {
        paramFqName1 = paramFqName1.asString();
        Intrinsics.checkExpressionValueIsNotNull(paramFqName1, "asString()");
        int i = paramFqName2.asString().length();
        if (paramFqName1 != null)
        {
          paramFqName1 = paramFqName1.substring(i + 1);
          Intrinsics.checkExpressionValueIsNotNull(paramFqName1, "(this as java.lang.String).substring(startIndex)");
          localFqName = new FqName(paramFqName1);
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
      }
    }
    return localFqName;
  }
}
