package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\000*\004\b\000\020\001*\004\b\001\020\0022\b\022\004\022\0020\0040\003BB\022\006\020\005\032\0020\004\022\f\020\006\032\b\022\004\022\0028\0010\007\022\"\020\b\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\n\022\006\022\004\030\0010\0130\t?\001\000?\006\002\020\fJ\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\021H?\002J\b\020\022\032\0020\023H\026R/\020\b\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0010\n\022\006\022\004\030\0010\0130\tX?\004?\001\000?\006\004\n\002\020\rR\024\020\006\032\b\022\004\022\0028\0010\007X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\024"}, d2={"Lkotlinx/coroutines/SelectAwaitOnCompletion;", "T", "R", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/JobSupport;", "job", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class SelectAwaitOnCompletion<T, R>
  extends JobNode<JobSupport>
{
  private final Function2<T, Continuation<? super R>, Object> block;
  private final SelectInstance<R> select;
  
  public SelectAwaitOnCompletion(JobSupport paramJobSupport, SelectInstance<? super R> paramSelectInstance, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    super((Job)paramJobSupport);
    this.select = paramSelectInstance;
    this.block = paramFunction2;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (this.select.trySelect()) {
      ((JobSupport)this.job).selectAwaitCompletion$kotlinx_coroutines_core(this.select, this.block);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SelectAwaitOnCompletion[");
    localStringBuilder.append(this.select);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
