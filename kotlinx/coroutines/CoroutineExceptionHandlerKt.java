package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\003\n\002\020\002\n\002\b\007\032%\020\000\032\0020\0012\032\b\004\020\002\032\024\022\004\022\0020\004\022\004\022\0020\005\022\004\022\0020\0060\003H?\b\032\030\020\007\032\0020\0062\006\020\b\032\0020\0042\006\020\t\032\0020\005H\007\032\030\020\n\032\0020\0052\006\020\013\032\0020\0052\006\020\f\032\0020\005H\000?\006\r"}, d2={"CoroutineExceptionHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handler", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext;", "", "", "handleCoroutineException", "context", "exception", "handlerException", "originalException", "thrownException", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CoroutineExceptionHandlerKt
{
  public static final CoroutineExceptionHandler CoroutineExceptionHandler(Function2<? super CoroutineContext, ? super Throwable, Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "handler");
    (CoroutineExceptionHandler)new AbstractCoroutineContextElement(paramFunction2)
    {
      public void handleException(CoroutineContext paramAnonymousCoroutineContext, Throwable paramAnonymousThrowable)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousCoroutineContext, "context");
        Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "exception");
        this.$handler.invoke(paramAnonymousCoroutineContext, paramAnonymousThrowable);
      }
    };
  }
  
  public static final void handleCoroutineException(CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    try
    {
      CoroutineExceptionHandler localCoroutineExceptionHandler = (CoroutineExceptionHandler)paramCoroutineContext.get((CoroutineContext.Key)CoroutineExceptionHandler.Key);
      if (localCoroutineExceptionHandler != null)
      {
        localCoroutineExceptionHandler.handleException(paramCoroutineContext, paramThrowable);
        return;
      }
      CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(paramCoroutineContext, paramThrowable);
      return;
    }
    finally
    {
      CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(paramCoroutineContext, handlerException(paramThrowable, localThrowable));
    }
  }
  
  public static final Throwable handlerException(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable1, "originalException");
    Intrinsics.checkParameterIsNotNull(paramThrowable2, "thrownException");
    if (paramThrowable1 == paramThrowable2) {
      return paramThrowable1;
    }
    paramThrowable2 = (Throwable)new RuntimeException("Exception while trying to handle coroutine exception", paramThrowable2);
    ExceptionsKt.addSuppressed(paramThrowable2, paramThrowable1);
    return paramThrowable2;
  }
}
