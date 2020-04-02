package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class CompositePackageFragmentProvider
  implements PackageFragmentProvider
{
  private final List<PackageFragmentProvider> providers;
  
  public CompositePackageFragmentProvider(List<? extends PackageFragmentProvider> paramList)
  {
    this.providers = paramList;
  }
  
  public List<PackageFragmentDescriptor> getPackageFragments(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.providers.iterator();
    while (localIterator.hasNext()) {
      localArrayList.addAll((Collection)((PackageFragmentProvider)localIterator.next()).getPackageFragments(paramFqName));
    }
    return CollectionsKt.toList((Iterable)localArrayList);
  }
  
  public Collection<FqName> getSubPackagesOf(FqName paramFqName, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    HashSet localHashSet = new HashSet();
    Iterator localIterator = this.providers.iterator();
    while (localIterator.hasNext()) {
      localHashSet.addAll(((PackageFragmentProvider)localIterator.next()).getSubPackagesOf(paramFqName, paramFunction1));
    }
    return (Collection)localHashSet;
  }
}
