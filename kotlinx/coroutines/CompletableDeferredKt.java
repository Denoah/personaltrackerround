package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\032\037\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\002H\002?\006\002\020\004\032\036\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\n\b\002\020\005\032\004\030\0010\006\032.\020\007\032\0020\b\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\t\032\b\022\004\022\002H\0020\nH\007?\001\000?\006\002\020\013?\002\004\n\002\b\031?\006\f"}, d2={"CompletableDeferred", "Lkotlinx/coroutines/CompletableDeferred;", "T", "value", "(Ljava/lang/Object;)Lkotlinx/coroutines/CompletableDeferred;", "parent", "Lkotlinx/coroutines/Job;", "completeWith", "", "result", "Lkotlin/Result;", "(Lkotlinx/coroutines/CompletableDeferred;Ljava/lang/Object;)Z", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CompletableDeferredKt
{
  public static final <T> CompletableDeferred<T> CompletableDeferred(T paramT)
  {
    CompletableDeferredImpl localCompletableDeferredImpl = new CompletableDeferredImpl(null);
    localCompletableDeferredImpl.complete(paramT);
    return (CompletableDeferred)localCompletableDeferredImpl;
  }
  
  public static final <T> CompletableDeferred<T> CompletableDeferred(Job paramJob)
  {
    return (CompletableDeferred)new CompletableDeferredImpl(paramJob);
  }
  
  public static final <T> boolean completeWith(CompletableDeferred<T> paramCompletableDeferred, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCompletableDeferred, "$this$completeWith");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    boolean bool;
    if (localThrowable == null) {
      bool = paramCompletableDeferred.complete(paramObject);
    } else {
      bool = paramCompletableDeferred.completeExceptionally(localThrowable);
    }
    return bool;
  }
}
