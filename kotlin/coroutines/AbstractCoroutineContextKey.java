package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\006\b'\030\000*\b\b\000\020\001*\0020\002*\b\b\001\020\003*\002H\0012\b\022\004\022\002H\0030\004B8\022\f\020\005\032\b\022\004\022\0028\0000\004\022#\020\006\032\037\022\023\022\0210\002?\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\006\022\004\030\0018\0010\007?\006\002\020\013J\031\020\r\032\0020\0162\n\020\017\032\006\022\002\b\0030\004H\000?\006\002\b\020J\031\020\021\032\004\030\0018\0012\006\020\n\032\0020\002H\000?\006\004\b\022\020\023R+\020\006\032\037\022\023\022\0210\002?\006\f\b\b\022\b\b\t\022\004\b\b(\n\022\006\022\004\030\0018\0010\007X?\004?\006\002\n\000R\022\020\f\032\006\022\002\b\0030\004X?\004?\006\002\n\000?\006\024"}, d2={"Lkotlin/coroutines/AbstractCoroutineContextKey;", "B", "Lkotlin/coroutines/CoroutineContext$Element;", "E", "Lkotlin/coroutines/CoroutineContext$Key;", "baseKey", "safeCast", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "element", "(Lkotlin/coroutines/CoroutineContext$Key;Lkotlin/jvm/functions/Function1;)V", "topmostKey", "isSubKey", "", "key", "isSubKey$kotlin_stdlib", "tryCast", "tryCast$kotlin_stdlib", "(Lkotlin/coroutines/CoroutineContext$Element;)Lkotlin/coroutines/CoroutineContext$Element;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class AbstractCoroutineContextKey<B extends CoroutineContext.Element, E extends B>
  implements CoroutineContext.Key<E>
{
  private final Function1<CoroutineContext.Element, E> safeCast;
  private final CoroutineContext.Key<?> topmostKey;
  
  public AbstractCoroutineContextKey(CoroutineContext.Key<B> paramKey, Function1<? super CoroutineContext.Element, ? extends E> paramFunction1)
  {
    this.safeCast = paramFunction1;
    paramFunction1 = paramKey;
    if ((paramKey instanceof AbstractCoroutineContextKey)) {
      paramFunction1 = ((AbstractCoroutineContextKey)paramKey).topmostKey;
    }
    this.topmostKey = paramFunction1;
  }
  
  public final boolean isSubKey$kotlin_stdlib(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    boolean bool;
    if ((paramKey != (AbstractCoroutineContextKey)this) && (this.topmostKey != paramKey)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public final E tryCast$kotlin_stdlib(CoroutineContext.Element paramElement)
  {
    Intrinsics.checkParameterIsNotNull(paramElement, "element");
    return (CoroutineContext.Element)this.safeCast.invoke(paramElement);
  }
}
