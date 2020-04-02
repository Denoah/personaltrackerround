package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;
import kotlinx.coroutines.ThreadContextElement.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\003\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0028\000\022\f\020\004\032\b\022\004\022\0028\0000\005?\006\002\020\006J(\020\f\032\004\030\001H\r\"\b\b\001\020\r*\0020\0162\f\020\007\032\b\022\004\022\002H\r0\bH?\002?\006\002\020\017J\024\020\020\032\0020\0212\n\020\007\032\006\022\002\b\0030\bH\026J\035\020\022\032\0020\0232\006\020\024\032\0020\0212\006\020\025\032\0028\000H\026?\006\002\020\026J\b\020\027\032\0020\030H\026J\025\020\031\032\0028\0002\006\020\024\032\0020\021H\026?\006\002\020\032R\030\020\007\032\006\022\002\b\0030\bX?\004?\006\b\n\000\032\004\b\t\020\nR\024\020\004\032\b\022\004\022\0028\0000\005X?\004?\006\002\n\000R\020\020\003\032\0028\000X?\004?\006\004\n\002\020\013?\006\033"}, d2={"Lkotlinx/coroutines/internal/ThreadLocalElement;", "T", "Lkotlinx/coroutines/ThreadContextElement;", "value", "threadLocal", "Ljava/lang/ThreadLocal;", "(Ljava/lang/Object;Ljava/lang/ThreadLocal;)V", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "Ljava/lang/Object;", "get", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "Lkotlin/coroutines/CoroutineContext;", "restoreThreadContext", "", "context", "oldState", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "toString", "", "updateThreadContext", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ThreadLocalElement<T>
  implements ThreadContextElement<T>
{
  private final CoroutineContext.Key<?> key;
  private final ThreadLocal<T> threadLocal;
  private final T value;
  
  public ThreadLocalElement(T paramT, ThreadLocal<T> paramThreadLocal)
  {
    this.value = paramT;
    this.threadLocal = paramThreadLocal;
    this.key = ((CoroutineContext.Key)new ThreadLocalKey(paramThreadLocal));
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return ThreadContextElement.DefaultImpls.fold(this, paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if (Intrinsics.areEqual(getKey(), paramKey)) {
      paramKey = (CoroutineContext.Element)this;
    } else {
      paramKey = null;
    }
    return paramKey;
  }
  
  public CoroutineContext.Key<?> getKey()
  {
    return this.key;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if (Intrinsics.areEqual(getKey(), paramKey)) {
      paramKey = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
    } else {
      paramKey = (CoroutineContext)this;
    }
    return paramKey;
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return ThreadContextElement.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public void restoreThreadContext(CoroutineContext paramCoroutineContext, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    this.threadLocal.set(paramT);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ThreadLocal(value=");
    localStringBuilder.append(this.value);
    localStringBuilder.append(", threadLocal = ");
    localStringBuilder.append(this.threadLocal);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public T updateThreadContext(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    paramCoroutineContext = this.threadLocal.get();
    this.threadLocal.set(this.value);
    return paramCoroutineContext;
  }
}
