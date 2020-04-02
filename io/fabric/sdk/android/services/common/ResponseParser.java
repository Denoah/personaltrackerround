package io.fabric.sdk.android.services.common;

public class ResponseParser
{
  public static final int ResponseActionDiscard = 0;
  public static final int ResponseActionRetry = 1;
  
  public ResponseParser() {}
  
  public static int parse(int paramInt)
  {
    if ((paramInt >= 200) && (paramInt <= 299)) {
      return 0;
    }
    if ((paramInt >= 300) && (paramInt <= 399)) {
      return 1;
    }
    if ((paramInt >= 400) && (paramInt <= 499)) {
      return 0;
    }
    if (paramInt >= 500) {}
    return 1;
  }
}
