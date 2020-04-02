package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.pcollections.HashPMap;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\030\002\n\002\020\016\n\000\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\b\020\005\032\0020\006H\000\032&\020\007\032\b\022\004\022\002H\t0\b\"\b\b\000\020\t*\0020\0042\f\020\n\032\b\022\004\022\002H\t0\013H\000\"*\020\000\032\036\022\f\022\n \003*\004\030\0010\0020\002\022\f\022\n \003*\004\030\0010\0040\0040\001X?\016?\006\002\n\000?\006\f"}, d2={"K_CLASS_CACHE", "Lkotlin/reflect/jvm/internal/pcollections/HashPMap;", "", "kotlin.jvm.PlatformType", "", "clearKClassCache", "", "getOrCreateKotlinClass", "Lkotlin/reflect/jvm/internal/KClassImpl;", "T", "jClass", "Ljava/lang/Class;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KClassCacheKt
{
  private static HashPMap<String, Object> K_CLASS_CACHE;
  
  static
  {
    HashPMap localHashPMap = HashPMap.empty();
    Intrinsics.checkExpressionValueIsNotNull(localHashPMap, "HashPMap.empty<String, Any>()");
    K_CLASS_CACHE = localHashPMap;
  }
  
  public static final void clearKClassCache()
  {
    HashPMap localHashPMap = HashPMap.empty();
    Intrinsics.checkExpressionValueIsNotNull(localHashPMap, "HashPMap.empty()");
    K_CLASS_CACHE = localHashPMap;
  }
  
  public static final <T> KClassImpl<T> getOrCreateKotlinClass(Class<T> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "jClass");
    String str = paramClass.getName();
    Object localObject1 = K_CLASS_CACHE.get(str);
    boolean bool = localObject1 instanceof WeakReference;
    Object localObject2 = null;
    if (bool)
    {
      localObject1 = (KClassImpl)((WeakReference)localObject1).get();
      if (localObject1 != null) {
        localObject2 = ((KClassImpl)localObject1).getJClass();
      }
      if (Intrinsics.areEqual(localObject2, paramClass)) {
        return localObject1;
      }
    }
    else if (localObject1 != null)
    {
      WeakReference[] arrayOfWeakReference = (WeakReference[])localObject1;
      int i = arrayOfWeakReference.length;
      for (int j = 0; j < i; j++)
      {
        KClassImpl localKClassImpl = (KClassImpl)arrayOfWeakReference[j].get();
        if (localKClassImpl != null) {
          localObject2 = localKClassImpl.getJClass();
        } else {
          localObject2 = null;
        }
        if (Intrinsics.areEqual(localObject2, paramClass)) {
          return localKClassImpl;
        }
      }
      j = ((Object[])localObject1).length;
      localObject2 = new WeakReference[j + 1];
      System.arraycopy(localObject1, 0, localObject2, 0, j);
      paramClass = new KClassImpl(paramClass);
      localObject2[j] = new WeakReference(paramClass);
      localObject2 = K_CLASS_CACHE.plus(str, localObject2);
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "K_CLASS_CACHE.plus(name, newArray)");
      K_CLASS_CACHE = (HashPMap)localObject2;
      return paramClass;
    }
    paramClass = new KClassImpl(paramClass);
    localObject2 = K_CLASS_CACHE.plus(str, new WeakReference(paramClass));
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "K_CLASS_CACHE.plus(name, WeakReference(newKClass))");
    K_CLASS_CACHE = (HashPMap)localObject2;
    return paramClass;
  }
}
