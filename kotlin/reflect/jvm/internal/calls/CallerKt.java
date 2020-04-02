package kotlin.reflect.jvm.internal.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\b\n\002\030\002\n\002\b\003\"\034\020\000\032\0020\001*\006\022\002\b\0030\0028@X?\004?\006\006\032\004\b\003\020\004?\006\005"}, d2={"arity", "", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getArity", "(Lkotlin/reflect/jvm/internal/calls/Caller;)I", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class CallerKt
{
  public static final int getArity(Caller<?> paramCaller)
  {
    Intrinsics.checkParameterIsNotNull(paramCaller, "$this$arity");
    return paramCaller.getParameterTypes().size();
  }
}
