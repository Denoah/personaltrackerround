package kotlin.reflect.jvm.internal.impl.name;

public final class Name
  implements Comparable<Name>
{
  private final String name;
  private final boolean special;
  
  private Name(String paramString, boolean paramBoolean)
  {
    this.name = paramString;
    this.special = paramBoolean;
  }
  
  public static Name guessByFirstCharacter(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(6);
    }
    if (paramString.startsWith("<")) {
      return special(paramString);
    }
    return identifier(paramString);
  }
  
  public static Name identifier(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(3);
    }
    return new Name(paramString, false);
  }
  
  public static boolean isValidIdentifier(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(4);
    }
    if ((!paramString.isEmpty()) && (!paramString.startsWith("<")))
    {
      int i = 0;
      while (i < paramString.length())
      {
        int j = paramString.charAt(i);
        if ((j != 46) && (j != 47) && (j != 92)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static Name special(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(5);
    }
    if (paramString.startsWith("<")) {
      return new Name(paramString, true);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("special name must start with '<': ");
    localStringBuilder.append(paramString);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public String asString()
  {
    String str = this.name;
    if (str == null) {
      $$$reportNull$$$0(1);
    }
    return str;
  }
  
  public int compareTo(Name paramName)
  {
    return this.name.compareTo(paramName.name);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Name)) {
      return false;
    }
    paramObject = (Name)paramObject;
    if (this.special != paramObject.special) {
      return false;
    }
    return this.name.equals(paramObject.name);
  }
  
  public String getIdentifier()
  {
    if (!this.special)
    {
      localObject = asString();
      if (localObject == null) {
        $$$reportNull$$$0(2);
      }
      return localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("not identifier: ");
    ((StringBuilder)localObject).append(this);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public int hashCode()
  {
    return this.name.hashCode() * 31 + this.special;
  }
  
  public boolean isSpecial()
  {
    return this.special;
  }
  
  public String toString()
  {
    return this.name;
  }
}
