package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class RenderingUtilsKt
{
  public static final String render(FqNameUnsafe paramFqNameUnsafe)
  {
    Intrinsics.checkParameterIsNotNull(paramFqNameUnsafe, "$this$render");
    paramFqNameUnsafe = paramFqNameUnsafe.pathSegments();
    Intrinsics.checkExpressionValueIsNotNull(paramFqNameUnsafe, "pathSegments()");
    return renderFqName(paramFqNameUnsafe);
  }
  
  public static final String render(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "$this$render");
    if (shouldBeEscaped(paramName))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str = paramName.asString();
      Intrinsics.checkExpressionValueIsNotNull(str, "asString()");
      paramName = new StringBuilder();
      paramName.append(String.valueOf('`'));
      paramName.append(str);
      localStringBuilder.append(paramName.toString());
      localStringBuilder.append('`');
      paramName = localStringBuilder.toString();
    }
    else
    {
      paramName = paramName.asString();
      Intrinsics.checkExpressionValueIsNotNull(paramName, "asString()");
    }
    return paramName;
  }
  
  public static final String renderFqName(List<Name> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "pathSegments");
    StringBuilder localStringBuilder = new StringBuilder();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Name localName = (Name)paramList.next();
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append(".");
      }
      localStringBuilder.append(render(localName));
    }
    paramList = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramList, "StringBuilder().apply(builderAction).toString()");
    return paramList;
  }
  
  private static final boolean shouldBeEscaped(Name paramName)
  {
    boolean bool1 = paramName.isSpecial();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    paramName = paramName.asString();
    Intrinsics.checkExpressionValueIsNotNull(paramName, "asString()");
    if (!KeywordStringsGenerated.KEYWORDS.contains(paramName))
    {
      paramName = (CharSequence)paramName;
      for (int i = 0; i < paramName.length(); i++)
      {
        char c = paramName.charAt(i);
        int j;
        if ((!Character.isLetterOrDigit(c)) && (c != '_')) {
          j = 1;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          i = 1;
          break label104;
        }
      }
      i = 0;
      label104:
      if (i == 0) {}
    }
    else
    {
      bool2 = true;
    }
    return bool2;
  }
}
