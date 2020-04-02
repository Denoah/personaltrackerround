package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.MemberVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

final class ReflectClassStructure
{
  public static final ReflectClassStructure INSTANCE = new ReflectClassStructure();
  
  private ReflectClassStructure() {}
  
  private final ClassLiteralValue classLiteralValue(Class<?> paramClass)
  {
    int i = 0;
    while (paramClass.isArray())
    {
      i++;
      paramClass = paramClass.getComponentType();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "currentClass.componentType");
    }
    if (paramClass.isPrimitive())
    {
      if (Intrinsics.areEqual(paramClass, Void.TYPE))
      {
        paramClass = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.unit.toSafe());
        Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(KotlinB…s.FQ_NAMES.unit.toSafe())");
        return new ClassLiteralValue(paramClass, i);
      }
      paramClass = JvmPrimitiveType.get(paramClass.getName());
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "JvmPrimitiveType.get(currentClass.name)");
      paramClass = paramClass.getPrimitiveType();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "JvmPrimitiveType.get(cur…Class.name).primitiveType");
      if (i > 0)
      {
        paramClass = ClassId.topLevel(paramClass.getArrayTypeFqName());
        Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(primitiveType.arrayTypeFqName)");
        return new ClassLiteralValue(paramClass, i - 1);
      }
      paramClass = ClassId.topLevel(paramClass.getTypeFqName());
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(primitiveType.typeFqName)");
      return new ClassLiteralValue(paramClass, i);
    }
    paramClass = ReflectClassUtilKt.getClassId(paramClass);
    Object localObject = JavaToKotlinClassMap.INSTANCE;
    FqName localFqName = paramClass.asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "javaClassId.asSingleFqName()");
    localObject = ((JavaToKotlinClassMap)localObject).mapJavaToKotlin(localFqName);
    if (localObject != null) {
      paramClass = (Class<?>)localObject;
    }
    return new ClassLiteralValue(paramClass, i);
  }
  
  private final void loadConstructorAnnotations(Class<?> paramClass, KotlinJvmBinaryClass.MemberVisitor paramMemberVisitor)
  {
    for (Object localObject1 : paramClass.getDeclaredConstructors())
    {
      Object localObject2 = Name.special("<init>");
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Name.special(\"<init>\")");
      Object localObject3 = SignatureSerializer.INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "constructor");
      localObject2 = paramMemberVisitor.visitMethod((Name)localObject2, ((SignatureSerializer)localObject3).constructorDesc(localObject1));
      if (localObject2 != null)
      {
        for (localObject3 : localObject1.getDeclaredAnnotations())
        {
          localObject5 = (KotlinJvmBinaryClass.AnnotationVisitor)localObject2;
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "annotation");
          processAnnotation((KotlinJvmBinaryClass.AnnotationVisitor)localObject5, (Annotation)localObject3);
        }
        Object localObject5 = localObject1.getParameterAnnotations();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "parameterAnnotations");
        ??? = (Object[])localObject5;
        if (???.length == 0) {
          ??? = 1;
        } else {
          ??? = 0;
        }
        localObject3 = paramClass;
        ??? = ???;
        if ((??? ^ 0x1) != 0)
        {
          int n = localObject1.getParameterTypes().length - ???.length;
          int i1 = localObject5.length;
          for (??? = 0;; ???++)
          {
            localObject3 = paramClass;
            ??? = ???;
            if (??? >= i1) {
              break;
            }
            for (localObject1 : localObject5[???])
            {
              ??? = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(localObject1));
              Object localObject6 = ReflectClassUtilKt.getClassId((Class)???);
              Intrinsics.checkExpressionValueIsNotNull(localObject1, "annotation");
              localObject6 = ((KotlinJvmBinaryClass.MethodAnnotationVisitor)localObject2).visitParameterAnnotation(??? + n, (ClassId)localObject6, (SourceElement)new ReflectAnnotationSource(localObject1));
              if (localObject6 != null) {
                INSTANCE.processAnnotationArguments((KotlinJvmBinaryClass.AnnotationArgumentVisitor)localObject6, localObject1, (Class)???);
              }
            }
          }
        }
        paramClass = (Class<?>)localObject3;
        ??? = ???;
        ((KotlinJvmBinaryClass.MethodAnnotationVisitor)localObject2).visitEnd();
      }
    }
  }
  
  private final void loadFieldAnnotations(Class<?> paramClass, KotlinJvmBinaryClass.MemberVisitor paramMemberVisitor)
  {
    for (Object localObject1 : paramClass.getDeclaredFields())
    {
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "field");
      Object localObject2 = Name.identifier(localObject1.getName());
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Name.identifier(field.name)");
      localObject2 = paramMemberVisitor.visitField((Name)localObject2, SignatureSerializer.INSTANCE.fieldDesc(localObject1), null);
      if (localObject2 != null)
      {
        for (localObject1 : localObject1.getDeclaredAnnotations())
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "annotation");
          processAnnotation((KotlinJvmBinaryClass.AnnotationVisitor)localObject2, localObject1);
        }
        ((KotlinJvmBinaryClass.AnnotationVisitor)localObject2).visitEnd();
      }
    }
  }
  
  private final void loadMethodAnnotations(Class<?> paramClass, KotlinJvmBinaryClass.MemberVisitor paramMemberVisitor)
  {
    for (Object localObject1 : paramClass.getDeclaredMethods())
    {
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "method");
      Object localObject2 = Name.identifier(((Method)localObject1).getName());
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Name.identifier(method.name)");
      localObject2 = paramMemberVisitor.visitMethod((Name)localObject2, SignatureSerializer.INSTANCE.methodDesc((Method)localObject1));
      if (localObject2 != null)
      {
        Object localObject4;
        KotlinJvmBinaryClass.AnnotationVisitor localAnnotationVisitor;
        for (localObject4 : ((Method)localObject1).getDeclaredAnnotations())
        {
          localAnnotationVisitor = (KotlinJvmBinaryClass.AnnotationVisitor)localObject2;
          Intrinsics.checkExpressionValueIsNotNull(localObject4, "annotation");
          processAnnotation(localAnnotationVisitor, localObject4);
        }
        localObject1 = ((Method)localObject1).getParameterAnnotations();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "method.parameterAnnotations");
        int n = localObject1.length;
        for (??? = 0; ??? < n; ???++) {
          for (localAnnotationVisitor : localObject1[???])
          {
            ??? = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(localAnnotationVisitor));
            Object localObject5 = ReflectClassUtilKt.getClassId((Class)???);
            Intrinsics.checkExpressionValueIsNotNull(localAnnotationVisitor, "annotation");
            localObject5 = ((KotlinJvmBinaryClass.MethodAnnotationVisitor)localObject2).visitParameterAnnotation(???, (ClassId)localObject5, (SourceElement)new ReflectAnnotationSource(localAnnotationVisitor));
            if (localObject5 != null) {
              INSTANCE.processAnnotationArguments((KotlinJvmBinaryClass.AnnotationArgumentVisitor)localObject5, localAnnotationVisitor, (Class)???);
            }
          }
        }
        ((KotlinJvmBinaryClass.MethodAnnotationVisitor)localObject2).visitEnd();
      }
    }
  }
  
  private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor paramAnnotationVisitor, Annotation paramAnnotation)
  {
    Class localClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(paramAnnotation));
    paramAnnotationVisitor = paramAnnotationVisitor.visitAnnotation(ReflectClassUtilKt.getClassId(localClass), (SourceElement)new ReflectAnnotationSource(paramAnnotation));
    if (paramAnnotationVisitor != null) {
      INSTANCE.processAnnotationArguments(paramAnnotationVisitor, paramAnnotation, localClass);
    }
  }
  
  private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor paramAnnotationArgumentVisitor, Name paramName, Object paramObject)
  {
    Object localObject = paramObject.getClass();
    if (Intrinsics.areEqual(localObject, Class.class))
    {
      if (paramObject != null) {
        paramAnnotationArgumentVisitor.visitClassLiteral(paramName, classLiteralValue((Class)paramObject));
      } else {
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
      }
    }
    else if (ReflectKotlinClassKt.access$getTYPES_ELIGIBLE_FOR_SIMPLE_VISIT$p().contains(localObject))
    {
      paramAnnotationArgumentVisitor.visit(paramName, paramObject);
    }
    else if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass((Class)localObject))
    {
      if (!((Class)localObject).isEnum()) {
        localObject = ((Class)localObject).getEnclosingClass();
      }
      Intrinsics.checkExpressionValueIsNotNull(localObject, "(if (clazz.isEnum) clazz…lse clazz.enclosingClass)");
      localObject = ReflectClassUtilKt.getClassId((Class)localObject);
      if (paramObject != null)
      {
        paramObject = Name.identifier(((Enum)paramObject).name());
        Intrinsics.checkExpressionValueIsNotNull(paramObject, "Name.identifier((value as Enum<*>).name)");
        paramAnnotationArgumentVisitor.visitEnum(paramName, (ClassId)localObject, paramObject);
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Enum<*>");
      }
    }
    else if (Annotation.class.isAssignableFrom((Class)localObject))
    {
      localObject = ((Class)localObject).getInterfaces();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "clazz.interfaces");
      localObject = (Class)ArraysKt.single((Object[])localObject);
      Intrinsics.checkExpressionValueIsNotNull(localObject, "annotationClass");
      paramAnnotationArgumentVisitor = paramAnnotationArgumentVisitor.visitAnnotation(paramName, ReflectClassUtilKt.getClassId((Class)localObject));
      if (paramAnnotationArgumentVisitor != null)
      {
        if (paramObject != null)
        {
          processAnnotationArguments(paramAnnotationArgumentVisitor, (Annotation)paramObject, (Class)localObject);
          break label528;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Annotation");
      }
    }
    else
    {
      if (!((Class)localObject).isArray()) {
        break label541;
      }
      paramAnnotationArgumentVisitor = paramAnnotationArgumentVisitor.visitArray(paramName);
      if (paramAnnotationArgumentVisitor == null) {
        break label540;
      }
      paramName = ((Class)localObject).getComponentType();
      Intrinsics.checkExpressionValueIsNotNull(paramName, "componentType");
      boolean bool = paramName.isEnum();
      int i = 0;
      int j = 0;
      int k = 0;
      if (bool)
      {
        paramName = ReflectClassUtilKt.getClassId(paramName);
        if (paramObject != null)
        {
          paramObject = (Object[])paramObject;
          i = paramObject.length;
          for (;;)
          {
            if (k >= i) {
              break label522;
            }
            localObject = paramObject[k];
            if (localObject == null) {
              break;
            }
            localObject = Name.identifier(((Enum)localObject).name());
            Intrinsics.checkExpressionValueIsNotNull(localObject, "Name.identifier((element as Enum<*>).name)");
            paramAnnotationArgumentVisitor.visitEnum(paramName, (Name)localObject);
            k++;
          }
          throw new TypeCastException("null cannot be cast to non-null type kotlin.Enum<*>");
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
      }
      if (Intrinsics.areEqual(paramName, Class.class))
      {
        if (paramObject != null)
        {
          paramName = (Object[])paramObject;
          j = paramName.length;
          for (k = i;; k++)
          {
            if (k >= j) {
              break label522;
            }
            paramObject = paramName[k];
            if (paramObject == null) {
              break;
            }
            paramAnnotationArgumentVisitor.visitClassLiteral(classLiteralValue((Class)paramObject));
          }
          throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
      }
      if (paramObject == null) {
        break label529;
      }
      paramName = (Object[])paramObject;
      i = paramName.length;
      for (k = j; k < i; k++) {
        paramAnnotationArgumentVisitor.visit(paramName[k]);
      }
      label522:
      paramAnnotationArgumentVisitor.visitEnd();
    }
    label528:
    return;
    label529:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
    label540:
    return;
    label541:
    paramAnnotationArgumentVisitor = new StringBuilder();
    paramAnnotationArgumentVisitor.append("Unsupported annotation argument value (");
    paramAnnotationArgumentVisitor.append(localObject);
    paramAnnotationArgumentVisitor.append("): ");
    paramAnnotationArgumentVisitor.append(paramObject);
    throw ((Throwable)new UnsupportedOperationException(paramAnnotationArgumentVisitor.toString()));
  }
  
  private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor paramAnnotationArgumentVisitor, Annotation paramAnnotation, Class<?> paramClass)
  {
    paramClass = paramClass.getDeclaredMethods();
    int i = paramClass.length;
    int j = 0;
    while (j < i)
    {
      Object localObject1 = paramClass[j];
      try
      {
        Object localObject2 = ((Method)localObject1).invoke(paramAnnotation, new Object[0]);
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "method");
        localObject1 = Name.identifier(((Method)localObject1).getName());
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "Name.identifier(method.name)");
        processAnnotationArgumentValue(paramAnnotationArgumentVisitor, (Name)localObject1, localObject2);
        j++;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        for (;;) {}
      }
    }
    paramAnnotationArgumentVisitor.visitEnd();
  }
  
  public final void loadClassAnnotations(Class<?> paramClass, KotlinJvmBinaryClass.AnnotationVisitor paramAnnotationVisitor)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "klass");
    Intrinsics.checkParameterIsNotNull(paramAnnotationVisitor, "visitor");
    for (paramClass : paramClass.getDeclaredAnnotations())
    {
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "annotation");
      processAnnotation(paramAnnotationVisitor, paramClass);
    }
    paramAnnotationVisitor.visitEnd();
  }
  
  public final void visitMembers(Class<?> paramClass, KotlinJvmBinaryClass.MemberVisitor paramMemberVisitor)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "klass");
    Intrinsics.checkParameterIsNotNull(paramMemberVisitor, "memberVisitor");
    loadMethodAnnotations(paramClass, paramMemberVisitor);
    loadConstructorAnnotations(paramClass, paramMemberVisitor);
    loadFieldAnnotations(paramClass, paramMemberVisitor);
  }
}
