package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

public final class DescriptorUtilKt
{
  public static final ClassDescriptor resolveClassByFqName(ModuleDescriptor paramModuleDescriptor, FqName paramFqName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$resolveClassByFqName");
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "lookupLocation");
    boolean bool = paramFqName.isRoot();
    Object localObject1 = null;
    if (bool) {
      return null;
    }
    Object localObject2 = paramFqName.parent();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "fqName.parent()");
    Object localObject3 = paramModuleDescriptor.getPackage((FqName)localObject2).getMemberScope();
    localObject2 = paramFqName.shortName();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "fqName.shortName()");
    localObject3 = ((MemberScope)localObject3).getContributedClassifier((Name)localObject2, paramLookupLocation);
    localObject2 = localObject3;
    if (!(localObject3 instanceof ClassDescriptor)) {
      localObject2 = null;
    }
    localObject2 = (ClassDescriptor)localObject2;
    if (localObject2 != null) {
      return localObject2;
    }
    localObject2 = paramFqName.parent();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "fqName.parent()");
    paramModuleDescriptor = resolveClassByFqName(paramModuleDescriptor, (FqName)localObject2, paramLookupLocation);
    if (paramModuleDescriptor != null)
    {
      paramModuleDescriptor = paramModuleDescriptor.getUnsubstitutedInnerClassesScope();
      if (paramModuleDescriptor != null)
      {
        paramFqName = paramFqName.shortName();
        Intrinsics.checkExpressionValueIsNotNull(paramFqName, "fqName.shortName()");
        paramModuleDescriptor = paramModuleDescriptor.getContributedClassifier(paramFqName, paramLookupLocation);
        break label176;
      }
    }
    paramModuleDescriptor = null;
    label176:
    if (!(paramModuleDescriptor instanceof ClassDescriptor)) {
      paramModuleDescriptor = localObject1;
    }
    return (ClassDescriptor)paramModuleDescriptor;
  }
}
