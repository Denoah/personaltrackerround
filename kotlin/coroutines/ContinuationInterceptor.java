package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\002\bg\030\000 \0172\0020\001:\001\017J(\020\002\032\004\030\001H\003\"\b\b\000\020\003*\0020\0012\f\020\004\032\b\022\004\022\002H\0030\005H?\002?\006\002\020\006J\"\020\007\032\b\022\004\022\002H\t0\b\"\004\b\000\020\t2\f\020\n\032\b\022\004\022\002H\t0\bH&J\024\020\013\032\0020\f2\n\020\004\032\006\022\002\b\0030\005H\026J\024\020\r\032\0020\0162\n\020\n\032\006\022\002\b\0030\bH\026?\006\020"}, d2={"Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/CoroutineContext$Element;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "minusKey", "Lkotlin/coroutines/CoroutineContext;", "releaseInterceptedContinuation", "", "Key", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract interface ContinuationInterceptor
  extends CoroutineContext.Element
{
  public static final Key Key = Key.$$INSTANCE;
  
  public abstract <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey);
  
  public abstract <T> Continuation<T> interceptContinuation(Continuation<? super T> paramContinuation);
  
  public abstract CoroutineContext minusKey(CoroutineContext.Key<?> paramKey);
  
  public abstract void releaseInterceptedContinuation(Continuation<?> paramContinuation);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(ContinuationInterceptor paramContinuationInterceptor, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)paramContinuationInterceptor, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      boolean bool = paramKey instanceof AbstractCoroutineContextKey;
      AbstractCoroutineContextKey localAbstractCoroutineContextKey = null;
      Object localObject = null;
      if (bool)
      {
        localAbstractCoroutineContextKey = (AbstractCoroutineContextKey)paramKey;
        paramKey = (CoroutineContext.Key<E>)localObject;
        if (localAbstractCoroutineContextKey.isSubKey$kotlin_stdlib(paramContinuationInterceptor.getKey()))
        {
          paramKey = localAbstractCoroutineContextKey.tryCast$kotlin_stdlib((CoroutineContext.Element)paramContinuationInterceptor);
          if (!(paramKey instanceof CoroutineContext.Element)) {
            paramKey = (CoroutineContext.Key<E>)localObject;
          }
        }
        return paramKey;
      }
      localObject = localAbstractCoroutineContextKey;
      if (ContinuationInterceptor.Key == paramKey) {
        if (paramContinuationInterceptor != null) {
          localObject = (CoroutineContext.Element)paramContinuationInterceptor;
        } else {
          throw new TypeCastException("null cannot be cast to non-null type E");
        }
      }
      return localObject;
    }
    
    public static CoroutineContext minusKey(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      if ((paramKey instanceof AbstractCoroutineContextKey))
      {
        AbstractCoroutineContextKey localAbstractCoroutineContextKey = (AbstractCoroutineContextKey)paramKey;
        paramKey = paramContinuationInterceptor;
        if (localAbstractCoroutineContextKey.isSubKey$kotlin_stdlib(paramContinuationInterceptor.getKey()))
        {
          paramKey = paramContinuationInterceptor;
          if (localAbstractCoroutineContextKey.tryCast$kotlin_stdlib((CoroutineContext.Element)paramContinuationInterceptor) != null) {
            paramKey = EmptyCoroutineContext.INSTANCE;
          }
        }
        return (CoroutineContext)paramKey;
      }
      if (ContinuationInterceptor.Key == paramKey) {
        paramContinuationInterceptor = EmptyCoroutineContext.INSTANCE;
      }
      return (CoroutineContext)paramContinuationInterceptor;
    }
    
    public static CoroutineContext plus(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)paramContinuationInterceptor, paramCoroutineContext);
    }
    
    public static void releaseInterceptedContinuation(ContinuationInterceptor paramContinuationInterceptor, Continuation<?> paramContinuation)
    {
      Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlin/coroutines/ContinuationInterceptor$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<ContinuationInterceptor>
  {
    private Key() {}
  }
}
