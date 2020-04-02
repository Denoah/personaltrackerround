package io.reactivex.disposables;

import io.reactivex.functions.Action;

final class ActionDisposable
  extends ReferenceDisposable<Action>
{
  private static final long serialVersionUID = -8219729196779211169L;
  
  ActionDisposable(Action paramAction)
  {
    super(paramAction);
  }
  
  protected void onDisposed(Action paramAction)
  {
    try
    {
      paramAction.run();
      return;
    }
    finally {}
  }
}
