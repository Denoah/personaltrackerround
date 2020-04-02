package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002R\024\020\003\032\0020\0048VX?\004?\006\006\032\004\b\005\020\006?\006\007"}, d2={"Lkotlinx/coroutines/GlobalScope;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class GlobalScope
  implements CoroutineScope
{
  public static final GlobalScope INSTANCE = new GlobalScope();
  
  private GlobalScope() {}
  
  public CoroutineContext getCoroutineContext()
  {
    return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  }
}
