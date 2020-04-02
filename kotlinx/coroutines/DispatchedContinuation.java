package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000t\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\003\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\000\n\002\b\003\n\002\020\016\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\020\n\002\030\002\b\000\030\000*\006\b\000\020\001 \0002\b\022\004\022\0028\0000C2\00601j\002`22\b\022\004\022\0028\0000\004B\035\022\006\020\003\032\0020\002\022\f\020\005\032\b\022\004\022\0028\0000\004?\006\004\b\006\020\007J\033\020\n\032\004\030\0010\t2\n\020\005\032\006\022\002\b\0030\b?\006\004\b\n\020\013J\025\020\r\032\n\022\004\022\0028\000\030\0010\f?\006\004\b\r\020\016J\037\020\025\032\0020\0222\006\020\020\032\0020\0172\006\020\021\032\0028\000H\000?\006\004\b\023\020\024J\027\020\030\032\n\030\0010\026j\004\030\001`\027H\026?\006\004\b\030\020\031J\025\020\034\032\0020\0332\006\020\032\032\0020\t?\006\004\b\034\020\035J!\020 \032\0020\0222\f\020\037\032\b\022\004\022\0028\0000\036H?\b?\001\000?\006\004\b \020!J\020\020\"\032\0020\033H?\b?\006\004\b\"\020#J!\020$\032\0020\0222\f\020\037\032\b\022\004\022\0028\0000\036H?\b?\001\000?\006\004\b$\020!J \020%\032\0020\0222\f\020\037\032\b\022\004\022\0028\0000\036H\026?\001\000?\006\004\b%\020!J\021\020)\032\004\030\0010&H\020?\006\004\b'\020(J\017\020+\032\0020*H\026?\006\004\b+\020,R\036\020-\032\004\030\0010&8\000@\000X?\016?\006\f\n\004\b-\020.\022\004\b/\0200R$\0203\032\n\030\00101j\004\030\001`28\026@\026X?\004?\006\f\n\004\b3\0204\032\004\b5\0206R\026\020\020\032\0020\0178\026@\026X?\005?\006\006\032\004\b7\0208R\034\020\005\032\b\022\004\022\0028\0000\0048\006@\007X?\004?\006\006\n\004\b\005\0209R\026\020:\032\0020&8\000@\001X?\004?\006\006\n\004\b:\020.R\034\020=\032\b\022\004\022\0028\0000\0048P@\020X?\004?\006\006\032\004\b;\020<R\026\020\003\032\0020\0028\006@\007X?\004?\006\006\n\004\b\003\020>R\023\020?\032\0020\0338F@\006?\006\006\032\004\b?\020#R\031\020A\032\b\022\002\b\003\030\0010\f8F@\006?\006\006\032\004\b@\020\016?\002\004\n\002\b\031?\006B"}, d2={"Lkotlinx/coroutines/DispatchedContinuation;", "T", "Lkotlinx/coroutines/CoroutineDispatcher;", "dispatcher", "Lkotlin/coroutines/Continuation;", "continuation", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/CancellableContinuation;", "", "checkPostponedCancellation", "(Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/CancellableContinuationImpl;", "claimReusableCancellableContinuation", "()Lkotlinx/coroutines/CancellableContinuationImpl;", "Lkotlin/coroutines/CoroutineContext;", "context", "value", "", "dispatchYield$kotlinx_coroutines_core", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "dispatchYield", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "cause", "", "postponeCancellation", "(Ljava/lang/Throwable;)Z", "Lkotlin/Result;", "result", "resumeCancellableWith", "(Ljava/lang/Object;)V", "resumeCancelled", "()Z", "resumeUndispatchedWith", "resumeWith", "", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "", "toString", "()Ljava/lang/String;", "_state", "Ljava/lang/Object;", "_state$annotations", "()V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "callerFrame", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "countOrElement", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "delegate", "Lkotlinx/coroutines/CoroutineDispatcher;", "isReusable", "getReusableCancellableContinuation", "reusableCancellableContinuation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;"}, k=1, mv={1, 1, 16})
public final class DispatchedContinuation<T>
  extends DispatchedTask<T>
  implements CoroutineStackFrame, Continuation<T>
{
  private static final AtomicReferenceFieldUpdater _reusableCancellableContinuation$FU = AtomicReferenceFieldUpdater.newUpdater(DispatchedContinuation.class, Object.class, "_reusableCancellableContinuation");
  private volatile Object _reusableCancellableContinuation;
  public Object _state;
  private final CoroutineStackFrame callerFrame;
  public final Continuation<T> continuation;
  public final Object countOrElement;
  public final CoroutineDispatcher dispatcher;
  
  public DispatchedContinuation(CoroutineDispatcher paramCoroutineDispatcher, Continuation<? super T> paramContinuation)
  {
    super(0);
    this.dispatcher = paramCoroutineDispatcher;
    this.continuation = paramContinuation;
    this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
    paramContinuation = this.continuation;
    paramCoroutineDispatcher = paramContinuation;
    if (!(paramContinuation instanceof CoroutineStackFrame)) {
      paramCoroutineDispatcher = null;
    }
    this.callerFrame = ((CoroutineStackFrame)paramCoroutineDispatcher);
    this.countOrElement = ThreadContextKt.threadContextElements(getContext());
    this._reusableCancellableContinuation = null;
  }
  
  public final Throwable checkPostponedCancellation(CancellableContinuation<?> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
    Object localObject;
    do
    {
      localObject = this._reusableCancellableContinuation;
      if (localObject != DispatchedContinuationKt.REUSABLE_CLAIMED) {
        break;
      }
    } while (!_reusableCancellableContinuation$FU.compareAndSet(this, DispatchedContinuationKt.REUSABLE_CLAIMED, paramCancellableContinuation));
    return null;
    if (localObject == null) {
      return null;
    }
    if ((localObject instanceof Throwable))
    {
      if (_reusableCancellableContinuation$FU.compareAndSet(this, localObject, null)) {
        return (Throwable)localObject;
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    paramCancellableContinuation = new StringBuilder();
    paramCancellableContinuation.append("Inconsistent state ");
    paramCancellableContinuation.append(localObject);
    throw ((Throwable)new IllegalStateException(paramCancellableContinuation.toString().toString()));
  }
  
  public final CancellableContinuationImpl<T> claimReusableCancellableContinuation()
  {
    Object localObject;
    do
    {
      localObject = this._reusableCancellableContinuation;
      if (localObject == null)
      {
        this._reusableCancellableContinuation = DispatchedContinuationKt.REUSABLE_CLAIMED;
        return null;
      }
      if (!(localObject instanceof CancellableContinuationImpl)) {
        break;
      }
    } while (!_reusableCancellableContinuation$FU.compareAndSet(this, localObject, DispatchedContinuationKt.REUSABLE_CLAIMED));
    return (CancellableContinuationImpl)localObject;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Inconsistent state ");
    localStringBuilder.append(localObject);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public final void dispatchYield$kotlinx_coroutines_core(CoroutineContext paramCoroutineContext, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    this._state = paramT;
    this.resumeMode = 1;
    this.dispatcher.dispatchYield(paramCoroutineContext, (Runnable)this);
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    return this.callerFrame;
  }
  
  public CoroutineContext getContext()
  {
    return this.continuation.getContext();
  }
  
  public Continuation<T> getDelegate$kotlinx_coroutines_core()
  {
    return (Continuation)this;
  }
  
  public final CancellableContinuationImpl<?> getReusableCancellableContinuation()
  {
    Object localObject1 = this._reusableCancellableContinuation;
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof CancellableContinuationImpl)) {
      localObject2 = null;
    }
    return (CancellableContinuationImpl)localObject2;
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public final boolean isReusable()
  {
    boolean bool;
    if (this._reusableCancellableContinuation != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean postponeCancellation(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    Object localObject;
    do
    {
      do
      {
        localObject = this._reusableCancellableContinuation;
        if (!Intrinsics.areEqual(localObject, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
          break;
        }
      } while (!_reusableCancellableContinuation$FU.compareAndSet(this, DispatchedContinuationKt.REUSABLE_CLAIMED, paramThrowable));
      return true;
      if ((localObject instanceof Throwable)) {
        return true;
      }
    } while (!_reusableCancellableContinuation$FU.compareAndSet(this, localObject, null));
    return false;
  }
  
  public final void resumeCancellableWith(Object paramObject)
  {
    Object localObject1 = CompletedExceptionallyKt.toState(paramObject);
    EventLoop localEventLoop;
    if (this.dispatcher.isDispatchNeeded(getContext()))
    {
      this._state = localObject1;
      this.resumeMode = 1;
      this.dispatcher.dispatch(getContext(), (Runnable)this);
    }
    else
    {
      localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
      if (localEventLoop.isUnconfinedLoopActive())
      {
        this._state = localObject1;
        this.resumeMode = 1;
        localEventLoop.dispatchUnconfined((DispatchedTask)this);
      }
      else
      {
        localObject1 = (DispatchedTask)this;
        localEventLoop.incrementUseCount(true);
        try
        {
          Object localObject2 = (Job)getContext().get((CoroutineContext.Key)Job.Key);
          Object localObject3;
          int i;
          if ((localObject2 != null) && (!((Job)localObject2).isActive()))
          {
            localObject2 = (Throwable)((Job)localObject2).getCancellationException();
            localObject3 = Result.Companion;
            resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject2)));
            i = 1;
          }
          else
          {
            i = 0;
          }
          if (i == 0)
          {
            localObject2 = getContext();
            localObject3 = ThreadContextKt.updateThreadContext((CoroutineContext)localObject2, this.countOrElement);
          }
          for (;;)
          {
            boolean bool;
            try
            {
              this.continuation.resumeWith(paramObject);
              paramObject = Unit.INSTANCE;
              InlineMarker.finallyStart(1);
              ThreadContextKt.restoreThreadContext((CoroutineContext)localObject2, localObject3);
              InlineMarker.finallyEnd(1);
            }
            finally
            {
              InlineMarker.finallyStart(1);
              ThreadContextKt.restoreThreadContext((CoroutineContext)localObject2, localObject3);
              InlineMarker.finallyEnd(1);
            }
          }
        }
        finally {}
      }
    }
    try
    {
      ((DispatchedTask)localObject1).handleFatalException$kotlinx_coroutines_core(paramObject, null);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public final boolean resumeCancelled()
  {
    Object localObject = (Job)getContext().get((CoroutineContext.Key)Job.Key);
    if ((localObject != null) && (!((Job)localObject).isActive()))
    {
      localObject = (Throwable)((Job)localObject).getCancellationException();
      Result.Companion localCompanion = Result.Companion;
      resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject)));
      return true;
    }
    return false;
  }
  
  public final void resumeUndispatchedWith(Object paramObject)
  {
    CoroutineContext localCoroutineContext = getContext();
    Object localObject = ThreadContextKt.updateThreadContext(localCoroutineContext, this.countOrElement);
    try
    {
      this.continuation.resumeWith(paramObject);
      paramObject = Unit.INSTANCE;
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public void resumeWith(Object paramObject)
  {
    localObject1 = this.continuation.getContext();
    localObject2 = CompletedExceptionallyKt.toState(paramObject);
    if (this.dispatcher.isDispatchNeeded((CoroutineContext)localObject1))
    {
      this._state = localObject2;
      this.resumeMode = 0;
      this.dispatcher.dispatch((CoroutineContext)localObject1, (Runnable)this);
    }
    else
    {
      localObject1 = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
      if (((EventLoop)localObject1).isUnconfinedLoopActive())
      {
        this._state = localObject2;
        this.resumeMode = 0;
        ((EventLoop)localObject1).dispatchUnconfined((DispatchedTask)this);
      }
      else
      {
        localObject2 = (DispatchedTask)this;
        ((EventLoop)localObject1).incrementUseCount(true);
        try
        {
          CoroutineContext localCoroutineContext = getContext();
          Object localObject3 = ThreadContextKt.updateThreadContext(localCoroutineContext, this.countOrElement);
          try
          {
            ((DispatchedTask)localObject2).handleFatalException$kotlinx_coroutines_core(paramObject, null);
            return;
          }
          finally
          {
            ((EventLoop)localObject1).decrementUseCount(true);
          }
        }
        finally
        {
          try
          {
            this.continuation.resumeWith(paramObject);
            paramObject = Unit.INSTANCE;
            ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject3);
            while (((EventLoop)localObject1).processUnconfinedEvent()) {}
          }
          finally
          {
            ThreadContextKt.restoreThreadContext(localCoroutineContext, localObject3);
          }
        }
      }
    }
  }
  
  public Object takeState$kotlinx_coroutines_core()
  {
    Object localObject = this._state;
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (localObject != DispatchedContinuationKt.access$getUNDEFINED$p()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
    return localObject;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DispatchedContinuation[");
    localStringBuilder.append(this.dispatcher);
    localStringBuilder.append(", ");
    localStringBuilder.append(DebugStringsKt.toDebugString(this.continuation));
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
