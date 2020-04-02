package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\003\b \030\0002#\022\025\022\023\030\0010\002?\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\007B\005?\006\002\020\bJ\023\020\t\032\0020\0062\b\020\005\032\004\030\0010\002H¦\002?\006\n"}, d2={"Lkotlinx/coroutines/CancelHandlerBase;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "()V", "invoke", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class CancelHandlerBase
  implements Function1<Throwable, Unit>
{
  public CancelHandlerBase() {}
  
  public abstract void invoke(Throwable paramThrowable);
}
