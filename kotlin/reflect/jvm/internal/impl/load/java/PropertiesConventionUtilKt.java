package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.StringsKt;

public final class PropertiesConventionUtilKt
{
  public static final List<Name> getPropertyNamesCandidatesByAccessorName(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    String str = paramName.asString();
    Intrinsics.checkExpressionValueIsNotNull(str, "name.asString()");
    if (JvmAbi.isGetterName(str)) {
      return CollectionsKt.listOfNotNull(propertyNameByGetMethodName(paramName));
    }
    if (JvmAbi.isSetterName(str)) {
      return propertyNamesBySetMethodName(paramName);
    }
    return BuiltinSpecialProperties.INSTANCE.getPropertyNameCandidatesBySpecialGetterName(paramName);
  }
  
  public static final Name propertyNameByGetMethodName(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "methodName");
    Name localName = propertyNameFromAccessorMethodName$default(paramName, "get", false, null, 12, null);
    if (localName != null) {
      paramName = localName;
    } else {
      paramName = propertyNameFromAccessorMethodName$default(paramName, "is", false, null, 8, null);
    }
    return paramName;
  }
  
  public static final Name propertyNameBySetMethodName(Name paramName, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "methodName");
    String str;
    if (paramBoolean) {
      str = "is";
    } else {
      str = null;
    }
    return propertyNameFromAccessorMethodName$default(paramName, "set", false, str, 4, null);
  }
  
  private static final Name propertyNameFromAccessorMethodName(Name paramName, String paramString1, boolean paramBoolean, String paramString2)
  {
    if (paramName.isSpecial()) {
      return null;
    }
    String str = paramName.getIdentifier();
    Intrinsics.checkExpressionValueIsNotNull(str, "methodName.identifier");
    if (!StringsKt.startsWith$default(str, paramString1, false, 2, null)) {
      return null;
    }
    if (str.length() == paramString1.length()) {
      return null;
    }
    int i = str.charAt(paramString1.length());
    if ((97 <= i) && (122 >= i)) {
      return null;
    }
    if (paramString2 != null)
    {
      if ((_Assertions.ENABLED) && (!paramBoolean)) {
        throw ((Throwable)new AssertionError("Assertion failed"));
      }
      paramName = new StringBuilder();
      paramName.append(paramString2);
      paramName.append(StringsKt.removePrefix(str, (CharSequence)paramString1));
      return Name.identifier(paramName.toString());
    }
    if (!paramBoolean) {
      return paramName;
    }
    paramName = CapitalizeDecapitalizeKt.decapitalizeSmartForCompiler(StringsKt.removePrefix(str, (CharSequence)paramString1), true);
    if (!Name.isValidIdentifier(paramName)) {
      return null;
    }
    return Name.identifier(paramName);
  }
  
  public static final List<Name> propertyNamesBySetMethodName(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "methodName");
    return CollectionsKt.listOfNotNull(new Name[] { propertyNameBySetMethodName(paramName, false), propertyNameBySetMethodName(paramName, true) });
  }
}
