package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\004\n\002\020\016\n\000\b\002\030\0002\0020\001B\031\022\b\020\002\032\004\030\0010\001\022\b\020\003\032\004\030\0010\001?\006\002\020\004J\b\020\005\032\0020\006H\026R\022\020\002\032\004\030\0010\0018\006X?\004?\006\002\n\000R\022\020\003\032\004\030\0010\0018\006X?\004?\006\002\n\000?\006\007"}, d2={"Lkotlinx/coroutines/CompletedIdempotentResult;", "", "idempotentResume", "result", "(Ljava/lang/Object;Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CompletedIdempotentResult
{
  public final Object idempotentResume;
  public final Object result;
  
  public CompletedIdempotentResult(Object paramObject1, Object paramObject2)
  {
    this.idempotentResume = paramObject1;
    this.result = paramObject2;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CompletedIdempotentResult[");
    localStringBuilder.append(this.result);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
