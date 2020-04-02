package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;

public abstract class FlexibleType
  extends UnwrappedType
  implements SubtypingRepresentatives, FlexibleTypeMarker
{
  private final SimpleType lowerBound;
  private final SimpleType upperBound;
  
  public FlexibleType(SimpleType paramSimpleType1, SimpleType paramSimpleType2)
  {
    super(null);
    this.lowerBound = paramSimpleType1;
    this.upperBound = paramSimpleType2;
  }
  
  public Annotations getAnnotations()
  {
    return getDelegate().getAnnotations();
  }
  
  public List<TypeProjection> getArguments()
  {
    return getDelegate().getArguments();
  }
  
  public TypeConstructor getConstructor()
  {
    return getDelegate().getConstructor();
  }
  
  public abstract SimpleType getDelegate();
  
  public final SimpleType getLowerBound()
  {
    return this.lowerBound;
  }
  
  public MemberScope getMemberScope()
  {
    return getDelegate().getMemberScope();
  }
  
  public KotlinType getSubTypeRepresentative()
  {
    return (KotlinType)this.lowerBound;
  }
  
  public KotlinType getSuperTypeRepresentative()
  {
    return (KotlinType)this.upperBound;
  }
  
  public final SimpleType getUpperBound()
  {
    return this.upperBound;
  }
  
  public boolean isMarkedNullable()
  {
    return getDelegate().isMarkedNullable();
  }
  
  public abstract String render(DescriptorRenderer paramDescriptorRenderer, DescriptorRendererOptions paramDescriptorRendererOptions);
  
  public boolean sameTypeConstructor(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    return false;
  }
  
  public String toString()
  {
    return DescriptorRenderer.DEBUG_TEXT.renderType((KotlinType)this);
  }
}
