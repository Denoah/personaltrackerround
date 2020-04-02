package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.List;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class LazyJavaPackageFragmentProvider
  implements PackageFragmentProvider
{
  private final LazyJavaResolverContext c;
  private final CacheWithNotNullValues<FqName, LazyJavaPackageFragment> packageFragments;
  
  public LazyJavaPackageFragmentProvider(JavaResolverComponents paramJavaResolverComponents)
  {
    paramJavaResolverComponents = new LazyJavaResolverContext(paramJavaResolverComponents, (TypeParameterResolver)TypeParameterResolver.EMPTY.INSTANCE, LazyKt.lazyOf(null));
    this.c = paramJavaResolverComponents;
    this.packageFragments = paramJavaResolverComponents.getStorageManager().createCacheWithNotNullValues();
  }
  
  private final LazyJavaPackageFragment getPackageFragment(FqName paramFqName)
  {
    final JavaPackage localJavaPackage = this.c.getComponents().getFinder().findPackage(paramFqName);
    if (localJavaPackage != null) {
      (LazyJavaPackageFragment)this.packageFragments.computeIfAbsent(paramFqName, (Function0)new Lambda(localJavaPackage)
      {
        public final LazyJavaPackageFragment invoke()
        {
          return new LazyJavaPackageFragment(LazyJavaPackageFragmentProvider.access$getC$p(this.this$0), localJavaPackage);
        }
      });
    }
    return null;
  }
  
  public List<LazyJavaPackageFragment> getPackageFragments(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return CollectionsKt.listOfNotNull(getPackageFragment(paramFqName));
  }
  
  public List<FqName> getSubPackagesOf(FqName paramFqName, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    paramFqName = getPackageFragment(paramFqName);
    if (paramFqName != null) {
      paramFqName = paramFqName.getSubPackageFqNames$descriptors_jvm();
    } else {
      paramFqName = null;
    }
    if (paramFqName == null) {
      paramFqName = CollectionsKt.emptyList();
    }
    return paramFqName;
  }
}
