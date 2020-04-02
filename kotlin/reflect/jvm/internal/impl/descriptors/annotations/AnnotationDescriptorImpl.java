package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class AnnotationDescriptorImpl
  implements AnnotationDescriptor
{
  private final KotlinType annotationType;
  private final SourceElement source;
  private final Map<Name, ConstantValue<?>> valueArguments;
  
  public AnnotationDescriptorImpl(KotlinType paramKotlinType, Map<Name, ConstantValue<?>> paramMap, SourceElement paramSourceElement)
  {
    this.annotationType = paramKotlinType;
    this.valueArguments = paramMap;
    this.source = paramSourceElement;
  }
  
  public Map<Name, ConstantValue<?>> getAllValueArguments()
  {
    Map localMap = this.valueArguments;
    if (localMap == null) {
      $$$reportNull$$$0(4);
    }
    return localMap;
  }
  
  public FqName getFqName()
  {
    return AnnotationDescriptor.DefaultImpls.getFqName(this);
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = this.source;
    if (localSourceElement == null) {
      $$$reportNull$$$0(5);
    }
    return localSourceElement;
  }
  
  public KotlinType getType()
  {
    KotlinType localKotlinType = this.annotationType;
    if (localKotlinType == null) {
      $$$reportNull$$$0(3);
    }
    return localKotlinType;
  }
  
  public String toString()
  {
    return DescriptorRenderer.FQ_NAMES_IN_TYPES.renderAnnotation(this, null);
  }
}
