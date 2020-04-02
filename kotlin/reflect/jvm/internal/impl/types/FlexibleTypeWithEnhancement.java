package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class FlexibleTypeWithEnhancement
  extends FlexibleType
  implements TypeWithEnhancement
{
  private final KotlinType enhancement;
  private final FlexibleType origin;
  
  public FlexibleTypeWithEnhancement(FlexibleType paramFlexibleType, KotlinType paramKotlinType)
  {
    super(paramFlexibleType.getLowerBound(), paramFlexibleType.getUpperBound());
    this.origin = paramFlexibleType;
    this.enhancement = paramKotlinType;
  }
  
  public SimpleType getDelegate()
  {
    return getOrigin().getDelegate();
  }
  
  public KotlinType getEnhancement()
  {
    return this.enhancement;
  }
  
  public FlexibleType getOrigin()
  {
    return this.origin;
  }
  
  public UnwrappedType makeNullableAsSpecified(boolean paramBoolean)
  {
    return TypeWithEnhancementKt.wrapEnhancement(getOrigin().makeNullableAsSpecified(paramBoolean), (KotlinType)getEnhancement().unwrap().makeNullableAsSpecified(paramBoolean));
  }
  
  public FlexibleTypeWithEnhancement refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    KotlinType localKotlinType = paramKotlinTypeRefiner.refineType((KotlinType)getOrigin());
    if (localKotlinType != null) {
      return new FlexibleTypeWithEnhancement((FlexibleType)localKotlinType, paramKotlinTypeRefiner.refineType(getEnhancement()));
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
  }
  
  public String render(DescriptorRenderer paramDescriptorRenderer, DescriptorRendererOptions paramDescriptorRendererOptions)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
    Intrinsics.checkParameterIsNotNull(paramDescriptorRendererOptions, "options");
    if (paramDescriptorRendererOptions.getEnhancedTypes()) {
      return paramDescriptorRenderer.renderType(getEnhancement());
    }
    return getOrigin().render(paramDescriptorRenderer, paramDescriptorRendererOptions);
  }
  
  public UnwrappedType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return TypeWithEnhancementKt.wrapEnhancement(getOrigin().replaceAnnotations(paramAnnotations), getEnhancement());
  }
}
