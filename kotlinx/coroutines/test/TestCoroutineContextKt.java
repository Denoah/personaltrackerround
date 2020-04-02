package kotlinx.coroutines.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\032+\020\000\032\0020\0012\b\b\002\020\002\032\0020\0032\027\020\004\032\023\022\004\022\0020\003\022\004\022\0020\0010\005?\006\002\b\006H\007?\006\007"}, d2={"withTestContext", "", "testContext", "Lkotlinx/coroutines/test/TestCoroutineContext;", "testBody", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class TestCoroutineContextKt
{
  @Deprecated(level=DeprecationLevel.WARNING, message="This API has been deprecated to integrate with Structured Concurrency.", replaceWith=@ReplaceWith(expression="testContext.runBlockingTest(testBody)", imports={"kotlin.coroutines.test"}))
  public static final void withTestContext(TestCoroutineContext paramTestCoroutineContext, Function1<? super TestCoroutineContext, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTestCoroutineContext, "testContext");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "testBody");
    paramFunction1.invoke(paramTestCoroutineContext);
    paramFunction1 = (Iterable)paramTestCoroutineContext.getExceptions();
    boolean bool = paramFunction1 instanceof Collection;
    int i = 1;
    int j;
    if ((bool) && (((Collection)paramFunction1).isEmpty()))
    {
      j = i;
    }
    else
    {
      paramFunction1 = paramFunction1.iterator();
      do
      {
        j = i;
        if (!paramFunction1.hasNext()) {
          break;
        }
      } while (((Throwable)paramFunction1.next() instanceof CancellationException));
      j = 0;
    }
    if (j != 0) {
      return;
    }
    paramFunction1 = new StringBuilder();
    paramFunction1.append("Coroutine encountered unhandled exceptions:\n");
    paramFunction1.append(paramTestCoroutineContext.getExceptions());
    throw ((Throwable)new AssertionError(paramFunction1.toString()));
  }
}
