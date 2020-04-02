package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;

public final class ReflectJavaField
  extends ReflectJavaMember
  implements JavaField
{
  private final Field member;
  
  public ReflectJavaField(Field paramField)
  {
    this.member = paramField;
  }
  
  public boolean getHasConstantNotNullInitializer()
  {
    return false;
  }
  
  public Field getMember()
  {
    return this.member;
  }
  
  public ReflectJavaType getType()
  {
    ReflectJavaType.Factory localFactory = ReflectJavaType.Factory;
    Type localType = getMember().getGenericType();
    Intrinsics.checkExpressionValueIsNotNull(localType, "member.genericType");
    return localFactory.create(localType);
  }
  
  public boolean isEnumEntry()
  {
    return getMember().isEnumConstant();
  }
}
