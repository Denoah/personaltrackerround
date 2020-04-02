package io.fabric.sdk.android.services.concurrency.internal;

public abstract interface Backoff
{
  public abstract long getDelayMillis(int paramInt);
}
