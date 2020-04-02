package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class AnnotationUtilKt
{
  private static final Name DEPRECATED_LEVEL_NAME;
  private static final Name DEPRECATED_MESSAGE_NAME;
  private static final Name DEPRECATED_REPLACE_WITH_NAME;
  private static final Name REPLACE_WITH_EXPRESSION_NAME;
  private static final Name REPLACE_WITH_IMPORTS_NAME;
  
  static
  {
    Name localName = Name.identifier("message");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"message\")");
    DEPRECATED_MESSAGE_NAME = localName;
    localName = Name.identifier("replaceWith");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"replaceWith\")");
    DEPRECATED_REPLACE_WITH_NAME = localName;
    localName = Name.identifier("level");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"level\")");
    DEPRECATED_LEVEL_NAME = localName;
    localName = Name.identifier("expression");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"expression\")");
    REPLACE_WITH_EXPRESSION_NAME = localName;
    localName = Name.identifier("imports");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"imports\")");
    REPLACE_WITH_IMPORTS_NAME = localName;
  }
  
  public static final AnnotationDescriptor createDeprecatedAnnotation(KotlinBuiltIns paramKotlinBuiltIns, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinBuiltIns, "$this$createDeprecatedAnnotation");
    Intrinsics.checkParameterIsNotNull(paramString1, "message");
    Intrinsics.checkParameterIsNotNull(paramString2, "replaceWith");
    Intrinsics.checkParameterIsNotNull(paramString3, "level");
    Object localObject = KotlinBuiltIns.FQ_NAMES.replaceWith;
    Intrinsics.checkExpressionValueIsNotNull(localObject, "KotlinBuiltIns.FQ_NAMES.replaceWith");
    localObject = new BuiltInAnnotationDescriptor(paramKotlinBuiltIns, (FqName)localObject, MapsKt.mapOf(new Pair[] { TuplesKt.to(REPLACE_WITH_EXPRESSION_NAME, new StringValue(paramString2)), TuplesKt.to(REPLACE_WITH_IMPORTS_NAME, new ArrayValue(CollectionsKt.emptyList(), (Function1)new Lambda(paramKotlinBuiltIns)
    {
      public final SimpleType invoke(ModuleDescriptor paramAnonymousModuleDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousModuleDescriptor, "module");
        paramAnonymousModuleDescriptor = paramAnonymousModuleDescriptor.getBuiltIns().getArrayType(Variance.INVARIANT, (KotlinType)this.$this_createDeprecatedAnnotation.getStringType());
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousModuleDescriptor, "module.builtIns.getArray…ce.INVARIANT, stringType)");
        return paramAnonymousModuleDescriptor;
      }
    })) }));
    paramString2 = KotlinBuiltIns.FQ_NAMES.deprecated;
    Intrinsics.checkExpressionValueIsNotNull(paramString2, "KotlinBuiltIns.FQ_NAMES.deprecated");
    paramString1 = TuplesKt.to(DEPRECATED_MESSAGE_NAME, new StringValue(paramString1));
    localObject = TuplesKt.to(DEPRECATED_REPLACE_WITH_NAME, new AnnotationValue((AnnotationDescriptor)localObject));
    Name localName = DEPRECATED_LEVEL_NAME;
    ClassId localClassId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.deprecationLevel);
    Intrinsics.checkExpressionValueIsNotNull(localClassId, "ClassId.topLevel(KotlinB…Q_NAMES.deprecationLevel)");
    paramString3 = Name.identifier(paramString3);
    Intrinsics.checkExpressionValueIsNotNull(paramString3, "Name.identifier(level)");
    return (AnnotationDescriptor)new BuiltInAnnotationDescriptor(paramKotlinBuiltIns, paramString2, MapsKt.mapOf(new Pair[] { paramString1, localObject, TuplesKt.to(localName, new EnumValue(localClassId, paramString3)) }));
  }
}
