package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

public final class ReflectClassUtilKt
{
  private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
  private static final List<KClass<? extends Object>> PRIMITIVE_CLASSES;
  private static final Map<Class<? extends Object>, Class<? extends Object>> PRIMITIVE_TO_WRAPPER;
  private static final Map<Class<? extends Object>, Class<? extends Object>> WRAPPER_TO_PRIMITIVE;
  
  static
  {
    Object localObject1 = Reflection.getOrCreateKotlinClass(Boolean.TYPE);
    int i = 0;
    localObject1 = CollectionsKt.listOf(new KClass[] { localObject1, Reflection.getOrCreateKotlinClass(Byte.TYPE), Reflection.getOrCreateKotlinClass(Character.TYPE), Reflection.getOrCreateKotlinClass(Double.TYPE), Reflection.getOrCreateKotlinClass(Float.TYPE), Reflection.getOrCreateKotlinClass(Integer.TYPE), Reflection.getOrCreateKotlinClass(Long.TYPE), Reflection.getOrCreateKotlinClass(Short.TYPE) });
    PRIMITIVE_CLASSES = (List)localObject1;
    Object localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    Object localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (KClass)((Iterator)localObject3).next();
      ((Collection)localObject1).add(TuplesKt.to(JvmClassMappingKt.getJavaObjectType((KClass)localObject2), JvmClassMappingKt.getJavaPrimitiveType((KClass)localObject2)));
    }
    WRAPPER_TO_PRIMITIVE = MapsKt.toMap((Iterable)localObject1);
    localObject2 = (Iterable)PRIMITIVE_CLASSES;
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (KClass)((Iterator)localObject3).next();
      ((Collection)localObject1).add(TuplesKt.to(JvmClassMappingKt.getJavaPrimitiveType((KClass)localObject2), JvmClassMappingKt.getJavaObjectType((KClass)localObject2)));
    }
    PRIMITIVE_TO_WRAPPER = MapsKt.toMap((Iterable)localObject1);
    localObject2 = (Iterable)CollectionsKt.listOf(new Class[] { Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class });
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = ((Iterator)localObject2).next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      ((Collection)localObject1).add(TuplesKt.to((Class)localObject3, Integer.valueOf(i)));
      i++;
    }
    FUNCTION_CLASSES = MapsKt.toMap((Iterable)localObject1);
  }
  
  public static final Class<?> createArrayType(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$createArrayType");
    return Array.newInstance(paramClass, 0).getClass();
  }
  
  public static final ClassId getClassId(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$classId");
    if (!paramClass.isPrimitive())
    {
      if (!paramClass.isArray())
      {
        if ((paramClass.getEnclosingMethod() == null) && (paramClass.getEnclosingConstructor() == null))
        {
          localObject = paramClass.getSimpleName();
          Intrinsics.checkExpressionValueIsNotNull(localObject, "simpleName");
          int i;
          if (((CharSequence)localObject).length() == 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0)
          {
            localObject = paramClass.getDeclaringClass();
            if (localObject != null)
            {
              localObject = getClassId((Class)localObject);
              if (localObject != null)
              {
                localObject = ((ClassId)localObject).createNestedClassId(Name.identifier(paramClass.getSimpleName()));
                if (localObject != null)
                {
                  paramClass = (Class<?>)localObject;
                  break label167;
                }
              }
            }
            paramClass = ClassId.topLevel(new FqName(paramClass.getName()));
            Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(FqName(name))");
            break label167;
          }
        }
        paramClass = new FqName(paramClass.getName());
        paramClass = new ClassId(paramClass.parent(), FqName.topLevel(paramClass.shortName()), true);
        label167:
        return paramClass;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Can't compute ClassId for array type: ");
      ((StringBuilder)localObject).append(paramClass);
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Can't compute ClassId for primitive type: ");
    ((StringBuilder)localObject).append(paramClass);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
  }
  
  public static final String getDesc(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$desc");
    if (Intrinsics.areEqual(paramClass, Void.TYPE)) {
      return "V";
    }
    paramClass = createArrayType(paramClass).getName();
    Intrinsics.checkExpressionValueIsNotNull(paramClass, "createArrayType().name");
    if (paramClass != null)
    {
      paramClass = paramClass.substring(1);
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "(this as java.lang.String).substring(startIndex)");
      return StringsKt.replace$default(paramClass, '.', '/', false, 4, null);
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public static final Integer getFunctionClassArity(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$functionClassArity");
    return (Integer)FUNCTION_CLASSES.get(paramClass);
  }
  
  public static final List<Type> getParameterizedTypeArguments(Type paramType)
  {
    Intrinsics.checkParameterIsNotNull(paramType, "$this$parameterizedTypeArguments");
    if (!(paramType instanceof ParameterizedType)) {
      return CollectionsKt.emptyList();
    }
    ParameterizedType localParameterizedType = (ParameterizedType)paramType;
    if (localParameterizedType.getOwnerType() == null)
    {
      paramType = localParameterizedType.getActualTypeArguments();
      Intrinsics.checkExpressionValueIsNotNull(paramType, "actualTypeArguments");
      return ArraysKt.toList(paramType);
    }
    return SequencesKt.toList(SequencesKt.flatMap(SequencesKt.generateSequence(paramType, (Function1)parameterizedTypeArguments.1.INSTANCE), (Function1)parameterizedTypeArguments.2.INSTANCE));
  }
  
  public static final Class<?> getPrimitiveByWrapper(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$primitiveByWrapper");
    return (Class)WRAPPER_TO_PRIMITIVE.get(paramClass);
  }
  
  public static final ClassLoader getSafeClassLoader(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$safeClassLoader");
    paramClass = paramClass.getClassLoader();
    if (paramClass == null)
    {
      paramClass = ClassLoader.getSystemClassLoader();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassLoader.getSystemClassLoader()");
    }
    return paramClass;
  }
  
  public static final Class<?> getWrapperByPrimitive(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$wrapperByPrimitive");
    return (Class)PRIMITIVE_TO_WRAPPER.get(paramClass);
  }
  
  public static final boolean isEnumClassOrSpecializedEnumEntryClass(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$isEnumClassOrSpecializedEnumEntryClass");
    return Enum.class.isAssignableFrom(paramClass);
  }
}
