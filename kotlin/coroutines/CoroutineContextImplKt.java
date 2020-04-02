package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032+\020\000\032\004\030\001H\001\"\b\b\000\020\001*\0020\002*\0020\0022\f\020\003\032\b\022\004\022\002H\0010\004H\007?\006\002\020\005\032\030\020\006\032\0020\007*\0020\0022\n\020\003\032\006\022\002\b\0030\004H\007?\006\b"}, d2={"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class CoroutineContextImplKt
{
  public static final <E extends CoroutineContext.Element> E getPolymorphicElement(CoroutineContext.Element paramElement, CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramElement, "$this$getPolymorphicElement");
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    boolean bool = paramKey instanceof AbstractCoroutineContextKey;
    Object localObject = null;
    if (bool)
    {
      AbstractCoroutineContextKey localAbstractCoroutineContextKey = (AbstractCoroutineContextKey)paramKey;
      paramKey = localObject;
      if (localAbstractCoroutineContextKey.isSubKey$kotlin_stdlib(paramElement.getKey()))
      {
        paramKey = localAbstractCoroutineContextKey.tryCast$kotlin_stdlib(paramElement);
        if (!(paramKey instanceof CoroutineContext.Element)) {
          paramKey = localObject;
        }
      }
      return paramKey;
    }
    if (paramElement.getKey() != paramKey) {
      paramElement = null;
    }
    return paramElement;
  }
  
  public static final CoroutineContext minusPolymorphicKey(CoroutineContext.Element paramElement, CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramElement, "$this$minusPolymorphicKey");
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if ((paramKey instanceof AbstractCoroutineContextKey))
    {
      localObject = (AbstractCoroutineContextKey)paramKey;
      paramKey = paramElement;
      if (((AbstractCoroutineContextKey)localObject).isSubKey$kotlin_stdlib(paramElement.getKey()))
      {
        paramKey = paramElement;
        if (((AbstractCoroutineContextKey)localObject).tryCast$kotlin_stdlib(paramElement) != null) {
          paramKey = EmptyCoroutineContext.INSTANCE;
        }
      }
      return (CoroutineContext)paramKey;
    }
    Object localObject = paramElement;
    if (paramElement.getKey() == paramKey) {
      localObject = EmptyCoroutineContext.INSTANCE;
    }
    return (CoroutineContext)localObject;
  }
}
