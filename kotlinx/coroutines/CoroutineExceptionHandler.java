package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Element.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\002\b\002\bf\030\000 \b2\0020\001:\001\bJ\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\007H&?\006\t"}, d2={"Lkotlinx/coroutines/CoroutineExceptionHandler;", "Lkotlin/coroutines/CoroutineContext$Element;", "handleException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface CoroutineExceptionHandler
  extends CoroutineContext.Element
{
  public static final Key Key = Key.$$INSTANCE;
  
  public abstract void handleException(CoroutineContext paramCoroutineContext, Throwable paramThrowable);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(CoroutineExceptionHandler paramCoroutineExceptionHandler, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)paramCoroutineExceptionHandler, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(CoroutineExceptionHandler paramCoroutineExceptionHandler, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.get((CoroutineContext.Element)paramCoroutineExceptionHandler, paramKey);
    }
    
    public static CoroutineContext minusKey(CoroutineExceptionHandler paramCoroutineExceptionHandler, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)paramCoroutineExceptionHandler, paramKey);
    }
    
    public static CoroutineContext plus(CoroutineExceptionHandler paramCoroutineExceptionHandler, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)paramCoroutineExceptionHandler, paramCoroutineContext);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlinx/coroutines/CoroutineExceptionHandler$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<CoroutineExceptionHandler>
  {
    private Key() {}
  }
}
