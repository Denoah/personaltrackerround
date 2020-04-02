package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.CancellableKt;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B9\022\006\020\003\032\0020\004\022'\020\005\032#\b\001\022\004\022\0020\007\022\n\022\b\022\004\022\0028\0000\b\022\006\022\004\030\0010\t0\006?\006\002\b\n?\001\000?\006\002\020\013J\b\020\016\032\0020\rH\024R\024\020\f\032\b\022\004\022\0020\r0\bX?\004?\006\002\n\000?\002\004\n\002\b\031?\006\017"}, d2={"Lkotlinx/coroutines/LazyDeferredCoroutine;", "T", "Lkotlinx/coroutines/DeferredCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "continuation", "", "onStart", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class LazyDeferredCoroutine<T>
  extends DeferredCoroutine<T>
{
  private final Continuation<Unit> continuation = IntrinsicsKt.createCoroutineUnintercepted(paramFunction2, this, (Continuation)this);
  
  public LazyDeferredCoroutine(CoroutineContext paramCoroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    super(paramCoroutineContext, false);
  }
  
  protected void onStart()
  {
    CancellableKt.startCoroutineCancellable(this.continuation, (Continuation)this);
  }
}
