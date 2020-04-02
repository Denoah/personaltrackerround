package io.fabric.sdk.android.services.concurrency.internal;

public class DefaultRetryPolicy
  implements RetryPolicy
{
  private final int maxRetries;
  
  public DefaultRetryPolicy()
  {
    this(1);
  }
  
  public DefaultRetryPolicy(int paramInt)
  {
    this.maxRetries = paramInt;
  }
  
  public boolean shouldRetry(int paramInt, Throwable paramThrowable)
  {
    boolean bool;
    if (paramInt < this.maxRetries) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
