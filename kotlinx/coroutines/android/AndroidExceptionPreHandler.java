package kotlinx.coroutines.android;

import android.os.Build.VERSION;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineExceptionHandler;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\002\b\002\b\001\030\0002\0020\0012\0020\0022\n\022\006\022\004\030\0010\0040\003B\005?\006\002\020\005J\030\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\020H\026J\013\020\021\032\004\030\0010\004H?\002R\035\020\006\032\004\030\0010\0048BX??\002?\006\f\n\004\b\t\020\n\032\004\b\007\020\b?\006\022"}, d2={"Lkotlinx/coroutines/android/AndroidExceptionPreHandler;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "Lkotlin/Function0;", "Ljava/lang/reflect/Method;", "()V", "preHandler", "getPreHandler", "()Ljava/lang/reflect/Method;", "preHandler$delegate", "Lkotlin/Lazy;", "handleException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "invoke", "kotlinx-coroutines-android"}, k=1, mv={1, 1, 16})
public final class AndroidExceptionPreHandler
  extends AbstractCoroutineContextElement
  implements CoroutineExceptionHandler, Function0<Method>
{
  private final Lazy preHandler$delegate = LazyKt.lazy((Function0)this);
  
  public AndroidExceptionPreHandler()
  {
    super((CoroutineContext.Key)CoroutineExceptionHandler.Key);
  }
  
  private final Method getPreHandler()
  {
    Lazy localLazy = this.preHandler$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Method)localLazy.getValue();
  }
  
  public void handleException(CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    Thread localThread = Thread.currentThread();
    if (Build.VERSION.SDK_INT >= 28)
    {
      Intrinsics.checkExpressionValueIsNotNull(localThread, "thread");
      localThread.getUncaughtExceptionHandler().uncaughtException(localThread, paramThrowable);
    }
    else
    {
      paramCoroutineContext = getPreHandler();
      Object localObject = null;
      if (paramCoroutineContext != null) {
        paramCoroutineContext = paramCoroutineContext.invoke(null, new Object[0]);
      } else {
        paramCoroutineContext = null;
      }
      if (!(paramCoroutineContext instanceof Thread.UncaughtExceptionHandler)) {
        paramCoroutineContext = localObject;
      }
      paramCoroutineContext = (Thread.UncaughtExceptionHandler)paramCoroutineContext;
      if (paramCoroutineContext != null) {
        paramCoroutineContext.uncaughtException(localThread, paramThrowable);
      }
    }
  }
  
  public Method invoke()
  {
    Object localObject1 = null;
    int i = 0;
    try
    {
      Method localMethod = Thread.class.getDeclaredMethod("getUncaughtExceptionPreHandler", new Class[0]);
      Intrinsics.checkExpressionValueIsNotNull(localMethod, "it");
      int j = i;
      if (Modifier.isPublic(localMethod.getModifiers()))
      {
        boolean bool = Modifier.isStatic(localMethod.getModifiers());
        j = i;
        if (bool) {
          j = 1;
        }
      }
      if (j != 0) {
        localObject1 = localMethod;
      }
    }
    finally
    {
      for (;;) {}
    }
    return localObject1;
  }
}
