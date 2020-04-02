package kotlinx.coroutines;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\t\n\002\b\005\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\007\b?\002\030\0002\0020\0012\0060\002j\002`\003B\007\b\002?\006\002\020\004J\b\020\031\032\0020\032H\002J\b\020\033\032\0020\020H\002J\r\020\034\032\0020\032H\000?\006\002\b\035J\034\020\036\032\0020\0372\006\020 \032\0020\b2\n\020!\032\0060\002j\002`\003H\026J\b\020\"\032\0020\024H\002J\b\020#\032\0020\032H\026J\016\020$\032\0020\0322\006\020%\032\0020\bR\016\020\005\032\0020\006X?T?\006\002\n\000R\016\020\007\032\0020\bX?T?\006\002\n\000R\016\020\t\032\0020\006X?T?\006\002\n\000R\016\020\n\032\0020\bX?\004?\006\002\n\000R\016\020\013\032\0020\006X?T?\006\002\n\000R\016\020\f\032\0020\006X?T?\006\002\n\000R\016\020\r\032\0020\016X?T?\006\002\n\000R\026\020\017\032\004\030\0010\020X?\016?\006\b\n\000\022\004\b\021\020\004R\016\020\022\032\0020\006X?\016?\006\002\n\000R\024\020\023\032\0020\0248BX?\004?\006\006\032\004\b\023\020\025R\024\020\026\032\0020\0208TX?\004?\006\006\032\004\b\027\020\030?\006&"}, d2={"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "ACTIVE", "", "DEFAULT_KEEP_ALIVE", "", "FRESH", "KEEP_ALIVE_NANOS", "SHUTDOWN_ACK", "SHUTDOWN_REQ", "THREAD_NAME", "", "_thread", "Ljava/lang/Thread;", "_thread$annotations", "debugStatus", "isShutdownRequested", "", "()Z", "thread", "getThread", "()Ljava/lang/Thread;", "acknowledgeShutdownIfNeeded", "", "createThreadSync", "ensureStarted", "ensureStarted$kotlinx_coroutines_core", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "notifyStartup", "run", "shutdown", "timeout", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class DefaultExecutor
  extends EventLoopImplBase
  implements Runnable
{
  private static final int ACTIVE = 1;
  private static final long DEFAULT_KEEP_ALIVE = 1000L;
  private static final int FRESH = 0;
  public static final DefaultExecutor INSTANCE;
  private static final long KEEP_ALIVE_NANOS;
  private static final int SHUTDOWN_ACK = 3;
  private static final int SHUTDOWN_REQ = 2;
  public static final String THREAD_NAME = "kotlinx.coroutines.DefaultExecutor";
  private static volatile Thread _thread;
  private static volatile int debugStatus;
  
  static
  {
    Object localObject = new DefaultExecutor();
    INSTANCE = (DefaultExecutor)localObject;
    EventLoop.incrementUseCount$default((EventLoop)localObject, false, 1, null);
    TimeUnit localTimeUnit = TimeUnit.MILLISECONDS;
    Long localLong;
    try
    {
      localObject = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
    }
    catch (SecurityException localSecurityException)
    {
      localLong = Long.valueOf(1000L);
    }
    Intrinsics.checkExpressionValueIsNotNull(localLong, "try {\n            java.l…AULT_KEEP_ALIVE\n        }");
    KEEP_ALIVE_NANOS = localTimeUnit.toNanos(localLong.longValue());
  }
  
  private DefaultExecutor() {}
  
  private final void acknowledgeShutdownIfNeeded()
  {
    try
    {
      boolean bool = isShutdownRequested();
      if (!bool) {
        return;
      }
      debugStatus = 3;
      resetAll();
      ((Object)this).notifyAll();
      return;
    }
    finally {}
  }
  
  private final Thread createThreadSync()
  {
    try
    {
      Thread localThread = _thread;
      if (localThread == null)
      {
        localThread = new java/lang/Thread;
        localThread.<init>((Runnable)this, "kotlinx.coroutines.DefaultExecutor");
        _thread = localThread;
        localThread.setDaemon(true);
        localThread.start();
      }
      return localThread;
    }
    finally {}
  }
  
  private final boolean isShutdownRequested()
  {
    int i = debugStatus;
    boolean bool;
    if ((i != 2) && (i != 3)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private final boolean notifyStartup()
  {
    try
    {
      boolean bool = isShutdownRequested();
      if (bool) {
        return false;
      }
      debugStatus = 1;
      ((Object)this).notifyAll();
      return true;
    }
    finally {}
  }
  
  public final void ensureStarted$kotlinx_coroutines_core()
  {
    try
    {
      boolean bool = DebugKt.getASSERTIONS_ENABLED();
      int i = 1;
      int j;
      AssertionError localAssertionError;
      if (bool)
      {
        if (_thread == null) {
          j = 1;
        } else {
          j = 0;
        }
        if (j == 0)
        {
          localAssertionError = new java/lang/AssertionError;
          localAssertionError.<init>();
          throw ((Throwable)localAssertionError);
        }
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        j = i;
        if (debugStatus != 0) {
          if (debugStatus == 3) {
            j = i;
          } else {
            j = 0;
          }
        }
        if (j == 0)
        {
          localAssertionError = new java/lang/AssertionError;
          localAssertionError.<init>();
          throw ((Throwable)localAssertionError);
        }
      }
      debugStatus = 0;
      createThreadSync();
      while (debugStatus == 0) {
        ((Object)this).wait();
      }
      return;
    }
    finally {}
  }
  
  protected Thread getThread()
  {
    Thread localThread = _thread;
    if (localThread == null) {
      localThread = createThreadSync();
    }
    return localThread;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    return scheduleInvokeOnTimeout(paramLong, paramRunnable);
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: getstatic 167	kotlinx/coroutines/ThreadLocalEventLoop:INSTANCE	Lkotlinx/coroutines/ThreadLocalEventLoop;
    //   3: aload_0
    //   4: checkcast 71	kotlinx/coroutines/EventLoop
    //   7: invokevirtual 171	kotlinx/coroutines/ThreadLocalEventLoop:setEventLoop$kotlinx_coroutines_core	(Lkotlinx/coroutines/EventLoop;)V
    //   10: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   13: astore_1
    //   14: aload_1
    //   15: ifnull +9 -> 24
    //   18: aload_1
    //   19: invokeinterface 182 1 0
    //   24: aload_0
    //   25: invokespecial 184	kotlinx/coroutines/DefaultExecutor:notifyStartup	()Z
    //   28: istore_2
    //   29: iload_2
    //   30: ifne +41 -> 71
    //   33: aconst_null
    //   34: checkcast 129	java/lang/Thread
    //   37: putstatic 127	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   40: aload_0
    //   41: invokespecial 186	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   44: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   47: astore_1
    //   48: aload_1
    //   49: ifnull +9 -> 58
    //   52: aload_1
    //   53: invokeinterface 189 1 0
    //   58: aload_0
    //   59: invokevirtual 192	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   62: ifne +8 -> 70
    //   65: aload_0
    //   66: invokevirtual 194	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   69: pop
    //   70: return
    //   71: ldc2_w 195
    //   74: lstore_3
    //   75: invokestatic 199	java/lang/Thread:interrupted	()Z
    //   78: pop
    //   79: aload_0
    //   80: invokevirtual 202	kotlinx/coroutines/DefaultExecutor:processNextEvent	()J
    //   83: lstore 5
    //   85: lload_3
    //   86: lstore 7
    //   88: lload 5
    //   90: lstore 9
    //   92: lload 5
    //   94: ldc2_w 195
    //   97: lcmp
    //   98: ifne +132 -> 230
    //   101: lload_3
    //   102: ldc2_w 195
    //   105: lcmp
    //   106: istore 11
    //   108: iload 11
    //   110: ifne +107 -> 217
    //   113: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   116: astore_1
    //   117: aload_1
    //   118: ifnull +14 -> 132
    //   121: aload_1
    //   122: invokeinterface 205 1 0
    //   127: lstore 9
    //   129: goto +8 -> 137
    //   132: invokestatic 208	java/lang/System:nanoTime	()J
    //   135: lstore 9
    //   137: iload 11
    //   139: ifne +12 -> 151
    //   142: getstatic 111	kotlinx/coroutines/DefaultExecutor:KEEP_ALIVE_NANOS	J
    //   145: lstore_3
    //   146: lload_3
    //   147: lload 9
    //   149: ladd
    //   150: lstore_3
    //   151: lload_3
    //   152: lload 9
    //   154: lsub
    //   155: lstore 9
    //   157: lload 9
    //   159: lconst_0
    //   160: lcmp
    //   161: ifgt +41 -> 202
    //   164: aconst_null
    //   165: checkcast 129	java/lang/Thread
    //   168: putstatic 127	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   171: aload_0
    //   172: invokespecial 186	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   175: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   178: astore_1
    //   179: aload_1
    //   180: ifnull +9 -> 189
    //   183: aload_1
    //   184: invokeinterface 189 1 0
    //   189: aload_0
    //   190: invokevirtual 192	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   193: ifne +8 -> 201
    //   196: aload_0
    //   197: invokevirtual 194	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   200: pop
    //   201: return
    //   202: lload 5
    //   204: lload 9
    //   206: invokestatic 214	kotlin/ranges/RangesKt:coerceAtMost	(JJ)J
    //   209: lstore 9
    //   211: lload_3
    //   212: lstore 7
    //   214: goto +16 -> 230
    //   217: lload 5
    //   219: getstatic 111	kotlinx/coroutines/DefaultExecutor:KEEP_ALIVE_NANOS	J
    //   222: invokestatic 214	kotlin/ranges/RangesKt:coerceAtMost	(JJ)J
    //   225: lstore 9
    //   227: lload_3
    //   228: lstore 7
    //   230: lload 7
    //   232: lstore_3
    //   233: lload 9
    //   235: lconst_0
    //   236: lcmp
    //   237: ifle -162 -> 75
    //   240: aload_0
    //   241: invokespecial 115	kotlinx/coroutines/DefaultExecutor:isShutdownRequested	()Z
    //   244: istore_2
    //   245: iload_2
    //   246: ifeq +41 -> 287
    //   249: aconst_null
    //   250: checkcast 129	java/lang/Thread
    //   253: putstatic 127	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   256: aload_0
    //   257: invokespecial 186	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   260: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   263: astore_1
    //   264: aload_1
    //   265: ifnull +9 -> 274
    //   268: aload_1
    //   269: invokeinterface 189 1 0
    //   274: aload_0
    //   275: invokevirtual 192	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   278: ifne +8 -> 286
    //   281: aload_0
    //   282: invokevirtual 194	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   285: pop
    //   286: return
    //   287: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   290: astore_1
    //   291: aload_1
    //   292: ifnull +18 -> 310
    //   295: aload_1
    //   296: aload_0
    //   297: lload 9
    //   299: invokeinterface 218 4 0
    //   304: lload 7
    //   306: lstore_3
    //   307: goto -232 -> 75
    //   310: aload_0
    //   311: lload 9
    //   313: invokestatic 221	java/util/concurrent/locks/LockSupport:parkNanos	(Ljava/lang/Object;J)V
    //   316: lload 7
    //   318: lstore_3
    //   319: goto -244 -> 75
    //   322: astore 12
    //   324: aconst_null
    //   325: checkcast 129	java/lang/Thread
    //   328: putstatic 127	kotlinx/coroutines/DefaultExecutor:_thread	Ljava/lang/Thread;
    //   331: aload_0
    //   332: invokespecial 186	kotlinx/coroutines/DefaultExecutor:acknowledgeShutdownIfNeeded	()V
    //   335: invokestatic 177	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   338: astore_1
    //   339: aload_1
    //   340: ifnull +9 -> 349
    //   343: aload_1
    //   344: invokeinterface 189 1 0
    //   349: aload_0
    //   350: invokevirtual 192	kotlinx/coroutines/DefaultExecutor:isEmpty	()Z
    //   353: ifne +8 -> 361
    //   356: aload_0
    //   357: invokevirtual 194	kotlinx/coroutines/DefaultExecutor:getThread	()Ljava/lang/Thread;
    //   360: pop
    //   361: aload 12
    //   363: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	364	0	this	DefaultExecutor
    //   13	331	1	localTimeSource	TimeSource
    //   28	218	2	bool1	boolean
    //   74	245	3	l1	long
    //   83	135	5	l2	long
    //   86	231	7	l3	long
    //   90	222	9	l4	long
    //   106	32	11	bool2	boolean
    //   322	40	12	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   24	29	322	finally
    //   75	85	322	finally
    //   113	117	322	finally
    //   121	129	322	finally
    //   132	137	322	finally
    //   142	146	322	finally
    //   202	211	322	finally
    //   217	227	322	finally
    //   240	245	322	finally
    //   287	291	322	finally
    //   295	304	322	finally
    //   310	316	322	finally
  }
  
  public final void shutdown(long paramLong)
  {
    try
    {
      long l = System.currentTimeMillis();
      if (!isShutdownRequested()) {
        debugStatus = 2;
      }
      while ((debugStatus != 3) && (_thread != null))
      {
        Thread localThread = _thread;
        if (localThread != null)
        {
          TimeSource localTimeSource = TimeSourceKt.getTimeSource();
          if (localTimeSource != null) {
            localTimeSource.unpark(localThread);
          } else {
            LockSupport.unpark(localThread);
          }
        }
        if (l + paramLong - System.currentTimeMillis() <= 0L) {
          break;
        }
        ((Object)this).wait(paramLong);
      }
      debugStatus = 0;
      return;
    }
    finally {}
  }
}
