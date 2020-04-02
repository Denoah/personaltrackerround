package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ThreadLocalElement;
import kotlinx.coroutines.internal.ThreadLocalKey;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\013\n\000\032+\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\b\b\002\020\004\032\002H\002?\006\002\020\005\032\031\020\006\032\0020\007*\006\022\002\b\0030\003H?H?\001\000?\006\002\020\b\032\031\020\t\032\0020\n*\006\022\002\b\0030\003H?H?\001\000?\006\002\020\b?\002\004\n\002\b\031?\006\013"}, d2={"asContextElement", "Lkotlinx/coroutines/ThreadContextElement;", "T", "Ljava/lang/ThreadLocal;", "value", "(Ljava/lang/ThreadLocal;Ljava/lang/Object;)Lkotlinx/coroutines/ThreadContextElement;", "ensurePresent", "", "(Ljava/lang/ThreadLocal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isPresent", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ThreadContextElementKt
{
  public static final <T> ThreadContextElement<T> asContextElement(ThreadLocal<T> paramThreadLocal, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramThreadLocal, "$this$asContextElement");
    return (ThreadContextElement)new ThreadLocalElement(paramT, paramThreadLocal);
  }
  
  public static final Object ensurePresent(ThreadLocal<?> paramThreadLocal, Continuation<? super Unit> paramContinuation)
  {
    boolean bool;
    if (paramContinuation.getContext().get((CoroutineContext.Key)new ThreadLocalKey(paramThreadLocal)) != null) {
      bool = true;
    } else {
      bool = false;
    }
    if (Boxing.boxBoolean(bool).booleanValue()) {
      return Unit.INSTANCE;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ThreadLocal ");
    localStringBuilder.append(paramThreadLocal);
    localStringBuilder.append(" is missing from context ");
    localStringBuilder.append(paramContinuation.getContext());
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  private static final Object ensurePresent$$forInline(ThreadLocal paramThreadLocal, Continuation paramContinuation)
  {
    InlineMarker.mark(3);
    throw new NullPointerException();
  }
  
  public static final Object isPresent(ThreadLocal<?> paramThreadLocal, Continuation<? super Boolean> paramContinuation)
  {
    boolean bool;
    if (paramContinuation.getContext().get((CoroutineContext.Key)new ThreadLocalKey(paramThreadLocal)) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return Boxing.boxBoolean(bool);
  }
  
  private static final Object isPresent$$forInline(ThreadLocal paramThreadLocal, Continuation paramContinuation)
  {
    InlineMarker.mark(3);
    throw new NullPointerException();
  }
}
