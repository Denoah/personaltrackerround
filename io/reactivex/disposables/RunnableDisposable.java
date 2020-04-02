package io.reactivex.disposables;

final class RunnableDisposable
  extends ReferenceDisposable<Runnable>
{
  private static final long serialVersionUID = -8219729196779211169L;
  
  RunnableDisposable(Runnable paramRunnable)
  {
    super(paramRunnable);
  }
  
  protected void onDisposed(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("RunnableDisposable(disposed=");
    localStringBuilder.append(isDisposed());
    localStringBuilder.append(", ");
    localStringBuilder.append(get());
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
