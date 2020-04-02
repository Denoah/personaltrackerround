package kotlin.time.jdk8;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\002\030\002\n\002\b\005\032\027\020\000\032\0020\001*\0020\002H?\b?\001\000?\006\004\b\003\020\004\032\025\020\005\032\0020\002*\0020\001H?\b?\001\000?\006\002\020\006?\002\004\n\002\b\031?\006\007"}, d2={"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(D)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)D", "kotlin-stdlib-jdk8"}, k=2, mv={1, 1, 15}, pn="kotlin.time")
public final class DurationConversionsJDK8Kt
{
  private static final java.time.Duration toJavaDuration-LRDsOJo(double paramDouble)
  {
    java.time.Duration localDuration = java.time.Duration.ofSeconds(kotlin.time.Duration.getInSeconds-impl(paramDouble), kotlin.time.Duration.getNanosecondsComponent-impl(paramDouble));
    Intrinsics.checkExpressionValueIsNotNull(localDuration, "java.time.Duration.ofSec…ds, nanoseconds.toLong())");
    Intrinsics.checkExpressionValueIsNotNull(localDuration, "toComponents { seconds, …, nanoseconds.toLong()) }");
    return localDuration;
  }
  
  private static final double toKotlinDuration(java.time.Duration paramDuration)
  {
    return kotlin.time.Duration.plus-LRDsOJo(DurationKt.getSeconds(paramDuration.getSeconds()), DurationKt.getNanoseconds(paramDuration.getNano()));
  }
}
