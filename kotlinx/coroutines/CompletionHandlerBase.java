package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\b \030\0002\0020\0012#\022\025\022\023\030\0010\003?\006\f\b\004\022\b\b\005\022\004\b\b(\006\022\004\022\0020\0070\002j\002`\bB\005?\006\002\020\tJ\023\020\n\032\0020\0072\b\020\006\032\004\030\0010\003H¦\002?\006\013"}, d2={"Lkotlinx/coroutines/CompletionHandlerBase;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "()V", "invoke", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class CompletionHandlerBase
  extends LockFreeLinkedListNode
  implements Function1<Throwable, Unit>
{
  public CompletionHandlerBase() {}
  
  public abstract void invoke(Throwable paramThrowable);
}
