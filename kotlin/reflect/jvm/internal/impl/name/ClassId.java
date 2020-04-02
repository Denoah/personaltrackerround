package kotlin.reflect.jvm.internal.impl.name;

import kotlin.text.StringsKt;

public final class ClassId
{
  private final boolean local;
  private final FqName packageFqName;
  private final FqName relativeClassName;
  
  public ClassId(FqName paramFqName1, FqName paramFqName2, boolean paramBoolean)
  {
    this.packageFqName = paramFqName1;
    this.relativeClassName = paramFqName2;
    this.local = paramBoolean;
  }
  
  public ClassId(FqName paramFqName, Name paramName)
  {
    this(paramFqName, FqName.topLevel(paramName), false);
  }
  
  public static ClassId fromString(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(11);
    }
    return fromString(paramString, false);
  }
  
  public static ClassId fromString(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      $$$reportNull$$$0(12);
    }
    String str = StringsKt.substringBeforeLast(paramString, '/', "").replace('/', '.');
    paramString = StringsKt.substringAfterLast(paramString, '/', paramString);
    return new ClassId(new FqName(str), new FqName(paramString), paramBoolean);
  }
  
  public static ClassId topLevel(FqName paramFqName)
  {
    if (paramFqName == null) {
      $$$reportNull$$$0(0);
    }
    return new ClassId(paramFqName.parent(), paramFqName.shortName());
  }
  
  public FqName asSingleFqName()
  {
    if (this.packageFqName.isRoot())
    {
      localObject = this.relativeClassName;
      if (localObject == null) {
        $$$reportNull$$$0(9);
      }
      return localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.packageFqName.asString());
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(this.relativeClassName.asString());
    return new FqName(((StringBuilder)localObject).toString());
  }
  
  public String asString()
  {
    if (this.packageFqName.isRoot())
    {
      localObject = this.relativeClassName.asString();
      if (localObject == null) {
        $$$reportNull$$$0(13);
      }
      return localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.packageFqName.asString().replace('.', '/'));
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(this.relativeClassName.asString());
    localObject = ((StringBuilder)localObject).toString();
    if (localObject == null) {
      $$$reportNull$$$0(14);
    }
    return localObject;
  }
  
  public ClassId createNestedClassId(Name paramName)
  {
    if (paramName == null) {
      $$$reportNull$$$0(8);
    }
    return new ClassId(getPackageFqName(), this.relativeClassName.child(paramName), this.local);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (ClassId)paramObject;
      if ((!this.packageFqName.equals(paramObject.packageFqName)) || (!this.relativeClassName.equals(paramObject.relativeClassName)) || (this.local != paramObject.local)) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public ClassId getOuterClassId()
  {
    Object localObject = this.relativeClassName.parent();
    if (((FqName)localObject).isRoot()) {
      localObject = null;
    } else {
      localObject = new ClassId(getPackageFqName(), (FqName)localObject, this.local);
    }
    return localObject;
  }
  
  public FqName getPackageFqName()
  {
    FqName localFqName = this.packageFqName;
    if (localFqName == null) {
      $$$reportNull$$$0(5);
    }
    return localFqName;
  }
  
  public FqName getRelativeClassName()
  {
    FqName localFqName = this.relativeClassName;
    if (localFqName == null) {
      $$$reportNull$$$0(6);
    }
    return localFqName;
  }
  
  public Name getShortClassName()
  {
    Name localName = this.relativeClassName.shortName();
    if (localName == null) {
      $$$reportNull$$$0(7);
    }
    return localName;
  }
  
  public int hashCode()
  {
    return (this.packageFqName.hashCode() * 31 + this.relativeClassName.hashCode()) * 31 + Boolean.valueOf(this.local).hashCode();
  }
  
  public boolean isLocal()
  {
    return this.local;
  }
  
  public boolean isNestedClass()
  {
    return this.relativeClassName.parent().isRoot() ^ true;
  }
  
  public String toString()
  {
    Object localObject;
    if (this.packageFqName.isRoot())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("/");
      ((StringBuilder)localObject).append(asString());
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = asString();
    }
    return localObject;
  }
}
