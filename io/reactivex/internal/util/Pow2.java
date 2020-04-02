package io.reactivex.internal.util;

public final class Pow2
{
  private Pow2()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static boolean isPowerOfTwo(int paramInt)
  {
    boolean bool;
    if ((paramInt & paramInt - 1) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static int roundToPowerOfTwo(int paramInt)
  {
    return 1 << 32 - Integer.numberOfLeadingZeros(paramInt - 1);
  }
}
