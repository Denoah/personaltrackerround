package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.ContinuationInterceptor.DefaultImpls;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\016\n\000\b&\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\034\020\004\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH&J\034\020\013\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH\027J \020\f\032\b\022\004\022\002H\0160\r\"\004\b\000\020\0162\f\020\017\032\b\022\004\022\002H\0160\rJ\020\020\020\032\0020\0212\006\020\006\032\0020\007H\026J\021\020\022\032\0020\0002\006\020\023\032\0020\000H?\002J\024\020\024\032\0020\0052\n\020\017\032\006\022\002\b\0030\rH\027J\b\020\025\032\0020\026H\026?\006\027"}, d2={"Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "isDispatchNeeded", "", "plus", "other", "releaseInterceptedContinuation", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class CoroutineDispatcher
  extends AbstractCoroutineContextElement
  implements ContinuationInterceptor
{
  public CoroutineDispatcher()
  {
    super((CoroutineContext.Key)ContinuationInterceptor.Key);
  }
  
  public abstract void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable);
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    dispatch(paramCoroutineContext, paramRunnable);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return ContinuationInterceptor.DefaultImpls.get(this, paramKey);
  }
  
  public final <T> Continuation<T> interceptContinuation(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    return (Continuation)new DispatchedContinuation(this, paramContinuation);
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return true;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return ContinuationInterceptor.DefaultImpls.minusKey(this, paramKey);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")
  public final CoroutineDispatcher plus(CoroutineDispatcher paramCoroutineDispatcher)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineDispatcher, "other");
    return paramCoroutineDispatcher;
  }
  
  public void releaseInterceptedContinuation(Continuation<?> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    paramContinuation = ((DispatchedContinuation)paramContinuation).getReusableCancellableContinuation();
    if (paramContinuation != null) {
      paramContinuation.detachChild$kotlinx_coroutines_core();
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    return localStringBuilder.toString();
  }
}
