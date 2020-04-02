package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
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

public class MutableClassDescriptor
  extends ClassDescriptorBase
{
  private final boolean isInner;
  private final ClassKind kind;
  private Modality modality;
  private final StorageManager storageManager;
  private final Collection<KotlinType> supertypes = new ArrayList();
  private TypeConstructor typeConstructor;
  private List<TypeParameterDescriptor> typeParameters;
  private Visibility visibility;
  
  public MutableClassDescriptor(DeclarationDescriptor paramDeclarationDescriptor, ClassKind paramClassKind, boolean paramBoolean1, boolean paramBoolean2, Name paramName, SourceElement paramSourceElement, StorageManager paramStorageManager)
  {
    super(paramStorageManager, paramDeclarationDescriptor, paramName, paramSourceElement, paramBoolean2);
    this.storageManager = paramStorageManager;
    this.kind = paramClassKind;
    this.isInner = paramBoolean1;
  }
  
  public void createTypeConstructor()
  {
    this.typeConstructor = new ClassTypeConstructorImpl(this, this.typeParameters, this.supertypes, this.storageManager);
    Iterator localIterator = getConstructors().iterator();
    while (localIterator.hasNext()) {
      ((ClassConstructorDescriptorImpl)localIterator.next()).setReturnType(getDefaultType());
    }
  }
  
  public Annotations getAnnotations()
  {
    Annotations localAnnotations = Annotations.Companion.getEMPTY();
    if (localAnnotations == null) {
      $$$reportNull$$$0(5);
    }
    return localAnnotations;
  }
  
  public ClassDescriptor getCompanionObjectDescriptor()
  {
    return null;
  }
  
  public Set<ClassConstructorDescriptor> getConstructors()
  {
    Set localSet = Collections.emptySet();
    if (localSet == null) {
      $$$reportNull$$$0(13);
    }
    return localSet;
  }
  
  public List<TypeParameterDescriptor> getDeclaredTypeParameters()
  {
    List localList = this.typeParameters;
    if (localList == null) {
      $$$reportNull$$$0(15);
    }
    return localList;
  }
  
  public ClassKind getKind()
  {
    ClassKind localClassKind = this.kind;
    if (localClassKind == null) {
      $$$reportNull$$$0(8);
    }
    return localClassKind;
  }
  
  public Modality getModality()
  {
    Modality localModality = this.modality;
    if (localModality == null) {
      $$$reportNull$$$0(7);
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
      $$$reportNull$$$0(18);
    }
    return localEmpty;
  }
  
  public TypeConstructor getTypeConstructor()
  {
    TypeConstructor localTypeConstructor = this.typeConstructor;
    if (localTypeConstructor == null) {
      $$$reportNull$$$0(11);
    }
    return localTypeConstructor;
  }
  
  public MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(16);
    }
    paramKotlinTypeRefiner = MemberScope.Empty.INSTANCE;
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(17);
    }
    return paramKotlinTypeRefiner;
  }
  
  public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor()
  {
    return null;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = this.visibility;
    if (localVisibility == null) {
      $$$reportNull$$$0(10);
    }
    return localVisibility;
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
    return this.isInner;
  }
  
  public void setModality(Modality paramModality)
  {
    if (paramModality == null) {
      $$$reportNull$$$0(6);
    }
    this.modality = paramModality;
  }
  
  public void setTypeParameterDescriptors(List<TypeParameterDescriptor> paramList)
  {
    if (paramList == null) {
      $$$reportNull$$$0(14);
    }
    if (this.typeParameters == null)
    {
      this.typeParameters = new ArrayList(paramList);
      return;
    }
    paramList = new StringBuilder();
    paramList.append("Type parameters are already set for ");
    paramList.append(getName());
    throw new IllegalStateException(paramList.toString());
  }
  
  public void setVisibility(Visibility paramVisibility)
  {
    if (paramVisibility == null) {
      $$$reportNull$$$0(9);
    }
    this.visibility = paramVisibility;
  }
  
  public String toString()
  {
    return DeclarationDescriptorImpl.toString(this);
  }
}
