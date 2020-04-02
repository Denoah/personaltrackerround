package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaEnumValueAnnotationArgument
  extends ReflectJavaAnnotationArgument
  implements JavaEnumValueAnnotationArgument
{
  private final Enum<?> value;
  
  public ReflectJavaEnumValueAnnotationArgument(Name paramName, Enum<?> paramEnum)
  {
    super(paramName);
    this.value = paramEnum;
  }
  
  public Name getEntryName()
  {
    return Name.identifier(this.value.name());
  }
  
  public ClassId getEnumClassId()
  {
    Class localClass = this.value.getClass();
    if (!localClass.isEnum()) {
      localClass = localClass.getEnclosingClass();
    }
    Intrinsics.checkExpressionValueIsNotNull(localClass, "enumClass");
    return ReflectClassUtilKt.getClassId(localClass);
  }
}
