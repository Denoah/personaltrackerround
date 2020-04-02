package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public enum PrimitiveType
{
  public static final Set<PrimitiveType> NUMBER_TYPES;
  private FqName arrayTypeFqName = null;
  private final Name arrayTypeName;
  private FqName typeFqName = null;
  private final Name typeName;
  
  static
  {
    BYTE = new PrimitiveType("BYTE", 2, "Byte");
    SHORT = new PrimitiveType("SHORT", 3, "Short");
    INT = new PrimitiveType("INT", 4, "Int");
    FLOAT = new PrimitiveType("FLOAT", 5, "Float");
    LONG = new PrimitiveType("LONG", 6, "Long");
    PrimitiveType localPrimitiveType1 = new PrimitiveType("DOUBLE", 7, "Double");
    DOUBLE = localPrimitiveType1;
    PrimitiveType localPrimitiveType2 = BOOLEAN;
    PrimitiveType localPrimitiveType3 = CHAR;
    PrimitiveType localPrimitiveType4 = BYTE;
    PrimitiveType localPrimitiveType5 = SHORT;
    PrimitiveType localPrimitiveType6 = INT;
    PrimitiveType localPrimitiveType7 = FLOAT;
    PrimitiveType localPrimitiveType8 = LONG;
    $VALUES = new PrimitiveType[] { localPrimitiveType2, localPrimitiveType3, localPrimitiveType4, localPrimitiveType5, localPrimitiveType6, localPrimitiveType7, localPrimitiveType8, localPrimitiveType1 };
    NUMBER_TYPES = Collections.unmodifiableSet(EnumSet.of(localPrimitiveType3, new PrimitiveType[] { localPrimitiveType4, localPrimitiveType5, localPrimitiveType6, localPrimitiveType7, localPrimitiveType8, localPrimitiveType1 }));
  }
  
  private PrimitiveType(String paramString)
  {
    this.typeName = Name.identifier(paramString);
    ??? = new StringBuilder();
    ((StringBuilder)???).append(paramString);
    ((StringBuilder)???).append("Array");
    this.arrayTypeName = Name.identifier(((StringBuilder)???).toString());
  }
  
  public FqName getArrayTypeFqName()
  {
    FqName localFqName = this.arrayTypeFqName;
    if (localFqName != null)
    {
      if (localFqName == null) {
        $$$reportNull$$$0(4);
      }
      return localFqName;
    }
    localFqName = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(this.arrayTypeName);
    this.arrayTypeFqName = localFqName;
    if (localFqName == null) {
      $$$reportNull$$$0(5);
    }
    return localFqName;
  }
  
  public Name getArrayTypeName()
  {
    Name localName = this.arrayTypeName;
    if (localName == null) {
      $$$reportNull$$$0(3);
    }
    return localName;
  }
  
  public FqName getTypeFqName()
  {
    FqName localFqName = this.typeFqName;
    if (localFqName != null)
    {
      if (localFqName == null) {
        $$$reportNull$$$0(1);
      }
      return localFqName;
    }
    localFqName = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(this.typeName);
    this.typeFqName = localFqName;
    if (localFqName == null) {
      $$$reportNull$$$0(2);
    }
    return localFqName;
  }
  
  public Name getTypeName()
  {
    Name localName = this.typeName;
    if (localName == null) {
      $$$reportNull$$$0(0);
    }
    return localName;
  }
}
