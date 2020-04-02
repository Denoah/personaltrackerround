package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.Primitive;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Contract;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Result;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class JavaIncompatibilityRulesOverridabilityCondition
  implements ExternalOverridabilityCondition
{
  public static final Companion Companion = new Companion(null);
  
  public JavaIncompatibilityRulesOverridabilityCondition() {}
  
  private final boolean isIncompatibleInAccordanceWithBuiltInOverridabilityRules(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor)
  {
    if (((paramCallableDescriptor1 instanceof CallableMemberDescriptor)) && ((paramCallableDescriptor2 instanceof FunctionDescriptor)) && (!KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramCallableDescriptor2)))
    {
      Object localObject = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
      FunctionDescriptor localFunctionDescriptor = (FunctionDescriptor)paramCallableDescriptor2;
      paramCallableDescriptor2 = localFunctionDescriptor.getName();
      Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor2, "subDescriptor.name");
      if (!((BuiltinMethodsWithSpecialGenericSignature)localObject).getSameAsBuiltinMethodWithErasedValueParameters(paramCallableDescriptor2))
      {
        paramCallableDescriptor2 = BuiltinMethodsWithDifferentJvmName.INSTANCE;
        localObject = localFunctionDescriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "subDescriptor.name");
        if (!paramCallableDescriptor2.getSameAsRenamedInJvmBuiltin((Name)localObject)) {
          return false;
        }
      }
      localObject = SpecialBuiltinMembers.getOverriddenSpecialBuiltin((CallableMemberDescriptor)paramCallableDescriptor1);
      boolean bool1 = localFunctionDescriptor.isHiddenToOvercomeSignatureClash();
      boolean bool2 = paramCallableDescriptor1 instanceof FunctionDescriptor;
      if (!bool2) {
        paramCallableDescriptor2 = null;
      } else {
        paramCallableDescriptor2 = paramCallableDescriptor1;
      }
      paramCallableDescriptor2 = (FunctionDescriptor)paramCallableDescriptor2;
      int i;
      if ((paramCallableDescriptor2 != null) && (bool1 == paramCallableDescriptor2.isHiddenToOvercomeSignatureClash())) {
        i = 0;
      } else {
        i = 1;
      }
      if ((i != 0) && ((localObject == null) || (!localFunctionDescriptor.isHiddenToOvercomeSignatureClash()))) {
        return true;
      }
      if (((paramClassDescriptor instanceof JavaClassDescriptor)) && (localFunctionDescriptor.getInitialSignatureDescriptor() == null) && (localObject != null) && (!SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(paramClassDescriptor, (CallableDescriptor)localObject)))
      {
        if (((localObject instanceof FunctionDescriptor)) && (bool2) && (BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor)localObject) != null))
        {
          paramCallableDescriptor2 = MethodSignatureMappingKt.computeJvmDescriptor$default(localFunctionDescriptor, false, false, 2, null);
          paramCallableDescriptor1 = ((FunctionDescriptor)paramCallableDescriptor1).getOriginal();
          Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor1, "superDescriptor.original");
          if (Intrinsics.areEqual(paramCallableDescriptor2, MethodSignatureMappingKt.computeJvmDescriptor$default(paramCallableDescriptor1, false, false, 2, null))) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
  
  public ExternalOverridabilityCondition.Contract getContract()
  {
    return ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY;
  }
  
  public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor1, "superDescriptor");
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor2, "subDescriptor");
    if (isIncompatibleInAccordanceWithBuiltInOverridabilityRules(paramCallableDescriptor1, paramCallableDescriptor2, paramClassDescriptor)) {
      return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
    }
    if (Companion.doesJavaOverrideHaveIncompatibleValueParameterKinds(paramCallableDescriptor1, paramCallableDescriptor2)) {
      return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
    }
    return ExternalOverridabilityCondition.Result.UNKNOWN;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final boolean isPrimitiveCompareTo(FunctionDescriptor paramFunctionDescriptor)
    {
      int i = paramFunctionDescriptor.getValueParameters().size();
      boolean bool1 = true;
      if (i != 1) {
        return false;
      }
      Object localObject1 = paramFunctionDescriptor.getContainingDeclaration();
      boolean bool2 = localObject1 instanceof ClassDescriptor;
      Object localObject2 = null;
      if (!bool2) {
        localObject1 = null;
      }
      localObject1 = (ClassDescriptor)localObject1;
      if (localObject1 != null)
      {
        paramFunctionDescriptor = paramFunctionDescriptor.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "f.valueParameters");
        paramFunctionDescriptor = CollectionsKt.single(paramFunctionDescriptor);
        Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "f.valueParameters.single()");
        paramFunctionDescriptor = ((ValueParameterDescriptor)paramFunctionDescriptor).getType().getConstructor().getDeclarationDescriptor();
        if (!(paramFunctionDescriptor instanceof ClassDescriptor)) {
          paramFunctionDescriptor = localObject2;
        }
        paramFunctionDescriptor = (ClassDescriptor)paramFunctionDescriptor;
        if (paramFunctionDescriptor != null)
        {
          if ((!KotlinBuiltIns.isPrimitiveClass((ClassDescriptor)localObject1)) || (!Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)localObject1), DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)paramFunctionDescriptor)))) {
            bool1 = false;
          }
          return bool1;
        }
      }
      return false;
    }
    
    private final JvmType mapValueParameterType(FunctionDescriptor paramFunctionDescriptor, ValueParameterDescriptor paramValueParameterDescriptor)
    {
      if ((!MethodSignatureMappingKt.forceSingleValueParameterBoxing((CallableDescriptor)paramFunctionDescriptor)) && (!((Companion)this).isPrimitiveCompareTo(paramFunctionDescriptor)))
      {
        paramFunctionDescriptor = paramValueParameterDescriptor.getType();
        Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "valueParameterDescriptor.type");
        paramFunctionDescriptor = MethodSignatureMappingKt.mapToJvmType(paramFunctionDescriptor);
      }
      else
      {
        paramFunctionDescriptor = paramValueParameterDescriptor.getType();
        Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "valueParameterDescriptor.type");
        paramFunctionDescriptor = MethodSignatureMappingKt.mapToJvmType(TypeUtilsKt.makeNullable(paramFunctionDescriptor));
      }
      return paramFunctionDescriptor;
    }
    
    public final boolean doesJavaOverrideHaveIncompatibleValueParameterKinds(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2)
    {
      Intrinsics.checkParameterIsNotNull(paramCallableDescriptor1, "superDescriptor");
      Intrinsics.checkParameterIsNotNull(paramCallableDescriptor2, "subDescriptor");
      if (((paramCallableDescriptor2 instanceof JavaMethodDescriptor)) && ((paramCallableDescriptor1 instanceof FunctionDescriptor)))
      {
        Object localObject1 = (JavaMethodDescriptor)paramCallableDescriptor2;
        int i = ((JavaMethodDescriptor)localObject1).getValueParameters().size();
        paramCallableDescriptor1 = (FunctionDescriptor)paramCallableDescriptor1;
        if (i == paramCallableDescriptor1.getValueParameters().size()) {
          i = 1;
        } else {
          i = 0;
        }
        if ((_Assertions.ENABLED) && (i == 0)) {
          throw ((Throwable)new AssertionError("External overridability condition with CONFLICTS_ONLY should not be run with different value parameters size"));
        }
        localObject1 = ((JavaMethodDescriptor)localObject1).getOriginal();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "subDescriptor.original");
        localObject1 = ((SimpleFunctionDescriptor)localObject1).getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "subDescriptor.original.valueParameters");
        localObject1 = (Iterable)localObject1;
        Object localObject2 = paramCallableDescriptor1.getOriginal();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "superDescriptor.original");
        localObject2 = ((FunctionDescriptor)localObject2).getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "superDescriptor.original.valueParameters");
        localObject2 = CollectionsKt.zip((Iterable)localObject1, (Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          Object localObject3 = (Pair)((Iterator)localObject2).next();
          localObject1 = (ValueParameterDescriptor)((Pair)localObject3).component1();
          ValueParameterDescriptor localValueParameterDescriptor = (ValueParameterDescriptor)((Pair)localObject3).component2();
          Companion localCompanion = (Companion)this;
          localObject3 = (FunctionDescriptor)paramCallableDescriptor2;
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "subParameter");
          boolean bool = localCompanion.mapValueParameterType((FunctionDescriptor)localObject3, (ValueParameterDescriptor)localObject1) instanceof JvmType.Primitive;
          Intrinsics.checkExpressionValueIsNotNull(localValueParameterDescriptor, "superParameter");
          if (bool != localCompanion.mapValueParameterType(paramCallableDescriptor1, localValueParameterDescriptor) instanceof JvmType.Primitive) {
            return true;
          }
        }
      }
      return false;
    }
  }
}
