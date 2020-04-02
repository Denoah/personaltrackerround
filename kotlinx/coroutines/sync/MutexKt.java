package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\f\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\002\b\002\032\020\020\023\032\0020\0242\b\b\002\020\025\032\0020\026\0325\020\027\032\002H\030\"\004\b\000\020\030*\0020\0242\n\b\002\020\031\032\004\030\0010\0322\f\020\033\032\b\022\004\022\002H\0300\034H?H?\001\000?\006\002\020\035\"\026\020\000\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\b\020\003\"\026\020\t\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\n\020\003\"\026\020\013\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\f\020\003\"\026\020\r\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\016\020\003\"\026\020\017\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\020\020\003\"\026\020\021\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\022\020\003?\002\004\n\002\b\031?\006\036"}, d2={"EMPTY_LOCKED", "Lkotlinx/coroutines/sync/Empty;", "EMPTY_LOCKED$annotations", "()V", "EMPTY_UNLOCKED", "EMPTY_UNLOCKED$annotations", "ENQUEUE_FAIL", "Lkotlinx/coroutines/internal/Symbol;", "ENQUEUE_FAIL$annotations", "LOCKED", "LOCKED$annotations", "LOCK_FAIL", "LOCK_FAIL$annotations", "SELECT_SUCCESS", "SELECT_SUCCESS$annotations", "UNLOCKED", "UNLOCKED$annotations", "UNLOCK_FAIL", "UNLOCK_FAIL$annotations", "Mutex", "Lkotlinx/coroutines/sync/Mutex;", "locked", "", "withLock", "T", "owner", "", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Mutex;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class MutexKt
{
  private static final Empty EMPTY_LOCKED = new Empty(LOCKED);
  private static final Empty EMPTY_UNLOCKED = new Empty(UNLOCKED);
  private static final Symbol ENQUEUE_FAIL;
  private static final Symbol LOCKED;
  private static final Symbol LOCK_FAIL = new Symbol("LOCK_FAIL");
  private static final Symbol SELECT_SUCCESS;
  private static final Symbol UNLOCKED;
  private static final Symbol UNLOCK_FAIL;
  
  static
  {
    ENQUEUE_FAIL = new Symbol("ENQUEUE_FAIL");
    UNLOCK_FAIL = new Symbol("UNLOCK_FAIL");
    SELECT_SUCCESS = new Symbol("SELECT_SUCCESS");
    LOCKED = new Symbol("LOCKED");
    UNLOCKED = new Symbol("UNLOCKED");
  }
  
  public static final Mutex Mutex(boolean paramBoolean)
  {
    return (Mutex)new MutexImpl(paramBoolean);
  }
  
  public static final <T> Object withLock(Mutex paramMutex, Object paramObject, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof withLock.1))
    {
      local1 = (withLock.1)paramContinuation;
      if ((local1.label & 0x80000000) != 0)
      {
        local1.label += Integer.MIN_VALUE;
        break label50;
      }
    }
    ContinuationImpl local1 = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return MutexKt.withLock(null, null, null, this);
      }
    };
    label50:
    Object localObject1 = local1.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = local1.label;
    Object localObject3;
    if (i != 0)
    {
      if (i == 1)
      {
        paramFunction0 = (Function0)local1.L$2;
        localObject3 = local1.L$1;
        paramContinuation = (Mutex)local1.L$0;
        ResultKt.throwOnFailure(localObject1);
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      local1.L$0 = paramMutex;
      local1.L$1 = paramObject;
      local1.L$2 = paramFunction0;
      local1.label = 1;
      paramContinuation = paramMutex;
      localObject3 = paramObject;
      if (paramMutex.lock(paramObject, local1) == localObject2) {
        return localObject2;
      }
    }
    try
    {
      paramMutex = paramFunction0.invoke();
      return paramMutex;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramContinuation.unlock(localObject3);
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final Object withLock$$forInline(Mutex paramMutex, Object paramObject, Function0 paramFunction0, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    paramMutex.lock(paramObject, paramContinuation);
    InlineMarker.mark(2);
    InlineMarker.mark(1);
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramMutex.unlock(paramObject);
      InlineMarker.finallyEnd(1);
    }
  }
}
