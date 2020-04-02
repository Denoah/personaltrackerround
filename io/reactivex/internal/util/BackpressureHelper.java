package io.reactivex.internal.util;

import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;

public final class BackpressureHelper
{
  private BackpressureHelper()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static long add(AtomicLong paramAtomicLong, long paramLong)
  {
    long l;
    do
    {
      l = paramAtomicLong.get();
      if (l == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
    } while (!paramAtomicLong.compareAndSet(l, addCap(l, paramLong)));
    return l;
  }
  
  public static long addCancel(AtomicLong paramAtomicLong, long paramLong)
  {
    long l;
    do
    {
      l = paramAtomicLong.get();
      if (l == Long.MIN_VALUE) {
        return Long.MIN_VALUE;
      }
      if (l == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
    } while (!paramAtomicLong.compareAndSet(l, addCap(l, paramLong)));
    return l;
  }
  
  public static long addCap(long paramLong1, long paramLong2)
  {
    paramLong2 = paramLong1 + paramLong2;
    paramLong1 = paramLong2;
    if (paramLong2 < 0L) {
      paramLong1 = Long.MAX_VALUE;
    }
    return paramLong1;
  }
  
  public static long multiplyCap(long paramLong1, long paramLong2)
  {
    long l = paramLong1 * paramLong2;
    if (((paramLong1 | paramLong2) >>> 31 != 0L) && (l / paramLong1 != paramLong2)) {
      return Long.MAX_VALUE;
    }
    return l;
  }
  
  public static long produced(AtomicLong paramAtomicLong, long paramLong)
  {
    long l1;
    long l3;
    do
    {
      l1 = paramAtomicLong.get();
      if (l1 == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
      long l2 = l1 - paramLong;
      l3 = l2;
      if (l2 < 0L)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("More produced than requested: ");
        localStringBuilder.append(l2);
        RxJavaPlugins.onError(new IllegalStateException(localStringBuilder.toString()));
        l3 = 0L;
      }
    } while (!paramAtomicLong.compareAndSet(l1, l3));
    return l3;
  }
  
  public static long producedCancel(AtomicLong paramAtomicLong, long paramLong)
  {
    long l1;
    long l3;
    do
    {
      l1 = paramAtomicLong.get();
      if (l1 == Long.MIN_VALUE) {
        return Long.MIN_VALUE;
      }
      if (l1 == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
      long l2 = l1 - paramLong;
      l3 = l2;
      if (l2 < 0L)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("More produced than requested: ");
        localStringBuilder.append(l2);
        RxJavaPlugins.onError(new IllegalStateException(localStringBuilder.toString()));
        l3 = 0L;
      }
    } while (!paramAtomicLong.compareAndSet(l1, l3));
    return l3;
  }
}
