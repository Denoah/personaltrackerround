package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\020\020\n\002\b\002\n\002\020\013\n\002\b\004\n\002\020\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\007\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002JC\020\b\032\0020\t\"\004\b\000\020\n2\034\020\013\032\030\b\001\022\n\022\b\022\004\022\002H\n0\r\022\006\022\004\030\0010\0160\f2\f\020\017\032\b\022\004\022\002H\n0\rH?\002?\001\000?\006\002\020\020J\\\020\b\032\0020\t\"\004\b\000\020\021\"\004\b\001\020\n2'\020\013\032#\b\001\022\004\022\002H\021\022\n\022\b\022\004\022\002H\n0\r\022\006\022\004\030\0010\0160\022?\006\002\b\0232\006\020\024\032\002H\0212\f\020\017\032\b\022\004\022\002H\n0\rH?\002?\001\000?\006\002\020\025R\032\020\003\032\0020\0048FX?\004?\006\f\022\004\b\005\020\006\032\004\b\003\020\007j\002\b\026j\002\b\027j\002\b\030j\002\b\031?\002\004\n\002\b\031?\006\032"}, d2={"Lkotlinx/coroutines/CoroutineStart;", "", "(Ljava/lang/String;I)V", "isLazy", "", "isLazy$annotations", "()V", "()Z", "invoke", "", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "DEFAULT", "LAZY", "ATOMIC", "UNDISPATCHED", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public enum CoroutineStart
{
  static
  {
    CoroutineStart localCoroutineStart1 = new CoroutineStart("DEFAULT", 0);
    DEFAULT = localCoroutineStart1;
    CoroutineStart localCoroutineStart2 = new CoroutineStart("LAZY", 1);
    LAZY = localCoroutineStart2;
    CoroutineStart localCoroutineStart3 = new CoroutineStart("ATOMIC", 2);
    ATOMIC = localCoroutineStart3;
    CoroutineStart localCoroutineStart4 = new CoroutineStart("UNDISPATCHED", 3);
    UNDISPATCHED = localCoroutineStart4;
    $VALUES = new CoroutineStart[] { localCoroutineStart1, localCoroutineStart2, localCoroutineStart3, localCoroutineStart4 };
  }
  
  private CoroutineStart() {}
  
  public final <T> void invoke(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    int i = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4) {
            throw new NoWhenBranchMatchedException();
          }
        }
        else {
          UndispatchedKt.startCoroutineUndispatched(paramFunction1, paramContinuation);
        }
      }
      else {
        ContinuationKt.startCoroutine(paramFunction1, paramContinuation);
      }
    }
    else {
      CancellableKt.startCoroutineCancellable(paramFunction1, paramContinuation);
    }
  }
  
  public final <R, T> void invoke(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    int i = CoroutineStart.WhenMappings.$EnumSwitchMapping$1[ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4) {
            throw new NoWhenBranchMatchedException();
          }
        }
        else {
          UndispatchedKt.startCoroutineUndispatched(paramFunction2, paramR, paramContinuation);
        }
      }
      else {
        ContinuationKt.startCoroutine(paramFunction2, paramR, paramContinuation);
      }
    }
    else {
      CancellableKt.startCoroutineCancellable(paramFunction2, paramR, paramContinuation);
    }
  }
  
  public final boolean isLazy()
  {
    boolean bool;
    if ((CoroutineStart)this == LAZY) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
