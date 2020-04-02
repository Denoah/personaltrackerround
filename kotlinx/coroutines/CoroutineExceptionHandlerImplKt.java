package kotlinx.coroutines;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\000\032\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\000\"\024\020\000\032\b\022\004\022\0020\0020\001X?\004?\006\002\n\000?\006\t"}, d2={"handlers", "", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleCoroutineExceptionImpl", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CoroutineExceptionHandlerImplKt
{
  private static final List<CoroutineExceptionHandler> handlers;
  
  static
  {
    Iterator localIterator = ServiceLoader.load(CoroutineExceptionHandler.class, CoroutineExceptionHandler.class.getClassLoader()).iterator();
    Intrinsics.checkExpressionValueIsNotNull(localIterator, "ServiceLoader.load(\n    ….classLoader\n).iterator()");
    handlers = SequencesKt.toList(SequencesKt.asSequence(localIterator));
  }
  
  /* Error */
  public static final void handleCoroutineExceptionImpl(kotlin.coroutines.CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 69
    //   3: invokestatic 72	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: ldc 73
    //   9: invokestatic 72	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: getstatic 66	kotlinx/coroutines/CoroutineExceptionHandlerImplKt:handlers	Ljava/util/List;
    //   15: invokeinterface 76 1 0
    //   20: astore_2
    //   21: aload_2
    //   22: invokeinterface 82 1 0
    //   27: ifeq +55 -> 82
    //   30: aload_2
    //   31: invokeinterface 86 1 0
    //   36: checkcast 30	kotlinx/coroutines/CoroutineExceptionHandler
    //   39: astore_3
    //   40: aload_3
    //   41: aload_0
    //   42: aload_1
    //   43: invokeinterface 89 3 0
    //   48: goto -27 -> 21
    //   51: astore 4
    //   53: invokestatic 95	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   56: astore_3
    //   57: aload_3
    //   58: ldc 96
    //   60: invokestatic 54	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   63: aload_3
    //   64: invokevirtual 100	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   67: aload_3
    //   68: aload_1
    //   69: aload 4
    //   71: invokestatic 106	kotlinx/coroutines/CoroutineExceptionHandlerKt:handlerException	(Ljava/lang/Throwable;Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   74: invokeinterface 112 3 0
    //   79: goto -58 -> 21
    //   82: invokestatic 95	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   85: astore_0
    //   86: aload_0
    //   87: ldc 96
    //   89: invokestatic 54	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   92: aload_0
    //   93: invokevirtual 100	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   96: aload_0
    //   97: aload_1
    //   98: invokeinterface 112 3 0
    //   103: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	104	0	paramCoroutineContext	kotlin.coroutines.CoroutineContext
    //   0	104	1	paramThrowable	Throwable
    //   20	11	2	localIterator	Iterator
    //   39	29	3	localObject	Object
    //   51	19	4	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   40	48	51	finally
  }
}
