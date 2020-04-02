package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.Pair;
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
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.CheckResult;
import kotlin.reflect.jvm.internal.impl.util.OperatorChecks;

public class JavaMethodDescriptor
  extends SimpleFunctionDescriptorImpl
  implements JavaCallableMemberDescriptor
{
  public static final CallableDescriptor.UserDataKey<ValueParameterDescriptor> ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER = new CallableDescriptor.UserDataKey() {};
  private ParameterNamesStatus parameterNamesStatus = null;
  
  protected JavaMethodDescriptor(DeclarationDescriptor paramDeclarationDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor, Annotations paramAnnotations, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramSimpleFunctionDescriptor, paramAnnotations, paramName, paramKind, paramSourceElement);
  }
  
  public static JavaMethodDescriptor createJavaMethod(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, SourceElement paramSourceElement)
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
    if (paramSourceElement == null) {
      $$$reportNull$$$0(8);
    }
    return new JavaMethodDescriptor(paramDeclarationDescriptor, null, paramAnnotations, paramName, CallableMemberDescriptor.Kind.DECLARATION, paramSourceElement);
  }
  
  protected JavaMethodDescriptor createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(14);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(15);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(16);
    }
    paramFunctionDescriptor = (SimpleFunctionDescriptor)paramFunctionDescriptor;
    if (paramName == null) {
      paramName = getName();
    }
    paramDeclarationDescriptor = new JavaMethodDescriptor(paramDeclarationDescriptor, paramFunctionDescriptor, paramAnnotations, paramName, paramKind, paramSourceElement);
    paramDeclarationDescriptor.setParameterNamesStatus(hasStableParameterNames(), hasSynthesizedParameterNames());
    return paramDeclarationDescriptor;
  }
  
  public JavaMethodDescriptor enhance(KotlinType paramKotlinType1, List<ValueParameterData> paramList, KotlinType paramKotlinType2, Pair<CallableDescriptor.UserDataKey<?>, ?> paramPair)
  {
    if (paramList == null) {
      $$$reportNull$$$0(18);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(19);
    }
    paramList = UtilKt.copyValueParameters(paramList, getValueParameters(), this);
    if (paramKotlinType1 == null) {
      paramKotlinType1 = null;
    } else {
      paramKotlinType1 = DescriptorFactory.createExtensionReceiverParameterForCallable(this, paramKotlinType1, Annotations.Companion.getEMPTY());
    }
    paramKotlinType1 = (JavaMethodDescriptor)newCopyBuilder().setValueParameters(paramList).setReturnType(paramKotlinType2).setExtensionReceiverParameter(paramKotlinType1).setDropOriginalInContainingParts().setPreserveSourceElement().build();
    if (paramPair != null) {
      paramKotlinType1.putInUserDataMap((CallableDescriptor.UserDataKey)paramPair.getFirst(), paramPair.getSecond());
    }
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(20);
    }
    return paramKotlinType1;
  }
  
  public boolean hasStableParameterNames()
  {
    return this.parameterNamesStatus.isStable;
  }
  
  public boolean hasSynthesizedParameterNames()
  {
    return this.parameterNamesStatus.isSynthesized;
  }
  
  public SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> paramMap)
  {
    if (paramList == null) {
      $$$reportNull$$$0(9);
    }
    if (paramList1 == null) {
      $$$reportNull$$$0(10);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(11);
    }
    paramReceiverParameterDescriptor1 = super.initialize(paramReceiverParameterDescriptor1, paramReceiverParameterDescriptor2, paramList, paramList1, paramKotlinType, paramModality, paramVisibility, paramMap);
    setOperator(OperatorChecks.INSTANCE.check(paramReceiverParameterDescriptor1).isSuccess());
    if (paramReceiverParameterDescriptor1 == null) {
      $$$reportNull$$$0(12);
    }
    return paramReceiverParameterDescriptor1;
  }
  
  public void setParameterNamesStatus(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.parameterNamesStatus = ParameterNamesStatus.get(paramBoolean1, paramBoolean2);
  }
  
  private static enum ParameterNamesStatus
  {
    public final boolean isStable;
    public final boolean isSynthesized;
    
    static
    {
      NON_STABLE_SYNTHESIZED = new ParameterNamesStatus("NON_STABLE_SYNTHESIZED", 2, false, true);
      ParameterNamesStatus localParameterNamesStatus = new ParameterNamesStatus("STABLE_SYNTHESIZED", 3, true, true);
      STABLE_SYNTHESIZED = localParameterNamesStatus;
      $VALUES = new ParameterNamesStatus[] { NON_STABLE_DECLARED, STABLE_DECLARED, NON_STABLE_SYNTHESIZED, localParameterNamesStatus };
    }
    
    private ParameterNamesStatus(boolean paramBoolean1, boolean paramBoolean2)
    {
      this.isStable = paramBoolean1;
      this.isSynthesized = paramBoolean2;
    }
    
    public static ParameterNamesStatus get(boolean paramBoolean1, boolean paramBoolean2)
    {
      ParameterNamesStatus localParameterNamesStatus;
      if (paramBoolean1)
      {
        if (paramBoolean2) {
          localParameterNamesStatus = STABLE_SYNTHESIZED;
        } else {
          localParameterNamesStatus = STABLE_DECLARED;
        }
      }
      else if (paramBoolean2) {
        localParameterNamesStatus = NON_STABLE_SYNTHESIZED;
      } else {
        localParameterNamesStatus = NON_STABLE_DECLARED;
      }
      if (localParameterNamesStatus == null) {
        $$$reportNull$$$0(0);
      }
      return localParameterNamesStatus;
    }
  }
}
