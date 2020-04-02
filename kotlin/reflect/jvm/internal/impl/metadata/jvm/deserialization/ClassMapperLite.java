package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class ClassMapperLite
{
  public static final ClassMapperLite INSTANCE = new ClassMapperLite();
  private static final Map<String, String> map;
  
  static
  {
    Map localMap = (Map)new LinkedHashMap();
    Object localObject1 = CollectionsKt.listOf(new String[] { "Boolean", "Z", "Char", "C", "Byte", "B", "Short", "S", "Int", "I", "Float", "F", "Long", "J", "Double", "D" });
    Object localObject2 = RangesKt.step((IntProgression)CollectionsKt.getIndices((Collection)localObject1), 2);
    int i = ((IntProgression)localObject2).getFirst();
    int j = ((IntProgression)localObject2).getLast();
    int k = ((IntProgression)localObject2).getStep();
    if (k >= 0 ? i <= j : i >= j) {
      for (;;)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("kotlin/");
        ((StringBuilder)localObject2).append((String)((List)localObject1).get(i));
        localObject2 = ((StringBuilder)localObject2).toString();
        int m = i + 1;
        localMap.put(localObject2, ((List)localObject1).get(m));
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("kotlin/");
        ((StringBuilder)localObject2).append((String)((List)localObject1).get(i));
        ((StringBuilder)localObject2).append("Array");
        localObject3 = ((StringBuilder)localObject2).toString();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append('[');
        ((StringBuilder)localObject2).append((String)((List)localObject1).get(m));
        localMap.put(localObject3, ((StringBuilder)localObject2).toString());
        if (i == j) {
          break;
        }
        i += k;
      }
    }
    localMap.put("kotlin/Unit", "V");
    localObject1 = new Lambda(localMap)
    {
      public final void invoke(String paramAnonymousString1, String paramAnonymousString2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousString1, "kotlinSimpleName");
        Intrinsics.checkParameterIsNotNull(paramAnonymousString2, "javaInternalName");
        Map localMap = this.$this_apply;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("kotlin/");
        ((StringBuilder)localObject).append(paramAnonymousString1);
        localObject = ((StringBuilder)localObject).toString();
        paramAnonymousString1 = new StringBuilder();
        paramAnonymousString1.append('L');
        paramAnonymousString1.append(paramAnonymousString2);
        paramAnonymousString1.append(';');
        localMap.put(localObject, paramAnonymousString1.toString());
      }
    };
    ((map.1.1)localObject1).invoke("Any", "java/lang/Object");
    ((map.1.1)localObject1).invoke("Nothing", "java/lang/Void");
    ((map.1.1)localObject1).invoke("Annotation", "java/lang/annotation/Annotation");
    Object localObject4 = CollectionsKt.listOf(new String[] { "String", "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum" }).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject2 = (String)((Iterator)localObject4).next();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("java/lang/");
      ((StringBuilder)localObject3).append((String)localObject2);
      ((map.1.1)localObject1).invoke((String)localObject2, ((StringBuilder)localObject3).toString());
    }
    Object localObject3 = CollectionsKt.listOf(new String[] { "Iterator", "Collection", "List", "Set", "Map", "ListIterator" }).iterator();
    Object localObject5;
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (String)((Iterator)localObject3).next();
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("collections/");
      ((StringBuilder)localObject4).append((String)localObject2);
      localObject5 = ((StringBuilder)localObject4).toString();
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("java/util/");
      ((StringBuilder)localObject4).append((String)localObject2);
      ((map.1.1)localObject1).invoke((String)localObject5, ((StringBuilder)localObject4).toString());
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("collections/Mutable");
      ((StringBuilder)localObject4).append((String)localObject2);
      localObject5 = ((StringBuilder)localObject4).toString();
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("java/util/");
      ((StringBuilder)localObject4).append((String)localObject2);
      ((map.1.1)localObject1).invoke((String)localObject5, ((StringBuilder)localObject4).toString());
    }
    ((map.1.1)localObject1).invoke("collections/Iterable", "java/lang/Iterable");
    ((map.1.1)localObject1).invoke("collections/MutableIterable", "java/lang/Iterable");
    ((map.1.1)localObject1).invoke("collections/Map.Entry", "java/util/Map$Entry");
    ((map.1.1)localObject1).invoke("collections/MutableMap.MutableEntry", "java/util/Map$Entry");
    for (i = 0; i <= 22; i++)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Function");
      ((StringBuilder)localObject2).append(i);
      localObject2 = ((StringBuilder)localObject2).toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("kotlin/jvm/functions/Function");
      ((StringBuilder)localObject3).append(i);
      ((map.1.1)localObject1).invoke((String)localObject2, ((StringBuilder)localObject3).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("reflect/KFunction");
      ((StringBuilder)localObject2).append(i);
      ((map.1.1)localObject1).invoke(((StringBuilder)localObject2).toString(), "kotlin/reflect/KFunction");
    }
    localObject3 = CollectionsKt.listOf(new String[] { "Char", "Byte", "Short", "Int", "Float", "Long", "Double", "String", "Enum" }).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (String)((Iterator)localObject3).next();
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append((String)localObject2);
      ((StringBuilder)localObject4).append(".Companion");
      localObject4 = ((StringBuilder)localObject4).toString();
      localObject5 = new StringBuilder();
      ((StringBuilder)localObject5).append("kotlin/jvm/internal/");
      ((StringBuilder)localObject5).append((String)localObject2);
      ((StringBuilder)localObject5).append("CompanionObject");
      ((map.1.1)localObject1).invoke((String)localObject4, ((StringBuilder)localObject5).toString());
    }
    map = localMap;
  }
  
  private ClassMapperLite() {}
  
  @JvmStatic
  public static final String mapClass(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "classId");
    Object localObject = (String)map.get(paramString);
    if (localObject != null)
    {
      paramString = (String)localObject;
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append('L');
      ((StringBuilder)localObject).append(StringsKt.replace$default(paramString, '.', '$', false, 4, null));
      ((StringBuilder)localObject).append(';');
      paramString = ((StringBuilder)localObject).toString();
    }
    return paramString;
  }
}
