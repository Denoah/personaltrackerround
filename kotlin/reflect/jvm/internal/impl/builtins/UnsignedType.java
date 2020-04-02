package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

public enum UnsignedType
{
  private final ClassId arrayClassId;
  private final ClassId classId;
  private final Name typeName;
  
  static
  {
    Object localObject1 = ClassId.fromString("kotlin/UByte");
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "ClassId.fromString(\"kotlin/UByte\")");
    localObject1 = new UnsignedType("UBYTE", 0, (ClassId)localObject1);
    UBYTE = (UnsignedType)localObject1;
    Object localObject2 = ClassId.fromString("kotlin/UShort");
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "ClassId.fromString(\"kotlin/UShort\")");
    localObject2 = new UnsignedType("USHORT", 1, (ClassId)localObject2);
    USHORT = (UnsignedType)localObject2;
    Object localObject3 = ClassId.fromString("kotlin/UInt");
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.fromString(\"kotlin/UInt\")");
    localObject3 = new UnsignedType("UINT", 2, (ClassId)localObject3);
    UINT = (UnsignedType)localObject3;
    Object localObject4 = ClassId.fromString("kotlin/ULong");
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "ClassId.fromString(\"kotlin/ULong\")");
    localObject4 = new UnsignedType("ULONG", 3, (ClassId)localObject4);
    ULONG = (UnsignedType)localObject4;
    $VALUES = new UnsignedType[] { localObject1, localObject2, localObject3, localObject4 };
  }
  
  private UnsignedType(ClassId paramClassId)
  {
    this.classId = paramClassId;
    ??? = paramClassId.getShortClassName();
    Intrinsics.checkExpressionValueIsNotNull(???, "classId.shortClassName");
    this.typeName = ((Name)???);
    ClassId paramClassId = this.classId.getPackageFqName();
    ??? = new StringBuilder();
    ((StringBuilder)???).append(this.typeName.asString());
    ((StringBuilder)???).append("Array");
    this.arrayClassId = new ClassId(paramClassId, Name.identifier(((StringBuilder)???).toString()));
  }
  
  public final ClassId getArrayClassId()
  {
    return this.arrayClassId;
  }
  
  public final ClassId getClassId()
  {
    return this.classId;
  }
  
  public final Name getTypeName()
  {
    return this.typeName;
  }
}
