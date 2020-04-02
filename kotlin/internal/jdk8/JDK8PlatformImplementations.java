package kotlin.internal.jdk8;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.jdk8.PlatformThreadLocalRandom;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroup;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\b\020\030\0002\0020\001B\005?\006\002\020\002J\b\020\003\032\0020\004H\026J\032\020\005\032\004\030\0010\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\026?\006\013"}, d2={"Lkotlin/internal/jdk8/JDK8PlatformImplementations;", "Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "()V", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "kotlin-stdlib-jdk8"}, k=1, mv={1, 1, 15})
public class JDK8PlatformImplementations
  extends JDK7PlatformImplementations
{
  public JDK8PlatformImplementations() {}
  
  public Random defaultPlatformRandom()
  {
    return (Random)new PlatformThreadLocalRandom();
  }
  
  public MatchGroup getMatchResultNamedGroup(MatchResult paramMatchResult, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramMatchResult, "matchResult");
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    boolean bool = paramMatchResult instanceof Matcher;
    Object localObject = null;
    if (!bool) {
      paramMatchResult = null;
    }
    Matcher localMatcher = (Matcher)paramMatchResult;
    if (localMatcher != null)
    {
      IntRange localIntRange = new IntRange(localMatcher.start(paramString), localMatcher.end(paramString) - 1);
      paramMatchResult = localObject;
      if (localIntRange.getStart().intValue() >= 0)
      {
        paramMatchResult = localMatcher.group(paramString);
        Intrinsics.checkExpressionValueIsNotNull(paramMatchResult, "matcher.group(name)");
        paramMatchResult = new MatchGroup(paramMatchResult, localIntRange);
      }
      return paramMatchResult;
    }
    throw ((Throwable)new UnsupportedOperationException("Retrieving groups by name is not supported on this platform."));
  }
}
