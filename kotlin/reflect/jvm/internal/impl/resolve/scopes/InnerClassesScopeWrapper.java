package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class InnerClassesScopeWrapper
  extends MemberScopeImpl
{
  private final MemberScope workerScope;
  
  public InnerClassesScopeWrapper(MemberScope paramMemberScope)
  {
    this.workerScope = paramMemberScope;
  }
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    paramLookupLocation = this.workerScope.getContributedClassifier(paramName, paramLookupLocation);
    paramName = null;
    if (paramLookupLocation != null)
    {
      if (!(paramLookupLocation instanceof ClassDescriptor)) {
        paramName = null;
      } else {
        paramName = paramLookupLocation;
      }
      paramName = (ClassDescriptor)paramName;
      if (paramName != null)
      {
        paramName = (ClassifierDescriptor)paramName;
      }
      else
      {
        paramName = paramLookupLocation;
        if (!(paramLookupLocation instanceof TypeAliasDescriptor)) {
          paramName = null;
        }
        paramName = (ClassifierDescriptor)paramName;
      }
    }
    return paramName;
  }
  
  public List<ClassifierDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    paramDescriptorKindFilter = paramDescriptorKindFilter.restrictedToKindsOrNull(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK());
    if (paramDescriptorKindFilter != null)
    {
      paramFunction1 = (Iterable)this.workerScope.getContributedDescriptors(paramDescriptorKindFilter, paramFunction1);
      paramDescriptorKindFilter = (Collection)new ArrayList();
      Iterator localIterator = paramFunction1.iterator();
      while (localIterator.hasNext())
      {
        paramFunction1 = localIterator.next();
        if ((paramFunction1 instanceof ClassifierDescriptorWithTypeParameters)) {
          paramDescriptorKindFilter.add(paramFunction1);
        }
      }
      return (List)paramDescriptorKindFilter;
    }
    return CollectionsKt.emptyList();
  }
  
  public Set<Name> getFunctionNames()
  {
    return this.workerScope.getFunctionNames();
  }
  
  public Set<Name> getVariableNames()
  {
    return this.workerScope.getVariableNames();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Classes from ");
    localStringBuilder.append(this.workerScope);
    return localStringBuilder.toString();
  }
}
