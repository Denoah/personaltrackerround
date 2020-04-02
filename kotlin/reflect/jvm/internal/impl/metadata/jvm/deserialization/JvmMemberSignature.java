package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.Intrinsics;

public abstract class JvmMemberSignature
{
  private JvmMemberSignature() {}
  
  public abstract String asString();
  
  public abstract String getDesc();
  
  public abstract String getName();
  
  public final String toString()
  {
    return asString();
  }
  
  public static final class Field
    extends JvmMemberSignature
  {
    private final String desc;
    private final String name;
    
    public Field(String paramString1, String paramString2)
    {
      super();
      this.name = paramString1;
      this.desc = paramString2;
    }
    
    public String asString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getName());
      localStringBuilder.append(':');
      localStringBuilder.append(getDesc());
      return localStringBuilder.toString();
    }
    
    public final String component1()
    {
      return getName();
    }
    
    public final String component2()
    {
      return getDesc();
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject) {
        if ((paramObject instanceof Field))
        {
          paramObject = (Field)paramObject;
          if ((Intrinsics.areEqual(getName(), paramObject.getName())) && (Intrinsics.areEqual(getDesc(), paramObject.getDesc()))) {}
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    
    public String getDesc()
    {
      return this.desc;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public int hashCode()
    {
      String str = getName();
      int i = 0;
      int j;
      if (str != null) {
        j = str.hashCode();
      } else {
        j = 0;
      }
      str = getDesc();
      if (str != null) {
        i = str.hashCode();
      }
      return j * 31 + i;
    }
  }
  
  public static final class Method
    extends JvmMemberSignature
  {
    private final String desc;
    private final String name;
    
    public Method(String paramString1, String paramString2)
    {
      super();
      this.name = paramString1;
      this.desc = paramString2;
    }
    
    public String asString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getName());
      localStringBuilder.append(getDesc());
      return localStringBuilder.toString();
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject) {
        if ((paramObject instanceof Method))
        {
          paramObject = (Method)paramObject;
          if ((Intrinsics.areEqual(getName(), paramObject.getName())) && (Intrinsics.areEqual(getDesc(), paramObject.getDesc()))) {}
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    
    public String getDesc()
    {
      return this.desc;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public int hashCode()
    {
      String str = getName();
      int i = 0;
      int j;
      if (str != null) {
        j = str.hashCode();
      } else {
        j = 0;
      }
      str = getDesc();
      if (str != null) {
        i = str.hashCode();
      }
      return j * 31 + i;
    }
  }
}
