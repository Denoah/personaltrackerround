package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract class ReflectJavaAnnotationArgument
  implements JavaAnnotationArgument
{
  public static final Factory Factory = new Factory(null);
  private final Name name;
  
  public ReflectJavaAnnotationArgument(Name paramName)
  {
    this.name = paramName;
  }
  
  public Name getName()
  {
    return this.name;
  }
  
  public static final class Factory
  {
    private Factory() {}
    
    public final ReflectJavaAnnotationArgument create(Object paramObject, Name paramName)
    {
      Intrinsics.checkParameterIsNotNull(paramObject, "value");
      if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(paramObject.getClass())) {
        paramObject = (ReflectJavaAnnotationArgument)new ReflectJavaEnumValueAnnotationArgument(paramName, (Enum)paramObject);
      } else if ((paramObject instanceof Annotation)) {
        paramObject = (ReflectJavaAnnotationArgument)new ReflectJavaAnnotationAsAnnotationArgument(paramName, (Annotation)paramObject);
      } else if ((paramObject instanceof Object[])) {
        paramObject = (ReflectJavaAnnotationArgument)new ReflectJavaArrayAnnotationArgument(paramName, (Object[])paramObject);
      } else if ((paramObject instanceof Class)) {
        paramObject = (ReflectJavaAnnotationArgument)new ReflectJavaClassObjectAnnotationArgument(paramName, (Class)paramObject);
      } else {
        paramObject = (ReflectJavaAnnotationArgument)new ReflectJavaLiteralAnnotationArgument(paramName, paramObject);
      }
      return paramObject;
    }
  }
}
