package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\n\002\020\016\n\000\b\002\030\0002\0020\001B.\022'\020\002\032#\022\025\022\023\030\0010\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\t?\006\002\020\nJ\023\020\013\032\0020\b2\b\020\007\032\004\030\0010\004H?\002J\b\020\f\032\0020\rH\026R/\020\002\032#\022\025\022\023\030\0010\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\tX?\004?\006\002\n\000?\006\016"}, d2={"Lkotlinx/coroutines/InvokeOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "handler", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlin/jvm/functions/Function1;)V", "invoke", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class InvokeOnCancel
  extends CancelHandler
{
  private final Function1<Throwable, Unit> handler;
  
  public InvokeOnCancel(Function1<? super Throwable, Unit> paramFunction1)
  {
    this.handler = paramFunction1;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handler.invoke(paramThrowable);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("InvokeOnCancel[");
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this.handler));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
