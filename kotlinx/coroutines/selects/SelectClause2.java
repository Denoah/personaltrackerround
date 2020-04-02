package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\bf\030\000*\006\b\000\020\001 \000*\006\b\001\020\002 \0012\0020\003JP\020\004\032\0020\005\"\004\b\002\020\0062\f\020\007\032\b\022\004\022\002H\0060\b2\006\020\t\032\0028\0002\"\020\n\032\036\b\001\022\004\022\0028\001\022\n\022\b\022\004\022\002H\0060\f\022\006\022\004\030\0010\0030\013H'?\001\000?\006\002\020\r?\002\004\n\002\b\031?\006\016"}, d2={"Lkotlinx/coroutines/selects/SelectClause2;", "P", "Q", "", "registerSelectClause2", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "param", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface SelectClause2<P, Q>
{
  public abstract <R> void registerSelectClause2(SelectInstance<? super R> paramSelectInstance, P paramP, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> paramFunction2);
}
