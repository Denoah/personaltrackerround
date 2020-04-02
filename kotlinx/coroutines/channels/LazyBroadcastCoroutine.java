package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.CancellableKt;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BM\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006\022-\020\007\032)\b\001\022\n\022\b\022\004\022\0028\0000\t\022\n\022\b\022\004\022\0020\0130\n\022\006\022\004\030\0010\f0\b?\006\002\b\r?\001\000?\006\002\020\016J\b\020\020\032\0020\013H\024J\016\020\021\032\b\022\004\022\0028\0000\022H\026R\024\020\017\032\b\022\004\022\0020\0130\nX?\004?\006\002\n\000?\002\004\n\002\b\031?\006\023"}, d2={"Lkotlinx/coroutines/channels/LazyBroadcastCoroutine;", "E", "Lkotlinx/coroutines/channels/BroadcastCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function2;)V", "continuation", "onStart", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class LazyBroadcastCoroutine<E>
  extends BroadcastCoroutine<E>
{
  private final Continuation<Unit> continuation = IntrinsicsKt.createCoroutineUnintercepted(paramFunction2, this, (Continuation)this);
  
  public LazyBroadcastCoroutine(CoroutineContext paramCoroutineContext, BroadcastChannel<E> paramBroadcastChannel, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    super(paramCoroutineContext, paramBroadcastChannel, false);
  }
  
  protected void onStart()
  {
    CancellableKt.startCoroutineCancellable(this.continuation, (Continuation)this);
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    ReceiveChannel localReceiveChannel = get_channel().openSubscription();
    start();
    return localReceiveChannel;
  }
}
