package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Type;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassObjectAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaClassObjectAnnotationArgument
  extends ReflectJavaAnnotationArgument
  implements JavaClassObjectAnnotationArgument
{
  private final Class<?> klass;
  
  public ReflectJavaClassObjectAnnotationArgument(Name paramName, Class<?> paramClass)
  {
    super(paramName);
    this.klass = paramClass;
  }
  
  public JavaType getReferencedType()
  {
    return (JavaType)ReflectJavaType.Factory.create((Type)this.klass);
  }
}
