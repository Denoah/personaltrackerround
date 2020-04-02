package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public abstract class UnwrappedType
  extends KotlinType
{
  private UnwrappedType()
  {
    super(null);
  }
  
  public abstract UnwrappedType makeNullableAsSpecified(boolean paramBoolean);
  
  public abstract UnwrappedType refine(KotlinTypeRefiner paramKotlinTypeRefiner);
  
  public abstract UnwrappedType replaceAnnotations(Annotations paramAnnotations);
  
  public final UnwrappedType unwrap()
  {
    return this;
  }
}
