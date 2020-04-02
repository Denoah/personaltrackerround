package io.fabric.sdk.android.services.concurrency.internal;

public abstract interface RetryPolicy
{
  public abstract boolean shouldRetry(int paramInt, Throwable paramThrowable);
}
