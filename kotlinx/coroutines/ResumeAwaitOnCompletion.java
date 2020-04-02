package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\002B\033\022\006\020\004\032\0020\003\022\f\020\005\032\b\022\004\022\0028\0000\006?\006\002\020\007J\023\020\b\032\0020\t2\b\020\n\032\004\030\0010\013H?\002J\b\020\f\032\0020\rH\026R\024\020\005\032\b\022\004\022\0028\0000\006X?\004?\006\002\n\000?\006\016"}, d2={"Lkotlinx/coroutines/ResumeAwaitOnCompletion;", "T", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/JobSupport;", "job", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ResumeAwaitOnCompletion<T>
  extends JobNode<JobSupport>
{
  private final CancellableContinuationImpl<T> continuation;
  
  public ResumeAwaitOnCompletion(JobSupport paramJobSupport, CancellableContinuationImpl<? super T> paramCancellableContinuationImpl)
  {
    super((Job)paramJobSupport);
    this.continuation = paramCancellableContinuationImpl;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    Object localObject1 = ((JobSupport)this.job).getState$kotlinx_coroutines_core();
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(localObject1 instanceof Incomplete ^ true))) {
      throw ((Throwable)new AssertionError());
    }
    Object localObject2;
    if ((localObject1 instanceof CompletedExceptionally))
    {
      paramThrowable = (Continuation)this.continuation;
      localObject2 = ((CompletedExceptionally)localObject1).cause;
      localObject1 = Result.Companion;
      paramThrowable.resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject2)));
    }
    else
    {
      paramThrowable = (Continuation)this.continuation;
      localObject2 = JobSupportKt.unboxState(localObject1);
      localObject1 = Result.Companion;
      paramThrowable.resumeWith(Result.constructor-impl(localObject2));
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ResumeAwaitOnCompletion[");
    localStringBuilder.append(this.continuation);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
