package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor.CopyBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class SimpleFunctionDescriptorImpl
  extends FunctionDescriptorImpl
  implements SimpleFunctionDescriptor
{
  protected SimpleFunctionDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor, Annotations paramAnnotations, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramSimpleFunctionDescriptor, paramAnnotations, paramName, paramKind, paramSourceElement);
  }
  
  public static SimpleFunctionDescriptorImpl create(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(6);
    }
    if (paramName == null) {
      $$$reportNull$$$0(7);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(8);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(9);
    }
    return new SimpleFunctionDescriptorImpl(paramDeclarationDescriptor, null, paramAnnotations, paramName, paramKind, paramSourceElement);
  }
  
  public SimpleFunctionDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    paramDeclarationDescriptor = (SimpleFunctionDescriptor)super.copy(paramDeclarationDescriptor, paramModality, paramVisibility, paramKind, paramBoolean);
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(23);
    }
    return paramDeclarationDescriptor;
  }
  
  protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(19);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(20);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(21);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(22);
    }
    paramFunctionDescriptor = (SimpleFunctionDescriptor)paramFunctionDescriptor;
    if (paramName == null) {
      paramName = getName();
    }
    return new SimpleFunctionDescriptorImpl(paramDeclarationDescriptor, paramFunctionDescriptor, paramAnnotations, paramName, paramKind, paramSourceElement);
  }
  
  public SimpleFunctionDescriptor getOriginal()
  {
    SimpleFunctionDescriptor localSimpleFunctionDescriptor = (SimpleFunctionDescriptor)super.getOriginal();
    if (localSimpleFunctionDescriptor == null) {
      $$$reportNull$$$0(18);
    }
    return localSimpleFunctionDescriptor;
  }
  
  public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility)
  {
    if (paramList == null) {
      $$$reportNull$$$0(10);
    }
    if (paramList1 == null) {
      $$$reportNull$$$0(11);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(12);
    }
    paramReceiverParameterDescriptor1 = initialize(paramReceiverParameterDescriptor1, paramReceiverParameterDescriptor2, paramList, paramList1, paramKotlinType, paramModality, paramVisibility, null);
    if (paramReceiverParameterDescriptor1 == null) {
      $$$reportNull$$$0(13);
    }
    return paramReceiverParameterDescriptor1;
  }
  
  public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> paramMap)
  {
    if (paramList == null) {
      $$$reportNull$$$0(14);
    }
    if (paramList1 == null) {
      $$$reportNull$$$0(15);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(16);
    }
    super.initialize(paramReceiverParameterDescriptor1, paramReceiverParameterDescriptor2, paramList, paramList1, paramKotlinType, paramModality, paramVisibility);
    if ((paramMap != null) && (!paramMap.isEmpty())) {
      this.userDataMap = new LinkedHashMap(paramMap);
    }
    return this;
  }
  
  public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder()
  {
    FunctionDescriptor.CopyBuilder localCopyBuilder = super.newCopyBuilder();
    if (localCopyBuilder == null) {
      $$$reportNull$$$0(24);
    }
    return localCopyBuilder;
  }
}
