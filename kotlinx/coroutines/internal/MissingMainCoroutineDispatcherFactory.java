package kotlinx.coroutines.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\026\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0020\0010\nH\026R\024\020\003\032\0020\0048VX?\004?\006\006\032\004\b\005\020\006?\006\013"}, d2={"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcherFactory;", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "()V", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class MissingMainCoroutineDispatcherFactory
  implements MainDispatcherFactory
{
  public static final MissingMainCoroutineDispatcherFactory INSTANCE = new MissingMainCoroutineDispatcherFactory();
  
  private MissingMainCoroutineDispatcherFactory() {}
  
  public MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "allFactories");
    return (MainCoroutineDispatcher)new MissingMainCoroutineDispatcher(null, null, 2, null);
  }
  
  public int getLoadPriority()
  {
    return -1;
  }
  
  public String hintOnError()
  {
    return MainDispatcherFactory.DefaultImpls.hintOnError(this);
  }
}
