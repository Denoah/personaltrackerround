package kotlin.reflect.jvm.internal.calls;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020$\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\001\n\000\n\002\020\b\n\002\b\007\032I\020\000\032\002H\001\"\b\b\000\020\001*\0020\0022\f\020\003\032\b\022\004\022\002H\0010\0042\022\020\005\032\016\022\004\022\0020\007\022\004\022\0020\0020\0062\016\b\002\020\b\032\b\022\004\022\0020\n0\tH\000?\006\002\020\013\032$\020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\0072\n\020\021\032\006\022\002\b\0030\004H\002\032\034\020\022\032\004\030\0010\002*\004\030\0010\0022\n\020\023\032\006\022\002\b\0030\004H\002?\006\024?\006\024\020\025\032\0020\017\"\b\b\000\020\001*\0020\002X??\002?\006\024\020\026\032\0020\007\"\b\b\000\020\001*\0020\002X??\002"}, d2={"createAnnotationInstance", "T", "", "annotationClass", "Ljava/lang/Class;", "values", "", "", "methods", "", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/Map;Ljava/util/List;)Ljava/lang/Object;", "throwIllegalArgumentType", "", "index", "", "name", "expectedJvmType", "transformKotlinToJvm", "expectedType", "kotlin-reflection", "hashCode", "toString"}, k=2, mv={1, 1, 15})
public final class AnnotationConstructorCallerKt
{
  public static final <T> T createAnnotationInstance(Class<T> paramClass, final Map<String, ? extends Object> paramMap, final List<Method> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "annotationClass");
    Intrinsics.checkParameterIsNotNull(paramMap, "values");
    Intrinsics.checkParameterIsNotNull(paramList, "methods");
    final Lambda local2 = new Lambda(paramClass)
    {
      public final boolean invoke(Object paramAnonymousObject)
      {
        boolean bool1 = paramAnonymousObject instanceof Annotation;
        Object localObject1 = null;
        if (!bool1) {
          localObject2 = null;
        } else {
          localObject2 = paramAnonymousObject;
        }
        Object localObject3 = (Annotation)localObject2;
        Object localObject2 = localObject1;
        if (localObject3 != null)
        {
          localObject3 = JvmClassMappingKt.getAnnotationClass((Annotation)localObject3);
          localObject2 = localObject1;
          if (localObject3 != null) {
            localObject2 = JvmClassMappingKt.getJavaClass((KClass)localObject3);
          }
        }
        bool1 = Intrinsics.areEqual(localObject2, this.$annotationClass);
        boolean bool2 = true;
        if (bool1)
        {
          localObject2 = (Iterable)paramList;
          if (((localObject2 instanceof Collection)) && (((Collection)localObject2).isEmpty())) {}
          do
          {
            while (!((Iterator)localObject2).hasNext())
            {
              i = 1;
              break;
              localObject2 = ((Iterable)localObject2).iterator();
            }
            localObject1 = (Method)((Iterator)localObject2).next();
            localObject3 = paramMap.get(((Method)localObject1).getName());
            localObject1 = ((Method)localObject1).invoke(paramAnonymousObject, new Object[0]);
            if ((localObject3 instanceof boolean[]))
            {
              localObject3 = (boolean[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((boolean[])localObject3, (boolean[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.BooleanArray");
              }
            }
            else if ((localObject3 instanceof char[]))
            {
              localObject3 = (char[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((char[])localObject3, (char[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharArray");
              }
            }
            else if ((localObject3 instanceof byte[]))
            {
              localObject3 = (byte[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((byte[])localObject3, (byte[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.ByteArray");
              }
            }
            else if ((localObject3 instanceof short[]))
            {
              localObject3 = (short[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((short[])localObject3, (short[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.ShortArray");
              }
            }
            else if ((localObject3 instanceof int[]))
            {
              localObject3 = (int[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((int[])localObject3, (int[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.IntArray");
              }
            }
            else if ((localObject3 instanceof float[]))
            {
              localObject3 = (float[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((float[])localObject3, (float[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.FloatArray");
              }
            }
            else if ((localObject3 instanceof long[]))
            {
              localObject3 = (long[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((long[])localObject3, (long[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.LongArray");
              }
            }
            else if ((localObject3 instanceof double[]))
            {
              localObject3 = (double[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((double[])localObject3, (double[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.DoubleArray");
              }
            }
            else if ((localObject3 instanceof Object[]))
            {
              localObject3 = (Object[])localObject3;
              if (localObject1 != null) {
                bool1 = Arrays.equals((Object[])localObject3, (Object[])localObject1);
              } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
              }
            }
            else
            {
              bool1 = Intrinsics.areEqual(localObject3, localObject1);
            }
          } while (bool1);
          int i = 0;
          if (i != 0) {
            return bool2;
          }
        }
        bool1 = false;
        return bool1;
      }
    };
    final Lazy localLazy1 = LazyKt.lazy((Function0)new Lambda(paramMap)
    {
      public final int invoke()
      {
        Iterator localIterator = ((Iterable)this.$values.entrySet()).iterator();
        int i = 0;
        while (localIterator.hasNext())
        {
          Object localObject = (Map.Entry)localIterator.next();
          String str = (String)((Map.Entry)localObject).getKey();
          localObject = ((Map.Entry)localObject).getValue();
          int j;
          if ((localObject instanceof boolean[])) {
            j = Arrays.hashCode((boolean[])localObject);
          } else if ((localObject instanceof char[])) {
            j = Arrays.hashCode((char[])localObject);
          } else if ((localObject instanceof byte[])) {
            j = Arrays.hashCode((byte[])localObject);
          } else if ((localObject instanceof short[])) {
            j = Arrays.hashCode((short[])localObject);
          } else if ((localObject instanceof int[])) {
            j = Arrays.hashCode((int[])localObject);
          } else if ((localObject instanceof float[])) {
            j = Arrays.hashCode((float[])localObject);
          } else if ((localObject instanceof long[])) {
            j = Arrays.hashCode((long[])localObject);
          } else if ((localObject instanceof double[])) {
            j = Arrays.hashCode((double[])localObject);
          } else if ((localObject instanceof Object[])) {
            j = Arrays.hashCode((Object[])localObject);
          } else {
            j = localObject.hashCode();
          }
          i += (j ^ str.hashCode() * 127);
        }
        return i;
      }
    });
    final KProperty localKProperty1 = $$delegatedProperties[0];
    final Lazy localLazy2 = LazyKt.lazy((Function0)new Lambda(paramClass)
    {
      public final String invoke()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append('@');
        ((StringBuilder)localObject).append(this.$annotationClass.getCanonicalName());
        CollectionsKt.joinTo$default((Iterable)paramMap.entrySet(), (Appendable)localObject, (CharSequence)", ", (CharSequence)"(", (CharSequence)")", 0, null, (Function1)1.1.INSTANCE, 48, null);
        localObject = ((StringBuilder)localObject).toString();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "StringBuilder().apply(builderAction).toString()");
        return localObject;
      }
    });
    final KProperty localKProperty2 = $$delegatedProperties[1];
    paramList = paramClass.getClassLoader();
    paramMap = (InvocationHandler)new InvocationHandler()
    {
      public final Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      {
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousMethod, "method");
        paramAnonymousObject = paramAnonymousMethod.getName();
        if (paramAnonymousObject != null)
        {
          int i = paramAnonymousObject.hashCode();
          if (i != -1776922004)
          {
            if (i != 147696667)
            {
              if ((i == 1444986633) && (paramAnonymousObject.equals("annotationType")))
              {
                paramAnonymousObject = this.$annotationClass;
                break label170;
              }
            }
            else if (paramAnonymousObject.equals("hashCode"))
            {
              paramAnonymousObject = localLazy1.getValue();
              break label170;
            }
          }
          else if (paramAnonymousObject.equals("toString"))
          {
            paramAnonymousObject = localLazy2.getValue();
            break label170;
          }
        }
        if ((Intrinsics.areEqual(paramAnonymousObject, "equals")) && (paramAnonymousArrayOfObject != null) && (paramAnonymousArrayOfObject.length == 1))
        {
          paramAnonymousObject = Boolean.valueOf(local2.invoke(ArraysKt.single(paramAnonymousArrayOfObject)));
        }
        else
        {
          if (!paramMap.containsKey(paramAnonymousObject)) {
            break label172;
          }
          paramAnonymousObject = paramMap.get(paramAnonymousObject);
        }
        label170:
        return paramAnonymousObject;
        label172:
        paramAnonymousObject = new StringBuilder();
        paramAnonymousObject.append("Method is not supported: ");
        paramAnonymousObject.append(paramAnonymousMethod);
        paramAnonymousObject.append(" (args: ");
        if (paramAnonymousArrayOfObject == null) {
          paramAnonymousArrayOfObject = new Object[0];
        }
        paramAnonymousObject.append(ArraysKt.toList(paramAnonymousArrayOfObject));
        paramAnonymousObject.append(')');
        throw ((Throwable)new KotlinReflectionInternalError(paramAnonymousObject.toString()));
      }
    };
    paramClass = Proxy.newProxyInstance(paramList, new Class[] { paramClass }, paramMap);
    if (paramClass != null) {
      return paramClass;
    }
    throw new TypeCastException("null cannot be cast to non-null type T");
  }
  
  private static final Void throwIllegalArgumentType(int paramInt, String paramString, Class<?> paramClass)
  {
    if (Intrinsics.areEqual(paramClass, Class.class)) {
      paramClass = Reflection.getOrCreateKotlinClass(KClass.class);
    } else if ((paramClass.isArray()) && (Intrinsics.areEqual(paramClass.getComponentType(), Class.class))) {
      paramClass = Reflection.getOrCreateKotlinClass([Lkotlin.reflect.KClass.class);
    } else {
      paramClass = JvmClassMappingKt.getKotlinClass(paramClass);
    }
    if (Intrinsics.areEqual(paramClass.getQualifiedName(), Reflection.getOrCreateKotlinClass([Ljava.lang.Object.class).getQualifiedName()))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramClass.getQualifiedName());
      localStringBuilder.append('<');
      paramClass = JvmClassMappingKt.getJavaClass(paramClass).getComponentType();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "kotlinClass.java.componentType");
      localStringBuilder.append(JvmClassMappingKt.getKotlinClass(paramClass).getQualifiedName());
      localStringBuilder.append('>');
      paramClass = localStringBuilder.toString();
    }
    else
    {
      paramClass = paramClass.getQualifiedName();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Argument #");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(' ');
    localStringBuilder.append(paramString);
    localStringBuilder.append(" is not of the required type ");
    localStringBuilder.append(paramClass);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  private static final Object transformKotlinToJvm(Object paramObject, Class<?> paramClass)
  {
    boolean bool = paramObject instanceof Class;
    Object localObject1 = null;
    if (bool) {
      return null;
    }
    Object localObject2;
    if ((paramObject instanceof KClass))
    {
      localObject2 = JvmClassMappingKt.getJavaClass((KClass)paramObject);
    }
    else
    {
      localObject2 = paramObject;
      if ((paramObject instanceof Object[]))
      {
        localObject2 = (Object[])paramObject;
        if ((localObject2 instanceof Class[])) {
          return null;
        }
        if ((localObject2 instanceof KClass[])) {
          if (paramObject != null)
          {
            paramObject = (KClass[])paramObject;
            localObject2 = (Collection)new ArrayList(paramObject.length);
            int i = paramObject.length;
            for (int j = 0; j < i; j++) {
              ((Collection)localObject2).add(JvmClassMappingKt.getJavaClass(paramObject[j]));
            }
            localObject2 = ((Collection)localObject2).toArray(new Class[0]);
            if (localObject2 == null) {
              throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
          }
          else
          {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.reflect.KClass<*>>");
          }
        }
      }
    }
    paramObject = localObject1;
    if (paramClass.isInstance(localObject2)) {
      paramObject = localObject2;
    }
    return paramObject;
  }
}
