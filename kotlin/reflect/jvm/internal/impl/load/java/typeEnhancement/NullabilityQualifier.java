package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

public enum NullabilityQualifier
{
  static
  {
    NullabilityQualifier localNullabilityQualifier1 = new NullabilityQualifier("NULLABLE", 0);
    NULLABLE = localNullabilityQualifier1;
    NullabilityQualifier localNullabilityQualifier2 = new NullabilityQualifier("NOT_NULL", 1);
    NOT_NULL = localNullabilityQualifier2;
    NullabilityQualifier localNullabilityQualifier3 = new NullabilityQualifier("FORCE_FLEXIBILITY", 2);
    FORCE_FLEXIBILITY = localNullabilityQualifier3;
    $VALUES = new NullabilityQualifier[] { localNullabilityQualifier1, localNullabilityQualifier2, localNullabilityQualifier3 };
  }
  
  private NullabilityQualifier() {}
}
