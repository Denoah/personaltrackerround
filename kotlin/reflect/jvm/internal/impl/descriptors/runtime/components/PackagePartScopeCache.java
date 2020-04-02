package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader.Kind;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;

public final class PackagePartScopeCache
{
  private final ConcurrentHashMap<ClassId, MemberScope> cache;
  private final ReflectKotlinClassFinder kotlinClassFinder;
  private final DeserializedDescriptorResolver resolver;
  
  public PackagePartScopeCache(DeserializedDescriptorResolver paramDeserializedDescriptorResolver, ReflectKotlinClassFinder paramReflectKotlinClassFinder)
  {
    this.resolver = paramDeserializedDescriptorResolver;
    this.kotlinClassFinder = paramReflectKotlinClassFinder;
    this.cache = new ConcurrentHashMap();
  }
  
  public final MemberScope getPackagePartScope(ReflectKotlinClass paramReflectKotlinClass)
  {
    Intrinsics.checkParameterIsNotNull(paramReflectKotlinClass, "fileClass");
    ConcurrentMap localConcurrentMap = (ConcurrentMap)this.cache;
    ClassId localClassId = paramReflectKotlinClass.getClassId();
    Object localObject1 = localConcurrentMap.get(localClassId);
    if (localObject1 != null)
    {
      paramReflectKotlinClass = (ReflectKotlinClass)localObject1;
    }
    else
    {
      FqName localFqName = paramReflectKotlinClass.getClassId().getPackageFqName();
      Intrinsics.checkExpressionValueIsNotNull(localFqName, "fileClass.classId.packageFqName");
      if (paramReflectKotlinClass.getClassHeader().getKind() == KotlinClassHeader.Kind.MULTIFILE_CLASS)
      {
        localObject2 = (Iterable)paramReflectKotlinClass.getClassHeader().getMultifilePartNames();
        localObject1 = (Collection)new ArrayList();
        localObject2 = ((Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = JvmClassName.byInternalName((String)((Iterator)localObject2).next());
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "JvmClassName.byInternalName(partName)");
          localObject3 = ClassId.topLevel(((JvmClassName)localObject3).getFqNameForTopLevelClassMaybeWithDollars());
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.topLevel(JvmClas…velClassMaybeWithDollars)");
          localObject3 = KotlinClassFinderKt.findKotlinClass((KotlinClassFinder)this.kotlinClassFinder, (ClassId)localObject3);
          if (localObject3 != null) {
            ((Collection)localObject1).add(localObject3);
          }
        }
        localObject1 = (List)localObject1;
      }
      else
      {
        localObject1 = CollectionsKt.listOf(paramReflectKotlinClass);
      }
      Object localObject2 = new EmptyPackageFragmentDescriptor(this.resolver.getComponents().getModuleDescriptor(), localFqName);
      Object localObject3 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList();
      localObject3 = ((Iterable)localObject3).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        Object localObject4 = (KotlinJvmBinaryClass)((Iterator)localObject3).next();
        localObject4 = this.resolver.createKotlinPackagePartScope((PackageFragmentDescriptor)localObject2, (KotlinJvmBinaryClass)localObject4);
        if (localObject4 != null) {
          ((Collection)localObject1).add(localObject4);
        }
      }
      localObject2 = CollectionsKt.toList((Iterable)localObject1);
      localObject3 = ChainedMemberScope.Companion;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("package ");
      ((StringBuilder)localObject1).append(localFqName);
      ((StringBuilder)localObject1).append(" (");
      ((StringBuilder)localObject1).append(paramReflectKotlinClass);
      ((StringBuilder)localObject1).append(')');
      paramReflectKotlinClass = ((ChainedMemberScope.Companion)localObject3).create(((StringBuilder)localObject1).toString(), (List)localObject2);
      localObject1 = localConcurrentMap.putIfAbsent(localClassId, paramReflectKotlinClass);
      if (localObject1 != null) {
        paramReflectKotlinClass = (ReflectKotlinClass)localObject1;
      }
    }
    Intrinsics.checkExpressionValueIsNotNull(paramReflectKotlinClass, "cache.getOrPut(fileClass…ileClass)\", scopes)\n    }");
    return (MemberScope)paramReflectKotlinClass;
  }
}
