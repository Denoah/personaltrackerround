package androidx.work.impl.constraints;

public abstract interface ConstraintListener<T>
{
  public abstract void onConstraintChanged(T paramT);
}
