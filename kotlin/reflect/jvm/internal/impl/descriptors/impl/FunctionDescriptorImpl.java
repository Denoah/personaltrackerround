package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor.CopyBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;

public abstract class FunctionDescriptorImpl
  extends DeclarationDescriptorNonRootImpl
  implements FunctionDescriptor
{
  private ReceiverParameterDescriptor dispatchReceiverParameter;
  private ReceiverParameterDescriptor extensionReceiverParameter;
  private boolean hasStableParameterNames = true;
  private boolean hasSynthesizedParameterNames = false;
  private FunctionDescriptor initialSignatureDescriptor = null;
  private boolean isActual = false;
  private boolean isExpect = false;
  private boolean isExternal = false;
  private boolean isHiddenForResolutionEverywhereBesideSupercalls = false;
  private boolean isHiddenToOvercomeSignatureClash = false;
  private boolean isInfix = false;
  private boolean isInline = false;
  private boolean isOperator = false;
  private boolean isSuspend = false;
  private boolean isTailrec = false;
  private final CallableMemberDescriptor.Kind kind;
  private volatile Function0<Collection<FunctionDescriptor>> lazyOverriddenFunctionsTask = null;
  private Modality modality;
  private final FunctionDescriptor original;
  private Collection<? extends FunctionDescriptor> overriddenFunctions = null;
  private List<TypeParameterDescriptor> typeParameters;
  private KotlinType unsubstitutedReturnType;
  private List<ValueParameterDescriptor> unsubstitutedValueParameters;
  protected Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap = null;
  private Visibility visibility = Visibilities.UNKNOWN;
  
  protected FunctionDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, Annotations paramAnnotations, Name paramName, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, paramSourceElement);
    paramDeclarationDescriptor = paramFunctionDescriptor;
    if (paramFunctionDescriptor == null) {
      paramDeclarationDescriptor = this;
    }
    this.original = paramDeclarationDescriptor;
    this.kind = paramKind;
  }
  
  private SourceElement getSourceToUseForCopy(boolean paramBoolean, FunctionDescriptor paramFunctionDescriptor)
  {
    if (paramBoolean)
    {
      if (paramFunctionDescriptor == null) {
        paramFunctionDescriptor = getOriginal();
      }
      paramFunctionDescriptor = paramFunctionDescriptor.getSource();
    }
    else
    {
      paramFunctionDescriptor = SourceElement.NO_SOURCE;
    }
    if (paramFunctionDescriptor == null) {
      $$$reportNull$$$0(25);
    }
    return paramFunctionDescriptor;
  }
  
  public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor paramFunctionDescriptor, List<ValueParameterDescriptor> paramList, TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramList == null) {
      $$$reportNull$$$0(26);
    }
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(27);
    }
    return getSubstitutedValueParameters(paramFunctionDescriptor, paramList, paramTypeSubstitutor, false, false, null);
  }
  
  public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor paramFunctionDescriptor, List<ValueParameterDescriptor> paramList, TypeSubstitutor paramTypeSubstitutor, boolean paramBoolean1, boolean paramBoolean2, boolean[] paramArrayOfBoolean)
  {
    if (paramList == null) {
      $$$reportNull$$$0(28);
    }
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(29);
    }
    ArrayList localArrayList = new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (ValueParameterDescriptor)localIterator.next();
      KotlinType localKotlinType = paramTypeSubstitutor.substitute(((ValueParameterDescriptor)localObject1).getType(), Variance.IN_VARIANCE);
      Object localObject2 = ((ValueParameterDescriptor)localObject1).getVarargElementType();
      if (localObject2 == null) {
        paramList = null;
      } else {
        paramList = paramTypeSubstitutor.substitute((KotlinType)localObject2, Variance.IN_VARIANCE);
      }
      if (localKotlinType == null) {
        return null;
      }
      if (((localKotlinType != ((ValueParameterDescriptor)localObject1).getType()) || (localObject2 != paramList)) && (paramArrayOfBoolean != null)) {
        paramArrayOfBoolean[0] = true;
      }
      if ((localObject1 instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration)) {
        localObject2 = new Function0()
        {
          public List<VariableDescriptor> invoke()
          {
            return this.val$destructuringVariables;
          }
        };
      } else {
        localObject2 = null;
      }
      Object localObject3;
      if (paramBoolean1) {
        localObject3 = null;
      } else {
        localObject3 = localObject1;
      }
      int i = ((ValueParameterDescriptor)localObject1).getIndex();
      Annotations localAnnotations = ((ValueParameterDescriptor)localObject1).getAnnotations();
      Name localName = ((ValueParameterDescriptor)localObject1).getName();
      boolean bool1 = ((ValueParameterDescriptor)localObject1).declaresDefaultValue();
      boolean bool2 = ((ValueParameterDescriptor)localObject1).isCrossinline();
      boolean bool3 = ((ValueParameterDescriptor)localObject1).isNoinline();
      if (paramBoolean2) {
        localObject1 = ((ValueParameterDescriptor)localObject1).getSource();
      } else {
        localObject1 = SourceElement.NO_SOURCE;
      }
      localArrayList.add(ValueParameterDescriptorImpl.createWithDestructuringDeclarations(paramFunctionDescriptor, localObject3, i, localAnnotations, localName, localKotlinType, bool1, bool2, bool3, paramList, (SourceElement)localObject1, (Function0)localObject2));
    }
    return localArrayList;
  }
  
  private void performOverriddenLazyCalculationIfNeeded()
  {
    Function0 localFunction0 = this.lazyOverriddenFunctionsTask;
    if (localFunction0 != null)
    {
      this.overriddenFunctions = ((Collection)localFunction0.invoke());
      this.lazyOverriddenFunctionsTask = null;
    }
  }
  
  private void setHiddenForResolutionEverywhereBesideSupercalls(boolean paramBoolean)
  {
    this.isHiddenForResolutionEverywhereBesideSupercalls = paramBoolean;
  }
  
  private void setHiddenToOvercomeSignatureClash(boolean paramBoolean)
  {
    this.isHiddenToOvercomeSignatureClash = paramBoolean;
  }
  
  private void setInitialSignatureDescriptor(FunctionDescriptor paramFunctionDescriptor)
  {
    this.initialSignatureDescriptor = paramFunctionDescriptor;
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitFunctionDescriptor(this, paramD);
  }
  
  public FunctionDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    paramDeclarationDescriptor = newCopyBuilder().setOwner(paramDeclarationDescriptor).setModality(paramModality).setVisibility(paramVisibility).setKind(paramKind).setCopyOverrides(paramBoolean).build();
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(24);
    }
    return paramDeclarationDescriptor;
  }
  
  protected abstract FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement);
  
  protected FunctionDescriptor doSubstitute(CopyConfiguration paramCopyConfiguration)
  {
    if (paramCopyConfiguration == null) {
      $$$reportNull$$$0(23);
    }
    Object localObject1 = new boolean[1];
    Object localObject2;
    if (paramCopyConfiguration.additionalAnnotations != null) {
      localObject2 = AnnotationsKt.composeAnnotations(getAnnotations(), paramCopyConfiguration.additionalAnnotations);
    } else {
      localObject2 = getAnnotations();
    }
    FunctionDescriptorImpl localFunctionDescriptorImpl = createSubstitutedCopy(paramCopyConfiguration.newOwner, paramCopyConfiguration.original, paramCopyConfiguration.kind, paramCopyConfiguration.name, (Annotations)localObject2, getSourceToUseForCopy(paramCopyConfiguration.preserveSourceElement, paramCopyConfiguration.original));
    if (paramCopyConfiguration.newTypeParameters == null) {
      localObject2 = getTypeParameters();
    } else {
      localObject2 = paramCopyConfiguration.newTypeParameters;
    }
    localObject1[0] |= ((List)localObject2).isEmpty() ^ true;
    ArrayList localArrayList = new ArrayList(((List)localObject2).size());
    final TypeSubstitutor localTypeSubstitutor = DescriptorSubstitutor.substituteTypeParameters((List)localObject2, paramCopyConfiguration.substitution, localFunctionDescriptorImpl, localArrayList, (boolean[])localObject1);
    if (localTypeSubstitutor == null) {
      return null;
    }
    Object localObject3;
    int j;
    if (paramCopyConfiguration.newExtensionReceiverParameter != null)
    {
      localObject3 = localTypeSubstitutor.substitute(paramCopyConfiguration.newExtensionReceiverParameter.getType(), Variance.IN_VARIANCE);
      if (localObject3 == null) {
        return null;
      }
      localObject2 = new ReceiverParameterDescriptorImpl(localFunctionDescriptorImpl, new ExtensionReceiver(localFunctionDescriptorImpl, (KotlinType)localObject3, paramCopyConfiguration.newExtensionReceiverParameter.getValue()), paramCopyConfiguration.newExtensionReceiverParameter.getAnnotations());
      i = localObject1[0];
      if (localObject3 != paramCopyConfiguration.newExtensionReceiverParameter.getType()) {
        j = 1;
      } else {
        j = 0;
      }
      localObject1[0] = (j | i);
    }
    else
    {
      localObject2 = null;
    }
    if (paramCopyConfiguration.dispatchReceiverParameter != null)
    {
      localObject3 = paramCopyConfiguration.dispatchReceiverParameter.substitute(localTypeSubstitutor);
      if (localObject3 == null) {
        return null;
      }
      i = localObject1[0];
      if (localObject3 != paramCopyConfiguration.dispatchReceiverParameter) {
        j = 1;
      } else {
        j = 0;
      }
      localObject1[0] = (i | j);
    }
    else
    {
      localObject3 = null;
    }
    List localList = getSubstitutedValueParameters(localFunctionDescriptorImpl, paramCopyConfiguration.newValueParameterDescriptors, localTypeSubstitutor, paramCopyConfiguration.dropOriginalInContainingParts, paramCopyConfiguration.preserveSourceElement, (boolean[])localObject1);
    if (localList == null) {
      return null;
    }
    KotlinType localKotlinType = localTypeSubstitutor.substitute(paramCopyConfiguration.newReturnType, Variance.OUT_VARIANCE);
    if (localKotlinType == null) {
      return null;
    }
    int i = localObject1[0];
    if (localKotlinType != paramCopyConfiguration.newReturnType) {
      j = 1;
    } else {
      j = 0;
    }
    localObject1[0] = (i | j);
    if ((localObject1[0] == 0) && (paramCopyConfiguration.justForTypeSubstitution)) {
      return this;
    }
    localFunctionDescriptorImpl.initialize((ReceiverParameterDescriptor)localObject2, (ReceiverParameterDescriptor)localObject3, localArrayList, localList, localKotlinType, paramCopyConfiguration.newModality, paramCopyConfiguration.newVisibility);
    localFunctionDescriptorImpl.setOperator(this.isOperator);
    localFunctionDescriptorImpl.setInfix(this.isInfix);
    localFunctionDescriptorImpl.setExternal(this.isExternal);
    localFunctionDescriptorImpl.setInline(this.isInline);
    localFunctionDescriptorImpl.setTailrec(this.isTailrec);
    localFunctionDescriptorImpl.setSuspend(this.isSuspend);
    localFunctionDescriptorImpl.setExpect(this.isExpect);
    localFunctionDescriptorImpl.setActual(this.isActual);
    localFunctionDescriptorImpl.setHasStableParameterNames(this.hasStableParameterNames);
    localFunctionDescriptorImpl.setHiddenToOvercomeSignatureClash(paramCopyConfiguration.isHiddenToOvercomeSignatureClash);
    localFunctionDescriptorImpl.setHiddenForResolutionEverywhereBesideSupercalls(paramCopyConfiguration.isHiddenForResolutionEverywhereBesideSupercalls);
    boolean bool;
    if (paramCopyConfiguration.newHasSynthesizedParameterNames != null) {
      bool = paramCopyConfiguration.newHasSynthesizedParameterNames.booleanValue();
    } else {
      bool = this.hasSynthesizedParameterNames;
    }
    localFunctionDescriptorImpl.setHasSynthesizedParameterNames(bool);
    if ((!paramCopyConfiguration.userDataMap.isEmpty()) || (this.userDataMap != null))
    {
      localObject2 = paramCopyConfiguration.userDataMap;
      localObject3 = this.userDataMap;
      if (localObject3 != null)
      {
        localObject1 = ((Map)localObject3).entrySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject3 = (Map.Entry)((Iterator)localObject1).next();
          if (!((Map)localObject2).containsKey(((Map.Entry)localObject3).getKey())) {
            ((Map)localObject2).put(((Map.Entry)localObject3).getKey(), ((Map.Entry)localObject3).getValue());
          }
        }
      }
      if (((Map)localObject2).size() == 1) {
        localFunctionDescriptorImpl.userDataMap = Collections.singletonMap(((Map)localObject2).keySet().iterator().next(), ((Map)localObject2).values().iterator().next());
      } else {
        localFunctionDescriptorImpl.userDataMap = ((Map)localObject2);
      }
    }
    if ((paramCopyConfiguration.signatureChange) || (getInitialSignatureDescriptor() != null))
    {
      if (getInitialSignatureDescriptor() != null) {
        localObject2 = getInitialSignatureDescriptor();
      } else {
        localObject2 = this;
      }
      localFunctionDescriptorImpl.setInitialSignatureDescriptor(((FunctionDescriptor)localObject2).substitute(localTypeSubstitutor));
    }
    if ((paramCopyConfiguration.copyOverrides) && (!getOriginal().getOverriddenDescriptors().isEmpty())) {
      if (paramCopyConfiguration.substitution.isEmpty())
      {
        paramCopyConfiguration = this.lazyOverriddenFunctionsTask;
        if (paramCopyConfiguration != null) {
          localFunctionDescriptorImpl.lazyOverriddenFunctionsTask = paramCopyConfiguration;
        } else {
          localFunctionDescriptorImpl.setOverriddenDescriptors(getOverriddenDescriptors());
        }
      }
      else
      {
        localFunctionDescriptorImpl.lazyOverriddenFunctionsTask = new Function0()
        {
          public Collection<FunctionDescriptor> invoke()
          {
            SmartList localSmartList = new SmartList();
            Iterator localIterator = FunctionDescriptorImpl.this.getOverriddenDescriptors().iterator();
            while (localIterator.hasNext()) {
              localSmartList.add(((FunctionDescriptor)localIterator.next()).substitute(localTypeSubstitutor));
            }
            return localSmartList;
          }
        };
      }
    }
    return localFunctionDescriptorImpl;
  }
  
  public ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    return this.dispatchReceiverParameter;
  }
  
  public ReceiverParameterDescriptor getExtensionReceiverParameter()
  {
    return this.extensionReceiverParameter;
  }
  
  public FunctionDescriptor getInitialSignatureDescriptor()
  {
    return this.initialSignatureDescriptor;
  }
  
  public CallableMemberDescriptor.Kind getKind()
  {
    CallableMemberDescriptor.Kind localKind = this.kind;
    if (localKind == null) {
      $$$reportNull$$$0(19);
    }
    return localKind;
  }
  
  public Modality getModality()
  {
    Modality localModality = this.modality;
    if (localModality == null) {
      $$$reportNull$$$0(13);
    }
    return localModality;
  }
  
  public FunctionDescriptor getOriginal()
  {
    Object localObject = this.original;
    if (localObject == this) {
      localObject = this;
    } else {
      localObject = ((FunctionDescriptor)localObject).getOriginal();
    }
    if (localObject == null) {
      $$$reportNull$$$0(18);
    }
    return localObject;
  }
  
  public Collection<? extends FunctionDescriptor> getOverriddenDescriptors()
  {
    performOverriddenLazyCalculationIfNeeded();
    Object localObject = this.overriddenFunctions;
    if (localObject == null) {
      localObject = Collections.emptyList();
    }
    if (localObject == null) {
      $$$reportNull$$$0(12);
    }
    return localObject;
  }
  
  public KotlinType getReturnType()
  {
    return this.unsubstitutedReturnType;
  }
  
  public List<TypeParameterDescriptor> getTypeParameters()
  {
    List localList = this.typeParameters;
    if (localList == null) {
      $$$reportNull$$$0(16);
    }
    return localList;
  }
  
  public <V> V getUserData(CallableDescriptor.UserDataKey<V> paramUserDataKey)
  {
    Map localMap = this.userDataMap;
    if (localMap == null) {
      return null;
    }
    return localMap.get(paramUserDataKey);
  }
  
  public List<ValueParameterDescriptor> getValueParameters()
  {
    List localList = this.unsubstitutedValueParameters;
    if (localList == null) {
      $$$reportNull$$$0(17);
    }
    return localList;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = this.visibility;
    if (localVisibility == null) {
      $$$reportNull$$$0(14);
    }
    return localVisibility;
  }
  
  public boolean hasStableParameterNames()
  {
    return this.hasStableParameterNames;
  }
  
  public boolean hasSynthesizedParameterNames()
  {
    return this.hasSynthesizedParameterNames;
  }
  
  public FunctionDescriptorImpl initialize(ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility)
  {
    if (paramList == null) {
      $$$reportNull$$$0(5);
    }
    if (paramList1 == null) {
      $$$reportNull$$$0(6);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(7);
    }
    this.typeParameters = CollectionsKt.toList(paramList);
    this.unsubstitutedValueParameters = CollectionsKt.toList(paramList1);
    this.unsubstitutedReturnType = paramKotlinType;
    this.modality = paramModality;
    this.visibility = paramVisibility;
    this.extensionReceiverParameter = paramReceiverParameterDescriptor1;
    this.dispatchReceiverParameter = paramReceiverParameterDescriptor2;
    int i = 0;
    int k;
    for (int j = 0;; j++)
    {
      k = i;
      if (j >= paramList.size()) {
        break label183;
      }
      paramReceiverParameterDescriptor1 = (TypeParameterDescriptor)paramList.get(j);
      if (paramReceiverParameterDescriptor1.getIndex() != j) {
        break;
      }
    }
    paramReceiverParameterDescriptor2 = new StringBuilder();
    paramReceiverParameterDescriptor2.append(paramReceiverParameterDescriptor1);
    paramReceiverParameterDescriptor2.append(" index is ");
    paramReceiverParameterDescriptor2.append(paramReceiverParameterDescriptor1.getIndex());
    paramReceiverParameterDescriptor2.append(" but position is ");
    paramReceiverParameterDescriptor2.append(j);
    throw new IllegalStateException(paramReceiverParameterDescriptor2.toString());
    label183:
    while (k < paramList1.size())
    {
      paramReceiverParameterDescriptor1 = (ValueParameterDescriptor)paramList1.get(k);
      if (paramReceiverParameterDescriptor1.getIndex() == k + 0)
      {
        k++;
      }
      else
      {
        paramReceiverParameterDescriptor2 = new StringBuilder();
        paramReceiverParameterDescriptor2.append(paramReceiverParameterDescriptor1);
        paramReceiverParameterDescriptor2.append("index is ");
        paramReceiverParameterDescriptor2.append(paramReceiverParameterDescriptor1.getIndex());
        paramReceiverParameterDescriptor2.append(" but position is ");
        paramReceiverParameterDescriptor2.append(k);
        throw new IllegalStateException(paramReceiverParameterDescriptor2.toString());
      }
    }
    return this;
  }
  
  public boolean isActual()
  {
    return this.isActual;
  }
  
  public boolean isExpect()
  {
    return this.isExpect;
  }
  
  public boolean isExternal()
  {
    return this.isExternal;
  }
  
  public boolean isHiddenForResolutionEverywhereBesideSupercalls()
  {
    return this.isHiddenForResolutionEverywhereBesideSupercalls;
  }
  
  public boolean isHiddenToOvercomeSignatureClash()
  {
    return this.isHiddenToOvercomeSignatureClash;
  }
  
  public boolean isInfix()
  {
    if (this.isInfix) {
      return true;
    }
    Iterator localIterator = getOriginal().getOverriddenDescriptors().iterator();
    while (localIterator.hasNext()) {
      if (((FunctionDescriptor)localIterator.next()).isInfix()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isInline()
  {
    return this.isInline;
  }
  
  public boolean isOperator()
  {
    if (this.isOperator) {
      return true;
    }
    Iterator localIterator = getOriginal().getOverriddenDescriptors().iterator();
    while (localIterator.hasNext()) {
      if (((FunctionDescriptor)localIterator.next()).isOperator()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isSuspend()
  {
    return this.isSuspend;
  }
  
  public boolean isTailrec()
  {
    return this.isTailrec;
  }
  
  public FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> newCopyBuilder()
  {
    CopyConfiguration localCopyConfiguration = newCopyBuilder(TypeSubstitutor.EMPTY);
    if (localCopyConfiguration == null) {
      $$$reportNull$$$0(21);
    }
    return localCopyConfiguration;
  }
  
  protected CopyConfiguration newCopyBuilder(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(22);
    }
    return new CopyConfiguration(paramTypeSubstitutor.getSubstitution(), getContainingDeclaration(), getModality(), getVisibility(), getKind(), getValueParameters(), getExtensionReceiverParameter(), getReturnType(), null);
  }
  
  public <V> void putInUserDataMap(CallableDescriptor.UserDataKey<V> paramUserDataKey, Object paramObject)
  {
    if (this.userDataMap == null) {
      this.userDataMap = new LinkedHashMap();
    }
    this.userDataMap.put(paramUserDataKey, paramObject);
  }
  
  public void setActual(boolean paramBoolean)
  {
    this.isActual = paramBoolean;
  }
  
  public void setExpect(boolean paramBoolean)
  {
    this.isExpect = paramBoolean;
  }
  
  public void setExternal(boolean paramBoolean)
  {
    this.isExternal = paramBoolean;
  }
  
  public void setHasStableParameterNames(boolean paramBoolean)
  {
    this.hasStableParameterNames = paramBoolean;
  }
  
  public void setHasSynthesizedParameterNames(boolean paramBoolean)
  {
    this.hasSynthesizedParameterNames = paramBoolean;
  }
  
  public void setInfix(boolean paramBoolean)
  {
    this.isInfix = paramBoolean;
  }
  
  public void setInline(boolean paramBoolean)
  {
    this.isInline = paramBoolean;
  }
  
  public void setOperator(boolean paramBoolean)
  {
    this.isOperator = paramBoolean;
  }
  
  public void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> paramCollection)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(15);
    }
    this.overriddenFunctions = paramCollection;
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (((FunctionDescriptor)paramCollection.next()).isHiddenForResolutionEverywhereBesideSupercalls()) {
        this.isHiddenForResolutionEverywhereBesideSupercalls = true;
      }
    }
  }
  
  public void setReturnType(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(10);
    }
    this.unsubstitutedReturnType = paramKotlinType;
  }
  
  public void setSuspend(boolean paramBoolean)
  {
    this.isSuspend = paramBoolean;
  }
  
  public void setTailrec(boolean paramBoolean)
  {
    this.isTailrec = paramBoolean;
  }
  
  public void setVisibility(Visibility paramVisibility)
  {
    if (paramVisibility == null) {
      $$$reportNull$$$0(9);
    }
    this.visibility = paramVisibility;
  }
  
  public FunctionDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(20);
    }
    if (paramTypeSubstitutor.isEmpty()) {
      return this;
    }
    return newCopyBuilder(paramTypeSubstitutor).setOriginal(getOriginal()).setJustForTypeSubstitution(true).build();
  }
  
  public class CopyConfiguration
    implements FunctionDescriptor.CopyBuilder<FunctionDescriptor>
  {
    private Annotations additionalAnnotations = null;
    protected boolean copyOverrides = true;
    protected ReceiverParameterDescriptor dispatchReceiverParameter = FunctionDescriptorImpl.this.dispatchReceiverParameter;
    protected boolean dropOriginalInContainingParts = false;
    private boolean isHiddenForResolutionEverywhereBesideSupercalls = FunctionDescriptorImpl.this.isHiddenForResolutionEverywhereBesideSupercalls();
    private boolean isHiddenToOvercomeSignatureClash = FunctionDescriptorImpl.this.isHiddenToOvercomeSignatureClash();
    protected boolean justForTypeSubstitution = false;
    protected CallableMemberDescriptor.Kind kind;
    protected Name name;
    protected ReceiverParameterDescriptor newExtensionReceiverParameter;
    private Boolean newHasSynthesizedParameterNames = null;
    protected Modality newModality;
    protected DeclarationDescriptor newOwner;
    protected KotlinType newReturnType;
    private List<TypeParameterDescriptor> newTypeParameters = null;
    protected List<ValueParameterDescriptor> newValueParameterDescriptors;
    protected Visibility newVisibility;
    protected FunctionDescriptor original = null;
    protected boolean preserveSourceElement = false;
    protected boolean signatureChange = false;
    protected TypeSubstitution substitution;
    private Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap = new LinkedHashMap();
    
    public CopyConfiguration(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, List<ValueParameterDescriptor> paramList, ReceiverParameterDescriptor paramReceiverParameterDescriptor, KotlinType paramKotlinType, Name paramName)
    {
      this.substitution = paramDeclarationDescriptor;
      this.newOwner = paramModality;
      this.newModality = paramVisibility;
      this.newVisibility = paramKind;
      this.kind = paramList;
      this.newValueParameterDescriptors = paramReceiverParameterDescriptor;
      this.newExtensionReceiverParameter = paramKotlinType;
      this.newReturnType = paramName;
      Object localObject;
      this.name = localObject;
    }
    
    public FunctionDescriptor build()
    {
      return FunctionDescriptorImpl.this.doSubstitute(this);
    }
    
    public CopyConfiguration setAdditionalAnnotations(Annotations paramAnnotations)
    {
      if (paramAnnotations == null) {
        $$$reportNull$$$0(32);
      }
      this.additionalAnnotations = paramAnnotations;
      return this;
    }
    
    public CopyConfiguration setCopyOverrides(boolean paramBoolean)
    {
      this.copyOverrides = paramBoolean;
      return this;
    }
    
    public CopyConfiguration setDispatchReceiverParameter(ReceiverParameterDescriptor paramReceiverParameterDescriptor)
    {
      this.dispatchReceiverParameter = paramReceiverParameterDescriptor;
      return this;
    }
    
    public CopyConfiguration setDropOriginalInContainingParts()
    {
      this.dropOriginalInContainingParts = true;
      return this;
    }
    
    public CopyConfiguration setExtensionReceiverParameter(ReceiverParameterDescriptor paramReceiverParameterDescriptor)
    {
      this.newExtensionReceiverParameter = paramReceiverParameterDescriptor;
      return this;
    }
    
    public CopyConfiguration setHasSynthesizedParameterNames(boolean paramBoolean)
    {
      this.newHasSynthesizedParameterNames = Boolean.valueOf(paramBoolean);
      return this;
    }
    
    public CopyConfiguration setHiddenForResolutionEverywhereBesideSupercalls()
    {
      this.isHiddenForResolutionEverywhereBesideSupercalls = true;
      return this;
    }
    
    public CopyConfiguration setHiddenToOvercomeSignatureClash()
    {
      this.isHiddenToOvercomeSignatureClash = true;
      return this;
    }
    
    public CopyConfiguration setJustForTypeSubstitution(boolean paramBoolean)
    {
      this.justForTypeSubstitution = paramBoolean;
      return this;
    }
    
    public CopyConfiguration setKind(CallableMemberDescriptor.Kind paramKind)
    {
      if (paramKind == null) {
        $$$reportNull$$$0(13);
      }
      this.kind = paramKind;
      return this;
    }
    
    public CopyConfiguration setModality(Modality paramModality)
    {
      if (paramModality == null) {
        $$$reportNull$$$0(9);
      }
      this.newModality = paramModality;
      return this;
    }
    
    public CopyConfiguration setName(Name paramName)
    {
      if (paramName == null) {
        $$$reportNull$$$0(16);
      }
      this.name = paramName;
      return this;
    }
    
    public CopyConfiguration setOriginal(CallableMemberDescriptor paramCallableMemberDescriptor)
    {
      this.original = ((FunctionDescriptor)paramCallableMemberDescriptor);
      return this;
    }
    
    public CopyConfiguration setOwner(DeclarationDescriptor paramDeclarationDescriptor)
    {
      if (paramDeclarationDescriptor == null) {
        $$$reportNull$$$0(7);
      }
      this.newOwner = paramDeclarationDescriptor;
      return this;
    }
    
    public CopyConfiguration setPreserveSourceElement()
    {
      this.preserveSourceElement = true;
      return this;
    }
    
    public CopyConfiguration setReturnType(KotlinType paramKotlinType)
    {
      if (paramKotlinType == null) {
        $$$reportNull$$$0(22);
      }
      this.newReturnType = paramKotlinType;
      return this;
    }
    
    public CopyConfiguration setSignatureChange()
    {
      this.signatureChange = true;
      return this;
    }
    
    public CopyConfiguration setSubstitution(TypeSubstitution paramTypeSubstitution)
    {
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(34);
      }
      this.substitution = paramTypeSubstitution;
      return this;
    }
    
    public CopyConfiguration setTypeParameters(List<TypeParameterDescriptor> paramList)
    {
      if (paramList == null) {
        $$$reportNull$$$0(20);
      }
      this.newTypeParameters = paramList;
      return this;
    }
    
    public CopyConfiguration setValueParameters(List<ValueParameterDescriptor> paramList)
    {
      if (paramList == null) {
        $$$reportNull$$$0(18);
      }
      this.newValueParameterDescriptors = paramList;
      return this;
    }
    
    public CopyConfiguration setVisibility(Visibility paramVisibility)
    {
      if (paramVisibility == null) {
        $$$reportNull$$$0(11);
      }
      this.newVisibility = paramVisibility;
      return this;
    }
  }
}
