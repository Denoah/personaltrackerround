package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public class EnumEntrySyntheticClassDescriptor
  extends ClassDescriptorBase
{
  private final Annotations annotations;
  private final NotNullLazyValue<Set<Name>> enumMemberNames;
  private final MemberScope scope;
  private final TypeConstructor typeConstructor;
  
  private EnumEntrySyntheticClassDescriptor(StorageManager paramStorageManager, ClassDescriptor paramClassDescriptor, KotlinType paramKotlinType, Name paramName, NotNullLazyValue<Set<Name>> paramNotNullLazyValue, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    super(paramStorageManager, paramClassDescriptor, paramName, paramSourceElement, false);
    this.annotations = paramAnnotations;
    this.typeConstructor = new ClassTypeConstructorImpl(this, Collections.emptyList(), Collections.singleton(paramKotlinType), paramStorageManager);
    this.scope = new EnumEntryScope(paramStorageManager);
    this.enumMemberNames = paramNotNullLazyValue;
  }
  
  public static EnumEntrySyntheticClassDescriptor create(StorageManager paramStorageManager, ClassDescriptor paramClassDescriptor, Name paramName, NotNullLazyValue<Set<Name>> paramNotNullLazyValue, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    if (paramStorageManager == null) {
      $$$reportNull$$$0(0);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(1);
    }
    if (paramName == null) {
      $$$reportNull$$$0(2);
    }
    if (paramNotNullLazyValue == null) {
      $$$reportNull$$$0(3);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(4);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(5);
    }
    return new EnumEntrySyntheticClassDescriptor(paramStorageManager, paramClassDescriptor, paramClassDescriptor.getDefaultType(), paramName, paramNotNullLazyValue, paramAnnotations, paramSourceElement);
  }
  
  public Annotations getAnnotations()
  {
    Annotations localAnnotations = this.annotations;
    if (localAnnotations == null) {
      $$$reportNull$$$0(21);
    }
    return localAnnotations;
  }
  
  public ClassDescriptor getCompanionObjectDescriptor()
  {
    return null;
  }
  
  public Collection<ClassConstructorDescriptor> getConstructors()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(16);
    }
    return localList;
  }
  
  public List<TypeParameterDescriptor> getDeclaredTypeParameters()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(22);
    }
    return localList;
  }
  
  public ClassKind getKind()
  {
    ClassKind localClassKind = ClassKind.ENUM_ENTRY;
    if (localClassKind == null) {
      $$$reportNull$$$0(18);
    }
    return localClassKind;
  }
  
  public Modality getModality()
  {
    Modality localModality = Modality.FINAL;
    if (localModality == null) {
      $$$reportNull$$$0(19);
    }
    return localModality;
  }
  
  public Collection<ClassDescriptor> getSealedSubclasses()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(23);
    }
    return localList;
  }
  
  public MemberScope getStaticScope()
  {
    MemberScope.Empty localEmpty = MemberScope.Empty.INSTANCE;
    if (localEmpty == null) {
      $$$reportNull$$$0(15);
    }
    return localEmpty;
  }
  
  public TypeConstructor getTypeConstructor()
  {
    TypeConstructor localTypeConstructor = this.typeConstructor;
    if (localTypeConstructor == null) {
      $$$reportNull$$$0(17);
    }
    return localTypeConstructor;
  }
  
  public MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(13);
    }
    paramKotlinTypeRefiner = this.scope;
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(14);
    }
    return paramKotlinTypeRefiner;
  }
  
  public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor()
  {
    return null;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = Visibilities.PUBLIC;
    if (localVisibility == null) {
      $$$reportNull$$$0(20);
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
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("enum entry ");
    localStringBuilder.append(getName());
    return localStringBuilder.toString();
  }
  
  private class EnumEntryScope
    extends MemberScopeImpl
  {
    private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
    private final MemoizedFunctionToNotNull<Name, Collection<? extends SimpleFunctionDescriptor>> functions;
    private final MemoizedFunctionToNotNull<Name, Collection<? extends PropertyDescriptor>> properties;
    
    public EnumEntryScope(StorageManager paramStorageManager)
    {
      this.functions = paramStorageManager.createMemoizedFunction(new Function1()
      {
        public Collection<? extends SimpleFunctionDescriptor> invoke(Name paramAnonymousName)
        {
          return EnumEntrySyntheticClassDescriptor.EnumEntryScope.this.computeFunctions(paramAnonymousName);
        }
      });
      this.properties = paramStorageManager.createMemoizedFunction(new Function1()
      {
        public Collection<? extends PropertyDescriptor> invoke(Name paramAnonymousName)
        {
          return EnumEntrySyntheticClassDescriptor.EnumEntryScope.this.computeProperties(paramAnonymousName);
        }
      });
      this.allDescriptors = paramStorageManager.createLazyValue(new Function0()
      {
        public Collection<DeclarationDescriptor> invoke()
        {
          return EnumEntrySyntheticClassDescriptor.EnumEntryScope.this.computeAllDeclarations();
        }
      });
    }
    
    private Collection<DeclarationDescriptor> computeAllDeclarations()
    {
      HashSet localHashSet = new HashSet();
      Iterator localIterator = ((Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke()).iterator();
      while (localIterator.hasNext())
      {
        Name localName = (Name)localIterator.next();
        localHashSet.addAll(getContributedFunctions(localName, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
        localHashSet.addAll(getContributedVariables(localName, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
      }
      return localHashSet;
    }
    
    private Collection<? extends SimpleFunctionDescriptor> computeFunctions(Name paramName)
    {
      if (paramName == null) {
        $$$reportNull$$$0(8);
      }
      return resolveFakeOverrides(paramName, getSupertypeScope().getContributedFunctions(paramName, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
    }
    
    private Collection<? extends PropertyDescriptor> computeProperties(Name paramName)
    {
      if (paramName == null) {
        $$$reportNull$$$0(4);
      }
      return resolveFakeOverrides(paramName, getSupertypeScope().getContributedVariables(paramName, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
    }
    
    private MemberScope getSupertypeScope()
    {
      MemberScope localMemberScope = ((KotlinType)EnumEntrySyntheticClassDescriptor.this.getTypeConstructor().getSupertypes().iterator().next()).getMemberScope();
      if (localMemberScope == null) {
        $$$reportNull$$$0(9);
      }
      return localMemberScope;
    }
    
    private <D extends CallableMemberDescriptor> Collection<? extends D> resolveFakeOverrides(Name paramName, Collection<? extends D> paramCollection)
    {
      if (paramName == null) {
        $$$reportNull$$$0(10);
      }
      if (paramCollection == null) {
        $$$reportNull$$$0(11);
      }
      final LinkedHashSet localLinkedHashSet = new LinkedHashSet();
      OverridingUtil.DEFAULT.generateOverridesInFunctionGroup(paramName, paramCollection, Collections.emptySet(), EnumEntrySyntheticClassDescriptor.this, new NonReportingOverrideStrategy()
      {
        public void addFakeOverride(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
        {
          if (paramAnonymousCallableMemberDescriptor == null) {
            $$$reportNull$$$0(0);
          }
          OverridingUtil.resolveUnknownVisibilityForMember(paramAnonymousCallableMemberDescriptor, null);
          localLinkedHashSet.add(paramAnonymousCallableMemberDescriptor);
        }
        
        protected void conflict(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor1, CallableMemberDescriptor paramAnonymousCallableMemberDescriptor2)
        {
          if (paramAnonymousCallableMemberDescriptor1 == null) {
            $$$reportNull$$$0(1);
          }
          if (paramAnonymousCallableMemberDescriptor2 == null) {
            $$$reportNull$$$0(2);
          }
        }
      });
      return localLinkedHashSet;
    }
    
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
    {
      if (paramDescriptorKindFilter == null) {
        $$$reportNull$$$0(13);
      }
      if (paramFunction1 == null) {
        $$$reportNull$$$0(14);
      }
      paramDescriptorKindFilter = (Collection)this.allDescriptors.invoke();
      if (paramDescriptorKindFilter == null) {
        $$$reportNull$$$0(15);
      }
      return paramDescriptorKindFilter;
    }
    
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(5);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(6);
      }
      paramName = (Collection)this.functions.invoke(paramName);
      if (paramName == null) {
        $$$reportNull$$$0(7);
      }
      return paramName;
    }
    
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(1);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(2);
      }
      paramName = (Collection)this.properties.invoke(paramName);
      if (paramName == null) {
        $$$reportNull$$$0(3);
      }
      return paramName;
    }
    
    public Set<Name> getFunctionNames()
    {
      Set localSet = (Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke();
      if (localSet == null) {
        $$$reportNull$$$0(17);
      }
      return localSet;
    }
    
    public Set<Name> getVariableNames()
    {
      Set localSet = (Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke();
      if (localSet == null) {
        $$$reportNull$$$0(19);
      }
      return localSet;
    }
  }
}
