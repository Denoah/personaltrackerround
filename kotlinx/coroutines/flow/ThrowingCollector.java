package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\020\000\n\000\n\002\020\003\n\002\b\002\n\002\020\002\n\002\b\003\b\002\030\0002\n\022\006\022\004\030\0010\0020\001B\r\022\006\020\003\032\0020\004?\006\002\020\005J\033\020\006\032\0020\0072\b\020\b\032\004\030\0010\002H?@?\001\000?\006\002\020\tR\016\020\003\032\0020\004X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\n"}, d2={"Lkotlinx/coroutines/flow/ThrowingCollector;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "e", "", "(Ljava/lang/Throwable;)V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ThrowingCollector
  implements FlowCollector<Object>
{
  private final Throwable e;
  
  public ThrowingCollector(Throwable paramThrowable)
  {
    this.e = paramThrowable;
  }
  
  public Object emit(Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    throw this.e;
  }
}
