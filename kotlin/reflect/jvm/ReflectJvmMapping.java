package kotlin.reflect.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass.Factory;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\000\n\002\b\007\n\002\030\002\n\002\030\002\n\000\032\016\020%\032\004\030\0010&*\0020'H\002\"/\020\000\032\n\022\004\022\002H\002\030\0010\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0038F?\006\f\022\004\b\004\020\005\032\004\b\006\020\007\"\033\020\b\032\004\030\0010\t*\006\022\002\b\0030\n8F?\006\006\032\004\b\013\020\f\"\033\020\r\032\004\030\0010\016*\006\022\002\b\0030\n8F?\006\006\032\004\b\017\020\020\"\033\020\021\032\004\030\0010\016*\006\022\002\b\0030\0038F?\006\006\032\004\b\022\020\023\"\033\020\024\032\004\030\0010\016*\006\022\002\b\0030\0258F?\006\006\032\004\b\026\020\027\"\025\020\030\032\0020\031*\0020\0328F?\006\006\032\004\b\033\020\034\"-\020\035\032\n\022\004\022\002H\002\030\0010\003\"\b\b\000\020\002*\0020\036*\b\022\004\022\002H\0020\0018F?\006\006\032\004\b\037\020 \"\033\020\035\032\b\022\002\b\003\030\0010\003*\0020\0168F?\006\006\032\004\b\037\020!\"\033\020\"\032\b\022\002\b\003\030\0010\n*\0020\t8F?\006\006\032\004\b#\020$?\006("}, d2={"javaConstructor", "Ljava/lang/reflect/Constructor;", "T", "Lkotlin/reflect/KFunction;", "javaConstructor$annotations", "(Lkotlin/reflect/KFunction;)V", "getJavaConstructor", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Constructor;", "javaField", "Ljava/lang/reflect/Field;", "Lkotlin/reflect/KProperty;", "getJavaField", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Field;", "javaGetter", "Ljava/lang/reflect/Method;", "getJavaGetter", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Method;", "javaMethod", "getJavaMethod", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Method;", "javaSetter", "Lkotlin/reflect/KMutableProperty;", "getJavaSetter", "(Lkotlin/reflect/KMutableProperty;)Ljava/lang/reflect/Method;", "javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "kotlinFunction", "", "getKotlinFunction", "(Ljava/lang/reflect/Constructor;)Lkotlin/reflect/KFunction;", "(Ljava/lang/reflect/Method;)Lkotlin/reflect/KFunction;", "kotlinProperty", "getKotlinProperty", "(Ljava/lang/reflect/Field;)Lkotlin/reflect/KProperty;", "getKPackage", "Lkotlin/reflect/KDeclarationContainer;", "Ljava/lang/reflect/Member;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class ReflectJvmMapping
{
  public static final <T> Constructor<T> getJavaConstructor(KFunction<? extends T> paramKFunction)
  {
    Intrinsics.checkParameterIsNotNull(paramKFunction, "$this$javaConstructor");
    paramKFunction = UtilKt.asKCallableImpl(paramKFunction);
    Object localObject = null;
    if (paramKFunction != null)
    {
      paramKFunction = paramKFunction.getCaller();
      if (paramKFunction != null)
      {
        paramKFunction = paramKFunction.getMember();
        break label38;
      }
    }
    paramKFunction = null;
    label38:
    if (!(paramKFunction instanceof Constructor)) {
      paramKFunction = localObject;
    }
    return (Constructor)paramKFunction;
  }
  
  public static final Field getJavaField(KProperty<?> paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramKProperty, "$this$javaField");
    paramKProperty = UtilKt.asKPropertyImpl(paramKProperty);
    if (paramKProperty != null) {
      paramKProperty = paramKProperty.getJavaField();
    } else {
      paramKProperty = null;
    }
    return paramKProperty;
  }
  
  public static final Method getJavaGetter(KProperty<?> paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramKProperty, "$this$javaGetter");
    return getJavaMethod((KFunction)paramKProperty.getGetter());
  }
  
  public static final Method getJavaMethod(KFunction<?> paramKFunction)
  {
    Intrinsics.checkParameterIsNotNull(paramKFunction, "$this$javaMethod");
    paramKFunction = UtilKt.asKCallableImpl(paramKFunction);
    Object localObject = null;
    if (paramKFunction != null)
    {
      paramKFunction = paramKFunction.getCaller();
      if (paramKFunction != null)
      {
        paramKFunction = paramKFunction.getMember();
        break label38;
      }
    }
    paramKFunction = null;
    label38:
    if (!(paramKFunction instanceof Method)) {
      paramKFunction = localObject;
    }
    return (Method)paramKFunction;
  }
  
  public static final Method getJavaSetter(KMutableProperty<?> paramKMutableProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramKMutableProperty, "$this$javaSetter");
    return getJavaMethod((KFunction)paramKMutableProperty.getSetter());
  }
  
  public static final Type getJavaType(KType paramKType)
  {
    Intrinsics.checkParameterIsNotNull(paramKType, "$this$javaType");
    return ((KTypeImpl)paramKType).getJavaType$kotlin_reflection();
  }
  
  private static final KDeclarationContainer getKPackage(Member paramMember)
  {
    ReflectKotlinClass.Factory localFactory = ReflectKotlinClass.Factory;
    Object localObject = paramMember.getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "declaringClass");
    localObject = localFactory.create((Class)localObject);
    localFactory = null;
    if (localObject != null)
    {
      localObject = ((ReflectKotlinClass)localObject).getClassHeader();
      if (localObject != null)
      {
        localObject = ((KotlinClassHeader)localObject).getKind();
        break label48;
      }
    }
    localObject = null;
    label48:
    if (localObject == null)
    {
      paramMember = localFactory;
    }
    else
    {
      int i = ReflectJvmMapping.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
      if ((i != 1) && (i != 2) && (i != 3))
      {
        paramMember = localFactory;
      }
      else
      {
        paramMember = paramMember.getDeclaringClass();
        Intrinsics.checkExpressionValueIsNotNull(paramMember, "declaringClass");
        paramMember = (KDeclarationContainer)new KPackageImpl(paramMember, null, 2, null);
      }
    }
    return paramMember;
  }
  
  public static final <T> KFunction<T> getKotlinFunction(Constructor<T> paramConstructor)
  {
    Intrinsics.checkParameterIsNotNull(paramConstructor, "$this$kotlinFunction");
    Object localObject = paramConstructor.getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "declaringClass");
    Iterator localIterator = ((Iterable)JvmClassMappingKt.getKotlinClass((Class)localObject).getConstructors()).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (Intrinsics.areEqual(getJavaConstructor((KFunction)localObject), paramConstructor))
      {
        paramConstructor = (Constructor<T>)localObject;
        break label72;
      }
    }
    paramConstructor = null;
    label72:
    return (KFunction)paramConstructor;
  }
  
  public static final KFunction<?> getKotlinFunction(Method paramMethod)
  {
    Intrinsics.checkParameterIsNotNull(paramMethod, "$this$kotlinFunction");
    boolean bool = Modifier.isStatic(paramMethod.getModifiers());
    Object localObject1 = null;
    Object localObject2 = null;
    if (bool)
    {
      localObject3 = getKPackage((Member)paramMethod);
      Object localObject4;
      if (localObject3 != null)
      {
        localObject1 = (Iterable)((KDeclarationContainer)localObject3).getMembers();
        localObject3 = (Collection)new ArrayList();
        localObject1 = ((Iterable)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject4 = ((Iterator)localObject1).next();
          if ((localObject4 instanceof KFunction)) {
            ((Collection)localObject3).add(localObject4);
          }
        }
        localObject1 = ((Iterable)localObject3).iterator();
        do
        {
          localObject3 = localObject2;
          if (!((Iterator)localObject1).hasNext()) {
            break;
          }
          localObject3 = ((Iterator)localObject1).next();
        } while (!Intrinsics.areEqual(getJavaMethod((KFunction)localObject3), paramMethod));
        return (KFunction)localObject3;
      }
      localObject3 = paramMethod.getDeclaringClass();
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "declaringClass");
      localObject3 = KClasses.getCompanionObject(JvmClassMappingKt.getKotlinClass((Class)localObject3));
      if (localObject3 != null)
      {
        Iterator localIterator = ((Iterable)KClasses.getFunctions((KClass)localObject3)).iterator();
        while (localIterator.hasNext())
        {
          localObject3 = localIterator.next();
          Method localMethod = getJavaMethod((KFunction)localObject3);
          if ((localMethod != null) && (Intrinsics.areEqual(localMethod.getName(), paramMethod.getName())))
          {
            localObject4 = localMethod.getParameterTypes();
            if (localObject4 == null) {
              Intrinsics.throwNpe();
            }
            localObject2 = paramMethod.getParameterTypes();
            Intrinsics.checkExpressionValueIsNotNull(localObject2, "this.parameterTypes");
            if ((Arrays.equals((Object[])localObject4, (Object[])localObject2)) && (Intrinsics.areEqual(localMethod.getReturnType(), paramMethod.getReturnType())))
            {
              i = 1;
              break label311;
            }
          }
          int i = 0;
          label311:
          if (i != 0) {
            break label322;
          }
        }
        localObject3 = null;
        label322:
        localObject3 = (KFunction)localObject3;
        if (localObject3 != null) {
          return localObject3;
        }
      }
    }
    Object localObject3 = paramMethod.getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "declaringClass");
    localObject2 = ((Iterable)KClasses.getFunctions(JvmClassMappingKt.getKotlinClass((Class)localObject3))).iterator();
    do
    {
      localObject3 = localObject1;
      if (!((Iterator)localObject2).hasNext()) {
        break;
      }
      localObject3 = ((Iterator)localObject2).next();
    } while (!Intrinsics.areEqual(getJavaMethod((KFunction)localObject3), paramMethod));
    return (KFunction)localObject3;
  }
  
  public static final KProperty<?> getKotlinProperty(Field paramField)
  {
    Intrinsics.checkParameterIsNotNull(paramField, "$this$kotlinProperty");
    boolean bool = paramField.isSynthetic();
    Object localObject1 = null;
    Iterator localIterator1 = null;
    if (bool) {
      return null;
    }
    Object localObject2 = getKPackage((Member)paramField);
    if (localObject2 != null)
    {
      localObject1 = (Iterable)((KDeclarationContainer)localObject2).getMembers();
      localObject2 = (Collection)new ArrayList();
      Iterator localIterator2 = ((Iterable)localObject1).iterator();
      while (localIterator2.hasNext())
      {
        localObject1 = localIterator2.next();
        if ((localObject1 instanceof KProperty)) {
          ((Collection)localObject2).add(localObject1);
        }
      }
      localObject1 = ((Iterable)localObject2).iterator();
      do
      {
        localObject2 = localIterator1;
        if (!((Iterator)localObject1).hasNext()) {
          break;
        }
        localObject2 = ((Iterator)localObject1).next();
      } while (!Intrinsics.areEqual(getJavaField((KProperty)localObject2), paramField));
      return (KProperty)localObject2;
    }
    localObject2 = paramField.getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "declaringClass");
    localIterator1 = ((Iterable)KClasses.getMemberProperties(JvmClassMappingKt.getKotlinClass((Class)localObject2))).iterator();
    do
    {
      localObject2 = localObject1;
      if (!localIterator1.hasNext()) {
        break;
      }
      localObject2 = localIterator1.next();
    } while (!Intrinsics.areEqual(getJavaField((KProperty)localObject2), paramField));
    return (KProperty)localObject2;
  }
}
