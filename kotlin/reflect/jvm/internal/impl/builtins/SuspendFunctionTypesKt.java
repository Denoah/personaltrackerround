package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.MutableClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class SuspendFunctionTypesKt
{
  private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL;
  private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE;
  
  static
  {
    Object localObject = ErrorUtils.getErrorModule();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "ErrorUtils.getErrorModule()");
    FqName localFqName = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "DescriptorUtils.COROUTIN�KAGE_FQ_NAME_EXPERIMENTAL");
    localObject = new MutableClassDescriptor((DeclarationDescriptor)new EmptyPackageFragmentDescriptor((ModuleDescriptor)localObject, localFqName), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
    ((MutableClassDescriptor)localObject).setModality(Modality.ABSTRACT);
    ((MutableClassDescriptor)localObject).setVisibility(Visibilities.PUBLIC);
    ((MutableClassDescriptor)localObject).setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound((DeclarationDescriptor)localObject, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0)));
    ((MutableClassDescriptor)localObject).createTypeConstructor();
    FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL = (MutableClassDescriptor)localObject;
    localObject = ErrorUtils.getErrorModule();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "ErrorUtils.getErrorModule()");
    localFqName = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE");
    localObject = new MutableClassDescriptor((DeclarationDescriptor)new EmptyPackageFragmentDescriptor((ModuleDescriptor)localObject, localFqName), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
    ((MutableClassDescriptor)localObject).setModality(Modality.ABSTRACT);
    ((MutableClassDescriptor)localObject).setVisibility(Visibilities.PUBLIC);
    ((MutableClassDescriptor)localObject).setTypeParameterDescriptors(CollectionsKt.listOf(TypeParameterDescriptorImpl.createWithDefaultBound((DeclarationDescriptor)localObject, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0)));
    ((MutableClassDescriptor)localObject).createTypeConstructor();
    FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE = (MutableClassDescriptor)localObject;
  }
  
  public static final boolean isContinuation(FqName paramFqName, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramBoolean = Intrinsics.areEqual(paramFqName, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE);
    } else {
      paramBoolean = Intrinsics.areEqual(paramFqName, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL);
    }
    return paramBoolean;
  }
  
  public static final SimpleType transformSuspendFunctionToRuntimeFunctionType(KotlinType paramKotlinType, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "suspendFunType");
    boolean bool = FunctionTypesKt.isSuspendFunctionType(paramKotlinType);
    if ((_Assertions.ENABLED) && (!bool))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("This type should be suspend function type: ");
      ((StringBuilder)localObject1).append(paramKotlinType);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject1).toString()));
    }
    KotlinBuiltIns localKotlinBuiltIns = TypeUtilsKt.getBuiltIns(paramKotlinType);
    Annotations localAnnotations = paramKotlinType.getAnnotations();
    KotlinType localKotlinType = FunctionTypesKt.getReceiverTypeFromFunctionType(paramKotlinType);
    Object localObject2 = (Iterable)FunctionTypesKt.getValueParameterTypesFromFunctionType(paramKotlinType);
    Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Collection)localObject1).add(((TypeProjection)((Iterator)localObject2).next()).getType());
    }
    Collection localCollection = (Collection)localObject1;
    localObject2 = Annotations.Companion.getEMPTY();
    if (paramBoolean) {
      localObject1 = FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE.getTypeConstructor();
    } else {
      localObject1 = FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL.getTypeConstructor();
    }
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "if (isReleaseCoroutines)�ERIMENTAL.typeConstructor");
    localObject2 = CollectionsKt.plus(localCollection, KotlinTypeFactory.simpleType$default((Annotations)localObject2, (TypeConstructor)localObject1, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(FunctionTypesKt.getReturnTypeFromFunctionType(paramKotlinType))), false, null, 16, null));
    localObject1 = TypeUtilsKt.getBuiltIns(paramKotlinType).getNullableAnyType();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "suspendFunType.builtIns.nullableAnyType");
    return FunctionTypesKt.createFunctionType$default(localKotlinBuiltIns, localAnnotations, localKotlinType, (List)localObject2, null, (KotlinType)localObject1, false, 64, null).makeNullableAsSpecified(paramKotlinType.isMarkedNullable());
  }
}
