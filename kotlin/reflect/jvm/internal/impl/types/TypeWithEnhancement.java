package kotlin.reflect.jvm.internal.impl.types;

public abstract interface TypeWithEnhancement
{
  public abstract KotlinType getEnhancement();
  
  public abstract UnwrappedType getOrigin();
}
