package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\032\b\020\005\032\0020\006H\000\032\020\020\007\032\0020\004*\006\022\002\b\0030\bH\000\" \020\000\032\024\022\004\022\0020\002\022\n\022\b\022\004\022\0020\0040\0030\001X?\004?\006\002\n\000?\006\t"}, d2={"moduleByClassLoader", "Ljava/util/concurrent/ConcurrentMap;", "Lkotlin/reflect/jvm/internal/WeakClassLoaderBox;", "Ljava/lang/ref/WeakReference;", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/RuntimeModuleData;", "clearModuleByClassLoaderCache", "", "getOrCreateModule", "Ljava/lang/Class;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class ModuleByClassLoaderKt
{
  private static final ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> moduleByClassLoader = (ConcurrentMap)new ConcurrentHashMap();
  
  public static final void clearModuleByClassLoaderCache()
  {
    moduleByClassLoader.clear();
  }
  
  public static final RuntimeModuleData getOrCreateModule(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$getOrCreateModule");
    Object localObject1 = ReflectClassUtilKt.getSafeClassLoader(paramClass);
    paramClass = new WeakClassLoaderBox((ClassLoader)localObject1);
    Object localObject3 = (WeakReference)moduleByClassLoader.get(paramClass);
    Object localObject4;
    if (localObject3 != null)
    {
      localObject4 = (RuntimeModuleData)((WeakReference)localObject3).get();
      if (localObject4 != null)
      {
        Intrinsics.checkExpressionValueIsNotNull(localObject4, "it");
        return localObject4;
      }
      moduleByClassLoader.remove(paramClass, localObject3);
    }
    localObject1 = RuntimeModuleData.Companion.create((ClassLoader)localObject1);
    try
    {
      for (;;)
      {
        localObject3 = moduleByClassLoader;
        localObject4 = new java/lang/ref/WeakReference;
        ((WeakReference)localObject4).<init>(localObject1);
        localObject3 = (WeakReference)((ConcurrentMap)localObject3).putIfAbsent(paramClass, localObject4);
        if (localObject3 == null) {
          break;
        }
        localObject4 = (RuntimeModuleData)((WeakReference)localObject3).get();
        if (localObject4 != null) {
          return localObject4;
        }
        moduleByClassLoader.remove(paramClass, localObject3);
      }
      return localObject1;
    }
    finally
    {
      paramClass.setTemporaryStrongRef((ClassLoader)null);
    }
  }
}
