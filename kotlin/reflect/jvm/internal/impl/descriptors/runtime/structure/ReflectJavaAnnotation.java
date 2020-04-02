package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaAnnotation
  extends ReflectJavaElement
  implements JavaAnnotation
{
  private final Annotation annotation;
  
  public ReflectJavaAnnotation(Annotation paramAnnotation)
  {
    this.annotation = paramAnnotation;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectJavaAnnotation)) && (Intrinsics.areEqual(this.annotation, ((ReflectJavaAnnotation)paramObject).annotation))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final Annotation getAnnotation()
  {
    return this.annotation;
  }
  
  public Collection<JavaAnnotationArgument> getArguments()
  {
    Method[] arrayOfMethod = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)).getDeclaredMethods();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfMethod, "annotation.annotationClass.java.declaredMethods");
    Collection localCollection = (Collection)new ArrayList(arrayOfMethod.length);
    int i = arrayOfMethod.length;
    for (int j = 0; j < i; j++)
    {
      Method localMethod = arrayOfMethod[j];
      ReflectJavaAnnotationArgument.Factory localFactory = ReflectJavaAnnotationArgument.Factory;
      Object localObject = localMethod.invoke(this.annotation, new Object[0]);
      Intrinsics.checkExpressionValueIsNotNull(localObject, "method.invoke(annotation)");
      Intrinsics.checkExpressionValueIsNotNull(localMethod, "method");
      localCollection.add(localFactory.create(localObject, Name.identifier(localMethod.getName())));
    }
    return (Collection)localCollection;
  }
  
  public ClassId getClassId()
  {
    return ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)));
  }
  
  public int hashCode()
  {
    return this.annotation.hashCode();
  }
  
  public boolean isIdeExternalAnnotation()
  {
    return JavaAnnotation.DefaultImpls.isIdeExternalAnnotation(this);
  }
  
  public ReflectJavaClass resolve()
  {
    return new ReflectJavaClass(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(this.annotation)));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(this.annotation);
    return localStringBuilder.toString();
  }
}
