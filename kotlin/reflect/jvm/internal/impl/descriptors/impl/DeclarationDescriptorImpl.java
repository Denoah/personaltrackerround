package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotatedImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;

public abstract class DeclarationDescriptorImpl
  extends AnnotatedImpl
  implements DeclarationDescriptor
{
  private final Name name;
  
  public DeclarationDescriptorImpl(Annotations paramAnnotations, Name paramName)
  {
    super(paramAnnotations);
    this.name = paramName;
  }
  
  public static String toString(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(4);
    }
    try
    {
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append(DescriptorRenderer.DEBUG_TEXT.render(paramDeclarationDescriptor));
      ((StringBuilder)localObject1).append("[");
      ((StringBuilder)localObject1).append(paramDeclarationDescriptor.getClass().getSimpleName());
      ((StringBuilder)localObject1).append("@");
      ((StringBuilder)localObject1).append(Integer.toHexString(System.identityHashCode(paramDeclarationDescriptor)));
      ((StringBuilder)localObject1).append("]");
      localObject1 = ((StringBuilder)localObject1).toString();
      if (localObject1 == null) {
        $$$reportNull$$$0(5);
      }
      return localObject1;
    }
    finally
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramDeclarationDescriptor.getClass().getSimpleName());
      localStringBuilder.append(" ");
      localStringBuilder.append(paramDeclarationDescriptor.getName());
      paramDeclarationDescriptor = localStringBuilder.toString();
      if (paramDeclarationDescriptor == null) {
        $$$reportNull$$$0(6);
      }
    }
    return paramDeclarationDescriptor;
  }
  
  public Name getName()
  {
    Name localName = this.name;
    if (localName == null) {
      $$$reportNull$$$0(2);
    }
    return localName;
  }
  
  public DeclarationDescriptor getOriginal()
  {
    return this;
  }
  
  public String toString()
  {
    return toString(this);
  }
}
