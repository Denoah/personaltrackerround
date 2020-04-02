package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\000\n\002\020\b\n\000\n\002\020\002\n\002\b\004\n\002\020$\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\020\017\n\000\n\002\020\021\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\016\n\002\b\004\n\002\030\002\n\000\032\021\020\002\032\0020\0032\006\020\004\032\0020\001H?\b\032\020\020\005\032\0020\0012\006\020\006\032\0020\001H\001\0322\020\007\032\016\022\004\022\002H\t\022\004\022\002H\n0\b\"\004\b\000\020\t\"\004\b\001\020\n2\022\020\013\032\016\022\004\022\002H\t\022\004\022\002H\n0\f\032Y\020\r\032\016\022\004\022\002H\t\022\004\022\002H\n0\016\"\016\b\000\020\t*\b\022\004\022\002H\t0\017\"\004\b\001\020\n2*\020\020\032\026\022\022\b\001\022\016\022\004\022\002H\t\022\004\022\002H\n0\f0\021\"\016\022\004\022\002H\t\022\004\022\002H\n0\f?\006\002\020\022\032@\020\023\032\002H\n\"\004\b\000\020\t\"\004\b\001\020\n*\016\022\004\022\002H\t\022\004\022\002H\n0\0242\006\020\025\032\002H\t2\f\020\026\032\b\022\004\022\002H\n0\027H?\b?\006\002\020\030\032\031\020\031\032\0020\032*\016\022\004\022\0020\033\022\004\022\0020\0330\bH?\b\0322\020\034\032\016\022\004\022\002H\t\022\004\022\002H\n0\b\"\004\b\000\020\t\"\004\b\001\020\n*\020\022\006\b\001\022\002H\t\022\004\022\002H\n0\bH\000\0321\020\035\032\016\022\004\022\002H\t\022\004\022\002H\n0\b\"\004\b\000\020\t\"\004\b\001\020\n*\016\022\004\022\002H\t\022\004\022\002H\n0\bH?\b\032:\020\036\032\016\022\004\022\002H\t\022\004\022\002H\n0\016\"\016\b\000\020\t*\b\022\004\022\002H\t0\017\"\004\b\001\020\n*\020\022\006\b\001\022\002H\t\022\004\022\002H\n0\b\032@\020\036\032\016\022\004\022\002H\t\022\004\022\002H\n0\016\"\004\b\000\020\t\"\004\b\001\020\n*\020\022\006\b\001\022\002H\t\022\004\022\002H\n0\b2\016\020\037\032\n\022\006\b\000\022\002H\t0 \"\016\020\000\032\0020\001X?T?\006\002\n\000?\006!"}, d2={"INT_MAX_POWER_OF_TWO", "", "checkBuilderCapacity", "", "capacity", "mapCapacity", "expectedSize", "mapOf", "", "K", "V", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "", "pairs", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "comparator", "Ljava/util/Comparator;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/MapsKt")
class MapsKt__MapsJVMKt
  extends MapsKt__MapWithDefaultKt
{
  private static final int INT_MAX_POWER_OF_TWO = 1073741824;
  
  public MapsKt__MapsJVMKt() {}
  
  private static final void checkBuilderCapacity(int paramInt) {}
  
  public static final <K, V> V getOrPut(ConcurrentMap<K, V> paramConcurrentMap, K paramK, Function0<? extends V> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramConcurrentMap, "$this$getOrPut");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    Object localObject = paramConcurrentMap.get(paramK);
    if (localObject != null)
    {
      paramConcurrentMap = localObject;
    }
    else
    {
      paramFunction0 = paramFunction0.invoke();
      paramK = paramConcurrentMap.putIfAbsent(paramK, paramFunction0);
      paramConcurrentMap = paramFunction0;
      if (paramK != null) {
        paramConcurrentMap = paramK;
      }
    }
    return paramConcurrentMap;
  }
  
  public static final int mapCapacity(int paramInt)
  {
    if (paramInt >= 0) {
      if (paramInt < 3) {
        paramInt++;
      } else if (paramInt < 1073741824) {
        paramInt = (int)(paramInt / 0.75F + 1.0F);
      } else {
        paramInt = Integer.MAX_VALUE;
      }
    }
    return paramInt;
  }
  
  public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V> paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramPair, "pair");
    paramPair = Collections.singletonMap(paramPair.getFirst(), paramPair.getSecond());
    Intrinsics.checkExpressionValueIsNotNull(paramPair, "java.util.Collections.si…(pair.first, pair.second)");
    return paramPair;
  }
  
  public static final <K extends Comparable<? super K>, V> SortedMap<K, V> sortedMapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    TreeMap localTreeMap = new TreeMap();
    MapsKt.putAll((Map)localTreeMap, paramVarArgs);
    return (SortedMap)localTreeMap;
  }
  
  private static final Properties toProperties(Map<String, String> paramMap)
  {
    Properties localProperties = new Properties();
    localProperties.putAll(paramMap);
    return localProperties;
  }
  
  public static final <K, V> Map<K, V> toSingletonMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$toSingletonMap");
    paramMap = (Map.Entry)paramMap.entrySet().iterator().next();
    paramMap = Collections.singletonMap(paramMap.getKey(), paramMap.getValue());
    Intrinsics.checkExpressionValueIsNotNull(paramMap, "java.util.Collections.singletonMap(key, value)");
    Intrinsics.checkExpressionValueIsNotNull(paramMap, "with(entries.iterator().…ingletonMap(key, value) }");
    return paramMap;
  }
  
  private static final <K, V> Map<K, V> toSingletonMapOrSelf(Map<K, ? extends V> paramMap)
  {
    return MapsKt.toSingletonMap(paramMap);
  }
  
  public static final <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$toSortedMap");
    return (SortedMap)new TreeMap(paramMap);
  }
  
  public static final <K, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> paramMap, Comparator<? super K> paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$toSortedMap");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    paramComparator = new TreeMap(paramComparator);
    paramComparator.putAll(paramMap);
    return (SortedMap)paramComparator;
  }
}
