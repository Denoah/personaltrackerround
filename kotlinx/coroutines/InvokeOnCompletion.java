package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\n\002\020\016\n\000\b\002\030\0002\b\022\004\022\0020\0020\001B6\022\006\020\003\032\0020\002\022'\020\004\032#\022\025\022\023\030\0010\006?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\004\022\0020\n0\005j\002`\013?\006\002\020\fJ\023\020\r\032\0020\n2\b\020\t\032\004\030\0010\006H?\002J\b\020\016\032\0020\017H\026R/\020\004\032#\022\025\022\023\030\0010\006?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\004\022\0020\n0\005j\002`\013X?\004?\006\002\n\000?\006\020"}, d2={"Lkotlinx/coroutines/InvokeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/Job;", "job", "handler", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlinx/coroutines/Job;Lkotlin/jvm/functions/Function1;)V", "invoke", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class InvokeOnCompletion
  extends JobNode<Job>
{
  private final Function1<Throwable, Unit> handler;
  
  public InvokeOnCompletion(Job paramJob, Function1<? super Throwable, Unit> paramFunction1)
  {
    super(paramJob);
    this.handler = paramFunction1;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handler.invoke(paramThrowable);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("InvokeOnCompletion[");
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
