package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;

public final class ChainedMemberScope
  implements MemberScope
{
  public static final Companion Companion = new Companion(null);
  private final String debugName;
  private final List<MemberScope> scopes;
  
  public ChainedMemberScope(String paramString, List<? extends MemberScope> paramList)
  {
    this.debugName = paramString;
    this.scopes = paramList;
  }
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    Object localObject1 = this.scopes;
    Object localObject2 = (ClassifierDescriptor)null;
    Iterator localIterator = ((List)localObject1).iterator();
    for (;;)
    {
      localObject1 = localObject2;
      if (!localIterator.hasNext()) {
        break;
      }
      localObject1 = ((MemberScope)localIterator.next()).getContributedClassifier(paramName, paramLookupLocation);
      if (localObject1 != null)
      {
        if ((!(localObject1 instanceof ClassifierDescriptorWithTypeParameters)) || (!((ClassifierDescriptorWithTypeParameters)localObject1).isExpect())) {
          break;
        }
        if (localObject2 == null) {
          localObject2 = localObject1;
        }
      }
    }
    return localObject1;
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    Object localObject = this.scopes;
    if (((List)localObject).isEmpty())
    {
      paramDescriptorKindFilter = (Collection)SetsKt.emptySet();
    }
    else
    {
      Collection localCollection = (Collection)null;
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localCollection = ScopeUtilsKt.concat(localCollection, ((MemberScope)((Iterator)localObject).next()).getContributedDescriptors(paramDescriptorKindFilter, paramFunction1));
      }
      if (localCollection != null) {
        paramDescriptorKindFilter = localCollection;
      } else {
        paramDescriptorKindFilter = (Collection)SetsKt.emptySet();
      }
    }
    return paramDescriptorKindFilter;
  }
  
  public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    Object localObject = this.scopes;
    if (((List)localObject).isEmpty())
    {
      paramName = (Collection)SetsKt.emptySet();
    }
    else
    {
      Collection localCollection = (Collection)null;
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localCollection = ScopeUtilsKt.concat(localCollection, ((MemberScope)((Iterator)localObject).next()).getContributedFunctions(paramName, paramLookupLocation));
      }
      if (localCollection != null) {
        paramName = localCollection;
      } else {
        paramName = (Collection)SetsKt.emptySet();
      }
    }
    return paramName;
  }
  
  public Collection<PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    Object localObject = this.scopes;
    if (((List)localObject).isEmpty())
    {
      paramName = (Collection)SetsKt.emptySet();
    }
    else
    {
      Collection localCollection = (Collection)null;
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localCollection = ScopeUtilsKt.concat(localCollection, ((MemberScope)((Iterator)localObject).next()).getContributedVariables(paramName, paramLookupLocation));
      }
      if (localCollection != null) {
        paramName = localCollection;
      } else {
        paramName = (Collection)SetsKt.emptySet();
      }
    }
    return paramName;
  }
  
  public Set<Name> getFunctionNames()
  {
    Object localObject = (Iterable)this.scopes;
    Collection localCollection = (Collection)new LinkedHashSet();
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      CollectionsKt.addAll(localCollection, (Iterable)((MemberScope)((Iterator)localObject).next()).getFunctionNames());
    }
    return (Set)localCollection;
  }
  
  public Set<Name> getVariableNames()
  {
    Object localObject = (Iterable)this.scopes;
    Collection localCollection = (Collection)new LinkedHashSet();
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      CollectionsKt.addAll(localCollection, (Iterable)((MemberScope)((Iterator)localObject).next()).getVariableNames());
    }
    return (Set)localCollection;
  }
  
  public String toString()
  {
    return this.debugName;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final MemberScope create(String paramString, List<? extends MemberScope> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "debugName");
      Intrinsics.checkParameterIsNotNull(paramList, "scopes");
      int i = paramList.size();
      if (i != 0)
      {
        if (i != 1) {
          paramString = (MemberScope)new ChainedMemberScope(paramString, paramList);
        } else {
          paramString = (MemberScope)CollectionsKt.single(paramList);
        }
      }
      else {
        paramString = (MemberScope)MemberScope.Empty.INSTANCE;
      }
      return paramString;
    }
  }
}
