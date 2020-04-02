package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;

public class PropertyDescriptorImpl
  extends VariableDescriptorWithInitializerImpl
  implements PropertyDescriptor
{
  private FieldDescriptor backingField;
  private FieldDescriptor delegateField;
  private ReceiverParameterDescriptor dispatchReceiverParameter;
  private ReceiverParameterDescriptor extensionReceiverParameter;
  private PropertyGetterDescriptorImpl getter;
  private final boolean isActual;
  private final boolean isConst;
  private final boolean isDelegated;
  private final boolean isExpect;
  private final boolean isExternal;
  private final CallableMemberDescriptor.Kind kind;
  private final boolean lateInit;
  private final Modality modality;
  private final PropertyDescriptor original;
  private Collection<? extends PropertyDescriptor> overriddenProperties = null;
  private PropertySetterDescriptor setter;
  private boolean setterProjectedOut;
  private List<TypeParameterDescriptor> typeParameters;
  private Visibility visibility;
  
  protected PropertyDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations, Modality paramModality, Visibility paramVisibility, boolean paramBoolean1, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, null, paramBoolean1, paramSourceElement);
    this.modality = paramModality;
    this.visibility = paramVisibility;
    if (paramPropertyDescriptor == null) {
      paramDeclarationDescriptor = this;
    } else {
      paramDeclarationDescriptor = paramPropertyDescriptor;
    }
    this.original = paramDeclarationDescriptor;
    this.kind = paramKind;
    this.lateInit = paramBoolean2;
    this.isConst = paramBoolean3;
    this.isExpect = paramBoolean4;
    this.isActual = paramBoolean5;
    this.isExternal = paramBoolean6;
    this.isDelegated = paramBoolean7;
  }
  
  public static PropertyDescriptorImpl create(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Modality paramModality, Visibility paramVisibility, boolean paramBoolean1, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(7);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(8);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(9);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(10);
    }
    if (paramName == null) {
      $$$reportNull$$$0(11);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(12);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(13);
    }
    return new PropertyDescriptorImpl(paramDeclarationDescriptor, null, paramAnnotations, paramModality, paramVisibility, paramBoolean1, paramName, paramKind, paramSourceElement, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6, paramBoolean7);
  }
  
  private SourceElement getSourceToUseForCopy(boolean paramBoolean, PropertyDescriptor paramPropertyDescriptor)
  {
    if (paramBoolean)
    {
      if (paramPropertyDescriptor == null) {
        paramPropertyDescriptor = getOriginal();
      }
      paramPropertyDescriptor = paramPropertyDescriptor.getSource();
    }
    else
    {
      paramPropertyDescriptor = SourceElement.NO_SOURCE;
    }
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(23);
    }
    return paramPropertyDescriptor;
  }
  
  private static FunctionDescriptor getSubstitutedInitialSignatureDescriptor(TypeSubstitutor paramTypeSubstitutor, PropertyAccessorDescriptor paramPropertyAccessorDescriptor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(25);
    }
    if (paramPropertyAccessorDescriptor == null) {
      $$$reportNull$$$0(26);
    }
    if (paramPropertyAccessorDescriptor.getInitialSignatureDescriptor() != null) {
      paramTypeSubstitutor = paramPropertyAccessorDescriptor.getInitialSignatureDescriptor().substitute(paramTypeSubstitutor);
    } else {
      paramTypeSubstitutor = null;
    }
    return paramTypeSubstitutor;
  }
  
  private static Visibility normalizeVisibility(Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind)
  {
    Visibility localVisibility = paramVisibility;
    if (paramKind == CallableMemberDescriptor.Kind.FAKE_OVERRIDE)
    {
      localVisibility = paramVisibility;
      if (Visibilities.isPrivate(paramVisibility.normalize())) {
        localVisibility = Visibilities.INVISIBLE_FAKE;
      }
    }
    return localVisibility;
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitPropertyDescriptor(this, paramD);
  }
  
  public PropertyDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    paramDeclarationDescriptor = newCopyBuilder().setOwner(paramDeclarationDescriptor).setOriginal(null).setModality(paramModality).setVisibility(paramVisibility).setKind(paramKind).setCopyOverrides(paramBoolean).build();
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(37);
    }
    return paramDeclarationDescriptor;
  }
  
  protected PropertyDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, PropertyDescriptor paramPropertyDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(27);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(28);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(29);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(30);
    }
    if (paramName == null) {
      $$$reportNull$$$0(31);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(32);
    }
    return new PropertyDescriptorImpl(paramDeclarationDescriptor, paramPropertyDescriptor, getAnnotations(), paramModality, paramVisibility, isVar(), paramName, paramKind, paramSourceElement, isLateInit(), isConst(), isExpect(), isActual(), isExternal(), isDelegated());
  }
  
  protected PropertyDescriptor doSubstitute(CopyConfiguration paramCopyConfiguration)
  {
    if (paramCopyConfiguration == null) {
      $$$reportNull$$$0(24);
    }
    PropertyDescriptorImpl localPropertyDescriptorImpl = createSubstitutedCopy(paramCopyConfiguration.owner, paramCopyConfiguration.modality, paramCopyConfiguration.visibility, paramCopyConfiguration.original, paramCopyConfiguration.kind, paramCopyConfiguration.name, getSourceToUseForCopy(paramCopyConfiguration.preserveSourceElement, paramCopyConfiguration.original));
    if (paramCopyConfiguration.newTypeParameters == null) {
      localObject1 = getTypeParameters();
    } else {
      localObject1 = paramCopyConfiguration.newTypeParameters;
    }
    Object localObject2 = new ArrayList(((List)localObject1).size());
    TypeSubstitutor localTypeSubstitutor = DescriptorSubstitutor.substituteTypeParameters((List)localObject1, paramCopyConfiguration.substitution, localPropertyDescriptorImpl, (List)localObject2);
    Object localObject3 = localTypeSubstitutor.substitute(getType(), Variance.OUT_VARIANCE);
    FieldDescriptorImpl localFieldDescriptorImpl = null;
    if (localObject3 == null) {
      return null;
    }
    Object localObject1 = paramCopyConfiguration.dispatchReceiverParameter;
    if (localObject1 != null)
    {
      localObject4 = ((ReceiverParameterDescriptor)localObject1).substitute(localTypeSubstitutor);
      localObject1 = localObject4;
      if (localObject4 == null) {
        return null;
      }
    }
    else
    {
      localObject1 = null;
    }
    Object localObject4 = this.extensionReceiverParameter;
    if (localObject4 != null)
    {
      localObject4 = localTypeSubstitutor.substitute(((ReceiverParameterDescriptor)localObject4).getType(), Variance.IN_VARIANCE);
      if (localObject4 == null) {
        return null;
      }
      localObject4 = new ReceiverParameterDescriptorImpl(localPropertyDescriptorImpl, new ExtensionReceiver(localPropertyDescriptorImpl, (KotlinType)localObject4, this.extensionReceiverParameter.getValue()), this.extensionReceiverParameter.getAnnotations());
    }
    else
    {
      localObject4 = null;
    }
    localPropertyDescriptorImpl.setType((KotlinType)localObject3, (List)localObject2, (ReceiverParameterDescriptor)localObject1, (ReceiverParameterDescriptor)localObject4);
    if (this.getter == null) {
      localObject1 = null;
    } else {
      localObject1 = new PropertyGetterDescriptorImpl(localPropertyDescriptorImpl, this.getter.getAnnotations(), paramCopyConfiguration.modality, normalizeVisibility(this.getter.getVisibility(), paramCopyConfiguration.kind), this.getter.isDefault(), this.getter.isExternal(), this.getter.isInline(), paramCopyConfiguration.kind, paramCopyConfiguration.getOriginalGetter(), SourceElement.NO_SOURCE);
    }
    if (localObject1 != null)
    {
      localObject4 = this.getter.getReturnType();
      ((PropertyGetterDescriptorImpl)localObject1).setInitialSignatureDescriptor(getSubstitutedInitialSignatureDescriptor(localTypeSubstitutor, this.getter));
      if (localObject4 != null) {
        localObject4 = localTypeSubstitutor.substitute((KotlinType)localObject4, Variance.OUT_VARIANCE);
      } else {
        localObject4 = null;
      }
      ((PropertyGetterDescriptorImpl)localObject1).initialize((KotlinType)localObject4);
    }
    if (this.setter == null) {
      localObject4 = null;
    } else {
      localObject4 = new PropertySetterDescriptorImpl(localPropertyDescriptorImpl, this.setter.getAnnotations(), paramCopyConfiguration.modality, normalizeVisibility(this.setter.getVisibility(), paramCopyConfiguration.kind), this.setter.isDefault(), this.setter.isExternal(), this.setter.isInline(), paramCopyConfiguration.kind, paramCopyConfiguration.getOriginalSetter(), SourceElement.NO_SOURCE);
    }
    if (localObject4 != null)
    {
      localObject3 = FunctionDescriptorImpl.getSubstitutedValueParameters((FunctionDescriptor)localObject4, this.setter.getValueParameters(), localTypeSubstitutor, false, false, null);
      localObject2 = localObject3;
      if (localObject3 == null)
      {
        localPropertyDescriptorImpl.setSetterProjectedOut(true);
        localObject2 = Collections.singletonList(PropertySetterDescriptorImpl.createSetterParameter((PropertySetterDescriptor)localObject4, DescriptorUtilsKt.getBuiltIns(paramCopyConfiguration.owner).getNothingType(), ((ValueParameterDescriptor)this.setter.getValueParameters().get(0)).getAnnotations()));
      }
      if (((List)localObject2).size() == 1)
      {
        ((PropertySetterDescriptorImpl)localObject4).setInitialSignatureDescriptor(getSubstitutedInitialSignatureDescriptor(localTypeSubstitutor, this.setter));
        ((PropertySetterDescriptorImpl)localObject4).initialize((ValueParameterDescriptor)((List)localObject2).get(0));
      }
      else
      {
        throw new IllegalStateException();
      }
    }
    localObject2 = this.backingField;
    if (localObject2 == null) {
      localObject2 = null;
    } else {
      localObject2 = new FieldDescriptorImpl(((FieldDescriptor)localObject2).getAnnotations(), localPropertyDescriptorImpl);
    }
    localObject3 = this.delegateField;
    if (localObject3 != null) {
      localFieldDescriptorImpl = new FieldDescriptorImpl(((FieldDescriptor)localObject3).getAnnotations(), localPropertyDescriptorImpl);
    }
    localPropertyDescriptorImpl.initialize((PropertyGetterDescriptorImpl)localObject1, (PropertySetterDescriptor)localObject4, (FieldDescriptor)localObject2, localFieldDescriptorImpl);
    if (paramCopyConfiguration.copyOverrides)
    {
      paramCopyConfiguration = SmartSet.create();
      localObject1 = getOverriddenDescriptors().iterator();
      while (((Iterator)localObject1).hasNext()) {
        paramCopyConfiguration.add(((PropertyDescriptor)((Iterator)localObject1).next()).substitute(localTypeSubstitutor));
      }
      localPropertyDescriptorImpl.setOverriddenDescriptors(paramCopyConfiguration);
    }
    if ((isConst()) && (this.compileTimeInitializer != null)) {
      localPropertyDescriptorImpl.setCompileTimeInitializer(this.compileTimeInitializer);
    }
    return localPropertyDescriptorImpl;
  }
  
  public List<PropertyAccessorDescriptor> getAccessors()
  {
    ArrayList localArrayList = new ArrayList(2);
    Object localObject = this.getter;
    if (localObject != null) {
      localArrayList.add(localObject);
    }
    localObject = this.setter;
    if (localObject != null) {
      localArrayList.add(localObject);
    }
    return localArrayList;
  }
  
  public FieldDescriptor getBackingField()
  {
    return this.backingField;
  }
  
  public FieldDescriptor getDelegateField()
  {
    return this.delegateField;
  }
  
  public ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    return this.dispatchReceiverParameter;
  }
  
  public ReceiverParameterDescriptor getExtensionReceiverParameter()
  {
    return this.extensionReceiverParameter;
  }
  
  public PropertyGetterDescriptorImpl getGetter()
  {
    return this.getter;
  }
  
  public CallableMemberDescriptor.Kind getKind()
  {
    CallableMemberDescriptor.Kind localKind = this.kind;
    if (localKind == null) {
      $$$reportNull$$$0(34);
    }
    return localKind;
  }
  
  public Modality getModality()
  {
    Modality localModality = this.modality;
    if (localModality == null) {
      $$$reportNull$$$0(19);
    }
    return localModality;
  }
  
  public PropertyDescriptor getOriginal()
  {
    Object localObject = this.original;
    if (localObject == this) {
      localObject = this;
    } else {
      localObject = ((PropertyDescriptor)localObject).getOriginal();
    }
    if (localObject == null) {
      $$$reportNull$$$0(33);
    }
    return localObject;
  }
  
  public Collection<? extends PropertyDescriptor> getOverriddenDescriptors()
  {
    Object localObject = this.overriddenProperties;
    if (localObject == null) {
      localObject = Collections.emptyList();
    }
    if (localObject == null) {
      $$$reportNull$$$0(36);
    }
    return localObject;
  }
  
  public KotlinType getReturnType()
  {
    KotlinType localKotlinType = getType();
    if (localKotlinType == null) {
      $$$reportNull$$$0(18);
    }
    return localKotlinType;
  }
  
  public PropertySetterDescriptor getSetter()
  {
    return this.setter;
  }
  
  public List<TypeParameterDescriptor> getTypeParameters()
  {
    List localList = this.typeParameters;
    if (localList == null) {
      $$$reportNull$$$0(17);
    }
    return localList;
  }
  
  public <V> V getUserData(CallableDescriptor.UserDataKey<V> paramUserDataKey)
  {
    return null;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = this.visibility;
    if (localVisibility == null) {
      $$$reportNull$$$0(20);
    }
    return localVisibility;
  }
  
  public void initialize(PropertyGetterDescriptorImpl paramPropertyGetterDescriptorImpl, PropertySetterDescriptor paramPropertySetterDescriptor)
  {
    initialize(paramPropertyGetterDescriptorImpl, paramPropertySetterDescriptor, null, null);
  }
  
  public void initialize(PropertyGetterDescriptorImpl paramPropertyGetterDescriptorImpl, PropertySetterDescriptor paramPropertySetterDescriptor, FieldDescriptor paramFieldDescriptor1, FieldDescriptor paramFieldDescriptor2)
  {
    this.getter = paramPropertyGetterDescriptorImpl;
    this.setter = paramPropertySetterDescriptor;
    this.backingField = paramFieldDescriptor1;
    this.delegateField = paramFieldDescriptor2;
  }
  
  public boolean isActual()
  {
    return this.isActual;
  }
  
  public boolean isConst()
  {
    return this.isConst;
  }
  
  public boolean isDelegated()
  {
    return this.isDelegated;
  }
  
  public boolean isExpect()
  {
    return this.isExpect;
  }
  
  public boolean isExternal()
  {
    return this.isExternal;
  }
  
  public boolean isLateInit()
  {
    return this.lateInit;
  }
  
  public boolean isSetterProjectedOut()
  {
    return this.setterProjectedOut;
  }
  
  public CopyConfiguration newCopyBuilder()
  {
    return new CopyConfiguration();
  }
  
  public void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> paramCollection)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(35);
    }
    this.overriddenProperties = paramCollection;
  }
  
  public void setSetterProjectedOut(boolean paramBoolean)
  {
    this.setterProjectedOut = paramBoolean;
  }
  
  public void setType(KotlinType paramKotlinType, List<? extends TypeParameterDescriptor> paramList, ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(14);
    }
    if (paramList == null) {
      $$$reportNull$$$0(15);
    }
    setOutType(paramKotlinType);
    this.typeParameters = new ArrayList(paramList);
    this.extensionReceiverParameter = paramReceiverParameterDescriptor2;
    this.dispatchReceiverParameter = paramReceiverParameterDescriptor1;
  }
  
  public void setVisibility(Visibility paramVisibility)
  {
    if (paramVisibility == null) {
      $$$reportNull$$$0(16);
    }
    this.visibility = paramVisibility;
  }
  
  public PropertyDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(22);
    }
    if (paramTypeSubstitutor.isEmpty()) {
      return this;
    }
    return newCopyBuilder().setSubstitution(paramTypeSubstitutor.getSubstitution()).setOriginal(getOriginal()).build();
  }
  
  public class CopyConfiguration
  {
    private boolean copyOverrides = true;
    private ReceiverParameterDescriptor dispatchReceiverParameter = PropertyDescriptorImpl.this.dispatchReceiverParameter;
    private CallableMemberDescriptor.Kind kind = PropertyDescriptorImpl.this.getKind();
    private Modality modality = PropertyDescriptorImpl.this.getModality();
    private Name name = PropertyDescriptorImpl.this.getName();
    private List<TypeParameterDescriptor> newTypeParameters = null;
    private PropertyDescriptor original = null;
    private DeclarationDescriptor owner = PropertyDescriptorImpl.this.getContainingDeclaration();
    private boolean preserveSourceElement = false;
    private TypeSubstitution substitution = TypeSubstitution.EMPTY;
    private Visibility visibility = PropertyDescriptorImpl.this.getVisibility();
    
    public CopyConfiguration() {}
    
    public PropertyDescriptor build()
    {
      return PropertyDescriptorImpl.this.doSubstitute(this);
    }
    
    PropertyGetterDescriptor getOriginalGetter()
    {
      PropertyDescriptor localPropertyDescriptor = this.original;
      if (localPropertyDescriptor == null) {
        return null;
      }
      return localPropertyDescriptor.getGetter();
    }
    
    PropertySetterDescriptor getOriginalSetter()
    {
      PropertyDescriptor localPropertyDescriptor = this.original;
      if (localPropertyDescriptor == null) {
        return null;
      }
      return localPropertyDescriptor.getSetter();
    }
    
    public CopyConfiguration setCopyOverrides(boolean paramBoolean)
    {
      this.copyOverrides = paramBoolean;
      return this;
    }
    
    public CopyConfiguration setKind(CallableMemberDescriptor.Kind paramKind)
    {
      if (paramKind == null) {
        $$$reportNull$$$0(8);
      }
      this.kind = paramKind;
      return this;
    }
    
    public CopyConfiguration setModality(Modality paramModality)
    {
      if (paramModality == null) {
        $$$reportNull$$$0(4);
      }
      this.modality = paramModality;
      return this;
    }
    
    public CopyConfiguration setOriginal(CallableMemberDescriptor paramCallableMemberDescriptor)
    {
      this.original = ((PropertyDescriptor)paramCallableMemberDescriptor);
      return this;
    }
    
    public CopyConfiguration setOwner(DeclarationDescriptor paramDeclarationDescriptor)
    {
      if (paramDeclarationDescriptor == null) {
        $$$reportNull$$$0(0);
      }
      this.owner = paramDeclarationDescriptor;
      return this;
    }
    
    public CopyConfiguration setSubstitution(TypeSubstitution paramTypeSubstitution)
    {
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(13);
      }
      this.substitution = paramTypeSubstitution;
      return this;
    }
    
    public CopyConfiguration setVisibility(Visibility paramVisibility)
    {
      if (paramVisibility == null) {
        $$$reportNull$$$0(6);
      }
      this.visibility = paramVisibility;
      return this;
    }
  }
}
