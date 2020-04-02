package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;

@Metadata(bv={1, 0, 3}, d1={"kotlinx/coroutines/JobKt__FutureKt", "kotlinx/coroutines/JobKt__JobKt"}, k=4, mv={1, 1, 16})
public final class JobKt
{
  public static final DisposableHandle DisposableHandle(Function0<Unit> paramFunction0)
  {
    return JobKt__JobKt.DisposableHandle(paramFunction0);
  }
  
  public static final CompletableJob Job(Job paramJob)
  {
    return JobKt__JobKt.Job(paramJob);
  }
  
  public static final void cancel(CoroutineContext paramCoroutineContext, CancellationException paramCancellationException)
  {
    JobKt__JobKt.cancel(paramCoroutineContext, paramCancellationException);
  }
  
  public static final void cancel(Job paramJob, String paramString, Throwable paramThrowable)
  {
    JobKt__JobKt.cancel(paramJob, paramString, paramThrowable);
  }
  
  public static final Object cancelAndJoin(Job paramJob, Continuation<? super Unit> paramContinuation)
  {
    return JobKt__JobKt.cancelAndJoin(paramJob, paramContinuation);
  }
  
  public static final void cancelChildren(CoroutineContext paramCoroutineContext, CancellationException paramCancellationException)
  {
    JobKt__JobKt.cancelChildren(paramCoroutineContext, paramCancellationException);
  }
  
  public static final void cancelChildren(Job paramJob, CancellationException paramCancellationException)
  {
    JobKt__JobKt.cancelChildren(paramJob, paramCancellationException);
  }
  
  public static final void cancelFutureOnCancellation(CancellableContinuation<?> paramCancellableContinuation, Future<?> paramFuture)
  {
    JobKt__FutureKt.cancelFutureOnCancellation(paramCancellableContinuation, paramFuture);
  }
  
  public static final DisposableHandle cancelFutureOnCompletion(Job paramJob, Future<?> paramFuture)
  {
    return JobKt__FutureKt.cancelFutureOnCompletion(paramJob, paramFuture);
  }
  
  public static final DisposableHandle disposeOnCompletion(Job paramJob, DisposableHandle paramDisposableHandle)
  {
    return JobKt__JobKt.disposeOnCompletion(paramJob, paramDisposableHandle);
  }
  
  public static final void ensureActive(CoroutineContext paramCoroutineContext)
  {
    JobKt__JobKt.ensureActive(paramCoroutineContext);
  }
  
  public static final void ensureActive(Job paramJob)
  {
    JobKt__JobKt.ensureActive(paramJob);
  }
  
  public static final boolean isActive(CoroutineContext paramCoroutineContext)
  {
    return JobKt__JobKt.isActive(paramCoroutineContext);
  }
}
