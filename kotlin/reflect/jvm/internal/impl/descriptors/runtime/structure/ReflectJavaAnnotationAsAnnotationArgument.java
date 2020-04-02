package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationAsAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaAnnotationAsAnnotationArgument
  extends ReflectJavaAnnotationArgument
  implements JavaAnnotationAsAnnotationArgument
{
  private final Annotation annotation;
  
  public ReflectJavaAnnotationAsAnnotationArgument(Name paramName, Annotation paramAnnotation)
  {
    super(paramName);
    this.annotation = paramAnnotation;
  }
  
  public JavaAnnotation getAnnotation()
  {
    return (JavaAnnotation)new ReflectJavaAnnotation(this.annotation);
  }
}
