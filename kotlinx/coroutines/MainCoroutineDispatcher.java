package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\030\002\n\002\b\005\b&\030\0002\0020\001B\005?\006\002\020\002R\022\020\003\032\0020\000X¦\004?\006\006\032\004\b\004\020\005?\006\006"}, d2={"Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class MainCoroutineDispatcher
  extends CoroutineDispatcher
{
  public MainCoroutineDispatcher() {}
  
  public abstract MainCoroutineDispatcher getImmediate();
}
