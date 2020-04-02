package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.text.StringsKt;

final class JvmTypeFactoryImpl
  implements JvmTypeFactory<JvmType>
{
  public static final JvmTypeFactoryImpl INSTANCE = new JvmTypeFactoryImpl();
  
  private JvmTypeFactoryImpl() {}
  
  public JvmType boxType(JvmType paramJvmType)
  {
    Intrinsics.checkParameterIsNotNull(paramJvmType, "possiblyPrimitiveType");
    JvmType localJvmType = paramJvmType;
    if ((paramJvmType instanceof JvmType.Primitive))
    {
      JvmType.Primitive localPrimitive = (JvmType.Primitive)paramJvmType;
      localJvmType = paramJvmType;
      if (localPrimitive.getJvmPrimitiveType() != null)
      {
        paramJvmType = JvmClassName.byFqNameWithoutInnerClasses(localPrimitive.getJvmPrimitiveType().getWrapperFqName());
        Intrinsics.checkExpressionValueIsNotNull(paramJvmType, "JvmClassName.byFqNameWit…mitiveType.wrapperFqName)");
        paramJvmType = paramJvmType.getInternalName();
        Intrinsics.checkExpressionValueIsNotNull(paramJvmType, "JvmClassName.byFqNameWit…apperFqName).internalName");
        localJvmType = (JvmType)createObjectType(paramJvmType);
      }
    }
    return localJvmType;
  }
  
  public JvmType createFromString(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "representation");
    CharSequence localCharSequence = (CharSequence)paramString;
    int i = localCharSequence.length();
    int j = 0;
    if (i > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0)) {
      throw ((Throwable)new AssertionError("empty string as JvmType"));
    }
    int k = paramString.charAt(0);
    for (localObject : JvmPrimitiveType.values())
    {
      int n;
      if (((JvmPrimitiveType)localObject).getDesc().charAt(0) == k) {
        n = 1;
      } else {
        n = 0;
      }
      if (n != 0) {
        break label129;
      }
    }
    Object localObject = null;
    label129:
    if (localObject != null) {
      return (JvmType)new JvmType.Primitive((JvmPrimitiveType)localObject);
    }
    if (k != 86)
    {
      if (k != 91)
      {
        i = j;
        if (k == 76)
        {
          i = j;
          if (StringsKt.endsWith$default(localCharSequence, ';', false, 2, null)) {
            i = 1;
          }
        }
        if ((_Assertions.ENABLED) && (i == 0))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Type that is not primitive nor array should be Object, but '");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append("' was found");
          throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
        }
        paramString = paramString.substring(1, paramString.length() - 1);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        paramString = (JvmType)new JvmType.Object(paramString);
      }
      else
      {
        paramString = paramString.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
        paramString = (JvmType)new JvmType.Array(createFromString(paramString));
      }
    }
    else {
      paramString = (JvmType)new JvmType.Primitive(null);
    }
    return paramString;
  }
  
  public JvmType.Object createObjectType(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "internalName");
    return new JvmType.Object(paramString);
  }
  
  public JvmType getJavaLangClassType()
  {
    return (JvmType)createObjectType("java/lang/Class");
  }
  
  public String toString(JvmType paramJvmType)
  {
    Intrinsics.checkParameterIsNotNull(paramJvmType, "type");
    StringBuilder localStringBuilder;
    if ((paramJvmType instanceof JvmType.Array))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append(toString(((JvmType.Array)paramJvmType).getElementType()));
      paramJvmType = localStringBuilder.toString();
    }
    else if ((paramJvmType instanceof JvmType.Primitive))
    {
      paramJvmType = ((JvmType.Primitive)paramJvmType).getJvmPrimitiveType();
      if (paramJvmType != null)
      {
        paramJvmType = paramJvmType.getDesc();
        if (paramJvmType != null) {}
      }
      else
      {
        paramJvmType = "V";
      }
    }
    else
    {
      if (!(paramJvmType instanceof JvmType.Object)) {
        break label137;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("L");
      localStringBuilder.append(((JvmType.Object)paramJvmType).getInternalName());
      localStringBuilder.append(";");
      paramJvmType = localStringBuilder.toString();
    }
    return paramJvmType;
    label137:
    throw new NoWhenBranchMatchedException();
  }
}
