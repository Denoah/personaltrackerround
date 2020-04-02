package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\006\n\002\030\002\n\002\b\004\n\002\020\003\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\007\bf\030\000*\006\b\000\020\001 \0002\0020\002J\024\020\016\032\0020\0042\n\b\002\020\017\032\004\030\0010\020H&J-\020\021\032\0020\0222#\020\023\032\037\022\025\022\023\030\0010\020?\006\f\b\025\022\b\b\026\022\004\b\b(\017\022\004\022\0020\0220\024H'J\025\020\027\032\0020\0042\006\020\030\032\0028\000H&?\006\002\020\031J\031\020\032\032\0020\0222\006\020\030\032\0028\000H¦@?\001\000?\006\002\020\033R\032\020\003\032\0020\0048&X§\004?\006\f\022\004\b\005\020\006\032\004\b\003\020\007R\032\020\b\032\0020\0048&X§\004?\006\f\022\004\b\t\020\006\032\004\b\b\020\007R$\020\n\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0000\013X¦\004?\006\006\032\004\b\f\020\r?\002\004\n\002\b\031?\006\034"}, d2={"Lkotlinx/coroutines/channels/SendChannel;", "E", "", "isClosedForSend", "", "isClosedForSend$annotations", "()V", "()Z", "isFull", "isFull$annotations", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "close", "cause", "", "invokeOnClose", "", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "offer", "element", "(Ljava/lang/Object;)Z", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface SendChannel<E>
{
  public abstract boolean close(Throwable paramThrowable);
  
  public abstract SelectClause2<E, SendChannel<E>> getOnSend();
  
  public abstract void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract boolean isClosedForSend();
  
  public abstract boolean isFull();
  
  public abstract boolean offer(E paramE);
  
  public abstract Object send(E paramE, Continuation<? super Unit> paramContinuation);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
