package kotlin.reflect.jvm.internal.impl.types.model;

public enum TypeVariance
{
  private final String presentation;
  
  static
  {
    TypeVariance localTypeVariance1 = new TypeVariance("IN", 0, "in");
    IN = localTypeVariance1;
    TypeVariance localTypeVariance2 = new TypeVariance("OUT", 1, "out");
    OUT = localTypeVariance2;
    TypeVariance localTypeVariance3 = new TypeVariance("INV", 2, "");
    INV = localTypeVariance3;
    $VALUES = new TypeVariance[] { localTypeVariance1, localTypeVariance2, localTypeVariance3 };
  }
  
  private TypeVariance(String paramString)
  {
    this.presentation = paramString;
  }
  
  public String toString()
  {
    return this.presentation;
  }
}
