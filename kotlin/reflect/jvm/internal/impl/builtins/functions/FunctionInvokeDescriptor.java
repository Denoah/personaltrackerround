package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl.CopyConfiguration;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;

public final class FunctionInvokeDescriptor
  extends SimpleFunctionDescriptorImpl
{
  public static final Factory Factory = new Factory(null);
  
  private FunctionInvokeDescriptor(DeclarationDescriptor paramDeclarationDescriptor, FunctionInvokeDescriptor paramFunctionInvokeDescriptor, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    super(paramDeclarationDescriptor, (SimpleFunctionDescriptor)paramFunctionInvokeDescriptor, Annotations.Companion.getEMPTY(), OperatorNameConventions.INVOKE, paramKind, SourceElement.NO_SOURCE);
    setOperator(true);
    setSuspend(paramBoolean);
    setHasStableParameterNames(false);
  }
  
  private final FunctionDescriptor replaceParameterNames(List<Name> paramList)
  {
    int i = getValueParameters().size() - paramList.size();
    boolean bool1 = false;
    int j;
    if ((i != 0) && (i != 1)) {
      j = 0;
    } else {
      j = 1;
    }
    if ((_Assertions.ENABLED) && (j == 0)) {
      throw ((Throwable)new AssertionError("Assertion failed"));
    }
    Object localObject1 = getValueParameters();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "valueParameters");
    localObject1 = (Iterable)localObject1;
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      ValueParameterDescriptor localValueParameterDescriptor = (ValueParameterDescriptor)localIterator.next();
      Intrinsics.checkExpressionValueIsNotNull(localValueParameterDescriptor, "it");
      Name localName = localValueParameterDescriptor.getName();
      Intrinsics.checkExpressionValueIsNotNull(localName, "it.name");
      j = localValueParameterDescriptor.getIndex();
      int k = j - i;
      localObject1 = localName;
      if (k >= 0)
      {
        localObject2 = (Name)paramList.get(k);
        localObject1 = localName;
        if (localObject2 != null) {
          localObject1 = localObject2;
        }
      }
      localCollection.add(localValueParameterDescriptor.copy((CallableDescriptor)this, (Name)localObject1, j));
    }
    Object localObject2 = (List)localCollection;
    localObject1 = newCopyBuilder(TypeSubstitutor.EMPTY);
    paramList = (Iterable)paramList;
    boolean bool2;
    if (((paramList instanceof Collection)) && (((Collection)paramList).isEmpty()))
    {
      bool2 = bool1;
    }
    else
    {
      paramList = paramList.iterator();
      do
      {
        bool2 = bool1;
        if (!paramList.hasNext()) {
          break;
        }
        if ((Name)paramList.next() == null) {
          j = 1;
        } else {
          j = 0;
        }
      } while (j == 0);
      bool2 = true;
    }
    paramList = ((FunctionDescriptorImpl.CopyConfiguration)localObject1).setHasSynthesizedParameterNames(bool2).setValueParameters((List)localObject2).setOriginal((CallableMemberDescriptor)getOriginal());
    Intrinsics.checkExpressionValueIsNotNull(paramList, "newCopyBuilder(TypeSubst…   .setOriginal(original)");
    paramList = super.doSubstitute(paramList);
    if (paramList == null) {
      Intrinsics.throwNpe();
    }
    return paramList;
  }
  
  protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    return (FunctionDescriptorImpl)new FunctionInvokeDescriptor(paramDeclarationDescriptor, (FunctionInvokeDescriptor)paramFunctionDescriptor, paramKind, isSuspend());
  }
  
  protected FunctionDescriptor doSubstitute(FunctionDescriptorImpl.CopyConfiguration paramCopyConfiguration)
  {
    Intrinsics.checkParameterIsNotNull(paramCopyConfiguration, "configuration");
    paramCopyConfiguration = (FunctionInvokeDescriptor)super.doSubstitute(paramCopyConfiguration);
    if (paramCopyConfiguration != null)
    {
      Object localObject1 = paramCopyConfiguration.getValueParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "substituted.valueParameters");
      localObject1 = (Iterable)localObject1;
      boolean bool = localObject1 instanceof Collection;
      int i = 0;
      if ((bool) && (((Collection)localObject1).isEmpty())) {}
      do
      {
        while (!((Iterator)localObject1).hasNext())
        {
          j = 1;
          break;
          localObject1 = ((Iterable)localObject1).iterator();
        }
        localObject2 = (ValueParameterDescriptor)((Iterator)localObject1).next();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
        localObject2 = ((ValueParameterDescriptor)localObject2).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "it.type");
        if (FunctionTypesKt.extractParameterNameFromFunctionTypeArgument((KotlinType)localObject2) != null) {
          j = 1;
        } else {
          j = 0;
        }
      } while (j == 0);
      int j = i;
      if (j != 0) {
        return (FunctionDescriptor)paramCopyConfiguration;
      }
      localObject1 = paramCopyConfiguration.getValueParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "substituted.valueParameters");
      Object localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject3 = (ValueParameterDescriptor)((Iterator)localObject2).next();
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "it");
        localObject3 = ((ValueParameterDescriptor)localObject3).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "it.type");
        ((Collection)localObject1).add(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument((KotlinType)localObject3));
      }
      return paramCopyConfiguration.replaceParameterNames((List)localObject1);
    }
    return null;
  }
  
  public boolean isExternal()
  {
    return false;
  }
  
  public boolean isInline()
  {
    return false;
  }
  
  public boolean isTailrec()
  {
    return false;
  }
  
  public static final class Factory
  {
    private Factory() {}
    
    private final ValueParameterDescriptor createValueParameter(FunctionInvokeDescriptor paramFunctionInvokeDescriptor, int paramInt, TypeParameterDescriptor paramTypeParameterDescriptor)
    {
      Object localObject = paramTypeParameterDescriptor.getName().asString();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "typeParameter.name.asString()");
      int i = ((String)localObject).hashCode();
      if (i != 69)
      {
        if ((i == 84) && (((String)localObject).equals("T")))
        {
          localObject = "instance";
          break label95;
        }
      }
      else if (((String)localObject).equals("E"))
      {
        localObject = "receiver";
        break label95;
      }
      if (localObject != null)
      {
        localObject = ((String)localObject).toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "(this as java.lang.String).toLowerCase()");
        label95:
        paramFunctionInvokeDescriptor = (CallableDescriptor)paramFunctionInvokeDescriptor;
        Annotations localAnnotations = Annotations.Companion.getEMPTY();
        localObject = Name.identifier((String)localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "Name.identifier(name)");
        paramTypeParameterDescriptor = paramTypeParameterDescriptor.getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "typeParameter.defaultType");
        paramTypeParameterDescriptor = (KotlinType)paramTypeParameterDescriptor;
        SourceElement localSourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkExpressionValueIsNotNull(localSourceElement, "SourceElement.NO_SOURCE");
        return (ValueParameterDescriptor)new ValueParameterDescriptorImpl(paramFunctionInvokeDescriptor, null, paramInt, localAnnotations, (Name)localObject, paramTypeParameterDescriptor, false, false, false, null, localSourceElement);
      }
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }
    
    public final FunctionInvokeDescriptor create(FunctionClassDescriptor paramFunctionClassDescriptor, boolean paramBoolean)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionClassDescriptor, "functionClass");
      List localList1 = paramFunctionClassDescriptor.getDeclaredTypeParameters();
      FunctionInvokeDescriptor localFunctionInvokeDescriptor = new FunctionInvokeDescriptor((DeclarationDescriptor)paramFunctionClassDescriptor, null, CallableMemberDescriptor.Kind.DECLARATION, paramBoolean, null);
      paramFunctionClassDescriptor = paramFunctionClassDescriptor.getThisAsReceiverParameter();
      List localList2 = CollectionsKt.emptyList();
      Object localObject1 = (Iterable)localList1;
      Object localObject2 = new ArrayList();
      Object localObject3 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject1 = ((Iterator)localObject3).next();
        int i;
        if (((TypeParameterDescriptor)localObject1).getVariance() == Variance.IN_VARIANCE) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break;
        }
        ((ArrayList)localObject2).add(localObject1);
      }
      localObject1 = CollectionsKt.withIndex((Iterable)localObject2);
      localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (IndexedValue)((Iterator)localObject1).next();
        ((Collection)localObject2).add(FunctionInvokeDescriptor.Factory.createValueParameter(localFunctionInvokeDescriptor, ((IndexedValue)localObject3).getIndex(), (TypeParameterDescriptor)((IndexedValue)localObject3).getValue()));
      }
      localFunctionInvokeDescriptor.initialize(null, paramFunctionClassDescriptor, localList2, (List)localObject2, (KotlinType)((TypeParameterDescriptor)CollectionsKt.last(localList1)).getDefaultType(), Modality.ABSTRACT, Visibilities.PUBLIC);
      localFunctionInvokeDescriptor.setHasSynthesizedParameterNames(true);
      return localFunctionInvokeDescriptor;
    }
  }
}
