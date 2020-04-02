package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;

public final class LazyScopeAdapter
  extends AbstractScopeAdapter
{
  private final NotNullLazyValue<MemberScope> scope;
  
  public LazyScopeAdapter(NotNullLazyValue<? extends MemberScope> paramNotNullLazyValue)
  {
    this.scope = paramNotNullLazyValue;
  }
  
  protected MemberScope getWorkerScope()
  {
    return (MemberScope)this.scope.invoke();
  }
}
