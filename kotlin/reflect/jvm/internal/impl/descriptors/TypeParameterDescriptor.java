package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;

public abstract interface TypeParameterDescriptor
  extends ClassifierDescriptor, TypeParameterMarker
{
  public abstract int getIndex();
  
  public abstract TypeParameterDescriptor getOriginal();
  
  public abstract TypeConstructor getTypeConstructor();
  
  public abstract List<KotlinType> getUpperBounds();
  
  public abstract Variance getVariance();
  
  public abstract boolean isCapturedFromOuterDeclaration();
  
  public abstract boolean isReified();
}
