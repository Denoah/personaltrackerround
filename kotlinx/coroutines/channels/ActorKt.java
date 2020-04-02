package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\032?\001\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\t2-\b\002\020\n\032'\022\025\022\023\030\0010\f?\006\f\b\r\022\b\b\016\022\004\b\b(\017\022\004\022\0020\020\030\0010\013j\004\030\001`\0212-\020\022\032)\b\001\022\n\022\b\022\004\022\002H\0020\024\022\n\022\b\022\004\022\0020\0200\025\022\006\022\004\030\0010\0260\023?\006\002\b\027H\007?\001\000?\006\002\020\030?\002\004\n\002\b\031?\006\031"}, d2={"actor", "Lkotlinx/coroutines/channels/SendChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ActorScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/SendChannel;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ActorKt
{
  public static final <E> SendChannel<E> actor(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, CoroutineStart paramCoroutineStart, Function1<? super Throwable, Unit> paramFunction1, Function2<? super ActorScope<E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$actor");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    paramCoroutineContext = CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext);
    paramCoroutineScope = ChannelKt.Channel(paramInt);
    if (paramCoroutineStart.isLazy()) {
      paramCoroutineScope = (ActorCoroutine)new LazyActorCoroutine(paramCoroutineContext, paramCoroutineScope, paramFunction2);
    } else {
      paramCoroutineScope = new ActorCoroutine(paramCoroutineContext, paramCoroutineScope, true);
    }
    if (paramFunction1 != null) {
      paramCoroutineScope.invokeOnCompletion(paramFunction1);
    }
    paramCoroutineScope.start(paramCoroutineStart, paramCoroutineScope, paramFunction2);
    return (SendChannel)paramCoroutineScope;
  }
}
