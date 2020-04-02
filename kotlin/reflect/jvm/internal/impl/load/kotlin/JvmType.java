package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

public abstract class JvmType
{
  private JvmType() {}
  
  public String toString()
  {
    return JvmTypeFactoryImpl.INSTANCE.toString(this);
  }
  
  public static final class Array
    extends JvmType
  {
    private final JvmType elementType;
    
    public Array(JvmType paramJvmType)
    {
      super();
      this.elementType = paramJvmType;
    }
    
    public final JvmType getElementType()
    {
      return this.elementType;
    }
  }
  
  public static final class Object
    extends JvmType
  {
    private final String internalName;
    
    public Object(String paramString)
    {
      super();
      this.internalName = paramString;
    }
    
    public final String getInternalName()
    {
      return this.internalName;
    }
  }
  
  public static final class Primitive
    extends JvmType
  {
    private final JvmPrimitiveType jvmPrimitiveType;
    
    public Primitive(JvmPrimitiveType paramJvmPrimitiveType)
    {
      super();
      this.jvmPrimitiveType = paramJvmPrimitiveType;
    }
    
    public final JvmPrimitiveType getJvmPrimitiveType()
    {
      return this.jvmPrimitiveType;
    }
  }
}
