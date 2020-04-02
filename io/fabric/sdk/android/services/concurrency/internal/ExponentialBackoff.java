package io.fabric.sdk.android.services.concurrency.internal;

public class ExponentialBackoff
  implements Backoff
{
  private static final int DEFAULT_POWER = 2;
  private final long baseTimeMillis;
  private final int power;
  
  public ExponentialBackoff(long paramLong)
  {
    this(paramLong, 2);
  }
  
  public ExponentialBackoff(long paramLong, int paramInt)
  {
    this.baseTimeMillis = paramLong;
    this.power = paramInt;
  }
  
  public long getDelayMillis(int paramInt)
  {
    return (this.baseTimeMillis * Math.pow(this.power, paramInt));
  }
}
