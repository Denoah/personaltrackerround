package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\020\002\n\002\b\005\n\002\020\013\n\002\b\004\n\002\030\002\b\002\030\000*\006\b\000\020\001 \0002\b\022\004\022\0028\0000\025B\035\022\006\020\003\032\0020\002\022\f\020\005\032\b\022\004\022\0028\0000\004?\006\004\b\006\020\007J\031\020\013\032\0020\n2\b\020\t\032\004\030\0010\bH\024?\006\004\b\013\020\fJ\031\020\r\032\0020\n2\b\020\t\032\004\030\0010\bH\024?\006\004\b\r\020\fJ\017\020\016\032\004\030\0010\b?\006\004\b\016\020\017J\017\020\021\032\0020\020H\002?\006\004\b\021\020\022J\017\020\023\032\0020\020H\002?\006\004\b\023\020\022?\006\024"}, d2={"Lkotlinx/coroutines/DispatchedCoroutine;", "T", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "", "state", "", "afterCompletion", "(Ljava/lang/Object;)V", "afterResume", "getResult", "()Ljava/lang/Object;", "", "tryResume", "()Z", "trySuspend", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/ScopeCoroutine;"}, k=1, mv={1, 1, 16})
final class DispatchedCoroutine<T>
  extends ScopeCoroutine<T>
{
  private static final AtomicIntegerFieldUpdater _decision$FU = AtomicIntegerFieldUpdater.newUpdater(DispatchedCoroutine.class, "_decision");
  private volatile int _decision = 0;
  
  public DispatchedCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, paramContinuation);
  }
  
  private final boolean tryResume()
  {
    do
    {
      int i = this._decision;
      if (i != 0)
      {
        if (i == 1) {
          return false;
        }
        throw ((Throwable)new IllegalStateException("Already resumed".toString()));
      }
    } while (!_decision$FU.compareAndSet(this, 0, 2));
    return true;
  }
  
  private final boolean trySuspend()
  {
    do
    {
      int i = this._decision;
      if (i != 0)
      {
        if (i == 2) {
          return false;
        }
        throw ((Throwable)new IllegalStateException("Already suspended".toString()));
      }
    } while (!_decision$FU.compareAndSet(this, 0, 1));
    return true;
  }
  
  protected void afterCompletion(Object paramObject)
  {
    afterResume(paramObject);
  }
  
  protected void afterResume(Object paramObject)
  {
    if (tryResume()) {
      return;
    }
    DispatchedContinuationKt.resumeCancellableWith(IntrinsicsKt.intercepted(this.uCont), CompletedExceptionallyKt.recoverResult(paramObject, this.uCont));
  }
  
  public final Object getResult()
  {
    if (trySuspend()) {
      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
    Object localObject = JobSupportKt.unboxState(getState$kotlinx_coroutines_core());
    if (!(localObject instanceof CompletedExceptionally)) {
      return localObject;
    }
    throw ((CompletedExceptionally)localObject).cause;
  }
}
