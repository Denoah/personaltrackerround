package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class DynamicType
  extends FlexibleType
  implements DynamicTypeMarker
{
  private final Annotations annotations;
  
  public DynamicType(KotlinBuiltIns paramKotlinBuiltIns, Annotations paramAnnotations)
  {
    super(localSimpleType, paramKotlinBuiltIns);
    this.annotations = paramAnnotations;
  }
  
  public Annotations getAnnotations()
  {
    return this.annotations;
  }
  
  public SimpleType getDelegate()
  {
    return getUpperBound();
  }
  
  public boolean isMarkedNullable()
  {
    return false;
  }
  
  public DynamicType makeNullableAsSpecified(boolean paramBoolean)
  {
    return this;
  }
  
  public DynamicType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return this;
  }
  
  public String render(DescriptorRenderer paramDescriptorRenderer, DescriptorRendererOptions paramDescriptorRendererOptions)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
    Intrinsics.checkParameterIsNotNull(paramDescriptorRendererOptions, "options");
    return "dynamic";
  }
  
  public DynamicType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new DynamicType(TypeUtilsKt.getBuiltIns((KotlinType)getDelegate()), paramAnnotations);
  }
}
