package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\002B<\022\006\020\004\032\0020\003\022\f\020\005\032\b\022\004\022\0028\0000\006\022\034\020\007\032\030\b\001\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\n0\b?\001\000?\006\002\020\013J\023\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H?\002J\b\020\021\032\0020\022H\026R)\020\007\032\030\b\001\022\n\022\b\022\004\022\0028\0000\t\022\006\022\004\030\0010\n0\bX?\004?\001\000?\006\004\n\002\020\fR\024\020\005\032\b\022\004\022\0028\0000\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\023"}, d2={"Lkotlinx/coroutines/SelectJoinOnCompletion;", "R", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/JobSupport;", "job", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/jvm/functions/Function1;", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class SelectJoinOnCompletion<R>
  extends JobNode<JobSupport>
{
  private final Function1<Continuation<? super R>, Object> block;
  private final SelectInstance<R> select;
  
  public SelectJoinOnCompletion(JobSupport paramJobSupport, SelectInstance<? super R> paramSelectInstance, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1)
  {
    super((Job)paramJobSupport);
    this.select = paramSelectInstance;
    this.block = paramFunction1;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (this.select.trySelect()) {
      CancellableKt.startCoroutineCancellable(this.block, this.select.getCompletion());
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SelectJoinOnCompletion[");
    localStringBuilder.append(this.select);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
