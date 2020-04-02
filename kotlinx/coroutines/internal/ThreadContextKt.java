package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\005\032\032\020\013\032\0020\f2\006\020\r\032\0020\0162\b\020\017\032\004\030\0010\004H\000\032\020\020\020\032\0020\0042\006\020\r\032\0020\016H\000\032\034\020\021\032\004\030\0010\0042\006\020\r\032\0020\0162\b\020\022\032\004\030\0010\004H\000\"\016\020\000\032\0020\001X?\004?\006\002\n\000\"$\020\002\032\030\022\006\022\004\030\0010\004\022\004\022\0020\005\022\006\022\004\030\0010\0040\003X?\004?\006\002\n\000\",\020\006\032 \022\n\022\b\022\002\b\003\030\0010\007\022\004\022\0020\005\022\n\022\b\022\002\b\003\030\0010\0070\003X?\004?\006\002\n\000\" \020\b\032\024\022\004\022\0020\t\022\004\022\0020\005\022\004\022\0020\t0\003X?\004?\006\002\n\000\" \020\n\032\024\022\004\022\0020\t\022\004\022\0020\005\022\004\022\0020\t0\003X?\004?\006\002\n\000?\006\023"}, d2={"ZERO", "Lkotlinx/coroutines/internal/Symbol;", "countAll", "Lkotlin/Function2;", "", "Lkotlin/coroutines/CoroutineContext$Element;", "findOne", "Lkotlinx/coroutines/ThreadContextElement;", "restoreState", "Lkotlinx/coroutines/internal/ThreadState;", "updateState", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "threadContextElements", "updateThreadContext", "countOrElement", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ThreadContextKt
{
  private static final Symbol ZERO = new Symbol("ZERO");
  private static final Function2<Object, CoroutineContext.Element, Object> countAll = (Function2)countAll.1.INSTANCE;
  private static final Function2<ThreadContextElement<?>, CoroutineContext.Element, ThreadContextElement<?>> findOne = (Function2)findOne.1.INSTANCE;
  private static final Function2<ThreadState, CoroutineContext.Element, ThreadState> restoreState = (Function2)restoreState.1.INSTANCE;
  private static final Function2<ThreadState, CoroutineContext.Element, ThreadState> updateState = (Function2)updateState.1.INSTANCE;
  
  public static final void restoreThreadContext(CoroutineContext paramCoroutineContext, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    if (paramObject == ZERO) {
      return;
    }
    if ((paramObject instanceof ThreadState))
    {
      ((ThreadState)paramObject).start();
      paramCoroutineContext.fold(paramObject, restoreState);
    }
    else
    {
      Object localObject = paramCoroutineContext.fold(null, findOne);
      if (localObject == null) {
        break label69;
      }
      ((ThreadContextElement)localObject).restoreThreadContext(paramCoroutineContext, paramObject);
    }
    return;
    label69:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
  }
  
  public static final Object threadContextElements(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    paramCoroutineContext = paramCoroutineContext.fold(Integer.valueOf(0), countAll);
    if (paramCoroutineContext == null) {
      Intrinsics.throwNpe();
    }
    return paramCoroutineContext;
  }
  
  public static final Object updateThreadContext(CoroutineContext paramCoroutineContext, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    if (paramObject == null) {
      paramObject = threadContextElements(paramCoroutineContext);
    }
    if (paramObject == Integer.valueOf(0))
    {
      paramCoroutineContext = ZERO;
    }
    else if ((paramObject instanceof Integer))
    {
      paramCoroutineContext = paramCoroutineContext.fold(new ThreadState(paramCoroutineContext, ((Number)paramObject).intValue()), updateState);
    }
    else
    {
      if (paramObject == null) {
        break label85;
      }
      paramCoroutineContext = ((ThreadContextElement)paramObject).updateThreadContext(paramCoroutineContext);
    }
    return paramCoroutineContext;
    label85:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
  }
}
