package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public abstract interface KotlinTypeChecker
{
  public static final KotlinTypeChecker DEFAULT = NewKotlinTypeChecker.Companion.getDefault();
  
  public abstract boolean equalTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2);
  
  public abstract boolean isSubtypeOf(KotlinType paramKotlinType1, KotlinType paramKotlinType2);
  
  public static abstract interface TypeConstructorEquality
  {
    public abstract boolean equals(TypeConstructor paramTypeConstructor1, TypeConstructor paramTypeConstructor2);
  }
}
