package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\020\016\n\000\n\002\020\013\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\000\032\f\020\002\032\0020\003*\0020\004H\007\032\032\020\005\032\0020\004*\0020\0062\f\020\007\032\b\022\004\022\0020\0060\bH\007\"\016\020\000\032\0020\001X?T?\006\002\n\000?\006\t"}, d2={"FAST_SERVICE_LOADER_PROPERTY_NAME", "", "isMissing", "", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "tryCreateDispatcher", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "factories", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class MainDispatchersKt
{
  private static final String FAST_SERVICE_LOADER_PROPERTY_NAME = "kotlinx.coroutines.fast.service.loader";
  
  public static final boolean isMissing(MainCoroutineDispatcher paramMainCoroutineDispatcher)
  {
    Intrinsics.checkParameterIsNotNull(paramMainCoroutineDispatcher, "$this$isMissing");
    return paramMainCoroutineDispatcher instanceof MissingMainCoroutineDispatcher;
  }
  
  /* Error */
  public static final MainCoroutineDispatcher tryCreateDispatcher(MainDispatcherFactory paramMainDispatcherFactory, java.util.List<? extends MainDispatcherFactory> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 42
    //   3: invokestatic 36	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: ldc 43
    //   9: invokestatic 36	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: aload_1
    //   14: invokeinterface 49 2 0
    //   19: astore_1
    //   20: aload_1
    //   21: astore_0
    //   22: goto +22 -> 44
    //   25: astore_1
    //   26: new 38	kotlinx/coroutines/internal/MissingMainCoroutineDispatcher
    //   29: dup
    //   30: aload_1
    //   31: aload_0
    //   32: invokeinterface 53 1 0
    //   37: invokespecial 57	kotlinx/coroutines/internal/MissingMainCoroutineDispatcher:<init>	(Ljava/lang/Throwable;Ljava/lang/String;)V
    //   40: checkcast 59	kotlinx/coroutines/MainCoroutineDispatcher
    //   43: astore_0
    //   44: aload_0
    //   45: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	46	0	paramMainDispatcherFactory	MainDispatcherFactory
    //   0	46	1	paramList	java.util.List<? extends MainDispatcherFactory>
    // Exception table:
    //   from	to	target	type
    //   12	20	25	finally
  }
}
