package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

public final class JavaTypeQualifiers
{
  public static final Companion Companion = new Companion(null);
  private static final JavaTypeQualifiers NONE = new JavaTypeQualifiers(null, null, false, false, 8, null);
  private final boolean isNotNullTypeParameter;
  private final boolean isNullabilityQualifierForWarning;
  private final MutabilityQualifier mutability;
  private final NullabilityQualifier nullability;
  
  public JavaTypeQualifiers(NullabilityQualifier paramNullabilityQualifier, MutabilityQualifier paramMutabilityQualifier, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.nullability = paramNullabilityQualifier;
    this.mutability = paramMutabilityQualifier;
    this.isNotNullTypeParameter = paramBoolean1;
    this.isNullabilityQualifierForWarning = paramBoolean2;
  }
  
  public final MutabilityQualifier getMutability()
  {
    return this.mutability;
  }
  
  public final NullabilityQualifier getNullability()
  {
    return this.nullability;
  }
  
  public final boolean isNotNullTypeParameter()
  {
    return this.isNotNullTypeParameter;
  }
  
  public final boolean isNullabilityQualifierForWarning()
  {
    return this.isNullabilityQualifierForWarning;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final JavaTypeQualifiers getNONE()
    {
      return JavaTypeQualifiers.access$getNONE$cp();
    }
  }
}
