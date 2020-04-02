package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\000\n\002\b\003\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\037\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\b\020\007\032\004\030\0010\b?\006\002\020\tJ\022\020\r\032\0020\0162\b\020\017\032\004\030\0010\020H\024J\013\020\021\032\0028\000?\006\002\020\022R\016\020\005\032\0020\006X?\004?\006\002\n\000R\020\020\007\032\004\030\0010\bX?\004?\006\002\n\000R\024\020\n\032\0020\0138TX?\004?\006\006\032\004\b\n\020\f?\006\023"}, d2={"Lkotlinx/coroutines/BlockingCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "blockedThread", "Ljava/lang/Thread;", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Thread;Lkotlinx/coroutines/EventLoop;)V", "isScopedCoroutine", "", "()Z", "afterCompletion", "", "state", "", "joinBlocking", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class BlockingCoroutine<T>
  extends AbstractCoroutine<T>
{
  private final Thread blockedThread;
  private final EventLoop eventLoop;
  
  public BlockingCoroutine(CoroutineContext paramCoroutineContext, Thread paramThread, EventLoop paramEventLoop)
  {
    super(paramCoroutineContext, true);
    this.blockedThread = paramThread;
    this.eventLoop = paramEventLoop;
  }
  
  protected void afterCompletion(Object paramObject)
  {
    if ((Intrinsics.areEqual(Thread.currentThread(), this.blockedThread) ^ true)) {
      LockSupport.unpark(this.blockedThread);
    }
  }
  
  protected boolean isScopedCoroutine()
  {
    return true;
  }
  
  /* Error */
  public final T joinBlocking()
  {
    // Byte code:
    //   0: invokestatic 74	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   3: astore_1
    //   4: aload_1
    //   5: ifnull +9 -> 14
    //   8: aload_1
    //   9: invokeinterface 80 1 0
    //   14: aload_0
    //   15: getfield 50	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   18: astore_2
    //   19: aconst_null
    //   20: astore_1
    //   21: aload_2
    //   22: ifnull +10 -> 32
    //   25: aload_2
    //   26: iconst_0
    //   27: iconst_1
    //   28: aconst_null
    //   29: invokestatic 86	kotlinx/coroutines/EventLoop:incrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   32: invokestatic 89	java/lang/Thread:interrupted	()Z
    //   35: ifne +128 -> 163
    //   38: aload_0
    //   39: getfield 50	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   42: astore_2
    //   43: aload_2
    //   44: ifnull +11 -> 55
    //   47: aload_2
    //   48: invokevirtual 93	kotlinx/coroutines/EventLoop:processNextEvent	()J
    //   51: lstore_3
    //   52: goto +7 -> 59
    //   55: ldc2_w 94
    //   58: lstore_3
    //   59: aload_0
    //   60: invokevirtual 98	kotlinx/coroutines/BlockingCoroutine:isCompleted	()Z
    //   63: istore 5
    //   65: iload 5
    //   67: ifeq +69 -> 136
    //   70: aload_0
    //   71: getfield 50	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   74: astore_2
    //   75: aload_2
    //   76: ifnull +10 -> 86
    //   79: aload_2
    //   80: iconst_0
    //   81: iconst_1
    //   82: aconst_null
    //   83: invokestatic 101	kotlinx/coroutines/EventLoop:decrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   86: invokestatic 74	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   89: astore_2
    //   90: aload_2
    //   91: ifnull +9 -> 100
    //   94: aload_2
    //   95: invokeinterface 104 1 0
    //   100: aload_0
    //   101: invokevirtual 107	kotlinx/coroutines/BlockingCoroutine:getState$kotlinx_coroutines_core	()Ljava/lang/Object;
    //   104: invokestatic 113	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   107: astore_2
    //   108: aload_2
    //   109: instanceof 115
    //   112: ifne +6 -> 118
    //   115: goto +5 -> 120
    //   118: aload_2
    //   119: astore_1
    //   120: aload_1
    //   121: checkcast 115	kotlinx/coroutines/CompletedExceptionally
    //   124: astore_1
    //   125: aload_1
    //   126: ifnonnull +5 -> 131
    //   129: aload_2
    //   130: areturn
    //   131: aload_1
    //   132: getfield 119	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   135: athrow
    //   136: invokestatic 74	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   139: astore_2
    //   140: aload_2
    //   141: ifnull +14 -> 155
    //   144: aload_2
    //   145: aload_0
    //   146: lload_3
    //   147: invokeinterface 123 4 0
    //   152: goto -120 -> 32
    //   155: aload_0
    //   156: lload_3
    //   157: invokestatic 124	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
    //   160: goto -128 -> 32
    //   163: new 126	java/lang/InterruptedException
    //   166: astore_1
    //   167: aload_1
    //   168: invokespecial 128	java/lang/InterruptedException:<init>	()V
    //   171: aload_0
    //   172: aload_1
    //   173: checkcast 130	java/lang/Throwable
    //   176: invokevirtual 134	kotlinx/coroutines/BlockingCoroutine:cancelCoroutine	(Ljava/lang/Throwable;)Z
    //   179: pop
    //   180: aload_1
    //   181: checkcast 130	java/lang/Throwable
    //   184: athrow
    //   185: astore_1
    //   186: aload_0
    //   187: getfield 50	kotlinx/coroutines/BlockingCoroutine:eventLoop	Lkotlinx/coroutines/EventLoop;
    //   190: astore_2
    //   191: aload_2
    //   192: ifnull +10 -> 202
    //   195: aload_2
    //   196: iconst_0
    //   197: iconst_1
    //   198: aconst_null
    //   199: invokestatic 101	kotlinx/coroutines/EventLoop:decrementUseCount$default	(Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
    //   202: aload_1
    //   203: athrow
    //   204: astore_1
    //   205: invokestatic 74	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   208: astore_2
    //   209: aload_2
    //   210: ifnull +9 -> 219
    //   213: aload_2
    //   214: invokeinterface 104 1 0
    //   219: aload_1
    //   220: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	BlockingCoroutine
    //   3	178	1	localObject1	Object
    //   185	18	1	localObject2	Object
    //   204	16	1	localObject3	Object
    //   18	196	2	localObject4	Object
    //   51	106	3	l	long
    //   63	3	5	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   32	43	185	finally
    //   47	52	185	finally
    //   59	65	185	finally
    //   136	140	185	finally
    //   144	152	185	finally
    //   155	160	185	finally
    //   163	185	185	finally
    //   14	19	204	finally
    //   25	32	204	finally
    //   70	75	204	finally
    //   79	86	204	finally
    //   186	191	204	finally
    //   195	202	204	finally
    //   202	204	204	finally
  }
}
