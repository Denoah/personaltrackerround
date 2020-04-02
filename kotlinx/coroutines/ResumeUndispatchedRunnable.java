package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\003\b\002\030\0002\0060\001j\002`\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0020\0070\006?\006\002\020\bJ\b\020\t\032\0020\007H\026R\024\020\005\032\b\022\004\022\0020\0070\006X?\004?\006\002\n\000R\016\020\003\032\0020\004X?\004?\006\002\n\000?\006\n"}, d2={"Lkotlinx/coroutines/ResumeUndispatchedRunnable;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlinx/coroutines/CancellableContinuation;)V", "run", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ResumeUndispatchedRunnable
  implements Runnable
{
  private final CancellableContinuation<Unit> continuation;
  private final CoroutineDispatcher dispatcher;
  
  public ResumeUndispatchedRunnable(CoroutineDispatcher paramCoroutineDispatcher, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    this.dispatcher = paramCoroutineDispatcher;
    this.continuation = paramCancellableContinuation;
  }
  
  public void run()
  {
    this.continuation.resumeUndispatched(this.dispatcher, Unit.INSTANCE);
  }
}
