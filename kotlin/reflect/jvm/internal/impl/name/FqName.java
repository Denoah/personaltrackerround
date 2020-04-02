package kotlin.reflect.jvm.internal.impl.name;

import java.util.List;

public final class FqName
{
  public static final FqName ROOT = new FqName("");
  private final FqNameUnsafe fqName;
  private transient FqName parent;
  
  public FqName(String paramString)
  {
    this.fqName = new FqNameUnsafe(paramString, this);
  }
  
  public FqName(FqNameUnsafe paramFqNameUnsafe)
  {
    this.fqName = paramFqNameUnsafe;
  }
  
  private FqName(FqNameUnsafe paramFqNameUnsafe, FqName paramFqName)
  {
    this.fqName = paramFqNameUnsafe;
    this.parent = paramFqName;
  }
  
  public static FqName topLevel(Name paramName)
  {
    if (paramName == null) {
      $$$reportNull$$$0(13);
    }
    return new FqName(FqNameUnsafe.topLevel(paramName));
  }
  
  public String asString()
  {
    String str = this.fqName.asString();
    if (str == null) {
      $$$reportNull$$$0(4);
    }
    return str;
  }
  
  public FqName child(Name paramName)
  {
    if (paramName == null) {
      $$$reportNull$$$0(8);
    }
    return new FqName(this.fqName.child(paramName), this);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof FqName)) {
      return false;
    }
    paramObject = (FqName)paramObject;
    return this.fqName.equals(paramObject.fqName);
  }
  
  public int hashCode()
  {
    return this.fqName.hashCode();
  }
  
  public boolean isRoot()
  {
    return this.fqName.isRoot();
  }
  
  public FqName parent()
  {
    FqName localFqName = this.parent;
    if (localFqName != null)
    {
      if (localFqName == null) {
        $$$reportNull$$$0(6);
      }
      return localFqName;
    }
    if (!isRoot())
    {
      localFqName = new FqName(this.fqName.parent());
      this.parent = localFqName;
      if (localFqName == null) {
        $$$reportNull$$$0(7);
      }
      return localFqName;
    }
    throw new IllegalStateException("root");
  }
  
  public List<Name> pathSegments()
  {
    List localList = this.fqName.pathSegments();
    if (localList == null) {
      $$$reportNull$$$0(11);
    }
    return localList;
  }
  
  public Name shortName()
  {
    Name localName = this.fqName.shortName();
    if (localName == null) {
      $$$reportNull$$$0(9);
    }
    return localName;
  }
  
  public Name shortNameOrSpecial()
  {
    Name localName = this.fqName.shortNameOrSpecial();
    if (localName == null) {
      $$$reportNull$$$0(10);
    }
    return localName;
  }
  
  public boolean startsWith(Name paramName)
  {
    if (paramName == null) {
      $$$reportNull$$$0(12);
    }
    return this.fqName.startsWith(paramName);
  }
  
  public String toString()
  {
    return this.fqName.toString();
  }
  
  public FqNameUnsafe toUnsafe()
  {
    FqNameUnsafe localFqNameUnsafe = this.fqName;
    if (localFqNameUnsafe == null) {
      $$$reportNull$$$0(5);
    }
    return localFqNameUnsafe;
  }
}
