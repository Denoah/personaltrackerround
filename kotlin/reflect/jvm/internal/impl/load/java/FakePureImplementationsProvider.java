package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class FakePureImplementationsProvider
{
  public static final FakePureImplementationsProvider INSTANCE;
  private static final HashMap<FqName, FqName> pureImplementations;
  
  static
  {
    FakePureImplementationsProvider localFakePureImplementationsProvider = new FakePureImplementationsProvider();
    INSTANCE = localFakePureImplementationsProvider;
    pureImplementations = new HashMap();
    FqName localFqName = KotlinBuiltIns.FQ_NAMES.mutableList;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "FQ_NAMES.mutableList");
    localFakePureImplementationsProvider.implementedWith(localFqName, localFakePureImplementationsProvider.fqNameListOf(new String[] { "java.util.ArrayList", "java.util.LinkedList" }));
    localFqName = KotlinBuiltIns.FQ_NAMES.mutableSet;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "FQ_NAMES.mutableSet");
    localFakePureImplementationsProvider.implementedWith(localFqName, localFakePureImplementationsProvider.fqNameListOf(new String[] { "java.util.HashSet", "java.util.TreeSet", "java.util.LinkedHashSet" }));
    localFqName = KotlinBuiltIns.FQ_NAMES.mutableMap;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "FQ_NAMES.mutableMap");
    localFakePureImplementationsProvider.implementedWith(localFqName, localFakePureImplementationsProvider.fqNameListOf(new String[] { "java.util.HashMap", "java.util.TreeMap", "java.util.LinkedHashMap", "java.util.concurrent.ConcurrentHashMap", "java.util.concurrent.ConcurrentSkipListMap" }));
    localFakePureImplementationsProvider.implementedWith(new FqName("java.util.function.Function"), localFakePureImplementationsProvider.fqNameListOf(new String[] { "java.util.function.UnaryOperator" }));
    localFakePureImplementationsProvider.implementedWith(new FqName("java.util.function.BiFunction"), localFakePureImplementationsProvider.fqNameListOf(new String[] { "java.util.function.BinaryOperator" }));
  }
  
  private FakePureImplementationsProvider() {}
  
  private final List<FqName> fqNameListOf(String... paramVarArgs)
  {
    Collection localCollection = (Collection)new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      localCollection.add(new FqName(paramVarArgs[j]));
    }
    return (List)localCollection;
  }
  
  private final void implementedWith(FqName paramFqName, List<FqName> paramList)
  {
    Object localObject1 = (Iterable)paramList;
    paramList = (Map)pureImplementations;
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (FqName)localObject2;
      paramList.put(localObject2, paramFqName);
    }
  }
  
  public final FqName getPurelyImplementedInterface(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "classFqName");
    return (FqName)pureImplementations.get(paramFqName);
  }
}
