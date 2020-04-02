package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\b\022\004\022\0020\0020\001B\033\022\006\020\003\032\0020\002\022\f\020\004\032\b\022\004\022\0020\0060\005?\006\002\020\007J\023\020\b\032\0020\0062\b\020\t\032\004\030\0010\nH?\002J\b\020\013\032\0020\fH\026R\024\020\004\032\b\022\004\022\0020\0060\005X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/ResumeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/Job;", "job", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)V", "invoke", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ResumeOnCompletion
  extends JobNode<Job>
{
  private final Continuation<Unit> continuation;
  
  public ResumeOnCompletion(Job paramJob, Continuation<? super Unit> paramContinuation)
  {
    super(paramJob);
    this.continuation = paramContinuation;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    Continuation localContinuation = this.continuation;
    paramThrowable = Unit.INSTANCE;
    Result.Companion localCompanion = Result.Companion;
    localContinuation.resumeWith(Result.constructor-impl(paramThrowable));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ResumeOnCompletion[");
    localStringBuilder.append(this.continuation);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
