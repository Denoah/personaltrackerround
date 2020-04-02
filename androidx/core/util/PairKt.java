package androidx.core.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\0322\020\000\032\n \002*\004\030\001H\001H\001\"\004\b\000\020\001\"\004\b\001\020\003*\016\022\004\022\002H\001\022\004\022\002H\0030\004H?\n?\006\002\020\005\0322\020\006\032\n \002*\004\030\001H\003H\003\"\004\b\000\020\001\"\004\b\001\020\003*\016\022\004\022\002H\001\022\004\022\002H\0030\004H?\n?\006\002\020\005\0321\020\007\032\016\022\004\022\002H\001\022\004\022\002H\0030\004\"\004\b\000\020\001\"\004\b\001\020\003*\016\022\004\022\002H\001\022\004\022\002H\0030\bH?\b\032A\020\t\032\036\022\f\022\n \002*\004\030\001H\001H\001\022\f\022\n \002*\004\030\001H\003H\0030\b\"\004\b\000\020\001\"\004\b\001\020\003*\016\022\004\022\002H\001\022\004\022\002H\0030\004H?\b?\006\n"}, d2={"component1", "F", "kotlin.jvm.PlatformType", "S", "Landroid/util/Pair;", "(Landroid/util/Pair;)Ljava/lang/Object;", "component2", "toAndroidPair", "Lkotlin/Pair;", "toKotlinPair", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class PairKt
{
  public static final <F, S> F component1(android.util.Pair<F, S> paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramPair, "$this$component1");
    return paramPair.first;
  }
  
  public static final <F, S> S component2(android.util.Pair<F, S> paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramPair, "$this$component2");
    return paramPair.second;
  }
  
  public static final <F, S> android.util.Pair<F, S> toAndroidPair(kotlin.Pair<? extends F, ? extends S> paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramPair, "$this$toAndroidPair");
    return new android.util.Pair(paramPair.getFirst(), paramPair.getSecond());
  }
  
  public static final <F, S> kotlin.Pair<F, S> toKotlinPair(android.util.Pair<F, S> paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramPair, "$this$toKotlinPair");
    return new kotlin.Pair(paramPair.first, paramPair.second);
  }
}
