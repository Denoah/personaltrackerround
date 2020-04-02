package kotlinx.coroutines.internal;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000f\n\000\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\003\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\021\n\002\b\002\n\002\020\001\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\b\032\020\020\006\032\0020\0072\006\020\b\032\0020\001H\007\0329\020\t\032\002H\n\"\b\b\000\020\n*\0020\0132\006\020\f\032\002H\n2\006\020\r\032\002H\n2\020\020\016\032\f\022\b\022\0060\007j\002`\0200\017H\002?\006\002\020\021\032\036\020\022\032\f\022\b\022\0060\007j\002`\0200\0172\n\020\023\032\0060\024j\002`\025H\002\0321\020\026\032\0020\0272\020\020\030\032\f\022\b\022\0060\007j\002`\0200\0312\020\020\r\032\f\022\b\022\0060\007j\002`\0200\017H\002?\006\002\020\032\032\031\020\033\032\0020\0342\006\020\035\032\0020\013H?H?\001\000?\006\002\020\036\032+\020\037\032\002H\n\"\b\b\000\020\n*\0020\0132\006\020\035\032\002H\n2\n\020\023\032\0060\024j\002`\025H\002?\006\002\020 \032\037\020!\032\002H\n\"\b\b\000\020\n*\0020\0132\006\020\035\032\002H\nH\000?\006\002\020\"\032+\020!\032\002H\n\"\b\b\000\020\n*\0020\0132\006\020\035\032\002H\n2\n\020\023\032\006\022\002\b\0030#H\000?\006\002\020$\032\037\020%\032\002H\n\"\b\b\000\020\n*\0020\0132\006\020\035\032\002H\nH\000?\006\002\020\"\0321\020&\032\030\022\004\022\002H\n\022\016\022\f\022\b\022\0060\007j\002`\0200\0310'\"\b\b\000\020\n*\0020\013*\002H\nH\002?\006\002\020(\032\034\020)\032\0020**\0060\007j\002`\0202\n\020+\032\0060\007j\002`\020H\002\032#\020,\032\0020-*\f\022\b\022\0060\007j\002`\0200\0312\006\020.\032\0020\001H\002?\006\002\020/\032\024\0200\032\0020\027*\0020\0132\006\020\f\032\0020\013H\000\032\020\0201\032\0020**\0060\007j\002`\020H\000\032\033\0202\032\002H\n\"\b\b\000\020\n*\0020\013*\002H\nH\002?\006\002\020\"\"\016\020\000\032\0020\001X?T?\006\002\n\000\"\026\020\002\032\n \003*\004\030\0010\0010\001X?\004?\006\002\n\000\"\016\020\004\032\0020\001X?T?\006\002\n\000\"\026\020\005\032\n \003*\004\030\0010\0010\001X?\004?\006\002\n\000*\f\b\000\0203\"\0020\0242\0020\024*\f\b\000\0204\"\0020\0072\0020\007?\002\004\n\002\b\031?\0065"}, d2={"baseContinuationImplClass", "", "baseContinuationImplClassName", "kotlin.jvm.PlatformType", "stackTraceRecoveryClass", "stackTraceRecoveryClassName", "artificialFrame", "Ljava/lang/StackTraceElement;", "message", "createFinalException", "E", "", "cause", "result", "resultStackTrace", "Ljava/util/ArrayDeque;", "Lkotlinx/coroutines/internal/StackTraceElement;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Ljava/util/ArrayDeque;)Ljava/lang/Throwable;", "createStackTrace", "continuation", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "mergeRecoveredTraces", "", "recoveredStacktrace", "", "([Ljava/lang/StackTraceElement;Ljava/util/ArrayDeque;)V", "recoverAndThrow", "", "exception", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recoverFromStackFrame", "(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;", "recoverStackTrace", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;", "unwrap", "causeAndStacktrace", "Lkotlin/Pair;", "(Ljava/lang/Throwable;)Lkotlin/Pair;", "elementWiseEquals", "", "e", "frameIndex", "", "methodName", "([Ljava/lang/StackTraceElement;Ljava/lang/String;)I", "initCause", "isArtificial", "sanitizeStackTrace", "CoroutineStackFrame", "StackTraceElement", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class StackTraceRecoveryKt
{
  private static final String baseContinuationImplClass = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
  private static final String baseContinuationImplClassName;
  private static final String stackTraceRecoveryClass = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
  private static final String stackTraceRecoveryClassName;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: ldc 69
    //   2: astore_0
    //   3: ldc 67
    //   5: astore_1
    //   6: getstatic 77	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   9: astore_2
    //   10: ldc 67
    //   12: invokestatic 83	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   15: astore_2
    //   16: aload_2
    //   17: ldc 85
    //   19: invokestatic 91	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   22: aload_2
    //   23: invokevirtual 95	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   26: invokestatic 99	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   29: astore_2
    //   30: goto +16 -> 46
    //   33: astore_2
    //   34: getstatic 77	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   37: astore_3
    //   38: aload_2
    //   39: invokestatic 105	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   42: invokestatic 99	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   45: astore_2
    //   46: aload_2
    //   47: invokestatic 109	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   50: ifnonnull +5 -> 55
    //   53: aload_2
    //   54: astore_1
    //   55: aload_1
    //   56: checkcast 111	java/lang/String
    //   59: putstatic 113	kotlinx/coroutines/internal/StackTraceRecoveryKt:baseContinuationImplClassName	Ljava/lang/String;
    //   62: getstatic 77	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   65: astore_2
    //   66: ldc 69
    //   68: invokestatic 83	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   71: astore_2
    //   72: aload_2
    //   73: ldc 115
    //   75: invokestatic 91	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   78: aload_2
    //   79: invokevirtual 95	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   82: invokestatic 99	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   85: astore_2
    //   86: goto +16 -> 102
    //   89: astore_1
    //   90: getstatic 77	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   93: astore_2
    //   94: aload_1
    //   95: invokestatic 105	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   98: invokestatic 99	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   101: astore_2
    //   102: aload_0
    //   103: astore_1
    //   104: aload_2
    //   105: invokestatic 109	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   108: ifnonnull +5 -> 113
    //   111: aload_2
    //   112: astore_1
    //   113: aload_1
    //   114: checkcast 111	java/lang/String
    //   117: putstatic 117	kotlinx/coroutines/internal/StackTraceRecoveryKt:stackTraceRecoveryClassName	Ljava/lang/String;
    //   120: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   2	101	0	str	String
    //   5	51	1	localObject1	Object
    //   89	6	1	localThrowable1	Throwable
    //   103	11	1	localObject2	Object
    //   9	21	2	localObject3	Object
    //   33	6	2	localThrowable2	Throwable
    //   45	67	2	localObject4	Object
    //   37	1	3	localCompanion	kotlin.Result.Companion
    // Exception table:
    //   from	to	target	type
    //   6	30	33	finally
    //   62	86	89	finally
  }
  
  public static final StackTraceElement artificialFrame(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("\b\b\b(");
    localStringBuilder.append(paramString);
    return new StackTraceElement(localStringBuilder.toString(), "\b", "\b", -1);
  }
  
  private static final <E extends Throwable> Pair<E, StackTraceElement[]> causeAndStacktrace(E paramE)
  {
    Throwable localThrowable = paramE.getCause();
    if ((localThrowable != null) && (Intrinsics.areEqual(localThrowable.getClass(), paramE.getClass())))
    {
      StackTraceElement[] arrayOfStackTraceElement = paramE.getStackTrace();
      Intrinsics.checkExpressionValueIsNotNull(arrayOfStackTraceElement, "currentTrace");
      int i = arrayOfStackTraceElement.length;
      for (int j = 0; j < i; j++)
      {
        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
        Intrinsics.checkExpressionValueIsNotNull(localStackTraceElement, "it");
        if (isArtificial(localStackTraceElement))
        {
          j = 1;
          break label82;
        }
      }
      j = 0;
      label82:
      if (j != 0) {
        paramE = TuplesKt.to(localThrowable, arrayOfStackTraceElement);
      } else {
        paramE = TuplesKt.to(paramE, new StackTraceElement[0]);
      }
    }
    else
    {
      paramE = TuplesKt.to(paramE, new StackTraceElement[0]);
    }
    return paramE;
  }
  
  private static final <E extends Throwable> E createFinalException(E paramE1, E paramE2, ArrayDeque<StackTraceElement> paramArrayDeque)
  {
    paramArrayDeque.addFirst(artificialFrame("Coroutine boundary"));
    StackTraceElement[] arrayOfStackTraceElement = paramE1.getStackTrace();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfStackTraceElement, "causeTrace");
    paramE1 = baseContinuationImplClassName;
    Intrinsics.checkExpressionValueIsNotNull(paramE1, "baseContinuationImplClassName");
    int i = frameIndex(arrayOfStackTraceElement, paramE1);
    int j = 0;
    if (i == -1)
    {
      paramE1 = ((Collection)paramArrayDeque).toArray(new StackTraceElement[0]);
      if (paramE1 != null)
      {
        paramE2.setStackTrace((StackTraceElement[])paramE1);
        return paramE2;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
    paramE1 = new StackTraceElement[paramArrayDeque.size() + i];
    for (int k = 0; k < i; k++) {
      paramE1[k] = arrayOfStackTraceElement[k];
    }
    paramArrayDeque = ((Iterable)paramArrayDeque).iterator();
    for (k = j; paramArrayDeque.hasNext(); k++) {
      paramE1[(i + k)] = ((StackTraceElement)paramArrayDeque.next());
    }
    paramE2.setStackTrace(paramE1);
    return paramE2;
  }
  
  private static final ArrayDeque<StackTraceElement> createStackTrace(CoroutineStackFrame paramCoroutineStackFrame)
  {
    ArrayDeque localArrayDeque = new ArrayDeque();
    StackTraceElement localStackTraceElement = paramCoroutineStackFrame.getStackTraceElement();
    CoroutineStackFrame localCoroutineStackFrame = paramCoroutineStackFrame;
    if (localStackTraceElement != null)
    {
      localArrayDeque.add(localStackTraceElement);
      localCoroutineStackFrame = paramCoroutineStackFrame;
    }
    for (;;)
    {
      paramCoroutineStackFrame = localCoroutineStackFrame;
      if (!(localCoroutineStackFrame instanceof CoroutineStackFrame)) {
        paramCoroutineStackFrame = null;
      }
      if (paramCoroutineStackFrame == null) {
        break;
      }
      paramCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
      if (paramCoroutineStackFrame == null) {
        break;
      }
      localStackTraceElement = paramCoroutineStackFrame.getStackTraceElement();
      localCoroutineStackFrame = paramCoroutineStackFrame;
      if (localStackTraceElement != null)
      {
        localArrayDeque.add(localStackTraceElement);
        localCoroutineStackFrame = paramCoroutineStackFrame;
      }
    }
    return localArrayDeque;
  }
  
  private static final boolean elementWiseEquals(StackTraceElement paramStackTraceElement1, StackTraceElement paramStackTraceElement2)
  {
    boolean bool;
    if ((paramStackTraceElement1.getLineNumber() == paramStackTraceElement2.getLineNumber()) && (Intrinsics.areEqual(paramStackTraceElement1.getMethodName(), paramStackTraceElement2.getMethodName())) && (Intrinsics.areEqual(paramStackTraceElement1.getFileName(), paramStackTraceElement2.getFileName())) && (Intrinsics.areEqual(paramStackTraceElement1.getClassName(), paramStackTraceElement2.getClassName()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final int frameIndex(StackTraceElement[] paramArrayOfStackTraceElement, String paramString)
  {
    int i = paramArrayOfStackTraceElement.length;
    for (int j = 0; j < i; j++) {
      if (Intrinsics.areEqual(paramString, paramArrayOfStackTraceElement[j].getClassName())) {
        return j;
      }
    }
    j = -1;
    return j;
  }
  
  public static final void initCause(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable1, "$this$initCause");
    Intrinsics.checkParameterIsNotNull(paramThrowable2, "cause");
    paramThrowable1.initCause(paramThrowable2);
  }
  
  public static final boolean isArtificial(StackTraceElement paramStackTraceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramStackTraceElement, "$this$isArtificial");
    paramStackTraceElement = paramStackTraceElement.getClassName();
    Intrinsics.checkExpressionValueIsNotNull(paramStackTraceElement, "className");
    return StringsKt.startsWith$default(paramStackTraceElement, "\b\b\b", false, 2, null);
  }
  
  private static final void mergeRecoveredTraces(StackTraceElement[] paramArrayOfStackTraceElement, ArrayDeque<StackTraceElement> paramArrayDeque)
  {
    int i = paramArrayOfStackTraceElement.length;
    for (int j = 0; j < i; j++) {
      if (isArtificial(paramArrayOfStackTraceElement[j])) {
        break label30;
      }
    }
    j = -1;
    label30:
    i = j + 1;
    j = paramArrayOfStackTraceElement.length - 1;
    if (j >= i) {
      for (;;)
      {
        StackTraceElement localStackTraceElement = paramArrayOfStackTraceElement[j];
        Object localObject = paramArrayDeque.getLast();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "result.last");
        if (elementWiseEquals(localStackTraceElement, (StackTraceElement)localObject)) {
          paramArrayDeque.removeLast();
        }
        paramArrayDeque.addFirst(paramArrayOfStackTraceElement[j]);
        if (j == i) {
          break;
        }
        j--;
      }
    }
  }
  
  public static final Object recoverAndThrow(Throwable paramThrowable, Continuation<?> paramContinuation)
  {
    if (DebugKt.getRECOVER_STACK_TRACES())
    {
      if (!(paramContinuation instanceof CoroutineStackFrame)) {
        throw paramThrowable;
      }
      throw access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)paramContinuation);
    }
    throw paramThrowable;
  }
  
  private static final Object recoverAndThrow$$forInline(Throwable paramThrowable, Continuation paramContinuation)
  {
    if (DebugKt.getRECOVER_STACK_TRACES())
    {
      InlineMarker.mark(0);
      if (!(paramContinuation instanceof CoroutineStackFrame)) {
        throw paramThrowable;
      }
      throw access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)paramContinuation);
    }
    throw paramThrowable;
  }
  
  private static final <E extends Throwable> E recoverFromStackFrame(E paramE, CoroutineStackFrame paramCoroutineStackFrame)
  {
    Object localObject = causeAndStacktrace(paramE);
    Throwable localThrowable1 = (Throwable)((Pair)localObject).component1();
    StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])((Pair)localObject).component2();
    Throwable localThrowable2 = ExceptionsConstuctorKt.tryCopyException(localThrowable1);
    localObject = paramE;
    if (localThrowable2 != null)
    {
      if ((Intrinsics.areEqual(localThrowable2.getMessage(), localThrowable1.getMessage()) ^ true)) {
        return paramE;
      }
      paramCoroutineStackFrame = createStackTrace(paramCoroutineStackFrame);
      if (paramCoroutineStackFrame.isEmpty()) {
        return paramE;
      }
      if (localThrowable1 != paramE) {
        mergeRecoveredTraces(arrayOfStackTraceElement, paramCoroutineStackFrame);
      }
      localObject = createFinalException(localThrowable1, localThrowable2, paramCoroutineStackFrame);
    }
    return localObject;
  }
  
  public static final <E extends Throwable> E recoverStackTrace(E paramE)
  {
    Intrinsics.checkParameterIsNotNull(paramE, "exception");
    if (!DebugKt.getRECOVER_STACK_TRACES()) {
      return paramE;
    }
    Throwable localThrowable = ExceptionsConstuctorKt.tryCopyException(paramE);
    if (localThrowable != null) {
      paramE = sanitizeStackTrace(localThrowable);
    }
    return paramE;
  }
  
  public static final <E extends Throwable> E recoverStackTrace(E paramE, Continuation<?> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramE, "exception");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    Object localObject = paramE;
    if (DebugKt.getRECOVER_STACK_TRACES()) {
      if (!(paramContinuation instanceof CoroutineStackFrame)) {
        localObject = paramE;
      } else {
        localObject = recoverFromStackFrame(paramE, (CoroutineStackFrame)paramContinuation);
      }
    }
    return localObject;
  }
  
  private static final <E extends Throwable> E sanitizeStackTrace(E paramE)
  {
    StackTraceElement[] arrayOfStackTraceElement1 = paramE.getStackTrace();
    int i = arrayOfStackTraceElement1.length;
    Intrinsics.checkExpressionValueIsNotNull(arrayOfStackTraceElement1, "stackTrace");
    Object localObject = stackTraceRecoveryClassName;
    Intrinsics.checkExpressionValueIsNotNull(localObject, "stackTraceRecoveryClassName");
    int j = frameIndex(arrayOfStackTraceElement1, (String)localObject);
    localObject = baseContinuationImplClassName;
    Intrinsics.checkExpressionValueIsNotNull(localObject, "baseContinuationImplClassName");
    int k = frameIndex(arrayOfStackTraceElement1, (String)localObject);
    int m = 0;
    if (k == -1) {
      k = 0;
    } else {
      k = i - k;
    }
    i = i - j - k;
    StackTraceElement[] arrayOfStackTraceElement2 = new StackTraceElement[i];
    for (k = m; k < i; k++)
    {
      if (k == 0) {
        localObject = artificialFrame("Coroutine boundary");
      } else {
        localObject = arrayOfStackTraceElement1[(j + 1 + k - 1)];
      }
      arrayOfStackTraceElement2[k] = localObject;
    }
    paramE.setStackTrace(arrayOfStackTraceElement2);
    return paramE;
  }
  
  public static final <E extends Throwable> E unwrap(E paramE)
  {
    Intrinsics.checkParameterIsNotNull(paramE, "exception");
    if (!DebugKt.getRECOVER_STACK_TRACES()) {
      return paramE;
    }
    Throwable localThrowable = paramE.getCause();
    if (localThrowable != null)
    {
      boolean bool = Intrinsics.areEqual(localThrowable.getClass(), paramE.getClass());
      int i = 1;
      if (!(bool ^ true))
      {
        StackTraceElement[] arrayOfStackTraceElement = paramE.getStackTrace();
        Intrinsics.checkExpressionValueIsNotNull(arrayOfStackTraceElement, "exception.stackTrace");
        int j = arrayOfStackTraceElement.length;
        for (int k = 0; k < j; k++)
        {
          StackTraceElement localStackTraceElement = arrayOfStackTraceElement[k];
          Intrinsics.checkExpressionValueIsNotNull(localStackTraceElement, "it");
          if (isArtificial(localStackTraceElement))
          {
            k = i;
            break label113;
          }
        }
        k = 0;
        label113:
        if (k != 0) {
          return localThrowable;
        }
      }
    }
    return paramE;
  }
}
