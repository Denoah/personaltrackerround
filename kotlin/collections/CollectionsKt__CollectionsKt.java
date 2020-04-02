package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;

@Metadata(bv={1, 0, 3}, d1={"\000|\n\000\n\002\030\002\n\002\020\036\n\002\b\003\n\002\020\b\n\000\n\002\020 \n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020!\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\b\005\n\002\020\002\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\r\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\017\n\002\b\007\n\002\020\013\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\006\032@\020\013\032\b\022\004\022\002H\0070\b\"\004\b\000\020\0072\006\020\f\032\0020\0062!\020\r\032\035\022\023\022\0210\006?\006\f\b\017\022\b\b\020\022\004\b\b(\021\022\004\022\002H\0070\016H?\b\032@\020\022\032\b\022\004\022\002H\0070\023\"\004\b\000\020\0072\006\020\f\032\0020\0062!\020\r\032\035\022\023\022\0210\006?\006\f\b\017\022\b\b\020\022\004\b\b(\021\022\004\022\002H\0070\016H?\b\032\037\020\024\032\022\022\004\022\002H\0070\025j\b\022\004\022\002H\007`\026\"\004\b\000\020\007H?\b\0325\020\024\032\022\022\004\022\002H\0070\025j\b\022\004\022\002H\007`\026\"\004\b\000\020\0072\022\020\027\032\n\022\006\b\001\022\002H\0070\030\"\002H\007?\006\002\020\031\032K\020\032\032\b\022\004\022\002H\0330\b\"\004\b\000\020\0332\006\020\034\032\0020\0062\037\b\001\020\035\032\031\022\n\022\b\022\004\022\002H\0330\023\022\004\022\0020\0360\016?\006\002\b\037H?\b?\002\n\n\b\b\001\022\002\020\002 \001\032C\020\032\032\b\022\004\022\002H\0330\b\"\004\b\000\020\0332\037\b\001\020\035\032\031\022\n\022\b\022\004\022\002H\0330\023\022\004\022\0020\0360\016?\006\002\b\037H?\b?\002\n\n\b\b\001\022\002\020\001 \001\032\022\020 \032\b\022\004\022\002H\0070\b\"\004\b\000\020\007\032\025\020!\032\b\022\004\022\002H\0070\b\"\004\b\000\020\007H?\b\032+\020!\032\b\022\004\022\002H\0070\b\"\004\b\000\020\0072\022\020\027\032\n\022\006\b\001\022\002H\0070\030\"\002H\007?\006\002\020\"\032%\020#\032\b\022\004\022\002H\0070\b\"\b\b\000\020\007*\0020$2\b\020%\032\004\030\001H\007?\006\002\020&\0323\020#\032\b\022\004\022\002H\0070\b\"\b\b\000\020\007*\0020$2\026\020\027\032\f\022\b\b\001\022\004\030\001H\0070\030\"\004\030\001H\007?\006\002\020\"\032\025\020'\032\b\022\004\022\002H\0070\023\"\004\b\000\020\007H?\b\032+\020'\032\b\022\004\022\002H\0070\023\"\004\b\000\020\0072\022\020\027\032\n\022\006\b\001\022\002H\0070\030\"\002H\007?\006\002\020\"\032%\020(\032\0020\0362\006\020\f\032\0020\0062\006\020)\032\0020\0062\006\020*\032\0020\006H\002?\006\002\b+\032\b\020,\032\0020\036H\001\032\b\020-\032\0020\036H\001\032%\020.\032\b\022\004\022\002H\0070\002\"\004\b\000\020\007*\n\022\006\b\001\022\002H\0070\030H\000?\006\002\020/\032S\0200\032\0020\006\"\004\b\000\020\007*\b\022\004\022\002H\0070\b2\006\020%\032\002H\0072\032\0201\032\026\022\006\b\000\022\002H\00702j\n\022\006\b\000\022\002H\007`32\b\b\002\020)\032\0020\0062\b\b\002\020*\032\0020\006?\006\002\0204\032>\0200\032\0020\006\"\004\b\000\020\007*\b\022\004\022\002H\0070\b2\b\b\002\020)\032\0020\0062\b\b\002\020*\032\0020\0062\022\0205\032\016\022\004\022\002H\007\022\004\022\0020\0060\016\032E\0200\032\0020\006\"\016\b\000\020\007*\b\022\004\022\002H\00706*\n\022\006\022\004\030\001H\0070\b2\b\020%\032\004\030\001H\0072\b\b\002\020)\032\0020\0062\b\b\002\020*\032\0020\006?\006\002\0207\032d\0208\032\0020\006\"\004\b\000\020\007\"\016\b\001\0209*\b\022\004\022\002H906*\b\022\004\022\002H\0070\b2\b\020:\032\004\030\001H92\b\b\002\020)\032\0020\0062\b\b\002\020*\032\0020\0062\026\b\004\020;\032\020\022\004\022\002H\007\022\006\022\004\030\001H90\016H?\b?\006\002\020<\032,\020=\032\0020>\"\t\b\000\020\007?\006\002\b?*\b\022\004\022\002H\0070\0022\f\020\027\032\b\022\004\022\002H\0070\002H?\b\0328\020@\032\002HA\"\020\b\000\020B*\006\022\002\b\0030\002*\002HA\"\004\b\001\020A*\002HB2\f\020C\032\b\022\004\022\002HA0DH?\b?\006\002\020E\032\031\020F\032\0020>\"\004\b\000\020\007*\b\022\004\022\002H\0070\002H?\b\032,\020G\032\0020>\"\004\b\000\020\007*\n\022\004\022\002H\007\030\0010\002H?\b?\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000\032\036\020H\032\b\022\004\022\002H\0070\b\"\004\b\000\020\007*\b\022\004\022\002H\0070\bH\000\032!\020I\032\b\022\004\022\002H\0070\002\"\004\b\000\020\007*\n\022\004\022\002H\007\030\0010\002H?\b\032!\020I\032\b\022\004\022\002H\0070\b\"\004\b\000\020\007*\n\022\004\022\002H\007\030\0010\bH?\b\"\031\020\000\032\0020\001*\006\022\002\b\0030\0028F?\006\006\032\004\b\003\020\004\"!\020\005\032\0020\006\"\004\b\000\020\007*\b\022\004\022\002H\0070\b8F?\006\006\032\004\b\t\020\n?\006J"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "buildList", "E", "capacity", "builderAction", "", "Lkotlin/ExtensionFunctionType;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__CollectionsKt
  extends CollectionsKt__CollectionsJVMKt
{
  public CollectionsKt__CollectionsKt() {}
  
  private static final <T> List<T> List(int paramInt, Function1<? super Integer, ? extends T> paramFunction1)
  {
    ArrayList localArrayList = new ArrayList(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localArrayList.add(paramFunction1.invoke(Integer.valueOf(i)));
    }
    return (List)localArrayList;
  }
  
  private static final <T> List<T> MutableList(int paramInt, Function1<? super Integer, ? extends T> paramFunction1)
  {
    ArrayList localArrayList = new ArrayList(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localArrayList.add(paramFunction1.invoke(Integer.valueOf(i)));
    }
    return (List)localArrayList;
  }
  
  private static final <T> ArrayList<T> arrayListOf()
  {
    return new ArrayList();
  }
  
  public static final <T> ArrayList<T> arrayListOf(T... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    if (paramVarArgs.length == 0) {
      paramVarArgs = new ArrayList();
    } else {
      paramVarArgs = new ArrayList((Collection)new ArrayAsCollection(paramVarArgs, true));
    }
    return paramVarArgs;
  }
  
  public static final <T> Collection<T> asCollection(T[] paramArrayOfT)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfT, "$this$asCollection");
    return (Collection)new ArrayAsCollection(paramArrayOfT, false);
  }
  
  public static final <T> int binarySearch(List<? extends T> paramList, int paramInt1, int paramInt2, Function1<? super T, Integer> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$binarySearch");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "comparison");
    rangeCheck$CollectionsKt__CollectionsKt(paramList.size(), paramInt1, paramInt2);
    paramInt2--;
    while (paramInt1 <= paramInt2)
    {
      int i = paramInt1 + paramInt2 >>> 1;
      int j = ((Number)paramFunction1.invoke(paramList.get(i))).intValue();
      if (j < 0) {
        paramInt1 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static final <T extends Comparable<? super T>> int binarySearch(List<? extends T> paramList, T paramT, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$binarySearch");
    rangeCheck$CollectionsKt__CollectionsKt(paramList.size(), paramInt1, paramInt2);
    paramInt2--;
    while (paramInt1 <= paramInt2)
    {
      int i = paramInt1 + paramInt2 >>> 1;
      int j = ComparisonsKt.compareValues((Comparable)paramList.get(i), paramT);
      if (j < 0) {
        paramInt1 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static final <T> int binarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$binarySearch");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    rangeCheck$CollectionsKt__CollectionsKt(paramList.size(), paramInt1, paramInt2);
    paramInt2--;
    while (paramInt1 <= paramInt2)
    {
      int i = paramInt1 + paramInt2 >>> 1;
      int j = paramComparator.compare(paramList.get(i), paramT);
      if (j < 0) {
        paramInt1 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static final <T, K extends Comparable<? super K>> int binarySearchBy(List<? extends T> paramList, final K paramK, int paramInt1, int paramInt2, Function1<? super T, ? extends K> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$binarySearchBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    CollectionsKt.binarySearch(paramList, paramInt1, paramInt2, (Function1)new Lambda(paramFunction1)
    {
      public final int invoke(T paramAnonymousT)
      {
        return ComparisonsKt.compareValues((Comparable)this.$selector.invoke(paramAnonymousT), paramK);
      }
    });
  }
  
  private static final <E> List<E> buildList(int paramInt, Function1<? super List<E>, Unit> paramFunction1)
  {
    ArrayList localArrayList = new ArrayList(paramInt);
    paramFunction1.invoke(localArrayList);
    return (List)localArrayList;
  }
  
  private static final <E> List<E> buildList(Function1<? super List<E>, Unit> paramFunction1)
  {
    ArrayList localArrayList = new ArrayList();
    paramFunction1.invoke(localArrayList);
    return (List)localArrayList;
  }
  
  private static final <T> boolean containsAll(Collection<? extends T> paramCollection1, Collection<? extends T> paramCollection2)
  {
    return paramCollection1.containsAll(paramCollection2);
  }
  
  public static final <T> List<T> emptyList()
  {
    return (List)EmptyList.INSTANCE;
  }
  
  public static final IntRange getIndices(Collection<?> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$indices");
    return new IntRange(0, paramCollection.size() - 1);
  }
  
  public static final <T> int getLastIndex(List<? extends T> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$lastIndex");
    return paramList.size() - 1;
  }
  
  private static final <C extends Collection<?>,  extends R, R> R ifEmpty(C paramC, Function0<? extends R> paramFunction0)
  {
    Object localObject = paramC;
    if (paramC.isEmpty()) {
      localObject = paramFunction0.invoke();
    }
    return localObject;
  }
  
  private static final <T> boolean isNotEmpty(Collection<? extends T> paramCollection)
  {
    return paramCollection.isEmpty() ^ true;
  }
  
  private static final <T> boolean isNullOrEmpty(Collection<? extends T> paramCollection)
  {
    boolean bool;
    if ((paramCollection != null) && (!paramCollection.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static final <T> List<T> listOf()
  {
    return CollectionsKt.emptyList();
  }
  
  public static final <T> List<T> listOf(T... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    if (paramVarArgs.length > 0) {
      paramVarArgs = ArraysKt.asList(paramVarArgs);
    } else {
      paramVarArgs = CollectionsKt.emptyList();
    }
    return paramVarArgs;
  }
  
  public static final <T> List<T> listOfNotNull(T paramT)
  {
    if (paramT != null) {
      paramT = CollectionsKt.listOf(paramT);
    } else {
      paramT = CollectionsKt.emptyList();
    }
    return paramT;
  }
  
  public static final <T> List<T> listOfNotNull(T... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return ArraysKt.filterNotNull(paramVarArgs);
  }
  
  private static final <T> List<T> mutableListOf()
  {
    return (List)new ArrayList();
  }
  
  public static final <T> List<T> mutableListOf(T... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    if (paramVarArgs.length == 0) {
      paramVarArgs = (List)new ArrayList();
    } else {
      paramVarArgs = (List)new ArrayList((Collection)new ArrayAsCollection(paramVarArgs, true));
    }
    return paramVarArgs;
  }
  
  public static final <T> List<T> optimizeReadOnlyList(List<? extends T> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$optimizeReadOnlyList");
    int i = paramList.size();
    if (i != 0)
    {
      if (i == 1) {
        paramList = CollectionsKt.listOf(paramList.get(0));
      }
    }
    else {
      paramList = CollectionsKt.emptyList();
    }
    return paramList;
  }
  
  private static final <T> Collection<T> orEmpty(Collection<? extends T> paramCollection)
  {
    if (paramCollection == null) {
      paramCollection = (Collection)CollectionsKt.emptyList();
    }
    return paramCollection;
  }
  
  private static final <T> List<T> orEmpty(List<? extends T> paramList)
  {
    if (paramList == null) {
      paramList = CollectionsKt.emptyList();
    }
    return paramList;
  }
  
  private static final void rangeCheck$CollectionsKt__CollectionsKt(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 <= paramInt3)
    {
      if (paramInt2 >= 0)
      {
        if (paramInt3 <= paramInt1) {
          return;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("toIndex (");
        localStringBuilder.append(paramInt3);
        localStringBuilder.append(") is greater than size (");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append(").");
        throw ((Throwable)new IndexOutOfBoundsException(localStringBuilder.toString()));
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("fromIndex (");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append(") is less than zero.");
      throw ((Throwable)new IndexOutOfBoundsException(localStringBuilder.toString()));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("fromIndex (");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(") is greater than toIndex (");
    localStringBuilder.append(paramInt3);
    localStringBuilder.append(").");
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final void throwCountOverflow()
  {
    throw ((Throwable)new ArithmeticException("Count overflow has happened."));
  }
  
  public static final void throwIndexOverflow()
  {
    throw ((Throwable)new ArithmeticException("Index overflow has happened."));
  }
}
