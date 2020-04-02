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
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\032\030\020\t\032\0020\n2\006\020\013\032\0020\0072\b\b\002\020\f\032\0020\007\032)\020\r\032\002H\016\"\004\b\000\020\016*\0020\n2\f\020\017\032\b\022\004\022\002H\0160\020H?H?\001\000?\006\002\020\021\"\026\020\000\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0078\002X?\004?\006\b\n\000\022\004\b\b\020\003?\002\004\n\002\b\031?\006\022"}, d2={"CANCELLED", "Lkotlinx/coroutines/internal/Symbol;", "CANCELLED$annotations", "()V", "RESUMED", "RESUMED$annotations", "SEGMENT_SIZE", "", "SEGMENT_SIZE$annotations", "Semaphore", "Lkotlinx/coroutines/sync/Semaphore;", "permits", "acquiredPermits", "withPermit", "T", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Semaphore;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SemaphoreKt
{
  private static final Symbol CANCELLED = new Symbol("CANCELLED");
  private static final Symbol RESUMED = new Symbol("RESUMED");
  private static final int SEGMENT_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.semaphore.segmentSize", 16, 0, 0, 12, null);
  
  public static final Semaphore Semaphore(int paramInt1, int paramInt2)
  {
    return (Semaphore)new SemaphoreImpl(paramInt1, paramInt2);
  }
  
  public static final <T> Object withPermit(Semaphore paramSemaphore, Function0<? extends T> paramFunction0, Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof withPermit.1))
    {
      local1 = (withPermit.1)paramContinuation;
      if ((local1.label & 0x80000000) != 0)
      {
        local1.label += Integer.MIN_VALUE;
        break label45;
      }
    }
    ContinuationImpl local1 = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return SemaphoreKt.withPermit(null, null, this);
      }
    };
    label45:
    Object localObject1 = local1.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = local1.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramFunction0 = (Function0)local1.L$1;
        paramContinuation = (Semaphore)local1.L$0;
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
      local1.L$0 = paramSemaphore;
      local1.L$1 = paramFunction0;
      local1.label = 1;
      paramContinuation = paramSemaphore;
      if (paramSemaphore.acquire(local1) == localObject2) {
        return localObject2;
      }
    }
    try
    {
      paramSemaphore = paramFunction0.invoke();
      return paramSemaphore;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramContinuation.release();
      InlineMarker.finallyEnd(1);
    }
  }
  
  private static final Object withPermit$$forInline(Semaphore paramSemaphore, Function0 paramFunction0, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    paramSemaphore.acquire(paramContinuation);
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
      paramSemaphore.release();
      InlineMarker.finallyEnd(1);
    }
  }
}
