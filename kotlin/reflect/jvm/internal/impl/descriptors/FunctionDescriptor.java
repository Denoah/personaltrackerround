package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface FunctionDescriptor
  extends CallableMemberDescriptor
{
  public abstract DeclarationDescriptor getContainingDeclaration();
  
  public abstract FunctionDescriptor getInitialSignatureDescriptor();
  
  public abstract FunctionDescriptor getOriginal();
  
  public abstract Collection<? extends FunctionDescriptor> getOverriddenDescriptors();
  
  public abstract boolean isHiddenForResolutionEverywhereBesideSupercalls();
  
  public abstract boolean isHiddenToOvercomeSignatureClash();
  
  public abstract boolean isInfix();
  
  public abstract boolean isInline();
  
  public abstract boolean isOperator();
  
  public abstract boolean isSuspend();
  
  public abstract boolean isTailrec();
  
  public abstract CopyBuilder<? extends FunctionDescriptor> newCopyBuilder();
  
  public abstract FunctionDescriptor substitute(TypeSubstitutor paramTypeSubstitutor);
  
  public static abstract interface CopyBuilder<D extends FunctionDescriptor>
  {
    public abstract D build();
    
    public abstract CopyBuilder<D> setAdditionalAnnotations(Annotations paramAnnotations);
    
    public abstract CopyBuilder<D> setCopyOverrides(boolean paramBoolean);
    
    public abstract CopyBuilder<D> setDispatchReceiverParameter(ReceiverParameterDescriptor paramReceiverParameterDescriptor);
    
    public abstract CopyBuilder<D> setDropOriginalInContainingParts();
    
    public abstract CopyBuilder<D> setExtensionReceiverParameter(ReceiverParameterDescriptor paramReceiverParameterDescriptor);
    
    public abstract CopyBuilder<D> setHiddenForResolutionEverywhereBesideSupercalls();
    
    public abstract CopyBuilder<D> setHiddenToOvercomeSignatureClash();
    
    public abstract CopyBuilder<D> setKind(CallableMemberDescriptor.Kind paramKind);
    
    public abstract CopyBuilder<D> setModality(Modality paramModality);
    
    public abstract CopyBuilder<D> setName(Name paramName);
    
    public abstract CopyBuilder<D> setOriginal(CallableMemberDescriptor paramCallableMemberDescriptor);
    
    public abstract CopyBuilder<D> setOwner(DeclarationDescriptor paramDeclarationDescriptor);
    
    public abstract CopyBuilder<D> setPreserveSourceElement();
    
    public abstract CopyBuilder<D> setReturnType(KotlinType paramKotlinType);
    
    public abstract CopyBuilder<D> setSignatureChange();
    
    public abstract CopyBuilder<D> setSubstitution(TypeSubstitution paramTypeSubstitution);
    
    public abstract CopyBuilder<D> setTypeParameters(List<TypeParameterDescriptor> paramList);
    
    public abstract CopyBuilder<D> setValueParameters(List<ValueParameterDescriptor> paramList);
    
    public abstract CopyBuilder<D> setVisibility(Visibility paramVisibility);
  }
}
