package kotlinx.coroutines.internal;

import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\016\n\000\bg\030\0002\0020\001J\026\020\006\032\0020\0072\f\020\b\032\b\022\004\022\0020\0000\tH&J\n\020\n\032\004\030\0010\013H\026R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\004\020\005?\006\f"}, d2={"Lkotlinx/coroutines/internal/MainDispatcherFactory;", "", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", "", "hintOnError", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface MainDispatcherFactory
{
  public abstract MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> paramList);
  
  public abstract int getLoadPriority();
  
  public abstract String hintOnError();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static String hintOnError(MainDispatcherFactory paramMainDispatcherFactory)
    {
      return null;
    }
  }
}
