package androidx.work.impl.constraints;

import java.util.List;

public abstract interface WorkConstraintsCallback
{
  public abstract void onAllConstraintsMet(List<String> paramList);
  
  public abstract void onAllConstraintsNotMet(List<String> paramList);
}
