package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class CharValue
  extends IntegerValueConstant<Character>
{
  public CharValue(char paramChar)
  {
    super(Character.valueOf(paramChar));
  }
  
  private final String getPrintablePart(char paramChar)
  {
    switch (paramChar)
    {
    case '\013': 
    default: 
      if (isPrintableUnicode(paramChar)) {
        str = String.valueOf(paramChar);
      }
      break;
    case '\r': 
      str = "\\r";
      break;
    case '\f': 
      str = "\\f";
      break;
    case '\n': 
      str = "\\n";
      break;
    case '\t': 
      str = "\\t";
      break;
    case '\b': 
      str = "\\b";
      break;
    }
    String str = "?";
    return str;
  }
  
  private final boolean isPrintableUnicode(char paramChar)
  {
    int i = (byte)Character.getType(paramChar);
    boolean bool;
    if ((i != 0) && (i != 13) && (i != 14) && (i != 15) && (i != 16) && (i != 18) && (i != 19)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getCharType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.charType");
    return paramModuleDescriptor;
  }
  
  public String toString()
  {
    String str = String.format("\\u%04X ('%s')", Arrays.copyOf(new Object[] { Integer.valueOf(((Character)getValue()).charValue()), getPrintablePart(((Character)getValue()).charValue()) }, 2));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(this, *args)");
    return str;
  }
}
