package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\t\n\002\020\003\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\b\002\030\000*\004\b\000\020\0012\0020\0022\b\022\004\022\002H\0010\0032\b\022\004\022\002H\0010\004B\017\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\021\020\017\032\0028\000H?@?\001\000?\006\002\020\020J\025\020\021\032\0020\f2\006\020\022\032\0028\000H\026?\006\002\020\023J\020\020\024\032\0020\f2\006\020\025\032\0020\026H\026J\r\020\027\032\0028\000H\026?\006\002\020\030JH\020\031\032\0020\032\"\004\b\001\020\0332\f\020\034\032\b\022\004\022\002H\0330\0352\"\020\036\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\002H\0330 \022\006\022\004\030\0010!0\037H\026?\001\000?\006\002\020\"R\032\020\b\032\b\022\004\022\0028\0000\0048VX?\004?\006\006\032\004\b\t\020\nR\024\020\013\032\0020\f8PX?\004?\006\006\032\004\b\r\020\016?\002\004\n\002\b\031?\006#"}, d2={"Lkotlinx/coroutines/CompletableDeferredImpl;", "T", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/CompletableDeferred;", "Lkotlinx/coroutines/selects/SelectClause1;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "onAwait", "getOnAwait", "()Lkotlinx/coroutines/selects/SelectClause1;", "onCancelComplete", "", "getOnCancelComplete$kotlinx_coroutines_core", "()Z", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "complete", "value", "(Ljava/lang/Object;)Z", "completeExceptionally", "exception", "", "getCompleted", "()Ljava/lang/Object;", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CompletableDeferredImpl<T>
  extends JobSupport
  implements CompletableDeferred<T>, SelectClause1<T>
{
  public CompletableDeferredImpl(Job paramJob)
  {
    super(true);
    initParentJobInternal$kotlinx_coroutines_core(paramJob);
  }
  
  public Object await(Continuation<? super T> paramContinuation)
  {
    if ((paramContinuation instanceof await.1))
    {
      localObject1 = (await.1)paramContinuation;
      if ((((await.1)localObject1).label & 0x80000000) != 0)
      {
        ((await.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super T>)localObject1;
        break label48;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return this.this$0.await(this);
      }
    };
    label48:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramContinuation = (CompletableDeferredImpl)paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject1);
        paramContinuation = (Continuation<? super T>)localObject1;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      paramContinuation.L$0 = this;
      paramContinuation.label = 1;
      localObject1 = awaitInternal$kotlinx_coroutines_core(paramContinuation);
      paramContinuation = (Continuation<? super T>)localObject1;
      if (localObject1 == localObject2) {
        return localObject2;
      }
    }
    return paramContinuation;
  }
  
  public boolean complete(T paramT)
  {
    return makeCompleting$kotlinx_coroutines_core(paramT);
  }
  
  public boolean completeExceptionally(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    return makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(paramThrowable, false, 2, null));
  }
  
  public T getCompleted()
  {
    return getCompletedInternal$kotlinx_coroutines_core();
  }
  
  public SelectClause1<T> getOnAwait()
  {
    return (SelectClause1)this;
  }
  
  public boolean getOnCancelComplete$kotlinx_coroutines_core()
  {
    return true;
  }
  
  public <R> void registerSelectClause1(SelectInstance<? super R> paramSelectInstance, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    registerSelectClause1Internal$kotlinx_coroutines_core(paramSelectInstance, paramFunction2);
  }
}
