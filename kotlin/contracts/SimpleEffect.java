package kotlin.contracts;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\bg\030\0002\0020\001J\021\020\002\032\0020\0032\006\020\004\032\0020\005H�\004?\006\006"}, d2={"Lkotlin/contracts/SimpleEffect;", "Lkotlin/contracts/Effect;", "implies", "Lkotlin/contracts/ConditionalEffect;", "booleanExpression", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract interface SimpleEffect
  extends Effect
{
  public abstract ConditionalEffect implies(boolean paramBoolean);
}
