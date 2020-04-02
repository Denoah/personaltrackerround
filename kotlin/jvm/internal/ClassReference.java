package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
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
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000p\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\020\033\n\002\b\003\n\002\020\036\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\022\n\002\030\002\n\002\b\007\n\002\020\016\n\002\b\b\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\020\001\n\000\n\002\020\b\n\002\b\005\030\000 K2\b\022\004\022\0020\0020\0012\0020\003:\001KB\021\022\n\020\004\032\006\022\002\b\0030\005?\006\002\020\006J\023\020B\032\0020\0222\b\020C\032\004\030\0010\002H?\002J\b\020D\032\0020EH\002J\b\020F\032\0020GH\026J\022\020H\032\0020\0222\b\020I\032\004\030\0010\002H\027J\b\020J\032\0020-H\026R\032\020\007\032\b\022\004\022\0020\t0\b8VX?\004?\006\006\032\004\b\n\020\013R \020\f\032\016\022\n\022\b\022\004\022\0020\0020\0160\r8VX?\004?\006\006\032\004\b\017\020\020R\032\020\021\032\0020\0228VX?\004?\006\f\022\004\b\023\020\024\032\004\b\021\020\025R\032\020\026\032\0020\0228VX?\004?\006\f\022\004\b\027\020\024\032\004\b\026\020\025R\032\020\030\032\0020\0228VX?\004?\006\f\022\004\b\031\020\024\032\004\b\030\020\025R\032\020\032\032\0020\0228VX?\004?\006\f\022\004\b\033\020\024\032\004\b\032\020\025R\032\020\034\032\0020\0228VX?\004?\006\f\022\004\b\035\020\024\032\004\b\034\020\025R\032\020\036\032\0020\0228VX?\004?\006\f\022\004\b\037\020\024\032\004\b\036\020\025R\032\020 \032\0020\0228VX?\004?\006\f\022\004\b!\020\024\032\004\b \020\025R\030\020\004\032\006\022\002\b\0030\005X?\004?\006\b\n\000\032\004\b\"\020#R\036\020$\032\f\022\b\022\006\022\002\b\0030%0\r8VX?\004?\006\006\032\004\b&\020\020R\036\020'\032\f\022\b\022\006\022\002\b\0030\0010\r8VX?\004?\006\006\032\004\b(\020\020R\026\020)\032\004\030\0010\0028VX?\004?\006\006\032\004\b*\020+R\026\020,\032\004\030\0010-8VX?\004?\006\006\032\004\b.\020/R(\0200\032\020\022\f\022\n\022\006\b\001\022\0020\0020\0010\b8VX?\004?\006\f\022\004\b1\020\024\032\004\b2\020\013R\026\0203\032\004\030\0010-8VX?\004?\006\006\032\004\b4\020/R \0205\032\b\022\004\022\002060\b8VX?\004?\006\f\022\004\b7\020\024\032\004\b8\020\013R \0209\032\b\022\004\022\0020:0\b8VX?\004?\006\f\022\004\b;\020\024\032\004\b<\020\013R\034\020=\032\004\030\0010>8VX?\004?\006\f\022\004\b?\020\024\032\004\b@\020A?\006L"}, d2={"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "sealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "supertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "typeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "visibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class ClassReference
  implements KClass<Object>, ClassBasedDeclarationContainer
{
  public static final Companion Companion = new Companion(null);
  private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
  private static final HashMap<String, String> classFqNames;
  private static final HashMap<String, String> primitiveFqNames;
  private static final HashMap<String, String> primitiveWrapperFqNames;
  private static final Map<String, String> simpleNames;
  private final Class<?> jClass;
  
  static
  {
    int i = 0;
    Object localObject1 = (Iterable)CollectionsKt.listOf(new Class[] { Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class });
    Object localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    Object localObject3 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject1 = ((Iterator)localObject3).next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      ((Collection)localObject2).add(TuplesKt.to((Class)localObject1, Integer.valueOf(i)));
      i++;
    }
    FUNCTION_CLASSES = MapsKt.toMap((Iterable)localObject2);
    localObject2 = new HashMap();
    ((HashMap)localObject2).put("boolean", "kotlin.Boolean");
    ((HashMap)localObject2).put("char", "kotlin.Char");
    ((HashMap)localObject2).put("byte", "kotlin.Byte");
    ((HashMap)localObject2).put("short", "kotlin.Short");
    ((HashMap)localObject2).put("int", "kotlin.Int");
    ((HashMap)localObject2).put("float", "kotlin.Float");
    ((HashMap)localObject2).put("long", "kotlin.Long");
    ((HashMap)localObject2).put("double", "kotlin.Double");
    primitiveFqNames = (HashMap)localObject2;
    localObject2 = new HashMap();
    ((HashMap)localObject2).put("java.lang.Boolean", "kotlin.Boolean");
    ((HashMap)localObject2).put("java.lang.Character", "kotlin.Char");
    ((HashMap)localObject2).put("java.lang.Byte", "kotlin.Byte");
    ((HashMap)localObject2).put("java.lang.Short", "kotlin.Short");
    ((HashMap)localObject2).put("java.lang.Integer", "kotlin.Int");
    ((HashMap)localObject2).put("java.lang.Float", "kotlin.Float");
    ((HashMap)localObject2).put("java.lang.Long", "kotlin.Long");
    ((HashMap)localObject2).put("java.lang.Double", "kotlin.Double");
    primitiveWrapperFqNames = (HashMap)localObject2;
    localObject2 = new HashMap();
    ((HashMap)localObject2).put("java.lang.Object", "kotlin.Any");
    ((HashMap)localObject2).put("java.lang.String", "kotlin.String");
    ((HashMap)localObject2).put("java.lang.CharSequence", "kotlin.CharSequence");
    ((HashMap)localObject2).put("java.lang.Throwable", "kotlin.Throwable");
    ((HashMap)localObject2).put("java.lang.Cloneable", "kotlin.Cloneable");
    ((HashMap)localObject2).put("java.lang.Number", "kotlin.Number");
    ((HashMap)localObject2).put("java.lang.Comparable", "kotlin.Comparable");
    ((HashMap)localObject2).put("java.lang.Enum", "kotlin.Enum");
    ((HashMap)localObject2).put("java.lang.annotation.Annotation", "kotlin.Annotation");
    ((HashMap)localObject2).put("java.lang.Iterable", "kotlin.collections.Iterable");
    ((HashMap)localObject2).put("java.util.Iterator", "kotlin.collections.Iterator");
    ((HashMap)localObject2).put("java.util.Collection", "kotlin.collections.Collection");
    ((HashMap)localObject2).put("java.util.List", "kotlin.collections.List");
    ((HashMap)localObject2).put("java.util.Set", "kotlin.collections.Set");
    ((HashMap)localObject2).put("java.util.ListIterator", "kotlin.collections.ListIterator");
    ((HashMap)localObject2).put("java.util.Map", "kotlin.collections.Map");
    ((HashMap)localObject2).put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
    ((HashMap)localObject2).put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
    ((HashMap)localObject2).put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
    ((HashMap)localObject2).putAll((Map)primitiveFqNames);
    ((HashMap)localObject2).putAll((Map)primitiveWrapperFqNames);
    localObject1 = primitiveFqNames.values();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "primitiveFqNames.values");
    localObject1 = ((Iterable)localObject1).iterator();
    Object localObject4;
    while (((Iterator)localObject1).hasNext())
    {
      localObject4 = ((Iterator)localObject1).next();
      localObject3 = (Map)localObject2;
      localObject4 = (String)localObject4;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("kotlin.jvm.internal.");
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "kotlinName");
      localStringBuilder.append(StringsKt.substringAfterLast$default((String)localObject4, '.', null, 2, null));
      localStringBuilder.append("CompanionObject");
      String str = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject4);
      localStringBuilder.append(".Companion");
      localObject4 = TuplesKt.to(str, localStringBuilder.toString());
      ((Map)localObject3).put(((Pair)localObject4).getFirst(), ((Pair)localObject4).getSecond());
    }
    localObject1 = (Map)localObject2;
    localObject1 = FUNCTION_CLASSES.entrySet().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject4 = (Map.Entry)((Iterator)localObject1).next();
      localObject3 = (Class)((Map.Entry)localObject4).getKey();
      i = ((Number)((Map.Entry)localObject4).getValue()).intValue();
      localObject4 = ((Class)localObject3).getName();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("kotlin.Function");
      ((StringBuilder)localObject3).append(i);
      ((HashMap)localObject2).put(localObject4, ((StringBuilder)localObject3).toString());
    }
    classFqNames = (HashMap)localObject2;
    localObject1 = (Map)localObject2;
    localObject2 = (Map)new LinkedHashMap(MapsKt.mapCapacity(((Map)localObject1).size()));
    localObject1 = ((Iterable)((Map)localObject1).entrySet()).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject3 = (Map.Entry)((Iterator)localObject1).next();
      ((Map)localObject2).put(((Map.Entry)localObject3).getKey(), StringsKt.substringAfterLast$default((String)((Map.Entry)localObject3).getValue(), '.', null, 2, null));
    }
    simpleNames = (Map)localObject2;
  }
  
  public ClassReference(Class<?> paramClass)
  {
    this.jClass = paramClass;
  }
  
  private final Void error()
  {
    throw ((Throwable)new KotlinReflectionNotSupportedError());
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ClassReference)) && (Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType(this), JvmClassMappingKt.getJavaObjectType((KClass)paramObject)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public List<Annotation> getAnnotations()
  {
    error();
    throw null;
  }
  
  public Collection<KFunction<Object>> getConstructors()
  {
    error();
    throw null;
  }
  
  public Class<?> getJClass()
  {
    return this.jClass;
  }
  
  public Collection<KCallable<?>> getMembers()
  {
    error();
    throw null;
  }
  
  public Collection<KClass<?>> getNestedClasses()
  {
    error();
    throw null;
  }
  
  public Object getObjectInstance()
  {
    error();
    throw null;
  }
  
  public String getQualifiedName()
  {
    return Companion.getClassQualifiedName(getJClass());
  }
  
  public List<KClass<? extends Object>> getSealedSubclasses()
  {
    error();
    throw null;
  }
  
  public String getSimpleName()
  {
    return Companion.getClassSimpleName(getJClass());
  }
  
  public List<KType> getSupertypes()
  {
    error();
    throw null;
  }
  
  public List<KTypeParameter> getTypeParameters()
  {
    error();
    throw null;
  }
  
  public KVisibility getVisibility()
  {
    error();
    throw null;
  }
  
  public int hashCode()
  {
    return JvmClassMappingKt.getJavaObjectType(this).hashCode();
  }
  
  public boolean isAbstract()
  {
    error();
    throw null;
  }
  
  public boolean isCompanion()
  {
    error();
    throw null;
  }
  
  public boolean isData()
  {
    error();
    throw null;
  }
  
  public boolean isFinal()
  {
    error();
    throw null;
  }
  
  public boolean isInner()
  {
    error();
    throw null;
  }
  
  public boolean isInstance(Object paramObject)
  {
    return Companion.isInstance(paramObject, getJClass());
  }
  
  public boolean isOpen()
  {
    error();
    throw null;
  }
  
  public boolean isSealed()
  {
    error();
    throw null;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getJClass().toString());
    localStringBuilder.append(" (Kotlin reflection is not available)");
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020$\n\002\030\002\n\002\030\002\n\002\020\b\n\000\n\002\030\002\n\002\020\016\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\024\020\017\032\004\030\0010\n2\n\020\020\032\006\022\002\b\0030\005J\024\020\021\032\004\030\0010\n2\n\020\020\032\006\022\002\b\0030\005J\034\020\022\032\0020\0232\b\020\024\032\004\030\0010\0012\n\020\020\032\006\022\002\b\0030\005R&\020\003\032\032\022\020\022\016\022\n\b\001\022\006\022\002\b\0030\0060\005\022\004\022\0020\0070\004X?\004?\006\002\n\000R*\020\b\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X?\004?\006\002\n\000R*\020\f\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X?\004?\006\002\n\000R*\020\r\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X?\004?\006\002\n\000R\032\020\016\032\016\022\004\022\0020\n\022\004\022\0020\n0\004X?\004?\006\002\n\000?\006\025"}, d2={"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final String getClassQualifiedName(Class<?> paramClass)
    {
      Intrinsics.checkParameterIsNotNull(paramClass, "jClass");
      boolean bool = paramClass.isAnonymousClass();
      Object localObject = null;
      String str = null;
      if (bool)
      {
        paramClass = (Class<?>)localObject;
      }
      else if (paramClass.isLocalClass())
      {
        paramClass = (Class<?>)localObject;
      }
      else if (paramClass.isArray())
      {
        localObject = paramClass.getComponentType();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "componentType");
        paramClass = str;
        if (((Class)localObject).isPrimitive())
        {
          localObject = (String)ClassReference.access$getClassFqNames$cp().get(((Class)localObject).getName());
          paramClass = str;
          if (localObject != null)
          {
            paramClass = new StringBuilder();
            paramClass.append((String)localObject);
            paramClass.append("Array");
            paramClass = paramClass.toString();
          }
        }
        if (paramClass == null) {
          paramClass = "kotlin.Array";
        }
      }
      else
      {
        str = (String)ClassReference.access$getClassFqNames$cp().get(paramClass.getName());
        if (str != null) {
          paramClass = str;
        } else {
          paramClass = paramClass.getCanonicalName();
        }
      }
      return paramClass;
    }
    
    public final String getClassSimpleName(Class<?> paramClass)
    {
      Intrinsics.checkParameterIsNotNull(paramClass, "jClass");
      boolean bool = paramClass.isAnonymousClass();
      String str = "Array";
      Method localMethod = null;
      Object localObject = null;
      if (bool) {}
      for (;;)
      {
        return (Class<?>)localObject;
        if (paramClass.isLocalClass())
        {
          str = paramClass.getSimpleName();
          localMethod = paramClass.getEnclosingMethod();
          if (localMethod != null)
          {
            Intrinsics.checkExpressionValueIsNotNull(str, "name");
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(localMethod.getName());
            ((StringBuilder)localObject).append("$");
            localObject = StringsKt.substringAfter$default(str, ((StringBuilder)localObject).toString(), null, 2, null);
            if (localObject != null)
            {
              paramClass = (Class<?>)localObject;
              break label171;
            }
          }
          localObject = paramClass.getEnclosingConstructor();
          if (localObject != null)
          {
            Intrinsics.checkExpressionValueIsNotNull(str, "name");
            paramClass = new StringBuilder();
            paramClass.append(((Constructor)localObject).getName());
            paramClass.append("$");
            paramClass = StringsKt.substringAfter$default(str, paramClass.toString(), null, 2, null);
          }
          else
          {
            paramClass = null;
          }
          label171:
          if (paramClass != null) {
            return paramClass;
          }
          Intrinsics.checkExpressionValueIsNotNull(str, "name");
          return StringsKt.substringAfter$default(str, '$', null, 2, null);
        }
        if (!paramClass.isArray()) {
          break;
        }
        paramClass = paramClass.getComponentType();
        Intrinsics.checkExpressionValueIsNotNull(paramClass, "componentType");
        localObject = localMethod;
        if (paramClass.isPrimitive())
        {
          paramClass = (String)ClassReference.access$getSimpleNames$cp().get(paramClass.getName());
          localObject = localMethod;
          if (paramClass != null)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramClass);
            ((StringBuilder)localObject).append("Array");
            localObject = ((StringBuilder)localObject).toString();
          }
        }
        paramClass = str;
        if (localObject == null) {
          return paramClass;
        }
      }
      localObject = (String)ClassReference.access$getSimpleNames$cp().get(paramClass.getName());
      if (localObject != null) {
        paramClass = (Class<?>)localObject;
      } else {
        paramClass = paramClass.getSimpleName();
      }
      return paramClass;
    }
    
    public final boolean isInstance(Object paramObject, Class<?> paramClass)
    {
      Intrinsics.checkParameterIsNotNull(paramClass, "jClass");
      Object localObject = ClassReference.access$getFUNCTION_CLASSES$cp();
      if (localObject != null)
      {
        localObject = (Integer)((Map)localObject).get(paramClass);
        if (localObject != null) {
          return TypeIntrinsics.isFunctionOfArity(paramObject, ((Number)localObject).intValue());
        }
        localObject = paramClass;
        if (paramClass.isPrimitive()) {
          localObject = JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(paramClass));
        }
        return ((Class)localObject).isInstance(paramObject);
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
    }
  }
}
