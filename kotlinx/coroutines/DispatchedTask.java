package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.Task;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\003\n\002\b\016\b \030\000*\006\b\000\020\001 \0002\0060\002j\002`\003B\r\022\006\020\004\032\0020\005?\006\002\020\006J\037\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\006\020\017\032\0020\020H\020?\006\002\b\021J\031\020\022\032\004\030\0010\0202\b\020\r\032\004\030\0010\016H\000?\006\002\b\023J\037\020\024\032\002H\001\"\004\b\001\020\0012\b\020\r\032\004\030\0010\016H\020?\006\004\b\025\020\026J!\020\027\032\0020\f2\b\020\030\032\004\030\0010\0202\b\020\031\032\004\030\0010\020H\000?\006\002\b\032J\006\020\033\032\0020\fJ\017\020\034\032\004\030\0010\016H ?\006\002\b\035R\030\020\007\032\b\022\004\022\0028\0000\bX \004?\006\006\032\004\b\t\020\nR\022\020\004\032\0020\0058\006@\006X?\016?\006\002\n\000?\006\036"}, d2={"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelResult", "", "state", "", "cause", "", "cancelResult$kotlinx_coroutines_core", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "handleFatalException$kotlinx_coroutines_core", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class DispatchedTask<T>
  extends Task
{
  public int resumeMode;
  
  public DispatchedTask(int paramInt)
  {
    this.resumeMode = paramInt;
  }
  
  public void cancelResult$kotlinx_coroutines_core(Object paramObject, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
  }
  
  public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();
  
  public final Throwable getExceptionalResult$kotlinx_coroutines_core(Object paramObject)
  {
    boolean bool = paramObject instanceof CompletedExceptionally;
    Object localObject = null;
    if (!bool) {
      paramObject = null;
    }
    CompletedExceptionally localCompletedExceptionally = (CompletedExceptionally)paramObject;
    paramObject = localObject;
    if (localCompletedExceptionally != null) {
      paramObject = localCompletedExceptionally.cause;
    }
    return paramObject;
  }
  
  public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object paramObject)
  {
    return paramObject;
  }
  
  public final void handleFatalException$kotlinx_coroutines_core(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    if ((paramThrowable1 == null) && (paramThrowable2 == null)) {
      return;
    }
    if ((paramThrowable1 != null) && (paramThrowable2 != null)) {
      ExceptionsKt.addSuppressed(paramThrowable1, paramThrowable2);
    }
    if (paramThrowable1 == null) {
      paramThrowable1 = paramThrowable2;
    }
    paramThrowable2 = new StringBuilder();
    paramThrowable2.append("Fatal exception in coroutines machinery for ");
    paramThrowable2.append(this);
    paramThrowable2.append(". ");
    paramThrowable2.append("Please read KDoc to 'handleFatalException' method and report this incident to maintainers");
    paramThrowable2 = paramThrowable2.toString();
    if (paramThrowable1 == null) {
      Intrinsics.throwNpe();
    }
    paramThrowable1 = new CoroutinesInternalError(paramThrowable2, paramThrowable1);
    CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), (Throwable)paramThrowable1);
  }
  
  /* Error */
  public final void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 122	kotlinx/coroutines/DispatchedTask:taskContext	Lkotlinx/coroutines/scheduling/TaskContext;
    //   4: astore_1
    //   5: aconst_null
    //   6: astore_2
    //   7: aconst_null
    //   8: checkcast 112	java/lang/Throwable
    //   11: astore_3
    //   12: aload_0
    //   13: invokevirtual 104	kotlinx/coroutines/DispatchedTask:getDelegate$kotlinx_coroutines_core	()Lkotlin/coroutines/Continuation;
    //   16: astore 4
    //   18: aload 4
    //   20: ifnull +265 -> 285
    //   23: aload 4
    //   25: checkcast 124	kotlinx/coroutines/DispatchedContinuation
    //   28: astore 5
    //   30: aload 5
    //   32: getfield 127	kotlinx/coroutines/DispatchedContinuation:continuation	Lkotlin/coroutines/Continuation;
    //   35: astore 6
    //   37: aload 6
    //   39: invokeinterface 110 1 0
    //   44: astore 4
    //   46: aload_0
    //   47: invokevirtual 130	kotlinx/coroutines/DispatchedTask:takeState$kotlinx_coroutines_core	()Ljava/lang/Object;
    //   50: astore 7
    //   52: aload 4
    //   54: aload 5
    //   56: getfield 134	kotlinx/coroutines/DispatchedContinuation:countOrElement	Ljava/lang/Object;
    //   59: invokestatic 140	kotlinx/coroutines/internal/ThreadContextKt:updateThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
    //   62: astore 5
    //   64: aload_0
    //   65: aload 7
    //   67: invokevirtual 142	kotlinx/coroutines/DispatchedTask:getExceptionalResult$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   70: astore 8
    //   72: aload_0
    //   73: getfield 51	kotlinx/coroutines/DispatchedTask:resumeMode	I
    //   76: invokestatic 148	kotlinx/coroutines/DispatchedTaskKt:isCancellableMode	(I)Z
    //   79: ifeq +20 -> 99
    //   82: aload 4
    //   84: getstatic 154	kotlinx/coroutines/Job:Key	Lkotlinx/coroutines/Job$Key;
    //   87: checkcast 156	kotlin/coroutines/CoroutineContext$Key
    //   90: invokeinterface 162 2 0
    //   95: checkcast 150	kotlinx/coroutines/Job
    //   98: astore_2
    //   99: aload 8
    //   101: ifnonnull +63 -> 164
    //   104: aload_2
    //   105: ifnull +59 -> 164
    //   108: aload_2
    //   109: invokeinterface 166 1 0
    //   114: ifne +50 -> 164
    //   117: aload_2
    //   118: invokeinterface 170 1 0
    //   123: astore_2
    //   124: aload_0
    //   125: aload 7
    //   127: aload_2
    //   128: checkcast 112	java/lang/Throwable
    //   131: invokevirtual 172	kotlinx/coroutines/DispatchedTask:cancelResult$kotlinx_coroutines_core	(Ljava/lang/Object;Ljava/lang/Throwable;)V
    //   134: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   137: astore 7
    //   139: aload 6
    //   141: aload_2
    //   142: checkcast 112	java/lang/Throwable
    //   145: aload 6
    //   147: invokestatic 184	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   150: invokestatic 190	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   153: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   156: invokeinterface 197 2 0
    //   161: goto +53 -> 214
    //   164: aload 8
    //   166: ifnull +25 -> 191
    //   169: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   172: astore_2
    //   173: aload 6
    //   175: aload 8
    //   177: invokestatic 190	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   180: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   183: invokeinterface 197 2 0
    //   188: goto +26 -> 214
    //   191: aload_0
    //   192: aload 7
    //   194: invokevirtual 199	kotlinx/coroutines/DispatchedTask:getSuccessfulResult$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   197: astore_2
    //   198: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   201: astore 7
    //   203: aload 6
    //   205: aload_2
    //   206: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   209: invokeinterface 197 2 0
    //   214: getstatic 205	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   217: astore_2
    //   218: aload 4
    //   220: aload 5
    //   222: invokestatic 209	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   225: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   228: astore_2
    //   229: aload_0
    //   230: checkcast 2	kotlinx/coroutines/DispatchedTask
    //   233: astore_2
    //   234: aload_1
    //   235: invokeinterface 214 1 0
    //   240: getstatic 205	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   243: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   246: astore_2
    //   247: goto +16 -> 263
    //   250: astore_1
    //   251: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   254: astore_2
    //   255: aload_1
    //   256: invokestatic 190	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   259: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   262: astore_2
    //   263: aload_0
    //   264: aload_3
    //   265: aload_2
    //   266: invokestatic 217	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   269: invokevirtual 219	kotlinx/coroutines/DispatchedTask:handleFatalException$kotlinx_coroutines_core	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   272: goto +73 -> 345
    //   275: astore_2
    //   276: aload 4
    //   278: aload 5
    //   280: invokestatic 209	kotlinx/coroutines/internal/ThreadContextKt:restoreThreadContext	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
    //   283: aload_2
    //   284: athrow
    //   285: new 221	kotlin/TypeCastException
    //   288: astore_2
    //   289: aload_2
    //   290: ldc -33
    //   292: invokespecial 226	kotlin/TypeCastException:<init>	(Ljava/lang/String;)V
    //   295: aload_2
    //   296: athrow
    //   297: astore_3
    //   298: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   301: astore_2
    //   302: aload_0
    //   303: checkcast 2	kotlinx/coroutines/DispatchedTask
    //   306: astore_2
    //   307: aload_1
    //   308: invokeinterface 214 1 0
    //   313: getstatic 205	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   316: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   319: astore_2
    //   320: goto +16 -> 336
    //   323: astore_2
    //   324: getstatic 178	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   327: astore_1
    //   328: aload_2
    //   329: invokestatic 190	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   332: invokestatic 193	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   335: astore_2
    //   336: aload_0
    //   337: aload_3
    //   338: aload_2
    //   339: invokestatic 217	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   342: invokevirtual 219	kotlinx/coroutines/DispatchedTask:handleFatalException$kotlinx_coroutines_core	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   345: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	346	0	this	DispatchedTask
    //   4	231	1	localTaskContext	kotlinx.coroutines.scheduling.TaskContext
    //   250	58	1	localThrowable1	Throwable
    //   327	1	1	localCompanion	kotlin.Result.Companion
    //   6	260	2	localObject1	Object
    //   275	9	2	localObject2	Object
    //   288	32	2	localObject3	Object
    //   323	6	2	localThrowable2	Throwable
    //   335	4	2	localObject4	Object
    //   11	254	3	localThrowable3	Throwable
    //   297	41	3	localThrowable4	Throwable
    //   16	261	4	localObject5	Object
    //   28	251	5	localObject6	Object
    //   35	169	6	localContinuation	Continuation
    //   50	152	7	localObject7	Object
    //   70	106	8	localThrowable5	Throwable
    // Exception table:
    //   from	to	target	type
    //   225	247	250	finally
    //   64	72	275	finally
    //   72	99	275	finally
    //   108	161	275	finally
    //   169	188	275	finally
    //   191	214	275	finally
    //   214	218	275	finally
    //   12	18	297	finally
    //   23	64	297	finally
    //   218	225	297	finally
    //   276	285	297	finally
    //   285	297	297	finally
    //   298	320	323	finally
  }
  
  public abstract Object takeState$kotlinx_coroutines_core();
}
