package kotlin.reflect.jvm.internal.impl.types;

public abstract interface CustomTypeVariable
{
  public abstract boolean isTypeVariable();
  
  public abstract KotlinType substitutionResult(KotlinType paramKotlinType);
}
