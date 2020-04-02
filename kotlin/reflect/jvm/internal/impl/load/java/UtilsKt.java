package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.NumberWithRadix;
import kotlin.reflect.jvm.internal.impl.utils.NumbersKt;
import kotlin.text.StringsKt;

public final class UtilsKt
{
  public static final JavaDefaultValue lexicalCastFrom(KotlinType paramKotlinType, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$lexicalCastFrom");
    Intrinsics.checkParameterIsNotNull(paramString, "value");
    Object localObject1 = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool = localObject1 instanceof ClassDescriptor;
    Object localObject2 = null;
    KotlinType localKotlinType = null;
    if (bool)
    {
      localObject1 = (ClassDescriptor)localObject1;
      if (((ClassDescriptor)localObject1).getKind() == ClassKind.ENUM_CLASS)
      {
        paramKotlinType = ((ClassDescriptor)localObject1).getUnsubstitutedInnerClassesScope();
        paramString = Name.identifier(paramString);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "Name.identifier(value)");
        paramString = paramKotlinType.getContributedClassifier(paramString, (LookupLocation)NoLookupLocation.FROM_BACKEND);
        paramKotlinType = localKotlinType;
        if ((paramString instanceof ClassDescriptor))
        {
          paramString = (ClassDescriptor)paramString;
          paramKotlinType = localKotlinType;
          if (paramString.getKind() == ClassKind.ENUM_ENTRY) {
            paramKotlinType = (JavaDefaultValue)new EnumEntry(paramString);
          }
        }
        return paramKotlinType;
      }
    }
    localKotlinType = TypeUtilsKt.makeNotNullable(paramKotlinType);
    localObject1 = NumbersKt.extractRadix(paramString);
    paramKotlinType = ((NumberWithRadix)localObject1).component1();
    int i = ((NumberWithRadix)localObject1).component2();
    try
    {
      if (KotlinBuiltIns.isBoolean(localKotlinType))
      {
        paramString = Boolean.valueOf(Boolean.parseBoolean(paramString));
      }
      else if (KotlinBuiltIns.isChar(localKotlinType))
      {
        paramString = StringsKt.singleOrNull((CharSequence)paramString);
      }
      else if (KotlinBuiltIns.isByte(localKotlinType))
      {
        paramString = StringsKt.toByteOrNull(paramKotlinType, i);
      }
      else if (KotlinBuiltIns.isShort(localKotlinType))
      {
        paramString = StringsKt.toShortOrNull(paramKotlinType, i);
      }
      else if (KotlinBuiltIns.isInt(localKotlinType))
      {
        paramString = StringsKt.toIntOrNull(paramKotlinType, i);
      }
      else if (KotlinBuiltIns.isLong(localKotlinType))
      {
        paramString = StringsKt.toLongOrNull(paramKotlinType, i);
      }
      else if (KotlinBuiltIns.isFloat(localKotlinType))
      {
        paramString = StringsKt.toFloatOrNull(paramString);
      }
      else if (KotlinBuiltIns.isDouble(localKotlinType))
      {
        paramString = StringsKt.toDoubleOrNull(paramString);
      }
      else
      {
        bool = KotlinBuiltIns.isString(localKotlinType);
        if (!bool) {}
      }
    }
    catch (IllegalArgumentException paramKotlinType)
    {
      for (;;) {}
    }
    paramString = null;
    paramKotlinType = localObject2;
    if (paramString != null) {
      paramKotlinType = (JavaDefaultValue)new Constant(paramString);
    }
    return paramKotlinType;
  }
}
