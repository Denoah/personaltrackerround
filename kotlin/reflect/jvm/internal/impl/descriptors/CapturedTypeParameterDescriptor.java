package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

final class CapturedTypeParameterDescriptor
  implements TypeParameterDescriptor
{
  private final DeclarationDescriptor declarationDescriptor;
  private final int declaredTypeParametersCount;
  private final TypeParameterDescriptor originalDescriptor;
  
  public CapturedTypeParameterDescriptor(TypeParameterDescriptor paramTypeParameterDescriptor, DeclarationDescriptor paramDeclarationDescriptor, int paramInt)
  {
    this.originalDescriptor = paramTypeParameterDescriptor;
    this.declarationDescriptor = paramDeclarationDescriptor;
    this.declaredTypeParametersCount = paramInt;
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return this.originalDescriptor.accept(paramDeclarationDescriptorVisitor, paramD);
  }
  
  public Annotations getAnnotations()
  {
    return this.originalDescriptor.getAnnotations();
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    return this.declarationDescriptor;
  }
  
  public SimpleType getDefaultType()
  {
    return this.originalDescriptor.getDefaultType();
  }
  
  public int getIndex()
  {
    return this.declaredTypeParametersCount + this.originalDescriptor.getIndex();
  }
  
  public Name getName()
  {
    return this.originalDescriptor.getName();
  }
  
  public TypeParameterDescriptor getOriginal()
  {
    TypeParameterDescriptor localTypeParameterDescriptor = this.originalDescriptor.getOriginal();
    Intrinsics.checkExpressionValueIsNotNull(localTypeParameterDescriptor, "originalDescriptor.original");
    return localTypeParameterDescriptor;
  }
  
  public SourceElement getSource()
  {
    return this.originalDescriptor.getSource();
  }
  
  public TypeConstructor getTypeConstructor()
  {
    return this.originalDescriptor.getTypeConstructor();
  }
  
  public List<KotlinType> getUpperBounds()
  {
    return this.originalDescriptor.getUpperBounds();
  }
  
  public Variance getVariance()
  {
    return this.originalDescriptor.getVariance();
  }
  
  public boolean isCapturedFromOuterDeclaration()
  {
    return true;
  }
  
  public boolean isReified()
  {
    return this.originalDescriptor.isReified();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.originalDescriptor);
    localStringBuilder.append("[inner-copy]");
    return localStringBuilder.toString();
  }
}
