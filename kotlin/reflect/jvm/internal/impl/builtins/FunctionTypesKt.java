package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin._Assertions;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory.Companion;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class FunctionTypesKt
{
  public static final SimpleType createFunctionType(KotlinBuiltIns paramKotlinBuiltIns, Annotations paramAnnotations, KotlinType paramKotlinType1, List<? extends KotlinType> paramList, List<Name> paramList1, KotlinType paramKotlinType2, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinBuiltIns, "builtIns");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramList, "parameterTypes");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "returnType");
    paramKotlinType2 = getFunctionTypeArgumentProjections(paramKotlinType1, paramList, paramList1, paramKotlinType2, paramKotlinBuiltIns);
    int i = paramList.size();
    if (paramKotlinType1 != null) {
      i++;
    }
    if (paramBoolean) {
      paramList = paramKotlinBuiltIns.getSuspendFunction(i);
    } else {
      paramList = paramKotlinBuiltIns.getFunction(i);
    }
    Intrinsics.checkExpressionValueIsNotNull(paramList, "if (suspendFunction) bui…tFunction(parameterCount)");
    paramList1 = paramAnnotations;
    if (paramKotlinType1 != null)
    {
      paramKotlinType1 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
      Intrinsics.checkExpressionValueIsNotNull(paramKotlinType1, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
      if (paramAnnotations.findAnnotation(paramKotlinType1) != null)
      {
        paramList1 = paramAnnotations;
      }
      else
      {
        paramKotlinType1 = Annotations.Companion;
        paramList1 = (Iterable)paramAnnotations;
        paramAnnotations = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
        Intrinsics.checkExpressionValueIsNotNull(paramAnnotations, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        paramList1 = paramKotlinType1.create(kotlin.collections.CollectionsKt.plus(paramList1, new BuiltInAnnotationDescriptor(paramKotlinBuiltIns, paramAnnotations, MapsKt.emptyMap())));
      }
    }
    return KotlinTypeFactory.simpleNotNullType(paramList1, paramList, paramKotlinType2);
  }
  
  public static final Name extractParameterNameFromFunctionTypeArgument(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$extractParameterNameFromFunctionTypeArgument");
    paramKotlinType = paramKotlinType.getAnnotations();
    Object localObject = KotlinBuiltIns.FQ_NAMES.parameterName;
    Intrinsics.checkExpressionValueIsNotNull(localObject, "KotlinBuiltIns.FQ_NAMES.parameterName");
    paramKotlinType = paramKotlinType.findAnnotation((FqName)localObject);
    if (paramKotlinType != null)
    {
      localObject = kotlin.collections.CollectionsKt.singleOrNull((Iterable)paramKotlinType.getAllValueArguments().values());
      paramKotlinType = (KotlinType)localObject;
      if (!(localObject instanceof StringValue)) {
        paramKotlinType = null;
      }
      paramKotlinType = (StringValue)paramKotlinType;
      if (paramKotlinType != null)
      {
        paramKotlinType = (String)paramKotlinType.getValue();
        if (paramKotlinType != null)
        {
          if (!Name.isValidIdentifier(paramKotlinType)) {
            paramKotlinType = null;
          }
          if (paramKotlinType != null) {
            return Name.identifier(paramKotlinType);
          }
        }
      }
    }
    return null;
  }
  
  public static final List<TypeProjection> getFunctionTypeArgumentProjections(KotlinType paramKotlinType1, List<? extends KotlinType> paramList, List<Name> paramList1, KotlinType paramKotlinType2, KotlinBuiltIns paramKotlinBuiltIns)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "parameterTypes");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "returnType");
    Intrinsics.checkParameterIsNotNull(paramKotlinBuiltIns, "builtIns");
    int i = paramList.size();
    int j = 0;
    if (paramKotlinType1 != null) {
      k = 1;
    } else {
      k = 0;
    }
    ArrayList localArrayList = new ArrayList(i + k + 1);
    Collection localCollection = (Collection)localArrayList;
    if (paramKotlinType1 != null) {
      paramKotlinType1 = TypeUtilsKt.asTypeProjection(paramKotlinType1);
    } else {
      paramKotlinType1 = null;
    }
    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(localCollection, paramKotlinType1);
    Iterator localIterator = ((Iterable)paramList).iterator();
    for (int k = j; localIterator.hasNext(); k++)
    {
      paramKotlinType1 = localIterator.next();
      if (k < 0) {
        kotlin.collections.CollectionsKt.throwIndexOverflow();
      }
      KotlinType localKotlinType = (KotlinType)paramKotlinType1;
      if (paramList1 != null)
      {
        paramKotlinType1 = (Name)paramList1.get(k);
        if ((paramKotlinType1 != null) && (!paramKotlinType1.isSpecial())) {}
      }
      else
      {
        paramKotlinType1 = null;
      }
      paramList = localKotlinType;
      if (paramKotlinType1 != null)
      {
        FqName localFqName = KotlinBuiltIns.FQ_NAMES.parameterName;
        Intrinsics.checkExpressionValueIsNotNull(localFqName, "KotlinBuiltIns.FQ_NAMES.parameterName");
        paramList = Name.identifier("name");
        paramKotlinType1 = paramKotlinType1.asString();
        Intrinsics.checkExpressionValueIsNotNull(paramKotlinType1, "name.asString()");
        paramKotlinType1 = new BuiltInAnnotationDescriptor(paramKotlinBuiltIns, localFqName, MapsKt.mapOf(TuplesKt.to(paramList, new StringValue(paramKotlinType1))));
        paramList = TypeUtilsKt.replaceAnnotations(localKotlinType, Annotations.Companion.create(kotlin.collections.CollectionsKt.plus((Iterable)localKotlinType.getAnnotations(), paramKotlinType1)));
      }
      localCollection.add(TypeUtilsKt.asTypeProjection(paramList));
    }
    localArrayList.add(TypeUtilsKt.asTypeProjection(paramKotlinType2));
    return (List)localArrayList;
  }
  
  public static final FunctionClassDescriptor.Kind getFunctionalClassKind(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$getFunctionalClassKind");
    if (!(paramDeclarationDescriptor instanceof ClassDescriptor)) {
      return null;
    }
    if (!KotlinBuiltIns.isUnderKotlinPackage(paramDeclarationDescriptor)) {
      return null;
    }
    return getFunctionalClassKind(DescriptorUtilsKt.getFqNameUnsafe(paramDeclarationDescriptor));
  }
  
  private static final FunctionClassDescriptor.Kind getFunctionalClassKind(FqNameUnsafe paramFqNameUnsafe)
  {
    if ((paramFqNameUnsafe.isSafe()) && (!paramFqNameUnsafe.isRoot()))
    {
      BuiltInFictitiousFunctionClassFactory.Companion localCompanion = BuiltInFictitiousFunctionClassFactory.Companion;
      String str = paramFqNameUnsafe.shortName().asString();
      Intrinsics.checkExpressionValueIsNotNull(str, "shortName().asString()");
      paramFqNameUnsafe = paramFqNameUnsafe.toSafe().parent();
      Intrinsics.checkExpressionValueIsNotNull(paramFqNameUnsafe, "toSafe().parent()");
      return localCompanion.getFunctionalClassKind(str, paramFqNameUnsafe);
    }
    return null;
  }
  
  public static final KotlinType getReceiverTypeFromFunctionType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getReceiverTypeFromFunctionType");
    boolean bool = isBuiltinFunctionalType(paramKotlinType);
    if ((_Assertions.ENABLED) && (!bool))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Not a function type: ");
      localStringBuilder.append(paramKotlinType);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    if (isTypeAnnotatedWithExtensionFunctionType(paramKotlinType)) {
      paramKotlinType = ((TypeProjection)kotlin.collections.CollectionsKt.first(paramKotlinType.getArguments())).getType();
    } else {
      paramKotlinType = null;
    }
    return paramKotlinType;
  }
  
  public static final KotlinType getReturnTypeFromFunctionType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getReturnTypeFromFunctionType");
    boolean bool = isBuiltinFunctionalType(paramKotlinType);
    if ((_Assertions.ENABLED) && (!bool))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Not a function type: ");
      localStringBuilder.append(paramKotlinType);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    paramKotlinType = ((TypeProjection)kotlin.collections.CollectionsKt.last(paramKotlinType.getArguments())).getType();
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "arguments.last().type");
    return paramKotlinType;
  }
  
  public static final List<TypeProjection> getValueParameterTypesFromFunctionType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getValueParameterTypesFromFunctionType");
    boolean bool = isBuiltinFunctionalType(paramKotlinType);
    if ((_Assertions.ENABLED) && (!bool))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Not a function type: ");
      ((StringBuilder)localObject).append(paramKotlinType);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
    }
    Object localObject = paramKotlinType.getArguments();
    int i = isBuiltinExtensionFunctionalType(paramKotlinType);
    int j = ((List)localObject).size();
    int k = 1;
    j--;
    if (i > j) {
      k = 0;
    }
    if ((_Assertions.ENABLED) && (k == 0))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Not an exact function type: ");
      ((StringBuilder)localObject).append(paramKotlinType);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
    }
    return ((List)localObject).subList(i, j);
  }
  
  public static final boolean isBuiltinExtensionFunctionalType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isBuiltinExtensionFunctionalType");
    boolean bool;
    if ((isBuiltinFunctionalType(paramKotlinType)) && (isTypeAnnotatedWithExtensionFunctionType(paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isBuiltinFunctionalType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isBuiltinFunctionalType");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    if (paramKotlinType != null) {
      paramKotlinType = getFunctionalClassKind((DeclarationDescriptor)paramKotlinType);
    } else {
      paramKotlinType = null;
    }
    boolean bool;
    if ((paramKotlinType != FunctionClassDescriptor.Kind.Function) && (paramKotlinType != FunctionClassDescriptor.Kind.SuspendFunction)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final boolean isFunctionType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isFunctionType");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    if (paramKotlinType != null) {
      paramKotlinType = getFunctionalClassKind((DeclarationDescriptor)paramKotlinType);
    } else {
      paramKotlinType = null;
    }
    boolean bool;
    if (paramKotlinType == FunctionClassDescriptor.Kind.Function) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isSuspendFunctionType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isSuspendFunctionType");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    if (paramKotlinType != null) {
      paramKotlinType = getFunctionalClassKind((DeclarationDescriptor)paramKotlinType);
    } else {
      paramKotlinType = null;
    }
    boolean bool;
    if (paramKotlinType == FunctionClassDescriptor.Kind.SuspendFunction) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isTypeAnnotatedWithExtensionFunctionType(KotlinType paramKotlinType)
  {
    paramKotlinType = paramKotlinType.getAnnotations();
    FqName localFqName = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
    boolean bool;
    if (paramKotlinType.findAnnotation(localFqName) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
