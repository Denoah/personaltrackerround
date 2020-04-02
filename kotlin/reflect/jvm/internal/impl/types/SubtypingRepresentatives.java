package kotlin.reflect.jvm.internal.impl.types;

public abstract interface SubtypingRepresentatives
{
  public abstract KotlinType getSubTypeRepresentative();
  
  public abstract KotlinType getSuperTypeRepresentative();
  
  public abstract boolean sameTypeConstructor(KotlinType paramKotlinType);
}
