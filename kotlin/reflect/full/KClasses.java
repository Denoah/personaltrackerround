package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KClassImpl.Data;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties.LazyVal;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors;
import kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler;
import kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandlerWithListResult;
import kotlin.reflect.jvm.internal.impl.utils.DFS.Visited;
import kotlin.reflect.jvm.internal.impl.utils.DFS.VisitedWithSet;

@Metadata(bv={1, 0, 3}, d1={"\000Z\n\000\n\002\020\036\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\007\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\n\n\002\020\013\n\002\030\002\n\002\b\026\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\r\032+\020S\032\002H\035\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0022\b\020T\032\004\030\0010\020H\007?\006\002\020U\032!\020V\032\002H\035\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\002H\007?\006\002\020\023\032\034\020W\032\00203*\006\022\002\b\0030\0022\n\020X\032\006\022\002\b\0030\002H\007\032\034\020Y\032\00203*\006\022\002\b\0030\0022\n\020Z\032\006\022\002\b\0030\002H\007\032-\020[\032\004\030\001H\035\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0022\b\020T\032\004\030\0010\020H\007?\006\002\020U\",\020\000\032\f\022\b\022\006\022\002\b\0030\0020\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\003\020\004\032\004\b\005\020\006\"(\020\007\032\b\022\004\022\0020\b0\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\t\020\004\032\004\b\n\020\006\"(\020\013\032\b\022\002\b\003\030\0010\002*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\f\020\004\032\004\b\r\020\016\"$\020\017\032\004\030\0010\020*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\021\020\004\032\004\b\022\020\023\",\020\024\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\026\020\004\032\004\b\027\020\006\",\020\030\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\031\020\004\032\004\b\032\020\006\"B\020\033\032\026\022\022\022\020\022\004\022\002H\035\022\002\b\003\022\002\b\0030\0340\001\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0028FX?\004?\006\f\022\004\b\036\020\004\032\004\b\037\020\006\",\020 \032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b!\020\004\032\004\b\"\020\006\">\020#\032\022\022\016\022\f\022\004\022\002H\035\022\002\b\0030$0\001\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0028FX?\004?\006\f\022\004\b%\020\004\032\004\b&\020\006\",\020'\032\f\022\b\022\006\022\002\b\0030(0\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b)\020\004\032\004\b*\020\006\"\"\020+\032\0020\b*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b,\020\004\032\004\b-\020.\",\020/\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b0\020\004\032\004\b1\020\006\"\034\0202\032\00203*\006\022\002\b\003048BX?\004?\006\006\032\004\b2\0205\"\034\0206\032\00203*\006\022\002\b\003048BX?\004?\006\006\032\004\b6\0205\",\0207\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b8\020\004\032\004\b9\020\006\"B\020:\032\026\022\022\022\020\022\004\022\002H\035\022\002\b\003\022\002\b\0030\0340\001\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0028FX?\004?\006\f\022\004\b;\020\004\032\004\b<\020\006\",\020=\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b>\020\004\032\004\b?\020\006\">\020@\032\022\022\016\022\f\022\004\022\002H\035\022\002\b\0030$0\001\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0028FX?\004?\006\f\022\004\bA\020\004\032\004\bB\020\006\"6\020C\032\n\022\004\022\002H\035\030\0010\025\"\b\b\000\020\035*\0020\020*\b\022\004\022\002H\0350\0028FX?\004?\006\f\022\004\bD\020\004\032\004\bE\020F\",\020G\032\f\022\b\022\006\022\002\b\0030\0250\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\bH\020\004\032\004\bI\020\006\",\020J\032\f\022\b\022\006\022\002\b\0030K0\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\bL\020\004\032\004\bM\020\006\",\020N\032\f\022\b\022\006\022\002\b\0030\0020O*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\bP\020\004\032\004\bQ\020R?\006\\"}, d2={"allSuperclasses", "", "Lkotlin/reflect/KClass;", "allSuperclasses$annotations", "(Lkotlin/reflect/KClass;)V", "getAllSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/Collection;", "allSupertypes", "Lkotlin/reflect/KType;", "allSupertypes$annotations", "getAllSupertypes", "companionObject", "companionObject$annotations", "getCompanionObject", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KClass;", "companionObjectInstance", "", "companionObjectInstance$annotations", "getCompanionObjectInstance", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "declaredFunctions", "Lkotlin/reflect/KFunction;", "declaredFunctions$annotations", "getDeclaredFunctions", "declaredMemberExtensionFunctions", "declaredMemberExtensionFunctions$annotations", "getDeclaredMemberExtensionFunctions", "declaredMemberExtensionProperties", "Lkotlin/reflect/KProperty2;", "T", "declaredMemberExtensionProperties$annotations", "getDeclaredMemberExtensionProperties", "declaredMemberFunctions", "declaredMemberFunctions$annotations", "getDeclaredMemberFunctions", "declaredMemberProperties", "Lkotlin/reflect/KProperty1;", "declaredMemberProperties$annotations", "getDeclaredMemberProperties", "declaredMembers", "Lkotlin/reflect/KCallable;", "declaredMembers$annotations", "getDeclaredMembers", "defaultType", "defaultType$annotations", "getDefaultType", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KType;", "functions", "functions$annotations", "getFunctions", "isExtension", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "(Lkotlin/reflect/jvm/internal/KCallableImpl;)Z", "isNotExtension", "memberExtensionFunctions", "memberExtensionFunctions$annotations", "getMemberExtensionFunctions", "memberExtensionProperties", "memberExtensionProperties$annotations", "getMemberExtensionProperties", "memberFunctions", "memberFunctions$annotations", "getMemberFunctions", "memberProperties", "memberProperties$annotations", "getMemberProperties", "primaryConstructor", "primaryConstructor$annotations", "getPrimaryConstructor", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KFunction;", "staticFunctions", "staticFunctions$annotations", "getStaticFunctions", "staticProperties", "Lkotlin/reflect/KProperty0;", "staticProperties$annotations", "getStaticProperties", "superclasses", "", "superclasses$annotations", "getSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/List;", "cast", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "createInstance", "isSubclassOf", "base", "isSuperclassOf", "derived", "safeCast", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KClasses
{
  public static final <T> T cast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$cast");
    if (paramKClass.isInstance(paramObject))
    {
      if (paramObject != null) {
        return paramObject;
      }
      throw new TypeCastException("null cannot be cast to non-null type T");
    }
    paramObject = new StringBuilder();
    paramObject.append("Value cannot be cast to ");
    paramObject.append(paramKClass.getQualifiedName());
    throw ((Throwable)new TypeCastException(paramObject.toString()));
  }
  
  public static final <T> T createInstance(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$createInstance");
    Iterator localIterator = ((Iterable)paramKClass.getConstructors()).iterator();
    Object localObject1 = null;
    int i = 0;
    Object localObject2 = null;
    while (localIterator.hasNext())
    {
      Object localObject3 = localIterator.next();
      Object localObject4 = (Iterable)((KFunction)localObject3).getParameters();
      if (((localObject4 instanceof Collection)) && (((Collection)localObject4).isEmpty())) {}
      do
      {
        while (!((Iterator)localObject4).hasNext())
        {
          j = 1;
          break;
          localObject4 = ((Iterable)localObject4).iterator();
        }
      } while (((KParameter)((Iterator)localObject4).next()).isOptional());
      int j = 0;
      if (j != 0)
      {
        if (i != 0)
        {
          localObject2 = localObject1;
          break label161;
        }
        localObject2 = localObject3;
        i = 1;
      }
    }
    if (i == 0) {
      localObject2 = localObject1;
    }
    label161:
    localObject2 = (KFunction)localObject2;
    if (localObject2 != null) {
      return ((KFunction)localObject2).callBy(MapsKt.emptyMap());
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Class should have a single no-arg constructor: ");
    ((StringBuilder)localObject2).append(paramKClass);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject2).toString()));
  }
  
  public static final Collection<KClass<?>> getAllSuperclasses(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$allSuperclasses");
    paramKClass = (Iterable)getAllSupertypes(paramKClass);
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramKClass, 10));
    Iterator localIterator = paramKClass.iterator();
    while (localIterator.hasNext())
    {
      KType localKType = (KType)localIterator.next();
      KClassifier localKClassifier = localKType.getClassifier();
      paramKClass = localKClassifier;
      if (!(localKClassifier instanceof KClass)) {
        paramKClass = null;
      }
      paramKClass = (KClass)paramKClass;
      if (paramKClass != null)
      {
        localCollection.add(paramKClass);
      }
      else
      {
        paramKClass = new StringBuilder();
        paramKClass.append("Supertype not a class: ");
        paramKClass.append(localKType);
        throw ((Throwable)new KotlinReflectionInternalError(paramKClass.toString()));
      }
    }
    return (Collection)localCollection;
  }
  
  public static final Collection<KType> getAllSupertypes(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$allSupertypes");
    paramKClass = DFS.dfs((Collection)paramKClass.getSupertypes(), (DFS.Neighbors)allSupertypes.1.INSTANCE, (DFS.Visited)new DFS.VisitedWithSet(), (DFS.NodeHandler)new DFS.NodeHandlerWithListResult()
    {
      public boolean beforeChildren(KType paramAnonymousKType)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKType, "current");
        ((LinkedList)this.result).add(paramAnonymousKType);
        return true;
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(paramKClass, "DFS.dfs(\n            sup…    }\n            }\n    )");
    return (Collection)paramKClass;
  }
  
  public static final KClass<?> getCompanionObject(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$companionObject");
    Iterator localIterator = ((Iterable)paramKClass.getNestedClasses()).iterator();
    while (localIterator.hasNext())
    {
      paramKClass = localIterator.next();
      KClass localKClass = (KClass)paramKClass;
      if (localKClass != null)
      {
        if (((KClassImpl)localKClass).getDescriptor().isCompanionObject()) {
          break label78;
        }
      }
      else {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
      }
    }
    paramKClass = null;
    label78:
    return (KClass)paramKClass;
  }
  
  public static final Object getCompanionObjectInstance(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$companionObjectInstance");
    paramKClass = getCompanionObject(paramKClass);
    if (paramKClass != null) {
      paramKClass = paramKClass.getObjectInstance();
    } else {
      paramKClass = null;
    }
    return paramKClass;
  }
  
  public static final Collection<KFunction<?>> getDeclaredFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredFunctions");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredMembers();
    paramKClass = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      if ((localObject2 instanceof KFunction)) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KFunction<?>> getDeclaredMemberExtensionFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredMemberExtensionFunctions");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KFunction))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final <T> Collection<KProperty2<T, ?, ?>> getDeclaredMemberExtensionProperties(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredMemberExtensionProperties");
    Object localObject = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      KCallableImpl localKCallableImpl = (KCallableImpl)localObject;
      int i;
      if ((isExtension(localKCallableImpl)) && ((localKCallableImpl instanceof KProperty2))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KFunction<?>> getDeclaredMemberFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredMemberFunctions");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isNotExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KFunction))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final <T> Collection<KProperty1<T, ?>> getDeclaredMemberProperties(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredMemberProperties");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isNotExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KProperty1))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KCallable<?>> getDeclaredMembers(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$declaredMembers");
    return ((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getDeclaredMembers();
  }
  
  public static final KType getDefaultType(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$defaultType");
    SimpleType localSimpleType = ((KClassImpl)paramKClass).getDescriptor().getDefaultType();
    Intrinsics.checkExpressionValueIsNotNull(localSimpleType, "(this as KClassImpl<*>).descriptor.defaultType");
    (KType)new KTypeImpl((KotlinType)localSimpleType, (Function0)new Lambda(paramKClass)
    {
      public final Class<? extends Object> invoke()
      {
        return ((KClassImpl)this.$this_defaultType).getJClass();
      }
    });
  }
  
  public static final Collection<KFunction<?>> getFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$functions");
    Object localObject1 = (Iterable)paramKClass.getMembers();
    paramKClass = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      if ((localObject2 instanceof KFunction)) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KFunction<?>> getMemberExtensionFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$memberExtensionFunctions");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KFunction))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final <T> Collection<KProperty2<T, ?, ?>> getMemberExtensionProperties(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$memberExtensionProperties");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KProperty2))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KFunction<?>> getMemberFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$memberFunctions");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isNotExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KFunction))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final <T> Collection<KProperty1<T, ?>> getMemberProperties(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$memberProperties");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllNonStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      localObject1 = (KCallableImpl)localObject2;
      int i;
      if ((isNotExtension((KCallableImpl)localObject1)) && ((localObject1 instanceof KProperty1))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final <T> KFunction<T> getPrimaryConstructor(KClass<T> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$primaryConstructor");
    Iterator localIterator = ((Iterable)((KClassImpl)paramKClass).getConstructors()).iterator();
    while (localIterator.hasNext())
    {
      paramKClass = localIterator.next();
      Object localObject = (KFunction)paramKClass;
      if (localObject != null)
      {
        localObject = ((KFunctionImpl)localObject).getDescriptor();
        if (localObject != null)
        {
          if (((ConstructorDescriptor)localObject).isPrimary()) {
            break label99;
          }
        }
        else {
          throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ConstructorDescriptor");
        }
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KFunctionImpl");
      }
    }
    paramKClass = null;
    label99:
    return (KFunction)paramKClass;
  }
  
  public static final Collection<KFunction<?>> getStaticFunctions(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$staticFunctions");
    Object localObject = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllStaticMembers();
    paramKClass = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof KFunction)) {
        paramKClass.add(localObject);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final Collection<KProperty0<?>> getStaticProperties(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$staticProperties");
    Object localObject1 = (Iterable)((KClassImpl.Data)((KClassImpl)paramKClass).getData().invoke()).getAllStaticMembers();
    paramKClass = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      KCallableImpl localKCallableImpl = (KCallableImpl)localObject2;
      int i;
      if ((isNotExtension(localKCallableImpl)) && ((localKCallableImpl instanceof KProperty0))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKClass.add(localObject2);
      }
    }
    return (Collection)paramKClass;
  }
  
  public static final List<KClass<?>> getSuperclasses(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$superclasses");
    paramKClass = (Iterable)paramKClass.getSupertypes();
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = paramKClass.iterator();
    while (localIterator.hasNext())
    {
      KClassifier localKClassifier = ((KType)localIterator.next()).getClassifier();
      paramKClass = localKClassifier;
      if (!(localKClassifier instanceof KClass)) {
        paramKClass = null;
      }
      paramKClass = (KClass)paramKClass;
      if (paramKClass != null) {
        localCollection.add(paramKClass);
      }
    }
    return (List)localCollection;
  }
  
  private static final boolean isExtension(KCallableImpl<?> paramKCallableImpl)
  {
    boolean bool;
    if (paramKCallableImpl.getDescriptor().getExtensionReceiverParameter() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final boolean isNotExtension(KCallableImpl<?> paramKCallableImpl)
  {
    return isExtension(paramKCallableImpl) ^ true;
  }
  
  public static final boolean isSubclassOf(KClass<?> paramKClass1, KClass<?> paramKClass2)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass1, "$this$isSubclassOf");
    Intrinsics.checkParameterIsNotNull(paramKClass2, "base");
    if (!Intrinsics.areEqual(paramKClass1, paramKClass2))
    {
      Collection localCollection = (Collection)CollectionsKt.listOf(paramKClass1);
      Function1 localFunction1 = (Function1)KClasses.isSubclassOf.1.INSTANCE;
      paramKClass1 = localFunction1;
      if (localFunction1 != null) {
        paramKClass1 = new KClasses.sam.org_jetbrains_kotlin_utils_DFS_Neighbors.0(localFunction1);
      }
      paramKClass1 = DFS.ifAny(localCollection, (DFS.Neighbors)paramKClass1, (Function1)new Lambda(paramKClass2)
      {
        public final boolean invoke(KClass<?> paramAnonymousKClass)
        {
          return Intrinsics.areEqual(paramAnonymousKClass, this.$base);
        }
      });
      Intrinsics.checkExpressionValueIsNotNull(paramKClass1, "DFS.ifAny(listOf(this), …erclasses) { it == base }");
      if (!paramKClass1.booleanValue())
      {
        bool = false;
        break label98;
      }
    }
    boolean bool = true;
    label98:
    return bool;
  }
  
  public static final boolean isSuperclassOf(KClass<?> paramKClass1, KClass<?> paramKClass2)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass1, "$this$isSuperclassOf");
    Intrinsics.checkParameterIsNotNull(paramKClass2, "derived");
    return isSubclassOf(paramKClass2, paramKClass1);
  }
  
  public static final <T> T safeCast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$safeCast");
    if (paramKClass.isInstance(paramObject))
    {
      if (paramObject == null) {
        throw new TypeCastException("null cannot be cast to non-null type T");
      }
    }
    else {
      paramObject = null;
    }
    return paramObject;
  }
}
