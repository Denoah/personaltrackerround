package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.sequences.SequencesKt;

public final class FindClassInModuleKt
{
  public static final ClassDescriptor findClassAcrossModuleDependencies(ModuleDescriptor paramModuleDescriptor, ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$findClassAcrossModuleDependencies");
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    paramClassId = findClassifierAcrossModuleDependencies(paramModuleDescriptor, paramClassId);
    paramModuleDescriptor = paramClassId;
    if (!(paramClassId instanceof ClassDescriptor)) {
      paramModuleDescriptor = null;
    }
    return (ClassDescriptor)paramModuleDescriptor;
  }
  
  public static final ClassifierDescriptor findClassifierAcrossModuleDependencies(ModuleDescriptor paramModuleDescriptor, ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$findClassifierAcrossModuleDependencies");
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    Object localObject = paramClassId.getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "classId.packageFqName");
    paramModuleDescriptor = paramModuleDescriptor.getPackage((FqName)localObject);
    paramClassId = paramClassId.getRelativeClassName().pathSegments();
    Intrinsics.checkExpressionValueIsNotNull(paramClassId, "classId.relativeClassName.pathSegments()");
    localObject = paramModuleDescriptor.getMemberScope();
    paramModuleDescriptor = CollectionsKt.first(paramClassId);
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "segments.first()");
    paramModuleDescriptor = ((MemberScope)localObject).getContributedClassifier((Name)paramModuleDescriptor, (LookupLocation)NoLookupLocation.FROM_DESERIALIZATION);
    if (paramModuleDescriptor != null)
    {
      localObject = paramClassId.subList(1, paramClassId.size()).iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramClassId = (Name)((Iterator)localObject).next();
        if (!(paramModuleDescriptor instanceof ClassDescriptor)) {
          return null;
        }
        paramModuleDescriptor = ((ClassDescriptor)paramModuleDescriptor).getUnsubstitutedInnerClassesScope();
        Intrinsics.checkExpressionValueIsNotNull(paramClassId, "name");
        paramClassId = paramModuleDescriptor.getContributedClassifier(paramClassId, (LookupLocation)NoLookupLocation.FROM_DESERIALIZATION);
        paramModuleDescriptor = paramClassId;
        if (!(paramClassId instanceof ClassDescriptor)) {
          paramModuleDescriptor = null;
        }
        paramModuleDescriptor = (ClassDescriptor)paramModuleDescriptor;
        if (paramModuleDescriptor != null) {
          paramModuleDescriptor = (ClassifierDescriptor)paramModuleDescriptor;
        } else {
          return null;
        }
      }
      return paramModuleDescriptor;
    }
    return null;
  }
  
  public static final ClassDescriptor findNonGenericClassAcrossDependencies(ModuleDescriptor paramModuleDescriptor, ClassId paramClassId, NotFoundClasses paramNotFoundClasses)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$findNonGenericClassAcrossDependencies");
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    Intrinsics.checkParameterIsNotNull(paramNotFoundClasses, "notFoundClasses");
    paramModuleDescriptor = findClassAcrossModuleDependencies(paramModuleDescriptor, paramClassId);
    if (paramModuleDescriptor != null) {
      return paramModuleDescriptor;
    }
    return paramNotFoundClasses.getClass(paramClassId, SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(paramClassId, (Function1)findNonGenericClassAcrossDependencies.typeParametersCount.1.INSTANCE), (Function1)findNonGenericClassAcrossDependencies.typeParametersCount.2.INSTANCE)));
  }
  
  public static final TypeAliasDescriptor findTypeAliasAcrossModuleDependencies(ModuleDescriptor paramModuleDescriptor, ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$findTypeAliasAcrossModuleDependencies");
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    paramClassId = findClassifierAcrossModuleDependencies(paramModuleDescriptor, paramClassId);
    paramModuleDescriptor = paramClassId;
    if (!(paramClassId instanceof TypeAliasDescriptor)) {
      paramModuleDescriptor = null;
    }
    return (TypeAliasDescriptor)paramModuleDescriptor;
  }
}
