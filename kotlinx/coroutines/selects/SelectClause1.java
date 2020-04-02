package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bf\030\000*\006\b\000\020\001 \0012\0020\002JH\020\003\032\0020\004\"\004\b\001\020\0052\f\020\006\032\b\022\004\022\002H\0050\0072\"\020\b\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\002H\0050\n\022\006\022\004\030\0010\0020\tH'?\001\000?\006\002\020\013?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlinx/coroutines/selects/SelectClause1;", "Q", "", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface SelectClause1<Q>
{
  public abstract <R> void registerSelectClause1(SelectInstance<? super R> paramSelectInstance, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2);
}
