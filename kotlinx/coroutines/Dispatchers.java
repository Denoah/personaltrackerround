package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\007\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002R\034\020\003\032\0020\0048\006X?\004?\006\016\n\000\022\004\b\005\020\002\032\004\b\006\020\007R\034\020\b\032\0020\0048\006X?\004?\006\016\n\000\022\004\b\t\020\002\032\004\b\n\020\007R\032\020\013\032\0020\f8FX?\004?\006\f\022\004\b\r\020\002\032\004\b\016\020\017R\034\020\020\032\0020\0048\006X?\004?\006\016\n\000\022\004\b\021\020\002\032\004\b\022\020\007?\006\023"}, d2={"Lkotlinx/coroutines/Dispatchers;", "", "()V", "Default", "Lkotlinx/coroutines/CoroutineDispatcher;", "Default$annotations", "getDefault", "()Lkotlinx/coroutines/CoroutineDispatcher;", "IO", "IO$annotations", "getIO", "Main", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Main$annotations", "getMain", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "Unconfined", "Unconfined$annotations", "getUnconfined", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class Dispatchers
{
  private static final CoroutineDispatcher Default;
  public static final Dispatchers INSTANCE = new Dispatchers();
  private static final CoroutineDispatcher IO = DefaultScheduler.INSTANCE.getIO();
  private static final CoroutineDispatcher Unconfined;
  
  static
  {
    Default = CoroutineContextKt.createDefaultDispatcher();
    Unconfined = (CoroutineDispatcher)Unconfined.INSTANCE;
  }
  
  private Dispatchers() {}
  
  public static final CoroutineDispatcher getDefault()
  {
    return Default;
  }
  
  public static final CoroutineDispatcher getIO()
  {
    return IO;
  }
  
  public static final MainCoroutineDispatcher getMain()
  {
    return MainDispatcherLoader.dispatcher;
  }
  
  public static final CoroutineDispatcher getUnconfined()
  {
    return Unconfined;
  }
}
