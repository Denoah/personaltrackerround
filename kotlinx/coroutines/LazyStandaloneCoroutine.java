package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.CancellableKt;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\004\b\002\030\0002\0020\001B9\022\006\020\002\032\0020\003\022'\020\004\032#\b\001\022\004\022\0020\006\022\n\022\b\022\004\022\0020\b0\007\022\006\022\004\030\0010\t0\005?\006\002\b\n?\001\000?\006\002\020\013J\b\020\r\032\0020\bH\024R\024\020\f\032\b\022\004\022\0020\b0\007X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\016"}, d2={"Lkotlinx/coroutines/LazyStandaloneCoroutine;", "Lkotlinx/coroutines/StandaloneCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)V", "continuation", "onStart", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class LazyStandaloneCoroutine
  extends StandaloneCoroutine
{
  private final Continuation<Unit> continuation = IntrinsicsKt.createCoroutineUnintercepted(paramFunction2, this, (Continuation)this);
  
  public LazyStandaloneCoroutine(CoroutineContext paramCoroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    super(paramCoroutineContext, false);
  }
  
  protected void onStart()
  {
    CancellableKt.startCoroutineCancellable(this.continuation, (Continuation)this);
  }
}
