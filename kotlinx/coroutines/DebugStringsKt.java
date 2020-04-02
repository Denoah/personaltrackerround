package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\000\n\002\020\016\n\002\020\000\n\002\b\005\n\002\030\002\n\000\032\020\020\007\032\0020\001*\006\022\002\b\0030\bH\000\"\030\020\000\032\0020\001*\0020\0028@X?\004?\006\006\032\004\b\003\020\004\"\030\020\005\032\0020\001*\0020\0028@X?\004?\006\006\032\004\b\006\020\004?\006\t"}, d2={"classSimpleName", "", "", "getClassSimpleName", "(Ljava/lang/Object;)Ljava/lang/String;", "hexAddress", "getHexAddress", "toDebugString", "Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DebugStringsKt
{
  public static final String getClassSimpleName(Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramObject, "$this$classSimpleName");
    paramObject = paramObject.getClass().getSimpleName();
    Intrinsics.checkExpressionValueIsNotNull(paramObject, "this::class.java.simpleName");
    return paramObject;
  }
  
  public static final String getHexAddress(Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramObject, "$this$hexAddress");
    paramObject = Integer.toHexString(System.identityHashCode(paramObject));
    Intrinsics.checkExpressionValueIsNotNull(paramObject, "Integer.toHexString(System.identityHashCode(this))");
    return paramObject;
  }
  
  /* Error */
  public static final String toDebugString(kotlin.coroutines.Continuation<?> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 68
    //   3: invokestatic 33	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: instanceof 70
    //   10: ifeq +11 -> 21
    //   13: aload_0
    //   14: invokevirtual 73	java/lang/Object:toString	()Ljava/lang/String;
    //   17: astore_0
    //   18: goto +117 -> 135
    //   21: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   24: astore_1
    //   25: new 81	java/lang/StringBuilder
    //   28: astore_1
    //   29: aload_1
    //   30: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   33: aload_1
    //   34: aload_0
    //   35: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_1
    //   40: bipush 64
    //   42: invokevirtual 92	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_1
    //   47: aload_0
    //   48: invokestatic 94	kotlinx/coroutines/DebugStringsKt:getHexAddress	(Ljava/lang/Object;)Ljava/lang/String;
    //   51: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload_1
    //   56: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   59: invokestatic 102	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   62: astore_1
    //   63: goto +16 -> 79
    //   66: astore_2
    //   67: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   70: astore_1
    //   71: aload_2
    //   72: invokestatic 108	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   75: invokestatic 102	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   78: astore_1
    //   79: aload_1
    //   80: invokestatic 112	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   83: ifnonnull +6 -> 89
    //   86: goto +44 -> 130
    //   89: new 81	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   96: astore_1
    //   97: aload_1
    //   98: aload_0
    //   99: invokevirtual 37	java/lang/Object:getClass	()Ljava/lang/Class;
    //   102: invokevirtual 115	java/lang/Class:getName	()Ljava/lang/String;
    //   105: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: aload_1
    //   110: bipush 64
    //   112: invokevirtual 92	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: aload_1
    //   117: aload_0
    //   118: invokestatic 94	kotlinx/coroutines/DebugStringsKt:getHexAddress	(Ljava/lang/Object;)Ljava/lang/String;
    //   121: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload_1
    //   126: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   129: astore_1
    //   130: aload_1
    //   131: checkcast 117	java/lang/String
    //   134: astore_0
    //   135: aload_0
    //   136: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	137	0	paramContinuation	kotlin.coroutines.Continuation<?>
    //   24	107	1	localObject	Object
    //   66	6	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   21	63	66	finally
  }
}
