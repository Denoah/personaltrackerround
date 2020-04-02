package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class TypeIntersectionScope
  extends AbstractScopeAdapter
{
  public static final Companion Companion = new Companion(null);
  private final ChainedMemberScope workerScope;
  
  private TypeIntersectionScope(ChainedMemberScope paramChainedMemberScope)
  {
    this.workerScope = paramChainedMemberScope;
  }
  
  @JvmStatic
  public static final MemberScope create(String paramString, Collection<? extends KotlinType> paramCollection)
  {
    return Companion.create(paramString, paramCollection);
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    Object localObject = (Iterable)super.getContributedDescriptors(paramDescriptorKindFilter, paramFunction1);
    paramFunction1 = new ArrayList();
    paramDescriptorKindFilter = new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (((DeclarationDescriptor)localObject instanceof CallableDescriptor)) {
        paramFunction1.add(localObject);
      } else {
        paramDescriptorKindFilter.add(localObject);
      }
    }
    paramFunction1 = new Pair(paramFunction1, paramDescriptorKindFilter);
    paramDescriptorKindFilter = (List)paramFunction1.component1();
    paramFunction1 = (List)paramFunction1.component2();
    if (paramDescriptorKindFilter != null) {
      return (Collection)CollectionsKt.plus(OverridingUtilsKt.selectMostSpecificInEachOverridableGroup((Collection)paramDescriptorKindFilter, (Function1)getContributedDescriptors.2.INSTANCE), (Iterable)paramFunction1);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.CallableDescriptor>");
  }
  
  public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(super.getContributedFunctions(paramName, paramLookupLocation), (Function1)getContributedFunctions.1.INSTANCE);
  }
  
  public Collection<PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(super.getContributedVariables(paramName, paramLookupLocation), (Function1)getContributedVariables.1.INSTANCE);
  }
  
  protected ChainedMemberScope getWorkerScope()
  {
    return this.workerScope;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final MemberScope create(String paramString, Collection<? extends KotlinType> paramCollection)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "message");
      Intrinsics.checkParameterIsNotNull(paramCollection, "types");
      Object localObject = (Iterable)paramCollection;
      Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localCollection.add(((KotlinType)((Iterator)localObject).next()).getMemberScope());
      }
      paramString = new ChainedMemberScope(paramString, (List)localCollection);
      if (paramCollection.size() <= 1) {
        return (MemberScope)paramString;
      }
      return (MemberScope)new TypeIntersectionScope(paramString, null);
    }
  }
}
