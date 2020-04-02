package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public class JvmClassName
{
  private FqName fqName;
  private final String internalName;
  
  private JvmClassName(String paramString)
  {
    this.internalName = paramString;
  }
  
  public static JvmClassName byClassId(ClassId paramClassId)
  {
    if (paramClassId == null) {
      $$$reportNull$$$0(1);
    }
    FqName localFqName = paramClassId.getPackageFqName();
    paramClassId = paramClassId.getRelativeClassName().asString().replace('.', '$');
    if (localFqName.isRoot())
    {
      paramClassId = new JvmClassName(paramClassId);
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(localFqName.asString().replace('.', '/'));
      localStringBuilder.append("/");
      localStringBuilder.append(paramClassId);
      paramClassId = new JvmClassName(localStringBuilder.toString());
    }
    return paramClassId;
  }
  
  public static JvmClassName byFqNameWithoutInnerClasses(FqName paramFqName)
  {
    if (paramFqName == null) {
      $$$reportNull$$$0(2);
    }
    JvmClassName localJvmClassName = new JvmClassName(paramFqName.asString().replace('.', '/'));
    localJvmClassName.fqName = paramFqName;
    return localJvmClassName;
  }
  
  public static JvmClassName byInternalName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(0);
    }
    return new JvmClassName(paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass())) {
      return this.internalName.equals(((JvmClassName)paramObject).internalName);
    }
    return false;
  }
  
  public FqName getFqNameForTopLevelClassMaybeWithDollars()
  {
    return new FqName(this.internalName.replace('/', '.'));
  }
  
  public String getInternalName()
  {
    String str = this.internalName;
    if (str == null) {
      $$$reportNull$$$0(8);
    }
    return str;
  }
  
  public FqName getPackageFqName()
  {
    int i = this.internalName.lastIndexOf("/");
    if (i == -1)
    {
      FqName localFqName = FqName.ROOT;
      if (localFqName == null) {
        $$$reportNull$$$0(7);
      }
      return localFqName;
    }
    return new FqName(this.internalName.substring(0, i).replace('/', '.'));
  }
  
  public int hashCode()
  {
    return this.internalName.hashCode();
  }
  
  public String toString()
  {
    return this.internalName;
  }
}
