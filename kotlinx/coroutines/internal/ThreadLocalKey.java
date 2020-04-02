package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b?\b\030\0002\f\022\b\022\006\022\002\b\0030\0020\001B\021\022\n\020\003\032\006\022\002\b\0030\004?\006\002\020\005J\r\020\006\032\006\022\002\b\0030\004H?\003J\027\020\007\032\0020\0002\f\b\002\020\003\032\006\022\002\b\0030\004H?\001J\023\020\b\032\0020\t2\b\020\n\032\004\030\0010\013H?\003J\t\020\f\032\0020\rH?\001J\t\020\016\032\0020\017H?\001R\022\020\003\032\006\022\002\b\0030\004X?\004?\006\002\n\000?\006\020"}, d2={"Lkotlinx/coroutines/internal/ThreadLocalKey;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/internal/ThreadLocalElement;", "threadLocal", "Ljava/lang/ThreadLocal;", "(Ljava/lang/ThreadLocal;)V", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ThreadLocalKey
  implements CoroutineContext.Key<ThreadLocalElement<?>>
{
  private final ThreadLocal<?> threadLocal;
  
  public ThreadLocalKey(ThreadLocal<?> paramThreadLocal)
  {
    this.threadLocal = paramThreadLocal;
  }
  
  private final ThreadLocal<?> component1()
  {
    return this.threadLocal;
  }
  
  public final ThreadLocalKey copy(ThreadLocal<?> paramThreadLocal)
  {
    Intrinsics.checkParameterIsNotNull(paramThreadLocal, "threadLocal");
    return new ThreadLocalKey(paramThreadLocal);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof ThreadLocalKey))
      {
        paramObject = (ThreadLocalKey)paramObject;
        if (Intrinsics.areEqual(this.threadLocal, paramObject.threadLocal)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    ThreadLocal localThreadLocal = this.threadLocal;
    int i;
    if (localThreadLocal != null) {
      i = localThreadLocal.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ThreadLocalKey(threadLocal=");
    localStringBuilder.append(this.threadLocal);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
