package androidx.work.impl.constraints.controllers;

import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.constraints.trackers.ConstraintTracker;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ConstraintController<T>
  implements ConstraintListener<T>
{
  private OnConstraintUpdatedCallback mCallback;
  private T mCurrentValue;
  private final List<String> mMatchingWorkSpecIds = new ArrayList();
  private ConstraintTracker<T> mTracker;
  
  ConstraintController(ConstraintTracker<T> paramConstraintTracker)
  {
    this.mTracker = paramConstraintTracker;
  }
  
  private void updateCallback(OnConstraintUpdatedCallback paramOnConstraintUpdatedCallback, T paramT)
  {
    if ((!this.mMatchingWorkSpecIds.isEmpty()) && (paramOnConstraintUpdatedCallback != null)) {
      if ((paramT != null) && (!isConstrained(paramT))) {
        paramOnConstraintUpdatedCallback.onConstraintMet(this.mMatchingWorkSpecIds);
      } else {
        paramOnConstraintUpdatedCallback.onConstraintNotMet(this.mMatchingWorkSpecIds);
      }
    }
  }
  
  abstract boolean hasConstraint(WorkSpec paramWorkSpec);
  
  abstract boolean isConstrained(T paramT);
  
  public boolean isWorkSpecConstrained(String paramString)
  {
    Object localObject = this.mCurrentValue;
    boolean bool;
    if ((localObject != null) && (isConstrained(localObject)) && (this.mMatchingWorkSpecIds.contains(paramString))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onConstraintChanged(T paramT)
  {
    this.mCurrentValue = paramT;
    updateCallback(this.mCallback, paramT);
  }
  
  public void replace(Iterable<WorkSpec> paramIterable)
  {
    this.mMatchingWorkSpecIds.clear();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      paramIterable = (WorkSpec)localIterator.next();
      if (hasConstraint(paramIterable)) {
        this.mMatchingWorkSpecIds.add(paramIterable.id);
      }
    }
    if (this.mMatchingWorkSpecIds.isEmpty()) {
      this.mTracker.removeListener(this);
    } else {
      this.mTracker.addListener(this);
    }
    updateCallback(this.mCallback, this.mCurrentValue);
  }
  
  public void reset()
  {
    if (!this.mMatchingWorkSpecIds.isEmpty())
    {
      this.mMatchingWorkSpecIds.clear();
      this.mTracker.removeListener(this);
    }
  }
  
  public void setCallback(OnConstraintUpdatedCallback paramOnConstraintUpdatedCallback)
  {
    if (this.mCallback != paramOnConstraintUpdatedCallback)
    {
      this.mCallback = paramOnConstraintUpdatedCallback;
      updateCallback(paramOnConstraintUpdatedCallback, this.mCurrentValue);
    }
  }
  
  public static abstract interface OnConstraintUpdatedCallback
  {
    public abstract void onConstraintMet(List<String> paramList);
    
    public abstract void onConstraintNotMet(List<String> paramList);
  }
}
