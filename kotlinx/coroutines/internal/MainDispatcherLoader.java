package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\007\032\0020\006H\002R\016\020\003\032\0020\004X?\004?\006\002\n\000R\020\020\005\032\0020\0068\006X?\004?\006\002\n\000?\006\b"}, d2={"Lkotlinx/coroutines/internal/MainDispatcherLoader;", "", "()V", "FAST_SERVICE_LOADER_ENABLED", "", "dispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "loadMainDispatcher", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class MainDispatcherLoader
{
  private static final boolean FAST_SERVICE_LOADER_ENABLED;
  public static final MainDispatcherLoader INSTANCE;
  public static final MainCoroutineDispatcher dispatcher;
  
  static
  {
    MainDispatcherLoader localMainDispatcherLoader = new MainDispatcherLoader();
    INSTANCE = localMainDispatcherLoader;
    FAST_SERVICE_LOADER_ENABLED = SystemPropsKt.systemProp("kotlinx.coroutines.fast.service.loader", true);
    dispatcher = localMainDispatcherLoader.loadMainDispatcher();
  }
  
  private MainDispatcherLoader() {}
  
  private final MainCoroutineDispatcher loadMainDispatcher()
  {
    MainCoroutineDispatcher localMainCoroutineDispatcher;
    try
    {
      List localList;
      if (FAST_SERVICE_LOADER_ENABLED)
      {
        localList = FastServiceLoader.INSTANCE.loadMainDispatcherFactory$kotlinx_coroutines_core();
      }
      else
      {
        localObject1 = ServiceLoader.load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader()).iterator();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "ServiceLoader.load(\n    …             ).iterator()");
        localList = SequencesKt.toList(SequencesKt.asSequence((Iterator)localObject1));
      }
      Iterator localIterator = ((Iterable)localList).iterator();
      if (!localIterator.hasNext())
      {
        localObject1 = null;
      }
      else
      {
        localObject1 = localIterator.next();
        if (localIterator.hasNext())
        {
          int i = ((MainDispatcherFactory)localObject1).getLoadPriority();
          Object localObject2 = localObject1;
          do
          {
            Object localObject3 = localIterator.next();
            int j = ((MainDispatcherFactory)localObject3).getLoadPriority();
            localObject1 = localObject2;
            int k = i;
            if (i < j)
            {
              localObject1 = localObject3;
              k = j;
            }
            localObject2 = localObject1;
            i = k;
          } while (localIterator.hasNext());
        }
      }
      Object localObject1 = (MainDispatcherFactory)localObject1;
      if (localObject1 != null)
      {
        localObject1 = MainDispatchersKt.tryCreateDispatcher((MainDispatcherFactory)localObject1, localList);
        if (localObject1 != null) {}
      }
      else
      {
        localObject1 = new kotlinx/coroutines/internal/MissingMainCoroutineDispatcher;
        ((MissingMainCoroutineDispatcher)localObject1).<init>(null, null, 2, null);
        localObject1 = (MainCoroutineDispatcher)localObject1;
      }
    }
    finally
    {
      localMainCoroutineDispatcher = (MainCoroutineDispatcher)new MissingMainCoroutineDispatcher(localThrowable, null, 2, null);
    }
    return localMainCoroutineDispatcher;
  }
}
