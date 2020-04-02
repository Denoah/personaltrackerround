package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure;

public final class SpecialBuiltinMembers
{
  private static final FqName child(FqName paramFqName, String paramString)
  {
    paramFqName = paramFqName.child(Name.identifier(paramString));
    Intrinsics.checkExpressionValueIsNotNull(paramFqName, "child(Name.identifier(name))");
    return paramFqName;
  }
  
  private static final FqName childSafe(FqNameUnsafe paramFqNameUnsafe, String paramString)
  {
    paramFqNameUnsafe = paramFqNameUnsafe.child(Name.identifier(paramString)).toSafe();
    Intrinsics.checkExpressionValueIsNotNull(paramFqNameUnsafe, "child(Name.identifier(name)).toSafe()");
    return paramFqNameUnsafe;
  }
  
  public static final boolean doesOverrideBuiltinWithDifferentJvmName(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$doesOverrideBuiltinWithDifferentJvmName");
    boolean bool;
    if (getOverriddenBuiltinWithDifferentJvmName(paramCallableMemberDescriptor) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final String getJvmMethodNameIfSpecial(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "callableMemberDescriptor");
    Object localObject1 = getOverriddenBuiltinThatAffectsJvmName(paramCallableMemberDescriptor);
    Object localObject2 = null;
    paramCallableMemberDescriptor = localObject2;
    if (localObject1 != null)
    {
      localObject1 = DescriptorUtilsKt.getPropertyIfAccessor((CallableMemberDescriptor)localObject1);
      paramCallableMemberDescriptor = localObject2;
      if (localObject1 != null) {
        if ((localObject1 instanceof PropertyDescriptor))
        {
          paramCallableMemberDescriptor = BuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName((CallableMemberDescriptor)localObject1);
        }
        else
        {
          paramCallableMemberDescriptor = localObject2;
          if ((localObject1 instanceof SimpleFunctionDescriptor))
          {
            localObject1 = BuiltinMethodsWithDifferentJvmName.INSTANCE.getJvmName((SimpleFunctionDescriptor)localObject1);
            paramCallableMemberDescriptor = localObject2;
            if (localObject1 != null) {
              paramCallableMemberDescriptor = ((Name)localObject1).asString();
            }
          }
        }
      }
    }
    return paramCallableMemberDescriptor;
  }
  
  private static final CallableMemberDescriptor getOverriddenBuiltinThatAffectsJvmName(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    if (KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramCallableMemberDescriptor)) {
      paramCallableMemberDescriptor = getOverriddenBuiltinWithDifferentJvmName(paramCallableMemberDescriptor);
    } else {
      paramCallableMemberDescriptor = null;
    }
    return paramCallableMemberDescriptor;
  }
  
  public static final <T extends CallableMemberDescriptor> T getOverriddenBuiltinWithDifferentJvmName(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "$this$getOverriddenBuiltinWithDifferentJvmName");
    boolean bool = BuiltinMethodsWithDifferentJvmName.INSTANCE.getORIGINAL_SHORT_NAMES().contains(paramT.getName());
    CallableMemberDescriptor localCallableMemberDescriptor = null;
    if ((!bool) && (!BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES$descriptors_jvm().contains(DescriptorUtilsKt.getPropertyIfAccessor(paramT).getName()))) {
      return null;
    }
    if (((paramT instanceof PropertyDescriptor)) || ((paramT instanceof PropertyAccessorDescriptor))) {
      localCallableMemberDescriptor = DescriptorUtilsKt.firstOverridden$default(paramT, false, (Function1)getOverriddenBuiltinWithDifferentJvmName.1.INSTANCE, 1, null);
    } else if ((paramT instanceof SimpleFunctionDescriptor)) {
      localCallableMemberDescriptor = DescriptorUtilsKt.firstOverridden$default(paramT, false, (Function1)getOverriddenBuiltinWithDifferentJvmName.2.INSTANCE, 1, null);
    }
    return localCallableMemberDescriptor;
  }
  
  public static final <T extends CallableMemberDescriptor> T getOverriddenSpecialBuiltin(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "$this$getOverriddenSpecialBuiltin");
    Object localObject = getOverriddenBuiltinWithDifferentJvmName(paramT);
    if (localObject != null) {
      return localObject;
    }
    localObject = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
    Name localName = paramT.getName();
    Intrinsics.checkExpressionValueIsNotNull(localName, "name");
    if (!((BuiltinMethodsWithSpecialGenericSignature)localObject).getSameAsBuiltinMethodWithErasedValueParameters(localName)) {
      return null;
    }
    return DescriptorUtilsKt.firstOverridden$default(paramT, false, (Function1)getOverriddenSpecialBuiltin.2.INSTANCE, 1, null);
  }
  
  public static final boolean hasRealKotlinSuperClassWithOverrideOf(ClassDescriptor paramClassDescriptor, CallableDescriptor paramCallableDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$hasRealKotlinSuperClassWithOverrideOf");
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor, "specialCallableDescriptor");
    paramCallableDescriptor = paramCallableDescriptor.getContainingDeclaration();
    if (paramCallableDescriptor != null)
    {
      paramCallableDescriptor = ((ClassDescriptor)paramCallableDescriptor).getDefaultType();
      Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor, "(specialCallableDescript…ssDescriptor).defaultType");
      for (paramClassDescriptor = DescriptorUtils.getSuperClassDescriptor(paramClassDescriptor);; paramClassDescriptor = DescriptorUtils.getSuperClassDescriptor(paramClassDescriptor))
      {
        int i = 0;
        if (paramClassDescriptor == null) {
          break;
        }
        if (!(paramClassDescriptor instanceof JavaClassDescriptor))
        {
          if (TypeCheckingProcedure.findCorrespondingSupertype((KotlinType)paramClassDescriptor.getDefaultType(), (KotlinType)paramCallableDescriptor) != null) {
            i = 1;
          }
          if (i != 0) {
            return KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramClassDescriptor) ^ true;
          }
        }
      }
      return false;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
  }
  
  public static final boolean isFromJava(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$isFromJava");
    return DescriptorUtilsKt.getPropertyIfAccessor(paramCallableMemberDescriptor).getContainingDeclaration() instanceof JavaClassDescriptor;
  }
  
  public static final boolean isFromJavaOrBuiltins(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$isFromJavaOrBuiltins");
    boolean bool;
    if ((!isFromJava(paramCallableMemberDescriptor)) && (!KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramCallableMemberDescriptor))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static final NameAndSignature method(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Name localName = Name.identifier(paramString2);
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(name)");
    SignatureBuildingComponents localSignatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString2);
    localStringBuilder.append('(');
    localStringBuilder.append(paramString3);
    localStringBuilder.append(')');
    localStringBuilder.append(paramString4);
    return new NameAndSignature(localName, localSignatureBuildingComponents.signature(paramString1, localStringBuilder.toString()));
  }
}
