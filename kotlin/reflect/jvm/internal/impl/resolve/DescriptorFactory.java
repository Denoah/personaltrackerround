package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collections;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ReceiverParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public class DescriptorFactory
{
  public static PropertyGetterDescriptorImpl createDefaultGetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(14);
    }
    return createGetter(paramPropertyDescriptor, paramAnnotations, true, false, false);
  }
  
  public static PropertySetterDescriptorImpl createDefaultSetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations1, Annotations paramAnnotations2)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(0);
    }
    if (paramAnnotations1 == null) {
      $$$reportNull$$$0(1);
    }
    if (paramAnnotations2 == null) {
      $$$reportNull$$$0(2);
    }
    return createSetter(paramPropertyDescriptor, paramAnnotations1, paramAnnotations2, true, false, false, paramPropertyDescriptor.getSource());
  }
  
  public static SimpleFunctionDescriptor createEnumValueOfMethod(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(24);
    }
    SimpleFunctionDescriptorImpl localSimpleFunctionDescriptorImpl = SimpleFunctionDescriptorImpl.create(paramClassDescriptor, Annotations.Companion.getEMPTY(), DescriptorUtils.ENUM_VALUE_OF, CallableMemberDescriptor.Kind.SYNTHESIZED, paramClassDescriptor.getSource());
    ValueParameterDescriptorImpl localValueParameterDescriptorImpl = new ValueParameterDescriptorImpl(localSimpleFunctionDescriptorImpl, null, 0, Annotations.Companion.getEMPTY(), Name.identifier("value"), DescriptorUtilsKt.getBuiltIns(paramClassDescriptor).getStringType(), false, false, false, null, paramClassDescriptor.getSource());
    paramClassDescriptor = localSimpleFunctionDescriptorImpl.initialize(null, null, Collections.emptyList(), Collections.singletonList(localValueParameterDescriptorImpl), paramClassDescriptor.getDefaultType(), Modality.FINAL, Visibilities.PUBLIC);
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(25);
    }
    return paramClassDescriptor;
  }
  
  public static SimpleFunctionDescriptor createEnumValuesMethod(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(22);
    }
    paramClassDescriptor = SimpleFunctionDescriptorImpl.create(paramClassDescriptor, Annotations.Companion.getEMPTY(), DescriptorUtils.ENUM_VALUES, CallableMemberDescriptor.Kind.SYNTHESIZED, paramClassDescriptor.getSource()).initialize(null, null, Collections.emptyList(), Collections.emptyList(), DescriptorUtilsKt.getBuiltIns(paramClassDescriptor).getArrayType(Variance.INVARIANT, paramClassDescriptor.getDefaultType()), Modality.FINAL, Visibilities.PUBLIC);
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(23);
    }
    return paramClassDescriptor;
  }
  
  public static ReceiverParameterDescriptor createExtensionReceiverParameterForCallable(CallableDescriptor paramCallableDescriptor, KotlinType paramKotlinType, Annotations paramAnnotations)
  {
    if (paramCallableDescriptor == null) {
      $$$reportNull$$$0(29);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(30);
    }
    Object localObject = null;
    if (paramKotlinType == null) {
      paramCallableDescriptor = localObject;
    } else {
      paramCallableDescriptor = new ReceiverParameterDescriptorImpl(paramCallableDescriptor, new ExtensionReceiver(paramCallableDescriptor, paramKotlinType, null), paramAnnotations);
    }
    return paramCallableDescriptor;
  }
  
  public static PropertyGetterDescriptorImpl createGetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(15);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(16);
    }
    return createGetter(paramPropertyDescriptor, paramAnnotations, paramBoolean1, paramBoolean2, paramBoolean3, paramPropertyDescriptor.getSource());
  }
  
  public static PropertyGetterDescriptorImpl createGetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, SourceElement paramSourceElement)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(17);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(18);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(19);
    }
    return new PropertyGetterDescriptorImpl(paramPropertyDescriptor, paramAnnotations, paramPropertyDescriptor.getModality(), paramPropertyDescriptor.getVisibility(), paramBoolean1, paramBoolean2, paramBoolean3, CallableMemberDescriptor.Kind.DECLARATION, null, paramSourceElement);
  }
  
  public static ClassConstructorDescriptorImpl createPrimaryConstructorForObject(ClassDescriptor paramClassDescriptor, SourceElement paramSourceElement)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(20);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(21);
    }
    return new DefaultClassConstructorDescriptor(paramClassDescriptor, paramSourceElement);
  }
  
  public static PropertySetterDescriptorImpl createSetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations1, Annotations paramAnnotations2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, SourceElement paramSourceElement)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(3);
    }
    if (paramAnnotations1 == null) {
      $$$reportNull$$$0(4);
    }
    if (paramAnnotations2 == null) {
      $$$reportNull$$$0(5);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(6);
    }
    return createSetter(paramPropertyDescriptor, paramAnnotations1, paramAnnotations2, paramBoolean1, paramBoolean2, paramBoolean3, paramPropertyDescriptor.getVisibility(), paramSourceElement);
  }
  
  public static PropertySetterDescriptorImpl createSetter(PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations1, Annotations paramAnnotations2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Visibility paramVisibility, SourceElement paramSourceElement)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(7);
    }
    if (paramAnnotations1 == null) {
      $$$reportNull$$$0(8);
    }
    if (paramAnnotations2 == null) {
      $$$reportNull$$$0(9);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(10);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(11);
    }
    paramAnnotations1 = new PropertySetterDescriptorImpl(paramPropertyDescriptor, paramAnnotations1, paramPropertyDescriptor.getModality(), paramVisibility, paramBoolean1, paramBoolean2, paramBoolean3, CallableMemberDescriptor.Kind.DECLARATION, null, paramSourceElement);
    paramAnnotations1.initialize(PropertySetterDescriptorImpl.createSetterParameter(paramAnnotations1, paramPropertyDescriptor.getType(), paramAnnotations2));
    return paramAnnotations1;
  }
  
  private static boolean isEnumSpecialMethod(FunctionDescriptor paramFunctionDescriptor)
  {
    if (paramFunctionDescriptor == null) {
      $$$reportNull$$$0(28);
    }
    boolean bool;
    if ((paramFunctionDescriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED) && (DescriptorUtils.isEnumClass(paramFunctionDescriptor.getContainingDeclaration()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isEnumValueOfMethod(FunctionDescriptor paramFunctionDescriptor)
  {
    if (paramFunctionDescriptor == null) {
      $$$reportNull$$$0(27);
    }
    boolean bool;
    if ((paramFunctionDescriptor.getName().equals(DescriptorUtils.ENUM_VALUE_OF)) && (isEnumSpecialMethod(paramFunctionDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isEnumValuesMethod(FunctionDescriptor paramFunctionDescriptor)
  {
    if (paramFunctionDescriptor == null) {
      $$$reportNull$$$0(26);
    }
    boolean bool;
    if ((paramFunctionDescriptor.getName().equals(DescriptorUtils.ENUM_VALUES)) && (isEnumSpecialMethod(paramFunctionDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static class DefaultClassConstructorDescriptor
    extends ClassConstructorDescriptorImpl
  {
    public DefaultClassConstructorDescriptor(ClassDescriptor paramClassDescriptor, SourceElement paramSourceElement)
    {
      super(null, Annotations.Companion.getEMPTY(), true, CallableMemberDescriptor.Kind.DECLARATION, paramSourceElement);
      initialize(Collections.emptyList(), DescriptorUtils.getDefaultConstructorVisibility(paramClassDescriptor));
    }
  }
}
