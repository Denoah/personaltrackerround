package kotlin.jdk7;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\002\032\030\020\000\032\0020\001*\004\030\0010\0022\b\020\003\032\004\030\0010\004H\001\0328\020\005\032\002H\006\"\n\b\000\020\007*\004\030\0010\002\"\004\b\001\020\006*\002H\0072\022\020\b\032\016\022\004\022\002H\007\022\004\022\002H\0060\tH?\b?\006\002\020\n?\006\013"}, d2={"closeFinally", "", "Ljava/lang/AutoCloseable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, k=2, mv={1, 1, 15}, pn="kotlin")
public final class AutoCloseableKt
{
  /* Error */
  public static final void closeFinally(AutoCloseable paramAutoCloseable, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +6 -> 7
    //   4: goto +31 -> 35
    //   7: aload_1
    //   8: ifnonnull +12 -> 20
    //   11: aload_0
    //   12: invokeinterface 36 1 0
    //   17: goto +18 -> 35
    //   20: aload_0
    //   21: invokeinterface 36 1 0
    //   26: goto +9 -> 35
    //   29: astore_0
    //   30: aload_1
    //   31: aload_0
    //   32: invokevirtual 42	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   35: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	36	0	paramAutoCloseable	AutoCloseable
    //   0	36	1	paramThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   20	26	29	finally
  }
  
  private static final <T extends AutoCloseable, R> R use(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    Throwable localThrowable = (Throwable)null;
    try
    {
      paramFunction1 = paramFunction1.invoke(paramT);
      InlineMarker.finallyStart(1);
      closeFinally(paramT, localThrowable);
      InlineMarker.finallyEnd(1);
      return paramFunction1;
    }
    finally
    {
      try
      {
        throw paramFunction1;
      }
      finally
      {
        InlineMarker.finallyStart(1);
        closeFinally(paramT, paramFunction1);
        InlineMarker.finallyEnd(1);
      }
    }
  }
}
