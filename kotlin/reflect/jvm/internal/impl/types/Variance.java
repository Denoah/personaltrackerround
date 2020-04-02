package kotlin.reflect.jvm.internal.impl.types;

public enum Variance
{
  private final boolean allowsInPosition;
  private final boolean allowsOutPosition;
  private final String label;
  private final int superpositionFactor;
  
  static
  {
    Variance localVariance1 = new Variance("INVARIANT", 0, "", true, true, 0);
    INVARIANT = localVariance1;
    Variance localVariance2 = new Variance("IN_VARIANCE", 1, "in", true, false, -1);
    IN_VARIANCE = localVariance2;
    Variance localVariance3 = new Variance("OUT_VARIANCE", 2, "out", false, true, 1);
    OUT_VARIANCE = localVariance3;
    $VALUES = new Variance[] { localVariance1, localVariance2, localVariance3 };
  }
  
  private Variance(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this.label = paramString;
    this.allowsInPosition = paramBoolean1;
    this.allowsOutPosition = paramBoolean2;
    this.superpositionFactor = paramInt;
  }
  
  public final boolean getAllowsOutPosition()
  {
    return this.allowsOutPosition;
  }
  
  public final String getLabel()
  {
    return this.label;
  }
  
  public String toString()
  {
    return this.label;
  }
}
