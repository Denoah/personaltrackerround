package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract class AbstractScopeAdapter
  implements MemberScope
{
  public AbstractScopeAdapter() {}
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return getWorkerScope().getContributedClassifier(paramName, paramLookupLocation);
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    return getWorkerScope().getContributedDescriptors(paramDescriptorKindFilter, paramFunction1);
  }
  
  public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return getWorkerScope().getContributedFunctions(paramName, paramLookupLocation);
  }
  
  public Collection<PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return getWorkerScope().getContributedVariables(paramName, paramLookupLocation);
  }
  
  public Set<Name> getFunctionNames()
  {
    return getWorkerScope().getFunctionNames();
  }
  
  public Set<Name> getVariableNames()
  {
    return getWorkerScope().getVariableNames();
  }
  
  protected abstract MemberScope getWorkerScope();
}
