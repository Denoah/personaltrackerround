package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor.CopyBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.SubstitutingScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public class LazySubstitutingClassDescriptor
  extends ModuleAwareClassDescriptor
{
  private List<TypeParameterDescriptor> declaredTypeParameters;
  private TypeSubstitutor newSubstitutor;
  private final ModuleAwareClassDescriptor original;
  private final TypeSubstitutor originalSubstitutor;
  private TypeConstructor typeConstructor;
  private List<TypeParameterDescriptor> typeConstructorParameters;
  
  public LazySubstitutingClassDescriptor(ModuleAwareClassDescriptor paramModuleAwareClassDescriptor, TypeSubstitutor paramTypeSubstitutor)
  {
    this.original = paramModuleAwareClassDescriptor;
    this.originalSubstitutor = paramTypeSubstitutor;
  }
  
  private TypeSubstitutor getSubstitutor()
  {
    if (this.newSubstitutor == null) {
      if (this.originalSubstitutor.isEmpty())
      {
        this.newSubstitutor = this.originalSubstitutor;
      }
      else
      {
        List localList = this.original.getTypeConstructor().getParameters();
        this.typeConstructorParameters = new ArrayList(localList.size());
        this.newSubstitutor = DescriptorSubstitutor.substituteTypeParameters(localList, this.originalSubstitutor.getSubstitution(), this, this.typeConstructorParameters);
        this.declaredTypeParameters = CollectionsKt.filter(this.typeConstructorParameters, new Function1()
        {
          public Boolean invoke(TypeParameterDescriptor paramAnonymousTypeParameterDescriptor)
          {
            return Boolean.valueOf(paramAnonymousTypeParameterDescriptor.isCapturedFromOuterDeclaration() ^ true);
          }
        });
      }
    }
    return this.newSubstitutor;
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitClassDescriptor(this, paramD);
  }
  
  public Annotations getAnnotations()
  {
    Annotations localAnnotations = this.original.getAnnotations();
    if (localAnnotations == null) {
      $$$reportNull$$$0(18);
    }
    return localAnnotations;
  }
  
  public ClassDescriptor getCompanionObjectDescriptor()
  {
    return this.original.getCompanionObjectDescriptor();
  }
  
  public Collection<ClassConstructorDescriptor> getConstructors()
  {
    Object localObject = this.original.getConstructors();
    ArrayList localArrayList = new ArrayList(((Collection)localObject).size());
    Iterator localIterator = ((Collection)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = (ClassConstructorDescriptor)localIterator.next();
      localArrayList.add(((ClassConstructorDescriptor)((ClassConstructorDescriptor)localObject).newCopyBuilder().setOriginal(((ClassConstructorDescriptor)localObject).getOriginal()).setModality(((ClassConstructorDescriptor)localObject).getModality()).setVisibility(((ClassConstructorDescriptor)localObject).getVisibility()).setKind(((ClassConstructorDescriptor)localObject).getKind()).setCopyOverrides(false).build()).substitute(getSubstitutor()));
    }
    return localArrayList;
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    DeclarationDescriptor localDeclarationDescriptor = this.original.getContainingDeclaration();
    if (localDeclarationDescriptor == null) {
      $$$reportNull$$$0(21);
    }
    return localDeclarationDescriptor;
  }
  
  public List<TypeParameterDescriptor> getDeclaredTypeParameters()
  {
    getSubstitutor();
    List localList = this.declaredTypeParameters;
    if (localList == null) {
      $$$reportNull$$$0(29);
    }
    return localList;
  }
  
  public SimpleType getDefaultType()
  {
    Object localObject = TypeUtils.getDefaultTypeProjections(getTypeConstructor().getParameters());
    localObject = KotlinTypeFactory.simpleNotNullType(getAnnotations(), this, (List)localObject);
    if (localObject == null) {
      $$$reportNull$$$0(16);
    }
    return localObject;
  }
  
  public ClassKind getKind()
  {
    ClassKind localClassKind = this.original.getKind();
    if (localClassKind == null) {
      $$$reportNull$$$0(24);
    }
    return localClassKind;
  }
  
  public MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution)
  {
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(10);
    }
    paramTypeSubstitution = getMemberScope(paramTypeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(11);
    }
    return paramTypeSubstitution;
  }
  
  public MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(5);
    }
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(6);
    }
    paramTypeSubstitution = this.original.getMemberScope(paramTypeSubstitution, paramKotlinTypeRefiner);
    if (this.originalSubstitutor.isEmpty())
    {
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(7);
      }
      return paramTypeSubstitution;
    }
    return new SubstitutingScope(paramTypeSubstitution, getSubstitutor());
  }
  
  public Modality getModality()
  {
    Modality localModality = this.original.getModality();
    if (localModality == null) {
      $$$reportNull$$$0(25);
    }
    return localModality;
  }
  
  public Name getName()
  {
    Name localName = this.original.getName();
    if (localName == null) {
      $$$reportNull$$$0(19);
    }
    return localName;
  }
  
  public ClassDescriptor getOriginal()
  {
    ClassDescriptor localClassDescriptor = this.original.getOriginal();
    if (localClassDescriptor == null) {
      $$$reportNull$$$0(20);
    }
    return localClassDescriptor;
  }
  
  public Collection<ClassDescriptor> getSealedSubclasses()
  {
    Collection localCollection = this.original.getSealedSubclasses();
    if (localCollection == null) {
      $$$reportNull$$$0(30);
    }
    return localCollection;
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = SourceElement.NO_SOURCE;
    if (localSourceElement == null) {
      $$$reportNull$$$0(28);
    }
    return localSourceElement;
  }
  
  public MemberScope getStaticScope()
  {
    MemberScope localMemberScope = this.original.getStaticScope();
    if (localMemberScope == null) {
      $$$reportNull$$$0(15);
    }
    return localMemberScope;
  }
  
  public ReceiverParameterDescriptor getThisAsReceiverParameter()
  {
    throw new UnsupportedOperationException();
  }
  
  public TypeConstructor getTypeConstructor()
  {
    Object localObject1 = this.original.getTypeConstructor();
    if (this.originalSubstitutor.isEmpty())
    {
      if (localObject1 == null) {
        $$$reportNull$$$0(0);
      }
      return localObject1;
    }
    if (this.typeConstructor == null)
    {
      localObject2 = getSubstitutor();
      Object localObject3 = ((TypeConstructor)localObject1).getSupertypes();
      localObject1 = new ArrayList(((Collection)localObject3).size());
      localObject3 = ((Collection)localObject3).iterator();
      while (((Iterator)localObject3).hasNext()) {
        ((Collection)localObject1).add(((TypeSubstitutor)localObject2).substitute((KotlinType)((Iterator)localObject3).next(), Variance.INVARIANT));
      }
      this.typeConstructor = new ClassTypeConstructorImpl(this, this.typeConstructorParameters, (Collection)localObject1, LockBasedStorageManager.NO_LOCKS);
    }
    Object localObject2 = this.typeConstructor;
    if (localObject2 == null) {
      $$$reportNull$$$0(1);
    }
    return localObject2;
  }
  
  public MemberScope getUnsubstitutedInnerClassesScope()
  {
    MemberScope localMemberScope = this.original.getUnsubstitutedInnerClassesScope();
    if (localMemberScope == null) {
      $$$reportNull$$$0(27);
    }
    return localMemberScope;
  }
  
  public MemberScope getUnsubstitutedMemberScope()
  {
    MemberScope localMemberScope = getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this.original)));
    if (localMemberScope == null) {
      $$$reportNull$$$0(12);
    }
    return localMemberScope;
  }
  
  public MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(13);
    }
    paramKotlinTypeRefiner = this.original.getUnsubstitutedMemberScope(paramKotlinTypeRefiner);
    if (this.originalSubstitutor.isEmpty())
    {
      if (paramKotlinTypeRefiner == null) {
        $$$reportNull$$$0(14);
      }
      return paramKotlinTypeRefiner;
    }
    return new SubstitutingScope(paramKotlinTypeRefiner, getSubstitutor());
  }
  
  public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor()
  {
    return this.original.getUnsubstitutedPrimaryConstructor();
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = this.original.getVisibility();
    if (localVisibility == null) {
      $$$reportNull$$$0(26);
    }
    return localVisibility;
  }
  
  public boolean isActual()
  {
    return this.original.isActual();
  }
  
  public boolean isCompanionObject()
  {
    return this.original.isCompanionObject();
  }
  
  public boolean isData()
  {
    return this.original.isData();
  }
  
  public boolean isExpect()
  {
    return this.original.isExpect();
  }
  
  public boolean isExternal()
  {
    return this.original.isExternal();
  }
  
  public boolean isInline()
  {
    return this.original.isInline();
  }
  
  public boolean isInner()
  {
    return this.original.isInner();
  }
  
  public ClassDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(22);
    }
    if (paramTypeSubstitutor.isEmpty()) {
      return this;
    }
    return new LazySubstitutingClassDescriptor(this, TypeSubstitutor.createChainedSubstitutor(paramTypeSubstitutor.getSubstitution(), getSubstitutor().getSubstitution()));
  }
}
