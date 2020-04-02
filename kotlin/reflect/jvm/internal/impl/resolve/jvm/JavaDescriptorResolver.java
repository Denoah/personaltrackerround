package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.LightClassOriginKind;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

public final class JavaDescriptorResolver
{
  private final JavaResolverCache javaResolverCache;
  private final LazyJavaPackageFragmentProvider packageFragmentProvider;
  
  public JavaDescriptorResolver(LazyJavaPackageFragmentProvider paramLazyJavaPackageFragmentProvider, JavaResolverCache paramJavaResolverCache)
  {
    this.packageFragmentProvider = paramLazyJavaPackageFragmentProvider;
    this.javaResolverCache = paramJavaResolverCache;
  }
  
  public final LazyJavaPackageFragmentProvider getPackageFragmentProvider()
  {
    return this.packageFragmentProvider;
  }
  
  public final ClassDescriptor resolveClass(JavaClass paramJavaClass)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaClass, "javaClass");
    FqName localFqName = paramJavaClass.getFqName();
    if ((localFqName != null) && (paramJavaClass.getLightClassOriginKind() == LightClassOriginKind.SOURCE)) {
      return this.javaResolverCache.getClassResolvedFromSource(localFqName);
    }
    JavaClass localJavaClass = paramJavaClass.getOuterClass();
    Object localObject1 = null;
    Object localObject2 = null;
    if (localJavaClass != null)
    {
      localObject1 = resolveClass(localJavaClass);
      if (localObject1 != null) {
        localObject1 = ((ClassDescriptor)localObject1).getUnsubstitutedInnerClassesScope();
      } else {
        localObject1 = null;
      }
      if (localObject1 != null) {
        paramJavaClass = ((MemberScope)localObject1).getContributedClassifier(paramJavaClass.getName(), (LookupLocation)NoLookupLocation.FROM_JAVA_LOADER);
      } else {
        paramJavaClass = null;
      }
      if (!(paramJavaClass instanceof ClassDescriptor)) {
        paramJavaClass = (JavaClass)localObject2;
      }
      return (ClassDescriptor)paramJavaClass;
    }
    if (localFqName == null) {
      return null;
    }
    localObject2 = this.packageFragmentProvider;
    localFqName = localFqName.parent();
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "fqName.parent()");
    localObject2 = (LazyJavaPackageFragment)CollectionsKt.firstOrNull(((LazyJavaPackageFragmentProvider)localObject2).getPackageFragments(localFqName));
    if (localObject2 != null) {
      localObject1 = ((LazyJavaPackageFragment)localObject2).findClassifierByJavaClass$descriptors_jvm(paramJavaClass);
    }
    return localObject1;
  }
}
