package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaLiteralAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaLiteralAnnotationArgument
  extends ReflectJavaAnnotationArgument
  implements JavaLiteralAnnotationArgument
{
  private final Object value;
  
  public ReflectJavaLiteralAnnotationArgument(Name paramName, Object paramObject)
  {
    super(paramName);
    this.value = paramObject;
  }
  
  public Object getValue()
  {
    return this.value;
  }
}
