package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;

public abstract interface ClassDescriptor
  extends ClassOrPackageFragmentDescriptor, ClassifierDescriptorWithTypeParameters
{
  public abstract ClassDescriptor getCompanionObjectDescriptor();
  
  public abstract Collection<ClassConstructorDescriptor> getConstructors();
  
  public abstract DeclarationDescriptor getContainingDeclaration();
  
  public abstract List<TypeParameterDescriptor> getDeclaredTypeParameters();
  
  public abstract SimpleType getDefaultType();
  
  public abstract ClassKind getKind();
  
  public abstract MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution);
  
  public abstract Modality getModality();
  
  public abstract ClassDescriptor getOriginal();
  
  public abstract Collection<ClassDescriptor> getSealedSubclasses();
  
  public abstract MemberScope getStaticScope();
  
  public abstract ReceiverParameterDescriptor getThisAsReceiverParameter();
  
  public abstract MemberScope getUnsubstitutedInnerClassesScope();
  
  public abstract MemberScope getUnsubstitutedMemberScope();
  
  public abstract ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor();
  
  public abstract Visibility getVisibility();
  
  public abstract boolean isCompanionObject();
  
  public abstract boolean isData();
  
  public abstract boolean isInline();
}
