package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bf\030\0002\0020\001JB\020\002\032\0020\003\"\004\b\000\020\0042\f\020\005\032\b\022\004\022\002H\0040\0062\034\020\007\032\030\b\001\022\n\022\b\022\004\022\002H\0040\t\022\006\022\004\030\0010\0010\bH'?\001\000?\006\002\020\n?\002\004\n\002\b\031?\006\013"}, d2={"Lkotlinx/coroutines/selects/SelectClause0;", "", "registerSelectClause0", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface SelectClause0
{
  public abstract <R> void registerSelectClause0(SelectInstance<? super R> paramSelectInstance, Function1<? super Continuation<? super R>, ? extends Object> paramFunction1);
}
