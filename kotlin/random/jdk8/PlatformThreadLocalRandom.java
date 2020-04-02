package kotlin.random.jdk8;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.AbstractPlatformRandom;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\006\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\t\n\000\b\000\030\0002\0020\001B\005?\006\002\020\002J\020\020\007\032\0020\b2\006\020\t\032\0020\bH\026J\030\020\n\032\0020\0132\006\020\f\032\0020\0132\006\020\t\032\0020\013H\026J\020\020\r\032\0020\0162\006\020\t\032\0020\016H\026J\030\020\r\032\0020\0162\006\020\f\032\0020\0162\006\020\t\032\0020\016H\026R\024\020\003\032\0020\0048VX?\004?\006\006\032\004\b\005\020\006?\006\017"}, d2={"Lkotlin/random/jdk8/PlatformThreadLocalRandom;", "Lkotlin/random/AbstractPlatformRandom;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "nextDouble", "", "until", "nextInt", "", "from", "nextLong", "", "kotlin-stdlib-jdk8"}, k=1, mv={1, 1, 15})
public final class PlatformThreadLocalRandom
  extends AbstractPlatformRandom
{
  public PlatformThreadLocalRandom() {}
  
  public Random getImpl()
  {
    ThreadLocalRandom localThreadLocalRandom = ThreadLocalRandom.current();
    Intrinsics.checkExpressionValueIsNotNull(localThreadLocalRandom, "ThreadLocalRandom.current()");
    return (Random)localThreadLocalRandom;
  }
  
  public double nextDouble(double paramDouble)
  {
    return ThreadLocalRandom.current().nextDouble(paramDouble);
  }
  
  public int nextInt(int paramInt1, int paramInt2)
  {
    return ThreadLocalRandom.current().nextInt(paramInt1, paramInt2);
  }
  
  public long nextLong(long paramLong)
  {
    return ThreadLocalRandom.current().nextLong(paramLong);
  }
  
  public long nextLong(long paramLong1, long paramLong2)
  {
    return ThreadLocalRandom.current().nextLong(paramLong1, paramLong2);
  }
}
