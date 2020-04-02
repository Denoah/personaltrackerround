package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;

final class TypeArgument
{
  private final KotlinType inProjection;
  private final KotlinType outProjection;
  private final TypeParameterDescriptor typeParameter;
  
  public TypeArgument(TypeParameterDescriptor paramTypeParameterDescriptor, KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    this.typeParameter = paramTypeParameterDescriptor;
    this.inProjection = paramKotlinType1;
    this.outProjection = paramKotlinType2;
  }
  
  public final KotlinType getInProjection()
  {
    return this.inProjection;
  }
  
  public final KotlinType getOutProjection()
  {
    return this.outProjection;
  }
  
  public final TypeParameterDescriptor getTypeParameter()
  {
    return this.typeParameter;
  }
  
  public final boolean isConsistent()
  {
    return KotlinTypeChecker.DEFAULT.isSubtypeOf(this.inProjection, this.outProjection);
  }
}
