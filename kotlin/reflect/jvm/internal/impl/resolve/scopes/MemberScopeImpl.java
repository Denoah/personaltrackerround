package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;

public abstract class MemberScopeImpl
  implements MemberScope
{
  public MemberScopeImpl() {}
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return null;
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    return (Collection)CollectionsKt.emptyList();
  }
  
  public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return (Collection)CollectionsKt.emptyList();
  }
  
  public Collection<? extends PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return (Collection)CollectionsKt.emptyList();
  }
  
  public Set<Name> getFunctionNames()
  {
    Object localObject1 = (Iterable)getContributedDescriptors(DescriptorKindFilter.FUNCTIONS, FunctionsKt.alwaysTrue());
    Collection localCollection = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      if ((localObject2 instanceof SimpleFunctionDescriptor)) {
        localCollection.add(localObject2);
      }
    }
    localObject1 = (Iterable)localCollection;
    localCollection = (Collection)new LinkedHashSet();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext()) {
      localCollection.add(((SimpleFunctionDescriptor)((Iterator)localObject1).next()).getName());
    }
    return (Set)localCollection;
  }
  
  public Set<Name> getVariableNames()
  {
    Object localObject = (Iterable)getContributedDescriptors(DescriptorKindFilter.VARIABLES, FunctionsKt.alwaysTrue());
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof VariableDescriptor)) {
        localCollection.add(localObject);
      }
    }
    localObject = (Iterable)localCollection;
    localCollection = (Collection)new LinkedHashSet();
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      localCollection.add(((VariableDescriptor)((Iterator)localObject).next()).getName());
    }
    return (Set)localCollection;
  }
  
  public void recordLookup(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    MemberScope.DefaultImpls.recordLookup(this, paramName, paramLookupLocation);
  }
}
