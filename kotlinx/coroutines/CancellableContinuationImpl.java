package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\000\n\000\n\002\020\002\n\002\b\002\n\002\020\003\n\000\n\002\020\013\n\002\b\022\n\002\030\002\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\020\016\n\002\b\013\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\r\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\007\n\002\030\002\n\002\030\002\b\021\030\000*\006\b\000\020\001 \0002\b\022\004\022\0028\0000{2\b\022\004\022\0028\0000|2\0060cj\002`dB\035\022\f\020\003\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\004?\006\004\b\006\020\007J\031\020\013\032\0020\n2\b\020\t\032\004\030\0010\bH\002?\006\004\b\013\020\fJ\031\020\020\032\0020\0172\b\020\016\032\004\030\0010\rH\026?\006\004\b\020\020\021J\027\020\022\032\0020\0172\006\020\016\032\0020\rH\002?\006\004\b\022\020\021J!\020\026\032\0020\n2\b\020\023\032\004\030\0010\b2\006\020\016\032\0020\rH\020?\006\004\b\024\020\025J\017\020\027\032\0020\017H\002?\006\004\b\027\020\030J\027\020\032\032\0020\n2\006\020\031\032\0020\bH\026?\006\004\b\032\020\fJ\017\020\035\032\0020\nH\000?\006\004\b\033\020\034J\017\020\036\032\0020\nH\002?\006\004\b\036\020\034J\027\020 \032\0020\n2\006\020\037\032\0020\004H\002?\006\004\b \020!J\027\020$\032\0020\r2\006\020#\032\0020\"H\026?\006\004\b$\020%J\021\020&\032\004\030\0010\bH\001?\006\004\b&\020'J\027\020*\032\n\030\0010(j\004\030\001`)H\026?\006\004\b*\020+J\037\020.\032\0028\001\"\004\b\001\020\0012\b\020\023\032\004\030\0010\bH\020?\006\004\b,\020-J\017\020/\032\0020\nH\026?\006\004\b/\020\034J\036\0202\032\0020\n2\f\0201\032\b\022\004\022\0020\n00H?\b?\006\004\b2\0203J8\0209\032\0020\n2'\0208\032#\022\025\022\023\030\0010\r?\006\f\b5\022\b\b6\022\004\b\b(\016\022\004\022\0020\n04j\002`7H\026?\006\004\b9\020:J\017\020;\032\0020\017H\002?\006\004\b;\020\030J8\020=\032\0020<2'\0208\032#\022\025\022\023\030\0010\r?\006\f\b5\022\b\b6\022\004\b\b(\016\022\004\022\0020\n04j\002`7H\002?\006\004\b=\020>JB\020?\032\0020\n2'\0208\032#\022\025\022\023\030\0010\r?\006\f\b5\022\b\b6\022\004\b\b(\016\022\004\022\0020\n04j\002`72\b\020\023\032\004\030\0010\bH\002?\006\004\b?\020@J\017\020B\032\0020AH\024?\006\004\bB\020CJ\027\020F\032\0020\n2\006\020\016\032\0020\rH\000?\006\004\bD\020EJ\017\020H\032\0020\017H\000?\006\004\bG\020\030J:\020K\032\0020\n2\006\020I\032\0028\0002!\020J\032\035\022\023\022\0210\r?\006\f\b5\022\b\b6\022\004\b\b(\016\022\004\022\0020\n04H\026?\006\004\bK\020LJ#\020N\032\004\030\0010M2\b\020\t\032\004\030\0010\b2\006\020\005\032\0020\004H\002?\006\004\bN\020OJ \020R\032\0020\n2\f\020Q\032\b\022\004\022\0028\0000PH\026?\001\000?\006\004\bR\020\fJ\017\020S\032\0020\nH\002?\006\004\bS\020\034J\021\020U\032\004\030\0010\bH\020?\006\004\bT\020'J\017\020V\032\0020AH\026?\006\004\bV\020CJ\017\020W\032\0020\017H\002?\006\004\bW\020\030J#\020W\032\004\030\0010\b2\006\020I\032\0028\0002\b\020X\032\004\030\0010\bH\026?\006\004\bW\020YJ\031\020[\032\004\030\0010\b2\006\020Z\032\0020\rH\026?\006\004\b[\020\\J\017\020]\032\0020\017H\002?\006\004\b]\020\030J\033\020_\032\0020\n*\0020^2\006\020I\032\0028\000H\026?\006\004\b_\020`J\033\020a\032\0020\n*\0020^2\006\020Z\032\0020\rH\026?\006\004\ba\020bR\036\020g\032\n\030\0010cj\004\030\001`d8V@\026X?\004?\006\006\032\004\be\020fR\034\020i\032\0020h8\026@\026X?\004?\006\f\n\004\bi\020j\032\004\bk\020lR\"\020\003\032\b\022\004\022\0028\0000\0028\000@\000X?\004?\006\f\n\004\b\003\020m\032\004\bn\020oR\026\020p\032\0020\0178V@\026X?\004?\006\006\032\004\bp\020\030R\026\020q\032\0020\0178V@\026X?\004?\006\006\032\004\bq\020\030R\026\020r\032\0020\0178V@\026X?\004?\006\006\032\004\br\020\030R(\020x\032\004\030\0010s2\b\020I\032\004\030\0010s8B@BX?\016?\006\f\032\004\bt\020u\"\004\bv\020wR\030\020\023\032\004\030\0010\b8@@\000X?\004?\006\006\032\004\by\020'?\002\004\n\002\b\031?\006z"}, d2={"Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "Lkotlin/coroutines/Continuation;", "delegate", "", "resumeMode", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "", "proposedUpdate", "", "alreadyResumedError", "(Ljava/lang/Object;)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "cancelLater", "state", "cancelResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelResult", "checkCompleted", "()Z", "token", "completeResume", "detachChild$kotlinx_coroutines_core", "()V", "detachChild", "detachChildIfNonResuable", "mode", "dispatchResume", "(I)V", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "initCancellability", "Lkotlin/Function0;", "block", "invokeHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "invokeOnCancellation", "(Lkotlin/jvm/functions/Function1;)V", "isReusable", "Lkotlinx/coroutines/CancelHandler;", "makeHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "multipleHandlersError", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)V", "", "nameString", "()Ljava/lang/String;", "parentCancelled$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "parentCancelled", "resetState$kotlinx_coroutines_core", "resetState", "value", "onCancellation", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/CancelledContinuation;", "resumeImpl", "(Ljava/lang/Object;I)Lkotlinx/coroutines/CancelledContinuation;", "Lkotlin/Result;", "result", "resumeWith", "setupCancellation", "takeState$kotlinx_coroutines_core", "takeState", "toString", "tryResume", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "exception", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "trySuspend", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "isActive", "isCancelled", "isCompleted", "Lkotlinx/coroutines/DisposableHandle;", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setParentHandle", "(Lkotlinx/coroutines/DisposableHandle;)V", "parentHandle", "getState$kotlinx_coroutines_core", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;"}, k=1, mv={1, 1, 16})
public class CancellableContinuationImpl<T>
  extends DispatchedTask<T>
  implements CancellableContinuation<T>, CoroutineStackFrame
{
  private static final AtomicIntegerFieldUpdater _decision$FU = AtomicIntegerFieldUpdater.newUpdater(CancellableContinuationImpl.class, "_decision");
  private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_state");
  private volatile int _decision;
  private volatile Object _parentHandle;
  private volatile Object _state;
  private final CoroutineContext context;
  private final Continuation<T> delegate;
  
  public CancellableContinuationImpl(Continuation<? super T> paramContinuation, int paramInt)
  {
    super(paramInt);
    this.delegate = paramContinuation;
    this.context = paramContinuation.getContext();
    this._decision = 0;
    this._state = Active.INSTANCE;
    this._parentHandle = null;
  }
  
  private final void alreadyResumedError(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Already resumed, but proposed with update ");
    localStringBuilder.append(paramObject);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  private final boolean cancelLater(Throwable paramThrowable)
  {
    if (this.resumeMode != 0) {
      return false;
    }
    Continuation localContinuation = this.delegate;
    Object localObject = localContinuation;
    if (!(localContinuation instanceof DispatchedContinuation)) {
      localObject = null;
    }
    localObject = (DispatchedContinuation)localObject;
    if (localObject != null) {
      return ((DispatchedContinuation)localObject).postponeCancellation(paramThrowable);
    }
    return false;
  }
  
  private final boolean checkCompleted()
  {
    boolean bool1 = isCompleted();
    if (this.resumeMode != 0) {
      return bool1;
    }
    Continuation localContinuation = this.delegate;
    Object localObject = localContinuation;
    if (!(localContinuation instanceof DispatchedContinuation)) {
      localObject = null;
    }
    localObject = (DispatchedContinuation)localObject;
    boolean bool2 = bool1;
    if (localObject != null)
    {
      localObject = ((DispatchedContinuation)localObject).checkPostponedCancellation((CancellableContinuation)this);
      bool2 = bool1;
      if (localObject != null)
      {
        if (!bool1) {
          cancel((Throwable)localObject);
        }
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private final void detachChildIfNonResuable()
  {
    if (!isReusable()) {
      detachChild$kotlinx_coroutines_core();
    }
  }
  
  private final void dispatchResume(int paramInt)
  {
    if (tryResume()) {
      return;
    }
    DispatchedTaskKt.dispatch(this, paramInt);
  }
  
  private final DisposableHandle getParentHandle()
  {
    return (DisposableHandle)this._parentHandle;
  }
  
  /* Error */
  private final void invokeHandlerSafely(kotlin.jvm.functions.Function0<Unit> paramFunction0)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 257 1 0
    //   6: pop
    //   7: goto +50 -> 57
    //   10: astore_2
    //   11: aload_0
    //   12: invokevirtual 258	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   15: astore_1
    //   16: new 202	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 204	java/lang/StringBuilder:<init>	()V
    //   23: astore_3
    //   24: aload_3
    //   25: ldc_w 260
    //   28: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload_3
    //   33: aload_0
    //   34: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   37: pop
    //   38: aload_1
    //   39: new 262	kotlinx/coroutines/CompletionHandlerException
    //   42: dup
    //   43: aload_3
    //   44: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: aload_2
    //   48: invokespecial 265	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   51: checkcast 223	java/lang/Throwable
    //   54: invokestatic 271	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   57: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	CancellableContinuationImpl
    //   0	58	1	paramFunction0	kotlin.jvm.functions.Function0<Unit>
    //   10	38	2	localThrowable	Throwable
    //   23	21	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
  
  private final boolean isReusable()
  {
    Continuation localContinuation = this.delegate;
    boolean bool;
    if (((localContinuation instanceof DispatchedContinuation)) && (((DispatchedContinuation)localContinuation).isReusable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final CancelHandler makeHandler(Function1<? super Throwable, Unit> paramFunction1)
  {
    if ((paramFunction1 instanceof CancelHandler)) {
      paramFunction1 = (CancelHandler)paramFunction1;
    } else {
      paramFunction1 = (CancelHandler)new InvokeOnCancel(paramFunction1);
    }
    return paramFunction1;
  }
  
  private final void multipleHandlersError(Function1<? super Throwable, Unit> paramFunction1, Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("It's prohibited to register multiple handlers, tried to register ");
    localStringBuilder.append(paramFunction1);
    localStringBuilder.append(", already has ");
    localStringBuilder.append(paramObject);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  private final CancelledContinuation resumeImpl(Object paramObject, int paramInt)
  {
    for (;;)
    {
      Object localObject = this._state;
      if ((localObject instanceof NotCompleted))
      {
        if (_state$FU.compareAndSet(this, localObject, paramObject))
        {
          detachChildIfNonResuable();
          dispatchResume(paramInt);
          return null;
        }
      }
      else
      {
        if ((localObject instanceof CancelledContinuation))
        {
          localObject = (CancelledContinuation)localObject;
          if (((CancelledContinuation)localObject).makeResumed()) {
            return localObject;
          }
        }
        alreadyResumedError(paramObject);
      }
    }
  }
  
  private final void setParentHandle(DisposableHandle paramDisposableHandle)
  {
    this._parentHandle = paramDisposableHandle;
  }
  
  private final void setupCancellation()
  {
    if (checkCompleted()) {
      return;
    }
    if (getParentHandle() != null) {
      return;
    }
    Object localObject = (Job)this.delegate.getContext().get((CoroutineContext.Key)Job.Key);
    if (localObject != null)
    {
      ((Job)localObject).start();
      localObject = Job.DefaultImpls.invokeOnCompletion$default((Job)localObject, true, false, (Function1)new ChildContinuation((Job)localObject, this), 2, null);
      setParentHandle((DisposableHandle)localObject);
      if ((isCompleted()) && (!isReusable()))
      {
        ((DisposableHandle)localObject).dispose();
        setParentHandle((DisposableHandle)NonDisposableHandle.INSTANCE);
      }
    }
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
  
  /* Error */
  public boolean cancel(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 196	kotlinx/coroutines/CancellableContinuationImpl:_state	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: instanceof 287
    //   9: ifne +5 -> 14
    //   12: iconst_0
    //   13: ireturn
    //   14: aload_0
    //   15: checkcast 182	kotlin/coroutines/Continuation
    //   18: astore_3
    //   19: aload_2
    //   20: instanceof 275
    //   23: istore 4
    //   25: new 297	kotlinx/coroutines/CancelledContinuation
    //   28: dup
    //   29: aload_3
    //   30: aload_1
    //   31: iload 4
    //   33: invokespecial 358	kotlinx/coroutines/CancelledContinuation:<init>	(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;Z)V
    //   36: astore_3
    //   37: getstatic 168	kotlinx/coroutines/CancellableContinuationImpl:_state$FU	Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
    //   40: aload_0
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 291	java/util/concurrent/atomic/AtomicReferenceFieldUpdater:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z
    //   46: ifne +6 -> 52
    //   49: goto -49 -> 0
    //   52: iload 4
    //   54: ifeq +61 -> 115
    //   57: aload_2
    //   58: checkcast 275	kotlinx/coroutines/CancelHandler
    //   61: aload_1
    //   62: invokevirtual 360	kotlinx/coroutines/CancelHandler:invoke	(Ljava/lang/Throwable;)V
    //   65: goto +50 -> 115
    //   68: astore_1
    //   69: aload_0
    //   70: invokevirtual 258	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   73: astore_2
    //   74: new 202	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 204	java/lang/StringBuilder:<init>	()V
    //   81: astore_3
    //   82: aload_3
    //   83: ldc_w 260
    //   86: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: pop
    //   90: aload_3
    //   91: aload_0
    //   92: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload_2
    //   97: new 262	kotlinx/coroutines/CompletionHandlerException
    //   100: dup
    //   101: aload_3
    //   102: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: aload_1
    //   106: invokespecial 265	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: checkcast 223	java/lang/Throwable
    //   112: invokestatic 271	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   115: aload_0
    //   116: invokespecial 293	kotlinx/coroutines/CancellableContinuationImpl:detachChildIfNonResuable	()V
    //   119: aload_0
    //   120: iconst_0
    //   121: invokespecial 295	kotlinx/coroutines/CancellableContinuationImpl:dispatchResume	(I)V
    //   124: iconst_1
    //   125: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	126	0	this	CancellableContinuationImpl
    //   0	126	1	paramThrowable	Throwable
    //   4	93	2	localObject1	Object
    //   18	84	3	localObject2	Object
    //   23	30	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   57	65	68	finally
  }
  
  /* Error */
  public void cancelResult$kotlinx_coroutines_core(Object paramObject, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc_w 361
    //   4: invokestatic 176	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_1
    //   8: instanceof 363
    //   11: ifeq +67 -> 78
    //   14: aload_1
    //   15: checkcast 363	kotlinx/coroutines/CompletedWithCancellation
    //   18: getfield 366	kotlinx/coroutines/CompletedWithCancellation:onCancellation	Lkotlin/jvm/functions/Function1;
    //   21: aload_2
    //   22: invokeinterface 368 2 0
    //   27: pop
    //   28: goto +50 -> 78
    //   31: astore_2
    //   32: aload_0
    //   33: invokevirtual 258	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   36: astore_3
    //   37: new 202	java/lang/StringBuilder
    //   40: dup
    //   41: invokespecial 204	java/lang/StringBuilder:<init>	()V
    //   44: astore_1
    //   45: aload_1
    //   46: ldc_w 260
    //   49: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_1
    //   54: aload_0
    //   55: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_3
    //   60: new 262	kotlinx/coroutines/CompletionHandlerException
    //   63: dup
    //   64: aload_1
    //   65: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: aload_2
    //   69: invokespecial 265	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   72: checkcast 223	java/lang/Throwable
    //   75: invokestatic 271	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   78: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	this	CancellableContinuationImpl
    //   0	79	1	paramObject	Object
    //   0	79	2	paramThrowable	Throwable
    //   36	24	3	localCoroutineContext	CoroutineContext
    // Exception table:
    //   from	to	target	type
    //   14	28	31	finally
  }
  
  public void completeResume(Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramObject, "token");
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (paramObject == CancellableContinuationImplKt.RESUME_TOKEN) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    dispatchResume(this.resumeMode);
  }
  
  public final void detachChild$kotlinx_coroutines_core()
  {
    DisposableHandle localDisposableHandle = getParentHandle();
    if (localDisposableHandle != null) {
      localDisposableHandle.dispose();
    }
    setParentHandle((DisposableHandle)NonDisposableHandle.INSTANCE);
  }
  
  public CoroutineStackFrame getCallerFrame()
  {
    Continuation localContinuation1 = this.delegate;
    Continuation localContinuation2 = localContinuation1;
    if (!(localContinuation1 instanceof CoroutineStackFrame)) {
      localContinuation2 = null;
    }
    return (CoroutineStackFrame)localContinuation2;
  }
  
  public CoroutineContext getContext()
  {
    return this.context;
  }
  
  public Throwable getContinuationCancellationCause(Job paramJob)
  {
    Intrinsics.checkParameterIsNotNull(paramJob, "parent");
    return (Throwable)paramJob.getCancellationException();
  }
  
  public final Continuation<T> getDelegate$kotlinx_coroutines_core()
  {
    return this.delegate;
  }
  
  public final Object getResult()
  {
    setupCancellation();
    if (trySuspend()) {
      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
    Object localObject1 = getState$kotlinx_coroutines_core();
    if (!(localObject1 instanceof CompletedExceptionally))
    {
      if (this.resumeMode == 1)
      {
        Object localObject2 = (Job)getContext().get((CoroutineContext.Key)Job.Key);
        if ((localObject2 != null) && (!((Job)localObject2).isActive()))
        {
          localObject2 = (Throwable)((Job)localObject2).getCancellationException();
          cancelResult$kotlinx_coroutines_core(localObject1, (Throwable)localObject2);
          throw StackTraceRecoveryKt.recoverStackTrace((Throwable)localObject2, (Continuation)this);
        }
      }
      return getSuccessfulResult$kotlinx_coroutines_core(localObject1);
    }
    throw StackTraceRecoveryKt.recoverStackTrace(((CompletedExceptionally)localObject1).cause, (Continuation)this);
  }
  
  public StackTraceElement getStackTraceElement()
  {
    return null;
  }
  
  public final Object getState$kotlinx_coroutines_core()
  {
    return this._state;
  }
  
  public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object paramObject)
  {
    Object localObject;
    if ((paramObject instanceof CompletedIdempotentResult))
    {
      localObject = ((CompletedIdempotentResult)paramObject).result;
    }
    else
    {
      localObject = paramObject;
      if ((paramObject instanceof CompletedWithCancellation)) {
        localObject = ((CompletedWithCancellation)paramObject).result;
      }
    }
    return localObject;
  }
  
  public void invokeOnCancellation(Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "handler");
    Object localObject1 = null;
    Object localObject2 = (CancelHandler)null;
    Object localObject3;
    Object localObject4;
    for (;;)
    {
      localObject3 = this._state;
      if ((localObject3 instanceof Active))
      {
        if (localObject2 != null) {
          localObject4 = localObject2;
        } else {
          localObject4 = makeHandler(paramFunction1);
        }
        localObject2 = localObject4;
        if (!_state$FU.compareAndSet(this, localObject3, localObject4)) {}
      }
      else
      {
        if (!(localObject3 instanceof CancelHandler)) {
          break;
        }
        multipleHandlersError(paramFunction1, localObject3);
      }
    }
    if ((localObject3 instanceof CancelledContinuation))
    {
      if (!((CancelledContinuation)localObject3).makeHandled()) {
        multipleHandlersError(paramFunction1, localObject3);
      }
      localObject4 = localObject3;
      try
      {
        if (!(localObject3 instanceof CompletedExceptionally)) {
          localObject4 = null;
        }
        localObject2 = (CompletedExceptionally)localObject4;
        localObject4 = localObject1;
        if (localObject2 != null) {
          localObject4 = ((CompletedExceptionally)localObject2).cause;
        }
        paramFunction1.invoke(localObject4);
      }
      finally
      {
        paramFunction1 = getContext();
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("Exception in cancellation handler for ");
        ((StringBuilder)localObject4).append(this);
        CoroutineExceptionHandlerKt.handleCoroutineException(paramFunction1, (Throwable)new CompletionHandlerException(((StringBuilder)localObject4).toString(), localThrowable));
      }
    }
  }
  
  public boolean isActive()
  {
    return getState$kotlinx_coroutines_core() instanceof NotCompleted;
  }
  
  public boolean isCancelled()
  {
    return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
  }
  
  public boolean isCompleted()
  {
    return getState$kotlinx_coroutines_core() instanceof NotCompleted ^ true;
  }
  
  protected String nameString()
  {
    return "CancellableContinuation";
  }
  
  public final void parentCancelled$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    if (cancelLater(paramThrowable)) {
      return;
    }
    cancel(paramThrowable);
    detachChildIfNonResuable();
  }
  
  public final boolean resetState$kotlinx_coroutines_core()
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (getParentHandle() != NonDisposableHandle.INSTANCE) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    Object localObject = this._state;
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(localObject instanceof NotCompleted ^ true))) {
      throw ((Throwable)new AssertionError());
    }
    if ((localObject instanceof CompletedIdempotentResult))
    {
      detachChild$kotlinx_coroutines_core();
      return false;
    }
    this._decision = 0;
    this._state = Active.INSTANCE;
    return true;
  }
  
  /* Error */
  public void resume(T paramT, Function1<? super Throwable, Unit> paramFunction1)
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc_w 437
    //   4: invokestatic 176	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: new 363	kotlinx/coroutines/CompletedWithCancellation
    //   11: dup
    //   12: aload_1
    //   13: aload_2
    //   14: invokespecial 439	kotlinx/coroutines/CompletedWithCancellation:<init>	(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
    //   17: aload_0
    //   18: getfield 225	kotlinx/coroutines/CancellableContinuationImpl:resumeMode	I
    //   21: invokespecial 441	kotlinx/coroutines/CancellableContinuationImpl:resumeImpl	(Ljava/lang/Object;I)Lkotlinx/coroutines/CancelledContinuation;
    //   24: astore_1
    //   25: aload_1
    //   26: ifnull +64 -> 90
    //   29: aload_2
    //   30: aload_1
    //   31: getfield 442	kotlinx/coroutines/CancelledContinuation:cause	Ljava/lang/Throwable;
    //   34: invokeinterface 368 2 0
    //   39: pop
    //   40: goto +50 -> 90
    //   43: astore_2
    //   44: aload_0
    //   45: invokevirtual 258	kotlinx/coroutines/CancellableContinuationImpl:getContext	()Lkotlin/coroutines/CoroutineContext;
    //   48: astore_3
    //   49: new 202	java/lang/StringBuilder
    //   52: dup
    //   53: invokespecial 204	java/lang/StringBuilder:<init>	()V
    //   56: astore_1
    //   57: aload_1
    //   58: ldc_w 260
    //   61: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload_1
    //   66: aload_0
    //   67: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: aload_3
    //   72: new 262	kotlinx/coroutines/CompletionHandlerException
    //   75: dup
    //   76: aload_1
    //   77: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: aload_2
    //   81: invokespecial 265	kotlinx/coroutines/CompletionHandlerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   84: checkcast 223	java/lang/Throwable
    //   87: invokestatic 271	kotlinx/coroutines/CoroutineExceptionHandlerKt:handleCoroutineException	(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Throwable;)V
    //   90: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	91	0	this	CancellableContinuationImpl
    //   0	91	1	paramT	T
    //   0	91	2	paramFunction1	Function1<? super Throwable, Unit>
    //   48	24	3	localCoroutineContext	CoroutineContext
    // Exception table:
    //   from	to	target	type
    //   29	40	43	finally
  }
  
  public void resumeUndispatched(CoroutineDispatcher paramCoroutineDispatcher, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineDispatcher, "$this$resumeUndispatched");
    Object localObject1 = this.delegate;
    boolean bool = localObject1 instanceof DispatchedContinuation;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    DispatchedContinuation localDispatchedContinuation = (DispatchedContinuation)localObject1;
    localObject1 = localObject2;
    if (localDispatchedContinuation != null) {
      localObject1 = localDispatchedContinuation.dispatcher;
    }
    int i;
    if (localObject1 == paramCoroutineDispatcher) {
      i = 2;
    } else {
      i = this.resumeMode;
    }
    resumeImpl(paramT, i);
  }
  
  public void resumeUndispatchedWithException(CoroutineDispatcher paramCoroutineDispatcher, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineDispatcher, "$this$resumeUndispatchedWithException");
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    Object localObject1 = this.delegate;
    boolean bool = localObject1 instanceof DispatchedContinuation;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    DispatchedContinuation localDispatchedContinuation = (DispatchedContinuation)localObject1;
    int i = 2;
    localObject1 = new CompletedExceptionally(paramThrowable, false, 2, null);
    paramThrowable = localObject2;
    if (localDispatchedContinuation != null) {
      paramThrowable = localDispatchedContinuation.dispatcher;
    }
    if (paramThrowable != paramCoroutineDispatcher) {
      i = this.resumeMode;
    }
    resumeImpl(localObject1, i);
  }
  
  public void resumeWith(Object paramObject)
  {
    resumeImpl(CompletedExceptionallyKt.toState(paramObject, (CancellableContinuation)this), this.resumeMode);
  }
  
  public Object takeState$kotlinx_coroutines_core()
  {
    return getState$kotlinx_coroutines_core();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(nameString());
    localStringBuilder.append('(');
    localStringBuilder.append(DebugStringsKt.toDebugString(this.delegate));
    localStringBuilder.append("){");
    localStringBuilder.append(getState$kotlinx_coroutines_core());
    localStringBuilder.append("}@");
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    return localStringBuilder.toString();
  }
  
  public Object tryResume(T paramT, Object paramObject)
  {
    Object localObject1;
    do
    {
      localObject1 = this._state;
      if (!(localObject1 instanceof NotCompleted)) {
        break;
      }
      if (paramObject == null) {
        localObject2 = paramT;
      } else {
        localObject2 = new CompletedIdempotentResult(paramObject, paramT);
      }
    } while (!_state$FU.compareAndSet(this, localObject1, localObject2));
    detachChildIfNonResuable();
    return CancellableContinuationImplKt.RESUME_TOKEN;
    boolean bool = localObject1 instanceof CompletedIdempotentResult;
    Object localObject3 = null;
    Object localObject2 = localObject3;
    if (bool)
    {
      localObject1 = (CompletedIdempotentResult)localObject1;
      localObject2 = localObject3;
      if (((CompletedIdempotentResult)localObject1).idempotentResume == paramObject)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i;
          if (((CompletedIdempotentResult)localObject1).result == paramT) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
        localObject2 = CancellableContinuationImplKt.RESUME_TOKEN;
      }
    }
    return localObject2;
  }
  
  public Object tryResumeWithException(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    Object localObject;
    CompletedExceptionally localCompletedExceptionally;
    do
    {
      localObject = this._state;
      if (!(localObject instanceof NotCompleted)) {
        break;
      }
      localCompletedExceptionally = new CompletedExceptionally(paramThrowable, false, 2, null);
    } while (!_state$FU.compareAndSet(this, localObject, localCompletedExceptionally));
    detachChildIfNonResuable();
    return CancellableContinuationImplKt.RESUME_TOKEN;
    return null;
  }
}
