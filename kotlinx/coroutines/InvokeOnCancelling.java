package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\004\n\002\030\002\b\002\030\0002\b\022\004\022\0020\0010\024B8\022\006\020\002\032\0020\001\022'\020\n\032#\022\025\022\023\030\0010\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\t?\006\004\b\013\020\fJ\032\020\r\032\0020\b2\b\020\007\032\004\030\0010\004H?\002?\006\004\b\r\020\016J\017\020\020\032\0020\017H\026?\006\004\b\020\020\021R7\020\n\032#\022\025\022\023\030\0010\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003j\002`\t8\002@\002X?\004?\006\006\n\004\b\n\020\022?\006\023"}, d2={"Lkotlinx/coroutines/InvokeOnCancelling;", "Lkotlinx/coroutines/Job;", "job", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "handler", "<init>", "(Lkotlinx/coroutines/Job;Lkotlin/jvm/functions/Function1;)V", "invoke", "(Ljava/lang/Throwable;)V", "", "toString", "()Ljava/lang/String;", "Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/JobCancellingNode;"}, k=1, mv={1, 1, 16})
final class InvokeOnCancelling
  extends JobCancellingNode<Job>
{
  private static final AtomicIntegerFieldUpdater _invoked$FU = AtomicIntegerFieldUpdater.newUpdater(InvokeOnCancelling.class, "_invoked");
  private volatile int _invoked;
  private final Function1<Throwable, Unit> handler;
  
  public InvokeOnCancelling(Job paramJob, Function1<? super Throwable, Unit> paramFunction1)
  {
    super(paramJob);
    this.handler = paramFunction1;
    this._invoked = 0;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (_invoked$FU.compareAndSet(this, 0, 1)) {
      this.handler.invoke(paramThrowable);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("InvokeOnCancelling[");
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
