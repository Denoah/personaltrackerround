package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\n\n\000\n\002\030\002\n\002\b\003\032#\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\001H?\b?\006\004"}, d2={"probeCoroutineCreated", "Lkotlin/coroutines/Continuation;", "T", "completion", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ProbesSupportKt
{
  public static final <T> Continuation<T> probeCoroutineCreated(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    return DebugProbesKt.probeCoroutineCreated(paramContinuation);
  }
}
