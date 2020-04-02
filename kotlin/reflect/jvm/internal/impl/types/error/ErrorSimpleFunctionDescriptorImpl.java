package kotlin.reflect.jvm.internal.impl.types.error;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
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
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils.ErrorScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;

public class ErrorSimpleFunctionDescriptorImpl
  extends SimpleFunctionDescriptorImpl
{
  private final ErrorUtils.ErrorScope ownerScope;
  
  public ErrorSimpleFunctionDescriptorImpl(ClassDescriptor paramClassDescriptor, ErrorUtils.ErrorScope paramErrorScope)
  {
    super(paramClassDescriptor, null, Annotations.Companion.getEMPTY(), Name.special("<ERROR FUNCTION>"), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
    this.ownerScope = paramErrorScope;
  }
  
  public SimpleFunctionDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    return this;
  }
  
  protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(2);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(3);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(4);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(5);
    }
    return this;
  }
  
  public <V> V getUserData(CallableDescriptor.UserDataKey<V> paramUserDataKey)
  {
    return null;
  }
  
  public boolean isSuspend()
  {
    return false;
  }
  
  public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder()
  {
    new FunctionDescriptor.CopyBuilder()
    {
      public SimpleFunctionDescriptor build()
      {
        return ErrorSimpleFunctionDescriptorImpl.this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setAdditionalAnnotations(Annotations paramAnonymousAnnotations)
      {
        if (paramAnonymousAnnotations == null) {
          $$$reportNull$$$0(29);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setCopyOverrides(boolean paramAnonymousBoolean)
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDispatchReceiverParameter(ReceiverParameterDescriptor paramAnonymousReceiverParameterDescriptor)
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDropOriginalInContainingParts()
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setExtensionReceiverParameter(ReceiverParameterDescriptor paramAnonymousReceiverParameterDescriptor)
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenForResolutionEverywhereBesideSupercalls()
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenToOvercomeSignatureClash()
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setKind(CallableMemberDescriptor.Kind paramAnonymousKind)
      {
        if (paramAnonymousKind == null) {
          $$$reportNull$$$0(6);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setModality(Modality paramAnonymousModality)
      {
        if (paramAnonymousModality == null) {
          $$$reportNull$$$0(2);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setName(Name paramAnonymousName)
      {
        if (paramAnonymousName == null) {
          $$$reportNull$$$0(9);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOriginal(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOwner(DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(0);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setPreserveSourceElement()
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setReturnType(KotlinType paramAnonymousKotlinType)
      {
        if (paramAnonymousKotlinType == null) {
          $$$reportNull$$$0(19);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSignatureChange()
      {
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSubstitution(TypeSubstitution paramAnonymousTypeSubstitution)
      {
        if (paramAnonymousTypeSubstitution == null) {
          $$$reportNull$$$0(13);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setTypeParameters(List<TypeParameterDescriptor> paramAnonymousList)
      {
        if (paramAnonymousList == null) {
          $$$reportNull$$$0(17);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setValueParameters(List<ValueParameterDescriptor> paramAnonymousList)
      {
        if (paramAnonymousList == null) {
          $$$reportNull$$$0(11);
        }
        return this;
      }
      
      public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setVisibility(Visibility paramAnonymousVisibility)
      {
        if (paramAnonymousVisibility == null) {
          $$$reportNull$$$0(4);
        }
        return this;
      }
    };
  }
  
  public void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> paramCollection)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(8);
    }
  }
}
