package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public enum JvmPrimitiveType
{
  private static final Map<String, JvmPrimitiveType> TYPE_BY_DESC;
  private static final Map<String, JvmPrimitiveType> TYPE_BY_NAME;
  private static final Map<PrimitiveType, JvmPrimitiveType> TYPE_BY_PRIMITIVE_TYPE;
  private static final Set<FqName> WRAPPERS_CLASS_NAMES;
  private final String desc;
  private final String name;
  private final PrimitiveType primitiveType;
  private final FqName wrapperFqName;
  
  static
  {
    BYTE = new JvmPrimitiveType("BYTE", 2, PrimitiveType.BYTE, "byte", "B", "java.lang.Byte");
    SHORT = new JvmPrimitiveType("SHORT", 3, PrimitiveType.SHORT, "short", "S", "java.lang.Short");
    INT = new JvmPrimitiveType("INT", 4, PrimitiveType.INT, "int", "I", "java.lang.Integer");
    FLOAT = new JvmPrimitiveType("FLOAT", 5, PrimitiveType.FLOAT, "float", "F", "java.lang.Float");
    LONG = new JvmPrimitiveType("LONG", 6, PrimitiveType.LONG, "long", "J", "java.lang.Long");
    JvmPrimitiveType localJvmPrimitiveType = new JvmPrimitiveType("DOUBLE", 7, PrimitiveType.DOUBLE, "double", "D", "java.lang.Double");
    DOUBLE = localJvmPrimitiveType;
    Object localObject = BOOLEAN;
    int i = 0;
    $VALUES = new JvmPrimitiveType[] { localObject, CHAR, BYTE, SHORT, INT, FLOAT, LONG, localJvmPrimitiveType };
    WRAPPERS_CLASS_NAMES = new HashSet();
    TYPE_BY_NAME = new HashMap();
    TYPE_BY_PRIMITIVE_TYPE = new EnumMap(PrimitiveType.class);
    TYPE_BY_DESC = new HashMap();
    localObject = values();
    int j = localObject.length;
    while (i < j)
    {
      localJvmPrimitiveType = localObject[i];
      WRAPPERS_CLASS_NAMES.add(localJvmPrimitiveType.getWrapperFqName());
      TYPE_BY_NAME.put(localJvmPrimitiveType.getJavaKeywordName(), localJvmPrimitiveType);
      TYPE_BY_PRIMITIVE_TYPE.put(localJvmPrimitiveType.getPrimitiveType(), localJvmPrimitiveType);
      TYPE_BY_DESC.put(localJvmPrimitiveType.getDesc(), localJvmPrimitiveType);
      i++;
    }
  }
  
  private JvmPrimitiveType(PrimitiveType paramPrimitiveType, String paramString1, String paramString2, String paramString3)
  {
    this.primitiveType = paramPrimitiveType;
    this.name = paramString1;
    this.desc = paramString2;
    this.wrapperFqName = new FqName(paramString3);
  }
  
  public static JvmPrimitiveType get(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(1);
    }
    Object localObject = (JvmPrimitiveType)TYPE_BY_NAME.get(paramString);
    if (localObject != null)
    {
      if (localObject == null) {
        $$$reportNull$$$0(2);
      }
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Non-primitive type name passed: ");
    ((StringBuilder)localObject).append(paramString);
    throw new AssertionError(((StringBuilder)localObject).toString());
  }
  
  public static JvmPrimitiveType get(PrimitiveType paramPrimitiveType)
  {
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(3);
    }
    paramPrimitiveType = (JvmPrimitiveType)TYPE_BY_PRIMITIVE_TYPE.get(paramPrimitiveType);
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(4);
    }
    return paramPrimitiveType;
  }
  
  public String getDesc()
  {
    String str = this.desc;
    if (str == null) {
      $$$reportNull$$$0(12);
    }
    return str;
  }
  
  public String getJavaKeywordName()
  {
    String str = this.name;
    if (str == null) {
      $$$reportNull$$$0(11);
    }
    return str;
  }
  
  public PrimitiveType getPrimitiveType()
  {
    PrimitiveType localPrimitiveType = this.primitiveType;
    if (localPrimitiveType == null) {
      $$$reportNull$$$0(10);
    }
    return localPrimitiveType;
  }
  
  public FqName getWrapperFqName()
  {
    FqName localFqName = this.wrapperFqName;
    if (localFqName == null) {
      $$$reportNull$$$0(13);
    }
    return localFqName;
  }
}
