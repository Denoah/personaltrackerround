package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class InlineClassManglingRulesKt
{
  private static final boolean isDontMangleClass(ClassDescriptor paramClassDescriptor)
  {
    return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)paramClassDescriptor), DescriptorUtils.RESULT_FQ_NAME);
  }
  
  public static final boolean isInlineClassThatRequiresMangling(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$isInlineClassThatRequiresMangling");
    boolean bool;
    if ((InlineClassesUtilsKt.isInlineClass(paramDeclarationDescriptor)) && (!isDontMangleClass((ClassDescriptor)paramDeclarationDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isInlineClassThatRequiresMangling(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isInlineClassThatRequiresMangling");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool = true;
    if ((paramKotlinType == null) || (isInlineClassThatRequiresMangling((DeclarationDescriptor)paramKotlinType) != true)) {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isTypeParameterWithUpperBoundThatRequiresMangling(KotlinType paramKotlinType)
  {
    ClassifierDescriptor localClassifierDescriptor = paramKotlinType.getConstructor().getDeclarationDescriptor();
    paramKotlinType = localClassifierDescriptor;
    if (!(localClassifierDescriptor instanceof TypeParameterDescriptor)) {
      paramKotlinType = null;
    }
    paramKotlinType = (TypeParameterDescriptor)paramKotlinType;
    if (paramKotlinType != null) {
      return requiresFunctionNameMangling(TypeUtilsKt.getRepresentativeUpperBound(paramKotlinType));
    }
    return false;
  }
  
  private static final boolean requiresFunctionNameMangling(KotlinType paramKotlinType)
  {
    boolean bool;
    if ((!isInlineClassThatRequiresMangling(paramKotlinType)) && (!isTypeParameterWithUpperBoundThatRequiresMangling(paramKotlinType))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final boolean shouldHideConstructorDueToInlineClassTypeValueParameters(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "descriptor");
    Object localObject = paramCallableMemberDescriptor;
    if (!(paramCallableMemberDescriptor instanceof ClassConstructorDescriptor)) {
      localObject = null;
    }
    paramCallableMemberDescriptor = (ClassConstructorDescriptor)localObject;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramCallableMemberDescriptor != null)
    {
      if (Visibilities.isPrivate(paramCallableMemberDescriptor.getVisibility())) {
        return false;
      }
      localObject = paramCallableMemberDescriptor.getConstructedClass();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "constructorDescriptor.constructedClass");
      if (((ClassDescriptor)localObject).isInline()) {
        return false;
      }
      if (DescriptorUtils.isSealedClass((DeclarationDescriptor)paramCallableMemberDescriptor.getConstructedClass())) {
        return false;
      }
      paramCallableMemberDescriptor = paramCallableMemberDescriptor.getValueParameters();
      Intrinsics.checkExpressionValueIsNotNull(paramCallableMemberDescriptor, "constructorDescriptor.valueParameters");
      paramCallableMemberDescriptor = (Iterable)paramCallableMemberDescriptor;
      if (((paramCallableMemberDescriptor instanceof Collection)) && (((Collection)paramCallableMemberDescriptor).isEmpty()))
      {
        bool2 = bool1;
      }
      else
      {
        paramCallableMemberDescriptor = paramCallableMemberDescriptor.iterator();
        do
        {
          bool2 = bool1;
          if (!paramCallableMemberDescriptor.hasNext()) {
            break;
          }
          localObject = (ValueParameterDescriptor)paramCallableMemberDescriptor.next();
          Intrinsics.checkExpressionValueIsNotNull(localObject, "it");
          localObject = ((ValueParameterDescriptor)localObject).getType();
          Intrinsics.checkExpressionValueIsNotNull(localObject, "it.type");
        } while (!requiresFunctionNameMangling((KotlinType)localObject));
        bool2 = true;
      }
    }
    return bool2;
  }
}
