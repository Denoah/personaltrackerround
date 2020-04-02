package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;

public final class PossiblyInnerType
{
  private final List<TypeProjection> arguments;
  private final ClassifierDescriptorWithTypeParameters classifierDescriptor;
  private final PossiblyInnerType outerType;
  
  public PossiblyInnerType(ClassifierDescriptorWithTypeParameters paramClassifierDescriptorWithTypeParameters, List<? extends TypeProjection> paramList, PossiblyInnerType paramPossiblyInnerType)
  {
    this.classifierDescriptor = paramClassifierDescriptorWithTypeParameters;
    this.arguments = paramList;
    this.outerType = paramPossiblyInnerType;
  }
  
  public final List<TypeProjection> getArguments()
  {
    return this.arguments;
  }
  
  public final ClassifierDescriptorWithTypeParameters getClassifierDescriptor()
  {
    return this.classifierDescriptor;
  }
  
  public final PossiblyInnerType getOuterType()
  {
    return this.outerType;
  }
}
