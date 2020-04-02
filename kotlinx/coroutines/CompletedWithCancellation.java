package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\020\016\n\000\b\002\030\0002\0020\001B2\022\b\020\002\032\004\030\0010\001\022!\020\003\032\035\022\023\022\0210\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\004?\006\002\020\nJ\b\020\013\032\0020\fH\026R+\020\003\032\035\022\023\022\0210\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\0048\006X?\004?\006\002\n\000R\022\020\002\032\004\030\0010\0018\006X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/CompletedWithCancellation;", "", "result", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CompletedWithCancellation
{
  public final Function1<Throwable, Unit> onCancellation;
  public final Object result;
  
  public CompletedWithCancellation(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    this.result = paramObject;
    this.onCancellation = paramFunction1;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CompletedWithCancellation[");
    localStringBuilder.append(this.result);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
