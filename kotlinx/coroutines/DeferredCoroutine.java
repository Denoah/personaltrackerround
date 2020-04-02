package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\t\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\b\022\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\b\022\004\022\002H\0010\004B\025\022\006\020\005\032\0020\006\022\006\020\007\032\0020\b?\006\002\020\tJ\021\020\r\032\0028\000H?@?\001\000?\006\002\020\016J\r\020\017\032\0028\000H\026?\006\002\020\020JH\020\021\032\0020\022\"\004\b\001\020\0232\f\020\024\032\b\022\004\022\002H\0230\0252\"\020\026\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\002H\0230\030\022\006\022\004\030\0010\0310\027H\026?\001\000?\006\002\020\032R\032\020\n\032\b\022\004\022\0028\0000\0048VX?\004?\006\006\032\004\b\013\020\f?\002\004\n\002\b\031?\006\033"}, d2={"Lkotlinx/coroutines/DeferredCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "Lkotlinx/coroutines/Deferred;", "Lkotlinx/coroutines/selects/SelectClause1;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Z)V", "onAwait", "getOnAwait", "()Lkotlinx/coroutines/selects/SelectClause1;", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompleted", "()Ljava/lang/Object;", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
class DeferredCoroutine<T>
  extends AbstractCoroutine<T>
  implements Deferred<T>, SelectClause1<T>
{
  public DeferredCoroutine(CoroutineContext paramCoroutineContext, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramBoolean);
  }
  
  public Object await(Continuation<? super T> paramContinuation)
  {
    return await$suspendImpl(this, paramContinuation);
  }
  
  public T getCompleted()
  {
    return getCompletedInternal$kotlinx_coroutines_core();
  }
  
  public SelectClause1<T> getOnAwait()
  {
    return (SelectClause1)this;
  }
  
  public <R> void registerSelectClause1(SelectInstance<? super R> paramSelectInstance, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    registerSelectClause1Internal$kotlinx_coroutines_core(paramSelectInstance, paramFunction2);
  }
}
