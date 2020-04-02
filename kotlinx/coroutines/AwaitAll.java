package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\006\n\002\020\000\b\002\030\000*\004\b\000\020\0012\0020\016:\002\013\fB\035\022\024\020\004\032\020\022\f\b\001\022\b\022\004\022\0028\0000\0030\002?\006\004\b\005\020\006J\031\020\b\032\b\022\004\022\0028\0000\007H?@?\001\000?\006\004\b\b\020\tR$\020\004\032\020\022\f\b\001\022\b\022\004\022\0028\0000\0030\0028\002@\002X?\004?\006\006\n\004\b\004\020\n?\002\004\n\002\b\031?\006\r"}, d2={"Lkotlinx/coroutines/AwaitAll;", "T", "", "Lkotlinx/coroutines/Deferred;", "deferreds", "<init>", "([Lkotlinx/coroutines/Deferred;)V", "", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "[Lkotlinx/coroutines/Deferred;", "AwaitAllNode", "DisposeHandlersOnCancel", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
final class AwaitAll<T>
{
  static final AtomicIntegerFieldUpdater notCompletedCount$FU = AtomicIntegerFieldUpdater.newUpdater(AwaitAll.class, "notCompletedCount");
  private final Deferred<T>[] deferreds;
  volatile int notCompletedCount;
  
  public AwaitAll(Deferred<? extends T>[] paramArrayOfDeferred)
  {
    this.deferreds = paramArrayOfDeferred;
    this.notCompletedCount = paramArrayOfDeferred.length;
  }
  
  public final Object await(Continuation<? super List<? extends T>> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    Object localObject1 = (CancellableContinuation)localCancellableContinuationImpl;
    int i = access$getDeferreds$p(this).length;
    AwaitAllNode[] arrayOfAwaitAllNode = new AwaitAllNode[i];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      int m = ((Number)Boxing.boxInt(k)).intValue();
      localObject2 = access$getDeferreds$p(this)[m];
      ((Deferred)localObject2).start();
      AwaitAllNode localAwaitAllNode = new AwaitAllNode((CancellableContinuation)localObject1, (Job)localObject2);
      localAwaitAllNode.setHandle(((Deferred)localObject2).invokeOnCompletion((Function1)localAwaitAllNode));
      arrayOfAwaitAllNode[k] = localAwaitAllNode;
    }
    Object localObject2 = new DisposeHandlersOnCancel(arrayOfAwaitAllNode);
    for (k = j; k < i; k++) {
      arrayOfAwaitAllNode[k].setDisposer((DisposeHandlersOnCancel)localObject2);
    }
    if (((CancellableContinuation)localObject1).isCompleted()) {
      ((DisposeHandlersOnCancel)localObject2).disposeAll();
    } else {
      ((CancellableContinuation)localObject1).invokeOnCancellation((Function1)localObject2);
    }
    localObject1 = localCancellableContinuationImpl.getResult();
    if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return localObject1;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\002\020 \n\000\n\002\030\002\n\002\b\003\n\002\020\003\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\b?\004\030\0002\b\022\004\022\0020\0040\036B#\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0020\001\022\006\020\005\032\0020\004?\006\004\b\006\020\007J\032\020\013\032\0020\n2\b\020\t\032\004\030\0010\bH?\002?\006\004\b\013\020\fR\"\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0020\0018\002@\002X?\004?\006\006\n\004\b\003\020\rR<\020\025\032\016\030\0010\016R\b\022\004\022\0028\0000\0172\022\020\020\032\016\030\0010\016R\b\022\004\022\0028\0000\0178F@FX?\016?\006\f\032\004\b\021\020\022\"\004\b\023\020\024R\"\020\027\032\0020\0268\006@\006X?.?\006\022\n\004\b\027\020\030\032\004\b\031\020\032\"\004\b\033\020\034?\006\035"}, d2={"Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/CancellableContinuation;", "", "continuation", "Lkotlinx/coroutines/Job;", "job", "<init>", "(Lkotlinx/coroutines/AwaitAll;Lkotlinx/coroutines/CancellableContinuation;Lkotlinx/coroutines/Job;)V", "", "cause", "", "invoke", "(Ljava/lang/Throwable;)V", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/AwaitAll;", "value", "getDisposer", "()Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "setDisposer", "(Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;)V", "disposer", "Lkotlinx/coroutines/DisposableHandle;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "getHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setHandle", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core", "Lkotlinx/coroutines/JobNode;"}, k=1, mv={1, 1, 16})
  private final class AwaitAllNode
    extends JobNode<Job>
  {
    private volatile Object _disposer;
    private final CancellableContinuation<List<? extends T>> continuation;
    public DisposableHandle handle;
    
    public AwaitAllNode(Job paramJob)
    {
      super();
      this.continuation = paramJob;
      this._disposer = null;
    }
    
    public final AwaitAll<T>.DisposeHandlersOnCancel getDisposer()
    {
      return (AwaitAll.DisposeHandlersOnCancel)this._disposer;
    }
    
    public final DisposableHandle getHandle()
    {
      DisposableHandle localDisposableHandle = this.handle;
      if (localDisposableHandle == null) {
        Intrinsics.throwUninitializedPropertyAccessException("handle");
      }
      return localDisposableHandle;
    }
    
    public void invoke(Throwable paramThrowable)
    {
      if (paramThrowable != null)
      {
        paramThrowable = this.continuation.tryResumeWithException(paramThrowable);
        if (paramThrowable != null)
        {
          this.continuation.completeResume(paramThrowable);
          paramThrowable = getDisposer();
          if (paramThrowable != null) {
            paramThrowable.disposeAll();
          }
        }
      }
      else
      {
        paramThrowable = AwaitAll.this;
        if (AwaitAll.notCompletedCount$FU.decrementAndGet(paramThrowable) == 0)
        {
          paramThrowable = (Continuation)this.continuation;
          Object localObject1 = AwaitAll.access$getDeferreds$p(AwaitAll.this);
          Object localObject2 = (Collection)new ArrayList(localObject1.length);
          int i = localObject1.length;
          for (int j = 0; j < i; j++) {
            ((Collection)localObject2).add(localObject1[j].getCompleted());
          }
          localObject2 = (List)localObject2;
          localObject1 = Result.Companion;
          paramThrowable.resumeWith(Result.constructor-impl(localObject2));
        }
      }
    }
    
    public final void setDisposer(AwaitAll<T>.DisposeHandlersOnCancel paramAwaitAll)
    {
      this._disposer = paramAwaitAll;
    }
    
    public final void setHandle(DisposableHandle paramDisposableHandle)
    {
      Intrinsics.checkParameterIsNotNull(paramDisposableHandle, "<set-?>");
      this.handle = paramDisposableHandle;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\003\n\000\n\002\020\016\n\000\b?\004\030\0002\0020\001B\035\022\026\020\002\032\022\022\016\022\f0\004R\b\022\004\022\0028\0000\0050\003?\006\002\020\006J\006\020\b\032\0020\tJ\023\020\n\032\0020\t2\b\020\013\032\004\030\0010\fH?\002J\b\020\r\032\0020\016H\026R \020\002\032\022\022\016\022\f0\004R\b\022\004\022\0028\0000\0050\003X?\004?\006\004\n\002\020\007?\006\017"}, d2={"Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "nodes", "", "Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/AwaitAll;", "(Lkotlinx/coroutines/AwaitAll;[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;)V", "[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "disposeAll", "", "invoke", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private final class DisposeHandlersOnCancel
    extends CancelHandler
  {
    private final AwaitAll<T>[].AwaitAllNode nodes;
    
    public DisposeHandlersOnCancel()
    {
      this.nodes = localObject;
    }
    
    public final void disposeAll()
    {
      AwaitAll.AwaitAllNode[] arrayOfAwaitAllNode = this.nodes;
      int i = arrayOfAwaitAllNode.length;
      for (int j = 0; j < i; j++) {
        arrayOfAwaitAllNode[j].getHandle().dispose();
      }
    }
    
    public void invoke(Throwable paramThrowable)
    {
      disposeAll();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("DisposeHandlersOnCancel[");
      localStringBuilder.append(this.nodes);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
  }
}
