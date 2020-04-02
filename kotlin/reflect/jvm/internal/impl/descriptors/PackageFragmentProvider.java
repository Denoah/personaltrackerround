package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface PackageFragmentProvider
{
  public abstract List<PackageFragmentDescriptor> getPackageFragments(FqName paramFqName);
  
  public abstract Collection<FqName> getSubPackagesOf(FqName paramFqName, Function1<? super Name, Boolean> paramFunction1);
}
