package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\000\n\002\020\000\n\000\n\002\020\003\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\017\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\001\032+\020\004\032\b\022\004\022\002H\0060\005\"\004\b\000\020\0062\f\020\007\032\b\022\004\022\002H\0060\bH?\b?\001\000?\006\002\020\t\032?\001\020\n\032\002H\006\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\f\032\035\022\023\022\021H\013?\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\r2!\020\021\032\035\022\023\022\0210\003?\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH?\b?\001\000?\002\024\n\b\b\001\022\002\020\001 \000\n\b\b\001\022\002\020\002 \000?\006\002\020\022\0323\020\023\032\002H\006\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052\006\020\024\032\002H\006H?\b?\001\000?\006\002\020\025\032[\020\026\032\002H\006\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\021\032\035\022\023\022\0210\003?\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \000?\006\002\020\027\032!\020\030\032\002H\013\"\004\b\000\020\013*\b\022\004\022\002H\0130\005H?\b?\001\000?\006\002\020\031\032]\020\032\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\021H\013?\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\rH?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \000?\006\002\020\027\032P\020\034\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\004\b\001\020\013*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\021H\013?\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\002H\0060\rH?\b?\001\000?\006\002\020\027\032W\020\021\032\b\022\004\022\002H\0130\005\"\004\b\000\020\013*\b\022\004\022\002H\0130\0052!\020\035\032\035\022\023\022\0210\003?\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\0020\0360\rH?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \000?\006\002\020\027\032W\020\f\032\b\022\004\022\002H\0130\005\"\004\b\000\020\013*\b\022\004\022\002H\0130\0052!\020\035\032\035\022\023\022\021H\013?\006\f\b\016\022\b\b\017\022\004\b\b(\020\022\004\022\0020\0360\rH?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \000?\006\002\020\027\032a\020\037\032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\0210\003?\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \000?\006\002\020\027\032T\020 \032\b\022\004\022\002H\0060\005\"\004\b\000\020\006\"\b\b\001\020\013*\002H\006*\b\022\004\022\002H\0130\0052!\020\033\032\035\022\023\022\0210\003?\006\f\b\016\022\b\b\017\022\004\b\b(\002\022\004\022\002H\0060\rH?\b?\001\000?\006\002\020\027\032@\020\004\032\b\022\004\022\002H\0060\005\"\004\b\000\020\013\"\004\b\001\020\006*\002H\0132\027\020\007\032\023\022\004\022\002H\013\022\004\022\002H\0060\r?\006\002\b!H?\b?\001\000?\006\002\020\027\032\030\020\"\032\0020\036*\006\022\002\b\0030\005H\001?\001\000?\006\002\020#?\002\004\n\002\b\031?\006$"}, d2={"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ResultKt
{
  public static final Object createFailure(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    return new Result.Failure(paramThrowable);
  }
  
  private static final <R, T> R fold(Object paramObject, Function1<? super T, ? extends R> paramFunction1, Function1<? super Throwable, ? extends R> paramFunction11)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable == null) {
      paramObject = paramFunction1.invoke(paramObject);
    } else {
      paramObject = paramFunction11.invoke(localThrowable);
    }
    return paramObject;
  }
  
  private static final <R, T extends R> R getOrDefault(Object paramObject, R paramR)
  {
    if (Result.isFailure-impl(paramObject)) {
      return paramR;
    }
    return paramObject;
  }
  
  private static final <R, T extends R> R getOrElse(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramObject = paramFunction1.invoke(localThrowable);
    }
    return paramObject;
  }
  
  private static final <T> T getOrThrow(Object paramObject)
  {
    throwOnFailure(paramObject);
    return paramObject;
  }
  
  private static final <R, T> Object map(Object paramObject, Function1<? super T, ? extends R> paramFunction1)
  {
    if (Result.isSuccess-impl(paramObject))
    {
      Result.Companion localCompanion = Result.Companion;
      paramObject = Result.constructor-impl(paramFunction1.invoke(paramObject));
    }
    else
    {
      paramObject = Result.constructor-impl(paramObject);
    }
    return paramObject;
  }
  
  /* Error */
  private static final <R, T> Object mapCatching(Object paramObject, Function1<? super T, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 91	kotlin/Result:isSuccess-impl	(Ljava/lang/Object;)Z
    //   4: ifeq +37 -> 41
    //   7: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   10: astore_2
    //   11: aload_1
    //   12: aload_0
    //   13: invokeinterface 77 2 0
    //   18: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: astore_0
    //   22: goto +24 -> 46
    //   25: astore_0
    //   26: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   29: astore_1
    //   30: aload_0
    //   31: invokestatic 101	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   34: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   37: astore_0
    //   38: goto +8 -> 46
    //   41: aload_0
    //   42: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   45: astore_0
    //   46: aload_0
    //   47: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	paramObject	Object
    //   0	48	1	paramFunction1	Function1<? super T, ? extends R>
    //   10	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   7	22	25	finally
  }
  
  private static final <T> Object onFailure(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramFunction1.invoke(localThrowable);
    }
    return paramObject;
  }
  
  private static final <T> Object onSuccess(Object paramObject, Function1<? super T, Unit> paramFunction1)
  {
    if (Result.isSuccess-impl(paramObject)) {
      paramFunction1.invoke(paramObject);
    }
    return paramObject;
  }
  
  private static final <R, T extends R> Object recover(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null)
    {
      paramObject = Result.Companion;
      paramObject = Result.constructor-impl(paramFunction1.invoke(localThrowable));
    }
    return paramObject;
  }
  
  /* Error */
  private static final <R, T extends R> Object recoverCatching(Object paramObject, Function1<? super Throwable, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 72	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnonnull +6 -> 12
    //   9: goto +34 -> 43
    //   12: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   15: astore_0
    //   16: aload_1
    //   17: aload_2
    //   18: invokeinterface 77 2 0
    //   23: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   26: astore_0
    //   27: goto +16 -> 43
    //   30: astore_1
    //   31: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   34: astore_0
    //   35: aload_1
    //   36: invokestatic 101	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   39: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   42: astore_0
    //   43: aload_0
    //   44: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	paramObject	Object
    //   0	45	1	paramFunction1	Function1<? super Throwable, ? extends R>
    //   4	14	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   12	27	30	finally
  }
  
  /* Error */
  private static final <T, R> Object runCatching(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   3: astore_2
    //   4: aload_1
    //   5: aload_0
    //   6: invokeinterface 77 2 0
    //   11: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   14: astore_0
    //   15: goto +16 -> 31
    //   18: astore_0
    //   19: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   22: astore_1
    //   23: aload_0
    //   24: invokestatic 101	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   27: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   30: astore_0
    //   31: aload_0
    //   32: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	33	0	paramT	T
    //   0	33	1	paramFunction1	Function1<? super T, ? extends R>
    //   3	1	2	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   0	15	18	finally
  }
  
  /* Error */
  private static final <R> Object runCatching(kotlin.jvm.functions.Function0<? extends R> paramFunction0)
  {
    // Byte code:
    //   0: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   3: astore_1
    //   4: aload_0
    //   5: invokeinterface 110 1 0
    //   10: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   13: astore_0
    //   14: goto +16 -> 30
    //   17: astore_0
    //   18: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   21: astore_1
    //   22: aload_0
    //   23: invokestatic 101	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   26: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   29: astore_0
    //   30: aload_0
    //   31: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	32	0	paramFunction0	kotlin.jvm.functions.Function0<? extends R>
    //   3	19	1	localCompanion	Result.Companion
    // Exception table:
    //   from	to	target	type
    //   0	14	17	finally
  }
  
  public static final void throwOnFailure(Object paramObject)
  {
    if (!(paramObject instanceof Result.Failure)) {
      return;
    }
    throw ((Result.Failure)paramObject).exception;
  }
}
