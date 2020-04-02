package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract class VariableDescriptorWithInitializerImpl
  extends VariableDescriptorImpl
{
  protected NullableLazyValue<ConstantValue<?>> compileTimeInitializer;
  private final boolean isVar;
  
  public VariableDescriptorWithInitializerImpl(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, KotlinType paramKotlinType, boolean paramBoolean, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, paramKotlinType, paramSourceElement);
    this.isVar = paramBoolean;
  }
  
  public ConstantValue<?> getCompileTimeInitializer()
  {
    NullableLazyValue localNullableLazyValue = this.compileTimeInitializer;
    if (localNullableLazyValue != null) {
      return (ConstantValue)localNullableLazyValue.invoke();
    }
    return null;
  }
  
  public boolean isVar()
  {
    return this.isVar;
  }
  
  public void setCompileTimeInitializer(NullableLazyValue<ConstantValue<?>> paramNullableLazyValue)
  {
    if (paramNullableLazyValue == null) {
      $$$reportNull$$$0(4);
    }
    this.compileTimeInitializer = paramNullableLazyValue;
  }
}
