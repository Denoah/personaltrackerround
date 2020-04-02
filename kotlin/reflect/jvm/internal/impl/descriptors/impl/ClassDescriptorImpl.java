package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public class ClassDescriptorImpl
  extends ClassDescriptorBase
{
  private Set<ClassConstructorDescriptor> constructors;
  private final ClassKind kind;
  private final Modality modality;
  private ClassConstructorDescriptor primaryConstructor;
  private final TypeConstructor typeConstructor;
  private MemberScope unsubstitutedMemberScope;
  
  public ClassDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, Name paramName, Modality paramModality, ClassKind paramClassKind, Collection<KotlinType> paramCollection, SourceElement paramSourceElement, boolean paramBoolean, StorageManager paramStorageManager)
  {
    super(paramStorageManager, paramDeclarationDescriptor, paramName, paramSourceElement, paramBoolean);
    this.modality = paramModality;
    this.kind = paramClassKind;
    this.typeConstructor = new ClassTypeConstructorImpl(this, Collections.emptyList(), paramCollection, paramStorageManager);
  }
  
  public Annotations getAnnotations()
  {
    Annotations localAnnotations = Annotations.Companion.getEMPTY();
    if (localAnnotations == null) {
      $$$reportNull$$$0(9);
    }
    return localAnnotations;
  }
  
  public ClassDescriptor getCompanionObjectDescriptor()
  {
    return null;
  }
  
  public Collection<ClassConstructorDescriptor> getConstructors()
  {
    Set localSet = this.constructors;
    if (localSet == null) {
      $$$reportNull$$$0(11);
    }
    return localSet;
  }
  
  public List<TypeParameterDescriptor> getDeclaredTypeParameters()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(18);
    }
    return localList;
  }
  
  public ClassKind getKind()
  {
    ClassKind localClassKind = this.kind;
    if (localClassKind == null) {
      $$$reportNull$$$0(15);
    }
    return localClassKind;
  }
  
  public Modality getModality()
  {
    Modality localModality = this.modality;
    if (localModality == null) {
      $$$reportNull$$$0(16);
    }
    return localModality;
  }
  
  public Collection<ClassDescriptor> getSealedSubclasses()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(19);
    }
    return localList;
  }
  
  public MemberScope getStaticScope()
  {
    MemberScope.Empty localEmpty = MemberScope.Empty.INSTANCE;
    if (localEmpty == null) {
      $$$reportNull$$$0(14);
    }
    return localEmpty;
  }
  
  public TypeConstructor getTypeConstructor()
  {
    TypeConstructor localTypeConstructor = this.typeConstructor;
    if (localTypeConstructor == null) {
      $$$reportNull$$$0(10);
    }
    return localTypeConstructor;
  }
  
  public MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(12);
    }
    paramKotlinTypeRefiner = this.unsubstitutedMemberScope;
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(13);
    }
    return paramKotlinTypeRefiner;
  }
  
  public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor()
  {
    return this.primaryConstructor;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = Visibilities.PUBLIC;
    if (localVisibility == null) {
      $$$reportNull$$$0(17);
    }
    return localVisibility;
  }
  
  public final void initialize(MemberScope paramMemberScope, Set<ClassConstructorDescriptor> paramSet, ClassConstructorDescriptor paramClassConstructorDescriptor)
  {
    if (paramMemberScope == null) {
      $$$reportNull$$$0(7);
    }
    if (paramSet == null) {
      $$$reportNull$$$0(8);
    }
    this.unsubstitutedMemberScope = paramMemberScope;
    this.constructors = paramSet;
    this.primaryConstructor = paramClassConstructorDescriptor;
  }
  
  public boolean isActual()
  {
    return false;
  }
  
  public boolean isCompanionObject()
  {
    return false;
  }
  
  public boolean isData()
  {
    return false;
  }
  
  public boolean isExpect()
  {
    return false;
  }
  
  public boolean isInline()
  {
    return false;
  }
  
  public boolean isInner()
  {
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("class ");
    localStringBuilder.append(getName());
    return localStringBuilder.toString();
  }
}
