package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude.TopLevelPackages;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;

public class SubpackagesScope
  extends MemberScopeImpl
{
  private final FqName fqName;
  private final ModuleDescriptor moduleDescriptor;
  
  public SubpackagesScope(ModuleDescriptor paramModuleDescriptor, FqName paramFqName)
  {
    this.moduleDescriptor = paramModuleDescriptor;
    this.fqName = paramFqName;
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    if (!paramDescriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getPACKAGES_MASK())) {
      return (Collection)kotlin.collections.CollectionsKt.emptyList();
    }
    if ((this.fqName.isRoot()) && (paramDescriptorKindFilter.getExcludes().contains(DescriptorKindExclude.TopLevelPackages.INSTANCE))) {
      return (Collection)kotlin.collections.CollectionsKt.emptyList();
    }
    Object localObject = this.moduleDescriptor.getSubPackagesOf(this.fqName, paramFunction1);
    paramDescriptorKindFilter = new ArrayList(((Collection)localObject).size());
    Iterator localIterator = ((Collection)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = ((FqName)localIterator.next()).shortName();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "subFqName.shortName()");
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull((Collection)paramDescriptorKindFilter, getPackage((Name)localObject));
      }
    }
    return (Collection)paramDescriptorKindFilter;
  }
  
  protected final PackageViewDescriptor getPackage(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    if (paramName.isSpecial()) {
      return null;
    }
    ModuleDescriptor localModuleDescriptor = this.moduleDescriptor;
    paramName = this.fqName.child(paramName);
    Intrinsics.checkExpressionValueIsNotNull(paramName, "fqName.child(name)");
    paramName = localModuleDescriptor.getPackage(paramName);
    if (paramName.isEmpty()) {
      return null;
    }
    return paramName;
  }
}
